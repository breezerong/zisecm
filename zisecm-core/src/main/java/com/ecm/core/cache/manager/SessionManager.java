package com.ecm.core.cache.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.ecm.core.entity.EcmSession;
import com.ecm.icore.service.IEcmSession;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class SessionManager {

	private LoadingCache<String, IEcmSession> loginSession = null;
	private static SessionManager current = new SessionManager();

	private SessionManager() {
		loginSession = CacheBuilder.newBuilder().maximumSize(3000)
				.expireAfterAccess(20, TimeUnit.MINUTES) //20分钟没有访问，自动清除
				.build(new CacheLoader<String, IEcmSession>() {
			@Override
			public EcmSession load(String key) throws FileNotFoundException, IOException {
				return null;
			}
		});
	}

	public static SessionManager getInstance() {
		return current;
	}

	public LoadingCache<String, IEcmSession> getLoginSession() {
		return loginSession;
	}
	
	public IEcmSession getSession(String token)  {
		try {
			
			return loginSession.getUnchecked(token);
		}catch(Exception ex) {
			return null;
		}
	}
	/**
	 * 保存到缓存
	 * @param token
	 * @param session
	 * @throws Exception
	 */
	public synchronized void put(String token,IEcmSession session) throws Exception {
		IEcmSession s = null;
		try {
			s = loginSession.getUnchecked(token);
		}
		catch(Exception ex) {
			
		}
		if(s==null) {
			loginSession.put(token, session);
		}else {
			s = session;
		}
	}
	/**
	 * 刷新 
	 * @param token
	 * @throws Exception
	 */
	public synchronized void refresh(String token) throws Exception {
		loginSession.refresh(token);
	}
	
}
