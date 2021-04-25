package com.ecm.portal.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmMenu;
import com.ecm.core.entity.EcmMenuItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.MenuItemService;
import com.ecm.core.service.MenuService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 菜单控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class MenuManager extends ControllerAbstract {
	/**
	 * 菜单数据访问
	 */
	@Autowired
	private MenuService ecmMenu;

	@Autowired
	private MenuItemService ecmMenuItem;
	
	@Autowired
	private MenuService menuService;

	@ResponseBody
	@RequestMapping("/admin/getMenu")
	public Map<String, Object> getMenu() {
		List<EcmMenu> list;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			list = ecmMenu.getAllObject(getToken());
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/updateMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMenu(@RequestBody EcmMenu obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmMenu.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/deleteMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMenu(@RequestBody EcmMenu obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmMenu.deleteObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/newMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newMenu(@RequestBody EcmMenu obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
		
			ecmMenu.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/getMenuItem", method = RequestMethod.POST)
	public Map<String, Object> getMenuItem(@RequestBody String name) {
		List<EcmMenuItem> list;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String condition = "MENU_NAME='" + name + "'";
			list = ecmMenuItem.getObjects(getToken(), condition);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", list);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@ResponseBody
	@RequestMapping(value = "/admin/getMenuItemWithRole", method = RequestMethod.POST)
	public Map<String, Object> getMenuItemWithRole(@RequestBody String argStr) {
		EcmMenu menu = new EcmMenu();
		List<String> menuList = new ArrayList<>();
		List<EcmMenuItem> subList = new ArrayList<>();
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String name = (String)args.get("name");
			String lang = (String)args.get("lang");
			menu = menuService.getMyMenu(getToken(), name, lang);
			for(int i=0; i<menu.getMenuItems().size(); i++) {
				subList = menu.getMenuItems().get(i).getSubmenus();
				if(subList.size()>0) {
					for(int j=0; j<subList.size(); j++) {
						menuList.add(subList.get(j).getName());
					}
				}else {
					menuList.add(menu.getMenuItems().get(i).getName());
				}
			}
			mp.put("data", menuList);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/updateMenuItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMenuItem(@RequestBody EcmMenuItem obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmMenuItem.updateObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/deleteMenuItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMenuItem(@RequestBody EcmMenuItem obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ecmMenuItem.deleteObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/admin/newMenuItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newMenuItem(@RequestBody EcmMenuItem obj) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			
			ecmMenuItem.newObject(getToken(), obj);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

}
