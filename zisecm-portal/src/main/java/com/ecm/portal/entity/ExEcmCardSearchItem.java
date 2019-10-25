package com.ecm.portal.entity;

import java.util.List;

import com.ecm.core.entity.EcmCardSearchItem;
/**
 * 卡片查询字段
 * @author Administrator
 *
 */
public class ExEcmCardSearchItem extends EcmCardSearchItem {
	/**
	 * 获取属性名称
	 * @return
	 */
	public String getAttrName() {
		return attrName;
	}
	/**
	 * 设置属性名称
	 * @param attrName
	 */
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	/**
	 * 获取标签
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * 设置标签
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	private String attrName;
	private String label;
	//是否必填
    private Boolean required;
  public Boolean getRequired() {
	  return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<String> getValidValues() {
		return validValues;
	}
	public void setValidValues(List<String> validValues) {
		this.validValues = validValues;
	}
	//控件类型
    private String controlType;
  //默认值 从Category查询该项的默认值
    private String defaultValue;
  //List<String>,字符串类型从Category读取
    private List<String> validValues;
}
