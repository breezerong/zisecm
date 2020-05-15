package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmStorageRow;
import com.ecm.core.entity.Pager;

public interface EcmStorageRowMapper {
	List<EcmStorageRow> selectByParent(String parentCoding);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmStorageRow record);

    int insertSelective(EcmStorageRow record);

    EcmStorageRow selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmStorageRow record);

    int updateByPrimaryKey(EcmStorageRow record);
    
    List<Map<String,Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
    
    List<Map<String, Object>> executeSQL(Pager pager, @Param(value="sqlStr") String sqlStr);
}