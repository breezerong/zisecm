package com.ecm.core.service;

import java.util.List;
import java.util.Map;

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
		return deleteObject(((com.ecm.core.entity.EcmRelation)obj).getId());
	}
	
	public boolean deleteObject(String id) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.deleteByPrimaryKey(id)>0;
	}
	
	@Override
	public String newObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		((com.ecm.core.entity.EcmRelation)obj).createId();
		List<Map<String, Object>> c= ecmRelationMapper.executeSQL("select count(*) as cnum from ecm_relation where parent_id='"
		+((com.ecm.core.entity.EcmRelation)obj).getParentId()+"' and name='"
				+((com.ecm.core.entity.EcmRelation)obj).getName()+"'");
		int orderIndex=Integer.parseInt(c.get(0).get("cnum").toString());
		((com.ecm.core.entity.EcmRelation)obj).setOrderIndex(orderIndex+1);
		ecmRelationMapper.insert((com.ecm.core.entity.EcmRelation)obj);
		return ((com.ecm.core.entity.EcmRelation)obj).getId();
	}
	
	@Override
	public List<Map<String, Object>> getMapList(String token, String sql) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.executeSQL(sql);
	}
}
