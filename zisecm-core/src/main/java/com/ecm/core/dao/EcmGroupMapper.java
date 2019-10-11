package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmGroup;
@Component
@Mapper
public interface EcmGroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmGroup record);

    int insertSelective(EcmGroup record);

    EcmGroup selectByPrimaryKey(String id);
    
    EcmGroup selectByName(String name);

    int updateByPrimaryKeySelective(EcmGroup record);

    int updateByPrimaryKey(EcmGroup record);
    
    List<EcmGroup> selectByParentId(String id);
    
    List<EcmGroup> searchToEntity(@Param(value="sqlStr") String sqlStr);
    
    List<Map<String, Object>> searchToMap(@Param(value="sqlStr") String sqlStr);
}