package com.ecm.core.sync;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	@Autowired
	IExcSynDetailService iExcSynDetailService;
	@Autowired
	private EcmContentMapper ecmContentMapper;
	@Autowired
	private EcmDocumentMapper ecmDocumentMapper;
	@Autowired
	ExcSynDetailMapper excSynDetailMapper;
	String folderName = "";

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
	 * 
	 * @throws Exception
	 */
	public boolean exportData() throws Exception {
//		List<ExcSynDetail> excSynDetailObjList = readExcSynDetail();
		List<ExcSynDetail> excSynDetailObjList = new ArrayList<ExcSynDetail>();
		List<EcmDocument> ecmDocumentObjList = new ArrayList<EcmDocument>();
		EcmDocument en = ecmDocumentMapper.selectByPrimaryKey("ef993cc172fe46c9b752fb0cf1c52cdc");
		EcmDocument en2 = ecmDocumentMapper.selectByPrimaryKey("ef993cc172fe46c9b752fb0cf1c52cdc");
		en2.setId("ef993cc172fe46c9b752fb0cf1c52cdc_1");
		ecmDocumentObjList.add(en);
		ecmDocumentObjList.add(en2);
		List<EcmDocument> resultObjList = new ArrayList<EcmDocument>();
		folderName = getFolderName();
		for (int i = 0; i < ecmDocumentObjList.size(); i++) {
			exportFile(ecmDocumentObjList.get(i), getSyncPathPublic() + "/" + folderName + "/");
			resultObjList.add(ecmDocumentObjList.get(i));
		}
		writeJsonFile(resultObjList, folderName + ".json");
		updateExcSynDetailStatus(excSynDetailObjList, "已导出", new Date());
		String zipFileName = folderName + ".zip";
		String abusoluteFolderPath = getSyncPathPublic() + "/" + folderName;
		ZipUtil.zip(abusoluteFolderPath, abusoluteFolderPath+"/"+zipFileName);
		writeMD5Info(zipFileName);
		return false;
	}

	/**
	 * @param zipFileName
	 * @throws IOException
	 */
	private void writeMD5Info(String zipFileName) throws IOException {
		String md5 = generateZipFileMD5(zipFileName);
		String md5FileName = zipFileName + ".MD5.txt";
		FileWriter fw = new FileWriter(getSyncPathPublic() + "/" + md5FileName, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(md5 + "\n"); // 字符串末尾不需要换行符
		pw.close();
		fw.close();
	}

	/**
	 * 1.2.2
	 * 
	 * @param folderPath
	 * @throws Exception
	 */
	private void exportFile(EcmDocument obj, String folderPath) throws Exception {
		List<EcmContent> list = ecmContentMapper.getContents(obj.getId(), 1);
		EcmContent en = null;
		if (list.size() > 0) {
			en = list.get(0);
			InputStream in = getContentStream(en);
			String fileName = folderPath + generateFileNamePrefix(obj.getId(), en, "MasterFile");
			FileUtils.copyInputStreamToFile(in, new File(fileName));
		}
		TODOApplication.getNeedTOChange("导出主文件时需要导出关联关系");
	}

	/**
	 * 1.2.3
	 * 
	 */
	public boolean updateExcSynDetailStatus(List<ExcSynDetail> objList, String status, Date updateDate) {
		ExcSynDetail en = new ExcSynDetail();
		en.setId("excsyncdetailtest01");
		en.setStauts(status);
		en.setExportDate(updateDate);
		excSynDetailMapper.updateByPrimaryKey(en);
		return false;
	}

	/**
	 * 1.3.取内网返回结果
	 */
	public EcmDocument readJsonResult(String fileName) {
		List<EcmDocument> objList = JSON.parseArray(readJsonFile(fileName), EcmDocument.class);
		EcmDocument en = objList.get(0);
		return en;
	}

	/**
	 * 1.4.将结果更新到外网系统
	 */
	public boolean updateExcSynDetail() {
		//
		updateExcSynDetailStatus(null, "已同步", new Date());
		return false;
	}

	/**
	 * 2.1从指定目录导入数据到当前系统
	 */
	@Override
	public boolean importData() {
		File fileDirectory = new File(getSyncPathPrivate());
		List<File> folderList = new ArrayList<File>();
		for (File temp : fileDirectory.listFiles()) {
			if (temp.isDirectory() && !temp.getName().startsWith("DONE_")) {
				folderList.add(temp);
			}
		}
		TODOApplication.getNeedTOChange("导入文件时，需要根据情况导入文件及相关关系");
		ecmDocumentMapper
				.insert(readJsonResult(folderList.get(0).toString() + "/" + folderList.get(0).getName() + ".json"));

		writeJsonResult(folderList.get(0), "DONE_");
		return false;
	}

	/**
	 * 2.2从指定目录导入数据到当前系统后，将结果信息文件到指定目录 完成DONE_ 错误ERR_
	 */
	public boolean writeJsonResult(File folder, String result) {
		folder.renameTo(new File(getSyncPathPublic() + "/" + result + folder.getName()));
		return false;
	}

	private String generateFileNamePrefix(String id, EcmContent obj, String relationType) {
		String fileName = obj.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(id).append("_").append(relationType).append("_").append(fileName);
		return sb.toString();
	}

	private String getSyncPathPublic() {
		String appPath = SysConfig.getSyncPathPublic();
		return appPath;
	}

	private String getSyncPathPrivate() {
		String appPath = SysConfig.getSyncPathPrivate();
		return appPath;
	}

	private String readJsonFile(String filePath) {
		Path ConfPath = Paths.get("", filePath);
		byte[] bytes = new byte[] {};
		try {
			bytes = Files.readAllBytes(ConfPath);
		} catch (Exception e) {
			logger.error("读取文件失败{}", ConfPath.toAbsolutePath(), e);
		}

		String jsonString = new String(bytes);
		return jsonString;
	}

	private void writeJsonFile(Object obj, String fileName) throws IOException {

		String jsonString = JSONObject.toJSONString(obj, true);
		Path ConfPath = Paths.get(getSyncPathPublic() + "/" + folderName, fileName);
		;
		if (!Files.exists(ConfPath))
			Files.createFile(ConfPath);
		Files.write(ConfPath, jsonString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
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
		if (list.size() > 0) {
			EcmContent en = list.get(0);
			String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			return storePath + en.getFilePath();
		}
		return null;
	}

	public String getFolderName() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(dt);
	}

	private String generateZipFileMD5(String filePath) throws IOException {
		String md5 = "";
		try (FileInputStream fis = new FileInputStream(filePath);) {
			md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
		}
		return md5;

	}

}
