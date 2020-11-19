package com.ecm.core.entity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ecm.common.util.DateUtils;
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

    private String widthType = "4";

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
    
    private Map<String, Object> attributes = new HashMap<String, Object>();
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
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public Object getAttributeValue(String key) {
		Object result=null;
		switch (key.toUpperCase()) {
		case "ID":
			result=this.getId();
			break;
		case "ORDER_INDEX":
			result=this.getOrderIndex();
			break;
		case "LABLE":
			result=this.getLabel();
			break;
		case "ATTR_NAME":
			result=this.getAttrName();
			break;
		case "CLASSIFICATION":
			result=this.getClassification();
			break;
		case "REQUIRED":
			result=this.getRequired();
			break;
		case "DEFAULT_VALUE":
			result=this.getDefaultValue();
			break;
		case "value_list":
			result=this.getValueList();
			break;
		case "CONTRIOL_TYPE":
			result=this.getControlType();
			break;
		case "is_hide":
			result=this.getIsHide();
			break;
		case "QUERY_NAME":
			result=this.getQueryName();
			break;
		case "READ_ONLY":
			result=this.getReadOnly();
			break;
		default:
			result=attributes.get(key.toUpperCase());
			break;
		}
		return result;
	}
	
	public void addAttribute(String key,Object value) {
		
		if(this.attributes==null) {
			this.attributes=new HashMap<String, Object>();
		}
		
		switch (key.toUpperCase()) {
		case "ID":
			this.setId(getString(value));
			break;
		case "ORDER_INDEX":
			this.setOrderIndex(Integer.parseInt(value.toString()));;
			break;
		case "LABLE":
			this.setLabel(getString(value));
			break;
		case "ATTR_NAME":
			this.setAttrName(getString(value));
			break;
		case "CLASSIFICATION":
			this.setClassification(getString(value));
			break;
		case "REQUIRED":
			this.setRequired(getString(value).equals("Y")?true:false);
			break;
		case "DEFAULT_VALUE":
			this.setDefaultValue(getString(value));
			break;
		case "value_list":
			this.setValueList(getString(value));
			break;
		case "CONTRIOL_TYPE":
			this.setControlType(getString(value));
			break;
		case "is_hide":
			this.setIsHide(getString(value).equals("1")?true:false);
			break;
		case "QUERY_NAME":
			this.setQueryName(getString(value));
			break;
		case "READ_ONLY":
			this.setReadOnly(getString(value).equals("1")?true:false);
			break;
		default:
			attributes.put(key.toUpperCase(), value);
			break;
		}
		
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
		if(attributes.get("ID")!=null) {
			this.setId(getString(attributes.get("ID")));
		}
		if(attributes.get("ORDER_INDEX")!=null) {
			this.setOrderIndex(Integer.parseInt(attributes.get("ORDER_INDEX").toString()));
		}
		if(attributes.get("LABLE")!=null) {
			this.setLabel(getString(attributes.get("LABLE")));
		}
		if(attributes.get("ATTR_NAME")!=null) {
			this.setAttrName(getString(attributes.get("ATTR_NAME")));
		}
		if(attributes.get("CLASSIFICATION")!=null) {
			this.setClassification(getString(attributes.get("CLASSIFICATION")));
		}
		if(attributes.get("REQUIRED")!=null) {
			this.setRequired(attributes.get("REQUIRED").toString().equals("Y")?true:false);
		}
		if(attributes.get("DEFAULT_VALUE")!=null) {
			this.setDefaultValue(getString(attributes.get("DEFAULT_VALUE")));
		}
		if(attributes.get("value_list")!=null) {
			this.setValueList(getString(attributes.get("value_list")));
		}
		if(attributes.get("CONTRIOL_TYPE")!=null) {
			this.setControlType(getString(attributes.get("CONTRIOL_TYPE")));
		}
		if(attributes.get("is_hide")!=null) {
			this.setIsHide(attributes.get("is_hide").toString().equals("1")?true:false);
		}
		if(attributes.get("QUERY_NAME")!=null) {
			this.setQueryName(getString(attributes.get("QUERY_NAME")));
		}
	    if(attributes.get("READ_ONLY")!=null) {
	    	this.setReadOnly(attributes.get("READ_ONLY").toString().equals("1")?true:false);
	    }
	   
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
		if(this.getValidValues()!=null) {
			List<String> list = new ArrayList<String>();
			for(String str: this.getValidValues()) {
				list.add(str);
			}
			item.setValidValues(list);
		}
		item.setValueList(valueList);
		item.setWidthType(widthType);
		item.setEnableChange(enableChange);
		item.setDependName(dependName);
		return item;
	}

	public String getClassification() {
		if(classification==null || classification.equals("")) {
			return "attr_cls_basicinfo";
		}
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	private String getString(Object val) {
		if(val==null) {
			return null;
		}
		return val.toString();
	}
	
	private Date getDate(Object val) {
		if(val == null) {
			return null;
		}
		else if(val instanceof Date) {
			return (Date)val;
		}
		else if(val instanceof Long) {
			return new Date((long)val);
		}
		try {
			return DateUtils.sdfAll.parse(val.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//	public boolean isHidden() {
//		return isHide;
//	}
//
//	public void setHidden(boolean isHidden) {
//		this.isHide = isHidden;
//		if(attributes!=null) {
//			attributes.put("IS_HIDDEN",  this.isHide?1:0);
//		}
//	}
}