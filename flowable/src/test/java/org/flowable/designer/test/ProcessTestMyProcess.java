package org.flowable.designer.test;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentProperties;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.test.FlowableRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ecm.flowable.controller.ExpenseController;
@Configurable
@Configuration
@ComponentScan({"com.ecm.flowable.*"})
 public class ProcessTestMyProcess {

	private String filename = "C:\\Workfolder\\zisecm\\flowable\\src\\main\\resources\\diagrams\\借阅流程.bpmn";
 
//	@Test
//	public void startProcess() throws Exception {
//		
//		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ProcessTestMyProcess.class);
////		ExpenseController econtroller= (ExpenseController)context.getBean("expenseController");
////		econtroller.deploymentProcessExpense();
//		context.refresh();
//		
//		RuntimeService runtimeService= (RuntimeService)context.getBean(RuntimeService.class);
//		
// 		Map<String, Object> variableMap = new HashMap<String, Object>();
//		variableMap.put("name", "Activiti");
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
//		assertNotNull(processInstance.getId());
//		System.out.println("id " + processInstance.getId() + " "
//				+ processInstance.getProcessDefinitionId());
//	}
//	
	
 	@Rule
	public FlowableRule activitiRule = new FlowableRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("借阅流程.bpmn", new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_borrow", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("ProcessId=============================== " + processInstance.getId() + "  ProcessDefinitionId=============================== "
				+ processInstance.getProcessDefinitionId());
	}
}