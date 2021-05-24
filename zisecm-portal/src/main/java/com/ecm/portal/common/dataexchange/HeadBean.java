package com.ecm.portal.common.dataexchange;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class HeadBean {
	
	private List<HeadAttrBean> headAttrs =new ArrayList<HeadAttrBean>();
	
	
	private String name;

	public String getName() {
		return name;
	}
	
	@XmlAttribute(name="name")
	public void setName(String name) {
		this.name = name;
	}

	public List<HeadAttrBean> getHeadAttrs() {
		return headAttrs;
	}

	@XmlElement(name="headAttrBean")
	public void setHeadAttrs(List<HeadAttrBean> headAttrs) {
		this.headAttrs = headAttrs;
	}
	

}
