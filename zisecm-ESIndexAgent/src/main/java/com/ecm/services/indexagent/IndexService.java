package com.ecm.services.indexagent;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.DateUtils;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.EcmException;
import com.ecm.core.search.ESClient;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.QueueItemService;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Component
public class IndexService {
	
	private final Logger logger = LoggerFactory.getLogger(IndexService.class);
	
	@Autowired
	private QueueItemService queueItemService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ContentService contentService;
	
	public void reindexAll(String token, RestHighLevelClient client) {
		if(client == null)
			return;
		try {
			if(!ESClient.getInstance().indexExist(client, ESClient.getInstance().getPackageName())) {
				return ;
			}
			String sql = "select ID from ecm_document where is_hidden=0";
			List<Map<String, Object>> list;
			list = documentService.getMapList(token, sql);
			for(Map<String,Object> item : list) {
				String id = item.get("ID").toString();
				indexDocument( token, client, id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void indexFromQueue(String token, RestHighLevelClient client) {
		if(client == null)
			return;
		String condition = " NAME='ecm_full_index' and STATUS=0";
		List<EcmQueueItem> list = queueItemService.getObjects(token, condition);
		for(EcmQueueItem item : list) {
			if(indexDocument( token, client, item.getObjectId())){
				queueItemService.deleteObject(token, item);
			}
			else {
				item.setStatus(9);
				queueItemService.updateObject(token, item);
			}
		}
	}
	
	private boolean indexDocument(String token,RestHighLevelClient client, String docId) {
		EcmDocument doc = documentService.getObjectById(token, docId);
		if(doc != null) {
			IndexRequest request = new IndexRequest(ESClient.getInstance().getPackageName());
			request.id(docId);     //文档id 
			request.source(getDocumentJSon( token, doc), XContentType.JSON); 
			 IndexResponse indexResponse = null;
	            try {
	                // 同步方式
	                indexResponse = client.index(request, RequestOptions.DEFAULT);            
	            } catch(ElasticsearchException e) {
	                // 捕获，并处理异常
	                //判断是否版本冲突、create但文档已存在冲突
	                if (e.status() == RestStatus.CONFLICT) {
	                    logger.error("冲突了\n" + e.getDetailedMessage());
	                }
	                logger.error("索引异常", e);
	            } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            if(indexResponse != null) {
	                if (indexResponse.getResult() == Result.CREATED) {
	                	return true;
	                }
	            }
		}else {
			DeleteRequest request = new DeleteRequest();
			request.id(docId);
			DeleteResponse  delResponse = null;
            try {
                // 同步方式
            	delResponse = client.delete(request, RequestOptions.DEFAULT);            
            } catch(ElasticsearchException e) {
                // 捕获，并处理异常
                //判断是否版本冲突、create但文档已存在冲突
                if (e.status() == RestStatus.CONFLICT) {
                    logger.error("冲突了\n" + e.getDetailedMessage());
                }
                logger.error("索引异常", e);
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(delResponse != null) {
                if (delResponse.getResult() == Result.DELETED) {
                	return true;
                }
            }
		}
		return false;
	}
	
	private String getDocumentJSon(String token, EcmDocument doc) {
		Map<String, Object> map = doc.getAttributes();
		Map<String, String> indexMap = new HashMap<String, String>();
		StringBuilder allValue = new StringBuilder("");
		for(String attrName: map.keySet()) {
			if(!"ID".equalsIgnoreCase(attrName)) {
				Object obj = map.get(attrName.toUpperCase());
				if(ESClient.getInstance().isIndexField(attrName.toLowerCase())) {
					if(attrName.toLowerCase().endsWith("_date")) {
						if(obj instanceof Timestamp) {
							obj = DateUtils.sdfAll.format(new Date(((Timestamp)obj).getTime()));
						}
						indexMap.put(attrName.toLowerCase(), obj == null?null:obj.toString());
					}else {
						indexMap.put(attrName.toLowerCase(), obj == null?"":obj.toString());
					}
				}
				if(obj != null) {
					allValue.append(obj.toString().replace("\r", " ").replace("\n", " ")).append(" ");
				}
			}
		}
		indexMap.put("fileattr", allValue.toString());
//		for(String attrName: ESClient.getIncludeFields()) {
//			if(!StringUtils.isEmpty(attrName)) {
//				Object obj = map.get(attrName.toUpperCase());
//				if(attrName.endsWith("_date")) {
//					if(obj instanceof Timestamp) {
//						obj = DateUtils.sdfAll.format(new Date(((Timestamp)obj).getTime()));
//					}
//					indexMap.put(attrName, obj == null?null:obj.toString());
//				}else {
//					indexMap.put(attrName, obj == null?"":obj.toString());
//				}
//			}
//		}
		if(doc.getContentSize()>0 && doc.getContentSize()<ESClient.getInstance().getMaxSize()) {
			StringBuilder contentStr = new StringBuilder("");
			if(doc.getFormatName().equalsIgnoreCase("doc")) {
				EcmContent pc;
				try {
					pc = documentService.getContent(token, doc.getId());
					InputStream iss = contentService.getContentStream(token, pc);
					contentStr.append(new WordExtractor(iss).getText());
					iss.close();
				} catch (Exception e)
				{
					logger.error("Read content error objectId===" + doc.getId());
					logger.error(e.getMessage());
				}
				
			}
			else if(doc.getFormatName().equalsIgnoreCase("docx")) {
				try {
					EcmContent pc = documentService.getContent(token, doc.getId());
					InputStream iss = contentService.getContentStream(token, pc);
					contentStr.append(new XWPFWordExtractor(new XWPFDocument(iss)).getText());
					iss.close();
				} catch (Exception e)
				{
					logger.error("Read content error objectId===" + doc.getId());
					logger.error(e.getMessage());
				}
			}
			else if(doc.getFormatName().equalsIgnoreCase("pdf")) {
				try
				{
					EcmContent pc = documentService.getContent(token, doc.getId());
					InputStream iss = contentService.getContentStream(token, pc);
					PdfReader pdfReader = new PdfReader(iss); // 读取pdf所使用的输出流
					int num = pdfReader.getNumberOfPages();// 获得页数
					for (int i = 1; i < num + 1; i++)
					{
						contentStr.append(PdfTextExtractor.getTextFromPage(pdfReader, i)); // 读取第i页的文档内容

					}
					pdfReader.close();
					iss.close();
				} catch (Exception e)
				{
					logger.error("Read content error objectId===" + doc.getId());
					logger.error(e.getMessage());
				}
				
			}
			indexMap.put("filecontent", contentStr.toString());
		}
		return JSON.toJSONString(indexMap);
	}
	
}
