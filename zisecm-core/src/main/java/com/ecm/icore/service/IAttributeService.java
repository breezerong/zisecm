package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmDefAttribute;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
/**
 * 属性服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:45:03
 */
public interface IAttributeService extends IEcmObjectService{
	
	/**
	 * 获取文件类型属性
	 * @param token
	 * @return
	 * @throws EcmException
	 */
	List<EcmAttribute> getDocAttributes(String token) throws EcmException;

	/**
	 * 添加文档属性
	 * @param token
	 * @param obj
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException
	 * @throws NoPermissionException 
	 */
	boolean newAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException;

	/**
	 * 删除文档属性
	 * @param token
	 * @param obj
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException
	 * @throws NoPermissionException 
	 */
	boolean deleteAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException;

	/**
	 * 更新文档属性
	 * @param token
	 * @param obj
	 * @return
	 * @throws EcmException
	 * @throws AccessDeniedException
	 * @throws NoPermissionException 
	 */
	boolean updateAttribute(String token, EcmAttribute obj) throws EcmException, AccessDeniedException, NoPermissionException;

}
