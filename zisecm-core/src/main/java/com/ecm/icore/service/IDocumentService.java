package com.ecm.icore.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;

public interface IDocumentService {
	/**
	 * 文档查询
	 * @param gridName 视图名称
	 * @param folderId 文件夹Id
	 * @param pageSize 每页数目
	 * @param startIndex 起始大小
	 * @param condition 条件
	 * @param orderBy 排序
	 * @return
	 */
	List<Map<String, Object>> getObjects(String token, String gridName,String folderId, Pager pager,String condition,String orderBy);
	/**
	 * 获取对象元数据
	 * @param id 对象Id
	 * @return
	 */
	Map<String, Object> getObjectMapById(String token, String id);
	
	/**
	 * 新建对象
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public String newObject(String token, Map<String, Object> args) throws Exception;
	
	/**
	 * 获取对象数
	 * @param gridName
	 * @param folderId
	 * @param condition
	 * @return
	 */
	long getObjectCount(String token, String gridName, String folderId, String condition);
	/**
	 * 根据文档ID获取对象权限
	 * @param id 文档ID
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getPermit(String token, String id) throws AccessDeniedException;
	/**
	 * 更新文档状态
	 * @param id
	 * @param status
	 * @return
	 * @throws AccessDeniedException 
	 */
	boolean updateStatus(String token, String id, String status) throws AccessDeniedException;
	/**
	 * 更新状态和说明
	 * @param id
	 * @param status
	 * @param comment
	 * @return
	 * @throws AccessDeniedException 
	 */
	boolean updateStatus(String token, String id, String status, String comment) throws AccessDeniedException;
	/**
	 * 执行查询
	 * @param token
	 * @param sql
	 * @return
	 */
	boolean executeSQL(String token, String sql);
	/**
	 * 根据文档Id添加角色权限
	 * @param token
	 * @param id 文档ID
	 * @param targetName 组名
	 * @param permission 权限值，ObjectPermission获取
	 * @param expireDate 超期日期
	 * @param newAcl 是否新建ACL
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 */
	String grantGroup(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException;;
	/**
	 * 根据文档对象添加角色权限
	 * @param token
	 * @param doc 文档对象
	 * @param targetName 组名
	 * @param permission 权限值，ObjectPermission获取
	 * @param expireDate
	 * @param newAcl 是否新建ACL
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 */
	String grantGroup(String token, EcmDocument doc, String targetName, int permission, Date expireDate, boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException;
	/**
	 * 根据文档Id添加用户权限
	 * @param token
	 * @param id
	 * @param targetName
	 * @param permission
	 * @param expireDate
	 * @param newAcl 是否新建ACL
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 */
	String grantUser(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException;
	/**
	 * 根据文档对象添加用户权限
	 * @param token
	 * @param doc
	 * @param targetName
	 * @param permission
	 * @param expireDate
	 * @param newAcl 是否新建ACL
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 */
	String grantUser(String token, EcmDocument doc, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException;
	/**
	 * 根据文档对象添加用户权限
	 * @param token
	 * @param doc
	 * @param targetName
	 * @param permission
	 * @param expireDate
	 * @param newAcl 是否新建ACL
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws NoPermissionException 
	 */
	String grantUsers(String token, EcmDocument doc, String[] targetNames, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException;
	/**
	 * 移除用户权限
	 * @param token
	 * @param id 文档ID
	 * @param targetName 用户名
	 * @param newAcl 是否新建ACL
	 * @throws Exception 
	 */
	String revokeUser(String token, String id, String targetName, boolean newAcl) throws Exception;
	/**
	 * 根据文档对象移除用户权限
	 * @param token
	 * @param doc 文档对象
	 * @param targetName 用户名
	 * @param newAcl 是否新建ACL
	 * @throws Exception 
	 */
	String revokeUser(String token, EcmDocument doc, String targetName, boolean newAcl) throws Exception;
	/**
	 * 根据文档Id移除组权限
	 * @param token
	 * @param id 文档ID
	 * @param targetName 组名
	 * @param newAcl 是否新建ACL
	 * @throws Exception 
	 */
	String revokeGroup(String token, String id, String targetName, boolean newAcl) throws Exception;
	/**
	 * 根据文档对象移除组权限
	 * @param token
	 * @param doc 文档对象
	 * @param targetName 组名
	 * @param newAcl 是否新建ACL
	 * @throws Exception 
	 */
	String revokeGroup(String token, EcmDocument doc, String targetName, boolean newAcl) throws Exception;
	/**
	 * 根据文档对象获取权限
	 * @param token
	 * @param doc 文档对象
	 * @return
	 * @throws AccessDeniedException 
	 */
	int getPermit(String token, EcmDocument doc) throws AccessDeniedException;
	/**
	 * 新建文档
	 * @param token
	 * @param doc
	 * @param content 内容为空，只添加属性；内容不为空：原始文件名，输入流必需
	 * @return 文档ID
	 * @throws Exception
	 */
	String newObject(String token, EcmDocument doc, EcmContent content) throws Exception;
	/**
	 * 根据对象锁定文档
	 * @param token
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	boolean lock(String token, EcmDocument doc) throws Exception;
	/**
	 * 根据Id锁定文档，修改属性权限
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean lock(String token, String id) throws Exception;
	/**
	 * 解锁，锁定人和超级用户
	 * @param token
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	boolean unlock(String token, EcmDocument doc) throws Exception;
	/**
	 * 根据ID解锁
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean unlock(String token, String id) throws Exception;
	/**
	 * 更新文档
	 * @param token
	 * @param doc
	 * @param content 内容为空更新元数据，内容不为空：原始文件名，输入流必需
	 * @throws Exception
	 */
	void updateObject(String token, EcmDocument doc, EcmContent content) throws Exception;
	/**
	 * 添加格式副本
	 * @param token
	 * @param id
	 * @param content 内容不为空：原始文件名，输入流必需
	 * @return
	 * @throws Exception
	 */
	boolean addRendition(String token, String id, EcmContent content) throws Exception;
	/**
	 * 移除格式副本
	 * @param token
	 * @param id
	 * @param formatName
	 * @return
	 * @throws Exception
	 */
	boolean removeRendition(String token, String id, String formatName) throws Exception;
	/**
	 * 获取所有格式副本，不包含主格式
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<EcmContent> getAllRendition(String token, String id) throws Exception;
	/**
	 * 获取主格式
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	EcmContent getContent(String token, String id) throws Exception;
	
	/**
	 * 获取文件流
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public InputStream getContentStream(String token, EcmContent en,String id) throws Exception ;
		
	
	/**
	 * 根据ID签出
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean checkOut(String token, String id) throws Exception;
	/**
	 * 根据对象签出
	 * @param token
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	boolean checkOut(String token, EcmDocument doc) throws Exception;
	/**
	 * 签入
	 * @param token
	 * @param docId 文档Id
	 * @param attrMap 需要修改属性
	 * @param content 电子文件，如果为空，从上一版继承主格式
	 * @param isCurrent 是否当前版本
	 * @return
	 * @throws Exception
	 */
	EcmDocument checkIn(String token, String docId, Map<String, Object> attrMap, EcmContent content, boolean isCurrent)
			throws Exception;
	/**
	 * 获取所有版本文档
	 * @param token
	 * @param id
	 * @return 文档对象
	 * @throws Exception 
	 */
	List<EcmDocument> getAllVersions(String token, String id) throws Exception;
	/**
	 * 获取
	 * @param token
	 * @param contentId
	 * @return
	 * @throws Exception
	 */
	boolean removeRendition(String token, String contentId) throws Exception;
	/**
	 * 获取所有版本Map
	 * @param token
	 * @param id
	 * @return Map
	 * @throws Exception
	 */
	List<Map<String, Object>> getAllVersionsMap(String token, String id) throws Exception;
	/**
	 * 发起队列
	 * @param token
	 * @param id 文档ID
	 * @param name 队列用户
	 * @param eventName 事件名称
	 * @param message 消息
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 */
	void queue(String token, String id, String name, String eventName, String message) throws EcmException, AccessDeniedException;
	boolean detachLifeCycle(String token, String id) throws NoPermissionException, AccessDeniedException;
	boolean attachLifeCycle(String token, String id, String lifecycelName)
			throws AccessDeniedException, NoPermissionException;
	boolean promote(String token, String id) throws NoPermissionException, AccessDeniedException, Exception;
	boolean demote(String token, String id) throws NoPermissionException, AccessDeniedException, Exception;
	/**
	 * 
	 * @Title: 合并到其他文件版本
	 * @author Haihong Rong
	 * @date:   2020年8月6日 下午2:34:44 
	 * @Description:       
	 * @param token
	 * @param docId 文档ID
	 * @param attrMap 需要修改的属性
	 * @param toId 被合并文档ID
	 * @param isCurrent 是否当前版本
	 * @return
	 * @throws Exception
	 */
	void newRevisionTo(String token, String docId, Map<String, Object> attrMap, String toId, boolean isCurrent)
			throws Exception;
	/**
	 * 根据现有对象创建新对象
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-9-20 21:34:06 
	 * @Description:       
	 * @param token
	 * @param id 对象ID
	 * @param attrValues 需要修改的属性值
	 * @param includeContent 是否包含电子文件
	 * @return 新对象ID
	 * @throws EcmException
	 * @throws AccessDeniedException 
	 * @throws Exception 
	 */
	String saveAsNew(String token, String id, Map<String, Object> attrValues, boolean includeContent)
			throws EcmException, AccessDeniedException, Exception;
	void updateObject(String token, Map<String, Object> args)
			throws NoPermissionException, AccessDeniedException, EcmException;
	
	
	/**
	 * 签入内容 并升系统版本
	 * @param token
	 * @param docId 文档Id
	 * @param attrMap 需要修改属性
	 * @param content 电子文件，如果为空，从上一版继承主格式
	 * @param isCurrent 是否当前版本
	 * @return
	 * @throws Exception
	 */
	String checkInUpgradeContent(String token, String docId, EcmContent content) throws Exception ;
	
	
}
