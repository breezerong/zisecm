package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmGroupItem;
@Component
@Mapper
public interface EcmGroupItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmGroupItem record);

    int insertSelective(EcmGroupItem record);

    EcmGroupItem selectByPrimaryKey(String id);
    
    List<EcmGroupItem> selectParent(String id);

    int updateByPrimaryKeySelective(EcmGroupItem record);

    int updateByPrimaryKey(EcmGroupItem record);
    
    List<Map<String, Object>> executeSql(@Param(value="sqlStr") String sqlStr);
}