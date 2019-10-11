package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAuditPermission;
@Component
@Mapper
public interface EcmAuditPermissionMapper {
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<EcmAuditPermission> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmAuditPermission record);

    int insertSelective(EcmAuditPermission record);

    EcmAuditPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmAuditPermission record);

    int updateByPrimaryKey(EcmAuditPermission record);
}