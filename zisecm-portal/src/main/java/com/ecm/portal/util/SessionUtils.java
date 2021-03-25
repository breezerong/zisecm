package com.ecm.portal.util;

import org.springframework.core.env.Environment;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.cache.manager.SessionManager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;

public class SessionUtils {
	public static IEcmSession getWorkflowSession(Environment env,AuthService authService) {
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		try {
			ecmSession = authService.login("workflow", workflowSpecialUserName, env.getProperty("ecm.password"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		return ecmSession;
	}
	/**
	 * 获取文件转换用户Session
	 * @param authService
	 * @return
	 * @throws Exception
	 */
	public static IEcmSession getSuperUserSession(AuthService authService) throws Exception {
		String user = null;
		String password =null;
		try {
			user = CacheManagerOper.getEcmParameters().get("superUser").getValue();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new Exception("未配置superUser用户名");
		}
		try {
			password = CacheManagerOper.getEcmParameters().get("superUserPassword").getValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new Exception("未配置superUserPassword用户名密码");
		}
		try {
			return authService.login("superUser",user,password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	/**
	 * 获取Session
	 * @param token
	 * @return
	 * @throws AccessDeniedException
	 */
	public static IEcmSession getSession(String token) throws AccessDeniedException {
		try {
			IEcmSession session = SessionManager.getInstance().getSession(token);
			if(session == null) {
				throw new AccessDeniedException("Invalid token: " + token);
			}
			return session;
		}catch(Exception ex)
		{
			throw new AccessDeniedException("Invalid token: " + token);
		}
		
	}
	
	public static void releaseSession(AuthService authService,IEcmSession session) {
		authService.logout(session.getCurrentUser().getLoginName());
	}
}
