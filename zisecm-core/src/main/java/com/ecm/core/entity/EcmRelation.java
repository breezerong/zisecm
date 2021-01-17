package com.ecm.core.entity;

import java.util.HashMap;
import java.util.Map;

public class EcmRelation extends EcmSysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String childId;
    
    private int orderIndex;
    
    public EcmRelation() {
    	
    }
    
    public EcmRelation(String name,String parentId,String childId) {
    	this.setName(name);
    	this.setParentId(parentId);
    	this.setChildId(childId);
    }
    
    private Map<String,Object> attibutes=new HashMap<String,Object>();
    
    
    private String getString(Object val) {
		if(val==null) {
			return null;
		}
		return val.toString();
	}
    public Map<String, Object> getAttibutes() {
		return attibutes;
	}
    
    public void addAttribute(String key,Object value) {
    	if(this.attibutes==null) {
			this.attibutes=new HashMap<String, Object>();
		}
    	this.attibutes.put(key, value);
    	
    	switch(key) {
    		case "DESCRIPTION":
    			this.setDescription(getString(attibutes.get("DESCRIPTION")));
    			break;
    		case "ORDER_INDEX":
    			this.setOrderIndex(Integer.parseInt(getString(attibutes.get("ORDER_INDEX"))));
    			break;
    		case "PARENT_ID":
    			this.setParentId(getString(attibutes.get("PARENT_ID")));
    			break;
    		case "CHILD_ID":
    			this.setChildId(getString(attibutes.get("CHILD_ID")));
    			break;
    	}
    	
    }
    
    public Object getAttributeValue(String key) {
    	return this.attibutes.get(key);
    }
	public void setAttibutes(Map<String, Object> attibutes) {
		this.attibutes = attibutes;
		if(attibutes.get("DESCRIPTION")!=null) {
			this.setDescription(getString(attibutes.get("DESCRIPTION")));
		}
		if(attibutes.get("ORDER_INDEX")!=null) {
			this.setOrderIndex(Integer.parseInt(getString(attibutes.get("ORDER_INDEX"))));
		}
		if(attibutes.get("PARENT_ID")!=null) {
			this.setParentId(getString(attibutes.get("PARENT_ID")));
		}
		if(attibutes.get("CHILD_ID")!=null) {
			this.setChildId(getString(attibutes.get("CHILD_ID")));
		}
		
	}

	public String getDescription() {
		return super.getDescription();
	}

	public void setDescription(String description) {
		super.setDescription(description);
	}

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