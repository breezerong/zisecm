package com.ecm.core.entity;

public class EcmCardSearchItem extends EcmObject{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

    private String formItemId;

    private Integer orderIndex;
    
    private String description;

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
	
	public EcmCardSearchItem clone(String langKey) {
		EcmCardSearchItem en = new EcmCardSearchItem();
		en.setFormItemId(getFormItemId());
		en.setOrderIndex(getOrderIndex());
		en.setParentId(getParentId());
		en.setDescription(getDescription());
		en.setId(getId());
		if(ecmFormItem != null) {
			en.setEcmFormItem(ecmFormItem.clone(langKey));
		}
		return en;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}