package com.ecm.icore.service;

import java.util.List;
import java.util.Map;

import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
/**
 * 对象服务基础接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:50:34
 * @param <T>
 */
public interface IEcmObjectService<T>{
	
	List<T> getAllObject(String tocken) throws EcmException, AccessDeniedException, NoPermissionException;
	
	T getObjectById(String tocken, String id) throws EcmException, AccessDeniedException, NoPermissionException;
	
	long getServiceCode();
	
	boolean updateObject(String tocken, T obj) throws EcmException, AccessDeniedException, NoPermissionException;
	
	boolean deleteObject(String tocken, T obj) throws EcmException, AccessDeniedException, NoPermissionException;

	String newObject(String tocken, T obj) throws EcmException, AccessDeniedException, NoPermissionException;
	
	String getCode(String msg);
	
	boolean hasPermission(String tocken, int permissionId,int systemPermission) throws EcmException, AccessDeniedException, NoPermissionException;
	
	List<T> getObjects(String tocken, String condition) throws EcmException;
	/**
	 * 获取Map列表
	 * @param tocken
	 * @param sql
	 * @return
	 * @throws EcmException
	 */
	List<Map<String, Object>> getMapList(String tocken, String sql) throws EcmException;
	/**
	 * 根据条件获取map列表
	 * @param condition
	 * @return
	 * @throws EcmException
	 */
	List<Map<String, Object>> getObjectMap(String tocken, String condition)throws EcmException;
	/**
	 * 创建新UUID
	 * @return
	 */
	String newUUID();
	/**
	 * 写对象操作日志
	 * @param token
	 * @param appName
	 * @param actionName
	 * @param objId
	 * @param extendId
	 * @param message
	 * @return
	 * @throws AccessDeniedException
	 */
	String newAudit(String token, String appName, String actionName, String objId, String extendId, String message) throws AccessDeniedException;

}
