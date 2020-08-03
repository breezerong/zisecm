package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.cnpe.exchange.service.GroupServiceExchange;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmGroup;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.GroupService;
import com.ecm.portal.controller.ControllerAbstract;
@Controller
public class DCPermissionController  extends ControllerAbstract  {
	@Autowired
	private GroupServiceExchange groupService;

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/getGroups", method = RequestMethod.POST)
	public Map<String, Object> getGroups(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmGroup> list = null;
			Pager pager = null;
			if (args.get("pageSize") != null) {
				pager = new Pager();
				pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
				pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
				list = groupService.getGroups(getToken(), args.get("id").toString(), args.get("groupType").toString(),
						pager, args.get("condition").toString());
			} else {
				list = groupService.getAllGroups(getToken(), args.get("id").toString(),
						args.get("groupType").toString());
			}
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
}
