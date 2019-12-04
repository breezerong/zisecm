package com.ecm.core.dao;

import java.util.List;

import com.ecm.core.entity.EcmShopingCart;

public interface EcmShopingCartMapper {
	
	List<EcmShopingCart> selectAll();
	
    int deleteByPrimaryKey(String id);

    int insert(EcmShopingCart record);

    int insertSelective(EcmShopingCart record);

    EcmShopingCart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EcmShopingCart record);

    int updateByPrimaryKey(EcmShopingCart record);
}