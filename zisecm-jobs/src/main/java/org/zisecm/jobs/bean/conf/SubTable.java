package org.zisecm.jobs.bean.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class SubTable {
	
	private List<RelationShip> formoperation;
	
	private List<RelationShip> relationoperation;
	
	private List<RelationShip> suboperation;
	
	private List<RelationShip> mainattachs;
	
	private List<RelationShip> subattachs;
	
	
	@XmlElementWrapper(name="formoperation")
	@XmlElement(name="relationShip")
	public List<RelationShip> getFormoperation() {
		return formoperation;
	}
	@XmlElementWrapper(name="subattachs")
	@XmlElement(name="relationShip")
	public List<RelationShip> getSubattachs() {
		return subattachs;
	}

	public void setSubattachs(List<RelationShip> subattachs) {
		this.subattachs = subattachs;
	}

	@XmlElementWrapper(name="mainattachs")
	@XmlElement(name="relationShip")
	public List<RelationShip> getMainattachs() {
		return mainattachs;
	}

	public void setMainattachs(List<RelationShip> mainattachs) {
		this.mainattachs = mainattachs;
	}

	public void setFormoperation(List<RelationShip> formoperation) {
		this.formoperation = formoperation;
	}
	@XmlElementWrapper(name="relationoperation")
	@XmlElement(name="relationShip")
	public List<RelationShip> getRelationoperation() {
		return relationoperation;
	}

	public void setRelationoperation(List<RelationShip> relationoperation) {
		this.relationoperation = relationoperation;
	}
	@XmlElementWrapper(name="suboperation")
	@XmlElement(name="relationShip")
	public List<RelationShip> getSuboperation() {
		return suboperation;
	}

	public void setSuboperation(List<RelationShip> suboperation) {
		this.suboperation = suboperation;
	}
	
}
