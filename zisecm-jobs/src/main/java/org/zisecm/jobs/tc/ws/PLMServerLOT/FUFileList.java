/**
 * FUFileList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FUFileList",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FUFileList",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class FUFileList  implements java.io.Serializable {
    private java.lang.String fileIntCode;

    private java.lang.String fileExtCode;

    private java.lang.String fileTitle;

    private java.lang.String fileRev;

    public FUFileList() {
    }

    public FUFileList(
           java.lang.String fileIntCode,
           java.lang.String fileExtCode,
           java.lang.String fileTitle,
           java.lang.String fileRev) {
           this.fileIntCode = fileIntCode;
           this.fileExtCode = fileExtCode;
           this.fileTitle = fileTitle;
           this.fileRev = fileRev;
    }


    /**
     * Gets the fileIntCode value for this FUFileList.
     * 
     * @return fileIntCode
     */
    public java.lang.String getFileIntCode() {
        return fileIntCode;
    }


    /**
     * Sets the fileIntCode value for this FUFileList.
     * 
     * @param fileIntCode
     */
    public void setFileIntCode(java.lang.String fileIntCode) {
        this.fileIntCode = fileIntCode;
    }


    /**
     * Gets the fileExtCode value for this FUFileList.
     * 
     * @return fileExtCode
     */
    public java.lang.String getFileExtCode() {
        return fileExtCode;
    }


    /**
     * Sets the fileExtCode value for this FUFileList.
     * 
     * @param fileExtCode
     */
    public void setFileExtCode(java.lang.String fileExtCode) {
        this.fileExtCode = fileExtCode;
    }


    /**
     * Gets the fileTitle value for this FUFileList.
     * 
     * @return fileTitle
     */
    public java.lang.String getFileTitle() {
        return fileTitle;
    }


    /**
     * Sets the fileTitle value for this FUFileList.
     * 
     * @param fileTitle
     */
    public void setFileTitle(java.lang.String fileTitle) {
        this.fileTitle = fileTitle;
    }


    /**
     * Gets the fileRev value for this FUFileList.
     * 
     * @return fileRev
     */
    public java.lang.String getFileRev() {
        return fileRev;
    }


    /**
     * Sets the fileRev value for this FUFileList.
     * 
     * @param fileRev
     */
    public void setFileRev(java.lang.String fileRev) {
        this.fileRev = fileRev;
    }

   

}
