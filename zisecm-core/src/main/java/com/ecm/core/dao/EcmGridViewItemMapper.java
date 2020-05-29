package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmGridViewItem;
@Component
@Mapper
public interface EcmGridViewItemMapper {

    List<EcmGridViewItem> selectAll();

    List<EcmGridViewItem> selectByParentId(String id);

    int deleteByPrimaryKey(String id);
    
    int deleteByParentId(String parentId);
    
    int insert(EcmGridViewItem record);

    int insertSelective(EcmGridViewItem record);

    EcmGridViewItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmGridViewItem record);

    int updateByPrimaryKey(EcmGridViewItem record);
    
    List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
    
    List<EcmGridViewItem> getItemByParam(@Param(value="name") String name,@Param(value="creator") String creator);
    
}