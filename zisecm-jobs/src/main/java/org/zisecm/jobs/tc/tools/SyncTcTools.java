package org.zisecm.jobs.tc.tools;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.ws.PLMServerLOT.LoginInfo;

import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;
import com.teamcenter.services.strong.core.DataManagementService;
import com.teamcenter.services.strong.core._2006_03.DataManagement.CreateRelationsResponse;
import com.teamcenter.services.strong.core._2006_03.DataManagement.Relationship;
import com.teamcenter.services.strong.core._2007_01.DataManagement.VecStruct;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsData;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsOutput;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsPref;
import com.teamcenter.services.strong.core._2007_06.DataManagement.ExpandGRMRelationsResponse;
import com.teamcenter.services.strong.core._2007_06.DataManagement.RelationAndTypesFilter2;
import com.teamcenter.soa.client.model.ModelObject;
import com.teamcenter.soa.client.model.ServiceData;
import com.teamcenter.soa.exceptions.NotLoadedException;

public class SyncTcTools {
	private static Session session;
	
	public static Session getSession(Environment env) throws Exception {
		if(session==null) {
			session=loginTC(env);
			
		}
		return session;
		
	}
	
	private static Session loginTC(Environment env) throws Exception
	  {
//	    LoginInfo loginInfo = new LoginInfo("http://10.30.2.132:9080/tc", "dcproxy", "dcproxy", "SoaAppX");
		String url=env.getProperty("ecm.tcUrl");
		String userName=env.getProperty("ecm.tcUsername");
		String password=env.getProperty("ecm.tcPassword");
		  LoginInfo loginInfo = new LoginInfo(url, userName, password, "SoaAppX");

		  Session sess = new Session(loginInfo.getServerHost());
	    System.out.println(loginInfo.getServerHost() + "---------");
	    try
	    {
	      sess.login(loginInfo.getUserID(), loginInfo.getPassword(), 
	        loginInfo.getDiscriminator());
	    } catch (Exception e) {
	      e.printStackTrace();

	      throw new Exception("登录失败，请检查用户名密码，或网络地址是否正确");
	    }

	    return sess;
	  }
	/**
	 * 获取项目ID
	 * @param dmService
	 * @param obj
	 * @return
	 * @throws NotLoadedException
	 */
	public static String getProjectId(DataManagementService dmService,ModelObject obj) throws NotLoadedException {
		ModelObject[] projects=getModelObjectArrayValue(dmService, obj, "project_list");
		String project_id="";
		if(projects!=null&&projects.length>0) {
			dmService.getProperties(new ModelObject[] { projects[projects.length-1]}, new String[] {"project_id"});
			project_id=projects[projects.length-1].getPropertyObject("project_id").getStringValue();
		}
		return project_id;
	}
	/**
	 * 更改对象属性数据
	 * @param dmService
	 * @param object
	 * @param names
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static String setObjectProperties(DataManagementService dmService,ModelObject object, String[] names,
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
	/**
	 * 通过对象某一属性名找到其对应的数据对象结果数组
	 * @param dmService
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static ModelObject[] getModelObjectArrayValue(DataManagementService dmService,ModelObject object,
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
	
	
	/**
	 * 得到对象下关联的所有对象
	 * @param dmService
	 * @param object
	 * @return
	 */
	public static ModelObject[] getRelatedObjects(DataManagementService dmService,ModelObject object) {
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
	 * 获取属性数据
	 * @param dmService
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static String getProperty(DataManagementService dmService,ModelObject object, String propertyName,String typeName) {
		try {
			dmService.refreshObjects(new ModelObject[]{object});
			
			dmService.getProperties(new ModelObject[] { object },
					new String[] { propertyName });
			String value = "";
			if("Time".equals(typeName)) {
				value = object.getPropertyObject(propertyName).getDisplayValue();
				if("".equals(value)) {
					return null;
				}
				SimpleDateFormat format=new SimpleDateFormat("yyyy-M-dd HH:mm");
				SimpleDateFormat targetFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value=targetFormat.format(format.parse(value));
			}else {
				value = object.getPropertyObject(propertyName).getStringValue();
			}

			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
