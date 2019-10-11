package com.ecm.core.entity;

public class EcmMenu extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roleName;

    private String parentId;

    private String componentId;

    private String icon;

    private String itemType;

    private String callFunction;

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

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
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
    
    public EcmMenu clone() {
    	EcmMenu obj =new EcmMenu();
    	obj.setCallFunction(callFunction);
    	obj.setDescription(getDescription());
    	obj.setHasPermission(hasPermission);
    	obj.setId("");
    	obj.setName(getName());
    	obj.setIcon(icon);
    	obj.setItemType(itemType);
    	obj.setOrderIndex(orderIndex);
    	obj.setParentId(parentId);
    	obj.setComponentId(componentId);
    	obj.setRoleName(roleName);
    	return obj;
    }
}