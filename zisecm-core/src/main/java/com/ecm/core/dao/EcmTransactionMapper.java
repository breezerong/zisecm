package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmTransaction;

public interface EcmTransactionMapper {
	
	List<EcmTransaction> selectByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmTransaction record);

    int insertSelective(EcmTransaction record);

    EcmTransaction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmTransaction record);

    int updateByPrimaryKey(EcmTransaction record);
}