package com.ecm.core.entity;

import java.util.List;

public class EcmGroup extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String coding;

    private String groupType;

    private String parentId;
    
    private boolean extended = false;
    
    private String extendId;
    
    private String manager;
    
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	
}