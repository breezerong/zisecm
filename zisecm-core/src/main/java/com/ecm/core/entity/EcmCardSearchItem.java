package com.ecm.core.entity;

public class EcmCardSearchItem extends EcmObject{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String formItemId;

    private Integer orderIndex;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(String formItemId) {
        this.formItemId = formItemId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    private EcmFormItem ecmFormItem;
	public EcmFormItem getEcmFormItem() {
		// TODO Auto-generated method stub
		return ecmFormItem;
	}
	
	public void setEcmFormItem(EcmFormItem item) {
		// TODO Auto-generated method stub
		ecmFormItem = item;
	}
	
	public EcmCardSearchItem clone() {
		EcmCardSearchItem en = new EcmCardSearchItem();
		en.setFormItemId(getFormItemId());
		en.setOrderIndex(getOrderIndex());
		en.setParentId(getParentId());
		en.setId(getId());
		if(ecmFormItem != null) {
			en.setEcmFormItem(ecmFormItem.clone());
		}
		return en;
	}
}