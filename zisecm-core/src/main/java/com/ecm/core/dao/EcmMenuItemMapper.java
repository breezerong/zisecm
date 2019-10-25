package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmMenuItem;
@Component
@Mapper
public interface EcmMenuItemMapper {

	List<EcmMenuItem> selectByCondition(@Param(value="condition")String condition);
	
    List<EcmMenuItem> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmMenuItem record);

    int insertSelective(EcmMenuItem record);

    EcmMenuItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmMenuItem record);

    int updateByPrimaryKey(EcmMenuItem record);
}