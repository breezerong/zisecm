package com.ecm.portal.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.SQL;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.ChartBean;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.entity.UserEntity;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.QueryService;
import com.ecm.core.service.RelationService;
import com.ecm.portal.util.ExcelUtil;

/**
 * @ClassName EcmDcController
 * @Description 文档访问
 * @author Haihong Rong
 * @date 2018年8月11日 下午2:21:50
 *
 */
@Controller
public class EcmDcController extends ControllerAbstract{

	@Autowired
	private DocumentService documentService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private RelationService relatoinService;
	
	@Autowired
	private FolderService folderService;

	@Autowired
	private QueryService queryService;



	private static final Logger logger = LoggerFactory.getLogger(EcmDcController.class);

	@RequestMapping(value = "/dc/getDocumentCount", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocumentCount(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			long count = documentService.getObjectCount(getToken(),
					args.get("gridName").toString(), args.get("folderId").toString(),
					args.get("condition").toString());
			mp.put("itemCount", count);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/dc/getDocuments", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocuments(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			List<Map<String, Object>> list = documentService.getObjects(getToken(),
					args.get("gridName").toString(), args.get("folderId").toString(),
					pager,
					args.get("condition").toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getNewDocuments", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getNewDocument(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		Pager pager = new Pager();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		String orderBy = args.get("orderBy").toString();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize); 
		StringBuffer sql = new StringBuffer("select * from ecm_document where STATUS='利用'  and TYPE_NAME<>'卷盒' ");
		if(!StringUtils.isEmpty(orderBy)) {
			sql.append("order by"+orderBy);
		}else {
			sql.append("order by ID desc");
		}
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			list = documentService.getMapList(getToken(), sql.toString(), pager);
			if (list.size()>0) {
				mp.put("data",list);
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("data", null);
				mp.put("code", ActionContext.SUCESS);
			}
		} catch (AccessDeniedException | EcmException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			e.printStackTrace();
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getContainBoxDocuments", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getContainBoxDocuments(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer("("+gv.getCondition()+"or TYPE_NAME = '卷盒' )  and STATUS='利用'");
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%"+newCondition+"%' or CODING like '%"+newCondition+"%')");
			}
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(), args.get("gridName").toString(), args.get("folderId").toString(), pager, condition.toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	@RequestMapping(value = "/dc/getExceptBoxDocuments", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getObjectsExceptBox(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer("("+gv.getCondition()+" and TYPE_NAME<>'卷盒' )  and STATUS='利用'");
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%"+newCondition+"%' or CODING like '%"+newCondition+"%')");
			}
			List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(), args.get("gridName").toString(), args.get("folderId").toString(), pager, condition.toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDeletedDocuments", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getDeletedObjects(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer("("+gv.getCondition()+" and TYPE_NAME<>'卷盒' )  and STATUS='注销'  ");
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%"+newCondition+"%' or CODING like '%"+newCondition+"%')");
			}
			List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(), args.get("gridName").toString(), args.get("folderId").toString(), pager, condition.toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDelContainBoxDocuments", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getDelContainBoxetedObjects(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer("("+gv.getCondition()+" or TYPE_NAME = '卷盒' )  and STATUS='注销' ");
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%"+newCondition+"%' or CODING like '%"+newCondition+"%')");
			}
			List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(), args.get("gridName").toString(), args.get("folderId").toString(), pager, condition.toString(), args.get("orderBy").toString());
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	
	
	@RequestMapping(value = "/dc/getDocument", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocument(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> data = documentService.getObjectMapById(getToken(),id);
			int permit = documentService.getPermit(getToken(), id);
			boolean hasPdf = data.get("FORMAT_NAME")!=null && data.get("FORMAT_NAME").toString().equalsIgnoreCase("pdf");
			if(!hasPdf) {
				List<EcmContent> list = contentService.getObjects(getToken(), id, 0);
				if(list!=null) {
					for(EcmContent en:list) {
						if("pdf".equalsIgnoreCase(en.getFormatName())){
							hasPdf = true;
							break;
						}
					}
				}
			}
			mp.put("data", data);
			mp.put("permit", permit);
			mp.put("hasPdf", hasPdf);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	

	private String getRequestData(HttpServletRequest req)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			BufferedReader reader = req.getReader();
			char[] buff = new char[1024];
			int len = reader.read(buff);
			while(len != -1)
			{
				sb.append(buff, 0, len);
				len = reader.read(buff);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	@RequestMapping(value = "/dc/getContent") // , method = RequestMethod.POST PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public void getContent(HttpServletRequest request,HttpServletResponse response) {
		//String data = getRequestData(request);
		
		try {
			String id = "";
			if(request.getAttribute("id")!=null){
				id = request.getAttribute("id").toString();
			}
			else{
				id = request.getParameter("id");
			}
			String action = "";
			if(request.getAttribute("action")!=null){
				action = request.getAttribute("action").toString();
			}
			else{
				action = request.getParameter("action");
			}
			String format = "";
			if(request.getAttribute("format")!=null){
				format = request.getAttribute("format").toString();
			}
			else{
				format = request.getParameter("format");
			}
			EcmContent en = null;
			if(!StringUtils.isEmpty(format)){
				en = contentService.getObject(getToken(), id, 0, format);
			}else
			{
				en =contentService.getPrimaryContent(getToken(),id);
			}
			InputStream iStream = contentService.getContentStream(getToken(),en);
			// 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(en.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + en.getContentSize());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            try {
            	response.setContentType("application/octet-stream");
	            byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = iStream.read(buffer)) != -1) {
					toClient.write(buffer, 0, bytesRead);
				}
            }finally {
				iStream.close();
	            toClient.flush();
	            toClient.close();
	            if(!StringUtils.isEmpty(action)&&action.equals("download")) {
	            	contentService.newAudit(getToken(), "portal", AuditContext.DOWNLOAD, id, null, null);
	            }else {
	            	contentService.newAudit(getToken(), "portal", AuditContext.READ, id, null, null);
	            }
            }
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/dc/getExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public void getExportExcel(HttpServletRequest request,HttpServletResponse response,@RequestBody String argStr) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		String limitExportNum = "2000";
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String gridName = args.get("gridName").toString();
		String lang = args.get("lang").toString();
		String folderId= args.get("folderId").toString();
		String orderBy = args.get("orderBy").toString();
		StringBuffer sql = new StringBuffer("select ID,");
		StringBuffer condition = new StringBuffer();
		StringBuffer queryAttr = new StringBuffer();
		boolean showBox = Boolean.parseBoolean(args.get("showBox").toString()) ;
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		if (showBox) {
			condition.append("("+gv.getCondition()+"or TYPE_NAME = '卷盒') and STATUS='利用'");
		}else {
			condition.append("("+gv.getCondition()+"and TYPE_NAME<>'卷盒') and STATUS='利用'");
		}
		List<EcmGridViewItem> list = gv.getGridViewItems(lang);
		String[] titleName = new String[list.size()+1];
		titleName[0] = "ID";
		String[] titleCNName =new String[list.size()+1];
		titleCNName[0] = "行ID";
		for(int i = 1;i<list.size()+1;i++) {
			titleName[i] = list.get(i-1).getAttrName();
			titleCNName[i] = list.get(i-1).getLabel(); 
			queryAttr.append(list.get(i-1).getAttrName()+",");
		}
		datalist.add(titleCNName);
		sql.append(queryAttr.deleteCharAt(queryAttr.length()-1).toString()+" from ecm_document where 1=1");
		if (!StringUtils.isEmpty(folderId)) {
			sql.append(" and folder_id='"+folderId+"'");
		}if (!StringUtils.isEmpty(condition.toString())) {
			sql.append(" and "+condition.toString());
		}if (!StringUtils.isEmpty(orderBy)) {
			sql.append(" order by " + orderBy);
		}else {
			sql.append(" order by ID desc");
		}
		try {
			List<Map<String, Object>> data = documentService.getMapList(getToken(), sql.toString());
			for (Map<String, Object> map : data) {
				Object[] values = new Object[titleName.length];
				for(int i=0;i<titleName.length;i++) {
					if ( map.get(titleName[i]) != null ) {
						if(titleName[i].equals("C_DOC_DATE")) {
							values[i] = DateUtils.DateToFolderPath((Date) map.get(titleName[i]), "-");
						}else {
							values[i] = map.get(titleName[i]);
						}
					}else {
						values[i] = "";
					}
				}
				datalist.add(values);
			}
		} catch (EcmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AccessDeniedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			excelUtil.makeStreamExcel("filelist.xlsx","EXPORT",titleName,datalist,response,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/dc/saveDocument", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> saveDocument(@RequestBody String argStr) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		documentService.updateObject(getToken(), doc, null);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	@RequestMapping(value = "/dc/newDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newDocument(String metaData, MultipartFile uploadFile) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmContent en = null; 
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		if (uploadFile != null) {
			en = new EcmContent();
			en.setName(uploadFile.getOriginalFilename());
			en.setContentSize(uploadFile.getSize());
			en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
			en.setInputStream(uploadFile.getInputStream());
		}
		String id = documentService.newObject(getToken(),doc,en);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}
	
	@RequestMapping(value = "/dc/newAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newAudit(@RequestBody String objId) throws Exception {
		
		
		String id = documentService.newAudit(getToken(), null, AuditContext.READ, objId, null, null);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}

	@RequestMapping(value = "/dc/delDocument", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> delDocument(@RequestBody String argStr) throws Exception {
		List<String> list = JSONUtils.stringToArray(argStr);
		for (String id : list) {
			documentService.deleteObject(getToken(),id);
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	/**
	 * 获取所有列表项
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dc/getGridViewInfo", method = RequestMethod.POST)
	public Map<String, Object> getGridViewInfo(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String gridName = args.get("gridName").toString();
		String lang = args.get("lang").toString();
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		List<EcmGridViewItem> list = gv.getGridViewItems(lang);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}
	/**
	 * 获取项目
	 * @param itemInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dc/getProjects", method = RequestMethod.POST)
	public Map<String, Object> getOptions(){
		String condition=" TYPE_NAME='项目' and STATUS='启用'  ";
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list=documentService.getObjectMap(getToken(),condition);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
		
	}
	
	/**
	 * 获取所有表单项
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dc/getFormItem", method = RequestMethod.POST)
	public Map<String, Object> getFormItem(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String itemInfo = args.get("itemInfo").toString();
		String lang = args.get("lang").toString();
		EcmDocument en = null;
		List<EcmFormItem> list = null;
		try {
			en = documentService.getObjectById(getToken(),itemInfo);
			EcmForm frm = CacheManagerOper.getEcmForms().get(en.getTypeName() + "_EDIT");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(en.getTypeName() + "_1");
			}
			list = frm.getEcmFormItems(lang);
		} catch (Exception ex) {
			EcmForm frm = CacheManagerOper.getEcmForms().get(itemInfo + "_NEW");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(itemInfo + "_1");
			}
			list = frm.getEcmFormItems(lang);
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", list);
		return mp;
	}
	

	@ResponseBody
	@RequestMapping(value = "/dc/lockDocument", method = RequestMethod.POST)
	public Map<String, Object> lockDocument(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			documentService.lock(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@ResponseBody
	@RequestMapping(value = "/dc/unlockDocument", method = RequestMethod.POST)
	public Map<String, Object> unlockDocument(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			documentService.unlock(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	/**
	 * 挂载文件
	 * @param metaData
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping(value = "/dc/mountFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mountFile(String metaData, MultipartFile uploadFile) {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = args.get("ID").toString();
			if (uploadFile != null) {
				EcmContent en = new EcmContent();
				en.setName(uploadFile.getOriginalFilename());
				en.setContentSize(uploadFile.getSize());
				en.setInputStream(uploadFile.getInputStream());
				en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
				en.setContentType(1);
				documentService.mountFile(getToken(), id, en);
				mp.put("code", ActionContext.SUCESS);
			}
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	/**
	 * 添加格式副本
	 * @param metaData
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping(value = "/dc/addRendition", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRendition(String metaData, MultipartFile uploadFile) {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = args.get("ID").toString();
			if (uploadFile != null) {
				EcmContent en = new EcmContent();
				en.setName(uploadFile.getOriginalFilename());
				en.setContentSize(uploadFile.getSize());
				en.setInputStream(uploadFile.getInputStream());
				documentService.addRendition(getToken(), id, en);
				mp.put("code", ActionContext.SUCESS);
			}
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	/**
	 * 删除格式副本
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dc/removeRendition", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRendition(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			documentService.removeRendition(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/checkOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkOut(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			documentService.checkOut(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	
	
	@RequestMapping(value = "/dc/checkIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkIn(String metaData, MultipartFile uploadFile) {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String id = args.get("id").toString();
			boolean isCurrent = args.get("isCurrent").toString()=="1"?true:false;
			EcmContent en = null;
			if (uploadFile != null) {
				en = new EcmContent();
				en.setName(uploadFile.getOriginalFilename());
				en.setContentSize(uploadFile.getSize());
				en.setInputStream(uploadFile.getInputStream());
			}
			documentService.checkIn(getToken(), id, en,isCurrent);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getVerions", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVerions(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<Map<String, Object>>  docs = documentService.getAllVersionsMap(getToken(), id);
			mp.put("data", docs);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getRenditions", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRenditions(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmContent>  list = contentService.getObjects(getToken(), id, 0);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getRelations", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRelations(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String sql = "select b.ID,a.NAME AS RELATION_NAME,a.PARENT_ID,a.CHILD_ID,b.NAME,b.CODING,b.REVISION,b.TITLE,b.C_SECURITY_LEVEL,b.CREATOR,b.CREATION_DATE,b.C_DOC_DATE"
					+ " from ecm_relation a, ecm_document b where a.NAME not like 'irel%' and (a.PARENT_ID=b.ID or a.CHILD_ID=b.ID)"
					+ " and b.ID='"+id+"' order by b.CREATION_DATE";
			//System.out.println(sql);
			List<Map<String, Object>>  list = documentService.getMapList(getToken(), sql);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/getDocUseInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDocUseInfo(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("(select count(*) from ecm_relation where NAME='irel_borrow' and CHILD_ID='"+id+"') as borrowCount,");
			sql.append("(select count(*) from ecm_audit_general where ACTION_NAME='ecm_read' and DOC_ID='"+id+"') as readCount,");
			sql.append("(select count(*) from ecm_audit_general where ACTION_NAME='ecm_download' and DOC_ID='"+id+"') as downloadCount");
			sql.append(" from dual ");
			List<Map<String, Object>>  list = documentService.getMapList(getToken(), sql.toString());
			mp.put("data", list.get(0));
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/createRelation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createRelation(@RequestBody EcmRelation en) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			relatoinService.newObject(getToken(), en);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/deleteRelation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRelation(@RequestBody EcmRelation en) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			relatoinService.deleteObject(getToken(), en);
			mp.put("code", ActionContext.SUCESS);
		}
		catch(Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	@RequestMapping(value = "/dc/updatePrimaryContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrimaryContent(String id,MultipartFile uploadFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			InputStream instream = null;
			String fileName = null;
			if(uploadFile!=null) {
				instream = uploadFile.getInputStream();
				fileName = uploadFile.getOriginalFilename();
				EcmContent en = contentService.getPrimaryContent(getToken(), id);
				EcmDocument doc =documentService.getObjectById(getToken(), id);
				if(en== null) {
					
					en = new EcmContent();
					en.createId();
					en.setName(fileName);
					en.setContentSize(uploadFile.getSize());
					en.setFormatName(FileUtils.getExtention(fileName));
					en.setInputStream(instream);
					en.setParentId(id);
					en.setContentType(1);
					if(StringUtils.isEmpty(en.getStoreName())) {
						en.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
					}
					doc.setFormatName(en.getFormatName());
					doc.setContentSize(en.getContentSize());
					contentService.newObject(getToken(), en);
					documentService.updateObject(getToken(), doc, null);
					if(instream!=null) {
						instream.close();
					}
					mp.put("code", ActionContext.SUCESS);
				}else {
					en.setName(fileName);
					en.setContentSize(uploadFile.getSize());
					en.setFormatName(FileUtils.getExtention(fileName));
					en.setInputStream(instream);
//					contentService.updateObject(getToken(), en);
					doc.setFormatName(en.getFormatName());
					doc.setContentSize(en.getContentSize());
					
					documentService.updateObject(getToken(), doc, en);
					if(instream!=null) {
						instream.close();
					}
					mp.put("code", ActionContext.SUCESS);
				}
			}
			{
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "File is null.");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	//销毁文件，更改文件状态为“注销”
	@RequestMapping(value = "/dc/destroyDocuments", method = RequestMethod.POST)
	 @ResponseBody
	 public Map<String,Object> getDocumentByRelationParentId(@RequestBody String argStr) {
	  List<String> list = JSONUtils.stringToArray(argStr);
	  Map<String, Object> mp = new HashMap<String, Object>();
	  try {
		  for (String id : list) {
			  EcmDocument ecmDocument = documentService.getObjectById(getToken(), id);
			  if(ecmDocument.getTypeName().equals("卷盒")||ecmDocument.getTypeName().equals("图册")) {
				  documentService.updateStatus(getToken(), id, "注销");
				  String sql = "select b.*,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
						     + " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
						     + " and a.PARENT_ID='"+id+"' order by a.ORDER_INDEX,b.CREATION_DATE";
				  List<Map<String, Object>>  childList = documentService.getMapList(getToken(), sql);
				  for (Map<String, Object> child : childList) {
					String childId = (String) child.get("CHILD_ID");
					documentService.updateStatus(getToken(), childId, "注销");
				}
			  }else {
				documentService.updateStatus(getToken(), id, "注销");
			}
		  }
		  mp.put("code", ActionContext.SUCESS);
	  }
	  catch(Exception ex) {
	   mp.put("code", ActionContext.FAILURE);
	   mp.put("message", ex.getMessage());
	  }
	  return mp;
	 }
	
	//下架文件，更改文件状态为“整编”
		@RequestMapping(value = "/dc/obtainDocuments", method = RequestMethod.POST)
		 @ResponseBody
		 public Map<String,Object> obtainDocument(@RequestBody String argStr) {
		  List<String> list = JSONUtils.stringToArray(argStr);
		  Map<String, Object> mp = new HashMap<String, Object>();
		  try {
			  for (String id : list) {
				  EcmDocument ecmDocument = documentService.getObjectById(getToken(), id);
				  if(ecmDocument.getTypeName().equals("卷盒")||ecmDocument.getTypeName().equals("图册")) {
					  documentService.updateStatus(getToken(), id, "整编");
					  String sql = "select b.ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX,b.NAME,b.CODING,b.C_SECURITY_LEVEL,b.REVISION,b.TITLE,b.CREATOR,b.TYPE_NAME,b.SUB_TYPE,b.CREATION_DATE"
							     + " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
							     + " and a.PARENT_ID='"+id+"' order by a.ORDER_INDEX,b.CREATION_DATE";
					  List<Map<String, Object>>  childList = documentService.getMapList(getToken(), sql);
					  for (Map<String, Object> child : childList) {
						String childId = (String) child.get("CHILD_ID");
						documentService.updateStatus(getToken(), childId, "整编");
					}
				  }else {
					documentService.updateStatus(getToken(), id, "整编");
				}
			  }
			  mp.put("code", ActionContext.SUCESS);
		  }
		  catch(Exception ex) {
		   mp.put("code", ActionContext.FAILURE);
		   mp.put("message", ex.getMessage());
		  }
		  return mp;
		 }
		//在回收站恢复文档
		@RequestMapping(value = "/dc/restoreDocuments", method = RequestMethod.POST)
		 @ResponseBody
		 public Map<String,Object> restoreDocument(@RequestBody String argStr) {
		  List<String> list = JSONUtils.stringToArray(argStr);
		  Map<String, Object> mp = new HashMap<String, Object>();
		  try {
			  for (String id : list) {
				  EcmDocument ecmDocument = documentService.getObjectById(getToken(), id);
				  if(ecmDocument.getTypeName().equals("卷盒")||ecmDocument.getTypeName().equals("图册")) {
					  documentService.updateStatus(getToken(), id, "利用");
					  String sql = "select b.ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX,b.NAME,b.CODING,b.C_SECURITY_LEVEL,b.REVISION,b.TITLE,b.CREATOR,b.TYPE_NAME,b.SUB_TYPE,b.CREATION_DATE"
							     + " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID "
							     + " and a.PARENT_ID='"+id+"' order by a.ORDER_INDEX,b.CREATION_DATE";
					  List<Map<String, Object>>  childList = documentService.getMapList(getToken(), sql);
					  for (Map<String, Object> child : childList) {
						String childId = (String) child.get("CHILD_ID");
						documentService.updateStatus(getToken(), childId, "利用");
					}
				  }else {
					documentService.updateStatus(getToken(), id, "利用");
				}
			  }
			  mp.put("code", ActionContext.SUCESS);
		  }
		  catch(Exception ex) {
		   mp.put("code", ActionContext.FAILURE);
		   mp.put("message", ex.getMessage());
		  }
		  return mp;
		 }
		
		//创建接口返回馆藏状态信息，数据用于echarts显示
		@RequestMapping("/dc/getCollectionData")
		@ResponseBody
		public Map<String, Object> collectionData(){
			Map<String, Object> mp = new HashMap<String, Object>();
			ChartBean bean = new ChartBean();
			Pager pager = new Pager();
			pager.setPageIndex(0);
			pager.setPageSize(10000);
			List<EcmFolder> list = new ArrayList<EcmFolder>();
			List<Map<String, Object>> arcCountList = new ArrayList<Map<String,Object>>();
			try {
				list = folderService.getFoldersByParentPath(getToken(),"/档案库");
				arcCountList = documentService.getMapList(getToken(), "select C_ARC_CLASSIC, count(*) C_COUNT from ecm_document where STATUS='利用' and type_name<>'卷盒' and C_ARC_CLASSIC<>'' and C_ARC_CLASSIC is not null group by C_ARC_CLASSIC");
				for (EcmFolder ecmFolder : list) {
					String ecmName = ecmFolder.getName();
					boolean flag = false;
					long count = 0;
					bean.getxAxisData().add(ecmFolder.getName());
					for (Map<String, Object> map : arcCountList) {
						if(ecmName.equals(map.get("C_ARC_CLASSIC"))) {
							flag = true;
							count = (long) map.get("C_COUNT");
						}
					}
					if (flag) {
						bean.getyAxisData().add(count);
						flag = false;
						count = 0;
					}else {
						bean.getyAxisData().add(count);
					}
				}
				mp.put("data", bean);
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException | EcmException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
				e.printStackTrace();
			}
			
			return mp;
		}
		@RequestMapping(value = "/dc/getSelectList", method = RequestMethod.POST)
		 @ResponseBody
		 public Map<String,Object> getSelectList(@RequestBody String argStr) {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String queryName = args.get("queryName").toString();
			String dependValue = args.get("dependValue").toString();
			Map<String, Object> mp = new HashMap<String, Object>();
			EcmQuery query;
			try {
				query = queryService.getObjectByName(getToken(), queryName);
				String sql = query.getSqlString();
				sql = sql.replace("{0}", dependValue);
				List<Map<String, Object>> list = queryService.executeSQL(getToken(), sql);
				List<String> listName = new ArrayList<String>();
				for(Map<String,Object> obj: list) {
					listName.add(obj.get("NAME").toString());
				}
				mp.put("data", listName);
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
			}
			return mp;
		}
}
