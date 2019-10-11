package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmDefType;
@Component
@Mapper
public interface EcmDefTypeMapper {
	
	List<EcmDefType> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmDefType record);

    int insertSelective(EcmDefType record);

    EcmDefType selectByPrimaryKey(String id);
    
    EcmDefType selectByName(String name);

    int updateByPrimaryKeySelective(EcmDefType record);

    int updateByPrimaryKey(EcmDefType record);
}