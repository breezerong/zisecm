package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmMenu;

public interface EcmMenuMapper {
	
	List<EcmMenu> selectByCondition(@Param(value="condition")String condition);
	
    List<EcmMenu> selectAll();
    
    int deleteByPrimaryKey(String id);

    int insert(EcmMenu record);

    int insertSelective(EcmMenu record);

    EcmMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmMenu record);

    int updateByPrimaryKey(EcmMenu record);
}