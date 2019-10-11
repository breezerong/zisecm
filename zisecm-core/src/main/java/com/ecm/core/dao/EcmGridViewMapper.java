package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmRelationType;
@Component
@Mapper
public interface EcmGridViewMapper {
	
	List<EcmGridView> selectByCondition(@Param(value="condition")String condition);
    
	List<EcmGridView> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmGridView record);

    int insertSelective(EcmGridView record);

    EcmGridView selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmGridView record);

    int updateByPrimaryKey(EcmGridView record);
}