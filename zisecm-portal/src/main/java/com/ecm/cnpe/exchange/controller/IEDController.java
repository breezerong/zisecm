package com.ecm.cnpe.exchange.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.cnpe.exchange.controller.param.DocParam;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.LoginUser;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.controller.ControllerAbstract;

@RestController

public class IEDController  extends ControllerAbstract  {
	
	@Autowired
	private DocumentService documentService;
	
	@RequestMapping(value = "/exchange/ied/newIED", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> newIED(String metaData, MultipartFile uploadFile) throws Exception {
		Map<String, Object> args = JSONUtils.stringToMap(metaData);
		EcmContent en = null;
		EcmDocument doc = new EcmDocument();
		doc.setAttributes(args);
		if (uploadFile != null) {
			en = new EcmContent();
			en.setName(uploadFile.getOriginalFilename());
			en.setContentSize(uploadFile.getSize());
			en.setFormatName(FileUtils.getExtention(uploadFile.getOriginalFilename()));
			en.setInputStream(uploadFile.getInputStream());
		}
		String id = documentService.newObject(getToken(), doc, en);
		
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("code", ActionContext.SUCESS);
		mp.put("id", id);
		return mp;
	}
	
	/**
	 * 
	 * @Title: changeIED
	 * @author lsyl
	 * @date:  2020年7月17日 上午10:39:19
	 * @Description IED变更
	 * @param ids
	 * @return
	 */
	@PostMapping("/exchange/ied/changeIED")
	@ResponseBody
	public Map<String, Object> changeIED(@RequestBody List<String> ids){
		Map<String, Object> result = new HashMap<String, Object>();		
		for (String id : ids) {
			try {
				EcmDocument docObj = documentService.getObjectById(getToken(), id);
				EcmDocument checkInDoc = documentService.checkIn(getToken(), id, null, docObj.isCurrent());
				documentService.updateStatus(getToken(), id, "变更中");		
     			checkInDoc.addAttribute("C_ITEM_STATUS1", "修订");
				checkInDoc.addAttribute("C_IS_RELEASED", 0);
				checkInDoc.setCurrent(false);
				documentService.updateObject(getToken(), checkInDoc, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("code", ActionContext.SUCESS);
		return result;		
	}
}
