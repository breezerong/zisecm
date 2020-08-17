package com.ecm.cnpe.exchange.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.cnpe.exchange.entity.StatusEntity;
import com.ecm.cnpe.exchange.service.impl.DocumentService4Cnpe;
import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class DocumentController4Cnpe extends ControllerAbstract {
	@Autowired
	private DocumentService4Cnpe documentService;
	@Autowired
	private ExcTransferServiceImpl excTransferService;
	
	@Autowired
	private ExcSynDetailService detailService;
	
	@RequestMapping(value = "/dc/getDocuments4Cnpe", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocuments4Cnpe(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			List<Map<String, Object>> list = documentService.getObjects4Cnpe(getToken(), args.get("gridName").toString(),
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
	
	
	/**
	 * 分发文件
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/dispenseDc", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> dispenseDc(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String idsStr=args.get("ids").toString();
		String contractorsStr=args.get("contractors").toString();
		List<String> idsList = JSONUtils.stringToArray(idsStr);
		List<String> contractorsList = JSONUtils.stringToArray(contractorsStr);
		for(String contractorName : contractorsList) {
			for(String childId : idsList) {
				EcmDocument doc= documentService.getObjectById(getToken(), childId);
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)) {
					currentStatus="新建";
				}
				ExcTransfer excTransfer=new ExcTransfer();
				excTransfer.setItemType(1);
				excTransfer.setDocId(childId);
				excTransfer.setFromName(doc.getAttributeValue("C_FROM")!=null?doc.getAttributeValue("C_FROM").toString():"");
				excTransfer.setToName(contractorName);
				excTransfer.setCreationDate(new Date());
				excTransfer.setCreator(this.getSession().getCurrentUser().getUserName());
				excTransfer.setSender(this.getSession().getCurrentUser().getUserName());
				excTransfer.setSendDate(new Date());
				String nextStatus= StatusEntity.getNextDcStatusValue("新建", doc.getTypeName(), true);
				excTransfer.setStauts(nextStatus);
				excTransferService.newObject(excTransfer);
				if("新建".equals(currentStatus)) {
					doc.addAttribute("c_item_date", new Date());
				}
				doc.addAttribute("C_IS_RELEASED", 1);
				doc.setStatus(nextStatus);
				documentService.updateObject(getToken(), doc, null);
			}
			
		}
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
	}
	
	/**
	 * 下一状态
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/nextStatusCnpe", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> nextStatus(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			
			List<String> list = JSONUtils.stringToArray(idsStr);
			
			String idsStrs=String.join("','", list);
			idsStrs="'"+idsStrs+"'";
			
			List<Map<String, Object>> excTransferIds= documentService.getExcTransferByDocId(getToken(),idsStrs);
			if(excTransferIds==null) {
				mp.put("code", ActionContext.SUCESS);
				return mp;
			}
			//
			for(Map<String, Object> childId : excTransferIds) {
				
				ExcTransfer doc= excTransferService.getObjectById(childId.get("ID").toString());
				String currentStatus= doc.getStauts();
				if(currentStatus==null||"".equals(currentStatus)) {
					currentStatus="新建";
				}
				String nextStatus= StatusEntity.getNextDcStatusValue(currentStatus, null, true);
				doc.setStauts(nextStatus);
				excTransferService.updateObject(doc);
//				OptionLogger.logger(detailService, doc, "分包商接收", "CNPE");
				OptionLogger.logger(detailService, doc, "CNPE");
								
			}
			mp.put("code", ActionContext.SUCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	}
	
	/**
	 * 设计文件上一状态
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/previousDrawingFile", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> previousDrawingFile(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			
			List<String> list = JSONUtils.stringToArray(idsStr);
			String rejectCommon=args.get("rejectCommon")!=null?args.get("rejectCommon").toString():"";
			//
			for(String childId : list) {
				EcmDocument doc= documentService.getObjectById(getToken(), childId);
				
				doc.addAttribute("C_PROCESS_STATUS", "已解锁");
				doc.addAttribute("C_REJECT_COMMENT", rejectCommon);
				doc.addAttribute("C_REJECTOR", this.getSession().getCurrentUser().getUserName());
				doc.addAttribute("C_REJECT_DATE", new Date());
				documentService.updateObject(getToken(), doc, null);
			}
			mp.put("code", ActionContext.SUCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", "操作失败请联系管理员！");
		}
		
		return mp;
	}
	
	/**
	 * 上一状态
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/previousStatusCnpe", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> previousStatus(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			String rejectCommon=args.get("rejectCommon")!=null?args.get("rejectCommon").toString():"";
			
			List<String> list = JSONUtils.stringToArray(idsStr);
			
			String idsStrs=String.join("','", list);
			idsStrs="'"+idsStrs+"'";
			
			List<Map<String, Object>> excTransferIds= documentService.getExcTransferByDocId(getToken(),idsStrs);
			if(excTransferIds==null) {
				mp.put("code", ActionContext.SUCESS);
				return mp;
			}
			//
			for(Map<String, Object> childId : excTransferIds) {
				ExcTransfer doc= excTransferService.getObjectById(childId.get("ID").toString());
				String currentStatus= doc.getStauts();
				if(currentStatus==null||"".equals(currentStatus)) {
					currentStatus="新建";
				}
				String previousStatus= StatusEntity.getPreviousDcStatusValue(currentStatus, null, true);
				doc.setStauts(previousStatus);
				doc.setComment(rejectCommon);
				doc.setRejecter(this.getSession().getCurrentUser().getUserName());
				doc.setRejectDate(new Date());
				excTransferService.updateObject(doc);
//				OptionLogger.logger(detailService, doc, "分包商驳回", "CNPE");
				OptionLogger.logger(detailService, doc, "CNPE");
				
			}
			mp.put("code", ActionContext.SUCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	}
	
	/**
	 * 申请解锁
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/applyForUnlock", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> applyForUnlock(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
//		Map<String, Object> args = JSONUtils.stringToMap(argStr);
//		String idsStr=args.get("ids").toString();
		
		List<String> list = JSONUtils.stringToArray(argStr);
		for(String id : list) {
			EcmDocument doc= documentService.getObjectById(getToken(), id);
			
			doc.addAttribute("C_PROCESS_STATUS", "申请解锁");
			documentService.updateObject(getToken(), doc, null);
			OptionLogger.logger(detailService, doc, "申请解锁", 
					doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	}
	/**
	 * 申请解锁
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/unlock", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> unlock(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
//		Map<String, Object> args = JSONUtils.stringToMap(argStr);
//		String idsStr=args.get("ids").toString();
		
		List<String> list = JSONUtils.stringToArray(argStr);
		for(String id : list) {
			EcmDocument doc= documentService.getObjectById(getToken(), id);
			
			doc.addAttribute("C_PROCESS_STATUS", "已解锁");
			documentService.updateObject(getToken(), doc, null);
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
		
	}
	
	/**
	 * 处理完成
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/handleComplete", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> handleComplete(@RequestBody String argStr) throws Exception {

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<String> list = JSONUtils.stringToArray(argStr);
			
			documentService.handleComplete(getToken(), list);
			mp.put("code", ActionContext.SUCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
	
	}
}
