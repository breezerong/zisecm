package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmQuery;
@Component
@Mapper
public interface EcmQueryMapper {
    List<EcmQuery> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmQuery record);

    int insertSelective(EcmQuery record);

    EcmQuery selectByPrimaryKey(String id);

    EcmQuery selectByName(String name);

    int updateByPrimaryKeySelective(EcmQuery record);

    int updateByPrimaryKey(EcmQuery record);
    
    List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
}