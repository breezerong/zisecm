package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmStorageRoom;

public interface EcmStorageRoomMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmStorageRoom record);

    int insertSelective(EcmStorageRoom record);

    EcmStorageRoom selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStorageRoom record);

    int updateByPrimaryKey(EcmStorageRoom record);
    
    List<Map<String,Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
}