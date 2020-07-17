package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.entity.EcmUiRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.service.EcmUiRelationService;
import com.ecm.core.service.QueryService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
public class UiRelationManager extends ControllerAbstract{

	@Autowired
	private EcmUiRelationService service;
	
	@PostMapping("/admin/uirelation/list")
	@ResponseBody
	public Map<String, Object> list(@RequestBody String argStr){
		Map<String, Object> result = new HashMap<String,Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String condition="";
		if(args.containsKey("condition")) {
			condition = args.get("condition").toString();
		}
		
		Pager pager = new Pager();
		pager.setPageSize(10);
		pager.setPageIndex(0);
		if(args.containsKey("pager")) {
			String pagerStr = args.get("pager").toString();
			JSONObject json = JSON.parseObject(pagerStr);
			pager.setPageIndex(json.getIntValue("currentPage"));
			pager.setPageSize(json.getIntValue("pageSize"));
		}
		condition = StringUtils.isEmpty(condition)?" 1=1 ":condition;
		
		List<EcmUiRelation> rlist = service.selectByCondition(pager,condition);
		pager.setTotal(service.selectByCondition(condition).size());
		result.put("data", rlist);
		result.put("pager", pager);
		result.put("code", ActionContext.SUCESS);
		
		return result;
	}
	
	@PostMapping("/admin/uirelation/save")
	@ResponseBody
	public Map<String, Object> save(@RequestBody EcmUiRelation form){
		Map<String, Object> result = new HashMap<String,Object>();
		
		EcmUiRelation obj = service.getObjectByName(form.getRelationName());
		if(obj==null) {
			service.newObject(form);
			result.put("code", ActionContext.SUCESS);
		}else {
			result.put("code", ActionContext.FAILURE);
			result.put("message", "关系名称不可重复");
		}
		
				
		return result;
	}
	
	@PostMapping("/admin/uirelation/update")
	@ResponseBody
	public Map<String, Object> update(@RequestBody EcmUiRelation form){
		Map<String, Object> result = new HashMap<String,Object>();
		if(service.updateObject(form)) {
			
			result.put("code", ActionContext.SUCESS);		
		}else {
			result.put("code", ActionContext.BUSINESS_ERROR);
		}
		return result;
	}
	
	
	@PostMapping("/admin/uirelation/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody String id){
		Map<String, Object> result = new HashMap<String,Object>();
		EcmUiRelation obj = service.getObjectById(id.replace("\"", ""));
		if(obj!=null) {
			service.deleteObject(obj);
		}
		
		result.put("code", ActionContext.SUCESS);		
		return result;
	}
	
	@Autowired
	private QueryService queryService;
	
	@ResponseBody
	@PostMapping("/query/getquery")
	public Map<String,Object> getQuery(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> map = new HashMap<String, Object>();
		EcmQuery queryObject = queryService.getObjectByName("", args.get("queryName").toString());
		
		map.put("data",queryService.executeSQL("", queryObject.getSqlString()));
		map.put("valueField", queryObject.getValueColumn());
		map.put("labelField", queryObject.getLabelColumn());
		map.put("code", ActionContext.SUCESS);
		return map;
	}
	
	@ResponseBody
	@PostMapping("/admin/uirelation/get")
	public Map<String,Object> getRelation(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> map = new HashMap<String, Object>();
		EcmUiRelation relation  = service.getObjectByTypeName(args.get("typeName").toString());
		
		map.put("data",relation);
		map.put("code", ActionContext.SUCESS);
		return map;
	}
}
