package com.ecm.core.entity;

import java.util.Date;

public class EcmAuditWorkflow extends EcmSysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startUser;

	public String getStartUser() {
		return startUser;
	}

	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	private String processName;

    private String processInstanceId;

    private String processInstanceName;
    
    private String processDefId;

    private Date startTime;

    private Date endTime;

    private String docId;

    private String formId;
    
    private String currentUser;

    
    public EcmAuditWorkflow() {
    	fields = "ID,START_USER,PROCESS_NAME,PROCESS_INSTANCE_ID,PROCESS_INSTANCE_NAME,CREATE_TIME,END_TIME," + 
    			"DOC_ID,FORM_ID,DESCRIPTION";
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName == null ? null : processInstanceName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
}