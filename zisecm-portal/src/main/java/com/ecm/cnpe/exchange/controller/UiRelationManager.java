package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.entity.EcmUiRelation;
import com.ecm.core.service.EcmUiRelationService;
import com.ecm.core.service.QueryService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
public class UiRelationManager extends ControllerAbstract{

	@Autowired
	private EcmUiRelationService service;
	
	@PostMapping("/admin/uirelation/list")
	public Map<String, Object> list(){
		Map<String, Object> result = new HashMap<String,Object>();
		List<EcmUiRelation> rlist = service.selectAll();
		result.put("code", ActionContext.SUCESS);
		result.put("data", rlist);
		return result;
	}
	
	@PostMapping("/admin/uirelation/save")
	@ResponseBody
	public Map<String, Object> save(@RequestBody EcmUiRelation form){
		Map<String, Object> result = new HashMap<String,Object>();
		service.newObject(form);
		result.put("code", ActionContext.SUCESS);		
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
}
