package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmFolder;
@Component
@Mapper
public interface EcmFolderMapper {
    int deleteByPrimaryKey(String id);

    int insert(EcmFolder record);

    int insertSelective(EcmFolder record);

    EcmFolder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmFolder record);

    int updateByPrimaryKey(EcmFolder record);
    
    List<EcmFolder> selectByParentId(String parentId);
    
    //List<EcmFolder> selectByParentIdType(Integer id,String type);
    List<EcmFolder> selectByCondition(@Param(value="condition")String condition);
    
    List<Map<String, Object>> searchToMap(@Param(value="sqlStr") String sqlStr);
}