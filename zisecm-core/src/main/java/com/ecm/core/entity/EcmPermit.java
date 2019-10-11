package com.ecm.core.entity;

import java.util.Date;

public class EcmPermit extends EcmObject{
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String targetName;
    
    private int targetType=1;

    private Integer permission;

    private String parentId;

    private Date expireDate;

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName == null ? null : targetName.trim();
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

	public int getTargetType() {
		return targetType;
	}
	/**
	 * 
	 * @param targetType 1: user, 2:group
	 */
	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}
}