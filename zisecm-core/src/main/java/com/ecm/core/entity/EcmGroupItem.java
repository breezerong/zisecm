package com.ecm.core.entity;

public class EcmGroupItem extends EcmObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String childId;

    /**
     * 1 用户，2角色
     */
    private String itemType;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }
}