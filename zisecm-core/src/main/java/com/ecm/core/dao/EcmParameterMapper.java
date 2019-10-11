package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmParameter;
@Component
@Mapper
public interface EcmParameterMapper {
    
	List<EcmParameter> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmParameter record);

    int insertSelective(EcmParameter record);

    EcmParameter selectByPrimaryKey(String id);

    EcmParameter selectByName(String name);

    int updateByPrimaryKeySelective(EcmParameter record);

    int updateByPrimaryKey(EcmParameter record);
}