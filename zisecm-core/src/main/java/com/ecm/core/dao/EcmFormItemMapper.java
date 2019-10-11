package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmFormItem;
@Component
@Mapper
public interface EcmFormItemMapper {

    
	List<EcmFormItem> selectAll();

    List<EcmFormItem> selectByParentId(String id);

    int deleteByPrimaryKey(String id);

    int insert(EcmFormItem record);

    int insertSelective(EcmFormItem record);

    EcmFormItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmFormItem record);

    int updateByPrimaryKey(EcmFormItem record);
}