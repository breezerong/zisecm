package org.zisecm.jobs.entity;

public class FileInfo {
	private String objId;
	private String name;
	private String url;
	private long size;
	private String wjbm;
	private String revision;
	
	
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getWjbm() {
		return wjbm;
	}
	public void setWjbm(String wjbm) {
		this.wjbm = wjbm;
	}
	
	
}
