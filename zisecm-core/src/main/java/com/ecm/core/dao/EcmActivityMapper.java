package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmActivity;
@Component
@Mapper
public interface EcmActivityMapper {
	
	List<EcmActivity> selectByCondition(@Param(value="condition")String condition);
	
    int deleteByPrimaryKey(String id);

    int insert(EcmActivity record);

    int insertSelective(EcmActivity record);

    EcmActivity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmActivity record);

    int updateByPrimaryKey(EcmActivity record);
}