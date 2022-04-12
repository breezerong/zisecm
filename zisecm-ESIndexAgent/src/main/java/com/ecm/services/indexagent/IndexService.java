package com.ecm.services.indexagent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.metadata.Metadata;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ecm.common.util.DateUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.search.ESClient;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.QueueItemService;
import com.google.common.base.Strings;

@Component
public class IndexService {

	private final Logger logger = LoggerFactory.getLogger(IndexService.class);

	@Autowired
	private QueueItemService queueItemService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private ContentService contentService;

	@Value("${ecm.reindex.sql}")
	private String sqlBase;

	@Value("${ecm.reindex.flag}")
	private String reindexFlag;

	@Value("${ecm.reindex.bufferSize}")
	private int bufferSize;

	@Value("${ecm.index.exclude.attrs}")
	private String excludeAttrs;
	@Value("${ocr_file_path_to}")
	private String ocr_file_path_to;
	@Value("${ocr_file_path_from}")
	private String ocr_file_path_from;
	@Value("${ocr_enable}")
	private String ocr_enable;

	public void reindexAll(String token, RestHighLevelClient client) {
		if (client == null)
			return;
		try {
			int tryTime = 0;
			int maxTime = 180;
			while (!ESClient.getInstance().indexExist(client, ESClient.getInstance().getPackageName())) {
				if (tryTime < maxTime) {
					tryTime++;
					Thread.sleep(1000);
				} else {
					logger.error("重做索引缓存刷新超时。");
					return;
				}
			}
//			String lastDate = reindexFlag;
			LocalDate localDate = LocalDate.now();
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String lastDate = localDate.format(fmt);
			String sql = sqlBase.replace("{0}", lastDate);
			List<Map<String, Object>> list;
			list = documentService.getMapList(token, sql);
			ids = new ArrayList<String>();
			while (list.size() > 0) {
				// 2次清除一次
				if (ids.size() > bufferSize) {
					ids = new ArrayList<String>();
				}
				for (Map<String, Object> item : list) {
					String id = item.get("ID").toString();
					lastDate = item.get("CREATION_DATE").toString();

					indexDocument(token, client, id, "ecm_full_index");
				}
				// 最后一笔
				if (list.size() < bufferSize) {
					break;
				}
				SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = shortSdf.parse(lastDate);
				String endAtStr = shortSdf.format(date);
				sql = sqlBase.replace("{0}", endAtStr);
				list = documentService.getMapList(token, sql);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void indexDocumentByIdsFile(String token, RestHighLevelClient client) {
		String classesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
		String path = classesPath + "ids.txt";
		if (System.getProperty("os.name").toLowerCase().contains("linux")) {
			path = "/" + path;
		}
		// logger.info("Start ids.txt path:"+path);
		File f = new File(path);
		int c = 0;
		if (f.exists()) {
			logger.info("Start ids.txt file index.");
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
				String s = null;
				while ((s = br.readLine()) != null) {
					try {
						indexDocument(token, client, s, "ecm_full_index");
						c++;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			logger.info("Finish ids.txt file index, count:" + c);
			f.delete();
		}
	}

	private List<String> ids = null;

	public void indexFromQueue(String token, RestHighLevelClient client) {
		if (client == null)
			return;
		String condition = " NAME='ecm_full_index' and STATUS=0";
		List<EcmQueueItem> list = queueItemService.getObjects(token, condition);
		ids = new ArrayList<String>();
		for (EcmQueueItem item : list) {

			if (indexDocument(token, client, item.getObjectId(), "ecm_full_index")) {
				queueItemService.deleteObject(token, item);
			} else {
				item.setStatus(9);
				queueItemService.updateObject(token, item);
			}
		}
	}

	public void indexFromQueueForOcr(String token, RestHighLevelClient client) {
		if (client == null)
			return;
		String condition = " NAME='ecm_pdf_ocr' and STATUS=0";
		List<EcmQueueItem> list = queueItemService.getObjects(token, condition);
		ids = new ArrayList<String>();
		for (EcmQueueItem item : list) {
			if (indexDocument(token, client, item.getObjectId(), "ecm_pdf_ocr")) {
				queueItemService.deleteObject(token, item);
			} else {
				item.setStatus(9);
				queueItemService.updateObject(token, item);
			}
		}
	}

	public boolean indexDocument(String token, RestHighLevelClient client, String docId, String indexType) {
		// 不要重复做索引
		if (ids != null && ids.contains(docId)) {
			logger.info("Indexed doc id:" + docId);
			return true;
		}
		EcmDocument doc = documentService.getObjectById(token, docId);
		Map<String, String> documentJSonMap= getDocumentJSon(token, doc, indexType);
		if(documentJSonMap.get("success").equals("false"))
			return false;
		ids.add(docId);
		if (doc != null) {
			logger.info("Indexing doc id:" + docId);
			IndexRequest request = new IndexRequest(ESClient.getInstance().getPackageName());
			request.id(docId); // 文档id
			request.source(documentJSonMap.get("data"), XContentType.JSON);
			IndexResponse indexResponse = null;
			try {
				// 同步方式
				indexResponse = client.index(request, RequestOptions.DEFAULT);
			} catch (ElasticsearchException e) {
				// 捕获，并处理异常
				// 判断是否版本冲突、create但文档已存在冲突
				if (e.status() == RestStatus.CONFLICT) {
					logger.error("冲突了\n" + e.getDetailedMessage());
				}
				logger.error("索引异常", e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (indexResponse != null) {
				logger.info("indexResponse.getResult():" + indexResponse.getResult());
				if (indexResponse.getResult() == Result.CREATED || indexResponse.getResult() == Result.UPDATED) {
					return true;
				}
			} else {
				logger.info("indexResponse is null.");
			}
		} else {
			logger.info("Deleting doc id:" + docId);
			DeleteRequest request = new DeleteRequest(ESClient.getInstance().getPackageName());
			request.id(docId);
			DeleteResponse delResponse = null;
			try {
				// 同步方式
				delResponse = client.delete(request, RequestOptions.DEFAULT);
			} catch (ElasticsearchException e) {
				// 捕获，并处理异常
				// 判断是否版本冲突、create但文档已存在冲突
				if (e.status() == RestStatus.CONFLICT) {
					logger.error("冲突了\n" + e.getDetailedMessage());
				}
				logger.error("索引异常", e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (delResponse != null) {
				if (delResponse.getResult() == Result.DELETED) {
					return true;
				}
			}
		}
		return false;
	}

	private Map<String,String> getDocumentJSon(String token, EcmDocument doc, String indexType) {
		Map<String, Object> map = doc.getAttributes();
		Map<String, String> indexMap = new HashMap<String, String>();
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "false");
		if ("true".equals(ocr_enable)) {
			if ("ecm_pdf_ocr".equals(indexType)) {
				if (doc.getFormatName().equalsIgnoreCase("pdf")) {
					File toFIle = new File(ocr_file_path_to + doc.getId() + ".txt");
					if (toFIle.exists()) {
						StringBuilder allValue = new StringBuilder("");
						getAttrValue(map, indexMap, allValue);
						indexMap.put("filecontent", allValue.append(txt2String(toFIle)).toString());
						resultMap.put("data", JSON.toJSONString(indexMap));
						resultMap.put("success", "true");
						toFIle.delete();
						return resultMap;
					}
				}
			} else {
				return  getDocumentJSonNoneOcr(token, doc);
			}
		}
		return resultMap;
	}

	private Map<String,String> getDocumentJSonNoneOcr(String token, EcmDocument doc) {
		Map<String, Object> map = doc.getAttributes();
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("success", "false");
		Map<String, String> indexMap = new HashMap<String, String>();
		StringBuilder allValue = new StringBuilder("");
		getAttrValue(map, indexMap, allValue);
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
		if (doc.getContentSize() > 0 && doc.getContentSize() < ESClient.getInstance().getMaxSize()) {
			StringBuilder contentStr = new StringBuilder("");
//			if(doc.getFormatName().equalsIgnoreCase("doc")) {
//				EcmContent en;
//				try {
//					en = documentService.getContent(token, doc.getId());
//					InputStream iss = contentService.getContentStream(token, en);
//					
//					String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
//					String filePath = fullPath+en.getFilePath();
//					String end = filePath.substring(filePath.length() - 4, filePath.length());
//					File endFile = null;
//					if(end.equals(".acg")) {
//						filePath = filePath.substring(0, filePath.length() - 4);
//				        File inputFile = new File(filePath);
//						String endPath =inputFile.getParent()+"\\DecryptionTempFolderPath_" + inputFile.getName();
//						endFile = new File(endPath);
//					}else {
//						File inputFile = new File(filePath);
//						String endPath =inputFile.getParent()+"\\DecryptionTempFolderPath_" + inputFile.getName();
//						endFile = new File(endPath);
//					}
////					contentStr.append(new WordExtractor(iss).getText());
//					contentStr.append(tika.parseToString(iss));
//					iss.close();
//					endFile.delete();
//				} catch (Exception e)
//				{
//					logger.error("Read content error objectId===" + doc.getId());
//					logger.error(e.getMessage());
//				}
//				
//			}
//			else if(doc.getFormatName().equalsIgnoreCase("docx")) {
//				try {
//					EcmContent en = documentService.getContent(token, doc.getId());
//					InputStream iss = contentService.getContentStream(token, en);
//					
//					String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
//					String filePath = fullPath+en.getFilePath();
//					String end = filePath.substring(filePath.length() - 4, filePath.length());
//					File endFile = null;
//					if(end.equals(".acg")) {
//						filePath = filePath.substring(0, filePath.length() - 4);
//				        File inputFile = new File(filePath);
//						String endPath =inputFile.getParent()+"\\DecryptionTempFolderPath_" + inputFile.getName();
//						endFile = new File(endPath);
//					}else {
//						File inputFile = new File(filePath);
//						String endPath =inputFile.getParent()+"\\DecryptionTempFolderPath_" + inputFile.getName();
//						endFile = new File(endPath);
//					}
////					contentStr.append(new XWPFWordExtractor(new XWPFDocument(iss)).getText());
//					contentStr.append(tika.parseToString(iss));
//					iss.close();
//					endFile.delete();
//				} catch (Exception e)
//				{
//					logger.error("Read content error objectId===" + doc.getId());
//					logger.error(e.getMessage());
//				}
//			}
//			else 

//				if(doc.getFormatName().equalsIgnoreCase("pdf")) {
			try {
				EcmContent en = documentService.getContent(token, doc.getId());

				File endFile = getEndFile(en);
				InputStream is = contentService.getContentStream(token, en);
				if (is != null) {
					contentStr.append(TikaImpl.parse(is, new Metadata(), 10000));
					if ("true".equals(ocr_enable) && doc.getFormatName().equalsIgnoreCase("pdf")) {
						if (contentStr.length() < 10) {// 少于10个汉字需要
							contentService.queue(token, en.getParentId(), "ecm_pdf_ocr", "ecm_pdf_ocr", null,null);
						} else {
							endFile.delete();
						}
					}

				}
			} catch (Exception e) {
				logger.error("Read content error objectId===" + doc.getId());
				logger.error(e.getMessage());
			}

//			}
			indexMap.put("filecontent", allValue.append(contentStr).toString());
		}
		resultMap.put("data", JSON.toJSONString(indexMap));
		resultMap.put("success", "true");
		return resultMap;
	}

	private File getEndFile(EcmContent en) {
		String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		String filePath = fullPath + en.getFilePath();
		String end = filePath.substring(filePath.length() - 4, filePath.length());
		File endFile = null;
		if (end.equals(".acg")) {
			filePath = filePath.substring(0, filePath.length() - 4);
			File inputFile = new File(filePath);
			String endPath = inputFile.getParent() + "\\DecryptionTempFolderPath_" + inputFile.getName();
			endFile = new File(endPath);
		} else {
			File inputFile = new File(filePath);
			String endPath = inputFile.getParent() + "\\DecryptionTempFolderPath_" + inputFile.getName();
			endFile = new File(endPath);
		}
		return endFile;
	}

	private void getAttrValue(Map<String, Object> map, Map<String, String> indexMap, StringBuilder allValue) {
		for (String attrName : map.keySet()) {
			if (!"ID".equalsIgnoreCase(attrName)) {
				Object obj = map.get(attrName.toUpperCase());
				if (ESClient.getInstance().isIndexField(attrName.toLowerCase())) {
					if (attrName.toLowerCase().endsWith("_date")) {
						if (obj instanceof Timestamp) {
							obj = DateUtils.sdfAll.format(new Date(((Timestamp) obj).getTime()));
						}
						indexMap.put(attrName.toLowerCase(), obj == null ? null : obj.toString());
					} else {
						indexMap.put(attrName.toLowerCase(), obj == null ? "" : obj.toString());
					}
				}
				if (obj != null && attrName.indexOf("_ID") < 0 && excludeAttrs.indexOf(attrName) < 0) {
					allValue.append(obj.toString().replace("\r", " ").replace("\n", " ")).append(" ");
				}
			}
		}
		indexMap.put("fileattr", allValue.toString());
	}

	public static String txt2String(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
