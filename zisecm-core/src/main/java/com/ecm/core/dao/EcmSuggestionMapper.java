package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmSuggestion;
@Component
@Mapper
public interface EcmSuggestionMapper {
	
	List<EcmSuggestion> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmSuggestion record);

    int insertSelective(EcmSuggestion record);

    EcmSuggestion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmSuggestion record);

    int updateByPrimaryKey(EcmSuggestion record);
}