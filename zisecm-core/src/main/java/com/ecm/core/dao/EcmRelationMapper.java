package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmRelation;
@Component
@Mapper
public interface EcmRelationMapper {
    
	List<EcmRelation> selectByCondition(@Param(value="condition")String condition);
	
	int deleteByPrimaryKey(String id);

	int insert(EcmRelation record);

	int insertSelective(EcmRelation record);

    EcmRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmRelation record);

    int updateByPrimaryKey(EcmRelation record);
}