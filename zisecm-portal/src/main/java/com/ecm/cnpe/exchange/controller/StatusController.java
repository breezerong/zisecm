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
import com.ecm.cnpe.exchange.service.LogicOption4CnpeIED;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeInterface;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeRelevantDoc;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeTransfer;
import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.portal.controller.ControllerAbstract;
@Controller
public class StatusController extends ControllerAbstract{
	@Autowired
	private DocumentService documentService;
	@Autowired
	private LogicOption4CnpeInterface logicOptionInterfaceService;
	@Autowired
	private LogicOption4CnpeRelevantDoc logicOptionRelevantService;
	@Autowired
	private LogicOption4CnpeTransfer logicOptionTransferService;
	@Autowired
	private LogicOption4CnpeIED logicOptionCnpeIEDService;
	@Autowired
	private ExcSynDetailService detailService;
	
	@Autowired
	private ExcTransferServiceImpl excTransferService;
	
	/**
	 * 下一状态
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/nextStatus", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> nextStatus(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			String strIsCnpeSend=args.get("isCnpeSend")!=null?args.get("isCnpeSend").toString():"";
			
			boolean isCnpeSend=false;
			if("true".equals(strIsCnpeSend.toLowerCase())) {
				isCnpeSend=true;
			}
			List<String> list = JSONUtils.stringToArray(idsStr);
			//
			for(String childId : list) {
				EcmDocument doc= documentService.getObjectById(getToken(), childId);
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)) {
					currentStatus="新建";
				}
				if("新建".equals(currentStatus)) {
					doc.addAttribute("c_item_date", new Date());
				}
				
				String nextStatus= StatusEntity.getNextDcStatusValue(currentStatus, doc.getTypeName(), isCnpeSend);
				doc.setStatus(nextStatus);
				if("已生效".equals(nextStatus) || "已确认".equals(nextStatus)) {
					doc.addAttribute("C_RECEIVER",this.getSession().getCurrentUser().getUserName());
					doc.addAttribute("C_RECEIVE_DATE", new Date());
				}
				if("驳回".equals(currentStatus) || "已驳回".equals(currentStatus)) {
					doc.addAttribute("C_REJECTOR",this.getSession().getCurrentUser().getUserName());
					doc.addAttribute("C_REJECT_DATE", new Date());
				}
				if("IED".equals(doc.getTypeName())) {
					if("已生效".equals(nextStatus)) {
						//doc.addAttribute("C_ITEM_STATUS2", "Y");
						logicOptionCnpeIEDService.IEDOption(getToken(), doc);
						OptionLogger.logger(getToken(), detailService, doc, "接收", doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
						
					}
					documentService.updateObject(getToken(), doc, null);
					//
					/**
					 * if(判断状态){
					 * 	OptionLogger.logger(detailService, doc, "动作", doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
					 * }
					 */
					if("审核中".equals(nextStatus)) {
						OptionLogger.logger(getToken(), detailService, doc, "提交", doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
					}
					//
				}else if("图文传真,会议纪要".contains(doc.getTypeName())){
					documentService.updateObject(getToken(), doc, null);
					doc.addAttribute("C_IS_RELEASED", 1);
				}else {
					if("待确认".equals(nextStatus)) {
						//验证数据是否存在
						if("文件传递单".equals(doc.getTypeName())) {
							logicOptionTransferService.transferSubValidate(getToken(), doc);
						}else if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
							logicOptionInterfaceService.interfaceValidateOption(getToken(), doc);
						}else {
							logicOptionRelevantService.relevantValidateOption(getToken(),doc);
						}
					}
					if("已确认".equals(nextStatus)) {
						if("文件传递单".equals(doc.getTypeName())) {
							logicOptionTransferService.transferOption(getToken(), detailService,doc,false);
							
						}else if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
							logicOptionInterfaceService.interfaceOption(getToken(), doc);
						}else {
							logicOptionRelevantService.relevantOption(getToken(),doc,false);
						}
						doc.addAttribute("C_IS_RELEASED", 1);
					}
					documentService.updateObject(getToken(), doc, null);
					
				}
				
//				OptionLogger.logger(detailService, doc, "CNPE接收", 
//						doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				if("驳回".equals(currentStatus)) {
					OptionLogger.logger(getToken(), detailService, doc, "驳回提交", 
							doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				}else if(!"IED".equals(doc.getTypeName())) {
					if("新建".equals(currentStatus)||StringUtils.isEmpty(currentStatus)) {
						OptionLogger.logger(getToken(), detailService, doc, "提交", 
								doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
					}else if("已确认".equals(nextStatus)) {
						OptionLogger.logger(getToken(), detailService, doc, "接收", 
								doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
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
	
	
	
	/**
	 * 上一状态
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/previousStatus", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> previousStatus(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			String strIsCnpeSend=args.get("isCnpeSend")!=null?args.get("isCnpeSend").toString():"";
			boolean isCnpeSend=false;
			if("true".equals(strIsCnpeSend.toLowerCase())) {
				isCnpeSend=true;
			}
			List<String> list = JSONUtils.stringToArray(idsStr);
			String rejectCommon=args.get("rejectCommon")!=null?args.get("rejectCommon").toString():"";
			//
			for(String childId : list) {
				EcmDocument doc= documentService.getObjectById(getToken(), childId);
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)||"新建".equals(currentStatus)) {
					continue;
				}
				
				String previousStatus= StatusEntity.getPreviousDcStatusValue(currentStatus, doc.getTypeName(), isCnpeSend);
				if(previousStatus == null) {
					previousStatus = "驳回";
				}
				doc.setStatus(previousStatus);
				doc.addAttribute("C_REJECT_COMMENT", rejectCommon);
				doc.addAttribute("C_REJECTOR", this.getSession().getCurrentUser().getUserName());
				doc.addAttribute("C_REJECT_DATE", new Date());
				documentService.updateObject(getToken(), doc, null);
				

				// 如果是驳回申请，需要更新移交单属性
				
				String condition = "DOC_ID='"+childId+"' AND ITEM_TYPE=2";
				List<ExcTransfer> tlist = excTransferService.selectByCondition(condition);
				// 最多一条移交记录
				if(tlist.size()>0) {
					
					ExcTransfer obj = tlist.get(0);
					if("待确认".equalsIgnoreCase(obj.getStatus1())){
						obj.setStatus1("已驳回");
						obj.setRejecter(this.getSession().getCurrentUser().getUserName());
						obj.setRejectDate(new Date());
						excTransferService.updateObject(obj);
						OptionLogger.logger(getToken(), detailService, obj, "确认驳回", 
								doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
					}
				}
				
				
				if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
					logicOptionInterfaceService.interfaceRejectOption(getToken(), doc);
				}else if("文件传递单".equals(doc.getTypeName())){
					//待扩展需求
				}else {
					logicOptionRelevantService.relevantRejectOption(getToken(),doc);
				}
				
				if("IED".equals(doc.getTypeName())) {
					OptionLogger.logger(getToken(), detailService, doc, "驳回", 
							doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				}else {
					OptionLogger.logger(getToken(), detailService, doc,
							doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				}
				
				
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
	 * 撤回
	 * @param argStr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dc/withdraw", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> withdraw(@RequestBody String argStr) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			String idsStr=args.get("ids").toString();
			
			List<String> list = JSONUtils.stringToArray(idsStr);
			
			//
			for(String childId : list) {
				EcmDocument doc= documentService.getObjectById(getToken(), childId);
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)||"新建".equals(currentStatus)) {
					continue;
				}
				
				doc.setStatus("新建");
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
}
