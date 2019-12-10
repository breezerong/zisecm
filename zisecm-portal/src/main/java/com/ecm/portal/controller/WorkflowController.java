package com.ecm.portal.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.bpm.ProcessService;
import com.ecm.core.bpm.WorkflowAuditService;
import com.ecm.core.bpm.WorkflowService;
import com.ecm.core.bpm.WorkitemAuditService;
import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmAuditWorkitemMapper;
import com.ecm.core.dao.EcmWorkflowMapper;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmAuditWorkitem;
import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.AuditService;
import com.ecm.flowable.listener.JobListener;
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
		private WorkflowAuditService workflowAuditService;
		@Autowired
		private  WorkitemAuditService  workitemAuditService;
		@Autowired
		private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
		@Autowired
		private EcmAuditWorkitemMapper ecmAuditWorkitemMapper;
		@Autowired
		private AuditService auditService;
		
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
 			        runtimeService.setProcessInstanceName(processInstance.getId(),  "借阅流程 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  			        //创建流程日志
					EcmAuditWorkflow audit = new EcmAuditWorkflow();
					audit.createId();
					audit.setProcessInstanceId(processInstance.getId());
					audit.setProcessName(processInstance.getProcessDefinitionName());
					audit.setProcessInstanceName(processInstance.getName());
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
	        for (HistoricTaskInstance task : tasks) {
		        HashMap<String, Object> map = new HashMap<>();
		        map.put("id", task.getId());
		        map.put("processInstanceId", task.getProcessInstanceId());
		        map.put("name", task.getName());
		        map.put("startUser", historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getStartUserId());
		        map.put("createTime", task.getCreateTime());
		        map.put("endTime", task.getEndTime());
		        getTaskApprovalResult(task.getId(), map);

//		        List<Comment>  commentsList=  taskService.getTaskComments(task.getId());
//		        String taskComments="";
//		        for (int i = 0; i < commentsList.size(); i++) {
//	        	
//		        	taskComments=taskComments+commentsList.get(0).getFullMessage()+"; ";
//				}
 		        resultList.add(map);
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
			String condition= args.get("condition").toString();
			int pageSize= Integer.parseInt(args.get("pageSize").toString()) ;
			int pageIndex= Integer.parseInt(args.get("pageIndex").toString());
 
			List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().listPage(pageIndex, pageSize);
	        List<HashMap> resultList = new ArrayList<HashMap>();
	        for (Task task : tasks) {
		        HashMap<String, Object> map = new HashMap<>();
		        map.put("id", task.getId());
		        map.put("name", task.getName());
		        map.put("startUser", runtimeService.getVariable(task.getProcessInstanceId(), "startUser"));
		        map.put("formId", runtimeService.getVariable(task.getProcessInstanceId(), "formId"));
		        map.put("createTime", task.getCreateTime());
		        map.put("processInstanceId", task.getProcessInstanceId());
		        
		        resultList.add(map);
	        }
	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        resultMap.put("totalCount", taskService.createTaskQuery().taskAssignee(userId).count());

	        return resultMap;
	    }

	    /**
	     * 获取我发起的流程
	     */
	    @RequestMapping(value = "myWorkflow")
	    @ResponseBody
	    public HashMap<String,Object> myWorkflow(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String userId= args.get("userId").toString();
			String condition= args.get("condition").toString();
			int pageSize= Integer.parseInt(args.get("pageSize").toString()) ;
			int pageIndex= Integer.parseInt(args.get("pageIndex").toString());
	    	List<HistoricProcessInstance> processes =  historyService.createHistoricProcessInstanceQuery().startedBy(userId).orderByProcessInstanceStartTime().desc().listPage(pageIndex,pageSize);

 	        List<HashMap> resultList = new ArrayList<HashMap>();
	        for (HistoricProcessInstance process : processes) {
		        HashMap<String, Object> map = new HashMap<>();
		        map.put("id", process.getId());
		        map.put("name", process.getName());
		        map.put("startUser", userId);
		        map.put("startTime", process.getStartTime());
		        map.put("endTime", process.getEndTime()==null?"":process.getEndTime());
		        String currentAssignee="";
		        String currentTaskName="";
		        if(process.getEndTime()==null) {
		        	List<HistoricTaskInstance>  tasks= historyService.createHistoricTaskInstanceQuery().processInstanceId( process.getId()).orderByHistoricTaskInstanceStartTime().desc().list();

			        if(tasks.size()>0)
			        {
			        	currentAssignee=tasks.get(0).getAssignee();
			        	currentTaskName=tasks.get(0).getName();
			        }
			        map.put("currentTaskName", currentTaskName);
			        map.put("currentAssignee",  currentTaskName+":"+currentAssignee);
			    }else {		        
			        map.put("currentTaskName", "已结束");
			        map.put("currentAssignee",  "已结束");
			    }
		        resultList.add(map);
	        }
	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        resultMap.put("totalCount", historyService.createHistoricProcessInstanceQuery().startedBy(userId).count());
	        return resultMap;
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
//	        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//	        if (task == null) {
//	            throw new RuntimeException("流程不存在");
//	        }
	        setTaskApprovalResult(taskId, result, message);
	        taskService.complete(taskId, args);
	        EcmAuditWorkitem  audit =	new EcmAuditWorkitem();
	        HistoricTaskInstance  task= historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			audit.createId();
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
			ecmAuditWorkitemMapper.insert(audit);
	        return "processed ok!";
	    }

		private void setTaskApprovalResult(String taskId, String result, String message) {
			if(!"".equals(message)) {
		        taskService.setVariableLocal(taskId, "message",message);
	        }

	        //通过审核
	        taskService.setVariableLocal(taskId, "outcome",result);
		}


	    /**
	     * 生成流程图
	     *
	     * @param processId 任务ID
	     */
	    @RequestMapping(value = "processDiagram")
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
	     * 获取流程审批信息
	     * @param processInstanceId
	     */
	    @ResponseBody
	    @RequestMapping(value = "getWorkflowTask")
	    public List<EcmAuditWorkitem>  getWorkflowTask(@RequestBody String argStr) {
				Map<String, Object> args = JSONUtils.stringToMap(argStr);
				String processInstanceId=args.get("processInstanceId").toString();
				List<Map> resultList = new ArrayList<Map>();
	        EcmAuditWorkitem  audit =	new EcmAuditWorkitem();
	        try {
				return workitemAuditService.getObjects(getToken(), "process_instance_id='"+processInstanceId+"'");
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			}
         	return  new ArrayList<EcmAuditWorkitem>();
	    }
	    
	    /**
	     * 待办任务数量
	     * @param processInstanceId
	     */
	    @ResponseBody
	    @RequestMapping(value = "getMyAllTodoCount")
	    public List<EcmAuditWorkitem>  getMyAllTodoCount(@RequestBody String argStr) {
				Map<String, Object> args = JSONUtils.stringToMap(argStr);
				String processInstanceId=args.get("processInstanceId").toString();
				List<Map> resultList = new ArrayList<Map>();
	        EcmAuditWorkitem  audit =	new EcmAuditWorkitem();
	        try {
				return workitemAuditService.getObjects(getToken(), "workflow_id='"+processInstanceId+"'");
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			}
         	return  new ArrayList<EcmAuditWorkitem>();
	    }
	    
}
