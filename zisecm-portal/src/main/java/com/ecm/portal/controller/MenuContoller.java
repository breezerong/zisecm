package com.ecm.portal.controller;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.service.MenuService;
/**
 * 菜单操作
 * @author Haihong Rong
 *
 */
@Controller
public class MenuContoller extends ControllerAbstract{

	@Autowired
	private MenuService menuService;

	
	/**
	 * 获取当前用户菜单
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/memu/getMyMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyMenu(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String name = (String)args.get("name");
			String lang = (String)args.get("lang");
			mp.put("data",menuService.getMyMenu(getToken(), name, lang));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
}
