package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ecm.cnpe.exchange.service.impl.IEDImportService;
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
import com.ecm.core.entity.ExcSynBatch;
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
import com.ecm.core.service.ExcSynBatchService;

@RestController

public class ExcSynBatchController  extends ControllerAbstract {	
	@Autowired
	private ExcSynBatchService batchService;
	@Autowired
	private DocumentService documentService;
	
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
		String cond = "BATCH_NUM = '"+id+"'";//执行
		List<ExcSynBatch> result=batchService.selectByCondition(pager,cond);
		if(result.size()==0) {
			temp.setAppName("P6");
			temp.setCreationDate(now);
			temp.setActionName("同步");
			temp.setStauts("新建");
			temp.setBatchNum(childId);
			batchService.newObject(temp);
			
		}
		else if(result.size()>0) {
			K.append("编号为"+doc.getName()+"的同步日志已经创建,创建失败").append("<br>");		//resultString
			continue;
		}
		}
		K.append("新建日志完毕,出错结果如上,其余同步已完成");
		return K.toString();
	}
	
}
