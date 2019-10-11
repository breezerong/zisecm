package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmRelationType;
@Component
@Mapper
public interface EcmRelationTypeMapper {
	
	List<EcmRelationType> selectByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmRelationType record);

    int insertSelective(EcmRelationType record);

    EcmRelationType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmRelationType record);

    int updateByPrimaryKey(EcmRelationType record);

	List<EcmRelationType> selectAll();
}