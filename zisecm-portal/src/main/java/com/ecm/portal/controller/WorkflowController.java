package com.ecm.portal.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
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
import com.ecm.core.dao.EcmAuditWorkflowMapper;
import com.ecm.core.dao.EcmWorkflowMapper;
import com.ecm.core.entity.EcmAuditWorkflow;
import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.flowable.listener.JobListener;
import com.ecm.portal.controller.ControllerAbstract;

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
		private EcmAuditWorkflowMapper ecmAuditWorkflowMapper;
	 
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
//	    	try {
//				deploymentProcessExpense();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
	        //启动流程
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> result = new HashMap<String, Object>();
			try {
		    	String userName =workflowAuditService.getSession(getToken()).getCurrentUser().getUserName();
		    		Authentication.setAuthenticatedUserId(userName);
 			        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_borrow", args);
 			        runtimeService.setProcessInstanceName(processInstance.getId(),  "借阅流程 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  			        //创建流程日志
 			       runtimeService.setVariable(processInstance.getId(), "startUser", userName);
 			       List hpis = historyService.createHistoricProcessInstanceQuery()
 			              .startedBy(userName).list();
					EcmAuditWorkflow audit = new EcmAuditWorkflow();
					audit.createId();
					audit.setWorkflowId(processInstance.getId());
					audit.setWorkflowName(processInstance.getName());
					audit.setProcessName(processInstance.getProcessDefinitionName());
					audit.setCreator(userName);
					audit.setStartDate(new Date());
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
	     * 获取userId已审批的任务
	     */
	    @RequestMapping(value = "doneTask")
	    @ResponseBody
	    public HashMap<String,Object> doneTask(String userId) {
	    	List<HistoricTaskInstance> tasks =  historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
 	        List<HashMap> resultList = new ArrayList<HashMap>();
	        for (HistoricTaskInstance task : tasks) {
		        HashMap<String, Object> map = new HashMap<>();
		        map.put("id", task.getId());
		        map.put("name", task.getName());
		        map.put("startUser", runtimeService.getVariable(task.getProcessInstanceId(), "startUser"));
		        map.put("createTime", task.getCreateTime());
		        map.put("endTime", task.getEndTime());
 		        resultList.add(map);
	            System.out.println(task.toString());
	        }
	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        return resultMap;
	    }

	    /**
	     * 获取userId待已审批的任务
	     */
	    @RequestMapping(value = "todoTask")
	    @ResponseBody
	    public HashMap<String,Object> todoTask(String userId) {
	        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
	        List<HashMap> resultList = new ArrayList<HashMap>();
	        for (Task task : tasks) {
		        HashMap<String, Object> map = new HashMap<>();
		        map.put("id", task.getId());
		        map.put("name", task.getName());
		        map.put("startUser", runtimeService.getVariable(task.getProcessInstanceId(), "startUser"));
		        map.put("createTime", task.getCreateTime());
		        resultList.add(map);
	            System.out.println(task.toString());
	        }
	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        return resultMap;
	    }

	    /**
	     * 获取我发起的流程
	     */
	    @RequestMapping(value = "myWorkflow")
	    @ResponseBody
	    public HashMap<String,Object> myWorkflow(String userId) {
	    	List<HistoricProcessInstance> tasks =  historyService.createHistoricProcessInstanceQuery().startedBy(userId).orderByProcessInstanceStartTime().desc().list();

 	        List<HashMap> resultList = new ArrayList<HashMap>();
	        for (HistoricProcessInstance task : tasks) {
		        HashMap<String, Object> map = new HashMap<>();
		        map.put("id", task.getId());
		        map.put("name", task.getName());
		        map.put("startUser", userId);
		        map.put("createTime", task.getStartTime());
		        map.put("endTime", task.getEndTime());
		        resultList.add(map);
	            System.out.println(task.toString());
	        }
	        HashMap<String,Object> resultMap = new HashMap<String,Object>();
	        resultMap.put("data",  resultList);
	        return resultMap;
	    }

	    /**
	     * 批准
	     *
	     * @param taskId 任务ID
	     */
	    @RequestMapping(value = "apply")
	    @ResponseBody
	    public String apply(String taskId) {
	        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	        if (task == null) {
	            throw new RuntimeException("流程不存在");
	        }
	        //通过审核
	        HashMap<String, Object> map = new HashMap<>();
	        if(1==1) {
		        map.put("outcome", "通过");
	        }else {
		        map.put("outcome", "驳回");

	        }
	        taskService.complete(taskId, map);
	        return "processed ok!";
	    }

	    /**
	     * 拒绝
	     */
	    @ResponseBody
	    @RequestMapping(value = "reject")
	    public String reject(String taskId) {
	        HashMap<String, Object> map = new HashMap<>();
	        map.put("outcome", "驳回");
	        taskService.complete(taskId, map);
	        return "reject";
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
	
	
}
