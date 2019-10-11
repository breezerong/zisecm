package com.ecm.core.entity;

public class EcmAttribute  extends EcmSysObject{
   
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		if(type.startsWith("varchar")) {
			String len = type.replace("varchar(", "").replace(")", "");
			this.length = Integer.parseInt(len);
		}
		
		if(type.startsWith("varchar")) {
    		dataType= 1;
    	}
    	else if(type.startsWith("int")) {
    		dataType= 2;
    	}
    	else if(type.startsWith("double")) {
    		dataType= 3;
    	}
    	else if(type.startsWith("datetime")) {
    		dataType= 5;
    	}
    	else if(type.startsWith("tinyint")) {
    		dataType= 6;
    	}
    	else if(type.startsWith("bigint")) {
    		dataType= 4;
    	}
	}

	public int getLength() {
		return length;
	}

    private String type;
    
    private int length;
    
    public void setLength(int length) {
		this.length = length;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	private String isNull;
    
    private String defaultValue;
    
    private int dataType;
    
    public void setDataType(int dataType) {
		this.dataType = dataType;
	}
    /**
     	* 字段类型：1：String，2：Int，3：Double，,4:long，5：DateTime，6：boolean
     * @return
     */
	public int getDataType() {
    	
    	return dataType;
    }
   
}