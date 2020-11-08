package com.ecm.portal.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.Artifact;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.service.ActionService;
import com.ecm.core.service.CfgActivityService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.log.LogAopAction;

/**
 * 工作流配置管理
 * @author Haihong Rong
 * Date:2020年4月13日 下午4:59:11
 */
@Controller
@RequestMapping(value = "/cfgworkflow")
public class CfgActivityManager extends ControllerAbstract{
	
	private final Logger logger = LoggerFactory.getLogger(CfgActivityManager.class);

	@Autowired
    private CfgActivityService cfgActivityService;

	@Autowired
	private RepositoryService repositoryService;


	/**
	 * 获取所有流程定义
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="processes",method = RequestMethod.GET)
	public Map<String, Object> getProcesses() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
			List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
			for(ProcessDefinition item: list) {
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("deploymentId", item.getDeploymentId());
				p.put("id", item.getId());
				p.put("name", item.getName());
				p.put("version", item.getVersion());
				p.put("description", item.getDescription());
				itemList.add(p);
			}
			mp.put("data", itemList);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		
		return mp;
	}

	/**
	 * 更新事件
	 * 
	 * @param obj 事件jason对象
	 * @return  
	 */
	@RequestMapping(value = "/activities/{processId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getActivities(@PathVariable("processId") String processId) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			org.flowable.bpmn.model.Process process = repositoryService.getBpmnModel(processId).getMainProcess();
	        Collection<FlowElement> list = process.getFlowElements();
	        List<String> acts = new ArrayList<String>();
	        acts.add("start");
	        for (FlowElement f : list) {
	        	//System.out.println(f.getClass().getName()+":"+f.getId()+": "+f.getName());
	            if (f instanceof UserTask) {
	            	UserTask act = (UserTask)f;
	            	acts.add(act.getName());
	            }
	        }
			mp.put("data", acts);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
	@RequestMapping(value = "/cfgActivities/{processId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCfgActivities(@PathVariable("processId") String processId) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmCfgActivity> list = cfgActivityService.getProcessCfgActivities(getToken(), processId);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
	
	@RequestMapping(value = "/cfgActivity/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCfgActivity(@PathVariable("id") String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmCfgActivity obj = cfgActivityService.getObjectById(getToken(), id);
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
	
	@RequestMapping(value = "/newCfgActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newCfgActivity(@RequestBody EcmCfgActivity obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			cfgActivityService.newObject(getToken(), obj);
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
	
	@RequestMapping(value = "/updateCfgActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCfgActivity(@RequestBody EcmCfgActivity obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			cfgActivityService.updateObject(getToken(), obj);
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
	@RequestMapping(value = "/cfgActivity/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteCfgActivity(@PathVariable("id") String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			cfgActivityService.deleteObjectById(getToken(), id);
			mp.put("data", id);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
	
	@RequestMapping(value = "/updateProcessId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProcessId(@RequestBody Map<String, String> obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			cfgActivityService.updateProcessId(getToken(), obj.get("name"),obj.get("id"));
			mp.put("data", obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}
}
