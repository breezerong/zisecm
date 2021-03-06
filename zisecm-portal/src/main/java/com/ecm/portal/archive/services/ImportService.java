package com.ecm.portal.archive.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.CollectionUtil;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.common.util.FileUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmDocumentMapper;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmGridView;
import com.ecm.core.entity.EcmGridViewItem;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.EcmService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;
@Service
public class ImportService extends EcmService {
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private NumberService numberService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private FolderService folderService;
	
	private static String importExcelFolderId;
	
	private static String importDocFolderId;
	/**
	 * 批次导入
	 * @param token
	 * @param deliveryId 移交单编号
	 * @param excelSrcFile Excel文件
	 * @param files 电子文件
	 * @return 导入日志
	 * @throws Exception
	 */
	public String importExcel(String token,String deliveryId, MultipartFile  excelSrcFile,MultipartFile[] files) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sucessCount =0;
		int failedCount =0;
		sb.append("开始导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TYPE_NAME", "导入批次");
		String number = numberService.getNumber(token, map);
		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if(!uploadFolder.endsWith(File.separator))
		{
			uploadFolder += File.separator;
		}
		uploadFolder += getSession(token).getCurrentUser().getLoginName()+File.separator+number+File.separator;
		File f = new File(uploadFolder);
		f.mkdirs();
		ImportService.importDocFolderId = folderService.getObjectByPath(token, "/移交文档").getId();
		if(ImportService.importExcelFolderId==null) {
			ImportService.importExcelFolderId = folderService.getObjectByPath(token, "/表单/批量导入单").getId();
		}
		
		EcmDocument doc = new EcmDocument();
		doc.getAttributes().put("NAME", excelSrcFile.getOriginalFilename());
		doc.getAttributes().put("CODING", number);
		doc.setTypeName("导入批次");
		doc.setFolderId(importExcelFolderId);
		EcmContent content = new EcmContent();
		content.setName(excelSrcFile.getOriginalFilename());
		content.setContentSize(excelSrcFile.getSize());
		content.setFormatName(FileUtils.getExtention(excelSrcFile.getOriginalFilename()).toLowerCase());
		content.setInputStream(excelSrcFile.getInputStream());
		doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
		doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
		
		documentService.newObject(token, doc, content);
		
		excelSrcFile.getInputStream().close();
		
		Map<String,Long> fileList = new HashMap<String,Long>();
		//先存储文件到本地
		for(MultipartFile file: files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder +name;
			writeToLocal(itemPath,file.getInputStream());
			file.getInputStream().close();
			fileList.put(name,file.getSize());
		}
		
		String excelFile = content.getFilePath();
		String storePath = CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();
		
		
		//List<Object[]> rows = excelUtil.read(storePath+excelFile, 0);
		
		FileInputStream fis = new FileInputStream(storePath+excelFile);

		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(0);
		try {
			
			//第一行第一列为类型名称
			String parentType = sheet.getRow(0).getCell(0).getStringCellValue();
			//第一行第n列为子对象类型名称，没有不要填写值
			String childType = null;
			int childStartIndex =0;
			for(int i=1;i<=sheet.getRow(0).getLastCellNum();i++) {
				if(sheet.getRow(0).getCell(i)!=null && !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
					childType = sheet.getRow(0).getCell(i).getStringCellValue();
					childStartIndex = i;
					break;
				}
			}
			//第二行第一列为父子相同字段值定义，{父字段名称1}:{子字段名称1};{父字段名称2}:{子字段名称2}
			String sameFields = (sheet.getRow(1).getCell(0)!=null?sheet.getRow(1).getCell(0).getStringCellValue():"");
			Map<String,Object> sameValues = new HashMap<String,Object>();
			//第三行字段名称
			Map<Integer,String> attrNames = new HashMap<Integer,String>();
			for(int i=0;i<=sheet.getRow(2).getLastCellNum();i++) {
				if(sheet.getRow(2).getCell(i)!=null && !StringUtils.isEmpty(sheet.getRow(2).getCell(i).getStringCellValue())) {
					attrNames.put(i, sheet.getRow(2).getCell(i).getStringCellValue());
				}
			}
			String newId = null;
			boolean hasRendition = false;
			try{
				hasRendition = sheet.getRow(2).getCell(1).getStringCellValue().equalsIgnoreCase("reditionfile");
			}catch(Exception ex) {
				
			}
			
			//第四行为中文标签，第五行位值
			for (int i = 4; i <= sheet.getLastRowNum(); i++) {
				try {
					content = null;
					//存在子对象
					boolean isReuse = false;
					try {
						isReuse = sheet.getRow(i).getCell(getColumnIndex(attrNames, "DESIGN_REUSE",1,sheet.getLastRowNum())).getStringCellValue().equals("复用");
					}catch(Exception ex) {
						
					}
					if(childStartIndex>1) {
						
						//无格式副本2、3列至少一个不为空，有格式副本3、4不为空
						if((!hasRendition&&(!isEmptyCell(sheet.getRow(i).getCell(1))
								||!isEmptyCell(sheet.getRow(i).getCell(2)))
								||
								(hasRendition&&(!isEmptyCell(sheet.getRow(i).getCell(2))
										||!isEmptyCell(sheet.getRow(i).getCell(3))))
								)) {
		
								sameValues = new HashMap<String,Object>();
								newId = null;
								newId = newDocument( token, parentType,null, sheet.getRow(i),  fileList, attrNames,null,number, 1,childStartIndex-1,sameValues,sameFields);
								
								if(!StringUtils.isEmpty(newId)) {
									newRelation(token, deliveryId, newId, i,null);
								}
								
						}
						String fileName = getCellValue(sheet.getRow(i).getCell(0));
						if(fileName.indexOf(".")<0) {
							fileName += ".pdf";
						}
						String itemPath = uploadFolder +fileName;
					
						FileInputStream itemStream = null;
						if(!StringUtils.isEmpty(itemPath)) {
							File itemFile= new File(itemPath);
							
							if(itemFile.exists()) {
								itemStream = new FileInputStream(itemFile);
							}else {
								if(!isReuse) {
									sb.append("第").append(i+1).append("行文件不存在：").append(getCellValue(sheet.getRow(i).getCell(0))).append("\r\n");;
								}
							}
						}
						String tempId = null;
						try {
							tempId = newDocument( token, childType,itemStream, sheet.getRow(i),  fileList, attrNames,newId,number, childStartIndex,sheet.getRow(i).getLastCellNum(),sameValues,null);
							
							if(hasRendition) {
								String rendFileName = getCellValue(sheet.getRow(i).getCell(1));
								if(!StringUtils.isEmpty(rendFileName)) {
									if(FileUtils.getExtention(rendFileName).equalsIgnoreCase(FileUtils.getExtention(itemPath))) {
										sb.append("第").append(i+1).append("行主格式和副本扩展名相同.").append("\r\n");
									}else {
										itemPath = uploadFolder +rendFileName;
										File itemFile= new File(itemPath);
										
										if(itemFile.exists()) {
											FileInputStream rs =null;
											try {
												rs = new FileInputStream(itemFile);
												content = new EcmContent();
												content.setName(rendFileName);
												content.setContentSize(fileList.get(content.getName()));
												content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
												content.setInputStream(rs);
												documentService.addRendition(token, tempId, content);
											}
											finally {
												if(rs!=null) {
													rs.close();
													itemFile.delete();
												}
											}
										}else {
											sb.append("第").append(i+1).append("行格式副本不存在：").append(rendFileName).append("\r\n");;
										}
									}
								}
							}
						}
						finally {
							//删除缓存文件
							if(itemStream!=null) {
								itemStream.close();
								f = new File(itemPath);
								f.delete();
							}
						}
					}else {
						String itemPath = uploadFolder +getCellValue(sheet.getRow(i).getCell(0));
						FileInputStream itemStream = null;
						if(!StringUtils.isEmpty(itemPath)) {
							File itemFile= new File(itemPath);
							
							if(itemFile.exists()) {
								itemStream = new FileInputStream(itemFile);
							}else {
								if(!isReuse) {
									sb.append("第").append(i+1).append("行文件不存在：").append(getCellValue(sheet.getRow(i).getCell(0))).append("\r\n");
								}
							}
						}
						try {
							newId = newDocument( token, parentType,itemStream, sheet.getRow(i),  fileList, attrNames,null, number, 1,sheet.getRow(i).getLastCellNum(),null,null);
							if(hasRendition) {
								String rendFileName = getCellValue(sheet.getRow(i).getCell(1));
								if(!StringUtils.isEmpty(rendFileName)) {
									if(FileUtils.getExtention(rendFileName).equalsIgnoreCase(FileUtils.getExtention(itemPath))) {
										sb.append("第").append(i+1).append("行主格式和副本扩展名相同.").append("\r\n");
									}else {
										itemPath = uploadFolder +rendFileName;
										File itemFile= new File(itemPath);
										if(itemFile.exists()) {
											FileInputStream rs =null;
											try {
												content = new EcmContent();
												content.setName(rendFileName);
												content.setContentSize(fileList.get(content.getName()));
												content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
												
												rs = new FileInputStream(itemFile);
												content.setInputStream(rs);
												documentService.addRendition(token, newId, content);
											}
											finally {
												if(rs!=null) {
													rs.close();
													itemFile.delete();
												}
											}
										}else {
											sb.append("第").append(i+1).append("行格式副本不存在：").append(rendFileName).append("\r\n");;
										}
									}
								}
							}
							if(!StringUtils.isEmpty(newId)) {
								newRelation(token, deliveryId, newId, i,null);
							}
						}
						finally {
							//删除缓存文件
							if(itemStream!=null) {
								itemStream.close();
								f = new File(itemPath);
								f.delete();
							}
						}
					}
					sucessCount ++;
				}catch(Exception ex) {
					ex.printStackTrace();
					sb.append("第").append(i+1).append("行导入错误：").append(ex.getMessage()).append("\r\n");;
					failedCount ++;
				}
			}
		}finally {
			if(workbook != null) {
				workbook.close();
			}
		}
		sb.append("完成导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
	}
	
	
	public String importSystemExcel(String token,String deliveryId, MultipartFile  excelSrcFile,MultipartFile[] files) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sucessCount =0;
		int failedCount =0;
		sb.append("开始导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TYPE_NAME", "导入批次");
		String number = numberService.getNumber(token, map);
		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if(!uploadFolder.endsWith(File.separator))
		{
			uploadFolder += File.separator;
		}
		uploadFolder += getSession(token).getCurrentUser().getLoginName()+File.separator+number+File.separator;
		File f = new File(uploadFolder);
		f.mkdirs();
		ImportService.importDocFolderId = deliveryId;
		if(ImportService.importExcelFolderId==null) {
			ImportService.importExcelFolderId = folderService.getObjectByPath(token, "/表单/批量导入单").getId();
		}
		
		EcmDocument doc = new EcmDocument();
		doc.getAttributes().put("NAME", excelSrcFile.getOriginalFilename());
		doc.getAttributes().put("CODING", number);
		doc.setTypeName("导入批次");
		doc.setFolderId(importExcelFolderId);
		EcmContent content = new EcmContent();
		content.setName(excelSrcFile.getOriginalFilename());
		content.setContentSize(excelSrcFile.getSize());
		content.setFormatName(FileUtils.getExtention(excelSrcFile.getOriginalFilename()).toLowerCase());
		content.setInputStream(excelSrcFile.getInputStream());
		doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
		doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
		
		documentService.newObject(token, doc, content);
		
		excelSrcFile.getInputStream().close();
		
		Map<String,Long> fileList = new HashMap<String,Long>();
		//先存储文件到本地
		for(MultipartFile file: files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder +name;
			writeToLocal(itemPath,file.getInputStream());
			file.getInputStream().close();
			fileList.put(name,file.getSize());
		}
		
		String excelFile = content.getFilePath();
		String storePath = CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();
		
		
		//List<Object[]> rows = excelUtil.read(storePath+excelFile, 0);
		
		FileInputStream fis = new FileInputStream(storePath+excelFile);

		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(0);
		try {
			
			//第一行第一列为类型名称
			String parentType = sheet.getRow(0).getCell(0).getStringCellValue();
			//第一行第n列为子对象类型名称，没有不要填写值
			String childType = null;
			int childStartIndex =0;
			for(int i=1;i<=sheet.getRow(0).getLastCellNum();i++) {
				if(sheet.getRow(0).getCell(i)!=null && !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
					childType = sheet.getRow(0).getCell(i).getStringCellValue();
					childStartIndex = i;
					break;
				}
			}
			//第二行第一列为父子相同字段值定义，{父字段名称1}:{子字段名称1};{父字段名称2}:{子字段名称2}
			String sameFields = (sheet.getRow(1).getCell(0)!=null?sheet.getRow(1).getCell(0).getStringCellValue():"");
			Map<String,Object> sameValues = new HashMap<String,Object>();
			//第三行字段名称
			Map<Integer,String> attrNames = new HashMap<Integer,String>();
			for(int i=0;i<=sheet.getRow(2).getLastCellNum();i++) {
				if(sheet.getRow(2).getCell(i)!=null && !StringUtils.isEmpty(sheet.getRow(2).getCell(i).getStringCellValue())) {
					attrNames.put(i, sheet.getRow(2).getCell(i).getStringCellValue());
				}
			}
			String newId = null;
			boolean hasRendition = false;
			try{
				hasRendition = sheet.getRow(2).getCell(1).getStringCellValue().equalsIgnoreCase("reditionfile");
			}catch(Exception ex) {
				
			}
			
			//第四行为中文标签，第五行位值
			for (int i = 4; i <= sheet.getLastRowNum(); i++) {
				try {
					content = null;
					if(childStartIndex>1) {
						
						//无格式副本2、3列至少一个不为空，有格式副本3、4不为空
						if((!hasRendition&&(!isEmptyCell(sheet.getRow(i).getCell(1))
								||!isEmptyCell(sheet.getRow(i).getCell(2)))
								||
								(hasRendition&&(!isEmptyCell(sheet.getRow(i).getCell(2))
										||!isEmptyCell(sheet.getRow(i).getCell(3))))
								)) {
		
								sameValues = new HashMap<String,Object>();
								newId = null;
								newId = newDocument( token, parentType,null, sheet.getRow(i),  fileList, attrNames,null,number, 1,childStartIndex-1,sameValues,sameFields);
						}
						String itemPath = null;
						FileInputStream itemStream = null;
						
						String fileName = getCellValue(sheet.getRow(i).getCell(0));
						if(fileName!=null) {
							if(fileName.indexOf(".")<0) {
								fileName += ".pdf";
							}
							itemPath = uploadFolder +fileName;
							if(!StringUtils.isEmpty(itemPath)) {
								File itemFile= new File(itemPath);
								
								if(itemFile.exists()) {
									itemStream = new FileInputStream(itemFile);
								}
							}
						}
						String tempId = null;
						try {
							tempId = newDocument( token, childType,itemStream, sheet.getRow(i),  fileList, attrNames,newId,number, childStartIndex,sheet.getRow(i).getLastCellNum(),sameValues,null);
							
							if(hasRendition) {
								String rendFileName = getCellValue(sheet.getRow(i).getCell(1));
								if(!StringUtils.isEmpty(rendFileName)) {
									if(FileUtils.getExtention(rendFileName).equalsIgnoreCase(FileUtils.getExtention(itemPath))) {
										sb.append("第").append(i+1).append("行主格式和副本扩展名相同.").append("\r\n");
									}else {
										itemPath = uploadFolder +rendFileName;
										File itemFile= new File(itemPath);
										
										if(itemFile.exists()) {
											FileInputStream rs =null;
											try {
												rs = new FileInputStream(itemFile);
												content = new EcmContent();
												content.setName(rendFileName);
												content.setContentSize(fileList.get(content.getName()));
												content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
												content.setInputStream(rs);
												documentService.addRendition(token, tempId, content);
											}
											finally {
												if(rs!=null) {
													rs.close();
													itemFile.delete();
												}
											}
										}else {
											sb.append("第").append(i+1).append("行格式副本不存在：").append(rendFileName).append("\r\n");;
										}
									}
								}
							}
						}
						finally {
							//删除缓存文件
							if(itemStream!=null) {
								itemStream.close();
								f = new File(itemPath);
								f.delete();
							}
						}
					}else {
						String itemPath = uploadFolder +getCellValue(sheet.getRow(i).getCell(0));
						FileInputStream itemStream = null;
						if(!StringUtils.isEmpty(itemPath)) {
							File itemFile= new File(itemPath);
							
							if(itemFile.exists()) {
								itemStream = new FileInputStream(itemFile);
							}
						}
						try {
							newId = newDocument( token, parentType,itemStream, sheet.getRow(i),  fileList, attrNames,null, number, 1,sheet.getRow(i).getLastCellNum(),null,null);
							if(hasRendition) {
								String rendFileName = getCellValue(sheet.getRow(i).getCell(1));
								if(!StringUtils.isEmpty(rendFileName)) {
									if(FileUtils.getExtention(rendFileName).equalsIgnoreCase(FileUtils.getExtention(itemPath))) {
										sb.append("第").append(i+1).append("行主格式和副本扩展名相同.").append("\r\n");
									}else {
										itemPath = uploadFolder +rendFileName;
										File itemFile= new File(itemPath);
										if(itemFile.exists()) {
											FileInputStream rs =null;
											try {
												content = new EcmContent();
												content.setName(rendFileName);
												content.setContentSize(fileList.get(content.getName()));
												content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
												
												rs = new FileInputStream(itemFile);
												content.setInputStream(rs);
												documentService.addRendition(token, newId, content);
											}
											finally {
												if(rs!=null) {
													rs.close();
													itemFile.delete();
												}
											}
										}else {
											sb.append("第").append(i+1).append("行格式副本不存在：").append(rendFileName).append("\r\n");;
										}
									}
								}
							}
//							if(!StringUtils.isEmpty(newId)) {
//								newRelation(token, deliveryId, newId, i,null);
//							}
						}
						finally {
							//删除缓存文件
							if(itemStream!=null) {
								itemStream.close();
								f = new File(itemPath);
								f.delete();
							}
						}
					}
					sucessCount ++;
				}catch(Exception ex) {
					ex.printStackTrace();
					sb.append("第").append(i+1).append("行导入错误：").append(ex.getMessage()).append("\r\n");;
					failedCount ++;
				}
			}
		}finally {
			if(workbook != null) {
				workbook.close();
			}
		}
		sb.append("完成导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
	}
	
	private String newDocument(String token, String typeName,FileInputStream itemStream,Row row, Map<String,Long> fileList,
			Map<Integer,String> attrNames,String parentId, String batchName,int start,int end,Map<String,Object> sameValues, String sameFields) throws Exception {
		
		int index = 1;
		String docId = null;
		try {
			index  = ((Double)row.getCell(getColumnIndex(attrNames, "C_ORDER_INDEX",start,end)).getNumericCellValue()).intValue();
		}catch(Exception ex) {
			try {
				index  = Integer.parseInt(row.getCell(getColumnIndex(attrNames, "C_ORDER_INDEX",start,end)).getStringCellValue());
			}catch(Exception ex1) {
				
			}
		}
			EcmDocument doc = new EcmDocument();
			doc.setTypeName(typeName);
			doc.setFolderId(ImportService.importDocFolderId);
			EcmContent content = null;
			if(itemStream!=null) {
				content = new EcmContent();
				String fileName = row.getCell(0).getStringCellValue();
				if(fileName.indexOf(".")<0) {
					fileName += ".pdf";
				}
				content.setName(fileName);
				content.setContentSize(fileList.get(content.getName()));
				content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
				content.setInputStream(itemStream);
				
				doc.getAttributes().put("C_IMPORT_NAME", content.getName());
				doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
				doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
			}
			if(parentId!=null) {
				setValues(doc.getAttributes(), attrNames,row,start,end,sameValues,null);
			}else {
				setValues(doc.getAttributes(), attrNames,row,start,end,sameValues,sameFields);
			}
			setDefaultValues(documentService.getSession(token),doc.getAttributes());
			doc.getAttributes().put("C_BATCH_CODING", batchName);
			docId = documentService.newObject(token, doc, content);
		
		//子对象添加关系
		if(parentId!=null && docId != null) {
			newRelation(token, parentId, docId, index,null);
		}
		return docId;
	}
	/**
	 * 读取Cell值
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String retVal = null;
		if(cell==null) {
			return null;
		}
		switch (cell.getCellType()) {
		case BOOLEAN:
			// 得到Boolean对象的方法
			retVal = cell.getBooleanCellValue()+"";
			break;
		case NUMERIC:
			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				// 读取日期格式
				Date dt= cell.getDateCellValue();
				retVal = DateUtils.DateToStr(dt,"yyyy-MM-dd");
			} else {
				DecimalFormat df = new DecimalFormat();
				// 单元格的值,替换掉,
				retVal= df.format(cell.getNumericCellValue()).replace(",", "");

			}
			break;
		case FORMULA:
			// 读取公式
			retVal = cell.getCellFormula();
			break;
		case STRING:
			// 读取String
			retVal = cell.getRichStringCellValue().toString();
			break;
		}
		return retVal;
	}
	/**
	 * 设置默认值
	 * @param token
	 * @param docAttrs
	 */
	private void setDefaultValues(IEcmSession session,Map<String,Object> docAttrs) {
		String typeName = docAttrs.get("TYPE_NAME").toString();
		EcmForm frm = CacheManagerOper.getEcmForms().get(typeName + "_NEW");
		if (frm == null) {
			frm = CacheManagerOper.getEcmForms().get(typeName + "_1");
		}
		List<EcmFormItem> list = frm.getEcmFormItems(session, null);
		for(EcmFormItem item: list) {
			if(!StringUtils.isEmpty(item.getDefaultValue())) {
				if(docAttrs.get(item.getAttrName())==null) {
					docAttrs.put(item.getAttrName(), item.getDefaultValue());
				}
			}
		}
	}
	
	private void newRelation(String token,String parentId,String childId,int orderIndex,String description) throws EcmException {
		if(!StringUtils.isEmpty(parentId)) {
			EcmRelation rel = new EcmRelation();
			rel.setParentId(parentId);
			rel.setChildId(childId);
			rel.setName("irel_children");
			rel.setOrderIndex(orderIndex);
			if(description!=null) {
				rel.setDescription(description);
			}
			relationService.newObject(token, rel);
		}
	}
	
	private void setValues(Map<String,Object> docAttrs,Map<Integer,String> attrNames,Row row,int start,int end,Map<String,Object> sameValues, String sameFields) throws Exception {
		String[] sameFlds = null;
		if(sameFields!=null&&sameFields.length()>1) {
			sameFlds = sameFields.split(";");
		}
		for(int i=start; i<=end; i++) {
			String attrName = attrNames.get(i);
			if(!StringUtils.isEmpty(attrName)) {
				if(attrName.equalsIgnoreCase("reditionfile")) {
					continue;
				}
				String val = getCellValue(row.getCell(i));
				setValue( docAttrs, attrName, val);
				//缓存父对象相同值
				if(sameFields!=null &&sameValues!=null) {
					setSameValue(sameValues,sameFlds, attrName, val);
				}
			}
		}
		//子对象添加相同值
		if(sameFields==null && sameValues!=null) {
			for(String key: sameValues.keySet()) {
				String val = sameValues.get(key)!=null?sameValues.get(key).toString().trim():null;
				setValue( docAttrs, key, val);
			}
		}
	}
	
	private void setSameValue(Map<String,Object> sameValues,String[] sameFlds,String attrName,String val) {
		if(!StringUtils.isEmpty(val)) {
			for(String fld:sameFlds) {
				String[] flds = fld.split(":");
				String fromAttr = flds[0];
				String toAttr = flds[0];
				if(flds.length==2) {
					toAttr = flds[1];
				}
				if(fromAttr.equals(attrName)) {
					sameValues.put(toAttr, val);
					return;
				}
			}
		}
	}
	
	private void setValue(Map<String,Object> docAttrs,String attrName, String val) throws Exception {
		if(!StringUtils.isEmpty(val)) {
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(attrName);
			// EcmFormItem en = getFormItem(frm.getEcmFormItems(),key.toString());
			if (en == null) {
				System.out.println(""+ attrName + " 不存在.");
				return;
			}
			switch (en.getFieldType()) {
				case 2:// 日期
				{
					docAttrs.put(attrName, val.replace(".", "-").replace("/", "-"));
					break;
				}
				case 3:// 布尔
				{
					docAttrs.put(attrName, val.equals("是")||val.equalsIgnoreCase("yes")||val.equalsIgnoreCase("true"));
					break;
				}
				case 4:
				case 5:
				case 6:
				case 7:
				case 8: //数字
				{
					docAttrs.put(attrName, val);
					break;
				}
				default:// 字符串
				{
					docAttrs.put(attrName, val);
					break;
				}
			}
		}
	}
	
	private int getColumnIndex(Map<Integer,String> attrNames,String name,int start,int end) {
		Set<Integer> set = attrNames.keySet();
		for(Integer idx: set) {
			if(attrNames.get(idx).equals(name)&&idx>=start&&idx<=end) {
				return idx;
			}
		}
		return -1;
	}


	/**
	 * 将InputStream写入本地文件
	 * @param destination 写入本地目录
	 * @param input	输入流
	 * @throws IOException
	 */
	private void writeToLocal(String destination, InputStream input)
			throws IOException {
		int index;
		byte[] bytes = new byte[1024];
		FileOutputStream downloadFile = new FileOutputStream(destination);
		while ((index = input.read(bytes)) != -1) {
			downloadFile.write(bytes, 0, index);
			downloadFile.flush();
		}
		downloadFile.close();
		input.close();
	}
	
	
	public String importOnServer(String token, String scanFolder, int folderCount) throws Exception {
		// TODO Auto-generated method stub
		/*
		StringBuilder sb = new StringBuilder();
		int sucessCount =0;
		int failedCount =0;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("TYPE_NAME", "移交单");
		String number = numberService.getNumber(token, map);
		
		sb.append("开始导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		
		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if(!uploadFolder.endsWith(File.separator))
		{
			uploadFolder += File.separator;
		}
		uploadFolder += getSession(token).getCurrentUser().getLoginName()+File.separator+number+File.separator;
		File f = new File(uploadFolder);
		f.mkdirs();
		
		if(ImportService.importExcelFolderId==null) {
			ImportService.importExcelFolderId = folderService.getObjectByPath(token, "/表单/移交单").getId();
			ImportService.importDocFolderId = folderService.getObjectByPath(token, "/移交文档").getId();
		}
		
		EcmDocument doc = new EcmDocument();
		doc.getAttributes().put("NAME", number);
		doc.getAttributes().put("CODING", number);
		doc.setTypeName("导入批次");
		doc.setFolderId(importExcelFolderId);
		EcmContent content = new EcmContent();
		content.setName(excelSrcFile.getOriginalFilename());
		content.setContentSize(excelSrcFile.getSize());
		content.setFormatName(FileUtils.getExtention(excelSrcFile.getOriginalFilename()).toLowerCase());
		content.setInputStream(excelSrcFile.getInputStream());
		doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
		doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
		
		documentService.newObject(token, doc, content);
		
		excelSrcFile.getInputStream().close();
		
		Map<String,Long> fileList = new HashMap<String,Long>();
		//先存储文件到本地
		for(MultipartFile file: files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder +name;
			writeToLocal(itemPath,file.getInputStream());
			file.getInputStream().close();
			fileList.put(name,file.getSize());
		}
		
		String excelFile = content.getFilePath();
		String storePath = CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();
		
		
		//List<Object[]> rows = excelUtil.read(storePath+excelFile, 0);
		
		FileInputStream fis = new FileInputStream(storePath+excelFile);

		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(0);
		try {
			
			//第一行第一列为类型名称
			String parentType = sheet.getRow(0).getCell(0).getStringCellValue();
			//第一行第n列为子对象类型名称，没有不要填写值
			String childType = null;
			int childStartIndex =0;
			for(int i=1;i<=sheet.getRow(0).getLastCellNum();i++) {
				if(sheet.getRow(0).getCell(i)!=null && !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
					childType = sheet.getRow(0).getCell(i).getStringCellValue();
					childStartIndex = i;
					break;
				}
			}
			//第二行第一列为父子相同字段值定义，{父字段名称1}:{子字段名称1};{父字段名称2}:{子字段名称2}
			String sameFields = (sheet.getRow(1).getCell(0)!=null?sheet.getRow(1).getCell(0).getStringCellValue():"");
			Map<String,Object> sameValues = new HashMap<String,Object>();
			//第三行字段名称
			Map<Integer,String> attrNames = new HashMap<Integer,String>();
			for(int i=0;i<=sheet.getRow(2).getLastCellNum();i++) {
				if(sheet.getRow(2).getCell(i)!=null && !StringUtils.isEmpty(sheet.getRow(2).getCell(i).getStringCellValue())) {
					attrNames.put(i, sheet.getRow(2).getCell(i).getStringCellValue());
				}
			}
			String newId = null;
			
			//第四行为中文标签，第五行位值
			for (int i = 4; i <= sheet.getLastRowNum(); i++) {
				try {
					content = null;
					//存在子对象
					
					if(childStartIndex>1) {
						//2、3列至少一个不为空
						if((sheet.getRow(i).getCell(1)!=null&&!StringUtils.isEmpty(sheet.getRow(i).getCell(1).getStringCellValue()))
								||(sheet.getRow(i).getCell(2)!=null&&!StringUtils.isEmpty(sheet.getRow(i).getCell(2).getStringCellValue()))) {
							sameValues = new HashMap<String,Object>();
							newId = null;
							newId = newDocument( token, parentType,null, sheet.getRow(i),  fileList, attrNames,null,number, 1,childStartIndex-1,sameValues,sameFields);
							if(!StringUtils.isEmpty(newId)) {
								newRelation(token, deliveryId, newId, i,null);
							}
						}
						String itemPath = uploadFolder +sheet.getRow(i).getCell(0).getStringCellValue();
						FileInputStream itemStream = null;
						if(!StringUtils.isEmpty(itemPath)) {
							File itemFile= new File(itemPath);
							
							if(itemFile.exists()) {
								itemStream = new FileInputStream(itemFile);
							}else {
								sb.append("第").append(i+1).append("行文件不存在：").append(sheet.getRow(i).getCell(0).getStringCellValue()).append("\r\n");;
							}
						}
						String tempId = null;
						try {
							tempId = newDocument( token, childType,itemStream, sheet.getRow(i),  fileList, attrNames,newId,number, childStartIndex,sheet.getRow(i).getLastCellNum(),sameValues,null);
						}
						finally {
							//删除缓存文件
							if(itemStream!=null) {
								itemStream.close();
								f = new File(itemPath);
								f.delete();
							}
						}
					}else {
						String itemPath = uploadFolder +sheet.getRow(i).getCell(0).getStringCellValue();
						FileInputStream itemStream = null;
						if(!StringUtils.isEmpty(itemPath)) {
							File itemFile= new File(itemPath);
							
							if(itemFile.exists()) {
								itemStream = new FileInputStream(itemFile);
							}else {
								sb.append("第").append(i+1).append("行文件不存在：").append(sheet.getRow(i).getCell(0).getStringCellValue()).append("\r\n");;
							}
						}
						try {
							newId = newDocument( token, parentType,itemStream, sheet.getRow(i),  fileList, attrNames,null, number, 1,sheet.getRow(i).getLastCellNum(),null,null);
							if(!StringUtils.isEmpty(newId)) {
								newRelation(token, deliveryId, newId, i,null);
							}
						}
						finally {
							//删除缓存文件
							if(itemStream!=null) {
								itemStream.close();
								f = new File(itemPath);
								f.delete();
							}
						}
					}
					sucessCount ++;
				}catch(Exception ex) {
					ex.printStackTrace();
					sb.append("第").append(i+1).append("行导入错误：").append(ex.getMessage()).append("\r\n");;
					failedCount ++;
				}
			}
		}finally {
			if(workbook != null) {
				workbook.close();
			}
		}
		sb.append("完成导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
		*/
		return null;
	}
	
	private boolean isEmptyCell(Cell cell) {
		if(cell == null) {
			return true;
		}
		if(StringUtils.isEmpty(cell.getStringCellValue())) {
			return true;
		}
		if(cell.getStringCellValue().trim().length()<1) {
			return true;
		}
		return false;
	}
}