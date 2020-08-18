/**
 * NCRAttachment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="NCRAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NCRAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class NCRAttachment  implements java.io.Serializable {
    private java.lang.String NCRCode;

    private java.lang.String NCRRevision;

    private java.lang.String filePath;

    private java.lang.String fileID;

    private java.lang.String fileName;

    private int fileSize;

    public NCRAttachment() {
    }

    public NCRAttachment(
           java.lang.String NCRCode,
           java.lang.String NCRRevision,
           java.lang.String filePath,
           java.lang.String fileID,
           java.lang.String fileName,
           int fileSize) {
           this.NCRCode = NCRCode;
           this.NCRRevision = NCRRevision;
           this.filePath = filePath;
           this.fileID = fileID;
           this.fileName = fileName;
           this.fileSize = fileSize;
    }


    /**
     * Gets the NCRCode value for this NCRAttachment.
     * 
     * @return NCRCode
     */
    public java.lang.String getNCRCode() {
        return NCRCode;
    }


    /**
     * Sets the NCRCode value for this NCRAttachment.
     * 
     * @param NCRCode
     */
    public void setNCRCode(java.lang.String NCRCode) {
        this.NCRCode = NCRCode;
    }


    /**
     * Gets the NCRRevision value for this NCRAttachment.
     * 
     * @return NCRRevision
     */
    public java.lang.String getNCRRevision() {
        return NCRRevision;
    }


    /**
     * Sets the NCRRevision value for this NCRAttachment.
     * 
     * @param NCRRevision
     */
    public void setNCRRevision(java.lang.String NCRRevision) {
        this.NCRRevision = NCRRevision;
    }


    /**
     * Gets the filePath value for this NCRAttachment.
     * 
     * @return filePath
     */
    public java.lang.String getFilePath() {
        return filePath;
    }


    /**
     * Sets the filePath value for this NCRAttachment.
     * 
     * @param filePath
     */
    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }


    /**
     * Gets the fileID value for this NCRAttachment.
     * 
     * @return fileID
     */
    public java.lang.String getFileID() {
        return fileID;
    }


    /**
     * Sets the fileID value for this NCRAttachment.
     * 
     * @param fileID
     */
    public void setFileID(java.lang.String fileID) {
        this.fileID = fileID;
    }


    /**
     * Gets the fileName value for this NCRAttachment.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this NCRAttachment.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the fileSize value for this NCRAttachment.
     * 
     * @return fileSize
     */
    public int getFileSize() {
        return fileSize;
    }


    /**
     * Sets the fileSize value for this NCRAttachment.
     * 
     * @param fileSize
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

   

}
