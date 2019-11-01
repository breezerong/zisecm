package com.ecm.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.EcmLifeCycleItem;
@Component
@Mapper
public interface EcmLifeCycleItemMapper {
	/**
	 * 通过状态流Id和节点名称获取下一步事件
	 * @param lifecycleId状态流ID
	 * @param itemName 状态节点
	 * @return
	 */
	public String getNextImpleClassByLfId(String lifecycleId,String itemName);
	/**
	 * 通过状态流Id和节点名称获取上一步事件
	 * @param lifecycleId状态流ID
	 * @param itemName 状态节点
	 * @return
	 */
	public String getPreviousImpleClassByLfId(String lifecycleId,String itemName);
	/**
	 * 通过状态流名称和节点名称获取下一步事件
	 * @param lifecycleName状态流名称
	 * @param itemName 状态节点
	 * @return
	 */
	public String getNextImpleClassByName(String lifecycleName,String itemName);
	/**
	 * 通过状态流名称和节点名称获取上一步事件
	 * @param lifecycleName状态流名称
	 * @param itemName 状态节点
	 * @return
	 */
	public String getPreviousImpleClassByName(String lifecycleName,String itemName);
	public List<EcmLifeCycleItem> selectAll();
	public EcmLifeCycleItem selectByPrimaryKey(String id);
	
	public List<EcmLifeCycleItem> selectByLifeCycleId(String lifecycleId);
	
	public List<Map<String,String>> selectEcmLifeCycleBySql(@Param(value="sqlStr") String sqlStr);
	
	public int deleteByPrimaryKey(String id);
	public int deleteByLifeCycleId(String lifecycleId);
	
	public int insert(EcmLifeCycleItem lifecycleItem);

	
	public int insertSelective(EcmLifeCycleItem lifecycleItem);


	public int updateByPrimaryKeySelective(EcmLifeCycleItem lifecycleItem);

	public int updateByPrimaryKey(EcmLifeCycleItem lifecycleItem);


}
