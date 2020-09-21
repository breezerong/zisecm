package com.ecm.portal.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.AuditContext;
import com.ecm.core.entity.EcmAction;
import com.ecm.core.service.ActionService;
import com.ecm.portal.controller.ControllerAbstract;
import com.ecm.portal.log.LogAopAction;
import com.ecm.portal.service.PackageService;

/**
 * 
 * @author Administrator 事件管理控制器
 */
@Controller
public class PackageManager extends ControllerAbstract{
	
	private final Logger logger = LoggerFactory.getLogger(PackageManager.class);

	@Autowired
	PackageService packageService;

	/**
	 * 获取所有事件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="/admin/getPackage", method = RequestMethod.POST)
	public void getPackage(@RequestBody String argStr,HttpServletRequest request, HttpServletResponse response) {
		List<String> list = JSONUtils.stringToArray(argStr);
		InputStream iStream = null;
		File f = null;
		try {
			
			String fileName =  packageService.createPackage(getToken(), list);
			f = new File(fileName);
			Path filePath = Paths.get("", fileName);
			iStream = FileUtils.openInputStream(filePath.toFile());
		
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(f.getName(), "UTF-8"));
			response.addHeader("Content-Length", "" + f.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			try {
				response.setContentType("application/octet-stream");
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = iStream.read(buffer)) != -1) {
					toClient.write(buffer, 0, bytesRead);
				}
			} finally {
				iStream.close();
				toClient.flush();
				toClient.close();
				f.delete();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * 更新事件
	 * 
	 * @param obj 事件jason对象
	 * @return  
	 */
	@RequestMapping(value = "/admin/importPackage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPackage(@RequestParam("folderId")String folderId,@RequestParam("jsonFile") MultipartFile file) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			packageService.importPackage(getToken(), file.getInputStream(), folderId);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}

		return mp;
	}

	
}
