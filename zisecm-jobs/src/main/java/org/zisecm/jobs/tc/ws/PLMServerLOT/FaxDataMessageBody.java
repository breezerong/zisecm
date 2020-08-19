package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FaxDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FaxDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class FaxDataMessageBody implements java.io.Serializable {

	//private String code;
	
	private String revision;
	
	private String projectId;
	
	private String name;//current_name
	
	private String faxType;//一般信函
	
	
	
	private String authorUnit;//发自
	
	private String recivUnit;//主送
	
	
	
	private String letterSendNo;//我方发文号
	
	private String correspLetterRecNo;//对方发文号
	
	private String pages;//页数
	
	private String needReply;//是否需要回文
	
	private String theme;//主题
	
	private String content;//内容
	
	private String sendRecvLettDate;
	/*
	 * 发自   主送  对方发文编号  我方发文号  共发送页数  是否回文  题目  内容
	private String chNum;//渠道号
	
	private String receiverCh;//接收方
	
	private String senderCh;//发送方
	*/
	private String ccUnit;
	
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


	public String getCcUnit() {
		return ccUnit;
	}


	public void setCcUnit(String ccUnit) {
		this.ccUnit = ccUnit;
	}

	private String requireReplyDate;
	public String getRequireReplyDate() {
		return requireReplyDate;
	}


	public void setRequireReplyDate(String requireReplyDate) {
		this.requireReplyDate = requireReplyDate;
	}

	private FaxAttachment[] faxAttachment;

	 public FaxAttachment[] getFaxAttachment() {
		return faxAttachment;
	}


	public void setFaxAttachment(FaxAttachment[] faxAttachment) {
		this.faxAttachment = faxAttachment;
	}


	public FaxDataMessageBody() {
	}


	


	public FaxDataMessageBody(String revision, String projectId, String name,
			String faxType, String authorUnit, String recivUnit,
			String letterSendNo, String correspLetterRecNo, String pages,
			String needReply, String theme, String content,
			String sendRecvLettDate, String ccUnit, String requireReplyDate,
			FaxAttachment[] faxAttachment) {
		super();
		this.revision = revision;
		this.projectId = projectId;
		this.name = name;
		this.faxType = faxType;
		this.authorUnit = authorUnit;
		this.recivUnit = recivUnit;
		this.letterSendNo = letterSendNo;
		this.correspLetterRecNo = correspLetterRecNo;
		this.pages = pages;
		this.needReply = needReply;
		this.theme = theme;
		this.content = content;
		this.sendRecvLettDate = sendRecvLettDate;
		this.ccUnit = ccUnit;
		this.requireReplyDate = requireReplyDate;
		this.faxAttachment = faxAttachment;
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

	public String getFaxType() {
		return faxType;
	}

	public void setFaxType(String faxType) {
		this.faxType = faxType;
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

	public String getCorrespLetterRecNo() {
		return correspLetterRecNo;
	}

	public void setCorrespLetterRecNo(String correspLetterRecNo) {
		this.correspLetterRecNo = correspLetterRecNo;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getNeedReply() {
		return needReply;
	}

	public void setNeedReply(String needReply) {
		this.needReply = needReply;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendRecvLettDate() {
		return sendRecvLettDate;
	}

	public void setSendRecvLettDate(String sendRecvLettDate) {
		this.sendRecvLettDate = sendRecvLettDate;
	}

}
