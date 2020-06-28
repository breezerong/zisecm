package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecm.common.util.DateUtils;
import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;
import com.ecm.icore.service.IEcmSession;

public class EcmForm extends EcmSysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String typeName;

    private Integer columnCount;

    private String action;

    private Boolean isDefault;

    private String searchField;
    /**
     * 分类，可以使用语言标签，多个用英文分号分隔
     */
    private String classifications;
    
    List<String> list = new ArrayList<String>();
    
    public List<String> getClassificationList(String langKey) {
    	list.clear();
    	if(classifications != null && classifications.length()>0) {
    		String[] strs = classifications.split(";");
    		for(String s: strs) {
    			list.add(CacheManagerLangInfo.getLanguageLabel(langKey,s));
    		}
    	}
		return list;
	}
    
    public List<String> getClassificationList() {
    	list.clear();
    	if(classifications != null && classifications.length()>0) {
    		String[] strs = classifications.split(";");
    		for(String s: strs) {
    			list.add(s);
    		}
    	}
		return list;
	}

    
    public String getClassifications() {
		return classifications;
	}

	public void setClassifications(String classifications) {
		this.classifications = classifications;
	}

	public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField == null ? null : searchField.trim();
    }
    
    List<EcmFormClassification> formClassifications = new ArrayList<EcmFormClassification>();
	public List<EcmFormClassification> getFormClassifications() {
		return formClassifications;
	}
	/**
	 * 获取分类属性定义
	 * @param session
	 * @param langKey 语言
	 * @return
	 */
	public List<EcmFormClassification> getFormClassifications(IEcmSession session,String langKey) {
		List<EcmFormClassification> list = new ArrayList<EcmFormClassification>();
		for(EcmFormClassification fc: formClassifications) {
			EcmFormClassification en = new EcmFormClassification();
			en.setLabel(CacheManagerLangInfo.getLanguageLabel(langKey,fc.getLabel()));
			en.setEcmFormItems(fc.getEcmFormItems(session, langKey));
			list.add(en);
		}
		return list;
	}
	
    private List<EcmFormItem> ecmFormItems;
	public void setEcmFormItems(List<EcmFormItem> items) {
		// TODO Auto-generated method stub
		ecmFormItems = items;
		formClassifications.clear();
		if(items!=null) {
			for(EcmFormItem item:items) {
				addToClassification(item);
			}
		}
	}
	
	private void addToClassification(EcmFormItem item) {
		for(EcmFormClassification fc: formClassifications) {
			if(fc.getLabel().equals(item.getClassification())) {
				fc.getEcmFormItems().add(item);
				return;
			}
		}
		EcmFormClassification fc = new EcmFormClassification();
		fc.setLabel(item.getClassification());
		fc.getEcmFormItems().add(item);
	}

	List<EcmFormItem> ecmFormSearchItems;
	public List<EcmFormItem> getEcmFormSearchItems() {
		// TODO Auto-generated method stub
		return ecmFormSearchItems;
	}
	
	public void setEcmFormSearchItems(List<EcmFormItem> items) {
		// TODO Auto-generated method stub
		ecmFormSearchItems = items;
	}

	public List<EcmFormItem> getEcmFormItems() {
		// TODO Auto-generated method stub
		return ecmFormItems;
	}
	/**
	 * 获取所有属性定义
	 * @param session
	 * @param langKey 语言
	 * @return
	 */
	public List<EcmFormItem> getEcmFormItems(IEcmSession session,String langKey) {
		// TODO Auto-generated method stub
		List<EcmFormItem> list = new ArrayList<EcmFormItem>();
		if(ecmFormItems != null ) {
			
			for(EcmFormItem item : ecmFormItems) {
				EcmFormItem itemc = item.clone(langKey);
				String defaultValue = itemc.getDefaultValue();
				if("{now}".equalsIgnoreCase(defaultValue)) {
					itemc.setDefaultValue(DateUtils.currentDate("yyyy-MM-dd"));
				}else if("{username}".equalsIgnoreCase(defaultValue)) {
					itemc.setDefaultValue(session.getCurrentUser().getUserName());
				}else if("{department}".equalsIgnoreCase(defaultValue)) {
					itemc.setDefaultValue(session.getCurrentUser().getDepartment());
				}else if("{company}".equalsIgnoreCase(defaultValue)) {
					itemc.setDefaultValue(session.getCurrentUser().getCompany());
				}
				list.add(itemc);
			}
		}else {
			list = ecmFormItems;
		}
		return list;
	}
}