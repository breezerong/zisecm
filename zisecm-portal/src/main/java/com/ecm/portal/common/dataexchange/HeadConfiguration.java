package com.ecm.portal.common.dataexchange;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HeadConfiguration {
	
	//@XmlAttribute(name="version")
	private String version;
	
	public String getVersion() {
		return version;
	}
	@XmlAttribute(name="version")
	public void setVersion(String version) {
		this.version = version;
	}

	
	private List<HeadBean> headBeans=new ArrayList<HeadBean>();

	public List<HeadBean> getHeadBeans() {
		return headBeans;
	}

	@XmlElement(name="headBean")
	public void setHeadBeans(List<HeadBean> headBeans) {
		this.headBeans = headBeans;
	}
}
