package com.ecm.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.core.env.Environment;

import com.ecm.core.service.MailService;

@Service
public class ServiceDocMail {
	private static final Logger logger = LoggerFactory.getLogger(ServiceDocMail.class);
	@Autowired
    private MailService mailService;
	@Autowired
	private SpringTemplateEngine templateEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private Environment env;
	
	public boolean sendEndMail(String sendUser) throws Exception {

		try {
			   //logger.debug("发送邮件开始!");
			  
			 //创建邮件正文
			   Map<String,Object> map=new HashMap<String,Object>();
//			   map.put("id", row.get("ID").toString());
//			   map.put("userName", row.get("C_DRAFTER").toString());
//			   map.put("account", "163.com");
//			   map.put("name",row.get("C_DRAFTER").toString());
//			   map.put("accountType", "Email");
			   map.put("workflowName", "借阅流程");
			   
			   Context context = new Context();
			   context.setVariables(map);
			   /* String emailContent = templateEngine.process("taskArrivalMail", context);*/
			    String emailContent = templateEngine.process("processend", context);
			    mailService.sendHtmlMail(sendUser,"主题：借阅流程结束通知",emailContent);/**/
			    //logger.debug("发送完成");
			} catch (Exception e) {
				e.printStackTrace();
				 throw new Exception("发送邮件测试发生异常！");
			}
	
		return false;
		
	}
	
	/**
	 * Matthew change on 2020年12月15日09:34:00
	  *  单个人员邮件发送
	 * @param sendUser
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public boolean sendTaskMail(String sendUser,Task task) throws Exception {

		try {
			   logger.debug("发送邮件测试!");
			  
			 //创建邮件正文
			   Map<String,Object> map=new HashMap<String,Object>();
			   String todoTaskUrl = env.getProperty("workflow.todoTaskUrl");
			   StringBuilder todoUrl = new StringBuilder(todoTaskUrl);
			   StringBuilder subject = new StringBuilder("档案管理系统");
			   todoUrl.append("?openTaskFromMainPage=1&taskId=");
			   todoUrl.append(task.getId());
//			   map.put("id", row.get("ID").toString());
//			   map.put("userName", row.get("C_DRAFTER").toString());
//			   map.put("account", "163.com");
//			   map.put("name",row.get("C_DRAFTER").toString());
//			   map.put("accountType", "Email");
			   String workflowName= repositoryService.getProcessDefinition(task.getProcessDefinitionId()).getName();
//			   map.put("workflowName", "借阅流程");
			   map.put("workflowName", workflowName);
			   map.put("taskName", task.getName());
			   map.put("todoUrl", todoUrl.toString());
			   Context context = new Context();
			   context.setVariables(map);
			   /* String emailContent = templateEngine.process("taskArrivalMail", context);*/
			    String emailContent = templateEngine.process("taskArrive", context);
//			    mailService.sendHtmlMail(sendUser,"主题：借阅流程待审批",emailContent);/**/
				subject.append(workflowName);
				subject.append(task.getName());
				subject.append("任务处理");
			    mailService.sendHtmlMail(sendUser,"主题："+subject.toString(),emailContent);
			    
			    logger.debug("发送完成");
			} catch (Exception e) {
				e.printStackTrace();
				 throw new Exception("发送邮件测试发生异常！");
			}
	
		return false;
		
	}
	
	/**
	 * Matthew change on 2020年12月15日09:34:00
	  *  多个人员邮件发送
	 * @param sendUser
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public boolean sendTaskMailMultipleUsers(List<String> sendUsers,Task task) throws Exception {

		try {
			   logger.debug("发送邮件测试!");
			  
			 //创建邮件正文
			   Map<String,Object> map=new HashMap<String,Object>();
			   String todoTaskUrl = env.getProperty("workflow.todoTaskUrl");
			   StringBuilder todoUrl = new StringBuilder(todoTaskUrl);
			   StringBuilder subject = new StringBuilder("档案管理系统");
			   todoUrl.append("?openTaskFromMainPage=1&taskId=");
			   todoUrl.append(task.getId());
			   String workflowName= repositoryService.getProcessDefinition(task.getProcessDefinitionId()).getName();
			   map.put("workflowName", workflowName);
			   map.put("taskName", task.getName());
			   map.put("todoUrl", todoUrl.toString());
			   Context context = new Context();
			   context.setVariables(map);
			    String emailContent = templateEngine.process("taskArrive", context);
				subject.append(workflowName);
				subject.append(task.getName());
				subject.append("任务处理");
			    mailService.sendHtmlMailMultipleUsers(sendUsers,"主题："+subject.toString(),emailContent);
			    logger.debug("发送完成");
			} catch (Exception e) {
				e.printStackTrace();
				 throw new Exception("发送邮件测试发生异常！");
			}
	
		return false;
		
	}
	
}
