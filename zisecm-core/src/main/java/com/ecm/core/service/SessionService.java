package com.ecm.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.ServiceContext;
import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.cache.manager.SessionManager;

import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;

import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IEcmSession;
import com.ecm.icore.service.ISessionService;

/**
 * 
 * @Title: Session 管理
 * @author Haihong Rong
 * @date:   2020-12-15 22:22:01 
 * @Description:
 */
@Service
@Scope("prototype")
public class SessionService extends EcmObjectService<LoginUser> implements ISessionService{
	
	public SessionService() {
		serviceCode = ServiceContext.SESSION_CODE;
	}
	
	@Override
	public List<LoginUser> getAllObject(String token) {
		// TODO Auto-generated method stub
		List<LoginUser> list = new ArrayList<LoginUser>();
		for(IEcmSession s:SessionManager.getInstance().getLoginSession().asMap().values()) {
			list.add(s.getCurrentUser());
		}
		return list;
	}
	
	@Override
	public boolean deleteObjectById(String token, String id) throws NoPermissionException {
		if(token.equals(id)) {
			return false;
		}
		try {
			if(this.getSession(token).getCurrentUser().getSystemPermission()<SystemPermission.SUPER_USER) {
				throw new NoPermissionException("You have not super user permission.");
			}
		} catch (AccessDeniedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			IEcmSession s = SessionManager.getInstance().getLoginSession().get(id);
			if(s != null) {
				SessionManager.getInstance().getLoginSession().invalidate(id);
				return true;
			}
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
