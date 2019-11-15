package com.ecm.core.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EcmDocument extends EcmSysObject{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 文件夹ID
	 */
    private String folderId;
    /**
     * 文件夹路径
     */
    private String folderPath;
    
    /**
     * 状态
     */
    private String status;
    /**
     * 系统类型名称
     */
    private String typeName;

    /**
     * 业务版本
     */
    private String revision;
    /**
     * 编码
     */
    private String coding;
    /**
     * Acl名称
     */
    private String aclName;
    /**
     * 主格式名称
     */
    private String formatName;
    /**
     * 主格式大小
     */
    private Long contentSize;
    /**
     * 附件数
     */
    private Integer attachmentCount;
    /**
     * 业务子类
     */
    private String subType;
    /**
     * 业务类型
     */
    private String objectType;
    /**
     * 所有者
     */
    private String ownerName;
    /**
     * 版本ID
     */
    private String versionId;
    /**
     * 系统版本
     */
    private Double systemVersion;
    /**
     * 是否当前版本
     */
    private boolean isCurrent;
    /**
     * 是否隐藏
     */
    private boolean isHidden;
    /**
     * 锁定用户
     */
	private String lockOwner;
    /**
     * 锁定日期
     */
    private Date lockDate;
    /**
     * 锁定客户端信息
     */
    private String lockClient;
    /**
     * 生命周期名称
     */
    private String lifecycleName;
    public String getLifecycleName() {
		return lifecycleName;
	}

	public void setLifecycleName(String lifecycleName) {
		this.lifecycleName = lifecycleName;
		if(attributes!=null) {
			attributes.put("LIFECYCLE_NAME", this.lifecycleName);
		}
	}

	public String getLifecycleStatus() {
		return lifecycleStatus;
	}

	public void setLifecycleStatus(String lifecycleStatus) {
		this.lifecycleStatus = lifecycleStatus;
		if(attributes!=null) {
			attributes.put("LIFECYCLE_STATUS", this.lifecycleStatus);
		}
	}
	
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public int getLifecycleDir() {
		return lifecycleDir;
	}

	public void setLifecycleDir(int lifecycleDir) {
		this.lifecycleDir = lifecycleDir;
		if(attributes!=null) {
			attributes.put("LIFECYCLE_DIR", this.lifecycleDir);
		}
	}



	/**
     * 生命周期状态
     */
    private String lifecycleStatus;
    /**
     * 生命周期方向，0：回退,1：默认，2：前进
     */
    private int lifecycleDir=1;
    
    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    public String getLockOwner() {
		return lockOwner;
	}

	public void setLockOwner(String lockOwner) {
		this.lockOwner = lockOwner;
		if(attributes!=null) {
			attributes.put("LOCK_OWNER", this.lockOwner);
		}
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
		if(attributes!=null) {
			attributes.put("LOCK_DATE", this.lockDate);
		}
	}

	public String getLockClient() {
		return lockClient;
	}

	public void setLockClient(String lockClient) {
		this.lockClient = lockClient;
		if(attributes!=null) {
			attributes.put("LOCK_CLIENT", this.lockClient);
		}
	}

    public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
		if(attributes!=null) {
			attributes.put("VERSION_ID", this.versionId);
		}
	}

	public Double getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(Double systemVersion) {
		this.systemVersion = systemVersion;
		if(attributes!=null) {
			attributes.put("SYSTEM_VERSION", this.systemVersion);
		}
	}

	
    
    private Map<String, Object> objectValues;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
        if(attributes!=null) {
			attributes.put("TITLE",  this.title);
		}
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
        if(attributes!=null) {
			attributes.put("FOLDER_ID",  this.folderId);
		}
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
        if(attributes!=null) {
			attributes.put("STATUS",  this.status);
		}
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
        if(attributes!=null) {
			attributes.put("TYPE_NAME",  this.typeName);
		}
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision == null ? null : revision.trim();
        if(attributes!=null) {
			attributes.put("REVISION",  this.revision);
		}
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding == null ? null : coding.trim();
        if(attributes!=null) {
			attributes.put("CODING",  this.coding);
		}
    }

    public String getAclName() {
        return aclName;
    }

    public void setAclName(String aclName) {
        this.aclName = aclName == null ? null : aclName.trim();
        if(attributes!=null) {
			attributes.put("ACL_NAME",  this.aclName);
		}
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName == null ? null : formatName.trim();
        if(attributes!=null) {
			attributes.put("FORMAT_NAME",  this.formatName);
		}
    }

    public Long getContentSize() {
        return contentSize;
    }

    public void setContentSize(Long contentSize) {
        this.contentSize = contentSize;
        if(attributes!=null) {
			attributes.put("CONTENT_SIZE",  this.contentSize);
		}
    }

    public Integer getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(Integer attachmentCount) {
        this.attachmentCount = attachmentCount;
        if(attributes!=null) {
			attributes.put("ATTACHMENT_COUNT",  this.attachmentCount);
		}
    }

     public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType == null ? null : subType.trim();
        if(attributes!=null) {
			attributes.put("SUB_TYPE",  this.subType);
		}
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
        if(attributes!=null) {
			attributes.put("OBJECT_TYPE",  this.objectType);
		}
    }

	public Map<String, Object> getObjectValues() {
		return objectValues;
	}

	public void setObjectValues(Map<String, Object> objectValues) {
		this.objectValues = objectValues;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
		if(attributes!=null) {
			attributes.put("OWNER_NAME",  this.ownerName);
		}
	}
	@Override
	public void setCreator(String creator) {
		super.setCreator(creator);
		if(attributes!=null) {
			attributes.put("CREATOR",  creator);
		}
	}
	
	@Override
	public void setCreationDate(Date dt) {
		super.setCreationDate(dt);
		if(attributes!=null) {
			attributes.put("CREATION_DATE",  dt);
		}
	}
	
	@Override
	public void setModifier(String modifier) {
		super.setCreator(modifier);
		if(attributes!=null) {
			attributes.put("MODIFIER",  modifier);
		}
	}
	
	@Override
	public void setModifiedDate(Date dt) {
		super.setModifiedDate(dt);
		if(attributes!=null) {
			attributes.put("MODIFIED_DATE",  dt);
		}
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.setId(getString(attributes.get("ID")));
		if(attributes.get("ACL_NAME")!=null) {
			this.setAclName(getString(attributes.get("ACL_NAME")));
		}
		if(attributes.get("TITLE")!=null) {
			this.setTitle(getString(attributes.get("TITLE")));
		}
		if(attributes.get("NAME")!=null) {
			this.setName(getString(attributes.get("NAME")));
		}
		if(attributes.get("REVISION")!=null) {
			this.setRevision(getString(attributes.get("REVISION")));
		}
		if(attributes.get("CODING")!=null) {
			this.setCoding(getString(attributes.get("CODING")));
		}
		if(attributes.get("FOLDER_ID")!=null) {
			this.setFolderId(getString(attributes.get("FOLDER_ID")));
		}
		if(attributes.get("STATUS")!=null) {
			this.setStatus(getString(attributes.get("STATUS")));
		}
		if(attributes.get("TYPE_NAME")!=null) {
			this.setTypeName(getString(attributes.get("TYPE_NAME")));
		}
		if(attributes.get("FORMAT_NAME")!=null) {
			this.setFormatName(getString(attributes.get("FORMAT_NAME")));
		}
	    if(attributes.get("CONTENT_SIZE")!=null) {
	    	this.setContentSize(attributes.get("CONTENT_SIZE")==null?0:Long.parseLong(attributes.get("CONTENT_SIZE").toString()));
	    }
	    if(attributes.get("ATTACHMENT_COUNT")!=null) {
	    	this.setAttachmentCount(attributes.get("ATTACHMENT_COUNT")==null?0:Integer.parseInt(attributes.get("ATTACHMENT_COUNT").toString()));
	    }
	    if(attributes.get("SUB_TYPE")!=null) {
	    	this.setSubType(getString(attributes.get("SUB_TYPE")));
	    }
	    if(attributes.get("OBJECT_TYPE")!=null) {
	    	this.setObjectType(getString(attributes.get("OBJECT_TYPE")));
	    }
	    if(attributes.get("OWNER_NAME")!=null) {
	    	this.setOwnerName(getString(attributes.get("OWNER_NAME")));
	    }
	    if(attributes.get("VERSION_ID")!=null) {
	    	this.setVersionId(getString(attributes.get("VERSION_ID")));
	    }
	    if(attributes.get("SYSTEM_VERSION")!=null) {
	    	this.setSystemVersion(attributes.get("SYSTEM_VERSION")==null?0:Double.parseDouble(attributes.get("SYSTEM_VERSION").toString()));
	    }
	    if(attributes.get("CREATOR")!=null) {
	    	this.setCreator(getString(attributes.get("CREATOR")));
	    }
	    if(attributes.get("CREATION_DATE")!=null) {
	    	this.setCreationDate((Date)attributes.get("CREATION_DATE"));
	    }
	    if(attributes.get("MODIFIER")!=null) {
	    	this.setModifier(getString(attributes.get("MODIFIER")));
	    }
	    if(attributes.get("MODIFIED_DATE")!=null) {
	    	this.setModifiedDate((Date)attributes.get("MODIFIED_DATE"));
	    }
	    if(attributes.get("IS_HIDDEN")!=null) {
	    	this.setHidden(attributes.get("IS_HIDDEN")==null?false:attributes.get("IS_HIDDEN").toString().equals("1"));
	    }
	    if(attributes.get("IS_CURRENT")!=null) {
	    	this.setCurrent(attributes.get("IS_CURRENT")==null?true:attributes.get("IS_CURRENT").toString().equals("1"));
	    }
	    if(attributes.get("LOCK_OWNER")!=null) {
	    	this.setLockOwner(getString(attributes.get("LOCK_OWNER")));
	    }
	    if(attributes.get("LOCK_DATE")!=null) {
	    	this.setLockDate((Date)attributes.get("LOCK_DATE"));
	    }
	    if(attributes.get("LOCK_CLIENT")!=null) {
	    	this.setLockClient(getString(attributes.get("LOCK_CLIENT")));
	    }
	    if(attributes.get("LIFECYCLE_NAME")!=null) {
	    	this.setLifecycleName(getString(attributes.get("LIFECYCLE_NAME")));
	    }
	    if(attributes.get("LIFECYCLE_STATUS")!=null) {
	    	this.setLifecycleStatus(getString(attributes.get("LIFECYCLE_STATUS")));
	    }
	    if(attributes.get("LIFECYCLE_DIR")!=null) {
	    	try {
	    		this.setLifecycleDir(Integer.parseInt(getString(attributes.get("LIFECYCLE_DIR"))));
	    	}catch(Exception ex){}
	    }
	}
	
	private String getString(Object val) {
		if(val==null) {
			return null;
		}
		return val.toString();
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
		if(attributes!=null) {
			attributes.put("IS_CURRENT",  this.isCurrent?1:0);
		}
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		if(attributes!=null) {
			attributes.put("IS_HIDDEN",  this.isHidden?1:0);
		}
	}

	@Override
	public void createId() {
		// TODO Auto-generated method stub
		super.createId();
		if(attributes!=null) {
			attributes.put("ID",  this.getId());
		}
	}

   
 }