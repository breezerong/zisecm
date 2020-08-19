package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ResultMessage",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ResultMessage",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class ResultMessage {
	
	private String projectId;
	
	private String correspLetterRecNo;
	
	private String cotnent;
	
	private String userid;
	
	private String type;
	
	private String rev;

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCorrespLetterRecNo() {
		return correspLetterRecNo;
	}

	public void setCorrespLetterRecNo(String correspLetterRecNo) {
		this.correspLetterRecNo = correspLetterRecNo;
	}

	public String getCotnent() {
		return cotnent;
	}

	public void setCotnent(String cotnent) {
		this.cotnent = cotnent;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ResultMessage() {
	}

	public ResultMessage(String projectId, String correspLetterRecNo,
			String cotnent, String userid, String type, String rev) {
		super();
		this.projectId = projectId;
		this.correspLetterRecNo = correspLetterRecNo;
		this.cotnent = cotnent;
		this.userid = userid;
		this.type = type;
		this.rev = rev;
	}

	
	
	
	
	

}
