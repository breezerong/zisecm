/**
 * NCRDataMessageBody.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="NCRDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NCRDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class NCRDataMessageBody  implements java.io.Serializable {
    private java.lang.String code;

    private java.lang.String type;

    private java.lang.String publishTime;

    private java.lang.String revision;

    private java.lang.String productCode;

    private java.lang.String sendChannelNo;

    private java.lang.String pages;

    private java.lang.String CCUnit;
    
    private String authorUnit;

    public String getAuthorUnit() {
		return authorUnit;
	}

	public void setAuthorUnit(String authorUnit) {
		this.authorUnit = authorUnit;
	}

	private java.lang.String[] equipCodeName;

    private java.lang.String NCRName;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.NCRAttachment[] attachments;

    private org.zisecm.jobs.tc.ws.PLMServerLOT.NCRFileList[] fileList;
    
private String sendLotDate;
    
    private String sendLotNo;
    
    private java.lang.String closeStatus;

    private java.lang.String closeDate;

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

	public NCRDataMessageBody() {
    }

   


    public NCRDataMessageBody(String code, String type, String publishTime,
			String revision, String productCode, String sendChannelNo,
			String pages, String cCUnit, String authorUnit,
			String[] equipCodeName, String nCRName,
			NCRAttachment[] attachments, NCRFileList[] fileList,
			String sendLotDate, String sendLotNo, String closeStatus,
			String closeDate) {
		super();
		this.code = code;
		this.type = type;
		this.publishTime = publishTime;
		this.revision = revision;
		this.productCode = productCode;
		this.sendChannelNo = sendChannelNo;
		this.pages = pages;
		CCUnit = cCUnit;
		this.authorUnit = authorUnit;
		this.equipCodeName = equipCodeName;
		NCRName = nCRName;
		this.attachments = attachments;
		this.fileList = fileList;
		this.sendLotDate = sendLotDate;
		this.sendLotNo = sendLotNo;
		this.closeStatus = closeStatus;
		this.closeDate = closeDate;
	}

	/**
     * Gets the code value for this NCRDataMessageBody.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this NCRDataMessageBody.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the type value for this NCRDataMessageBody.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this NCRDataMessageBody.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the publishTime value for this NCRDataMessageBody.
     * 
     * @return publishTime
     */
    public java.lang.String getPublishTime() {
        return publishTime;
    }


    /**
     * Sets the publishTime value for this NCRDataMessageBody.
     * 
     * @param publishTime
     */
    public void setPublishTime(java.lang.String publishTime) {
        this.publishTime = publishTime;
    }


    /**
     * Gets the revision value for this NCRDataMessageBody.
     * 
     * @return revision
     */
    public java.lang.String getRevision() {
        return revision;
    }


    /**
     * Sets the revision value for this NCRDataMessageBody.
     * 
     * @param revision
     */
    public void setRevision(java.lang.String revision) {
        this.revision = revision;
    }


    /**
     * Gets the productCode value for this NCRDataMessageBody.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this NCRDataMessageBody.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the sendChannelNo value for this NCRDataMessageBody.
     * 
     * @return sendChannelNo
     */
    public java.lang.String getSendChannelNo() {
        return sendChannelNo;
    }


    /**
     * Sets the sendChannelNo value for this NCRDataMessageBody.
     * 
     * @param sendChannelNo
     */
    public void setSendChannelNo(java.lang.String sendChannelNo) {
        this.sendChannelNo = sendChannelNo;
    }


    /**
     * Gets the pages value for this NCRDataMessageBody.
     * 
     * @return pages
     */
    public java.lang.String getPages() {
        return pages;
    }


    /**
     * Sets the pages value for this NCRDataMessageBody.
     * 
     * @param pages
     */
    public void setPages(java.lang.String pages) {
        this.pages = pages;
    }


    /**
     * Gets the CCUnit value for this NCRDataMessageBody.
     * 
     * @return CCUnit
     */
    public java.lang.String getCCUnit() {
        return CCUnit;
    }


    /**
     * Sets the CCUnit value for this NCRDataMessageBody.
     * 
     * @param CCUnit
     */
    public void setCCUnit(java.lang.String CCUnit) {
        this.CCUnit = CCUnit;
    }


    /**
     * Gets the equipCodeName value for this NCRDataMessageBody.
     * 
     * @return equipCodeName
     */
    public java.lang.String[] getEquipCodeName() {
        return equipCodeName;
    }


    /**
     * Sets the equipCodeName value for this NCRDataMessageBody.
     * 
     * @param equipCodeName
     */
    public void setEquipCodeName(java.lang.String[] equipCodeName) {
        this.equipCodeName = equipCodeName;
    }

    public java.lang.String getEquipCodeName(int i) {
        return this.equipCodeName[i];
    }

    public void setEquipCodeName(int i, java.lang.String _value) {
        this.equipCodeName[i] = _value;
    }


    /**
     * Gets the NCRName value for this NCRDataMessageBody.
     * 
     * @return NCRName
     */
    public java.lang.String getNCRName() {
        return NCRName;
    }


    /**
     * Sets the NCRName value for this NCRDataMessageBody.
     * 
     * @param NCRName
     */
    public void setNCRName(java.lang.String NCRName) {
        this.NCRName = NCRName;
    }


    /**
     * Gets the attachments value for this NCRDataMessageBody.
     * 
     * @return attachments
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.NCRAttachment[] getAttachments() {
        return attachments;
    }


    /**
     * Sets the attachments value for this NCRDataMessageBody.
     * 
     * @param attachments
     */
    public void setAttachments(org.zisecm.jobs.tc.ws.PLMServerLOT.NCRAttachment[] attachments) {
        this.attachments = attachments;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.NCRAttachment getAttachments(int i) {
        return this.attachments[i];
    }

    public void setAttachments(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.NCRAttachment _value) {
        this.attachments[i] = _value;
    }


    /**
     * Gets the fileList value for this NCRDataMessageBody.
     * 
     * @return fileList
     */
    public org.zisecm.jobs.tc.ws.PLMServerLOT.NCRFileList[] getFileList() {
        return fileList;
    }


    /**
     * Sets the fileList value for this NCRDataMessageBody.
     * 
     * @param fileList
     */
    public void setFileList(org.zisecm.jobs.tc.ws.PLMServerLOT.NCRFileList[] fileList) {
        this.fileList = fileList;
    }

    public org.zisecm.jobs.tc.ws.PLMServerLOT.NCRFileList getFileList(int i) {
        return this.fileList[i];
    }

    public void setFileList(int i, org.zisecm.jobs.tc.ws.PLMServerLOT.NCRFileList _value) {
        this.fileList[i] = _value;
    }

   

}
