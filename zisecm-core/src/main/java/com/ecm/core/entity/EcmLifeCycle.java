package com.ecm.core.entity;

public class EcmLifeCycle {
	private String id;
	private String name;
	private String description;
	private String startStatus;
	private String endStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartStatus() {
		return startStatus;
	}
	public void setStartStatus(String startStatus) {
		this.startStatus = startStatus;
	}
	public String getEndStatus() {
		return endStatus;
	}
	public void setEndStatus(String endStatus) {
		this.endStatus = endStatus;
	}
	
}
