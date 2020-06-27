package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;

public class EcmFormItem extends EcmObject{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String attrName;

    private Boolean required;

    private String validatePolicy;

    private Boolean readOnly;

    private Integer orderIndex;

    private String widthType;

    private String label;

    private Boolean searchable;

    private String controlType;

    private String queryName;

    private String defaultValue;

    private Boolean isHide;

    private String valueList;
    
	private Boolean isRepeat = false;
    
    private Integer minCount =0 ;
    
    private Integer maxCount =0;
    /**
     * 分类
     */
    private String classification;
    /**
     * 依赖字段名
     */
    private String dependName;
    public String getDependName() {
		return dependName;
	}

	public void setDependName(String dependName) {
		this.dependName = dependName;
	}

	public Boolean getEnableChange() {
		return enableChange;
	}

	public void setEnableChange(Boolean enableChange) {
		this.enableChange = enableChange;
	}

	/**
     * 启用变更事件
     */
    private Boolean enableChange=false;
    
    public Boolean getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(Boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public Integer getMinCount() {
		return minCount;
	}

	public void setMinCount(Integer minCount) {
		this.minCount = minCount;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getValidatePolicy() {
        return validatePolicy;
    }

    public void setValidatePolicy(String validatePolicy) {
        this.validatePolicy = validatePolicy == null ? null : validatePolicy.trim();
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getWidthType() {
        return widthType;
    }

    public void setWidthType(String widthType) {
        this.widthType = widthType == null ? null : widthType.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType == null ? null : controlType.trim();
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName == null ? null : queryName.trim();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }

    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public String getValueList() {
        return valueList;
    }

    public void setValueList(String valueList) {
        this.valueList = valueList == null ? null : valueList.trim();
    }

    List<String>  validValues;
	public void setValidValues(List<String> list) {
		// TODO Auto-generated method stub
		validValues = list;
	}
	
	public List<String> getValidValues() {
		// TODO Auto-generated method stub
		return validValues;
	}
	
	

	public EcmFormItem clone(String lang) {
		EcmFormItem item = new EcmFormItem();
		item.setAttrName(attrName);
		item.setControlType(controlType);
		item.setDefaultValue(defaultValue);
		item.setId(this.getId());
		item.setIsHide(isHide);
		item.setIsRepeat(isRepeat);
		item.setClassification(CacheManagerLangInfo.getLanguageLabel(lang,classification));
		item.setLabel(CacheManagerLangInfo.getLanguageLabel(lang,label));
		//item.setLabel(label);
		item.setMaxCount(maxCount);
		item.setMinCount(minCount);
		item.setOrderIndex(orderIndex);
		item.setParentId(parentId);
		item.setQueryName(queryName);
		item.setReadOnly(readOnly);
		item.setRequired(required);
		item.setSearchable(searchable);
		item.setValidatePolicy(validatePolicy);
		item.setValidValues(this.getValidValues());
		item.setValueList(valueList);
		item.setWidthType(widthType);
		item.setEnableChange(enableChange);
		item.setDependName(dependName);
		return item;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
}