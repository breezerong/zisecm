package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmTransaction;
@Component
@Mapper
public interface EcmTransactionMapper {
	
	List<EcmTransaction> selectByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmTransaction record);

    int insertSelective(EcmTransaction record);

    EcmTransaction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmTransaction record);

    int updateByPrimaryKey(EcmTransaction record);
}