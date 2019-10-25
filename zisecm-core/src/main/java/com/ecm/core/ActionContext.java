package com.ecm.core;
/**
 * 事件上下文
 * @author Administrator
 *
 */
public class ActionContext {
	/**
	 * 失败
	 */
	public static final int FAILURE = 0;
	/**
	 * 成功
	 */
	public static final int SUCESS = 1;
	/**
	 * 系统错误
	 */
	public static final int SYSTEM_ERROR = 2;
	/**
	 * 业务错误
	 */
	public static final int BUSINESS_ERROR = 3;
	/**
	 * 没有权限
	 */
	public static final int NO_PERMSSION = 9;
	/**
	 * session超时
	 */
	public static final int TIME_OUT = 1001;
	
	/**
	 * DWG签章应用名称
	 */
	public static final String APP_DWG_SIGN = "dwg_sign";
	
	/**
	 * PDF签章应用名称
	 */
	public static final String APP_PDF_SIGN = "pdf_sign";
	
	/**
	 * Portal应用名称
	 */
	public static final String APP_PORTAL = "portal";
	
	/**
	 * Core应用名称
	 */
	public static final String APP_CORE = "core";
	
	/**
	 * IndexAgent应用名称
	 */
	public static final String APP_INDEX_AGENT = "index_agent";
	
}
