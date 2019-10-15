package com.ecm.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.PermissionContext;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmDefAttributeMapper;
import com.ecm.core.entity.EcmDefAttribute;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IDefAttributeService;


@Service
@Scope("prototype")
public class DefAttributeService extends EcmObjectService<EcmDefAttribute> implements IDefAttributeService {
	private final Logger logger = LoggerFactory.getLogger(DefAttributeService.class);

	public DefAttributeService()
	{
		serviceCode = ServiceContext.ATTRIBUTE_CODE;
		systemPermission = PermissionContext.SystemPermission.SYSTEM_CONFIGE;
		logger.info("ServiceCode:"+serviceCode+",systemPermission:"+systemPermission);
	}
	
	/**
	 * 数据访问
	 */
	@Autowired
	private EcmDefAttributeMapper ecmDefAttribute;

	
	@Override
	public List<EcmDefAttribute> getAllObject(String token) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+1,systemPermission);
		return (List<EcmDefAttribute>) ecmDefAttribute.selectByCondition(" 1=1 ");
	}

	@Override
	public EcmDefAttribute getObjectById(String token, String id) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+1,systemPermission);
		return (EcmDefAttribute) ecmDefAttribute.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+3,systemPermission);
		return ecmDefAttribute.updateByPrimaryKey((EcmDefAttribute) obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+4,systemPermission);
		return ecmDefAttribute.deleteByTypeId(((EcmDefAttribute)obj).getId())>0;
	}

	@Override
	public String newObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+2,systemPermission);
		((EcmDefAttribute)obj).createId();
		ecmDefAttribute.insert((EcmDefAttribute)obj);
		return ((EcmDefAttribute)obj).getId();
	}

	@Override
	public List<EcmDefAttribute> getAttributes(String token, String typeId) {
		return ecmDefAttribute.selectByCondition(" TYPE_ID="+typeId);
	}
	
	
}
