package com.ecm.core.sync;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.EcmRelationMapper;
import com.ecm.core.dao.ExcSynDetailMapper;
import com.ecm.core.dao.ExcTransferMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.entity.SyncBean;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.core.service.RelationService;
import com.ecm.core.util.SysConfig;
import com.ecm.icore.service.IEcmSession;
import com.ecm.icore.service.IExcSynDetailService;

@Service
public class SyncPublicNet implements ISyncPublicNet {
	private final Logger logger = LoggerFactory.getLogger(SyncPublicNet.class);
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	@Autowired
	IExcSynDetailService iExcSynDetailService;
	@Autowired
	private EcmContentMapper ecmContentMapper;
	@Autowired
	private ContentService contentService;
	@Autowired
	private EcmDocumentMapper ecmDocumentMapper;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ExcTransferServiceImpl transferService;
	@Autowired
	private ExcTransferMapper excTransferMapper;
	@Autowired
	private EcmRelationMapper ecmRelationMapper;
	@Autowired
	private RelationService relationService;
	@Autowired
	ExcSynDetailMapper excSynDetailMapper;
	@Autowired
	private AuthService authService;
	@Autowired
	private Environment env;
	String folderName = "";

	/**
	 * 1.1.读取需要同步的数据
	 * 
	 * @throws Exception
	 */

	public List<ExcSynDetail> readExcSynDetail() throws Exception {
		List<ExcSynDetail> excSynDetailObjList = iExcSynDetailService
				.selectByCondition(" APP_NAME='DOCEX' and ACTION_NAME in('提交')  and status='新建' ");
		TODOApplication.getNeedTOChange("同步数据清理，将 同一天多次修改，值只保留最后一次，将重复记录进行更新  ");
		return excSynDetailObjList;

	}

	/**
	 * 1.2.1.导出需要同步的数据到指定目录
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	public boolean exportData(String type) throws IOException {
//		List<ExcSynDetail> excSynDetailObjList = readExcSynDetail();
		List<ExcSynDetail> excSynDetailObjList = new ArrayList<ExcSynDetail>();
		List<String> docIds = new ArrayList<String>();
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		String token = null;
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
			token = ecmSession.getToken();

			List<SyncBean> resultObjList = new ArrayList<SyncBean>();
			folderName = getFolderName();
			if ("提交".equals(type)) {
				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc");
				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc_1");
			} else if ("CNPE驳回".equals(type)) {
				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc");
				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc_1");
			} else if ("CNPE接收".equals(type)) {
				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc");
				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc_1");
			} else if ("分发".equals(type)) {
				docIds.add("04b34fabc9d7449586a67a5fa8b0ea38");
				docIds.add("ef1bebb3fb2e4d77a8c61b2589432548");
			} else if ("分包商驳回".equals(type)) {
				docIds.add("04b34fabc9d7449586a67a5fa8b0ea38");
				docIds.add("ef1bebb3fb2e4d77a8c61b2589432548");
			} else if ("分包商接收".equals(type)) {
				docIds.add("04b34fabc9d7449586a67a5fa8b0ea38");
				docIds.add("ef1bebb3fb2e4d77a8c61b2589432548");
			}

			for (int i = 0; i < docIds.size(); i++) {
				SyncBean sb = generateSyncBean(token, type, docIds.get(i));
				exportFile(sb, getSyncPathPublic() + "/" + folderName + "/");
				resultObjList.add(sb);
			}
			TODOApplication.getNeedTOChange("需要根据不同类型，生成不同的json文件");
			writeJsonFile(resultObjList, folderName + ".json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != token)
				authService.logout(token);
		}
		updateExcSynDetailStatus(excSynDetailObjList, "已导出", new Date());
		String abusoluteFolderPath = getSyncPathPublic() + "/";
		String zipFilePath = abusoluteFolderPath + folderName + ".zip";
		ZipUtil.zip(abusoluteFolderPath + "/" + folderName + "/", zipFilePath);
		writeMD5Info(zipFilePath);
		return false;
	}

	/**
	 * @param ed
	 * @return
	 * @throws EcmException
	 */
	private SyncBean generateSyncBean(String token, String type, String docId) throws EcmException {
		SyncBean syncBean = new SyncBean();
		List<EcmRelation> relations = ecmRelationMapper.selectByCondition(" PARENT_ID='" + docId + "' ");
		syncBean.setRelations(relations);
		StringBuilder sb = new StringBuilder();
		sb.append("'").append(docId).append("'");
		for (int i = 0; i < relations.size(); i++) {
			sb.append(",'");
			sb.append(relations.get(i).getChildId());
			sb.append("'");
		}
		List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> transfers = null;
		String beanType = "create";
		if ("提交".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_提交";
		} else if ("分发".equals(type)) {
			transfers = excTransferMapper.executeSQL(
					"SELECT ID, ITEM_TYPE, DOC_ID, FROM_NAME, TO_NAME, CREATION_DATE, CREATOR, REJECTER, REJECT_DATE, SENDER, SEND_DATE, RECEIVER, RECEIVE_DATE, STAUTS, COMMENT, SYN_STATUS FROM exc_transfer where ID in('"
							+ docId + "') ");
			for (int i = 0; i < transfers.size(); i++) {
				Object transferDocId = transfers.get(i).get("DOC_ID");
				if (null != transferDocId) {
					sb.append(",'");
					sb.append(transferDocId.toString());
					sb.append("'");
				}
			}
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_分发";
		} else if ("CNPE驳回".equals(type)) {
			String col = "ID,STATUS,C_REJECT_COMMENT,C_REJECTOR,C_REJECT_DATE";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(documents, col);
			beanType = "update_CNPE驳回";
		} else if ("CNPE接收".equals(type)) {
			String col = "ID,STATUS,C_RECEIVER,C_RECEIVE_DATE";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(documents, col);
			beanType = "update_CNPE接收";
		} else if ("申请解锁".equals(type) || "解锁".equals(type)) {
			String col = "ID,C_PROCESS_STATUS";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(documents, col);
			beanType = "update_解锁_申请解锁";
		} else if ("分包商驳回".equals(type)) {
			String col = "ID,STATUS,REJECTER,REJECT_DATE,COMMENT";
			transfers = excTransferMapper
					.executeSQL("select " + col + " from exc_transfer where ID in('" + docId + "') ");
//			initResultList(transfers, col);
			beanType = "update_分包商驳回";
		} else if ("分包商接收".equals(type)) {
			String col = "ID,STATUS,RECEIVER,RECEIVE_DATE";
			transfers = excTransferMapper
					.executeSQL("select " + col + " from exc_transfer where ID in('" + docId + "') ");
//			initResultList(transfers, col);
			beanType = "update_分包商接收";
		}

		syncBean.setBeanType(beanType);
		syncBean.setDocuments(documents);
		syncBean.setTransfers(transfers);
		List<EcmContent> contents = ecmContentMapper.selectByCondition(" PARENT_ID in(" + sb.toString() + ") ");
		syncBean.setContents(contents);
		return syncBean;
	}

	/**
	 * @param resultList
	 * @param col
	 */
	private void initResultList(List<Map<String, Object>> resultList, String col) {
		for (int ii = 0; ii < resultList.size(); ii++) {
			Map<String, Object> mp=resultList.get(ii);
			String cols[] = col.replaceAll(" ", "").split(",");
			for (int i = 0; i < cols.length; i++) {
				mp.putIfAbsent(cols[i], null);
			}
		}
	}

	private void fatherToChild(Object father, Object child) {
		if (!(child.getClass().getSuperclass() == father.getClass())) {
			try {
				throw new Exception(child + "不是" + father + "的子类");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Class<? extends Object> fatherClass = father.getClass();
		Field[] fatherClassDeclaredFields = fatherClass.getDeclaredFields();
		for (Field tmpField : fatherClassDeclaredFields) {
			Method method = null;
			try {
				method = fatherClass.getMethod("get" + upperHeadChar(tmpField.getName()));
				Object invoke = method.invoke(father);
				tmpField.setAccessible(true);
				tmpField.set(child, invoke);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String upperHeadChar(String name) {
		String head = name.substring(0, 1);
		return head.toUpperCase() + name.substring(1, name.length());
	}

	/**
	 * @param zipFileName
	 * @throws IOException
	 */
	private void writeMD5Info(String zipFilePath) throws IOException {
		String md5 = generateZipFileMD5(zipFilePath);
		String md5FileName = zipFilePath + ".MD5.txt";
		FileWriter fw = new FileWriter(md5FileName, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(md5 + "\n"); // 字符串末尾不需要换行符
		pw.close();
		fw.close();
	}

	/**
	 * 1.2.2
	 * 
	 * @param folderPath
	 * @throws Exception
	 */
	private void exportFile(SyncBean sb, String folderPath) throws Exception {
		List<EcmContent> contents = sb.getContents();
		EcmContent en = null;
		for (int i = 0; i < contents.size(); i++) {
			en = contents.get(i);
			// 导出主文件
			InputStream in = getContentStream(en);
			String fileName = folderPath + generateFileNamePrefix(en);
			en.setFilePath(fileName);
			FileUtils.copyInputStreamToFile(in, new File(fileName));

		}
	}

	/**
	 * 1.2.3
	 * 
	 */
	public boolean updateExcSynDetailStatus(List<ExcSynDetail> objList, String status, Date updateDate) {
		ExcSynDetail en = new ExcSynDetail();
		en.setId("excsyncdetailtest01");
		en.setStauts(status);
		en.setExportDate(updateDate);
		excSynDetailMapper.updateByPrimaryKey(en);
		return false;
	}

	/**
	 * 1.3.取内网返回结果
	 */
	public SyncBean readJsonResult(String fileName) {
		List<SyncBean> objList = JSON.parseArray(readJsonFile(fileName), SyncBean.class);
		SyncBean en = objList.get(0);
		return en;
	}

	/**
	 * 1.4.将结果更新到外网系统
	 */
	public boolean updateExcSynDetail() {
		//
		updateExcSynDetailStatus(null, "已同步", new Date());
		return false;
	}

	/**
	 * 2.1从指定目录导入数据到当前系统
	 */
	@Override
	public boolean importData() {
		File fileDirectory = new File(getSyncPathPrivate());
		List<File> folderList = new ArrayList<File>();
		for (File temp : fileDirectory.listFiles()) {
			if (temp.isDirectory() && !temp.getName().startsWith("DONE_")) {
				folderList.add(temp);
			}
		}
		TODOApplication.getNeedTOChange("导入文件时，需要根据情况导入文件及相关关系");
		TODOApplication.getNeedTOChange("导入文件时，冲突处理：当导入数据时，发现同一天数据同时被内外网修改");
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		String token = null;
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
			token = ecmSession.getToken();
			SyncBean en = readJsonResult(folderList.get(0).toString() + "/" + folderList.get(0).getName() + ".json");
			List<Map<String, Object>> documents = en.getDocuments();
			List<Map<String, Object>> transfers = en.getTransfers();
			List<EcmRelation> relations = en.getRelations();
			List<EcmContent> contents = en.getContents();
			String beanType = en.getBeanType();
			for (int i = 0; documents != null && i < documents.size(); i++) {
				if (beanType.startsWith("create")) {
					documentService.newObject(token, documents.get(i));
				} else if (beanType.startsWith("update")) {
					documentService.updateObject(token, documents.get(i));
				}
			}

			for (int i = 0; transfers != null && i < transfers.size(); i++) {
				if (beanType.startsWith("create")) {
					transferService.newObject(transfers.get(i));
				} else if (beanType.startsWith("update")) {
					transferService.updateObject(transfers.get(i));
				}

			}

			for (int i = 0; relations != null && i < relations.size(); i++) {
				if (beanType.startsWith("create")) {
					relationService.newObject(token, relations.get(i));
				}
			}
			for (int i = 0; contents != null && i < contents.size(); i++) {
				BufferedInputStream fis = new BufferedInputStream(new FileInputStream(contents.get(i).getFilePath()));
				contents.get(i).setInputStream(fis);
				contentService.newObject(token, contents.get(i));
			}

			TODOApplication.getNeedTOChange("如果是升版，需要更新历史版本的字段");

			writeJsonResult(folderList.get(0), "DONE_");
		} catch (Exception e) {
			writeJsonResult(folderList.get(0), "ERROR_");
			e.printStackTrace();
		} finally {
			if (null != token)
				authService.logout(token);
		}

		return false;
	}

	/**
	 * 2.2从指定目录导入数据到当前系统后，将结果信息文件到指定目录 完成DONE_ 错误ERR_
	 */
	public boolean writeJsonResult(File folder, String result) {
		folder.renameTo(new File(getSyncPathPrivate() + "/" + result + folder.getName()));
		return false;
	}

	private String generateFileNamePrefix(EcmContent obj) {
		String fileName = obj.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(obj.getParentId()).append("_").append(obj.getId()).append("_").append(fileName);
		return sb.toString();
	}

	private String getSyncPathPublic() {
		String appPath = SysConfig.getSyncPathPublic();
		return appPath;
	}

	private String getSyncPathPrivate() {
		String appPath = SysConfig.getSyncPathPrivate();
		return appPath;
	}

	private String readJsonFile(String filePath) {
		Path ConfPath = Paths.get("", filePath);
		byte[] bytes = new byte[] {};
		try {
			bytes = Files.readAllBytes(ConfPath);
		} catch (Exception e) {
			logger.error("读取文件失败{}", ConfPath.toAbsolutePath(), e);
		}

		String jsonString = new String(bytes);
		return jsonString;
	}

	private void writeJsonFile(Object obj, String fileName) throws IOException {

		String jsonString = JSONObject.toJSONString(obj, true);
		Path ConfPath = Paths.get(getSyncPathPublic() + "/" + folderName, fileName);
		if (!Files.exists(ConfPath)) {
			FileUtils.forceMkdirParent(ConfPath.toFile());
			Files.createFile(ConfPath);
		}
		Files.write(ConfPath, jsonString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
	}

	public InputStream getContentStream(EcmContent en) throws Exception {
		// TODO Auto-generated method stub
		InputStream fis = null;
		String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		File f = new File(fullPath + en.getFilePath());
		en.setContentSize(f.length());
		fis = new BufferedInputStream(new FileInputStream(fullPath + en.getFilePath()));
		return fis;
	}

	public String getPrimaryFilePath(String objId) {
		// TODO Auto-generated method stub
		List<EcmContent> list = ecmContentMapper.getContents(objId, 1);
		if (list.size() > 0) {
			EcmContent en = list.get(0);
			String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			return storePath + en.getFilePath();
		}
		return null;
	}

	public String getFolderName() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(dt);
	}

	private String generateZipFileMD5(String filePath) throws IOException {
		String md5 = "";
		try (FileInputStream fis = new FileInputStream(filePath);) {
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
		}
		return md5;

	}

	@Override
	public boolean exportData() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
