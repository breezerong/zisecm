/**
 * FUDataMessageBody.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FUDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FUDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class FUDataMessageBody  implements java.io.Serializable {
    private String projectId;

    private String recivunit;

    private String sendDate;

    private String pages;

    private String content;
    
    private String fuDate;
    
    private String authorUnit;
    
    private String theme;

    public String getTheme() {
		return theme;
	}




	public void setTheme(String theme) {
		this.theme = theme;
	}

	private String letterSendNo;

    private FUFileList[] fileList;

    private FUAttachment[] attachments;

    public FUDataMessageBody() {
    }

    
	public FUDataMessageBody(String projectId, String recivunit,
			String sendDate, String pages, String content, String fuDate,
			String authorUnit, String theme, String letterSendNo,
			FUFileList[] fileList, FUAttachment[] attachments) {
		super();
		this.projectId = projectId;
		this.recivunit = recivunit;
		this.sendDate = sendDate;
		this.pages = pages;
		this.content = content;
		this.fuDate = fuDate;
		this.authorUnit = authorUnit;
		this.theme = theme;
		this.letterSendNo = letterSendNo;
		this.fileList = fileList;
		this.attachments = attachments;
	}




	public String getProjectId() {
		return projectId;
	}




	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}




	public String getRecivunit() {
		return recivunit;
	}

	public void setRecivunit(String recivunit) {
		this.recivunit = recivunit;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFuDate() {
		return fuDate;
	}

	public void setFuDate(String fuDate) {
		this.fuDate = fuDate;
	}

	public String getAuthorUnit() {
		return authorUnit;
	}

	public void setAuthorUnit(String authorUnit) {
		this.authorUnit = authorUnit;
	}

	public String getLetterSendNo() {
		return letterSendNo;
	}

	public void setLetterSendNo(String letterSendNo) {
		this.letterSendNo = letterSendNo;
	}

	public FUFileList[] getFileList() {
		return fileList;
	}

	public void setFileList(FUFileList[] fileList) {
		this.fileList = fileList;
	}

	public FUAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(FUAttachment[] attachments) {
		this.attachments = attachments;
	}


  

}
