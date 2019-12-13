package org.zisecm.jobs.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.zisecm.jobs.core.Ijobs;

import com.ecm.common.util.PasswordUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.MailService;
@Service
public class EmailService implements Ijobs {
	private String token;
	/**
     * 邮件发送测试类
     */
    @Autowired
    private MailService mailService;
    @Autowired
    private DocumentService documentService;
    
    @Autowired
	private AuthService authService;
    
    @Autowired
    private SpringTemplateEngine templateEngine;
	public void sendMail(List<Map<String,Object>> row) throws Exception {
		try {
			   System.out.println("发送邮件测试!");
			  
			 //创建邮件正文
			   Map<String,Object> map=new HashMap<String,Object>();
//			   map.put("id", row.get("ID").toString());
//			   map.put("userName", row.get("C_DRAFTER").toString());
//			   map.put("account", "163.com");
//			   map.put("name",row.get("C_DRAFTER").toString());
//			   map.put("accountType", "Email");
			   map.put("docList", row);
			   
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
	@Scheduled(cron = "0 0 14 * * * *")
	public synchronized void runJobs() throws Exception {
		
		String sql="select "+getDocumentAllColumns()
		+" from ecm_document where TYPE_NAME='借阅单'  and STATUS='待入库'  "
		+ " and date_add(date_format(now(),'%Y-%m-%d'),interval 5 day)>=C_END_DATE";
		List<Map<String,Object>> result= documentService.getMapList(getToken(), sql);
		if(result==null||result.size()<1) {
			return;
		}
		for(Map<String,Object> row : result) {
			String borrowDocsSql="select "+getDocumentAllColumns()+" from ecm_document a,"
					+ "ecm_relation b where a.id=b.child_id and b.name='irel_borrow' and"
					+ " b.parent_id='"+row.get("ID").toString()+"' and a.status='待入库'";
			List<Map<String,Object>> docs= documentService.getMapList(getToken(), borrowDocsSql);
			sendMail(docs);
		}
		
	}
	
	/**
	 * 获取文档所有列
	 * @return
	 */
	private String getDocumentAllColumns() {
		String cols = "";
		for(String attr :CacheManagerOper.getEcmAttributes().keySet()) {
			if(cols.length()==0) {
				cols = attr;
			}else {
				cols += ","+attr;
			}
		}
		if("".equals(cols)) {
			cols=" * ";
		}
		return cols;
	}
	
	public String getToken() {
		if(token==null || authService.login(token)==null) {
			String user = null;
			try {
				user = CacheManagerOper.getEcmParameters().get("IndexUser").getValue();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			}
			
			String password = CacheManagerOper.getEcmParameters().get("IndexPassword").getValue();
			if(!StringUtils.isEmpty(password)) {
				try {
					password = PasswordUtils.decodePassword(password);
				}
				catch(Exception ex) {
					
				}
			}
			try {
				token = authService.login(ActionContext.APP_INDEX_AGENT,user,password).getToken();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return token;
	}
	
	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
//		sendMail();
	}
}
