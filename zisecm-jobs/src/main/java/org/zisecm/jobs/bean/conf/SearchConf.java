package org.zisecm.jobs.bean.conf;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class SearchConf {
	private String name;
	private List<Condition> Conditions;
	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlElementWrapper(name="conditions")
	@XmlElement(name="condition")
	public List<Condition> getConditions() {
		return Conditions;
	}

	public void setConditions(List<Condition> conditions) {
		Conditions = conditions;
	}
	
	
}
