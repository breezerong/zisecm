package com.ecm.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.core.ActionContext;
import com.ecm.core.entity.ChartBean;
import com.ecm.core.service.ReportService;
/**
 * 报表服务
 * @author Haihong Rong
 * @date 2019年7月22日 上午8:51:18
 */
@Controller
public class ReportController extends ControllerAbstract{

	@Autowired
	private ReportService reportService;
	
	/**
	 * 系统基本数据
	 * @param argStr
	 * @return
	 */
	@RequestMapping("/report/systemData")
	@ResponseBody
	public Map<String, Object> startWorkflow() {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			ChartBean bean = new ChartBean();
			bean.getxAxisData().add("用户");
			bean.getyAxisData().add(reportService.getTableCount(getToken(),"ecm_user", null));
			bean.getxAxisData().add("组");
			bean.getyAxisData().add(reportService.getTableCount(getToken(),"ecm_group", null));
			bean.getxAxisData().add("类型");
			bean.getyAxisData().add(reportService.getTableCount(getToken(),"ecm_def_type", null));
			bean.getxAxisData().add("流程");
			bean.getyAxisData().add(reportService.getTableCount(getToken(),"ecm_def_process", null));
			bean.getxAxisData().add("文件");
			bean.getyAxisData().add(reportService.getTableCount(getToken(),"ecm_document", null));
			mp.put("data", bean);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	/**
	 * 用户登录数据
	 * @param argStr
	 * @return
	 */
	@RequestMapping("/report/userLoginData")
	@ResponseBody
	public Map<String, Object> userLoginData() {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", reportService.getLastYearUserLoginData(getToken()));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	/**
	 * 最近一年文档数据
	 * @param argStr
	 * @return
	 */
	@RequestMapping("/report/lastYearDocData")
	@ResponseBody
	public Map<String, Object> lastYearDocData() {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", reportService.getLastYearDocData(getToken()));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	/**
	 * 类型文档数据
	 * @param argStr
	 * @return
	 */
	@RequestMapping("/report/docTypeData")
	@ResponseBody
	public Map<String, Object> docTypeData() {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", reportService.getDocumentTypeData(getToken()));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	/**
	 * 图纸项目数据
	 * @return
	 */
	@RequestMapping("/report/drawingPorjectData")
	@ResponseBody
	public Map<String, Object> drawingPorjectData() {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", reportService.getDrawingPorjectData(getToken()));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	/**
	 * 项目日期数据
	 * @return
	 */
	@RequestMapping("/report/drawingtDateData")
	@ResponseBody
	public Map<String, Object> drawingDateData(@RequestBody String name) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", reportService.getDrawingDateData(getToken(),name));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	/**
	 * 项目状态数据
	 * @return
	 */
	@RequestMapping("/report/drawingStatusData")
	@ResponseBody
	public Map<String, Object> drawingStatusData(@RequestBody String name) {
		
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			mp.put("data", reportService.getDrawingStatusData(getToken(),name));
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
}
