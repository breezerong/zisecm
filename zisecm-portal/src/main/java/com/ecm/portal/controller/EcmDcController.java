package com.ecm.portal.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.PermissionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.dao.EcmRelationMapper;
import com.ecm.core.dao.EcmShopingCartMapper;
import com.ecm.core.db.DBBase;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.ChartBean;
import com.ecm.core.entity.EcmAcl;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormClassification;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmPermit;
import com.ecm.core.entity.EcmQuery;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.EcmShopingCart;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.MessageException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AclService;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.QueryService;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.portal.service.ZipDownloadService;

/**
 * @ClassName EcmDcController
 * @Description 文档访问
 * @author Haihong Rong
 * @date 2018年8月11日 下午2:21:50
 *
 */
@Controller
public class EcmDcController extends ControllerAbstract {

	@Autowired
	private DocumentService documentService;
	@Autowired
	private RelationService relationService;

	@Autowired
	private EcmShopingCartMapper shopingCartService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private RelationService relatoinService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private FolderPathService folderPathService;

	@Autowired
	private NumberService numberService;

	@Autowired
	private AuthService authService;
	@Autowired
	private ZipDownloadService zipDownloadService;

	@Autowired
	private EcmContentMapper contentMapper;
	@Autowired
	private EcmRelationMapper ecmRelationMapper;
	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(EcmDcController.class);

	@RequestMapping(value = "/dc/getDocumentCount", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocumentCount(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);

			long count = documentService.getObjectCount(getToken(), args.get("gridName").toString(),
					args.get("folderId").toString(), args.get("condition").toString());
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
			List<Map<String, Object>> list = documentService.getObjects(getToken(), args.get("gridName").toString(),
					args.get("folderId")==null?"":args.get("folderId").toString(), pager, args.get("condition").toString(),
					args.get("orderBy").toString());
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
		if (!StringUtils.isEmpty(orderBy)) {
			sql.append("order by" + orderBy);
		} else {
			sql.append("order by ID desc");
		}
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list = documentService.getMapList(getToken(), sql.toString(), pager);
			if (list.size() > 0) {
				mp.put("data", list);
				mp.put("code", ActionContext.SUCESS);
			} else {
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
			String folderId = (String) args.get("folderId");
			EcmFolder ecmFolder = folderService.getObjectById(getToken(), folderId);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer(
					"(" + gv.getCondition() + "or TYPE_NAME = '卷盒' )  and STATUS='利用'");
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%" + newCondition + "%' or CODING like '%" + newCondition
						+ "%') and FOLDER_ID in (SELECT id from ecm_folder where folder_path like '"
						+ ecmFolder.getFolderPath() + "%')");
			}
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			if (!EcmStringUtils.isEmpty(newCondition)) {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), null, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			} else {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), folderId, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			}
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
			String folderId = (String) args.get("folderId");
			EcmFolder ecmFolder = folderService.getObjectById(getToken(), folderId);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer(
					"(" + gv.getCondition() + " and TYPE_NAME<>'卷盒' )  and STATUS='利用'");
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%" + newCondition + "%' or CODING like '%" + newCondition
						+ "%') and FOLDER_ID in (SELECT id from ecm_folder where folder_path like '"
						+ ecmFolder.getFolderPath() + "%')");
			}
			if (!EcmStringUtils.isEmpty(newCondition)) {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), null, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			} else {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), folderId, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			}
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
			String folderId = (String) args.get("folderId");
			EcmFolder ecmFolder = folderService.getObjectById(getToken(), folderId);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer(
					"(" + gv.getCondition() + " and TYPE_NAME<>'卷盒' )  and STATUS='注销'  ");
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%" + newCondition + "%' or CODING like '%" + newCondition
						+ "%') and FOLDER_ID in (SELECT id from ecm_folder where folder_path like '"
						+ ecmFolder.getFolderPath() + "%')");
			}
			if (!EcmStringUtils.isEmpty(newCondition)) {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), null, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			} else {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), folderId, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			}
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
			String folderId = (String) args.get("folderId");
			EcmFolder ecmFolder = folderService.getObjectById(getToken(), folderId);
			EcmGridView gv = CacheManagerOper.getEcmGridViews().get(args.get("gridName").toString());
			StringBuffer condition = new StringBuffer(
					"(" + gv.getCondition() + " or TYPE_NAME = '卷盒' )  and STATUS='注销' ");
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			String newCondition = args.get("condition").toString();
			if (!EcmStringUtils.isEmpty(newCondition)) {
				condition.append(" and (NAME like '%" + newCondition + "%' or CODING like '%" + newCondition
						+ "%') and FOLDER_ID in (SELECT id from ecm_folder where folder_path like '"
						+ ecmFolder.getFolderPath() + "%')");
			}
			if (!EcmStringUtils.isEmpty(newCondition)) {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), null, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			} else {
				List<Map<String, Object>> list = documentService.getObjectsByConditon(getToken(),
						args.get("gridName").toString(), folderId, pager, condition.toString(),
						args.get("orderBy").toString());
				mp.put("data", list);
				mp.put("pager", pager);
				mp.put("code", ActionContext.SUCESS);
			}
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
			Map<String, Object> data = documentService.getObjectMapById(getToken(), id);
			int permit = documentService.getPermit(getToken(), id);
			boolean hasPdf = data.get("FORMAT_NAME") != null
					&& data.get("FORMAT_NAME").toString().equalsIgnoreCase("pdf");
			if (!hasPdf) {
				List<EcmContent> list = contentService.getObjects(getToken(), id, 0);
				if (list != null) {
					for (EcmContent en : list) {
						if ("pdf".equalsIgnoreCase(en.getFormatName())) {
							hasPdf = true;
							break;
						}
					}
				}
			}
			long changeCount = 0;
			String typeName = data.get("TYPE_NAME").toString();
			if (typeName.equals("图纸文件")) {
				try {
					String coding = data.get("CODING").toString();
					String revision = data.get("REVISION").toString();
					String cond = " TYPE_NAME='变更文件' and CODING='" + coding + "' and REVISION='" + revision + "'";
					changeCount = documentService.getObjectCount(getToken(), null, null, cond);
				} catch (Exception ex) {

				}
			}
			mp.put("changeCount", changeCount);
			mp.put("data", data);
			mp.put("permit", permit);
			mp.put("hasPdf", hasPdf);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}

	private String getRequestData(HttpServletRequest req) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader reader = req.getReader();
			char[] buff = new char[1024];
			int len = reader.read(buff);
			while (len != -1) {
				sb.append(buff, 0, len);
				len = reader.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	@RequestMapping(value = "/dc/getFilePath", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getFilePath(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			
			Object objId= args.get("id");
			if(objId==null) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "ID不能为空");
				return mp;
			}
			String id=objId.toString();
			EcmContent en = null;
			if (!StringUtils.isEmpty(args.get("format")==null?"":args.get("format").toString())) {
					en = contentService.getObject(getToken(), id, 0, args.get("format").toString());
			} else {
				en = contentService.getPrimaryContent(getToken(), id);
			}
			String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			mp.put("data", fullPath+en.getFilePath());
			mp.put("code", ActionContext.SUCESS);
			return mp;
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("message", e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			return mp;
		}
	}
	
	@RequestMapping(value = "/dc/getContent") // , method = RequestMethod.POST PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public void getContent(HttpServletRequest request, HttpServletResponse response) {
		// String data = getRequestData(request);
		try {
			String id = "";
			if (request.getAttribute("id") != null) {
				id = request.getAttribute("id").toString();
			} else {
				id = request.getParameter("id");
			}
			String action = "";
			if (request.getAttribute("action") != null) {
				action = request.getAttribute("action").toString();
			} else {
				action = request.getParameter("action");
			}
			String format = "";
			if (request.getAttribute("format") != null) {
				format = request.getAttribute("format").toString();
			} else {
				format = request.getParameter("format");
			}
			EcmContent en = null;
			if (!StringUtils.isEmpty(format)) {
				en = contentService.getObject(getToken(), id, 0, format);
			} else {
				en = contentService.getPrimaryContent(getToken(), id);
			}
			InputStream iStream = contentService.getContentStream(getToken(), en);
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(en.getName(), "UTF-8"));
			response.addHeader("Content-Length", "" + en.getContentSize());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			try {
				response.setContentType("application/octet-stream");
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = iStream.read(buffer)) != -1) {
					toClient.write(buffer, 0, bytesRead);
				}
			} finally {
				iStream.close();
				toClient.flush();
				toClient.close();
				if (!StringUtils.isEmpty(action) && action.equals("download")) {
					contentService.newAudit(getToken(), "portal", AuditContext.DOWNLOAD, id, null, null);
				} else {
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
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody String argStr) {
		ExcelUtil excelUtil = new ExcelUtil();
		List<Object[]> datalist = new ArrayList<Object[]>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String gridName = args.get("gridName").toString();
		String lang = args.get("lang").toString();
		String folderId = args.get("folderId").toString();
		String orderBy = args.get("orderBy").toString();
		StringBuffer sql = new StringBuffer("select ID,");
		StringBuffer condition = new StringBuffer();
		StringBuffer queryAttr = new StringBuffer();
		boolean showBox = Boolean.parseBoolean(args.get("showBox").toString());
		EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
		if (showBox) {
			condition.append("(" + gv.getCondition() + "or TYPE_NAME = '卷盒') and STATUS='利用'");
		} else {
			condition.append("(" + gv.getCondition() + "and TYPE_NAME<>'卷盒') and STATUS='利用'");
		}
		List<EcmGridViewItem> list = gv.getGridViewItems(lang);
		String[] titleName = new String[list.size() + 1];
		titleName[0] = "ID";
		String[] titleCNName = new String[list.size() + 1];
		titleCNName[0] = "行ID";
		for (int i = 1; i < list.size() + 1; i++) {
			titleName[i] = list.get(i - 1).getAttrName();
			titleCNName[i] = list.get(i - 1).getLabel();
			queryAttr.append(list.get(i - 1).getAttrName() + ",");
		}
		datalist.add(titleCNName);
		sql.append(queryAttr.deleteCharAt(queryAttr.length() - 1).toString() + " from ecm_document where 1=1");
		if (!StringUtils.isEmpty(folderId)) {
			sql.append(" and folder_id='" + folderId + "'");
		}
		if (!StringUtils.isEmpty(condition.toString())) {
			sql.append(" and " + condition.toString());
		}
		if (!StringUtils.isEmpty(orderBy)) {
			sql.append(" order by " + orderBy);
		} else {
			sql.append(" order by ID desc");
		}
		try {
			List<Map<String, Object>> data = documentService.getMapList(getToken(), sql.toString());
			for (Map<String, Object> map : data) {
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
		} catch (EcmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AccessDeniedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			excelUtil.makeStreamExcel("filelist.xlsx", "EXPORT", titleName, datalist, response, true);
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
	/**
	 * 创建或修改文件
	 * @param metaData 文件属性
	 * @param uploadFile 文件内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/createOrUpdateDoc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrUpdateDoc(String metaData, MultipartFile uploadFile) throws Exception {
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
		String id = documentService.creatOrUpdateObject(getToken(), doc, en);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
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
		String id = documentService.newObject(getToken(), doc, en);
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
			documentService.deleteObject(getToken(), id);
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
	 * 
	 * @param itemInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dc/getProjects", method = RequestMethod.POST)
	public Map<String, Object> getOptions() {
		String condition = " TYPE_NAME='项目' and STATUS='启用'  ";
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = documentService.getObjectMap(getToken(), condition);
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
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			en = documentService.getObjectById(getToken(), itemInfo);
			EcmForm frm = CacheManagerOper.getEcmForms().get(en.getTypeName() + "_EDIT");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(en.getTypeName() + "_1");
			}
			list = frm.getEcmFormItems(documentService.getSession(getToken()), lang);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			EcmForm frm = CacheManagerOper.getEcmForms().get(itemInfo + "_NEW");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(itemInfo + "_1");
			}
			try {
				list = frm.getEcmFormItems(documentService.getSession(getToken()), lang);
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
			}

		}
		mp.put("data", list);
		return mp;
	}
	
	/**
	 * 获取分类表单项
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dc/getFormClassifications", method = RequestMethod.POST)
	public Map<String, Object> getFormClassifications(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String itemInfo = args.get("itemInfo").toString();
		String lang = args.get("lang").toString();
		EcmDocument en = null;
		List<EcmFormClassification> list = null;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			en = documentService.getObjectById(getToken(), itemInfo);
			EcmForm frm = CacheManagerOper.getEcmForms().get(en.getTypeName() + "_EDIT");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(en.getTypeName() + "_1");
			}
			list = frm.getFormClassifications(documentService.getSession(getToken()), lang);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			EcmForm frm = CacheManagerOper.getEcmForms().get(itemInfo + "_NEW");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(itemInfo + "_1");
			}
			try {
				list = frm.getFormClassifications(documentService.getSession(getToken()), lang);
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
			}

		}
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
	 * 
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
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 添加格式副本
	 * 
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
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 删除格式副本
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dc/removeRendition", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeRendition(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			documentService.removeRendition(getToken(), id);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
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
		} catch (Exception ex) {
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
			boolean isCurrent = args.get("isCurrent").toString() == "1" ? true : false;
			EcmContent en = null;
			if (uploadFile != null) {
				en = new EcmContent();
				en.setName(uploadFile.getOriginalFilename());
				en.setContentSize(uploadFile.getSize());
				en.setInputStream(uploadFile.getInputStream());
			}
			documentService.checkIn(getToken(), id, en, isCurrent);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
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
			List<Map<String, Object>> docs = documentService.getAllVersionsMap(getToken(), id);
			mp.put("data", docs);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
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
			List<EcmContent> list = contentService.getObjects(getToken(), id, 0);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
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
			String sql = "select b.ID,a.NAME AS RELATION_NAME,a.PARENT_ID,a.CHILD_ID,b.NAME,b.CODING,b.REVISION,b.TITLE,b.C_SECURITY_LEVEL,b.CREATOR,b.CREATION_DATE,b.C_DOC_DATE" + 
					"					from ecm_relation a, ecm_document b where a.NAME not like 'irel%' and ((a.CHILD_ID=b.ID" + 
					"					and a.PARENT_ID='"+id+"') or (a.PARENT_ID=b.ID " + 
					"					and a.CHILD_ID='"+id+"')) order by b.CREATION_DATE";
			// System.out.println(sql);
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
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
			sql.append("(select count(*) from ecm_relation where NAME='irel_borrow' and CHILD_ID='" + id
					+ "') as borrowCount,");
			sql.append("(select count(*) from ecm_audit_general where ACTION_NAME='ecm_read' and DOC_ID='" + id
					+ "') as readCount,");
			sql.append("(select count(*) from ecm_audit_general where ACTION_NAME='ecm_download' and DOC_ID='" + id
					+ "') as downloadCount");
			if(DBBase.isOracle()||DBBase.isMySql()) {
				sql.append(" from dual ");
			}
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql.toString());
			mp.put("data", list.get(0));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
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
		} catch (Exception ex) {
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
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	@RequestMapping(value = "/dc/updatePrimaryContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrimaryContent(String id, MultipartFile uploadFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			InputStream instream = null;
			String fileName = null;
			if (uploadFile != null) {
				instream = uploadFile.getInputStream();
				fileName = uploadFile.getOriginalFilename();
				EcmContent en = contentService.getPrimaryContent(getToken(), id);
				EcmDocument doc = documentService.getObjectById(getToken(), id);
				if (en == null) {

					en = new EcmContent();
					en.createId();
					en.setName(fileName);
					en.setContentSize(uploadFile.getSize());
					en.setFormatName(FileUtils.getExtention(fileName));
					en.setInputStream(instream);
					en.setParentId(id);
					en.setContentType(1);
					if (StringUtils.isEmpty(en.getStoreName())) {
						en.setStoreName(CacheManagerOper.getEcmDefTypes().get(doc.getTypeName()).getStoreName());
					}
					doc.setFormatName(en.getFormatName());
					doc.setContentSize(en.getContentSize());
					contentService.newObject(getToken(), en);
					documentService.updateObject(getToken(), doc, null);
					if (instream != null) {
						instream.close();
					}
					mp.put("code", ActionContext.SUCESS);
				} else {
					en.setName(fileName);
					en.setContentSize(uploadFile.getSize());
					en.setFormatName(FileUtils.getExtention(fileName));
					en.setInputStream(instream);
//					contentService.updateObject(getToken(), en);
					doc.setFormatName(en.getFormatName());
					doc.setContentSize(en.getContentSize());

					documentService.updateObject(getToken(), doc, en);
					if (instream != null) {
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

	// 销毁文件，更改文件状态为“注销”
	@RequestMapping(value = "/dc/destroyDocuments", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> destroyDocuments(@RequestBody String argStr) {
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for (String id : list) {
				EcmDocument ecmDocument = documentService.getObjectById(getToken(), id);
				if (ecmDocument.getTypeName().equals("卷盒") || ecmDocument.getTypeName().equals("图册")) {
					documentService.updateStatus(getToken(), id, "注销");
					String sql = "select b.*,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX"
							+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID " + " and a.PARENT_ID='" + id
							+ "' order by a.ORDER_INDEX,b.CREATION_DATE";
					List<Map<String, Object>> childList = documentService.getMapList(getToken(), sql);
					for (Map<String, Object> child : childList) {
						String childId = (String) child.get("CHILD_ID");
						documentService.updateStatus(getToken(), childId, "注销");
					}
				} else {
					documentService.updateStatus(getToken(), id, "注销");
				}
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	// 添加到购物车
	@RequestMapping(value = "/dc/addToShopingCart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addToShopingCart(@RequestBody String argStr) {
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for (String id : list) {
				EcmDocument ecmDocument = documentService.getObjectById(getToken(), id);
				EcmShopingCart shopingCart = new EcmShopingCart();
				shopingCart.setDocumentId(id);
				String userName = this.getSession().getCurrentUser().getUserName();
				Date addDate=new Date();
				
				shopingCart.setAddDate(addDate);
				shopingCart.setUserName(userName);
				shopingCart.createId();

				List<Map<String, Object>> shopingObjectList = shopingCartService
						.executeSQL("select id  from ecm_shoping_cart where document_id='" + id + "' and user_name='"
								+ userName + "'");
				if (shopingObjectList.size() == 0) {
					shopingCartService.insertSelective(shopingCart);
				}
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
			ex.printStackTrace();
		}
		return mp;
	}

	// 清空购物车
	@RequestMapping(value = "/dc/cleanShopingCart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cleanShopingCart(@RequestBody String argStr) {
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String userName = this.getSession().getCurrentUser().getUserName();
			List<Map<String, Object>> shopingObjectList = shopingCartService
					.executeSQL("delete from ecm_shoping_cart where  user_name='" + userName + "'");
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
			ex.printStackTrace();
		}
		return mp;
	}

	// 从购物车删除
	@RequestMapping(value = "/dc/removeShopingCart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeShopingCart(@RequestBody String argStr) {
		List<String> list = JSONUtils.stringToArray(argStr);
		StringBuffer documentIds = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				documentIds.append(",");
			}
			documentIds.append("'").append(list.get(i).toString()).append("'");
		}
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if (documentIds.length() > 0) {
				String userName = this.getSession().getCurrentUser().getUserName();
				List<Map<String, Object>> shopingObjectList = shopingCartService
						.executeSQL("delete from ecm_shoping_cart where  user_name='" + userName
								+ "' and document_id in (" + documentIds.toString() + ")");
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
			ex.printStackTrace();
		}
		return mp;
	}

	// 添加到购物车
	@RequestMapping(value = "/dc/openShopingCart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> openShopingCart(@RequestBody String argStr) {
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object>> shopingCartList1 = null;
		List<Map<String, Object>> shopingCartList2 = new ArrayList<Map<String, Object>>();
		try {
			shopingCartList1 = shopingCartService
					.executeSQL("select DOCUMENT_ID from ecm_shoping_cart where user_name='"
							+ getSession().getCurrentUser().getUserName() + "'");

			EcmDocument docObj;
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < shopingCartList1.size(); i++) {
				if (i == 0) {
					sb.append("'").append(shopingCartList1.get(i).get("DOCUMENT_ID").toString()).append("'");
				} else {
					sb.append(",'").append(shopingCartList1.get(i).get("DOCUMENT_ID").toString()).append("'");
				}
			}
			if (!"".equals(sb.toString())) {
				String gridName = "shopingCartGrid";
				EcmGridView gv = CacheManagerOper.getEcmGridViews().get(gridName);
				String sql = "select a.ID as ID ,a.FORMAT_NAME as FORMAT_NAME" + getGridColumn(gv, gridName)
						+ "  from ecm_document a,ecm_shoping_cart b where a.ID=b.DOCUMENT_ID and  a.ID in("
						+ sb.toString() + ") and user_name='" + getSession().getCurrentUser().getUserName()
						+ "' order by b.ADD_DATE desc";
				shopingCartList2 = documentService.getMapList(getToken(), sql);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mp.put("data", shopingCartList2);
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	private String getGridColumn(EcmGridView gv, String gridName) {
		String col = "";
		String cols = ",";
		if (gv != null) {
			for (EcmGridViewItem item : gv.getGridViewItems()) {
				if (cols.indexOf("," + item.getAttrName() + ",") > -1) {
					continue;
				}
				col += "," + item.getAttrName();
			}
		}
		return col;
	}

	// 添加到购物车
	@RequestMapping(value = "/dc/SaveShowDrawing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> SaveShowDrawing(@RequestBody String argStr) {
		List<String> argMap = JSONUtils.stringToArray(argStr);
		String argName = null;
		Map<String, Object> formDataMap = null;
		List<String> documentIdArray = null;
		Map<String, Object> mp = new HashMap<String, Object>();
		String formId = "";
		for (int i = 0; i < argMap.size(); i++) {
			List<String> argMap2 = JSONUtils.stringToArray(argMap.get(i).toString());
			for (int j = 0; j < argMap2.size(); j++) {
				argName = argMap2.get(0);
				if ("formData".equals(argName) && argMap2.get(1) != null) {
					formDataMap = JSONUtils.stringToMap(argMap2.get(1));
				} else if ("documentIds".equals(argName) && argMap2.get(1) != null) {
					documentIdArray = JSONUtils.stringToArray(argMap2.get(1));
				} else if ("formId".equals(argName) && argMap2.get(1) != null) {
					formId = argMap2.get(1);
				}
			}
		}

		IEcmSession ecmSession = null;
		String specialUserName = env.getProperty("ecm.username");
		try {
			if ("".equals(formId)) {
				formDataMap.put("TYPE_NAME", "晒图单");
				formDataMap.put("FOLDER_ID", folderService.getObjectByPath(getToken(), "/表单/晒图单").getId());
				formDataMap.put("CODING", numberService.getNumber(getToken(), formDataMap));
				formId = documentService.newObject(getToken(), formDataMap);
				EcmRelation en = new EcmRelation();
				en.setParentId(formId);
				en.setName("irel_showdrawing");
				en.setCreationDate(new Date());
				en.setCreator(this.getSession().getCurrentUser().getUserName());
				ecmSession = authService.login("workflow", specialUserName, env.getProperty("ecm.password"));
				for (int i = 0; i < documentIdArray.size(); i++) {
					en.setChildId(documentIdArray.get(i));
					en.createId();
					en.setOrderIndex(i);
					ecmRelationMapper.insert(en);
					EcmDocument docObjSub = documentService.getObjectById(getToken(), documentIdArray.get(i));
					documentService.grantGroup(ecmSession.getToken(), docObjSub, "ST_文印中心",
							PermissionContext.ObjectPermission.DOWNLOAD, null, true);

				}
			} else {
				Map<String, Object> goodFormDataMap = new HashMap<String, Object>();
				if (formDataMap.containsKey("ID"))
					goodFormDataMap.put("ID", formDataMap.get("ID"));
				if (formDataMap.containsKey("CODING"))
					goodFormDataMap.put("CODING", formDataMap.get("CODING"));
				if (formDataMap.containsKey("C_DRAFTER"))
					goodFormDataMap.put("C_DRAFTER", formDataMap.get("C_DRAFTER"));
//						if(formDataMap.containsKey("C_DESC1"))					
//							goodFormDataMap.put("C_DESC1",formDataMap.get("C_DESC1"));
//						if(formDataMap.containsKey("TITLE"))					
//							goodFormDataMap.put("TITLE",formDataMap.get("TITLE"));
//						if(formDataMap.containsKey("C_CREATION_UNIT"))					
//							goodFormDataMap.put("C_CREATION_UNIT",formDataMap.get("C_CREATION_UNIT"));
//						if(formDataMap.containsKey("SUB_TYPE"))					
//							goodFormDataMap.put("SUB_TYPE",formDataMap.get("SUB_TYPE"));
//						if(formDataMap.containsKey("C_START_DATE"))					
//							goodFormDataMap.put("C_START_DATE",formDataMap.get("C_START_DATE"));
//						if(formDataMap.containsKey("C_END_DATE"))					
//							goodFormDataMap.put("C_END_DATE",formDataMap.get("C_END_DATE"));
//						if(formDataMap.containsKey("C_COMMENT"))					
//							goodFormDataMap.put("C_COMMENT",formDataMap.get("C_COMMENT"));
//						if(formDataMap.containsKey("C_REVIEWER1"))					
//							goodFormDataMap.put("C_REVIEWER1",formDataMap.get("C_REVIEWER1"));
//						if(formDataMap.containsKey("C_REVIEWER2"))					
//							goodFormDataMap.put("C_REVIEWER2",formDataMap.get("C_REVIEWER2"));
//						if(formDataMap.containsKey("C_REVIEWER3"))					
//							goodFormDataMap.put("C_REVIEWER3",formDataMap.get("C_REVIEWER3"));
//						if(formDataMap.containsKey("C_SECURITY_LEVEL"))					
//							goodFormDataMap.put("C_SECURITY_LEVEL",formDataMap.get("C_SECURITY_LEVEL"));
				if (formDataMap.containsKey("STATUS"))
					goodFormDataMap.put("STATUS", formDataMap.get("STATUS"));

				EcmDocument doc = new EcmDocument();
				doc.setAttributes(goodFormDataMap);
				documentService.updateObject(getToken(), doc, null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ecmSession != null) {
				authService.logout(specialUserName);
			}
		}

		mp.put("data", formId);
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	// 添加到购物车
	@RequestMapping(value = "/dc/saveBorrowForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBorrowForm(@RequestBody String argStr) {
		List<String> argMap = JSONUtils.stringToArray(argStr);
		String argName = null;
		Map<String, Object> formDataMap = null;
		String[] documentIdArray = null;
		Map<String, Object> mp = new HashMap<String, Object>();
		String formId = "";
		for (int i = 0; i < argMap.size(); i++) {
			List<String> argMap2 = JSONUtils.stringToArray(argMap.get(i).toString());
			for (int j = 0; j < argMap2.size(); j++) {
				argName = argMap2.get(0);
				if ("formData".equals(argName) && argMap2.get(1) != null) {
					formDataMap = JSONUtils.stringToMap(argMap2.get(1));
				} else if ("documentIds".equals(argName) && argMap2.get(1) != null) {
					documentIdArray = argMap2.get(1).split(",");
				} else if ("formId".equals(argName) && argMap2.get(1) != null) {
					formId = argMap2.get(1);
				}
			}
		}

		try {
			if ("".equals(formId)) {
				formDataMap.put("TYPE_NAME", "借阅单");
				formDataMap.put("FOLDER_ID", folderService.getObjectByPath(getToken(), "/表单/借阅单").getId());
				formDataMap.put("CODING", numberService.getNumber(getToken(), formDataMap));
				formId = documentService.newObject(getToken(), formDataMap);
				EcmRelation en = new EcmRelation();
				en.setParentId(formId);
				en.setName("irel_borrow");
				en.setCreationDate(new Date());
				en.setCreator(this.getSession().getCurrentUser().getUserName());
				for (int i = 0; i < documentIdArray.length; i++) {
					en.setChildId(documentIdArray[i]);
					en.createId();
					en.setOrderIndex(i);
					ecmRelationMapper.insert(en);
				}

			} else {
				Map<String, Object> goodFormDataMap = new HashMap<String, Object>();
				if (formDataMap.containsKey("ID"))
					goodFormDataMap.put("ID", formDataMap.get("ID"));
				if (formDataMap.containsKey("CODING"))
					goodFormDataMap.put("CODING", formDataMap.get("CODING"));
				if (formDataMap.containsKey("C_DRAFTER"))
					goodFormDataMap.put("C_DRAFTER", formDataMap.get("C_DRAFTER"));
				if (formDataMap.containsKey("C_DESC1"))
					goodFormDataMap.put("C_DESC1", formDataMap.get("C_DESC1"));
				if (formDataMap.containsKey("TITLE"))
					goodFormDataMap.put("TITLE", formDataMap.get("TITLE"));
				if (formDataMap.containsKey("C_CREATION_UNIT"))
					goodFormDataMap.put("C_CREATION_UNIT", formDataMap.get("C_CREATION_UNIT"));
				if (formDataMap.containsKey("SUB_TYPE"))
					goodFormDataMap.put("SUB_TYPE", formDataMap.get("SUB_TYPE"));
				if (formDataMap.containsKey("C_START_DATE"))
					goodFormDataMap.put("C_START_DATE", formDataMap.get("C_START_DATE"));
				if (formDataMap.containsKey("C_END_DATE"))
					goodFormDataMap.put("C_END_DATE", formDataMap.get("C_END_DATE"));
				if (formDataMap.containsKey("C_COMMENT"))
					goodFormDataMap.put("C_COMMENT", formDataMap.get("C_COMMENT"));
				if (formDataMap.containsKey("C_REVIEWER1"))
					goodFormDataMap.put("C_REVIEWER1", formDataMap.get("C_REVIEWER1"));
				if (formDataMap.containsKey("C_REVIEWER2"))
					goodFormDataMap.put("C_REVIEWER2", formDataMap.get("C_REVIEWER2"));
				if (formDataMap.containsKey("C_REVIEWER3"))
					goodFormDataMap.put("C_REVIEWER3", formDataMap.get("C_REVIEWER3"));
				if (formDataMap.containsKey("C_SECURITY_LEVEL"))
					goodFormDataMap.put("C_SECURITY_LEVEL", formDataMap.get("C_SECURITY_LEVEL"));
				if (formDataMap.containsKey("STATUS"))
					goodFormDataMap.put("STATUS", formDataMap.get("STATUS"));

				EcmDocument doc = new EcmDocument();
				doc.setAttributes(goodFormDataMap);
				documentService.updateObject(getToken(), doc, null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//				Map<String,String> relIDMap=new HashMap<String,String>();
//				try {
//					List<Map<String, Object>> existRelList= relationService.getMapList(getToken(), "select ID,PARENT_ID,CHILD_ID from ecm_relation where NAME='irel_borrow' and PARENT_ID=''");
//					for (int i = 0; i < existRelList.size(); i++) {
//						String relParentChildID=""+existRelList.get(i).get("PARENT_ID")+existRelList.get(i).get("CHILD_ID");
//						relIDMap.put(relParentChildID,existRelList.get(i).get("ID").toString());
//					}
//				   for(int i=0;i<documentIdArray.length;i++) {
//					   String formId_documentId=formId+documentIdArray[i];
//					 if(relIDMap.containsKey(formId_documentId)) {
//						 relIDMap.remove(formId_documentId);
//					 }else {
//							EcmRelation en=new EcmRelation();
//							en.setParentId(formId);
//							en.setChildId(documentIdArray[i]);
//							en.setName("irel_borrow");
//							en.setCreationDate(new Date());
//							en.setCreator(this.getSession().getCurrentUser().getUserName());
//							relationService.newObject(getToken(), en);						 				 
//					 }
//				   }

//				   Object[] needToDeleteRelObjects=relIDMap.values().toArray();
//				   for (int i = 0; i < needToDeleteRelObjects.length; i++) {
//					   relationService.deleteObject(needToDeleteRelObjects[i].toString());
//				   }

//	 			} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//					  mp.put("code", ActionContext.SUCESS);
//			} 
		mp.put("data", formId);
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	// 添加文档到已存在表单
	@RequestMapping(value = "/dc/addItemToForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addItemToForm(@RequestBody String argStr) {
		List<String> argMap = JSONUtils.stringToArray(argStr);
		String argName = null;
		String formId = "";
		List<String> documentIdArray = null;
		Map<String, Object> mp = new HashMap<String, Object>();
		for (int i = 0; i < argMap.size(); i++) {
			List<String> argMap2 = JSONUtils.stringToArray(argMap.get(i).toString());
			for (int j = 0; j < argMap2.size(); j++) {
				argName = argMap2.get(0);
				if ("formId".equals(argName) && argMap2.get(1) != null) {
					formId = argMap2.get(1);
				} else if ("documentIds".equals(argName) && argMap2.get(1) != null) {
					documentIdArray = JSONUtils.stringToArray(argMap2.get(1));
				}
			}
		}

		for (int i = 0; i < documentIdArray.size(); i++) {
			try {
				EcmRelation en = new EcmRelation();
				en.setParentId(formId);
				en.setChildId(documentIdArray.get(i));
				en.setName("irel_borrow");
				en.setCreationDate(new Date());
				en.setCreator(this.getSession().getCurrentUser().getUserName());
				relationService.newObject(getToken(), en);
			} catch (Exception e1) {
				e1.printStackTrace();
				mp.put("code", ActionContext.SUCESS);
			}
		}

		mp.put("data", formId);
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	// 表单中移除文档
	@RequestMapping(value = "/dc/removeItemFromForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeItemFromForm(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = JSONUtils.stringToMap(argStr);
		String relateId = args.get("relateId").toString();
		try {
			relationService.deleteObjectById(getToken(),relateId);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			mp.put("code", ActionContext.SUCESS);
		}

		mp.put("code", ActionContext.SUCESS);
		return mp;
	}

	@RequestMapping(value = "/dc/getFormRelateDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFormRelateDocument(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object>> childList = null;
		try {
			String sql = "select a.ID as RELATE_ID ,b.ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX,b.NAME,b.CODING,b.C_SECURITY_LEVEL,b.REVISION,b.TITLE,b.CREATOR,b.TYPE_NAME,b.SUB_TYPE,b.CREATION_DATE,b.C_ARCHIVE_DATE,b.C_ARCHIVE_UNIT,b.C_STORE_STATUS ,b.FORMAT_NAME as FORMAT_NAME "
					+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID " + " and a.PARENT_ID='" + id
					+ "' order by a.ORDER_INDEX,b.CREATION_DATE";
			childList = documentService.getMapList(getToken(), sql);
			mp.put("data", childList);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	@RequestMapping(value = "/dc/getDocumentById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDocumentById(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> objectMap = null;
		try {
			objectMap = documentService.getObjectMapById(getToken(), id);
			mp.put("data", objectMap);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	@RequestMapping(value = "/dc/getBorrowHelpDoc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBorrowHelpDoc(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = documentService.getObjectMap(getToken(), " type_name='帮助'  and name='借阅流程图'");
			mp.put("data", mapList.get(0).get("ID"));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 下架文件
	 * 
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/obtainDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> obtainDocuments(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		List<String> list = JSONUtils.stringToArray(argStr);
		StringBuffer responseFile = new StringBuffer();
		StringBuffer responseFox = new StringBuffer();
		for (String docId : list) {
			EcmDocument doc = documentService.getObjectById(getToken(), docId);
			if (doc.getTypeName().equals("卷盒")) {
				String sql1 = "select child_id from ecm_relation where parent_id ='" + docId
						+ "' and name='irel_children'";
				List<Map<String, Object>> childrenId = documentService.getMapList(getToken(), sql1);
				int count = 0;
				for (int i = 0; childrenId != null && i < childrenId.size(); i++) {
					Map<String, Object> first = childrenId.get(i);
					EcmDocument childDoc = documentService.getObjectById(getToken(), (String) first.get("child_id"));
					if (childDoc.getStatus().equals("注销")) {
						count = count + 1;
					}
				}
				if (count > 0) {
					responseFox.append(doc.getName() + "; ");
				} else {
					Map<String, Object> docAttr = doc.getAttributes();
					String boxFolderPathId = folderPathService.getArrangeFolderId(getToken(), docAttr);
					doc.setFolderId(boxFolderPathId);
					doc.setStatus("整编");
					documentService.updateObject(getToken(), doc, null);
					for (int i = 0; childrenId != null && i < childrenId.size(); i++) {
						Map<String, Object> first = childrenId.get(i);
						EcmDocument childDoc = documentService.getObjectById(getToken(),
								(String) first.get("child_id"));
						Map<String, Object> childAtt = childDoc.getAttributes();
						String folderPathId = folderPathService.getArrangeFolderId(getToken(), childAtt);
						childDoc.setFolderId(folderPathId);
						childDoc.setStatus("整编");
						documentService.updateObject(getToken(), childDoc, null);
					}
				}

			} else {
				responseFile.append(doc.getName() + ", ");
			}
		}

		String mess = "下架文件:";
		if (responseFile != null && !responseFile.toString().equals("")) {
			mess = mess + " " + responseFile.toString().trim().substring(0, responseFile.toString().length() - 2)
					+ "为非卷盒文件; ";
		}
		if (responseFox != null && !responseFox.toString().equals("")) {
			mess = mess + "" + responseFox.toString().trim().substring(0, responseFox.toString().length() - 2)
					+ "的子文件存在销毁项，请恢复后重试;";
		}
		if (mess.equals("下架文件:")) {
			mp.put("code", ActionContext.SUCESS);
			mp.put("msg", "下架成功");
		} else {
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", mess);
		}
		return mp;
	}

	// 在回收站恢复文档
	@RequestMapping(value = "/dc/restoreDocuments", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> restoreDocument(@RequestBody String argStr) {
		List<String> list = JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			for (String id : list) {
				EcmDocument ecmDocument = documentService.getObjectById(getToken(), id);
				if (ecmDocument.getTypeName().equals("卷盒") || ecmDocument.getTypeName().equals("图册")) {
					documentService.updateStatus(getToken(), id, "利用");
					String sql = "select b.ID,a.NAME as RELATION_NAME,a.PARENT_ID,a.CHILD_ID,a.ORDER_INDEX,b.NAME,b.CODING,b.C_SECURITY_LEVEL,b.REVISION,b.TITLE,b.CREATOR,b.TYPE_NAME,b.SUB_TYPE,b.CREATION_DATE"
							+ " from ecm_relation a, ecm_document b where  a.CHILD_ID=b.ID " + " and a.PARENT_ID='" + id
							+ "' order by a.ORDER_INDEX,b.CREATION_DATE";
					List<Map<String, Object>> childList = documentService.getMapList(getToken(), sql);
					for (Map<String, Object> child : childList) {
						String childId = (String) child.get("CHILD_ID");
						documentService.updateStatus(getToken(), childId, "利用");
					}
				} else {
					documentService.updateStatus(getToken(), id, "利用");
				}
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	// 创建接口返回馆藏状态信息，数据用于echarts显示
	@RequestMapping("/dc/getCollectionData")
	@ResponseBody
	public Map<String, Object> collectionData() {
		Map<String, Object> mp = new HashMap<String, Object>();
		ChartBean bean = new ChartBean();
		Pager pager = new Pager();
		pager.setPageIndex(0);
		pager.setPageSize(10000);
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		List<Map<String, Object>> arcCountList = new ArrayList<Map<String, Object>>();
		try {
			list = folderService.getFoldersByParentPath(getToken(), "/档案库");
			arcCountList = documentService.getMapList(getToken(),
					"select C_ARC_CLASSIC, count(*) C_COUNT from ecm_document where STATUS='利用' and type_name<>'卷盒' and C_ARC_CLASSIC<>'' and C_ARC_CLASSIC is not null group by C_ARC_CLASSIC");
			for (EcmFolder ecmFolder : list) {
				String ecmName = ecmFolder.getName();
				boolean flag = false;
				long count = 0;
				bean.getxAxisData().add(ecmFolder.getName());
				for (Map<String, Object> map : arcCountList) {
					if (ecmName.equals(map.get("C_ARC_CLASSIC"))) {
						flag = true;
						count = (long) map.get("C_COUNT");
					}
				}
				if (flag) {
					bean.getyAxisData().add(count);
					flag = false;
					count = 0;
				} else {
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
	public Map<String, Object> getSelectList(@RequestBody String argStr) {
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
			for (Map<String, Object> obj : list) {
				listName.add(obj.get("NAME").toString());
			}
			mp.put("data", listName);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}

	/**
	 * 不带分页查询
	 * 
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getObjectsByConfigClauseNoPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getObjectsByConfigClauseNoPage(@RequestBody String argStr) {

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);

			if (args.get("configName") == null) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "configName没有传递");
				return mp;
			}
			String configName = args.get("configName").toString();

			List<Map<String, Object>> list;
			try {
				list = documentService.getObjectsConfigclause(getToken(), null, configName, args);
			} catch (MessageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
				return mp;
			}
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;

	}

	/**
	 * 公共文档查询方法，在缓存中配置查询语句 将查询语句的name传递进此方法 条件使用‘@’开头例如 select * from ecm_document
	 * where id='@id' 在页面中需要传递一个名为‘id’的参数传递给controller
	 * 
	 * 使用configName名称来传递缓存中配置的语句名称
	 * 
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/getObjectsByConfigclause", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getObjectsByConfigclause(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			if (args.get("configName") == null) {
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "configName没有传递");
				return mp;
			}
			String configName = args.get("configName").toString();

			List<Map<String, Object>> list;
			try {
				list = documentService.getObjectsConfigclause(getToken(), pager, configName, args);
			} catch (MessageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
				return mp;
			}
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			e.printStackTrace();
		}
		return mp;
	}

	@RequestMapping(value = "/dc/updateContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConent(String id, @RequestParam("uploadFile") MultipartFile uploadFile) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if (uploadFile != null) {
				String ext = FileUtils.getExtention(uploadFile.getOriginalFilename());
				EcmContent en = contentService.getObject(getToken(), id, 0, ext);
				if (en != null) {
					en.setContentSize(uploadFile.getSize());
					en.setInputStream(uploadFile.getInputStream());
					en.setDescription("OCR");
					contentService.updateObject(getToken(), en);
					// 主格式更新内容大小
					if (en.getContentType() == 1) {
						contentService.updatePrimaryContentSize(getToken(), id, en.getContentSize());
					}
					documentService.queue(getToken(), id, "ecm_full_index", "ecm_full_index", null);
					mp.put("code", ActionContext.SUCESS);
				} else {

					System.out.println("Content not exists, id:" + id + ",format:" + ext);
					mp.put("code", ActionContext.FAILURE);
					mp.put("message", "内容对象为空");
				}
			} else {
				System.out.println("Upload file is null.");
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "电子文件为空");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	@RequestMapping(value = "/dc/moveDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> moveDocument(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String[] ids = args.get("ids").toString().split(";");
			String folderId = args.get("folderId").toString();
			if (!StringUtils.isEmpty(folderId)) {
				for (String id : ids) {
					if (!StringUtils.isEmpty(id)) {
						EcmDocument doc = documentService.getObjectById(getToken(), id);
						if (doc != null) {
							doc.setFolderId(folderId);
							documentService.updateObject(getToken(), doc, null);
						}
					}
				}
				mp.put("code", ActionContext.SUCESS);
			} else {
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", "Folder id is null.");
			}
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 复制，不复制内容
	 * 
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/copyDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyDocument(@RequestBody String argStr) {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String[] ids = args.get("ids").toString().split(";");
			String folderId = args.get("folderId").toString();

			for (String id : ids) {
				if (!StringUtils.isEmpty(id)) {
					EcmDocument doc = documentService.getObjectById(getToken(), id);
					if (doc != null) {
						doc.createId();
						doc.setName(doc.getName() + " Copy");
						if (!StringUtils.isEmpty(folderId)) {
							doc.setFolderId(folderId);
						}

						doc.setContentSize((long) 0);
						doc.setFormatName("");
						doc.setModifiedDate(new Date());
						doc.setModifier(documentService.getCurrentUser(getToken()).getUserName());
						documentService.newObject(getToken(), doc, null);
					}
				}
			}
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}

	/**
	 * 下载全部附件
	 */
	@RequestMapping(value = "/workflow/downloadAllFile")
	public void downloadAllFile(HttpServletResponse response, String objectIds) {
		String[] objectIdsList = objectIds.split(",");
		List<File> files = new ArrayList<File>();
		List<String> fileNames = new ArrayList<String>();
		for (int i = 0; i < objectIdsList.length; i++) {
			try {
				int permit = documentService.getPermit(getToken(), objectIdsList[i]);
				EcmDocument docObj = documentService.getObjectById(getToken(), objectIdsList[i]);
				String coding = docObj.getCoding() == null ? docObj.getId() : docObj.getCoding();
				if (permit >= PermissionContext.ObjectPermission.DOWNLOAD) {
					List<EcmContent> contentList = contentMapper.getAllContents(objectIdsList[i]);
					for (int j = 0; j < contentList.size(); j++) {
						EcmContent en = contentList.get(j);
						String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
						files.add(new File(storePath + en.getFilePath()));
						fileNames.add(coding + "." + en.getFormatName());
					}
				}
			} catch (AccessDeniedException e) {
				e.printStackTrace();
			}
		}
		zipDownloadService.createZipFiles(files, fileNames, response);
	}

	@ResponseBody
	@RequestMapping(value = "/dc/grantPermit", method = RequestMethod.POST)
	public Map<String, Object> grantPermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(permit.getTargetName())) {
				mp.put("code", ActionContext.FAILURE);
			} else {
				String aclName = "";
				String[] targetNames = permit.getTargetName().split(";");
				for (String targetName : targetNames) {
					if (permit.getTargetType() == 1) {
						aclName = documentService.grantUser(getToken(), permit.getParentId(), targetName,
								permit.getPermission(), permit.getExpireDate(), needNewAcl(permit.getParentId()));
					} else {
						aclName = documentService.grantGroup(getToken(), permit.getParentId(), targetName,
								permit.getPermission(), permit.getExpireDate(), needNewAcl(permit.getParentId()));
					}
				}
				mp.put("data", aclName);
				mp.put("code", ActionContext.SUCESS);
			}
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		return mp;
	}

	@ResponseBody
	@RequestMapping(value = "/dc/revokePermit", method = RequestMethod.POST)
	public Map<String, Object> revokePermit(@RequestBody EcmPermit permit) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			String aclName = "";
			if (permit.getTargetType() == 1) {
				aclName = documentService.revokeUser(getToken(), permit.getParentId(), permit.getTargetName(),
						needNewAcl(permit.getParentId()));
			} else {
				aclName = documentService.revokeGroup(getToken(), permit.getParentId(), permit.getTargetName(),
						needNewAcl(permit.getParentId()));
			}
			mp.put("data", aclName);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("data", e.getMessage());
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			mp.put("data", e.getMessage());
		}
		return mp;
	}

	/**
	 * 升版
	 * 
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/upgradeDocument", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upgradeDocument(@RequestBody String argStr) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		String newDocId="";
		try {
			if (!StringUtils.isEmpty(argStr)) {
				EcmDocument doc = documentService.getObjectById(getToken(), argStr);
				
				if (doc != null) {
					
					newDocId= documentService.newObject(getToken(), doc, null);
					contentService.copyContent(getToken(), argStr, newDocId, 1);
					doc.setModifiedDate(new Date());
					doc.setModifier(documentService.getCurrentUser(getToken()).getUserName());
					
					mp.put("id", newDocId);
				}
			}
			
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception ex) {
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", ex.getMessage());
		}
		return mp;
	}
	
	
	private boolean needNewAcl(String docId) {
		try {
			EcmDocument doc = documentService.getObjectById(getToken(), docId);
			String aclName = doc.getAclName();
			if (StringUtils.isEmpty(aclName) || !aclName.startsWith("ecm_")) {
				return true;
			}
			String sql = "select count(*) as aclCount from ecm_document where ACL_NAME='" + aclName + "'";
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			if (list != null && list.size() > 0) {
				return Integer.parseInt(list.get(0).get("aclCount").toString()) > 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private boolean needNewAclByAclName(String aclName) {
		try {
			if (StringUtils.isEmpty(aclName) || !aclName.startsWith("ecm_")) {
				return true;
			}
			String sql = "select count(*) as aclCount from ecm_document where ACL_NAME='" + aclName + "'";
			List<Map<String, Object>> list = documentService.getMapList(getToken(), sql);
			if (list != null && list.size() > 0) {
				return Integer.parseInt(list.get(0).get("aclCount").toString()) > 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
