package org.zisecm.jobs.tc.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
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

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.services.loose.core._2006_03.FileManagement.DatasetFileInfo;
import com.teamcenter.services.loose.core._2006_03.FileManagement.GetDatasetWriteTicketsInputData;
import com.teamcenter.services.strong.administration.PreferenceManagementService;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateDatasetsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.services.strong.core._2006_03.Session.PreferencesResponse;
import com.teamcenter.services.strong.core._2008_06.DataManagement.DatasetProperties2;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Preferences;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.Dataset;
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
	public String setFileData(Map<String,Object> data,Map<String,Object> tcData) throws Exception {
			Map<String,Object> tcdt=(Map<String, Object>) tcData.get("data");
			Session session=SyncTcTools.getSession();
		    logger.info("-----------begin setCRData------------");

		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String start = df.format(new Date());
		    logger.info("-----------开始时间：" + start + "------------");
		    System.out.println("CR开始时间：" + start);

		    CreateItemsOutput createItemOutput = null;
		    DataManagementService dmService = 
		      DataManagementService.getService(Session.getConnection());
		    try
		    {
		      DataManagement dataManagement = new DataManagement(session);
		      Query query = new Query(session);
		      Workflow workflow = new Workflow(session);
		     
		      DataEntity dt=(DataEntity)tcdt.get("projectId");
		      String projectId=dt.getAttrValue().toString();
		      TC_Project project = query.queryProjectExist(projectId);
		      

		      ModelObject findChangeRqRevision = query.queryFileRevision(projectId,data.get("CODING").toString());
		      
		      if (findChangeRqRevision != null) {
		    	 throw new Exception("文件传递单数据在TC中已经存在符合条件的文件传递单：" + data.get("C_PROJECT_NAME").toString() + "|" +  data.get("CODING").toString() + "|" );
		      }

//		      createItemOutput = dataManagement.createFileRevision(fileDataRequest);
		      createItemOutput=tcOption.createFileRevision(getEcmSession().getToken(),documentService,data, tcData);
		      dmService.refreshObjects(new ModelObject[] { createItemOutput.item });

		      dataManagement.removeAndAssignProject(createItemOutput.item, project);
		      dataManagement.removeAndAssignProject(createItemOutput.itemRev, project);
		      
		      String sql="select a.* from ecm_document a,ecm_relation b where a.id=b.child_id and b.parent_id='"+data.get("ID").toString()+"'";
		      List<Map<String,Object>> docList= documentService.getMapList(getEcmSession().getToken(), sql);
		      
		      if (docList != null&&docList.size()>0) {
//		        dataManagement.addChangeRqRevisionAttachements(createItemOutput.itemRev, fileDataRequest.getAttachments());
		    	  addChangeRqRevisionAttachements(session, dmService, createItemOutput.itemRev, data,tcData,docList);
		      }

		      String docManagerUserId = dataManagement.getUserFromPref(projectId);
		      User user = null;
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

		      String processTemplateName = "LDM-001-收文流程";

		      if (!processTemplateName.equals(""))
		      {
		        workflow.createWorkflow(createItemOutput.itemRev, processTemplateName, user);
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
		    return createItemOutput.item.get_item_id();
	
	}
	/**
	 * 添加附件
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addChangeRqRevisionAttachements(Session session,DataManagementService dmService,
			ItemRevision itemRev,Map<String,Object> data,Map<String,Object> tcData,List<Map<String,Object>> docList) throws Exception {
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
		
		for(int docIndex=0;docIndex<docList.size();docIndex++) {
			Map<String,Object> doc=docList.get(docIndex);
			
			
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
					String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
					
					String localFileName=storePath + en.getFilePath();
					
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
					createDataset(session,dmService,itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
					
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
