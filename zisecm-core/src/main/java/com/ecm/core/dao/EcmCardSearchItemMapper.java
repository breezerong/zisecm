package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmCardSearchItem;
@Component
@Mapper
public interface EcmCardSearchItemMapper {

    List<EcmCardSearchItem> selectByParentId(String parentId);

    List<EcmCardSearchItem> selectAll();

    int deleteByPrimaryKey(String id);

    int insert(EcmCardSearchItem record);

    int insertSelective(EcmCardSearchItem record);

    EcmCardSearchItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmCardSearchItem record);

    int updateByPrimaryKey(EcmCardSearchItem record);
}