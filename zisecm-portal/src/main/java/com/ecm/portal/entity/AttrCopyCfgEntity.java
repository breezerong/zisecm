package com.ecm.portal.entity;

import java.util.HashMap;
import java.util.Map;
/**
 * 属性复制配置
 * @Title:
 * @author Haihong Rong
 * @date:   2020-12-22 07:58:01 
 * @Description:
 */
public class AttrCopyCfgEntity {
	/**
	 * 被复制类型
	 */
	private String fromType;
	public String getFromType() {
		return fromType;
	}
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}
	public String getToType() {
		return toType;
	}
	public void setToType(String toType) {
		this.toType = toType;
	}
	public Map<String, String> getAttrNames() {
		return attrNames;
	}
	public void setAttrNames(Map<String, String> attrNames) {
		this.attrNames = attrNames;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * 复制类型
	 */
	private String toType;
	/**
	 * 复制属性配置，key:目标属性名，value：源属性名
	 */
	private Map<String,String> attrNames = new HashMap<String,String>();
	
	private String description;

	/**
	 * 是否启用
	 */
	private boolean enabled = true;

}
