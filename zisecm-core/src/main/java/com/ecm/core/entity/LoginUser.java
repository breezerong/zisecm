/**
 * 
 */
package com.ecm.core.entity;

import java.util.Date;


/**
 * @ClassName  User   
 * @Description TODO(用户)   
 * @author Haihong Rong
 * @date 2018年6月28日 下午4:37:36  
 *
 */
public class LoginUser {
	//用户ID
	private String userId;
	//用户名
	private String userName;
	//登录户名
	private String loginName;
	//登录时间
	private Date loginTime;
	//个人文件柜ID
	private String personalWSId;
	
	private int systemPermission;
	/**
	 * 用户部门
	 */
	private String department;

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	//客户端权限，1:查询用户，2:新建文件，3:执行流程，4:文件管理员，9:系统管理员
	private int clientPermission=1;
	/*
	 * 应用名称
	 */
	private String appName;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * @return the personalWSId
	 */
	public String getPersonalWSId() {
		return personalWSId;
	}
	/**
	 * @param personalWSId the personalWSId to set
	 */
	public void setPersonalWSId(String personalWSId) {
		this.personalWSId = personalWSId;
	}
	
	public int getSystemPermission() {
		return systemPermission;
	}
	public void setSystemPermission(int systemPermission) {
		this.systemPermission = systemPermission;
	}
	public int getClientPermission() {
		return clientPermission;
	}
	public void setClientPermission(int clientPermission) {
		this.clientPermission = clientPermission;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
