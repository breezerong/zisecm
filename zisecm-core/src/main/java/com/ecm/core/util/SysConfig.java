package com.ecm.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Haihong
 *系统配置
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:config/sysConfig.properties")
public class SysConfig {
	

	private static String departmentRootId;
	
	public static String getDepartmentRootId() {
		return departmentRootId;
	}

	// 设置静态属性值，不能用静态方法，否则Springboot 不会调用
	/**
	 * 部门根ID
	 * @param departmentRootId
	 */
	@Value("${department.root.id}")
	public void setDepartmentRootId(String departmentRootId) {
		SysConfig.departmentRootId = departmentRootId;
	}


	public static String getUnauthorized() {
		return unauthorized;
	}

	@Value("${code.unauthorized}")
	public void setUnauthorized(String unauthorized) {
		SysConfig.unauthorized = unauthorized;
	}


	public static String getSucess() {
		return sucess;
	}

	@Value("${code.success}")
	public void setSucess(String sucess) {
		SysConfig.sucess = sucess;
	}


	public static String getLoginPath() {
		return loginPath;
	}

	@Value("${login.path}")
	public void setLoginPath(String loginPath) {
		SysConfig.loginPath = loginPath;
	}

	public static String getSyncPathPublic() {
		return syncPathPublic;
	}


	
	@Value("${sync.path.public}")
	public void setSyncPathPublic(String syncPathPublic) {
		SysConfig.syncPathPublic = syncPathPublic;
	}
	
	public static String getSyncPathPrivate() {
		return syncPathPrivate;
	}
	
	@Value("${sync.path.private}")
	public void setSyncPath(String syncPathPrivate) {
		SysConfig.syncPathPrivate = syncPathPrivate;
	}
	

	public static String getSingleQuotationMark() {
		return singleQuotationMark;
	}

	@Value("${single.quotation.mark}")
	public void setSingleQuotationMark(String singleQuotationMark) {
		SysConfig.singleQuotationMark = singleQuotationMark;
	}


	public static String getNullDate() {
		return nullDate;
	}

	@Value("${data.null}")
	public void setNullDate(String nullDate) {
		SysConfig.nullDate = nullDate;
	}

	public static String getAllowOrigin() {
		return allowOrigin;
	}

	@Value("${access.control.allow.origin}")
	public void setAllowOrigin(String allowOrigin) {
		SysConfig.allowOrigin = allowOrigin;
	}

	private static String unauthorized;
	
	
	private static String sucess ;
	
	
	private static String loginPath;

	private static String syncPathPublic;
	private static String syncPathPrivate;
	
	
	private static String singleQuotationMark;
	
	
	private static String nullDate;

	
	private static String allowOrigin;
	
}
