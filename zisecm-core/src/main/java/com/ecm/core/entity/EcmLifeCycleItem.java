package com.ecm.core.entity;

public class EcmLifeCycleItem {

	private String id;
	private String name;
	private String description;
	private String previousName;
	private String nextName;
	private String previousEventName;
	private String nextEventName;
	private String lifecycleId;
	
	
	
	public String getLifecycleId() {
		return lifecycleId;
	}
	public void setLifecycleId(String lifecycleId) {
		this.lifecycleId = lifecycleId;
	}
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
	public String getPreviousName() {
		return previousName;
	}
	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}
	public String getNextName() {
		return nextName;
	}
	public void setNextName(String nextName) {
		this.nextName = nextName;
	}
	public String getPreviousEventName() {
		return previousEventName;
	}
	public void setPreviousEventName(String previousEventName) {
		this.previousEventName = previousEventName;
	}
	public String getNextEventName() {
		return nextEventName;
	}
	public void setNextEventName(String nextEventName) {
		this.nextEventName = nextEventName;
	}
	
	

}
