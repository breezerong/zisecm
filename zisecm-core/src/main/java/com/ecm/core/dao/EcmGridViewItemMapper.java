package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmGridViewItem;
@Component
@Mapper
public interface EcmGridViewItemMapper {

    List<EcmGridViewItem> selectAll();

    List<EcmGridViewItem> selectByParentId(String id);

    int deleteByPrimaryKey(String id);

    int insert(EcmGridViewItem record);

    int insertSelective(EcmGridViewItem record);

    EcmGridViewItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmGridViewItem record);

    int updateByPrimaryKey(EcmGridViewItem record);
}