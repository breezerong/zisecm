package com.ecm.flowable.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;

@Service
public class CustomWorkflowService {
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
	@Autowired
	private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
	@Autowired
	private DocumentService documentService;

	/**
	 * @param taskArgs
	 */
	@Transactional(rollbackFor = Exception.class)
	public void completeTask(Map<String, Object> taskArgs) {
		String taskId = taskArgs.get("taskId").toString();
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
		String message = args.get("message").toString();
		varLocalMap.put("message", args.get("message"));
		if (!"".equals(message)) {
			varLocalMap.put("message", message);
		}
		taskService.setVariablesLocal(args.get("taskId").toString(), varLocalMap);
	}

	/**
	 * @param taskId
	 * @param result
	 * @param message
	 */
	private void updateEcmauditWorkItem(Map<String, Object> varMap) {
		updateEcmauditWorkItem(varMap, "", "");
	}

	/**
	 * @param taskId
	 * @param result
	 * @param message
	 * @param formId
	 * @param docId
	 */
	private void updateEcmauditWorkItem(Map<String, Object> varMap, String formId, String docId) {
		String taskId = varMap.get("taskId").toString();
		EcmAuditWorkitem audit = new EcmAuditWorkitem();
		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		String auditId = ecmAuditWorkitemMapper.selectByCondition("TASK_ID='" + taskId + "'  and END_TIME is null")
				.get(0).getId();
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
	@Transactional(rollbackFor = Exception.class)
	public void delegateTask(String taskId, String delegateTaskUserId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (StringUtils.isEmpty(task.getOwner())) {
			taskService.setOwner(taskId, task.getAssignee());
			taskService.delegateTask(taskId, delegateTaskUserId);
		}

	}

	/**
	 * @param args
	 * @return
	 */

//	@Transactional(rollbackFor = Exception.class)
//	public Map<String, Object> startBorrowWorkflow(IEcmSession session, Map<String, Object> args) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		try {
//			String userName = session.getCurrentUser().getUserName();
//			String processName = "借阅流程 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//			Authentication.setAuthenticatedUserId(userName);
//			args.put("startUser", userName);
//			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_borrow", args);
//			runtimeService.setProcessInstanceName(processInstance.getId(), processName);
//			// 创建流程日志
//			EcmAuditWorkflow audit = new EcmAuditWorkflow();
//			audit.createId();
//			audit.setProcessInstanceId(processInstance.getId());
//			audit.setProcessName(processInstance.getProcessDefinitionName());
//			audit.setProcessInstanceName(processName);
//			audit.setCreator(userName);
//			audit.setStartTime(processInstance.getStartTime());
//			audit.setFormId("formId");
//			ecmAuditWorkflowMapper.insert(audit);
//			result.put("code", ActionContext.SUCESS);
//			result.put("processID", processInstance.getId());
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("code", ActionContext.FAILURE);
//			result.put("message", e.getMessage());
//		}
//		return result;
//	}
	
	private Map<String, Object> start(IEcmSession session,Map<String, Object> args,boolean byKey,boolean byId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String userName = session.getCurrentUser().getUserName();
			String processName = args.get("processName")+" "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			Authentication.setAuthenticatedUserId(userName);
			args.put("startUser", userName);
			ProcessInstance processInstance=null;
			if(byId) {
				String formId= args.get("formId").toString();
				EcmDocument form= documentService.getObjectById(session.getToken(), formId);
				args.putAll(form.getAttributes());
				processInstance = runtimeService.startProcessInstanceById(args.get("processInstanceId").toString(),
						args);
				
				runtimeService.setProcessInstanceName(processInstance.getId(), processName);
			}else {
				String formId= args.get("formId").toString();
				EcmDocument form= documentService.getObjectById(session.getToken(), formId);
				args.putAll(form.getAttributes());
				processInstance = runtimeService.startProcessInstanceByKey(args.get("processInstanceKey").toString(),
						args);
				
				runtimeService.setProcessInstanceName(processInstance.getId(), processName);
			}
			
			// 创建流程日志
			EcmAuditWorkflow audit = new EcmAuditWorkflow();
			audit.createId();
			audit.setProcessInstanceId(processInstance.getId());
			audit.setProcessName(processInstance.getProcessDefinitionName());
			audit.setProcessInstanceName(processName);
			audit.setCreator(userName);
			audit.setStartTime(processInstance.getStartTime());
			audit.setFormId("formId");
			ecmAuditWorkflowMapper.insert(audit);
			result.put("code", ActionContext.SUCESS);
			result.put("processID", processInstance.getId());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", ActionContext.FAILURE);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	/**
	 * @param args
	 * @return
	 */

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> startWorkflowById(IEcmSession session, Map<String, Object> args) {
		return start(session, args, false, true);
	}
	
	/**
	 * @param args
	 * @return
	 */

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> startWorkflow(IEcmSession session, Map<String, Object> args) {
		
		return start(session, args, true, false);
	}

	/**
	 * @param args
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> terminateWorkflow(IEcmSession session, Map<String, Object> args) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String processInstanceId = args.get("processInstanceId").toString();
			// taskService
			// repositoryService.process
			List<String> activeTaskList = runtimeService.getActiveActivityIds(processInstanceId);
			taskService.deleteTasks(activeTaskList);
			runtimeService.deleteProcessInstance(processInstanceId, "");
			result.put("code", ActionContext.SUCESS);
			result.put("processID", processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", ActionContext.FAILURE);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public void claimTask(IEcmSession session, String taskId) {
		taskService.claim(taskId, session.getCurrentUser().getUserName());
	}

	@Transactional(rollbackFor = Exception.class)
	public void unclaimTask(IEcmSession session, String taskId) {
		taskService.unclaim(taskId);
	}

}