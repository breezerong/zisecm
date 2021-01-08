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
import com.ecm.core.cache.manager.CacheManagerOper;
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
		int maxLoginCount = 10;
		try {
			if(CacheManagerOper.getEcmParameters().get("MaxErrorLoginCout")!=null) {
				maxLoginCount = Integer.parseInt(CacheManagerOper.getEcmParameters().get("MaxErrorLoginCout").getValue());
			}
		}catch(Exception e) {
			
		}
		int loginCount = 1;
		try {
			if(session.getAttribute("ECMLoginFailCount")!=null)
			{
				loginCount = Integer.parseInt(session.getAttribute("ECMLoginFailCount").toString());
			}
		}catch(Exception e) {
			
		}
		EcmUser ecmUser = new EcmUser();
		ecmUser.setLoginName(user.getUsername());
		ecmUser.setPassword(user.getPassword());
		Map<String, Object> mp = new HashMap<String, Object>();
			session.removeAttribute("ECMUserToken");
			//session.removeAttribute("ECMUserSession");
			if(loginCount>maxLoginCount) {
				authService.lockUser(username);
				session.removeAttribute("ECMLoginFailCount");
				mp.put("code", 2);
				mp.put("msg", "登录失败超过 最大次数："+maxLoginCount);
				try {
					auditService.newAudit(null,"portal",AuditContext.LOGIN_FAILED, "登录次数超过:"+maxLoginCount, null, username);
				} catch (AccessDeniedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				try {
					
					// 系统登录认证
					IEcmSession s = authService.login("portal",username,password);
					
					mp.put("code", ActionContext.SUCESS);
					mp.put("token", s.getToken());
					mp.put("data", s.getCurrentUser());
	//				mp.put("company", s.getCurrentUser().getCompany());
	//				mp.put("department", s.getCurrentUser().getDepartment());
	//				mp.put("userType", s.getCurrentUser().getUserType());
	//				mp.put("clientPermission", s.getCurrentUser().getClientPermission());
	//				mp.put("systemPermission", s.getCurrentUser().getSystemPermission());
	//				mp.put("loginName", s.getCurrentUser().getLoginName());
	//				mp.put("userName", s.getCurrentUser().getUserName());
	//				mp.put("roles", s.getCurrentUser().getRoles());
					session.setAttribute("ECMUserToken", s.getToken());
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					e.printStackTrace();
					session.setAttribute("ECMLoginFailCount",loginCount+1);
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
			}
		return mp;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/userLogout", method = RequestMethod.POST)
	public Map<String, Object> userLogout(HttpSession session,@RequestBody(required=false) String token) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if(token == null || token.length() == 0) {
			
				token = getToken();
			}
			session.removeAttribute("ECMUserToken");
			authService.logout(token);
			session.invalidate();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
}
