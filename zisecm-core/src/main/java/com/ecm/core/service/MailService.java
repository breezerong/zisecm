package com.ecm.core.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import io.renren.modules.sys.service.MailService;
@Service("mailService")
public class MailService {

	private final Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
    private JavaMailSender mailSender;
	
    @Value("${spring.mail.username}")
    private String from;


	public void sendHtmlMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();
	    try {
	        //true表示需要创建一个multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
	        helper.setFrom(from);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);
			
	        mailSender.send(message);
	        logger.info("一份html邮件已成功");
	    } catch (MessagingException e) {
	        logger.error("发送html邮件时发生异常！", e);
	    }

	}
   
	public void sendHtmlMailMultipleUsers(List<String> to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();
	    try {
	        //true表示需要创建一个multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
	        helper.setFrom(from);
	        helper.setTo(to.toArray(new String[to.size()]));
//	        String[] mails = new String[to.size()];
//	        to.toArray(mails);
//	        helper.setTo(mails);
	        helper.setSubject(subject);
	        helper.setText(content, true);
			
	        mailSender.send(message);
	        logger.info("一份html邮件已成功");
	    } catch (MessagingException e) {
	        logger.error("发送html邮件时发生异常！", e);
	    }

	}
	
	
}
