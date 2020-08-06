package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class QuestionStatisticController extends ControllerAbstract  {
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/ques/FeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> QuestionStatistic(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		
		String iedType = this.getStrValue(args, "iedType");
		String icmType = this.getStrValue(args, "icmType");
		String planType = this.getStrValue(args, "planType");
		String dcNum = this.getStrValue(args, "dcNum");
		String designType = this.getStrValue(args, "designType");
		
		String sqlied = "select count(*) as iedcount from  ecm_document ed where SUB_TYPE = '"+
				iedType +"' and C_ITEM_STATUS = '新建'";
		String sqlicm = "select count(*) as iedcount from  ecm_document ed where SUB_TYPE = '"+
				icmType +"' and C_ITEM_STATUS = '新建'";
		String sqlplan = "select count(*) as iedcount from  ecm_document ed where SUB_TYPE = '"+
				planType +"' and C_ITEM_STATUS = '新建'";
		String sqldc = "select count(*) as iedcount from  ecm_document ed where SUB_TYPE = '"+
				dcNum +"' and C_ITEM_STATUS = '新建'";
		String sqldesign = "select count(*) as iedcount from  ecm_document ed where SUB_TYPE = '"+
				designType +"' and C_ITEM_STATUS = '新建'";
		
		String sqlStatistic = "select SUB_TYPE, ("+
				sqlied+") as iedst, ("+
				sqlicm+") as icmst, ("+
				sqlplan+") as planst, ("+
				sqldc+") as dcst, ("+
				sqldesign+") as designst from ecm_document ed where SUB_TYPE = ed.SUB_TYPE and C_ITEM_STATUS = '新建'  group by SUB_TYPE";
		
		try {
			List<Map<String, Object>> listStatistic = documentService.getMapList(getToken(), sqlStatistic);
			
			mp.put("iedst", (int)listStatistic.get(0).get("iedst"));
			mp.put("icmst", (int)listStatistic.get(0).get("icmst"));
			mp.put("planst", (int)listStatistic.get(0).get("planst"));
			mp.put("dcst", (int)listStatistic.get(0).get("dcst"));
			mp.put("designst", (int)listStatistic.get(0).get("designst"));
			mp.put("code", ActionContext.SUCESS);
		}catch(Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	}
	
	private String getStrValue(Map<String, Object> args, String key) {
		return (args.containsKey(key) && args.get(key)!=null)?args.get(key).toString():"";
	}
}
