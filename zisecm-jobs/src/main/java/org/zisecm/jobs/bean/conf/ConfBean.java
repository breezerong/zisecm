package org.zisecm.jobs.bean.conf;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ConfBean {
	
	private List<AttrBean> attributes;
	@XmlElementWrapper(name="attributes")
	@XmlElement(name="attribute")
	public List<AttrBean> getAttributes() {
		return attributes;
	}
	@XmlAttribute
	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}
	@XmlAttribute
	public String getTcTypeName() {
		return tcTypeName;
	}

	public void setTcTypeName(String tcTypeName) {
		this.tcTypeName = tcTypeName;
	}
	@XmlAttribute
	public String getTcTableName() {
		return tcTableName;
	}

	public void setTcTableName(String tcTableName) {
		this.tcTableName = tcTableName;
	}

	public void setAttributes(List<AttrBean> attributes) {
		this.attributes = attributes;
	}
	
	@XmlAttribute
	public String getTargetSystem() {
		return targetSystem;
	}
	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}


	private String sourceTypeName;
	
	private String tcTypeName;
	
	private String tcTableName;
	
	private String targetSystem;
	
	
}
