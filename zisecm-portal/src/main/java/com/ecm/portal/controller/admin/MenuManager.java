package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.dao.EcmMenuMapper;
import com.ecm.core.entity.EcmMenu;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 菜单控制器
 * @author Administrator
 *
 */
@Controller
public class MenuManager extends ControllerAbstract{
	/**
	 * 菜单数据访问
	 */
	@Autowired
	private EcmMenuMapper ecmMenu;
	
	 @ResponseBody
	 @RequestMapping("/admin/getMenu")
	 public   Map<String, Object>  getMenu() {
		 List<EcmMenu> list =ecmMenu.selectAll();
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 mp.put("data", list);
		 return mp;
	 }
	 
	 @RequestMapping(value="/admin/updateMenu", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateMenu(@RequestBody  EcmMenu obj) {
		 ecmMenu.updateByPrimaryKey(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 @RequestMapping(value="/admin/deleteMenu", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteMenu(@RequestBody  EcmMenu obj) {
		 ecmMenu.deleteByPrimaryKey(obj.getId());
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 
	 @RequestMapping(value="/admin/newMenu", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newMenu(@RequestBody  EcmMenu obj) {
		 obj.createId();
		 ecmMenu.insert(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }

}
