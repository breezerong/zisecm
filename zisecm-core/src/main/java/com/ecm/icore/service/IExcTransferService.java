package com.ecm.icore.service;

import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
public interface IExcTransferService {
	public List<ExcTransfer> getAllObject(String token);
	
	public List<ExcTransfer> selectByCondition(String condition);
	
	public ExcTransfer getObjectById(String id);
	
	public String newObject(ExcTransfer obj);
	
	public boolean updateObject(ExcTransfer obj);
	
	public boolean deleteObject(ExcTransfer obj);
	
	public List<ExcTransfer> selectAll();

	List<Map<String, Object>> getObjectMap(String token, String condition);


	String newObject(Map<String, Object> args) throws EcmException, AccessDeniedException, NoPermissionException;

}
