package com.ecm.core.entity;

import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;

public class EcmGridViewItem extends EcmObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String label;

    private String width;

    private String visibleType;

    private String attrName;

    private Boolean allowOrderby;

    private Integer orderIndex;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width == null ? null : width.trim();
    }

    public String getVisibleType() {
        return visibleType;
    }

    public void setVisibleType(String visibleType) {
        this.visibleType = visibleType == null ? null : visibleType.trim();
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public Boolean getAllowOrderby() {
        return allowOrderby;
    }

    public void setAllowOrderby(Boolean allowOrderby) {
        this.allowOrderby = allowOrderby;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    public EcmGridViewItem clone(String lang) {
    	EcmGridViewItem item = new EcmGridViewItem();
    	item.setAttrName(attrName);
    	item.setId(getId());
    	item.setLabel(CacheManagerLangInfo.getLanguageLabel(lang,label));
    	item.setOrderIndex(orderIndex);
    	item.setVisibleType(visibleType);
    	item.setWidth(width);
    	item.setParentId(parentId);
    	item.setAllowOrderby(getAllowOrderby());
    	return item;
    }
}