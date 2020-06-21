package com.ecm.core.entity;

public class EcmStore extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer storeType;

    private String storeClass;

    private String storePath;
    
    private boolean isEncrypt;

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String getStoreClass() {
        return storeClass;
    }

    public void setStoreClass(String storeClass) {
        this.storeClass = storeClass == null ? null : storeClass.trim();
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath == null ? null : storePath.trim();
    }

	public boolean isEncrypt() {
		return isEncrypt;
	}

	public void setEncrypt(boolean isEncrypt) {
		this.isEncrypt = isEncrypt;
	}
}