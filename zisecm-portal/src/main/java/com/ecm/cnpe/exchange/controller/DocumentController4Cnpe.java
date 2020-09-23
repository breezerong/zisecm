package com.ecm.cnpe.exchange.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.cnpe.exchange.entity.StatusEntity;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeInterface;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeRelevantDoc;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeTransfer;
import com.ecm.cnpe.exchange.service.impl.DocumentService4Cnpe;
import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.util.CustomInfo;

@Controller
public class DocumentController4Cnpe extends ControllerAbstract {
	@Autowired
	private EcmDocumentMapper ecmDocument;
	@Autowired
	private DocumentService4Cnpe documentService;
	@Autowired
	private ExcTransferServiceImpl excTransferService;
	@Autowired
	private LogicOption4CnpeInterface logicOptionInterfaceService;
	@Autowired
	private LogicOption4CnpeRelevantDoc logicOptionRelevantService;
	
	@Autowired
	private ExcSynDetailService detailService;
	@Autowired
	private LogicOption4CnpeTransfer logicOptionTransferService;
	
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
	@RequestMapping(value = "/dc/getDocumentsICMUnion", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocumentsICMUnion(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			List<Map<String, Object>> list = documentService.getObjectsICM(getToken(), args.get("gridName").toString(),
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
	
	@RequestMapping(value = "/dc/getDocDesign", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getDocDesign(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			List<Map<String, Object>> list = documentService.getDesignDC(getToken(), args.get("gridName").toString(),
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
	
	@RequestMapping(value = "/dc/dispenseDc", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> dispenseDc(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String idsStr=args.get("ids").toString();
		List<String> idsList = JSONUtils.stringToArray(idsStr);

		for(String childId : idsList) {
			EcmDocument doc= documentService.getObjectById(getToken(), childId);
			String currentStatus= doc.getStatus();
			
			if(currentStatus==null||"".equals(currentStatus)) {
				currentStatus="新建";
			}
			String nextStatus= StatusEntity.getNextDcStatusValue("新建", doc.getTypeName(), true);
			
			
			if("图文传真,会议纪要".contains(doc.getTypeName())){
				documentService.updateObject(getToken(), doc, null);
				doc.addAttribute("C_IS_RELEASED", 1);
			}else {
				
				if("文件传递单".equals(doc.getTypeName())) {
					logicOptionTransferService.transferOption(getToken(), doc,true);
				}else if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
					logicOptionInterfaceService.interfaceOption(getToken(), doc);
				}else {
					logicOptionRelevantService.relevantOption(getToken(),doc,true);
				}
				doc.addAttribute("C_IS_RELEASED", 1);
				documentService.updateObject(getToken(), doc, null);
				
			}
			
			
			
			String contractorStr= doc.getAttributeValue("C_TO")==null?"":doc.getAttributeValue("C_TO").toString();
			String copyToStr=doc.getAttributeValue("C_COPY_TO")==null?"":doc.getAttributeValue("C_COPY_TO").toString();
			
			String codingStr=doc.getCoding();
			String[] contractors=contractorStr.split(";");
			String[] copyTos=copyToStr.split(";");
			String[] codings=codingStr.split(";");
			for(int i=0;i<contractors.length;i++) {
				if(!StringUtils.isEmpty(contractors[i])) {
					ExcTransfer excTransfer=new ExcTransfer();
					excTransfer.setItemType(1);
					excTransfer.setDocId(childId);
					excTransfer.setFromName(doc.getAttributeValue("C_FROM")!=null?doc.getAttributeValue("C_FROM").toString():"");
					excTransfer.setToName(contractors[i]);
					excTransfer.setCreationDate(new Date());
					excTransfer.setCreator(this.getSession().getCurrentUser().getUserName());
					excTransfer.setSender(this.getSession().getCurrentUser().getUserName());
					excTransfer.setSendDate(new Date());
					
					excTransfer.setStatus(nextStatus);
					excTransferService.newObject(excTransfer);
				}
				
			}
			
			for(int i=0;i<copyTos.length;i++) {
				if(!StringUtils.isEmpty(copyTos[i])) {
					ExcTransfer excTransfer=new ExcTransfer();
					excTransfer.setItemType(1);
					excTransfer.setDocId(childId);
					excTransfer.setFromName(doc.getAttributeValue("C_FROM")!=null?doc.getAttributeValue("C_FROM").toString():"");
					excTransfer.setToName(copyTos[i]);
					excTransfer.setCreationDate(new Date());
					excTransfer.setCreator(this.getSession().getCurrentUser().getUserName());
					excTransfer.setSender(this.getSession().getCurrentUser().getUserName());
					excTransfer.setSendDate(new Date());
					
					excTransfer.setStatus(nextStatus);
					excTransferService.newObject(excTransfer);
				}
				
			}
			
			if("新建".equals(currentStatus)) {
				doc.addAttribute("c_item_date", new Date());
			}
			doc.addAttribute("C_IS_RELEASED", 1);
			doc.setStatus(nextStatus);
			documentService.updateObject(getToken(), doc, null);
		}
		
	
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
	}
	@RequestMapping(value = "/dc/reDispenseDc", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> reDispenseDc(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String idsStr=args.get("ids").toString();
		List<String> idsList = JSONUtils.stringToArray(idsStr);

		for(String childId : idsList) {

			String sql = "update exc_transfer set STATUS='待接收'   where DOC_ID='"+childId+"'";	

			ecmDocument.executeSQL(sql);
		}
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
	}
	/**
	 * 分发文件
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "/dc/dispenseDc", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
//	@ResponseBody
//	public Map<String, Object> dispenseDc(@RequestBody String argStr) throws Exception {
//		Map<String, Object> mp = new HashMap<String, Object>();
//		Map<String, Object> args = JSONUtils.stringToMap(argStr);
//		String idsStr=args.get("ids").toString();
//		String contractorsStr=args.get("contractors").toString();
//		List<String> idsList = JSONUtils.stringToArray(idsStr);
//		List<String> contractorsList = JSONUtils.stringToArray(contractorsStr);
//		for(String contractorName : contractorsList) {
//			for(String childId : idsList) {
//				EcmDocument doc= documentService.getObjectById(getToken(), childId);
//				String currentStatus= doc.getStatus();
//				if(currentStatus==null||"".equals(currentStatus)) {
//					currentStatus="新建";
//				}
//				ExcTransfer excTransfer=new ExcTransfer();
//				excTransfer.setItemType(1);
//				excTransfer.setDocId(childId);
//				excTransfer.setFromName(doc.getAttributeValue("C_FROM")!=null?doc.getAttributeValue("C_FROM").toString():"");
//				excTransfer.c(contractorName);
//				excTransfer.setCreationDate(new Date());
//				excTransfer.setCreator(this.getSession().getCurrentUser().getUserName());
//				excTransfer.setSender(this.getSession().getCurrentUser().getUserName());
//				excTransfer.setSendDate(new Date());
//				String nextStatus= StatusEntity.getNextDcStatusValue("新建", doc.getTypeName(), true);
//				excTransfer.setStatus(nextStatus);
//				excTransferService.newObject(excTransfer);
//				if("新建".equals(currentStatus)) {
//					doc.addAttribute("c_item_date", new Date());
//				}
//				doc.addAttribute("C_IS_RELEASED", 1);
//				doc.setStatus(nextStatus);
//				documentService.updateObject(getToken(), doc, null);
//			}
//			
//		}
//		mp.put("code", ActionContext.SUCESS);
//		
//		return mp;
//	}
	
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
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)) {
					currentStatus="新建";
				}
				String nextStatus= StatusEntity.getNextDcStatusValue(currentStatus, null, true);
				doc.setStatus(nextStatus);
				doc.setReceiver(this.getSession().getCurrentUser().getUserName());
				doc.setReceiveDate(new Date());
				excTransferService.updateObject(doc);
				OptionLogger.logger(getToken(), detailService, doc, CustomInfo.OwnerCompany);
								
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
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)) {
					currentStatus="新建";
				}
				String previousStatus= StatusEntity.getPreviousDcStatusValue(currentStatus, null, true);
				doc.setStatus(previousStatus);
				doc.setComment(rejectCommon);
				doc.setRejecter(this.getSession().getCurrentUser().getUserName());
				doc.setRejectDate(new Date());
				if("待确认".equalsIgnoreCase(doc.getStatus1())){
					doc.setStatus1("已驳回");
				}
				excTransferService.updateObject(doc);
				OptionLogger.logger(getToken(), detailService, doc, CustomInfo.OwnerCompany);
				
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
			OptionLogger.logger(getToken(), detailService, doc, "申请解锁", 
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
	@RequestMapping(value = "/dc/getTRS", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> getTRS(@RequestBody String argStr) throws Exception {

		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<String> TRS = JSONUtils.stringToArray(argStr);
			System.out.println(TRS);
			documentService.getTRS(TRS);
			
			
		}catch (Exception e) {
			// TODO: handle exception
	
		}
		
		return mp;
	
	}
	
	@RequestMapping(value = "/exchange/doc/applyReject", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> applyReject(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			String isCnpe=args.get("iscnpe")!=null?args.get("iscnpe").toString():"";
			String msg = args.get("msg").toString();
			List<String> list = JSONUtils.stringToArray(idsStr);
			if(isCnpe.equalsIgnoreCase("true")) {
				for(String childId : list) {
					String condition = "DOC_ID='"+childId+"' AND ITEM_TYPE=1";
					List<ExcTransfer> tlist = excTransferService.selectByCondition(condition);
					for(ExcTransfer obj:tlist) {
						if(obj.getStatus1()!=null &&( obj.getStatus1().equals("待确认")
								||obj.getStatus().equals("驳回"))) {
							continue;
							
						}else {
							obj.setApplicant(documentService.getSession(getToken()).getCurrentUser().getUserName());
							obj.setApplyDate(new Date());
							obj.setStatus1("待确认");
							obj.setComment1(msg);
							excTransferService.updateObject(obj);
							OptionLogger.logger(getToken(), detailService, obj, "申请驳回", obj.getToName());
						}
					}
				}
			}else {
				for(String childId : list) {
					String condition = "DOC_ID='"+childId+"' AND ITEM_TYPE=2";
					List<ExcTransfer> tlist = excTransferService.selectByCondition(condition);
					if(tlist.size() == 0) {
						ExcTransfer obj = new ExcTransfer();
						obj.setItemType(2);
						obj.setCreator(documentService.getSession(getToken()).getCurrentUser().getUserName());
						obj.setCreationDate(new Date());
						obj.setApplicant(documentService.getSession(getToken()).getCurrentUser().getUserName());
						obj.setFromName(documentService.getSession(getToken()).getCurrentUser().getCompany());
						obj.setApplyDate(new Date());
						obj.setStatus1("待确认");
						obj.setComment1(msg);
						obj.setDocId(childId);
						obj.setToName(CustomInfo.OwnerCompany);
						excTransferService.newObject(obj);
						OptionLogger.logger(getToken(), detailService, obj, "申请驳回", obj.getToName());
					}else {
						ExcTransfer obj = tlist.get(0);
						if(obj.getStatus1()!=null && (obj.getStatus1().equals("待确认")||
								obj.getStatus1().equals("已驳回"))) {
							continue;
						}else {
							obj.setApplicant(documentService.getSession(getToken()).getCurrentUser().getUserName());
							obj.setApplyDate(new Date());
							obj.setStatus1("待确认");
							obj.setComment1(msg);
							excTransferService.updateObject(obj);
							OptionLogger.logger(getToken(), detailService, obj, "申请驳回", obj.getToName());
						}
					}
				}
			}
			mp.put("code", ActionContext.SUCESS);
		}catch (Exception e) {
			// TODO: handle exception
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
			e.printStackTrace();
		}
		
		return mp;
	}
}
