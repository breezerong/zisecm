package com.ecm.core.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class EcmObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 对象ID
	 */
	private String id;
	
	
	/**
	 * Session
	 */
	private EcmSession session;
	
	protected String fields;
	
	
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public EcmSession getSession() {
		return session;
	}
	public void setSession(EcmSession session) {
		this.session = session;
	}
	/**
	 * 创建UUID
	 */
	public void createId() {
		id = UUID.randomUUID().toString().replace("-", "");
	}

}
