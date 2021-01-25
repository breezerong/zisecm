package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.Pager;
@Component
@Mapper
public interface EcmGroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmGroup record);

    int insertSelective(EcmGroup record);

    EcmGroup selectByPrimaryKey(String id);
    
    EcmGroup selectByName(String name);

    int updateByPrimaryKeySelective(EcmGroup record);

    int updateByPrimaryKey(EcmGroup record);
    
    List<EcmGroup> selectByParentId(String id);
    
    List<EcmGroup> searchToEntity(@Param(value="sqlStr") String sqlStr, Pager pager);
    
    List<EcmGroup> searchToEntity(@Param(value="sqlStr") String sqlStr);
    
    List<Map<String, Object>> searchToMap(@Param(value="sqlStr") String sqlStr);
    
    List<EcmGroup> selectByCondition(@Param(value="condition")String condition);
    
}