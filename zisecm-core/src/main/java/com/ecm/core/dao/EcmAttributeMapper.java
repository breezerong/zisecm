package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAttribute;
@Component
@Mapper
public interface EcmAttributeMapper {
	List<EcmAttribute> selectAll();
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
}