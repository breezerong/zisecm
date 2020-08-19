package com.ecm.core.sync;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.entity.SyncBean;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
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
		List<ExcSynDetail> excSynDetailObjList = iExcSynDetailService.selectByCondition(
				" APP_NAME='DOCEX' and ACTION_NAME in('" + actionName + "')  and (stauts is null  or stauts not in('已同步','已导出'))  order by CREATION_DATE asc ");
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
		String workflowSpecialUserName = env.getProperty("ecm.username");
		String token = null;
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
			token = ecmSession.getToken();
			folderName = getFolderName();
			List<SyncBean> resultObjList = new ArrayList<SyncBean>();

			List<ExcSynDetail> excSynDetailObjList = null;
			if (actionName.equals("导出用户")) {
				excSynDetailObjList = exportUserInner(token, resultObjList);
			} else {
				excSynDetailObjList = exportDataInner(actionName, token, resultObjList);
			}
			TODOApplication.getNeedTOChange("需要根据不同类型，生成不同的json文件");
			writeJsonFile(resultObjList, folderName + ".json");
			updateExcSynDetailStatus(excSynDetailObjList, "已导出", folderName, new Date());
			String abusoluteFolderPath = getSyncPathPublic() + "/";
			String zipFilePath = abusoluteFolderPath + folderName + ".zip";
			ZipUtil.zip(abusoluteFolderPath + "/" + folderName + "/", zipFilePath);
			writeMD5Info(zipFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		for (Iterator iterator = excSynDetailObjList.iterator(); iterator.hasNext();) {
			ExcSynDetail excSynDetail = (ExcSynDetail) iterator.next();
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

		for (int i = 0; i < docIds.size(); i++) {
			SyncBean sb = generateSyncBean(token, actionName, docIds.get(i));
			TODOApplication.getNeedTOChange("正式环境需要导出文件，取消如下注释");
			// exportFile(sb, getSyncPathPublic() + "/" + folderName + "/");
			resultObjList.add(sb);
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
		for (Iterator iterator = excSynDetailObjList.iterator(); iterator.hasNext();) {
			ExcSynDetail excSynDetail = (ExcSynDetail) iterator.next();
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
				ecmUser=new EcmUser();
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
		} else if ("驳回提交".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "update_驳回提交";
		} else if ("新建".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_新建";
		} else if ("修改".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "update_修改";
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
		} else if ("驳回".equals(type)) {
			String col = "ID,STATUS,C_REJECTER,C_REJECT_DATE,C_REJECT_COMMENT";
			transfers = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(transfers, col);
			beanType = "update_驳回";
		} else if ("接收".equals(type)) {
			String col = "ID,STATUS,C_RECEIVER,C_RECEIVE_DATE";
			transfers = ecmDocumentMapper.executeSQL("select " + col + " from ecm_document where ID='" + docId + "'");
//			initResultList(transfers, col);
			beanType = "update_接收";
		} else if ("新建问题".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_新建问题";
		} else if ("回复问题".equals(type)) {
			documents = documentService.getObjectMap(token, " ID in(" + sb.toString() + ") ");
			beanType = "create_回复问题";
		}

		TODOApplication.getNeedTOChange("问题反馈类型需要增加...");
		TODOApplication.getNeedTOChange("用户同步问题...");

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
	private boolean updateExcSynDetailStatus(List<ExcSynDetail> objList, String status, String batchNum,
			Date updateDate) {
		for (Iterator iterator = objList.iterator(); iterator.hasNext();) {
			ExcSynDetail excSynDetail = (ExcSynDetail) iterator.next();
			excSynDetail.setId(excSynDetail.getId());
			excSynDetail.setStauts(status);
			excSynDetail.setBatchNum(batchNum);
			excSynDetail.setExportDate(updateDate);
			excSynDetailMapper.updateByPrimaryKey(excSynDetail);

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
		List<File> zipFileList = new ArrayList<File>();
		for (File temp : fileDirectory.listFiles()) {
			if (!temp.isDirectory() && temp.getName().endsWith("zip") && temp.getName().startsWith("DONE")) {
				zipFileList.add(temp);
			}
		}
		for (int i = 0; i < zipFileList.size(); i++) {

		}
		updateExcSynDetailStatus(null, "已同步", "", new Date());
		return false;
	}

	/**
	 * 2.1从指定目录导入数据到当前系统
	 */
	@Override
	public boolean importData(String actionName) {
		File fileDirectory = new File(getSyncPathPrivate());
		List<File> zipFileList = new ArrayList<File>();
		for (File temp : fileDirectory.listFiles()) {
			if (!temp.isDirectory() && temp.getName().endsWith("zip")
					&& (!temp.getName().startsWith("DONE") && !temp.getName().startsWith("ERROR"))) {
				zipFileList.add(temp);
			}
		}
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		String token = null;
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
			token = ecmSession.getToken();
			String zipFolderPath = fileDirectory.getAbsolutePath() + "/";
			String zipFileFullPath = zipFolderPath + zipFileList.get(0).getName();
			String zipMD5FileFullPath = zipFolderPath + zipFileList.get(0).getName() + ".MD5.txt";
			String MD5_org = readFirstLine(zipMD5FileFullPath).replaceAll("[\\s\\t\\n\\r]", "");
			if (generateZipFileMD5(zipFileFullPath).equals(MD5_org)) {
				ZipUtil.unZip(zipFileFullPath, zipFolderPath);
				List<SyncBean> syncBeanList = readJsonResult(zipFileList.get(0).toString().replace(".zip", "") + "/"
						+ zipFileList.get(0).getName().replace(".zip", "") + ".json");
				if (actionName.equals("导入用户")) {
					for (Iterator iterator = syncBeanList.iterator(); iterator.hasNext();) {
						SyncBean en = (SyncBean) iterator.next();
						String beanType = en.getBeanType();
						EcmUser ecmUserObj=en.getEcmUser();
						switch (beanType) {
						case "新建用户":
							userService.newObject(token, en);
 							break;
						case "修改用户":
							userService.updateObject(token, en);
							break;
						case "添加到角色":
							groupService.addUserToGroup(token, ecmUserObj.getName(), ecmUserObj.getGroupName());
							break;
						case "移除用户":
							groupService.removeUserFromRole(token, ecmUserObj.getName(), ecmUserObj.getGroupName());
							break;
						default:
						}
					}
				} else {
					importDataInner(token, syncBeanList);
				}
				writeJsonResult(zipFileList.get(0), "DONE_");

			} else {
				writeJsonResult(zipFileList.get(0), "ERROR_MD5_");
			}
			FileUtils.deleteDirectory(new File(zipFileList.get(0).toString().replace(".zip", "")));
		} catch (Exception e) {
			TODOApplication.getNeedTOChange("错误输出到文件好定位");
			writeJsonResult(zipFileList.get(0), "ERROR_");
			e.printStackTrace();
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
	 * @throws FileNotFoundException
	 */
	private void importDataInner(String token, List<SyncBean> syncBeanList)
			throws EcmException, AccessDeniedException, NoPermissionException, Exception, FileNotFoundException {
		for (Iterator iterator = syncBeanList.iterator(); iterator.hasNext();) {
			SyncBean en = (SyncBean) iterator.next();
			List<Map<String, Object>> documents = en.getDocuments();
			List<Map<String, Object>> transfers = en.getTransfers();
			List<EcmRelation> relations = en.getRelations();
			List<EcmContent> contents = en.getContents();
			String beanType = en.getBeanType();
			TODOApplication.getNeedTOChange("驳回提交，需要删除目标系统的...");
			for (int i = 0; documents != null && i < documents.size(); i++) {
				if (beanType.startsWith("create")) {
					documentService.newObject(token, documents.get(i));
					if (beanType.equals("create_回复问题")) {
						EcmDocument edObj = documentService.getObjectById(token,
								documents.get(i).get("C_FROM_CODING").toString());
						edObj.addAttribute("C_ITEM_STATUS", "已回复");
						documentService.updateObject(token, edObj);
					}
				} else if (beanType.startsWith("update")) {
					documentService.updateObject(token, documents.get(i));
					if (beanType.equals("create_驳回提交")) {
						relationService.deleteAllRelationByParentId(token,
								documents.get(i).get("ID").toString());
					}
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
				BufferedInputStream fis = new BufferedInputStream(
						new FileInputStream(contents.get(i).getFilePath()));
				contents.get(i).setInputStream(fis);
				contentService.newObject(token, contents.get(i));
			}

			TODOApplication.getNeedTOChange("如果是升版，需要更新历史版本的字段");
		}
	}

	/**
	 * 2.2从指定目录导入数据到当前系统后，将结果信息文件到指定目录 完成DONE_ 错误ERR_
	 */
	private boolean writeJsonResult(File file, String result) {
		file.renameTo(new File(getSyncPathPrivate() + "/" + result + file.getName()));
		file.renameTo(new File(getSyncPathPrivate() + "/" + result + file.getName() + ".MD5.txt"));
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
		try {
			jsonString = FileUtils.readFileToString(ConfPath.toFile(), DEFAULT_CHARSET);
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

	private String readFirstLine(String path) {// 路径
		File file = new File(path);
		String result = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET));// 构造一个BufferedReader类来读取文件
			String s = null;
			result = br.readLine();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
