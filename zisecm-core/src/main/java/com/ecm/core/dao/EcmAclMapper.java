package com.ecm.core.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAcl;

@Component
@Mapper
public interface EcmAclMapper {

	int deleteByPrimaryKey(String id);

	int insert(EcmAcl record);

	int insertSelective(EcmAcl record);

    EcmAcl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmAcl record);

    int updateByPrimaryKey(EcmAcl record);
    
    List<EcmAcl> searchToEntity(@Param(value="sqlStr") String sqlStr);
}