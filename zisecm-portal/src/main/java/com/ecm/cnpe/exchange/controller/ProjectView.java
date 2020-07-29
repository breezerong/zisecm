package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.entity.ProjectEntity;
import com.ecm.cnpe.exchange.entity.ProjectViewEntity;
import com.ecm.cnpe.exchange.service.ProjectService;
import com.ecm.cnpe.exchange.service.ProjectViewService;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
@RequestMapping("/exchange/project")
public class ProjectView extends ControllerAbstract  {
	@Autowired
	private ProjectService service;
	@Autowired
	private ProjectViewService documentService;
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
	@RequestMapping(value = "getDesignAtProjectView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDocumentByRelationParentId(@RequestBody String argStr) {
		
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String conditionWhere="";
			if(args.get("condition")!=null) {
				conditionWhere=args.get("condition").toString();
			}
			List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
			
			list = documentService.getDesignInProject(getToken(), conditionWhere,pager);
			
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
		
	}
}