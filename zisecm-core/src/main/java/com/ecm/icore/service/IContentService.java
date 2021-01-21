package com.ecm.icore.service;

import java.io.InputStream;
import java.util.List;

import com.ecm.core.entity.EcmContent;
import com.ecm.core.exception.EcmException;

public interface IContentService {
	/**
	 * 根据文档ID获取内容列表
	 * @param docId 文档Id
	 * @param contentType 内容类型：0:所有，1：主格式，2：格式副本，3：附件
	 * @return
	 */
	List<EcmContent> getObjects(String token, String docId,int contentType);
	
	/**
	 * 根据内容对象获取文件流
	 * @param token
	 * @param en
	 * @return
	 * @throws Exception
	 */
	InputStream getContentStream(String token, EcmContent en) throws Exception;
	/**
	 * 获取主格式
	 * @param token
	 * @param docId
	 * @return
	 */
	EcmContent getPrimaryContent(String token, String docId);
	
	/**
	 * 根据类型
	 * @param token
	 * @param objId
	 * @param contentType 类型,1:主文件，2：格式副本，3：附件，小于1任意类型
	 * @param formatName 格式（扩展名，都使用小写）
	 * @return
	 */
	EcmContent getObject(String token, String objId, int contentType, String formatName);

	/**
	 * 获取文档主格式文件路径
	 * @param token
	 * @param docId
	 * @return
	 */
	String getPrimaryFilePath(String token, String docId);
	/**
	 * 更新文件大小
	 * @param token
	 * @param docId
	 * @param fileSize
	 * @return
	 */
	boolean updatePrimaryContentSize(String token, String docId, long fileSize);

	/**
	 * 复制文件内容
	 * @param token
	 * @param fromDocId
	 * @param toDocId
	 * @throws Exception
	 */
	void copyContent(String token, String fromDocId, String toDocId) throws Exception;
	
	/**
	 * 增加isCurrent=1
	 * @param token
	 * @param docId
	 * @return
	 */
	public List<EcmContent> getAllContents(String token, String docId);
	
	/**
	 * 增加isCurrent=1
	 * @param token
	 * @param docId
	 * @param contentType
	 * @return
	 */
	public List<EcmContent> getContents(String token, String docId ,int contentType );
	
	
	
}
