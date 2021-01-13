package com.ecm.portal.archive.entity;

import java.util.HashMap;
import java.util.Map;

public class EcmCheck4 {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		this.attributes.put("ID", id);
	}
	public String getCoding() {
		return coding;
	}
	public void setCoding(String coding) {
		this.coding = coding;
		this.attributes.put("CODING", coding);
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
		this.attributes.put("REVISION", revision);
	}
	public String getArchiveCoding() {
		return archiveCoding;
	}
	public void setArchiveCoding(String archiveCoding) {
		this.archiveCoding = archiveCoding;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		this.attributes.put("TITLE", title);
	}
	public String getTrueSource() {
		return trueSource;
	}
	public void setTrueSource(String trueSource) {
		this.trueSource = trueSource;
		this.attributes.put("trueSource", trueSource);
	}
	public String getTrueMetaData() {
		return trueMetaData;
	}
	public void setTrueMetaData(String trueMetaData) {
		this.trueMetaData = trueMetaData;
		this.attributes.put("trueMetaData", trueMetaData);
	}
	public String getTrueContent() {
		return trueContent;
	}
	public void setTrueContent(String trueContent) {
		this.trueContent = trueContent;
		this.attributes.put("trueContent", trueContent);
	}
	public String getIntegrityMetaData() {
		return integrityMetaData;
	}
	public void setIntegrityMetaData(String integrityMetaData) {
		this.integrityMetaData = integrityMetaData;
		this.attributes.put("integrityMetaData", integrityMetaData);
	}
	public String getIntegrityContent() {
		return integrityContent;
	}
	public void setIntegrityContent(String integrityContent) {
		this.integrityContent = integrityContent;
		this.attributes.put("integrityContent", integrityContent);
	}
	public String getIntegrityPackage() {
		return integrityPackage;
	}
	public void setIntegrityPackage(String integrityPackage) {
		this.integrityPackage = integrityPackage;
		this.attributes.put("integrityPackage", integrityPackage);
	}
	public String getUseMetaData() {
		return useMetaData;
	}
	public void setUseMetaData(String useMetaData) {
		this.useMetaData = useMetaData;
		this.attributes.put("useMetaData", useMetaData);
	}
	public String getUseContent() {
		return useContent;
	}
	public void setUseContent(String useContent) {
		this.useContent = useContent;
		this.attributes.put("useContent", useContent);
	}
	public String getSecurityVirus() {
		return securityVirus;
	}
	public void setSecurityVirus(String securityVirus) {
		this.securityVirus = securityVirus;
		this.attributes.put("securityVirus", securityVirus);
	}
	public String getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(String totalResult) {
		this.totalResult = totalResult;
		this.attributes.put("totalResult", totalResult);
	}
	
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}


	private String id;
	private String coding;
	private String revision;
	private String archiveCoding;
	private String title;
	private String trueSource="导入";
	private String trueMetaData="通过";
	private String trueContent="";
	private String integrityMetaData="通过";
	private String integrityContent="通过";
	private String integrityPackage="通过";
	private String useMetaData="通过";
	private String useContent ="通过";
	private String securityVirus ="是";
	private String totalResult = "通过";
	
	private Map<String, String> attributes=new HashMap<>();
}
