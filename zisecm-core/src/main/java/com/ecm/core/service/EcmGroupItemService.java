package com.ecm.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmGroupItemMapper;
import com.ecm.core.dao.EcmGroupUserMapper;
import com.ecm.core.entity.EcmGroupItem;
import com.ecm.core.entity.EcmGroupUser;
@Service
public class EcmGroupItemService {
	@Autowired
	private EcmGroupItemMapper ecmGroupItemMapper ;
	
	public List<Map<String, Object>> getListMap(String sqlStr){
		return ecmGroupItemMapper.executeSql(sqlStr);
	}
	
	
	
	public boolean newObj(EcmGroupItem record) {
		ecmGroupItemMapper.insertSelective(record);
		return true;
	}
}
