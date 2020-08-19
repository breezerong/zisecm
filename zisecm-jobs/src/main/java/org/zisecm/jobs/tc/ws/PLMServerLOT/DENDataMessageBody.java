/**
 * DENDataMessageBody.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DENDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DENDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class DENDataMessageBody  implements java.io.Serializable {
    private java.lang.String project;

    private java.lang.String sendFrom;

    private java.lang.String DENNo;

    private java.lang.String revision;

    private java.lang.String DENType;

    private java.lang.String unitNo;

    private java.lang.String systemCode;

    private java.lang.String equipNo;

    private java.lang.String plantCode;

    private java.lang.String areaCode;

    private java.lang.String roomNo;

    private java.lang.String layerCode;

    private java.lang.String specialty;

    private java.lang.String DIPSCode;

    private java.lang.String senderChannelNo;

    private java.lang.String pages;

    private java.lang.String sendDate;

    private java.lang.String CCUnit;

    private java.lang.String title;

    private java.lang.String changeReason;

    private java.lang.String dutyUnit;

    private java.lang.String DENSourceNo;

    private java.lang.String changeDescribe;

    private java.lang.String DENConsequence;

    private java.lang.String author;

    private java.lang.String authorDate;

    private java.lang.String author2;

    private java.lang.String authorDate2;

    private java.lang.String checker;

    private java.lang.String checkDate;

    private java.lang.String reviewer;

    private java.lang.String reviewDate;

    private java.lang.String auditPerson;

    private java.lang.String auditDate;

    private java.lang.String approver;

    private java.lang.String approveDate;

    private java.lang.String jointSigner;

    private java.lang.String jointSignDate;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.DENFileList[] fileList;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.DENAddedMatList[] addedMatList;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.DENCancelledMatList[] cancelledMatList;

    private java.lang.String fieldRevConc;

    private java.lang.String fieldRevAdv;

    private java.lang.String fieldRevDate;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.DENAttachment[] attachments;

    public DENDataMessageBody() {
    }

    public DENDataMessageBody(
           java.lang.String project,
           java.lang.String sendFrom,
           java.lang.String DENNo,
           java.lang.String revision,
           java.lang.String DENType,
           java.lang.String unitNo,
           java.lang.String systemCode,
           java.lang.String equipNo,
           java.lang.String plantCode,
           java.lang.String areaCode,
           java.lang.String roomNo,
           java.lang.String layerCode,
           java.lang.String specialty,
           java.lang.String DIPSCode,
           java.lang.String senderChannelNo,
           java.lang.String pages,
           java.lang.String sendDate,
           java.lang.String CCUnit,
           java.lang.String title,
           java.lang.String changeReason,
           java.lang.String dutyUnit,
           java.lang.String DENSourceNo,
           java.lang.String changeDescribe,
           java.lang.String DENConsequence,
           java.lang.String author,
           java.lang.String authorDate,
           java.lang.String author2,
           java.lang.String authorDate2,
           java.lang.String checker,
           java.lang.String checkDate,
           java.lang.String reviewer,
           java.lang.String reviewDate,
           java.lang.String auditPerson,
           java.lang.String auditDate,
           java.lang.String approver,
           java.lang.String approveDate,
           java.lang.String jointSigner,
           java.lang.String jointSignDate,
           org.zisecm.jobs.tc.ws.PLMServerLOT.DENFileList[] fileList,
           org.zisecm.jobs.tc.ws.PLMServerLOT.DENAddedMatList[] addedMatList,
           org.zisecm.jobs.tc.ws.PLMServerLOT.DENCancelledMatList[] cancelledMatList,
           java.lang.String fieldRevConc,
           java.lang.String fieldRevAdv,
           java.lang.String fieldRevDate,
           org.zisecm.jobs.tc.ws.PLMServerLOT.DENAttachment[] attachments) {
           this.project = project;
           this.sendFrom = sendFrom;
           this.DENNo = DENNo;
           this.revision = revision;
           this.DENType = DENType;
           this.unitNo = unitNo;
           this.systemCode = systemCode;
           this.equipNo = equipNo;
           this.plantCode = plantCode;
           this.areaCode = areaCode;
           this.roomNo = roomNo;
           this.layerCode = layerCode;
           this.specialty = specialty;
           this.DIPSCode = DIPSCode;
           this.senderChannelNo = senderChannelNo;
           this.pages = pages;
           this.sendDate = sendDate;
           this.CCUnit = CCUnit;
           this.title = title;
           this.changeReason = changeReason;
           this.dutyUnit = dutyUnit;
           this.DENSourceNo = DENSourceNo;
           this.changeDescribe = changeDescribe;
           this.DENConsequence = DENConsequence;
           this.author = author;
           this.authorDate = authorDate;
           this.author2 = author2;
           this.authorDate2 = authorDate2;
           this.checker = checker;
           this.checkDate = checkDate;
           this.reviewer = reviewer;
           this.reviewDate = reviewDate;
           this.auditPerson = auditPerson;
           this.auditDate = auditDate;
           this.approver = approver;
           this.approveDate = approveDate;
           this.jointSigner = jointSigner;
           this.jointSignDate = jointSignDate;
           this.fileList = fileList;
           this.addedMatList = addedMatList;
           this.cancelledMatList = cancelledMatList;
           this.fieldRevConc = fieldRevConc;
           this.fieldRevAdv = fieldRevAdv;
           this.fieldRevDate = fieldRevDate;
           this.attachments = attachments;
    }


    /**
     * Gets the project value for this DENDataMessageBody.
     * 
     * @return project
     */
    public java.lang.String getProject() {
        return project;
    }


    /**
     * Sets the project value for this DENDataMessageBody.
     * 
     * @param project
     */
    public void setProject(java.lang.String project) {
        this.project = project;
    }


    /**
     * Gets the sendFrom value for this DENDataMessageBody.
     * 
     * @return sendFrom
     */
    public java.lang.String getSendFrom() {
        return sendFrom;
    }


    /**
     * Sets the sendFrom value for this DENDataMessageBody.
     * 
     * @param sendFrom
     */
    public void setSendFrom(java.lang.String sendFrom) {
        this.sendFrom = sendFrom;
    }


    /**
     * Gets the DENNo value for this DENDataMessageBody.
     * 
     * @return DENNo
     */
    public java.lang.String getDENNo() {
        return DENNo;
    }


    /**
     * Sets the DENNo value for this DENDataMessageBody.
     * 
     * @param DENNo
     */
    public void setDENNo(java.lang.String DENNo) {
        this.DENNo = DENNo;
    }


    /**
     * Gets the revision value for this DENDataMessageBody.
     * 
     * @return revision
     */
    public java.lang.String getRevision() {
        return revision;
    }


    /**
     * Sets the revision value for this DENDataMessageBody.
     * 
     * @param revision
     */
    public void setRevision(java.lang.String revision) {
        this.revision = revision;
    }


    /**
     * Gets the DENType value for this DENDataMessageBody.
     * 
     * @return DENType
     */
    public java.lang.String getDENType() {
        return DENType;
    }


    /**
     * Sets the DENType value for this DENDataMessageBody.
     * 
     * @param DENType
     */
    public void setDENType(java.lang.String DENType) {
        this.DENType = DENType;
    }


    /**
     * Gets the unitNo value for this DENDataMessageBody.
     * 
     * @return unitNo
     */
    public java.lang.String getUnitNo() {
        return unitNo;
    }


    /**
     * Sets the unitNo value for this DENDataMessageBody.
     * 
     * @param unitNo
     */
    public void setUnitNo(java.lang.String unitNo) {
        this.unitNo = unitNo;
    }


    /**
     * Gets the systemCode value for this DENDataMessageBody.
     * 
     * @return systemCode
     */
    public java.lang.String getSystemCode() {
        return systemCode;
    }


    /**
     * Sets the systemCode value for this DENDataMessageBody.
     * 
     * @param systemCode
     */
    public void setSystemCode(java.lang.String systemCode) {
        this.systemCode = systemCode;
    }


    /**
     * Gets the equipNo value for this DENDataMessageBody.
     * 
     * @return equipNo
     */
    public java.lang.String getEquipNo() {
        return equipNo;
    }


    /**
     * Sets the equipNo value for this DENDataMessageBody.
     * 
     * @param equipNo
     */
    public void setEquipNo(java.lang.String equipNo) {
        this.equipNo = equipNo;
    }


    /**
     * Gets the plantCode value for this DENDataMessageBody.
     * 
     * @return plantCode
     */
    public java.lang.String getPlantCode() {
        return plantCode;
    }


    /**
     * Sets the plantCode value for this DENDataMessageBody.
     * 
     * @param plantCode
     */
    public void setPlantCode(java.lang.String plantCode) {
        this.plantCode = plantCode;
    }


    /**
     * Gets the areaCode value for this DENDataMessageBody.
     * 
     * @return areaCode
     */
    public java.lang.String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this DENDataMessageBody.
     * 
     * @param areaCode
     */
    public void setAreaCode(java.lang.String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the roomNo value for this DENDataMessageBody.
     * 
     * @return roomNo
     */
    public java.lang.String getRoomNo() {
        return roomNo;
    }


    /**
     * Sets the roomNo value for this DENDataMessageBody.
     * 
     * @param roomNo
     */
    public void setRoomNo(java.lang.String roomNo) {
        this.roomNo = roomNo;
    }


    /**
     * Gets the layerCode value for this DENDataMessageBody.
     * 
     * @return layerCode
     */
    public java.lang.String getLayerCode() {
        return layerCode;
    }


    /**
     * Sets the layerCode value for this DENDataMessageBody.
     * 
     * @param layerCode
     */
    public void setLayerCode(java.lang.String layerCode) {
        this.layerCode = layerCode;
    }


    /**
     * Gets the specialty value for this DENDataMessageBody.
     * 
     * @return specialty
     */
    public java.lang.String getSpecialty() {
        return specialty;
    }


    /**
     * Sets the specialty value for this DENDataMessageBody.
     * 
     * @param specialty
     */
    public void setSpecialty(java.lang.String specialty) {
        this.specialty = specialty;
    }


    /**
     * Gets the DIPSCode value for this DENDataMessageBody.
     * 
     * @return DIPSCode
     */
    public java.lang.String getDIPSCode() {
        return DIPSCode;
    }


    /**
     * Sets the DIPSCode value for this DENDataMessageBody.
     * 
     * @param DIPSCode
     */
    public void setDIPSCode(java.lang.String DIPSCode) {
        this.DIPSCode = DIPSCode;
    }


    /**
     * Gets the senderChannelNo value for this DENDataMessageBody.
     * 
     * @return senderChannelNo
     */
    public java.lang.String getSenderChannelNo() {
        return senderChannelNo;
    }


    /**
     * Sets the senderChannelNo value for this DENDataMessageBody.
     * 
     * @param senderChannelNo
     */
    public void setSenderChannelNo(java.lang.String senderChannelNo) {
        this.senderChannelNo = senderChannelNo;
    }


    /**
     * Gets the pages value for this DENDataMessageBody.
     * 
     * @return pages
     */
    public java.lang.String getPages() {
        return pages;
    }


    /**
     * Sets the pages value for this DENDataMessageBody.
     * 
     * @param pages
     */
    public void setPages(java.lang.String pages) {
        this.pages = pages;
    }


    /**
     * Gets the sendDate value for this DENDataMessageBody.
     * 
     * @return sendDate
     */
    public java.lang.String getSendDate() {
        return sendDate;
    }


    /**
     * Sets the sendDate value for this DENDataMessageBody.
     * 
     * @param sendDate
     */
    public void setSendDate(java.lang.String sendDate) {
        this.sendDate = sendDate;
    }


    /**
     * Gets the CCUnit value for this DENDataMessageBody.
     * 
     * @return CCUnit
     */
    public java.lang.String getCCUnit() {
        return CCUnit;
    }


    /**
     * Sets the CCUnit value for this DENDataMessageBody.
     * 
     * @param CCUnit
     */
    public void setCCUnit(java.lang.String CCUnit) {
        this.CCUnit = CCUnit;
    }


    /**
     * Gets the title value for this DENDataMessageBody.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this DENDataMessageBody.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the changeReason value for this DENDataMessageBody.
     * 
     * @return changeReason
     */
    public java.lang.String getChangeReason() {
        return changeReason;
    }


    /**
     * Sets the changeReason value for this DENDataMessageBody.
     * 
     * @param changeReason
     */
    public void setChangeReason(java.lang.String changeReason) {
        this.changeReason = changeReason;
    }


    /**
     * Gets the dutyUnit value for this DENDataMessageBody.
     * 
     * @return dutyUnit
     */
    public java.lang.String getDutyUnit() {
        return dutyUnit;
    }


    /**
     * Sets the dutyUnit value for this DENDataMessageBody.
     * 
     * @param dutyUnit
     */
    public void setDutyUnit(java.lang.String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }


    /**
     * Gets the DENSourceNo value for this DENDataMessageBody.
     * 
     * @return DENSourceNo
     */
    public java.lang.String getDENSourceNo() {
        return DENSourceNo;
    }


    /**
     * Sets the DENSourceNo value for this DENDataMessageBody.
     * 
     * @param DENSourceNo
     */
    public void setDENSourceNo(java.lang.String DENSourceNo) {
        this.DENSourceNo = DENSourceNo;
    }


    /**
     * Gets the changeDescribe value for this DENDataMessageBody.
     * 
     * @return changeDescribe
     */
    public java.lang.String getChangeDescribe() {
        return changeDescribe;
    }


    /**
     * Sets the changeDescribe value for this DENDataMessageBody.
     * 
     * @param changeDescribe
     */
    public void setChangeDescribe(java.lang.String changeDescribe) {
        this.changeDescribe = changeDescribe;
    }


    /**
     * Gets the DENConsequence value for this DENDataMessageBody.
     * 
     * @return DENConsequence
     */
    public java.lang.String getDENConsequence() {
        return DENConsequence;
    }


    /**
     * Sets the DENConsequence value for this DENDataMessageBody.
     * 
     * @param DENConsequence
     */
    public void setDENConsequence(java.lang.String DENConsequence) {
        this.DENConsequence = DENConsequence;
    }


    /**
     * Gets the author value for this DENDataMessageBody.
     * 
     * @return author
     */
    public java.lang.String getAuthor() {
        return author;
    }


    /**
     * Sets the author value for this DENDataMessageBody.
     * 
     * @param author
     */
    public void setAuthor(java.lang.String author) {
        this.author = author;
    }


    /**
     * Gets the authorDate value for this DENDataMessageBody.
     * 
     * @return authorDate
     */
    public java.lang.String getAuthorDate() {
        return authorDate;
    }


    /**
     * Sets the authorDate value for this DENDataMessageBody.
     * 
     * @param authorDate
     */
    public void setAuthorDate(java.lang.String authorDate) {
        this.authorDate = authorDate;
    }


    /**
     * Gets the author2 value for this DENDataMessageBody.
     * 
     * @return author2
     */
    public java.lang.String getAuthor2() {
        return author2;
    }


    /**
     * Sets the author2 value for this DENDataMessageBody.
     * 
     * @param author2
     */
    public void setAuthor2(java.lang.String author2) {
        this.author2 = author2;
    }


    /**
     * Gets the authorDate2 value for this DENDataMessageBody.
     * 
     * @return authorDate2
     */
    public java.lang.String getAuthorDate2() {
        return authorDate2;
    }


    /**
     * Sets the authorDate2 value for this DENDataMessageBody.
     * 
     * @param authorDate2
     */
    public void setAuthorDate2(java.lang.String authorDate2) {
        this.authorDate2 = authorDate2;
    }


    /**
     * Gets the checker value for this DENDataMessageBody.
     * 
     * @return checker
     */
    public java.lang.String getChecker() {
        return checker;
    }


    /**
     * Sets the checker value for this DENDataMessageBody.
     * 
     * @param checker
     */
    public void setChecker(java.lang.String checker) {
        this.checker = checker;
    }


    /**
     * Gets the checkDate value for this DENDataMessageBody.
     * 
     * @return checkDate
     */
    public java.lang.String getCheckDate() {
        return checkDate;
    }


    /**
     * Sets the checkDate value for this DENDataMessageBody.
     * 
     * @param checkDate
     */
    public void setCheckDate(java.lang.String checkDate) {
        this.checkDate = checkDate;
    }


    /**
     * Gets the reviewer value for this DENDataMessageBody.
     * 
     * @return reviewer
     */
    public java.lang.String getReviewer() {
        return reviewer;
    }


    /**
     * Sets the reviewer value for this DENDataMessageBody.
     * 
     * @param reviewer
     */
    public void setReviewer(java.lang.String reviewer) {
        this.reviewer = reviewer;
    }


    /**
     * Gets the reviewDate value for this DENDataMessageBody.
     * 
     * @return reviewDate
     */
    public java.lang.String getReviewDate() {
        return reviewDate;
    }


    /**
     * Sets the reviewDate value for this DENDataMessageBody.
     * 
     * @param reviewDate
     */
    public void setReviewDate(java.lang.String reviewDate) {
        this.reviewDate = reviewDate;
    }


    /**
     * Gets the auditPerson value for this DENDataMessageBody.
     * 
     * @return auditPerson
     */
    public java.lang.String getAuditPerson() {
        return auditPerson;
    }


    /**
     * Sets the auditPerson value for this DENDataMessageBody.
     * 
     * @param auditPerson
     */
    public void setAuditPerson(java.lang.String auditPerson) {
        this.auditPerson = auditPerson;
    }


    /**
     * Gets the auditDate value for this DENDataMessageBody.
     * 
     * @return auditDate
     */
    public java.lang.String getAuditDate() {
        return auditDate;
    }


    /**
     * Sets the auditDate value for this DENDataMessageBody.
     * 
     * @param auditDate
     */
    public void setAuditDate(java.lang.String auditDate) {
        this.auditDate = auditDate;
    }


    /**
     * Gets the approver value for this DENDataMessageBody.
     * 
     * @return approver
     */
    public java.lang.String getApprover() {
        return approver;
    }


    /**
     * Sets the approver value for this DENDataMessageBody.
     * 
     * @param approver
     */
    public void setApprover(java.lang.String approver) {
        this.approver = approver;
    }


    /**
     * Gets the approveDate value for this DENDataMessageBody.
     * 
     * @return approveDate
     */
    public java.lang.String getApproveDate() {
        return approveDate;
    }


    /**
     * Sets the approveDate value for this DENDataMessageBody.
     * 
     * @param approveDate
     */
    public void setApproveDate(java.lang.String approveDate) {
        this.approveDate = approveDate;
    }


    /**
     * Gets the jointSigner value for this DENDataMessageBody.
     * 
     * @return jointSigner
     */
    public java.lang.String getJointSigner() {
        return jointSigner;
    }


    /**
     * Sets the jointSigner value for this DENDataMessageBody.
     * 
     * @param jointSigner
     */
    public void setJointSigner(java.lang.String jointSigner) {
        this.jointSigner = jointSigner;
    }


    /**
     * Gets the jointSignDate value for this DENDataMessageBody.
     * 
     * @return jointSignDate
     */
    public java.lang.String getJointSignDate() {
        return jointSignDate;
    }


    /**
     * Sets the jointSignDate value for this DENDataMessageBody.
     * 
     * @param jointSignDate
     */
    public void setJointSignDate(java.lang.String jointSignDate) {
        this.jointSignDate = jointSignDate;
    }


    /**
     * Gets the fileList value for this DENDataMessageBody.
     * 
     * @return fileList
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENFileList[] getFileList() {
        return fileList;
    }


    /**
     * Sets the fileList value for this DENDataMessageBody.
     * 
     * @param fileList
     */
    public void setFileList(org.zisecm.jobs.tc.ws.PLMServerLOT.DENFileList[] fileList) {
        this.fileList = fileList;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENFileList getFileList(int i) {
        return this.fileList[i];
    }

    public void setFileList(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.DENFileList _value) {
        this.fileList[i] = _value;
    }


    /**
     * Gets the addedMatList value for this DENDataMessageBody.
     * 
     * @return addedMatList
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENAddedMatList[] getAddedMatList() {
        return addedMatList;
    }


    /**
     * Sets the addedMatList value for this DENDataMessageBody.
     * 
     * @param addedMatList
     */
    public void setAddedMatList(org.zisecm.jobs.tc.ws.PLMServerLOT.DENAddedMatList[] addedMatList) {
        this.addedMatList = addedMatList;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENAddedMatList getAddedMatList(int i) {
        return this.addedMatList[i];
    }

    public void setAddedMatList(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.DENAddedMatList _value) {
        this.addedMatList[i] = _value;
    }


    /**
     * Gets the cancelledMatList value for this DENDataMessageBody.
     * 
     * @return cancelledMatList
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENCancelledMatList[] getCancelledMatList() {
        return cancelledMatList;
    }


    /**
     * Sets the cancelledMatList value for this DENDataMessageBody.
     * 
     * @param cancelledMatList
     */
    public void setCancelledMatList(org.zisecm.jobs.tc.ws.PLMServerLOT.DENCancelledMatList[] cancelledMatList) {
        this.cancelledMatList = cancelledMatList;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENCancelledMatList getCancelledMatList(int i) {
        return this.cancelledMatList[i];
    }

    public void setCancelledMatList(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.DENCancelledMatList _value) {
        this.cancelledMatList[i] = _value;
    }


    /**
     * Gets the fieldRevConc value for this DENDataMessageBody.
     * 
     * @return fieldRevConc
     */
    public java.lang.String getFieldRevConc() {
        return fieldRevConc;
    }


    /**
     * Sets the fieldRevConc value for this DENDataMessageBody.
     * 
     * @param fieldRevConc
     */
    public void setFieldRevConc(java.lang.String fieldRevConc) {
        this.fieldRevConc = fieldRevConc;
    }


    /**
     * Gets the fieldRevAdv value for this DENDataMessageBody.
     * 
     * @return fieldRevAdv
     */
    public java.lang.String getFieldRevAdv() {
        return fieldRevAdv;
    }


    /**
     * Sets the fieldRevAdv value for this DENDataMessageBody.
     * 
     * @param fieldRevAdv
     */
    public void setFieldRevAdv(java.lang.String fieldRevAdv) {
        this.fieldRevAdv = fieldRevAdv;
    }


    /**
     * Gets the fieldRevDate value for this DENDataMessageBody.
     * 
     * @return fieldRevDate
     */
    public java.lang.String getFieldRevDate() {
        return fieldRevDate;
    }


    /**
     * Sets the fieldRevDate value for this DENDataMessageBody.
     * 
     * @param fieldRevDate
     */
    public void setFieldRevDate(java.lang.String fieldRevDate) {
        this.fieldRevDate = fieldRevDate;
    }


    /**
     * Gets the attachments value for this DENDataMessageBody.
     * 
     * @return attachments
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENAttachment[] getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this DENDataMessageBody.
     * 
     * @param attachments
     */
    public void setAttachments(org.zisecm.jobs.tc.ws.PLMServerLOT.DENAttachment[] attachments) {
        this.attachments = attachments;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.DENAttachment getAttachments(int i) {
        return this.attachments[i];
    }

    public void setAttachments(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.DENAttachment _value) {
        this.attachments[i] = _value;
    }

    

}
