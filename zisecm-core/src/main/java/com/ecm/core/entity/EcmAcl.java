package com.ecm.core.entity;

/**
 * ACL
 * @author Haihong Rong
 * @date 2019年10月20日 下午4:57:25
 */
public class EcmAcl extends EcmSysObject {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	 */
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