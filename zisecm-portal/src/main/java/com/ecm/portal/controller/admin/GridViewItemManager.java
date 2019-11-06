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
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.GridViewItemService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 列表项控制器
 * @author Administrator
 *
 */
@Controller
public class GridViewItemManager extends ControllerAbstract{
	/**
	 * 列表项数据访问
	 */
	@Autowired
	private GridViewItemService ecmGridViewItem;
	
	/**
	 * 获取所有列表项
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value="/admin/getGridViewItem", method = RequestMethod.POST)
	 public   Map<String, Object>  getGridViewItem(@RequestBody String parentId) {
		 Map<String, Object> mp = new HashMap<String, Object>();
		 try {
			 parentId = parentId.replace("\"", "");
			 List<EcmGridViewItem> list =null;
			 //System.out.println(parentId);
			 if(parentId!=null&&parentId.trim().length()>0&&!"0".equals(parentId)&&!"".equals(parentId))
			 {
				 list =ecmGridViewItem.getByParentId(getToken(),parentId);
			 }
			 else
			 {
				 list = ecmGridViewItem.getAllObject(getToken());
			 }
			 mp.put("code", ActionContext.SUCESS);
			 mp.put("data", list);
		 } 
		 catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		 }
		 return mp;
	 }
	 /**
	  * 更新列表项
	  * @param obj 列表项实体
	  * @return
	  */
	 @RequestMapping(value="/admin/updateGridViewItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateGridViewItem(@RequestBody  EcmGridViewItem obj) {
		 Map<String, Object> mp = new HashMap<String, Object>();
		 try {
			 ecmGridViewItem.updateObject(getToken(),obj);
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		 }
		 return mp;
	 }
	 /**
	  * 删除列表项
	  * @param obj 列表项实体
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteGridViewItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteGridViewItem(@RequestBody  EcmGridViewItem obj) {
		 Map<String, Object> mp = new HashMap<String, Object>();
		 try {
			 ecmGridViewItem.deleteObject(getToken(),obj);
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch (AccessDeniedException e) {
				mp.put("code", ActionContext.TIME_OUT);
		 }
		 return mp;
	 }
	 
	 /**
	  * 新建列表项
	  * @param obj
	  * @return
	  */
	 @RequestMapping(value="/admin/newGridViewItem", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newGridViewItem(@RequestBody  EcmGridViewItem obj) {
		 Map<String, Object> mp = new HashMap<String, Object>();
		 try {
			 obj.createId();
			 ecmGridViewItem.newObject(getToken(),obj);
	//		 System.out.println("id:"+id);
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch (AccessDeniedException e) {
				mp.put("code", ActionContext.TIME_OUT);
		 }
		 return mp;
	 }

}
