/**
 * FCRDataMessageBody.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FCRDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FCRDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class FCRDataMessageBody  implements java.io.Serializable {
    private java.lang.String code;

    private java.lang.String revision;

    private java.lang.String specialty;

    private java.lang.String productCode;

    private java.lang.String unit;

    private java.lang.String system;

    private java.lang.String building;

    private java.lang.String subItem;

    private java.lang.String room;

    private java.lang.String publishUnit;

    private java.lang.String publishTime;

    private java.lang.String dipsCode;

    private java.lang.String dutyUnit;

    private java.lang.String changeReason;

    private java.lang.String isReply;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList[] fileList;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment[] attachments;

    private java.lang.String closeStatus;

    private java.lang.String closeDate;

    private java.lang.String area;

    private java.lang.String level;

    private java.lang.String theme;

    private java.lang.String devNum;

    private java.lang.String changeSolution;

    public FCRDataMessageBody() {
    }

    public FCRDataMessageBody(
           java.lang.String code,
           java.lang.String revision,
           java.lang.String specialty,
           java.lang.String productCode,
           java.lang.String unit,
           java.lang.String system,
           java.lang.String building,
           java.lang.String subItem,
           java.lang.String room,
           java.lang.String publishUnit,
           java.lang.String publishTime,
           java.lang.String dipsCode,
           java.lang.String dutyUnit,
           java.lang.String changeReason,
           java.lang.String isReply,
           org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList[] fileList,
           org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment[] attachments,
           java.lang.String closeStatus,
           java.lang.String closeDate,
           java.lang.String area,
           java.lang.String level,
           java.lang.String theme,
           java.lang.String devNum,
           java.lang.String changeSolution) {
           this.code = code;
           this.revision = revision;
           this.specialty = specialty;
           this.productCode = productCode;
           this.unit = unit;
           this.system = system;
           this.building = building;
           this.subItem = subItem;
           this.room = room;
           this.publishUnit = publishUnit;
           this.publishTime = publishTime;
           this.dipsCode = dipsCode;
           this.dutyUnit = dutyUnit;
           this.changeReason = changeReason;
           this.isReply = isReply;
           this.fileList = fileList;
           this.attachments = attachments;
           this.closeStatus = closeStatus;
           this.closeDate = closeDate;
           this.area = area;
           this.level = level;
           this.theme = theme;
           this.devNum = devNum;
           this.changeSolution = changeSolution;
    }


    /**
     * Gets the code value for this FCRDataMessageBody.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this FCRDataMessageBody.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the revision value for this FCRDataMessageBody.
     * 
     * @return revision
     */
    public java.lang.String getRevision() {
        return revision;
    }


    /**
     * Sets the revision value for this FCRDataMessageBody.
     * 
     * @param revision
     */
    public void setRevision(java.lang.String revision) {
        this.revision = revision;
    }


    /**
     * Gets the specialty value for this FCRDataMessageBody.
     * 
     * @return specialty
     */
    public java.lang.String getSpecialty() {
        return specialty;
    }


    /**
     * Sets the specialty value for this FCRDataMessageBody.
     * 
     * @param specialty
     */
    public void setSpecialty(java.lang.String specialty) {
        this.specialty = specialty;
    }


    /**
     * Gets the productCode value for this FCRDataMessageBody.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this FCRDataMessageBody.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the unit value for this FCRDataMessageBody.
     * 
     * @return unit
     */
    public java.lang.String getUnit() {
        return unit;
    }


    /**
     * Sets the unit value for this FCRDataMessageBody.
     * 
     * @param unit
     */
    public void setUnit(java.lang.String unit) {
        this.unit = unit;
    }


    /**
     * Gets the system value for this FCRDataMessageBody.
     * 
     * @return system
     */
    public java.lang.String getSystem() {
        return system;
    }


    /**
     * Sets the system value for this FCRDataMessageBody.
     * 
     * @param system
     */
    public void setSystem(java.lang.String system) {
        this.system = system;
    }


    /**
     * Gets the building value for this FCRDataMessageBody.
     * 
     * @return building
     */
    public java.lang.String getBuilding() {
        return building;
    }


    /**
     * Sets the building value for this FCRDataMessageBody.
     * 
     * @param building
     */
    public void setBuilding(java.lang.String building) {
        this.building = building;
    }


    /**
     * Gets the subItem value for this FCRDataMessageBody.
     * 
     * @return subItem
     */
    public java.lang.String getSubItem() {
        return subItem;
    }


    /**
     * Sets the subItem value for this FCRDataMessageBody.
     * 
     * @param subItem
     */
    public void setSubItem(java.lang.String subItem) {
        this.subItem = subItem;
    }


    /**
     * Gets the room value for this FCRDataMessageBody.
     * 
     * @return room
     */
    public java.lang.String getRoom() {
        return room;
    }


    /**
     * Sets the room value for this FCRDataMessageBody.
     * 
     * @param room
     */
    public void setRoom(java.lang.String room) {
        this.room = room;
    }


    /**
     * Gets the publishUnit value for this FCRDataMessageBody.
     * 
     * @return publishUnit
     */
    public java.lang.String getPublishUnit() {
        return publishUnit;
    }


    /**
     * Sets the publishUnit value for this FCRDataMessageBody.
     * 
     * @param publishUnit
     */
    public void setPublishUnit(java.lang.String publishUnit) {
        this.publishUnit = publishUnit;
    }


    /**
     * Gets the publishTime value for this FCRDataMessageBody.
     * 
     * @return publishTime
     */
    public java.lang.String getPublishTime() {
        return publishTime;
    }


    /**
     * Sets the publishTime value for this FCRDataMessageBody.
     * 
     * @param publishTime
     */
    public void setPublishTime(java.lang.String publishTime) {
        this.publishTime = publishTime;
    }


    /**
     * Gets the dipsCode value for this FCRDataMessageBody.
     * 
     * @return dipsCode
     */
    public java.lang.String getDipsCode() {
        return dipsCode;
    }


    /**
     * Sets the dipsCode value for this FCRDataMessageBody.
     * 
     * @param dipsCode
     */
    public void setDipsCode(java.lang.String dipsCode) {
        this.dipsCode = dipsCode;
    }


    /**
     * Gets the dutyUnit value for this FCRDataMessageBody.
     * 
     * @return dutyUnit
     */
    public java.lang.String getDutyUnit() {
        return dutyUnit;
    }


    /**
     * Sets the dutyUnit value for this FCRDataMessageBody.
     * 
     * @param dutyUnit
     */
    public void setDutyUnit(java.lang.String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }


    /**
     * Gets the changeReason value for this FCRDataMessageBody.
     * 
     * @return changeReason
     */
    public java.lang.String getChangeReason() {
        return changeReason;
    }


    /**
     * Sets the changeReason value for this FCRDataMessageBody.
     * 
     * @param changeReason
     */
    public void setChangeReason(java.lang.String changeReason) {
        this.changeReason = changeReason;
    }


    /**
     * Gets the isReply value for this FCRDataMessageBody.
     * 
     * @return isReply
     */
    public java.lang.String getIsReply() {
        return isReply;
    }


    /**
     * Sets the isReply value for this FCRDataMessageBody.
     * 
     * @param isReply
     */
    public void setIsReply(java.lang.String isReply) {
        this.isReply = isReply;
    }


    /**
     * Gets the fileList value for this FCRDataMessageBody.
     * 
     * @return fileList
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList[] getFileList() {
        return fileList;
    }


    /**
     * Sets the fileList value for this FCRDataMessageBody.
     * 
     * @param fileList
     */
    public void setFileList(org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList[] fileList) {
        this.fileList = fileList;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList getFileList(int i) {
        return this.fileList[i];
    }

    public void setFileList(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.FCRFileList _value) {
        this.fileList[i] = _value;
    }


    /**
     * Gets the attachments value for this FCRDataMessageBody.
     * 
     * @return attachments
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment[] getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this FCRDataMessageBody.
     * 
     * @param attachments
     */
    public void setAttachments(org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment[] attachments) {
        this.attachments = attachments;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment getAttachments(int i) {
        return this.attachments[i];
    }

    public void setAttachments(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.FCRAttachment _value) {
        this.attachments[i] = _value;
    }


    /**
     * Gets the closeStatus value for this FCRDataMessageBody.
     * 
     * @return closeStatus
     */
    public java.lang.String getCloseStatus() {
        return closeStatus;
    }


    /**
     * Sets the closeStatus value for this FCRDataMessageBody.
     * 
     * @param closeStatus
     */
    public void setCloseStatus(java.lang.String closeStatus) {
        this.closeStatus = closeStatus;
    }


    /**
     * Gets the closeDate value for this FCRDataMessageBody.
     * 
     * @return closeDate
     */
    public java.lang.String getCloseDate() {
        return closeDate;
    }


    /**
     * Sets the closeDate value for this FCRDataMessageBody.
     * 
     * @param closeDate
     */
    public void setCloseDate(java.lang.String closeDate) {
        this.closeDate = closeDate;
    }


    /**
     * Gets the area value for this FCRDataMessageBody.
     * 
     * @return area
     */
    public java.lang.String getArea() {
        return area;
    }


    /**
     * Sets the area value for this FCRDataMessageBody.
     * 
     * @param area
     */
    public void setArea(java.lang.String area) {
        this.area = area;
    }


    /**
     * Gets the level value for this FCRDataMessageBody.
     * 
     * @return level
     */
    public java.lang.String getLevel() {
        return level;
    }


    /**
     * Sets the level value for this FCRDataMessageBody.
     * 
     * @param level
     */
    public void setLevel(java.lang.String level) {
        this.level = level;
    }


    /**
     * Gets the theme value for this FCRDataMessageBody.
     * 
     * @return theme
     */
    public java.lang.String getTheme() {
        return theme;
    }


    /**
     * Sets the theme value for this FCRDataMessageBody.
     * 
     * @param theme
     */
    public void setTheme(java.lang.String theme) {
        this.theme = theme;
    }


    /**
     * Gets the devNum value for this FCRDataMessageBody.
     * 
     * @return devNum
     */
    public java.lang.String getDevNum() {
        return devNum;
    }


    /**
     * Sets the devNum value for this FCRDataMessageBody.
     * 
     * @param devNum
     */
    public void setDevNum(java.lang.String devNum) {
        this.devNum = devNum;
    }


    /**
     * Gets the changeSolution value for this FCRDataMessageBody.
     * 
     * @return changeSolution
     */
    public java.lang.String getChangeSolution() {
        return changeSolution;
    }


    /**
     * Sets the changeSolution value for this FCRDataMessageBody.
     * 
     * @param changeSolution
     */
    public void setChangeSolution(java.lang.String changeSolution) {
        this.changeSolution = changeSolution;
    }

    

}
