package com.ecm.core.bpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ecm.core.service.QueueItemService;
import com.ecm.icore.bpm.IProcessEngine;

/**
 * 工作流引擎
 * @author Haihong Rong
 *
 */
@Component
@Scope("prototype")
public class ProcessEngine implements IProcessEngine {

	/**
	 * 活动定义服务
	 * @return
	 */
	@Override
	public ActivityService getActivityService() {
		return activityService;
	}
	/**
	 * 工作流定义服务
	 * @return
	 */
	@Override
	public ProcessService getProcessService() {
		return processService;
	}
	/**
	 * 转移定义服务
	 * @return
	 */
	@Override
	public TransactionService getTransactionService() {
		return transactionService;
	}
	/**
	 * 流程实例服务
	 * @return
	 */
	@Override
	public WorkflowService getWorkflowService() {
		return workflowService;
	}
	/**
	 * 活动实例服务
	 * @return
	 */
	@Override
	public WorkitemService getWorkitemService() {
		return workitemService;
	}
	/**
	 * 队列服务
	 * @return
	 */
	@Override
	public QueueItemService getQueueItemService() {
		return queueService;
	}
	/**
	 * 流程日志服务
	 * @return
	 */
	@Override
	public WorkflowAuditService getWorkflowAuditService() {
		return workflowAuditService;
	}
	/**
	 * 任务日志服务
	 * @return
	 */
	@Override
	public WorkitemAuditService getWorkitemAuditService() {
		return workitemAuditService;
	}
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private WorkitemService workitemService;
	@Autowired
	private QueueItemService queueService;
	@Autowired
	private WorkflowAuditService workflowAuditService;
	@Autowired
	private WorkitemAuditService workitemAuditService;
	
}
