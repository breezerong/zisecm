package com.ecm.portal.archive.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmFolderMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.QueryService;
import com.ecm.core.service.RelationService;
import com.ecm.portal.archive.services.ArchiveFolderService;
import com.ecm.portal.archive.services.ArchiveRelationService;
import com.ecm.portal.archive.services.ImportService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class ImportController extends ControllerAbstract{

	public static String imporFolderId;
	
	@Autowired
	private NumberService numberService;
	
	@Autowired
	private ImportService importService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private QueryService queryService;
	
	@Autowired
	private FolderService folderService;
	
	
	@RequestMapping(value = "/import/getImportList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getImportList(@PathVariable("id") String id) throws Exception {
		String condition = "C_FROM_CODE='"+id+"' and TYPE_NAME='导入批次' ";
		Pager pager = new Pager();
		pager.setPageIndex(0);
		pager.setPageSize(2000);
		List<Map<String, Object>> objList = documentService.getObjectsByConditon(getToken(), "BatchImportGrid", null, pager, condition, "CREATION_DATE DESC");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", objList);
		return mp;
	}
	
	@RequestMapping(value = "/import/getImportTemplates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getImportTemplates(@RequestBody String path) throws Exception {
//		if(ImportController.imporFolderId==null) {
//			ImportController.imporFolderId = folderService.getObjectByPath(getToken(), "/系统配置/导入模板").getId();
//		}
		ImportController.imporFolderId = folderService.getObjectByPath(getToken(), path).getId();
		String sql = "select ID,NAME from ecm_document where FOLDER_ID='"+ImportController.imporFolderId+"' and TYPE_NAME='模板'  order by NAME";
		List<Map<String, Object>> objList = queryService.executeSQL(getToken(), sql);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", objList);
		return mp;
	}
	
	@RequestMapping(value = "/import/getImportTemplatesCommon", method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Map<String, Object> getImportTemplatesCommon(@RequestBody String tmpPath) throws Exception {
//		if(ImportController.imporFolderId==null) {
//			ImportController.imporFolderId = folderService.getObjectByPath(getToken(), "/系统配置/导入模板").getId();
//		}
		if(tmpPath==null||"".equals(tmpPath)) {
			tmpPath="/系统配置/导入模板";
		}
		ImportController.imporFolderId = folderService.getObjectByPath(getToken(), tmpPath).getId();
		String sql = "select ID,NAME from ecm_document where FOLDER_ID='"+ImportController.imporFolderId+"' and TYPE_NAME='模板'  order by NAME";
		List<Map<String, Object>> objList = queryService.executeSQL(getToken(), sql);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", objList);
		return mp;
	}
	
	@RequestMapping(value = "/import/getImportDocList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getImportDocList(@PathVariable("id") String id) throws Exception {
		String condition = "C_BATCH_CODE='"+id+"' ";
		Pager pager = new Pager();
		pager.setPageIndex(0);
		pager.setPageSize(2000);
		List<Map<String, Object>> objList = documentService.getObjectsByConditon(getToken(), "GeneralGrid", null, pager, condition, "C_ORDER_INDEX,CREATION_DATE DESC");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("data", objList);
		return mp;
	}
	@RequestMapping(value = "/import/batchGeneralImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchImport(@RequestParam("metaData")String metaData,@RequestParam("excel") MultipartFile excel, @RequestParam("files") MultipartFile[] files) throws AccessDeniedException{
		
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		String msg;
		try {
			String relationName=args.get("relationName")==null?"":args.get("relationName").toString();
			msg = importService.importExcel(getToken(),args.get("id").toString(),excel, files);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
		
	}
	@RequestMapping(value = "/import/batchSystemImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchSystemImport(@RequestParam("metaData")String metaData,@RequestParam("excel") MultipartFile excel, @RequestParam("files") MultipartFile[] files) throws AccessDeniedException{
		
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		String msg;
		try {
			msg = importService.importSystemExcel(getToken(),args.get("id").toString(),excel, files);
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
		
	}
	
	@RequestMapping(value = "/import/importOnServer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importOnServer(@RequestParam("argStr")String argStr) throws AccessDeniedException{
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String msg;
		try {
			msg = importService.importOnServer(getToken(),args.get("scanFolder").toString(),Integer.parseInt(args.get("folderCount").toString()));
			mp.put("code", ActionContext.SUCESS);
			mp.put("data", msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		
		return mp;
	}
	
}
	
