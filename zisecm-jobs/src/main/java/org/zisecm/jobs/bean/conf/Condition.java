package org.zisecm.jobs.bean.conf;

import javax.xml.bind.annotation.XmlAttribute;

public class Condition {
	private String name;
	private String value;
	@XmlAttribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
