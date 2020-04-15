package com.ecm.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmCfgActivityMapper;
import com.ecm.core.dao.EcmQueryMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmCfgActivity;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
@Service
public class CfgActivityService extends EcmObjectService<EcmCfgActivity> {

	@Autowired
	private EcmCfgActivityMapper ecmCfgActivityMapper;
	
	@Autowired
	private EcmQueryMapper ecmQueryMapper;
	
	@Override
	public List<EcmCfgActivity> getAllObject(String token) {
		return ecmCfgActivityMapper.selectAll();
	}
	
	@Override
	public EcmCfgActivity getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return ecmCfgActivityMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmCfgActivityMapper.updateByPrimaryKey((EcmCfgActivity) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmCfgActivityMapper.deleteByPrimaryKey(((EcmCfgActivity)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmCfgActivity)obj).createId();
		ecmCfgActivityMapper.insert((EcmCfgActivity)obj);
		return ((EcmCfgActivity)obj).getId();
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return ecmCfgActivityMapper.deleteByPrimaryKey(id)>0;
	}
	/**
	 * 获取流程任务配置
	 * @param token
	 * @param processId 流程定义ID
	 * @return
	 */
	public List<EcmCfgActivity> getProcessCfgActivities(String token,String processId) {
		return ecmCfgActivityMapper.selectByProcessId(processId);
	}
	
	/**
	 * 更新流程ID，用于流程版本更新
	 * @param token
	 * @param processName 流程名称
	 * @param processId 新版本流程定义ID
	 * @return
	 */
	public boolean updateProcessId(String token,String processName,String processId) {
		String sql = "update ecm_cfg_activity set PROCESS_ID='"+DBFactory.getDBConn().getDBUtils().getString(processId)
				+"' where PROCESS_NAME='"+DBFactory.getDBConn().getDBUtils().getString(processName)+"'";
		return ecmQueryMapper.executeSQL(sql).size()>0;
	}
	
}
