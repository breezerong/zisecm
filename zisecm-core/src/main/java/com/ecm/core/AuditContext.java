package com.ecm.core;
/**
 * 系统日志事件
 * @author Haihong Rong
 * @date 2019年9月25日 下午9:53:06
 */
public class AuditContext {
	/**
	 * 创建
	 */
	public static String CREATE="ecm_create";
	/**
	 * 登录
	 */
	public static String LOGIN="ecm_login";
	/**
	 * 登录失败
	 */
	public static String LOGIN_FAILED="ecm_login_failed";
	/**
	 * 修改权限
	 */
	public static String CHANGE_PERMIT="ecm_change_permit";
	/**
	 * 更新
	 */
	public static String UPDATE="ecm_update";
	/**
	 * 删除文件内容
	 */
	public static String GET_FILE="ecm_getfile";
	/**
	 * 添加文件内容
	 */
	public static String SET_FILE="ecm_setfile";
	/**
	 * 删除
	 */
	public static String DELETE="ecm_delete";
	/**
	 * 添加格式副本
	 */
	public static String ADD_REDITION="ecm_add_redition";
	/**
	 * 删除格式副本
	 */
	public static String REMOVE_REDITION="ecm_remove_redition";
	/**
	 * 添加附件
	 */
	public static String ADD_ATTACHMENT="ecm_add_attachment";
	/**
	 * 删除附件
	 */
	public static String DELETE_ATTACHMENT="ecm_delete_attachment";
	/**
	 * 锁定
	 */
	public static String LOCK = "ecm_lock";
	/**
	 * 解锁
	 */
	public static String UNLOCK = "ecm_unlock";
	/**
	 * 签出
	 */
	public static String CHECK_OUT = "ecm_check_out";
	/**
	 * 签入
	 */
	public static String CHECK_IN = "ecm_check_in";
	
	/**
	 *全文搜索
	 */
	public static String FULL_TEXT_SEARCH = "ecm_fulltext_search";
	
	/**
	 *卡片搜索
	 */
	public static String CARD_SEARCH = "ecm_card_search";
	
	/**
	 *高级搜索
	 */
	public static String ADV_SEARCH = "ecm_adv_search";
	
	/**
	 *生命周期操作
	 */
	public static String LIFE_CYCLE = "ecm_life_cycle";
	/**
	 *取消签出
	 */
	//public static String CANCEL_CHECK_OUT = "ecm_cancel_check_out";
}
