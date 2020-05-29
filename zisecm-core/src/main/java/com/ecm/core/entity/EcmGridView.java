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
    
    private int gridType;
    
    private String creator;
    
    private Date creationDate;

    
    
    public int getGridType() {
		return gridType;
	}

	public void setGridType(int gridType) {
		this.gridType = gridType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

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
}