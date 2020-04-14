package com.ecm.core.dao;

import java.util.List;

import com.ecm.core.entity.EcmCfgActivity;

public interface EcmCfgActivityMapper {
	
	List<EcmCfgActivity> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmCfgActivity record);

    int insertSelective(EcmCfgActivity record);

    EcmCfgActivity selectByPrimaryKey(String id);
    
    List<EcmCfgActivity> selectByProcessId(String processId);

    int updateByPrimaryKeySelective(EcmCfgActivity record);

    int updateByPrimaryKey(EcmCfgActivity record);
}