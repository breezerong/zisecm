package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmStore;
@Component
@Mapper
public interface EcmStoreMapper  {
	
	List<EcmStore> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmStore record);

    int insertSelective(EcmStore record);

    EcmStore selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStore record);

    int updateByPrimaryKey(EcmStore record);
}