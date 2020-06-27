package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecm.common.util.DateUtils;
import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;
import com.ecm.icore.service.IEcmSession;

public class EcmFormClassification extends EcmSysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String label;

	private List<EcmFormItem> ecmFormItems = new ArrayList<EcmFormItem>();
	 
	public List<EcmFormItem> getEcmFormItems() {
		return ecmFormItems;
	}


	public void setEcmFormItems(List<EcmFormItem> ecmFormItems) {
		this.ecmFormItems = ecmFormItems;
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


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}
}