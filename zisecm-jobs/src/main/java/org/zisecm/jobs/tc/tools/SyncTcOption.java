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
import org.zisecm.jobs.bean.conf.Operator;
import org.zisecm.jobs.entity.DataEntity;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.utils.DataManagement;
import org.zisecm.jobs.tc.utils.Query;
import org.zisecm.jobs.tc.utils.Workflow;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.PLMServerLOTImpl;
import org.zisecm.jobs.tc.ws.PLMServerLOT.ReturnVal;

import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemIdsAndInitialRevisionIds;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemProperties;
import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateIn;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateInput;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateResponse;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
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
	public IEcmSession getEcmSession() throws Exception {
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
		return ecmSession;
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
				.getService(SyncTcTools.getSession().getConnection());

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
	 * 创建文件传递单版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-22
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createFileRevision(String token,DocumentService docService,Map<String,Object> data,Map<String,Object> toTcData) throws Exception{
		String tcType=toTcData.get("tcTable").toString();
		
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
				.getService(SyncTcTools.getSession().getConnection());
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
		Map<String,Object> tcdt=(Map<String,Object>)toTcData.get("data");
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
		String sql="select a.* from ecm_document a,ecm_relation b where a.id=b.child_id and b.parent_id='"+data.get("ID").toString()+"'";
		List<Map<String,Object>> docList= documentService.getMapList(getEcmSession().getToken(), sql);
		
		//创建FileItemF Form并写入到cn9FileList属性中
//		FileList[] fileList = fileCRDataRequest.getFileList();
		if(docList!=null&&docList.size()>0) {
			
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				
				for(int i=0;i<docList.size();i++) {
					Map<String,Object> row=docList.get(i);
					String formName=row.get("CODING").toString()+"_"+row.get("REVISION").toString();
					Map<String,Object> afterMp= Operator.OperationContractorData(token,docService,row,"设计文件Form", "tc");
					Map<String,Object> rowData= (Map<String, Object>) afterMp.get("data");
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
					formVec.add(createForm(dmService,"CN9FileItemF", formName, formStrMap).getUid());
				}
				
				
			//add by xiaolei 20151105 begin	
			}catch (Exception e) {
				dmService.deleteObjects(new ModelObject[]{output.item}); 
				
				throw e;
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
				
				formStrMap.put("cn9Filelist", formVecStruct);
				
				serviceData = dmService.setProperties(
						new ModelObject[] { itemRev }, formStrMap);

				if (serviceData.sizeOfPartialErrors() != 0) {
					String ret = "";
					String[] msgs = serviceData.getPartialError(0).getMessages();

					for (int i = 0; i < msgs.length; i++) {
						ret = ret + msgs[i] + " ";
					}

					dmService.deleteObjects(new ModelObject[]{output.item}); //add by xiaolei 20151103
					
					throw new Exception(ret);
				}
			}
			
			
		
		}
				
		return output;
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
}
