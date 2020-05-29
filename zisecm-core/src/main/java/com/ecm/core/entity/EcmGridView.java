package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ecm.core.cache.manager.impl.CacheManagerLangInfo;
import com.ecm.core.cache.manager.impl.CacheManagerLanguage;

public class EcmGridView extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String condition;
	
	private String typeName;

    private String orderBy;
    
    //类型， 0 系统，1用户
    private Integer gridType=0;

    //客户端标签
    private String gridTag;



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
				EcmGridViewItem itemc = item.clone(langKey);
				list.add(itemc);
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

	public String getGridTag() {
		return gridTag;
	}

	public void setGridTag(String gridTag) {
		this.gridTag = gridTag;
	}

	public Integer getGridType() {
		return gridType;
	}

	public void setGridType(Integer gridType) {
		this.gridType = gridType;
	}
}