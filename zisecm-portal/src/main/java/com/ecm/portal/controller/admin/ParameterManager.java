package com.ecm.portal.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmActionMapper;
import com.ecm.core.dao.EcmParameterMapper;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.service.ParameterService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class ParameterManager extends ControllerAbstract{
	
	@Autowired
	private ParameterService ecmParameter;
	
	 @ResponseBody
	 @RequestMapping("/admin/getSysParameter")
	 public   Map<String, Object>  getSystemParameter() {
		 List<EcmParameter> list =ecmParameter.getAllObject(getToken());
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 mp.put("data", list);
		 return mp;
	 }
	 
	 @RequestMapping(value="/admin/updateSysParameter", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateSystemParameter(@RequestBody  EcmParameter obj) {
		 ecmParameter.updateObject(getToken(), obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }
	 
	 @RequestMapping(value="/admin/deleteSysParameter", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteSysParameter(@RequestBody  EcmParameter obj) {
		 ecmParameter.deleteObject(getToken(), obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }
	 
	 
	 @RequestMapping(value="/admin/newSysParameter", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newSystemParameter(@RequestBody  EcmParameter obj) {
		 ecmParameter.newObject(getToken(), obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }

}
