package com.ecm.icore.service;

/**
 * 认证服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:46:19
 */
public interface IAuthService {
	/**
	 * 登录
	 * @param appName 应用名
	 * @param loginName 登录用户名
	 * @param password 登录密码
	 * @return
	 * @throws Exception
	 */
	IEcmSession login(String appName,String loginName,String password) throws Exception;
	/**
	 * 根据Token登录
	 * @param token
	 * @return
	 */
	IEcmSession login(String token);
	/**
	 * 登出
	 * @param token
	 */
	void logout(String token);
}
