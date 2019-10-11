package com.ecm.core.entity;

import java.io.Serializable;
/**
 * 分页
 * @author Haihong Rong
 * @date 2019年10月5日 上午8:31:09
 */
public class Pager implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageIndex = 0;//页码，默认是第一页
    private int pageSize = 10;//每页显示的记录数，默认是5
    private int totalRecord;//总记录数
    private int total;//总记录数
    private int totalPage;//总页数
    private String scrollId;//全文分页ID
    //private List<T> results;//对应的当前页记录
 
    
    public int getTotal() {
		return total;
	}
 
	public void setTotal(int total) {
		this.total = total;
	}
 
	public int getPageIndex() {
        return pageIndex;
    }
 
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
 
    public int getPageSize() {
        return pageSize;
    }
 
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
        return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        this.total=totalRecord;
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
        return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

	public String getScrollId() {
		return scrollId;
	}

	public void setScrollId(String scrollId) {
		this.scrollId = scrollId;
	}
 
//    public List<T> getResults() {
//    	if(null != results && results.size() == 0){
//            return null;
//    	}
//        return results;
//    }
// 
//    public void setResults(List<T> results) {
//        this.results = results;
//    }

}
