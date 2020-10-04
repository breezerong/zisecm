package com.ecm.portal.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ecm.core.service.MailService;

@Service
public class ServiceDocMail {
	private static final Logger logger = LoggerFactory.getLogger(ServiceDocMail.class);
	@Autowired
    private MailService mailService;
	@Autowired
	private SpringTemplateEngine templateEngine;
	
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
	
	public boolean sendTaskMail(String sendUser) throws Exception {

		try {
			   logger.debug("发送邮件测试!");
			  
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
			    String emailContent = templateEngine.process("taskArrive", context);
			    mailService.sendHtmlMail(sendUser,"主题：借阅流程待审批",emailContent);/**/
			    logger.debug("发送完成");
			} catch (Exception e) {
				e.printStackTrace();
				 throw new Exception("发送邮件测试发生异常！");
			}
	
		return false;
		
	}
}
