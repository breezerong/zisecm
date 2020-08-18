package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DesignReviewFDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DesignReviewFDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class DesignReviewFDataMessageBody implements java.io.Serializable {

	//private String code;
	
	private String revision;
	
	private String projectId;
	
	private String name;//current_name
	
	private String authorUnit;//发自
	
	private String recivUnit;//主送
	
	private String sendRecvLettDate;//收发文时间
	
	private String letterSendNo;//我方发文号
	
	private String correspLetterRecNo;//回文号
	
	//private String fileChannelNo;//文件通道号
	
	private String dRCSRecDate;//drcs日期
	
	private String comment;//内容
	
	private String ccUnit;
	/*
	private String chNum;//渠道号
	
	private String receiverCh;//接收方
	
	private String senderCh;//发送方
	*/
	
	private String pages;//页数
	
	private DesignReviewFFileList[] designrfList;
	
	private DesignReviewFAttachment[] designrfAttachments ;
	
	private String sendLotDate;
    
    private String sendLotNo;

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

	public String getSendRecvLettDate() {
		return sendRecvLettDate;
	}

	public void setSendRecvLettDate(String sendRecvLettDate) {
		this.sendRecvLettDate = sendRecvLettDate;
	}

	public String getLetterSendNo() {
		return letterSendNo;
	}

	public void setLetterSendNo(String letterSendNo) {
		this.letterSendNo = letterSendNo;
	}

	public String getCorrespLetterRecNo() {
		return correspLetterRecNo;
	}

	public void setCorrespLetterRecNo(String correspLetterRecNo) {
		this.correspLetterRecNo = correspLetterRecNo;
	}

	public String getdRCSRecDate() {
		return dRCSRecDate;
	}

	public void setdRCSRecDate(String dRCSRecDate) {
		this.dRCSRecDate = dRCSRecDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCcUnit() {
		return ccUnit;
	}

	public void setCcUnit(String ccUnit) {
		this.ccUnit = ccUnit;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public DesignReviewFFileList[] getDesignrfList() {
		return designrfList;
	}

	public void setDesignrfList(DesignReviewFFileList[] designrfList) {
		this.designrfList = designrfList;
	}

	public DesignReviewFAttachment[] getDesignrfAttachments() {
		return designrfAttachments;
	}

	public void setDesignrfAttachments(DesignReviewFAttachment[] designrfAttachments) {
		this.designrfAttachments = designrfAttachments;
	}

	
	
	
	
	
}
