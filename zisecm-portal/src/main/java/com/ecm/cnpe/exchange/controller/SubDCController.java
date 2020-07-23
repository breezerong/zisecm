package com.ecm.cnpe.exchange.controller;

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
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.Pager;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;
@Controller
public class SubDCController extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	/**
	 * 通过id查找relation中childId对应的document
	 * @return
	 */
	@RequestMapping(value = "/dc/getDocuByOtherCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getDocumentByRelationParentId(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			
			List<Map<String, Object>>  list=new ArrayList<Map<String,Object>>();
			if(args.get("id")!=null&&!"".equals(args.get("id").toString())) {
				String sql = "select * from(select b.* from ecm_document a,ecm_document b where a.C_OTHER_CODING=b.CODING and a.id='"+args.get("id").toString()+"') t";
				list = documentService.getMapList(getToken(), sql,pager);
				
			}
			
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	
	}
}
