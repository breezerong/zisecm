package com.ecm.core.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EcmStorageRow {
    private String id;

    private String coding;

    private String parentCoding;

    private Double totalLength;

    private Double remainLength;

    private Integer archiveCount;

    private Integer itemCount;

    private String description;

    private String stauts;
    
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        if(attributes!=null) {
			attributes.put("ID",  this.id);
		}
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
        if(attributes!=null) {
			attributes.put("CODING",  this.coding);
		}
    }

    public String getParentCoding() {
        return parentCoding;
    }

    public void setParentCoding(String parentCoding) {
        this.parentCoding = parentCoding;
        if(attributes!=null) {
			attributes.put("PARENT_CODING",  this.parentCoding);
		}
    }

    public Double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Double totalLength) {
        this.totalLength = totalLength;
        if(attributes!=null) {
			attributes.put("TOTAL_LENGTH",  this.totalLength);
		}
    }

    public Double getRemainLength() {
        return remainLength;
    }

    public void setRemainLength(Double remainLength) {
        this.remainLength = remainLength;
        if(attributes!=null) {
			attributes.put("REMAIN_LENGTH",  this.remainLength);
		}
    }

    public Integer getArchiveCount() {
        return archiveCount;
    }

    public void setArchiveCount(Integer archiveCount) {
        this.archiveCount = archiveCount;
        if(attributes!=null) {
			attributes.put("ARCHIVE_COUNT",  this.archiveCount);
		}
    }

    
    
    public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
        if(attributes!=null) {
			attributes.put("ITEM_COUNT",  this.itemCount);
		}
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        if(attributes!=null) {
			attributes.put("DESCRIPTION",  this.description);
		}
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
        if(attributes!=null) {
			attributes.put("STAUTS",  this.stauts);
		}
    }
    
    public Map<String, Object> getAttributes() {
		return attributes;
	}
    public void setAttributes(Map<String, Object> attributes) {

		this.attributes = attributes;
		this.setId(getString(attributes.get("ID")));
		if(attributes.get("CODING")!=null) {
			this.setCoding(getString(attributes.get("CODING")));
		}
		if(attributes.get("PARENT_CODING")!=null) {
			this.setParentCoding(getString(attributes.get("PARENT_CODING")));
		}
		if(attributes.get("TOTAL_LENGTH")!=null) {
			this.setTotalLength(getFloat(attributes.get("TOTAL_LENGTH")));
		}
		if(attributes.get("REMAIN_LENGTH")!=null) {
			this.setRemainLength(getFloat(attributes.get("REMAIN_LENGTH")));
		}
		if(attributes.get("ARCHIVE_COUNT")!=null) {
			this.setArchiveCount(getInt(attributes.get("ARCHIVE_COUNT")));
		}
		
		if(attributes.get("ITEM_COUNT")!=null) {
			this.setArchiveCount(getInt(attributes.get("ITEM_COUNT")));
		}
		if(attributes.get("DESCRIPTION")!=null) {
			this.setDescription(getString(attributes.get("DESCRIPTION")));
		}
		
		if(attributes.get("STAUTS")!=null) {
			this.setStauts(getString(attributes.get("STAUTS")));
		}
    }
    
    private String getString(Object val) {
		if(val==null) {
			return null;
		}
		return val.toString();
	}
    
    private int getInt(Object val) {
    	if(val==null) {
			return 0;
		}
		return Integer.parseInt(val.toString());
    }
    private double getFloat(Object val) {
		if(val==null) {
			return 0f;
		}
		return Float.parseFloat(val.toString());
	}
    private double getDouble(Object val) {
		if(val==null) {
			return 0f;
		}
		return Double.parseDouble(val.toString());
	}
    public void createId() {
		// TODO Auto-generated method stub
    	id = UUID.randomUUID().toString().replace("-", "");
		
	}
}