package com.ecm.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ecm.core.cache.manager.SessionManager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.UserService;
import com.ecm.icore.service.IEcmSession;

@Controller
public abstract class ControllerAbstract {
	/**
	 * 获取Token
	 * @return
	 */
	public String getToken() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return (String)request.getSession().getAttribute("ECMUserToken");
	}
	/**
	 * 获取Session
	 * @return
	 * @throws AccessDeniedException 
	 */
	public IEcmSession getSession() throws AccessDeniedException {
		String token="null";
		try {
			token = getToken();
			IEcmSession session =   SessionManager.getInstance().getSession(token);
			if(session == null) {
				throw new AccessDeniedException("Invalid token: " + token);
			}
			return session;
		}catch(Exception ex) {
			throw new AccessDeniedException("Invalid token: " + token);
		}
		
	}

}
