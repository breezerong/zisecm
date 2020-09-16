package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.cnpe.exchange.service.IP6Service;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.ExcSynBatch;
import com.ecm.core.entity.Pager;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynBatchService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class ExcSynBatchController  extends ControllerAbstract {	
	@Autowired
	private ExcSynBatchService batchService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ExcSynDetailService detailService;
	
	@Autowired
	private IP6Service p6Service;
	
	
	
	@RequestMapping(value = "/exchange/ied/getBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBatch(@RequestBody String argStr) throws Exception {
		Pager pager = new Pager();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		List<Map<String, Object> > outList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mp = new HashMap<String, Object>();
		//String projectname = args.get("C_PROJECTNAME").toString();
		String ID = args.get("ID").toString();
		int pagesize = Integer.parseInt(args.get("pagesize").toString());
		int pageindex = Integer.parseInt(args.get("currentpage").toString());
		pager.setPageIndex(pageindex);
		pager.setPageSize(pagesize);
		String cond = "BATCH_NUM = '"+ID+"'";
		//System.out.println("取到的数据是"+projectname);
		List<ExcSynBatch> projList = batchService.selectByCondition(pager,cond);
		List<ExcSynBatch> totals = batchService.getByCondition(cond);
		int total = totals.size();
		mp.put("data", projList);
		mp.put("itemCount", total);
		return mp;
	}
	
	@RequestMapping(value = "/exchange/p6/getProjects", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getP6Projects(){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		mp.put("data", p6Service.getP6Projects());
		return mp;
	}
	
	@RequestMapping(value = "/exchange/exchange/createBatch", method = RequestMethod.POST)
	@ResponseBody
	public String	createBatch(@RequestBody String argStr) throws Exception{
		Date now = new Date();
		Pager pager = new Pager();
		pager.setPageIndex(0);
		pager.setPageSize(10);
		StringBuilder K = new StringBuilder();				//ResultString
		ExcSynBatch temp = new ExcSynBatch();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String idsStr=args.get("ids").toString();
		List<String> list = JSONUtils.stringToArray(idsStr);
		for(String childId : list) {
		EcmDocument doc= documentService.getObjectById(getToken(), childId);
		String id = doc.getId();	//获取联动ID，根据ID去查批次号，返回的list长度为0就说明数据库里没有同步日志，可以创建
		String cond = "BATCH_NUM = '"+id+"' and status='新建'";//执行
		List<ExcSynBatch> result=batchService.selectByCondition(pager,cond);
		if(result.size()==0) {
			temp.setAppName("P6");
			temp.setCreationDate(now);
			temp.setActionName("同步");
			temp.setStatus("新建");
			temp.setBatchNum(childId);
			batchService.newObject(temp);
			
		}
		else if(result.size()>0) {
			K.append("编号为"+doc.getName()+"的同步日志已经创建,重复创建创建失败").append("<br>");		//resultString
			continue;
		}
		}
		K.append("新建日志完毕,出错结果如上,其余同步已完成");
		return K.toString();
	}
	
}
