package com.ecm.core.entity;

/**
 * 关系类型
 * @author Haihong Rong
 *
 */
public class EcmRelationType  extends EcmSysObject{
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}

	public boolean isAutoCopy() {
		return autoCopy;
	}

	public void setAutoCopy(boolean autoCopy) {
		this.autoCopy = autoCopy;
	}
	/**
	 * 父类型
	 */
	private String parentType;
	/**
	 * 子类型
	 */
    private String childType;

    /**
     * 自动复制
     */
    private boolean autoCopy;

}