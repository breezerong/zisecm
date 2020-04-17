package com.ecm.portal.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.common.engine.impl.cmd.CustomSqlExecution;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.cmd.AbstractCustomSqlExecution;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.persistence.entity.VariableInstance;
import org.flowable.variable.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.bpm.ProcessService;
import com.ecm.core.bpm.WorkflowAuditService;
import com.ecm.core.bpm.WorkflowService;
import com.ecm.core.bpm.WorkitemAuditService;
import com.ecm.core.cache.manager.impl.CacheManagerCfgActivity;
import com.ecm.core.cache.manager.impl.CacheManagerEcmMenu;
import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.dao.EcmCfgActivityMapper;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.dao.EcmWorkflowMapper;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmMenuItem;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AuditService;
import com.ecm.core.service.UserService;
import com.ecm.flowable.controller.StartProcessNameProcessInstanceCmd;
import com.ecm.flowable.listener.JobListener;
import com.ecm.flowable.service.IFlowableBpmnModelService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.test.flowable.TODOApplication;

@Controller
@RequestMapping(value = "/workflow")
public class WorkflowController  extends ControllerAbstract{
  	@Autowired
    private RuntimeService runtimeService;
 	    @Autowired
	    private TaskService taskService;
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
		private WorkflowAuditService workflowAuditService;
		@Autowired
		private  WorkitemAuditService  workitemAuditService;
		@Autowired
		private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
		@Autowired
		private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
		@Autowired
		private AuditService auditService;
	    @Autowired
	    private IFlowableBpmnModelService flowableBpmnModelService;
		
		@Autowired
		EcmComponentMapper ecmComponentMapper;
		@Autowired
		private EcmCfgActivityMapper ecmCfgActivityMapper;
		private String dailiString="委托代理";
	    /***************此处为业务代码******************/
	    
	    /**
	     * 部署流程
	     * @throws Exception 
	     */
	    @RequestMapping(value = "deploymentProcess")
	    @ResponseBody
	    public String deploymentProcessExpense() throws Exception {

	        //流程部署
	        Deployment deployment = repositoryService.createDeployment().addInputStream("借阅流程.bpmn", new FileInputStream("C:\\Workfolder\\zisecm\\zisecm-portal\\src\\main\\resources\\diagrams\\借阅流程.bpmn"))
	                //.addClasspathResource("resources/diagrams/借阅流程.bpmn")
	                .name("flowable")
	                .deploy();
	        
	        //增加事件监听
	        //runtimeService.addEventListener(new JobListener());
	        return deployment.getId();
	    }
	    
	    /**
	     * 启动流程
	     *
	     * @param argStr    参数
	     */
	    @RequestMapping(value = "/startWorkflow")
	    @ResponseBody
	    public Map<String, Object> startWorkflow(@RequestBody String argStr) {
	        //启动流程
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> result = new HashMap<String, Object>();
			try {
		    	String userName =workflowAuditService.getSession(getToken()).getCurrentUser().getUserName();
		    		Authentication.setAuthenticatedUserId(userName);
		    		args.put("startUser", userName);
 			        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_borrow", args);
					String processName="借阅流程 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
 			        try { 			        
						runtimeService.setProcessInstanceName(processInstance.getId(), processName);
					}catch(Exception e) {
					}
 			        //创建流程日志
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
	     * 删除流程
	     *
	     * @param argStr    参数
	     */
	    @RequestMapping(value = "/terminateWorkflow")
	    @ResponseBody
	    public Map<String, Object> terminateWorkflow(@RequestBody String argStr) {
	        //启动流程
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> result = new HashMap<String, Object>();
			try {
				String processInstanceId=args.get("processInstanceId").toString();
			    // taskService
			    //repositoryService.process
				List<String> activeTaskList=runtimeService.getActiveActivityIds(processInstanceId);
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

	    /**
	     * 执行跳转
	     */
	    protected void moveExecutionsToSingleActivityId(List<String> executionIds, String activityId) {
	        runtimeService.createChangeActivityStateBuilder()
	                .moveExecutionsToSingleActivityId(executionIds, activityId)
	                .changeState();
	    }

	    /**
	     * 终止流程
	     */
	    @RequestMapping(value = "/stopProcessInstanceById")
	    @ResponseBody
	    public Map<String, Object>  stopProcessInstanceById(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> result = new HashMap<String, Object>();
			String processInstanceId=args.get("processInstanceId").toString();
	        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//	        if (processInstance != null) {
	            List<EndEvent> endNodes = flowableBpmnModelService.findEndFlowElement(processInstance.getProcessDefinitionId());
	            String endId = endNodes.get(0).getId();
	            //2、执行终止
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

 
	    /**
	     * 获取已审批的任务
	     * @param argStr    参数
	     * 
	     */
	    @RequestMapping(value = "doneTask")
	    @ResponseBody
	    public HashMap<String,Object> doneTask(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String userId= args.get("userId").toString();
			String condition= args.get("condition").toString();
			int pageSize= Integer.parseInt(args.get("pageSize").toString()) ;
			int pageIndex= Integer.parseInt(args.get("pageIndex").toString());
	    	List<HistoricTaskInstance> tasks =  historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished().orderByTaskCreateTime().desc().listPage(pageIndex,pageSize);
 	        List<HashMap> resultList = new ArrayList<HashMap>();
 	        List<HashMap> resultListTemp = new ArrayList<HashMap>();
 	        HashMap<String, Object> map=null;
 	        Set<String> processInstanceIdSet = new HashSet<String>();
	        for (HistoricTaskInstance task : tasks) {
		        map = new HashMap<>();
		        List<String> processInstanceIdsList=new ArrayList<String>();
		        map.put("id", task.getId());
		        map.put("processInstanceId", task.getProcessInstanceId());
		        processInstanceIdSet.add(map.get("processInstanceId").toString());
		        map.put("name", task.getName());
		        map.put("createTime", task.getCreateTime());
		        map.put("endTime", task.getEndTime());
//		        getTaskApprovalResult(task.getId(), map);

//		        List<Comment>  commentsList=  taskService.getTaskComments(task.getId());
//		        String taskComments="";
//		        for (int i = 0; i < commentsList.size(); i++) {
//	        	
//		        	taskComments=taskComments+commentsList.get(0).getFullMessage()+"; ";
//				}
 		        resultListTemp.add(map);
	        }
	        if(tasks.size()>0) {
		        getProcessVars(resultList, resultListTemp, processInstanceIdSet); 	
	        }

	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        resultMap.put("totalCount", historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished().count());
	        return resultMap;
	    }

		private void getTaskApprovalResult(String taskId, HashMap<String, Object> map) {
			List<HistoricVariableInstance> varList = historyService.createHistoricVariableInstanceQuery().taskId(taskId).list();
			for (int i = 0; i < varList.size(); i++) {
				if("outcome".equals( varList.get(i).getVariableName())) {
			        map.put("result", varList.get(i).getValue());					
				}else if("message".equals( varList.get(i).getVariableName())) {			
			        map.put("message", varList.get(i).getValue());					
				}else if("startUser".equals( varList.get(i).getVariableName())) {

				}
			}
		}

	    /**
	     * 获取userId待审批的任务
	     */
	    @RequestMapping(value = "todoTask")
	    @ResponseBody
	    public HashMap<String,Object> todoTask(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String userId= args.get("userId").toString();
			int pageSize= Integer.parseInt(args.get("pageSize").toString()) ;
			int pageIndex= Integer.parseInt(args.get("pageIndex").toString());
 
			List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().listPage(pageIndex, pageSize);
	        List<HashMap> resultList = new ArrayList<HashMap>();
//	        Map<String, VariableInstance> varMap=null;
	        HashMap<String, Object> map=null;
 	        List<HashMap> resultListTemp = new ArrayList<HashMap>();
 	        Set<String> processInstanceIdSet = new HashSet<String>();
	        for (Task task : tasks) {
		        map = new HashMap<>();
		        map.put("processInstanceId", task.getProcessInstanceId());
		        processInstanceIdSet.add(task.getProcessInstanceId());
		        map.put("id", task.getId());
		        map.put("name", task.getName());
//		        varMap=runtimeService.getVariableInstances(map.get("processInstanceId").toString());
//		        map.put("startUser", varMap.get("startUser").getValue());
//		        map.put("formId", varMap.get("formId").getValue());
		        map.put("createTime", task.getCreateTime());
		        map.put("processDefinitionId", task.getProcessDefinitionId());
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
	        if(tasks.size()>0) {
		        getProcessVars(resultList, resultListTemp, processInstanceIdSet); 	
	        }
	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        resultMap.put("totalCount", taskService.createTaskQuery().taskAssignee(userId).count());

	        return resultMap;
	    }

		private void getProcessVars(List<HashMap> resultList, List<HashMap> resultListTemp,
				Set<String> processInstanceIdSet) {
			HashMap<String, Object> map;
			StringBuffer  sql = new StringBuffer ("select * from  ACT_HI_VARINST  where  PROC_INST_ID_ in(");
			int temp=0;
			for (Iterator iterator = processInstanceIdSet.iterator(); iterator.hasNext();) {
				if(temp!=0) {
					sql.append(",");
				}
				sql.append("'").append(iterator.next()).append("'");
				temp++;
			} 
			sql.append(")");
			
			List<HistoricVariableInstance>  varInstanceList=  historyService.createNativeHistoricVariableInstanceQuery().sql(sql.toString()).list();
			for (int i = 0; i < resultListTemp.size(); i++) {
				map=resultListTemp.get(i);
				for (int j = 0; j < varInstanceList.size(); j++) {
					if(map.get("processInstanceId").equals(varInstanceList.get(j).getProcessInstanceId())){
						if("startUser".equals(varInstanceList.get(j).getVariableName())) {
				        	map.put("startUser", varInstanceList.get(j).getValue());
						}else if("formId".equals(varInstanceList.get(j).getVariableName())) {
							map.put("formId", varInstanceList.get(j).getValue());
						}
					}
				}
				resultList.add(map);
			}
		}

 
	    @RequestMapping(value = "myWorkflow")
	    @ResponseBody
	    public HashMap<String,Object> myWorkflow(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String userId= args.get("userId").toString();
			String condition= args.get("condition").toString();
			 
			int pageSize= Integer.parseInt(args.get("pageSize").toString()) ;
			int pageIndex= Integer.parseInt(args.get("pageIndex").toString());
	    	List<HistoricProcessInstance> processes = null;
	    	long processTotalCount=0;
			Map workflowForm=  args.get("workflowForm")==null?null:JSONUtils.stringToMap(args.get("workflowForm").toString());
			StringBuffer  sql0= new StringBuffer ("SELECT a.ID_, a.START_TIME_, a.END_TIME_,  a.START_USER_ID_, b.NAME_ " + 
	    			" FROM ACT_HI_PROCINST  a, ACT_RE_PROCDEF b where a.PROC_DEF_ID_ = b.ID_ ");
			StringBuffer  sqlCount0= new StringBuffer ("SELECT count(*) " + 
	    			" FROM ACT_HI_PROCINST a " + 
	    			" where 1=1 ");
			StringBuffer  sql1= new StringBuffer ("");
	    	if(workflowForm!=null) {
	    	        if(!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeAfter"))) {
	    	    		sql1.append(" and START_TIME_ >= '").append(workflowForm.get("startTimeAfter")).append("' ");
	    	        }
	    	        if(!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startTimeBefore"))) {
	    	    		sql1.append(" and START_TIME_ < '").append(workflowForm.get("startTimeBefore")).append("' ");
	    	        }	    	        
	    	        if(!org.springframework.util.StringUtils.isEmpty(workflowForm.get("endTimeAfter"))) {
	    	    		sql1.append(" and END_TIME_ > '").append(workflowForm.get("endTimeAfter")).append("' ");
	    	        }
	    	        if(!org.springframework.util.StringUtils.isEmpty(workflowForm.get("endTimeBefore"))) {
	    	    		sql1.append(" and END_TIME_ < '").append(workflowForm.get("endTimeBefore")).append("' ");
	    	        }
	    	        if(!org.springframework.util.StringUtils.isEmpty(workflowForm.get("startUser"))) {
	    	    		sql1.append(" and START_USER_ID_ = '").append(workflowForm.get("startUser")).append("' ");
	    	        }
	    	        String isFinished = org.springframework.util.StringUtils.isEmpty(workflowForm.get("isFinished"))?"":workflowForm.get("isFinished").toString();
					if("未完成".equals(isFinished)) {
	    	    		sql1.append(" and END_ACT_ID_  is  NULL ");
	    	        }
	    	        if("已完成".equals(isFinished)) {
	    	    		sql1.append(" and END_ACT_ID_  is not NULL ");
	    	        }
	    	        
	    	}
	    	
	    	if(!"all".equals(userId) ) {
	    		sql1.append(" and a.START_USER_ID_='").append(userId).append("' ");
	    	}
    		//processes = historyService.createNativeHistoricProcessInstanceQuery().sql(sql0.append(sql1).append(" order by a.START_TIME_ desc").toString()).listPage(pageIndex,pageSize);
    		processes = historyService.createNativeHistoricProcessInstanceQuery().sql(sql0.append(sql1).append(" order by a.START_TIME_ desc").toString()).listPage(pageIndex,pageSize);
    		processTotalCount = historyService.createNativeHistoricProcessInstanceQuery().sql(sqlCount0.append(sql1).toString()).count();
	        List<HashMap> resultList = new ArrayList<HashMap>();
//	        Map<String, VariableInstance> varMap=null;
	        HashMap<String, Object> map=null;
 	        List<HashMap> resultListTemp = new ArrayList<HashMap>();
 	        Set<String> processInstanceIdSet = new HashSet<String>();
	        for (HistoricProcessInstance process : processes) {
		        map = new HashMap<>();
		        map.put("id", process.getId());
		        map.put("processInstanceId", process.getId());
		        processInstanceIdSet.add(process.getId());
		        map.put("name", process.getName());
		        map.put("startUser", process.getStartUserId());
		        map.put("startTime", process.getStartTime());
		        map.put("endTime", process.getEndTime()==null?"":process.getEndTime());
		        //如下设置当前执行者
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
	        if(processes.size()>0) {
		        getProcessVars(resultList, resultListTemp, processInstanceIdSet); 	
	        }

	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        resultMap.put("totalCount",processTotalCount);
	        return resultMap;
	    }

	    
	    /**
	     * 获取组件名称
	     * @param processDefinitionId
	     */
	    @ResponseBody
	    @RequestMapping(value = "getEcmCfgActivity")
	    public Map<String, Object>  getEcmCfgActivity(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> mp =  new HashMap<String, Object>();
			String processDefinitionId=args.get("processDefinitionId").toString();
			String activityName=args.get("activityName").toString();
			String componentUrl="";
	        try {
	        	String processName= repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getName();
	        	String componentName=CacheManagerCfgActivity.getCfgActivity(processName, activityName).getComponentName();
	    		List<EcmComponent> comps = ecmComponentMapper.selectByCondition("NAME='" + componentName+"'");
	    		componentUrl=comps.get(0).getUrl();
 			} catch (Exception e) {
				e.printStackTrace();
			}
	        mp.put("data", componentUrl);
	        mp.put("success", true);
         	return  mp;
	    }
	    	    
	    
		@RequestMapping(value = "getApprovalUserList", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> getApprovalUserList(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String processDefinitionId=args.get("processDefinitionId").toString();
			String activityName=args.get("activityName").toString();
			Map<String, Object> mp = new HashMap<String, Object>();
			Map<String, Object> objectMap = null;
			String processName=repositoryService.getProcessDefinition(processDefinitionId).getName();
			try {
				List<EcmCfgActivity> acitivityList=ecmCfgActivityMapper.selectByProcessName(processName);
				acitivityList.removeIf(a->a.getSelectActivityList().indexOf(activityName)<0);
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
	     */
	    @RequestMapping(value = "completeTask")
	    @ResponseBody
	    public String completeTask(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String taskId= args.get("taskId").toString();
			String result= args.get("result").toString();
			String message= args.get("message").toString();
			args.put("outcome",result);
	        setTaskApprovalResult(taskId, result, message);
	        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	        // owner不为空说明可能存在委托任务
	        if (!StringUtils.isEmpty(task.getOwner())) {
	        	DelegationState delegationState = task.getDelegationState();
	            switch (delegationState) {
	            case PENDING:
	                taskService.resolveTask(taskId);
	                taskService.complete(taskId, args);
	                break;

	            default:
	                taskService.complete(taskId, args);
	                break;
	            }
	        } else {
	            taskService.complete(taskId, args);
	        }
	        updateEcmauditWorkItem(taskId, result, message);
	        return "processed ok!";
	    }

		/**
		 * @param taskId
		 * @param result
		 * @param message
		 */
		private void updateEcmauditWorkItem(String taskId, String result, String message) {
			updateEcmauditWorkItem(taskId, result, message,"","");
		}
		/**
		 * @param taskId
		 * @param result
		 * @param message
		 * @param formId
		 * @param docId
		 */
		private void updateEcmauditWorkItem(String taskId, String result, String message,String formId,String docId) {
			EcmAuditWorkitem  audit =	new EcmAuditWorkitem();
	        HistoricTaskInstance  task= historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			String auditId=ecmAuditWorkitemMapper.selectByCondition("TASK_ID='"+taskId+"' and ASSIGNEE='"+task.getAssignee()+"' and END_TIME is null").get(0).getId();
			audit.setId(auditId);
			audit.setCreateTime(task.getCreateTime());
			audit.setEndTime(task.getEndTime());
			audit.setDocId("");
			audit.setFormId("");
			audit.setTaskName(task.getName());
			audit.setAssignee(task.getAssignee());
			audit.setResult(result);
			audit.setMessage(message);
			audit.setProcessInstanceId(task.getProcessInstanceId());
			audit.setTaskId(taskId);
			ecmAuditWorkitemMapper.updateByPrimaryKey(audit);
		}
		/**
		 * @param taskId
		 * @param result
		 * @param message
		 */
		private void newEcmauditWorkItem(String taskId, String result, String message) {
			newEcmauditWorkItem(taskId, result, message,"","");
		}
		/**
		 * @param taskId
		 * @param result
		 * @param message
		 * @param formId
		 * @param docId
		 */
		private void newEcmauditWorkItem(String taskId, String result, String message,String formId,String docId) {
			EcmAuditWorkitem  audit =	new EcmAuditWorkitem();
	        HistoricTaskInstance  task= historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
	        audit.createId();
			audit.setCreateTime(new Date());
			if(dailiString.equals(result)) {
				audit.setEndTime(new Date());
			}else {
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

		private void setTaskApprovalResult(String taskId, String result, String message) {
			if(!"".equals(message)) {
		        taskService.setVariableLocal(taskId, "message",message);
	        }

	        //通过审核
	        taskService.setVariableLocal(taskId, "outcome",result);
		}


	    /**
	     * 代理任务
	     *
	     * @param taskId 任务ID
	     * userId 接收代理人员ID
	     */
	    @RequestMapping(value = "delegateTask")
	    @ResponseBody
	    public String delegateTask(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String taskId= args.get("taskId").toString();
			String delegateTaskUserId= args.get("delegateTaskUserId").toString();
			String result= dailiString;
			String message= args.get("message").toString();
	        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	        if (StringUtils.isEmpty(task.getOwner())) {
    			taskService.setOwner(taskId, task.getAssignee());
				taskService.delegateTask(taskId, delegateTaskUserId);
	        } else {
				taskService.delegateTask(taskId, delegateTaskUserId);
	        }
			newEcmauditWorkItem(taskId, result, message);
	        return "processed ok!";
	    }

 
	    /**
	     * 生成流程图
	     *
	     * @param processId 任务ID
	     */
	    @RequestMapping(value = "processDiagram")
	    @ResponseBody
	    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
	        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

	        //流程走完的不显示图
	        if (pi == null) {
	            return;
	        }
	        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
	        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
	        String InstanceId = task.getProcessInstanceId();
	        List<Execution> executions = runtimeService
	                .createExecutionQuery()
	                .processInstanceId(InstanceId)
	                .list();

	        //得到正在执行的Activity的Id
	        List<String> activityIds = new ArrayList<>();
	        List<String> flows = new ArrayList<>();
	        for (Execution exe : executions) {
	            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
	            activityIds.addAll(ids);
	        }

	        //获取流程图
	        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
	        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
	        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
	        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, false);
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
	
	    
	    /**
	     * 测试流程
	     *
	     * @param argStr
	     */
	    @RequestMapping(value = "testWorkflow")
	    @ResponseBody
	    public String testWorkflow(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String senseNumber=args.get("formId").toString();
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
			
 
	        //通过审核
 	        return "processed ok!";
	    }
	    
	    
	    /**
	     * 启动场景
	     *
	     * @param argStr
	     */
	    @RequestMapping(value = "startSensen1")
	    @ResponseBody
	    public String startSensen1(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			startWorkflow(argStr);
			
			//完成完成
			int tasknumber = 0;
			int i=30;
			do {
				i--;
				args.put("userId", "Admin");
				args.put("condition", "");
				args.put("pageSize", "10");
				args.put("pageIndex", "0");
 				HashMap<String,Object> tasks=todoTask(JSONUtils.mapToJson(args));
					tasknumber= ((List)tasks.get("data")).size()-1;
					if(tasknumber>=0) {
						String taskId= ((Map)((ArrayList)tasks.get("data")).get(0)).get("id").toString();
						args.put("taskId", taskId);
						args.put("result", "通过");
						args.put("message", args.get("message"));
 						completeTask(JSONUtils.mapToJson(args));
					}
				} while (i>0);
 
	        //通过审核
 	        return "processed ok!";
	    }
	        
	    /**
	     * 
	     * 从flowable获取流程审批信息
	     * @param processInstanceId
	     */
	    @ResponseBody
	    @RequestMapping(value = "getWorkflowTask0")
	    public Map<String, Object>  getWorkflowTask0(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> mp =  new HashMap();
				String processInstanceId=args.get("processInstanceId").toString();
				List<Map> resultList = new ArrayList<Map>();
				String isPocessFinished="0";
	        try {
				List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().asc().list();
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
		         HistoricProcessInstance   hiProcessInstance  = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).unfinished().singleResult();
		           if(hiProcessInstance==null) {
		        	   isPocessFinished="1";
		           }
 			} catch (Exception e) {
				e.printStackTrace();
			}
	        mp.put("data", resultList);
	        mp.put("isPocessFinished", isPocessFinished);

         	return  mp;
	    }
	    
	    /**
	     * 
	     * 从EcmAudit获取流程审批信息
	     * @param processInstanceId
	     */
	    @ResponseBody
	    @RequestMapping(value = "getWorkflowTask")
	    public Map<String, Object>  getWorkflowTask(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> mp =  new HashMap<String, Object>();
				String processInstanceId=args.get("processInstanceId").toString();
				List<Map> resultList = new ArrayList<Map>();
				String isPocessFinished="0";
	        try {
	        	List<EcmAuditWorkitem>  tasks=ecmAuditWorkitemMapper.selectByCondition("PROCESS_INSTANCE_ID='"+processInstanceId+"' order by CREATE_TIME desc");
 		        for (EcmAuditWorkitem task : tasks) {
			        HashMap<String, Object> map = new HashMap<>();
			        map.put("id", task.getTaskId());
			        map.put("name", task.getTaskName());
			        map.put("assignee", task.getAssignee());
			        map.put("result", task.getResult());					
			        map.put("message", task.getMessage());					
			        map.put("createTime", task.getCreateTime());
			        map.put("endTime", task.getEndTime());
			        map.put("processInstanceId", task.getProcessInstanceId());
 			        resultList.add(map);
		        }

				HistoricProcessInstance   hiProcessInstance  = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).unfinished().singleResult();
		           if(hiProcessInstance==null) {
		        	   isPocessFinished="1";
		           }
 			} catch (Exception e) {
				e.printStackTrace();
			}
	        mp.put("data", resultList);
	        mp.put("isPocessFinished", isPocessFinished);

         	return  mp;
	    }
	    
	    /**
	     * 待办任务数量
	     * @param LoginName
	     */
	    @ResponseBody
	    @RequestMapping(value = "getMyAllTodoCount")
	    public long  getMyAllTodoCount(@RequestParam String LoginName) {
	    	//根据登录名获取用户名
	    	EcmUser user = userService.getObjectByLoginName(null, LoginName);
	    	if(user !=null) {
	    		LoginName = user.getName();
	    	}
	        return  taskService.createTaskQuery().taskAssignee(LoginName).count() ;

	    }
	    
}
