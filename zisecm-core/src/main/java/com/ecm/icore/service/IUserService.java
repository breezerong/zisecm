package com.ecm.icore.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;

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

	List<EcmUser> getGroupUsers(String token, long groupId);

	List<EcmUser> updateUserDepartment(String token, String userId, String deptId, String deptName) throws EcmException, AccessDeniedException;

	boolean moveUserDepartment(String token,EcmUser en) throws EcmException, AccessDeniedException;

	List<EcmUser> getRoleUsers(String token,Pager pager, String noGroup, String groupId,
			String condition);

	long getRoleUserCount(String token,boolean noRole, String groupId, String condition);

	boolean removeUserGroup(String token,EcmUser en) throws EcmException, AccessDeniedException;

	boolean removeUserRole(String token,String userId, String roleId) throws EcmException, AccessDeniedException;

	String newObject(String token,Object en, InputStream instream,String fileName) throws EcmException, Exception;

	boolean updateSignImage(String token,String id, InputStream instream,String fileName) throws EcmException, Exception;

}
