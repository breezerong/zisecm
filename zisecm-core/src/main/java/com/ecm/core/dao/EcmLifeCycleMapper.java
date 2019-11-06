package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmLifeCycle;
@Component
@Mapper
public interface EcmLifeCycleMapper {
	public EcmLifeCycle selectAll();
	public EcmLifeCycle selectByPrimaryKey(String id);
	
	public EcmLifeCycle selectByName(String name);
	
	public List<Map<String,String>> selectEcmLifeCycleBySql(@Param(value="sqlStr") String sqlStr);
	
	public int deleteByPrimaryKey(String id);
	
	public int insert(EcmLifeCycle lifecycle);

	
	public int insertSelective(EcmLifeCycle lifecycle);


	public int updateByPrimaryKeySelective(EcmLifeCycle lifecycle);

	public int updateByPrimaryKey(EcmLifeCycle lifecycle);


}
