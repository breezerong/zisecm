package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAuditWorkflow;
@Component
@Mapper
public interface EcmAuditWorkflowMapper {
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<EcmAuditWorkflow> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmAuditWorkflow record);

    int insertSelective(EcmAuditWorkflow record);

    EcmAuditWorkflow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmAuditWorkflow record);

    int updateByPrimaryKey(EcmAuditWorkflow record);
}