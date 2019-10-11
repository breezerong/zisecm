package com.ecm.icore.bpm;


public interface IProcessEngine {

	IActivityService getActivityService();

	IProcessService getProcessService();

	ITransactionService getTransactionService();

	IWorkflowService getWorkflowService();

	IWorkitemService getWorkitemService();

	IWorkflowAuditService getWorkflowAuditService();

	IWorkitemAuditService getWorkitemAuditService();

	com.ecm.core.service.QueueItemService getQueueItemService();

}
