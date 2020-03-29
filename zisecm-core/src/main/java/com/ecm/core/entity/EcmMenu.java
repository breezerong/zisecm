package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;

public class EcmMenu extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String label;

    private String langKey;
    
    private List<EcmMenuItem> menuItems;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey == null ? null : langKey.trim();
    }

	public List<EcmMenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<EcmMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	public EcmMenu clone(String lang) {
		EcmMenu m = new EcmMenu();
		m.setId(getId());
		m.setName(getName());
		m.setDescription(getDescription());
		m.setLangKey(langKey);
		m.setLabel(CacheManagerLangInfo.getLanguageLabel(lang,label));
		if(menuItems != null) {
			List<EcmMenuItem> items = new ArrayList<EcmMenuItem>();
    		for(EcmMenuItem item:menuItems) {
    			items.add(item.clone(lang));
    		}
    		m.setMenuItems(items);
		}
		return m;
	}
}