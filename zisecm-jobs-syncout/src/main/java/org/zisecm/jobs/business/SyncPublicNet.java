package org.zisecm.jobs.business;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.entity.SyncBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.EcmRelationMapper;
import com.ecm.core.dao.ExcSynBatchMapper;
import com.ecm.core.dao.ExcSynDetailMapper;
import com.ecm.core.dao.ExcTransferMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.ExcSynBatch;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynBatchService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.RelationService;
import com.ecm.core.service.UserService;
import com.ecm.core.util.SysConfig;
import com.ecm.icore.service.IEcmSession;
import com.ecm.icore.service.IExcSynDetailService;

@Service
public class SyncPublicNet implements ISyncPublicNet {
	private final Logger logger = LoggerFactory.getLogger(SyncPublicNet.class);
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	@Autowired
	private ExcSynBatchService batchService;
	@Autowired
	IExcSynDetailService iExcSynDetailService;
	@Autowired
	private EcmContentMapper ecmContentMapper;
	@Autowired
	private EcmDocumentMapper ecmDocumentMapper;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
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
	ExcSynBatchMapper excSynBatchMapper;

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

	private List<ExcSynDetail> readExcSynDetail(String actionName) throws Exception {
//		List<ExcSynDetail> excSynDetailObjList = iExcSynDetailService.selectByCondition(" APP_NAME='DOCEX' and ACTION_NAME in('"+actionName+"')  and status='新建' ");
		List<ExcSynDetail> excSynDetailObjList = iExcSynDetailService
				.selectByCondition(" APP_NAME='DOCEX' and ACTION_NAME in('" + actionName
						+ "')  and (status is null  or status not in('已同步','已导出','错误'))  order by CREATION_DATE asc ");
		return excSynDetailObjList;

	}

	/**
	 * 1.2.1.导出需要同步的数据到指定目录
	 * 
	 * @throws Exception
	 */
	@Override
	public boolean exportData(String actionName) throws Exception {
		IEcmSession ecmSession = null;
		String userName = env.getProperty("ecm.username");
		String token = null;
		try {
			ecmSession = authService.login("内外网同步", userName, env.getProperty("ecm.password"));
			token = ecmSession.getToken();
			folderName = getFolderName();
			List<SyncBean> resultObjList = new ArrayList<SyncBean>();

			List<ExcSynDetail> excSynDetailObjList = null;
			if (actionName.equals("导出用户")) {
				excSynDetailObjList = exportUserInner(token, resultObjList);
			} else {
				excSynDetailObjList = exportDataInner(actionName, token, resultObjList);
			}
			if (resultObjList.size() > 0) {
				if(!(resultObjList.size()==1 && resultObjList.get(0).getBeanType().equals("create_计划同步") && resultObjList.get(0).getDocuments().size()==0)){
					writeJsonFile(resultObjList, folderName + ".json");
					updateExcSynDetailStatus(excSynDetailObjList, "已导出", folderName);
					String abusoluteFolderPath = getSyncPathPublic() + "/";
					String folderFullPath = abusoluteFolderPath + folderName;
					String zipFilePath = folderFullPath + ".zip";
					ZipUtil.zip(folderFullPath + "/", zipFilePath);
					writeMD5Info(zipFilePath);
					FileUtils.deleteDirectory(new File(folderFullPath));
					updateExcSynBatchStatus(resultObjList, "已导出");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (null != token)
				authService.logout(token);
		}
		return false;
	}

	/**
	 * @param actionName
	 * @param token
	 * @param resultObjList
	 * @return
	 * @throws Exception
	 * @throws EcmException
	 */
	private List<ExcSynDetail> exportDataInner(String actionName, String token, List<SyncBean> resultObjList)
			throws Exception, EcmException {
		List<ExcSynDetail> excSynDetailObjList = readExcSynDetail(actionName);
		List<String> docIds = new ArrayList<String>();
		for (Iterator<ExcSynDetail> iterator = excSynDetailObjList.iterator(); iterator.hasNext();) {
			ExcSynDetail excSynDetail = iterator.next();
			docIds.add(excSynDetail.getFromId());
		}
		docIds = docIds.stream().distinct().collect(Collectors.toList());
//			if ("提交".equals(actionName)) {
//				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc");
//				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc_1");
//			} else if ("CNPE驳回".equals(actionName)) {
//				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc");
//				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc_1");
//			} else if ("CNPE接收".equals(actionName)) {
//				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc");
//				docIds.add("ef993cc172fe46c9b752fb0cf1c52cdc_1");
//			} else if ("分发".equals(actionName)) {
//				docIds.add("04b34fabc9d7449586a67a5fa8b0ea38");
//				docIds.add("ef1bebb3fb2e4d77a8c61b2589432548");
//			} else if ("分包商驳回".equals(actionName)) {
//				docIds.add("04b34fabc9d7449586a67a5fa8b0ea38");
//				docIds.add("ef1bebb3fb2e4d77a8c61b2589432548");
//			} else if ("分包商接收".equals(actionName)) {
//				docIds.add("04b34fabc9d7449586a67a5fa8b0ea38");
//				docIds.add("ef1bebb3fb2e4d77a8c61b2589432548");
//			}

		if ("计划同步".equals(actionName)) {
			SyncBean sb = generateSyncBean(token, actionName, null);
			// TODOApplication.getNeedTOChange("正式环境需要导出文件，取消如下注释");
			resultObjList.add(sb);
		} else {

			for (int i = 0; i < docIds.size(); i++) {
				SyncBean sb = generateSyncBean(token, actionName, docIds.get(i));
				// TODOApplication.getNeedTOChange("正式环境需要导出文件，取消如下注释");
				exportFile(sb, getSyncPathPublic() + "/" + folderName + "/");
				resultObjList.add(sb);
			}
		}
		return excSynDetailObjList;
	}

	/**
	 * @param token
	 * @param resultObjList
	 * @return
	 * @throws Exception
	 * @throws EcmException
	 * @throws AccessDeniedException
	 * @throws NoPermissionException
	 */
	private List<ExcSynDetail> exportUserInner(String token, List<SyncBean> resultObjList)
			throws Exception, EcmException, AccessDeniedException, NoPermissionException {
		List<ExcSynDetail> excSynDetailObjList = readExcSynDetail("新建用户','修改用户','添加到角色','移除用户");
		EcmUser ecmUser = null;
		for (Iterator<ExcSynDetail> iterator = excSynDetailObjList.iterator(); iterator.hasNext();) {
			ExcSynDetail excSynDetail = iterator.next();
			String type = excSynDetail.getActionName();
			SyncBean syncBean = new SyncBean();
			syncBean.setBeanType(type);
			switch (type) {
			case "新建用户":
			case "修改用户":
				ecmUser = userService.getObjectById(token, excSynDetail.getFromId());
				break;
			case "添加到角色":
			case "移除用户":
				ecmUser = new EcmUser();
				ecmUser.setGroupName(excSynDetail.getToId());
				ecmUser.setName(excSynDetail.getFromId());
				break;
			default:
				break;
			}
			syncBean.setEcmUser(ecmUser);
			resultObjList.add(syncBean);
		}
		return excSynDetailObjList;
	}

	/**
	 * @param ed
	 * @return
	 * @throws EcmException
	 */
	private SyncBean generateSyncBean(String token, String type, String docId) throws EcmException {
		SyncBean syncBean = new SyncBean();
		List<EcmRelation> relations = null;
		if (docId == null) {
			relations = new ArrayList<EcmRelation>();
		} else {
			relations = ecmRelationMapper.selectByCondition(" PARENT_ID='" + docId + "' ");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("'").append(docId).append("'");
		for (int i = 0; relations!=null && i < relations.size(); i++) {
			sb.append(",'");
			sb.append(relations.get(i).getChildId());
			sb.append("'");
			if(relations.get(i).getName().equals("设计文件")) {
				List<EcmRelation>  relationsAtt = ecmRelationMapper.selectByCondition(" PARENT_ID='" + relations.get(i).getChildId() + "' AND NAME='附件' ");
				for (int a = 0; relationsAtt != null && a < relationsAtt.size(); a++) {
					relations.add(relationsAtt.get(a));
					sb.append(",'");
					sb.append(relationsAtt.get(a).getChildId());
					sb.append("'");
				}
			}
		}
		String onlyDoc = "'" + docId + "'";
		boolean updateConent = true;
		boolean updateRelation = true;
		List<Map<String, Object>> documents = new ArrayList<Map<String, Object>>();
		List<ExcSynBatch> synBatchList = new ArrayList<ExcSynBatch>();
		List<Map<String, Object>> transfers = null;
		String beanType = "create";
		if ("提交".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_提交";
		} else if ("驳回提交".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + onlyDoc + ") ");
			beanType = "create_驳回提交";
		} else if ("升版".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + onlyDoc + ") ");
			beanType = "update_升版";
			updateConent = false;
			updateRelation = false;
		} else if ("延误打开反馈".equals(type) || "延误打开确认".equals(type) ||
				"延误关闭反馈".equals(type) || "延误关闭确认".equals(type) ||
				"延误回复反馈".equals(type) || "延误回复确认".equals(type)
				) {
			documents = documentService.getObjectMap(token, " ID in(" + onlyDoc + ") ");
			beanType = "update_"+type;
			updateConent = false;
			updateRelation = false;
		} else if ("新建".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_新建";
		} else if ("变更".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + onlyDoc + ") ");
			beanType = "create_变更";
			updateConent = false;
			updateRelation = false;
		} else if ("删除".equals(type)) {
			documents = documentService.getMapList(token,
					"SELECT ID, TYPE_NAME from ecm_document where ID in(" + onlyDoc + ") ");
			beanType = "delete_删除";
			updateConent = false;
			updateRelation = false;
		} else if ("修改".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + onlyDoc + ") ");
			beanType = "update_修改";

			updateRelation = false;
		} else if ("分发".equals(type)) {
			transfers = excTransferMapper.executeSQL(
					"SELECT ID, ITEM_TYPE, DOC_ID, FROM_NAME, TO_NAME, CREATION_DATE, CREATOR, REJECTER, REJECT_DATE, SENDER, SEND_DATE, RECEIVER, RECEIVE_DATE, STATUS, COMMENT, SYN_STATUS FROM exc_transfer where DOC_ID in('"
							+ docId + "') ");
//			for (int i = 0; i < transfers.size(); i++) {
//				Object transferDocId = transfers.get(i).get("DOC_ID");
//				if (null != transferDocId) {
//					sb.append(",'");
//					sb.append(transferDocId.toString());
//					sb.append("'");
//				}
//			}
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_分发";
		} else if ("CNPE驳回".equals(type)) {
			String col = "ID,STATUS,C_REJECT_COMMENT,C_REJECTOR,C_REJECT_DATE";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(documents, col);
			beanType = "update_CNPE驳回";
			updateConent = false;
			updateRelation = false;
		} else if ("CNPE接收".equals(type)) {
			String col = "ID,STATUS,C_IS_RELEASED,C_RECEIVER,C_RECEIVE_DATE";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(documents, col);
			updateConent = false;
			updateRelation = false;
			beanType = "update_CNPE接收";
		} else if ("申请解锁".equals(type) || "解锁".equals(type)) {
			String col = "ID,C_PROCESS_STATUS";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(documents, col);
			beanType = "update_解锁_申请解锁";
			updateConent = false;
			updateRelation = false;
		} else if ("分包商驳回".equals(type)) {
			String col = "ID,STATUS,REJECTER,REJECT_DATE,COMMENT";
			transfers = excTransferMapper
					.executeSQL("select " + col + " from exc_transfer where ID in('" + docId + "') ");
//			initResultList(transfers, col);
			beanType = "update_分包商驳回";
			updateConent = false;
			updateRelation = false;
		} else if ("分包商接收".equals(type)) {
			String col = "ID,STATUS,RECEIVER,RECEIVE_DATE";
			transfers = excTransferMapper
					.executeSQL("select " + col + " from exc_transfer where ID in('" + docId + "') ");
//			initResultList(transfers, col);
			beanType = "update_分包商接收";
			updateConent = false;
			updateRelation = false;
		} else if ("驳回".equals(type)) {
			String col = "ID,STATUS,C_REJECTOR,C_REJECT_DATE,C_REJECT_COMMENT";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(transfers, col);
			beanType = "update_驳回";
			updateConent = false;
			updateRelation = false;
		} else if ("接收".equals(type)) {
			String col = "ID,STATUS,C_RECEIVER,C_RECEIVE_DATE";
			documents = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(transfers, col);
			beanType = "update_接收";
			updateConent = false;
			updateRelation = false;
		} else if ("新建问题".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_新建问题";
		} else if ("回复问题".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_回复问题";
		} else if ("申请驳回".equals(type) || "确认驳回".equals(type)) {
			transfers = excTransferMapper.executeSQL(
					"SELECT ID, ITEM_TYPE, DOC_ID, FROM_NAME, TO_NAME, CREATION_DATE, CREATOR, REJECTER, REJECT_DATE, SENDER, SEND_DATE, RECEIVER, RECEIVE_DATE, STATUS, COMMENT, SYN_STATUS,STATUS1,COMMENT1"
							+ " FROM exc_transfer where ID in('" + docId + "') ");
			// 分包商申请驳回，内网不存在分发对象，直接创建
			beanType = "update_" + type;
			updateConent = false;
			updateRelation = false;
		} else if ("计划同步".equals(type)) {
			// 已导出，已内外网同步
			String condition = "select C_PROJECT_NAME from ecm_document where TYPE_NAME='计划' AND ID IN (select BATCH_NUM from exc_syn_batch where APP_NAME='P6' AND STATUS='已同步')";
			documents = documentService.getObjectMap(token,
					" TYPE_NAME='计划任务' and SUB_TYPE in ('WBS','Activity') and C_PROJECT_NAME in (" + condition + ")");
			synBatchList = excSynBatchMapper.getByCondition("APP_NAME='P6' AND STATUS='已同步'");
			beanType = "create_" + type;
			updateConent = false;
			updateRelation = false;
		}

		syncBean.setBeanType(beanType);
		syncBean.setDocuments(documents);
		syncBean.setTransfers(transfers);
		syncBean.setSynBatchList(synBatchList);

		if (updateRelation) {
			syncBean.setRelations(relations);
		}
		if (updateConent) {
			List<EcmContent> contents = ecmContentMapper.selectByCondition(" PARENT_ID in(" + sb.toString() + ") ");
			syncBean.setContents(contents);
		}

		return syncBean;
	}

	/**
	 * @param resultList
	 * @param col
	 */
	private void initResultList(List<Map<String, Object>> resultList, String col) {
		for (int ii = 0; ii < resultList.size(); ii++) {
			Map<String, Object> mp = resultList.get(ii);
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
		FileWriter fw = new FileWriter(md5FileName, false);
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
		for (int i = 0; contents != null && i < contents.size(); i++) {
			en = contents.get(i);
			// 导出主文件
			InputStream in = getContentStream(en);
			String fileName = generateFileNamePrefix(en);
			en.setFilePath(fileName);
			//String s = new String((folderPath + fileName).getBytes("ISO-8859-1"),"GBK");
			FileUtils.copyInputStreamToFile(in, new File(folderPath + fileName));
			in.close();
		}
	}

	/**
	 * 1.2.3
	 * 
	 */
	private boolean updateExcSynDetailStatus(List<ExcSynDetail> objList, String status, String batchNum) {
		Date updateDate = new Date();
		for (Iterator<ExcSynDetail> iterator = objList.iterator(); iterator.hasNext();) {
			ExcSynDetail excSynDetail = iterator.next();
			excSynDetail.setStatus(status);
			excSynDetail.setBatchNum(batchNum);
			excSynDetail.setExportDate(new Date());
			excSynDetailMapper.updateByPrimaryKey(excSynDetail);

		}
		ExcSynBatch temp = new ExcSynBatch();
		temp.setAppName("DOCEX");
		temp.setCreationDate(updateDate);
		temp.setActionName("同步");
		temp.setStatus(status);
		temp.setBatchNum(batchNum);
		batchService.newObject(temp);
		return true;
	}

	/**
	 * 1.2.3
	 * 
	 */
	private boolean updateExcSynBatchStatus(List<SyncBean> resultObjList, String status) {

		for (SyncBean syncBean : resultObjList) {
			List<ExcSynBatch> list = syncBean.getSynBatchList();
			if (list != null && list.size() > 0) {
				for (ExcSynBatch excSynBatch : list) {
					excSynBatch.setStatus("已导出");
					excSynBatchMapper.updateByPrimaryKey(excSynBatch);
				}
			}
		}

		return true;
	}

	/**
	 * 1.3.取内网返回结果
	 */
	private List<SyncBean> readJsonResult(String fileName) {
		List<SyncBean> objList = JSON.parseArray(readJsonFile(fileName), SyncBean.class);
		return objList;
	}

	/**
	 * 1.4.将结果更新到外网系统
	 */
	public boolean UpdateImportResultStatus() {
		//
		File fileDirectory = new File(getSyncPathPrivate());
		String NetWorkEnv = CacheManagerOper.getEcmParameters().get("NetWorkEnv").getValue();
		for (File temp : fileDirectory.listFiles()) {
			String fileName = temp.getName();
			if (!temp.isDirectory() && fileName.endsWith("zip") && fileName.startsWith("DONE_" + NetWorkEnv)) {
				String batchNum = fileName.split("\\.")[0].substring(5);
				excSynDetailMapper
						.executeSQL("update exc_syn_detail set STATUS='已同步' where BATCH_NUM ='" + batchNum + "'");
				List<ExcSynBatch> syncBatchList = batchService.getByCondition("BATCH_NUM='" + batchNum + "'");
				if (syncBatchList.size() > 0) {
					ExcSynBatch syncBatch = syncBatchList.get(0);
					syncBatch.setExecuteDate(new Date());
					syncBatch.setStatus("已同步");
					batchService.updateObject(syncBatch);
				}
				// 删除完成数据
				String fileOrgName = temp.getAbsolutePath();
				temp.delete();
				File f =new File(fileOrgName + ".MD5.txt");
				if(f.exists()) {
					f.delete();
				}
					
			}
		}
		return false;
	}

	/**
	 * 2.1从指定目录导入数据到当前系统
	 */
	@Override
	public boolean importData(String actionName) {
		File fileDirectory = new File(getSyncPathPrivate());
		List<File> zipFileList = new ArrayList<File>();
		String ImportNetWorkEnv = "UNKNOW";
		switch (CacheManagerOper.getEcmParameters().get("NetWorkEnv").getValue()) {
		case "OUT":
			ImportNetWorkEnv = "IN";
			break;
		case "IN":
			ImportNetWorkEnv = "OUT";
			break;
		default:
			break;
		}

		for (File temp : fileDirectory.listFiles()) {
			String fileName = temp.getName();
			if (!temp.isDirectory() && fileName.endsWith("zip") && fileName.startsWith(ImportNetWorkEnv)) {
				if (new File(temp.getAbsolutePath() + ".MD5.txt").exists()) {
					zipFileList.add(temp);
				}
			}
		}
		IEcmSession ecmSession = null;
		String userName = env.getProperty("ecm.username");
		String token = null;
		File zipFile = null;
		try {
			ecmSession = authService.login("内外网同步", userName, env.getProperty("ecm.password"));
			token = ecmSession.getToken();
			String zipFolderPath = fileDirectory.getAbsolutePath() + "/";
			for (Iterator<File> iterator = zipFileList.iterator(); iterator.hasNext();) {
				zipFile = iterator.next();

				String zipFileFullPath = zipFolderPath + zipFile.getName();
				String zipMD5FileFullPath = zipFolderPath + zipFile.getName() + ".MD5.txt";
				String MD5_org = readFirstLine(zipMD5FileFullPath).replaceAll("[\\s\\t\\n\\r]", "");
				if (generateZipFileMD5(zipFileFullPath).equals(MD5_org)) {
					unZip(zipFileFullPath, zipFolderPath);
					List<SyncBean> syncBeanList = readJsonResult(zipFile.toString().replace(".zip", "") + "/"
							+ zipFile.getName().replace(".zip", "") + ".json");
					if (syncBeanList != null && syncBeanList.size()>0) 
					{
						String beanType = syncBeanList.get(0).getBeanType();
						if(beanType.equals("新建用户")||beanType.equals("修改用户")
								||beanType.equals("添加到角色")||beanType.equals("移除用户")){
							importUserInner(token, syncBeanList);
						} else {
							importDataInner(token, syncBeanList, zipFileFullPath.replace(".zip", ""));
						}
					}
					writeJsonResult(zipFile, "DONE_");

				} else {
					writeJsonResult(zipFile, "ERROR_MD5_");
				}
				FileUtils.deleteDirectory(new File(zipFile.toString().replace(".zip", "")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			if (zipFile != null)
				writeJsonResult(zipFile, "ERROR_");
		} finally {
			if (null != token)
				authService.logout(token);
		}

		return false;
	}

	/**
	 * @param token
	 * @param syncBeanList
	 * @throws EcmException
	 * @throws AccessDeniedException
	 * @throws NoPermissionException
	 * @throws Exception
	 */
	private void importUserInner(String token, List<SyncBean> syncBeanList)
			throws EcmException, AccessDeniedException, NoPermissionException, Exception {
		for (Iterator<SyncBean> syncBeanIt = syncBeanList.iterator(); syncBeanIt.hasNext();) {
			SyncBean en = syncBeanIt.next();
			String beanType = en.getBeanType();
			EcmUser ecmUserObj = en.getEcmUser();
			String userId = null;
			String groupId = null;
			switch (beanType) {
			case "新建用户":
				if (userService.getObjectById(token, ecmUserObj.getId()) == null) {
					userService.newObject(token, ecmUserObj);
				}
				break;
			case "修改用户":
				userService.updateObject(token, ecmUserObj);
				break;
			case "添加到角色":
				userId = userService.getObjectByLoginName(token, ecmUserObj.getName()).getId();
				groupId = groupService.getGroupByName(token, ecmUserObj.getGroupName()).getId();
				groupService.addUserToGroup(token, userId, groupId);
				break;
			case "移除用户":
				userId = userService.getObjectByLoginName(token, ecmUserObj.getName()).getId();
				groupId = groupService.getGroupByName(token, ecmUserObj.getGroupName()).getId();
				groupService.removeUserFromRole(token, userId, groupId);
				break;
			default:
			}
		}
	}

	/**
	 * @param token
	 * @param syncBeanList
	 * @throws EcmException
	 * @throws AccessDeniedException
	 * @throws NoPermissionException
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	private void importDataInner(String token, List<SyncBean> syncBeanList, String zipFolder)
			throws EcmException, AccessDeniedException, NoPermissionException, Exception, FileNotFoundException {
		for (Iterator<SyncBean> iterator = syncBeanList.iterator(); iterator.hasNext();) {
			SyncBean en = iterator.next();
			List<Map<String, Object>> documents = en.getDocuments();
			List<Map<String, Object>> transfers = en.getTransfers();
			List<EcmRelation> relations = en.getRelations();
			List<EcmContent> contents = en.getContents();
			String beanType = en.getBeanType();
			for (int i = 0; documents != null && i < documents.size(); i++) {
				if (beanType.startsWith("create")) {
					if (documentService.getObjectById(token, documents.get(i).get("ID").toString()) == null) {
						documentService.newObject(token, documents.get(i));
					} else {
						documentService.updateObject(token, documents.get(i));
					}
					if (beanType.equals("create_回复问题")) {
						EcmDocument edObj = documentService.getObjectById(token,
								documents.get(i).get("C_FROM_CODING").toString());
						edObj.addAttribute("C_ITEM_STATUS", "已回复");
						documentService.updateObject(token, edObj);
					} else if (beanType.equals("create_变更")) {
						// 更新上一版IED 状态为“变更中”
						EcmDocument doc = documentService.getObjectById(token, documents.get(i).get("ID").toString());
						if (doc != null) {
							if (doc.getTypeName().equalsIgnoreCase("IED")) {
								String condition = " IS_CURRENT = 1 AND VERSION_ID='" + doc.getVersionId() + "'";
								List<EcmDocument> docList = documentService.getObjects(token, condition);
								if (docList.size() > 0) {
									documentService.updateStatus(token, docList.get(0).getId(), "变更中");
								}
							}
						}

					}
					if (beanType.equals("create_驳回提交")) {
						relationService.deleteAllRelationByParentId(token, documents.get(i).get("ID").toString());
					}
				} else if (beanType.startsWith("update")) {
					// IED 设计文件升版，需要修改上一版本的属性
					if (beanType.equals("update_升版") || beanType.equals("update_接收")) {
						
						EcmDocument doc = documentService.getObjectById(token, documents.get(i).get("ID").toString());
						if (doc != null) {
							String typeName = doc.getTypeName();
							String condition = " IS_CURRENT = 1 AND VERSION_ID='"
									+ doc.getVersionId() + "'";
							if (typeName.equalsIgnoreCase("IED")) {
								List<EcmDocument> docList = documentService.getObjects(token, condition);
								if (docList.size() > 0) {
									Map<String, Object> attrs = new HashMap<String, Object>();
									attrs.put("ID", docList.get(0).getId());
									attrs.put("STATUS", "已升版");
									attrs.put("IS_CURRENT", 0);
									documentService.updateObject(token, attrs);
								}
								documents.get(i).put("IS_CURRENT", 1);
								documents.get(i).put("C_IS_RELEASED", 1);
								
							} else if (typeName.equalsIgnoreCase("设计文件")) {
								
								List<EcmDocument> docList = documentService.getObjects(token, condition);
								if (docList.size() > 0) {
									Map<String, Object> attrs = new HashMap<String, Object>();
									attrs.put("ID", docList.get(0).getId());
									attrs.put("IS_CURRENT", 0);
									documentService.updateObject(token, attrs);
								}
								documents.get(i).put("IS_CURRENT", 1);
								documents.get(i).put("C_IS_RELEASED", 1);
							}
						}

					} 
					documentService.updateObject(token, documents.get(i));	

				} else if (beanType.startsWith("delete")) {
					EcmDocument doc = documentService.getObjectById(token, documents.get(i).get("ID").toString());
					if (doc != null) {
						if (doc.getTypeName().equalsIgnoreCase("IED")) {
							String condition = " IS_CURRENT = 1 AND VERSION_ID='" + doc.getVersionId() + "'";
							List<EcmDocument> docList = documentService.getObjects(token, condition);
							if (docList.size() > 0) {
								documentService.updateStatus(token, docList.get(0).getId(), "已生效");
							}
						}
						documentService.deleteObjectById(token, doc.getId());
					}
				}
			}

			for (int i = 0; transfers != null && i < transfers.size(); i++) {
				if (beanType.startsWith("create")) {
					try {
						transferService.newObject(transfers.get(i));
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				} else if (beanType.startsWith("update")) {
					transferService.updateObject(transfers.get(i));
				}

			}

			for (int i = 0; relations != null && i < relations.size(); i++) {
				if (beanType.startsWith("create")) {
					try {
						relationService.newObject(token, relations.get(i));
					}catch(Exception ex) {
						ex.printStackTrace();
					}	
				}
			}
			for (int i = 0; contents != null && i < contents.size(); i++) {
				// TODOApplication.getNeedTOChange("正式环境需取消注释");
				BufferedInputStream fis = new BufferedInputStream(
						new FileInputStream(zipFolder + "/" + contents.get(i).getFilePath()));
				try {
					
					contents.get(i).setInputStream(fis);
					if(beanType.equals("create_驳回提交")) {
						EcmContent cont = contentService.getObjectById(token, contents.get(i).getId());
						if(cont != null) {
							contentService.updateObject(token, contents.get(i));
						}else {
							contentService.newObject(token, contents.get(i));
						}
					}else {
						contentService.newObject(token, contents.get(i));
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}	
				fis.close();
			}

			// TODOApplication.getNeedTOChange("如果是升版，需要更新历史版本的字段");
		}
	}

	/**
	 * 2.2从指定目录导入数据到当前系统后，将结果信息文件到指定目录 完成DONE_ 错误ERR_
	 */
	private boolean writeJsonResult(File file, String result) {
		String fileNewName = file.getParent() + "/" + result + file.getName();
		String fileOrgName = file.getAbsolutePath();
		file.renameTo(new File(fileNewName));
		new File(fileOrgName + ".MD5.txt").renameTo(new File(fileNewName + ".MD5.txt"));
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
		String jsonString = "";
		try (InputStream in = FileUtils.openInputStream(ConfPath.toFile())) {
			jsonString = IOUtils.toString(in, Charsets.toCharset(DEFAULT_CHARSET));
		} catch (Exception e) {
			logger.error("读取文件失败{}", ConfPath.toAbsolutePath(), e);
		}
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

	private InputStream getContentStream(EcmContent en) throws Exception {
		// TODO Auto-generated method stub
		InputStream fis = null;
		String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		File f = new File(fullPath + en.getFilePath());
		en.setContentSize(f.length());
		fis = new BufferedInputStream(new FileInputStream(fullPath + en.getFilePath()));
		return fis;
	}

	private String getPrimaryFilePath(String objId) {
		// TODO Auto-generated method stub
		List<EcmContent> list = ecmContentMapper.getContents(objId, 1);
		if (list.size() > 0) {
			EcmContent en = list.get(0);
			String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			return storePath + en.getFilePath();
		}
		return null;
	}

	private String getFolderName() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return CacheManagerOper.getEcmParameters().get("NetWorkEnv").getValue() + "_" + sdf.format(dt);
	}

	private String generateZipFileMD5(String filePath) throws IOException {
		String md5 = "";
		try (FileInputStream fis = new FileInputStream(filePath);) {
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
			fis.close();
		}
		return md5;

	}

	private String readFirstLine(String path) {// 路径
		File file = new File(path);
		String result = "";
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, DEFAULT_CHARSET);
				BufferedReader br = new BufferedReader(isr)) {// 构造一个BufferedReader类来读取文件
			result = br.readLine();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Description: 解压缩
	 * @param src 需要解压的压缩文件
	 * @param out 解压到的目录
	 */
	private static void unZip(String zipFilePath, String outFolder) throws IOException {
		File src = new File(zipFilePath);
		File out = new File(outFolder);
		// 先创建要解压的文件
		ZipFile zipFile = new ZipFile(src, "GB18030");
		// 通过entries()循环读取来得到文件。 hasMoreElemerts() 用来判断是否有元素
		for (Enumeration<ZipEntry> entries = zipFile.getEntries(); entries.hasMoreElements();) {
			// 可以连续地调用nextElement()方法来得到 Enumeration枚举对象中的元素
			ZipEntry entry = entries.nextElement();
			File file = new File(out, entry.getName());
			if (entry.isDirectory()) {
				file.mkdirs();
			} else {
				File parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				try (FileOutputStream fos = new FileOutputStream(file);
						InputStream is = zipFile.getInputStream(entry);) {
					IOUtils.copy(is, fos);
				}
			}
		}
		zipFile.close();
	}
}
