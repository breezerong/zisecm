package com.ecm.core.entity;


public class EcmDefType extends EcmSysObject{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer status;

    private String storeName;
    
    private String typeTag;
    
    private String gridName;

    
    public String getTypeTag() {
		return typeTag;
	}

	public void setTypeTag(String typeTag) {
		this.typeTag = typeTag;
	}

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

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
}