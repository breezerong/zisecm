package com.ecm.core.entity;

import java.util.Date;
/**
 * 可编辑对象
 * @author Haihong Rong
 * @date 2019年9月19日 下午6:24:21
 */
public class EcmSysObject extends EcmObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对象名称
	 */
	private String name;
	/**
	 * 对象说明
	 */
	private String description;
	/**
	 * 创建人
	 */
	private String creator;
    /**
	 * 创建时间
	 */
	private Date creationDate;
    /**
     * 修改人
     */
	private String modifier;
    /**
     * 修改时间
     */
	private Date modifiedDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		if(modifiedDate==null) {
			this.modifiedDate = creationDate;
		}
		this.creationDate = creationDate;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		if(modifier==null) {
			this.modifier = creator;
		}
		this.creator = creator;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
