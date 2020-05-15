package com.ecm.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmStorageColumn;
import com.ecm.core.entity.EcmStorageRoom;
import com.ecm.core.entity.EcmStorageRow;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.EcmStorageColumnService;
import com.ecm.core.service.EcmStorageRoomService;
import com.ecm.core.service.EcmStorageRowService;

@Controller
public class StorageRoomController extends ControllerAbstract{
	@Autowired
	private EcmStorageRoomService storageRoomService;
	@Autowired
	private EcmStorageColumnService storageColumnService;
	@Autowired
	private EcmStorageRowService ecmStorageRowService;
	/**
	 * 创建库房
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/createStorageRoom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createStorageRoom(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		EcmStorageRoom storageRoom = new EcmStorageRoom();
		storageRoom.setAttributes(args);
		boolean boo;
		try {
			boo=storageRoomService.createOrUpdateStorageRoom(getToken(), storageRoom);
			if(boo) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
		
	}
	
	/**
	 * 删除库房
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/deleteStorageRoom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStorageRoom(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		boolean boo;
		try {
			boo=storageRoomService.deleteStorageRoom(getToken(), argStr);
			if(boo) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
		
	}
	
	/**
	 * 删除列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/deleteStorageColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStorageColumn(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		boolean boo;
		try {
			boo=storageColumnService.deleteStorageColumn(getToken(), argStr);
			if(boo) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
		
	}
	/**
	 * 删除列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/deleteStorageRow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStorageRow(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		boolean boo;
		try {
			boo=ecmStorageRowService.deleteStorageRow(getToken(), argStr);
			if(boo) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
		
	}
	
	/**
	 * 获取所有库房
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/getAllStorageRoom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllStorageRoom(){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			List<Map<String,Object>> data= storageRoomService.getAllStorageRoom(getToken());
			mp.put("data", data);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			
		}
		
		return mp;
		
	}
	
	/**
	 * 获取库房中的列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/getColumnsByStorageRoom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getColumnByStorageRoom(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			List<Map<String,Object>> data= 
					storageColumnService.getColumnByStorageRoom(getToken(), argStr);
			mp.put("data", data);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			
		}
		
		return mp;
		
	}
	
	/**
	 * 获取库房中的列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/getRoomById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRoomById(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			List<Map<String,Object>> data= 
					storageRoomService.getRoomById(getToken(),argStr);
			if(data!=null&&data.size()>0) {
				mp.put("data", data.get(0));
			}
			
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", e.getMessage());
			
		}
		
		return mp;
		
	}
	
	/**
	 * 获取库房中的列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/getColumnById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getColumnById(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			List<Map<String,Object>> data= 
					storageColumnService.getColumnById(getToken(),argStr);
			if(data!=null&&data.size()>0) {
				mp.put("data", data.get(0));
			}
			
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", e.getMessage());
			
		}
		
		return mp;
		
	}
	
	/**
	 * 获取库房中的列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/getRowById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRowById(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			List<Map<String,Object>> data= 
					ecmStorageRowService.getRowById(getToken(),argStr);
			if(data!=null&&data.size()>0) {
				mp.put("data", data.get(0));
			}
			
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("msg", e.getMessage());
			
		}
		
		return mp;
		
	}
	
	/**
	 * 创建列
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/createStorageColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createStorageColumn(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		EcmStorageColumn storageColumn = new EcmStorageColumn();
		storageColumn.setAttributes(args);
		boolean boo;
		try {
			boo=storageColumnService.createOrUpdateStorageColumn(getToken(), storageColumn);
			if(boo) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
		
	}
	@RequestMapping(value = "/dc/getJsonParam", method = RequestMethod.POST) // PostMapping("/dc/getDocumentCount")
	@ResponseBody
	public Map<String, Object> getOneParameterValue(@RequestBody String argStr) {
		
		String params = CacheManagerOper.getEcmParameters().get(argStr).getValue();
		Map<String, Object> mp = new HashMap<String, Object>();
		if(params!=null) {
			
			List<Object> data= JSONArray.parseArray(params);
			mp.put("data", data);
		}
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
	}
	
	/**
	 * 创建连
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/createStorageRow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createStorageRow(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		EcmStorageRow storageRow = new EcmStorageRow();
		storageRow.setAttributes(args);
		boolean boo;
		try {
			boo=ecmStorageRowService.createOrUpdateStorageRow(getToken(), storageRow);
			if(boo) {
				mp.put("code", ActionContext.SUCESS);
			}else {
				mp.put("code", ActionContext.FAILURE);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
		}
		
		return mp;
		
	}
	
	/**
	 * 获取库房中的列的连
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value ="/dc/getRowByColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRowByColumn(@RequestBody String argStr){
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		int pageSize = Integer.parseInt(args.get("pageSize").toString());
		int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
		Pager pager = new Pager();
		pager.setPageIndex(pageIndex);
		pager.setPageSize(pageSize);
		Object codingObj=args.get("columnCoding");
		String columnCoding="";
		if(codingObj!=null) {
			columnCoding=codingObj.toString();
		}
		
		try {
			
			List<Map<String,Object>> data= 
					ecmStorageRowService.getAllStorageRows(getToken(),pager, columnCoding);
			mp.put("pager", pager);
			mp.put("data", data);
			mp.put("code", ActionContext.SUCESS);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			
		}
		
		return mp;
		
	}
}
