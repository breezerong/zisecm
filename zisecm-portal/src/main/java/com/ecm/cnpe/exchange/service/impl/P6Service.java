package com.ecm.cnpe.exchange.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnpe.p6.authenticationservice.AuthenticationService;
import com.cnpe.p6.authenticationservice.AuthenticationServicePortType;
import com.cnpe.p6.authenticationservice.IntegrationFault;
import com.cnpe.p6.projectservice.Project;
import com.cnpe.p6.projectservice.ProjectPortType;
import com.cnpe.p6.projectservice.ProjectService;
import com.ecm.cnpe.exchange.config.P6Config;
import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.service.IP6Service;


@Service
public class P6Service implements IP6Service{
	@Autowired
	private P6Config p6;
	
	private HTTPClientPolicy httpClientPolicy = null;

	private URL authenticationURL = null;
	private URL projectURL = null;

	

	private AuthenticationService authenticationService;
	
	private boolean init() {
		httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(5000);
		httpClientPolicy.setAllowChunking(false);
		httpClientPolicy.setReceiveTimeout(20000);
		httpClientPolicy.setConnection(ConnectionType.KEEP_ALIVE);		
		try {
			authenticationURL = new URL(p6.getAuthenticationService());
			projectURL = new URL(p6.getProjectService());
			return true;
		} catch (MalformedURLException e) {
			System.err.println("P6 WebService地址配置错误，错误信息：" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	private ProjectPortType getProjectService() {
		ProjectService service = new ProjectService(projectURL);
		ProjectPortType porttype = service.getProjectPort();
		Client client = ClientProxy.getClient(porttype);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		conduit.setClient(httpClientPolicy);
		return porttype;
	}
	
	private List<Project> getProjectInfo(String filter,ProjectPortType projectPort)  {
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
			projectList = projectPort.readProjects(fieldList, filter, null);
			return projectList;
		} catch (com.cnpe.p6.projectservice.IntegrationFault e) {
			e.printStackTrace();
			return projectList= new ArrayList<Project>();
		}
	}
	
	@Override
	public List<ProjectEntity> getP6Projects() {
		List<ProjectEntity> list = new ArrayList<ProjectEntity>();
		if(this.p6.isEnable()) {	
			this.init();
			boolean isLoginSuccess;
			try {
				isLoginSuccess = this.auth();
				if(!isLoginSuccess) {
					System.err.println("P6用户 "+p6.getUsername()+" 登录失败");
					return list;
				}
				ProjectPortType service = this.getProjectService();
				List<Project> projectList = this.getProjectInfo("",service);
				for (Project project : projectList) {
					ProjectEntity item = new ProjectEntity();
					item.setId(project.getWBSObjectId().getValue()+"");
					item.setCode(project.getId());
					item.setName(project.getName());
					list.add(item);
				}
				
			}catch (Exception e) {
				list = new ArrayList<ProjectEntity>();
			}
		}
		return list;
	}
	
	
}
