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
			fieldType= 1;
    	}
		else if(type.startsWith("datetime")) {
			fieldType= 2;
    	}
		else if(type.startsWith("tinyint")) {
			fieldType= 3;
    	}
    	else if(type.startsWith("int")) {
    		fieldType= 4;
    	}
    	else if(type.startsWith("bigint")) {
    		fieldType= 5;
    	}
    	else if(type.startsWith("double")) {
    		fieldType= 6;
    	}
    	else if(type.startsWith("decimal")) {
    		fieldType= 7;
    	}else if(type.startsWith("float")) {
    		fieldType= 8;
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
    
    private int fieldType;
    
    public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
    /**
     	* 字段类型：1：String，2：DateTime，3：boolean,4：Int，5:long，6：Double，,7:decimal,8:float
     * @return
     */
	public int getFieldType() {
    	
    	return fieldType;
    }
   
}