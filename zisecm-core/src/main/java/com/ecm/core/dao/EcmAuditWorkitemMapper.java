package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAuditWorkitem;
@Component
@Mapper
public interface EcmAuditWorkitemMapper {
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<EcmAuditWorkitem> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition")String condition);

	int deleteByPrimaryKey(String id);

	int insert(EcmAuditWorkitem record);

	int insertSelective(EcmAuditWorkitem record);

    EcmAuditWorkitem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmAuditWorkitem record);

    int updateByPrimaryKey(EcmAuditWorkitem record);
}