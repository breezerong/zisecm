package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmProcess;
@Component
@Mapper
public interface EcmProcessMapper {
	
	List<EcmProcess> selectByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmProcess record);

    int insertSelective(EcmProcess record);

    EcmProcess selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmProcess record);

    int updateByPrimaryKey(EcmProcess record);
}