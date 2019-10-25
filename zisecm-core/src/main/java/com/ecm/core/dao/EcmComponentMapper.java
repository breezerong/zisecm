package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmComponent;
@Component
@Mapper
public interface EcmComponentMapper {

	List<EcmComponent> selectByCondition(@Param(value="condition")String condition);
	
    List<EcmComponent> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmComponent record);

    int insertSelective(EcmComponent record);

    EcmComponent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmComponent record);

    int updateByPrimaryKey(EcmComponent record);
}