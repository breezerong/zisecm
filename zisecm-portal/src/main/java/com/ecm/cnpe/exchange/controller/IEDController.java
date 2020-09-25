package com.ecm.cnpe.exchange.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormClassification;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.FolderPathService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.core.service.FolderService;

@RestController

public class IEDController  extends ControllerAbstract  {
	
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;
	
	@Autowired
	private ExcSynDetailService detailService;
	
	
	
	@Autowired
	private DocumentService documentService;
	public class column{									//类内实体，用数组存放列数据
		private String label;
		private String attrname;
		public void setLabel(String label) {
			this.label=label;
		}
		public String getLabel() {
			return label;
		}
		public void setAttrName(String attr) {
			this.attrname=attr;
		}
		public String getAttrName() {
			return attrname;
		}
	}
	public String lang;										//语言，找列的时候要用
	public String IDS;										//公用ID，找列的时候要用
	@RequestMapping(value = "/exchange/ied/newIED", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newIED(String metaData, MultipartFile uploadFile) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();	
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
		String coding=doc.getCoding();
		String cond = "TYPE_NAME='IED' AND CODING = '"+coding+"'";
		List<Map<String,Object>> result = documentService.getObjectMap(getToken(), cond);
		if(result.size()!=0) {
			mp.put("code",ActionContext.FAILURE);
			mp.put("mess","IED已存在，不可重复创建,外部编号为 "+coding);
			return mp;
		}
		//doc.addAttribute("C_WBS_CODING","1516-E-L3-N1-A-FU.E1.E314C.ED.11.1");//测试用数据，使用时删除
		//Object temp1=doc.getAttributeValue("C_WBS_CODING");
		/*if(temp1 != null) {
			String WBS_CODING=temp1.toString();						//获取WBScoding，转换成String
			String wbscond="TYPE_NAME='计划任务' AND C_WBS_CODING = '"+WBS_CODING+"'";
			List<Map<String,Object>> result = documentService.getObjectMap(getToken(), wbscond);
			for(int i=0;i<result.size();i++) {
			EcmDocument e1 = new EcmDocument();
			e1.setAttributes(result.get(i));
			Object o1 = e1.getAttributeValue("C_ITEM2_DATE");	//取WBS计划完成时间
			Object o2 = doc.getAttributeValue("C_ITEM5_DATE");	//取IED的计划完成时间,目前ITEM5
			String WBSitem2date = o1.toString();					//WBS转换转换
			String IEDitem2date = o2.toString();					//IED的转换
			Date WBS =df.parse(WBSitem2date);
			//System.out.println("WBS:"+WBSitem2date);
			Date IED =df.parse(IEDitem2date);
			//System.out.println("IED:"+IEDitem2date);
			boolean before = WBS.before(IED);
			if(before == true) {
				Map<String, Object> mp3 = new HashMap<String, Object>();		
				mp3.put("code",3);
				mp3.put("mess", "IED的外部计划时间不可晚于WBS的计划完成时间");
				return mp3;
			}
		}
		}/*/
		/*List<Map<String,Object>> result =documentService.getObjectMap(getToken(), cond);//查询IED用MAP
		Map<String, Object> mp = new HashMap<String, Object>();					//结果map
		if(result!=null&&result.size()>0) {
			mp.put("code",ActionContext.FAILURE);
			mp.put("mess","IED已存在，不可重复创建,外部编号为 "+coding);
			return mp;	
		}/*/
		Object fid= args.get("folderId");
		String folderId="";
		if(fid==null) {
			folderId = folderPathService.getFolderId(getToken(), doc.getAttributes(), "3");
		}else {
			folderId=fid.toString();
		}
		EcmFolder folder = folderService.getObjectById(getToken(), folderId);
		doc.setFolderId(folderId);
		doc.setAclName(folder.getAclName());
		
		String id = documentService.newObject(getToken(), doc, en);
		
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
		
	}
	
	/**
	 * 
	 * @Title: changeIED
	 * @author Chen Shuo
	 * @date:  2020年7月17日 上午10:39:19
	 * @Description IED变更
	 * @param ids
	 * @return
	 */
	@PostMapping("/exchange/ied/changeIED")
	@ResponseBody
	public Map<String, Object> changeIED(@RequestBody List<String> ids){
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : ids) {
			try {
				EcmDocument docObj = documentService.getObjectById(getToken(), id);
				EcmDocument newDoc = documentService.getObjectById(getToken(), id);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("C_ITEM_STATUS1", "修订");
				map.put("C_IS_RELEASED", 0);
				newDoc=documentService.checkIn(getToken(), id, map, null, false);
				documentService.updateStatus(getToken(), newDoc.getId(), "新建");
				documentService.updateStatus(getToken(), docObj.getId(), "变更中");
				OptionLogger.logger(getToken(), detailService, newDoc, "变更",
						newDoc.getAttributeValue("C_COMPANY")!=null?newDoc.getAttributeValue("C_COMPANY").toString():"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("code", ActionContext.SUCESS);
		return result;		
	}
	
	@PostMapping("/exchange/ied/changeIEDSingle")
	@ResponseBody
	public Map<String, Object> changeIEDSingle(@RequestBody String  metadata){
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(metadata);
		String id = args.get("ID").toString();
		String coding = args.get("CODING").toString();
			try {
				EcmDocument docObj = documentService.getObjectById(getToken(), id);
				EcmDocument newDoc = documentService.getObjectById(getToken(), id);
				Map<String, Object> map = new HashMap<String, Object>();
				String cond = "TYPE_NAME='IED' AND CODING='"+coding+"' and status='新建'";
				List<Map<String,Object>> res =documentService.getObjectMap(getToken(), cond);	
				if(res.size()>0) {
					result.put("code", "3");
					return result;
				}
				map = args;
				map.put("C_ITEM_STATUS1", "修订");
				map.put("C_IS_RELEASED", 0);
				newDoc=documentService.checkIn(getToken(), id, map, null, false);
				documentService.updateStatus(getToken(), newDoc.getId(), "新建");
				documentService.updateStatus(getToken(), docObj.getId(), "变更中");
			} catch (Exception e) {
				e.printStackTrace();
			}

		result.put("code", ActionContext.SUCESS);
		return result;		
	}
	
	
	
	
	
	
	
	@PostMapping("/exchange/ied/iedFeedback")
	@ResponseBody
	public Map<String, Object> IEDFeedBack(String metaData)  throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		Map<String, Object> result = new HashMap<String, Object>();
		EcmDocument temp = new EcmDocument();
		Date reviewdate = new Date();
		String comment = args.get("comment").toString();//获取进展说明
		String item4date = args.get("date").toString();	//获取预计时间
		String user = args.get("username").toString();	//获取当前用户名
		String id = args.get("id").toString();
		temp.addAttribute("C_ITEM4_DATE",item4date);
		temp.addAttribute("C_COMMENT4",comment);
		temp.addAttribute("C_REVIEWER1", user);
		temp.addAttribute("C_REVIEW1_DATE", reviewdate);
		temp.addAttribute("ID", id);
		documentService.updateObject(getToken(), temp, null);
		result.put("code", ActionContext.SUCESS);
		return result;
	}
	@RequestMapping("/exchange/ied/iedContrast")
	@ResponseBody
	public List<Map<String,Object>> IEDContrast(String metaData) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		Map<String, Object> now = new HashMap<String,Object>();
		Map<String, Object> current = new HashMap<String,Object>();
		List<Map<String,Object>> contrast = new ArrayList<Map<String,Object>>();
		String id = args.get("ID").toString();
		String lan = args.get("lang").toString();
		this.lang = lan;
		String Ver_id = args.get("Version_id").toString();
		this.IDS = args.get("ID").toString();
		EcmDocument temp = new EcmDocument();
		temp = documentService.getObjectById(getToken(), id);
		String Coding = temp.getCoding();
		String cond = "TYPE_NAME = 'IED' AND STATUS='变更中' AND Version_id = '"+Ver_id+"'";
		List<Map<String,Object>> result = documentService.getObjectMap(getToken(), cond);
		now=documentService.getObjectMapById(getToken(), id);		//获取当前IED的map
		for(int i = 0; i < result.size();i++) {
			current = result.get(i);
		}									//获取服务器IED版本的map
		for(int i = 0;i<result.size();i++) {
			temp.setAttributes(result.get(i));
		}
		contrast = check(now,current);
		
		return contrast;
	}
	
	
	
	@RequestMapping(value = "/exchange/ied/getColumn", method = RequestMethod.POST)	//获取列
	@ResponseBody
	public List<column> getColumn() throws Exception {
		EcmDocument temp = new EcmDocument();
		Map<String,Object> map = new HashMap<String,Object>();
		temp = documentService.getObjectById(getToken(),this.IDS);
		Map<String, Object> now = new HashMap<String,Object>();
		Map<String, Object> current = new HashMap<String,Object>();
		String Coding = temp.getCoding();
		String cond = "TYPE_NAME = 'IED' AND STATUS='变更中' AND CODING = '"+Coding+"'";
		now=documentService.getObjectMapById(getToken(),this.IDS);		//获取当前IED的map
		List<Map<String,Object>> result = documentService.getObjectMap(getToken(), cond);
		for(int i = 0; i < result.size();i++) {
			current = result.get(i);
		}		
		List<column> L1= getIEDcolumn(now,current);						//结果LIST
		 for(int j = 0;j < L1.size();j++) {
	    	 	
	    	 	column tempC1 = new column();
	    	 	tempC1 = L1.get(j);
	    	 	//tempC1.setLabel(checkLabel(tempsave[j]));
	    	 	//tempC1.setAttrName(tempsave[j]);
	    	 	//System.out.println(tempsave[j]);
	    	 	System.out.println(tempC1.attrname);
	    	 	System.out.println(tempC1.label);
	    	 	//columnresult.add(tempC1);
	    	 	
         }
		//map.put("data", L1);
		return L1;
	}
	
	
	public List<column> getIEDcolumn(Map<String,Object> now,Map<String,Object> current){			//后台获取列的方法
		int i = 0;
		Iterator<String> iter1 = now.keySet().iterator();
		String[] tempsave = new String[100];
		List<column> columnresult = new ArrayList<column>();
		
		  for (Iterator<String> iter2 = now.keySet().iterator();iter2.hasNext();) {
			  	
			  	String nowKey = (String) iter2.next();
	            if (!now.get(nowKey).equals(current.get(nowKey))&&!nowKey.equals("C_IN_CODING")
	            &&!nowKey.equals("CODING")&&!nowKey.equals("TITLE")&&!nowKey.equals("ID")
	            &&!nowKey.equals("SUB_TYPE")&&!nowKey.equals("REVISION")&&!nowKey.equals("FUStatus")
	            &&!nowKey.equals("CREATOR")&&!nowKey.equals("OWNER_NAME")&&!nowKey.equals("C_IS_RELEASED")
	            &&!nowKey.equals("VERSION_ID")&&!nowKey.equals("IS_CURRENT")&&!nowKey.equals("MODIFIER")
	            &&!nowKey.equals("MODIFIED_DATE")&&!nowKey.equals("ACL_NAME")&&!nowKey.equals("LOCK_OWNER")
	            &&!nowKey.equals("LOCK_CLIENT")&&!nowKey.equals("FOLDER_ID")&&!nowKey.equals("SYSTEM_VERSION")
	            &&!nowKey.equals("C_BATCH_CODE")&&!nowKey.equals("TYPE_NAME"))  {//若两个map中相同key对应的value不相等
	               //String key = (String) iter1.next();			//取key,下面取value,然后把结果不同的key&value put进对应的Map
	              tempsave[i]=nowKey;
	              i++;
	            }
		  }
		     for(int j = 0;j < tempsave.length;j++) {
		    	 	if(tempsave[j]!=null) {
		    	 	column tempC1 = new column();
		    	 	String res = checkLabel(tempsave[j]);
		    	 	if(!res.equals("未识别")) {
		    	 	tempC1.setLabel(checkLabel(tempsave[j]));
		    	 	tempC1.setAttrName(tempsave[j]);
		    	 	}
		    	 	//System.out.println(tempsave[j]);
		    	 	//System.out.println(tempC1.attrname);
		    	 	//System.out.println(tempC1.label);
		    	 	columnresult.add(tempC1);
		    	 	}
	            }
		  return columnresult;
	}
	
	
	
	
	private static String[] getFiledName(Object o) {			//获取对象所有属性
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }
	public List<Map<String,Object>> check(Map<String,Object> now,Map<String,Object> current){			//比对方法
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		//List<column> columnresult = new ArrayList<column>();
		Map<String,Object> nowresult = new HashMap<String,Object>();
		Map<String,Object> currentresult = new HashMap<String,Object>();
		Object nowC_in_coding = now.get("C_IN_CODING");
		Object nowCoding = now.get("CODING");
		Object nowTitle = now.get("TITLE");
		Object CurrentC_in_coding = current.get("C_IN_CODING");
		Object CurrentCoding = current.get("CODING");
		Object CurrentTitle = current.get("TITLE");
		nowresult.put("C_IN_CODING",nowC_in_coding);
		nowresult.put("CODING",nowCoding);
		nowresult.put("TITLE",nowTitle);
		currentresult.put("C_IN_CODING",CurrentC_in_coding);
		currentresult.put("CODING",CurrentCoding);
		currentresult.put("TITLE",CurrentTitle);
		Iterator<String> iter1 = now.keySet().iterator();
		  while (iter1.hasNext()) {
	            String nowKey = (String) iter1.next();
	            if (!now.get(nowKey).equals(current.get(nowKey))&&!nowKey.equals("C_IN_CODING")
	            &&!nowKey.equals("CODING")&&!nowKey.equals("TITLE")&&!nowKey.equals("ID")
	            &&!nowKey.equals("SUB_TYPE")&&!nowKey.equals("REVISION")&&!nowKey.equals("FUStatus")
	            &&!nowKey.equals("CREATOR")&&!nowKey.equals("OWNER_NAME")&&!nowKey.equals("C_IS_RELEASED")
	            &&!nowKey.equals("VERSION_ID")&&!nowKey.equals("IS_CURRENT"))  {//若两个map中相同key对应的value不相等
	               //String key = (String) iter1.next();					   //取key,下面取value,然后把结果不同的key&value put进对应的Map
	               nowresult.put(nowKey, now.get(nowKey));
	               currentresult.put(nowKey, current.get(nowKey));
	            }
	        }
		  result.add(nowresult);						//把结果加进结果map里
		  result.add(currentresult);
		  return result;
	
	}
	

	
	
	public String  checkLabel(String column) {
		//String itemInfo = args.get("itemInfo").toString();
		//String lang = args.get(zh-cn).toString();
		//String formName=args.get("formName").toString();
		String itemInfo = "IED";
		String formName="";
		EcmDocument en = null;
		List<EcmFormClassification> list = null;
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			en = documentService.getObjectById(getToken(), itemInfo);
			if(formName==null||"".equals(formName)) {
				formName=en.getTypeName();
			}
			EcmForm frm = CacheManagerOper.getEcmForms().get(formName + "_EDIT");
			if (frm == null) {
				frm = CacheManagerOper.getEcmForms().get(formName + "_1");
			}
			list = frm.getFormClassifications(documentService.getSession(getToken()), this.lang);
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
		int ItemSize = list.size();
		for(int i=0;i<ItemSize;i++) {
		for(int j=0;j<list.get(i).getEcmFormItems().size();j++) {
			if(column.equals(list.get(i).getEcmFormItems().get(j).getAttrName())) {
			System.out.println(list.get(i).getEcmFormItems().get(j).getAttrName());
			return list.get(i).getEcmFormItems().get(j).getLabel();}
				
		}
		}
		return "未识别";
	}
	
	
	
}





