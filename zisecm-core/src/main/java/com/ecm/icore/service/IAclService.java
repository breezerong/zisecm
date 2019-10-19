package com.ecm.icore.service;

import java.util.Date;
import java.util.List;

import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmPermit;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
/**
 * 权限列表服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:45:41
 */
public interface IAclService {
	/**
	 * 根据名称查询ACL
	 * @param tocken
	 * @param pager
	 * @param name ， 支持%模糊查询
	 * @return
	 */
	public List<EcmAcl> getAcls(String tocken, Pager pager, String name);
	/**
	 * 根据ID复制ACL
	 * @param token
	 * @param id
	 * @return
	 * @throws AccessDeniedException 
	 */
	EcmAcl copy(String token, String id) throws AccessDeniedException;
	/**
	 * 根据ACL对象复制ACL
	 * @param token
	 * @param acl
	 * @return
	 * @throws AccessDeniedException 
	 */
	EcmAcl copy(String token, EcmAcl acl) throws AccessDeniedException;
	/**
	 * 移除用户权限
	 * @param token
	 * @param aclId
	 * @param targetName
	 * @throws AccessDeniedException 
	 */
	void revokeUser(String token, String aclId, String targetName) throws AccessDeniedException;
	/**
	 * 移除组权限
	 * @param token
	 * @param aclId
	 * @param targetName
	 * @throws AccessDeniedException 
	 */
	void revokeGroup(String token, String aclId, String targetName) throws AccessDeniedException;
	/**
	 * 用户授权
	 * @param token
	 * @param aclId
	 * @param targetName
	 * @param permission
	 * @param expireDate
	 * @throws AccessDeniedException 
	 */
	void grantUser(String token, String aclId, String targetName, int permission, Date expireDate) throws AccessDeniedException;

	/**
	 * 组授权
	 * @param token
	 * @param aclId
	 * @param targetName
	 * @param permission
	 * @param expireDate
	 * @throws AccessDeniedException 
	 */
	void grantGroup(String token, String aclId, String targetName, int permission, Date expireDate) throws AccessDeniedException;

	/**
	 * 获取ACL授权清单
	 * @param token
	 * @param id
	 * @return
	 */
	List<EcmPermit> getPermits(String token, String id);
	/**
	 * 设置权限
	 * @param token 登陆Token
	 * @param acl Acl对象
	 * @param permit 权限对象
	 * @throws AccessDeniedException 
	 */
	void grant(String token, EcmAcl acl, EcmPermit permit) throws AccessDeniedException;
	/**
	 * 根据名称获取Acl
	 * @param token
	 * @param name Acl名称
	 * @return
	 * @throws AccessDeniedException 
	 */
	EcmAcl getObjectByName(String token, String name) throws AccessDeniedException;
	/**
	 * 获取当前用户对ACL权限
	 * @param token
	 * @param aclName
	 * @return
	 * @throws AccessDeniedException
	 */
	int getPermission(String token, String aclName) throws AccessDeniedException;
}
