package com.ecm.core.entity;

import java.util.Date;

public class EcmAuditPermission extends EcmObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

    private String objId;

    private String formId;

    private Date permissionDate;

    private Date expireDate;

    private Boolean status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Date getPermissionDate() {
        return permissionDate;
    }

    public void setPermissionDate(Date permissionDate) {
        this.permissionDate = permissionDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}