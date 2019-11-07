package com.ecm.core.entity;


public class EcmRelation extends EcmSysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String childId;
    
    private int orderIndex;

    public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId == null ? null : childId.trim();
    }

   
}