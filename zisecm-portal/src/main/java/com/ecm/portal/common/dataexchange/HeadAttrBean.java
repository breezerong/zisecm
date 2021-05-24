package com.ecm.portal.common.dataexchange;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


public class HeadAttrBean {
	
	public int getRowIndex() {
		return rowIndex;
	}
	@XmlAttribute(name="rowIndex")
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColumIndex() {
		return columIndex;
	}
	@XmlAttribute(name="columIndex")
	public void setColumIndex(int columIndex) {
		this.columIndex = columIndex;
	}
	public String getAttrName() {
		return attrName;
	}
	@XmlAttribute(name="attrName")
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	
	
	private int rowIndex;
	
	private int columIndex;
	
	private String attrName;
	
	

}
