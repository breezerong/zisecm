package com.ecm.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmAuditGeneralMapper;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.util.AuditUtils;
import com.ecm.icore.service.IEcmObjectService;
import com.ecm.icore.service.IEcmSession;

public abstract class EcmObjectService<T> extends EcmService implements IEcmObjectService {
	
	@Autowired
    private EcmAuditGeneralMapper ecmAuditGeneralMapper;
	
	@Override
	public List<T> getObjects(String token, String condition) throws EcmException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getMapList(String token, String sql) throws EcmException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Map<String, Object>> getMapList(String token, String sql,Pager pager) throws EcmException{
		return null;
	}
	
	@Override
	public List<T> getAllObject(String token) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getObjectById(String token, String id) throws EcmException, AccessDeniedException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateObject(String token, Object obj) throws EcmException, AccessDeniedException{
		// TODO Auto-generated method stub
		return hasPermission(token,1,0);
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException{
		// TODO Auto-generated method stub
		return hasPermission(token,1,0);
	}

	@Override
	public String newObject(String token, Object obj) throws EcmException, AccessDeniedException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(String token, int permissionId,int systemPermission) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		IEcmSession session = getSession(token);
		if(session==null) {
			throw new EcmException(serviceCode,systemPermission,"session is null.");
		}
		if(systemPermission>0) {
			if(session.getCurrentUser().getSystemPermission()<systemPermission) {
				throw new EcmException(serviceCode,systemPermission,session.getCurrentUser().getUserName()+" has no system permission.");
			}
		}
		//查询权限
		if(permissionId==serviceCode+1) {
			if(session.getCurrentUser().getClientPermission()<1) {
				throw new EcmException(serviceCode,systemPermission,session.getCurrentUser().getUserName()+" has no read permission.");
			}
		}
		//有新建权限就有修改和删除权限，对象权限通过权限控制列表实现
		if(permissionId==serviceCode+2) {
			if(session.getCurrentUser().getClientPermission()<2) {
				throw new EcmException(serviceCode,systemPermission,session.getCurrentUser().getUserName()+" has no create permission.");
			}
		}
		if(permissionId==serviceCode+3) {
			if(session.getCurrentUser().getClientPermission()<2) {
				throw new EcmException(serviceCode,systemPermission,session.getCurrentUser().getUserName()+" has no update permission.");
			}
		}
		if(permissionId==serviceCode+4) {
			if(session.getCurrentUser().getClientPermission()<2) {
				throw new EcmException(serviceCode,systemPermission,session.getCurrentUser().getUserName()+" has no delete permission.");
			}
		}
		return true;
	}

	@Override
	public long getServiceCode() {
		// TODO Auto-generated method stub
		return serviceCode;
	}

	@Override
	public String getCode(String msg) {
		// TODO Auto-generated method stub
		if(msg.startsWith("ErrorCode:")) {
			return msg.split(",")[0].replaceAll("ErrorCode:", "");
		}
		return "99999";
	}
	
	/**
	 * 创建UUID
	 * @return
	 */
	@Override
	public String newUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public List<Map<String, Object>> getObjectMap(String token, String condition) throws EcmException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String newAudit(String token, String appName,String actionName,String objId,String extendId,String message) throws AccessDeniedException {
		if(!AuditUtils.isEnabled(actionName)) {
			return null;
		}
		IEcmSession session = getSession(token);
		EcmAuditGeneral en = new EcmAuditGeneral();
		if(StringUtils.isEmpty(appName)) {
			appName = session.getCurrentUser().getAppName();
		}
		if(StringUtils.isEmpty(appName)) {
			appName = ActionContext.APP_CORE;
		}
		en.createId();
		en.setExcuteDate(new Date());
		en.setAppName(appName);
		en.setActionName(actionName);
		en.setDocId(objId==null?"":objId);
		en.setMessage(message);
		en.setExtendId(extendId==null?"":extendId);
		try {
			en.setUserId(session.getCurrentUser().getUserId());
			en.setUserName(session.getCurrentUser().getUserName());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		ecmAuditGeneralMapper.insertSelective(en);
		return en.getId();
	}
}
