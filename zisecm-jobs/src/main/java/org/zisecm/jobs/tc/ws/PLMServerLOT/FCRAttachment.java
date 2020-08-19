/**
 * FCRAttachment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="FCRAttachment",namespace="http://PLMServerCM.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FCRAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")


public class FCRAttachment  implements java.io.Serializable {
    private java.lang.String FCRCode;

    private java.lang.String FCRRevision;

    private java.lang.String filePath;

    private java.lang.String fileID;

    private java.lang.String fileName;

    private int fileSize;

    public FCRAttachment() {
    }

    public FCRAttachment(
           java.lang.String FCRCode,
           java.lang.String FCRRevision,
           java.lang.String filePath,
           java.lang.String fileID,
           java.lang.String fileName,
           int fileSize) {
           this.FCRCode = FCRCode;
           this.FCRRevision = FCRRevision;
           this.filePath = filePath;
           this.fileID = fileID;
           this.fileName = fileName;
           this.fileSize = fileSize;
    }


    /**
     * Gets the FCRCode value for this FCRAttachment.
     * 
     * @return FCRCode
     */
    public java.lang.String getFCRCode() {
        return FCRCode;
    }


    /**
     * Sets the FCRCode value for this FCRAttachment.
     * 
     * @param FCRCode
     */
    public void setFCRCode(java.lang.String FCRCode) {
        this.FCRCode = FCRCode;
    }


    /**
     * Gets the FCRRevision value for this FCRAttachment.
     * 
     * @return FCRRevision
     */
    public java.lang.String getFCRRevision() {
        return FCRRevision;
    }


    /**
     * Sets the FCRRevision value for this FCRAttachment.
     * 
     * @param FCRRevision
     */
    public void setFCRRevision(java.lang.String FCRRevision) {
        this.FCRRevision = FCRRevision;
    }


    /**
     * Gets the filePath value for this FCRAttachment.
     * 
     * @return filePath
     */
    public java.lang.String getFilePath() {
        return filePath;
    }


    /**
     * Sets the filePath value for this FCRAttachment.
     * 
     * @param filePath
     */
    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }


    /**
     * Gets the fileID value for this FCRAttachment.
     * 
     * @return fileID
     */
    public java.lang.String getFileID() {
        return fileID;
    }


    /**
     * Sets the fileID value for this FCRAttachment.
     * 
     * @param fileID
     */
    public void setFileID(java.lang.String fileID) {
        this.fileID = fileID;
    }


    /**
     * Gets the fileName value for this FCRAttachment.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this FCRAttachment.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the fileSize value for this FCRAttachment.
     * 
     * @return fileSize
     */
    public int getFileSize() {
        return fileSize;
    }


    /**
     * Sets the fileSize value for this FCRAttachment.
     * 
     * @param fileSize
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    

}
