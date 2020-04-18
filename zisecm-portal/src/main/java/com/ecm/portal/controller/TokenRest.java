package com.ecm.portal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.entity.LoginUser;
import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;
@Controller
public class TokenRest {
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value ="/admin/validataAdminToken", method = RequestMethod.POST)
	@ResponseBody
	public boolean validataAdminToken(String token) {
		IEcmSession session= authService.login(token);
		if(session==null) {
			return false;
		}
		LoginUser currentUser= session.getCurrentUser();
		int permission= currentUser.getSystemPermission();
		if(permission>5) {
			return true;
		}
		return false;
		
	}
}
