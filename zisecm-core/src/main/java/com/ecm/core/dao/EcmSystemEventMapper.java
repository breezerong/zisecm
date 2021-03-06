package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmSystemEvent;
@Component
@Mapper
public interface EcmSystemEventMapper {
	
	List<EcmSystemEvent> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmSystemEvent record);

    int insertSelective(EcmSystemEvent record);

    EcmSystemEvent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmSystemEvent record);

    int updateByPrimaryKey(EcmSystemEvent record);
}