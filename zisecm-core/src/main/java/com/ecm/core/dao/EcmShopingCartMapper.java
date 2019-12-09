package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmShopingCart;

@Component
@Mapper
public interface EcmShopingCartMapper {
	
	List<EcmShopingCart> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmShopingCart record);

    int insertSelective(EcmShopingCart record);

    EcmShopingCart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmShopingCart record);

    int updateByPrimaryKey(EcmShopingCart record);
    
    List<Map<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);

}