package com.ecm.core.dao;

import java.util.List;

import com.ecm.core.entity.EcmStorageRow;

public interface EcmStorageRowMapper {
	List<EcmStorageRow> selectByParent(String parentCoding);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmStorageRow record);

    int insertSelective(EcmStorageRow record);

    EcmStorageRow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStorageRow record);

    int updateByPrimaryKey(EcmStorageRow record);
}