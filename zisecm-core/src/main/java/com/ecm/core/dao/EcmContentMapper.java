package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmContent;
@Component
@Mapper
public interface EcmContentMapper {
    
	int deleteByPrimaryKey(String id);
	
	int deleteByDocument(String id);
	
	Long selectDataTicket(@Param(value="sqlStr")String sqlStr);

	int insert(EcmContent record);

	int insertSelective(EcmContent record);

    EcmContent selectByPrimaryKey(String id);
    List<EcmContent> selectByCondition(@Param(value="condition")String condition);
    
    int updateByPrimaryKeySelective(EcmContent record);

    int updateByPrimaryKey(EcmContent record);
    
    List<EcmContent> getContents(String docId,int contentType);
    
    List<EcmContent> getAllContents(String docId);
    
}