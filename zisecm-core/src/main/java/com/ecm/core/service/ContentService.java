package com.ecm.core.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IContentService;
/**
 * 内容服务
 * @author Haihong Rong
 * @date 2019年7月21日 上午8:30:45
 */
@Service
@Scope("prototype")
public class ContentService extends EcmObjectService<EcmContent> implements IContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
			
	@Autowired
	private EcmContentMapper contentMapper;

	@Autowired
	private QueueItemService queueItemService;

	
	@Override
	public List<EcmContent> getObjects(String token, String docId, int contentType) {
		if(contentType<1)
		{
			return contentMapper.getAllContents(docId);
		}
		return contentMapper.getContents(docId, contentType);
	}
	
	@Override
	public List<EcmContent> getObjects(String token, String condition) {
		
		return contentMapper.selectByCondition(condition);
	}

	@Override
	public String newObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		try {
			EcmContent en = (EcmContent)obj;
			if(StringUtils.isEmpty(en.getId())) {
				en.createId();
			}
			en.setDataTicket(contentMapper.selectDataTicket(DBFactory.getDBConn().getDBSequece().getContentDataTicketSql()));
			String fullPath = getFullPath(en);
			logger.info("Full path:{}",fullPath);
			File file = new File(fullPath);   
			long len = 0;
			BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(file));
			try
			{
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = en.getInputStream().read(buffer)) != -1) {
				   fis.write(buffer, 0, bytesRead);
				   len += bytesRead;
				}
			}
			finally
			{
				fis.close();
			}
			en.setContentSize(len);
			en.setCreationDate(new Date());
			en.setCreator(getSession(token).getCurrentUser().getUserName());
			en.setModifiedDate(en.getCreationDate());
			en.setModifier(en.getCreator());
			contentMapper.insert(en);
			createPdfOcrEvent(token, en);
			logger.info("Insert content:{}",en.getId());
			return en.getId();
		}catch(Exception ex) {
			new EcmException(ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean updateObject(String token, Object obj) throws EcmException {
		try {
			EcmContent en = (EcmContent)obj;
			boolean ocrUpdate = "OCR".equalsIgnoreCase(en.getDescription());
			if(ocrUpdate) {
				en.setDescription("");
			}
			// TODO Auto-generated method stub
			if(en.getInputStream()!=null && en.getFilePath()!=null && en.getFilePath().length()>0)
			{
				String filePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
				filePath += File.separator + en.getFilePath();
				File f = new File(filePath);
				f.delete();
			}
			String fullPath = getFullPath(en);
			File file = new File(fullPath);   
			long len = 0;
			BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(file));
			try
			{
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = en.getInputStream().read(buffer)) != -1) {
				   fis.write(buffer, 0, bytesRead);
				   len += bytesRead;
				}
			}
			finally
			{
				fis.close();
			}
			en.setContentSize(len);
			en.setModifiedDate(new Date());
			en.setModifier(getSession(token).getCurrentUser().getUserName());
			contentMapper.updateByPrimaryKey(en);
			if(!ocrUpdate) {
				createPdfOcrEvent(token, en);
			}
			return true;
		}
		catch(Exception ex) {
			new EcmException(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteObject(String token, Object obj) {
		// TODO Auto-generated method stub
		return contentMapper.deleteByPrimaryKey(((EcmContent)obj).getId())>0;
	}

	@Override
	public InputStream getContentStream(String token, EcmContent en) throws Exception {
		// TODO Auto-generated method stub
		InputStream fis = null;
		String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		File f = new File(fullPath+en.getFilePath());
		en.setContentSize(f.length());
		fis = new BufferedInputStream(new FileInputStream(fullPath+en.getFilePath()));
		return fis;
	}

	@Override
	public EcmContent getObjectById(String token, String objId) {
		// TODO Auto-generated method stub
		return contentMapper.selectByPrimaryKey(objId);
	}
	/**
	 */
	@Override
	public EcmContent getObject(String token, String objId,int contentType,String formatName) {
		// TODO Auto-generated method stub
		String sql = " PARENT_ID='"+DBFactory.getDBConn().getDBUtils().getString(objId)
				+"' and FORMAT_NAME='"+DBFactory.getDBConn().getDBUtils().getString(formatName)+"'";
		if(contentType>0) {
			sql = " PARENT_ID='"+DBFactory.getDBConn().getDBUtils().getString(objId)+"' and CONTENT_TYPE="+contentType
					+" and FORMAT_NAME='"+DBFactory.getDBConn().getDBUtils().getString(formatName)+"'";
		}
		List<EcmContent> list = contentMapper.selectByCondition(sql);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public void copyContent(String token, String fromDocId,String toDocId) throws Exception {
		List<EcmContent> list = getObjects(token, toDocId, 0);
		if(list.size()>0) {
			throw new EcmException("Document already has content:"+toDocId); 
		}
		list = getObjects(token, fromDocId, 0);
		for(EcmContent conent:list) {
			conent.setInputStream(getContentStream( token, conent));
			conent.createId();
			conent.setParentId(toDocId);
			this.newObject(token, conent);
		}
	}
	
	private String getFullPath(EcmContent en) throws Exception
	{
		String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		String fullPath= storePath;
//		String os = System.getProperty("os.name").toLowerCase(); 
//		String splitStr = os.startsWith("win")?"\\":"/";
		if(fullPath.endsWith(File.separator))
		{
			fullPath = fullPath.substring(0,fullPath.length()-1);
		}
		Date dt = new Date();
		fullPath += getPath(en.getDataTicket());
		//Document ID文件路径+类型+时间+格式，格式副本在同一个目录。
		String  fileName = fullPath +"_"+en.getContentType()+"."+en.getFormatName();
		en.setFilePath(fileName.replace(storePath, ""));
		Path path = Paths.get(fileName);
		if (!Files.isWritable(path)) {
			fullPath = fullPath.substring(0,fullPath.length()-3);
			Files.createDirectories(Paths.get(fullPath));
		}
		return fileName;
	}
	/**
	 * 创建4级目录+
	 * @param id
	 * @param splitStr
	 * @return
	 */
	private String getPath(Long id) {
		//最多1078203909375
		String strHex = String.format("%010x", id);
		String path ="";
		for(int i=0;i<5;i++) {
			path +=File.separator+strHex.substring(i*2, i*2+2);
		}
		return path;
	}

	@Override
	public EcmContent getPrimaryContent(String token, String objId) {
		// TODO Auto-generated method stub
		List<EcmContent> list = contentMapper.getContents(objId, 1);
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public String getPrimaryFilePath(String token, String objId) {
		// TODO Auto-generated method stub
		List<EcmContent> list = contentMapper.getContents(objId, 1);
		if(list.size()>0) {
			EcmContent en = list.get(0);
			String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			return storePath+en.getFilePath();
		}
		return null;
	}
	@Override
	public boolean updatePrimaryContentSize(String token, String docId,long fileSize) {
		List<EcmContent> list = contentMapper.getContents(docId, 1);
		if(list.size()>0) {
			EcmContent en = list.get(0);
			en.setContentSize(fileSize);
			return contentMapper.updateByPrimaryKey(en)>0;
		}
		return false;
	}
	
	/**
	 * 是否启用PDF OCR
	 * @return
	 */
	private boolean pdfOcrEnabled() {
		boolean ret = false;
		try {
			ret = Boolean.parseBoolean(CacheManagerOper.getEcmParameters().get("PdfOcrEnabled").getValue());
		}
		catch(Exception ex) {
			
		}
		return ret;
	}
	/**
	 * PDF OCR 最大文件大小
	 * @return
	 */
	private int pdfOcrMaxSize() {
		//默认50MB
		int maxSize = 50485760;
		try {
			maxSize = Integer.parseInt(CacheManagerOper.getEcmParameters().get("MaxIndexSize").getValue());
		}
		catch(Exception ex) {
			
		}
		return maxSize;
	}

	private void createPdfOcrEvent(String token, EcmContent en) throws EcmException, AccessDeniedException {
		if(pdfOcrEnabled()&& en!= null && en.getFormatName().equals("pdf") && en.getContentSize()<=pdfOcrMaxSize()) {
			queue(token, en.getId(), "ecm_pdf_ocr", "ecm_pdf_ocr", en.getParentId(),null);
		}
	}
	
	public void queue(String token, String id, String name, String eventName, String parentId,String message)
			throws EcmException, AccessDeniedException {
		EcmQueueItem queueItem = new EcmQueueItem();
		queueItem.createId();
		queueItem.setObjectId(id);
		queueItem.setName(name);
		queueItem.setEventName(eventName);
		queueItem.setRouterId(parentId);
		queueItem.setMessage(message);
		queueItem.setDelectFlag(false);
		queueItem.setStatus(0);
		queueItemService.newObject(token, queueItem);
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return contentMapper.deleteByPrimaryKey(id)>0;
	}
}
