package com.ecm.icore.service;

import java.io.InputStream;
import java.util.List;

import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;

/**
 * @ClassName  IUserService   
 * @Description TODO(用户服务类)   
 * @author Haihong Rong
 * @date 2018年7月4日 上午10:03:01  
 *
 */
public interface IUserService {
	/**
	 * 
	 * @Title authentication   
	 * @Description TODO(用户认证)   
	 * @param ecmUser
	 * @return PortalUser
	 * @throws Exception           
	 * @author Haihong Rong
	 * @date 2018年7月4日 上午10:03:43 
	 */
	public LoginUser authentication(EcmUser ecmUser) throws Exception;
		
	List<EcmUser> getUsers(String token,Pager pager,boolean noGroup,String condition);

	long getUserCount(String token,boolean noGroup, String condition);

	List<EcmUser> updateUserDepartment(String token, String userId, String deptId, String deptName) throws EcmException, AccessDeniedException, NoPermissionException;

	boolean moveUserDepartment(String token,EcmUser en) throws EcmException, AccessDeniedException, NoPermissionException;

	List<EcmUser> getRoleUsers(String token,Pager pager, String noGroup, String groupId,
			String condition);

	long getRoleUserCount(String token,boolean noRole, String groupId, String condition);

	String newObject(String token,Object en, InputStream instream,String fileName) throws EcmException, Exception;

	boolean updateSignImage(String token,String id, InputStream instream,String fileName) throws EcmException, Exception;

	EcmUser getObjectByName(String token, String userName);

	boolean updatePassword(String token, String userName, String password, String newPassword)
			throws EcmException, AccessDeniedException, NoPermissionException;

	List<EcmUser> getUsersByGroupName(String token, Pager pager, String noGroup, String groupName, String condition);

	LoginUser authenticationSSO(EcmUser ecmUser) throws Exception;

	List<EcmUser> getGroupUsers(String token, String groupId);

	EcmUser getObjectByLoginName(String token, String loginName);


	/**
	 * 根据用户ID获取用户所有组，效率比用户名获取高
	 * @param token
	 * @param userId
	 * @return
	 */
	List<EcmGroup> getUserGroupsById(String token, String userId);
	/**
	 * 根据用户名获取所有组
	 * @param token
	 * @param userName
	 * @return
	 */
	List<EcmGroup> getUserGroupsByName(String token, String userName);

	List<EcmUser> getRoleAllUsers(String token, Pager pager, String groupId, String condition);

}
