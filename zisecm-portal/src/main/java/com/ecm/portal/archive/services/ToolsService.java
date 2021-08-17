package com.ecm.portal.archive.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.FileUtils;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmAttribute;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmFolder;
import com.ecm.core.entity.EcmForm;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoTakeNumberRuleException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.EcmService;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;
//import com.ecm.portal.archivegc.service.ImportServiceGc.UploadFile;
//import com.ecm.portal.archivegc.service.ImportServiceGc;

@Service
public class ToolsService extends EcmService {
	private static final Logger logger = LoggerFactory.getLogger(ToolsService.class);
	@Autowired
	private DocumentService documentService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private RelationService relationService;

	@Autowired
	private FolderService folderService;
	@Autowired
	private NumberService numberService;
	@Autowired
	private FolderPathService folderPathService;

	private static String importExcelFolderId;

	private static String importDocFolderId;

	/**
	 * 批量更新
	 * 
	 * @param token
	 * @param excelSrcFile Excel文件
	 * @return 导入日志
	 * @throws Exception
	 */
	public String updateByExcel(String token,MultipartFile[] files, MultipartFile excelSrcFile) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sucessCount = 0;
		int failedCount = 0;
		sb.append("开始更新").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		Workbook workbook = WorkbookFactory.create(excelSrcFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		EcmContent content = new EcmContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("TYPE_NAME", "导入批次");
		String number="";
		String uploadFolder="";
		
		try {
			number = numberService.getNumber(token, map);
			uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
			if (!uploadFolder.endsWith(File.separator)) {
				uploadFolder += File.separator;
			}
			uploadFolder += getSession(token).getCurrentUser().getLoginName() + File.separator + number + File.separator;
		}catch (NoTakeNumberRuleException e) {
			uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
			if (!uploadFolder.endsWith(File.separator)) {
				uploadFolder += File.separator;
			}
			uploadFolder += getSession(token).getCurrentUser().getLoginName() + File.separator ;
		}
		
		File f = new File(uploadFolder);
		f.mkdirs();

		if (ToolsService.importExcelFolderId == null) {
			ToolsService.importExcelFolderId = folderService.getObjectByPath(token, "/表单/批量导入单").getId();
//			ImportService.importDocFolderId = folderService.getObjectByPath(token, "/移交文档").getId();
		}

		EcmDocument doc0 = new EcmDocument();
		doc0.getAttributes().put("NAME", excelSrcFile.getOriginalFilename());
		doc0.getAttributes().put("CODING", number);
		doc0.setTypeName("导入批次");
		doc0.setFolderId(importExcelFolderId);
		content.setName(excelSrcFile.getOriginalFilename());
		content.setContentSize(excelSrcFile.getSize());
		content.setFormatName(FileUtils.getExtention(excelSrcFile.getOriginalFilename()).toLowerCase());
		content.setInputStream(excelSrcFile.getInputStream());
		doc0.getAttributes().put("FORMAT_NAME", content.getFormatName());
		doc0.getAttributes().put("CONTENT_SIZE", content.getContentSize());

		documentService.newObject(token, doc0, content);

		excelSrcFile.getInputStream().close();
		
		Map<String, Long> fileList = new HashMap<String, Long>();
		// 先存储文件到本地
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder + name;
			writeToLocal(itemPath, file.getInputStream());
			file.getInputStream().close();
			fileList.put(name, file.getSize());
		}
		
		if (excelSrcFile.getInputStream() != null) {
			excelSrcFile.getInputStream().close();
		}
		try {

			// 第一行字段名称
			Map<Integer, String> attrNames = new HashMap<Integer, String>();
			int childStartIndex = 0;
			Map<String, Object> sameValues = new HashMap<String, Object>();
			String parentId = null;
			for (int i = 0; i <= sheet.getRow(1).getLastCellNum(); i++) {
				if (sheet.getRow(0).getCell(i) != null
						&& !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
					attrNames.put(i, sheet.getRow(0).getCell(i).getStringCellValue());
				}else if(sheet.getRow(1).getCell(i) != null && !StringUtils.isEmpty(sheet.getRow(1).getCell(i).getStringCellValue())){
					attrNames.put(i, sheet.getRow(1).getCell(i).getStringCellValue());
					childStartIndex = i;
				}
			}
			boolean hasRendition = false;


			// 第二行为中文标签，第三行位值
			for (int i = 3; i <= sheet.getLastRowNum(); i++) {
				String tempId = null;
				try {
					content=null;
					if (childStartIndex > 1) {
						// 父ID不存在时 2、3、4列至少一个不为空
						if (parentId == null && isEmptyCell(sheet.getRow(i).getCell(1))
								&& isEmptyCell(sheet.getRow(i).getCell(2)) && isEmptyCell(sheet.getRow(i).getCell(3))) {
							continue;
							// 子对象1、2、3列至少一个不为空
						} 
					} else {
						if (isEmptyCell(sheet.getRow(i).getCell(1)) && isEmptyCell(sheet.getRow(i).getCell(2))
								&& isEmptyCell(sheet.getRow(i).getCell(3))) {
							continue;
						}
					}
					if (childStartIndex > 1) {
						if ((!hasRendition
								&& (!isEmptyCell(sheet.getRow(i).getCell(1))
										|| !isEmptyCell(sheet.getRow(i).getCell(2))
										|| !isEmptyCell(sheet.getRow(i).getCell(3)))
								|| (hasRendition && (!isEmptyCell(sheet.getRow(i).getCell(2))
										|| !isEmptyCell(sheet.getRow(i).getCell(3))
										|| !isEmptyCell(sheet.getRow(i).getCell(4)))))) {
							sameValues = new HashMap<String, Object>();
							parentId = null;

							String excelFileName = getCellValue(sheet.getRow(i).getCell(0));
							// String itemPath = getItemFilePath(excelFileName, files, uploadFolder);
							//UploadFile ufile =  getUploadFile(excelFileName, files, uploadFolder, i, sb);


//							parentId = newDocument(token, parentType, null,null /*ufile.itemStream*/, sheet.getRow(i), fileList,
//									attrNames, null, null, number, 1, childStartIndex - 1, sameValues,
//									sameFields,id,idType);
//							if(parentId != null) {
//								sucessCount ++;
//								// 将案卷添加至移交单
//								if(!StringUtils.isEmpty(id) && idType==0) {
//									newRelation(token, id,relationName, parentId, i,null);
//								}
//							}else {
//								failedCount ++;
//							}
//							if(!StringUtils.isEmpty(newId)) {
//								if("设计文件".equals(parentType)&&(relationName==null||"".equals(relationName.trim()))) {
//									relationName="设计文件";
//								}
//								newRelation(token, deliveryId,relationName, newId, i,null);
//							}
						}
						if(parentId == null) {
							sb.append("第").append(i + 1).append("父对象不存在.").append("\r\n");
							continue;
						}
						if ( parentId != null &&isEmptyCell(sheet.getRow(i).getCell(childStartIndex))
								&& isEmptyCell(sheet.getRow(i).getCell(childStartIndex + 1))
								&& isEmptyCell(sheet.getRow(i).getCell(childStartIndex + 2))) {
							continue;
						}
						String id = sheet.getRow(i).getCell(1).getStringCellValue();
						if (StringUtils.isEmpty(id)) {
							continue;
						}
						EcmDocument doc = documentService.getObjectById(token, id);
						if (doc != null) {
							Map<String, Object>  values = this.setValues(token,doc, attrNames, sheet.getRow(i), 1,
									sheet.getRow(i).getLastCellNum());
							documentService.updateObject(token, values);
							tempId = null;
							String fileNameStr = getCellValue(sheet.getRow(i).getCell(0));
							if(fileNameStr==null) {
								fileNameStr = "";
							}
							String[] fileNames = fileNameStr.split(";");
							for (int fileNameIndex = 0; fileNameIndex < fileNames.length; fileNameIndex++) {
								// fileNameIndex==0表示第一个附件将其放入主件
								
								String excelFileName = fileNames[fileNameIndex];
								excelFileName = excelFileName.substring(0,
										excelFileName.lastIndexOf(".") > -1 ? excelFileName.lastIndexOf(".")
												: excelFileName.length());
								UploadFile ufile =  getUploadFile(excelFileName, files, uploadFolder, i, sb);
								
								if (fileNameIndex == 0) {
									try {
	//									tempId = newDocument(token, childType, ufile.getFileName(), ufile.getItemStream(), sheet.getRow(i),
	//											fileList, attrNames, parentId, relationName, number, childStartIndex,
	//											sheet.getRow(i).getLastCellNum(), sameValues, null,id,idType);
										
										if(id != null) {
											sucessCount ++;
										}else {
											failedCount ++;
										}
																	
										hasRendition = sheet.getRow(2).getCell(0).getStringCellValue().equalsIgnoreCase("reditionfile");
		
										if (hasRendition) {
											String rendFileName = getCellValue(sheet.getRow(i).getCell(1));
											if (!StringUtils.isEmpty(rendFileName)) {
												if (FileUtils.getExtention(rendFileName)
														.equalsIgnoreCase(FileUtils.getExtention(ufile.getFilePath()))) {
													sb.append("第").append(i + 1).append("行主格式和副本扩展名相同.").append("\r\n");
												} else {
													String renditionPath = uploadFolder + rendFileName;
													File itemFile = new File(renditionPath);
	
													if (itemFile.exists()) {
														FileInputStream rs = null;
														try {
															rs = new FileInputStream(itemFile);
															content = new EcmContent();
															content.setName(rendFileName);
															content.setContentSize(fileList.get(content.getName()));
															content.setFormatName(FileUtils.getExtention(content.getName())
																	.toLowerCase());
															content.setInputStream(rs);
															documentService.addRendition(token, id, content);
														} finally {
															if (rs != null) {
																rs.close();
																itemFile.delete();
															}
														}
													} else {
														sb.append("第").append(i + 1).append("行的").append(excelFileName)
																.append("文件不存在：")
																.append(getCellValue(sheet.getRow(i).getCell(0)))
																.append("\r\n");
													}
												}
											}
										}
									} finally {
										// 删除缓存文件
	//									if (ufile.getItemStream() != null) {
	//										ufile.getItemStream().close();
	//										f = new File(ufile.filePath);
	//										f.delete();
	//									}
									}
								}
							}
							sucessCount++;
						} else {
							sb.append("第").append(i + 1).append("行更新错误：").append(id).append("不存在\r\n");
							;
							failedCount++;
						}
					}else {//无子对象关系

						String fileNameStr = getCellValue(sheet.getRow(i).getCell(0));
						String[] fileNames = fileNameStr.split(";");

						for (int fileNameIndex = 0; fileNameIndex < fileNames.length; fileNameIndex++) {
							// fileNameIndex==0表示第一个附件将其放入主件
							String excelFileName = fileNames[fileNameIndex];
							excelFileName = excelFileName.substring(0,
									excelFileName.lastIndexOf(".") > -1 ? excelFileName.lastIndexOf(".")
											: excelFileName.length());
							String itemPath = "";
							String deskFileName = "";
							for (int findx = 0; findx < files.length; findx++) {
								MultipartFile file = files[findx];
								deskFileName = file.getOriginalFilename();
								if (excelFileName.equals(deskFileName.substring(0,
										deskFileName.lastIndexOf(".") > -1 ? deskFileName.lastIndexOf(".")
												: deskFileName.length()))) {
									itemPath = uploadFolder + deskFileName;
									break;
								}
							}
							FileInputStream itemStream = null;
							if (!StringUtils.isEmpty(itemPath)) {
								File itemFile = new File(itemPath);

								if (itemFile.exists()) {
									itemStream = new FileInputStream(itemFile);
								} else {
									sb.append("第").append(i + 1).append("行的").append(excelFileName).append("文件不存在：")
											.append(getCellValue(sheet.getRow(i).getCell(0))).append("\r\n");
								}
							} else {
								sb.append("第").append(i + 1).append("行的").append(excelFileName).append("文件不存在：")
										.append(getCellValue(sheet.getRow(i).getCell(0))).append("\r\n");
							}
							if (fileNameIndex == 0) {
								try {
//									tempId = newDocument(token, parentType, deskFileName, itemStream, sheet.getRow(i),
//											fileList, attrNames, null, relationName, number, 1,
//											sheet.getRow(i).getLastCellNum(), null, null,id,idType);
									if (hasRendition) {
										String rendFileName = getCellValue(sheet.getRow(i).getCell(1));
										if (!StringUtils.isEmpty(rendFileName)) {
											if (FileUtils.getExtention(rendFileName)
													.equalsIgnoreCase(FileUtils.getExtention(itemPath))) {
												sb.append("第").append(i + 1).append("行主格式和副本扩展名相同.").append("\r\n");
											} else {
												itemPath = uploadFolder + rendFileName;
												File itemFile = new File(itemPath);
												if (itemFile.exists()) {
													FileInputStream rs = null;
													try {
														content = new EcmContent();
														content.setName(rendFileName);
														content.setContentSize(fileList.get(content.getName()));
														content.setFormatName(FileUtils.getExtention(content.getName())
																.toLowerCase());

														rs = new FileInputStream(itemFile);
														content.setInputStream(rs);
														documentService.addRendition(token, tempId, content);
													} finally {
														if (rs != null) {
															rs.close();
															itemFile.delete();
														}
													}
												} else {
													sb.append("第").append(i + 1).append("行格式副本不存在：")
															.append(rendFileName).append("\r\n");
													;
												}
											}
										}
									}
//									if (!StringUtils.isEmpty(tempId)) {
//										if(tempId != null) {
//											sucessCount ++;
//										}else {
//											failedCount ++;
//										}
//										// 添加与移交单关联关系
//										if(!StringUtils.isEmpty(id) && idType ==0) {
//											newRelation(token, id,relationName, tempId, i,null);
//										}
//									}
								} finally {
									// 删除缓存文件
//									if (itemStream != null) {
//										itemStream.close();
//										f = new File(itemPath);
//										f.delete();
//									}
								}
							} else {

								try {
									EcmDocument attachDoc = new EcmDocument();
									attachDoc.setTypeName("附件");
									attachDoc.setName(deskFileName.substring(0,
											deskFileName.lastIndexOf(".") > -1 ? deskFileName.lastIndexOf(".")
													: deskFileName.length()));
									String folderId = "";
									folderId = folderPathService.getFolderId(token, attachDoc.getAttributes(), "3");
									EcmFolder folder = folderService.getObjectById(token, folderId);
									attachDoc.setFolderId(folderId);
									attachDoc.setAclName(folder.getAclName());

									EcmContent en = new EcmContent();
									if (itemStream != null) {
										en.setInputStream(itemStream);
									}
									en.setName(deskFileName);
									en.setFormatName(FileUtils.getExtention(deskFileName).toLowerCase());
									en.setContentSize(fileList.get(deskFileName));
									String attachId = documentService.newObject(token, attachDoc, en);
									newRelation(token, tempId, "附件", attachId, fileNameIndex, null);
								} finally {
									// 删除缓存文件
									if (itemStream != null) {
										itemStream.close();
//										f = new File(itemPath);
//										f.delete();
									}
								}

							}

						}

//						String itemPath = uploadFolder +getCellValue(sheet.getRow(i).getCell(0));

					}			
				} catch (Exception ex) {
					ex.printStackTrace();
					sb.append("第").append(i + 1).append("行更新错误：").append(ex.getMessage()).append("\r\n");
					;
					failedCount++;
				}
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		
		sb.append("完成更新:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
	}

	/**
	 * 根据Excel挂载文件
	 * @param token
	 * @param excelSrcFile，第一列ID，第二列为文件名
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public String mountByExcel(String token, MultipartFile excelSrcFile, MultipartFile[] files) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sucessCount = 0;
		int failedCount = 0;
		sb.append("开始挂载:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");

		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if (!uploadFolder.endsWith(File.separator)) {
			uploadFolder += File.separator;
		}
		uploadFolder += getSession(token).getCurrentUser().getLoginName();
		File f = new File(uploadFolder);
		if (!f.exists()) {
			f.mkdirs();
		}
		uploadFolder += File.separator;

		Map<String, Long> fileList = new HashMap<String, Long>();
		// 先存储文件到本地
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder + name;
			writeToLocal(itemPath, file.getInputStream());
			file.getInputStream().close();
			fileList.put(name, file.getSize());
		}

		Workbook workbook = WorkbookFactory.create(excelSrcFile.getInputStream());
		if (excelSrcFile.getInputStream() != null) {
			excelSrcFile.getInputStream().close();
		}
		Sheet sheet = workbook.getSheetAt(0);
		try {
			// 第2行为中文标签，第3行位值
			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				try {
					String id = sheet.getRow(i).getCell(0).getStringCellValue();
					String file = sheet.getRow(i).getCell(1).getStringCellValue();
					if (StringUtils.isEmpty(id)) {
						continue;
					}
					if (fileList.get(file) == null) {
						sb.append("第").append(i + 1).append("行挂载错误：").append(file).append("不存在\r\n");
						;
						failedCount++;
						continue;
					}
					EcmDocument doc = documentService.getObjectById(token, id);
					if (doc != null) {
						String format = FileUtils.getExtention(file);
						EcmContent content = contentService.getObject(token, id, 0, format);
						FileInputStream itemStream = new FileInputStream(uploadFolder + file);
						try {
							// 不存在对于内容，新建
							if (content == null) {
								content = new EcmContent();
								content.setName(file);
								// 无主格式，当前为主格式
								if (doc.getContentSize() == 0) {
									content.setContentType(1);
								} else {
									content.setContentType(2);
								}
								content.setContentSize(fileList.get(content.getName()));
								content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
								content.setInputStream(itemStream);
								// 增加主格式
								if (doc.getContentSize() == 0) {
									doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
									doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
									documentService.mountFile(token, id, content);
								} else {
									documentService.addRendition(token, id, content);
								}
							} else {
								if(!allowReplace()) {
									sb.append("挂载错误：").append(file).append("当前格式已存在\r\n");
									failedCount++;
									continue;
								}else {
									content.setName(file);
									content.setContentSize(fileList.get(content.getName()));
									content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
									content.setInputStream(itemStream);
									contentService.updateObject(token, content);
									if (doc.getFormatName().equals(content.getFormatName())) {
										contentService.updatePrimaryContentSize(token, id, content.getContentSize());
									}
								}
							}
						} finally {
							itemStream.close();
						}
						sucessCount++;
					} else {
						sb.append("第").append(i + 1).append("行挂载错误：").append(id).append("不存在\r\n");
						;
						failedCount++;
					}
					File fl = new File(uploadFolder + file);
					if (fl.exists()) {
						fl.delete();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					sb.append("第").append(i + 1).append("行挂载错误：").append(ex.getMessage()).append("\r\n");
					;
					failedCount++;
				}
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		sb.append("完成挂载:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
	}

	/**
	 * 根据文件名批量更新，只支持图纸文件
	 * @param token
	 * @param files，命名规则：编码@版本@文件名.扩展名，或编码@版本.扩展名
	 * @return
	 * @throws Exception
	 */
	public String mountByFile(String token, MultipartFile[] files) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sucessCount = 0;
		int failedCount = 0;
		sb.append("开始挂载:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");

		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if (!uploadFolder.endsWith(File.separator)) {
			uploadFolder += File.separator;
		}
		uploadFolder += getSession(token).getCurrentUser().getLoginName();
		File f = new File(uploadFolder);
		if (!f.exists()) {
			f.mkdirs();
		}
		uploadFolder += File.separator;

		Map<String, Long> fileList = new HashMap<String, Long>();
		// 先存储文件到本地
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder + name;
			writeToLocal(itemPath, file.getInputStream());
			file.getInputStream().close();
			fileList.put(name, file.getSize());
		}
		try {
			for (String file : fileList.keySet()) {
				if (fileList.get(file) == null) {
					sb.append("挂载错误：").append(file).append("不存在\r\n");
					;
					failedCount++;
					continue;
				}
				String[] strs = file.split("@");
				if (strs.length < 2) {
					sb.append("挂载错误：").append(file).append("文件名不符合规则\r\n");
					;
					failedCount++;
					continue;
				}
				String coding = strs[0];
				String revision = strs[1];
				if (revision.length() < 1) {
					sb.append("挂载错误：").append(file).append("版本为空\r\n");
					failedCount++;
					continue;
				}
				if (revision.indexOf(".") > 0) {
					revision = revision.split(".")[0];
				}
				String condition = "CODING='" + coding + "' and REVISION='" + revision + "' and TYPE_NAME='图纸文件'";
				List<Map<String, Object>> list = documentService.getObjectMap(token, condition);
				if (list.size() == 1) {
					String id = list.get(0).get("ID").toString();
					EcmDocument doc = documentService.getObjectById(token, id);
					if (doc != null) {
						String format = FileUtils.getExtention(file);
						EcmContent content = contentService.getObject(token, id, 0, format);
						FileInputStream itemStream = new FileInputStream(uploadFolder + file);
						try {
							// 不存在对于内容，新建
							if (content == null) {
								content = new EcmContent();
								content.setName(file);
								// 无主格式，当前为主格式
								if (doc.getContentSize() == 0) {
									content.setContentType(1);
								} else {
									content.setContentType(2);
								}
								content.setContentSize(fileList.get(content.getName()));
								content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
								content.setInputStream(itemStream);
								// 增加主格式
								if (doc.getContentSize() == 0) {
									doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
									doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
									documentService.mountFile(token, id, content);
								} else {
									documentService.addRendition(token, id, content);
								}
							} else {
								if(!allowReplace()) {
									sb.append("挂载错误：").append(file).append("当前格式已存在\r\n");
									failedCount++;
									continue;
								}else {
									content.setName(file);
									content.setContentSize(fileList.get(content.getName()));
									content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
									content.setInputStream(itemStream);
									contentService.updateObject(token, content);
									if (doc.getFormatName().equals(content.getFormatName())) {
										contentService.updatePrimaryContentSize(token, id, content.getContentSize());
									}
								}
							}
						} finally {
							itemStream.close();
						}
						sucessCount++;
					}
				} else if (list.size() > 1) {
					sb.append("挂载错误：").append(file).append("系统存在多条数据\r\n");
					failedCount++;
				}
				File fl = new File(uploadFolder + file);
				if (fl.exists()) {
					fl.delete();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			sb.append("挂载错误：").append(ex.getMessage()).append("\r\n");
			;
			failedCount++;
		}
		sb.append("完成挂载:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
	}

	/**
	 * 读取Cell值
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String retVal = null;
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case BOOLEAN:
			// 得到Boolean对象的方法
			retVal = cell.getBooleanCellValue() + "";
			break;
		case NUMERIC:
			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				// 读取日期格式
				Date dt = cell.getDateCellValue();
				retVal = DateUtils.DateToStr(dt, "yyyy-MM-dd");
			} else {
				DecimalFormat df = new DecimalFormat();
				// 单元格的值,替换掉,
				retVal = df.format(cell.getNumericCellValue()).replace(",", "");
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
	 * 
	 * @param token
	 * @param docAttrs
	 */
	private void setDefaultValues(IEcmSession session, Map<String, Object> docAttrs) {
		String typeName = docAttrs.get("TYPE_NAME").toString();
		EcmForm frm = CacheManagerOper.getEcmForms().get(typeName + "_NEW");
		if (frm == null) {
			frm = CacheManagerOper.getEcmForms().get(typeName + "_1");
		}
		List<EcmFormItem> list = frm.getEcmFormItems(session, null);
		for (EcmFormItem item : list) {
			if (!StringUtils.isEmpty(item.getDefaultValue())) {
				if (docAttrs.get(item.getAttrName()) == null) {
					docAttrs.put(item.getAttrName(), item.getDefaultValue());
				}
			}
		}
	}

	private void newRelation(String token, String parentId, String childId, int orderIndex, String description)
			throws EcmException {
		if (!StringUtils.isEmpty(parentId)) {
			EcmRelation rel = new EcmRelation();
			rel.setParentId(parentId);
			rel.setChildId(childId);
			rel.setName("irel_children");
			rel.setOrderIndex(orderIndex);
			if (description != null) {
				rel.setDescription(description);
			}
			relationService.newObject(token, rel);
		}
	}

	private Map<String, Object>  setValues(String token,EcmDocument doc, Map<Integer, String> attrNames, Row row, int start, int end)
			throws Exception {
		Map<String, Object> attrValues = new HashMap<String,Object>();
		attrValues.put("ID", doc.getId());
		for (int i = start; i <= end; i++) {
			String attrName = attrNames.get(i);
			if (!StringUtils.isEmpty(attrName)) {
				String val = getCellValue(row.getCell(i));
				setValue(token, doc, attrName, val,attrValues);
			}
		}
		return attrValues;

	}

	private void setSameValue(Map<String, Object> sameValues, String[] sameFlds, String attrName, String val) {
		if (!StringUtils.isEmpty(val)) {
			for (String fld : sameFlds) {
				String[] flds = fld.split(":");
				String fromAttr = flds[0];
				String toAttr = flds[0];
				if (flds.length == 2) {
					toAttr = flds[1];
				}
				if (fromAttr.equals(attrName)) {
					sameValues.put(toAttr, val);
					return;
				}
			}
		}
	}

	private void setValue(String token,EcmDocument doc, String attrName, String val,Map<String, Object> attrValues) throws Exception {
		if (!StringUtils.isEmpty(val)) {
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(attrName);
			if(en == null) {
				EcmForm frm = CacheManagerOper.getEcmForms().get(doc.getTypeName() + "_EDIT");
				if (frm == null) {
					frm = CacheManagerOper.getEcmForms().get(doc.getTypeName() + "_1");
				}
				List<EcmFormItem> list = frm.getEcmFormItems(documentService.getSession(token), "zh-cn");
				if(list != null) {
					for(EcmFormItem item:list) {
						if(item.getLabel().equals(attrName)) {
							attrName = item.getAttrName();
							en = CacheManagerOper.getEcmAttributes().get(item.getAttrName());
							break;
						}
					}
				}
			}

			
			if (en == null) {
				logger.debug("" + attrName + " 不存在.");
				return;
			}
			switch (en.getFieldType()) {
			case 2:// 日期
			{
				attrValues.put(attrName, val.replace(".", "-").replace("/", "-"));
				break;
			}
			case 3:// 布尔
			{
				attrValues.put(attrName, val.equals("是") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("true"));
				break;
			}
			case 4:
			case 5:
			case 6:
			case 7:
			case 8: // 数字
			{
				attrValues.put(attrName, val);
				break;
			}
			default:// 字符串
			{
				attrValues.put(attrName, val);
				break;
			}
			}
		}
	}

	private int getColumnIndex(Map<Integer, String> attrNames, String name, int start, int end) {
		Set<Integer> set = attrNames.keySet();
		for (Integer idx : set) {
			if (attrNames.get(idx).equals(name) && idx >= start && idx <= end) {
				return idx;
			}
		}
		return -1;
	}

	/**
	 * 将InputStream写入本地文件
	 * 
	 * @param destination 写入本地目录
	 * @param input       输入流
	 * @throws IOException
	 */
	private void writeToLocal(String destination, InputStream input) throws IOException {
		int index;
		File f = new File(destination);
		if (f.exists()) {
			f.delete();
		}
		byte[] bytes = new byte[1024];
		FileOutputStream downloadFile = new FileOutputStream(destination);
		while ((index = input.read(bytes)) != -1) {
			downloadFile.write(bytes, 0, index);
			downloadFile.flush();
		}
		downloadFile.close();
		input.close();
	}
	
	private boolean allowReplace() {
		boolean allowReplace = false;
		try {
			allowReplace = "true".equalsIgnoreCase(CacheManagerOper.getEcmParameters().get("BatchMountReplace").getValue());
		}catch(Exception ex) {
			
		}
		return allowReplace;
	}
	//上传文件对象
		private class UploadFile {
			private FileInputStream itemStream;
			public FileInputStream getItemStream() {
				return itemStream;
			}
			public void setItemStream(FileInputStream itemStream) {
				this.itemStream = itemStream;
			}
			public String getFileName() {
				return fileName;
			}
			public void setFileName(String fileName) {
				this.fileName = fileName;
			}
			public MultipartFile getMultipartFile() {
				return multipartFile;
			}
			public void setMultipartFile(MultipartFile multipartFile) {
				this.multipartFile = multipartFile;
			}
			private String fileName;
			private String filePath;
			public String getFilePath() {
				return filePath;
			}
			public void setFilePath(String filePath) {
				this.filePath = filePath;
			}
			MultipartFile multipartFile;
		}
		private UploadFile getUploadFile(String excelFileName, MultipartFile[] files, String uploadFolder,
				int rowIndex, StringBuilder sb) throws Exception {
			
			UploadFile ufile = new UploadFile();
			if(excelFileName==null || excelFileName.length()==0) {
				return ufile;
			}
			excelFileName = excelFileName.substring(0,
					excelFileName.lastIndexOf(".") > -1 ? excelFileName.lastIndexOf(".") : excelFileName.length());
			String deskFileName = "";
			for (int findx = 0; findx < files.length; findx++) {
				MultipartFile file = files[findx];
				deskFileName = file.getOriginalFilename();
				
				if (excelFileName.equals(deskFileName.substring(0,
						deskFileName.lastIndexOf(".") > -1 ? deskFileName.lastIndexOf(".") : deskFileName.length()))) {
					ufile.setFileName(deskFileName);
					ufile.setFilePath(uploadFolder + deskFileName);
					ufile.setMultipartFile(file);
					break;
				}
			}
			
			FileInputStream itemStream = null;
			if (!StringUtils.isEmpty(ufile.getFilePath())) {
				File itemFile = new File(ufile.getFilePath());

				if (itemFile.exists()) {
					itemStream = new FileInputStream(itemFile);
				} else {
					sb.append("第").append(rowIndex + 1).append("行的").append("文件不存在：").append(excelFileName).append("\r\n");
				}
			} else {
				sb.append("第").append(rowIndex + 1).append("行的").append("文件不存在：").append(excelFileName).append("\r\n");
			}
			ufile.setItemStream(itemStream);
			return ufile;
		}
		private boolean isEmptyCell(Cell cell) {
			if (cell == null) {
				return true;
			}
			if (StringUtils.isEmpty(cell.getStringCellValue())) {
				return true;
			}
			if (cell.getStringCellValue().trim().length() < 1) {
				return true;
			}
			return false;
		}
		private void newRelation(String token, String parentId, String relationName, String childId, int orderIndex,
				String description) throws EcmException {
			if (!StringUtils.isEmpty(parentId)) {
				EcmRelation rel = new EcmRelation();
				rel.setParentId(parentId);
				rel.setChildId(childId);
				if (relationName == null || "".equals(relationName)) {
					rel.setName("irel_children");
				} else {
					rel.setName(relationName);
				}

				rel.setOrderIndex(orderIndex);
				if (description != null) {
					rel.setDescription(description);
				}
				relationService.newObject(token, rel);
			}
		}
		

	
}
