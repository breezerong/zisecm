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
import com.ecm.core.entity.EcmDefAttribute;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.service.DefAttributeService;
import com.ecm.portal.controller.ControllerAbstract;

/**
 * 
 * @author Administrator
 * 属性定义管理控制器
 */
@Controller
public class DefAttributeController extends ControllerAbstract{
	/**
	 * 属性定义数据访问
	 */
	@Autowired
	private DefAttributeService attributeService;
	
	
	/**
	 * 获取文档属性
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping("/admin/getDefAttribute")
	 public   Map<String, Object>  getAttribute(String id) {
		 EcmDefAttribute obj;
		 Map<String, Object>   mp = new HashMap<String, Object> ();
		try {
			obj = attributeService.getObjectById(getToken(),id);
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
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			mp.put("code", ActionContext.NO_PERMSSION);
			mp.put("message", e.getMessage());
		}
		 return mp;
	 }
	 
	 /**
		 * 获取文档属性
		 * @return
		 */
		 @ResponseBody
		 @RequestMapping("/admin/getDefAttributeByTypeId")
		 public   Map<String, Object>  getDefAttributeByTypeId(@RequestBody String typeId)  {
			 List<EcmDefAttribute> list;
			 Map<String, Object>   mp = new HashMap<String, Object> ();
			try {
				 list = attributeService.getAttributes(getToken(), typeId);
				 mp.put("data", list);
				 mp.put("code", ActionContext.SUCESS);
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
		@RequestMapping(value = "/admin/updateDefAttribute", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateDefAttribute(@RequestBody EcmDefAttribute obj) {
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				attributeService.updateObject(getToken(),obj);
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
		@RequestMapping(value = "/admin/deleteDefAttribute", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteDefAttribute(@RequestBody EcmDefAttribute obj) {
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				if(attributeService.deleteObject(getToken(),obj)) {
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
		@RequestMapping(value = "/admin/newDefAttribute", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> newDefAttribute(@RequestBody EcmDefAttribute obj) {
			Map<String, Object> mp = new HashMap<String, Object>();
			try {
				obj.createId();
				attributeService.newObject(getToken(),obj);
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
