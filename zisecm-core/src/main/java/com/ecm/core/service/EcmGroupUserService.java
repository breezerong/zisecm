package com.ecm.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmGroupUserMapper;
import com.ecm.core.entity.EcmGroupUser;
@Service
public class EcmGroupUserService{
	
	@Autowired
	private EcmGroupUserMapper ecmGroupUserMapper;
	
	public List<Map<String, Object>> getListMap(String sqlStr){
		return ecmGroupUserMapper.executeSql(sqlStr);
	}
	
	public boolean updateObj(EcmGroupUser record) {
		ecmGroupUserMapper.updateByPrimaryKeySelective(record);
		return true;
	}
	
	public boolean newObj(EcmGroupUser record) {
		ecmGroupUserMapper.insertSelective(record);
		return true;
	}
}
