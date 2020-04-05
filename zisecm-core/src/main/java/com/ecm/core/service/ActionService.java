package com.ecm.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.PermissionContext;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmActionMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;


@Service
@Scope("prototype")
public class ActionService extends EcmObjectService<EcmAction> {
	private final Logger logger = LoggerFactory.getLogger(ActionService.class);

	public ActionService()
	{
		serviceCode = ServiceContext.ACTION_CODE;
		systemPermission = PermissionContext.SystemPermission.UI_CONFIGE;
		logger.info("ServiceCode:"+serviceCode+",systemPermission:"+systemPermission);
	}
	
	/**
	 * 事件管理数据访问
	 */
	@Autowired
	private EcmActionMapper ecmAction;


	@Override
	public List<EcmAction> getAllObject(String token) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+1,systemPermission);
		return (List<EcmAction>) ecmAction.selectAll();
	}

	@Override
	public EcmAction getObjectById(String token, String id) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+1,systemPermission);
		return (EcmAction) ecmAction.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+3,systemPermission);
		return ecmAction.updateByPrimaryKey((com.ecm.core.entity.EcmAction) obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+4,systemPermission);
		return ecmAction.deleteByPrimaryKey(((com.ecm.core.entity.EcmAction)obj).getId())>0;
	}

	@Override
	public String newObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+2,systemPermission);
		((EcmAction)obj).createId();
		ecmAction.insert((com.ecm.core.entity.EcmAction)obj);
		return ((com.ecm.core.entity.EcmAction)obj).getId();
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+4,systemPermission);
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmAction.deleteByPrimaryKey(id)>0;
	}

}
