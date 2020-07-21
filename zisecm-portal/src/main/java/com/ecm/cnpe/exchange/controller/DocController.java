package com.ecm.cnpe.exchange.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.ecm.cnpe.exchange.controller.param.DocParam;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
@RequestMapping("/exchange/doc")
public class DocController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;
	
	@PostMapping("export")
	@ResponseBody
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody DocParam params) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(params.getGridName());
		gv.getGridViewItems();
		StringBuffer sql = new StringBuffer("select ID,");
		
		List<EcmGridViewItem> list = gv.getGridViewItems(params.getLang());
		String[] titleName = new String[list.size() + 1];
		String[] titleCNName = new String[list.size() + 1];
		StringBuffer queryAttr = new StringBuffer();
		titleName[0]="ID";
		titleCNName[0]="ID";
		for (int i = 1; i < list.size() + 1; i++) {
			titleName[i] = list.get(i - 1).getAttrName();
			titleCNName[i] = list.get(i - 1).getLabel();
			queryAttr.append(list.get(i - 1).getAttrName());
			queryAttr.append(",");
		}
		datalist.add(titleCNName);
		String gvCondition=gv.getCondition();
		if(gvCondition==null||"".equals(gvCondition)) {
			gvCondition="";
		}else {
			gvCondition=" and "+gvCondition;
		}
				
		sql.append(queryAttr.deleteCharAt(queryAttr.length() - 1).toString() 
				+ " from ecm_document where 1=1"+gvCondition);
		if (!StringUtils.isEmpty(params.getFolderId())) {
			sql.append(" and folder_id='" + params.getFolderId() + "'");
		}
		
		if (!StringUtils.isEmpty(params.getCondition())) {
			sql.append(" and " + params.getCondition());
		}
		
		if (!StringUtils.isEmpty(params.getOrderBy())) {
			sql.append(" order by " + params.getOrderBy());
		} else {
			sql.append(" order by ID desc");
		}
		try {
			List<Map<String,Object>> queryList =documentService.getMapList(getToken(), sql.toString());
			for (Map<String, Object> map : queryList) {
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
		} catch (EcmException | AccessDeniedException e) {
			e.printStackTrace();
		}
		
		try {
			excelUtil.makeStreamExcel(params.getFilename(), params.getSheetname(),titleName, datalist, response, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出，cnpe
	 * @param request
	 * @param response
	 * @param params
	 */
	@PostMapping("export4Cnpe")
	@ResponseBody
	public void getExportExcel4Cnpe(HttpServletRequest request, HttpServletResponse response, @RequestBody DocParam params) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(params.getGridName());
		gv.getGridViewItems();
		StringBuffer columnsStr = new StringBuffer(" ID,");
		
		List<EcmGridViewItem> list = gv.getGridViewItems(params.getLang());
		String[] titleName = new String[list.size() + 1];
		String[] titleCNName = new String[list.size() + 1];
		StringBuffer queryAttr = new StringBuffer();
		titleName[0]="ID";
		titleCNName[0]="ID";
		for (int i = 1; i < list.size() + 1; i++) {
			titleName[i] = list.get(i - 1).getAttrName();
			titleCNName[i] = list.get(i - 1).getLabel();
			queryAttr.append(list.get(i - 1).getAttrName());
			queryAttr.append(",");
		}
		datalist.add(titleCNName);
		String gvCondition=gv.getCondition();
		if(gvCondition==null||"".equals(gvCondition)) {
			gvCondition=" 1=1 ";
		}else {
			gvCondition=" "+gvCondition;
		}
		columnsStr.append(queryAttr.deleteCharAt(queryAttr.length() - 1).toString());
		
		String sql="select "+ columnsStr.toString() +" from (" + 
				"	select a.*,b.STAUTS as STAUTS,b.TO_NAME from ecm_document a, exc_transfer b where a.id=b.doc_id" + 
				")t where "+gvCondition;
		
		
		if (!StringUtils.isEmpty(params.getFolderId())) {
			sql += " and folder_id='" + params.getFolderId() + "'";
		}
		if (!EcmStringUtils.isEmpty(params.getCondition())) {
			sql += " and (" + params.getCondition() + ")";
		}
		if (!EcmStringUtils.isEmpty(params.getOrderBy())) {
			sql += " order by " + params.getOrderBy();
		} else {
			sql += " " + gv.getOrderBy();
		}
		
		String currentUser="";
		LoginUser userObj=null;
		try {
			userObj=this.getSession().getCurrentUser();
			currentUser = userObj.getUserName();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sql!=null&&sql.contains("@currentuser")) {
			sql=sql.replaceAll("@currentuser", currentUser);
    	}
		if(sql!=null&&sql.contains("@company")) {
			sql=sql.replaceAll("@company", userObj.getCompany());
	    }
		
		
		try {
			List<Map<String,Object>> queryList =documentService.getMapList(getToken(), sql);
			for (Map<String, Object> map : queryList) {
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
		} catch (EcmException | AccessDeniedException e) {
			e.printStackTrace();
		}
		
		try {
			excelUtil.makeStreamExcel(params.getFilename(), params.getSheetname(),titleName, datalist, response, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出，cnpe
	 * @param request
	 * @param response
	 * @param params
	 */
	@PostMapping("export4Report")
	@ResponseBody
	public void getExport4Report(HttpServletRequest request, HttpServletResponse response, @RequestBody DocParam params) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(params.getGridName());
		gv.getGridViewItems();
		StringBuffer columnsStr = new StringBuffer(" ID,");
		
		List<EcmGridViewItem> list = gv.getGridViewItems(params.getLang());
		String[] titleName = new String[list.size() + 1];
		String[] titleCNName = new String[list.size() + 1];
		StringBuffer queryAttr = new StringBuffer();
		titleName[0]="ID";
		titleCNName[0]="ID";
		for (int i = 1; i < list.size() + 1; i++) {
			titleName[i] = list.get(i - 1).getAttrName();
			titleCNName[i] = list.get(i - 1).getLabel();
			queryAttr.append(list.get(i - 1).getAttrName());
			queryAttr.append(",");
		}
		datalist.add(titleCNName);
		
		columnsStr.append(queryAttr.deleteCharAt(queryAttr.length() - 1).toString());
		
		
		String sql="select "+ columnsStr.toString() +" from (" + 
				"select a.*,b.id as rid from ecm_document a " + 
				"left join ecm_document b on a.CODING=b.C_OTHER_CODING " + 
				"where a.TYPE_NAME='图文传真' and a.C_ITEM_STATUS1='是' and a.C_ITEM1_DATE<=dateadd(day, -1, getdate()) " + 
				") t where rid is null ";
		
		
		
		if (!StringUtils.isEmpty(params.getFolderId())) {
			sql += " and folder_id='" + params.getFolderId() + "'";
		}
		if (!EcmStringUtils.isEmpty(params.getCondition())) {
			sql += " and (" + params.getCondition() + ")";
		}
		if (!EcmStringUtils.isEmpty(params.getOrderBy())) {
			sql += " order by " + params.getOrderBy();
		} else {
			sql += " " + gv.getOrderBy();
		}
		
		String currentUser="";
		LoginUser userObj=null;
		try {
			userObj=this.getSession().getCurrentUser();
			currentUser = userObj.getUserName();
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sql!=null&&sql.contains("@currentuser")) {
			sql=sql.replaceAll("@currentuser", currentUser);
    	}
		if(sql!=null&&sql.contains("@company")) {
			sql=sql.replaceAll("@company", userObj.getCompany());
	    }
		if(sql!=null&&sql.contains("@project")) {
			List<String> projectList= userObj.getMyProjects();
			
			if(projectList==null||projectList.size()==0) {
				sql=sql.replaceAll("'@project'", "''");
				sql=sql.replaceAll("@project", "''");
			}else {
				String whereProject=" (";
				for(int i=0;i<projectList.size();i++) {
					String project=projectList.get(i);
					if(i==0) {
						whereProject+="C_PROJECT_NAME ='"+project+"'";
					}else {
						whereProject+=" or C_PROJECT_NAME ='"+project+"'";
					}
					
				}
				whereProject+=")";
				if(sql.contains("C_PROJECT_NAME in(@project)")) {
					sql=sql.replaceAll("C_PROJECT_NAME in(@project)", whereProject);
				} 
				if(sql.contains("C_PROJECT_NAME in (@project)")) {
					sql=sql.replaceAll("C_PROJECT_NAME in (@project)", whereProject);
				} 
				if(sql.contains("C_PROJECT_NAME in (@project) ")) {
					sql=sql.replaceAll("C_PROJECT_NAME in (@project) ", whereProject);
				} 
				if(sql.contains("C_PROJECT_NAME='@project'")) {
					sql=sql.replaceAll("C_PROJECT_NAME='@project'", whereProject);
				}
				if(sql.contains("C_PROJECT_NAME ='@project'")) {
					sql=sql.replaceAll("C_PROJECT_NAME ='@project'", whereProject);
				}
				if(sql.contains("C_PROJECT_NAME = '@project'")) {
					sql=sql.replaceAll("C_PROJECT_NAME = '@project'", whereProject);
				}
				if(sql.contains("C_PROJECT_NAME = '@project' ")) {
					sql=sql.replaceAll("C_PROJECT_NAME = '@project' ", whereProject);
				}
			}
			
		}
		
		try {
			List<Map<String,Object>> queryList =documentService.getMapList(getToken(), sql);
			for (Map<String, Object> map : queryList) {
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
		} catch (EcmException | AccessDeniedException e) {
			e.printStackTrace();
		}
		
		try {
			excelUtil.makeStreamExcel(params.getFilename(), params.getSheetname(),titleName, datalist, response, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
