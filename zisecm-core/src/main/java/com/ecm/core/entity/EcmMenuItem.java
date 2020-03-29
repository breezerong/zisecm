package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;

public class EcmMenuItem extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roleName;

    private String parentId;

    private String componentName;

    private String icon;

    private String itemType;

    private String callFunction;
    
    private String label;
    
    private List<EcmMenuItem> submenus;
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	private String url;

    private String parameter;
    
    public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	private String langKey;
    
    private String menuName;

    private Integer orderIndex;
    
    private boolean hasPermission;

    public boolean isHasPermission() {
		return hasPermission;
	}

	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
	}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getCallFunction() {
        return callFunction;
    }

    public void setCallFunction(String callFunction) {
        this.callFunction = callFunction == null ? null : callFunction.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    public EcmMenuItem clone(String lang) {
    	EcmMenuItem obj =new EcmMenuItem();
    	obj.setCallFunction(callFunction);
    	obj.setDescription(getDescription());
    	obj.setHasPermission(hasPermission);
    	obj.setId(getId());
    	obj.setName(getName());
    	obj.setIcon(icon);
    	obj.setItemType(itemType);
    	obj.setOrderIndex(orderIndex);
    	obj.setParentId(parentId);
    	obj.setComponentName(componentName);
    	obj.setRoleName(roleName);
    	obj.setLangKey(langKey);
    	obj.setMenuName(menuName);
    	obj.setLabel(CacheManagerLangInfo.getLanguageLabel(lang,label));
    	obj.setUrl(url);
    	obj.setParameter(parameter);
    	if(submenus != null) {
    		List<EcmMenuItem> items = new ArrayList<EcmMenuItem>();
    		for(EcmMenuItem item:submenus) {
    			items.add(item.clone(lang));
    		}
    		obj.setSubmenus(items);
    	}
    	return obj;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<EcmMenuItem> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<EcmMenuItem> submenus) {
		this.submenus = submenus;
	}
}