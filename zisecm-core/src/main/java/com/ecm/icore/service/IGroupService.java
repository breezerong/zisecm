package com.ecm.icore.service;

import java.util.List;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.Pager;

/**
 * 组服务接口
 * @author Haihong Rong
 * @date 2019年10月7日 上午11:58:21
 */
public interface IGroupService {
	
	long getChildGroupCount(String token,String id);
	long getUserCount(String token,String id);
	List<EcmUser> getUsers(String token,String id);
	List<EcmGroup> getChildGroup(String token,String id);
	boolean isMemberOfGroup(String token,String userName,String groupName);
	List<EcmGroup> getGroups(String token,String parentId, String type, Pager pager, String condition);
	long getGroupCount(String token,String id, String type, String string);
	List<EcmGroup> getAllGroups(String token,String parentId, String type);
	boolean addUserToGroup(String token,String userId, String groupId) throws Exception;
	boolean addUserToRole(String token,String userId, String roleId) throws Exception;
	boolean addRole(String token,String parentRoleId, String childRoleId) throws Exception;
	boolean removeUserFromGroup(String token,String userId, String deptId) throws Exception;
	EcmGroup getGroup(String token,String id);
	boolean deleteGroup(String token,String id);
	boolean updateGroup(String token,EcmGroup obj);
	boolean newGroup(String token,EcmGroup obj);
	List<EcmUser> getAllUser(String token,String id);
	List<EcmGroup> getUserGroup(String token, String userName);
}
