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
			
			String startDate = args.get("startDate").toString();
			String endDate = args.get("endDate").toString();
			
			List<String> projList = documentService.getSession(getToken()).getCurrentUser().getMyProjects();					
			
			for(String projName: projList) {
				projMap = new HashMap<String, Object>();
				projMap.put("projectName", projName);

				String sqlplancount = "select count(*) as iedCount from ecm_document where TYPE_NAME='IED' and IS_CURRENT=1 and C_IS_RELEASED=1 and C_PROJECT_NAME='"+
						projName +"'";
				String sqlplancompleted = "select count(*) as completedCount from ecm_document where TYPE_NAME='IED' and IS_CURRENT=1 and C_IS_RELEASED=1 and C_ITEM_STATUS2='Y' and C_PROJECT_NAME='"+
						projName +"'";
				
				if(startDate!= null && endDate!= null) {
					sqlplancount += " and (C_ITEM_DATE BETWEEN '" + startDate + "' and '" + endDate + "')";
					sqlplancompleted += " and (C_ITEM_DATE BETWEEN '" + startDate + "' and '" + endDate + "')";
				}
				
				if(startDate == null && endDate!= null) {
					sqlplancount += " and (C_ITEM_DATE < '" + endDate + "')";
					sqlplancompleted += " and (C_ITEM_DATE < '" + endDate + "')";
				}
				
				if(startDate!= null && endDate == null) {
					sqlplancount += " and (C_ITEM_DATE > '" + startDate + "')";
					sqlplancompleted += " and (C_ITEM_DATE > '" + startDate + "')";
				}
				
				List<Map<String, Object>>  listTotal = documentService.getMapList(getToken(), sqlplancount);
				List<Map<String, Object>>  listCompleted = documentService.getMapList(getToken(), sqlplancompleted);
				
				int total = (int)listTotal.get(0).get("iedCount");
				projMap.put("iedCount", total);
				
				int complted = (int)listCompleted.get(0).get("completedCount");
				projMap.put("completedCount", complted);
				
				projMap.put("completedPercent", ((double)complted)/total);
				
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
}
