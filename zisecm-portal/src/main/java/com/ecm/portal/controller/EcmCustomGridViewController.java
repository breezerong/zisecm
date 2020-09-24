package com.ecm.portal.controller;

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
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.GridViewItemService;
import com.ecm.core.service.GridViewService;

@Controller
public class EcmCustomGridViewController extends ControllerAbstract {
	@Autowired
	private GridViewItemService itemService;
	
	@Autowired
	private GridViewService gridViewService;
	
	@RequestMapping(value = "/dc/getOneEcmCustomGridViewInfo", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getOneEcmCustomGridViewInfo(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String gridId=args.get("gridId")!=null?args.get("gridId").toString():"";
		String lang = args.get("lang").toString();
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmGridViewItem> customItem = itemService.getEcmCustomGridViewInfo(getToken(), gridId);
			List<EcmGridViewItem> cItems=new ArrayList<EcmGridViewItem>();
			if(customItem!=null&&customItem.size()>0) {
				for(EcmGridViewItem item : customItem) {
					EcmGridViewItem itemc = item.clone(lang);
					cItems.add(itemc);
				}
			}
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", cItems);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "查询失败！");
		}
		
		return mp;
		
	}
	
	private EcmGridView getGridView(String gridName) {
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		if(gv==null) {
			try {
				gv = gridViewService.getObjectByName(getToken(), gridName);
				List<EcmGridViewItem> itemlist =itemService.getEcmCustomGridViewInfo(getToken(), gv.getId());
				if(itemlist==null) {
					itemlist = new ArrayList<EcmGridViewItem>();
				}
				gv.setGridViewItems(itemlist);
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			}
		}
		return gv;
	}
	
	@RequestMapping(value = "/dc/getEcmCustomGridViewInfo", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getEcmCustomGridViewInfo(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String gridName = args.get("gridName").toString();
		String gridId=args.get("gridId")!=null?args.get("gridId").toString():"";
		String lang = args.get("lang").toString();
		EcmGridView gv = getGridView(gridName);
		List<EcmGridViewItem> list = gv.getGridViewItems(lang);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmGridViewItem> cItems=new ArrayList<EcmGridViewItem>();
			if("".equals(gridId)) {
				cItems=list;
			}else {
				List<EcmGridViewItem> customItem = itemService.getEcmCustomGridViewInfo(getToken(), gridId);
				
				if(customItem!=null&&customItem.size()>0) {
					for(EcmGridViewItem item : customItem) {
						EcmGridViewItem itemc = item.clone(lang);
						cItems.add(itemc);
					}
				}else {
					cItems=list;
				}
			}
			
			mp.put("code", ActionContext.SUCESS);
			mp.put("sysGridInfo", list);
			mp.put("customGridInfo", cItems);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "查询失败！");
		}
		
		return mp;
		
	}
}
