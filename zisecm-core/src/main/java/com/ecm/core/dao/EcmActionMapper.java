package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmAction;

@Component
@Mapper
public interface EcmActionMapper {

   	List<EcmAction> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmAction record);

    int insertSelective(EcmAction record);

    EcmAction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmAction record);

    int updateByPrimaryKey(EcmAction record);
}