package com.ecm.core.entity;

public class SyncUserIpBean {
	
	private String keyid; 
	private String ip; 
	private String code;
	private String secretlevel;
	private String message;
	
	
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSecretlevel() {
		return secretlevel;
	}
	public void setSecretlevel(String secretlevel) {
		this.secretlevel = secretlevel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	} 
	
	
	
	
}
