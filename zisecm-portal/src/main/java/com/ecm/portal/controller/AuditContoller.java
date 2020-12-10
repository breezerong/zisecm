package com.ecm.portal.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
//import com.ecm.core.bpm.ProcessEngineExt;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.entity.EcmSystemEvent;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.AuditService;

@Controller
public class AuditContoller extends ControllerAbstract {

	private static final Logger logger = LoggerFactory.getLogger(AuditContoller.class);
	
	@Autowired
	protected AuditService auidtService;
	
//	@Autowired
//	private ProcessEngineExt processEngine;
	
	@RequestMapping("/audit/addAudit")
	@ResponseBody
	public String saveAudit(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String docId = args.get("docId").toString();
		String actionName = args.get("actionName").toString();
		String appName = args.get("appName").toString();
		EcmSystemEvent ecmSystemEvent = CacheManagerOper.getEcmSystemEvents().get(actionName);
		EcmAuditGeneral en = new EcmAuditGeneral();
		if (!ecmSystemEvent.getEnabled()) {
			logger.info(ecmSystemEvent.getName()+"已经关闭，无法记录日志");
			return "fail";
		}else {
			try {
				LoginUser currentUser  = this.getSession().getCurrentUser();
				en.setActionName(ecmSystemEvent.getName());
				en.setUserName(currentUser.getUserName());
				en.setUserId(currentUser.getUserId());
				en.setDocId(docId);
				en.setAppName(appName);
				en.createId();
				en.setExcuteDate(new Date());
				auidtService.newObject(getToken(),en);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}
		}
	}
	
	@RequestMapping("/auditTest")
	@ResponseBody
	public String auditTest() {
		String id ="";
		return id;
	}
}
