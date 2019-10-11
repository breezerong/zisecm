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

import com.ecm.core.dao.EcmCardSearchItemMapper;
import com.ecm.core.dao.EcmCardSearchMapper;
import com.ecm.core.entity.EcmCardSearch;
import com.ecm.core.entity.EcmCardSearchItem;
import com.ecm.core.entity.EcmForm;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 卡片查询控制器
 * @author Administrator
 *
 */
@Controller
public class CardSearchManager extends ControllerAbstract{
	
	/**
	 * 卡片查询数据访问
	 */
	@Autowired
	private EcmCardSearchMapper ecmCardSearch;
	
	@Autowired
	private EcmCardSearchItemMapper ecmCardSearchItem;
	
	/**
	 * 获取所有卡片查询
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping("/admin/getCardSearch")
	 public   Map<String, Object>  getCardSearch() {
		 List<EcmCardSearch> list =ecmCardSearch.selectAll();
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 mp.put("data", list);
		 return mp;
	 }
	 /**
	  * 更新卡片查询
	  * @param obj 卡片查询实体
	  * @return
	  */
	 @RequestMapping(value="/admin/updateCardSearch", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateCardSearch(@RequestBody  EcmCardSearch obj) {
		 ecmCardSearch.updateByPrimaryKey(obj);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 复制卡片查询
	  * @param obj 卡片查询实体
	  * @return
	  */
	 @RequestMapping(value="/admin/copyCardSearch", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  copyCardSearch(@RequestBody  EcmCardSearch obj) {
		
		 String name = obj.getName()+" Copy";
		 String fromId = obj.getId();
		 obj.setId(fromId);
		 obj.setName(name);
		 ecmCardSearch.insert(obj);
		 
		 obj = getEcmCardSearch(name);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 if(obj!=null)
		 {
			 List<EcmCardSearchItem> items = ecmCardSearchItem.selectByParentId(fromId);
			 for(EcmCardSearchItem en:items)
			 {
				 
				 en.setParentId(obj.getId());
				 ecmCardSearchItem.insert(en);
			 }
			 mp.put("success", true);
		 }
		 else
		 {
			 mp.put("success", false);
		 }

		 return mp;
	 }
	 /**
	  * 删除卡片查询
	  * @param obj 卡片查询实体
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteCardSearch", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteCardSearch(@RequestBody  EcmCardSearch obj) {
		 
		 List<EcmCardSearchItem> items = ecmCardSearchItem.selectByParentId(obj.getId());
		 for(EcmCardSearchItem en:items)
		 {
			 ecmCardSearchItem.deleteByPrimaryKey(en.getId());
		 }
		 ecmCardSearch.deleteByPrimaryKey(obj.getId());
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 /**
	  * 新建卡片查询
	  * @param obj 卡片查询实体
	  * @return
	  */
	 @RequestMapping(value="/admin/newCardSearch", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newCardSearch(@RequestBody  EcmCardSearch obj) {
		 obj.createId();
		 ecmCardSearch.insert(obj);
		 //Long id=ecmCardSearch.insert(obj);
		 //System.out.println("id:"+id);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 mp.put("success", true);
		 return mp;
	 }
	 
	 private EcmCardSearch getEcmCardSearch(String name)
	 {
		 List<EcmCardSearch> list =ecmCardSearch.selectAll();
		 for(EcmCardSearch obj:list)
		 {
			 if(obj.getName().equals(obj))
				 return obj;
		 }
		 return null;
	 }
}
