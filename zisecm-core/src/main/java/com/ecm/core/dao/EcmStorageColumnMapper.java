package com.ecm.core.dao;

import java.util.List;

import com.ecm.core.entity.EcmStorageColumn;

public interface EcmStorageColumnMapper {
	
	List<EcmStorageColumn> selectByParent(String parentCoding);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmStorageColumn record);

    int insertSelective(EcmStorageColumn record);

    EcmStorageColumn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStorageColumn record);

    int updateByPrimaryKey(EcmStorageColumn record);
}