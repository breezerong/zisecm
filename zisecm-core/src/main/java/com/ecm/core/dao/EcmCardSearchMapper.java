package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmCardSearch;
@Component
@Mapper
public interface EcmCardSearchMapper {

    List<EcmCardSearch> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmCardSearch record);

    int insertSelective(EcmCardSearch record);

    EcmCardSearch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmCardSearch record);

    int updateByPrimaryKey(EcmCardSearch record);
}