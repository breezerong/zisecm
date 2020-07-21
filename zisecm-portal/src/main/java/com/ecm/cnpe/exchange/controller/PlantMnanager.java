package com.ecm.cnpe.exchange.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			+ "C_IN_CODING parentTask,C_OTHER_CODING predecessorTask,C_REF_CODING nextTask,"
			+ "C_TYPE1 designType,MODIFIED_DATE dataTime,C_ITEM3_DATE actualStartDate," 
			+ "C_ITEM4_DATE actualEndDate,C_DOUBLE1 finishPercentage,C_COUNT1 needDay,C_TYPE1 plantLevel,SYN_ID id";

	@PostMapping("/exchange/plant/list")
	@ResponseBody
	public Map<String, Object> list(@RequestBody Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select " + columns + ",(select count(*) from ecm_document sed where sed.C_IN_CODING = ed.SYN_ID) as childrenCount from ecm_document ed where ed.type_name='计划任务'");
		if(params.containsKey("id")) {
			sql.append(" and C_IN_CODING='"+params.get("id").toString()+"'");
		}else {
			sql.append(" and (C_IN_CODING is null or C_IN_CODING='')");
		}
		
		if(params.containsKey("condition")) {
			sql.append(" and "+params.get("condition").toString());
		}
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			System.out.println(sql.toString());
			list = documentService.getMapList(getToken(), sql.toString());
			for (Map<String, Object> map : list) {
				int count = Integer.valueOf(map.get("childrenCount").toString()).intValue();
				map.put("hasChildren", count>0);
				resultList.add(map);
			}
		} catch (EcmException e) {
			e.printStackTrace();
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
		result.put("data", resultList);
		result.put("code", ActionContext.SUCESS);
		return result;
	}

}
