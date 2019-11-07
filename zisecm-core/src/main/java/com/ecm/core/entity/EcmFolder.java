package com.ecm.core.entity;

import java.util.Date;
import java.util.List;

public class EcmFolder  extends EcmSysObject{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String status;

    private Date creationDate;

    private String creator;

    private String typeName;

    private String modifier;

    private Date modifiedDate;

    private String gridView;

    private String aclName;
    
    public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public String getFullCoding() {
		return fullCoding;
	}

	public void setFullCoding(String fullCoding) {
		this.fullCoding = fullCoding;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	private String coding;
    private String fullCoding;
    private String folderPath;
    private int orderIndex;
    
    public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	private boolean extended = false;
    
    private int childCount = 0;
    
    private List<EcmFolder> children;

    public List<EcmFolder> getChildren() {
		return children;
	}

	public void setChildren(List<EcmFolder> children) {
		this.children = children;
	}

	
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getGridView() {
        return gridView;
    }

    public void setGridView(String gridView) {
        this.gridView = gridView == null ? null : gridView.trim();
    }

    public String getAclName() {
        return aclName;
    }

    public void setAclName(String aclName) {
        this.aclName = aclName == null ? null : aclName.trim();
    }
}