package com.ecm.icore.service;

import java.util.List;

import com.ecm.core.entity.EcmUiRelation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-22
 */
public interface IEcmUiRelationService {
	public List<EcmUiRelation> getAllObject(String token);
	
	public List<EcmUiRelation> selectByCondition(String condition);
	
	public EcmUiRelation getObjectById(String id);
	
	public String newObject(EcmUiRelation obj);
	
	public boolean updateObject(EcmUiRelation obj);
	
	public boolean deleteObject(EcmUiRelation obj);
	
	public List<EcmUiRelation> selectAll();
}
