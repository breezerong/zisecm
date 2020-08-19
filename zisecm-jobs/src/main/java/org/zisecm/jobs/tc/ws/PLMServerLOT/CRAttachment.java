/**
 * CRAttachment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="CRAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="CRAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")

public class CRAttachment  implements java.io.Serializable {
	
	
	
    private java.lang.String CRCode;
	//@XmlElement(name="CRRevision",namespace="")
    private java.lang.String CRRevision;
	//@XmlElement(name="filePath",namespace="")
    private java.lang.String filePath;
	//@XmlElement(name="fileID",namespace="")
    private java.lang.String fileID;
	//@XmlElement(name="fileName",namespace="")
    private java.lang.String fileName;
	//@XmlElement(name="fileSize",namespace="")
    private int fileSize;

    public CRAttachment() {
    }

    public CRAttachment(
           java.lang.String CRCode,
           java.lang.String CRRevision,
           java.lang.String filePath,
           java.lang.String fileID,
           java.lang.String fileName,
           int fileSize) {
           this.CRCode = CRCode;
           this.CRRevision = CRRevision;
           this.filePath = filePath;
           this.fileID = fileID;
           this.fileName = fileName;
           this.fileSize = fileSize;
    }


    /**
     * Gets the CRCode value for this CRAttachment.
     * 
     * @return CRCode
     */
    public java.lang.String getCRCode() {
        return CRCode;
    }


    /**
     * Sets the CRCode value for this CRAttachment.
     * 
     * @param CRCode
     */
    public void setCRCode(java.lang.String CRCode) {
        this.CRCode = CRCode;
    }


    /**
     * Gets the CRRevision value for this CRAttachment.
     * 
     * @return CRRevision
     */
    public java.lang.String getCRRevision() {
        return CRRevision;
    }


    /**
     * Sets the CRRevision value for this CRAttachment.
     * 
     * @param CRRevision
     */
    public void setCRRevision(java.lang.String CRRevision) {
        this.CRRevision = CRRevision;
    }


    /**
     * Gets the filePath value for this CRAttachment.
     * 
     * @return filePath
     */
    public java.lang.String getFilePath() {
        return filePath;
    }


    /**
     * Sets the filePath value for this CRAttachment.
     * 
     * @param filePath
     */
    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }


    /**
     * Gets the fileID value for this CRAttachment.
     * 
     * @return fileID
     */
    public java.lang.String getFileID() {
        return fileID;
    }


    /**
     * Sets the fileID value for this CRAttachment.
     * 
     * @param fileID
     */
    public void setFileID(java.lang.String fileID) {
        this.fileID = fileID;
    }


    /**
     * Gets the fileName value for this CRAttachment.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this CRAttachment.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the fileSize value for this CRAttachment.
     * 
     * @return fileSize
     */
    public int getFileSize() {
        return fileSize;
    }


    /**
     * Sets the fileSize value for this CRAttachment.
     * 
     * @param fileSize
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    
   

}
