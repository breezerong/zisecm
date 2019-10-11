package com.ecm.icore.bpm;

import java.util.List;

import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.exception.AccessDeniedException;
/**
 * 工作流日志服务
 * @author Haihong Rong
 * @date 2019年7月17日 上午11:14:46
 */
public interface IWorkflowAuditService {

	/**
	 * 获取当前用户所有工作流日志
	 * @return
	 * @throws AccessDeniedException 
	 */
	List<EcmAuditWorkflow> getMyAuditWorkflow(String token) throws AccessDeniedException;

	/**
	 * 获取当前用户工作流日志数
	 * @param condition 条件
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getMyAuditWorkflowCount(String token, String condition) throws AccessDeniedException;

	/**
	 * 获取当前用户工作流日志分页数据
	 * @param pageSize 每页数目
	 * @param startIndex 当前页
	 * @param condition 条件
	 * @return
	 * @throws AccessDeniedException 
	 */
	List<EcmAuditWorkflow> getMyAuditWorkflow(String token, int pageSize, int startIndex, String condition) throws AccessDeniedException;

	/**
	 * 获取所有工作流日志数
	 * @param condition 条件
	 * @return
	 */
	int getAuditWorkflowCount(String token, String condition);

	/**
	 * 获取工作流日志分页数据
	 * @param pageSize 每页数目
	 * @param startIndex 当前页
	 * @param condition 条件
	 * @return
	 */
	List<EcmAuditWorkflow> getAuditWorkflow(String token, int pageSize, int startIndex, String condition);

}
