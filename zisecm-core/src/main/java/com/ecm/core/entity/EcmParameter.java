package com.ecm.core.entity;
/**
 * 参数
 * @author Haihong Rong
 * @date 2019年11月27日 上午7:45:50
 */
public class EcmParameter extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 类型
	 */
	private String itemType;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}