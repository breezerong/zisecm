package com.ecm.cnpe.exchange.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.cnpe.exchange.controller.param.DocParam;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class IEDReportController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/ied/IEDReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> IEDReport(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object> > outList = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			Map<String, Object> projMap = new HashMap<String, Object>();
			
			String iedPlanStatistic = this.getStrValue(args, "iedPlanStatistic");
			String startDate = this.getStrValue(args, "startDate");
			String endDate = this.getStrValue(args, "endDate");
			
			String sqlplancountdate = new String();
			String sqlplancompleteddate = new String();
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				sqlplancountdate = " and (C_ITEM_DATE BETWEEN '" + startDate + "' and '" + endDate + "')";
				sqlplancompleteddate = " and (C_ITEM_DATE BETWEEN '" + startDate + "' and '" + endDate + "')";
			}
			
			if(StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				sqlplancountdate = " and (C_ITEM_DATE < '" + endDate + "')";
				sqlplancompleteddate+= " and (C_ITEM_DATE < '" + endDate + "')";
			}
			
			if(!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
				sqlplancountdate = " and (C_ITEM_DATE > '" + startDate + "')";
				sqlplancompleteddate = " and (C_ITEM_DATE > '" + startDate + "')";
			}
			if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
				sqlplancountdate = "";
				sqlplancompleteddate = "";
			}
			
			String getProjName = "SELECT C_PROJECT_NAME FROM ecm_document WHERE C_PROJECT_NAME IN ("+
					iedPlanStatistic+") GROUP BY C_PROJECT_NAME";
			
			List<Map<String, Object>> projListMap = documentService.getMapList(getToken(), getProjName);
			List<String> projList = new ArrayList<String>();
			
			for(Map<String, Object> map : projListMap) {
				for(String s : map.keySet()) {
					projList.add((String) map.get(s));
				}
			}
			
			//List<String> projList = documentService.getSession(getToken()).getCurrentUser().getMyProjects();
			
			for(String projName: projList) {
				projMap = new HashMap<String, Object>();
				projMap.put("projectName", projName);
				
				String sqlplantotal = "select count(*) from ecm_document where TYPE_NAME='IED' and C_ITEM_DATE IS NOT NULL and IS_CURRENT=1 and C_IS_RELEASED=1 and C_PROJECT_NAME = ('"+
						projName +"')" + sqlplancountdate;
				String sqlplanfinished = "select count(*) from ecm_document where TYPE_NAME='IED' and C_ITEM_DATE IS NOT NULL and IS_CURRENT=1 and C_IS_RELEASED=1 and C_ITEM_STATUS2='Y' and C_PROJECT_NAME = ('"+
						projName +"')" + sqlplancompleteddate;
						
				String sqlplanstatistic = "select C_PROJECT_NAME, ("+
						sqlplantotal+") as PlantCount, ("+
						sqlplanfinished+") as FinishedCount from ecm_document where TYPE_NAME='IED' and C_ITEM_DATE IS NOT NULL and C_PROJECT_NAME in ("+
						iedPlanStatistic+")group by C_PROJECT_NAME";
				
				List<Map<String, Object>> listStatistic = documentService.getMapList(getToken(), sqlplanstatistic);
				
				int PlantCount = (int)listStatistic.get(0).get("PlantCount");
				projMap.put("iedCount", PlantCount);
				
				int FinishedCount = (int)listStatistic.get(0).get("FinishedCount");
				projMap.put("completedCount", FinishedCount);
				
				double completedPerc = (double)FinishedCount/PlantCount;
				
				if(PlantCount == 0 || FinishedCount ==0) {
					completedPerc = 0.00;
				}
				
				projMap.put("completedPercent", completedPerc);
				
				outList.add(projMap);
			}
			mp.put("data", outList);	
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
