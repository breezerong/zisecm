/**
 * 
 */
package org.zisecm.jobs.tc.utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.zisecm.jobs.tc.ws.PLMServerLOT.Ap1000IICSDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.Ap1000IITFAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.Ap1000IITFDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.CRAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.CRDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.CRFileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.DesignReviewFAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.DesignReviewFDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.DesignReviewFFileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FCRDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FUAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FUDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FUFileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FaxAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FaxDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.FileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.IICSDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.IITFAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.IITFDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.NCRAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.NCRDataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.NCRFileList;
import org.zisecm.jobs.tc.ws.PLMServerLOT.TAAttachment;
import org.zisecm.jobs.tc.ws.PLMServerLOT.TADataMessageBody;
import org.zisecm.jobs.tc.ws.PLMServerLOT.TAFileList;
import org.zisecm.jobs.tc.clientx.Session;
import com.teamcenter.schemas.soa._2006_03.exceptions.ServiceException;
import com.teamcenter.services.loose.core._2006_03.FileManagement.DatasetFileInfo;
import com.teamcenter.services.loose.core._2006_03.FileManagement.GetDatasetWriteTicketsInputData;
import com.teamcenter.services.strong.administration.PreferenceManagementService;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core.EnvelopeService;
import com.teamcenter.services.strong.core.ProjectLevelSecurityService;
import com.teamcenter.services.strong.core.SessionService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateDatasetsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsOutput;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateItemsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateRelationsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.GenerateItemIdsAndInitialRevisionIdsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemIdsAndInitialRevisionIds;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ItemProperties;
import com.teamcenter.services.strong.core._2006_03.DataManagement.ObjectOwner;
import com.teamcenter.services.strong.core._2006_03.DataManagement.Relationship;
import com.teamcenter.services.strong.core._2006_03.Session.PreferencesResponse;
import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsData;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsOutput;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsPref;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsResponse;
import com.teamcenter.services.strong.core._2007_06.DataManagement.RelationAndTypesFilter2;
import com.teamcenter.services.strong.core._2007_09.ProjectLevelSecurity.AssignedOrRemovedObjects;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateIn;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateInput;
import com.teamcenter.services.strong.core._2008_06.DataManagement.CreateResponse;
import com.teamcenter.services.strong.core._2008_06.DataManagement.DatasetProperties2;
import com.teamcenter.soa.client.FileManagementUtility;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.Preferences;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.client.model.strong.Dataset;
import com.teamcenter.soa.client.model.strong.Envelope;
import com.teamcenter.soa.client.model.strong.Form;
import com.teamcenter.soa.client.model.strong.Group;
import com.teamcenter.soa.client.model.strong.GroupMember;
import com.teamcenter.soa.client.model.strong.ImanFile;
import com.teamcenter.soa.client.model.strong.ItemRevision;
import com.teamcenter.soa.client.model.strong.TC_Project;
import com.teamcenter.soa.client.model.strong.User;

/**
 * @author xiaolei Creation Date 2013-6-4
 */
public class DataManagement {

	public DataManagementService dmService = null;

	Session session;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss+08:00");
	
	public DataManagement(Session session) {
		this.session = session;
		dmService = DataManagementService.getService(session.getConnection());
	}

	public boolean changeOwner(ModelObject object, ModelObject userObject)
			throws Exception {
		ModelObject group = null;
		ModelObject user = null;

		if (userObject instanceof GroupMember) {
			dmService.getProperties(new ModelObject[] { userObject },
					new String[] { "group", "user" });
			group = userObject.getPropertyObject("group").getModelObjectValue();
			user = userObject.getPropertyObject("user").getModelObjectValue();
		} else if (userObject instanceof User) {
			user = userObject;
			dmService.getProperties(new ModelObject[] { userObject },
					new String[] { "default_group" });
			group = user.getPropertyObject("default_group")
					.getModelObjectValue();
		} else {
			throw new Exception("输入参数不符合条件！");
		}

		ObjectOwner objectOwner = new ObjectOwner();
		objectOwner.object = object;
		objectOwner.owner = (User) user;
		objectOwner.group = (Group) group;

		ServiceData serviceData = dmService
				.changeOwnership(new ObjectOwner[] { objectOwner });

		if (serviceData.sizeOfPartialErrors() == 0)
			return true;
		else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}
	}

	public boolean changeOwner(ModelObject object, ModelObject userObject,
			ModelObject groupObject) throws Exception {
		ModelObject group = null;
		ModelObject user = null;

		if (userObject instanceof GroupMember) {
			dmService.getProperties(new ModelObject[] { userObject },
					new String[] { "group", "user" });
			group = userObject.getPropertyObject("group").getModelObjectValue();
			user = userObject.getPropertyObject("user").getModelObjectValue();
		} else if (userObject instanceof User) {
			user = userObject;
			group = groupObject;
		} else {
			throw new Exception("输入参数不符合条件！");
		}

		ObjectOwner objectOwner = new ObjectOwner();
		objectOwner.object = object;
		objectOwner.owner = (User) user;
		objectOwner.group = (Group) group;

		ServiceData serviceData = dmService
				.changeOwnership(new ObjectOwner[] { objectOwner });

		if (serviceData.sizeOfPartialErrors() == 0)
			return true;
		else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}
	}

	public String getProperty(ModelObject object, String propertyName) {
		try {
			dmService.refreshObjects(new ModelObject[]{object});
			
			dmService.getProperties(new ModelObject[] { object },
					new String[] { propertyName });
			String value = object.getPropertyObject(propertyName)
					.getStringValue();

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getIntProperty(ModelObject object, String propertyName) {
		try {
			dmService.refreshObjects(new ModelObject[]{object});
			
			dmService.getProperties(new ModelObject[] { object },
					new String[] { propertyName });
			String value = String.valueOf(object
					.getPropertyObject(propertyName).getIntValue());

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] getStringArrayProperty(ModelObject object,
			String propertyName) {
		try {
			dmService.getProperties(new ModelObject[] { object },
					new String[] { propertyName });
			String[] value = object.getPropertyObject(propertyName)
					.getStringArrayValue();

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ModelObject[] getModelObjectArrayValue(ModelObject object,
			String propertyName) {
		try {
			dmService.refreshObjects(new ModelObject[]{object});
			
			dmService.getProperties(new ModelObject[] { object },
					new String[] { propertyName });
			ModelObject[] value = object.getPropertyObject(propertyName)
					.getModelObjectArrayValue();

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean[] getBooleanArrayProperty(ModelObject object,
			String propertyName) {
		try {
			dmService.refreshObjects(new ModelObject[]{object});
			
			dmService.getProperties(new ModelObject[] { object },
					new String[] { propertyName });
			boolean[] value = object.getPropertyObject(propertyName)
					.getBoolArrayValue();

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public String setObjectArrayProperties(ModelObject object, String name,
			String[] value) {
		try {
			HashMap map = new HashMap();

			VecStruct vecStruct = new VecStruct();
			vecStruct.stringVec = value;
			map.put(name, vecStruct);

			ServiceData serviceData = dmService.setProperties(
					new ModelObject[] { object }, map);

			if (serviceData.sizeOfPartialErrors() == 0) {
				return "";
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@SuppressWarnings("unchecked")
	public String setObjectProperties(ModelObject object, String[] names,
			String[] values) throws Exception{
		try {
			HashMap map = new HashMap();

			for (int i = 0; i < names.length; i++) {
				VecStruct vecStruct = new VecStruct();
				vecStruct.stringVec = new String[] { values[i] };
				map.put(names[i], vecStruct);
			}

			ServiceData serviceData = dmService.setProperties(
					new ModelObject[] { object }, map);

			if (serviceData.sizeOfPartialErrors() == 0) {
				return "";
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//return e.getMessage();
			throw new Exception("写属性异常："+e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public String setObjectProperty(ModelObject object, String name,
			String value) {
		try {
			HashMap map = new HashMap();

			VecStruct vecStruct = new VecStruct();
			vecStruct.stringVec = new String[] { value };
			map.put(name, vecStruct);

			ServiceData serviceData = dmService.setProperties(
					new ModelObject[] { object }, map);

			if (serviceData.sizeOfPartialErrors() == 0) {
				return "";
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				return ret;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public ModelObject getLatestItemRevision(ModelObject object) {

		try {
			dmService.getProperties(new ModelObject[] { object },
					new String[] { "revision_list" });
			ModelObject[] revisions = object.getPropertyObject("revision_list")
					.getModelObjectArrayValue();

			if (revisions.length > 0) {
				return revisions[revisions.length - 1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ModelObject getLatestReleaseItemRevision(ModelObject object) {

		try {
			dmService.getProperties(new ModelObject[] { object },
					new String[] { "revision_list" });
			ModelObject[] revisions = object.getPropertyObject("revision_list")
					.getModelObjectArrayValue();

			for (int i = revisions.length - 1; i >= 0; i--) {
				ModelObject revision = revisions[i];
				dmService.getProperties(new ModelObject[] { revision },
						new String[] { "release_status_list" });
				ModelObject[] release_status_list = revision.getPropertyObject(
						"release_status_list").getModelObjectArrayValue();
				if (release_status_list != null)
					return revision;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ModelObject getMasterForm(ModelObject object) {

		ModelObject masterForm = null;

		try {
			ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
			pref.expItemRev = true;
			pref.info = new RelationAndTypesFilter2[1];
			pref.info[0] = new RelationAndTypesFilter2();
			pref.info[0].relationName = "IMAN_master_form";

			ExpandGRMRelationsResponse response = dmService
					.expandGRMRelationsForPrimary(new ModelObject[] { object },
							pref);

			ExpandGRMRelationsOutput[] outputs = response.output;

			if (outputs[0].otherSideObjData.length > 0) {
				ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

				if (data.otherSideObjects.length > 0) {
					masterForm = data.otherSideObjects[0];
				}
			}

			return masterForm;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
	public Dataset createDataset(ModelObject object, String datasetType,
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

	/**
	 * 根据关系名称，数据集类型，数据集名称查找对应对象下的数据集
	 * 
	 * @param object
	 * @param relationTypeName
	 *            关系名称
	 * @param datasetType
	 *            数据集类型
	 * @param datasetName
	 *            数据集名称
	 * @return 找到的数据集对象
	 * @throws Exception
	 */
	public ModelObject getDatasetByRelationAndTypeAndName(ModelObject object,
			String relationTypeName, String datasetType, String datasetName)
			throws Exception {
		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = relationTypeName;

		ExpandGRMRelationsResponse response = dmService
				.expandGRMRelationsForPrimary(new ModelObject[] { object },
						pref);

		if (response.serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = response.serviceData.getPartialError(0)
					.getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}

		ExpandGRMRelationsOutput[] outputs = response.output;

		ModelObject[] relationObjects = null;

		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				relationObjects = data.otherSideObjects;
				dmService.getProperties(relationObjects,
						new String[] { "object_name" });
				for (int i = 0; i < relationObjects.length; i++) {
					String objectType = relationObjects[i].getTypeObject()
							.getName();
					String objectName = relationObjects[i].getPropertyObject(
							"object_name").getStringValue();

					if (objectType.equals(datasetType)
							&& objectName.equals(datasetName)) {

						return relationObjects[i];
					}
				}
			}
		}

		return null;
	}

	/**
	 * 根据关系名称，数据集类型查找对应对象下的数据集
	 * 
	 * @param object
	 * @param relationTypeName
	 *            关系名称
	 * @param datasetType
	 *            数据集类型
	 * @return 找到的数据集对象数组
	 * @throws Exception
	 */
	public ModelObject[] getAllDatasetByRelationAndType(ModelObject object,
			String relationTypeName, String datasetType) throws Exception {
		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = relationTypeName;

		ExpandGRMRelationsResponse response = dmService
				.expandGRMRelationsForPrimary(new ModelObject[] { object },
						pref);

		if (response.serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = response.serviceData.getPartialError(0)
					.getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			throw new Exception(ret);
		}

		ArrayList<ModelObject> datasetList = new ArrayList<ModelObject>();

		ExpandGRMRelationsOutput[] outputs = response.output;

		ModelObject[] relationObjects = null;

		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				relationObjects = data.otherSideObjects;

				for (int i = 0; i < relationObjects.length; i++) {
					String objectType = relationObjects[i].getTypeObject()
							.getName();

					if (objectType.equals(datasetType)) {
						datasetList.add(relationObjects[i]);
					}
				}
			}
		}
		if (datasetList.size() == 0)
			return null;
		ModelObject[] outs = (ModelObject[]) datasetList
				.toArray(new ModelObject[datasetList.size()]);
		return outs;
	}

	/**
	 * 
	 * @param object
	 *            输入对象
	 * @return 正确时返回归属用户，异常返回null
	 */
	public User getObjectOwningUser(ModelObject object) {
		try {
			dmService.getProperties(new ModelObject[] { object },
					new String[] { "owning_user" });
			ModelObject user = object.getPropertyObject("owning_user")
					.getModelObjectValue();

			return (User) user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param object
	 *            输入对象
	 * @return 正确时返回归属用户名称，异常返回空
	 */
	public String getObjectOwningUserName(ModelObject object) {
		try {
			dmService.getProperties(new ModelObject[] { object },
					new String[] { "owning_user" });
			ModelObject user = object.getPropertyObject("owning_user")
					.getModelObjectValue();

			dmService.getProperties(new ModelObject[] { user },
					new String[] { "user_name" });
			return ((User) user).get_user_name();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 创建信封
	 * 
	 * @param users
	 *            发送用户对象
	 * @param subject
	 *            主题
	 * @param comments
	 *            正文
	 * @return 创建的信封对象
	 * @throws Exception
	 */
	public Envelope createEnvelope(User[] users, String subject, String comments)
			throws Exception {

		CreateIn[] ins = new CreateIn[1];
		ins[0] = new CreateIn();
		ins[0].clientId = "";

		CreateInput input = new CreateInput();
		input.boName = "Envelope";

		HashMap<String, String> strMap = new HashMap<String, String>();
		strMap.put("object_name", subject);
		strMap.put("object_desc", comments);

		input.stringProps = strMap;

		HashMap<String, User[]> tagArrayMap = new HashMap<String, User[]>();
		tagArrayMap.put("listOfReceivers", users);
		input.tagArrayProps = tagArrayMap;

		ins[0].data = input;

		CreateResponse response = dmService.createObjects(ins);

		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			// get the dataset
			Envelope envelope = (Envelope) response.output[0].objects[0];
			EnvelopeService envelopeService = EnvelopeService
					.getService(session.getConnection());
			envelopeService.sendAndDeleteEnvelopes(new Envelope[] { envelope });
			return envelope;
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
	 * 判断对象对应关系下是否存在内容
	 * 
	 * @param object
	 * @param relationTypeName
	 * @return 存在true，不存在false
	 */
	public boolean isExistObjects(ModelObject object, String relationTypeName) {
		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = relationTypeName;

		ExpandGRMRelationsResponse response = dmService
				.expandGRMRelationsForPrimary(new ModelObject[] { object },
						pref);

		if (response.serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = response.serviceData.getPartialError(0)
					.getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			return false;
		}

		ExpandGRMRelationsOutput[] outputs = response.output;

		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 得到对象下关联的所有对象
	 * 
	 * @param object
	 * @return
	 */
	public ModelObject[] getRelatedObjects(ModelObject object) {
		ExpandGRMRelationsPref pref = new ExpandGRMRelationsPref();
		pref.expItemRev = true;
		pref.info = new RelationAndTypesFilter2[1];
		pref.info[0] = new RelationAndTypesFilter2();
		pref.info[0].relationName = "IMAN_specification";

		ExpandGRMRelationsResponse response = dmService
				.expandGRMRelationsForPrimary(new ModelObject[] { object },
						pref);

		if (response.serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = response.serviceData.getPartialError(0)
					.getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println(ret);
			return null;
		}

		ExpandGRMRelationsOutput[] outputs = response.output;

		if (outputs[0].otherSideObjData.length > 0) {
			ExpandGRMRelationsData data = outputs[0].otherSideObjData[0];

			if (data.otherSideObjects.length > 0) {
				return data.otherSideObjects;
			}
		}

		return null;
	}

	/**
	 * 判断对象是否发布
	 * 
	 * @param object
	 * @return 已发布返回true，未发布或异常返回false
	 */
	public boolean isReleased(ModelObject object) {
		try {
			dmService.getProperties(new ModelObject[] { object },
					new String[] { "release_status_list" });
			ModelObject[] release_status_list = object.getPropertyObject(
					"release_status_list").getModelObjectArrayValue();

			if (release_status_list != null && release_status_list.length > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 用来判断UGMASTER或UGPART数据集下是否存在prt文件
	 * 
	 * @param dataset
	 * @return
	 */
	public boolean hasPartFile(ModelObject dataset) {
		try {
			dmService.getProperties(new ModelObject[] { dataset },
					new String[] { "ref_list", "object_name" });

			ModelObject[] imanfiles = dataset.getPropertyObject("ref_list")
					.getModelObjectArrayValue();
			if (imanfiles.length > 0) {
				for (int i = 0; i < imanfiles.length; i++) {
					ModelObject imanfile = imanfiles[i];
					if (!(imanfile instanceof ImanFile))
						continue;
					dmService.getProperties(new ModelObject[] { imanfile },
							new String[] { "file_ext" });

					String fileExt = imanfile.getPropertyObject("file_ext")
							.getStringValue();
					if (fileExt.toLowerCase().equals("prt"))
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
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
	public Form createForm(String formType, String formName,
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
	 * 
	 * @param type item type
	 * @return 
	 * @throws ServiceException
	 */
	public ItemIdsAndInitialRevisionIds generateItemIds(String type) throws ServiceException {
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
	
	/**
	 * 该方法不适用于itemrevision上存在继承的属性的情况
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public ModelObject createChangeRqRevision2(CRDataMessageBody setCRDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9ChangeRqRevision");

		CreateIn[] ins = new CreateIn[1];
		ins[0] = new CreateIn();
		ins[0].clientId = "webservice";

		CreateInput input = new CreateInput();
		input.boName = "CN9ChangeRq";

		HashMap<String, String> strMap = new HashMap<String, String>();
		
		strMap.put("item_id", itemidAndRevId.newItemId);
		strMap.put("object_name", itemidAndRevId.newItemId);

		input.stringProps = strMap;
		
		//HashMap<String, Vector<CreateInput>> compoundCreateInput = new HashMap<String, Vector<CreateInput>>();
		HashMap<String, CreateInput[]> compoundCreateInput = new HashMap<String, CreateInput[]>();
		//Vector<CreateInput> mapValueVec = new Vector<CreateInput>();
		CreateInput[] revisionInputs = new CreateInput[1];
		revisionInputs[0] = new CreateInput();
		
		CreateInput revisionInput = new CreateInput();
		revisionInput.boName = "CN9ChangeRqRevision";
		HashMap<String, String> revisionStrMap = new HashMap<String, String>();
		revisionStrMap.put("item_revision_id", itemidAndRevId.newRevId);
		revisionStrMap.put("cn9FileID", setCRDataRequest.getFileID());//编号
		revisionStrMap.put("cn9Rev", setCRDataRequest.getRevision());//版次
		revisionStrMap.put("cn9Theme", setCRDataRequest.getTheme());//名称
		revisionStrMap.put("cn9Discipline", setCRDataRequest.getDiscipline());//专业
		
		//revisionStrMap.put("", setCRDataRequest.getType());//CR类型
		//revisionStrMap.put("", setCRDataRequest.getFileCode());//相关文件
		
		revisionStrMap.put("cn9Unit", setCRDataRequest.getUnit());//机组
		revisionStrMap.put("cn9System", setCRDataRequest.getSystem());//系统
		revisionStrMap.put("cn9BuildingCode", setCRDataRequest.getBuildingCode());//厂房
		//revisionStrMap.put("", setCRDataRequest.getSubItem());//子项
		revisionStrMap.put("cn9Room", setCRDataRequest.getRoom());//房间
		//revisionStrMap.put("", setCRDataRequest.getIsReply());//是否需要设计院答复
		revisionStrMap.put("cn9AuthorUnit", setCRDataRequest.getAuthorUnit());//发布单位
		
		revisionInput.stringProps = revisionStrMap;
		
		if(!setCRDataRequest.getSendDate().equals("")){
			HashMap<String, Date> revisionDateMap = new HashMap<String, Date>();
			revisionDateMap.put("cn9SendDate", dateFormat.parse(setCRDataRequest.getSendDate()));//发布日期
			
			revisionInput.dateProps = revisionDateMap;
		}
		
		//创建FileItemF Form并写入到cn9FileList属性中
		CRFileList[] fileList = setCRDataRequest.getCrList();
		if(fileList!=null && fileList.length>0){
			Vector<ModelObject> formVec = new Vector<ModelObject>();
			for(int i=0;i<fileList.length;i++){
				String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
				HashMap<String, String> formStrMap = new HashMap<String, String>();
				formStrMap.put("cn9SequenceNo", (i+1)+"");
				formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
				formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
				formStrMap.put("cn9Rev", fileList[i].getFileRev());
				formStrMap.put("cn9States", fileList[i].getFileExeStatus());
				
				formVec.add(createForm("CN9FileItemF", formName, formStrMap));
			}
		
			if(formVec.size()>0){
				HashMap<String, Vector<ModelObject>> revisionTagVectorMap = new HashMap<String, Vector<ModelObject>>();
				revisionTagVectorMap.put("cn9FileList", formVec);//相关文件清单
				
				revisionInput.tagArrayProps = revisionTagVectorMap;
			}
		}
		
		revisionInputs[0] = revisionInput;
		//mapValueVec.add(revisionInput);
		
		compoundCreateInput.put("revision", revisionInputs);
		
		input.compoundCreateInput = compoundCreateInput;

		ins[0].data = input;

		CreateResponse response = dmService.createObjects(ins);
		
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			ModelObject item = (ModelObject) response.output[0].objects[0];
			return item;
		} else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			throw new Exception(ret);
		}
	}
	
	public void insertValueMap(HashMap<String, VecStruct> revisionStrMap,String propertyName,String value) {
		if(value!=null){
			VecStruct vecStruct = new VecStruct();
			vecStruct.stringVec = new String[] { value };
			revisionStrMap.put(propertyName, vecStruct);
		}
	}

	public void insertDateValueMap(HashMap<String, VecStruct> revisionStrMap,String propertyName,String value)throws Exception {
		if(value!=null && !value.equals("")){
			Date time = dateFormat.parse(value);
			String timeStr = dateFormat2.format(time)+"T"+dateFormat3.format(time);
			insertValueMap(revisionStrMap,propertyName,timeStr);
		}
	}
	/**
	 * 创建图文传真版本
	 */
	public CreateItemsOutput createFaxRevision(FaxDataMessageBody faxDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9FaxRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= faxDataRequest.getTheme();
        int length=object_name.length();
        String str="";
        if(length>90){
        	str=object_name.substring(0,90);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
        //itemProperty.name =str+".."; 
        itemProperty.type = "CN9Fax";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		String  faxtype="";
		if("00".equals(faxDataRequest.getFaxType())){
			faxtype="一般信函";
		}
		insertValueMap(revisionStrMap,"cn9FaxType",faxtype);//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9LetterSendNo",faxDataRequest.getLetterSendNo());//发文编号
		
		insertValueMap(revisionStrMap,"cn9RecivUnit",faxDataRequest.getRecivUnit());//主送
		
		
		insertValueMap(revisionStrMap,"cn9Pages",faxDataRequest.getPages());//页数
		
		insertValueMap(revisionStrMap,"cn9CorrespLetterRecNo",faxDataRequest.getCorrespLetterRecNo());//回文号
		
		
		insertValueMap(revisionStrMap,"cn9Theme",faxDataRequest.getTheme());//主题
		
		insertValueMap(revisionStrMap,"cn9Content",faxDataRequest.getContent());//内容
		
		
		insertValueMap(revisionStrMap,"cn9NeedReply",faxDataRequest.getNeedReply());//是否需要回文
		
		if("是".equals(faxDataRequest.getNeedReply())){
			insertDateValueMap(revisionStrMap,"cn9RequireReplyDate",faxDataRequest.getSendRecvLettDate());//收发文日期
		}
		//modify end
		insertValueMap(revisionStrMap,"cn9AuthorUnit",faxDataRequest.getAuthorUnit());//发布单位  发自
		
		
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",faxDataRequest.getSendRecvLettDate());//收发文日期
		insertValueMap(revisionStrMap,"cn9CCUnit",faxDataRequest.getCcUnit());//发布单位  发自
		
		
		
		//add end
		
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
		
				
		return output;
	}
	
	public CreateItemsOutput createDVMIEDRevision(FileList fileList) throws Exception{
		
		
		ItemIdsAndInitialRevisionIds dvmitemidAndRevId = generateItemIds("CN9DVMIEDRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = fileList.getFileExtCode();
        itemProperty.revId = fileList.getFileRev();
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
      
        itemProperty.name =fileList.getFileName();
       
        
        //itemProperty.name =str+".."; 
        itemProperty.type = "CN9DVMIED";
        itemProperty.description = "";
        itemProperty.uom = "";
      
        itemProps[0] = itemProperty;
		
        CreateItemsResponse response = dmService.createItems(itemProps, null, "");
		
		/*
		
		//ItemIdsAndInitialRevisionIds itemidAndRevIdDVM = generateItemIds("CN9DVMIEDRevision");
		CreateItemsOutput output1 = null;
		//CreateItemsOutput output = null;
		ItemProperties[] itemPropsDVM = new ItemProperties[1];
		
		ItemProperties itemPropertyDVM = new ItemProperties();

		itemPropertyDVM.clientId = "webservice";
		itemPropertyDVM.itemId = fileList[i].getFileExtCode();
		itemPropertyDVM.revId = fileList[i].getFileRev();
		itemPropertyDVM.name=fileList[i].getFileName();
		
		itemPropertyDVM.type = "CN9DVMIED";
		itemPropertyDVM.description = "";
		itemPropertyDVM.uom = "";
      
        itemPropsDVM[0] = itemPropertyDVM;
		
        CreateItemsResponse responseDVM = dmService.createItems(itemProps, null, "");
        */
        ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			output =  response.output[0];
		} else {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int j = 0; j < msgs.length; j++) {
				ret = ret + msgs[j] + " ";
			}

			throw new Exception(ret);
		}
        
		ItemRevision itemRevDVM = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMapDVM = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMapDVM,"item_revision_id",fileList.getFileRev());
		//insertValueMap(revisionStrMapDVM,"cn9ExternalCode",fileList[i].getFileIntCode());
		insertValueMap(revisionStrMapDVM,"cn9DrawingStatus",fileList.getFileExeStatus());
	
		serviceData = dmService.setProperties(
				new ModelObject[] { itemRevDVM }, revisionStrMapDVM);

		if (serviceData.sizeOfPartialErrors() != 0) {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int k = 0; k < msgs.length; k++) {
				ret = ret + msgs[k] + " ";
			}

			dmService.deleteObjects(new ModelObject[]{output.item}); //add by xiaolei 20151103
			
			throw new Exception(ret);
		}
		
		return output;
		
	}
	
	
	/**
	 * 创建文件传递单版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-22
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createFileRevision(FileDataMessageBody fileCRDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9FileTFRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= fileCRDataRequest.getName();
        int length=object_name.length();
        String str="";
        if(length>120){
        	str=object_name.substring(0,120);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
        //itemProperty.name =str+".."; 
        itemProperty.type = "CN9FileTF";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","文件传递单");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9LetterSendNo",fileCRDataRequest.getLetterSendNo());//发文编号
		insertValueMap(revisionStrMap,"cn9Rev",fileCRDataRequest.getRevision());//版次
		
		insertValueMap(revisionStrMap,"cn9RecivUnit",fileCRDataRequest.getRecivUnit());//主送
		
		insertValueMap(revisionStrMap,"cn9Pages",fileCRDataRequest.getPage());//页数
		
		String sendNo[]=fileCRDataRequest.getLetterSendNo().split("-");
		
		insertValueMap(revisionStrMap,"cn9SenderCh",sendNo[0]);//发送方
		
		insertValueMap(revisionStrMap,"cn9ReceiverCh",sendNo[2]);//接受方
		
		insertValueMap(revisionStrMap,"cn9ChNum",sendNo[1]);//渠道号
		
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getType());//CR类型
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getFileCode());//相关文件
		
		//insertValueMap(revisionStrMap,"cn9Unit",setCRDataRequest.getUnit());//机组
		//insertValueMap(revisionStrMap,"cn9System",setCRDataRequest.getSystem());//系统
		//insertValueMap(revisionStrMap,"cn9RoomCode",setCRDataRequest.getRoom());//房间
		
		
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getSubItem());//子项
		
		//modify by xiaolei 20151105 for bug 2385 begin
		//insertValueMap(revisionStrMap,"cn9BuildingCode",setCRDataRequest.getBuilding());//厂房
		
		
		//modify end
		insertValueMap(revisionStrMap,"cn9AuthorUnit",fileCRDataRequest.getAuthorUnit());//发布单位  发自
		
		
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",fileCRDataRequest.getShouDate());//收发文日期
		
		insertDateValueMap(revisionStrMap,"cn9ReplayDeadline",fileCRDataRequest.getReplayDeadLine());//审查日期
		
		//add end
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		FileList[] fileList = fileCRDataRequest.getFileList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					if(fileList[i].getFileName()!=null) formStrMap.put("cn9FileName", fileList[i].getFileName());
					if(fileList[i].getPage()!=null) formStrMap.put("cn9Page", fileList[i].getPage());
					if(fileList[i].getPaper()!=null) formStrMap.put("cn9Paper", fileList[i].getPaper());
					if(fileList[i].getDigital()!=null) formStrMap.put("cn9Digital", fileList[i].getDigital());
					if(fileList[i].getBlue()!=null) formStrMap.put("cn9Blue", fileList[i].getBlue());
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
					/*
					Query query=new Query(session);
					ModelObject modelobejct=	query.queryDVMRevision(fileList[i].getFileExtCode(), fileList[i].getFileRev());
					
					if(modelobejct == null){
						createDVMIEDRevision(fileList[i]);
					}
				*/
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
	 * 创建CR版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-22
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createChangeRqRevision(CRDataMessageBody setCRDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9ChangeRqRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= setCRDataRequest.getTheme();
        int length=object_name.length();
        String str="";
        if(length>43){
        	str=object_name.substring(0,43);
        	itemProperty.name =str+"..";
        }else{
        	itemProperty.name =object_name;
        }
        
        //itemProperty.name =str+".."; 
        itemProperty.type = "CN9ChangeRq";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		String fileid="";
		if(!"".equals(setCRDataRequest.getFileID()) || setCRDataRequest.getFileID()!=null ){
			fileid=setCRDataRequest.getFileID().substring(0,2);
			fileid=fileid+"-"+setCRDataRequest.getFileID().substring(2);
		}
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","CR单及答复单");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9FileID",fileid);//编号
		insertValueMap(revisionStrMap,"cn9Rev",setCRDataRequest.getRevision());//版次
		insertValueMap(revisionStrMap,"cn9Theme",setCRDataRequest.getTheme());//名称
		insertValueMap(revisionStrMap,"cn9Discipline",setCRDataRequest.getDiscipline());//专业
		
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getType());//CR类型
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getFileCode());//相关文件
		
		insertValueMap(revisionStrMap,"cn9Unit",setCRDataRequest.getUnit());//机组
		insertValueMap(revisionStrMap,"cn9System",setCRDataRequest.getSystem());//系统
		insertValueMap(revisionStrMap,"cn9RoomCode",setCRDataRequest.getRoom());//房间
		
		//insertValueMap(revisionStrMap,"cn9RoomCode",setCRDataRequest.getRoom());//房间
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getSubItem());//子项
		
		//modify by xiaolei 20151105 for bug 2385 begin
		//insertValueMap(revisionStrMap,"cn9BuildingCode",setCRDataRequest.getBuilding());//厂房
		String building = setCRDataRequest.getBuildingCode();
		if(building==null || building.trim().equals("")){
			building = setCRDataRequest.getSubItem();
		}
		insertValueMap(revisionStrMap,"cn9BuildingCode",building);//厂房
		//modify end
		insertValueMap(revisionStrMap,"cn9IsReply",setCRDataRequest.getIsReply());//是否需要设计院答复
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setCRDataRequest.getAuthorUnit());//发布单位
		
		//add by xiaolei 20151111 for bug 2397 begin
		insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn9CorrespLetterRecNo",setCRDataRequest.getCorrespLetterRecNo());//来文
		insertDateValueMap(revisionStrMap,"cn9SendDate",setCRDataRequest.getSendDate());//发布日期
		
		//add by xiaolei 20160223 for bug 2562 begin
		insertValueMap(revisionStrMap,"cn9Reason",setCRDataRequest.getReason());//变更原因及描述
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		insertValueMap(revisionStrMap,"cn9DevNum",setCRDataRequest.getDevNum());//设备位号
		//add end
		insertValueMap(revisionStrMap,"cn9Pages",setCRDataRequest.getPages());//设备位号
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		CRFileList[] fileList = setCRDataRequest.getCrList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
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
	 * 创建TA版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-22
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createTARevision(TADataMessageBody setCRDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9TARevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= setCRDataRequest.getTheme();
        int length=object_name.length();
        String str="";
        if(length>43){
        	str=object_name.substring(0,43);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
       // itemProperty.name =str+".."; 
        itemProperty.type = "CN9TA";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		String fileid="";
		if(!"".equals(setCRDataRequest.getFileID()) || setCRDataRequest.getFileID()!=null ){
			fileid=setCRDataRequest.getFileID().substring(0,2);
			fileid=fileid+"-"+setCRDataRequest.getFileID().substring(2);
		}
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","TA单及答复单");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9FileID",fileid);//编号
		insertValueMap(revisionStrMap,"cn9Rev",setCRDataRequest.getRevision());//版次
		insertValueMap(revisionStrMap,"cn9Theme",setCRDataRequest.getTheme());//名称
		insertValueMap(revisionStrMap,"cn9Discipline",setCRDataRequest.getDiscipline());//专业
		
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getType());//CR类型
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getFileCode());//相关文件
		
		insertValueMap(revisionStrMap,"cn9Unit",setCRDataRequest.getUnit());//机组
		insertValueMap(revisionStrMap,"cn9System",setCRDataRequest.getSystem());//系统
		insertValueMap(revisionStrMap,"cn9RoomCode",setCRDataRequest.getRoom());//房间
		
		//insertValueMap(revisionStrMap,"cn9RoomCode",setCRDataRequest.getRoom());//房间
		//insertValueMap(revisionStrMap,"",setCRDataRequest.getSubItem());//子项
		
		//modify by xiaolei 20151105 for bug 2385 begin
		//insertValueMap(revisionStrMap,"cn9BuildingCode",setCRDataRequest.getBuilding());//厂房
		String building = setCRDataRequest.getBuildingCode();
		if(building==null || building.trim().equals("")){
			building = setCRDataRequest.getSubItem();
		}
		insertValueMap(revisionStrMap,"cn9BuildingCode",building);//厂房
		//modify end
		//insertValueMap(revisionStrMap,"cn9IsReply",setCRDataRequest.getIsReply());//是否需要设计院答复
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setCRDataRequest.getAuthorUnit());//发布单位
		
		//add by xiaolei 20151111 for bug 2397 begin
		insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn9CorrespLetterRecNo",setCRDataRequest.getCorrespLetterRecNo());//来文
		insertDateValueMap(revisionStrMap,"cn9SendDate",setCRDataRequest.getSendDate());//发布日期
		
		//add by xiaolei 20160223 for bug 2562 begin
		insertValueMap(revisionStrMap,"cn9Reason",setCRDataRequest.getReason());//变更原因及描述
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		insertValueMap(revisionStrMap,"cn9DevNum",setCRDataRequest.getDevNum());//设备位号
		//add end
		insertValueMap(revisionStrMap,"cn9Pages",setCRDataRequest.getPages());//设备位号
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		TAFileList[] fileList = setCRDataRequest.getTaList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
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
	 * 设计审查意见单
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	
	public CreateItemsOutput createDesignReviewFRevision(DesignReviewFDataMessageBody setDesignReviewFDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9DesignRevAnsFRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= setDesignReviewFDataRequest.getName();
        int length=object_name.length();
        String str="";
        if(length>90){
        	str=object_name.substring(0,90);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
       // itemProperty.name =str+".."; 
        itemProperty.type = "CN9DesignRevAnsF";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","设计审查意见答复单");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9LetterSendNo",setDesignReviewFDataRequest.getLetterSendNo());//编号
		insertValueMap(revisionStrMap,"cn9Rev",setDesignReviewFDataRequest.getRevision());//版次
		insertValueMap(revisionStrMap,"cn9Theme",setDesignReviewFDataRequest.getName());//名称
		insertValueMap(revisionStrMap,"cn9RecivUnit",setDesignReviewFDataRequest.getRecivUnit());
		
		insertValueMap(revisionStrMap,"cn9Pages",setDesignReviewFDataRequest.getPages());
		
		insertValueMap(revisionStrMap,"cn9Comment",setDesignReviewFDataRequest.getComment());
		
		insertValueMap(revisionStrMap,"cn9CCUnit",setDesignReviewFDataRequest.getCcUnit());
		//modify end
		//insertValueMap(revisionStrMap,"cn9IsReply",setCRDataRequest.getIsReply());//是否需要设计院答复
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setDesignReviewFDataRequest.getAuthorUnit());//发布单位
		
		//add by xiaolei 20151111 for bug 2397 begin
		//insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		//insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn9CorrespLetterRecNo",setDesignReviewFDataRequest.getCorrespLetterRecNo());//来文
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",setDesignReviewFDataRequest.getSendRecvLettDate());//发布日期
		
		insertDateValueMap(revisionStrMap,"cn9DRCSRecDate",setDesignReviewFDataRequest.getdRCSRecDate());
		
		//add by xiaolei 20160223 for bug 2562 begin
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		//add end
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		DesignReviewFFileList[] fileList = setDesignReviewFDataRequest.getDesignrfList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					if(fileList[i].getFileName()!=null) formStrMap.put("cn9FileName", fileList[i].getFileName());
					if(fileList[i].getResult()!=null) formStrMap.put("cn9ReplyConclusion", fileList[i].getResult());
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
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
	 * IITF
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	
	public CreateItemsOutput createIITFRevision(IITFDataMessageBody setIITFDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9IITFRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = "00";
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= "接口信息传递单";
       
        
        itemProperty.name =object_name; 
        itemProperty.type = "CN9IITF";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id","00");
		//insertValueMap(revisionStrMap,"cn9LetterType","接口信息传递单(IITFACP1000)版本");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9OurDocNum",setIITFDataRequest.getOurDocNum());//编号
		insertValueMap(revisionStrMap,"cn9RevisionID",setIITFDataRequest.getRevisionID());//版次
		//insertValueMap(revisionStrMap,"cn9Theme",setDesignReviewFDataRequest.getName());//名称
		insertValueMap(revisionStrMap,"cn9SendTo",setIITFDataRequest.getSendTo());
		
		insertValueMap(revisionStrMap,"cn9SendFrom",setIITFDataRequest.getSendFrom());
		
		insertValueMap(revisionStrMap,"cn9Pages",setIITFDataRequest.getPages());
		
		String finOrPre=setIITFDataRequest.getPreOrFin();
		if("FIN".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","是");
			insertValueMap(revisionStrMap,"cn9PREExchange","否");
		}else if("PRE".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","否");
			insertValueMap(revisionStrMap,"cn9PREExchange","是");
		}
		
		insertValueMap(revisionStrMap,"cn9Displ",setIITFDataRequest.getDispl());//发布单位
		
		insertValueMap(revisionStrMap,"cn91stParty",setIITFDataRequest.getOneParty());
		//add by xiaolei 20151111 for bug 2397 begin
		//insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		//insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn92ndParty",setIITFDataRequest.getTwoParty());//来文
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",setIITFDataRequest.getSendRecvLettDate());//发布日期
		
		insertValueMap(revisionStrMap,"cn9IntfcType",setIITFDataRequest.getIntfcType());//来文
		
		insertValueMap(revisionStrMap,"cn9SerNum",setIITFDataRequest.getSerNum());
		
		insertValueMap(revisionStrMap,"cn9ReleaseParty",setIITFDataRequest.getReleaseParty());
		
		insertValueMap(revisionStrMap,"cn9RecvParty",setIITFDataRequest.getRecvParty());
		
		insertDateValueMap(revisionStrMap,"cn9ICMDate",setIITFDataRequest.getIcmDate());
		
		
		insertValueMap(revisionStrMap,"cn9IntfcDetailDescr",setIITFDataRequest.getIntfcDetailDescr());
		
		insertValueMap(revisionStrMap,"cn9IntfcInfo",setIITFDataRequest.getIntfcInfo());
		
		insertValueMap(revisionStrMap,"cn9IfSendCD",setIITFDataRequest.getIfSendCD());
		
		
		
		
		//add by xiaolei 20160223 for bug 2562 begin
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		//add end
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		
				
		return output;
	}
	
	
	/**
	 * IITF
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	
	public CreateItemsOutput createAp1000IITFRevision(Ap1000IITFDataMessageBody setIITFDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9IITFAP1000Revision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
       // itemProperty.revId = itemidAndRevId.newRevId;
        itemProperty.revId = "00";
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= "IITF";
       
        
        itemProperty.name =object_name; 
        itemProperty.type = "CN9IITFAP1000";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id","00");
		//insertValueMap(revisionStrMap,"cn9LetterType","接口信息传递单(IITFAP1000)版本");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9OurDocNum",setIITFDataRequest.getOurDocNum());//编号
		insertValueMap(revisionStrMap,"cn9RevisionID",setIITFDataRequest.getRevisionID());//版次
		insertValueMap(revisionStrMap,"cn9RevDocNo",setIITFDataRequest.getRevDocNo());//
		insertValueMap(revisionStrMap,"cn9ExtCc",setIITFDataRequest.getExtCc());//
		
		insertValueMap(revisionStrMap,"cn9IntfcIdent",setIITFDataRequest.getIntfcIdent());//
		
		insertValueMap(revisionStrMap,"cn9SendTo",setIITFDataRequest.getSendTo());
		
		insertValueMap(revisionStrMap,"cn9SendFrom",setIITFDataRequest.getSendFrom());
		
		insertValueMap(revisionStrMap,"cn9Pages",setIITFDataRequest.getPages());
		
		String finOrPre=setIITFDataRequest.getPreOrFin();
		if("FIN".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","是");
			insertValueMap(revisionStrMap,"cn9PREExchange","否");
		}else if("PRE".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","否");
			insertValueMap(revisionStrMap,"cn9PREExchange","是");
		}
		
		
		insertValueMap(revisionStrMap,"cn91stParty",setIITFDataRequest.getOneParty());
		//add by xiaolei 20151111 for bug 2397 begin
		//insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		//insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn92ndParty",setIITFDataRequest.getTwoParty());//来文
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",setIITFDataRequest.getSendRecvLettDate());//发布日期
		
		//insertValueMap(revisionStrMap,"cn9IntfcType",setIITFDataRequest.getIntfcType());//来文
		
		//insertValueMap(revisionStrMap,"cn9SerNum",setIITFDataRequest.getSerNum());
		
		insertValueMap(revisionStrMap,"cn9ReleaseParty",setIITFDataRequest.getReleaseParty());
		
		insertValueMap(revisionStrMap,"cn9RecvParty",setIITFDataRequest.getRecvParty());
		
		insertDateValueMap(revisionStrMap,"cn9ICMDate",setIITFDataRequest.getIcmDate());
		
		
		insertValueMap(revisionStrMap,"cn9IntfcDetailDescr",setIITFDataRequest.getIntfcDetailDescr());
		
		insertValueMap(revisionStrMap,"cn9IntfcInfo",setIITFDataRequest.getIntfcInfo());
		
		insertValueMap(revisionStrMap,"cn9IntCC",setIITFDataRequest.getIntCC());
		
		
		
		
		//add by xiaolei 20160223 for bug 2562 begin
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		//add end
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		
				
		return output;
	}
	
	
	/**
	 * IICS
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	
	public CreateItemsOutput createIICSRevision(IICSDataMessageBody setIICSDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9IICSRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        //itemProperty.revId = itemidAndRevId.newRevId;
        itemProperty.revId = "00";
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= "接口信息意见单";
       
        
        itemProperty.name =object_name; 
        itemProperty.type = "CN9IICS";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id","00");
		//insertValueMap(revisionStrMap,"cn9LetterType","接口信息传递单(IITFACP1000)版本");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9OurDocNum",setIICSDataRequest.getOurDocNum());//编号
		insertValueMap(revisionStrMap,"cn9Ver",setIICSDataRequest.getRevisionID());//版次
		//insertValueMap(revisionStrMap,"cn9Theme",setDesignReviewFDataRequest.getName());//名称
		insertValueMap(revisionStrMap,"cn9SendTo",setIICSDataRequest.getSendTo());
		
		insertValueMap(revisionStrMap,"cn9SendFrom",setIICSDataRequest.getSendFrom());
		
		insertValueMap(revisionStrMap,"cn9Pages",setIICSDataRequest.getPages());
		
		String finOrPre=setIICSDataRequest.getPreOrFin();
		if("FIN".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","是");
			insertValueMap(revisionStrMap,"cn9PREExchange","否");
		}else if("PRE".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","否");
			insertValueMap(revisionStrMap,"cn9PREExchange","是");
		}
		
		insertValueMap(revisionStrMap,"cn9Displ",setIICSDataRequest.getDispl());//发布单位
		
		insertValueMap(revisionStrMap,"cn91stParty",setIICSDataRequest.getOneParty());
		//add by xiaolei 20151111 for bug 2397 begin
		//insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		//insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn92ndParty",setIICSDataRequest.getTwoParty());//来文
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",setIICSDataRequest.getSendRecvLettDate());//发布日期
		
		insertValueMap(revisionStrMap,"cn9IntfcType",setIICSDataRequest.getIntfcType());//来文
		
		insertValueMap(revisionStrMap,"cn9SerNum",setIICSDataRequest.getSerNum());
		
		insertValueMap(revisionStrMap,"cn9ReleaseParty",setIICSDataRequest.getReleaseParty());
		
		insertValueMap(revisionStrMap,"cn9RecvParty",setIICSDataRequest.getRecvParty());
		
		insertDateValueMap(revisionStrMap,"cn9ICMDate",setIICSDataRequest.getIcmDate());
		
		
		insertValueMap(revisionStrMap,"cn9IntfcDetailDescr",setIICSDataRequest.getIntfcDetailDescr());
		
		insertValueMap(revisionStrMap,"cn9IntfcInfo",setIICSDataRequest.getIntfcInfo());
		
		insertValueMap(revisionStrMap,"cn9CpartDocNum",setIICSDataRequest.getCpartDocNum());
		
		insertDateValueMap(revisionStrMap,"cn9IITFSendDocDate",setIICSDataRequest.getiITFSendDocDate());
		//insertValueMap(revisionStrMap,"cn9IfSendCD",setIITFDataRequest.getIfSendCD());
		
		insertValueMap(revisionStrMap,"cn9ShenCConclu",setIICSDataRequest.getShenCConclu());
		
		insertValueMap(revisionStrMap,"cn9ShenCSugg",setIICSDataRequest.getShenCSugg());
		
		
		
		
		//add by xiaolei 20160223 for bug 2562 begin
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		//add end
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		
				
		return output;
	}
	
	/**
	 * IICS
	 * @param setCRDataRequest
	 * @return
	 * @throws Exception
	 */
	
	public CreateItemsOutput createIICSRevision(Ap1000IICSDataMessageBody setIICSDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9IICSAP1000Revision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
       // itemProperty.revId = itemidAndRevId.newRevId;
        itemProperty.revId = "00";
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= "IICS";
       
        
        itemProperty.name =object_name; 
        itemProperty.type = "CN9IICSAP1000";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id","00");
		//insertValueMap(revisionStrMap,"cn9LetterType","接口信息意见单(IICSAP1000)版本");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9OurDocNum",setIICSDataRequest.getOurDocNum());//编号
		insertValueMap(revisionStrMap,"cn9Ver",setIICSDataRequest.getRevisionID());//版次
		//insertValueMap(revisionStrMap,"cn9Theme",setDesignReviewFDataRequest.getName());//名称
		insertValueMap(revisionStrMap,"cn9SendTo",setIICSDataRequest.getSendTo());
		
		insertValueMap(revisionStrMap,"cn9SendFrom",setIICSDataRequest.getSendFrom());
		
		insertValueMap(revisionStrMap,"cn9Pages",setIICSDataRequest.getPages());
		
		String finOrPre=setIICSDataRequest.getPreOrFin();
		if("FIN".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","是");
			insertValueMap(revisionStrMap,"cn9PREExchange","否");
		}else if("PRE".equals(finOrPre)){
			insertValueMap(revisionStrMap,"cn9FINExchange","否");
			insertValueMap(revisionStrMap,"cn9PREExchange","是");
		}
		
		//insertValueMap(revisionStrMap,"cn9Displ",setIICSDataRequest.getDispl());//发布单位
		
		insertValueMap(revisionStrMap,"cn91stParty",setIICSDataRequest.getOneParty());
		//add by xiaolei 20151111 for bug 2397 begin
		//insertValueMap(revisionStrMap,"cn9AreaCode",setCRDataRequest.getAreaCode());//区域
		//insertValueMap(revisionStrMap,"cn9LevelCode",setCRDataRequest.getLevelCode());//层位
		//add end
		insertValueMap(revisionStrMap,"cn92ndParty",setIICSDataRequest.getTwoParty());//来文
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",setIICSDataRequest.getSendRecvLettDate());//发布日期
		
		//insertValueMap(revisionStrMap,"cn9IntfcType",setIICSDataRequest.getIntfcType());//来文
		
		//insertValueMap(revisionStrMap,"cn9SerNum",setIICSDataRequest.getSerNum());
		
		insertValueMap(revisionStrMap,"cn9ReleaseParty",setIICSDataRequest.getReleaseParty());
		
		insertValueMap(revisionStrMap,"cn9RecvParty",setIICSDataRequest.getRecvParty());
		
		insertDateValueMap(revisionStrMap,"cn9ICMDate",setIICSDataRequest.getIcmDate());
		
		insertValueMap(revisionStrMap,"cn9IntfcIdent",setIICSDataRequest.getIntfcIdent());
		
		insertValueMap(revisionStrMap,"cn9IntfcDetailDescr",setIICSDataRequest.getIntfcDetailDescr());
		
		//insertValueMap(revisionStrMap,"cn9IntfcInfo",setIICSDataRequest.getIntfcInfo());
		
		insertValueMap(revisionStrMap,"cn9CpartDocNum",setIICSDataRequest.getCpartDocNum());
		insertValueMap(revisionStrMap,"cn9IntCC",setIICSDataRequest.getIntCC());
		
		insertValueMap(revisionStrMap,"cn9ExtCc",setIICSDataRequest.getExtCc());
		
		//insertDateValueMap(revisionStrMap,"cn9IITFSendDocDate",setIICSDataRequest.getiITFSendDocDate());
		//insertValueMap(revisionStrMap,"cn9IfSendCD",setIITFDataRequest.getIfSendCD());
		
		insertValueMap(revisionStrMap,"cn9ShenCConclu",setIICSDataRequest.getShenCConclu());
		
		insertValueMap(revisionStrMap,"cn9ShenCSugg",setIICSDataRequest.getShenCSugg());
		
		
		
		
		//add by xiaolei 20160223 for bug 2562 begin
		//add end
		
		//add by xiaolei 20160311 for bug 2573 begin
		//add end
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		
				
		return output;
	}
	

	/**
	 * 创建FCR版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-29
	 * @param setFCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createFieldCRRevision(FCRDataMessageBody setFCRDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9FieldCRRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        
        String object_name= setFCRDataRequest.getTheme();
        int length=object_name.length();
        String str="";
        if(length>90){
        	str=object_name.substring(0,90);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
        //itemProperty.name =str+".."; 
        
        itemProperty.type = "CN9FieldCR";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","FCR单及答复单");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9FileID",setFCRDataRequest.getCode());//编号
		insertValueMap(revisionStrMap,"cn9Rev",setFCRDataRequest.getRevision());//版次
		insertValueMap(revisionStrMap,"cn9Discipline",setFCRDataRequest.getSpecialty());//专业
		
		insertValueMap(revisionStrMap,"cn9Unit",setFCRDataRequest.getUnit());//机组
		insertValueMap(revisionStrMap,"cn9System",setFCRDataRequest.getSystem());//系统
		insertValueMap(revisionStrMap,"cn9RoomCode",setFCRDataRequest.getRoom());//房间
		
		//insertValueMap(revisionStrMap,"",setFCRDataRequest.getSubItem());//子项
		
		//modify by xiaolei 20151105 for bug 2385 begin
		//insertValueMap(revisionStrMap,"cn9BuildingCode",setFCRDataRequest.getBuilding());//厂房
		String building = setFCRDataRequest.getBuilding();
		if(building==null || building.trim().equals("")){
			building = setFCRDataRequest.getSubItem();
		}
		insertValueMap(revisionStrMap,"cn9BuildingCode",building);//房间
		//modify end
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setFCRDataRequest.getPublishUnit());//发布单位
			
		insertDateValueMap(revisionStrMap,"cn9SendDate",setFCRDataRequest.getPublishTime());//发布时间
		
		insertValueMap(revisionStrMap,"cn9DIPS",setFCRDataRequest.getDipsCode());//DIPS
		insertValueMap(revisionStrMap,"cn9ResponsibleParty",setFCRDataRequest.getDutyUnit());//责任方
		insertValueMap(revisionStrMap,"cn9Reason",setFCRDataRequest.getChangeReason());//变更原因
		
		insertValueMap(revisionStrMap,"cn9IsReply",setFCRDataRequest.getIsReply());//是否需要设计院答复
		
		//add by xiaolei 20151111 for bug 2397 begin
		insertValueMap(revisionStrMap,"cn9AreaCode",setFCRDataRequest.getArea());//区域
		insertValueMap(revisionStrMap,"cn9LevelCode",setFCRDataRequest.getLevel());//层位
		//add end
		
		//add by xiaolei 20160223 for bug 2562 begin
		insertValueMap(revisionStrMap,"cn9Theme",setFCRDataRequest.getTheme());//主题
		insertValueMap(revisionStrMap,"cn9DevNum",setFCRDataRequest.getDevNum());//设备位号
		//modify by xiaolei 20160311 for bug 2573
		//insertValueMap(revisionStrMap,"cn9ChangeSolution",setFCRDataRequest.getChangeSolution());//变更方案
		insertValueMap(revisionStrMap,"cn9Scheme",setFCRDataRequest.getChangeSolution());//变更方案
		//add end
		
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		FCRFileList[] fileList = setFCRDataRequest.getFileList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
				}
				//add by xiaolei 20151105 begin	.
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
	 * 创建FCR版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-29
	 * @param setFCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createFURevision(FUDataMessageBody setFUDataMessageBody) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9FUReqRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        	 itemProperty.name =setFUDataMessageBody.getTheme();
        itemProperty.type = "CN9FUReq";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","FU申请");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		//insertValueMap(revisionStrMap,"cn9FileID",setFCRDataRequest.getCode());//编号
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setFUDataMessageBody.getAuthorUnit());
		insertValueMap(revisionStrMap,"cn9RecivUnit",setFUDataMessageBody.getRecivunit());
		
		insertValueMap(revisionStrMap,"cn9Content",setFUDataMessageBody.getContent());
	
		insertValueMap(revisionStrMap,"cn9LetterSendNo",setFUDataMessageBody.getLetterSendNo());
		
		insertValueMap(revisionStrMap,"cn9Pages",setFUDataMessageBody.getPages());
		
		insertDateValueMap(revisionStrMap,"cn9ReplayDeadline",setFUDataMessageBody.getFuDate());//发布时间
		
		insertDateValueMap(revisionStrMap,"cn9SendRecvLettDate",setFUDataMessageBody.getSendDate());//发布时间
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		FUFileList[] fileList = setFUDataMessageBody.getFileList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					//if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
				}
				//add by xiaolei 20151105 begin	.
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
	 * 创建NCR版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-29
	 * @param setNCRDataRequest
	 * @return
	 * @throws Exception
	 */
	public CreateItemsOutput createNonConformaRevision(NCRDataMessageBody setNCRDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9NonconformaRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        String object_name= setNCRDataRequest.getNCRName();
        int length=object_name.length();
        String str="";
        if(length>90){
        	str=object_name.substring(0,90);
        	 itemProperty.name =str+"..";
        }else{
        	 itemProperty.name =object_name;
        }
        
        //itemProperty.name =str+"..";
        itemProperty.type = "CN9NonConforma";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","NCR单及答复单");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9FileID",setNCRDataRequest.getCode());//编号
		insertValueMap(revisionStrMap,"cn9Classfication",setNCRDataRequest.getType());//类别
		insertValueMap(revisionStrMap,"cn9Rev",setNCRDataRequest.getRevision());//版本
		insertValueMap(revisionStrMap,"cn9CorrespLetterRecNo",setNCRDataRequest.getSendChannelNo());//渠道号
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setNCRDataRequest.getAuthorUnit());//页数
		insertValueMap(revisionStrMap,"cn9Pages",setNCRDataRequest.getPages());//页数
		insertValueMap(revisionStrMap,"cn9CCUnit",setNCRDataRequest.getCCUnit());//抄送
		//TC中是数组类型
		if(setNCRDataRequest.getEquipCodeName()!=null){
			VecStruct equipCodeNameVecStruct = new VecStruct();
			equipCodeNameVecStruct.stringVec = setNCRDataRequest.getEquipCodeName();
			
			revisionStrMap.put("cn9DevNameAndNum", equipCodeNameVecStruct); //设备编号及名称
		}
		
		
		insertValueMap(revisionStrMap,"cn9Nonconformities",setNCRDataRequest.getNCRName());//不符合项名称
			
		insertDateValueMap(revisionStrMap,"cn9SendDate",setNCRDataRequest.getPublishTime());//发布时间
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		NCRFileList[] fileList = setNCRDataRequest.getFileList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) 
					formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null)
						formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileExeStatus()!=null) formStrMap.put("cn9States", fileList[i].getFileExeStatus());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
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
	 * 创建DEN版本，并且导入其中的文件清单,增加的材料清单,取消的材料清单
	 * add by xiaolei 2014-12-15
	 * @param setDENDataRequest
	 * @return
	 * @throws Exception
	 */
	/*
	public CreateItemsOutput createDENRevision(DENDataMessageBody setDENDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9DesignEnvNotiRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        //modify by xiaolei 20160620 for bug 2641
        //itemProperty.name = itemidAndRevId.newItemId;
        
        String object_name=  setDENDataRequest.getTitle();
        int length=object_name.length();
        String str="";
        if(length>120){
        	str=object_name.substring(0,120);
        }
        
        itemProperty.name =str+"..";
        itemProperty.type = "CN9DesignEnvNoti";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();

		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","DEN");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setDENDataRequest.getSendFrom());//发布单位
		insertValueMap(revisionStrMap,"cn9FileID",setDENDataRequest.getDENNo());//编号
		insertValueMap(revisionStrMap,"cn9Rev",setDENDataRequest.getRevision());//版本
		insertValueMap(revisionStrMap,"cn9DENType",setDENDataRequest.getDENType());//类型

		
		insertValueMap(revisionStrMap,"cn9Unit",setDENDataRequest.getUnitNo());//机组
		insertValueMap(revisionStrMap,"cn9System",setDENDataRequest.getSystemCode());//系统
		insertValueMap(revisionStrMap,"cn9DevNum",setDENDataRequest.getEquipNo());//设备位号
		insertValueMap(revisionStrMap,"cn9BuildingCode",setDENDataRequest.getPlantCode());//厂房
		insertValueMap(revisionStrMap,"cn9AreaCode",setDENDataRequest.getAreaCode());//区域
		insertValueMap(revisionStrMap,"cn9RoomCode",setDENDataRequest.getRoomNo());//房间
		insertValueMap(revisionStrMap,"cn9LevelCode",setDENDataRequest.getLayerCode());//层位
		insertValueMap(revisionStrMap,"cn9Discipline",setDENDataRequest.getSpecialty());//专业
		insertValueMap(revisionStrMap,"cn9DIPS",setDENDataRequest.getDIPSCode());//DIPS码
		insertValueMap(revisionStrMap,"cn9LetterSendNo",setDENDataRequest.getSenderChannelNo());//渠道号
		insertValueMap(revisionStrMap,"cn9Pages",setDENDataRequest.getPages());//页数

		insertDateValueMap(revisionStrMap,"cn9SendDate",setDENDataRequest.getSendDate());//发布日期
		
		insertValueMap(revisionStrMap,"cn9CCUnit",setDENDataRequest.getCCUnit());//抄送
		insertValueMap(revisionStrMap,"cn9Theme",setDENDataRequest.getTitle());//主题

		
		
		//变更原因 特殊处理
		if(setDENDataRequest.getChangeReason()!=null){
			String[] reasons = setDENDataRequest.getChangeReason().split(",");
			for(int i=0;i<reasons.length;i++){
				if(reasons[i].equals("FCR"))
					insertValueMap(revisionStrMap,"cn9ChangeFCR","Y");
				else if(reasons[i].equals("CR"))
					insertValueMap(revisionStrMap,"cn9ChangeCR","Y");
				else if(reasons[i].equals("TA"))
					insertValueMap(revisionStrMap,"cn9ChangeTA","Y");
				else if(reasons[i].equals("NCR"))
					insertValueMap(revisionStrMap,"cn9ChangeNCR","Y");
				else if(reasons[i].equals("SDM/SSD修改"))
					insertValueMap(revisionStrMap,"cn9ChangeSDMSSD","Y");
				else if(reasons[i].equals("DEN"))
					insertValueMap(revisionStrMap,"cn9ChangeOtherDEN","Y");
				else if(reasons[i].equals("接口变化"))
					insertValueMap(revisionStrMap,"cn9ChangeInterface","Y");
				else if(reasons[i].equals("设计改进"))
					insertValueMap(revisionStrMap,"cn9ChangeDesignInnovation","Y");
				else if(reasons[i].equals("设备资料变化或材料代用"))
					insertValueMap(revisionStrMap,"cn9ChangeEquipment","Y");
				else if(reasons[i].equals("业主要求"))
					insertValueMap(revisionStrMap,"cn9ChangePurchaser","Y");
				else if(reasons[i].equals("其他"))
					insertValueMap(revisionStrMap,"cn9ChangeOther","Y");
				//add by xiaolei 20151111 for bug 2395 begin
				else {
					insertValueMap(revisionStrMap,"cn9ChangeOther","Y");
				}
				//add end
			}
		}
		
		insertValueMap(revisionStrMap,"cn9ResponseParties",setDENDataRequest.getDutyUnit());//责任方
		insertValueMap(revisionStrMap,"cn9ChangeSourceComment",setDENDataRequest.getDENSourceNo());//变更来源详细信息
		insertValueMap(revisionStrMap,"cn9DescripOfChange",setDENDataRequest.getChangeDescribe());//变更的描述或意见

		//DEN后果 特殊处理
		if(setDENDataRequest.getDENConsequence()!=null){
			String[] results = setDENDataRequest.getDENConsequence().split(",");
			
			for(int i=0;i<results.length;i++){
				if(results[i].equals("施工"))
					insertValueMap(revisionStrMap,"cn9ResultConstruction","Y");
				else if(results[i].equals("调试"))
					//modify by xiaolei 20160327
					//insertValueMap(revisionStrMap,"cn9ResultCommission","Y");
					insertValueMap(revisionStrMap,"cn9ResultProcurement","Y");
				else if(results[i].equals("采购"))
					//modify by xiaolei 20160327
					//insertValueMap(revisionStrMap,"cn9ResultProcurement","Y");
					insertValueMap(revisionStrMap,"cn9ResultCommission","Y");
			}
		}
		
		insertValueMap(revisionStrMap,"cn9Author",setDENDataRequest.getAuthor());//编制1
		insertValueMap(revisionStrMap,"cn9Author2",setDENDataRequest.getAuthor2());//编制2
		insertValueMap(revisionStrMap,"cn9JiaoHPerson",setDENDataRequest.getChecker());//校对
		insertValueMap(revisionStrMap,"cn9ShenHPerson",setDENDataRequest.getReviewer());//审核
		insertValueMap(revisionStrMap,"cn9ShenDPerson",setDENDataRequest.getAuditPerson());//审定
		insertValueMap(revisionStrMap,"cn9Approver",setDENDataRequest.getApprover());//批准

		insertDateValueMap(revisionStrMap,"cn9AuthorDate",setDENDataRequest.getAuthorDate());//编制1日期
		insertDateValueMap(revisionStrMap,"cn9AuthorDate2",setDENDataRequest.getAuthorDate2());//编制2日期
		insertDateValueMap(revisionStrMap,"cn9JiaoHDate",setDENDataRequest.getCheckDate());//校对日期
		insertDateValueMap(revisionStrMap,"cn9ShenHDate",setDENDataRequest.getReviewDate());//审核日期
		insertDateValueMap(revisionStrMap,"cn9ShenDDate",setDENDataRequest.getAuditDate());//审定日期
		insertDateValueMap(revisionStrMap,"cn9ApproveDate",setDENDataRequest.getApproveDate());//批准日期

		//TC中是数组类型
		if(setDENDataRequest.getJointSigner()!=null && !setDENDataRequest.getJointSigner().equals("") ){
			VecStruct JointSignerVecStruct = new VecStruct();
			String[] JointSigner = setDENDataRequest.getJointSigner().split(",");
			JointSignerVecStruct.stringVec = JointSigner;
			
			revisionStrMap.put("cn9HuiqianRen", JointSignerVecStruct); //会签
		}
		if(setDENDataRequest.getJointSignDate()!=null && !setDENDataRequest.getJointSignDate().equals("")){
			VecStruct JointSignerDateVecStruct = new VecStruct();
			String[] JointSignDate = setDENDataRequest.getJointSignDate().split(",");
			for(int i=0;i<JointSignDate.length;i++){
				if(JointSignDate[i]!=null){
					Date time = dateFormat.parse(JointSignDate[i]);
					String timeStr = dateFormat2.format(time)+"T"+dateFormat3.format(time);
					JointSignDate[i]=timeStr;
				}
			}
			
			JointSignerDateVecStruct.stringVec = JointSignDate;
			
			revisionStrMap.put("cn9HuiqianDate", JointSignerDateVecStruct); //会签日期
		}
		
		insertValueMap(revisionStrMap,"cn9FieldReviewResult",setDENDataRequest.getFieldRevConc());//现场审查结论
		//insertValueMap(revisionStrMap,"",setDENDataRequest.getFieldRevAdv());//现场审查意见
		//insertDateValueMap(revisionStrMap,"",setDENDataRequest.getFieldRevDate());//现场审查日期

			


		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		DENFileList[] fileList = setDENDataRequest.getFileList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{//add by xiaolei 20151105
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
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
		
		//创建MatItemF Form并写入到cn9AddedMaterial属性中
		DENAddedMatList[] addedMatList = setDENDataRequest.getAddedMatList();
		if(addedMatList!=null && addedMatList.length>0){
			Vector<String> formVec = new Vector<String>();
			for(int i=0;i<addedMatList.length;i++){
				String formName = addedMatList[i].getFileExtCode()+"_"+addedMatList[i].getFileRev();
				HashMap<String, String> formStrMap = new HashMap<String, String>();
				if(addedMatList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", addedMatList[i].getFileExtCode());
				if(addedMatList[i].getFileRev()!=null) formStrMap.put("cn9Rev", addedMatList[i].getFileRev());
				if(addedMatList[i].getSeqNo()!=null) formStrMap.put("cn9SeqCode", addedMatList[i].getSeqNo());
				if(addedMatList[i].getMatNo()!=null) formStrMap.put("cn9MaterialCode", addedMatList[i].getMatNo());
				if(addedMatList[i].getMatName()!=null) formStrMap.put("cn9Description", addedMatList[i].getMatName());
				if(addedMatList[i].getMatType()!=null) formStrMap.put("cn9Type", addedMatList[i].getMatType());
				if(addedMatList[i].getQuantity()!=null) formStrMap.put("cn9Quantity", addedMatList[i].getQuantity());
				if(addedMatList[i].getUnitofValue()!=null) formStrMap.put("cn9UM", addedMatList[i].getUnitofValue());

				formVec.add(createForm("CN9MatItemF", formName, formStrMap).getUid());
			}
		
			if(formVec.size()>0){
				HashMap<String, VecStruct> formStrMap = new HashMap<String, VecStruct>();
				
				String[] formUids = new String[formVec.size()];
				for(int i=0;i<formUids.length;i++){
					formUids[i] = formVec.get(i);
				}
				VecStruct formVecStruct = new VecStruct();
				formVecStruct.stringVec = formUids;
				
				formStrMap.put("cn9AddedMaterial", formVecStruct);
				
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
		
		//创建MatItemF Form并写入到cn9CancelledMaterial属性中
		DENCancelledMatList[] cancelledMatList = setDENDataRequest.getCancelledMatList();
		if(cancelledMatList!=null && cancelledMatList.length>0){
			Vector<String> formVec = new Vector<String>();
			for(int i=0;i<cancelledMatList.length;i++){
				String formName = cancelledMatList[i].getFileExtCode()+"_"+cancelledMatList[i].getFileRev();
				HashMap<String, String> formStrMap = new HashMap<String, String>();
				if(cancelledMatList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", cancelledMatList[i].getFileExtCode());
				if(cancelledMatList[i].getFileRev()!=null) formStrMap.put("cn9Rev", cancelledMatList[i].getFileRev());
				if(cancelledMatList[i].getSeqNo()!=null) formStrMap.put("cn9SeqCode", cancelledMatList[i].getSeqNo());
				if(cancelledMatList[i].getMatNo()!=null) formStrMap.put("cn9MaterialCode", cancelledMatList[i].getMatNo());
				if(cancelledMatList[i].getMatName()!=null) formStrMap.put("cn9Description", cancelledMatList[i].getMatName());
				if(cancelledMatList[i].getMatType()!=null) formStrMap.put("cn9Type", cancelledMatList[i].getMatType());
				if(cancelledMatList[i].getQuantity()!=null) formStrMap.put("cn9Quantity", cancelledMatList[i].getQuantity());
				if(cancelledMatList[i].getUnitofValue()!=null) formStrMap.put("cn9UM", cancelledMatList[i].getUnitofValue());

				formVec.add(createForm("CN9MatItemF", formName, formStrMap).getUid());
			}
		
			if(formVec.size()>0){
				HashMap<String, VecStruct> formStrMap = new HashMap<String, VecStruct>();
				
				String[] formUids = new String[formVec.size()];
				for(int i=0;i<formUids.length;i++){
					formUids[i] = formVec.get(i);
				}
				VecStruct formVecStruct = new VecStruct();
				formVecStruct.stringVec = formUids;
				
				formStrMap.put("cn9CancelledMaterial", formVecStruct);
				
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
	*/
	
	/**
	 * 从CNPE_project_docmanager首选项中读取项目、文档员列表，找出符合条件的文档员用户id
	 * add by xiaolei 2014-11-24
	 * @param projectId
	 * @return 符合条件的文档员用户id
	 */

	/**
	 * 创建FU申请单版本，并且导入其中的文件清单
	 * add by xiaolei 2014-11-29
	 * @param setFUDataRequest
	 * @return
	 * @throws Exception
	 */
	/*
	public CreateItemsOutput createFURevision(FUDataMessageBody setFUDataRequest) throws Exception{
		ItemIdsAndInitialRevisionIds itemidAndRevId = generateItemIds("CN9FUReqRevision");

		CreateItemsOutput output = null;
		ItemProperties[] itemProps = new ItemProperties[1];
		
		ItemProperties itemProperty = new ItemProperties();

        itemProperty.clientId = "webservice";
        itemProperty.itemId = itemidAndRevId.newItemId;
        itemProperty.revId = itemidAndRevId.newRevId;
        itemProperty.name = itemidAndRevId.newItemId;
        itemProperty.type = "CN9FUReq";
        itemProperty.description = "";
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
        
		ItemRevision itemRev = output.itemRev;
		
		HashMap<String, VecStruct> revisionStrMap = new HashMap<String, VecStruct>();
		
		insertValueMap(revisionStrMap,"item_revision_id",itemidAndRevId.newRevId);
		insertValueMap(revisionStrMap,"cn9LetterType","FU申请");//文函类型
		insertValueMap(revisionStrMap,"cn9ReceiveSendFlag","0");//收发文标记   //add by xiaolei 20141226
		insertValueMap(revisionStrMap,"cn9RecivUnit",setFUDataRequest.getSendTo());//主送方单位
		insertValueMap(revisionStrMap,"cn9AuthorUnit",setFUDataRequest.getSendFrom());//发自单位
		insertValueMap(revisionStrMap,"cn9LetterSendNo",setFUDataRequest.getSenderChannelNo());//发文方的文号
		
		insertValueMap(revisionStrMap,"cn9Pages",setFUDataRequest.getPages());//页数
		
		insertDateValueMap(revisionStrMap,"cn9SendDate",setFUDataRequest.getSendDate());//发出日期
		
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
		
		//创建FileItemF Form并写入到cn9FileList属性中
		FUFileList[] fileList = setFUDataRequest.getFileList();
		if(fileList!=null && fileList.length>0){
			Vector<String> formVec = new Vector<String>();
			try{
				for(int i=0;i<fileList.length;i++){
					String formName = fileList[i].getFileExtCode()+"_"+fileList[i].getFileRev();
					HashMap<String, String> formStrMap = new HashMap<String, String>();
					formStrMap.put("cn9SequenceNo", (i+1)+"");
					if(fileList[i].getFileExtCode()!=null) formStrMap.put("cn9ExternalCode", fileList[i].getFileExtCode());
					if(fileList[i].getFileIntCode()!=null) formStrMap.put("cn9InternalCode", fileList[i].getFileIntCode());
					if(fileList[i].getFileRev()!=null) formStrMap.put("cn9Rev", fileList[i].getFileRev());
					if(fileList[i].getFileTitle()!=null) formStrMap.put("cn9FileName", fileList[i].getFileTitle());
					
					formVec.add(createForm("CN9FileItemF", formName, formStrMap).getUid());
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
*/

	public String getUserFromPref(String projectId) {
		
		//DataManagementService.getService(session.getConnection());
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		
		
		try {
			prefService.refreshPreferences();
			
			PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_project_docmanager_lot"});
			
			ServiceData serviceData = response.serviceData;
			if (serviceData.sizeOfPartialErrors() == 0) {
				Preferences prefs = response.preferences;
				
				List<String> prefValueList = prefs.getPreference("CNPE_project_docmanager_lot");
				
				if(prefValueList!=null && prefValueList.size()>0){
					for(int i=0;i<prefValueList.size();i++){
						String[] pairs = prefValueList.get(i).split(",");
						if(pairs.length==2 && pairs[0].equals(projectId))
							return pairs[1];
					}
				}
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				System.out.println(ret);;
			}
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public String getUserFrom(String projectId) {
		
		//DataManagementService.getService(session.getConnection());
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		
		
		try {
			prefService.refreshPreferences();
			
			PreferencesResponse response = sessionService.getPreferences("site", new String[]{"cnpe_project_docmanager_query"});
			
			ServiceData serviceData = response.serviceData;
			if (serviceData.sizeOfPartialErrors() == 0) {
				Preferences prefs = response.preferences;
				
				List<String> prefValueList = prefs.getPreference("cnpe_project_docmanager_query");
				
				if(prefValueList!=null && prefValueList.size()>0){
					for(int i=0;i<prefValueList.size();i++){
						String[] pairs = prefValueList.get(i).split(",");
						if(pairs.length==2 && pairs[0].equals(projectId))
							return pairs[1];
					}
				}
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				System.out.println(ret);;
			}
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
public String getICMFromPref(String projectId) {
		
		//DataManagementService.getService(session.getConnection());
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		
		
		try {
			prefService.refreshPreferences();
			
			PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_project_docmanager_lot"});
			
			ServiceData serviceData = response.serviceData;
			if (serviceData.sizeOfPartialErrors() == 0) {
				Preferences prefs = response.preferences;
				
				List<String> prefValueList = prefs.getPreference("CNPE_project_docmanager_lot");
				
				if(prefValueList!=null && prefValueList.size()>0){
					for(int i=0;i<prefValueList.size();i++){
						String[] pairs = prefValueList.get(i).split(",");
						if(pairs.length==2 && pairs[0].equals(projectId))
							return pairs[1];
					}
				}
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				System.out.println(ret);;
			}
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "";
	}

	
	/**
	 * 从CNPE_webservice_process_template首选项中根据对象类型、是否回复找出符合条件的流程模板名称
	 * add by xiaolei 2014-11-25
	 * @param 
	 * @return 流程模板名称
	 */
	public String getProcessTemplateNameFromPref(String itemRevType,boolean isReply) {
		
		//DataManagementService.getService(session.getConnection());
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		
		
		try {
			prefService.refreshPreferences();
			
			PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_webservice_process_template"});
			
			ServiceData serviceData = response.serviceData;
			if (serviceData.sizeOfPartialErrors() == 0) {
				Preferences prefs = response.preferences;
				
				List<String> prefValueList = prefs.getPreference("CNPE_webservice_process_template");
				
				if(prefValueList!=null && prefValueList.size()>0){
					for(int i=0;i<prefValueList.size();i++){
						String[] values = prefValueList.get(i).split(",");
						if(values.length==3 && values[0].equals(itemRevType))
							if(isReply)
								return values[1];
							else {
								return values[2];
							}
					}
				}
			} else {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				System.out.println(ret);;
			}
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 给对象指派项目，如果对象之前存在项目信息，先移除项目信息再指派
	 * @param object
	 * @param project
	 * @throws Exception
	 */
	public void removeAndAssignProject(ModelObject object,TC_Project project) throws Exception{
		
		ProjectLevelSecurityService projectService = ProjectLevelSecurityService.getService(session.getConnection());
		
		//移除项目信息
		ModelObject[] project_list = getModelObjectArrayValue(object,"project_list");
		if(project_list!=null && project_list.length>0){
			AssignedOrRemovedObjects[] input = new AssignedOrRemovedObjects[1];
			input[0] = new AssignedOrRemovedObjects();
			input[0].projects = new TC_Project[project_list.length];
			for(int i=0;i<project_list.length;i++){
				input[0].projects[i] = (TC_Project)project_list[i];
			}
			input[0].objectToRemove = new ModelObject[]{object};
			ServiceData serviceData = projectService.assignOrRemoveObjects(input);
			if (serviceData.sizeOfPartialErrors() > 0) {
				String ret = "";
				String[] msgs = serviceData.getPartialError(0).getMessages();

				for (int i = 0; i < msgs.length; i++) {
					ret = ret + msgs[i] + " ";
				}

				System.out.println("移除项目失败："+ret);
			}
			dmService.refreshObjects(new ModelObject[]{object});
		}
		//指派项目信息
		AssignedOrRemovedObjects[] input = new AssignedOrRemovedObjects[1];
		input[0] = new AssignedOrRemovedObjects();
		input[0].projects = new TC_Project[]{project};
		input[0].objectToAssign = new ModelObject[]{object};
		ServiceData serviceData = projectService.assignOrRemoveObjects(input);
		if (serviceData.sizeOfPartialErrors() > 0) {
			String ret = "";
			String[] msgs = serviceData.getPartialError(0).getMessages();

			for (int i = 0; i < msgs.length; i++) {
				ret = ret + msgs[i] + " ";
			}

			System.out.println("指派项目失败："+ret);
		}
		dmService.refreshObjects(new ModelObject[]{object});
		
	}
	
	
	/**
	 * 对CR版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addChangeRqRevisionAttachements(ItemRevision itemRev,CRAttachment[] attachments) throws Exception {
	System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			
			
			
			
			
			
			
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
	
	/**
	 * 对TA版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addTARevisionAttachements(ItemRevision itemRev,TAAttachment[] attachments) throws Exception {
	System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
	
	/**
	 * 对设计审查意见答复单版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addDesignRFRevisionAttachements(ItemRevision itemRev,DesignReviewFAttachment[] attachments) throws Exception {
	System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
	
	
	/**
	 * 对IITF版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addIITFRevisionAttachements(ItemRevision itemRev,IITFAttachment[] attachments) throws Exception {
	System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
	
	
	/**
	 * 对AP1000IITF版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addAp1000IITFRevisionAttachements(ItemRevision itemRev,Ap1000IITFAttachment[] attachments) throws Exception {
	System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
	
	
	/**
	 * 对文件传递单版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */		
	public void addChangeRqRevisionAttachements(ItemRevision itemRev,FileAttachment[] attachments) throws Exception {
	System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}

	/**
	 * 对FCR版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addFieldCRRevisionAttachements(ItemRevision itemRev,FCRAttachment[] attachments) throws Exception {
		
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_webservice_ftp_config"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_webservice_ftp_config");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_webservice_ftp_config配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();

			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
	
	/**
	 * 对图文传真版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addFaxRevisionAttachements(ItemRevision itemRev,FaxAttachment[] attachments) throws Exception {
		
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			System.out.println("ext:"+ext);
			
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}

	/**
	 * 对NCR版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addNonConformaRevisionAttachements(ItemRevision itemRev,NCRAttachment[] attachments) throws Exception {
		System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
	}
	
	
	/**
	 * 对NCR版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	public void addFURevisionAttachements(ItemRevision itemRev,FUAttachment[] attachments) throws Exception {
		System.out.println("--------------------------------------------------------------------------"+attachments);
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_LOT_webservice_ftp"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_LOT_webservice_ftp");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_LOT_webservice_ftp配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String fileName="";
			
			if("rar".equals(ext) || "RAR".equals(ext)){
				ext="zip";
				fileName=attachments[i].getFileName().substring(0,attachments[i].getFileName().length()-3)+ext;
				
			}else{
				fileName=attachments[i].getFileName();
			}
			
			//String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();
			String localFileName = System.getProperty("java.io.tmpdir") + fileName;
			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			
			//String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
	}

	/**
	 * 对DEN版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	/*
	public void addDENRevisionAttachements(ItemRevision itemRev,DENAttachment[] attachments) throws Exception {
		
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_webservice_ftp_config"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_webservice_ftp_config");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_webservice_ftp_config配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();

			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
*/
	/**
	 * 对FU申请单版本添加附件数据集
	 * @param itemRev
	 * @param attachments
	 * @throws Exception
	 */
	/*
	public void addFURevisionAttachements(ItemRevision itemRev,FUAttachment[] attachments) throws Exception {
		
		//1 ftp get file to local
		SessionService sessionService = SessionService.getService(session.getConnection());
		PreferenceManagementService prefService = PreferenceManagementService.getService(session.getConnection());
		prefService.refreshPreferences();
		
		PreferencesResponse response = sessionService.getPreferences("site", new String[]{"CNPE_webservice_ftp_config"});
		List<String> ftpConfigs=null;
		ServiceData serviceData = response.serviceData;
		if (serviceData.sizeOfPartialErrors() == 0) {
			Preferences prefs = response.preferences;
			
			ftpConfigs = prefs.getPreference("CNPE_webservice_ftp_config");
			
			if(ftpConfigs==null){
				throw new Exception("CNPE_webservice_ftp_config配置错误！");
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
		
		response = sessionService.getPreferences("site", new String[]{"CNPE_File_Import_Type"});
		List<String> fileImportTypeList=null;
		serviceData = response.serviceData;
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
		
		
		FtpDownload ftpDownload = new FtpDownload(ftpConfigs.get(0), Integer.parseInt(ftpConfigs.get(1)), ftpConfigs.get(2),ftpConfigs.get(3));   
		
		for(int i=0;i<attachments.length;i++){
			if(attachments[i].getFilePath()==null || attachments[i].getFilePath().equals(""))
				throw new Exception("附件列表中的文件路径没有填写！");
			
			if(attachments[i].getFileName()==null || attachments[i].getFileName().equals(""))
				throw new Exception("附件列表中的文件原名字没有填写！");
			
			System.out.println(attachments[i].getFilePath()+"----------"+attachments[i].getFileName());
			
			String localFileName = System.getProperty("java.io.tmpdir") + attachments[i].getFileName();

			ftpDownload.downloadToLocal(attachments[i].getFilePath(), localFileName);
			
			String ext = attachments[i].getFileName().substring(attachments[i].getFileName().lastIndexOf(".") + 1);
			String datasetName = attachments[i].getFileName().substring(0,attachments[i].getFileName().lastIndexOf("."));
			String datasetType = "";
			String referenceName = "";
			
			for(int j=0;j<fileImportTypeList.size();j++){
				String[] ss = fileImportTypeList.get(j).split("#");
				
				if(ss.length != 3)
					continue;
				
				if(ss[0].equalsIgnoreCase(ext))
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
			
			// create dataset
			createDataset(itemRev, datasetType, datasetName, referenceName, localFileName, "CN9Attachment");
		}
		
		
		
	}
*/
	public boolean addRelation(ModelObject object,ModelObject object2,String relationType){
		
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
	public void resultFoler(String name){
		//User user=
	
	}
	
	
	
}
