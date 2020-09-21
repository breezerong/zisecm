package com.ecm.portal.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.DateUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.MailService;

@Service
public class PackageService {
	
	private final Logger logger = LoggerFactory.getLogger(PackageService.class);
	
	
	@Autowired
    private DocumentService documentService;
	
	public String createPackage(String token, List<String> idList) throws IOException {
		List<Map<String,Object>> docList = new ArrayList<Map<String,Object>>();
		for(String id: idList) {
			EcmDocument doc = documentService.getObjectById(token, id);
			if(doc != null) {
				Map<String,Object> mp = new HashMap<String, Object>();
				EcmForm frm = CacheManagerOper.getEcmForms().get(doc.getTypeName() + "_EDIT");
				if (frm == null) {
					frm = CacheManagerOper.getEcmForms().get(doc.getTypeName() + "_1");
				}
				if(frm != null) {
					mp.put("TYPE_NAME", doc.getTypeName());
					for(EcmFormItem item :frm.getEcmFormItems()) {
						mp.put(item.getAttrName(), doc.getAttributeValue(item.getAttrName()));
					}
					docList.add(mp);
				}
			}
		}
		return writeJsonFile(docList);
	}
	
	public void importPackage(String token,String fileName,String folderId) {
		Path filePath = Paths.get("", fileName);
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(filePath.toFile());
			importPackage(token, in, folderId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(in !=null) {
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	public void importPackage(String token, InputStream in,String folderId) throws Exception {
		String jsonString = IOUtils.toString(in, StandardCharsets.UTF_8);
		List<String> docList = JSON.parseArray(jsonString, String.class);
		for(String str: docList) {
			JSONObject  jsonObject = JSONObject.parseObject(str);
			Map<String,Object> map = (Map<String,Object>)jsonObject;
			EcmDocument doc =new EcmDocument();
			doc.setAttributes(map);
			doc.setFolderId(folderId);
			documentService.newObject(token, doc, null);
		}
	}

	public String getFolderPath() {
		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if (!uploadFolder.endsWith(File.separator)) {
			uploadFolder += File.separator;
		}
		return uploadFolder;
	}
	
	public String readJsonFile(String filePath) {
		Path ConfPath = Paths.get("", filePath);
		String jsonString = "";
		try (InputStream in = FileUtils.openInputStream(ConfPath.toFile())) {
			jsonString = IOUtils.toString(in, StandardCharsets.UTF_8);
		} catch (Exception e) {
			logger.error("读取文件失败{}", ConfPath.toAbsolutePath(), e);
		}
		return jsonString;
	}

	public String writeJsonFile(Object obj) throws IOException {

		String jsonString = JSONObject.toJSONString(obj, true);
		Path filePath = Paths.get(getFolderPath() + DateUtils.DateToStr(new Date(),"yyyyMMddHHmmss")+".json");
		Files.write(filePath, jsonString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
		return filePath.toFile().getPath();
	}
}
