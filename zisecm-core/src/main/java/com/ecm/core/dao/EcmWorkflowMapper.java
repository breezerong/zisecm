package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmWorkflow;
@Component
@Mapper
public interface EcmWorkflowMapper {
	
	List<EcmWorkflow> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmWorkflow record);

    int insertSelective(EcmWorkflow record);

    EcmWorkflow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmWorkflow record);

    int updateByPrimaryKey(EcmWorkflow record);
}