package org.zisecm.jobs.tc.tools;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
import org.zisecm.jobs.bean.conf.ConfBean;
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.bean.conf.RelationShip;
import org.zisecm.jobs.bean.conf.SubTable;
import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.PLMServerLOTImpl;
import org.zisecm.jobs.tc.ws.PLMServerLOT.ReturnVal;
import org.zisecm.jobs.utils.HttpTools;

import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.entity.EcmContent;
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
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemIdsAndInitialRevisionIds;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemProperties;
import com.teamcenter.services.strong.core._2006_03.Session.PreferencesResponse;
import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateIn;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateInput;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateResponse;
import com.teamcenter.services.strong.core._2008_06.DataManagement.DatasetProperties2;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Preferences;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.Dataset;
import com.teamcenter.soa.client.model.strong.Form;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.User;
@Service
public class SyncTcOption {
	protected static Logger logger = Logger.getLogger(SyncTcOption.class);
	
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
	
	
	
	
	
	
	
	
	private void addChangeRqRevisionMain(Session session, DataManagementService dmService, ItemRevision itemRev,
			Map<String, Object> data, Map<String, Object> toTcData) throws Exception {
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
		String datasetName=data.get("TITLE")!=null?data.get("TITLE").toString():"";
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
