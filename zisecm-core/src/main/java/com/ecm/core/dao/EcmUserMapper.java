package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.Pager;
import com.ecm.core.entity.EcmUser;
@Component
@Mapper
public interface EcmUserMapper {
    
	int deleteByPrimaryKey(String id);

	int insert(EcmUser record);

	int insertSelective(EcmUser record);

    EcmUser selectByPrimaryKey(String id);
    
    EcmUser selectByLoginName(String loginName);

    int updateByPrimaryKeySelective(EcmUser record);

    int updateByPrimaryKey(EcmUser record);
    
    List<EcmUser> searchToEntity(Pager pager,@Param(value="sqlStr") String sqlStr);
    
    List<EcmUser> searchToEntity(@Param(value="sqlStr") String sqlStr);
    
    List<Map<String, Object>> searchToMap(Pager pager,@Param(value="sqlStr") String sqlStr);
    
    List<Map<String, Object>> searchToMap(@Param(value="sqlStr") String sqlStr);
    
    List<EcmUser> selectByGroupId(String goupId);
    
    List<EcmUser> searchNoDeptUsers();
}