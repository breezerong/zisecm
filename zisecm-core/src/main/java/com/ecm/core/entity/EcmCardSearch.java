package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;
/**
 * 卡片搜索，名称一定需要跟类型名相同
 * @author Haihong Rong
 * @date 2019年10月4日 下午1:55:51
 */
public class EcmCardSearch extends EcmSysObject{
	/**
	 * 排序
	 */
	private int orderIndex;
	
	/**
	 * 语言标签
	 */
	private String langKey;
	/**
	 * 是否启用
	 */
	private boolean enabled;
	/**
	 * 语言标签
	 */
	private String label;
	
	/**
	 * 列表名称
	 */
	private String gridView;
	
	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EcmCardSearchItem> ecmCardSearchItems;
    /**
     * @return the ecmCardSearchItems
     */
    public List<EcmCardSearchItem> getEcmCardSearchItems() {
        return ecmCardSearchItems;
    }

    /**
     * @param ecmCardSearchItems the ecmCardSearchItems to set
     */
    public void setEcmCardSearchItems(List<EcmCardSearchItem> ecmCardSearchItems) {
        this.ecmCardSearchItems = ecmCardSearchItems;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public EcmCardSearch clone(String lang, boolean includeItem) {
		EcmCardSearch en = new EcmCardSearch();
		en.setName(this.getName());
		en.setDescription(this.getDescription());
		en.setId(this.getId());
		en.setLangKey(langKey);
		en.setOrderIndex(getOrderIndex());
		en.setGridView(this.getGridView());
		en.setEnabled(isEnabled());
		String key = lang + "_system_" + langKey;
		String lan = CacheManagerOper.getLanguageCaches().get(key);
		if(lan != null && lan.length()>0) {
			en.setLabel(lan);
		}else {
			en.setLabel(this.getName());
		}
		if(includeItem) {
			if(ecmCardSearchItems != null) {
				List<EcmCardSearchItem> list = new ArrayList<EcmCardSearchItem>();
				for(EcmCardSearchItem item : ecmCardSearchItems) {
					EcmCardSearchItem newItem = item.clone();
					if(newItem.getEcmFormItem() != null) {
						String label = CacheManagerLanguage.getLanguage(langKey, this.getName(), newItem.getEcmFormItem().getAttrName());
						//如果类型属性未定义，获取默认标签
						if(StringUtils.isEmpty(label) && !StringUtils.isEmpty(this.getName())) {
							label = CacheManagerLanguage.getLanguage(langKey, newItem.getEcmFormItem().getAttrName());
						}
						if(label != null && label.length()>0) {
							newItem.getEcmFormItem().setLabel(label);
						}
						list.add(newItem);
					}
				}
				en.setEcmCardSearchItems(list);
			}
		}
		return en;
	}

	public String getGridView() {
		return gridView;
	}

	public void setGridView(String gridView) {
		this.gridView = gridView;
	}

}