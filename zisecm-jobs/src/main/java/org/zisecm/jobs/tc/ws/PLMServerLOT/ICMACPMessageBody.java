package org.zisecm.jobs.tc.ws.PLMServerLOT;

public class ICMACPMessageBody implements java.io.Serializable{
	
	private String projectid;
	
	private String oneParty;//第一方
	private String twoParty;//第二方
	private String intfcType;//接口类型
	private String serNum; //流水号
	private String offerParty; //发布方
	private String recvParty;//接收方
	private String icmLevel;//ICMLevel
	private String intfcDetailDescr;//接口详细描述
	private String preIntfcForDate;//初版预报日期
	private String finIntfcForDate;//终版预报日期
	private String preStatus;//初版状态
	private String finStatus;//终版状态
	private String displCode;//专业代码
	private String respDepart;//责任所
	//private String deptRoom;//所内专业室;
	//private String deptAuthor;//所内编制人
	private String resbChiefEnginner;//责任设总
	private String dcslot;
	private String batch;
	//private String intfcIdent;//接口标识
	private String lotPachNo;//Lot包号
	//private String remark;//备注
	/*
	private String preCloseDate;//初版关闭日期
	private String preCloseNum;//初版关闭单号
	private String finCloseDate; //终版关闭日期
	private String finCloseNum;//终版关闭单号
	private String openDelayReason;//打开延误情况说明
	private String dealDelayReason;//处理延误情况说明
	private String docReqDateLimit;//资料需求期限
	
	*/
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
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
	public String getOfferParty() {
		return offerParty;
	}
	public void setOfferParty(String offerParty) {
		this.offerParty = offerParty;
	}
	public String getRecvParty() {
		return recvParty;
	}
	public void setRecvParty(String recvParty) {
		this.recvParty = recvParty;
	}
	public String getIcmLevel() {
		return icmLevel;
	}
	public void setIcmLevel(String icmLevel) {
		this.icmLevel = icmLevel;
	}
	public String getIntfcDetailDescr() {
		return intfcDetailDescr;
	}
	public void setIntfcDetailDescr(String intfcDetailDescr) {
		this.intfcDetailDescr = intfcDetailDescr;
	}
	public String getPreIntfcForDate() {
		return preIntfcForDate;
	}
	public void setPreIntfcForDate(String preIntfcForDate) {
		this.preIntfcForDate = preIntfcForDate;
	}
	public String getFinIntfcForDate() {
		return finIntfcForDate;
	}
	public void setFinIntfcForDate(String finIntfcForDate) {
		this.finIntfcForDate = finIntfcForDate;
	}
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public String getFinStatus() {
		return finStatus;
	}
	public void setFinStatus(String finStatus) {
		this.finStatus = finStatus;
	}
	public String getDisplCode() {
		return displCode;
	}
	public void setDisplCode(String displCode) {
		this.displCode = displCode;
	}
	public String getRespDepart() {
		return respDepart;
	}
	public void setRespDepart(String respDepart) {
		this.respDepart = respDepart;
	}
	public String getResbChiefEnginner() {
		return resbChiefEnginner;
	}
	public void setResbChiefEnginner(String resbChiefEnginner) {
		this.resbChiefEnginner = resbChiefEnginner;
	}
	public String getDcslot() {
		return dcslot;
	}
	public void setDcslot(String dcslot) {
		this.dcslot = dcslot;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getLotPachNo() {
		return lotPachNo;
	}
	public void setLotPachNo(String lotPachNo) {
		this.lotPachNo = lotPachNo;
	}
	public ICMACPMessageBody() {
	}
	public ICMACPMessageBody(String oneParty, String twoParty,
			String intfcType, String serNum, String offerParty,
			String recvParty, String icmLevel, String intfcDetailDescr,
			String preIntfcForDate, String finIntfcForDate, String preStatus,
			String finStatus, String displCode, String respDepart,
			String resbChiefEnginner, String dcslot, String batch,
			String lotPachNo) {
		super();
		this.oneParty = oneParty;
		this.twoParty = twoParty;
		this.intfcType = intfcType;
		this.serNum = serNum;
		this.offerParty = offerParty;
		this.recvParty = recvParty;
		this.icmLevel = icmLevel;
		this.intfcDetailDescr = intfcDetailDescr;
		this.preIntfcForDate = preIntfcForDate;
		this.finIntfcForDate = finIntfcForDate;
		this.preStatus = preStatus;
		this.finStatus = finStatus;
		this.displCode = displCode;
		this.respDepart = respDepart;
		this.resbChiefEnginner = resbChiefEnginner;
		this.dcslot = dcslot;
		this.batch = batch;
		this.lotPachNo = lotPachNo;
	}
	
	
	
}
