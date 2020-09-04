package org.zisecm.jobs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "p6")
public class P6Config {
	private String username = "TCTest";
	private String password = "111111aaa";

	private String AuthenticationService = "http://10.30.7.78:8206/p6ws/services/AuthenticationService?wsdl";

	private String ProjectService = "http://10.30.7.78:8206/p6ws/services/ProjectService?wsdl";

	private String WbsService = "http://10.30.7.78:8206/p6ws/services/WBSService?wsdl";

	private String ActivityService = "http://10.30.7.78:8206/p6ws/services/ActivityService?wsdl";

	private String UdfValueService = "http://10.30.7.78:8206/p6ws/services/UDFValueService?wsdl";

	private String UdfTypeService = "http://10.30.7.78:8206/p6ws/services/UDFTypeService?wsdl";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthenticationService() {
		return AuthenticationService;
	}

	public void setAuthenticationService(String authenticationService) {
		AuthenticationService = authenticationService;
	}

	public String getProjectService() {
		return ProjectService;
	}

	public void setProjectService(String projectService) {
		ProjectService = projectService;
	}

	public String getWbsService() {
		return WbsService;
	}

	public void setWbsService(String wbsService) {
		WbsService = wbsService;
	}

	public String getActivityService() {
		return ActivityService;
	}

	public void setActivityService(String activityService) {
		ActivityService = activityService;
	}

	public String getUdfValueService() {
		return UdfValueService;
	}

	public void setUdfValueService(String udfValueService) {
		UdfValueService = udfValueService;
	}

	public String getUdfTypeService() {
		return UdfTypeService;
	}

	public void setUdfTypeService(String udfTypeService) {
		UdfTypeService = udfTypeService;
	}

}
