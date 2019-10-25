package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmParameter;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.ParameterService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class ParameterManager extends ControllerAbstract {

	@Autowired
	private ParameterService ecmParameter;

	@ResponseBody
	@RequestMapping("/admin/getSysParameter")
	public Map<String, Object> getSystemParameter() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmParameter> list = ecmParameter.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/updateSysParameter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSystemParameter(@RequestBody EcmParameter obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmParameter.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/deleteSysParameter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSysParameter(@RequestBody EcmParameter obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmParameter.deleteObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/newSysParameter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newSystemParameter(@RequestBody EcmParameter obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmParameter.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

}
