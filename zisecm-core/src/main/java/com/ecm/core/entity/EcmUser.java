package com.ecm.core.entity;

import java.util.Date;

public class EcmUser extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String loginName;

    private String phone;

    private String email;

    private Boolean isActived;

    private String groupName;

    private String password;
    
    private String passwordConfirm;

    private String groupId;

    private Integer loginType;

    private String ldapCn;

    private Integer clientPermission;

    private Integer systemPermission;

    private String ldapName;

    private String extendId;

    private String extendGroupId;
    
    private String signImage;
    
    private String delegateUser;
    
    private Date delegateStart;
    
    private Date delegateEnd;
    
    private String CompanyName;
    
    private String departmentName;
    
    private String jobTitle;

    public String getSignImage() {
		return signImage;
	}

	public void setSignImage(String signImage) {
		this.signImage = signImage;
	}

	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getIsActived() {
        return isActived;
    }

    public void setIsActived(Boolean isActived) {
        this.isActived = isActived;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
        if(this.passwordConfirm == null) {
        	this.passwordConfirm = this.password;
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getLdapCn() {
        return ldapCn;
    }

    public void setLdapCn(String ldapCn) {
        this.ldapCn = ldapCn == null ? null : ldapCn.trim();
    }

    public Integer getClientPermission() {
        return clientPermission;
    }

    public void setClientPermission(Integer clientPermission) {
        this.clientPermission = clientPermission;
    }

    public Integer getSystemPermission() {
        return systemPermission;
    }

    public void setSystemPermission(Integer systemPermission) {
        this.systemPermission = systemPermission;
    }

    public String getLdapName() {
        return ldapName;
    }

    public void setLdapName(String ldapName) {
        this.ldapName = ldapName == null ? null : ldapName.trim();
    }

    public String getExtendId() {
        return extendId;
    }

    public void setExtendId(String extendId) {
        this.extendId = extendId == null ? null : extendId.trim();
    }

    public String getExtendGroupId() {
        return extendGroupId;
    }

    public void setExtendGroupId(String extendGroupId) {
        this.extendGroupId = extendGroupId == null ? null : extendGroupId.trim();
    }

	public Date getDelegateStart() {
		return delegateStart;
	}

	public void setDelegateStart(Date delegateStart) {
		this.delegateStart = delegateStart;
	}

	public String getDelegateUser() {
		return delegateUser;
	}

	public void setDelegateUser(String delegateUser) {
		this.delegateUser = delegateUser;
	}

	public Date getDelegateEnd() {
		return delegateEnd;
	}

	public void setDelegateEnd(Date delegateEnd) {
		this.delegateEnd = delegateEnd;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartmentName() {
		String department = "";
		if(this.getGroupName()!=null)
		  department =this.getGroupName();
		department=department.split("/")[0];
		return department;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}