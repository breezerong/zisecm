package com.ecm.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmActionMapper;
import com.ecm.core.dao.EcmRelationTypeMapper;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.entity.EcmRelationType;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IRelationTypeService;

/**
 * 关系定义服务
 * @author Haihong Rong
 * @date 2019年8月27日 下午11:53:26
 */
@Service
@Scope("prototype")
public class RelationTypeService extends EcmObjectService<EcmRelationType>  implements IRelationTypeService{
	private final Logger logger = LoggerFactory.getLogger(RelationTypeService.class);

	public RelationTypeService()
	{
		serviceCode = ServiceContext.RELATIONTYPE_CODE;
		systemPermission = SystemPermission.SYSTEM_CONFIGE;
		logger.info("ServiceCode:"+serviceCode);
	}
	
	/**
	 * 数据访问
	 */
	@Autowired
	private EcmRelationTypeMapper ecmRelationTypeMapper;


	@Override
	public List<EcmRelationType> getAllObject(String token) throws EcmException {
		// TODO Auto-generated method stub
		return (List<EcmRelationType>) ecmRelationTypeMapper.selectAll();
	}

	@Override
	public EcmRelationType getObjectById(String token, String id) throws EcmException {
		// TODO Auto-generated method stub
		return (EcmRelationType) ecmRelationTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	/**
	 * 类型名*表示所有
	 */
	public boolean updateObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+3,systemPermission);
		return ecmRelationTypeMapper.updateByPrimaryKey((com.ecm.core.entity.EcmRelationType) obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+4,systemPermission);
		return ecmRelationTypeMapper.deleteByPrimaryKey(((com.ecm.core.entity.EcmRelationType)obj).getId())>0;
	}

	@Override
	/**
	 * 类型名*表示所有
	 */
	public String newObject(String token, Object obj) throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		super.hasPermission(token, serviceCode+2,systemPermission);
		((com.ecm.core.entity.EcmRelationType)obj).createId();
		ecmRelationTypeMapper.insert((com.ecm.core.entity.EcmRelationType)obj);
		return ((com.ecm.core.entity.EcmRelationType)obj).getId();
	}
	
	
	@Override
	public List<EcmRelationType> getRelationByParentType(String typeName) throws EcmException {
		// TODO Auto-generated method stub
		String condition = "PARENT_TYPE='"+typeName+"' or PARENT_TYPE='*'";
		return (List<EcmRelationType>) ecmRelationTypeMapper.selectByCondition(condition);
	}
	
	@Override
	public List<EcmRelationType> getRelationByChildType(String typeName) throws EcmException {
		// TODO Auto-generated method stub
		String condition = "CHILD_TYPE='"+typeName+"' or CHILD_TYPE='*'";
		return (List<EcmRelationType>) ecmRelationTypeMapper.selectByCondition(condition);
	}
	

}
