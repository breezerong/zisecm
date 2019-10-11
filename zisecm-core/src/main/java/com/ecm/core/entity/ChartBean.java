package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 报表数据
 * @author Haihong Rong
 * @date 2019年7月22日 上午6:46:33
 */
public class ChartBean {
	/**
	 * X坐标数据
	 */
	private List<String> xAxisData = new ArrayList<String>();
	/**
	 * y坐标数据
	 */
	private List<Long> yAxisData = new ArrayList<Long>();
	public List<String> getxAxisData() {
		return xAxisData;
	}
	public void setxAxisData(List<String> xAxisData) {
		this.xAxisData = xAxisData;
	}
	public List<Long> getyAxisData() {
		return yAxisData;
	}
	public void setyAxisData(List<Long> yAxisData) {
		this.yAxisData = yAxisData;
	}
	
}
