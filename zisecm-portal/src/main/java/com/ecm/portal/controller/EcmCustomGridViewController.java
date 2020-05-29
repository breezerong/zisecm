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

@Controller
public class EcmCustomGridViewController extends ControllerAbstract {
	@Autowired
	private GridViewItemService itemService;
	
	@RequestMapping(value = "/dc/getEcmCustomGridViewInfo", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getEcmCustomGridViewInfo(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String gridName = args.get("gridName").toString();
		String lang = args.get("lang").toString();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		List<EcmGridViewItem> list = gv.getGridViewItems(lang);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmGridViewItem> customItem = itemService.getEcmCustomGridViewInfo(getToken(), this.getSession().getCurrentUser().getUserName(), gridName);
			List<EcmGridViewItem> cItems=new ArrayList<EcmGridViewItem>();
			if(customItem!=null&&customItem.size()>0) {
				for(EcmGridViewItem item : customItem) {
					EcmGridViewItem itemc = item.clone(lang);
					cItems.add(itemc);
				}
			}else {
				cItems=list;
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
