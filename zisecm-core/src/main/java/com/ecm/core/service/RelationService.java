package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.ServiceContext;
import com.ecm.core.dao.EcmRelationMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;


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
	
	public boolean updateObjectByPrimaryKeySelective(String token, Object obj) {
		return ecmRelationMapper.updateByPrimaryKeySelective((com.ecm.core.entity.EcmRelation) obj)>0;
	}

	@Override
	public boolean deleteObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.deleteByPrimaryKey(((com.ecm.core.entity.EcmRelation)obj).getId())>0;
	}
	
	public boolean deleteObject(String token, String objId) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.deleteByPrimaryKey(objId)>0;
	}
	/**
	 * 通过childId和relatinName删除
	 * @param childId
	 * @param relationName
	 * @return
	 */
	public boolean deleteByChildIdAndRelationName(String token,String childId,String relationName) throws Exception {
		return ecmRelationMapper.deleteByChildAndRelationName(childId, relationName)>0;
	}
	public boolean deleteAllRelationByParentId(String token,String parentId) throws Exception {
		return ecmRelationMapper.deleteAllRelationByParentId(parentId)>0;
	}
	
	/**
	 * 通过parentId,childId和relatinName删除
	 * @param childId
	 * @param relationName
	 * @return
	 */
	public boolean deleteByCidPidAndRelationName(String token,String parentId,String childId,String relationName) throws Exception {
		return ecmRelationMapper.deleteByCidPidAndRelationName(parentId,childId, relationName)>0;
	}
	
	@Override
	public String newObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
		EcmRelation en =(EcmRelation)obj;
		if(StringUtils.isEmpty(en.getId())) {
			en.createId();
		}
		if(en.getOrderIndex()==0) {
			List<Map<String, Object>> c= ecmRelationMapper.executeSQL("select count(*) as cnum from ecm_relation where parent_id='"
			+((com.ecm.core.entity.EcmRelation)obj).getParentId()+"' and name='"
					+((com.ecm.core.entity.EcmRelation)obj).getName()+"'");
			int orderIndex=Integer.parseInt(c.get(0).get("cnum").toString());
			en.setOrderIndex(orderIndex+1);
		}
		ecmRelationMapper.insert(en);
		return en.getId();
	}
	
	@Override
	public List<Map<String, Object>> getMapList(String token, String sql) throws EcmException {
		// TODO Auto-generated method stub
		return ecmRelationMapper.executeSQL(sql);
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		id = DBFactory.getDBConn().getDBUtils().getString(id);
		return ecmRelationMapper.deleteByPrimaryKey(id)>0;
	}
}
