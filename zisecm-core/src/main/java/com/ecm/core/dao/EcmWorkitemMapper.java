package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmWorkitem;
@Component
@Mapper
public interface EcmWorkitemMapper {
	
	List<EcmWorkitem> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmWorkitem record);

    int insertSelective(EcmWorkitem record);

    EcmWorkitem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmWorkitem record);

    int updateByPrimaryKey(EcmWorkitem record);
}