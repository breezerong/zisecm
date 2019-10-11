package com.ecm.icore.bpm;

import java.util.List;

import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.exception.AccessDeniedException;
/**
 * 任务日志服务
 * @author Haihong Rong
 * @date 2019年7月17日 上午11:28:22
 */
public interface IWorkitemAuditService {
	/**
	 * 获取当前用户所有任务日志
	 * @return
	 * @throws AccessDeniedException 
	 */
	List<EcmAuditWorkitem> getMyAuditWorkitem(String token) throws AccessDeniedException;

	/**
	 * 获取当前用户分页任务日志
	 * @param pageSize 每页数目
	 * @param startIndex 当前页
	 * @param condition 条件
	 * @return
	 * @throws AccessDeniedException 
	 */
	List<EcmAuditWorkitem> getMyAuditWorkitem(String token, int pageSize, int startIndex, String condition) throws AccessDeniedException;

	/**
	 * 获取所有当前用户任务日志数
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getMyAuditWorkitemCount(String token) throws AccessDeniedException;

	/**
	 * 根据条件获取当前用户任务日志数
	 * @param condition 条件
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getMyAuditWorkitemCount(String token, String condition) throws AccessDeniedException;

}
