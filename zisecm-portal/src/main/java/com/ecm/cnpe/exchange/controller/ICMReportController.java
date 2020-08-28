package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
public class ICMReportController extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/icm/ICMReport", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> ICMReport(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object> > outList = new ArrayList<Map<String, Object>>();
		
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			Map<String, Object> projMap = new HashMap<String, Object>();
			
			String icmReportStatistc = this.getStrValue(args, "icmReportStatistc");
			//List<String> companyName = JSONUtils.stringToArray(icmReportStatistc);
			String startDate = this.getStrValue(args, "startDate");
			String endDate = this.getStrValue(args, "endDate");
			
			String timeCheck = new String();
			
			if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeSE(startDate, endDate);
			}
			if(StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeE(endDate);
			}
			if(!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeS(startDate);
			}
			if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
				timeCheck = setSQLTimeEmp();
			}
			
			String icmCountSQL = new String("select count(*) from ecm_document where TYPE_NAME='ICM'"
														+ "	and @condition"
														+ timeCheck
														+ " and C_PROJECT_NAME=ed.C_PROJECT_NAME");
			
			String[] sqlSetColumn = {"sqlshopenICMcount",
									 "sqlhaopenICMcount",
									 "sqlodopenICMcount",
									 "sqlshshutdownICMcount",
									 "sqlhashutdownICMcount",
									 "sqlodshutdownICMcount",
									 "sqlshreplyICMcount",
									 "sqlhareplyICMcount",
									 "sqlodreplyICMcount"};
			
			String[] sqlSetSen = {" C_ITEM_STATUS3 IS NOT NULL",
								  " C_ITEM_STATUS3 IN ('按期已打开','延期已打开')",
								  " C_ITEM_STATUS3 IN ('到期未打开')",
								  " C_ITEM_STATUS5 IS NOT NULL",
								  " C_ITEM_STATUS5 IN ('按期已关闭','延期已关闭')",
								  " C_ITEM_STATUS5 IN ('到期未关闭')",
								  " C_ITEM_STATUS4 IS NOT NULL",
								  " C_ITEM_STATUS4 IN ('按期已回复','延期已回复')",
								  " C_ITEM_STATUS4 IN ('到期未回复')"};
			
			String[] icGainKey = {"shopenICMcount",
								  "haopenICMcount",
								  "odopenICMcount",
								  "shshutdownICMcount",
								  "hashutdownICMcount",
								  "odshutdownICMcount",
								  "shreplyICMcount",
								  "hareplyICMcount",
								  "odreplyICMcount"};
			
			StringBuffer sql = new StringBuffer("select C_PROJECT_NAME, "
											  + "(SELECT COUNT(*) FROM(SELECT DISTINCT C_PROJECT_NAME, C_CODE5 FROM ecm_document ed WHERE C_CODE5 IS NOT NULL AND C_PROJECT_NAME IS NOT NULL) AS tonum WHERE C_PROJECT_NAME=ed.C_PROJECT_NAME) as toNum, "
											  + "(SELECT COUNT(*) FROM (SELECT DISTINCT C_PROJECT_NAME, C_CODE5, C_CODE6 FROM ecm_document ed WHERE C_PROJECT_NAME IS NOT NULL AND C_CODE5 IS NOT NULL AND C_CODE6 IS NOT NULL) AS fromnum WHERE C_PROJECT_NAME=ed.C_PROJECT_NAME) as fromNum");
			
			for(int i = 0; i < 9; i++) {
				String column = sqlSetColumn[i];
				String query = icmCountSQL.replace("@condition", sqlSetSen[i]);
				
				sql.append(", (");
				sql.append(query);
				sql.append(") as ");
				sql.append(column);
			}
			
			sql.append(" from ecm_document ed where TYPE_NAME='ICM' and C_PROJECT_NAME in (");
			sql.append(icmReportStatistc);
			sql.append(") group by C_PROJECT_NAME");
			//sql.append(" from ecm_document ed where TYPE_NAME='ICM' and C_PROJECT_NAME = ed.C_PROJECT_NAME group by C_PROJECT_NAME");
	        
			List<Map<String, Object>> listStatistic = documentService.getMapList(getToken(), sql.toString());
			
	        for(Map<String, Object> item : listStatistic) {
	        	projMap = new HashMap<String, Object>();
				projMap.put("projectName", item.get("C_PROJECT_NAME"));		
				
				int toNum = (int)item.get("toNum");
				projMap.put("toNum", toNum);
				int fromNum = (int)item.get("fromNum");
				projMap.put("fromNum", fromNum);
				
		        for(int i = 0; i < 9; i++) {
					int icmTableGain = (int)item.get(sqlSetColumn[i]);
					projMap.put(icGainKey[i], icmTableGain);
				}
		        
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
	
	private String setSQLTimeSE(String key1, String key2) {
		return " and (C_ITEM_DATE BETWEEN '" + key1 + "' and '" + key2 + "')";
	}
	
	private String setSQLTimeE(String key) {
		return " and (C_ITEM_DATE < '" + key + "')";
	}
	
	private String setSQLTimeS(String key) {
		return " and (C_ITEM_DATE > '" + key + "')";
	}
	
	private String setSQLTimeEmp() {
		return "";
	}
}
