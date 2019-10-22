package com.ecm.core.service;

import com.ecm.core.cache.manager.SessionManager;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.icore.service.IEcmSession;

public abstract class EcmService {
	/*
	 * 服务代码
	 */
	protected int serviceCode;
	/**
	 * 需要系统权限
	 */
	protected int systemPermission=0;
	
	/**
	 * 获取Session
	 * @param token
	 * @return
	 * @throws AccessDeniedException 
	 */
	public IEcmSession getSession(String token) throws AccessDeniedException {
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
	/**
	 * 获取登录用户
	 * @param token
	 * @return
	 * @throws AccessDeniedException 
	 */
	public  LoginUser getCurrentUser(String token) throws AccessDeniedException {
		// TODO Auto-gePortalUserethod stub
		return getSession(token).getCurrentUser();
	}
}
