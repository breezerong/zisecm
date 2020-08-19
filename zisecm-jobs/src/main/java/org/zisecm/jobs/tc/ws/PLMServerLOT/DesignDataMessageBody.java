package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DesignDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DesignDataMessageBody",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class DesignDataMessageBody implements java.io.Serializable {

	//private String code;
	
	private String revision;
	
	private String projectId;
	
	private String letterSendNo;//我方发文号
	
	private String sendLotDate;
    
    private String sendLotNo;

    
    
	public DesignDataMessageBody() {
	}

	public DesignDataMessageBody(String revision, String projectId,
			String letterSendNo, String sendLotDate, String sendLotNo) {
		super();
		this.revision = revision;
		this.projectId = projectId;
		this.letterSendNo = letterSendNo;
		this.sendLotDate = sendLotDate;
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

	public String getLetterSendNo() {
		return letterSendNo;
	}

	public void setLetterSendNo(String letterSendNo) {
		this.letterSendNo = letterSendNo;
	}

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

	
	
	
	
}
