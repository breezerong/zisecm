package com.ecm.core.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmSelectValue;

import java.util.List;
import java.util.Map;
@Component
@Mapper
public interface EcmSelectValueMapper {
    
	List<EcmSelectValue> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmSelectValue record);

    int insertSelective(EcmSelectValue record);

    EcmSelectValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmSelectValue record);

    int updateByPrimaryKey(EcmSelectValue record);

    List<Map<String,String>> selectEcmSelectValuesBySql(@Param(value="sqlStr") String sqlStr);

    class HelpProvider {
        public String createSql(String sql) {
            return sql;
        }
    }
}