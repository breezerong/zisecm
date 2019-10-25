package com.ecm.portal.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.AttributeService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 
 * @author Haihong Rong
 * 文档属性管理控制器
 */
@Controller
public class AttributeController extends ControllerAbstract{
	/**
	 * 数据访问
	 */
	@Autowired
	private AttributeService attributeService;
	
	
	/**
	 * 获取文档属性
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping("/admin/getAttribute")
	 public   Map<String, Object>  getAttribute() {
		 List<EcmAttribute> obj;
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		try {
			obj = attributeService.getDocAttributes(getToken());
			 mp.put("data", obj);
			 mp.put("code", ActionContext.SUCESS);
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.TIME_OUT);
			mp.put("message", e.getMessage());
		}
		 return mp;
	 }
	 
	 /**
		 * 更新
		 * @param obj 实体    
		 * @return
		 */
		@RequestMapping(value = "/admin/updateAttribute", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateAttribute(@RequestBody EcmAttribute obj) {
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				attributeService.updateAttribute(getToken(),obj);
				mp.put("code", ActionContext.SUCESS);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			} catch (NoPermissionException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.NO_PERMSSION);
				mp.put("message", e.getMessage());
			}
			return mp;
		}

		/**
		 * 删除
		 * 
		 * @param obj 实体
		 * @return
		 */
		@RequestMapping(value = "/admin/deleteAttribute", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAttribute(@RequestBody EcmAttribute obj) {
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				if(attributeService.deleteAttribute(getToken(),obj)) {
					mp.put("code", ActionContext.SUCESS);
				}else
				{
					mp.put("code", ActionContext.FAILURE);
				}
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			} catch (NoPermissionException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.NO_PERMSSION);
				mp.put("message", e.getMessage());
			}
			
			return mp;
		}

		/**
		 * 新建
		 * 
		 * @param obj 实体
		 * @return
		 */
		@RequestMapping(value = "/admin/newAttribute", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> newAttribute(@RequestBody EcmAttribute obj) {
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				obj.createId();
				attributeService.newAttribute(getToken(),obj);
				mp.put("code", ActionContext.SUCESS);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mp.put("code", ActionContext.FAILURE);
				mp.put("message", e.getMessage());
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			} catch (NoPermissionException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.NO_PERMSSION);
				mp.put("message", e.getMessage());
			}
			return mp;
		}


}
