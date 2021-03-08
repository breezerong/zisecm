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
import org.apache.commons.lang.StringUtils;
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
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.MailService;
import com.ecm.portal.entity.AttrCopyCfgEntity;

@Service
public class CustomCacheService {
	
	private final Logger logger = LoggerFactory.getLogger(CustomCacheService.class);
	
	private final String copyCacheName = "AttributeCopyCfg";
	
	@Autowired
    private DocumentService documentService;
	
	/**
	 * 获取复制配置
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-12-22 7:57:16 
	 * @Description:       
	 * @param token
	 * @param fromType 源对象类型
	 * @return
	 */
	public AttrCopyCfgEntity getAttrCopyCfg(String token,String fromType,Boolean sameType) {
		if(CacheManagerOper.getCustomCache().get(copyCacheName)==null) {
			initAttrCopyCache(token);
		}
		List<AttrCopyCfgEntity> clist = (List<AttrCopyCfgEntity>)CacheManagerOper.getCustomCache().get(copyCacheName);
		//如果复制与被复制文件类型相同
		if(sameType) {
			for(AttrCopyCfgEntity en: clist) {
				if(en.getFromType().equals(fromType)&&en.getToType().equals(fromType)) {
					return en;
				}
			}
		}
		//如果复制与被复制文件类型不同
		else {
			for(AttrCopyCfgEntity en: clist) {
				if(en.getFromType().equals(fromType)&&!en.getToType().equals(fromType)) {
					return en;
				}
			}
		}
		
		return null;
	}
	
	public AttrCopyCfgEntity getAttrCopyCfgFromChild(String token,String childType) {
		if(CacheManagerOper.getCustomCache().get(copyCacheName)==null) {
			initAttrCopyCache(token);
		}
		List<AttrCopyCfgEntity> clist = (List<AttrCopyCfgEntity>)CacheManagerOper.getCustomCache().get(copyCacheName);
		
			for(AttrCopyCfgEntity en: clist) {
				if(!en.getFromType().equals(childType)&&en.getToType().equals(childType)) {
					return en;
				}
			}
		
		return null;
	}
	
	
	/**
	 * 初始化回复配置缓存
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-9-20 17:21:26 
	 * @Description:       
	 * @param token
	 */
	private void initAttrCopyCache(String token) {
		String sql  = "select ID,C_FROM,C_TO,ITEM_CONTENT,C_COMMENT,C_ITEM_STATUS,STATUS from ecm_document where TYPE_NAME='属性复制配置'";
		List<AttrCopyCfgEntity> clist = new ArrayList<AttrCopyCfgEntity>();
		try {
			List<Map<String, Object>> list = documentService.getMapList(token, sql);
			for(Map<String, Object> mp: list) {
				AttrCopyCfgEntity en = new AttrCopyCfgEntity();
				en.setFromType(mp.get("C_FROM").toString());
				en.setToType(mp.get("C_TO").toString());
				en.setEnabled(mp.get("STATUS").toString().equals("是"));
				en.setDescription((String)mp.get("C_COMMENT"));
				String policy = (String)mp.get("ITEM_CONTENT");
				if(policy !=null && policy.length()>0) {
					policy = policy.trim();
					String[] attrs = policy.split(";");
					for(String str : attrs) {
						if(!StringUtils.isEmpty(str)) {
							String[]  strs = str.split(":");
							if(strs.length>1) {
								// 配置中为 源属性：目标属性，需要转换成key目标属性，value源属性
								en.getAttrNames().put(strs[1], strs[0]);
							}else {
								en.getAttrNames().put(str, str);
							}
						}
					}
					
				}
				clist.add(en);
			}
		} catch (EcmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CacheManagerOper.getCustomCache().put(copyCacheName, clist);
	}
}
