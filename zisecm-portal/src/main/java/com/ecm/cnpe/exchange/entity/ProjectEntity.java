package com.ecm.cnpe.exchange.entity;

import java.io.Serializable;

public class ProjectEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8804516439829660326L;
	private String name;
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}