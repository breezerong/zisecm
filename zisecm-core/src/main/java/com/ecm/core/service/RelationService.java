package com.ecm.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmRelationMapper;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.exception.EcmException;


@Service
@Scope("prototype")
public class RelationService extends EcmObjectService<EcmRelation> {
	private final Logger logger = LoggerFactory.getLogger(RelationService.class);

	public RelationService()
	{
		serviceCode = ServiceContext.RELATION_CODE;
		logger.info("ServiceCode:"+serviceCode);
	}
	
	/**
	 * 数据访问
	 */
	@Autowired
	private EcmRelationMapper ecmRelationMapper;


	@Override
	public EcmRelation getObjectById(String token, String id) throws EcmException {
		// TODO Auto-generated method stub
		return (EcmRelation) ecmRelationMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.updateByPrimaryKey((com.ecm.core.entity.EcmRelation) obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.deleteByPrimaryKey(((com.ecm.core.entity.EcmRelation)obj).getId())>0;
	}

	@Override
	public String newObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		((com.ecm.core.entity.EcmRelation)obj).createId();
		ecmRelationMapper.insert((com.ecm.core.entity.EcmRelation)obj);
		return ((com.ecm.core.entity.EcmRelation)obj).getId();
	}

}
