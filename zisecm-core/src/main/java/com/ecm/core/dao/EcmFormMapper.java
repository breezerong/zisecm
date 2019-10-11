package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmForm;
@Component
@Mapper
public interface EcmFormMapper {

	List<EcmForm> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmForm record);

    int insertSelective(EcmForm record);

    EcmForm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmForm record);

    int updateByPrimaryKey(EcmForm record);
}