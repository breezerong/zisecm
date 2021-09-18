package com.ecm.portal.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.job.api.Job;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.NativeTaskQuery;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.bpm.WorkflowAuditService;
import com.ecm.core.bpm.WorkitemAuditService;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.impl.CacheManagerCfgActivity;
import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.dao.EcmCfgActivityMapper;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.AuditService;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.UserService;
import com.ecm.flowable.config.CustomProcessDiagramGenerator;
import com.ecm.flowable.listener.JobListener;
import com.ecm.flowable.service.CustomWorkflowService;
import com.ecm.flowable.service.IFlowableBpmnModelService;
import com.ecm.icore.service.IEcmSession;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/workflow")
public class WorkflowController extends ControllerAbstract {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private CustomWorkflowService customWorkflowService;

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
	private AuthService authService;

	@Autowired
	private WorkflowAuditService workflowAuditService;
	@Autowired
	private WorkitemAuditService workitemAuditService;
	@Autowired
	private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
	@Autowired
	private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
	@Autowired
	private AuditService auditService;
	@Autowired
	private IFlowableBpmnModelService flowableBpmnModelService;
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	EcmComponentMapper ecmComponentMapper;
	@Autowired
	private EcmCfgActivityMapper ecmCfgActivityMapper;
	private String dailiString = "委托代理";

	/*************** 此处为业务代码 ******************/

	/**
	 * 部署流程
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "deployProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deployProcess(String name, MultipartFile bpmnFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			InputStream instream = bpmnFile.getInputStream();
			if(instream != null) {
				String resourceName = bpmnFile.getOriginalFilename();
				// 流程部署
				Deployment deployment = repositoryService.createDeployment().addInputStream(resourceName,
						instream).name(name).deploy();
				//增加事件监听
		        runtimeService.addEventListener(new JobListener());
				mp.put("data", deployment.getId());
				mp.put("code", ActionContext.SUCESS);
				instream.close();
			}
			else {
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "Upload file is null.");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "deleteProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProcess(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			repositoryService.deleteDeployment(id,true);
			mp.put("code", ActionContext.SUCESS);

		}catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 启动流程
	 *
	 * @param argStr 参数
	 */
	@RequestMapping(value = "/startWorkflow")
	@ResponseBody
	public Map<String, Object> startWorkflow(@RequestBody String argStr) throws AccessDeniedException, EcmException, NoPermissionException{
		// 启动流程
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> result = new HashMap<String, Object>();
		String id = (String) args.get("formId");
		try {
			Object processId=args.get("processInstanceId");
			if(processId!=null) {
				result = customWorkflowService.startWorkflowById(getSession(), args);
			}else {
				result = customWorkflowService.startWorkflow(getSession(), args);
			}
			
			EcmDocument file = documentService.getObjectById(getToken(), id);
			
			file.setStatus("流程中");
			documentService.updateObject(getToken(), file, null);
			
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 启动流程
	 *
	 * @param argStr 参数
	 */
	@RequestMapping(value = "/startWorkflowById")
	@ResponseBody
	public Map<String, Object> startWorkflowById(@RequestBody String argStr) {
		// 启动流程
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Object processId=args.get("processInstanceId");
			if(processId!=null) {
				result = customWorkflowService.startWorkflowById(getSession(), args);
			}else {
				result = customWorkflowService.startWorkflow(getSession(), args);
			}
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value = "/startWorkflowById4Copy")
	@ResponseBody
	public Map<String, Object> startWorkflowById4Copy(@RequestBody String argStr) {
		// 启动流程
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Object processId=args.get("processInstanceId");
			if(processId!=null) {
				result = customWorkflowService.startWorkflowById4Copy(getSession(), args);
			}else {
				result = customWorkflowService.startWorkflow(getSession(), args);
			}
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 启动流程
	 *
	 * @param argStr 参数
	 */
	@RequestMapping(value = "/startWorkflowByIds")
	@ResponseBody
	public Map<String, Object> startWorkflowByIds(@RequestBody String argStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<String> datas= JSONUtils.stringToArray(argStr);
			for(int i=0;datas!=null&&i<datas.size();i++) {
				String data=datas.get(i);
				// 启动流程
				Map<String, Object> args = JSONUtils.stringToMap(data);
				
				Object processId=args.get("processInstanceId");
				if(processId!=null) {
					result = customWorkflowService.startWorkflowById(getSession(), args);
				}else {
					result = customWorkflowService.startWorkflow(getSession(), args);
				}
			}
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除流程
	 *
	 * @param argStr 参数
	 */
	@RequestMapping(value = "/terminateWorkflow")
	@ResponseBody
	public Map<String, Object> terminateWorkflow(@RequestBody String argStr) {
		// 启动流程
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = customWorkflowService.terminateWorkflow(getSession(), args);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 执行跳转
	 */
	protected void moveExecutionsToSingleActivityId(List<String> executionIds, String activityId) {
		runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, activityId)
				.changeState();
	}
	protected void moveExecutionToActivityId(String executionId,String activityId){
		runtimeService.createChangeActivityStateBuilder().moveExecutionToActivityId(executionId, activityId);
	}
	/**
	 * 终止流程
	 */
	@RequestMapping(value = "/stopProcessInstanceById")
	@ResponseBody
	public Map<String, Object> stopProcessInstanceById(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> result = new HashMap<String, Object>();
		String processInstanceId = args.get("processInstanceId").toString();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
//	        if (processInstance != null) {
		List<EndEvent> endNodes = flowableBpmnModelService.findEndFlowElement(processInstance.getProcessDefinitionId());
		String endId = endNodes.get(0).getId();
		// 2、执行终止
		List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstanceId).list();
		List<String> executionIds = new ArrayList<>();
		executions.forEach(execution -> executionIds.add(execution.getId()));
		this.moveExecutionsToSingleActivityId(executionIds, endId);
		result.put("code", ActionContext.SUCESS);
		result.put("processID", processInstanceId);
//	        }else {
//				result.put("code", ActionContext.FAILURE);
//				result.put("message", e.getMessage());
//	        }
		return result;
	}

	
	@RequestMapping(value = "/moveActivityToTarget")
	@ResponseBody
	public Map<String, Object> moveActivityToTarget(@RequestBody String argStr) throws EcmException, AccessDeniedException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> result = new HashMap<String, Object>();
		String processInstanceId = args.get("currentID").toString();
		String targetTaskId = args.get("targetID").toString();
		Task currentTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		HistoricTaskInstance targetTask = historyService.createHistoricTaskInstanceQuery().taskId(targetTaskId).singleResult();
		String currentKey=currentTask.getTaskDefinitionKey();
		String targetKey=targetTask.getTaskDefinitionKey();						//至此获取了目标ID和当前ID，进行下一步操作
		List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstanceId).list();
		Execution execution = runtimeService.createExecutionQuery().parentId(processInstanceId).singleResult();
		runtimeService.createChangeActivityStateBuilder().moveExecutionToActivityId(execution.getId(), targetKey)
		.changeState();
//		String sql = "Delete from ECM_AUDIT_WORKITEM where TASK_ID ='"+currentTask.getId()+"'";			//把日志改了
//		documentService.getMapList(getToken(), sql);
		String condition = "TASK_ID='"+currentTask.getId()+"'";
		List<EcmAuditWorkitem> audits = ecmAuditWorkitemMapper.selectByCondition(condition);		//找日志
		EcmAuditWorkitem audit = audits.get(0);				//通过TASK_ID找的日志集肯定只有1个
		Date now = new Date();
		audit.setEndTime(now);
		audit.setResult("跳转");
		ecmAuditWorkitemMapper.updateByPrimaryKey(audit);	//日志更新
		
		
		
//		System.out.println(targetKey);
//		System.out.println(execution.getId());

//		 runtimeService.createChangeActivityStateBuilder().processInstanceId(processInstance.getProcessInstanceId())
//         .moveActivityIdTo(currentID,targetID).changeState();
////	        if (processInstance != null) {
//		List<EndEvent> endNodes = flowableBpmnModelService.findEndFlowElement(processInstance.getProcessDefinitionId());
//		String endId = endNodes.get(0).getId();
//		// 2、执行终止
//		List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstanceId).list();
//		List<String> executionIds = new ArrayList<>();
//		executions.forEach(execution -> executionIds.add(execution.getId()));
//		this.moveExecutionsToSingleActivityId(executionIds, endId);
		result.put("code", ActionContext.SUCESS);
		result.put("processID", processInstanceId);
//	        }else {
//				result.put("code", ActionContext.FAILURE);
//				result.put("message", e.getMessage());
//	        }
		return result;
	}
	
	
	/**
	 * 获取已审批的任务
	 * 
	 * @param argStr 参数
	 * @throws AccessDeniedException 
	 * @throws EcmException 
	 * 
	 */
	@RequestMapping(value = "doneTask")
	@ResponseBody
	public HashMap<String, Object> doneTask(@RequestBody String argStr) throws EcmException, AccessDeniedException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String userId = args.get("userId").toString();
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		StringBuilder condition = new StringBuilder("");
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		Map workflowForm = args.get("workflowForm") == null ? null
				: JSONUtils.stringToMap(args.get("workflowForm").toString());
		if(workflowForm != null) {
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("workflowName"))) {
				condition.append(" and process_def_id = '").append(workflowForm.get("workflowName")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("jobName"))) {
				condition.append(" and TASK_NAME = '").append(workflowForm.get("jobName")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeAfter"))) {
				condition.append(" and CREATE_TIME >= '").append(workflowForm.get("startTimeAfter")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeBefore"))) {
				condition.append(" and CREATE_TIME < '").append(workflowForm.get("startTimeBefore")).append("' ");
			}
		}
		condition.append("and END_TIME is not null");
		List<EcmAuditWorkitem> list = null;
		String finalCondition = "";
		if (condition.length()>0) {
			finalCondition = condition.toString().replaceFirst("and", "");
		}else {
			finalCondition = condition.toString();
		}
		try {
			//list = workitemAuditService.getMyAuditWorkitem(getToken());
			list = workitemAuditService.getMyAuditWorkitem(getToken(), pager, finalCondition);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId)
//				.finished().orderByTaskCreateTime().desc().listPage(pageIndex, pageSize);
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<HashMap> resultListTemp = new ArrayList<HashMap>();
		HashMap<String, Object> map = null;
		Set<String> processInstanceIdSet = new HashSet<String>();
		for (EcmAuditWorkitem task : list) {
			map = new HashMap<>();
			
			String titleSql = "SELECT ed.TITLE, ed.C_PROCESS_DEF_NAME FROM ECM_DOCUMENT ed, ECM_AUDIT_WORKITEM eaw WHERE ed.ID = eaw.DOC_ID AND eaw.TASK_ID = '"+ task.getTaskId() +"'";
			List<Map<String, Object>> titleList = documentService.getMapList(getToken(), titleSql);
			String title = new String();
			if(titleList.size()>0 && titleList.get(0) != null) {
				title = (String) titleList.get(0).get("TITLE");
			}else {
				title = "";
			}
			
			List<String> processInstanceIdsList = new ArrayList<String>();
			if(task.getEndTime() != null) {
				map.put("id", task.getId());
				map.put("processInstanceId", task.getProcessInstanceId());
				processInstanceIdSet.add(map.get("processInstanceId").toString());
				map.put("name", task.getTaskName());
				map.put("createTime", task.getCreateTime());
				map.put("endTime", task.getEndTime());
				map.put("title", title);
				map.put("processDefinitionId", task.getProcessDefId());
				Map<String,String> processInfo = getProcessName(task.getProcessDefId());
				if(processInfo!=null) {
					map.put("workflowName", processInfo.get("workflowName"));
					map.put("processKey", processInfo.get("processKey"));
				}
				resultListTemp.add(map);
			}
		}
//		for (HistoricTaskInstance task : tasks) {
//			map = new HashMap<>();
//			List<String> processInstanceIdsList = new ArrayList<String>();
//			map.put("id", task.getId());
//			map.put("processInstanceId", task.getProcessInstanceId());
//			processInstanceIdSet.add(map.get("processInstanceId").toString());
//			map.put("name", task.getName());
//			map.put("createTime", task.getCreateTime());
//			map.put("endTime", task.getEndTime());
//			map.put("processDefinitionId", task.getProcessDefinitionId());
//			resultListTemp.add(map);
//		}
		if (list.size() > 0) {
			getProcessVars(resultList, resultListTemp, processInstanceIdSet);
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", resultList);
		resultMap.put("totalCount", pager.getTotal());
		//historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished().count()
		return resultMap;
	}

	private void getTaskApprovalResult(String taskId, HashMap<String, Object> map) {
		List<HistoricVariableInstance> varList = historyService.createHistoricVariableInstanceQuery().taskId(taskId)
				.list();
		for (int i = 0; i < varList.size(); i++) {
			if ("outcome".equals(varList.get(i).getVariableName())) {
				map.put("result", varList.get(i).getValue());
			} else if ("message".equals(varList.get(i).getVariableName())) {
				map.put("message", varList.get(i).getValue());
			} else if ("startUser".equals(varList.get(i).getVariableName())) {

			}
		}
	}

	/**
	 * 获取userId待审批的任务
	 * @throws AccessDeniedException 
	 * @throws EcmException 
	 */
	@RequestMapping(value = "todoTask")
	@ResponseBody
	public HashMap<String, Object> todoTask(@RequestBody String argStr) throws EcmException, AccessDeniedException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String userId = args.get("userId").toString();
		StringBuilder condition = new StringBuilder("");
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Map workflowForm = args.get("workflowForm") == null ? null
				: JSONUtils.stringToMap(args.get("workflowForm").toString());
		if(workflowForm != null) {
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("workflowName"))) {
				condition.append(" and PROC_DEF_ID_ = '").append(workflowForm.get("workflowName")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("jobName"))) {
				condition.append(" and NAME_ = '").append(workflowForm.get("jobName")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeAfter"))) {
				condition.append(" and CREATE_TIME_ >= '").append(workflowForm.get("startTimeAfter")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeBefore"))) {
				condition.append(" and CREATE_TIME_ < '").append(workflowForm.get("startTimeBefore")).append("' ");
			}
		}
		long taskCount = 0;
		List<Task> taskByGroupName = null;
		List<Task> taskByUser = null;
		TaskQuery query=taskService.createTaskQuery().or().taskCandidateOrAssigned(userId);
//		List<String> roleList= user.getRoles();
//		for(int i=0;roleList!=null&&i<roleList.size();i++) {
//			query=query.taskCandidateOrAssigned(roleList.get(i));
//		}
//		taskByUser= query.orderByTaskCreateTime().desc()
//				.listPage(pageIndex, pageSize);
		
		String whereSql = todoTaskWhereSql();
		taskByGroupName=taskService.createNativeTaskQuery().sql("select * from "
				+managementService.getTableName(Task.class)+" T WHERE ("+whereSql+") "+condition.toString()+" order by CREATE_TIME_ desc").listPage(pageIndex, pageSize);
		taskCount = todoTaskCount(condition.toString(), whereSql);
		List<HashMap> resultList = new ArrayList<HashMap>();
		HashMap<String, Object> map = null;
		List<HashMap> resultListTemp = new ArrayList<HashMap>();
		Set<String> processInstanceIdSet = new HashSet<String>();
		taskByUser=taskByGroupName;
		for (Task task : taskByUser) {
			String titleSql = "SELECT ed.TITLE, ed.C_PROCESS_DEF_NAME FROM ECM_DOCUMENT ed, ECM_AUDIT_WORKITEM eaw WHERE ed.ID = eaw.DOC_ID AND eaw.TASK_ID = '"+ task.getId() +"'";
			List<Map<String, Object>> titleList = documentService.getMapList(getToken(), titleSql);
			String title = new String();
			if(titleList.size()>0 && titleList.get(0) != null) {
				title = (String) titleList.get(0).get("TITLE");
			}else {
				title = "";
			}
			
			map = new HashMap<>();
			map.put("processInstanceId", task.getProcessInstanceId());
			
			processInstanceIdSet.add(task.getProcessInstanceId());
			map.put("id", task.getId());
			map.put("title", title);
			map.put("assignee", task.getAssignee());
			map.put("name", task.getName());
			map.put("createTime", task.getCreateTime());
			map.put("processDefinitionId", task.getProcessDefinitionId());
			Map<String,String> processInfo = getProcessName(task.getProcessDefinitionId());
			if(processInfo!=null) {
				map.put("workflowName", processInfo.get("workflowName"));
				map.put("processKey", processInfo.get("processKey"));
			}
			resultListTemp.add(map);
		}
//		    CustomSqlExecution<VarQueryMapper, List<Map<String, Object>>> customSqlExecution = new AbstractCustomSqlExecution<VarQueryMapper, List<Map<String, Object>>>(VarQueryMapper.class) {
//	            @Override
//	            public List<Map<String, Object>> execute(VarQueryMapper customMapper) {
//	                return customMapper.selectVarWithProcessIds("");
//	            }
//	        };
//	        List<Map<String, Object>> varInstanceList = managementService.executeCustomSql(customSqlExecution);	 
//	        
		if (taskByUser.size() > 0) {
			getProcessVars(resultList, resultListTemp, processInstanceIdSet);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", resultList);
		resultMap.put("totalCount", taskCount);

		return resultMap;
	}
	
	public String todoTaskWhereSql() {
		LoginUser user = null;
		try {
			user = userService.getCurrentUser(getToken());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> roleList= user.getRoles();
		
		String whereSql="";
		if(roleList.size()==0) {
			whereSql = "T.ASSIGNEE_='"+user.getUserName()+"'";
		}else {
			whereSql="T.ASSIGNEE_ in(";
			for(int i=0;roleList!=null&&i<roleList.size();i++) {
				if(i==0) {
					whereSql+="'"+user.getUserName()+"','"+roleList.get(i)+"'";
//					whereSql+=" T.ASSIGNEE_='"+user.getUserName()+"' or T.ASSIGNEE_='"+roleList.get(i)+"'";
				}
				 
				 if(i!=0) {
					 whereSql+=",'"+roleList.get(i)+"'";
//					 whereSql+=" or T.ASSIGNEE_='"+roleList.get(i)+"'";
				 }
			}
			whereSql+=") ";
		}
		return whereSql;
	}

	public String todoTaskWhereSqlOA(LoginUser user) throws Exception {
		List<String> roleList= user.getRoles();
		
		String whereSql="";
		if(roleList.size()==0) {
			whereSql = "T.ASSIGNEE_='"+user.getUserName()+"'";
		}else {
			whereSql="T.ASSIGNEE_ in(";
			for(int i=0;roleList!=null&&i<roleList.size();i++) {
				if(i==0) {
					whereSql+="'"+user.getUserName()+"','"+roleList.get(i)+"'";
//					whereSql+=" T.ASSIGNEE_='"+user.getUserName()+"' or T.ASSIGNEE_='"+roleList.get(i)+"'";
				}
				 
				 if(i!=0) {
					 whereSql+=",'"+roleList.get(i)+"'";
//					 whereSql+=" or T.ASSIGNEE_='"+roleList.get(i)+"'";
				 }
			}
			whereSql+=") ";
		}
		return whereSql;
	}

	public long todoTaskCount(String condition, String whereSql) {
		if(condition==null)condition="";
		long taskCount;
		taskCount=taskService.createNativeTaskQuery().sql("select  count(*)   from " +managementService.getTableName(Task.class)+" T WHERE ("+whereSql+") "+condition.toString()).count();
		return taskCount;
	}
	/**
	 * 
	 * 
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "getDispenseCount", method = RequestMethod.GET) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDispenseCount() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> count = getCount();
			mp.put("data", count);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} 
		
		return mp;

	}
	@RequestMapping(value = "getAllTodoTaskCountForOA/usercode={id}", method = RequestMethod.GET) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public  Map<String, Object> getAllTodoTaskCountForOA(@PathVariable("id") String username) {
		Map<String, Object> mp = new HashMap<String, Object>();
		LoginUser user = null;
		IEcmSession s = null;
		try {
			try {
				//user = userService.getCurrentUser(getToken());
				s = authService.loginSSO("portal",username);
				user = userService.getCurrentUser(s.getToken());
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Map<String, Object> count = distributeCountOA(s);
			String whereSql = todoTaskWhereSqlOA(user);
			long taskCount = todoTaskCount(null, whereSql);
			taskCount=Integer.valueOf( count.get("approving").toString())+Integer.valueOf( count.get("reading").toString())+taskCount;
			mp.put("name", "");
			mp.put("systemurl", "");
			mp.put("count", taskCount);
			mp.put("txcount", "");
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} 
		
		return mp;

	}

	public Map<String, Object> getCount() throws AccessDeniedException, EcmException, SqlDeniedException {
		Map<String, Object> count = distributeCount();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		c.setTime(new Date());
		c.add(Calendar.DATE, - 6);
		c1.add(Calendar.DATE, - 14);
		Date d = c.getTime();
		String day = format.format(d);
		Date d2 = c1.getTime();
		String day2=format.format(d2);
		String condition3="select ed.CODING,ed.CREATION_DATE,ed.CREATOR,ed.C_TYPE1,ed.C_COMMENT,ed.C_APPROVE_DATE,ed2.CODING as C_IN_CODING,ed2.TYPE_NAME,ed2.ID,ed2.C_SECURITY_LEVEL "
				+ "from ecm_relation er ,ecm_document ed ,ecm_document ed2 "
				+ "where (er.PARENT_ID =ed.ID and er.CHILD_ID=ed2.ID) and  "
				+ "(ed.TYPE_NAME='借阅单' and ed.STATUS!='已完成' and ed.C_APPROVE_DATE is not null) "
				+ "and ((ed2.C_SECURITY_LEVEL='机密' and ed.C_APPROVE_DATE<='"+day+"') or (ed2.C_SECURITY_LEVEL='机密' and ed.C_APPROVE_DATE<='"+day2+"')) "
				+ "and ed.CREATOR ='"+this.getSession().getCurrentUser().getUserName()+"'";
		List<Map<String, Object>> result3 = documentService.getMapList(getToken(),condition3);
		count.put("deadline", result3.size());
		return count;
	}

	public Map<String, Object> distributeCount() throws AccessDeniedException, EcmException, SqlDeniedException {
		Map<String, Object> count = new HashMap<String, Object>();
		String currentUser=this.getSession().getCurrentUser().getUserName();
		String condition1 = "TYPE_NAME='分发单' and C_APPROVER='"+this.getSession().getCurrentUser().getUserName()+"' and STATUS='待批示'";
		String condition2 = "TYPE_NAME='分发单' and OWNER_NAME = '"+currentUser+"' and STATUS='待阅'";
		List<EcmDocument> result1 = documentService.getObjects(getToken(), condition1);
		List<EcmDocument> result2 = documentService.getObjects(getToken(), condition2);
		count.put("approving", result1.size());
		count.put("reading", result2.size());
		return count;
	}
	public Map<String, Object> distributeCountOA(IEcmSession s) throws AccessDeniedException, EcmException, SqlDeniedException {
		Map<String, Object> count = new HashMap<String, Object>();
		String currentUser=s.getCurrentUser().getUserName();
		String condition1 = "TYPE_NAME='分发单' and C_APPROVER='"+s.getCurrentUser().getUserName()+"' and STATUS='待批示'";
		String condition2 = "TYPE_NAME='分发单' and OWNER_NAME = '"+currentUser+"' and STATUS='待阅'";
		List<EcmDocument> result1 = documentService.getObjects(s.getToken(), condition1);
		List<EcmDocument> result2 = documentService.getObjects(s.getToken(), condition2);
		count.put("approving", result1.size());
		count.put("reading", result2.size());
		return count;
	}
	
	private Map<String,Map<String,String>> processDefList = new HashMap<String, Map<String,String>>();
	private Map<String,String> getProcessName(String processDefinitionId) {
		Map<String,String> processInfo = processDefList.get(processDefinitionId);
		if(processInfo == null && processDefinitionId != null) {
			ProcessDefinition processDefinitionObj=repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			Map<String,String> mp = new HashMap<String,String>();
			if(null==processDefinitionObj)return null;
			String processName = processDefinitionObj.getName();
			String processKey=processDefinitionObj.getKey();
			mp.put("workflowName", processName);
			mp.put("processKey", processKey);
			processDefList.put(processDefinitionId, mp);
			return mp;
		}
		return processInfo;
	}
	

	private void getProcessVars(List<HashMap> resultList, List<HashMap> resultListTemp,
			Set<String> processInstanceIdSet) {
		HashMap<String, Object> map;
		StringBuffer sql = new StringBuffer("select * from  ACT_HI_VARINST  where name_ in('startUser','formId','docId') and PROC_INST_ID_ in(");
		int temp = 0;
		for (Iterator<String> iterator = processInstanceIdSet.iterator(); iterator.hasNext();) {
			if (temp != 0) {
				sql.append(",");
			}
			sql.append("'").append(iterator.next()).append("'");
			temp++;
		}
		sql.append(")");

		List<HistoricVariableInstance> varInstanceList = historyService.createNativeHistoricVariableInstanceQuery()
				.sql(sql.toString()).list();
		for (int i = 0; i < resultListTemp.size(); i++) {
			map = resultListTemp.get(i);
			for (int j = 0; j < varInstanceList.size(); j++) {
				if (map.get("processInstanceId").equals(varInstanceList.get(j).getProcessInstanceId())) {
					switch (varInstanceList.get(j).getVariableName()) {
					case "startUser":
						map.put("startUser", varInstanceList.get(j).getValue());
						break;

					case "formId":
						map.put("formId", varInstanceList.get(j).getValue());
						break;

					case "docId":
						map.put("docId", varInstanceList.get(j).getValue());
						break;

					default:
						break;
					}
				}
			}
			resultList.add(map);
		}
	}

	@RequestMapping(value = "myWorkflow")
	@ResponseBody
	public HashMap<String, Object> myWorkflow(@RequestBody String argStr) throws EcmException, AccessDeniedException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String userId = args.get("userId").toString();
		String condition = args.get("condition").toString();
		
		HashMap<String, String> objecMap = new HashMap<String, String>();
		objecMap.put("档案借阅流程", "申请部门领导审批");
		objecMap.put("电子文件借阅流程", "申请部门领导审批" );
		objecMap.put("科研文件修改流程", "整编岗确认");
		objecMap.put("工作联系单审批流程", "定密");
		objecMap.put("文件生效流程", "定密");
		objecMap.put("档案鉴定流程", "修改保管日期");
		objecMap.put("档案销毁流程", "审批");

		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		List<HistoricProcessInstance> processes = null;
		long processTotalCount = 0;
		Map workflowForm = args.get("workflowForm") == null ? null
				: JSONUtils.stringToMap(args.get("workflowForm").toString());
		StringBuffer sql0 = new StringBuffer("SELECT  a.START_TIME_, a.ID_, a.END_TIME_,a.PROC_DEF_ID_,  a.START_USER_ID_, b.NAME_, c.DESCRIPTION "
				+ " FROM ACT_HI_PROCINST  a, ACT_RE_PROCDEF b, ECM_AUDIT_WORKFLOW c where a.PROC_DEF_ID_ = b.ID_ and a.PROC_INST_ID_=c.PROCESS_INSTANCE_ID ");
		StringBuffer sqlCount0 = new StringBuffer("SELECT count(*) " + " FROM ACT_HI_PROCINST a, ECM_AUDIT_WORKFLOW c " + " where 1=1 and a.PROC_INST_ID_=c.PROCESS_INSTANCE_ID ");
		StringBuffer sql1 = new StringBuffer("");
		Object desc = "%"+workflowForm.get("des")+"%";
		if (workflowForm != null) {
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("workflowName"))) {
				sql1.append(" and PROC_DEF_ID_ = '").append(workflowForm.get("workflowName")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeAfter"))) {
				sql1.append(" and START_TIME_ >= '").append(workflowForm.get("startTimeAfter")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeBefore"))) {
				sql1.append(" and START_TIME_ < '").append(workflowForm.get("startTimeBefore")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("endTimeAfter"))) {
				sql1.append(" and END_TIME_ > '").append(workflowForm.get("endTimeAfter")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("endTimeBefore"))) {
				sql1.append(" and END_TIME_ < '").append(workflowForm.get("endTimeBefore")).append("' ");
			}
			if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startUser"))) {
				sql1.append(" and START_USER_ID_ = '").append(workflowForm.get("startUser")).append("' ");
			}if (!org.springframework.util.StringUtils.isEmpty(workflowForm.get("des"))) {
				sql1.append(" and DESCRIPTION like '").append(desc).append("' ");
			}
			String isFinished = org.springframework.util.StringUtils.isEmpty(workflowForm.get("isFinished")) ? ""
					: workflowForm.get("isFinished").toString();
			if ("未完成".equals(isFinished)) {
				sql1.append(" and END_ACT_ID_  is  NULL ");
			}
			if ("已完成".equals(isFinished)) {
				sql1.append(" and END_ACT_ID_  is not NULL ");
			}

		}

		if (!"all".equals(userId)) {
			sql1.append(" and a.START_USER_ID_='").append(userId).append("' ");
		}
		sql1.append(" order by START_TIME_ desc");
		// processes =
		// historyService.createNativeHistoricProcessInstanceQuery().sql(sql0.append(sql1).append("
		// order by a.START_TIME_ desc").toString()).listPage(pageIndex,pageSize);
		processes = historyService.createNativeHistoricProcessInstanceQuery().sql(sql0.append(sql1).toString())
				.listPage(pageIndex, pageSize);
		processTotalCount = historyService.createNativeHistoricProcessInstanceQuery()
				.sql(sqlCount0.append(sql1).toString()).count();
		List<HashMap> resultList = new ArrayList<HashMap>();
//	        Map<String, VariableInstance> varMap=null;
		HashMap<String, Object> map = null;
		List<HashMap> resultListTemp = new ArrayList<HashMap>();
		Set<String> processInstanceIdSet = new HashSet<String>();
		for (HistoricProcessInstance process : processes) {
			map = new HashMap<>();
			map.put("id", process.getId());
			map.put("processInstanceId", process.getId());
			map.put("processDefinitionId", process.getProcessDefinitionId());
			processInstanceIdSet.add(process.getId());
			map.put("name", process.getName());
			map.put("startUser", process.getStartUserId());
			map.put("startTime", process.getStartTime());
			map.put("endTime", process.getEndTime() == null ? "" : process.getEndTime());
			map.put("description", process.getDescription());
			// 如下设置当前执行者
//		        String currentAssignee="";
//		        String currentTaskName="";
//		        if(process.getEndTime()==null) {
//		        	List<HistoricTaskInstance>  tasks= historyService.createHistoricTaskInstanceQuery().processInstanceId( process.getId()).orderByHistoricTaskInstanceStartTime().desc().list();
//
//			        if(tasks.size()>0)
//			        {
//			        	currentAssignee=tasks.get(0).getAssignee();
//			        	currentTaskName=tasks.get(0).getName();
//			        }
//			        map.put("currentTaskName", currentTaskName);
//			        map.put("currentAssignee",  currentTaskName+":"+currentAssignee);
//			    }else {		 
////			    	try {
////						map.put("name", workflowAuditService.(getToken(), "PROCESS_INSTANCE_ID='"+process.getId()+"'"));
////					} catch (Exception e) {
////						e.printStackTrace();
////					}
//			    	map.put("currentTaskName", "已结束");
//			        map.put("currentAssignee",  "已结束");
//			    }
			resultListTemp.add(map);
		}
		if (processes.size() > 0) {
			getProcessVars(resultList, resultListTemp, processInstanceIdSet);
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("data", resultList);
		resultMap.put("totalCount", processTotalCount);
		return resultMap;
	}

	/**
	 * 获取组件名称
	 * 
	 * @param processDefinitionId
	 */
	@ResponseBody
	@RequestMapping(value = "getEcmCfgActivity")
	public Map<String, Object> getEcmCfgActivity(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String processDefinitionId = args.get("processDefinitionId").toString();
		String activityName = args.get("activityName").toString();
		
//			String componentUrl="";
		EcmCfgActivity ecmCfgActivityObj = null;
		try {
			
			String processName = repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult().getName();
			ecmCfgActivityObj = CacheManagerCfgActivity.getCfgActivity(processName, activityName);
//	        	String componentName=CacheManagerCfgActivity.getCfgActivity(processName, activityName).getComponentName();
//	    		List<EcmComponent> comps = ecmComponentMapper.selectByCondition("NAME='" + componentName+"'");
			//获取当前任务的路由
			if(args.get("taskId")!=null&&!"".equals(args.get("taskId").toString())) {
				String taskId=args.get("taskId").toString();
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				if(task==null) {
					mp.put("data", ecmCfgActivityObj);
					mp.put("success", true);
					return mp;
				}
				ExecutionEntity ee = (ExecutionEntity) runtimeService.createExecutionQuery()
			            .executionId(task.getExecutionId()).singleResult();
				BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
			    FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(ee.getActivityId());
			    // 输出连线
			    List<SequenceFlow> outFlows = flowNode.getOutgoingFlows();
			    List<String> sequenceNames=new ArrayList<String>();
			    for(int n=0;outFlows!=null&&n<outFlows.size();n++) {
			    	String sequenceName= outFlows.get(n).getName();
			    	if(sequenceName==null||"".equals(sequenceName)) {
			    		continue;
			    	}
			    	sequenceNames.add(sequenceName);
			    }
			    if(sequenceNames.size()==0) {
			    	sequenceNames.add("通过");
			    }
			   Collections.sort(sequenceNames,comparator);
			   mp.put("sequenceNames", sequenceNames);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mp.put("data", ecmCfgActivityObj);
		mp.put("success", true);
		return mp;
	}
 
	    Comparator<String> comparator = new Comparator<String>() {

	        public int compare(String o1, String o2) {
	            Collator collator = Collator.getInstance();
	            return collator.getCollationKey(o1).compareTo(
	                collator.getCollationKey(o2));
	        }
	    };

	@RequestMapping(value = "getApprovalUserList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getApprovalUserList(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String processDefinitionId = args.get("processDefinitionId").toString();
		String activityName = args.get("activityName").toString();
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> objectMap = null;
		String processName = repositoryService.getProcessDefinition(processDefinitionId).getName();
		try {
			List<EcmCfgActivity> acitivityList = ecmCfgActivityMapper.selectByProcessName(processName);
			acitivityList.removeIf(a -> a.getSelectActivityList().indexOf(activityName) < 0);			
			mp.put("data", acitivityList);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	/**
	 * 带必填的数据
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "getApprovalUserListVisible", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getApprovalUserListVisible(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String processName="";
		if(args.get("processDefinitionId")==null) {
			processName=args.get("processName").toString();
		}else {
			String processDefinitionId = args.get("processDefinitionId").toString();
			processName= repositoryService.getProcessDefinition(processDefinitionId).getName();
		}
		
		String activityName = args.get("activityName").toString();
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> objectMap = null;
		try {
			String whereSql="PROCESS_NAME='"+processName+"' and ACTIVITY_NAME!='start'";
			List<EcmCfgActivity> acitivityList = ecmCfgActivityMapper.selectByCondition(whereSql);
//			List<EcmCfgActivity> acitivityList = ecmCfgActivityMapper.selectByProcessName(processName);
//			acitivityList.removeIf(a -> a.getSelectActivityList().indexOf(activityName) < 0);	
			acitivityList.removeIf(a -> a.getSelectActivities()==null||a.getSelectActivities().indexOf(activityName) < 0  );
//			acitivityList.removeIf(a -> a.getSelectActivityList().stream().anyMatch(s->!s.contains(activityName)));
			
			List<EcmCfgActivity> result=new ArrayList<EcmCfgActivity>();
			for(int x=0;x<acitivityList.size();x++) {
				EcmCfgActivity a=acitivityList.get(x);
				List<String> activitysList= a.getSelectActivityList();
				for(int n=0;n<activitysList.size();n++) {
					String activityStr=activitysList.get(n);
					if(activityStr.contains(activityName)) {
						String[] v= activityStr.split(":");
						if(v.length<2) {
							a.setActivityName(a.getActivityName()+":false");
						}else {
							a.setActivityName(a.getActivityName()+":"+v[1]);
						}
						result.add(a);
					}
				}
				
				
			}
			
			mp.put("data", result);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	@RequestMapping(value = "getApprovalAllUserList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getApprovalAllUserList(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Object processDefinitionIdObj=args.get("processDefinitionId");
		Map<String, Object> mp = new HashMap<String, Object>();
		
		String processName ="";
		if(args.get("processDefinitionName")!=null) {
			processName=args.get("processDefinitionName").toString();
		}else if(processDefinitionIdObj!=null) {
			processName = repositoryService.getProcessDefinition(processDefinitionIdObj.toString()).getName();
		}else {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "请选择正确的流程名称");
			return mp;
		}
		 String whereSql="PROCESS_NAME='"+processName+"' and ACTIVITY_NAME!='start'";
		try {
			List<EcmCfgActivity> acitivityList = ecmCfgActivityMapper.selectByCondition(whereSql);
			mp.put("data", acitivityList);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	private Date formatDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 批准
	 *
	 * @param taskId 任务ID
	 * @throws AccessDeniedException 
	 */
	@RequestMapping(value = "completeTask")
	@ResponseBody
	public String completeTask(@RequestBody String argStr) throws AccessDeniedException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		args.put("outcome", args.get("result").toString());
		args.remove("result");
		Object formIdObj= args.get("formId");
		if(formIdObj!=null) {
			EcmDocument document= documentService.getObjectById(getToken(), formIdObj.toString());
			if(CacheManagerOper.getEcmParameters().get("isNpic")==null) {
			args.putAll(document.getAttributes());
			}
		}
		args.put("C_ITEM_STATUS", args.get("outcome"));
		customWorkflowService.completeTask(getToken(),args);
		return "processed ok!";

	}

	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String taskId = args.get("id").toString();
		customWorkflowService.claimTask(getSession(), taskId);
		return "processed ok!";
	}

	@RequestMapping(value = "unclaim")
	@ResponseBody
	public String unclaim(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String taskId = args.get("taskId").toString();
		customWorkflowService.unclaimTask(getSession(), taskId);
		return "processed ok!";
	}

	/**
	 * @param taskId
	 * @param result
	 * @param message
	 */
	private void newEcmauditWorkItem(String taskId, String result, String message) {
		newEcmauditWorkItem(taskId, result, message, "", "");
	}

	/**
	 * @param taskId
	 * @param result
	 * @param message
	 * @param formId
	 * @param docId
	 */
	private void newEcmauditWorkItem(String taskId, String result, String message, String formId, String docId) {
		EcmAuditWorkitem audit = new EcmAuditWorkitem();
		HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		audit.createId();
		
		audit.setCreateTime(new Date());
		if (dailiString.equals(result)) {
			audit.setEndTime(new Date());
		} else {
			audit.setEndTime(task.getEndTime());
		}
		audit.setDocId("");
		audit.setFormId("");
		audit.setTaskName(task.getName());

		audit.setAssignee(task.getAssignee());
		audit.setResult(result);
		audit.setMessage(message);
		audit.setProcessInstanceId(task.getProcessInstanceId());
		audit.setTaskId(taskId);
		ecmAuditWorkitemMapper.insert(audit);
	}

	/**
	 * 代理任务
	 *
	 * @param taskId 任务ID userId 接收代理人员ID
	 */
	@RequestMapping(value = "delegateTask")
	@ResponseBody
	public String delegateTask(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String taskId = args.get("taskId").toString();
		String delegateTaskUserId = args.get("delegateTaskUserId").toString();
		String result = dailiString;
		String message = args.get("message").toString();
		customWorkflowService.delegateTask(taskId, delegateTaskUserId);
		newEcmauditWorkItem(taskId, result, message);
		return "processed ok!";
	}

	/**
	 * 生成流程图
	 *
	 * @param processId 任务ID
	 * @throws IOException 
	 */
	@RequestMapping(value = "processDiagram")
	@ResponseBody
	public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId,String time) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
		// 流程走完的话就不highlight流程节点，走另外的处理方式
		if (pi == null) {
	        String processDefinitionId = "";
	        if (this.isFinished(processId)) {// 如果流程已经结束，则得到结束节点
	            HistoricProcessInstance pis = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
	            processDefinitionId=pis.getProcessDefinitionId();
	        } else {// 如果流程没有结束，则取当前活动节点
	            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
	            ProcessInstance pis = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
	            processDefinitionId=pi.getProcessDefinitionId();
	        }
	        List<String> highLightedActivitis = new ArrayList<String>();
	        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();

	        for(HistoricActivityInstance tempActivity : highLightedActivitList){
	            String activityId = tempActivity.getActivityId();
	            highLightedActivitis.add(activityId);
	        }

	        List<String> flows = new ArrayList<>();
	        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
	        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

	        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
	        InputStream in = diagramGenerator.generateDiagram(
	        		bpmnModel, 
	        		"png", 
	        		highLightedActivitis, 
	        		flows, 
	        		engconf.getActivityFontName(),
	                engconf.getLabelFontName(), 
	                engconf.getAnnotationFontName(), 
	                engconf.getClassLoader(), 1.5, true);
	        OutputStream out = null;
	        byte[] buf = new byte[1024];
	        int legth = 0;
			try {
				out = httpServletResponse.getOutputStream();
				while ((legth = in.read(buf)) != -1) {
					out.write(buf, 0, legth);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			return;
		}
		//流程要是正在走的话就正常处理
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		Task task = tasks.get(0);		//如果说存在多审批人情况下，TASK不止一个，直接取一个task然后取model就可以了
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		String InstanceId = task.getProcessInstanceId();
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();
		List<String> highLightedFlows = new ArrayList<>();
		List<String> highLightedActivities = new ArrayList<>();
		List<HistoricActivityInstance> allHistoricActivityIntances = historyService
				.createHistoricActivityInstanceQuery().processInstanceId(InstanceId).list();
		allHistoricActivityIntances.forEach(historicActivityInstance -> {
			if (BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW.equals(historicActivityInstance.getActivityType())) {
				highLightedFlows.add(historicActivityInstance.getActivityId());
			} else {
				highLightedActivities.add(historicActivityInstance.getActivityId());
			}
		});

		List<String> runningActivitiIdList = null;
		// 流程已结束
		if (pi.isEnded()) {
			runningActivitiIdList = Arrays.asList();
		} else {
			runningActivitiIdList = runtimeService.getActiveActivityIds(InstanceId);
		}

		BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
		ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
		engconf.setProcessDiagramGenerator(new CustomProcessDiagramGenerator());
		CustomProcessDiagramGenerator diagramGenerator = (CustomProcessDiagramGenerator) engconf
				.getProcessDiagramGenerator();
		InputStream in = diagramGenerator.generateCustomDiagram(bpmnModel, "png", highLightedActivities,
				runningActivitiIdList, highLightedFlows, engconf.getActivityFontName(), engconf.getLabelFontName(),
				engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.5, true);

		OutputStream out = null;
		byte[] buf = new byte[1024];
		int legth = 0;
		try {
			out = httpServletResponse.getOutputStream();
			while ((legth = in.read(buf)) != -1) {
				out.write(buf, 0, legth);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	 public boolean isFinished(String processInstanceId) {
	        return historyService.createHistoricProcessInstanceQuery().finished()
	                .processInstanceId(processInstanceId).count() > 0;
	    }

	
	
	@ResponseBody
	@RequestMapping(value = "diagram")
	public void getJpgDiagram(HttpServletResponse httpServletResponse, String processMode) {
	    try {
			//ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
	        //根据modelId或者BpmnModel
//	        Model modelData = repositoryService.getModel(processId);
//	        byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
//	        JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
			String processDefinitionKey = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processMode)
			.latestVersion().singleResult().getId();	//获取最新流程定义ID
	    	BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionKey);

	        //获得图片流
	        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
	        InputStream in = diagramGenerator.generateDiagram(
	                bpmnModel,
	                "png",
	                Collections.emptyList(),
	                Collections.emptyList(),
	                "宋体",
	                "宋体",
	                "宋体",
	                null,
	                1.5,
	                true);
	        //输出为图片
			OutputStream out = null;
			byte[] buf = new byte[1024];
			int legth = 0;
			try {
				out = httpServletResponse.getOutputStream();
				while ((legth = in.read(buf)) != -1) {
					out.write(buf, 0, legth);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * 测试流程
	 *
	 * @param argStr
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 * @throws EcmException 
	 */
	@RequestMapping(value = "testWorkflow")
	@ResponseBody
	public String testWorkflow(@RequestBody String argStr) throws AccessDeniedException, EcmException, NoPermissionException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String senseNumber = args.get("formId").toString();
		args.put("formId", "cd857371a932488dabbf9bf399ba942e");

		switch (senseNumber) {
		case "1":
			args.put("case", "1");
			args.put("fileTopestSecurityLevel", "普通商密");
			args.put("drawingNumber", 21);
			args.put("fileNumber", 10);
			args.put("message", "1.普通商密+21个图纸或10个文件");

			break;

		case "2":
			args.put("case", "2");
			args.put("fileTopestSecurityLevel", "普通商密");
			args.put("drawingNumber", 2);
			args.put("fileNumber", 2);
			args.put("message", "2.普通商密+2个图纸或2个文件");

			break;

		case "3":
			args.put("case", "3");
			args.put("fileTopestSecurityLevel", "受限");
			args.put("drawingNumber", 21);
			args.put("fileNumber", 10);
			args.put("message", "3.受限+21个图纸或10个文件");
			break;

		case "4":
			args.put("case", "4");
			args.put("fileTopestSecurityLevel", "内部公开");
			args.put("drawingNumber", 300);
			args.put("fileNumber", 300);
			args.put("message", "4.内部公开+300个图纸或300个文件");

			break;

		default:
			break;
		}

		startSensen1(JSONUtils.mapToJson(args));

		// 通过审核
		return "processed ok!";
	}

	/**
	 * 启动场景
	 *
	 * @param argStr
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 * @throws EcmException 
	 */
	@RequestMapping(value = "startSensen1")
	@ResponseBody
	public String startSensen1(@RequestBody String argStr) throws AccessDeniedException, EcmException, NoPermissionException {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);

		startWorkflow(argStr);

		// 完成完成
		int tasknumber = 0;
		int i = 30;
		do {
			i--;
			args.put("userId", "Admin");
			args.put("condition", "");
			args.put("pageSize", "10");
			args.put("pageIndex", "0");
			HashMap<String, Object> tasks = todoTask(JSONUtils.mapToJson(args));
			tasknumber = ((List) tasks.get("data")).size() - 1;
			if (tasknumber >= 0) {
				String taskId = ((Map) ((ArrayList) tasks.get("data")).get(0)).get("id").toString();
				args.put("taskId", taskId);
				args.put("result", "通过");
				args.put("message", args.get("message"));
				completeTask(JSONUtils.mapToJson(args));
			}
		} while (i > 0);

		// 通过审核
		return "processed ok!";
	}

	/**
	 * 
	 * 从flowable获取流程审批信息
	 * 
	 * @param processInstanceId
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkflowTask0")
	public Map<String, Object> getWorkflowTask0(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String processInstanceId = args.get("processInstanceId").toString();
		List<Map> resultList = new ArrayList<Map>();
		String isPocessFinished = "0";
		try {
			List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).orderByTaskCreateTime().asc().list();
			for (HistoricTaskInstance task : tasks) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("id", task.getId());
				map.put("name", task.getName());
				map.put("assignee", task.getAssignee());
				getTaskApprovalResult(task.getId(), map);
				map.put("createTime", task.getCreateTime());
				map.put("endTime", task.getEndTime());
				map.put("processInstanceId", task.getProcessInstanceId());
				resultList.add(map);
			}
			HistoricProcessInstance hiProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).unfinished().singleResult();
			if (hiProcessInstance == null) {
				isPocessFinished = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mp.put("data", resultList);
		mp.put("isPocessFinished", isPocessFinished);

		return mp;
	}

	/**
	 * 
	 * 从EcmAudit获取流程审批信息
	 * 
	 * @param processInstanceId
	 */
	@ResponseBody
	@RequestMapping(value = "getWorkflowTask")
	public Map<String, Object> getWorkflowTask(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String processInstanceId = args.get("processInstanceId").toString();
		List<HashMap> resultList = new ArrayList<HashMap>();
		List<HashMap> resultListT = new ArrayList<HashMap>();
		Set<String> processInstanceIdSet = new HashSet<String>();
		String isPocessFinished = "0";
		try {
			List<EcmAuditWorkitem> tasks = ecmAuditWorkitemMapper
					.selectByCondition("PROCESS_INSTANCE_ID='" + processInstanceId + "' order by CREATE_TIME desc");
			for (EcmAuditWorkitem task : tasks) {
				HashMap<String, Object> map = new HashMap<>();
				
				processInstanceIdSet.add(task.getProcessInstanceId());
				map.put("id", task.getTaskId());
				map.put("taskId", task.getTaskId());
				map.put("name", task.getTaskName());
				map.put("assignee", task.getAssignee());
				map.put("result", task.getResult());
				map.put("message", task.getMessage());
				map.put("createTime", task.getCreateTime());
				map.put("endTime", task.getEndTime());
				map.put("processInstanceId", task.getProcessInstanceId());
				map.put("docId", task.getDocId());
				resultList.add(map);
			}
			
			getProcessVars(resultListT, resultList, processInstanceIdSet);

			HistoricProcessInstance hiProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).unfinished().singleResult();
			if (hiProcessInstance == null) {
				isPocessFinished = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mp.put("data", resultListT);
		mp.put("isPocessFinished", isPocessFinished);

		return mp;
	}

	/**
	 * 待办任务数量
	 * 
	 * @param LoginName
	 */
	@ResponseBody
	@RequestMapping(value = "getMyAllTodoCount")
	public long getMyAllTodoCount(@RequestParam String LoginName) {
		// 根据登录名获取用户名
		EcmUser user = userService.getObjectByLoginName(null, LoginName);
		if (user != null) {
			LoginName = user.getName();
		}
		return taskService.createTaskQuery().taskAssignee(LoginName).count();

	}

	/**
	 * 
	 * @param jobId
	 */
	@ResponseBody
	@RequestMapping(value = "getJobById")
	public Map<String, Object> getJobById(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String jobId = args.get("jobId").toString();
		Job job = geJobById(jobId);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("job", job);
		return mp;
	}

	/**
	 * 
	 * @param jobId
	 */
	@ResponseBody
	@RequestMapping(value = "getJobList")
	public Map<String, Object> getJobList(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String jobType = args.get("jobType").toString();
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Job> jobList = null;
		switch (jobType) {
		case "suspendJob":
			jobList = managementService.createSuspendedJobQuery().list();
			break;
		case "deadJob":
			jobList = managementService.createDeadLetterJobQuery().list();
			break;

		default:
			jobList = managementService.createJobQuery().list();
			break;
		}
		mp.put("data", jobList);
		mp.put("totalCount", jobList.size());
		return mp;
	}

	public Job geJobById(String jobId) {
		Job job = managementService.createJobQuery().jobId(jobId).singleResult();
		validateJob(job, jobId);
		return job;
	}

	/**
	 * 
	 * @param jobId
	 */
	@ResponseBody
	@RequestMapping(value = "getSuspendedJobById")
	public Map<String, Object> getSuspendedJobById(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String jobId = args.get("jobId").toString();
		Job job = managementService.createSuspendedJobQuery().jobId(jobId).singleResult();
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("job", job);
		return mp;
	}

	public Job getDeadLetterJobById(String jobId) {
		Job job = managementService.createDeadLetterJobQuery().jobId(jobId).singleResult();
		validateJob(job, jobId);
		return job;
	}

	protected void validateJob(Job job, String jobId) {
		if (job == null) {
			throw new FlowableObjectNotFoundException("Could not find a deadletter job with id '" + jobId + "'.",
					Job.class);
		}
	}

	/**
	 * 
	 * @param jobId
	 */
	@ResponseBody
	@RequestMapping(value = "deleteJobById")
	public Map<String, Object> deleteJobById(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String jobId = args.get("jobId").toString();
		Map<String, Object> mp = new HashMap<String, Object>();
		managementService.deleteJob(jobId);
		mp.put("success", true);
		return mp;
	}

	public void deleteDeadLetterJob(String jobId) {
		managementService.deleteDeadLetterJob(jobId);

	}

	public void deleteSuspendedJob(String jobId) {
		managementService.deleteSuspendedJob(jobId);
	}
	
	/**
	 * 
	 * @param jobId
	 */
	@ResponseBody
	@RequestMapping(value = "executeJob")
	public Map<String, Object> executeJob(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String jobId = args.get("jobId").toString();
		String jobType = args.get("jobType").toString();
		Map<String, Object> mp = new HashMap<String, Object>();

		switch (jobType) {
		case "suspendJob":
			managementService.moveSuspendedJobToExecutableJob(jobId);
			break;
		case "deadJob":
			managementService.moveDeadLetterJobToExecutableJob(jobId, 1);
			break;

		default:
			break;
		}
		managementService.executeJob(jobId);
		mp.put("success", true);
		return mp;
	}

	public void moveDeadLetterJobToExecutableJob(String jobId) {
		managementService.moveDeadLetterJobToExecutableJob(jobId, 3);

	}

}
