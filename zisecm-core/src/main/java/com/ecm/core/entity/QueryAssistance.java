/**
 * 
 */
package com.ecm.core.entity;

import java.util.List;
import java.util.Map;

/**
 * @ClassName  QueryAssistance   
 * @Description TODO(查询辅助类)   
 * @author yaozhigang
 * @date 2018年6月29日 上午11:08:17  
 *
 */
public class QueryAssistance {
	//排序字段
	private String orderBy;
	//页码
	private Integer pageNum;
	//页数
	private Integer pageSize;
	//查询字段
	private List<String> fieldItems;
	//查询条件
	private Map<String,String> condition;
	//排序规则
	private String orderRule = "DESC";
	//查询模式
	private String searchMode;
	//查询条件间的关联符
	private String union="and";

	@Override
	public String toString() {
		return "QueryAssistance [orderBy=" + orderBy + ", pageNum=" + pageNum + ", pageSize=" + pageSize
				+ ", fieldItems=" + fieldItems + ", condition=" + condition + ", orderRule=" + orderRule
				+ ", searchMode=" + searchMode + ", union=" + union + "]";
	}
	
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}
	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the fieldItems
	 */
	public List<String> getFieldItems() {
		return fieldItems;
	}
	/**
	 * @param fieldItems the fieldItems to set
	 */
	public void setFieldItems(List<String> fieldItems) {
		this.fieldItems = fieldItems;
	}
	/**
	 * @return the condition
	 */
	public Map<String, String> getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Map<String, String> condition) {
		this.condition = condition;
	}

	/**
	 * @return the orderRule
	 */
	public String getOrderRule() {
		return orderRule;
	}

	/**
	 * @param orderRule the orderRule to set
	 */
	public void setOrderRule(String orderRule) {
		this.orderRule = orderRule;
	}

	/**
	 * @return the searchMode
	 */
	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @param searchMode the searchMode to set
	 */
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @return the union
	 */
	public String getUnion() {
		return union;
	}

	/**
	 * @param union the union to set
	 */
	public void setUnion(String union) {
		this.union = union;
	}
}
