package org.zisecm.jobs.business;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.config.P6Config;

import com.cnpe.p6.activityservice.Activity;
import com.cnpe.p6.activityservice.ActivityFieldType;
import com.cnpe.p6.activityservice.ActivityPortType;
import com.cnpe.p6.activityservice.ActivityService;
import com.cnpe.p6.authenticationservice.AuthenticationService;
import com.cnpe.p6.authenticationservice.AuthenticationServicePortType;
import com.cnpe.p6.authenticationservice.IntegrationFault;
import com.cnpe.p6.projectservice.Project;
import com.cnpe.p6.projectservice.ProjectPortType;
import com.cnpe.p6.projectservice.ProjectService;
import com.cnpe.p6.wbsservice.WBS;
import com.cnpe.p6.wbsservice.WBSFieldType;
import com.cnpe.p6.wbsservice.WBSPortType;
import com.cnpe.p6.wbsservice.WBSService;

@Service
public class P6SyncService {

	@Autowired
	private P6Config p6;

	private HTTPClientPolicy httpClientPolicy = null;

	private URL authenticationURL = null;
	private URL projectURL = null;
	private URL wbsURL = null;
	private URL activityURL = null;
	

	private AuthenticationService authenticationService;

	private List<Map<String, Object>> loadlist;
	
	private ProjectPortType projectPort;
	
	private boolean init() {
		httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(5000);
		httpClientPolicy.setAllowChunking(false);
		httpClientPolicy.setReceiveTimeout(20000);
		httpClientPolicy.setConnection(ConnectionType.KEEP_ALIVE);
		
		try {
			authenticationURL = new URL(p6.getAuthenticationService());
			projectURL = new URL(p6.getProjectService());
			wbsURL = new URL(p6.getWbsService());
			activityURL = new URL(p6.getActivityService());
			return true;
		} catch (MalformedURLException e) {
			System.err.println("P6 WebService地址配置错误，错误信息：" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked", "unused" })
	private boolean auth() throws Exception {
		authenticationService = new AuthenticationService(authenticationURL);
		AuthenticationServicePortType anthenticationServicePort = authenticationService.getAuthenticationServiceSOAP12PortHttp();
		Client authenticationClient = ClientProxy.getClient(anthenticationServicePort);
		HTTPConduit authenticationHTTPConduit = (HTTPConduit) authenticationClient.getConduit();
		authenticationHTTPConduit.setClient(httpClientPolicy);
		
		boolean isLoginSuccess = false;

		try {
			isLoginSuccess = anthenticationServicePort.login(p6.getUsername(), p6.getPassword(), null);
		} catch (IntegrationFault e) {
			System.err.println("根据地址：" + p6.getAuthenticationService() + ",用户名：" + p6.getUsername() + ",密码：" + p6.getPassword() + "，访问P6接口出错，错误信息：" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		String cookie = "";
		TreeMap<String, List<String>> headerMap = (TreeMap) authenticationClient.getResponseContext().get("org.apache.cxf.message.Message.PROTOCOL_HEADERS");
		if (null != headerMap && headerMap.containsKey("Set-Cookie")) {
			List<String> cookieList = headerMap.get("Set-Cookie");
			if (cookieList.size() > 0) {
				cookie = cookieList.get(0);
			}
		}

		if (cookie.isEmpty()) {
			throw new Exception("获取不到P6服务返回的Cookie！");
		}

		httpClientPolicy.setCookie(cookie);
		
		return isLoginSuccess;
	}
	
	private WBSPortType getWbsService() {
		WBSService wbsService = new WBSService(wbsURL);
		WBSPortType wbsPort = wbsService.getWBSPort();
		Client wbsClient = ClientProxy.getClient(wbsPort);
		HTTPConduit wbsHTTPConduit = (HTTPConduit) wbsClient.getConduit();
		wbsHTTPConduit.setClient(httpClientPolicy);
		return wbsPort;
	}
	
	private ProjectPortType getProjectService() {
		ProjectService service = new ProjectService(projectURL);
		ProjectPortType porttype = service.getProjectPort();
		Client client = ClientProxy.getClient(porttype);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		conduit.setClient(httpClientPolicy);
		return porttype;
	}
	
	private ActivityPortType getActivityService() {
		ActivityService service = new ActivityService(activityURL);
		ActivityPortType porttype = service.getActivityPort();
		Client client = ClientProxy.getClient(porttype);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		conduit.setClient(httpClientPolicy);
		return porttype;
	}

	public List<Activity> getActivityList(String projectId){
		List<Activity> actList;
		this.init();
		boolean isLoginSuccess;
		try {
			isLoginSuccess = this.auth();
			if(!isLoginSuccess) {
				System.err.println("P6用户 "+p6.getUsername()+" 登录失败");
				return new ArrayList<Activity>();
			}
			ActivityPortType activityPortType = this.getActivityService();
			actList = getActivity(activityPortType, "ProjectId='"+projectId+"'");
		}catch (Exception e) {
			return new ArrayList<Activity>();
		}
		return actList;
	}
	
	public List<Map<String,Object>> getWbsList(String projectId,List<Activity> actList){
		
		this.init();
		boolean isLoginSuccess;
		try {
			isLoginSuccess = this.auth();
			if(!isLoginSuccess) {
				System.err.println("P6用户 "+p6.getUsername()+" 登录失败");
				return new ArrayList<Map<String,Object>>();
			}
			ProjectPortType  projectPort = this.getProjectService();
			Project projectInfo = getProjectInfo(projectId,projectPort);
			WBSPortType wbsPort = this.getWbsService();
			loadlist = new ArrayList<Map<String,Object>>();
			loadWbs(projectInfo.getWBSObjectId().getValue()+"",wbsPort,projectInfo.getId(),projectInfo,actList);
		}catch (Exception e) {
			return new ArrayList<Map<String,Object>>();
		}
		return loadlist;
	}
	
	
	private List<Activity> getActivity(ActivityPortType activityPortType,String filter){
		List<Activity> list = null;
		List<ActivityFieldType> fieldList = new ArrayList<ActivityFieldType>();
		fieldList.add(ActivityFieldType.ID);
		fieldList.add(ActivityFieldType.NAME);
		fieldList.add(ActivityFieldType.STATUS);
		fieldList.add(ActivityFieldType.PROJECT_ID);
		fieldList.add(ActivityFieldType.PROJECT_NAME);
		fieldList.add(ActivityFieldType.PROJECT_OBJECT_ID);
		
		fieldList.add(ActivityFieldType.START_DATE);
		fieldList.add(ActivityFieldType.FINISH_DATE);
		fieldList.add(ActivityFieldType.BASELINE_1_START_DATE);
		fieldList.add(ActivityFieldType.BASELINE_1_FINISH_DATE);
		fieldList.add(ActivityFieldType.WBS_CODE);
		fieldList.add(ActivityFieldType.WBS_NAME);
		fieldList.add(ActivityFieldType.WBS_OBJECT_ID);
		fieldList.add(ActivityFieldType.WBS_PATH);
		try {
			list = activityPortType.readActivities(fieldList, filter, null);
		} catch (com.cnpe.p6.activityservice.IntegrationFault e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public Project getProjectInfo(String projectId)  {
		ProjectPortType projectPort = this.getProjectService();
		return this.getProjectInfo(projectId, projectPort);
	}
	
	public Project getProjectInfo(String projectId,ProjectPortType projectPort)  {
		List<Project> projectList = null;
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("Id");
		fieldList.add("Name");
		fieldList.add("ObjectId");
		fieldList.add("WBSObjectId");
		// 项目计划开始时间
		fieldList.add("StartDate");
		fieldList.add("WBSCodeSeparator");
		// 项目完成时间
		fieldList.add("ScheduledFinishDate");
		try {
			projectList = projectPort.readProjects(fieldList, "Id='"+projectId+"'", null);
			if(projectList!=null && projectList.size()>0) {
				return projectList.get(0);
			}else {
				return null;
			}
		} catch (com.cnpe.p6.projectservice.IntegrationFault e) {
			e.printStackTrace();
			return null;
		}
	}

	private void loadWbs(String parentObjectId,WBSPortType wbsPort,String wbscode,Project projectInfo,List<Activity> actList) {
		try {
			List<WBS> wbsGetList = loadWbsInfo(wbsPort, "ParentObjectId='"+parentObjectId +"'");
			if(wbsGetList!=null && wbsGetList.size()>0) {
				for (WBS wbs : wbsGetList) {
					String itemWbsCode = wbscode + projectInfo.getWBSCodeSeparator()+ wbs.getCode();
					Map<String,Object> obj = new HashMap<String, Object>();
					obj.put(WBSFieldType.OBJECT_ID.name(), wbs.getObjectId());
					obj.put(WBSFieldType.CODE.name(), itemWbsCode);
					obj.put(WBSFieldType.NAME.name(), wbs.getName());
					obj.put(WBSFieldType.PARENT_OBJECT_ID.name(), wbs.getParentObjectId().getValue());
					obj.put(WBSFieldType.STATUS.name(), wbs.getStatus());
					obj.put(WBSFieldType.CREATE_DATE.name(), getDate(wbs.getCreateDate().getValue()));
					
					getActivitiesByWbs(wbs.getObjectId()+"",actList);
					
					loadlist.add(obj);
					loadWbs(wbs.getObjectId()+"", wbsPort, itemWbsCode, projectInfo, actList);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getActivitiesByWbs(String wbsId,List<Activity> actList) {
		
	}
	
	private List<WBS> loadWbsInfo(WBSPortType wbsPort, String p6ProjectId) throws Exception {
		List<WBSFieldType> wbsFieldList = new ArrayList<WBSFieldType>();
		wbsFieldList.add(WBSFieldType.CODE);
		wbsFieldList.add(WBSFieldType.NAME);
		wbsFieldList.add(WBSFieldType.OBJECT_ID);
		wbsFieldList.add(WBSFieldType.PARENT_OBJECT_ID);
		wbsFieldList.add(WBSFieldType.SEQUENCE_NUMBER);
		List<WBS> wbsList = null;
		try {
			wbsList = wbsPort.readWBS(wbsFieldList, "ProjectId='" + p6ProjectId + "'", null);
		} catch (com.cnpe.p6.wbsservice.IntegrationFault e) {
			throw new Exception("获取WBS信息出错");
		}

		if (wbsList == null || wbsList.size() == 0) {
			throw new Exception("未从P6系统中获取到WBS信息");
		}
		return wbsList;
	}
	
	private String getDate(XMLGregorianCalendar value) {
		if(value == null) {
			return "";
		}
		return value.toString().replace("T", " ");
		
	}
}
