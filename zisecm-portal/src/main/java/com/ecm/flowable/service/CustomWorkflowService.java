package com.ecm.flowable.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;

@Service
public class CustomWorkflowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ManagementService managementService;
	@Autowired
    private UserService userService;
	@Autowired
    private TaskService taskService;
	@Autowired
    private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
	/**
	 * @param taskArgs
	 */
	@Transactional(rollbackFor = Exception.class)
	public void completeTask(Map<String, Object> taskArgs) {
		String taskId= taskArgs.get("taskId").toString();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	        setTaskApprovalResult(taskArgs);
        // owner不为空说明可能存在委托任务
        if (!StringUtils.isEmpty(task.getOwner())) {
        	DelegationState delegationState = task.getDelegationState();
            switch (delegationState) {
            case PENDING:
                taskService.resolveTask(taskId);
                taskService.complete(taskId, taskArgs);
                break;

            default:
                taskService.complete(taskId, taskArgs);
                break;
            }
        } else {
            taskService.complete(taskId, taskArgs);
        }
        updateEcmauditWorkItem(taskArgs);
	}
	/**
	 * @param args
	 * @param taskId
	 */
	private void setTaskApprovalResult(Map<String, Object> args) {
		Map<String, Object> varLocalMap = new HashMap<String, Object>();
        varLocalMap.put("outcome", args.get("outcome"));
		String message= args.get("message").toString();
        varLocalMap.put("message", args.get("message"));
		if(!"".equals(message)) {
			varLocalMap.put("message",message);
        }
        taskService.setVariablesLocal(args.get("taskId").toString(), varLocalMap);
	}
	/**
	 * @param taskId
	 * @param result
	 * @param message
	 */
	private void updateEcmauditWorkItem(Map<String, Object> varMap) {
		updateEcmauditWorkItem(varMap,"","");
	}
	/**
	 * @param taskId
	 * @param result
	 * @param message
	 * @param formId
	 * @param docId
	 */
	private void updateEcmauditWorkItem(Map<String, Object> varMap, String formId,String docId) {
		String taskId= varMap.get("taskId").toString();
		EcmAuditWorkitem  audit =	new EcmAuditWorkitem();
        HistoricTaskInstance  task= historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		String auditId=ecmAuditWorkitemMapper.selectByCondition("TASK_ID='"+taskId+"'  and END_TIME is null").get(0).getId();
		audit.setId(auditId);
		audit.setCreateTime(task.getCreateTime());
		audit.setEndTime(task.getEndTime());
		audit.setDocId("");
		audit.setFormId("");
		audit.setTaskName(task.getName());
		audit.setAssignee(task.getAssignee());
		audit.setResult(varMap.get("outcome").toString());
		audit.setMessage(varMap.get("message").toString());
		audit.setProcessInstanceId(task.getProcessInstanceId());
		audit.setTaskId(taskId);
		ecmAuditWorkitemMapper.updateByPrimaryKey(audit);
	}
		/**
	 * @param taskId
	 * @param delegateTaskUserId
	 */
	public void delegateTask(String taskId, String delegateTaskUserId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (StringUtils.isEmpty(task.getOwner())) {
			taskService.setOwner(taskId, task.getAssignee());
			taskService.delegateTask(taskId, delegateTaskUserId);
        }

	}

	@Transactional(rollbackFor = Exception.class)
	public void claimTask(IEcmSession session,String taskId) {
		taskService.claim(taskId, session.getCurrentUser().getUserName());
	}

	@Transactional(rollbackFor = Exception.class)
	public void unclaimTask(IEcmSession session,String taskId) {
		taskService.unclaim(taskId);
	}

}