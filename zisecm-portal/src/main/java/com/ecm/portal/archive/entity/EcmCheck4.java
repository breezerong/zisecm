package com.ecm.portal.archive.entity;

public class EcmCheck4 {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCoding() {
		return coding;
	}
	public void setCoding(String coding) {
		this.coding = coding;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
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
	}
	public String getTrueSource() {
		return trueSource;
	}
	public void setTrueSource(String trueSource) {
		this.trueSource = trueSource;
	}
	public String getTrueMetaData() {
		return trueMetaData;
	}
	public void setTrueMetaData(String trueMetaData) {
		this.trueMetaData = trueMetaData;
	}
	public String getTrueContent() {
		return trueContent;
	}
	public void setTrueContent(String trueContent) {
		this.trueContent = trueContent;
	}
	public String getIntegrityMetaData() {
		return integrityMetaData;
	}
	public void setIntegrityMetaData(String integrityMetaData) {
		this.integrityMetaData = integrityMetaData;
	}
	public String getIntegrityContent() {
		return integrityContent;
	}
	public void setIntegrityContent(String integrityContent) {
		this.integrityContent = integrityContent;
	}
	public String getIntegrityPackage() {
		return integrityPackage;
	}
	public void setIntegrityPackage(String integrityPackage) {
		this.integrityPackage = integrityPackage;
	}
	public String getUseMetaData() {
		return useMetaData;
	}
	public void setUseMetaData(String useMetaData) {
		this.useMetaData = useMetaData;
	}
	public String getUseContent() {
		return useContent;
	}
	public void setUseContent(String useContent) {
		this.useContent = useContent;
	}
	public String getSecurityVirus() {
		return securityVirus;
	}
	public void setSecurityVirus(String securityVirus) {
		this.securityVirus = securityVirus;
	}
	public String getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(String totalResult) {
		this.totalResult = totalResult;
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
}
