package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.Pager;
@Component
@Mapper
public interface EcmDocumentMapper {
    
	int deleteByPrimaryKey(String id);

	int insert(EcmDocument record);

	int insertSelective(EcmDocument record);

    EcmDocument selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmDocument record);

    int updateByPrimaryKey(EcmDocument record);
    
    List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
    
    List<Map<String, Object>> executeSQL(Pager pager, @Param(value="sqlStr") String sqlStr);
}