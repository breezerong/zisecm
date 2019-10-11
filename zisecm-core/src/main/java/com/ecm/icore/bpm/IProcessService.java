package com.ecm.icore.bpm;

import com.ecm.core.entity.EcmProcess;
import com.ecm.core.exception.EcmException;

/**
 * 流程定义服务
 * @author Haihong Rong
 *
 */
public interface IProcessService {

	/**
	 * 根据名称获取流程定义
	 * @param name
	 * @return
	 * @throws EcmException
	 */
	EcmProcess getObjectByName(String token, String name) throws EcmException;
	
}
