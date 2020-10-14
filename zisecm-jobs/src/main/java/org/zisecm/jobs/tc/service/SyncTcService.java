package org.zisecm.jobs.tc.service;

import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.bean.conf.Condition;
import org.zisecm.jobs.bean.conf.ConfBean;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.bean.conf.RelationShip;
import org.zisecm.jobs.bean.conf.SearchConf;
import org.zisecm.jobs.bean.conf.SubTable;
import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.tools.SyncTcOption;
import org.zisecm.jobs.tc.tools.SyncTcTools;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.FtpDownload;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.ReturnVal;
import org.zisecm.jobs.utils.HttpTools;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.core._2006_03.FileManagement.DatasetFileInfo;
import com.teamcenter.services.loose.core._2006_03.FileManagement.GetDatasetWriteTicketsInputData;
import com.teamcenter.services.strong.administration.PreferenceManagementService;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateDatasetsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateRelationsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemIdsAndInitialRevisionIds;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.Relationship;
import com.teamcenter.services.strong.core._2006_03.Session.PreferencesResponse;
import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateIn;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateInput;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateResponse;
import com.teamcenter.services.strong.core._2008_06.DataManagement.DatasetProperties2;
import com.teamcenter.services.strong.query.SavedQueryService;
import com.teamcenter.services.strong.query._2006_03.SavedQuery.GetSavedQueriesResponse;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.QueryResults;
import com.teamcenter.services.strong.query._2007_09.SavedQuery.SavedQueriesResponse;
import com.teamcenter.services.strong.query._2008_06.SavedQuery;
import com.teamcenter.services.strong.query._2008_06.SavedQuery.QueryInput;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Preferences;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.Type;
import com.teamcenter.soa.client.model.strong.Dataset;
import com.teamcenter.soa.client.model.strong.Form;
import com.teamcenter.soa.client.model.strong.ImanQuery;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.User;
import com.teamcenter.soa.exceptions.NotLoadedException;
@Service
public class SyncTcService {
	@Autowired
	private SyncTcOption tcOption;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private Environment env;
	@Autowired
	private AuthService authService;
	@Autowired
	private EcmContentMapper contentMapper;
	public IEcmSession getEcmSession() throws Exception {
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
		return ecmSession;
	}
	private static Logger logger=Logger.getLogger(SyncTcService.class);
	/**
	 * 
	 * @param data
	 * @param cfb
	 * @return
	 * @throws Exception
	 */
	public String setFileData(Map<String,Object> data,ConfBean cfb) throws Exception {
		 IEcmSession ecmSession= getEcmSession();
		 String token=ecmSession.getToken();
		
		Session session=SyncTcTools.getSession(env);
	    logger.info("-----------begin setCRData------------");
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String start = df.format(new Date());
	    logger.info("-----------开始时间：" + start + "------------");
	    System.out.println("CR开始时间：" + start);

	    CreateItemsOutput createItemOutput = null;
	    ModelObject mainData=null;
	    DataManagementService dmService = 
	    DataManagementService.getService(session.getConnection());
	    cfb= Operator.OperationContractorData(ecmSession.getToken(),session,
				documentService,dmService,data,cfb);
	    Map<String,Object> tcdt=cfb.getData();
	    try
	    {
	      DataManagement dataManagement = new DataManagement(session);
	      Query query = new Query(session);
	      Workflow workflow = new Workflow(session);
	     
	      DataEntity dt=(DataEntity)tcdt.get("projectId");
	      String projectId=dt.getAttrValue().toString();
	      TC_Project project = query.queryProjectExist(projectId);

//	      ModelObject findChangeRqRevision = query.queryFileRevision(projectId,data.get("CODING").toString());
	      Operator.OperationContractorData(token, session, documentService, dmService, data, cfb);
	      ModelObject findChangeRqRevision=queryItemRevision(session, cfb);
	      
	      if (findChangeRqRevision != null) {
	    	  if("文件传递单".equals(data.get("TYPE_NAME").toString())) {
	    		  throw new Exception("文件传递单数据在TC中已经存在符合条件的文件传递单：" 
	    				  + data.get("C_PROJECT_NAME").toString() 
	    				  + "|" +  data.get("CODING").toString() + "|" );
	    	  }else {
	    		  
	    		  Map<String,Object> dataUp= cfb.getData();
	    		  if(dataUp!=null) {
	    			  List<String> names=new ArrayList<String>();
	    			  List<String> values=new ArrayList<String>();
	    			  Set<String> keys= dataUp.keySet();
	    			  Iterator<String> it= keys.iterator();
	    			  while(it.hasNext()) {
							String key = it.next();
							if("projectId".equals(key)) {
								continue;
							}
							names.add(key);
							DataEntity dataEntity = (DataEntity) dataUp.get(key);
							if (dataEntity.getDataType() != null && "Time".equals(dataEntity.getDataType())) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
								SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
								Object v=dataEntity.getAttrValue();
								String val="";
								if(v==null) {
									val=dateFormat.format(new Date());
								}else {
									val=v.toString();
								}
								Date time = dateFormat.parse(val);
								String timeStr = dateFormat2.format(time)+"T"+dateFormat3.format(time);
								values.add(timeStr);
							} else {
								Object v=dataEntity.getAttrValue();
								if(v==null) {
									values.add("");
								}else {
									values.add(dataEntity.getAttrValue().toString());
								}
								
							}
	    			  }
	    			  
	    			  SyncTcTools.setObjectProperties(dmService, findChangeRqRevision, names.toArray(new String[0]), values.toArray(new String[0]));
	    		  }
	    		  
	    		  
	    	  }
//	    	 return findChangeRqRevision.getUid();
	      }
	     
//	      createItemOutput = dataManagement.createFileRevision(fileDataRequest);
	      if(findChangeRqRevision==null) {
	    	  /***************************创建主表信息********************************************************/
		      createItemOutput=createFileRevision(getEcmSession().getToken(),documentService,data, cfb);
		      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

		      dataManagement.removeAndAssignProject(createItemOutput.item, project);
		      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);
		      mainData=createItemOutput.itemRev;
	      }else {
	    	  mainData=findChangeRqRevision;
	      }
	      
	      
	      
	      /**************************判断是否有子表需要操作***********************************************/
	      List<SubTable> subTables= cfb.getUprelationShips();
	      if(subTables==null||subTables.size()==0) {
	    	  return mainData.getUid();
//	    	 return createItemOutput.item.get_item_id();
	      }
	      for(int n=0;n<subTables.size();n++) {
	    	  SubTable subTable=subTables.get(n);
	    	  /**************************判断是否为主表创建附件******************************************/
	    	  List<RelationShip> mainAttachs=subTable.getMainattachs();
	    	  if(mainAttachs!=null&&mainAttachs.size()>0) {
	    		  for(int m=0;m<mainAttachs.size();m++) {
	    			  RelationShip relationShip=mainAttachs.get(m);
	    			  String mSql= relationShip.getChildrenDataSql();
	    			  mSql=Operator.operationSql(data, mSql);
	    			  List<Map<String,Object>> docList= documentService.getMapList(token, mSql);
	    			  if(docList!=null&&docList.size()>0) {
	    				  addAttachements(token, documentService, session, docList,data, dmService, mainData/*createItemOutput.itemRev*/, relationShip);
	    				  
	    				}
	    		  }
	    	  }
	    	  
	    	  /**************************判断是否创建子表对象******************************************/
	    	  List<CreateItemsOutput> createdItems=null; 
	    	  List<RelationShip> subShips=subTable.getSuboperation();
	    	  if(subShips!=null&&subShips.size()>0) {
	    		  for(int s=0;s<subShips.size();s++) {
	    			  RelationShip relationShip=subShips.get(s);
	    			  String sSql=relationShip.getChildrenDataSql();
	    			  sSql=Operator.operationSql(data, sSql);
	    			  List<Map<String,Object>> docList= documentService.getMapList(token, sSql);
	    			  if(docList!=null&&docList.size()>0) {
	    				  createdItems=createSubFileRevision(dmService, token,session, documentService, docList, relationShip);
	    			  }
	    		  }
	    	  }
	    	  
	    	  /**************************判断是否为子表创建附件******************************************/
	    	  List<RelationShip> subattachs=subTable.getSubattachs();
	    	  if(subattachs!=null&&subattachs.size()>0) {
	    		  for(int m=0;m<subattachs.size();m++) {
	    			  RelationShip relationShip=subattachs.get(m);
	    			  String mSql=relationShip.getChildrenDataSql();
	    			  mSql=Operator.operationSql(data, mSql);
	    			  List<Map<String,Object>> docList= documentService.getMapList(token, mSql);
	    			  if(docList!=null&&docList.size()>0) {
	    				  addSubAttachement(token, documentService, session, docList, dmService, relationShip);
	    			  }
	    		  }
	    	  }
	    	  
	    	  /**************************判断是否为子表创建form******************************************/
	    	  List<RelationShip> formOptionList= subTable.getFormoperation();
	    	  if(formOptionList!=null&&formOptionList.size()>0) {
	    		  for(int f=0;f<formOptionList.size();f++) {
	    			  RelationShip relationShip=formOptionList.get(f);
	    			  String fSql=relationShip.getChildrenDataSql();
	    			  fSql= Operator.operationSql(data, fSql);
	    			  List<Map<String,Object>> docList= documentService.getMapList(token, fSql);
	    			  if(docList!=null&&docList.size()>0) {
	    					addForm4Main(session,dmService, token, documentService, docList, mainData/*createItemOutput.itemRev*/, relationShip);
	    				}
	    				
	    		  }
	    	  }
	    	  /**************************判断是否为子表创建关系******************************************/
	    	  List<RelationShip> relationList= subTable.getRelationoperation();
	    	  if(relationList!=null&&relationList.size()>0) {
	    		  for(int r=0;r<relationList.size();r++) {
	    			  RelationShip relationShip=relationList.get(r);
	    			  String rSql=relationShip.getChildrenDataSql();
	    			  rSql= Operator.operationSql(data, rSql);
	    			  List<Map<String,Object>> docList= documentService.getMapList(token, rSql);
	    			  if(docList!=null&&docList.size()>0) {
	    					if(relationShip.getSearchConf()==null) {
	    						throw new Exception("请确认"+cfb.getId()+"中的relationoperation是否配置了查询配置");
	    					}
	    					addRelation4Children(token, session, documentService, dmService, 
	    							mainData/*createItemOutput.itemRev*/, relationShip, docList,createdItems);
	    			  }
	    		  }
	    	  }
	    			  
	      }
	      /*****************************发起流程*********************************************/
	      String docManagerUserId = dataManagement.getUserFromPref(projectId);
	      User user = null;
	      if(findChangeRqRevision==null) {
	    	  if (!docManagerUserId.equals("")) {
	  	        user = query.queryUser(docManagerUserId);
	  	        if (user != null) {
	  	          dataManagement.changeOwner(createItemOutput.item, user);
	  	          dataManagement.changeOwner(createItemOutput.itemRev, user);
	  	        }
	  	      }

	  	     // boolean isReply = setCRDataRequest.getIsReply().equals("Y");
	  	      dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new String[] { "object_type" });
	  	      String itemRevType = createItemOutput.itemRev.get_object_type();

//	  	      String processTemplateName = "LDM-001-收文流程";
	  	      String processTemplateName = cfb.getWorkflowName();

	  	      if (!processTemplateName.equals(""))
	  	      {
	  	        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	  	      }
	      }else {
	    	  if (!docManagerUserId.equals("")) {
		  	        user = query.queryUser(docManagerUserId);
		  	      }

//	  	      String processTemplateName = "LDM-001-收文流程";
	  	      String processTemplateName = cfb.getWorkflowName();

	  	      if (!processTemplateName.equals(""))
	  	      {
	  	        workflow.createWorkflow(mainData/*createItemOutput.itemRev*/, processTemplateName, user);
	  	      }
	      }
	      

	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error(e);

	      if (createItemOutput != null) {
	        try
	        {
	          ServiceData serviceData = dmService.deleteObjects(new ModelObject[] { createItemOutput.item });

	          String ret = "";
	          String[] msgs = serviceData.getPartialError(0).getMessages();

	          for (int i = 0; i < msgs.length; i++) {
	            ret = ret + msgs[i] + " ";
	          }

	          System.out.println(ret);
	          logger.info(ret);
	        }
	        catch (Exception e2) {
	          e2.printStackTrace();
	          logger.error(e2);
	        }
	      }

	      throw e;
	    }

	    
	    String end = df.format(new Date());
	    System.out.println("文件传递单结束时间：" + end);
	    logger.info("-----------结束时间：" + end + "------------");
	    logger.info("-----------end setCRData------------");
	    if(createItemOutput==null) {
	    	return mainData.getUid();
	    }
	    return createItemOutput.item.get_item_id();

	}
	
	/*
	 * public String setFileData(Map<String,Object> data,Map<String,Object> tcData)
	 * throws Exception { Map<String,Object> tcdt=(Map<String, Object>)
	 * tcData.get("data"); Session session=SyncTcTools.getSession(env);
	 * logger.info("-----------begin setCRData------------");
	 * 
	 * SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String
	 * start = df.format(new Date()); logger.info("-----------开始时间：" + start +
	 * "------------"); System.out.println("CR开始时间：" + start);
	 * 
	 * CreateItemsOutput createItemOutput = null; DataManagementService dmService =
	 * DataManagementService.getService(session.getConnection()); try {
	 * DataManagement dataManagement = new DataManagement(session); Query query =
	 * new Query(session); Workflow workflow = new Workflow(session);
	 * 
	 * DataEntity dt=(DataEntity)tcdt.get("projectId"); String
	 * projectId=dt.getAttrValue().toString(); TC_Project project =
	 * query.queryProjectExist(projectId);
	 * 
	 * ModelObject findChangeRqRevision =
	 * query.queryFileRevision(projectId,data.get("CODING").toString());
	 * 
	 * if (findChangeRqRevision != null) {
	 * if("文件传递单".equals(data.get("TYPE_NAME").toString())) { throw new
	 * Exception("文件传递单数据在TC中已经存在符合条件的文件传递单：" +
	 * data.get("C_PROJECT_NAME").toString() + "|" + data.get("CODING").toString() +
	 * "|" ); }else {
	 * 
	 * }
	 * 
	 * }
	 * 
	 * // createItemOutput = dataManagement.createFileRevision(fileDataRequest);
	 * createItemOutput=createFileRevision(getEcmSession().getToken(),
	 * documentService,data, tcData); dmService.refreshObjects(new ModelObject[] {
	 * createItemOutput.item });
	 * 
	 * dataManagement.removeAndAssignProject(createItemOutput.item, project);
	 * dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);
	 * 
	 * String
	 * sql="select a.* from ecm_document a,ecm_relation b where a.id=b.child_id and b.parent_id='"
	 * +data.get("ID").toString()+"'"; List<Map<String,Object>> docList=
	 * documentService.getMapList(getEcmSession().getToken(), sql);
	 * 
	 * if (docList != null&&docList.size()>0) { //
	 * dataManagement.addChangeRqRevisionAttachements(createItemOutput.itemRev,
	 * fileDataRequest.getAttachments()); addChangeRqRevisionAttachements(session,
	 * dmService, createItemOutput.itemRev, data,tcData,docList); }
	 * 
	 * String docManagerUserId = dataManagement.getUserFromPref(projectId); User
	 * user = null; if (!docManagerUserId.equals("")) { user =
	 * query.queryUser(docManagerUserId); if (user != null) {
	 * dataManagement.changeOwner(createItemOutput.item, user);
	 * dataManagement.changeOwner(createItemOutput.itemRev, user); } }
	 * 
	 * // boolean isReply = setCRDataRequest.getIsReply().equals("Y");
	 * dmService.getProperties(new ModelObject[] { createItemOutput.itemRev }, new
	 * String[] { "object_type" }); String itemRevType =
	 * createItemOutput.itemRev.get_object_type();
	 * 
	 * String processTemplateName = "LDM-001-收文流程";
	 * 
	 * if (!processTemplateName.equals("")) {
	 * workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
	 * }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); logger.error(e);
	 * 
	 * if (createItemOutput != null) { try { ServiceData serviceData =
	 * dmService.deleteObjects(new ModelObject[] { createItemOutput.item });
	 * 
	 * String ret = ""; String[] msgs =
	 * serviceData.getPartialError(0).getMessages();
	 * 
	 * for (int i = 0; i < msgs.length; i++) { ret = ret + msgs[i] + " "; }
	 * 
	 * System.out.println(ret); logger.info(ret); } catch (Exception e2) {
	 * e2.printStackTrace(); logger.error(e2); } }
	 * 
	 * throw e; }
	 * 
	 * 
	 * String end = df.format(new Date()); System.out.println("文件传递单结束时间：" + end);
	 * logger.info("-----------结束时间：" + end + "------------");
	 * logger.info("-----------end setCRData------------"); return
	 * createItemOutput.item.get_item_id();
	 * 
	 * }
	 */
	/**
	 *为子文件添加关系
	 * @param token
	 * @param session
	 * @param ecmDocService
	 * @param dmService
	 * @param object
	 * @param ship
	 * @param docList
	 * @return
	 * @throws Exception
	 */
	public boolean addRelation4Children(String token,Session session,DocumentService ecmDocService,
			DataManagementService dmService,ModelObject object,RelationShip ship,List<Map<String,Object>> docList,
			List<CreateItemsOutput> createdItems) throws Exception {
		if(createdItems==null) {
			for(int i=0;i<docList.size();i++) {
				Map<String,Object> doc= docList.get(i);
				ship=Operator.OperationContractorSubData(token,session,dmService, ecmDocService, doc, ship);
				ItemRevision itemRev=queryItemRevision(session, ship);
				if("p2c".equals(ship.getReferenceName())) {
					addRelation(dmService, itemRev, object, ship.getName());
				}else if("c2p".equals(ship.getReferenceName())) {
					addRelation(dmService, object, itemRev, ship.getName());
				}
			}
		}else {
			for(int i=0;i<createdItems.size();i++) {
				CreateItemsOutput createdItem=createdItems.get(i);
				if("p2c".equals(ship.getReferenceName())) {
					addRelation(dmService, createdItem.itemRev, object, ship.getName());
				}else if("c2p".equals(ship.getReferenceName())) {
					addRelation(dmService, object, createdItem.itemRev, ship.getName());
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * 创建关系
	 * @param dmService
	 * @param object
	 * @param object2
	 * @param relationType
	 * @return
	 */
	public boolean addRelation(DataManagementService dmService,ModelObject object,ModelObject object2,String relationType){
		
		Relationship ship=new Relationship();
		 ship.clientId="AppX-Test";
		 ship.primaryObject=object;
		 ship.secondaryObject=object2;
		 ship.relationType=relationType;
		 CreateRelationsResponse response=dmService.createRelations(new Relationship[]{ship});
		 boolean flag=true;
		 if(response.serviceData.sizeOfPartialErrors() > 0){
			flag=false;
		}else{
			flag=true;
		}
		 return flag;
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

			/*
			 * if (found.objectUIDS.length == 0) return null; else { DataManagementService
			 * dmService = DataManagementService.getService(SoaHelper.getSoaConnection());
			 * ServiceData serviceData = dmService .loadObjects(found.objectUIDS); if
			 * (serviceData.sizeOfPartialErrors() == 0) { return
			 * (ItemRevision)serviceData.getPlainObject(0); } }
			 */
			// add by Sean 20151012
			if (found.objectUIDS.length == 0) {
//				// add by Sean 20151012
//				savedQueryInput[0].entries = new String[] { "Item ID", "Revision" };
//				response = queryService.executeSavedQueries(savedQueryInput);
//				found = response.arrayOfResults[0];
//				if (found.objectUIDS.length == 0)
//					return null;
//				else {
//					DataManagementService dmService = DataManagementService.getService(session.getConnection());
//					ServiceData serviceData = dmService.loadObjects(found.objectUIDS);
//					if (serviceData.sizeOfPartialErrors() == 0) {
//
//						return (ItemRevision) serviceData.getPlainObject(0);
//					}
//				}
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
	
	/**
	 * 查询单条tc数据
	 * @param session
	 * @param ship
	 * @return
	 * @throws Exception 
	 */
	public ItemRevision queryItemRevision(Session session,ConfBean conf) throws Exception {
		
		SearchConf searchConf= conf.getValidateSearchConf();
		List<Condition> conditions= searchConf.getConditions();
		if(conditions==null||conditions.size()==0) {
			throw new Exception("请确认relationShip id="+conf.getId()+"是否有配置conditions");
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
				if(conf.getData().get(c.getValue())==null) {
					val=c.getValue();
				}else {
					DataEntity dt= (DataEntity) conf.getData().get(c.getValue());
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
	
	/**
	 * 
	 * @param type item type
	 * @return 
	 * @throws Exception 
	 */
	public ItemIdsAndInitialRevisionIds generateItemIds(String type) throws Exception {
		// Get the service stub
		DataManagementService dmService = DataManagementService
				.getService(SyncTcTools.getSession(env).getConnection());

		GenerateItemIdsAndInitialRevisionIdsProperties[] properties = new GenerateItemIdsAndInitialRevisionIdsProperties[1];
		GenerateItemIdsAndInitialRevisionIdsProperties property = new GenerateItemIdsAndInitialRevisionIdsProperties();

		property.count = 1;
		property.itemType = type;
		property.item = null; // Not used
		properties[0] = property;

		// *****************************
		// Execute the service operation
		// *****************************
		GenerateItemIdsAndInitialRevisionIdsResponse response = dmService
				.generateItemIdsAndInitialRevisionIds(properties);

		// The AppXPartialErrorListener is logging the partial errors returned
		// In this simple example if any partial errors occur we will throw a
		// ServiceException
		if (response.serviceData.sizeOfPartialErrors() > 0)
			throw new ServiceException(
					"DataManagementService.generateItemIdsAndInitialRevisionIds returned a partial error.");

		// The return is a map of ItemIdsAndInitialRevisionIds keyed on the
		// 0-based index of requested IDs. Since we only asked for IDs for one
		// data type, the map key is '0'
		BigInteger bIkey = new BigInteger("0");

		@SuppressWarnings("unchecked")
		Map<BigInteger, ItemIdsAndInitialRevisionIds[]> allNewIds = response.outputItemIdsAndInitialRevisionIds;
		ItemIdsAndInitialRevisionIds[] myNewIds = allNewIds.get(bIkey);

		return myNewIds[0];
	}
	/**
	 * 创建子文件版本
	 * add by xiaolei 2014-11-22
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public List<CreateItemsOutput> createSubFileRevision(DataManagementService dmService,String token,Session session,
			DocumentService docService,
			List<Map<String, Object>> docList, RelationShip relationShip) throws Exception {
		ConfBean cfb = Operator.getSubBeanById(relationShip.getRefSubId());
		List<CreateItemsOutput> createdOutputItems=new ArrayList<CreateItemsOutput>();
		
		String tcType = cfb.getTcTableName();
		for (int n = 0; n < docList.size(); n++) {
			//////////////////////// 创建主文件////////////////////////////
			Map<String, Object> data = docList.get(n);
			Operator.OperationContractorData(token,session, docService,dmService, data, cfb);
			Map<String, Object> tcdt = cfb.getData();
			
			ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds(tcType);
			HashMap<String, VecStruct> revisionStrMap = new HashMap<>();
			insertValueMap(revisionStrMap, "item_revision_id", itemidAndRevId.newRevId);
			
			CreateItemsOutput output = null;

			ItemProperties itemProperty = new ItemProperties();
			
			String itemId="";
			if(tcdt.get("item_id")==null) {
				itemId=itemidAndRevId.newItemId;
			}else {
				DataEntity dte=(DataEntity)tcdt.get("item_id");
				itemId=dte.getAttrValue().toString();
				tcdt.keySet().remove("item_id");
			}
			
			itemProperty.clientId = "webservice";
			itemProperty.itemId = itemidAndRevId.newItemId;
			itemProperty.revId = itemidAndRevId.newRevId;

			
			String object_name = data.get("CODING").toString()+(data.get("REVISION")==null?"":"_"+data.get("REVISION").toString());
					//data.get("TITLE") == null ? "" : data.get("TITLE").toString();
			int length = object_name.length();
			String str = "";
			if (length > 120) {
				str = object_name.substring(0, 120);
				itemProperty.name = str + "..";
			} else {
				itemProperty.name = object_name;
			}

			itemProperty.type = tcType.substring(0, tcType.length() - 8);
			itemProperty.description = "";
			itemProperty.uom = "";
			ItemProperties[] itemProps = new ItemProperties[1];
			itemProps[0] = itemProperty;

			CreateItemsResponse response = dmService.createItems(itemProps, null, "");

			ServiceData serviceData = response.serviceData;
			if (serviceData.sizeOfPartialErrors() == 0) {
				output = response.output[0];
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				throw new Exception(ret);
			}

			ItemRevision itemRev = output.itemRev;
			
			Set<String> keys = tcdt.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				DataEntity dt = (DataEntity) tcdt.get(key);
				if ("projectId".equals(key) || dt == null || dt.getAttrValue() == null) {
					continue;
				}
				if (dt.getDataType() != null && "Time".equals(dt.getDataType())) {
					insertDateValueMap(revisionStrMap, key, dt.getAttrValue().toString());
				} else {
					insertValueMap(revisionStrMap, key, dt.getAttrValue().toString());
				}

			}

			serviceData = dmService.setProperties(new ModelObject[] { itemRev }, revisionStrMap);

			if (serviceData.sizeOfPartialErrors() != 0) {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				dmService.deleteObjects(new ModelObject[] { output.item }); // add by xiaolei 20151103

				throw new Exception(ret);
			}

			////////////////////// 为主文件添加电子文件//////////////////////////////

//			Session session = SyncTcTools.getSession(env);
			addFile4Main(session, dmService, itemRev, data, cfb);
			/////////////////////////////////////////////////////////
			createdOutputItems.add(output);
		}

		return createdOutputItems;
	}
	
	
	/**
	 * 创建主文件版本
	 * add by xiaolei 2014-11-22
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createFileRevision(String token,DocumentService docService,
			Map<String,Object> data,ConfBean cfb) throws Exception{
		String tcType=cfb.getTcTableName();
		////////////////////////创建主文件////////////////////////////
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds(tcType);

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= data.get("TITLE")==null?"":data.get("TITLE").toString();
        int length=object_name.length();
        String str="";
        if(length>120){
        	str=object_name.substring(0,120);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
        //itemProperty.name =str+".."; 
        itemProperty.type = tcType.substring(0,tcType.length()-8);
        itemProperty.description = "";
        itemProperty.uom = "";
      
        itemProps[0] = itemProperty;
        DataManagementService dmService = DataManagementService
				.getService(SyncTcTools.getSession(env).getConnection());
        CreateItemsResponse response = dmService.createItems(itemProps, null, "");
        
        ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			output =  response.output[0];
		} else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap=new HashMap<>();
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		Map<String,Object> tcdt=cfb.getData();
		Set<String> keys=tcdt.keySet();
		Iterator<String> it= keys.iterator();
		while(it.hasNext()) {
			String key=it.next();
			DataEntity dt= (DataEntity) tcdt.get(key);
			if("projectId".equals(key)||dt==null||dt.getAttrValue()==null) {
				continue;
			}
			if(dt.getDataType()!=null&&"Time".equals(dt.getDataType())) {
				insertDateValueMap(revisionStrMap,key,dt.getAttrValue().toString());
			}else {
				insertValueMap(revisionStrMap,key,dt.getAttrValue().toString());
			}
			
		}
		
		serviceData = dmService.setProperties(
				new ModelObject[] { itemRev }, revisionStrMap);

		if (serviceData.sizeOfPartialErrors() != 0) {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			dmService.deleteObjects(new ModelObject[]{output.item}); //add by xiaolei 20151103
			
			throw new Exception(ret);
		}
		
		//////////////////////为主文件添加电子文件//////////////////////////////
		
		Session session=SyncTcTools.getSession(env);
		addFile4Main(session,dmService,itemRev,data,cfb);
		/////////////////////////////////////////////////////////
		
				
		return output;
	}
	
	public static void insertDateValueMap(HashMap<String, VecStruct> revisionStrMap,String propertyName,String value)throws Exception {
		if(value!=null && !value.equals("")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
			Date time = dateFormat.parse(value);
			String timeStr = dateFormat2.format(time)+"T"+dateFormat3.format(time);
			insertValueMap(revisionStrMap,propertyName,timeStr);
		}
	}
	public static void insertValueMap(HashMap<String, VecStruct> revisionStrMap,String propertyName,String value) {
		if(value!=null){
			VecStruct vecStruct = new VecStruct();
			vecStruct.stringVec = new String[] { value };
			revisionStrMap.put(propertyName, vecStruct);
		}
	}
	
	/**
	 * 添加form数据
	 * @param dmService
	 * @param token
	 * @param docService
	 * @param docList
	 * @param itemRev
	 * @throws Exception
	 */
	public void addForm4Main(Session session,DataManagementService dmService,String token,DocumentService docService,
			List<Map<String,Object>> docList,ModelObject itemRev,RelationShip relationShip) throws Exception {

		Vector<String> formVec = new Vector<String>();
		for(int i=0;i<docList.size();i++) {
			Map<String,Object> row=docList.get(i);
			String formName=row.get("CODING").toString()+"_"+row.get("REVISION").toString();
			relationShip= Operator.OperationContractorSubData(token,session,dmService,docService,row,relationShip);
			Map<String,Object> rowData= relationShip.getData();
			Set<String> rowKeys=rowData.keySet();
			Iterator<String> rowIt= rowKeys.iterator();
			HashMap<String, String> formStrMap = new HashMap<String, String>();
			while(rowIt.hasNext()) {
				String key=rowIt.next();
				DataEntity dt= (DataEntity) rowData.get(key);
				if("projectId".equals(key)||dt==null||dt.getAttrValue()==null) {
					continue;
				}
				formStrMap.put(key, dt.getAttrValue().toString());
				
			}
//			formVec.add(createForm(dmService,"CN9FileItemF", formName, formStrMap).getUid());
			formVec.add(createForm(dmService,relationShip.getFormName(), formName, formStrMap).getUid());
		}
		//add end
		if(formVec.size()>0){
			HashMap<String, VecStruct> formStrMap = new HashMap<String, VecStruct>();
			
			String[] formUids = new String[formVec.size()];
			for(int i=0;i<formUids.length;i++){
				formUids[i] = formVec.get(i);
			}
			VecStruct formVecStruct = new VecStruct();
			formVecStruct.stringVec = formUids;
			
//			formStrMap.put("cn9Filelist", formVecStruct);
			formStrMap.put(relationShip.getFileListColumn(), formVecStruct);
			ServiceData serviceData = dmService.setProperties(
					new ModelObject[] { itemRev }, formStrMap);

			if (serviceData.sizeOfPartialErrors() != 0) {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}
				
				throw new Exception(ret);
			}
		}
		
	}
	
	/**
	 * 创建Form
	 * add by xiaolei 2014-10-22
	 * @param formType
	 *            form类型
	 * @param formName
	 *            form名称
	 * @param strMap
	 *            属性名、属性值组合map
	 * @return
	 * @throws Exception
	 */
	public Form createForm(DataManagementService dmService,String formType, String formName,
			HashMap<String, String> strMap) throws Exception {

		CreateIn[] ins = new CreateIn[1];
		ins[0] = new CreateIn();
		ins[0].clientId = "webservice";

		CreateInput input = new CreateInput();
		input.boName = formType;

		strMap.put("object_name", formName);

		input.stringProps = strMap;

		ins[0].data = input;

		CreateResponse response = dmService.createObjects(ins);

		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			// get the dataset
			Form form = (Form) response.output[0].objects[0];
			return form;
		} else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}
	}
	
	/**
	 * 为主文件添加电子件
	 * @param session
	 * @param dmService
	 * @param itemRev
	 * @param data
	 * @param cfb
	 * @throws Exception
	 */
	public void addFile4Main(Session session, DataManagementService dmService, ItemRevision itemRev,
			Map<String, Object> data,ConfBean cfb) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("--------------------------------------------------------------------------");
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			fileImportTypeList = prefs.getPreference("CNPE_File_Import_Type");
			
			if(fileImportTypeList==null){
				throw new Exception("CNPE_File_Import_Type首选项配置错误！");
			}
		}else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}
		String localDir=env.getProperty("ecm.localDir");
		
		String parentId=data.get("ID").toString();
		List<EcmContent> contentList=contentMapper.getContents(parentId, 1);
		
		if(contentList==null||contentList.size()==0) {
			return;
		}
		String datasetName=(data.get("CODING")!=null?data.get("CODING").toString():"")
				+(data.get("REVISION")==null?"":"_"+data.get("REVISION").toString());
				//data.get("TITLE")!=null?data.get("TITLE").toString():"";
		EcmContent en = contentList.get(0);
//		String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
//		
//		String localFileName=storePath + en.getFilePath();
		
		String localFileName=localDir+en.getId()+"."+en.getFormatName();
		
		String datasetType = "";
		String referenceName = "";
		for(int j=0;j<fileImportTypeList.size();j++){
			String[] ss = fileImportTypeList.get(j).split("#");
			
			if(ss.length != 3)
				continue;
			
			if(ss[0].equalsIgnoreCase(en.getFormatName()))
			{
				datasetType = ss[1].trim();
				referenceName = ss[2].trim();
				break;
			}
		}
		
		if(datasetType.equals("") || referenceName.equals("")){
			System.out.println(localFileName+"can't find dataset config in preference CNPE_File_Import_Type!");
			return;
		}
		//下载电子文件
			HttpTools.downloadFile(env, localDir,en.getId(),en.getFormatName(), 
					env.getProperty("ecm.baseUrl"));
		//
		
		createDataset(session,dmService,itemRev, datasetType, datasetName, referenceName, localFileName, "IMAN_specification");
		
	}
	/**
	 * 为子表添加附件
	 * @param token
	 * @param docService
	 * @param session
	 * @param data 子表数据
	 * @param dmService
	 * @param itemRev
	 * @param ship
	 * @throws Exception 
	 */
	public void addSubAttachement(String token,DocumentService docService,Session session,List<Map<String,Object>> data,
			DataManagementService dmService,RelationShip ship) throws Exception {
		for(int x=0;x<data.size();x++) {
			Map<String,Object> obj=data.get(x);
			String sql="select b.*,a.ORDER_INDEX from ecm_relation a,"
					+ "ecm_document b where a.CHILD_ID=b.id and b.TYPE_NAME='附件' "
					+ "and a.PARENT_ID='"+obj.get("ID").toString()+"' order by a.ORDER_INDEX";
			ship=Operator.OperationContractorSubData(token,session,dmService, docService, obj, ship);
			List<Map<String,Object>> attaches= docService.getMapList(token, sql);
			if(attaches!=null&&attaches.size()>0) {
				ItemRevision itemRevChild= queryItemRevision(session, ship);
				if(itemRevChild==null) {
					return;
				}
				addAttachements(token, docService, session, data,obj, dmService, itemRevChild, ship);
			}
			
			
		}
	}
	
	/**
	 * 添加附件
	 * @param token
	 * @param docService
	 * @param session
	 * @param data
	 * @param dmService
	 * @param mainData
	 * @param ship
	 * @throws Exception
	 */
	public void addAttachements(String token,DocumentService docService,Session session,List<Map<String,Object>> data,
			Map<String,Object> parentObj,
			DataManagementService dmService,ModelObject mainData,RelationShip ship) throws Exception {
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			fileImportTypeList = prefs.getPreference("CNPE_File_Import_Type");
			
			if(fileImportTypeList==null){
				throw new Exception("CNPE_File_Import_Type首选项配置错误！");
			}
		}else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}
		String localDir=env.getProperty("ecm.localDir");
		for(int a=0;a<data.size();a++) {
			Map<String,Object> doc=data.get(a);
			String parentId=doc.get("ID").toString();
			List<EcmContent> contentList=contentMapper.getContents(parentId, 1);
			if(contentList==null||contentList.size()==0) {
				continue;
			}
			String datasetName=parentObj.get("CODING").toString()
					+(parentObj.get("RIVISION")==null?"":"_"+parentObj.get("RIVISION").toString())
					+(doc.get("ORDER_INDEX")==null?"":"_"+doc.get("ORDER_INDEX").toString());//doc.get("Name").toString();
			EcmContent en=contentList.get(0);
			String localFileName=localDir+parentId+"."+en.getFormatName();
			String datasetType = "";
			String referenceName = "";
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(en.getFormatName()))
				{
					datasetType = ss[1].trim();
					referenceName = ss[2].trim();
					break;
				}
			}
			
			if(datasetType.equals("") || referenceName.equals("")){
				System.out.println(localFileName+"can't find dataset config in preference CNPE_File_Import_Type!");
				continue;
			}
			//下载电子文件
				HttpTools.downloadFile(env, localDir,parentId,en.getFormatName(), 
						env.getProperty("ecm.baseUrl"));
			//
//			createDataset(session,dmService,itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
				createDataset(session,dmService,mainData, datasetType, datasetName, referenceName, localFileName, ship.getName());
			
		}
		
		
	}
	
	
	/**
	 * 添加附件
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addChangeRqRevisionAttachements(Session session,DataManagementService dmService,
			ItemRevision itemRev,Map<String,Object> data,Map<String,Object> tcData,
			List<Map<String,Object>> docList) throws Exception {
		System.out.println("--------------------------------------------------------------------------");
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			fileImportTypeList = prefs.getPreference("CNPE_File_Import_Type");
			
			if(fileImportTypeList==null){
				throw new Exception("CNPE_File_Import_Type首选项配置错误！");
			}
		}else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}
		String localDir=env.getProperty("ecm.localDir");
		for(int docIndex=0;docIndex<docList.size();docIndex++) {
			Map<String,Object> doc=docList.get(docIndex);
			
			if("附件".equals(doc.get("TYPE_NAME").toString())) {
				String parentId=doc.get("ID").toString();
				List<EcmContent> contentList=contentMapper.getContents(parentId, 1);
				if(contentList==null||contentList.size()==0) {
					continue;
				}
				String datasetName=doc.get("Name").toString();
				EcmContent en=contentList.get(0);
				String localFileName=localDir+parentId+"."+en.getFormatName();
				String datasetType = "";
				String referenceName = "";
				for(int j=0;j<fileImportTypeList.size();j++){
					String[] ss = fileImportTypeList.get(j).split("#");
					
					if(ss.length != 3)
						continue;
					
					if(ss[0].equalsIgnoreCase(en.getFormatName()))
					{
						datasetType = ss[1].trim();
						referenceName = ss[2].trim();
						break;
					}
				}
				
				if(datasetType.equals("") || referenceName.equals("")){
					System.out.println(localFileName+"can't find dataset config in preference CNPE_File_Import_Type!");
					continue;
				}
				//下载电子文件
					HttpTools.downloadFile(env, localDir,en.getId(),en.getFormatName(), 
							env.getProperty("ecm.baseUrl"));
				//
				createDataset(session,dmService,itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
			}else {
				String sql="select a.* from ecm_document a,ecm_relation b where a.id=b.child_id and b.name='附件' and b.parent_id='"+doc.get("ID").toString()+"'";
				List<Map<String,Object>> fileList= documentService.getMapList(getEcmSession().getToken(), sql); 
				
				if(fileList!=null&&fileList.size()>0) {
					String datasetName= doc.get("CODING").toString() 
							+"_"+doc.get("REVISION").toString();
					for(int i=0;i<fileList.size();i++) {
						List<EcmContent> contentList = contentMapper.getContents(fileList.get(i).get("ID").toString(), 1);
						if(contentList==null||contentList.size()==0) {
							continue;
						}
						EcmContent en = contentList.get(0);
//						String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
//						
//						String localFileName=storePath + en.getFilePath();
						
						String localFileName=localDir+en.getId()+"."+en.getFormatName();
						
						String datasetType = "";
						String referenceName = "";
						for(int j=0;j<fileImportTypeList.size();j++){
							String[] ss = fileImportTypeList.get(j).split("#");
							
							if(ss.length != 3)
								continue;
							
							if(ss[0].equalsIgnoreCase(en.getFormatName()))
							{
								datasetType = ss[1].trim();
								referenceName = ss[2].trim();
								break;
							}
						}
						
						if(datasetType.equals("") || referenceName.equals("")){
							System.out.println(localFileName+"can't find dataset config in preference CNPE_File_Import_Type!");
							continue;
						}
						//下载电子文件
							HttpTools.downloadFile(env, localDir,en.getId(),en.getFormatName(), 
									env.getProperty("ecm.baseUrl"));
						//
						createDataset(session,dmService,itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
					}
				}
			}
		}
			
	}
	
	/**
	 * 
	 * @param object
	 *            需要在之下创建数据集的对象
	 * @param datasetType
	 *            数据集类型
	 * @param datasetName
	 *            数据集名称
	 * @param referenceName
	 *            数据集引用名称
	 * @param fullFileName
	 *            数据集文件全路径
	 * @param relationTypeName
	 *            创建的关系名称
	 * @return 创建之后的数据集对象
	 * @throws Exception
	 */
	public Dataset createDataset(Session session,DataManagementService dmService,
			ModelObject object, String datasetType,
			String datasetName, String referenceName, String fullFileName,
			String relationTypeName) throws Exception {
		System.out.println("datasetname:"+datasetName);
		System.out.println("datasettype:"+datasetType);
		System.out.println("referencename:"+referenceName);
		System.out.println("fullfilename:"+fullFileName);
		DatasetProperties2 props = new DatasetProperties2();
		props.clientId = "datasetClientId" + datasetName;
		props.type = datasetType;
		props.name = datasetName;
		props.description = "";
		props.container = object;
		props.relationType = relationTypeName;

		DatasetProperties2[] currProps = { props };

		// create a datset
		CreateDatasetsResponse response = dmService.createDatasets2(currProps);

		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			// get the dataset
			Dataset dataset = response.output[0].dataset;

			// create a file to associate with dataset
			DatasetFileInfo fileInfo = new DatasetFileInfo();
			DatasetFileInfo[] fileInfos = new DatasetFileInfo[1];

			// assume this file is in current dir
			// File file1 = new File("ReadMe.txt");

			fileInfo.clientId = "file_1";
			fileInfo.fileName = fullFileName;
			fileInfo.namedReferencedName = referenceName;
			fileInfo.isText = false;
			fileInfo.allowReplace = true;
			fileInfos[0] = fileInfo;

			GetDatasetWriteTicketsInputData inputData = new GetDatasetWriteTicketsInputData();
			inputData.dataset = dataset;
			inputData.createNewVersion = false;
			inputData.datasetFileInfos = fileInfos;
			System.err.println("------------------"+System.getProperty("java.library.path"));
			FileManagementUtility fMSFileManagement = null;
			try {
				fMSFileManagement = new FileManagementUtility(
						session.getConnection());
					
			} catch (Exception e) {
				e.printStackTrace();
			}

			GetDatasetWriteTicketsInputData[] inputs = new GetDatasetWriteTicketsInputData[1];
			inputs[0] = inputData;
			ServiceData resp = fMSFileManagement.putFiles(inputs);

			if (resp.sizeOfPartialErrors() > 0) {
				System.out
						.println("FileManagementService upload returned partial errors: "
								+ resp.sizeOfPartialErrors());
				String ret = "";
				String[] msgs = resp.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}
				// Close FMS connection since done
				fMSFileManagement.term();
				throw new Exception(ret);
			} else {
				// Close FMS connection since done
				fMSFileManagement.term();
				return dataset;
			}
		} else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}
			System.out.println(ret);
			throw new Exception(ret);
		}

	}
	
}
