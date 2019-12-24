package com.ecm.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.UserEntity;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.AuditService;
import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;

/**
 * 
 * @author Administrator 登录管理
 */
@Controller
public class LoginManager extends ControllerAbstract{

	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuditService auditService;

	@RequestMapping("/login")
    public  String login(){
        return "login";
    }
	/**
	 * 登录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public Map<String, Object> userLogin(@RequestBody UserEntity user, HttpSession session,HttpServletRequest request) {
		
		String username = user.getUsername();
		String password = user.getPassword();
		
		EcmUser ecmUser = new EcmUser();
		ecmUser.setLoginName(user.getUsername());
		ecmUser.setPassword(user.getPassword());
		Map<String, Object> mp = new HashMap<String, Object>();
			session.removeAttribute("ECMUserToken");
			//session.removeAttribute("ECMUserSession");
			try {
				
				// 系统登录认证
				IEcmSession s = authService.login("portal",username,password);
				
				mp.put("code", ActionContext.SUCESS);
				mp.put("token", s.getToken());
				mp.put("department", s.getCurrentUser().getDepartment());
				mp.put("clientPermission", s.getCurrentUser().getClientPermission());
				mp.put("systemPermission", s.getCurrentUser().getSystemPermission());
				mp.put("loginName", s.getCurrentUser().getLoginName());
				mp.put("userName", s.getCurrentUser().getUserName());
				session.setAttribute("ECMUserToken", s.getToken());
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				e.printStackTrace();
				mp.put("code", 0);
				mp.put("msg", e.getMessage());
				try {
					auditService.newAudit(null,"portal",AuditContext.LOGIN_FAILED, "", null, username);
				} catch (AccessDeniedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//mp.put(key, value)
			}
		return mp;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/userLogout", method = RequestMethod.POST)
	public Map<String, Object> userLogout(@RequestBody String token) {
		Map<String, Object> mp = new HashMap<String, Object>();
		authService.logout(token);
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
}
