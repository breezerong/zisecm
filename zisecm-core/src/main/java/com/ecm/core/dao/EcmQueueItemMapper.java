package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecm.core.entity.EcmQueueItem;

public interface EcmQueueItemMapper {
	
	
	List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
	
	List<EcmQueueItem> selectByCondition(@Param(value="condition")String condition);
	
	int getCountByCondition(@Param(value="condition") String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmQueueItem record);

    int insertSelective(EcmQueueItem record);

    EcmQueueItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmQueueItem record);

    int updateByPrimaryKey(EcmQueueItem record);
}