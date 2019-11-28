package com.ecm.flowable.service;

import com.ecm.flowable.listener.ApproveTaskAssignmentListener;
import com.ecm.flowable.listener.ApproveTaskDeleteListener;
import com.ecm.flowable.listener.JobListener;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangRui
 * @Description:
 * @date: Created in 9:56 2018/4/3
 * @Modified By:
 */
@Service
public class ActivitiService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    public TaskService taskService;
    @Autowired
    HistoryService historyService;

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private IdentityService identityService;

    public List<Map<String,Object>> searchProcess() {

        List<Task> tasks = taskService.createTaskQuery().list();
        List<Map<String,Object>> list = new ArrayList<>();

        for (Task task : tasks) {
            Map<String, Object> map = new HashMap<>();
            System.out.println("流程实例ID"+task.getProcessInstanceId());
            System.out.println("taskId"+task.getId());
            map.put("taskId", task.getId());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("taskName", task.getName());

            list.add(map);
        }
        return list;
    }
    public boolean commitProcess(String duration) {

        //提交申请借阅 duration借阅期限 例如10秒后job执行 duration = PT10S
        List<Task> tasks = taskService.createTaskQuery().list();
        tasks.forEach(task -> {
            Map<String, Object> variables = task.getProcessVariables();
            variables.put("duration", duration);
            variables.put("approveusers", "");
            taskService.complete(task.getId(), variables);
        });

        return true;
    }
    public boolean approveProcess(boolean approved) {

        List<Task> tasks = taskService.createTaskQuery().list();
        tasks.forEach(task -> {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            if (approved) {
                processVariables.put("Task_review", "Task_return");
                processVariables.put("isReturn", false);
            } else {
                processVariables.put("Task_review", "Task_refuse");
            }

            taskService.complete(task.getId(), processVariables);
        });


        return true;
    }
    public boolean returnProcess() {

        List<Task> tasks = taskService.createTaskQuery().list();
        tasks.forEach(task -> {
            Map<String, Object> processVariables = taskService.getVariables(task.getId());
            processVariables.put("isReturn", true);
            taskService.complete(task.getId(),processVariables);
        });

        return true;
    }

    /**
     * 部署流程
     */
    public void deploymentProcess() {

        //流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/jobtest.bpmn20.xml")
                .name("flowable")
                .deploy();
        //增加事件监听
        runtimeService.addEventListener(new JobListener());
    }

    /**
     * 启动流程
     */
    public void startProcess() {
        //获取流程定义
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .latestVersion()
                .singleResult();

        Map<String, Object> variables = new HashMap<>();
        variables.put("delegateExpression", new ApproveTaskAssignmentListener());
        variables.put("expression", new ApproveTaskDeleteListener());
        // 设定实例发起者
        identityService.setAuthenticatedUserId("Initiator");
        //发起流程
        processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(),variables);
        identityService.setAuthenticatedUserId(null);

    }

}
