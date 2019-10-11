package com.ecm.core.entity;

public class EcmParameter extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}