package com.ecm.flowable.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;

public  class FlowableSession {

	@Autowired
	private AuthService authService;
	@Autowired
	private Environment env;

	/**
	 * 获取Token
	 * @return
	 * @throws AccessDeniedException 
	 */
	public String getToken() throws AccessDeniedException {
		String token = getSession().getToken();
		if(token == null) {
			throw new AccessDeniedException("Session is time out.");
		}
		return token;
	}
	/**
	 * 获取Session
	 * @return
	 * @throws AccessDeniedException 
	 */
	public IEcmSession getSession() throws AccessDeniedException {
		try {
			IEcmSession ecmSession = authService.login("workflow", "admin", "1");
			if(ecmSession == null) {
				throw new AccessDeniedException("Invalid session" );
			}
			return ecmSession;
		}catch(Exception ex) {
			throw new AccessDeniedException("Invalid session" );
		}
		
	}
}
