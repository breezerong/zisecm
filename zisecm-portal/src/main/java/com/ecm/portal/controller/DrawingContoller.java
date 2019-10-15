package com.ecm.portal.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
/**
 * 图纸操作
 * @author Haihong Rong
 *
 */
@Controller
public class DrawingContoller extends ControllerAbstract{

	private String gridviewName="DrawingGrid";
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ContentService contentService;

	
	/**
	 * 获取新建图纸列表
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/drawing/getDrawing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDrawing(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args =null;
			if(argStr==null) {
				args =new HashMap<String,Object>();
			}else {
				args = JSONUtils.stringToMap(argStr);
			}
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			mp.put("data", getDocList(argStr, pager));
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	
	/**
	 * 获取新建图纸数目
	 * @param argStr map字符串
	 * @return
	 */
	@RequestMapping(value = "/drawing/getDrawingCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDrawingCount(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();

			try {
				mp.put("data", getDocCount(argStr));
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			}

		return mp;
	}
	
	@RequestMapping(value="/drawing/uploadDrawing",method=RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> newDocument(String metaData, @RequestParam("files") MultipartFile[] files) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args =null;
		String id = null;
		
		if(files!=null&&files.length>0) {
			int i=0;
			for (MultipartFile uploadFile : files) {
				if(metaData==null) {
					args =new HashMap<String,Object>();
				}else {
					args = JSONUtils.stringObjectToMap(metaData);
				}
				i++;
				EcmContent en = null; 
				if (uploadFile != null) {
					en = new EcmContent();
					en.createId();
					en.setName(uploadFile.getOriginalFilename());
					en.setContentSize(uploadFile.getSize());
					en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()).toLowerCase());
					en.setInputStream(uploadFile.getInputStream());
//					Date date = new Date(); //获取当前的系统时间。
//					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ; //使用了默认的格式创建了一个日期格式化对象。
//					String time = dateFormat.format(date); //可以把日期转换转指定格式的字符串
//					args.put("NAME", time+i);
					// name : 01001zyz12-001@项目设计图纸1
					String name="";
					if(args != null && args.get("NAME")!=null) {
						name = args.get("NAME").toString();
					}
					if(name.trim().length()==0) {
						String oldName=uploadFile.getOriginalFilename();
						if(oldName.lastIndexOf(".")>-1) {
							name=oldName.substring(0,oldName.lastIndexOf("."));
						}
						String[] strs = name.split("@");
						if(strs.length>1) {
							args.put("CODING", strs[0]);
							if(strs[0].indexOf("-")>0) {
								String[] temps = strs[0].split("-");
								args.put("OBJECT_TYPE", temps[0]);
								args.put("SUB_TYPE", temps[1]);
							}
							args.put("NAME", strs[1]);
						}
						else {
							args.put("NAME", strs[0]);
						}
					}
					
					args.put("FORMAT_NAME", en.getFormatName());
					args.put("CONTENT_SIZE", en.getContentSize());
					if(CacheManagerOper.getEcmParameters().get("DrawingFolder") != null) {
						args.put("FOLDER_ID", CacheManagerOper.getEcmParameters().get("DrawingFolder").getValue());
					}
					args.put("STATUS", "新建");
				}
				EcmDocument doc = new EcmDocument();
				doc.setAttributes(args);
				id = documentService.newObject(getToken(),doc,en);
			}
		}
		mp.put("data", id);
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
		
	}
	/**
	 * 删除图纸文件
	 * @param metaData
	 * @return
	 */
	@RequestMapping(value="/drawing/delDrawing",method=RequestMethod.POST)
	@ResponseBody	
	public Map<String,Object> delDrawing(@RequestBody String metaData){
		Map<String, Object> mp = new HashMap<String, Object>();
		List<String> ids= JSONObject.parseArray(metaData, String.class);
		for(int i=0;i<ids.size();i++) {
//			Map<String,Object> id=JSON.parseObject(ids.get(i));
			String id=ids.get(i);
			try {
				documentService.deleteObject(getToken(),id);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			}
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	/**
	 * 更改为状态为已审批
	 * @param metaData
	 * @return
	 */
	@RequestMapping(value="/drawing/updateStatus",method=RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> updateStatus(@RequestBody String metaData){
		
		//已审批
		Map<String, Object> mp = new HashMap<String, Object>();
//		List<String> ids= JSONObject.parseArray(metaData, String.class);
		 Map<String,Object> obj =  JSONObject.parseObject(metaData); 
		 String status=(String)obj.get("STATUS");
		 String strList=(String) obj.get("list");
		 List<String> ids= JSONObject.parseArray(strList, String.class);
		 try {
			for(int i=0;i<ids.size();i++) {
	//			Map<String,Object> id=JSON.parseObject(ids.get(i));
				String id=ids.get(i);
				
					documentService.updateStatus(getToken(),id, status);
				
			}
			mp.put("code", ActionContext.SUCESS);
		 } catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
		 }
		
		return mp;
	
	}
	
	/**
	 * 根据状态获取文档数目
	 * @param argStr
	 * @return
	 * @throws AccessDeniedException 
	 */
	private long getDocCount(String argStr) throws AccessDeniedException  {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String condition = args.get("condition")==null?"":args.get("condition").toString();
		String status = args.get("status").toString();
		if(condition!=null&&condition.trim().length()>0) {
			condition += " and ";
		}
		condition += " CREATOR='"+getSession().getCurrentUser().getUserName()+"' and STATUS='"+status+"'";
		return documentService.getObjectCount(getToken(),gridviewName, "", condition);
	}
	/**
	 * 根据状态获取图纸文档清单
	 * @param argStr 请求Map
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String, Object>> getDocList(String argStr,Pager pager) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		
		String condition = args.get("condition").toString();
		String status = args.get("status").toString();
		if(condition!=null&&condition.trim().length()>0) {
			condition += " and ";
		}
		condition += " CREATOR='"+getSession().getCurrentUser().getUserName()+"' and STATUS='"+status+"'";
		String orderBy = " CODING ASC";
		return  documentService.getObjects(getToken(),gridviewName,null,pager,condition,orderBy);
	}
}
