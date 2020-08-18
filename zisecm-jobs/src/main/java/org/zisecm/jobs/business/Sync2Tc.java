package org.zisecm.jobs.business;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.core._2008_06.DataManagement.DatasetProperties2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.ws.PLMServerLOT.PLMServerLOTImpl;
import org.zisecm.jobs.tc.ws.PLMServerLOT.ReturnVal;

import com.teamcenter.services.loose.core._2006_03.FileManagement.DatasetFileInfo;
import com.teamcenter.services.loose.core._2006_03.FileManagement.GetDatasetWriteTicketsInputData;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.model.ModelObject;
import com.alibaba.druid.util.StringUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemIdsAndInitialRevisionIds;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateDatasetsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemProperties;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.Dataset;
import com.teamcenter.soa.client.model.strong.Item;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsResponse;
@Service
public class Sync2Tc {
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ExcSynDetailService synDetailService;
	@Autowired
	private AuthService authService;
	@Autowired
	private Environment env;
	@Autowired
	private ContentService contentService;
	
	@Scheduled(cron = "0/20 * * * * ?")
	public void run() {
		String sql="";

		String workflowSpecialUserName = env.getProperty("ecm.username");
		IEcmSession ecmSession = null;
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
			String condition=" status='已确认' and TYPE_NAME in('文件传递单','FU通知','作废通知','接口意见','接口传递'," + 
					"'CR澄清要求答复','FCR现场变更申请答复','NCR不符合项报告答复'," + 
					"'DCR设计变更申请答复','TCR试验澄清申请答复','设计变更通知DEN'," + 
					"'图文传真','会议纪要','设计审查意见') and SYN_STATUS is null";
			List<Map<String,Object>> data= documentService.getObjectMap(ecmSession.getToken(), condition);
			for(Map<String,Object> mp:data) {
				Map<String,Object> afterMp= Operator.OperationContractorData(mp, "tc");
				DataManagementService dms= getDmService();
				EcmContent en = null;
				en = contentService.getPrimaryContent(ecmSession.getToken(), mp.get("ID").toString());
				String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
				
				String tcId=createItem(dms, afterMp.get("tcTable").toString(), 
						(Map<String, Object>) afterMp.get("data"),fullPath+en.getFilePath());
				mp.put("SYN_ID", tcId);
				mp.put("SYN_STATUS", "已同步");
				EcmDocument doc=new EcmDocument();
				doc.setAttributes(mp);
				documentService.updateObject(ecmSession.getToken(), doc, null);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(ecmSession!=null) {
				
				authService.logout(ecmSession.getToken());
			}
		}
	
		
		
	}
	
	public static String createItem(DataManagementService dmService,String typeName,Map<String,Object> data,
			String filePath) throws Exception {
			
			ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds(typeName);
	
			CreateItemsOutput output = null;
			ItemProperties[] itemProps = new ItemProperties[1];
			
			ItemProperties itemProperty = new ItemProperties();
	
	        itemProperty.clientId = "webservice";
	        itemProperty.itemId = itemidAndRevId.newItemId;
	        itemProperty.revId = itemidAndRevId.newRevId;
	        //modify by xiaolei 20160620 for bug 2641
	        //itemProperty.name = itemidAndRevId.newItemId;
	        String object_name= data.get("k2_Coding").toString();
	        int length=object_name.length();
	        String str="";
	        if(length>120){
	        	str=object_name.substring(0,120);
	        	 itemProperty.name =str+"..";
	        }else{
	        	 itemProperty.name =object_name;
	        }
	        
	        //itemProperty.name =str+".."; 
	        itemProperty.type = typeName;
	        itemProperty.description = data.get("k2_title").toString();//object_desc data.get("description").toString();
	        itemProperty.uom = "";
	      
	        itemProps[0] = itemProperty;
			
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
			Item itemRev = output.item;
			
			HashMap<String, VecStruct> revisionStrMap=new HashMap<>();
			Set<String> keys=data.keySet();
			Iterator<String> it= keys.iterator();
			while(it.hasNext()) {
				String key=it.next();
				insertValueMap(revisionStrMap,key,data.get(key).toString());
			}
			
			serviceData = dmService.setProperties(
					new ModelObject[] { itemRev }, revisionStrMap);
//			createDataset(itemRev,"PDF", object_name, "pdf", filePath, "附件");
					
			return itemidAndRevId.newItemId;
			
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
	public static Dataset createDataset(ModelObject object, String datasetType,
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
	
	public static void insertValueMap(HashMap<String, VecStruct> revisionStrMap,String propertyName,String value) {
		if(value!=null){
			VecStruct vecStruct = new VecStruct();
			vecStruct.stringVec = new String[] { value };
			revisionStrMap.put(propertyName, vecStruct);
		}
	}
	public static ItemIdsAndInitialRevisionIds generateItemIds(String type) throws ServiceException {
		// Get the service stub
		DataManagementService dmService = DataManagementService
				.getService(Session.getConnection());
	
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

	private static DataManagementService dmService;
	private static Session session= null;
	public static DataManagementService getDmService() {
		if(dmService==null) {
			
			ReturnVal returnVal = new ReturnVal();
			   PLMServerLOTImpl plm=new PLMServerLOTImpl();
				if (session == null)
			    {
			      boolean flag = plm.loginTC();
			      if (!flag) {
			        returnVal.setReturnCode("INT_2");
			        returnVal.setReturnMessage("登陆失败");
			        //return returnVal;
			      }

			    }
				session=plm.session;
				dmService = 
				      DataManagementService.getService(session.getConnection());
		}
		
			return dmService;
	}
}
