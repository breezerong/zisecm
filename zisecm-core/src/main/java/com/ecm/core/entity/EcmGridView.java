package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecm.core.cache.manager.impl.CacheManagerLanguage;

public class EcmGridView extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String condition;
	
	private String typeName;

    private String orderBy;

    public String getCondition() {
    	
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition == null ? null : condition.trim();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy == null ? null : orderBy.trim();
    }

    private List<EcmGridViewItem> gridViewItems;
	public List<EcmGridViewItem> getGridViewItems() {
		// TODO Auto-generated method stub
		return gridViewItems;
	}
	
	public List<EcmGridViewItem> getGridViewItems(String langKey) {
		// TODO Auto-generated method stub
		List<EcmGridViewItem> list = new ArrayList<EcmGridViewItem>();
		if(gridViewItems != null && !StringUtils.isEmpty(langKey)) {
			
			for(EcmGridViewItem item : gridViewItems) {
				String label = CacheManagerLanguage.getLanguage(langKey, typeName, item.getAttrName());
				//如果类型属性未定义，获取默认标签
				if(StringUtils.isEmpty(label) && !StringUtils.isEmpty(typeName)) {
					label = CacheManagerLanguage.getLanguage(langKey, item.getAttrName());
				}
				if(!StringUtils.isEmpty(label)) {
					EcmGridViewItem itemc = item.clone();
					itemc.setLabel(label);
					list.add(itemc);
				} else {
					list.add(item);
				}
			}
		}else {
			list = gridViewItems;
		}
		return list;
	}

	public void setGridViewItems(List<EcmGridViewItem> items) {
		// TODO Auto-generated method stub
		gridViewItems = items;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}