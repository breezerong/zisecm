package org.zisecm.jobs.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zisecm.jobs.entity.WsEntity;
import org.zisecm.jobs.entity.WsService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.DocumentService;
import com.ecm.icore.service.IEcmSession;

/**
 * 
 * @Title:
 * @author Chen Shuo
 * @date:  2020年7月27日 下午4:13:44
 * @Description
 */
@Service
public class SyncWbs {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private Environment env;
	
	@Scheduled(cron = "${cron.syncwbs}")
	public void run () {
		//获取P6计划数据
		
		/*
		             根据计划ID（P6计划任务ID：C_SYN_ITEM_ID）更新实际开始日期、实际完成日期、文件完成百分比、尚需工期、更新时间。
			计算逻辑1：WBS相关文件、图纸发起传递单流程时，实际开始日期取分包院传递单提交流程发起的日期；如果相关文件、图纸未发起流程，当数据传递当天日期大于等于计划开始日期时，实际开始日期取计划开始日期。
			计算逻辑2：WBS相关文件、图纸全部完成提交项目部流程时的日期。
			计算逻辑3：（只对比日期，忽略时间）
			实际完成日期若有值，尚需工期=0
			实际完成日期若为空，当天日期<计划完成时，尚需工期=计划完成日期-当天日期（+1）；当天日期>=计划完成时，尚需工期=原定工期*5%
			原定工期=计划完成日期-计划开始日期（+1）。

		 */
		
		/*
		 * 获取到的同步数据
		 */
		List<Map<String,Object>> syncDataList = new ArrayList<Map<String,Object>>();
		
		IEcmSession ecmSession = null;
		String workflowSpecialUserName = env.getProperty("ecm.username");
		try {
			ecmSession = authService.login("jobs", workflowSpecialUserName, env.getProperty("ecm.password"));
			
			//读取配置文件
			JSONObject root = loadMapperFile();
			//通过服务key获取服务对象
			WsService serviceObj = loadService(root, "syncproject");
			
			serviceObj.getList().forEach((item) -> {
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title:读取服务定义文件
	 * @author Chen Shuo
	 * @date:  2020年7月29日 上午9:06:23
	 * @Description
	 * @return
	 */
	private static JSONObject loadMapperFile() {
		Resource rs = new ClassPathResource("webservice.json");
		String text;
		JSONObject jso=null;
		InputStream inputStream;
		try {
			inputStream = rs.getInputStream();
			text = IOUtils.toString(inputStream, "UTF-8");
			jso = JSON.parseObject(text);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return jso;
	}
	
	private static WsService loadService(JSONObject root,String serviceName) {
		JSONObject serviceObj = root.getJSONObject(serviceName);
		WsService ws = new WsService(serviceName);
		
		serviceObj.keySet().iterator().forEachRemaining(entityKey -> {			
			ws.getList().add(new WsEntity(entityKey, getServiceDefault(serviceObj, entityKey), getServiceMapper(serviceObj, entityKey).getInnerMap()));
		});
		System.out.println(JSON.toJSONString(ws));
		return ws;
	}
	
	/**
	 * 
	 * @Title: 获取服务对象中的映射内容
	 * @author Chen Shuo
	 * @date:  2020年7月29日 上午9:07:09
	 * @Description
	 * @param serviceObj
	 * @param entityKey
	 * @return
	 */
	private static JSONObject getServiceMapper(JSONObject serviceObj,String entityKey) {
		JSONObject obj = null;
		try {
			obj = serviceObj.getJSONObject(entityKey).getJSONObject("mapper");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	/**
	 * 
	 * @Title: 获取服务对象中的默认值
	 * @author Chen Shuo
	 * @date:  2020年7月29日 上午9:07:09
	 * @Description
	 * @param serviceObj
	 * @param entityKey
	 * @return
	 */
	private static Map<String, Object> getServiceDefault(JSONObject serviceObj,String mappername) {
		JSONObject obj = null;
		try {
			obj = serviceObj.getJSONObject(mappername).getJSONObject("default");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj.getInnerMap();
	}
	
	private void syncWbs(List<Map<String,Object>> wbsObjectList) {
		JSONObject root = loadMapperFile();
		if(root==null) {
			return;
		}
	}
}
