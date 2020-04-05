package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmAuditGeneral;
import com.ecm.core.entity.EcmLangInfo;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.AuditService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 日志管理
 * @author Haihong Rong
 * Date:2020年4月5日 下午12:08:00
 */
@Controller
public class AuditManager extends ControllerAbstract{
	
	private final Logger logger = LoggerFactory.getLogger(AuditManager.class);

	@Autowired
	AuditService auditService;

	/**
	 * 获取所有事件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/admin/audit", method = RequestMethod.GET)
	public Map<String, Object> getAudit() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			List<EcmAuditGeneral> list = auditService.getAllObject(getToken());
			mp.put("data", list);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		
		return mp;
	}

	@ResponseBody
	@RequestMapping(value="/admin/searchAudit", method = RequestMethod.POST)
	public Map<String, Object> searchAudit(@RequestBody String argStr) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args = JSONUtils.stringToMap(argStr);
			List<EcmAuditGeneral> list = null;
			Pager pager = new Pager();
			pager.setPageIndex(Integer.parseInt(args.get("pageIndex").toString()));
			pager.setPageSize(Integer.parseInt(args.get("pageSize").toString()));
			list = auditService.getObjectByCondition(getToken(), args.get("condition").toString(), pager);
			mp.put("data", list);
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			mp.put("code", ActionContext.TIME_OUT);
		} catch (SqlDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			e.printStackTrace();
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.FAILURE);
			e.printStackTrace();
		}
		return mp;
		
	} 
	

	/**
	 * 删除日志
	 * 
	 * @param obj
	 *            事件对象
	 * @return
	 */
	@RequestMapping(value = "/admin/audit", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteAction(@RequestBody String id) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			auditService.deleteObjectById(getToken(),id);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
}
