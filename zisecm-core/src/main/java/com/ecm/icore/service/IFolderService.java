package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmFolder;

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

	List<EcmFolder> getFoldersByParentId(String token, String parentId);

	EcmFolder getObjectByPath(String token, String folderPath);

	EcmFolder getObjectByName(String token, String name, String parentId);

	/**
	 * 复制文件夹
	 * @param token
	 * @param sourceId 源文件夹
	 * @param targetId 目标文件夹
	 * @param includeSource 是否包含源文件夹，否：只复制源文件夹中的子文件夹
	 * @return
	 */
	boolean copyFolders(String token, String sourceId, String targetId, boolean includeSource);

}
