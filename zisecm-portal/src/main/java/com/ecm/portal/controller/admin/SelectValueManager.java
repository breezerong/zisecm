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

import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmSelectValueMapper;
import com.ecm.core.entity.EcmSelectValue;
import com.ecm.core.service.SelectValueService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 组件控制器
 * @author Administrator
 *
 */
@Controller
public class SelectValueManager extends ControllerAbstract{
	/**
	 * 组件数据访问
	 */
	@Autowired
	private SelectValueService ecmSelectValue;
	
	/**
	 * 获取所有组件
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping("/admin/getSelectValue")
	 public   Map<String, Object>  getSelectValue() {
		 List<EcmSelectValue> list =ecmSelectValue.getAllObject(getToken());
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 mp.put("data", list);
		 return mp;
	 }
	 
	 /**
	  * 更新组件
	  * @param obj 组件实例
	  * @return
	  */
	 @RequestMapping(value="/admin/updateSelectValue", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateSelectValue(@RequestBody  EcmSelectValue obj) {
		 ecmSelectValue.updateObject(getToken(), obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }
	 
	 /**
	  * 删除组件
	  * @param obj 组件实例
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteSelectValue", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteSelectValue(@RequestBody  EcmSelectValue obj) {
		 ecmSelectValue.deleteObject(getToken(), obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }
	 
	 /**
	  * 新建组件
	  * @param obj 组件实例
	  * @return
	  */
	 @RequestMapping(value="/admin/newSelectValue", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newSelectValue(@RequestBody  EcmSelectValue obj) {
		 obj.createId();
		 ecmSelectValue.newObject(getToken(), obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("code", ActionContext.SUCESS);
		 return mp;
	 }

}
