package org.zisecm.jobs.entity;

import java.util.ArrayList;
import java.util.List;

public class WsService {
	private String name;
	private List<WsEntity> list;
	
	public WsService(String name){
		this.name = name;
		this.list = new ArrayList<WsEntity>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<WsEntity> getList() {
		return list;
	}
	public void setList(List<WsEntity> list) {
		this.list = list;
	}
	
}
