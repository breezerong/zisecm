package com.ecm.icore.service;


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

}
