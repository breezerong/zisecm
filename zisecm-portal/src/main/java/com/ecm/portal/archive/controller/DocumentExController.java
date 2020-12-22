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
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
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
						String cond = "TYPE_NAME='"+typeName+"' and CODING='"+coding+"' and IS_RELEASED=1 order by REVISION DESC";
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
	
