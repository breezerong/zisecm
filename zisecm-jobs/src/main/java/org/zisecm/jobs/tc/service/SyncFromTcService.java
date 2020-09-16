package org.zisecm.jobs.tc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.bean.conf.AttrBean;
import org.zisecm.jobs.bean.conf.ConfBean;
import org.zisecm.jobs.bean.conf.ConfigBean;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.entity.FileInfo;
import org.zisecm.jobs.entity.ResultData;
import org.zisecm.jobs.entity.StatusEntity;
import org.zisecm.jobs.service.LogicOption4CnpeInterface;
import org.zisecm.jobs.service.LogicOption4CnpeRelevantDoc;
import org.zisecm.jobs.service.LogicOption4CnpeTransfer;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.tools.SyncTcTools;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;

import com.ecm.common.util.FileUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.EcmContext;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.services.strong.core._2007_01.DataManagement.WhereReferencedResponse;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsData;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsOutput;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsPref;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsResponse;
import com.teamcenter.services.strong.core._2007_06.DataManagement.RelationAndTypesFilter2;
import com.teamcenter.services.strong.query.SavedQueryService;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.GetSavedQueriesResponse;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.QueryResults;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.SavedQueriesResponse;
import com.teamcenter.services.strong.query._2008_06.SavedQuery;
import com.teamcenter.services.strong.query._2008_06.SavedQuery.QueryInput;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.GetFileResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.Type;
import com.teamcenter.soa.client.model.strong.ImanFile;
import com.teamcenter.soa.client.model.strong.ImanQuery;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.WorkspaceObject;

@Service
public class SyncFromTcService {
	private static Logger logger = Logger.getLogger(SyncFromTcService.class);
	@Autowired
	private DocumentService documentService;
	@Autowired
	private AuthService authService;
	@Autowired
	private RelationService relationService;
	@Autowired
	private ExcTransferServiceImpl excTransferService;
	@Autowired
	private LogicOption4CnpeInterface logicOptionInterfaceService;
	@Autowired
	private LogicOption4CnpeRelevantDoc logicOptionRelevantService;
	@Autowired
	private LogicOption4CnpeTransfer logicOptionTransferService;
	
	@Autowired
	private Environment env;
	
	private static IEcmSession ecmSession=null;
	
	private IEcmSession getEcmSession() throws Exception{
		if(this.ecmSession==null) {
			String userName = env.getProperty("ecm.username");
			this.ecmSession = authService.login("jobs", userName, env.getProperty("ecm.password"));
			
		}
		
		return ecmSession;
	}
	
	public String getTcData() throws Exception {
		
		Session session = SyncTcTools.getSession(env);
		logger.info("-----------begin setCRData------------");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = df.format(new Date());
		logger.info("-----------开始时间：" + start + "------------");
		System.out.println("CR开始时间：" + start);

		CreateItemsOutput createItemOutput = null;
		DataManagementService dmService = DataManagementService.getService(session.getConnection());
		try {
			DataManagement dataManagement = new DataManagement(session);
			Query query = new Query(session);
			ConfigBean cf = Operator.getConfig();
			for (ConfBean conf : cf.getConfs()) {
				if (conf.isIschildType()) {
					continue;
				}
				ModelObject[] findChangeRqRevision = query.queryFilesRevision(conf.getTcTableName(), "1");
				if (findChangeRqRevision == null) {
					throw new Exception("数据在TC中已经不存在符合条件的数据：");
				}
				List<ConfBean> tcConfBeans = Operator.getConfBeanByTcTableName(conf.getTcTableName(), "tc");
				List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

				for (ModelObject obj : findChangeRqRevision) {
					for (ConfBean tcConfBean : tcConfBeans) {
						Map<String, Object> row = new HashMap<String, Object>();
						List<AttrBean> attrBeans = tcConfBean.getAttributes();
						row.put("TYPE_NAME", tcConfBean.getSourceTypeName());
						for (AttrBean attrBean : attrBeans) {
							String value = obj.getPropertyObject(attrBean.getTargetName()).getStringValue();
							row.put(attrBean.getSourceName(), value);
						}
						dataList.add(row);
						// 保存数据至设计分包系统
						EcmDocument doc = new EcmDocument();
						doc.setAttributes(row);
						String docId = documentService.newObject(ecmSession.getToken(), doc, null);
						// 检查数据是否有字表
						ModelObject[] modelObjects = obj.getPropertyObject("cn9FileList").getModelObjectArrayValue();
						if (modelObjects != null&&modelObjects.length>0) {
							List<ResultData> resultDatas= getDetail(session, (ItemRevision)obj, "", "cn9FileList");
							for(int x=0;resultDatas!=null&&x<resultDatas.size();x++) {
								ResultData resultData=resultDatas.get(x);
								EcmDocument document= resultData.getDocument();
								String childId= documentService.newObject(ecmSession.getToken(), document, null);
								List<FileInfo> files= resultData.getList();
								for(int n=0;files!=null&&n<files.size();n++) {
									FileInfo fileInfo=files.get(n);
									EcmDocument attachmentDoc=new EcmDocument();
									attachmentDoc.setTypeName("附件");
									attachmentDoc.setName(fileInfo.getName());
									EcmContent en=new EcmContent();
									en.setName(fileInfo.getName());
									en.setContentSize(fileInfo.getSize());
									en.setFormatName(FileUtils.getExtention(fileInfo.getName()));
									File file=new File(fileInfo.getUrl());
									InputStream input=new FileInputStream(file);
									en.setInputStream(input);
									String attachmentId= documentService.newObject(ecmSession.getToken(), attachmentDoc, en);
									
									EcmRelation relation=new EcmRelation();
									relation.setParentId(childId);
									
									relation.setChildId(attachmentId);
									relation.setName("附件");
									relationService.newObject(ecmSession.getToken(), relation);
								}
								
								EcmRelation relation=new EcmRelation();
								relation.setParentId(docId);
								
								relation.setChildId(childId);
//								relation.setName("设计文件");
								relationService.newObject(ecmSession.getToken(), relation);
								
							}
						}
						// 创建字表数据
						// 下载电子文件保存至子表
						
						dispense(ecmSession.getToken(),docId);
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	private void dispense(String token,String docId) throws Exception {

		EcmDocument doc= documentService.getObjectById(token, docId);
		String currentStatus= doc.getStatus();
		
		if(currentStatus==null||"".equals(currentStatus)) {
			currentStatus="新建";
		}
		String nextStatus= StatusEntity.getNextDcStatusValue("新建", doc.getTypeName(), true);
		
		
		if("图文传真,会议纪要".contains(doc.getTypeName())){
			documentService.updateObject(token, doc, null);
			doc.addAttribute("C_IS_RELEASED", 1);
		}else {
			
			if("文件传递单".equals(doc.getTypeName())) {
				logicOptionTransferService.transferOption(token, doc,true);
			}else if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
				logicOptionInterfaceService.interfaceOption(token, doc);
			}else {
				logicOptionRelevantService.relevantOption(token,doc,true);
			}
			doc.addAttribute("C_IS_RELEASED", 1);
			documentService.updateObject(token, doc, null);
			
		}
		
		
		
		String contractorStr= doc.getAttributeValue("C_TO")==null?"":doc.getAttributeValue("C_TO").toString();
		String copyToStr=doc.getAttributeValue("C_COPY_TO")==null?"":doc.getAttributeValue("C_COPY_TO").toString();
		
		String codingStr=doc.getCoding();
		String[] contractors=contractorStr.split(";");
		String[] copyTos=copyToStr.split(";");
		String[] codings=codingStr.split(";");
		for(int i=0;i<contractors.length;i++) {
			if(!StringUtils.isEmpty(contractors[i])) {
				ExcTransfer excTransfer=new ExcTransfer();
				excTransfer.setItemType(1);
				excTransfer.setDocId(docId);
				excTransfer.setFromName(doc.getAttributeValue("C_FROM")!=null?doc.getAttributeValue("C_FROM").toString():"");
				excTransfer.setToName(contractors[i]);
				excTransfer.setCreationDate(new Date());
				excTransfer.setCreator(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSender(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSendDate(new Date());
				
				excTransfer.setStatus(nextStatus);
				excTransferService.newObject(excTransfer);
			}
			
		}
		
		for(int i=0;i<copyTos.length;i++) {
			if(!StringUtils.isEmpty(copyTos[i])) {
				ExcTransfer excTransfer=new ExcTransfer();
				excTransfer.setItemType(1);
				excTransfer.setDocId(docId);
				excTransfer.setFromName(doc.getAttributeValue("C_FROM")!=null?doc.getAttributeValue("C_FROM").toString():"");
				excTransfer.setToName(copyTos[i]);
				excTransfer.setCreationDate(new Date());
				excTransfer.setCreator(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSender(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSendDate(new Date());
				
				excTransfer.setStatus(nextStatus);
				excTransferService.newObject(excTransfer);
			}
			
		}
		
		if("新建".equals(currentStatus)) {
			doc.addAttribute("c_item_date", new Date());
		}
		doc.addAttribute("C_IS_RELEASED", 1);
		doc.setStatus(nextStatus);
		documentService.updateObject(token, doc, null);
	
	}
	
	/**
	 * 获取文件清单 LOT
	 * 
	 * @param itemRev
	 * @param listPropName 文件清单属性名称
	 * @return
	 * @throws Exception
	 */
	public List<ResultData> getDetail(Session session, ItemRevision itemRev, 
			String item_id, String listPropName) throws Exception {
		DataManagementService dmServ = DataManagementService.getService(session.getConnection());

		dmServ.getProperties(new ModelObject[] { itemRev }, new String[] { listPropName, "cn9ReceiveSendFlag" });
		String receiveSendFlag = itemRev.getPropertyObject("cn9ReceiveSendFlag").getStringValue();
		

		ModelObject[] modelObjects = itemRev.getPropertyObject(listPropName).getModelObjectArrayValue();
		
		if (modelObjects != null && modelObjects.length > 0) {
			dmServ.getProperties(modelObjects,
					new String[] { "cn9InternalCode", "cn9ExternalCode", "cn9Rev", "cn9FileName", "cn9States",
							"cn9Page", "cn9Paper", "cn9Digital", "cn9Blue", "cn9Language", "cn9DrawNo" });
			List<ResultData> datas=new ArrayList<ResultData>();
			for (ModelObject modelObject : modelObjects) {
				ResultData data=new ResultData();
				EcmDocument document = new EcmDocument();
				List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
				// arrayOfDetailInfo.a
				// add by xiaolei 20150527 begin
				String iedItemId = modelObject.getPropertyObject("cn9ExternalCode").getStringValue();
				String iedRevId = modelObject.getPropertyObject("cn9Rev").getStringValue();
				// add end

				document.setCoding(iedItemId);// 编码
				document.addAttribute("C_IN_CODING", modelObject.getPropertyObject("cn9InternalCode").getStringValue());// 内部编码
				document.setRevision(iedRevId);
				document.setTitle(modelObject.getPropertyObject("cn9FileName").getStringValue());// 标题
				document.setStatus(modelObject.getPropertyObject("cn9States").getStringValue());// 状态
				document.addAttribute("C_COUNT1",
						(modelObject.getPropertyObject("cn9Paper") == null
								|| "".equals(modelObject.getPropertyObject("cn9Paper").getStringValue())) ? "0"
										: modelObject.getPropertyObject("cn9Paper").getStringValue());// 纸
				document.addAttribute("C_COUNT2",
						(modelObject.getPropertyObject("cn9Digital") == null
								|| "".equals(modelObject.getPropertyObject("cn9Digital").getStringValue())) ? "0"
										: modelObject.getPropertyObject("cn9Digital").getStringValue());// 电

				document.addAttribute("C_COUNT3",
						(modelObject.getPropertyObject("cn9Blue") == null
								|| "".equals(modelObject.getPropertyObject("cn9Blue").getStringValue())) ? "0"
										: modelObject.getPropertyObject("cn9Blue").getStringValue());// 蓝
				document.addAttribute("C_LANGUAGE",
						(modelObject.getPropertyObject("cn9Language") == null
								|| "".equals(modelObject.getPropertyObject("cn9Language").getStringValue())) ? "0"
										: modelObject.getPropertyObject("cn9Language").getStringValue());// 语言

				// iedFile.set
				// iedFile.setz(modelObject.getPropertyObject("cn9Paper").getStringValue()); //
				// 纸
				// iedFile.setMedia(modelObject.getPropertyObject("cn9Digital").getStringValue());
				// // 电
				// iedFile.setBlueprint(modelObject.getPropertyObject("cn9Blue").getStringValue());
				// // 蓝
				// iedFile.setLanguage(modelObject.getPropertyObject("cn9Language").getStringValue());
				// // 语言
				ItemRevision itemRevision = queryItemRevision(session,
						modelObject.getPropertyObject("cn9ExternalCode").getStringValue(),
						modelObject.getPropertyObject("cn9Rev").getStringValue());

				if ("0".equals(receiveSendFlag)) {
					document.addAttribute("C_FROM_CODING",
							(modelObject.getPropertyObject("cn9DrawNo") == null
									|| "".equals(modelObject.getPropertyObject("cn9DrawNo").getStringValue())) ? ""
											: modelObject.getPropertyObject("cn9DrawNo").getStringValue());// 图册号
				} else {
					// TODO IED对应附件

					if (itemRevision != null) {
						dmServ.getProperties(new ModelObject[] { itemRevision },
								new String[] { "cn9DrawingBookNum", "cn9DrawingBookName" });
						document.addAttribute("C_FROM_CODING",
								itemRevision.getPropertyObject("cn9DrawingBookNum").getStringValue());
//					

						ModelObject[] models = whereReferenced(session, itemRevision, 1);
						Type typetuzhi = itemRevision.getTypeObject();
						String typeNametuzhi = typetuzhi.getName();

						System.out.println("typeNametuzhi:" + typeNametuzhi);
						if ("CN9TCIEDDeliverRevision".equals(typeNametuzhi)) {
							dmServ.getProperties(models, new String[] { "item_revision_id" });
							System.out.println("models:" + models.length);

						}

					}
				}

				List<FileInfo> fileList = getFileTfAttachment(session, itemRevision, "IMAN_specification", iedItemId,
						iedRevId);

				if (fileList.size() > 0) {
					fileInfoList.addAll(fileList);
				}

				data.setDocument(document);
				data.setList(fileInfoList);
				datas.add(data);
				// modify end

				// fileList.add(iedFile);
			}

			return datas;
		}

		return null;
	}

	public ModelObject[] whereReferenced(Session session, ModelObject object, int level) {

		/*
		 * if(session == null || object == null){ return null; }
		 */
		try {
			WorkspaceObject wso = new WorkspaceObject(object.getTypeObject(), object.getUid());
			DataManagementService dmServ = DataManagementService.getService(session.getConnection());

			WhereReferencedResponse re = dmServ.whereReferenced(new WorkspaceObject[] { wso }, level);

			if (re.serviceData.sizeOfPartialErrors() > 0) {
				System.out.println("error");
				return null;
			}
			int size = re.serviceData.sizeOfPlainObjects();
			List<String> uidList = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				String uid = re.serviceData.getPlainObject(i).getUid();

				uidList.add(uid);
			}
			String[] uids = new String[uidList.size()];
			uids = uidList.toArray(uids);

			return this.loadModelObjects(session, uids);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	public ModelObject[] loadModelObjects(Session session, String[] uids) {
		DataManagementService dmServ = DataManagementService.getService(session.getConnection());
		ServiceData response = dmServ.loadObjects(uids);
		if (response.sizeOfPartialErrors() > 0) {

			System.out.println("Error" + response.sizeOfPartialErrors());

			return null;
		}

		int size = response.sizeOfPlainObjects();

		ModelObject[] objs = new ModelObject[size];

		for (int i = 0; i < size; i++) {
			objs[i] = response.getPlainObject(i);
		}

		return objs;
	}

	public List<FileInfo> getFileTfAttachment(Session session, ItemRevision itemRev, String relationTypeName,
			String fileId, String revId) throws Exception {
		DataManagementService dmServ = DataManagementService.getService(session.getConnection());
		FileManagementUtility fMSFileManagement = new FileManagementUtility(session.getConnection());

		// ArrayOfFileInfo attachments = new ArrayOfFileInfo();
		List<FileInfo> attachments = new ArrayList<FileInfo>();
		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = relationTypeName;

		ExpandGRMRelationsResponse response = dmServ.expandGRMRelationsForPrimary(new ModelObject[] { itemRev }, pref);

		if (response.serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = response.serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}

		// 所有导出文件放在系统temp目录下的WSOutPutFile目录中
		File exportDir = new File(System.getProperty("java.io.tmpdir"), "WSOutPutFile");
		if (!exportDir.exists())
			exportDir.mkdir();
		if (!exportDir.isDirectory()) {
			exportDir.delete();
			exportDir.mkdir();
		}

		// 每个导出的对象有单独的目录
		String tmpDirName = fileId + "_" + revId + "_" + (new Date()).getTime();
		File tmpExportDir = new File(exportDir, tmpDirName);

		if (!tmpExportDir.exists())
			tmpExportDir.mkdir();
		if (!tmpExportDir.isDirectory()) {
			tmpExportDir.delete();
			tmpExportDir.mkdir();
		}

		// 1 从数据集中导出文件
		ExpandGRMRelationsOutput[] outputs = response.output;

		ModelObject[] relationObjects = null;

		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				relationObjects = data.otherSideObjects;
				dmServ.getProperties(relationObjects, new String[] { "object_name", "ref_list" });
				for (int i = 0; i < relationObjects.length; i++) {
					ModelObject dataset = relationObjects[i];

					// add by xiaolei 20151029 for bug 2369 begin
					// 如果数据集文件类型不是PDF则不传输
					String objectType = relationObjects[i].getTypeObject().getName();
					// modify by xiaolei 20150824 for bug 1965
					// if(objectType.equals("MSWord") || objectType.equals("MSWordX"))
					if (!objectType.equals("PDF") && !objectType.equals("TIF"))
						continue;
					// add end

					// String objectType =
					// relationObjects[i].getTypeObject().getName();
					String objectName = relationObjects[i].getPropertyObject("object_name").getStringValue();

					ModelObject[] imanfiles = dataset.getPropertyObject("ref_list").getModelObjectArrayValue();

					ImanFile zIFile = null;

					for (int j = 0; j < imanfiles.length; j++) {
						if (imanfiles[j] instanceof ImanFile) {
							zIFile = (ImanFile) imanfiles[j];
							break;
						}
					}

					if (zIFile == null) {
						System.err.println("No named reference found for " + objectName);
						continue;
					}

					GetFileResponse getFileResp = fMSFileManagement.getFiles(new ImanFile[] { zIFile });
					if (getFileResp.sizeOfPartialErrors() > 0) {
						System.err.println("get files error for " + objectName);
						continue;
					}

					dmServ.getProperties(new ModelObject[] { zIFile },
							new String[] { "file_name", "original_file_name" });

//					SoaHelper.getProperties(zIFile, new String[] { "file_name", "original_file_name" });
					String fileName = zIFile.get_file_name();
					String originalFileName = zIFile.get_original_file_name();
					String fullFileName = tmpExportDir.getAbsolutePath() + "\\" + fileName;
					File fileToCopy[] = getFileResp.getFiles();
					File fullFile = new File(fullFileName);
					if (fullFile.exists())
						fullFile.delete();
					fileToCopy[0].renameTo(fullFile);

					System.out.println("----------" + fullFileName + "-----------");

					long fileSize = getFileSizes(fullFile);

					FileInfo attachment = new FileInfo();
					// attachment.setType("Transmittal");
					// attachment.setFileID(ftpDir + fileName);
					attachment.setName(originalFileName);
					attachment.setUrl(fullFileName);
					attachment.setSize(0);
					attachment.setWjbm(fileId);
					// attachments.add(attachment);
					// attachment.addFileInfo(attachment);
					// attachments.addFileInfo(attachment);
					attachments.add(attachment);
				}
			}
		}
		fMSFileManagement.term();
		return attachments;
	}

	
	/**
	 * 
	 * @param itemRev
	 * @param relationTypeName
	 *            关系名称
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getAttachment(Session session,ItemRevision itemRev, String relationTypeName, String fileId, String revId)
			throws Exception {
		DataManagementService dmServ = DataManagementService.getService(session.getConnection());
		FileManagementUtility fMSFileManagement = new FileManagementUtility(session.getConnection());

		ArrayList<FileInfo> attachments = new ArrayList<FileInfo>();

		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = relationTypeName;

		ExpandGRMRelationsResponse response = dmServ.expandGRMRelationsForPrimary(new ModelObject[] { itemRev }, pref);

		if (response.serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = response.serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}

		// 所有导出文件放在系统temp目录下的WSOutPutFile目录中
		File exportDir = new File(System.getProperty("java.io.tmpdir"), "WSOutPutFile");
		if (!exportDir.exists())
			exportDir.mkdir();
		if (!exportDir.isDirectory()) {
			exportDir.delete();
			exportDir.mkdir();
		}

		// 每个导出的对象有单独的目录
		String tmpDirName = fileId + "_" + revId + "_" + (new Date()).getTime();
		File tmpExportDir = new File(exportDir, tmpDirName);

		if (!tmpExportDir.exists())
			tmpExportDir.mkdir();
		if (!tmpExportDir.isDirectory()) {
			tmpExportDir.delete();
			tmpExportDir.mkdir();
		}

		// 1 从数据集中导出文件
		ExpandGRMRelationsOutput[] outputs = response.output;

		ModelObject[] relationObjects = null;

		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				relationObjects = data.otherSideObjects;
				dmServ.getProperties(relationObjects, new String[] { "object_name", "ref_list" });
				for (int i = 0; i < relationObjects.length; i++) {
					ModelObject dataset = relationObjects[i];

					//add by xiaolei 20151029 for bug 2369 begin
					//如果数据集文件类型不是PDF则不传输
					 String objectType =
					 relationObjects[i].getTypeObject().getName();
					 //modify by xiaolei 20150824 for bug 1965
					if(!objectType.equals("PDF"))
					//if(!objectType.equals("PDF") && !objectType.equals("TIF"))
						continue;
					//add end
					 
					// String objectType =
					// relationObjects[i].getTypeObject().getName();
					String objectName = relationObjects[i].getPropertyObject("object_name").getStringValue();

					ModelObject[] imanfiles = dataset.getPropertyObject("ref_list").getModelObjectArrayValue();

					ImanFile zIFile = null;

					for (int j = 0; j < imanfiles.length; j++) {
						if (imanfiles[j] instanceof ImanFile) {
							zIFile = (ImanFile) imanfiles[j];
							break;
						}
					}

					if (zIFile == null) {
						System.err.println("No named reference found for " + objectName);
						continue;
					}

					GetFileResponse getFileResp = fMSFileManagement.getFiles(new ImanFile[] { zIFile });
					if (getFileResp.sizeOfPartialErrors() > 0) {
						System.err.println("get files error for " + objectName);
						continue;
					}
					
					dmServ.getProperties(new ModelObject[] {zIFile}, new String[] { "file_name", "original_file_name" });
					
//					SoaHelper.getProperties(zIFile, new String[] { "file_name", "original_file_name" });
					String fileName = zIFile.get_file_name();
					String originalFileName = zIFile.get_original_file_name();
					String fullFileName = tmpExportDir.getAbsolutePath() + "\\" + fileName;
					File fileToCopy[] = getFileResp.getFiles();
					File fullFile = new File(fullFileName);
					if (fullFile.exists())
						fullFile.delete();
					fileToCopy[0].renameTo(fullFile);

					System.out.println("----------" + fullFileName + "-----------");

					long fileSize = getFileSizes(fullFile);

//					// 2 将文件上传到ftp
//					String ftpDir = cm_ftp_dir + tmpDirName + "/";
//					ftp.upload(ftpDir, fullFileName);
//
//					// 3 组合包
//					AttachmentVo attachmentVo = new AttachmentVo();
//
//					attachmentVo.setFileID(ftpDir + fileName);
//					attachmentVo.setFileName(originalFileName);
//					attachmentVo.setFilePath(ftpDir + fileName);
//					attachmentVo.setFileSize((int) fileSize);
//
//					attachments.add(attachmentVo);
					FileInfo fileInfo=new FileInfo();
					fileInfo.setName(originalFileName);
					fileInfo.setUrl(fullFileName);
					fileInfo.setSize(fileSize);
					fileInfo.setWjbm(fileId);
					fileInfo.setRevision(revId);
					attachments.add(fileInfo);
				}
			}
		}
		fMSFileManagement.term();
		return attachments;
	}
	
	public long getFileSizes(File f) {// 取得文件大小
		long s = 0;
		try {
			if (f.exists()) {
				FileInputStream fis = null;
				fis = new FileInputStream(f);
				s = fis.available();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取文件大小出错:" + f.getAbsolutePath());
		}
		return s;
	}

	public ItemRevision queryItemRevision(Session session, String itemId, String revId) {
		ImanQuery query = null;
		SavedQuery queryService = SavedQueryService.getService(session.getConnection());

		try {
			GetSavedQueriesResponse savedQueries = ((com.teamcenter.services.strong.query._2006_03.SavedQuery) queryService)
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				return null;
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
				if (savedQueries.queries[i].name.equals("Item Revision...")) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			return null;
		}

		if (query == null) {
			System.out.println("There is not an 'Item Revision...' query.");
			return null;
		}

		try {
			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
			savedQueryInput[0].entries = new String[] { "零组件 ID", "版本" };
			savedQueryInput[0].values = new String[] { itemId, revId };

			SavedQueriesResponse response = queryService.executeSavedQueries(savedQueryInput);

			QueryResults found = response.arrayOfResults[0];

			/*
			 * if (found.objectUIDS.length == 0) return null; else { DataManagementService
			 * dmService = DataManagementService.getService(SoaHelper.getSoaConnection());
			 * ServiceData serviceData = dmService .loadObjects(found.objectUIDS); if
			 * (serviceData.sizeOfPartialErrors() == 0) { return
			 * (ItemRevision)serviceData.getPlainObject(0); } }
			 */
			// add by Sean 20151012
			if (found.objectUIDS.length == 0) {
				// add by Sean 20151012
				savedQueryInput[0].entries = new String[] { "Item ID", "Revision" };
				response = queryService.executeSavedQueries(savedQueryInput);
				found = response.arrayOfResults[0];
				if (found.objectUIDS.length == 0)
					return null;
				else {
					DataManagementService dmService = DataManagementService.getService(session.getConnection());
					ServiceData serviceData = dmService.loadObjects(found.objectUIDS);
					if (serviceData.sizeOfPartialErrors() == 0) {

						return (ItemRevision) serviceData.getPlainObject(0);
					}
				}
				// add end
			} else {
				DataManagementService dmService = DataManagementService.getService(session.getConnection());
				ServiceData serviceData = dmService.loadObjects(found.objectUIDS);
				if (serviceData.sizeOfPartialErrors() == 0) {
					return (ItemRevision) serviceData.getPlainObject(0);
				}
			}
			// add end

		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
		}
		return null;
	}
}
