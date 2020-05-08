package com.ecm.portal.archive.controller;

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

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class BorrowDocumentController extends ControllerAbstract {
	@Autowired
	private DocumentService documentService;
	
	/**
	 * 文件出库
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/putInstoreFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> putInstoreFile(@RequestBody String argStr) {
		Map<String,Object> params= JSONUtils.stringToMap(argStr);
		String pid=params.get("pid").toString();
		String idsStr=params.get("ids").toString();
		List<String> fileIds=JSONUtils.stringToArray(idsStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			for(String fileId:fileIds) {
				EcmDocument doc= documentService.getObjectById(getToken(),fileId);
				doc.setStatus("利用");
				doc.addAttribute("C_STORE_STATUS", "在库");
				documentService.updateObject(getToken(), doc, null);
				
				String strSql="select count(*) as total from ecm_document where C_STORE_STATUS='借出'  and id in("
						+ " select child_id from ecm_relation where parent_id ='"+pid+"')";
				List<Map<String,Object>> mps= documentService.getMapList(getToken(), strSql);
				if(mps!=null&&mps.size()>0) {
					int total= Integer.parseInt(mps.get(0).get("total").toString());
					if(total==0) {
						EcmDocument order= documentService.getObjectById(getToken(), pid);
						order.setStatus("已完成");
						documentService.updateObject(getToken(), order, null);
					}
				}
			}
		} catch (EcmException | AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("message", "入库成功！");
		return mp;
	}
	
	/**
	 * 文件出库
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/outboundfile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> outboundfile(@RequestBody String argStr) {
		Map<String,Object> params= JSONUtils.stringToMap(argStr);
		String pid=params.get("pid").toString();
		String idsStr=params.get("ids").toString();
		List<String> fileIds=JSONUtils.stringToArray(idsStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			for(String fileId:fileIds) {
				EcmDocument doc= documentService.getObjectById(getToken(),fileId);
//				doc.setStatus("待入库");
				doc.addAttribute("C_STORE_STATUS", "借出");
				documentService.updateObject(getToken(), doc, null);
				
				String strSql="select count(*) as total from ecm_document where C_STORE_STATUS='在库' and id in("
						+ " select child_id from ecm_relation where parent_id ='"+pid+"')";
				List<Map<String,Object>> mps= documentService.getMapList(getToken(), strSql);
				if(mps!=null&&mps.size()>0) {
					int total= Integer.parseInt(mps.get(0).get("total").toString());
					if(total==0) {
						EcmDocument order= documentService.getObjectById(getToken(), pid);
						order.setStatus("待归还");
						documentService.updateObject(getToken(), order, null);
					}
				}
			}
		} catch (EcmException | AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("message", "出库成功！");
		return mp;
	}
	
	
	/**
	 * 入库单及其内文件出库
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/putInStorageBorrow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> putInStorageBorrow(@RequestBody String argStr) {
		List<String> orderIds=JSONUtils.stringToArray(argStr);
		String strWhere="'"+String.join("','", orderIds)+"'";
		
		String strSql="select child_id from ecm_relation where parent_id in("+strWhere+") and name='irel_borrow'";
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> childIds = documentService.getMapList(getToken(), strSql);
		
			for(Map<String,Object> m:childIds) {
				EcmDocument doc= documentService.getObjectById(getToken(), m.get("child_id").toString());
				doc.setStatus("利用");
				doc.addAttribute("C_STORE_STATUS", "在库");
				documentService.updateObject(getToken(), doc, null);
			}
			
			for(String orderId:orderIds) {
				EcmDocument order= documentService.getObjectById(getToken(),orderId);
				order.setStatus("已完成");
				documentService.updateObject(getToken(), order, null);
			}
		} catch (EcmException | AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("message", "入库成功！");
		return mp;
	}
	
	/**
	 * 待归还
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/giveback", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> giveBack(@RequestBody String argStr) {
		List<String> orderIds=JSONUtils.stringToArray(argStr);
		
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
		
			for(String orderId:orderIds) {
				EcmDocument order= documentService.getObjectById(getToken(),orderId);
				order.setStatus("待入库");
				documentService.updateObject(getToken(), order, null);
			}
		} catch (EcmException | AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("message", "操作成功！");
		return mp;
	}
	
	/**
	 * 出库单及其内文件出库
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/outboundorder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> outboundorder(@RequestBody String argStr) {
		List<String> orderIds=JSONUtils.stringToArray(argStr);
		String strWhere="'"+String.join("','", orderIds)+"'";
		
		String strSql="select child_id from ecm_relation where parent_id in("+strWhere+") and name='irel_borrow'";
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> childIds = documentService.getMapList(getToken(), strSql);
		
			for(Map<String,Object> m:childIds) {
				EcmDocument doc= documentService.getObjectById(getToken(), m.get("child_id").toString());
//				doc.setStatus("待入库");
				doc.addAttribute("C_STORE_STATUS", "借出");
				documentService.updateObject(getToken(), doc, null);
			}
			
			for(String orderId:orderIds) {
				EcmDocument order= documentService.getObjectById(getToken(),orderId);
				order.setStatus("待归还");
				documentService.updateObject(getToken(), order, null);
			}
		} catch (EcmException | AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("message", "出库成功！");
		return mp;
	}
	
	
	/**
	 * 查询借阅单
	 * @return
	 */
	@RequestMapping(value = "/dc/getBorrowOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getBorrowOrder(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			
			List<Map<String, Object>> list = documentService.getObjectsByObj(getToken(),
					args.get("gridName"),null,
					pager,
					args.get("condition"), args.get("orderBy"));
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}
	

	/**
	 * 晒图
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/dc/showdrawingorder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showdrawingorder(@RequestBody String argStr) {
		List<String> orderIds=JSONUtils.stringToArray(argStr);
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String,Object> obj= new HashMap<String, Object>();
			obj.put("C_END_DATE", new Date());
			obj.put("STATUS", "已晒图");
			for(String orderId:orderIds) {
				EcmDocument order= documentService.getObjectById(getToken(),orderId);
				obj.put("ID", orderId);
				order.setAttributes(obj);
				documentService.updateObject(getToken(), order, null);

			}
		} catch (EcmException | AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		mp.put("code", ActionContext.SUCESS);
		mp.put("message", "操作成功！");
		return mp;
	}
	

	
	/**
	 * 查询晒图
	 * @return
	 */
	@RequestMapping(value = "/dc/getShowDrawingOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getShowDrawingOrder(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			
			List<Map<String, Object>> list = documentService.getObjectsByObj(getToken(),
					args.get("gridName"),null,
					pager,
					args.get("condition"), args.get("orderBy"));
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		}
		return mp;
	}	
 
}

