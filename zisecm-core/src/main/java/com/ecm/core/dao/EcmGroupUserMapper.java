package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmGroupUser;
@Component
@Mapper
public interface EcmGroupUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmGroupUser record);

    int insertSelective(EcmGroupUser record);

    EcmGroupUser selectByPrimaryKey(String id);
    
    List<EcmGroupUser> selectByParent(String id);
    
    EcmGroupUser selectByGroupUser(String groupId,String userId);

    int updateByPrimaryKeySelective(EcmGroupUser record);

    int updateByPrimaryKey(EcmGroupUser record);
    
    List<Map<String, Object>> executeSql(@Param(value="sqlStr") String sqlStr);
}