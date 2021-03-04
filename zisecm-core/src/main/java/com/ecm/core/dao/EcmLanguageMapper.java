package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmLanguage;
@Component
@Mapper
public interface EcmLanguageMapper {
	
	List<EcmLanguage> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmLanguage record);

    int insertSelective(EcmLanguage record);

    EcmLanguage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmLanguage record);

    int updateByPrimaryKey(EcmLanguage record);
}