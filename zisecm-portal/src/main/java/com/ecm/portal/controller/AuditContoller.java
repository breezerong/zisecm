package com.ecm.portal.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.bpm.ProcessEngine;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.service.AuditService;
import com.ecm.icore.bpm.IProcessEngine;

@Controller
public class AuditContoller extends ControllerAbstract {

	@Autowired
	protected AuditService auidtService;
	@Autowired
	private ProcessEngine processEngine;
	
	@RequestMapping("/addAudit")
	@ResponseBody
	public String saveAudit() {
		EcmAuditGeneral en = new EcmAuditGeneral();
		en.setActionName("ecm_save");
		en.setUserName("admin");
		en.createId();
		en.setExcuteDate(new Date());
		//auidtService.newObject(getToken(),en);
		return "OK";
	}
	
	@RequestMapping("/auditTest")
	@ResponseBody
	public String auditTest() {
		String id ="";
		return id;
	}
}
