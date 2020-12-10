package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmCfgActivity;

public interface EcmCfgActivityMapper {
	
	List<EcmCfgActivity> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmCfgActivity record);

    int insertSelective(EcmCfgActivity record);

    EcmCfgActivity selectByPrimaryKey(String id);
    
    List<EcmCfgActivity> selectByProcessId(String processId);
    
    List<EcmCfgActivity> selectByProcessName(String processName);
    
    List<EcmCfgActivity> selectByCondition(@Param(value="condition") String condition);

    int updateByPrimaryKeySelective(EcmCfgActivity record);

    int updateByPrimaryKey(EcmCfgActivity record);
}