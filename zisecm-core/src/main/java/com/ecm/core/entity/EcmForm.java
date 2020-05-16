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
    private List<EcmFormItem> ecmFormItems;
	public void setEcmFormItems(List<EcmFormItem> items) {
		// TODO Auto-generated method stub
		ecmFormItems = items;
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
				}
				list.add(itemc);
			}
		}else {
			list = ecmFormItems;
		}
		return list;
	}
}