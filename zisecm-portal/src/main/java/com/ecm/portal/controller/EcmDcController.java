package com.ecm.portal.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.RelationService;

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

	@RequestMapping(value = "/dc/getDocument", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocument(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> data = documentService.getObjectMapById(getToken(),id);
			mp.put("data", data);
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
			EcmContent en = contentService.getPrimaryContent(getToken(),id);
			InputStream iStream = contentService.getContentStream(getToken(),en);
			// 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(en.getName(), "UTF-8"));
            response.addHeader("Content-Length", "" + en.getContentSize());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            if(en.getFormatName().equalsIgnoreCase("pdf")){
//            	response.setContentType("application/form-data");
//            }else {
            	response.setContentType("application/octet-stream");
//            }
            byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = iStream.read(buffer)) != -1) {
				toClient.write(buffer, 0, bytesRead);
			}
			iStream.close();
            toClient.flush();
            toClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			List<EcmContent>  list = documentService.getAllRendition(getToken(), id);
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
			String sql = "select b.ID,a.RELATION_NAME,a.PARENT_ID,a.CHILD_ID,b.NAME,b.CODING,b.REVISION,b.TITLE,b.CREATOR,b.CREATION_DATE"
					+ "from ecm_relation a, ecm_document b where a.RELATION_NAME not like 'irel%' and (a.PARENT_ID=b.ID or a.CHILD_ID=b.ID)"
					+ " and b.ID='"+id+"' order by b.CREATION_DATE";
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
}
