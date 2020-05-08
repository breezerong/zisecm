package com.ecm.portal.archive.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecm.common.util.JSONUtils;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.QueryService;
import com.ecm.portal.controller.ControllerAbstract;

@Controller
public class ArchiveReportController extends ControllerAbstract {

	@Autowired
	private QueryService queryService;

	@Autowired
	private FolderService folderService;

	/**
	 * 馆藏统计报表
	 * 
	 * @return
	 */
	@PostMapping("/report/getCollectionData")
	@ResponseBody
	public Map<String, Object> getCollectionData(@RequestBody String argStr) {
		Map<String, Object> data = new HashMap<>();
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			list = folderService.getFoldersByParentPath(getToken(), "/档案库");
			for (EcmFolder ecmFolder : list) {
				Map<String, Object> countMap = new HashMap<>();
				String countSql = "";
				countSql = "select C_ARC_CLASSIC as fileType, "
						+ "SUM(TYPE_NAME = '卷盒' and STATUS = '利用') as boxNum, "
						+ "SUM(TYPE_NAME = '图册' and STATUS = '利用') as atlasNum, "
						+ "SUM(TYPE_NAME <> '图册' and TYPE_NAME <> '卷盒' and STATUS = '利用') as fileNum "
						+ "from ecm_document where C_ARC_CLASSIC = '" + ecmFolder.getName() + "' "
						+ "and ((C_ARCHIVE_DATE is null and C_DOC_DATE BETWEEN '"+args.get("firstTime")+"' "
						+ "and '"+args.get("endTime")+"') or (C_ARCHIVE_DATE is not null and C_ARCHIVE_DATE BETWEEN '"+args.get("firstTime")+"' and '"+args.get("endTime")+"'))";
				List<Map<String, Object>> countList = queryService.executeSQL(getToken(), countSql);
				if (countList.get(0)!= null) {
					for (Map<String, Object> count : countList) {
						countMap.put("typeName", ecmFolder.getName());
						countMap.put("boxNum", count.get("boxNum"));
						countMap.put("atlasNum", count.get("atlasNum"));
						countMap.put("fileNum", count.get("fileNum"));
					}
				} else {
					countMap.put("typeName", ecmFolder.getName());
					countMap.put("boxNum", 0);
					countMap.put("atlasNum", 0);
					countMap.put("fileNum", 0);
				}
				result.add(countMap);
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.put("data", result);
		return data;
	}
	
	/**
	 * 工作量统计报表
	 * 
	 * @return
	 */
	@PostMapping("/report/getWorkloadData")
	@ResponseBody
	public Map<String, Object> getWorkloadData(@RequestBody String argStr) {
		Map<String, Object> data = new HashMap<>();
		List<EcmFolder> list = new ArrayList<EcmFolder>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		try {
			String usersNameSql = "SELECT DISTINCT C_INSTORE_USER as users FROM ecm_document where C_INSTORE_USER is not null";
			List<Map<String, Object>> usersName = queryService.executeSQL(getToken(), usersNameSql);
			list = folderService.getFoldersByParentPath(getToken(), "/档案库");
			for (Map<String, Object> userName : usersName) {
				String user = (String) userName.get("users");
				for (EcmFolder ecmFolder : list) {
					Map<String, Object> countMap = new HashMap<>();
					String countSql = "";
					countSql = "select C_ARC_CLASSIC as fileType,"
							+ "SUM(TYPE_NAME = '卷盒' and STATUS = '利用') as boxNum, "
							+ "SUM(TYPE_NAME = '图册' and STATUS = '利用') as atlasNum, "
							+ "SUM(TYPE_NAME <> '图册' and TYPE_NAME <> '卷盒' and STATUS = '利用') as fileNum "
							+ "from ecm_document where C_ARC_CLASSIC = '"+ecmFolder.getName()+"' AND C_INSTORE_USER='"+user+"'  and C_INSTORE_DATE BETWEEN "
							+ "'"+args.get("firstTime")+"' and '"+args.get("endTime")+"'";
					List<Map<String, Object>> countList = queryService.executeSQL(getToken(), countSql);
					if (countList.get(0)!= null) {
						for (Map<String, Object> count : countList) {
							countMap.put("userName", user);
							countMap.put("typeName", ecmFolder.getName());
							if (count.get("boxNum")==null) {
								countMap.put("boxNum", 0);
							}else {
								countMap.put("boxNum", count.get("boxNum"));
							}
							if (count.get("atlasNum")==null) {
								countMap.put("atlasNum", 0);
							}else {
								countMap.put("atlasNum", count.get("atlasNum"));
							}
							if (count.get("fileNum")==null) {
								countMap.put("fileNum", 0);
							}else {
								countMap.put("fileNum", count.get("fileNum"));
							}
						}
					} else {
						countMap.put("userName", user);
						countMap.put("typeName", ecmFolder.getName());
						countMap.put("boxNum", 0);
						countMap.put("atlasNum", 0);
						countMap.put("fileNum", 0);
					}
					if (Integer.parseInt(countMap.get("boxNum").toString())!=0&&Integer.parseInt(countMap.get("boxNum").toString())!=0&&Integer.parseInt(countMap.get("boxNum").toString())!=0) {
						result.add(countMap);
					}
				}
			}
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.put("data", result);
		return data;
	}
	
	/**
	  * 利用统计报表
	 * 
	 * @return
	 */
	@PostMapping("/report/getBrrowData")
	@ResponseBody
	public Map<String, Object> getBrrowData(@RequestBody String argStr) {
		Map<String, Object> data = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String brrowTypeSql[] = {"在线浏览","纸质借阅","下载"};
		for (String brrowType : brrowTypeSql) {
			Map<String, Object> countMap = new HashMap<>();
			countMap.put("brrowType", brrowType);
			String brrowCountSql = "";
			String brrowIdSql = "";
				brrowCountSql = "SELECT COUNT(*) as subCount FROM ecm_document "
						+ "where TYPE_NAME = '借阅单' and STATUS = '已完成' and SUB_TYPE = '"+brrowType+"' and CREATION_DATE BETWEEN "
						+ "'"+args.get("firstTime")+"' and '"+args.get("endTime")+"'";
				brrowIdSql = "SELECT id as parentId FROM ecm_document "
						+ "where TYPE_NAME = '借阅单' and STATUS = '已完成' and SUB_TYPE = '"+brrowType+"' and CREATION_DATE BETWEEN "
						+ "'"+args.get("firstTime")+"' and '"+args.get("endTime")+"'";
			try {
				List<Map<String, Object>> brrowCount = queryService.executeSQL(getToken(), brrowCountSql);
				List<Map<String, Object>> brrowIds = queryService.executeSQL(getToken(), brrowIdSql);
				countMap.put("subCount",brrowCount.get(0).get("subCount"));
				long countNum = 0;
				for (Map<String, Object> id : brrowIds) {
					String getChildCountSql = "select COUNT(*) as count FROM ecm_relation where parent_id = '"+id.get("parentId")+"'";
					List<Map<String, Object>> childCount = queryService.executeSQL(getToken(), getChildCountSql);
					long flag =  (long) childCount.get(0).get("count");
					countNum = countNum + flag;
				}
				countMap.put("subChildCount", countNum);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(countMap);
		}
		data.put("data", result);
		return data;
	}
}
