package com.ecm.core.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EcmStorageRoom {
    private String id;

    private String coding;

    private String roomType;

    private Double roomArea;

    private String roomFunction;

    private Integer columnCount;

    private String description;

    private String stauts;
    
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
        if(attributes!=null) {
			attributes.put("ID",  this.id);
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType == null ? null : roomType.trim();
        if(attributes!=null) {
			attributes.put("ROOM_TYPE",  this.roomType);
		}
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
        if(attributes!=null) {
			attributes.put("ROOM_AREA",  this.roomArea);
		}
    }

    public String getRoomFunction() {
        return roomFunction;
    }

    public void setRoomFunction(String roomFunction) {
        this.roomFunction = roomFunction == null ? null : roomFunction.trim();
        if(attributes!=null) {
			attributes.put("ROOM_FUNCTION",  this.roomFunction);
		}
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
        if(attributes!=null) {
			attributes.put("COLUMN_COUNT",  this.columnCount);
		}
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
        if(attributes!=null) {
			attributes.put("DESCRIPTION",  this.description);
		}
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts == null ? null : stauts.trim();
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
		if(attributes.get("ROOM_TYPE")!=null) {
			this.setRoomType(getString(attributes.get("ROOM_TYPE")));
		}
		if(attributes.get("ROOM_AREA")!=null) {
			this.setRoomArea(getDouble(attributes.get("ROOM_AREA")));
		}
		if(attributes.get("ROOM_FUNCTION")!=null) {
			this.setRoomFunction(getString(attributes.get("ROOM_FUNCTION")));
		}
		if(attributes.get("COLUMN_COUNT")!=null) {
			this.setColumnCount(getInt(attributes.get("COLUMN_COUNT")));
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