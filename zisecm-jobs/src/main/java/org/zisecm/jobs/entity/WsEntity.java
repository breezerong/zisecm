package org.zisecm.jobs.entity;

import java.util.Map;

public class WsEntity {
	private String name;
	private Map<String,Object> defaultValue;
	private Map<String,Object> mapper;
	public WsEntity() {
		
	}
	
	public WsEntity(String name,Map<String,Object> dValue,Map<String,Object> mapper){
		this.name = name;
		this.defaultValue = dValue;
		this.mapper = mapper;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(Map<String, Object> defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Map<String, Object> getMapper() {
		return mapper;
	}
	public void setMapper(Map<String, Object> mapper) {
		this.mapper = mapper;
	}
	
	
}
