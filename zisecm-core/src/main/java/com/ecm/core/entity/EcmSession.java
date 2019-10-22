package com.ecm.core.entity;

import com.ecm.icore.service.IEcmSession;

public class EcmSession implements IEcmSession{

	private LoginUser user;
	private String token;
	//private Authentication auth;
	
	@Override
	public LoginUser getCurrentUser() {
		// TODO Auto-generated method stub
		return user;
	}
	@Override
	public void setCurrentUser(LoginUser loginUser) {
		// TODO Auto-generated method stub
		this.user = loginUser;

	}
	@Override
	public String getToken() {
		return token;
	}
	@Override
	public void setToken(String token) {
		this.token = token;
	}
//	@Override
//	public Authentication getAuth() {
//		return auth;
//	}
//	@Override
//	public void setAuth(Authentication auth) {
//		this.auth = auth;
//	}

}
