package com.ecm.icore.bpm;

import com.ecm.core.entity.EcmWorkflow;
import com.ecm.core.entity.EcmWorkitem;
import com.ecm.core.exception.AccessDeniedException;
/**
 * 流程服务事件
 * @author Haihong Rong
 * @date 2019年7月24日 下午3:41:44
 */
public interface IBPMServerEvent {

	/**
	 * 开始工作流
	 * @param workflow
	 * @throws AccessDeniedException 
	 */
	void startWorkflow(String token,EcmWorkflow workflow) throws AccessDeniedException;
	/**
	 * 结束流程
	 * @param workflow
	 * @throws AccessDeniedException 
	 */
	void complateWorkflow(String token,EcmWorkflow workflow) throws AccessDeniedException;
	
	/**
	 * 执行自动方法
	 * @param workitem
	 */
	void doAutoMethod(String token,EcmWorkitem workitem);

}
