package com.ecm.core.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AggregationEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private Map<String,Long> groups = new HashMap<String, Long>();
	public Map<String,Long> getGroups() {
		return groups;
	}
	public void setGroups(Map<String,Long> groups) {
		this.groups = groups;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
