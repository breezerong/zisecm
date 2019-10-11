package com.ecm.core.entity;


public class EcmWorkflow extends EcmSysObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer status;

    private String docId;

    private String formId;

    private String processId;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}