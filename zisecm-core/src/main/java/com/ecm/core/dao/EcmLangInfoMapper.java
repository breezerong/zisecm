package com.ecm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.Pager;

public interface EcmLangInfoMapper {
    int deleteByPrimaryKey(String id);
    
    List<EcmLangInfo> selectAll();
    
    EcmLangInfo selectByMsgKey(String msgKey);
    
    List<EcmLangInfo> selectByCondition(@Param(value="condition")String condition, Pager pager);

    int insert(EcmLangInfo record);

    int insertSelective(EcmLangInfo record);

    EcmLangInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmLangInfo record);

    int updateByPrimaryKey(EcmLangInfo record);
}