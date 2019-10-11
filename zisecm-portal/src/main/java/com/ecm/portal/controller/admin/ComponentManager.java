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

import com.ecm.core.dao.EcmComponentMapper;
import com.ecm.core.entity.EcmComponent;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 组件控制器
 * @author Administrator
 *
 */
@Controller
public class ComponentManager extends ControllerAbstract{
	/**
	 * 组件数据访问
	 */
	@Autowired
	private EcmComponentMapper ecmComponent;
	
	/**
	 * 获取所有组件
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping("/admin/getComponent")
	 public   Map<String, Object>  getComponent() {
		 List<EcmComponent> list =ecmComponent.selectAll();
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 mp.put("data", list);
		 return mp;
	 }
	 
	 /**
	  * 更新组件
	  * @param obj 组件实例
	  * @return
	  */
	 @RequestMapping(value="/admin/updateComponent", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateComponent(@RequestBody  EcmComponent obj) {
		 ecmComponent.updateByPrimaryKey(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 删除组件
	  * @param obj 组件实例
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteComponent", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteComponent(@RequestBody  EcmComponent obj) {
		 ecmComponent.deleteByPrimaryKey(obj.getId());
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 新建组件
	  * @param obj 组件实例
	  * @return
	  */
	 @RequestMapping(value="/admin/newComponent", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newComponent(@RequestBody  EcmComponent obj) {
		 obj.createId();
		 ecmComponent.insert(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }

}
