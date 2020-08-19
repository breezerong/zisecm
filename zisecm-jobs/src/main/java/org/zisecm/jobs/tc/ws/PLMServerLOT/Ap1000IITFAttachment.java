/**
 * DENAttachment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Ap1000IITFAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Ap1000IITFAttachment",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class Ap1000IITFAttachment  implements java.io.Serializable {
    private java.lang.String filePath;

    private java.lang.String fileID;

    private java.lang.String fileName;

    private java.lang.String fileSize;

    public Ap1000IITFAttachment() {
    }

    public Ap1000IITFAttachment(
           java.lang.String filePath,
           java.lang.String fileID,
           java.lang.String fileName,
           java.lang.String fileSize) {
           this.filePath = filePath;
           this.fileID = fileID;
           this.fileName = fileName;
           this.fileSize = fileSize;
    }


    /**
     * Gets the filePath value for this DENAttachment.
     * 
     * @return filePath
     */
    public java.lang.String getFilePath() {
        return filePath;
    }


    /**
     * Sets the filePath value for this DENAttachment.
     * 
     * @param filePath
     */
    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }


    /**
     * Gets the fileID value for this DENAttachment.
     * 
     * @return fileID
     */
    public java.lang.String getFileID() {
        return fileID;
    }


    /**
     * Sets the fileID value for this DENAttachment.
     * 
     * @param fileID
     */
    public void setFileID(java.lang.String fileID) {
        this.fileID = fileID;
    }


    /**
     * Gets the fileName value for this DENAttachment.
     * 
     * @return fileName
     */
    public java.lang.String getFileName() {
        return fileName;
    }


    /**
     * Sets the fileName value for this DENAttachment.
     * 
     * @param fileName
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }


    /**
     * Gets the fileSize value for this DENAttachment.
     * 
     * @return fileSize
     */
    public java.lang.String getFileSize() {
        return fileSize;
    }


    /**
     * Sets the fileSize value for this DENAttachment.
     * 
     * @param fileSize
     */
    public void setFileSize(java.lang.String fileSize) {
        this.fileSize = fileSize;
    }

   

}
