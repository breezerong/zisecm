package com.ecm.core.dao;

import com.ecm.core.entity.EcmStorageRoom;

public interface EcmStorageRoomMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmStorageRoom record);

    int insertSelective(EcmStorageRoom record);

    EcmStorageRoom selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStorageRoom record);

    int updateByPrimaryKey(EcmStorageRoom record);
}