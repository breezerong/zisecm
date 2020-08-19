package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FileDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FileDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class FileDataMessageBody implements java.io.Serializable {

	//private String code;
	
	private String revision;
	
	private String projectId;
	
	private String name;//current_name
	
	private String authorUnit;//发自
	
	private String recivUnit;//主送
	
	
	
	private String letterSendNo;//我方发文号
	/*
	private String chNum;//渠道号
	
	private String receiverCh;//接收方
	
	private String senderCh;//发送方
	*/
	private String page;//页数
	
	private String shouDate;//收发文日期
	
	private String replayDeadLine;//审查期限
	
	 private FileList[] fileList;

	 private String sendLotNo;
	 private String sendLotDate;
	 public String getSendLotNo() {
		return sendLotNo;
	}

	public void setSendLotNo(String sendLotNo) {
		this.sendLotNo = sendLotNo;
	}

	public String getSendLotDate() {
		return sendLotDate;
	}

	public void setSendLotDate(String sendLotDate) {
		this.sendLotDate = sendLotDate;
	}

	public FileList[] getFileList() {
		return fileList;
	}

	public FileDataMessageBody(String revision, String projectId, String name,
			String authorUnit, String recivUnit, String letterSendNo,
			String page, String shouDate, String replayDeadLine,
			FileList[] fileList, FileAttachment[] attachments) {
		super();
		this.revision = revision;
		this.projectId = projectId;
		this.name = name;
		this.authorUnit = authorUnit;
		this.recivUnit = recivUnit;
		this.letterSendNo = letterSendNo;
		this.page = page;
		this.shouDate = shouDate;
		this.replayDeadLine = replayDeadLine;
		this.fileList = fileList;
		this.attachments = attachments;
	}

	public void setFileList(FileList[] fileList) {
		this.fileList = fileList;
	}

	public FileAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(FileAttachment[] attachments) {
		this.attachments = attachments;
	}

	private FileAttachment[] attachments; 

	public String getReplayDeadLine() {
		return replayDeadLine;
	}

	public void setReplayDeadLine(String replayDeadLine) {
		this.replayDeadLine = replayDeadLine;
	}

	public FileDataMessageBody() {
	}

	

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorUnit() {
		return authorUnit;
	}

	public void setAuthorUnit(String authorUnit) {
		this.authorUnit = authorUnit;
	}

	public String getRecivUnit() {
		return recivUnit;
	}

	public void setRecivUnit(String recivUnit) {
		this.recivUnit = recivUnit;
	}

	public String getLetterSendNo() {
		return letterSendNo;
	}

	public void setLetterSendNo(String letterSendNo) {
		this.letterSendNo = letterSendNo;
	}

	

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getShouDate() {
		return shouDate;
	}

	public void setShouDate(String shouDate) {
		this.shouDate = shouDate;
	}
	
	  

	
}
