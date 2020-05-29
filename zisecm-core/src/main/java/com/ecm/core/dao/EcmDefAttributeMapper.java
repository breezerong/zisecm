package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmDefAttribute;

public interface EcmDefAttributeMapper {
	
	List<EcmDefAttribute> selectByTypeId(String typeId);
	
	int deleteByTypeId(String typeId);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmDefAttribute record);

    int insertSelective(EcmDefAttribute record);

    EcmDefAttribute selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmDefAttribute record);

    int updateByPrimaryKey(EcmDefAttribute record);
}