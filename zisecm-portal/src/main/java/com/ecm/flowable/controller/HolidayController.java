package com.ecm.flowable.controller;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.flowable.listener.JobListener;

@RestController
@RequestMapping(value = "holiday")
public class HolidayController {
	 @Autowired
	    private RuntimeService runtimeService;
	    @Autowired
	    private TaskService taskService;
	    @Autowired
	    private RepositoryService repositoryService;
	    @Autowired
	    private ProcessEngine processEngine;
	 
	    /***************此处为业务代码******************/
	    
	    
	    /**
	     * 部署流程
	     */
	    @RequestMapping(value = "/deploymentProcessHoliday",method = RequestMethod.GET)
	    @ResponseBody
	    public void deploymentProcessHoliday() {
	        //流程部署
	    	RepositoryService repositoryService = processEngine.getRepositoryService();
	    	Deployment deployment = repositoryService.createDeployment()
	    	.addClasspathResource("holiday-request.bpmn20.xml")
	    	.deploy();
	        //增加事件监听
	        runtimeService.addEventListener(new JobListener());
	        
	        //查询流程定义
	        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
	    			.deploymentId(deployment.getId())
	    			.singleResult();
	    			System.out.println("Found process definition : " + processDefinition.getName());
	    }
	    
	    
	    
	    /**
	     * 添加报销
	     *
	     * @param userId    用户Id
	     * @param nrOfHolidays     多少假期
	     * @param descption 描述
	     */
	    @RequestMapping(value = "add")
	    @ResponseBody
	    public String addHoliday(String userId, Integer nrOfHolidays, String description) {
	        //启动流程
	        HashMap<String, Object> variables = new HashMap<String, Object>();
	        variables.put("employee", userId);
	        variables.put("nrOfHolidays", nrOfHolidays);
	        variables.put("description", description);
	        ProcessInstance processInstance =  runtimeService.startProcessInstanceByKey("holidayRequest", variables);
	        return "提交成功.流程Id为：" + processInstance.getId();
	    }
	  
	    /**
	     * 查询只返回管理员组
	     * 
	     */
	    @RequestMapping(value = "taskQuery")
	    @ResponseBody
	    public Object taskQuery() {
	        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
	        System.out.println("You have " + tasks.size() + " tasks:");
	        for (int i=0; i<tasks.size(); i++) {
	        	System.out.println((i+1) + ") " + tasks.get(i).getName());
	        }
	        return tasks.toArray().toString();
	    }
	    
	    
	    /**
	     * 完成任务
	     *
	     * @param taskId 任务ID
	     */
	    @RequestMapping(value = "apply")
	    @ResponseBody
	    public String apply(String taskId ,String apply) {
	        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	    	 HashMap<String, Object> variables = new HashMap<>();
	    	if(apply.equals("y")) {
	 	        variables.put("approved", true);
	    	}else {
	    		 variables.put("approved", false);
	    	}
	        taskService.complete(task.getId(), variables);
	        
	        return "processed ok!";
	    }

}
