package org.zisecm.jobs.bean.conf;

import javax.xml.bind.annotation.XmlAttribute;

public class AttrBean {
	
	private String sourceName;
	
	private String targetName;
	
	private String defaultValue;
	@XmlAttribute
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	@XmlAttribute
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	@XmlAttribute
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
