/**
 * CRDataMessageBody.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
//PLMServerLOT.ws.cnpe.com

@XmlRootElement(name="IITFDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IITFDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class IITFDataMessageBody  implements java.io.Serializable {
	
	
	
    private java.lang.String projectid;

    private  String revisionID;

   
    //发至
    private String sendTo;
    
    //发自
    private String sendFrom;
    //收发文日期
    private String sendRecvLettDate;
    //版本
    
    //附件数
    private String pages;
    //我方发文号
    
    private String ourDocNum;
    //状态  PREExchange  FINExchange
    private String preOrFin;
    //专业
    private String displ;
    //第一方 cn91stParty
    private String oneParty;
    //第二方 2ndParty
    private String twoParty;
    //接口类型
    private String intfcType;
    //流水号 
    private String serNum;
    //发布方
    private String releaseParty;
    //接收方 
    private String recvParty;
    //ICM日期 ICMDate
    private String icmDate;
    //接口详细描述  IntfcDetailDescr
    private String intfcDetailDescr;
    //接口信息  intfcInfo
    private String intfcInfo;
    //邮寄光盘
    private String ifSendCD;
    
    private String sendLotNo;
	
	private String sendLotDate;
    
	
    public IITFDataMessageBody(String projectid, String revisionID,
			String sendTo, String sendFrom, String sendRecvLettDate,
			String pages, String ourDocNum, String preOrFin, String displ,
			String oneParty, String twoParty, String intfcType, String serNum,
			String releaseParty, String recvParty, String icmDate,
			String intfcDetailDescr, String intfcInfo, String ifSendCD,
			String sendLotNo, String sendLotDate, IITFAttachment[] attachments) {
		super();
		this.projectid = projectid;
		this.revisionID = revisionID;
		this.sendTo = sendTo;
		this.sendFrom = sendFrom;
		this.sendRecvLettDate = sendRecvLettDate;
		this.pages = pages;
		this.ourDocNum = ourDocNum;
		this.preOrFin = preOrFin;
		this.displ = displ;
		this.oneParty = oneParty;
		this.twoParty = twoParty;
		this.intfcType = intfcType;
		this.serNum = serNum;
		this.releaseParty = releaseParty;
		this.recvParty = recvParty;
		this.icmDate = icmDate;
		this.intfcDetailDescr = intfcDetailDescr;
		this.intfcInfo = intfcInfo;
		this.ifSendCD = ifSendCD;
		this.sendLotNo = sendLotNo;
		this.sendLotDate = sendLotDate;
		this.attachments = attachments;
	}
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
	private IITFAttachment[] attachments;
    
	public IITFAttachment[] getAttachments() {
		return attachments;
	}
	public void setAttachments(IITFAttachment[] attachments) {
		this.attachments = attachments;
	}
	public IITFDataMessageBody() {
		super();
	}
	
	public java.lang.String getProjectid() {
		return projectid;
	}
	public void setProjectid(java.lang.String projectid) {
		this.projectid = projectid;
	}
	public String getRevisionID() {
		return revisionID;
	}
	public void setRevisionID(String revisionID) {
		this.revisionID = revisionID;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	public String getSendRecvLettDate() {
		return sendRecvLettDate;
	}
	public void setSendRecvLettDate(String sendRecvLettDate) {
		this.sendRecvLettDate = sendRecvLettDate;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getOurDocNum() {
		return ourDocNum;
	}
	public void setOurDocNum(String ourDocNum) {
		this.ourDocNum = ourDocNum;
	}
	public String getPreOrFin() {
		return preOrFin;
	}
	public void setPreOrFin(String preOrFin) {
		this.preOrFin = preOrFin;
	}
	public String getDispl() {
		return displ;
	}
	public void setDispl(String displ) {
		this.displ = displ;
	}
	public String getOneParty() {
		return oneParty;
	}
	public void setOneParty(String oneParty) {
		this.oneParty = oneParty;
	}
	public String getTwoParty() {
		return twoParty;
	}
	public void setTwoParty(String twoParty) {
		this.twoParty = twoParty;
	}
	public String getIntfcType() {
		return intfcType;
	}
	public void setIntfcType(String intfcType) {
		this.intfcType = intfcType;
	}
	public String getSerNum() {
		return serNum;
	}
	public void setSerNum(String serNum) {
		this.serNum = serNum;
	}
	public String getReleaseParty() {
		return releaseParty;
	}
	public void setReleaseParty(String releaseParty) {
		this.releaseParty = releaseParty;
	}
	public String getRecvParty() {
		return recvParty;
	}
	public void setRecvParty(String recvParty) {
		this.recvParty = recvParty;
	}
	public String getIcmDate() {
		return icmDate;
	}
	public void setIcmDate(String icmDate) {
		this.icmDate = icmDate;
	}
	public String getIntfcDetailDescr() {
		return intfcDetailDescr;
	}
	public void setIntfcDetailDescr(String intfcDetailDescr) {
		this.intfcDetailDescr = intfcDetailDescr;
	}
	public String getIntfcInfo() {
		return intfcInfo;
	}
	public void setIntfcInfo(String intfcInfo) {
		this.intfcInfo = intfcInfo;
	}
	public String getIfSendCD() {
		return ifSendCD;
	}
	public void setIfSendCD(String ifSendCD) {
		this.ifSendCD = ifSendCD;
	}
    
    
    
    
    
    
}
