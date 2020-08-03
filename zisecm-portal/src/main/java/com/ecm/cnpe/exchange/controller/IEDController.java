package com.ecm.cnpe.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	public Map<String, Object> newIED(String metaData)  throws Exception{
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
	
}
