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
											  + "(SELECT COUNT(*) FROM (SELECT DISTINCT C_PROJECT_NAME, C_CODE5 FROM ecm_document ed WHERE C_CODE5 IS NOT NULL AND C_PROJECT_NAME IS NOT NULL"+ timeCheck +") AS tonum WHERE C_PROJECT_NAME=ed.C_PROJECT_NAME) as toNum, "
											  + "(SELECT COUNT(*) FROM (SELECT DISTINCT C_PROJECT_NAME, C_CODE5, C_CODE6 FROM ecm_document ed WHERE C_PROJECT_NAME IS NOT NULL AND C_CODE5 IS NOT NULL AND C_CODE6 IS NOT NULL"+ timeCheck +") AS fromnum WHERE C_PROJECT_NAME=ed.C_PROJECT_NAME) as fromNum");
			
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
			//sql.append(" from ecm_document ed where TYPE_NAME='ICM' and C_PROJECT_NAME = ed.C_PROJECT_N AME group by C_PROJECT_NAME");
	        
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
	
	@RequestMapping(value = "/exchange/icm/ICMRNeweport", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> ICMRNeweport(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object> > outList = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			Map<String, Object> projMap = new HashMap<String, Object>();
			
			String icmReportStatistc = this.getStrValue(args, "icmReportStatistc");
			String startDate = this.getStrValue(args, "startDate");
			String endDate = this.getStrValue(args, "endDate");
			
			String sqlName = "select NAME from ecm_document where TYPE_NAME='配置项' and SUB_TYPE='主办所' and C_PROJECT_NAME IN ("+ icmReportStatistc +") ORDER　BY C_ORDER_INDEX";
			List<Map<String, Object>> listName = documentService.getMapList(getToken(), sqlName.toString());
			
			List<String> projList = new ArrayList<String>();
			
			for(Map<String, Object> map : listName) {
				for(String s : map.keySet()) {
					projList.add((String) map.get(s));
				}
			}
			
			int sumPlanOpen = 0;
			int sumActurOpen = 0;
			int sumNotOpen = 0;
			int sumDelayOpenYr = 0;
			int sumDelayOpenNr = 0;
			int sumSchOpen = 0;
			double sumPerSchOpen = 0.00;
			double sumPerOpen = 0.00;
			int sumPlanReply = 0;
			int sumActurReply = 0;
			int sumNotReply = 0;
			int sumDelayReplyYr = 0;
			int sumDelayReplyNr = 0;
			int sumSchReply = 0;
			double sumPerSchReply = 0.00;
			double sumPerReply = 0.00;
			int sumPlanClose = 0;
			int sumActurClose = 0;
			int sumNotClose = 0;
			int sumDelayCloseYr = 0;
			int sumDelayCloseNr = 0;
			int sumSchClose = 0;
			double sumPerSchClose = 0.00;
			double sumPerClose = 0.00;
			
			for(String nameType: projList) {
				projMap = new HashMap<String, Object>(); 
				
				String sqlplanOpen = "SELECT COUNT(*) FROM ecm_document WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND C_ITEM1_DATE > '"+ startDate +"' AND C_ITEM2_DATE < '"+ endDate +"'";
				String sqlacturOpen = "SELECT COUNT(*) FROM ecm_document WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM1_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX1_DATE IS NOT NULL AND (C_ITEM2_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX3_DATE IS NOT NULL";
				String sqlnotOpen = "SELECT COUNT(*) FROM ecm_document WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM1_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX1_DATE IS NULL AND (C_ITEM2_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX3_DATE IS NULL";
				String sqldelayOpenYr = "SELECT COUNT(*) FROM ecm_document WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM1_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX1_DATE IS NULL AND (C_ITEM2_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX3_DATE IS NULL AND C_ITEM_STATUS3 = '是'";
				String sqldelayOpenNr = "SELECT COUNT(*) FROM ecm_document WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM1_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX1_DATE IS NULL AND (C_ITEM2_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX3_DATE IS NULL AND C_ITEM_STATUS3 = '否'";
				String sqlschOpen = "SELECT COUNT(*) FROM ecm_document WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM1_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_ITEM1_DATE <= C_EX1_DATE AND (C_ITEM2_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_ITEM2_DATE <= C_EX3_DATE";
				
				String sqlopen = "SELECT C_HOST, "
						   + "("+ sqlplanOpen +") AS planOpen,"
						   + "("+ sqlacturOpen +") AS acturOpen,"
						   + "("+ sqlnotOpen +") AS notOpen,"
						   + "("+ sqldelayOpenYr +") AS delayOpenYr,"
						   + "("+ sqldelayOpenNr +") AS delayOpenNr,"
						   + "("+ sqlschOpen +") AS schOpen "
						   + "from ecm_document where TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' GROUP BY C_HOST";
				
				List<Map<String, Object>> listOpen = documentService.getMapList(getToken(), sqlopen.toString());
				
				if(listOpen != null && listOpen.size() > 0) {
					projMap.put("sponsorName", nameType);
					int cpOpen = getSponsorT(listOpen, "planOpen");
					sumPlanOpen += cpOpen;
					projMap.put("planOpen", cpOpen);
					int caOpen = getSponsorT(listOpen, "acturOpen");
					sumActurOpen += caOpen;
					projMap.put("acturOpen", caOpen);
					int cnOpen = getSponsorT(listOpen, "notOpen");
					sumNotOpen += cnOpen;
					projMap.put("notOpen", cnOpen);
					int cdyOpen = getSponsorT(listOpen, "delayOpenYr");
					sumDelayOpenYr += cdyOpen;
					projMap.put("delayOpenYr", cdyOpen);
					int cdnOpen = getSponsorT(listOpen, "delayOpenNr");
					sumDelayOpenNr += cdnOpen;
					projMap.put("delayOpenNr", cdnOpen);
					int csOpen = getSponsorT(listOpen, "schOpen");
					sumSchOpen += csOpen;
					projMap.put("schOpen", csOpen);
					
					double perSchOpen = (double)csOpen/cpOpen;
					double perOpen = (double)caOpen/cpOpen;
					
					if(csOpen == 0 || cpOpen == 0) {
						perSchOpen = 0.00;
					}
					if(caOpen == 0 || cpOpen == 0) {
						perOpen = 0.00;
					}
					
					sumPerSchOpen += perSchOpen;
					sumPerOpen += perOpen;
					
					projMap.put("perSchOpen", perSchOpen);
					projMap.put("perOpen", perOpen);
				}
				
				String sqlplanReply = "SELECT COUNT(*) FROM ecm_document WHERE C_HOST = '"+ nameType +"' AND (C_REPLY_PLAN_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND (TYPE_NAME = '接口信息意见单' OR TYPE_NAME = '接口信息传递单' )";
				String sqlacturReply = "SELECT COUNT(*) FROM ecm_document WHERE C_HOST = '"+ nameType +"' AND (C_REPLY_PLAN_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND (TYPE_NAME = '接口信息意见单' OR TYPE_NAME = '接口信息传递单' ) AND C_EX2_STRING IS NOT NULL";
				String sqlnotReply = "SELECT COUNT(*) FROM ecm_document WHERE C_HOST = '"+ nameType +"' AND (C_REPLY_PLAN_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND (TYPE_NAME = '接口信息意见单' OR TYPE_NAME = '接口信息传递单' ) AND C_EX2_STRING IS NULL";
				String sqldelayReplyYr = "SELECT COUNT(*) FROM ecm_document WHERE C_HOST = '"+ nameType +"' AND (C_REPLY_PLAN_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND (TYPE_NAME = '接口信息意见单' OR TYPE_NAME = '接口信息传递单' ) AND C_EX2_STRING IS NULL AND C_ITEM_STATUS3 = '是'";
				String sqldelayReplyNr = "SELECT COUNT(*) FROM ecm_document WHERE C_HOST = '"+ nameType +"' AND (C_REPLY_PLAN_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND (TYPE_NAME = '接口信息意见单' OR TYPE_NAME = '接口信息传递单' ) AND C_EX2_STRING IS NULL AND C_ITEM_STATUS3 = '否'";
				String sqlschReply = "SELECT COUNT(*) FROM ecm_document WHERE C_HOST = '"+ nameType +"' AND (C_REPLY_PLAN_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND (TYPE_NAME = '接口信息意见单' OR TYPE_NAME = '接口信息传递单' ) AND C_EX2_DATE < C_REPLY_PLAN_DATE";
				
				String sqlreply = "SELECT C_HOST, "
						   + "("+ sqlplanReply +") AS planReply,"
						   + "("+ sqlacturReply +") AS acturReply,"
						   + "("+ sqlnotReply +") AS notReply,"
						   + "("+ sqldelayReplyYr +") AS delayReplyYr,"
						   + "("+ sqldelayReplyNr +") AS delayReplyNr,"
						   + "("+ sqlschReply +") AS schReply "
						   + "from ecm_document where TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' GROUP BY C_HOST";
				
				List<Map<String, Object>> listReply = documentService.getMapList(getToken(), sqlreply.toString());
				
				if(listReply != null && listReply.size() > 0) {
					int cpReply = getSponsorT(listReply, "planReply");
					sumPlanReply += cpReply;
					projMap.put("planReply", cpReply);
					int caReply = getSponsorT(listReply, "acturReply");
					sumActurReply += caReply;
					projMap.put("acturReply", caReply);
					int cnReply = getSponsorT(listReply, "notReply");
					sumNotReply += cnReply;
					projMap.put("notReply", cnReply);
					int cdyReply = getSponsorT(listReply, "delayReplyYr");
					sumDelayReplyYr += cdyReply;
					projMap.put("delayReplyYr", cdyReply);
					int cdnReply = getSponsorT(listReply, "delayReplyNr");
					sumDelayReplyNr += cdnReply;
					projMap.put("delayReplyNr", cdnReply);
					int csReply = getSponsorT(listReply, "schReply");
					sumSchReply += csReply;
					projMap.put("schReply", csReply);
					
					double perSchReply = (double)csReply/cpReply;
					double perReply = (double)caReply/cpReply;
					
					if(csReply == 0 || cpReply == 0) {
						perSchReply = 0.00;
					}
					if(caReply == 0 || cpReply == 0) {
						perReply = 0.00;
					}
					
					sumPerSchReply += perSchReply;
					sumPerReply += perReply;
					
					projMap.put("perSchReply", perSchReply);
					projMap.put("perReply", perReply);
				}
				
				String sqlplanClose = "SELECT COUNT(*) FROM ecm_document ed WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND C_ITEM3_DATE > '"+ startDate +"' AND C_ITEM6_DATE < '"+ endDate +"'";
				String sqlacturClose = "SELECT COUNT(*) FROM ecm_document ed WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM3_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX2_DATE IS NOT NULL AND (C_ITEM6_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX4_DATE IS NOT NULL";
				String sqlnotClose = "SELECT COUNT(*) FROM ecm_document ed WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM3_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX2_DATE IS NULL AND (C_ITEM6_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX4_DATE IS NULL";
				String sqldelayCloseYr = "SELECT COUNT(*) FROM ecm_document ed WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM3_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX2_DATE IS NULL AND (C_ITEM6_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX4_DATE IS NULL AND C_ITEM_STATUS4 = '是'";
				String sqldelayCloseNr = "SELECT COUNT(*) FROM ecm_document ed WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM3_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX2_DATE IS NULL AND (C_ITEM6_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_EX4_DATE IS NULL AND C_ITEM_STATUS4 = '否'";
				String sqlschClose = "SELECT COUNT(*) FROM ecm_document ed WHERE TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' AND (C_ITEM3_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_ITEM3_DATE <= C_EX2_DATE AND (C_ITEM6_DATE BETWEEN '"+ startDate +"' AND '"+ endDate +"') AND C_ITEM6_DATE <= C_EX4_DATE";
				
				String sqlclose = "SELECT C_HOST, "
						   + "("+ sqlplanClose +") AS planClose,"
						   + "("+ sqlacturClose +") AS acturClose,"
						   + "("+ sqlnotClose +") AS notClose,"
						   + "("+ sqldelayCloseYr +") AS delayCloseYr,"
						   + "("+ sqldelayCloseNr +") AS delayCloseNr,"
						   + "("+ sqlschClose +") AS schClose "
						   + "from ecm_document where TYPE_NAME = 'ICM' AND C_HOST = '"+ nameType +"' GROUP BY C_HOST";
				
				List<Map<String, Object>> listClose = documentService.getMapList(getToken(), sqlclose.toString());
				
				if(listClose != null && listClose.size() > 0) {
					int cpClose = getSponsorT(listClose, "planClose");
					sumPlanClose += cpClose;
					projMap.put("planClose", cpClose);
					int caClose = getSponsorT(listClose, "acturClose");
					sumActurClose += caClose;
					projMap.put("acturClose", caClose);
					int cnClose = getSponsorT(listClose, "notClose");
					sumNotClose += cnClose;
					projMap.put("notClose", cnClose);
					int cdyClose = getSponsorT(listClose, "delayCloseYr");
					sumDelayCloseYr += cdyClose;
					projMap.put("delayCloseYr", cdyClose);
					int cdnClose = getSponsorT(listClose, "delayCloseNr");
					sumDelayCloseNr += cdnClose;
					projMap.put("delayCloseNr", cdnClose);
					int csClose = getSponsorT(listClose, "schClose");
					sumSchClose += csClose;
					projMap.put("schClose", csClose);
					
					double perSchClose = (double)csClose/cpClose;
					double perClose = (double)caClose/cpClose;
					
					if(csClose == 0 || cpClose == 0) {
						perSchClose = 0.00;
					}
					if(caClose == 0 || cpClose == 0) {
						perClose = 0.00;
					}
					
					sumPerSchClose += perSchClose;
					sumPerClose += perClose;
					
					projMap.put("perSchClose", perSchClose);
					projMap.put("perClose", perClose);
				}
				
				if(projMap != null && projMap.size() > 0) {
					outList.add(projMap);
				}
			}
			
			projMap = new HashMap<String, Object>();
			projMap.put("sponsorName", "合计");
			projMap.put("planOpen", sumPlanOpen);
			projMap.put("acturOpen", sumActurOpen);
			projMap.put("notOpen", sumNotOpen);
			projMap.put("delayOpenYr", sumDelayOpenYr);
			projMap.put("delayOpenNr", sumDelayOpenNr);
			projMap.put("schOpen", sumSchOpen);
			projMap.put("perSchOpen", sumPerSchOpen);
			projMap.put("perOpen", sumPerOpen);
			projMap.put("planReply", sumPlanReply);
			projMap.put("acturReply", sumActurReply);
			projMap.put("notReply", sumNotReply);
			projMap.put("delayReplyYr", sumDelayReplyYr);
			projMap.put("delayReplyNr", sumDelayReplyNr);
			projMap.put("schReply", sumSchReply);
			projMap.put("perSchReply", sumPerSchReply);
			projMap.put("perReply", sumPerReply);
			projMap.put("planClose", sumPlanClose);
			projMap.put("acturClose", sumActurClose);
			projMap.put("notClose", sumNotClose);
			projMap.put("delayCloseYr", sumDelayCloseYr);
			projMap.put("delayCloseNr", sumDelayCloseNr);
			projMap.put("schClose", sumSchClose);
			projMap.put("perSchClose", sumPerSchClose);
			projMap.put("perClose", sumPerClose);
			
			outList.add(projMap);
			
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
	
	private int getSponsorT(List<Map<String, Object>> list, String key) {
		return (list.get(0).get(key)!=null)?(int)list.get(0).get(key):0;
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
