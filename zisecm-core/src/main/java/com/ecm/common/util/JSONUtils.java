package com.ecm.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
	/**
	 * 获取Map
	 * @param argStr
	 * @return
	 */
	public static Map<String,Object> stringToMap(String argStr)
	{
		
		try {
			JSONArray json=JSONArray.parseArray(argStr);
			Map<String,Object> args = new HashMap<String,Object>();
			for(int i=0;i<json.size();i++)
			{
				JSONArray item = json.getJSONArray(i);
				args.put(item.getString(0), item.getString(1));
			}
			return args;
		}catch(Exception e) {
			JSONObject json = JSON.parseObject(argStr);
			Map<String,Object> args = new HashMap<String,Object>();
			Set<String> keys =json.keySet();
			for (String key : keys) {
				args.put(key, json.get(key));
			}
			return args;
		}
		
	}
	
	public static Map<String,Object> stringObjectToMap(String argStr)
	{
		ObjectMapper  mapper=new ObjectMapper();
		try {
			return mapper.readValue(argStr, Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	
	public static List<String> stringToArray(String argStr)
	{
		JSONArray json=JSONArray.parseArray(argStr);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<json.size();i++)
		{
			list.add(json.getString(i));
		}
		return list;
	}

}
