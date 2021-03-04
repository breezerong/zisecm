package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmStorageColumn;
@Component
@Mapper
public interface EcmStorageColumnMapper {
	
	List<EcmStorageColumn> selectByParent(String parentCoding);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmStorageColumn record);

    int insertSelective(EcmStorageColumn record);

    EcmStorageColumn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStorageColumn record);

    int updateByPrimaryKey(EcmStorageColumn record);
    
    List<Map<String,Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
}