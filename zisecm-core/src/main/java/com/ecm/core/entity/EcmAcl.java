package com.ecm.core.entity;


public class EcmAcl extends EcmSysObject {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeName;
    
    public EcmAcl() {
    	fields ="ID,NAME,DESCRIPTION,CREATION_DATE,CREATOR,TYPE_NAME,MODIFIER,MODIFIED_DATE";
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}