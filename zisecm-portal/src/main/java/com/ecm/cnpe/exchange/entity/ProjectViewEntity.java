package com.ecm.cnpe.exchange.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ecm.cnpe.exchange.controller.ProjectView;

public class ProjectViewEntity {
	private String name;
	private String code;
	private List<ProjectViewEntity> children=new ArrayList<ProjectViewEntity>();
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
	public List<ProjectViewEntity> getChildren() {
		return children;
	}
	public void setChildren(List<ProjectViewEntity> children) {
		this.children = children;
	}
	
	public void addChild(ProjectViewEntity child) {
		this.children.add(child);
	}
	
	public void setChildrenValue(String[] vals) {
		for(String val : vals) {
			ProjectViewEntity np=new ProjectViewEntity();
			np.setName(val);
			this.children.add(np);
		}
	}
}
