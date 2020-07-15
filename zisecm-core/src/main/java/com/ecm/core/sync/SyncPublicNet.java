package com.ecm.core.sync;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.dao.ExcSynDetailMapper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.ExcSynDetail;
import com.ecm.core.util.SysConfig;
import com.ecm.icore.service.IExcSynDetailService;

@Service
public class SyncPublicNet implements ISyncPublicNet {
	private final Logger logger = LoggerFactory.getLogger(SyncPublicNet.class);

	@Autowired
	IExcSynDetailService iExcSynDetailService;
	@Autowired
	private EcmContentMapper ecmContentMapper;
	@Autowired
	private EcmDocumentMapper ecmDocumentMapper;
	@Autowired
	ExcSynDetailMapper excSynDetailMapper; 
	String folderName="";
	/**
	 * 1.1.读取需要同步的数据
	 */

	public List<ExcSynDetail> readExcSynDetail() {
		List<ExcSynDetail> excSynDetailObjList = iExcSynDetailService
				.selectByCondition(" APP_NAME='DOCEX' and ACTION_NAME in('提交')  and status='新建' ");
		return excSynDetailObjList;

	}

	/**
	 * 1.2.1.导出需要同步的数据到指定目录
	 * @throws Exception 
	 */
	public boolean exportData() throws Exception {
//		List<ExcSynDetail> excSynDetailObjList = readExcSynDetail();
		List<ExcSynDetail> excSynDetailObjList = new ArrayList<ExcSynDetail>();
		List<EcmDocument> ecmDocumentObjList = new ArrayList<EcmDocument>();
		EcmDocument en = ecmDocumentMapper.selectByPrimaryKey("ef993cc172fe46c9b752fb0cf1c52cdc");
		ecmDocumentObjList.add(en);
		List<EcmDocument> resultObjList = new ArrayList<EcmDocument>();
		folderName=getFolderName();
		for (int i = 0; i < ecmDocumentObjList.size(); i++) {
			exportFile(ecmDocumentObjList.get(i), getConfPath()+"/"+folderName);
			resultObjList.add(ecmDocumentObjList.get(i));
		}
		String jsonStr = JSON.toJSONString(resultObjList);
		writeJsonFile(jsonStr, "a.json");
		updateExcSynDetailStatus(excSynDetailObjList, "已导出", new Date());
		ZipUtil.zip(getConfPath()+"/"+folderName, getConfPath()+"/"+folderName+".zip");
		return false;
	}

	/**
	 * 1.2.2
	 * 
	 * @param folderPath
	 * @throws Exception
	 */
	private void exportFile(EcmDocument obj, String folderPath) throws Exception {
		List<EcmContent> list = ecmContentMapper.getContents(obj.getId(), 1);
		if(list.size()>0) {
			EcmContent en = list.get(0);
			
		//InputStream in = getContentStream(en);
		InputStream in = new FileInputStream(getConfPath()+"/1.pdf");
		String fileName = folderPath+"/test.pdf";
		FileUtils.copyInputStreamToFile(in, new File(fileName));
		}
	}

	/**
	 * 1.2.3
	 * 
	 */
	public boolean updateExcSynDetailStatus(List<ExcSynDetail> objList, String status, Date updateDate) {	
		ExcSynDetail en= new ExcSynDetail();
				en.setId("excsyncdetailtest01");
				en.setStauts(status);
				en.setExportDate(updateDate);
		excSynDetailMapper.updateByPrimaryKey(en);
		return false;
	}

	/**
	 * 1.3.取内网返回结果
	 */
	public boolean readJsonResult() {
		return false;
	}

	/**
	 * 1.4.将结果更新到外网系统
	 */
	public boolean updateExcSynDetail() {
		return false;
	}

	/**
	 * 2.1从指定目录导入数据到当前系统
	 */
	public boolean importData() {
		return false;
	}

	/**
	 * 2.2从指定目录导入数据到当前系统后，将结果信息文件到指定目录
	 */
	public boolean writeJsonResult() {
		return false;
	}

	private String getConfPath() {
		String appPath = SysConfig.getSyncPath();
		return appPath;
	}

	private String readJsonFile(String fileName) {
		Path ConfPath = Paths.get(getConfPath(),fileName);
		byte[] bytes = new byte[] {};
		try {
			bytes = Files.readAllBytes(ConfPath);
		} catch (Exception e) {
			logger.error("读取文件失败{}", ConfPath.toAbsolutePath(), e);
		}

		String jsonString = new String(bytes);

		return jsonString;
	}

	private void writeJsonFile(Object obj, String fileName) {

		String jsonString = JSONObject.toJSONString(obj, true);
		Path ConfPath = Paths.get(getConfPath(),folderName+"/"+fileName);;

		try {
			if (!Files.exists(ConfPath))
				Files.createFile(ConfPath);
		} catch (Exception e) {
			logger.error("创建文件失败{}", ConfPath.toAbsolutePath(), e);
		}

		try {
			Files.write(ConfPath, jsonString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
		} catch (Exception ex) {
			logger.error("写入配置文件失败{}", ConfPath.toAbsolutePath(), ex);
		}
	}

	public InputStream getContentStream(EcmContent en) throws Exception {
		// TODO Auto-generated method stub
		InputStream fis = null;
		String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		File f = new File(fullPath + en.getFilePath());
		en.setContentSize(f.length());
		fis = new BufferedInputStream(new FileInputStream(fullPath + en.getFilePath()));
		return fis;
	}
	
	public String getPrimaryFilePath(String objId) {
		// TODO Auto-generated method stub
		List<EcmContent> list = ecmContentMapper.getContents(objId, 1);
		if(list.size()>0) {
			EcmContent en = list.get(0);
			String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			return storePath+en.getFilePath();
		}
		return null;
	}
	

	public String getFolderName() {
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");  
    return sdf.format(dt);
	}
}
