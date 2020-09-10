package com.ecm.cnpe.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.Pager;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
public class TcDocController extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ExcSynDetailService synDetailService;
	private final String queryBase = "SELECT ID,APP_NAME, CREATION_DATE, EXPORT_DATE, IMPORT_DATE, STATUS, ERROR_MESSAGE,FROM_ID,TO_ID FROM exc_syn_detail";
	
	@RequestMapping(value = "/doc/tc/TCMonitor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> TCMonitor(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object> > outList = new ArrayList<Map<String, Object>>();
		
		try {
			Map<String, Object> projMap = new HashMap<String, Object>();
			
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			
			String dataType = this.getStrValue(args, "dataType");
			String startDate = this.getStrValue(args, "startDate");
			String endDate = this.getStrValue(args, "endDate");
			
			String timeCheck = new String();
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeSE(startDate, endDate, dataType);
			}
			if(StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeE(endDate, dataType);
			}
			if(!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeS(startDate, dataType);
			}
			if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeEmp();
			}
			
			String tcListSQL = new String();
			
			if(dataType.equals("TCSync")) {
				tcListSQL = queryBase+" WHERE APP_NAME = 'TC' AND ACTION_NAME = '接收'"+
						       timeCheck;
			}
			if(dataType.equals("TCPush")){
				tcListSQL = queryBase+" WHERE APP_NAME = 'TC' AND ACTION_NAME = '发送'"+
							   timeCheck;
			}
			if(dataType.equals("NetSync")) {
				tcListSQL = queryBase+" WHERE APP_NAME = 'DOCEX'"+
							   timeCheck;
			}
			String sql="select * from ("+tcListSQL+") t ORDER BY EXPORT_DATE DESC";
			List<Map<String, Object>> getTCList = synDetailService.getExcSynDetails(pager, sql);
			
			for (Map<String, Object> item : getTCList) {
				projMap = new HashMap<String, Object>();
				
				projMap.put("id", (item.get("ID")==null)?"":item.get("ID").toString());
				
				String typeName = (item.get("APP_NAME")==null)?"":item.get("APP_NAME").toString();
				projMap.put("typeName", typeName);				
				
				String createdTime = (item.get("CREATION_DATE")==null)?"":item.get("CREATION_DATE").toString();
				projMap.put("createdTime", createdTime);
				
				String executedTime = (item.get("EXPORT_DATE")==null)?"":item.get("EXPORT_DATE").toString();
				projMap.put("executedTime", executedTime);
				
				String finishedTime = (item.get("IMPORT_DATE")==null)?"":item.get("IMPORT_DATE").toString();
				projMap.put("finishedTime", finishedTime);
				
				String logStatus = (item.get("STATUS")==null)?"":item.get("STATUS").toString();
				projMap.put("logStatus", logStatus);
				
				String errorMessage = (item.get("ERROR_MESSAGE")==null)?"":item.get("ERROR_MESSAGE").toString();
				projMap.put("errorMessage", errorMessage);
				
				
				projMap.put("fromId", (item.get("FROM_ID")==null)?"":item.get("FROM_ID").toString());
				
				
				projMap.put("toId", (item.get("TO_ID")==null)?"":item.get("TO_ID").toString());
				
				
				outList.add(projMap);
			}
			mp.put("pager", pager);
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
	
	private String setSQLTimeSE(String key1, String key2, String key3) {
		if(key3 == "NetSync") {
			return " and (CREATION_DATE BETWEEN '" + key1 + "' and '" + key2 + "')";
		}else{
			return " and (EXPORT_DATE BETWEEN '" + key1 + "' and '" + key2 + "')";
		}
	}
	
	private String setSQLTimeE(String key1, String key2) {
		if(key2 == "NetSync") {
			return " and (CREATION_DATE < '" + key1 + "')";
		}else{
			return " and (EXPORT_DATE < '" + key1 + "')";
		}
	}
	
	private String setSQLTimeS(String key1, String key2) {
		if(key2 == "NetSync") {
			return " and (CREATION_DATE > '" + key1 + "')";
		}else {
			return " and (EXPORT_DATE > '" + key1 + "')";
		}	
	}
	
	private String setSQLTimeEmp() {
		return "";
	}
}
