package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAuditGeneral;

@Component
@Mapper
public interface EcmAuditGeneralMapper {
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<EcmAuditGeneral> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmAuditGeneral record);

    int insertSelective(EcmAuditGeneral record);

    EcmAuditGeneral selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmAuditGeneral record);

    int updateByPrimaryKey(EcmAuditGeneral record);
}