package com.ecm.core.db;

import java.util.Date;

public interface IDBUtils {
	/**
	 * 单引号转义
	 * @return
	 */
	String getSingleQuotationMark();
	/**
	 * 空日期定义	 * @return
	 */
	String getDBNullDate();
	
	/**
	 * SQL值转义处理
	 * @param dataStr
	 * @return
	 */
	String getString(String dataStr);
	
	/**
	 * 插入日期格式转换
	 * @param date 字符串
	 * @return
	 */
	String getDBDateString(String date);
	
	/**
	 * 插入日期格式转换
	 * @param d 日期
	 * @return
	 */
	String getDBDateString(Date d);
	
	/**
	 * 获取当前日期
	 * @return
	 */
	String getDBDateNow();
	
	/***
	 * 验证条件，不允许条件包含update和delete字段
	 * @return
	 */
	//boolean verifyCondition(String condition);
	
}
