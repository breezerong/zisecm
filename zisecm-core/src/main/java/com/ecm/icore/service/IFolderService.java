package com.ecm.icore.service;

import java.util.Date;
import java.util.List;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;

/**
 * 文件夹服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:56:06
 */
public interface IFolderService {
	/**
	 * 获取文件夹数量
	 * @param token
	 * @param userName
	 * @param folderId
	 * @return
	 */
	long getFolderCount(String token, String folderId);

	List<EcmFolder> getFoldersByParentPath(String token, String path);

	List<EcmFolder> getFoldersByParentId(String token, String parentId) throws AccessDeniedException;

	EcmFolder getObjectByPath(String token, String folderPath);

	EcmFolder getObjectByName(String token, String name, String parentId);

	/**
	 * 复制文件夹
	 * @param token
	 * @param sourceId 源文件夹
	 * @param targetId 目标文件夹
	 * @param includeSource 是否包含源文件夹，否：只复制源文件夹中的子文件夹
	 * @return
	 * @throws AccessDeniedException 
	 */
	boolean copyFolders(String token, String sourceId, String targetId, boolean includeSource) throws AccessDeniedException;

	String revokeUser(String token, String id, String targetName, boolean newAcl) throws Exception;

	String revokeUser(String token, EcmFolder folder, String targetName, boolean newAcl)
			throws NoPermissionException, AccessDeniedException;

	String revokeGroup(String token, String id, String targetName, boolean newAcl) throws Exception;

	String revokeGroup(String token, EcmFolder folder, String targetName, boolean newAcl)
			throws NoPermissionException, AccessDeniedException;

	int getPermit(String token, String id) throws AccessDeniedException;

	int getPermit(String token, EcmFolder folder) throws AccessDeniedException;

	String grantUser(String token, EcmFolder folder, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException;

	String grantUser(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException;

	String grantGroup(String token, String id, String targetName, int permission, Date expireDate, boolean newAcl)
			throws EcmException, AccessDeniedException, NoPermissionException;

	String grantGroup(String token, EcmFolder folder, String targetName, int permission, Date expireDate,
			boolean newAcl) throws EcmException, AccessDeniedException, NoPermissionException;

	EcmFolder getObjectByPaths(String token, String folderPaths);

	List<EcmFolder> getFoldersByParentPaths(String token, String paths);

}
