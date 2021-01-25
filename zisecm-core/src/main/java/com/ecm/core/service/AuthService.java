package com.ecm.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.AuditContext;
import com.ecm.core.cache.manager.SessionManager;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmSession;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.icore.service.IAuthService;
import com.ecm.icore.service.IEcmSession;

@Service
public class AuthService implements IAuthService {

	@Autowired
	private UserService userService;
	
	@Override
	public IEcmSession login(String appName,String loginName, String password) throws Exception {
		loginName = DBFactory.getDBConn().getDBUtils().getString(loginName);
		EcmUser ecmUser = new EcmUser();
		ecmUser.setLoginName(loginName);
		ecmUser.setPassword(password);
		String clientInfo = userService.newUUID();
		LoginUser  cuser = userService.authentication(ecmUser);
		cuser.setAppName(appName);
		// 系统登录认证
		//Authentication auth = SecurityUtils.login(loginName, password, authenticationManager);
		cuser.setToken(clientInfo);
		IEcmSession s =null;
		try{ 
			s=SessionManager.getInstance().getSession(clientInfo);
		}catch(Exception ex) {
			
		}
		if(s==null) {
			s = new EcmSession();
			SessionManager.getInstance().put(clientInfo, s);
		}
		s.setCurrentUser(cuser);
		s.setToken(clientInfo);
		//s.setAuth(auth);
		userService.newAudit(s.getToken(),null, AuditContext.LOGIN, "", null, null);
		return s;
	}
	
	@Override
	public IEcmSession login(String appName,String loginName, String password,String userIp) throws Exception {
		loginName = DBFactory.getDBConn().getDBUtils().getString(loginName);
		EcmUser ecmUser = new EcmUser();
		ecmUser.setLoginName(loginName);
		ecmUser.setPassword(password);
		
		String clientInfo = userService.newUUID();
		LoginUser  cuser = userService.authentication(ecmUser);
		cuser.setAppName(appName);
		cuser.setLoginIp(userIp);
		// 系统登录认证
		//Authentication auth = SecurityUtils.login(loginName, password, authenticationManager);
		cuser.setToken(clientInfo);
		IEcmSession s =null;
		try{ 
			s=SessionManager.getInstance().getSession(clientInfo);
		}catch(Exception ex) {
			
		}
		if(s==null) {
			s = new EcmSession();
			SessionManager.getInstance().put(clientInfo, s);
		}
		s.setCurrentUser(cuser);
		s.setToken(clientInfo);
		//s.setAuth(auth);
		userService.newAudit(s.getToken(),null, AuditContext.LOGIN, "", null, userIp);
		return s;
	}
	
	@Override
	public IEcmSession loginSSO(String appName,String loginName) throws Exception {
		loginName = DBFactory.getDBConn().getDBUtils().getString(loginName);
		EcmUser ecmUser = new EcmUser();
		ecmUser.setLoginName(loginName);
		String clientInfo = userService.newUUID();
		LoginUser  cuser = userService.authenticationSSO(ecmUser);
		cuser.setAppName(appName);
		// 系统登录认证
		//Authentication auth = SecurityUtils.login(loginName, password, authenticationManager);
		cuser.setToken(clientInfo);
		IEcmSession s =null;
		try{ 
			s=SessionManager.getInstance().getSession(clientInfo);
		}catch(Exception ex) {
			
		}
		if(s==null) {
			s = new EcmSession();
			SessionManager.getInstance().put(clientInfo, s);
		}
		s.setCurrentUser(cuser);
		s.setToken(clientInfo);
		//s.setAuth(auth);
		userService.newAudit(s.getToken(),null, AuditContext.LOGIN, "", null, null);
		return s;
	}

	/**
	 * 
	 */
	@Override
	public IEcmSession login(String token) {
		// TODO Auto-generated method stub
		return SessionManager.getInstance().getSession(token);
	}
	@Override
	public void logout(String token) {
		IEcmSession session = SessionManager.getInstance().getSession(token);
		if(session!=null) {
			SessionManager.getInstance().getLoginSession().invalidate(token);
		}
	}
	@Override
	public void lockUser(String loginName) {
		userService.lockUser(loginName);
	}

}
