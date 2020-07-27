package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.entity.ProjectViewEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
@RequestMapping("/exchange/project")
public class ProjectView extends ControllerAbstract  {
	@Autowired
	private ProjectService service;
	
	@PostMapping("getProjectView")
	@ResponseBody
	public Map<String,Object> getProjectView(){
		Map<String, Object> result = new HashMap<String,Object>();
		List<ProjectViewEntity> treeData=new ArrayList<ProjectViewEntity>();
		List<ProjectEntity> rlist = null;
		try {
			rlist = service.getMyProject(this.getToken());
			for(ProjectEntity p : rlist) {
				ProjectViewEntity node=new ProjectViewEntity();
				node.setName(p.getName());
				node.setCode(p.getCode());
				ProjectViewEntity childPlan=new ProjectViewEntity();
				childPlan.setName("计划");
				childPlan.setCode("计划");
				node.addChild(childPlan);
				ProjectViewEntity childIED=new ProjectViewEntity();
				childIED.setName("IED");
				childIED.setCode("IED");
				node.addChild(childIED);
				ProjectViewEntity childICM=new ProjectViewEntity();
				childICM.setName("ICM");
				childICM.setCode("ICM");
				node.addChild(childICM);
				ProjectViewEntity childDC=new ProjectViewEntity();
				childDC.setName("文函");
				String params = CacheManagerOper.getEcmParameters().get("DCTypeConfig").getValue();
				
				if(params!=null) {
					String[] vals=params.split(",");
					childDC.setChildrenValue(vals);
				}
				node.addChild(childDC);
				ProjectViewEntity childDesign=new ProjectViewEntity();
				childDesign.setName("设计文件");
				childDesign.setCode("设计文件");
				node.addChild(childDesign);
				treeData.add(node);
			}
			result.put("code", ActionContext.SUCESS);
			result.put("data", treeData);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			result.put("code", ActionContext.SYSTEM_ERROR);
		}		
		
		return result;
	}
	
}