package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmDefAttribute;
@Component
@Mapper
public interface EcmDefAttributeMapper {
	
    List<EcmDefAttribute> selectByCondition(@Param(value="condition")String condition);
    
    int deleteByPrimaryKey(String id);
    
    int deleteByTypeId(String typeId);

    int insert(EcmDefAttribute record);

    int insertSelective(EcmDefAttribute record);

    EcmDefAttribute selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmDefAttribute record);

    int updateByPrimaryKey(EcmDefAttribute record);
}