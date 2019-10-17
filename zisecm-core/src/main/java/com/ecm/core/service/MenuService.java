package com.ecm.core.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecm.core.PermissionContext.SystemPermission;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.dao.EcmMenuItemMapper;
import com.ecm.core.dao.EcmMenuMapper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.EcmMenu;
import com.ecm.core.entity.EcmMenuItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.icore.service.IMenuService;

/**
 * 
 * @author Haihong Rong
 * @date 2019年10月17日 下午1:39:37
 */
@Service
@Scope("prototype")
public class MenuService extends EcmObjectService<EcmMenu> implements IMenuService{
	@Autowired
	private EcmMenuMapper ecmMenuMapper;
	
	@Autowired
	private EcmMenuItemMapper ecmMenuItemMapper;
	
	@Autowired
	EcmComponentMapper ecmComponentMapper;
	
	@Autowired
	GroupService groupService;
	
	@Override
	public EcmMenu getMyMenu(String token, String menuName, String lang) throws AccessDeniedException {
		if(StringUtils.isEmpty(menuName)) {
			return null;
		}
		String userId = getSession(token).getCurrentUser().getUserId();
		EcmMenu menu = CacheManagerOper.getEcmMenus().get(menuName);
		
		if(menu != null) {
			menu = menu.clone(lang);
			if(getSession(token).getCurrentUser().getSystemPermission()<SystemPermission.SUPER_USER) {
				//读取当前用户所有角色
				List<EcmGroup> glist = groupService.getUserGroupsById(token, userId);
				checkMenuPermission(menu.getMenuItems(), glist);
			}
		}
		return menu;
	}
	/**
	 * 检查菜单权限
	 * @param mlist
	 * @param glist
	 */
	private void checkMenuPermission(List<EcmMenuItem> mlist, List<EcmGroup> glist) {
		int count = mlist.size();
		for(int i= count-1; i >=0; i--) {
			EcmMenuItem sm =  mlist.get(i);
			if(!inGroup(glist, sm.getRoleName())) {
				mlist.remove(i);
			}
			else {
				if(sm.getSubmenus()!=null) {
					checkMenuPermission(sm.getSubmenus(),glist);
				}
			}
		}
	}
	
	/**
	 * 是否包含角色
	 * @param glist
	 * @param roleName
	 * @return
	 */
	private boolean inGroup(List<EcmGroup> glist,String roleName) {
		if(StringUtils.isEmpty(roleName)) {
			return true;
		}
		for(EcmGroup g: glist) {
			if(g.getName().equalsIgnoreCase(roleName)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<EcmMenu> getMenuWithChild(String token) {
		// TODO Auto-generated method stub
		List<EcmMenu> mlist = ecmMenuMapper.selectAll();
		for(EcmMenu m : mlist) {
			String name = m.getName();
			String condition = "MENU_NAME='"+name+"' and (PARENT_ID is null or PARENT_ID='')";
			List<EcmMenuItem> smlist = ecmMenuItemMapper.selectByCondition(condition);
			for(EcmMenuItem sm : smlist) {
				bindChild(sm);
			}
			m.setMenuItems(smlist);
		}
		return mlist;
	}
	
	
	
	private void bindChild(EcmMenuItem en) {
		String condition = "PARENT_ID='"+en.getId()+"'";
		String condition2 = "NAME='" + en.getComponentName()+"'";
		List<EcmComponent> comps = ecmComponentMapper.selectByCondition(condition2);
		if(comps.size()>0) {
			en.setUrl(comps.get(0).getUrl());
			en.setParameter(comps.get(0).getParameter());
		}
		List<EcmMenuItem> smlist = ecmMenuItemMapper.selectByCondition(condition);
		for(EcmMenuItem sm : smlist) {
			bindChild(sm);
		}
		en.setSubmenus(smlist);
	}
	
	@Override
	public List<EcmMenu> getAllObject(String token) {
		// TODO Auto-generated method stub
		return ecmMenuMapper.selectAll();
	}

	@Override
	public EcmMenu getObjectById(String token,String id) {
		// TODO Auto-generated method stub
		return ecmMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmMenuMapper.updateByPrimaryKey((EcmMenu) obj)>0;
	}

	@Override
	public boolean deleteObject(String token,Object obj) {
		// TODO Auto-generated method stub
		return ecmMenuMapper.deleteByPrimaryKey(((EcmMenu)obj).getId())>0;
	}

	@Override
	public String newObject(String token,Object obj) {
		// TODO Auto-generated method stub
		((EcmMenu)obj).createId();
		ecmMenuMapper.insert((EcmMenu)obj);
		return ((EcmMenu)obj).getId();
	}
	@Override
	public EcmMenu getObjectByName(String token, String name) {
		// TODO Auto-generated method stub
		String condition = "MENU_NMAE='"+name+"'";
		List<EcmMenu> mlist = ecmMenuMapper.selectByCondition(condition);
		if(mlist.size()>0) {
			return mlist.get(0);
		}
		return null;
	}
}
