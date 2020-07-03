package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.core.ActionContext;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
@RequestMapping("/exchange/project")
public class ProjectManager extends ControllerAbstract  {
	@Autowired
	private ProjectService service;
	
	@PostMapping("myproject")
	@ResponseBody
	public Map<String,Object> getMyProject(){
		Map<String, Object> result = new HashMap<String,Object>();
		List<ProjectEntity> rlist = null;
		try {
			rlist = service.getMyProject(this.getToken());
			result.put("code", ActionContext.SUCESS);
			result.put("data", rlist);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			result.put("code", ActionContext.SYSTEM_ERROR);
		}		
		
		return result;
	}
	
}