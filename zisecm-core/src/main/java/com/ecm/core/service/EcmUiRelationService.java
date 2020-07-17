package com.ecm.core.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.entity.EcmUiRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.dao.EcmUiRelationMapper;
import com.ecm.icore.service.IEcmUiRelationService;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-22
 */
@Service
public class EcmUiRelationService  implements IEcmUiRelationService {
	@Autowired
	private EcmUiRelationMapper mapper;

	@Override
	public List<EcmUiRelation> selectByCondition(String condition) {
		return mapper.selectByCondition(condition);
	}
	
	@Override
	public List<EcmUiRelation> selectByCondition(Pager pager,String condition) {
		return mapper.selectByCondition(pager,condition);
	}
	
	@Override
	public List<EcmUiRelation> getAllObject(String token) {
		return mapper.selectAll();
	}

	@Override
	public EcmUiRelation getObjectById(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public String newObject(EcmUiRelation obj) {
		obj.setId(UUID.randomUUID().toString().replace("-", ""));
		if(mapper.insertSelective(obj)>0) {
			return obj.getId();
		}else {
			return null;
		}
		
	}

	@Override
	public boolean updateObject(EcmUiRelation obj) {
		if(StringUtils.isEmpty(obj.getId())) {
			return false;
		}
		if(mapper.selectByPrimaryKey(obj.getId())!=null) {
			return mapper.updateByPrimaryKeySelective(obj)>0;
		}else {
			return false;
		}
	}

	@Override
	public boolean deleteObject(EcmUiRelation obj) {
		if(StringUtils.isEmpty(obj.getId())) {
			return false;
		}
		if(mapper.selectByPrimaryKey(obj.getId())!=null) {
			return mapper.deleteByPrimaryKey(obj.getId())>0;
		}else {
			return false;
		}
	}

	@Override
	public List<EcmUiRelation> selectAll() {
		return mapper.selectAll();
	}

	@Override
	public EcmUiRelation getObjectByName(String relationName) {
		List<EcmUiRelation> list = mapper.selectByCondition("RELATION_NAME ='"+relationName+"'");
		if(list.size()<1) {
			return null;
		}else {
			return list.get(0);
		}
		
	}

	public EcmUiRelation getObjectByTypeName(String typeName) {
		List<EcmUiRelation> list = mapper.selectByCondition("TYPE_NAME ='"+typeName+"'");
		if(list.size()<1) {
			return null;
		}else {
			return list.get(0);
		}
	}
}
