package com.ecm.core.dao;

import java.util.List;

import com.ecm.core.entity.EcmSuggestion;

public interface EcmSuggestionMapper {
	
	List<EcmSuggestion> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmSuggestion record);

    int insertSelective(EcmSuggestion record);

    EcmSuggestion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmSuggestion record);

    int updateByPrimaryKey(EcmSuggestion record);
}