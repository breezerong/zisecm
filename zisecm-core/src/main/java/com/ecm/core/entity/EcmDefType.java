package com.ecm.core.entity;


public class EcmDefType extends EcmSysObject{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer status;

    private String storeName;

    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }
}