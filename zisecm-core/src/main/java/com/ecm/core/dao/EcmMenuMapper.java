package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmMenu;
@Component
@Mapper
public interface EcmMenuMapper {

    List<EcmMenu> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmMenu record);

    int insertSelective(EcmMenu record);

    EcmMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmMenu record);

    int updateByPrimaryKey(EcmMenu record);
}