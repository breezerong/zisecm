package com.ecm.core.entity;

import java.util.List;

public class EcmGroup extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String coding;

    private String groupType;

    private Long parentId;
    
    private boolean extended = false;
    
    List<EcmGroup> children ;

    public List<EcmGroup> getChildren() {
		return children;
	}

	public void setChildren(List<EcmGroup> children) {
		this.children = children;
	}

	public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding == null ? null : coding.trim();
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType == null ? null : groupType.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}
}