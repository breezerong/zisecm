/**
 * CRFileList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FileList",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FileList",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class FileList  implements java.io.Serializable {
    private java.lang.String fileIntCode;

    private java.lang.String fileExtCode;

    private java.lang.String fileRev;

    private java.lang.String fileExeStatus;
    
    private String fileName;
    
    private String page;
    
    private String paper;
    
    private String digital;
    
    private String blue;

    public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getDigital() {
		return digital;
	}

	public void setDigital(String digital) {
		this.digital = digital;
	}

	public String getBlue() {
		return blue;
	}

	public void setBlue(String blue) {
		this.blue = blue;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public FileList() {
    }

    public FileList(
           java.lang.String fileIntCode,
           java.lang.String fileExtCode,
           java.lang.String fileRev,
           java.lang.String fileExeStatus) {
           this.fileIntCode = fileIntCode;
           this.fileExtCode = fileExtCode;
           this.fileRev = fileRev;
           this.fileExeStatus = fileExeStatus;
    }


    /**
     * Gets the fileIntCode value for this CRFileList.
     * 
     * @return fileIntCode
     */
    public java.lang.String getFileIntCode() {
        return fileIntCode;
    }


    /**
     * Sets the fileIntCode value for this CRFileList.
     * 
     * @param fileIntCode
     */
    public void setFileIntCode(java.lang.String fileIntCode) {
        this.fileIntCode = fileIntCode;
    }


    /**
     * Gets the fileExtCode value for this CRFileList.
     * 
     * @return fileExtCode
     */
    public java.lang.String getFileExtCode() {
        return fileExtCode;
    }


    /**
     * Sets the fileExtCode value for this CRFileList.
     * 
     * @param fileExtCode
     */
    public void setFileExtCode(java.lang.String fileExtCode) {
        this.fileExtCode = fileExtCode;
    }


    /**
     * Gets the fileRev value for this CRFileList.
     * 
     * @return fileRev
     */
    public java.lang.String getFileRev() {
        return fileRev;
    }


    /**
     * Sets the fileRev value for this CRFileList.
     * 
     * @param fileRev
     */
    public void setFileRev(java.lang.String fileRev) {
        this.fileRev = fileRev;
    }


    /**
     * Gets the fileExeStatus value for this CRFileList.
     * 
     * @return fileExeStatus
     */
    public java.lang.String getFileExeStatus() {
        return fileExeStatus;
    }


    /**
     * Sets the fileExeStatus value for this CRFileList.
     * 
     * @param fileExeStatus
     */
    public void setFileExeStatus(java.lang.String fileExeStatus) {
        this.fileExeStatus = fileExeStatus;
    }

   

}
