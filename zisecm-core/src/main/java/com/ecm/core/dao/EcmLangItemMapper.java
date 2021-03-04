package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmLangItem;
@Component
@Mapper
public interface EcmLangItemMapper {
	
	List<EcmLangItem> selectAll();
	
	List<EcmLangItem> selectByMessageKey(String msgKey);
	
	int deleteByMessageKey(String msgKey);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmLangItem record);

    int insertSelective(EcmLangItem record);

    EcmLangItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmLangItem record);

    int updateByPrimaryKey(EcmLangItem record);
}