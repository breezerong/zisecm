package com.ecm.portal.archive.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.DateUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmCardSearch;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.QueryService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class DocumentExController extends ControllerAbstract{

	public static String imporFolderId;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private QueryService queryService;
	@Autowired
	private ContentService contentService;
	
	
	@RequestMapping(value = "/dc/getVerionsEx", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVerionsEx(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			EcmDocument doc = documentService.getObjectById(getToken(), id);
			if(doc != null) {
				if(doc.getAttributes().get("CODING")!=null)
				{
					
					String coding = doc.getAttributes().get("CODING").toString();
					String typeName = doc.getTypeName();
					if(!StringUtils.isEmpty(coding))
					{
						String cond = "TYPE_NAME='"+typeName+"' and CODING='"+coding+"' order by REVISION DESC";
						List<Map<String, Object>>  docs = documentService.getObjectMap(getToken(), cond);
						mp.put("data", docs);
						mp.put("code", ActionContext.SUCESS);
					}else {
						mp.put("data", null);
						mp.put("code", ActionContext.FAILURE);
						mp.put("message", "编码为空。");
					}
				}
				else {
					mp.put("data", null);
					mp.put("code", ActionContext.FAILURE);
					mp.put("message", "编码为空。");
				}
			}
			else {
				mp.put("data", null);
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "文件不存在。");
			}
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDocumentsByConditon", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getDocumentsByConditon(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		String condition = args.get("condition").toString();
		try {
			List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(), args.get("gridName").toString(), null, pager, condition, args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getExportExcelByFind", method = RequestMethod.POST)
	@ResponseBody
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody String argStr) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String condition = args.get("condition").toString();
		String gridName = args.get("gridName").toString();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		List<EcmGridViewItem> list = gv.getGridViewItems("zh-cn");
		String[] titleName = new String[list.size() + 1];
		titleName[0] = "ID";
		String[] titleCNName = new String[list.size() + 1];
		titleCNName[0] = "行ID";
		for (int i = 1; i < list.size() + 1; i++) {
			titleName[i] = list.get(i - 1).getAttrName();
			titleCNName[i] = list.get(i - 1).getLabel();
		}
		datalist.add(titleCNName);
		try {
			List<Map<String, Object>> dataByCondition = documentService.getObjectsByConditon(getToken(), gridName, null, null, condition, args.get("orderBy").toString());
			for (Map<String, Object> map : dataByCondition) {
				Object[] values = new Object[titleName.length];
				for (int i = 0; i < titleName.length; i++) {
					if (map.get(titleName[i]) != null) {
						if (titleName[i].equals("C_DOC_DATE")) {
							values[i] = DateUtils.DateToFolderPath((Date) map.get(titleName[i]), "-");
						} else {
							values[i] = map.get(titleName[i]);
						}
					} else {
						values[i] = "";
					}
				}
				datalist.add(values);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			excelUtil.makeStreamExcel("filelist.xlsx", "EXPORT", titleName, datalist, response, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/dc/getExportExcelByFindTemplate", method = RequestMethod.POST)
	@ResponseBody
	public void getExportExcelByFindTemplate(HttpServletRequest request, HttpServletResponse response, @RequestBody String argStr) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String templateName=args.get("templateName").toString();
		String condition = args.get("condition").toString();
		String gridName = args.get("gridName").toString();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		Map<String,Object> excelData=new HashMap<String, Object>();
		try {
			String sql="" + 
					"select C_PROJECT_CODE,CODING,NAME,c_page_count,c_count1,c_count2," + 
					"sum(case when wfperson='校对' and questionType='优化问题' then num else 0 end) as C_STRING1," + 
					"sum(case when wfperson='校对' and questionType='违反强制性条文' then num else 0 end) as C_REVIEWER6," + 
					"sum(case when wfperson='校对' and questionType='一般错误' then num else 0 end) as C_STRING3," + 
					"sum(case when wfperson='工种负责人审核' and questionType='优化问题' then num else 0 end) as C_STRING4," + 
					"sum(case when wfperson='工种负责人审核' and questionType='违反强制性条文' then num else 0 end) as C_REVIEWER1," + 
					"sum(case when wfperson='工种负责人审核' and questionType='一般错误' then num else 0 end) as C_REVIEWER2," + 
					"sum(case when wfperson='所负责人审定' and questionType='优化问题' then num else 0 end) as C_REVIEWER3," + 
					"sum(case when wfperson='所负责人审定' and questionType='违反强制性条文' then num else 0 end) as C_REVIEWER4," + 
					"sum(case when wfperson='所负责人审定' and questionType='一般错误' then num else 0 end) as C_REVIEWER5," + 
					"C_STRING2,C_DRAFTER,C_DOC_DATE,STATUS" + 
					" from(" + 
					"select C_PROJECT_CODE,CODING,NAME,C_STRING2,C_DRAFTER,C_DOC_DATE,STATUS,wfperson,questionType,c_page_count,c_count1,c_count2,num from(" + 
					" " + 
					"select a.C_PROJECT_CODE,a.CODING,a.NAME,a.C_STRING2,a.C_DRAFTER,a.C_DOC_DATE,a.STATUS,a.c_page_count,a.c_count1,a.c_count2,b.C_PROJECT_CODE as wfperson,b.C_PROJECT_NAME as questionType,count(b.ID) as num from ecm_document a" + 
					" left join ecm_relation c on a.id=c.PARENT_ID" + 
					" left join ecm_document b on b.id=c.CHILD_ID" + 
					" and a.type_name='图纸文件' and b.type_name='设计校审记录' " + 
					" group by a.C_FROM_CODING,b.C_PROJECT_CODE ,b.C_PROJECT_NAME " + 
					"" + 
					") t group by CODING,wfperson,questionType" + 
					")x where STATUS ='已完成'  ";
			
			Object c= args.get("condition");
			if(c!=null) {
				sql+=" and ("+c.toString()+")";
			}
			
			sql+=" group by CODING ORDER BY C_DOC_DATE DESC ";
			
			List<Map<String,Object>> list= documentService.getMapList(getToken(), sql);
			
			excelData.put("data", list);
			
			String sqlTemp = "select ID,NAME from ecm_document where name='"+templateName+"' and TYPE_NAME='模板'  order by NAME";
			List<Map<String, Object>> objList = queryService.executeSQL(getToken(), sqlTemp);
			
			if(objList!=null||objList.size()>0) {
				Map<String,Object> templateData= objList.get(0);
				String templateId=templateData.get("ID").toString();

				EcmContent en = contentService.getPrimaryContent(getToken(), templateId);
				String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
				String tempatePath= fullPath+en.getFilePath();
				excelUtil.makeStreamExcel("filelist.xlsx",tempatePath ,excelData, response);
			}
			
		} catch (AccessDeniedException | IOException | EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value = "/search/getCardInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCardInfo(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String cardNameStr = "";
		if (args.get("cardName").equals("卷盒")) {
			cardNameStr = "所有";
		}else {
			cardNameStr = args.get("cardName").toString();
		}
		EcmCardSearch cardSearch = CacheManagerOper.getEcmCardSearchs().get(cardNameStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("data", cardSearch);
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDocsByFolderPathName", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDocsByFolderName(@RequestBody String argStr){
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String folderPath = args.get("folderPath").toString();
		String condition = " FOLDER_ID in (select id FROM ecm_folder where folder_path ='"+folderPath+"') ";
		String gridName = args.get("gridName").toString();
		if(args.get("condition").toString()!=null&&!args.get("condition").toString().equals("")) {
			condition = args.get("condition").toString()+" and FOLDER_ID in (select id FROM ecm_folder where folder_path ='"+folderPath+"') ";
		}
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		try {
			List<Map<String, Object>> dataList = documentService.getObjectsByConditon(getToken(), gridName, null, pager, condition, args.get("orderBy").toString());
			mp.put("data", dataList);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	/**
	 * 根据folderPath获取文件夹下的所有数据，并判断gridView
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getDocsViewDataByFolderPath", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDocsViewDataByFolderPath(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		String folderPath = args.get("folderPath").toString();
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		String gridView = "";
		String condition = "";
		String folderSql = "select * from ecm_folder where folder_path = '"+folderPath+"'";
		if(args.get("condition").toString()!=null&&!args.get("condition").toString().equals("")) {
			condition = args.get("condition").toString();
		}
		try {
			List<Map<String, Object>> list = queryService.executeSQL(getToken(), folderSql);
			Map<String, Object> folderMap = list.get(0);
			gridView = folderMap.get("GRID_VIEW").toString();
			String folderId = folderMap.get("ID").toString();
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridView);
			List<EcmGridViewItem> ecmList = gv.getGridViewItems("zh-cn");
			mp.put("view", ecmList);
			List<Map<String, Object>> dataList = documentService.getObjectsByConditon(getToken(), gridView, folderId, pager, condition, args.get("orderBy").toString());
			mp.put("data", dataList);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			e.printStackTrace();
		}
		return mp;
	}
	
}
	
