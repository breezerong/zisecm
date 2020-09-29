package com.ecm.cnpe.exchange.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecm.core.ActionContext;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController
public class PlantMnanager extends ControllerAbstract {

	@Autowired
	private DocumentService documentService;

	private final String columns = "C_PROJECT_CODE projectCode,C_PROJECT_NAME projectName,C_DESIGN_UNIT designUnit,CODING coding,TITLE title," 
			+ "C_WBS_CODING wbs,NAME name,C_ITEM1_DATE startDate,C_ITEM2_DATE endDate," 
			+ "C_IN_CODING pid,C_OTHER_CODING pre,C_REF_CODING nextTask,"
			+ "C_TYPE1 designType,MODIFIED_DATE dataTime,C_ITEM3_DATE realStartDate," 
			+ "C_ITEM4_DATE realEndDate,C_DOUBLE1 finishPercentage,C_COUNT1 needDay,C_TYPE1 plantLevel,SYN_ID id";

	@PostMapping("/exchange/plant/list")
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select " + columns + ",(select count(*) from ecm_document sed where sed.C_IN_CODING = ed.SYN_ID) as childrenCount from ecm_document ed where ed.type_name='计划任务'");
		String startDate = "";
		String endDate = "";
		if(params.containsKey("id")) {
			sql.append(" and C_IN_CODING='"+params.get("id").toString()+"'");
		}else {
			if(params.containsKey("condition")) {
				sql.append(" and "+params.get("condition").toString());
				startDate= getStartDate(params.get("condition").toString());
				endDate = getEndDate(params.get("condition").toString());
			}else {
				startDate= getStartDate("");
				endDate = getEndDate("");
			}
			sql.append(" and (C_IN_CODING is null or C_IN_CODING='')");
		}
		
		
		List<Map<String, Object>> list = null;
		try {
			String fullSQL = "select *,'hasChildren'=case when childrenCount>0 then 1 else 0 end from ("+ sql.toString()+") f";
			list = documentService.getMapList(getToken(), fullSQL);
		} catch (EcmException e) {
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		result.put("data", list);
		result.put("startDate", startDate);
		result.put("endDate", endDate);
		result.put("code", ActionContext.SUCESS);
		return result;
	}
	
	private String getStartDate(String condition) {
		StringBuffer sql = new StringBuffer("select top 1 C_ITEM1_DATE startDate from ecm_document where TYPE_NAME='计划任务' and C_ITEM1_DATE is not null  ");
		if(!StringUtils.isEmpty(condition)) {
			sql.append(" and ");
			sql.append(condition);
		}
		sql.append(" order by  startDate");
		List<Map<String, Object>> list = null;
		try {
			list = documentService.getMapList(getToken(), sql.toString());
		} catch (EcmException | AccessDeniedException e) {
			e.printStackTrace();
		}
		
		if(list!=null && list.size()>0) {
			return list.get(0).get("startDate").toString();
		}
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.DAY_OF_MONTH,-5);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(calender.getTime());
	}
	
	private String getEndDate(String condition) {
		StringBuffer sql = new StringBuffer("select top 1 C_ITEM2_DATE endDate from ecm_document where TYPE_NAME='计划任务'");
		if(!StringUtils.isEmpty(condition)) {
			sql.append(" and ");
			sql.append(condition);
		}
		sql.append(" order by  endDate desc");
		List<Map<String, Object>> list = null;
		try {
			list = documentService.getMapList(getToken(), sql.toString());
		} catch (EcmException | AccessDeniedException e) {
			e.printStackTrace();
		}
		
		if(list!=null && list.size()>0) {
			return list.get(0).get("endDate").toString();
		}
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.DAY_OF_MONTH,-5);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(calender.getTime());
	}

}
