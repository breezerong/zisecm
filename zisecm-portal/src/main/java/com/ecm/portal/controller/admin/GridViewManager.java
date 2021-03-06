package com.ecm.portal.controller.admin;

import java.util.Date;
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
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.GridViewItemService;
import com.ecm.core.service.GridViewService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 列表控制器
 * @author Administrator
 *
 */
@Controller
public class GridViewManager extends ControllerAbstract{
	
	/**
	 * 列表数据访问
	 */
	@Autowired
	private GridViewService ecmGridView;
	@Autowired
	private GridViewItemService itemService;
	
	/**
	 * 列表项数据访问
	 */
	@Autowired
	private GridViewItemService ecmGridViewItem;
	
	/**
	 * 获取所有列表
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping("/admin/getGridView")
	 public   Map<String, Object>  getGridView() {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 List<EcmGridView> list =ecmGridView.getAllObject(getToken());
			 mp.put("code", ActionContext.SUCESS);
			 mp.put("data", list);
		 }
		 catch(Exception ex) {
			 mp.put("code", ActionContext.FAILURE);
			 mp.put("message", ex.getMessage());
		 }
		 return mp;
	 }
	 /**
	  * 更新列表
	  * @param obj 列表实例
	  * @return
	  */
	 @RequestMapping(value="/admin/updateGridView", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  updateGridView(@RequestBody  EcmGridView obj) {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 ecmGridView.updateObject(getToken(), obj);
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch(Exception ex) {
			 mp.put("code", ActionContext.FAILURE);
			 mp.put("message", ex.getMessage());
		 }
		 return mp;
	 }
	 /**
	  * 删除列表
	  * @param obj 列表实例
	  * @return
	  */
	 @RequestMapping(value="/admin/deleteGridView", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteGridView(@RequestBody  EcmGridView obj) {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 //删除子对象
			 List<EcmGridViewItem> items = ecmGridViewItem.getByParentId(getToken(),obj.getId());
			 for(EcmGridViewItem en:items)
			 {
				 ecmGridViewItem.deleteObject(getToken(),en);
			 }
			 //删除列表
			 ecmGridView.deleteObject(getToken(),obj);
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch(Exception ex) {
			 mp.put("code", ActionContext.FAILURE);
			 mp.put("message", ex.getMessage());
		 }
		 return mp;
	 }
	 
	 /**
	  * 新建列表
	  * @param obj
	  * @return
	  */
	 @RequestMapping(value="/admin/newGridView", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  newGridView(@RequestBody  EcmGridView obj) {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 ecmGridView.newObject(getToken(),obj);
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch(Exception ex) {
			 mp.put("code", ActionContext.FAILURE);
			 mp.put("message", ex.getMessage());
		 }
		 return mp;
	 }
	 
	 
	 /**
	  * 复制列表
	  * @param obj 列表实体
	  * @return
	  */
	 @RequestMapping(value="/admin/copyGridView", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  copyGridView(@RequestBody  EcmGridView obj) {
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 try {
			 ecmGridView.copy(getToken(), obj);
			 mp.put("id", obj.getId());
			 mp.put("code", ActionContext.SUCESS);
		 }
		 catch(Exception ex) {
			 mp.put("code", ActionContext.FAILURE);
			 mp.put("message", ex.getMessage());
		 }
		 return mp;
	 }
	 
	 
	 /**
	  * 复制列表
	  * @param obj 列表实体
	  * @return
	  */
//	 @RequestMapping(value="/admin/createOrUpdateGridView", method = RequestMethod.POST)  
//	 @ResponseBody
//	 public  Map<String, Object>  createOrUpdateGridView(@RequestBody  String argStr){
//		 Map<String, Object> args = JSONUtils.stringToMap(argStr);
//		 Map<String, Object>   mp = new HashMap<String, Object> ();
//		 String gridName= args.get("gridName")==null?"":args.get("gridName").toString();
//		 try {
//			String creator=this.getSession().getCurrentUser().getUserName();
//			
//			String condition=" NAME='"+gridName+"' and CREATOR='"+creator+"'";
//			EcmGridView gridView= ecmGridView.getObjectByCondition(getToken(), condition);
//			String newId="";
//			if(gridView==null) {
//				gridView= ecmGridView.getObjectByName(getToken(), gridName);
//				gridView.setCreationDate(new Date());
//				gridView.setCreator(creator);
//				gridView.setGridType(1);
//				newId= ecmGridView.copyToCustomGridView(getToken(), gridView);
//				
//			}else {
//				itemService.deleteByParentId(getToken(), gridView.getId());
//				newId=gridView.getId();
//			}
//			String itemsStr= args.get("items")==null?"":args.get("items").toString();
//			if(!"".equals(itemsStr)) {
//				List<String> itemsList= JSONUtils.stringToArray(itemsStr);
//				for(int i=0;i<itemsList.size();i++) {
//					String item=itemsList.get(i);
//					EcmGridViewItem viewItem= JSONUtils.objectFromJsonStr(item, EcmGridViewItem.class);
//					viewItem.setParentId(newId);
//					viewItem.setOrderIndex(i+1);
////					viewItem.setVisibleType("1");
////					viewItem.setWidth("80");
//					itemService.newObject(getToken(), viewItem);
//				}
//			}
//			mp.put("code", ActionContext.SUCESS);
//		} catch (AccessDeniedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			mp.put("code", ActionContext.FAILURE);
//		}
//		 return mp;
//		 
//	 }
	 
	 @RequestMapping(value="/admin/deleteCustomGridView", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  deleteGridView(@RequestBody  String argStr){
		 Map<String, Object> args = JSONUtils.stringToMap(argStr);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 String gridName= args.get("gridName")==null?"":args.get("gridName").toString();
		 String name= args.get("NAME")==null?"":args.get("NAME").toString();
		 try {
			String creator=this.getSession().getCurrentUser().getUserName();
			
			String condition=" NAME='"+name+"' and GRID_TAG='"+gridName+"' and CREATOR='"+creator+"'";
			EcmGridView gridView= ecmGridView.getObjectByCondition(getToken(), condition);
			String newId="";
			if(gridView!=null) {
				itemService.deleteByParentId(getToken(), gridView.getId());
				newId=gridView.getId();
				ecmGridView.deleteObjectById(getToken(), newId);
			}
			mp.put("message", "删除成功");
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		 return mp;
		 
	 }
	 
	 private EcmGridView getGridView(String gridName) {
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		if(gv==null) {
			try {
				gv = ecmGridView.getObjectByName(getToken(), gridName);
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			}
		}
		return gv;
	 }
	 
	 @RequestMapping(value="/admin/createOrUpdateGridView", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  createOrUpdateGridView(@RequestBody  String argStr){
		 Map<String, Object> args = JSONUtils.stringToMap(argStr);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 String gridName= args.get("gridName")==null?"":args.get("gridName").toString();
		 String name= args.get("NAME")==null?"":args.get("NAME").toString();
		 try {
			String creator=this.getSession().getCurrentUser().getUserName();
			String fullName = creator+"_"+name;
			gridName = getSystemGridName( getToken(), gridName, creator);
			String condition=" NAME='"+fullName+"' and GRID_TAG='"+gridName+"' and CREATOR='"+creator+"'";
			EcmGridView tagGrid = getGridView(gridName);
			EcmGridView gridView= ecmGridView.getObjectByCondition(getToken(), condition);
			String newId="";
			if(gridView==null) {
//				gridView= ecmGridView.getObjectByName(getToken(), gridName);
				gridView=new EcmGridView();
				gridView.setName(fullName);
				gridView.setCreationDate(new Date());
				gridView.setCreator(creator);
				gridView.setTypeName(tagGrid.getTypeName());
				gridView.setGridType(1);
				gridView.setGridTag(gridName);
				gridView.setDescription(name);
				gridView.setCondition(tagGrid.getCondition());
				gridView.setOrderBy(tagGrid.getOrderBy());
				newId= ecmGridView.copyToCustomGridView(getToken(), gridView);
				CacheManagerOper.getEcmGridViews().put(fullName, gridView);
			}else {
				itemService.deleteByParentId(getToken(), gridView.getId());
				newId=gridView.getId();
			}
			String itemsStr= args.get("items")==null?"":args.get("items").toString();
			if(!"".equals(itemsStr)) {
				List<String> itemsList= JSONUtils.stringToArray(itemsStr);
				for(int i=0;i<itemsList.size();i++) {
					String item=itemsList.get(i);
					try {
						EcmGridViewItem viewItem= JSONUtils.objectFromJsonStr(item, EcmGridViewItem.class);
						viewItem.setParentId(newId);
						viewItem.setOrderIndex(i+1);
	//					viewItem.setVisibleType("1");
	//					viewItem.setWidth("80");
						itemService.newObject(getToken(), viewItem);
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		 return mp;
		 
	 }
	 
	 private String getSystemGridName(String token,String gridName,String creator) {
		 if( CacheManagerOper.getEcmGridViews().get(gridName) ==null) {
			 String condition=" NAME='"+gridName+"' AND GRID_TYPE=1 AND CREATOR='"+creator+"'";
			 List<EcmGridView> data= ecmGridView.getObjectsByCondition(token, condition);
			 if(data.size()>0) {
				 return data.get(0).getGridTag();
			 }
		 }
		 return gridName;
	 }
	 
	 @RequestMapping(value="/admin/getAllGridViewsOfCurrentUser", method = RequestMethod.POST)  
	 @ResponseBody
	 public  Map<String, Object>  getAllGridViewsOfCurrentUser(@RequestBody  String argStr){
		 Map<String, Object> args = JSONUtils.stringToMap(argStr);
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		 String gridName= args.get("gridName")==null?"":args.get("gridName").toString();
		try {
			String creator=this.getSession().getCurrentUser().getUserName();
			String condition=" CREATOR='"+creator+"' and GRID_TYPE=1 ";
			if(gridName!=null) {
				condition+="and (GRID_TAG='"+gridName+"' or GRID_TAG in(select GRID_TAG from ecm_gridview where NAME ='"+gridName+"')) ";
			}
			List<EcmGridView> data= ecmGridView.getObjectsByCondition(getToken(), condition);
			mp.put("data", data);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		 return mp;
		 
	 }
	 
}
