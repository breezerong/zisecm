package com.ecm.core.dao;

import java.util.List;

import com.ecm.core.entity.EcmLanguage;

public interface EcmLanguageMapper {
	List<EcmLanguage> selectAll();
	 
    int deleteByPrimaryKey(String id);

    int insert(EcmLanguage record);

    int insertSelective(EcmLanguage record);

    EcmLanguage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmLanguage record);

    int updateByPrimaryKey(EcmLanguage record);
}