package com.ecm.core.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecm.common.util.EcmStringUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmGroupItemMapper;
import com.ecm.core.dao.EcmGroupMapper;
import com.ecm.core.dao.EcmGroupUserMapper;
import com.ecm.core.dao.EcmUserMapper;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmGroupItem;
import com.ecm.core.entity.EcmGroupUser;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.util.DBUtils;
import com.ecm.icore.service.IGroupService;

/**
 * @ClassName  GroupServiceImpl   
 * @Description TODO(组服务类)   
 * @author Haihong Rong
 * @date 2018年8月19日 上午9:56:51  
 *
 */
@Service
@Scope("prototype")
public class GroupService implements IGroupService {
	
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

	@Override
	public long getChildGroupCount(String token,String id) {
		String sql = "select count(*) as itemcount from ecm_group where parent_id='"+id+"'";
		List<Map<String, Object>> list = ecmGroupMapper.searchToMap(sql);
		return (long) list.get(0).get("itemcount");
	}

	@Override
	public long getUserCount(String token,String id) {
		String sql = "select count(*) as itemcount from ecm_group_user where GROUP_ID='"+id+"'";
		List<Map<String, Object>> list = ecmGroupMapper.searchToMap(sql);
		return (long) list.get(0).get("itemcount");
	}

	@Override
	public List<EcmUser> getUsers(String token,String id) {
		// TODO Auto-generated method stub
		return ecmUserMapper.selectByGroupId(id);
	}

	@Override
	public List<EcmGroup> getChildGroup(String token,String id) {
		// TODO Auto-generated method stub
		return ecmGroupMapper.selectByParentId(id);
	}

	
	@Override
	public boolean isMemberOfGroup(String token,String userName, String groupName) {
		// TODO Auto-generated method stub
		EcmGroup group = ecmGroupMapper.selectByName(groupName);
		if(group!=null) {
			List<EcmUser> users = ecmUserMapper.selectByGroupId(group.getId());
			for(EcmUser u:users) {
				if(u.getName().equals(userName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<EcmGroup> getGroups(String token,String parentId, String type, Pager pager, String condition) {
		String sql = "select ID, NAME, DESCRIPTION, CODING, CREATION_DATE, CREATOR, MODIFIER, MODIFIED_DATE, GROUP_TYPE,PARENT_ID from ecm_group where 1=1 ";
		if(!StringUtils.isEmpty(type))
		{
			sql += " and GROUP_TYPE='"+type+"'";
		}
		if(!StringUtils.isEmpty(parentId))
		{
			sql += " and PARENT_ID='"+parentId+"'";
		}
		if(!EcmStringUtils.isEmpty(condition))
		{
			sql += " and ("+condition+")";
		}
		
		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql, pager);
		return list;
	}
	
	@Override
	public List<EcmGroup> getAllGroups(String token,String parentId, String type) {
		String sql = "select ID, NAME, DESCRIPTION, CODING, CREATION_DATE, CREATOR, MODIFIER, MODIFIED_DATE, GROUP_TYPE,PARENT_ID from ecm_group where 1=1 ";
		sql += " and GROUP_TYPE='"+type+"'";
		if(!StringUtils.isEmpty(parentId))
		{
			sql += " and PARENT_ID='"+parentId+"'";
		}
		sql += " order by NAME";
		
		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql);
		return list;
	}
	
	@Override
	public List<EcmGroup> getUserGroupsById(String token,String userId) {
		
		String sql = "select a.* from ecm_group a, ecm_group_user b "
				+ " where a.ID = b.GROUP_ID and b.USER_ID='"+DBUtils.getString(userId)+"'";
		
		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql);
		return list;
	}
	
	@Override
	public List<EcmGroup> getUserGroupsByName(String token,String userName) {
		
		String sql = "select a.* from ecm_group a, ecm_group_user b, ecm_user c where "
				+ " a.ID = b.GROUP_ID and b.USER_ID=c.ID and c.NAME='"+DBUtils.getString(userName)+"'";
		
		List<EcmGroup> list = ecmGroupMapper.searchToEntity(sql);
		return list;
	}

	@Override
	public long getGroupCount(String token,String id, String type, String condition) {
		String sql = "select count(*) as itemcount from ecm_group where";
		sql += " GROUP_TYPE='"+type+"'";
		if(!StringUtils.isEmpty(id))
		{
			sql += " and PARENT_ID='"+id+"'";
		}
		if(!EcmStringUtils.isEmpty(condition))
		{
			sql += " and ("+condition+")";
		}
		return Long.parseLong(ecmGroupMapper.searchToMap(sql).get(0).get("itemcount").toString());
	}

	@Override
	public EcmGroup getGroup(String token,String id) {
		// TODO Auto-generated method stub
		return ecmGroupMapper.selectByPrimaryKey(id);
	}
	
	@Override
	@Transactional
	public boolean addUserToGroup(String token,String userId,String groupId) throws Exception {
		EcmGroup group = ecmGroupMapper.selectByPrimaryKey(groupId);
		if(group==null)
			throw new Exception("Department :"+groupId+" is not exists.");
		EcmGroupItem item = new EcmGroupItem();
		item.createId();
		item.setItemType("2");
		item.setParentId(groupId);
		item.setChildId(userId);
		ecmGroupItemMapper.insert(item);
		EcmGroupUser gu = ecmGroupUserMapper.selectByGroupUser(groupId, userId);
		if(gu==null) {
			//添加到所有用户
			gu = new EcmGroupUser();
			gu.createId();
			gu.setGroupId(groupId);
			gu.setUserId(userId);
			ecmGroupUserMapper.insert(gu);
		}
		//添加用户到上级部门
		addUserToParentGroup(groupId, userId);
		if(group.getGroupType().equals("1"))
		{
			userService.updateUserDepartment(token,userId,group.getId(),group.getName());
		}
		return true;
	}
	/**
	 * 添加用户到上级角色
	 * @param groupId
	 * @param userId
	 */
	private void addUserToParentGroup(String groupId,String userId) {
		List<EcmGroupItem> list = ecmGroupItemMapper.selectParent(groupId);
		for(EcmGroupItem item:list) {
			EcmGroupUser gu = ecmGroupUserMapper.selectByGroupUser(item.getId(), userId);
			if(gu==null) {
				//添加到所有用户
				gu = new EcmGroupUser();
				gu.createId();
				gu.setGroupId(item.getId());
				gu.setUserId(userId);
				ecmGroupUserMapper.insert(gu);
			}
			addUserToParentGroup(item.getId(),userId);
		}
	}
	
	@Override
	@Transactional
	public boolean removeUserFromGroup(String token,String userId,String groupId) throws Exception {
		EcmGroup group = ecmGroupMapper.selectByPrimaryKey(groupId);
		if(group==null)
			throw new Exception("Group :"+groupId+" is not exists.");
		String sql = "delete from ecm_group_item where item_type='2' and PARENT_ID="+groupId+" and CHILD_ID="+userId;
		ecmGroupItemMapper.executeSql(sql);
		//从所有用户中移除，嵌套组后续处理
		sql = "delete from ecm_group_user where GROUP_ID="+groupId+" and USER_ID="+userId;
		ecmGroupUserMapper.executeSql(sql);
		//移除上级组、角色用户
		removeUserFromParent( groupId, userId);
		if(group.getGroupType().equals("1"))
		{
			userService.updateUserDepartment(token, userId,"","");
		}
		return true;
	}
	
	/**
	 * 移除上级角色用户
	 * @param groupId
	 * @param userId
	 */
	private void removeUserFromParent(String groupId,String userId) {
		List<EcmGroupItem> list = ecmGroupItemMapper.selectParent(groupId);
		for(EcmGroupItem item:list) {
			String sql = "delete from ecm_group_user where GROUP_ID="+item.getId()+" and USER_ID="+userId;
			sql += " and not exits(select 1 from ecm_group_item where ITEM_TYPE='2' and PARENT_ID="+item.getId()+" and CHILD_ID="+userId+")";
			ecmGroupUserMapper.executeSql(sql);
			removeUserFromParent(item.getId(),userId);
		}
	}
	
	@Override
	public boolean deleteGroup(String token,String id) {
		// TODO Auto-generated method stub
		return ecmGroupMapper.deleteByPrimaryKey(id)>0;
	}
	@Override
	public boolean updateGroup(String token,EcmGroup obj) {
		// TODO Auto-generated method stub
		return ecmGroupMapper.updateByPrimaryKey(obj)>0;
	}
	@Override
	public boolean newGroup(String token,EcmGroup obj) {
		// TODO Auto-generated method stub
		obj.createId();
		return ecmGroupMapper.insert(obj)>0;
	}

	@Transactional
	@Override
	public boolean addUserToRole(String token,String userId, String roleId) throws Exception {
		EcmGroup group = ecmGroupMapper.selectByPrimaryKey(roleId);
		if(group==null) {
			throw new Exception("Role :"+roleId+" is not exists.");
		}
		EcmGroupItem item = new EcmGroupItem();
		item.createId();
		item.setItemType("2");
		item.setParentId(roleId);
		item.setChildId(userId);
		ecmGroupItemMapper.insert(item);
		
		EcmGroupUser gu = ecmGroupUserMapper.selectByGroupUser(roleId, userId);
		if(gu==null) {
			//添加到所有用户
			gu = new EcmGroupUser();
			gu.createId();
			gu.setGroupId(roleId);
			gu.setUserId(userId);
			ecmGroupUserMapper.insert(gu);
		}

		return true;
	}

	@Transactional
	@Override
	public boolean addRole(String token,String parentRoleId, String childRoleId) throws Exception {
		//不允许循环嵌套
		if(exitsParent(childRoleId)) {
			throw new Exception("Exist in Parent Role :"+childRoleId+".");
		}
		EcmGroup parent = ecmGroupMapper.selectByPrimaryKey(parentRoleId);
		if(parent==null) {
			throw new Exception("Parent Role :"+parentRoleId+" is not exists.");
		}
		
		EcmGroup child = ecmGroupMapper.selectByPrimaryKey(childRoleId);
		if(child==null) {
			throw new Exception("Child Role :"+childRoleId+" is not exists.");
		}
		if(parent.getGroupType().equals("1")&&child.getGroupType().equals("2")) {
			throw new Exception("Department cannot add Role.");
		}
		EcmGroupItem item = new EcmGroupItem();
		item.setItemType("1");
		item.setParentId(parentRoleId);
		item.setChildId(childRoleId);
		ecmGroupItemMapper.insert(item);
		
		//遍历角色所有用户添加到角色
		List<EcmGroupUser> list = ecmGroupUserMapper.selectByParent(childRoleId);
		for(EcmGroupUser en:list) {
			EcmGroupUser gu = ecmGroupUserMapper.selectByGroupUser(parentRoleId, en.getUserId());
			if(gu==null) {
				//添加到所有用户
				gu = new EcmGroupUser();
				gu.setGroupId(parentRoleId);
				gu.setUserId(en.getUserId());
				ecmGroupUserMapper.insert(gu);
			}
		}
		return true;
	}
	/**
	 * 判断是否循环嵌套
	 * @param groupId
	 * @return
	 */
	private boolean exitsParent(String groupId) {
		List<EcmGroupItem> list = ecmGroupItemMapper.selectParent(groupId);
		for(EcmGroupItem item:list) {
			if(item.getId()==groupId) {
				return true;
			}
			exitsParent(item.getId());
		}
		return false;
	}

	@Override
	public List<EcmUser> getAllUser(String token,String id) {
		// TODO Auto-generated method stub
		String sql ="select a.* from ecm_user a, ecm_group_user b where b.GROUP_ID="+id+" and a.ID=b.USER_ID";
		return ecmUserMapper.searchToEntity(sql);
	}
	
	@Override
	public List<EcmGroup> getUserGroup(String token,String userName) {
		// TODO Auto-generated method stub
		String sql ="select a.* from ecm_group a, ecm_group_user b, ecm_user c where b.GROUP_ID=a.ID and b.USER_ID=c.ID and c.NAME='"+userName+"'";
		return ecmGroupMapper.searchToEntity(sql);
	}

	
}
