package com.ecm.cnpe.exchange.controller.param;

public class DocParam {
	
	private String gridName;
	
	private Boolean isCustom;
	
	private String lang;
	
	private String condition;
	
	private String folderId;
	
	private String filename;
	
	private String sheetname;
	
	private String orderBy;
	
	private String parentId;
	
	
	public Boolean getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getGridName() {
		return gridName;
	}
	
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getFilename() {
		return filename;
	}
	
	
	public String getFolderId() {
		return folderId;
	}
	
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getSheetname() {
		return sheetname;
	}
	
	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
