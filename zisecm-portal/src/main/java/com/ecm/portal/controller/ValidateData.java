package com.ecm.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.UniquenessException;
import com.ecm.core.service.DocumentService;

@Controller
public class ValidateData extends ControllerAbstract {
	@Autowired
	private DocumentService documentService;
	@RequestMapping(value = "/dc/validateOnly", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> validateOnly(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String,Object> mp=new HashMap<String, Object>();
		boolean isOk=false;
		try {
			isOk= documentService.validateOnlyOne(getToken(), args);
			mp.put("isOk", isOk);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("isOk", false);
			mp.put("msg", e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			return mp;
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("isOk", false);
			mp.put("msg", e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			return mp;
		} catch (UniquenessException e) {
			// TODO Auto-generated catch block
			mp.put("isOk", false);
			mp.put("msg", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	}
}
