package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmRelation;
@Component
@Mapper
public interface EcmRelationMapper {
    
	List<EcmRelation> selectByCondition(@Param(value="condition")String condition);
	
	int deleteByPrimaryKey(String id);
	
	int deleteByChildAndRelationName(String childId,String relationName);
	
	int deleteByCidPidAndRelationName(String parentId,String childId,String relationName);
	
	int insert(EcmRelation record);

	int insertSelective(EcmRelation record);

    EcmRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmRelation record);

    int updateByPrimaryKey(EcmRelation record);
    
    List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
}