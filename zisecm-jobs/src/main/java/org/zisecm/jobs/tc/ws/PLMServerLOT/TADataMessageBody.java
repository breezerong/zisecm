/**
 * CRDataMessageBody.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
//PLMServerLOT.ws.cnpe.com

@XmlRootElement(name="TADataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="TADataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class TADataMessageBody  implements java.io.Serializable {
	
	
	
    private java.lang.String projectid;

    private java.lang.String revision;

    private java.lang.String theme;

    private java.lang.String fileID;

    private java.lang.String discipline;

    private java.lang.String correspLetterRecNo;

    private java.lang.String type;

   // private java.lang.String isReply;

    private java.lang.String authorUnit;

    private String pages;
    
    private String unit;
    
    private java.lang.String system;

    private java.lang.String buildingCode;

    private java.lang.String subItem;

    private java.lang.String room;

    private java.lang.String isReply;


    private java.lang.String sendDate;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.TAFileList[] taList;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.TAAttachment[] attachments;

    private java.lang.String areaCode;

    private java.lang.String levelCode;

    private java.lang.String reason;

    private java.lang.String devNum;
    
    private java.lang.String closeStatus;

    private java.lang.String closeDate;
    
    private String sendLotDate;
    
    private String sendLotNo;

    public String getSendLotDate() {
		return sendLotDate;
	}

	public void setSendLotDate(String sendLotDate) {
		this.sendLotDate = sendLotDate;
	}

	public String getSendLotNo() {
		return sendLotNo;
	}

	public void setSendLotNo(String sendLotNo) {
		this.sendLotNo = sendLotNo;
	}

	public java.lang.String getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(java.lang.String closeStatus) {
		this.closeStatus = closeStatus;
	}

	public java.lang.String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(java.lang.String closeDate) {
		this.closeDate = closeDate;
	}

	public TADataMessageBody() {
    }

	

	public TADataMessageBody(String projectid, String revision, String theme,
			String fileID, String discipline, String correspLetterRecNo,
			String type, String authorUnit, String pages, String unit,
			String system, String buildingCode, String subItem, String room,
			String isReply, String sendDate, TAFileList[] taList,
			TAAttachment[] attachments, String areaCode, String levelCode,
			String reason, String devNum, String closeStatus, String closeDate) {
		super();
		this.projectid = projectid;
		this.revision = revision;
		this.theme = theme;
		this.fileID = fileID;
		this.discipline = discipline;
		this.correspLetterRecNo = correspLetterRecNo;
		this.type = type;
		this.authorUnit = authorUnit;
		this.pages = pages;
		this.unit = unit;
		this.system = system;
		this.buildingCode = buildingCode;
		this.subItem = subItem;
		this.room = room;
		this.isReply = isReply;
		this.sendDate = sendDate;
		this.taList = taList;
		this.attachments = attachments;
		this.areaCode = areaCode;
		this.levelCode = levelCode;
		this.reason = reason;
		this.devNum = devNum;
		this.closeStatus = closeStatus;
		this.closeDate = closeDate;
	}

	public java.lang.String getProjectid() {
		return projectid;
	}

	public void setProjectid(java.lang.String projectid) {
		this.projectid = projectid;
	}

	public java.lang.String getRevision() {
		return revision;
	}

	public void setRevision(java.lang.String revision) {
		this.revision = revision;
	}

	public java.lang.String getTheme() {
		return theme;
	}

	public void setTheme(java.lang.String theme) {
		this.theme = theme;
	}

	public java.lang.String getFileID() {
		return fileID;
	}

	public void setFileID(java.lang.String fileID) {
		this.fileID = fileID;
	}

	public java.lang.String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(java.lang.String discipline) {
		this.discipline = discipline;
	}

	public java.lang.String getCorrespLetterRecNo() {
		return correspLetterRecNo;
	}

	public void setCorrespLetterRecNo(java.lang.String correspLetterRecNo) {
		this.correspLetterRecNo = correspLetterRecNo;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getAuthorUnit() {
		return authorUnit;
	}

	public void setAuthorUnit(java.lang.String authorUnit) {
		this.authorUnit = authorUnit;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public java.lang.String getSystem() {
		return system;
	}

	public void setSystem(java.lang.String system) {
		this.system = system;
	}

	public java.lang.String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(java.lang.String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public java.lang.String getSubItem() {
		return subItem;
	}

	public void setSubItem(java.lang.String subItem) {
		this.subItem = subItem;
	}

	public java.lang.String getRoom() {
		return room;
	}

	public void setRoom(java.lang.String room) {
		this.room = room;
	}

	public java.lang.String getIsReply() {
		return isReply;
	}

	public void setIsReply(java.lang.String isReply) {
		this.isReply = isReply;
	}

	public java.lang.String getSendDate() {
		return sendDate;
	}

	public void setSendDate(java.lang.String sendDate) {
		this.sendDate = sendDate;
	}

	public org.zisecm.jobs.tc.ws.PLMServerLOT.TAFileList[] getTaList() {
		return taList;
	}

	public void setTaList(org.zisecm.jobs.tc.ws.PLMServerLOT.TAFileList[] taList) {
		this.taList = taList;
	}

	public org.zisecm.jobs.tc.ws.PLMServerLOT.TAAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(org.zisecm.jobs.tc.ws.PLMServerLOT.TAAttachment[] attachments) {
		this.attachments = attachments;
	}

	public java.lang.String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}

	public java.lang.String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(java.lang.String levelCode) {
		this.levelCode = levelCode;
	}

	public java.lang.String getReason() {
		return reason;
	}

	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}

	public java.lang.String getDevNum() {
		return devNum;
	}

	public void setDevNum(java.lang.String devNum) {
		this.devNum = devNum;
	}

   

}
