/**
 * NCRFileList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="NCRFileList",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NCRFileList",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class NCRFileList  implements java.io.Serializable {
    private java.lang.String fileExtCode;

    private java.lang.String fileRev;

    private java.lang.String fileIntCode;

    private java.lang.String fileExeStatus;

    public NCRFileList() {
    }

    public NCRFileList(
           java.lang.String fileExtCode,
           java.lang.String fileRev,
           java.lang.String fileIntCode,
           java.lang.String fileExeStatus) {
           this.fileExtCode = fileExtCode;
           this.fileRev = fileRev;
           this.fileIntCode = fileIntCode;
           this.fileExeStatus = fileExeStatus;
    }


    /**
     * Gets the fileExtCode value for this NCRFileList.
     * 
     * @return fileExtCode
     */
    public java.lang.String getFileExtCode() {
        return fileExtCode;
    }


    /**
     * Sets the fileExtCode value for this NCRFileList.
     * 
     * @param fileExtCode
     */
    public void setFileExtCode(java.lang.String fileExtCode) {
        this.fileExtCode = fileExtCode;
    }


    /**
     * Gets the fileRev value for this NCRFileList.
     * 
     * @return fileRev
     */
    public java.lang.String getFileRev() {
        return fileRev;
    }


    /**
     * Sets the fileRev value for this NCRFileList.
     * 
     * @param fileRev
     */
    public void setFileRev(java.lang.String fileRev) {
        this.fileRev = fileRev;
    }


    /**
     * Gets the fileIntCode value for this NCRFileList.
     * 
     * @return fileIntCode
     */
    public java.lang.String getFileIntCode() {
        return fileIntCode;
    }


    /**
     * Sets the fileIntCode value for this NCRFileList.
     * 
     * @param fileIntCode
     */
    public void setFileIntCode(java.lang.String fileIntCode) {
        this.fileIntCode = fileIntCode;
    }


    /**
     * Gets the fileExeStatus value for this NCRFileList.
     * 
     * @return fileExeStatus
     */
    public java.lang.String getFileExeStatus() {
        return fileExeStatus;
    }


    /**
     * Sets the fileExeStatus value for this NCRFileList.
     * 
     * @param fileExeStatus
     */
    public void setFileExeStatus(java.lang.String fileExeStatus) {
        this.fileExeStatus = fileExeStatus;
    }

   

}
