package com.ecm.icore.service;

import com.ecm.core.entity.EcmDefType;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
/**
 * 业务类型定义接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:49:45
 */
public interface IDefTypeService {
	/**
	 * 复制类型定义
	 * @param token
	 * @param obj
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException
	 */
	boolean copy(String token, EcmDefType obj) throws EcmException, AccessDeniedException;

	/**
	 * 获取类型定义
	 * @param token
	 * @param name
	 * @return
	 * @throws EcmException
	 */
	EcmDefType getObjectByName(String token, String name) throws EcmException;
}
