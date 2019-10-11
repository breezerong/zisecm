package com.ecm.portal.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName  MessageUtil   
 * @Description TODO(报文格式定义类)   
 * @author Haihong
 * @date 2018年7月13日 上午11:39:47  
 *
 */
public class MessageUtils {
	/**
	 * 
	 * @Title generateReturnMess   
	 * @Description TODO(创建返回报文)   
	 * @param status 执行状态码 
	 * @param msg 说明文字信息，没有为NULL  
	 * @param data 返回的数据
	 * @return Map<String,Object>          
	 * @author Haihong
	 * @date 2018年7月13日 上午11:43:15 
	 */
	public static Map<String,Object> generateReturnMess(int status,String msg,Object data){
		Map<String,Object> mess = new HashMap<>();
		mess.put("status", status);
		mess.put("msg", msg);
		mess.put("data", data);
		
		return mess;
	}
	
	/**
	 * 
	 * @Title generateReturnMess   
	 * @Description TODO(创建返回报文,这个方法只返回成功和失败)   
	 * @param status 执行状态码 
	 * @param msg 说明文字信息，没有为NULL  
	 * @return Map<String,Object>          
	 * @author Haihong
	 * @date 2018年7月13日 下午2:01:58 
	 */
	public static Map<String,Object> generateReturnMess(int status,String msg){
		Map<String,Object> mess = new HashMap<>();
		mess.put("status", status);
		mess.put("msg", msg);
		
		return mess;
	}
	
	/**
	 * 
	 * @Title generateReturnMess   
	 * @Description TODO(创建返回报文)   
	 * @param status 执行状态码 
	 * @param data 返回的数据
	 * @return Map<String,Object>          
	 * @author Haihong
	 * @date 2018年7月13日 上午11:43:15 
	 */
	public static Map<String,Object> generateReturnMess(int status,Object data){
		Map<String,Object> mess = new HashMap<>();
		mess.put("status", status);
		mess.put("data", data);
		
		return mess;
	}
	
	/**
	 * 
	 * @Title generateReturnMess   
	 * @Description TODO(创建返回报文)   
	 * @param status 执行状态码 
	 * @return Map<String,Object>          
	 * @author Haihong
	 * @date 2018年7月13日 上午11:43:15 
	 */
	public static Map<String,Object> generateReturnMess(int status){
		Map<String,Object> mess = new HashMap<>();
		mess.put("status", status);
		
		return mess;
	}
}
