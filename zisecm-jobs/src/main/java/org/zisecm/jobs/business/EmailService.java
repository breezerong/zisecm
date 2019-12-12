package org.zisecm.jobs.business;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.zisecm.jobs.core.Ijobs;

import com.ecm.core.service.MailService;
@Service
public class EmailService implements Ijobs {
	/**
     * 邮件发送测试类
     */
    @Autowired
    private MailService mailService;
    
    @Autowired
    private SpringTemplateEngine templateEngine;
	public void sendMail() throws Exception {
		try {
			   System.out.println("发送邮件测试!");
			  
			 //创建邮件正文
			   Map<String,Object> map=new HashMap<String,Object>();
			   map.put("id", "006");
			   map.put("userName", "吴先生");
			   map.put("account", "163.com");
			   map.put("name", "张三");
			   map.put("accountType", "Email");
			   
			   
			   Context context = new Context();
			   context.setVariables(map);
			   /* String emailContent = templateEngine.process("taskArrivalMail", context);*/
			    String emailContent = templateEngine.process("electronicLending", context);
			    mailService.sendHtmlMail("277919136@qq.com","主题：这是模板邮件",emailContent);/**/
			    System.out.println("发送完成");
			} catch (Exception e) {
				e.printStackTrace();
				 throw new Exception("发送邮件测试发生异常！");
			}
	}
	/**
	 * 启动服务
	 * @throws Exception 
	 */
	@Scheduled(cron = "0/10 * * * * *")
	public synchronized void runJobs() throws Exception {
		sendMail();
	}
	
	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		sendMail();
	}
}
