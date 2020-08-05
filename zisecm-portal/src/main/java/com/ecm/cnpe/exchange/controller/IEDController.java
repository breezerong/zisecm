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

import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class IEDController  extends ControllerAbstract  {
	
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
	
	public String IDS;										//公用ID，找列的时候要用
	@RequestMapping(value = "/exchange/ied/newIED", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newIED(String metaData, MultipartFile uploadFile) throws Exception {
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
		doc.addAttribute("C_WBS_CODING","1516-E-L3-N1-A-FU.E1.E314C.ED.11.1");//测试用数据，使用时删除
		Object temp1=doc.getAttributeValue("C_WBS_CODING");
		if(temp1 != null) {
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
			System.out.println("WBS:"+WBSitem2date);
			Date IED =df.parse(IEDitem2date);
			System.out.println("IED:"+IEDitem2date);
			boolean before = WBS.before(IED);
			if(before == true) {
				Map<String, Object> mp3 = new HashMap<String, Object>();		
				mp3.put("code",3);
				mp3.put("mess", "IED的外部计划时间不可晚于WBS的计划完成时间");
				return mp3;
			}
		}
		}
		/*List<Map<String,Object>> result =documentService.getObjectMap(getToken(), cond);//查询IED用MAP
		Map<String, Object> mp = new HashMap<String, Object>();					//结果map
		if(result!=null&&result.size()>0) {
			mp.put("code",ActionContext.FAILURE);
			mp.put("mess","IED已存在，不可重复创建,外部编号为 "+coding);
			return mp;	
		}/*/
		String id = documentService.newObject(getToken(), doc, en);
		
		Map<String, Object> mp = new HashMap<String, Object>();	
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
				EcmDocument checkInDoc = documentService.checkIn(getToken(), id, null, false);
     			checkInDoc.addAttribute("C_ITEM_STATUS1", "修订");
				checkInDoc.addAttribute("C_IS_RELEASED", 0);
				
				documentService.updateObject(getToken(), checkInDoc, null);
				documentService.updateStatus(getToken(), docObj.getId(), "变更中");
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		this.IDS = args.get("ID").toString();
		EcmDocument temp = new EcmDocument();
		temp = documentService.getObjectById(getToken(), id);
		String Coding = temp.getCoding();
		String cond = "TYPE_NAME = 'IED' AND IS_CURRENT = 1 AND CODING = '"+Coding+"'";
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
		String cond = "TYPE_NAME = 'IED' AND IS_CURRENT = 1 AND CODING = '"+Coding+"'";
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
		System.out.println("长度："+L1.size());
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
	            &&!nowKey.equals("VERSION_ID")&&!nowKey.equals("IS_CURRENT"))  {//若两个map中相同key对应的value不相等
	               //String key = (String) iter1.next();			//取key,下面取value,然后把结果不同的key&value put进对应的Map
	              tempsave[i]=nowKey;
	              i++;
	            }
		  }
		     for(int j = 0;j < tempsave.length;j++) {
		    	 	if(tempsave[j]!=null) {
		    	 	column tempC1 = new column();
		    	 	tempC1.setLabel(checkLabel(tempsave[j]));
		    	 	tempC1.setAttrName(tempsave[j]);
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
	

	private String checkLabel(String attr) {
		if(attr.equals("C_DESIGN_UNIT")) {
		return "设计单位";
		}
		if(attr.equals("C_PROJECT_CODE")) {
		return "项目ID";	
		}
		if(attr.equals("C_PROJECT_NAME")) {
			return "项目名称";
		}
		if(attr.equals("C_WBS_CODING")) {
			return "WBS编码";
		}
		if(attr.equals("C_TITLE")) {
			return "英文标题";
		}
		/*if(attr.equals("REVISION")) {
			return "版本";
		}/*/
		if(attr.equals("C_ITEM4_DATE")) {
			return "提交计划";
		}
		if(attr.equals("C_ITEM5_DATE")) {
			return "外部计划";
		}
		if(attr.equals("C_ITEM6_DATE")) {
			return "FU计划";
		}
		if(attr.equals("C_ITEM7_DATE")) {
			return "DEN计划";
		}
		/*if(attr.equals("SUB_TYPE")) {
			return "文件/图册标识";
		}/*/
		if(attr.equals("C_FROM_CODING")) {
			return "所属图册号";
		}
		if(attr.equals("C_TYPE1")) {
			return "设计类型";
		}
		if(attr.equals("C_UNIT")) {
			return "机组";
		}
		if(attr.equals("C_SYS_CODE")) {
			return "系统";
		}
		if(attr.equals("C_INST_BUILDING")) {
			return "安装厂房";
		}
		if(attr.equals("C_INST_LEVEL")) {
			return "安装层位及标高";
		}
		if(attr.equals("C_INST_AREA_ZONE")) {
			return "安装区域";
		}
		if(attr.equals("C_INST_POSITION")) {
			return "安装层位";
		}
		if(attr.equals("C_ISLAND_TYPE")) {
			return "岛别";
		}
		if(attr.equals("C_BUILDING")) {
			return "厂房";
		}
		if(attr.equals("C_ZONE")) {
			return "分区";
		}
		if(attr.equals("C_LEVEL")) {
			return "层位及标高";
		}
		if(attr.equals("C_AREA")) {
			return "区域";
		}
		if(attr.equals("C_ROOM")) {
			return "房间";
		}
		if(attr.equals("C_MODULE")) {
			return "模块";
		}
		if(attr.equals("C_EQUIPMENT")) {
			return "设备";
		}
		if(attr.equals("C_COMMENT")) {
			return "总体性文件";
		}
		if(attr.equals("CREATION_DATE")) {
			return "新增日期";
		}
		if(attr.equals("C_ITEM1_DATE")) {
			return "提交计划调整日期";
		}
		if(attr.equals("C_COMMENT1")) {
			return "提交计划调整依据";
		}
		if(attr.equals("C_ITEM2_DATE")) {
			return "外部计划调整日期";
		}
		if(attr.equals("C_COMMENT2")) {
			return "外部计划调整依据";
		}
		if(attr.equals("C_ITEM3_DATE")) {
			return "变更日期";
		}
		if(attr.equals("C_COMMENT3")) {
			return "变更依据";
		}
		if(attr.equals("C_ITEM4_DATE")) {
			return "预计日期";
		}
		if(attr.equals("C_COMMENT4")) {
			return "进展说明";
		}
		if(attr.equals("STATUS")) {				//预计修改点：字段名称
			return "状态";
		}
		if(attr.equals("C_SEND_DATE")) {
			return "传递单日期";
		}
		if(attr.equals("C_REF_CODING")) {
			return "传递单通道号";
		}
		/*if(attr.equals("FUStatus")) {
			return "FU状态";
		}/*/
		if(attr.equals("C_DOUBLE1")) {
			return "图册完成百分比";
		}
		if(attr.equals("C_ITEM_DATE")) {
			return "发布日期";
		}
		if(attr.equals("C_RECEIVER")) {
			return "接收人";
		}
		if(attr.equals("C_RECEIVE_DATE")) {
			return "接收日期";
		}
		if(attr.equals("C_ITEM_STATUS1")) {
			return "变更类型";
		}
		if(attr.equals("C_COMPANY")) {
			return "分包商";
		}
		/*if(attr.equals("CREATOR")) {
			return "创建人";
		}/*/
		/*if(attr.equals("C_IS_RELASED")) {
			return "创建人";
		}/*/
		return "未识别";
	}
}





