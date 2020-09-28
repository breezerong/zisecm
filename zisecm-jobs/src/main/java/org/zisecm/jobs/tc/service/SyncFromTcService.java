package org.zisecm.jobs.tc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.bean.conf.AttrBean;
import org.zisecm.jobs.bean.conf.Condition;
import org.zisecm.jobs.bean.conf.ConfBean;
import org.zisecm.jobs.bean.conf.ConfigBean;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.bean.conf.QueryConstructor;
import org.zisecm.jobs.bean.conf.RelationShip;
import org.zisecm.jobs.bean.conf.SearchConf;
import org.zisecm.jobs.bean.conf.SubTable;
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
import com.teamcenter.services.strong.core._2008_06.DataManagement.GetItemAndRelatedObjectsInfo;
import com.teamcenter.services.strong.query.SavedQueryService;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.ExecuteSavedQueryResponse;
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
import com.teamcenter.soa.exceptions.NotLoadedException;

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

	private static IEcmSession ecmSession = null;

	private IEcmSession getEcmSession() throws Exception {
		if (this.ecmSession == null) {
			String userName = env.getProperty("ecm.username");
			this.ecmSession = authService.login("jobs", userName, env.getProperty("ecm.password"));

		}

		return ecmSession;
	}
	
	/**
	 * 根据配置的查询构建器查询数据
	 * @param session
	 * @param ship
	 * @return
	 * @throws Exception 
	 */
	public ModelObject[] queryFilesRevision(Session session, QueryConstructor constructor,
			String startDate,String endDate) throws Exception {

		SavedQueryService queryService = SavedQueryService.getService(session.getConnection());
		ImanQuery query = null;
		
		try {
			GetSavedQueriesResponse savedQueries = queryService
					.getSavedQueries();

			if (savedQueries.queries.length == 0) {
				System.out.println("There are no saved queries in the system.");
				// return false;
				throw new Exception("There are no saved queries in the system.");
			}

			for (int i = 0; i < savedQueries.queries.length; i++) {
//				if (savedQueries.queries[i].name.equals("查询需要导出的传递单")) {
				if (savedQueries.queries[i].name.equals(constructor.getName())) {
					query = savedQueries.queries[i].query;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("GetSavedQueries service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("GetSavedQueries service request failed:"
					+ e.getMessage());
		}

		if (query == null) {
			System.out.println("There is not an '查询需要同步至设计分包的Item");
			// return false;
			throw new Exception("There is not an '查询需要同步至设计分包的Item");
		}

		try {
			
			List<String> entries=new ArrayList<String>();
			List<String> values=new ArrayList<String>();
			List<Condition> conditions= constructor.getConditions();
			
			for(int i=0;i<conditions.size();i++) {
				Condition c= conditions.get(i);
				entries.add(c.getName());
				String val="";

				if("startDate".equals(c.getValue())) {
					val=startDate;
				}else if("endDate".equals(c.getValue())) {
					val=endDate;
				}else {
					val=c.getValue();
				}
				values.add(val);
			}

			int limit = 0;
			ExecuteSavedQueryResponse found = queryService.executeSavedQuery(query, entries.toArray(new String[0]),
					values.toArray(new String[0]), limit);

			return found.objects;
		} catch (Exception e) {
			System.out.println("ExecuteSavedQuery service request failed.");
			System.out.println(e.getMessage());
			// return false;
			throw new Exception("ExecuteSavedQuery service request failed."
					+ e.getMessage());
		}
	
	}
	
	public String getTcData(ConfBean cfb) throws Exception {
		List<SubTable> downloadRelationShips= cfb.getDownrelationShips();
		
		if(downloadRelationShips==null||downloadRelationShips.size()==0) {
			return null;
		}
		/*********************** 查询要同步的数据 ********************/
		Session session = SyncTcTools.getSession(env);
		IEcmSession ecmSession= getEcmSession();
		String token=ecmSession.getToken();
		
		logger.info("-----------begin setCRData------------");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
		String start = df.format(new Date());
		logger.info("-----------开始时间：" + start + "------------");
		System.out.println("CR开始时间：" + start);

//		CreateItemsOutput createItemOutput = null;
		DataManagementService dmService = DataManagementService.getService(session.getConnection());
		try {
//			DataManagement dataManagement = new DataManagement(session);
//			Query query = new Query(session);
//			ConfigBean cf = Operator.getConfig();

			SimpleDateFormat dtFormat=new SimpleDateFormat("yyyy-M-dd");
			String endDate= dtFormat.format(new Date());
			
			String dateDifferenceStr= env.getProperty("ecm.dateDifference");
			int dateDifference=1;
			if(dateDifferenceStr!=null) {
				dateDifference=Integer.parseInt(dateDifferenceStr);
			}
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-M-dd");
			calendar.add(Calendar.DAY_OF_MONTH,dateDifference);
			String date = fmt.format(calendar.getTime());
			
			ModelObject[] findChangeRqRevision = queryFilesRevision(session,
					cfb.getQueryConstructor(),date+" 00:00", endDate+" 23:59");
			if (findChangeRqRevision == null||findChangeRqRevision.length==0) {
				throw new Exception("数据在TC中已经不存在符合条件的数据：");
			}
			
			
			
			/*********************同步Form数据******************************************/
			
			/*********************同步子表数据 **********************************/
			/*********************为主表添加主电子件*************************/
			/*********************为主表添加附件*************************************/
			
			/*********************为子表添加附件*************************************/
			
//			List<ConfBean> tcConfBeans = Operator.getConfBeanByTcTableName(conf.getTcTableName(), "tc");
//			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

			for (ModelObject obj : findChangeRqRevision) {
				String ccUnits= SyncTcTools.getProperty(dmService, obj, "cn9CCUnit","String");
				String recivUnit= SyncTcTools.getProperty(dmService, obj, "cn9RecivUnit","String");
//						obj.getPropertyObject("cn9RecivUnit").getStringValue();
				if("".equals(ccUnits)&&"".equals(recivUnit)) {
					continue;
				}
				if(!StringUtils.isEmpty(ccUnits)) {
					recivUnit+=","+ccUnits;
				}
				recivUnit=recivUnit.replaceAll(",", "','");
				
				List<EcmDocument> companys= documentService.getObjects(getEcmSession().getToken(), 
						" type_name='公司' and SUB_TYPE='分包' and name in('"+recivUnit+"') ");
				if(companys==null||companys.size()==0) {
					SyncTcTools.setObjectProperties(dmService, obj, new String[] {"cn9NewReserveText3"}, new String[] {"1"});
					continue;
				}
				/*********************同步主表数据***********************************/
				//装数据
				Map<String,Object> mainData= Operator.OperationTcData(token,documentService,dmService, obj, cfb);
				cfb.setData(mainData);
				String mainDocId= syncMainTable(token, documentService, session,dmService, obj, cfb);
				/////////////////////////////////////////////////////////////////
				for(int j=0;j<downloadRelationShips.size();j++) {
					SubTable subTable=downloadRelationShips.get(j);
					/***************************根据form获取数据**********************************/
					List<RelationShip> formShips= subTable.getFormoperation();
					if(formShips!=null&&formShips.size()>0) {
						for(int n=0;n<formShips.size();n++) {
							RelationShip formShip=formShips.get(n);
							getDataByForm(token, documentService, session, dmService, obj, 
									formShip, mainDocId, relationService);
						}
					}
					
					/***************************根据关系获取数据*******************************/
					List<RelationShip> relationShips=subTable.getRelationoperation();
					if(relationShips!=null&&relationShips.size()>0) {
						for(int x=0;x<relationShips.size();x++) {
							RelationShip relationShip=relationShips.get(x);
							
							getDataByRelationShip(token, documentService, session, dmService, obj, relationShip,
									mainDocId, relationService);
							
						}
					}
				}
				
				dispense(ecmSession.getToken(),mainDocId);
			

			}

		

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}
	/**
	 * 根据关系处理数据
	 * @param token
	 * @param docService
	 * @param session
	 * @param dmService
	 * @param itemRev
	 * @param ship
	 * @param parentId
	 * @param relateService
	 * @throws Exception
	 */
	public void getDataByRelationShip(String token,DocumentService docService,Session session,DataManagementService dmService,
			ModelObject itemRev,RelationShip ship,String parentId,RelationService relateService) throws Exception{
		List<ResultData> resultDatas = getObjectsByRelation(session, dmService, (ItemRevision)itemRev, ship);
		for (int x = 0; resultDatas != null && x < resultDatas.size(); x++) {
			ResultData resultData = resultDatas.get(x);
			EcmDocument document = resultData.getDocument();
			String childId = docService.newObject(token, document, null);
			List<FileInfo> files = resultData.getList();
			for (int n = 0; files != null && n < files.size(); n++) {
				FileInfo fileInfo = files.get(n);
				EcmDocument attachmentDoc = new EcmDocument();
				attachmentDoc.setTypeName("附件");
				attachmentDoc.setName(fileInfo.getName());
				EcmContent en = new EcmContent();
				en.setName(fileInfo.getName());
				en.setContentSize(fileInfo.getSize());
				en.setFormatName(FileUtils.getExtention(fileInfo.getName()));
				File file = new File(fileInfo.getUrl());
				InputStream input = new FileInputStream(file);
				en.setInputStream(input);
				String attachmentId = docService.newObject(token, attachmentDoc, en);

				EcmRelation relation = new EcmRelation();
				relation.setParentId(childId);

				relation.setChildId(attachmentId);
				relation.setName("附件");
				relateService.newObject(token, relation);
			}

			EcmRelation relation = new EcmRelation();
			relation.setParentId(parentId);

			relation.setChildId(childId);
//					relation.setName("设计文件");
			relateService.newObject(token, relation);

		}
	}
	
	/**
	 * 同步主表数据
	 * @param itemRev
	 * @param conf
	 * @return
	 * @throws Exception 
	 */
	public String syncMainTable(String token,DocumentService docService,Session session,DataManagementService dmService,
			ModelObject itemRev,ConfBean cfb) throws Exception {
		

		/*
		 * Map<String, Object> row = new HashMap<String, Object>(); List<AttrBean>
		 * attrBeans = cfb.getAttributes(); row.put("TYPE_NAME",
		 * cfb.getSourceTypeName()); for (AttrBean attrBean : attrBeans) { String value=
		 * SyncTcTools.getProperty(dmService, itemRev, attrBean.getTargetName());
		 * row.put(attrBean.getSourceName(), value); }
		 */
		
		// 保存数据至设计分包系统
		EcmDocument doc = new EcmDocument();
//		doc.setAttributes(cfb.getData());
		
		Map<String,Object> datas= cfb.getData();
		Set<String> keys=datas.keySet();
		Iterator<String> it= keys.iterator();
		while(it.hasNext()) {
			String key=it.next();
			DataEntity dte= (DataEntity)datas.get(key);
			doc.addAttribute(key, dte.getAttrValue());
		}
		
		//获取主文件电子件
		List<FileInfo> docFileInfos=getFileTfAttachment(session,(ItemRevision)itemRev,
				"IMAN_specification",doc.getCoding(),StringUtils.isEmpty(doc.getRevision())?"":doc.getRevision());
		EcmContent mainDocContent=new EcmContent();
		if(docFileInfos!=null&&docFileInfos.size()>0) {
			FileInfo mainFileInfo=docFileInfos.get(0);
			String mainFileUrl=mainFileInfo.getUrl();
			File mainFile=new File(mainFileUrl);
			InputStream in=new FileInputStream(mainFile);
			mainDocContent.setInputStream(in);
			mainDocContent.setContentSize(mainFileInfo.getSize());
			mainDocContent.setFormatName(FileUtils.getExtention(mainFileUrl));
			mainDocContent.setName(mainFileInfo.getName());
		}
		String docId = documentService.newObject(token, doc, null);
		
		// 创建字表数据
		// 下载电子文件保存至子表
		
//		dispense(ecmSession.getToken(),docId);
	
		
		
		return docId;
		
	}
	/**
	 * 根据form信息处理数据
	 * @param token
	 * @param docService
	 * @param session
	 * @param dmService
	 * @param itemRev
	 * @param ship
	 * @param parentId
	 * @param relateService
	 * @throws Exception
	 */
	public void getDataByForm(String token,DocumentService docService,Session session,DataManagementService dmService,
			ModelObject itemRev,RelationShip ship,String parentId,RelationService relateService) throws Exception {
		// 检查数据是否有字表
		
//		ModelObject[] modelObjects = itemRev.getPropertyObject("cn9FileList").getModelObjectArrayValue();
		ModelObject[] modelObjects = itemRev.getPropertyObject(ship.getFileListColumn()).getModelObjectArrayValue();
		if (modelObjects != null && modelObjects.length > 0) {
//			List<ResultData> resultDatas = getDetail(session, (ItemRevision) itemRev, "", "cn9FileList");
			List<ResultData> resultDatas = getDetail(session, (ItemRevision) itemRev,ship);
			for (int x = 0; resultDatas != null && x < resultDatas.size(); x++) {
				ResultData resultData = resultDatas.get(x);
				EcmDocument document = resultData.getDocument();
				String childId = docService.newObject(token, document, null);
				List<FileInfo> files = resultData.getList();
				for (int n = 0; files != null && n < files.size(); n++) {
					FileInfo fileInfo = files.get(n);
					EcmDocument attachmentDoc = new EcmDocument();
					attachmentDoc.setTypeName("附件");
					attachmentDoc.setName(fileInfo.getName());
					EcmContent en = new EcmContent();
					en.setName(fileInfo.getName());
					en.setContentSize(fileInfo.getSize());
					en.setFormatName(FileUtils.getExtention(fileInfo.getName()));
					File file = new File(fileInfo.getUrl());
					InputStream input = new FileInputStream(file);
					en.setInputStream(input);
					String attachmentId = docService.newObject(token, attachmentDoc, en);

					EcmRelation relation = new EcmRelation();
					relation.setParentId(childId);

					relation.setChildId(attachmentId);
					relation.setName("附件");
					relateService.newObject(token, relation);
				}

				EcmRelation relation = new EcmRelation();
				relation.setParentId(parentId);

				relation.setChildId(childId);
//						relation.setName("设计文件");
				relateService.newObject(token, relation);

			}
		}
	}
	/**
	 * 分发
	 * @param token
	 * @param docId
	 * @throws Exception
	 */
	private void dispense(String token, String docId) throws Exception {

		EcmDocument doc = documentService.getObjectById(token, docId);
		String currentStatus = doc.getStatus();

		if (currentStatus == null || "".equals(currentStatus)) {
			currentStatus = "新建";
		}
		String nextStatus = StatusEntity.getNextDcStatusValue("新建", doc.getTypeName(), true);

		if ("图文传真,会议纪要".contains(doc.getTypeName())) {
			documentService.updateObject(token, doc, null);
			doc.addAttribute("C_IS_RELEASED", 1);
		} else {

			if ("文件传递单".equals(doc.getTypeName())) {
				logicOptionTransferService.transferOption(token, doc, true);
			} else if ("接口信息传递单".equals(doc.getTypeName()) || "接口信息意见单".equals(doc.getTypeName())) {
				logicOptionInterfaceService.interfaceOption(token, doc);
			} else {
				logicOptionRelevantService.relevantOption(token, doc, true);
			}
			doc.addAttribute("C_IS_RELEASED", 1);
			documentService.updateObject(token, doc, null);

		}

		String contractorStr = doc.getAttributeValue("C_TO") == null ? "" : doc.getAttributeValue("C_TO").toString();
		String copyToStr = doc.getAttributeValue("C_COPY_TO") == null ? ""
				: doc.getAttributeValue("C_COPY_TO").toString();

		String codingStr = doc.getCoding();
		String[] contractors = contractorStr.split(";");
		String[] copyTos = copyToStr.split(";");
		String[] codings = codingStr.split(";");
		for (int i = 0; i < contractors.length; i++) {
			if (!StringUtils.isEmpty(contractors[i])) {
				ExcTransfer excTransfer = new ExcTransfer();
				excTransfer.setItemType(1);
				excTransfer.setDocId(docId);
				excTransfer.setFromName(
						doc.getAttributeValue("C_FROM") != null ? doc.getAttributeValue("C_FROM").toString() : "");
				excTransfer.setToName(contractors[i]);
				excTransfer.setCreationDate(new Date());
				excTransfer.setCreator(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSender(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSendDate(new Date());

				excTransfer.setStatus(nextStatus);
				excTransferService.newObject(excTransfer);
			}

		}

		for (int i = 0; i < copyTos.length; i++) {
			if (!StringUtils.isEmpty(copyTos[i])) {
				ExcTransfer excTransfer = new ExcTransfer();
				excTransfer.setItemType(1);
				excTransfer.setDocId(docId);
				excTransfer.setFromName(
						doc.getAttributeValue("C_FROM") != null ? doc.getAttributeValue("C_FROM").toString() : "");
				excTransfer.setToName(copyTos[i]);
				excTransfer.setCreationDate(new Date());
				excTransfer.setCreator(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSender(this.getEcmSession().getCurrentUser().getUserName());
				excTransfer.setSendDate(new Date());

				excTransfer.setStatus(nextStatus);
				excTransferService.newObject(excTransfer);
			}

		}

		if ("新建".equals(currentStatus)) {
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
	public List<ResultData> getDetail(Session session, ItemRevision itemRev, RelationShip ship)
			throws Exception {
		DataManagementService dmServ = DataManagementService.getService(session.getConnection());

		dmServ.getProperties(new ModelObject[] { itemRev }, new String[] { ship.getFileListColumn(), "cn9ReceiveSendFlag" });
		String receiveSendFlag = itemRev.getPropertyObject("cn9ReceiveSendFlag").getStringValue();
		
		ModelObject[] modelObjects = itemRev.getPropertyObject(ship.getFileListColumn()).getModelObjectArrayValue();
		
		if (modelObjects != null && modelObjects.length > 0) {
			
			List<String> cols=new ArrayList<String>();
			ConfBean formBean= Operator.getSubBeanById(ship.getRefformId());
			List<AttrBean> attrs= formBean.getAttributes();
			for(AttrBean attr:attrs) {
				cols.add(attr.getTargetName());
			}
//			dmServ.getProperties(modelObjects,
//					new String[] { "cn9InternalCode", "cn9ExternalCode", "cn9Rev", "cn9FileName", "cn9States",
//							"cn9Page", "cn9Paper", "cn9Digital", "cn9Blue", "cn9Language", "cn9DrawNo" });
			dmServ.getProperties(modelObjects,cols.toArray(new String[0]));
			List<ResultData> datas = new ArrayList<ResultData>();
			for (ModelObject modelObject : modelObjects) {
				ResultData data = new ResultData();
				EcmDocument document = new EcmDocument();
				List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
				Map<String,Object> row=new HashMap<>();
				Map<String,Object> rowDt=new HashMap<>();
				row.put("TYPE_NAME", formBean.getSourceTypeName());
				for(AttrBean attr:attrs) {
					row.put(attr.getSourceName(), modelObject.getPropertyObject(attr.getTargetName()).getStringValue());
					
					DataEntity dt=new DataEntity();
					dt.setAttrName(attr.getSourceName());
					dt.setAttrValue( modelObject.getPropertyObject(attr.getTargetName()).getStringValue());
					dt.setDataType(attr.getDataType());
					rowDt.put(attr.getSourceName(),dt);
				}
				document.setAttributes(row);
				ship.setData(rowDt);
				
				if(ship.getSearchConf()==null) {
					data.setDocument(document);
					datas.add(data);
					return datas;
				}
				/*******************获取电子文件***************************/
				ItemRevision itemRevision=queryItemRevision(session, ship);
				
//				ItemRevision itemRevision = queryItemRevision(session,
//						modelObject.getPropertyObject("cn9ExternalCode").getStringValue(),
//						modelObject.getPropertyObject("cn9Rev").getStringValue());

//				List<FileInfo> fileList = getFileTfAttachment(session, itemRevision, "IMAN_specification", iedItemId,
//						iedRevId);
				
				List<FileInfo> fileList = getFileTfAttachment(session, itemRevision, ship.getName(), document.getCoding(),
						document.getRevision());
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
//					if (!objectType.equals("PDF") && !objectType.equals("TIF"))
					if (!objectType.equals("PDF"))
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
	 * 通过关系获取对象
	 * @return
	 * @throws Exception 
	 */
	public List<ResultData> getObjectsByRelation(Session session,DataManagementService dmService, /* String relationTypeName, */
			ItemRevision itemRev,RelationShip ship) throws Exception {
		ConfBean subCfb= Operator.getSubBeanById(ship.getRefSubId());
		
		DataManagementService dmServ = DataManagementService.getService(session.getConnection());
		FileManagementUtility fMSFileManagement = new FileManagementUtility(session.getConnection());

		// ArrayOfFileInfo attachments = new ArrayOfFileInfo();
		List<FileInfo> attachments = new ArrayList<FileInfo>();
		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = ship.getName();/*relationTypeName;*/

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

		// 1 从数据集中导出文件
		ExpandGRMRelationsOutput[] outputs = response.output;

		ModelObject[] relationObjects = null;
		List<ResultData> result =new ArrayList<ResultData>();
		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				relationObjects = data.otherSideObjects;
				dmServ.getProperties(relationObjects, new String[] { "object_name", "ref_list" });
				
				for (int i = 0; i < relationObjects.length; i++) {
					ModelObject dataset = relationObjects[i];
					ResultData rs=new ResultData();
					// add by xiaolei 20151029 for bug 2369 begin
					// 如果数据集文件类型不是PDF则不传输
					String objectType = relationObjects[i].getTypeObject().getName();
					// modify by xiaolei 20150824 for bug 1965
					// if(objectType.equals("MSWord") || objectType.equals("MSWordX"))
//					if (!objectType.equals("PDF") && !objectType.equals("TIF"))
					if (!objectType.equals("PDF"))
						continue;
					// add end
					
					List<AttrBean> attrs= subCfb.getAttributes();
					Map<String,Object> row=new HashMap<String, Object>();
					for(int x=0;attrs!=null&&x<attrs.size();x++) {
						AttrBean attr= attrs.get(x);
						String value=SyncTcTools.getProperty(dmService, itemRev, attr.getTargetName(),attr.getDataType());
						row.put(attr.getSourceName(), value);
					}
					EcmDocument document=new EcmDocument();
					document.setAttributes(row);
					rs.setDocument(document);
					
					// 每个导出的对象有单独的目录
					String tmpDirName = document.getCoding() + "_" + document.getRevision() + "_" + (new Date()).getTime();
					File tmpExportDir = new File(exportDir, tmpDirName);

					if (!tmpExportDir.exists())
						tmpExportDir.mkdir();
					if (!tmpExportDir.isDirectory()) {
						tmpExportDir.delete();
						tmpExportDir.mkdir();
					}
					
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
					attachment.setSize(fileSize);
					attachment.setWjbm(document.getCoding());
					// attachments.add(attachment);
					// attachment.addFileInfo(attachment);
					// attachments.addFileInfo(attachment);
					attachments.add(attachment);
					rs.setList(attachments);
					result.add(rs);
				}
			}
		}
		fMSFileManagement.term();
		return result;
	
	}
	
	/**
	 * 
	 * @param itemRev
	 * @param relationTypeName 关系名称
	 * @return
	 * @throws Exception
	 */
	public List<FileInfo> getAttachment(Session session, ItemRevision itemRev, String relationTypeName, String fileId,
			String revId) throws Exception {
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

					// add by xiaolei 20151029 for bug 2369 begin
					// 如果数据集文件类型不是PDF则不传输
					String objectType = relationObjects[i].getTypeObject().getName();
					// modify by xiaolei 20150824 for bug 1965
					if (!objectType.equals("PDF"))
						// if(!objectType.equals("PDF") && !objectType.equals("TIF"))
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
					FileInfo fileInfo = new FileInfo();
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
	
	/**
	 * 查询单条tc数据
	 * @param session
	 * @param ship
	 * @return
	 * @throws Exception 
	 */
	public ItemRevision queryItemRevision(Session session, RelationShip ship) throws Exception {
		SearchConf searchConf= ship.getSearchConf();
		List<Condition> conditions= searchConf.getConditions();
		if(conditions==null||conditions.size()==0) {
			throw new Exception("请确认relationShip id="+ship.getId()+"是否有配置conditions");
		}
		
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
//				if (savedQueries.queries[i].name.equals("Item Revision...")) {
				if (savedQueries.queries[i].name.equals(searchConf.getName())) {
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
			List<String> entries=new ArrayList<String>();
			List<String> values=new ArrayList<String>();
			
			for(int i=0;i<conditions.size();i++) {
				Condition c= conditions.get(i);
				entries.add(c.getName());
				String val="";
				if(ship.getData().get(c.getValue())==null) {
					val=c.getValue();
				}else {
					DataEntity dt= (DataEntity) ship.getData().get(c.getValue());
					String type= dt.getDataType();
					
					if("Time".equals(type)) {
						SimpleDateFormat fmt=new SimpleDateFormat("yyyy-M-dd");
						Date date= fmt.parse(dt.getAttrValue().toString());
						val= fmt.format(date);
					}else {
						val=dt.getAttrValue().toString();
					}
				}
				values.add(val);
			}
			QueryInput[] savedQueryInput = new QueryInput[1];
			savedQueryInput[0] = new QueryInput();
			savedQueryInput[0].query = query;
			savedQueryInput[0].maxNumToReturn = 25;
			savedQueryInput[0].limitList = new ModelObject[0];
//			savedQueryInput[0].entries = new String[] { "零组件 ID", "版本" };
//			savedQueryInput[0].values = new String[] { itemId, revId };
			savedQueryInput[0].entries = entries.toArray(new String[0]);
			savedQueryInput[0].values = values.toArray(new String[0]);
			SavedQueriesResponse response = queryService.executeSavedQueries(savedQueryInput);
			QueryResults found = response.arrayOfResults[0];

			// add by Sean 20151012
			if (found.objectUIDS.length == 0) {
				return null;
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
