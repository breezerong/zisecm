package com.ecm.flowable.identity;

import java.util.List;

import org.flowable.idm.api.User;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.UserDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecm.core.entity.EcmUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.UserService;
import com.ecm.flowable.session.FlowableSession;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class CustomUserEntityManager extends UserEntityManagerImpl {
	@Autowired
	private UserService userService;

	public CustomUserEntityManager(IdmEngineConfiguration idmEngineConfiguration, UserDataManager userDataManager) {
		super(idmEngineConfiguration, userDataManager);
	}

	@Override
	public UserEntity findById(String entityId) {
		EcmUser user=null;
		try {
			user = userService.getObjectById(new FlowableSession().getToken(), entityId);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (user == null) {
			return null;
		}
		UserEntity userEntity = new UserEntityImpl();
		userEntity.setId(user.getId());
		userEntity.setFirstName(user.getLoginName());
		userEntity.setLastName(user.getLoginName());
		userEntity.setDisplayName(user.getLoginName());
		userEntity.setEmail(user.getEmail());
		return userEntity;
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query) {
//		List<SysUser> sysUsers = userService.getBaseMapper().selectList(flowableQueryToWrapper(query));
//		if (sysUsers == null || sysUsers.size() == 0) {
//			return new ArrayList<>();
//		}
//		List<User> users = new ArrayList<>();
//		for (SysUser sysUser : sysUsers) {
//			User user = new UserEntityImpl();
//			user.setId(sysUser.getUserId());
//			user.setFirstName(sysUser.getUserName());
//			user.setLastName(sysUser.getUserName());
//			user.setDisplayName(sysUser.getUserName());
//			user.setEmail(sysUser.getEmail());
//			users.add(user);
//		}
//		return users;
		return null;
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl query) {
//		return userService.getBaseMapper().selectCount(flowableQueryToWrapper(query));
		return 1;
	}

//	private QueryWrapper<SysUser> flowableQueryToWrapper(UserQueryImpl query) {
//		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//		if (query.getId() != null) {
//			queryWrapper.eq("user_id", query.getId());
//		}
//		if (query.getIds() != null) {
//			queryWrapper.in("user_id", query.getIds());
//		}
//		if (query.getFirstName() != null) {
//			queryWrapper.eq("user_name", query.getFirstName());
//		}
//		if (query.getFirstNameLike() != null) {
//			queryWrapper.like("user_name", query.getFirstName());
//		}
//		return queryWrapper;
//	}
}
