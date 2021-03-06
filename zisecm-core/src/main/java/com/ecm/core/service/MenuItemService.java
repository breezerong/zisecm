package com.ecm.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.dao.EcmGroupUserMapper;
import com.ecm.core.dao.EcmMenuItemMapper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmMenuItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IMenuItemService;
import com.google.common.base.Strings;

/**
 * 
 * @author Haihong Rong
 * @date 2019年10月17日 下午1:39:37
 */
@Service
@Scope("prototype")
public class MenuItemService extends EcmObjectService<EcmMenuItem> implements IMenuItemService{
	@Autowired
	private EcmMenuItemMapper ecmMenuItemMapper;
	
	@Autowired
	private EcmComponentMapper ecmComponentMapper;
	@Autowired
	private EcmGroupUserMapper  emGroupUserMapper;

	public List<EcmMenuItem> getObjectsWithRole(String token,String condition) {
		// TODO Auto-generated method stub
		String userName="";
		try {
			userName = getSession(token).getCurrentUser().getUserName();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<EcmMenuItem> resultList= new ArrayList<EcmMenuItem>(); 
		List<EcmMenuItem> list = ecmMenuItemMapper.selectByCondition(condition);
//		for(EcmMenuItem en: list) {
//			String cond = "NAME='" + en.getComponentName()+"'";
//			List<EcmComponent> comps = ecmComponentMapper.selectByCondition(cond);
//			if(comps.size()>0) {
//				en.setUrl(comps.get(0).getUrl());
//				en.setParameter(comps.get(0).getParameter());
//			}
//		}
		for(EcmMenuItem en: list) {
			String roleName=en.getRoleName();
			String roleNames="";
			if(!Strings.isNullOrEmpty(roleName)) {
				String[] roles=roleName.split(";");
				for (int i = 0; i < roles.length; i++) {
					roleNames=roleNames+"'"+roles[i]+"'";
					if(i<roles.length-1)
						roleNames=roleNames+",";
				}
				if(emGroupUserMapper.selectCountByUserAndRoles(roleNames,userName)>0) {
					resultList.add(en);
				};
			}else {
				resultList.add(en);
			}
		}		
		
		return resultList;
	}
	
	@Override
	public List<EcmMenuItem> getObjects(String token,String condition) {
		// TODO Auto-generated method stub
		List<EcmMenuItem> list = ecmMenuItemMapper.selectByCondition(condition);
//		for(EcmMenuItem en: list) {
//			String cond = "NAME='" + en.getComponentName()+"'";
//			List<EcmComponent> comps = ecmComponentMapper.selectByCondition(cond);
//			if(comps.size()>0) {
//				en.setUrl(comps.get(0).getUrl());
//				en.setParameter(comps.get(0).getParameter());
//			}
//		}

		return list;
	}
	
	@Override
	public List<EcmMenuItem> getAllObject(String token) {
		// TODO Auto-generated method stub
		List<EcmMenuItem> list = ecmMenuItemMapper.selectAll();
//		for(EcmMenuItem en: list) {
//			String cond = "NAME='" + en.getComponentName()+"'";
//			List<EcmComponent> comps = ecmComponentMapper.selectByCondition(cond);
//			if(comps.size()>0) {
//				en.setUrl(comps.get(0).getUrl());
//				en.setParameter(comps.get(0).getParameter());
//			}
//		}
		return list;
	}

	@Override
	public EcmMenuItem getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		EcmMenuItem en = ecmMenuItemMapper.selectByPrimaryKey(id);
		String cond = "NAME='" + en.getComponentName()+"'";
		List<EcmComponent> comps = ecmComponentMapper.selectByCondition(cond);
		if(comps.size()>0) {
			en.setUrl(comps.get(0).getUrl());
			en.setParameter(comps.get(0).getParameter());
		}
		return en;
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmMenuItemMapper.updateByPrimaryKey((EcmMenuItem) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmMenuItemMapper.deleteByPrimaryKey(((EcmMenuItem)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		EcmMenuItem en =(EcmMenuItem)obj;
		if(StringUtils.isEmpty(en.getId())) {
			en.createId();
		}
		ecmMenuItemMapper.insert(en);
		return en.getId();
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return ecmMenuItemMapper.deleteByPrimaryKey(id)>0;
	}
}
