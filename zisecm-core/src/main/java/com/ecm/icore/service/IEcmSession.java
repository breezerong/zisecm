package com.ecm.icore.service;


import com.ecm.core.entity.LoginUser;
/**
 * 登录Session
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:53:40
 */
public interface IEcmSession {
	/**
	 * 
	 * @return 获取当前用户
	 */
	LoginUser getCurrentUser();
	/**
	 * 设置当前用户
	 * @param loginUser 
	 */
	void setCurrentUser(LoginUser loginUser);
	/**
	 * 获取Token
	 * @return
	 */
	String getToken();
	/**
	 * 
	 * @param token
	 */
	void setToken(String token);
//	Authentication getAuth();
//	void setAuth(Authentication auth);
}
