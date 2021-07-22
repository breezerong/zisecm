/**
 * 
 */
package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
	
	//更新时间
	private Date updateTime;
	//个人文件柜ID
	private String personalWSId;
	
	private int systemPermission;
	// Token
	private String token;
	//登录IP
	private String loginIp;
	    

	
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	/**
	 * 用户部门
	 */
	private String department;
	private String groupName;
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }



	/**
	 * 公司
	 */
	private String company;
	
	/**
	 * 公司代码
	 */
	private String companyCode;
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyCode1() {
		return companyCode1;
	}
	public void setCompanyCode1(String companyCode1) {
		this.companyCode1 = companyCode1;
	}
	/**
	 * 短码
	 */
	private String companyCode1;
	
	/**
	 * 用户类型，1：总包商，2：分包商
	 */
	private int userType=0;
	
	/**
	 * 用户角色
	 */
	private List<String> roles = new ArrayList<String>();
	
	/**
	 * 我的项目
	 */
	private List<String> myProjects = new ArrayList<String>();

	public String getDepartment() {
		String departmentTemp = "";
		if(this.getGroupName()!=null)
		  departmentTemp =this.getGroupName();
		departmentTemp=departmentTemp.split("/")[0];
		return departmentTemp;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	//客户端权限，1:查询用户，2:新建文件，3:执行流程，4:文件管理员，5:系统管理员, 6:日志管理员
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getMyProjects() {
		return myProjects;
	}
	public void setMyProjects(List<String> myProjects) {
		this.myProjects = myProjects;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
