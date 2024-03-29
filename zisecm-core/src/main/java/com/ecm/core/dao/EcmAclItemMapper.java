package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmPermit;
@Component
@Mapper
public interface EcmAclItemMapper {
	
	List<EcmPermit> selectByParentId(String parentId);
	
    int deleteByPrimaryKey(String id);
    
    int deleteByParentId(String parentId);

	int insert(EcmPermit record);

	int insertSelective(EcmPermit record);

	EcmPermit selectByPrimaryKey(String id);
	
	EcmPermit selectByGrantName(@Param("parentId")String parentId, @Param("targetName")String targetName,@Param("targetType")int targetType);

	int updateByPrimaryKeySelective(EcmPermit record);

	int updateByPrimaryKey(EcmPermit record);
}