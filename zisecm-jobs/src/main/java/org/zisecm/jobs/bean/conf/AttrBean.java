package org.zisecm.jobs.bean.conf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class AttrBean {
	
	private String sourceName;
	
	private String targetName;
	
	private String defaultValue;
	
	private String dataType;
	
	private String label;
	
	private SearchConf searchConf;
	
	
	@XmlElement(name="searchConf")
	public SearchConf getSearchConf() {
		return searchConf;
	}

	public void setSearchConf(SearchConf searchConf) {
		this.searchConf = searchConf;
	}
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
	@XmlAttribute
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@XmlAttribute
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
