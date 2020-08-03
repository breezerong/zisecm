package com.ecm.cnpe.exchange.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.dao.EcmGroupItemMapper;
import com.ecm.core.dao.EcmGroupMapper;
import com.ecm.core.dao.EcmGroupUserMapper;
import com.ecm.core.dao.EcmUserMapper;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.Pager;
import com.ecm.core.service.GroupService;
import com.ecm.core.service.UserService;

@Service
@Scope("prototype")
public class GroupServiceExchange  {
	
	@Autowired
	private EcmGroupMapper ecmGroupMapper;
	
	@Autowired
	private EcmUserMapper ecmUserMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EcmGroupItemMapper ecmGroupItemMapper;
	
	@Autowired
	private EcmGroupUserMapper ecmGroupUserMapper;
	
	public List<EcmGroup> getGroups(String token,String userId, String type, Pager pager, String condition) {
		
		String sql="select * from( select distinct a.* from ecm_group a,ecm_group_user b where a.id=b.group_id and b.user_id='"+userId+"' " + 
				"union " + 
				"select * from ecm_group where name like '分包商%') t where 1=1 ";
		
		if(!StringUtils.isEmpty(type))
		{
			sql += " and GROUP_TYPE='"+type+"'";
		}
		
		if(!EcmStringUtils.isEmpty(condition))
		{
			sql += " and ("+condition+")";
		}
		
		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql, pager);
		return list;
	}
	
	
	public List<EcmGroup> getAllGroups(String token,String userId, String type) {
		String sql="select * from( select distinct a.* from ecm_group a,ecm_group_user b where a.id=b.group_id and b.user_id='"+userId+"'" + 
				"union" + 
				"select * from ecm_group where name like '分包商%') t where 1=1 ";
		
		if(!StringUtils.isEmpty(type))
		{
			sql += " and GROUP_TYPE='"+type+"'";
		}
		
		
		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql);
		return list;
	}
	
}
