package com.ecm.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.core.PermissionContext;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmDefAttributeMapper;
import com.ecm.core.dao.EcmDefTypeMapper;
import com.ecm.core.entity.EcmDefAttribute;
import com.ecm.core.entity.EcmDefType;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.icore.service.IDefTypeService;


@Service
@Scope("prototype")
public class DefTypeService extends EcmObjectService<EcmDefType> implements IDefTypeService {
	private final Logger logger = LoggerFactory.getLogger(DefTypeService.class);

	public DefTypeService()
	{
		serviceCode = ServiceContext.TYPE_CODE;
		systemPermission = PermissionContext.SystemPermission.SYSTEM_CONFIGE;
		logger.info("ServiceCode:"+serviceCode+",systemPermission:"+systemPermission);
	}
	
	/**
	 * 数据访问
	 */
	@Autowired
	private EcmDefTypeMapper ecmDefType;
	
	@Autowired
	private EcmDefAttributeMapper ecmDefAttribute;


	@Override
	public List<EcmDefType> getAllObject(String token) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+1,systemPermission);
		return (List<EcmDefType>) ecmDefType.selectAll();
	}

	@Override
	public EcmDefType getObjectById(String token, String id) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+1,systemPermission);
		return (EcmDefType) ecmDefType.selectByPrimaryKey(id);
	}
	
	@Override
	public EcmDefType getObjectByName(String token, String name) throws EcmException {
		// TODO Auto-generated method stub
		List<EcmDefType> list = ecmDefType.selectAll();
		for(EcmDefType en:list) {
			if(en.getName().equals(name)) {
				return en;
			}
		}
		return null;
	}

	@Override
	public boolean updateObject(String token, Object obj) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+3,systemPermission);
		return ecmDefType.updateByPrimaryKey((EcmDefType) obj)>0;
	}

	@Override
	@Transactional
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+4,systemPermission);
		ecmDefAttribute.deleteByTypeId(((EcmDefType)obj).getId());
		return ecmDefType.deleteByPrimaryKey(((EcmDefType)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) throws EcmException, AccessDeniedException {
		// TODO Auto-generated method stub
		super.hasPermission(token,serviceCode+2,systemPermission);
		((EcmDefType)obj).createId();
		ecmDefType.insert((EcmDefType)obj);
		return ((EcmDefType)obj).getId();
	}

	@Override
	@Transactional
	public boolean copy(String token, EcmDefType obj) throws EcmException, AccessDeniedException {
		super.hasPermission(token, serviceCode+5,systemPermission);
		String sourceId = obj.getId();
		String name=obj.getName()+" Copy";
		obj.createId();
		obj.setName(name);
		obj.setStatus(0);
		newObject(token,obj);
		obj = ecmDefType.selectByName(name);
		if(obj!=null) {
			List<EcmDefAttribute> list = ecmDefAttribute.selectByCondition(" TYPE_ID="+sourceId);
			for(EcmDefAttribute attrObj:list) {
				attrObj.setId(null);
				attrObj.setTypeId(obj.getId());
				ecmDefAttribute.insert(attrObj);
			}
		}
		return false;
	}
	
	
}
