package com.ecm.cnpe.exchange.service;

import java.util.List;

import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;

/**
 * 
 * @Title: 同步日志服务
 * @author Haihong Rong
 * @date:   2020年7月5日 上午10:36:34 
 * @Description:
 */
public interface ISynDetailService  {

	/**
	 * 
	 * @Title:根据属性创建日志
	 * @author Haihong Rong
	 * @date:   2020年7月5日 上午11:08:59 
	 * @Description:       
	 * @param token
	 * @param appName 应用名称
	 * @param actionName 活动名称
	 * @param fromId 数据ID
	 * @param toCompany 发送目标
	 * @return
	 * @throws NoPermissionException 
	 * @throws AccessDeniedException 
	 * @throws EcmException 
	 */
	String newObject(String token, String appName, String actionName, String fromId,String toCompany) throws EcmException, AccessDeniedException, NoPermissionException;
	
}