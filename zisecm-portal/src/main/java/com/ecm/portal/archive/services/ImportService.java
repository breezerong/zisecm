package com.ecm.portal.archive.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ecm.core.entity.EcmFolder;
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
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;

@Service
public class ImportService extends EcmService {
	private static final Logger logger = LoggerFactory.getLogger(ImportService.class);
	
	@Autowired
	private DocumentService documentService;

	@Autowired
	private NumberService numberService;

	@Autowired
	private RelationService relationService;

	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderPathService folderPathService;

	private static String importExcelFolderId;

	private static String importDocFolderId;

	/**
	 * 批次导入
	 * 
	 * @param token
	 * @param deliveryId   移交单编号
	 * @param excelSrcFile Excel文件
	 * @param files        电子文件
	 * @return 导入日志
	 * @throws Exception
	 */
	public String importExcel(String token, String deliveryId, String relationName, MultipartFile excelSrcFile,
			MultipartFile[] files) throws Exception {
		StringBuilder sb = new StringBuilder();
		int sucessCount = 0;
		int failedCount = 0;
		sb.append("开始导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("TYPE_NAME", "导入批次");
		String number = numberService.getNumber(token, map);
		String uploadFolder = CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		if (!uploadFolder.endsWith(File.separator)) {
			uploadFolder += File.separator;
		}
		uploadFolder += getSession(token).getCurrentUser().getLoginName() + File.separator + number + File.separator;
		File f = new File(uploadFolder);
		f.mkdirs();

		if (ImportService.importExcelFolderId == null) {
			ImportService.importExcelFolderId = folderService.getObjectByPath(token, "/表单/批量导入单").getId();
//			ImportService.importDocFolderId = folderService.getObjectByPath(token, "/移交文档").getId();
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

		Map<String, Long> fileList = new HashMap<String, Long>();
		// 先存储文件到本地
		for (MultipartFile file : files) {
			String name = file.getOriginalFilename();
			String itemPath = uploadFolder + name;
			writeToLocal(itemPath, file.getInputStream());
			file.getInputStream().close();
			fileList.put(name, file.getSize());
		}

		String excelFile = content.getFilePath();
		String storePath = CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();

		// List<Object[]> rows = excelUtil.read(storePath+excelFile, 0);

		FileInputStream fis = new FileInputStream(storePath + excelFile);

		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(0);
		try {

			// 第一行第一列为类型名称
			String parentType = sheet.getRow(0).getCell(0).getStringCellValue();
			// 第一行第n列为子对象类型名称，没有不要填写值
			String childType = null;
			int childStartIndex = 0;
			for (int i = 1; i <= sheet.getRow(0).getLastCellNum(); i++) {
				if (sheet.getRow(0).getCell(i) != null
						&& !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
					childType = sheet.getRow(0).getCell(i).getStringCellValue();
					childStartIndex = i;
					break;
				}
			}
			// 第二行第一列为父子相同字段值定义，{父字段名称1}:{子字段名称1};{父字段名称2}:{子字段名称2}
			String sameFields = (sheet.getRow(1).getCell(0) != null ? sheet.getRow(1).getCell(0).getStringCellValue()
					: "");
			Map<String, Object> sameValues = new HashMap<String, Object>();
			// 第三行字段名称
			Map<Integer, String> attrNames = new HashMap<Integer, String>();
			for (int i = 0; i <= sheet.getRow(2).getLastCellNum(); i++) {
				if (sheet.getRow(2).getCell(i) != null
						&& !StringUtils.isEmpty(sheet.getRow(2).getCell(i).getStringCellValue())) {
					attrNames.put(i, sheet.getRow(2).getCell(i).getStringCellValue());
				}
			}
			String parentId = null;
			boolean hasRendition = false;
			try {
				hasRendition = sheet.getRow(2).getCell(1).getStringCellValue().equalsIgnoreCase("reditionfile");
			} catch (Exception ex) {

			}

			// 第四行为中文标签，第五行为值
			for (int i = 4; i <= sheet.getLastRowNum(); i++) {

				String tempId = null;
				try {
					content = null;
					// 存在子对象
//					boolean isReuse = false;
//					try {
//						isReuse = sheet.getRow(i).getCell(getColumnIndex(attrNames, "DESIGN_REUSE",1,sheet.getLastRowNum())).getStringCellValue().equals("复用");
//					}catch(Exception ex) {
//						
//					}
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
						// 父对象，无格式副本2、3列至少一个不为空，有格式副本3、4不为空
						if ((!hasRendition
								&& (!isEmptyCell(sheet.getRow(i).getCell(1))
										|| !isEmptyCell(sheet.getRow(i).getCell(2))
										|| !isEmptyCell(sheet.getRow(i).getCell(3)))
								|| (hasRendition && (!isEmptyCell(sheet.getRow(i).getCell(2))
										|| !isEmptyCell(sheet.getRow(i).getCell(3))
										|| !isEmptyCell(sheet.getRow(i).getCell(4)))))) {
							sameValues = new HashMap<String, Object>();
							parentId = null;
							if("相关文件".equals(childType)) {
								
							}else {
								if(existsCheck(token, parentType,sheet.getRow(i),attrNames,1,childStartIndex-1)) {
									sb.append("第").append(i + 1).append("行数据已存在.").append("\r\n");
									continue;
								}
							}
							String excelFileName = getCellValue(sheet.getRow(i).getCell(0));
							// String itemPath = getItemFilePath(excelFileName, files, uploadFolder);
							UploadFile ufile =  getUploadFile(excelFileName, files, uploadFolder, i, sb);


							parentId = newDocument(token, parentType, ufile.fileName, ufile.itemStream, sheet.getRow(i), fileList,
									attrNames, null, null, number, 1, childStartIndex - 1, sameValues,
									sameFields);
							if(parentId != null) {
								sucessCount ++;
							}else {
								failedCount ++;
							}
							if("设计文件".equals(childType)) {
								continue;
							}
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
						if("设计文件".equals(childType)) {
							relationName="设计文件";
						}else if("相关文件".equals(childType)) {
							relationName="相关文件";
							tempId = newDocument(token, childType, null,null, sheet.getRow(i),
									fileList, attrNames, parentId, relationName, number, childStartIndex,
									sheet.getRow(i).getLastCellNum(), sameValues, null);
							continue;
						}
						tempId = null;
						String fileNameStr = getCellValue(sheet.getRow(i).getCell(0));
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
									if(existsCheck(token, childType,sheet.getRow(i),attrNames,childStartIndex,sheet.getRow(i).getLastCellNum())) {
										sb.append("第").append(i + 1).append("行数据已存在.").append("\r\n");
										break;
									}
									tempId = newDocument(token, childType, ufile.getFileName(), ufile.getItemStream(), sheet.getRow(i),
											fileList, attrNames, parentId, relationName, number, childStartIndex,
											sheet.getRow(i).getLastCellNum(), sameValues, null);
									if(tempId != null) {
										sucessCount ++;
									}else {
										failedCount ++;
									}
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
														documentService.addRendition(token, tempId, content);
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
									if (ufile.getItemStream() != null) {
										ufile.getItemStream().close();
										f = new File(ufile.filePath);
										f.delete();
									}
								}
							} else {
								try {
									EcmDocument attachDoc = new EcmDocument();
									attachDoc.setTypeName("附件");
									attachDoc.setName(ufile.getFileName().substring(0,
											ufile.getFileName().lastIndexOf(".") > -1 ? ufile.getFileName().lastIndexOf(".")
													: ufile.getFileName().length()));
									String folderId = "";
									folderId = folderPathService.getFolderId(token, attachDoc.getAttributes(), "3");
									EcmFolder folder = folderService.getObjectById(token, folderId);
									attachDoc.setFolderId(folderId);
									attachDoc.setAclName(folder.getAclName());

									EcmContent en = new EcmContent();
									if (ufile.getItemStream() != null) {
										en.setInputStream(ufile.getItemStream());
									}
									en.setName(ufile.fileName);
									en.setFormatName(FileUtils.getExtention(ufile.fileName).toLowerCase());
									en.setContentSize(fileList.get(ufile.fileName));
									String attachId = documentService.newObject(token, attachDoc, en);
									newRelation(token, tempId, "附件", attachId, fileNameIndex, null);
								} finally {
									// 删除缓存文件
									if (ufile.getItemStream() != null) {
										ufile.getItemStream().close();
										f = new File(ufile.filePath);
										f.delete();
									}
								}

							}
						}
						
						


					} else {//无子对象关系

						String fileNameStr = getCellValue(sheet.getRow(i).getCell(0));
						String[] fileNames = fileNameStr.split(";");
						if(existsCheck(token, parentType,sheet.getRow(i),attrNames,childStartIndex,sheet.getRow(i).getLastCellNum())) {
							sb.append("第").append(i + 1).append("已存在.").append("\r\n");
							break;
						}
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
									tempId = newDocument(token, parentType, deskFileName, itemStream, sheet.getRow(i),
											fileList, attrNames, null, relationName, number, 1,
											sheet.getRow(i).getLastCellNum(), null, null);
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
									if (!StringUtils.isEmpty(tempId)) {
										if(tempId != null) {
											sucessCount ++;
										}else {
											failedCount ++;
										}
										// 设计文件更新文件传递单属性
										if ("设计文件".equals(parentType)) {
											EcmDocument parentDoc = documentService.getObjectById(token, deliveryId);
											EcmDocument techDoc = null;
											if (!StringUtils.isEmpty(sameFields)) {

												techDoc = documentService.getObjectById(token, tempId);
												if (parentDoc != null) {
													String[] ps = sameFields.split(";");

													for (String p : ps) {
														if (!StringUtils.isEmpty(p)) {
															String[] names = p.split(":");
															String fromName = "";
															String toName = "";
															if (names.length == 2) {
																fromName = names[0];
																toName = names[1];
															} else if (names.length == 1) {
																fromName = names[0];
																toName = fromName;
															}
															techDoc.getAttributes().put(toName,
																	parentDoc.getAttributeValue(fromName));

														}
													}
													documentService.updateObject(token, techDoc, null);
												}
											}
											if (StringUtils.isEmpty(parentDoc.getTitle())) {
												if (techDoc == null) {
													techDoc = documentService.getObjectById(token, tempId);
												}
												if (techDoc != null && !StringUtils.isEmpty(parentDoc.getTitle())) {
													parentDoc.setTitle(techDoc.getTitle());
													documentService.updateObject(token, parentDoc, null);
												}
											}

										}

										if ("设计文件".equals(parentType)
												&& (relationName == null || "".equals(relationName.trim()))) {
											relationName = "设计文件";

										}
									}
								} finally {
									// 删除缓存文件
									if (itemStream != null) {
										itemStream.close();
										f = new File(itemPath);
										f.delete();
									}
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
										f = new File(itemPath);
										f.delete();
									}
								}

							}

						}

//						String itemPath = uploadFolder +getCellValue(sheet.getRow(i).getCell(0));

					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
					sb.append("第").append(i + 1).append("行导入错误：").append(ex.getMessage()).append("\r\n");
					;
					failedCount++;
				}
			}
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		sb.append("完成导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		sb.append("成功行数:").append(sucessCount).append("\r\n");
		sb.append("错误行数:").append(failedCount).append("\r\n");
		return sb.toString();
	}
	
	private boolean existsCheck(String token,String typeName,Row row, Map<Integer, String> attrNames,int start, int end) {
		int idx = getColumnIndex(attrNames, "CODING", start, end);
		String coding = null;
		if(idx>-1) {
			coding = row.getCell(idx)==null?null:row.getCell(idx).getStringCellValue();
		}
		idx = getColumnIndex(attrNames, "REVISION", start, end);
		String revision = null;
		if(idx>-1) {
			revision = row.getCell(idx)==null?null:row.getCell(idx).getStringCellValue();
		}
		String condition = "TYPE_NAME='"+typeName+"'";
		if(!StringUtils.isEmpty(coding)) {
			condition += " AND CODING='"+coding+"'";
			if(!StringUtils.isEmpty(revision )) {
				condition += " AND REVISION='"+revision+"'";
			}
			List<Map<String, Object>>  list = documentService.getObjectMap(token, condition);
			if(list != null && list.size()>0) {
				return true;
			}
		}else {
			return false;
		}
		return false;
	}
	/**
	 * 相关文件是否存在检查
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-10-31 14:18:04 
	 * @Description:       
	 * @param token
	 * @param typeName
	 * @param row
	 * @param attrNames
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean existsCheckRefDoc(String token,String parentId,Row row, Map<Integer, String> attrNames,int start, int end) {
		int idx = getColumnIndex(attrNames, "CODING", start, end);
		String coding = null;
		if(idx>-1) {
			coding = row.getCell(idx)==null?null:row.getCell(idx).getStringCellValue();
		}
		idx = getColumnIndex(attrNames, "REVISION", start, end);
		String revision = null;
		if(idx>-1) {
			revision = row.getCell(idx)==null?null:row.getCell(idx).getStringCellValue();
		}
		String condition = "TYPE_NAME='相关文件'";
		if(!StringUtils.isEmpty(coding)) {
			condition += " AND CODING='"+coding+"'";
			if(!StringUtils.isEmpty(revision )) {
				condition += " AND REVISION='"+revision+"' and exists(select 1 from ecm_relation b where b.PARENT_ID='"+parentId
						+"' AND CHILD_ID=ecm_document.ID)";
			}
			List<Map<String, Object>>  list = documentService.getObjectMap(token, condition);
			if(list != null && list.size()>0) {
				return true;
			}
		}else {
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @param token
	 * @param typeName
	 * @param deskFileName 文件名称，带扩展名
	 * @param itemStream
	 * @param row
	 * @param fileList
	 * @param attrNames
	 * @param parentId
	 * @param relationName
	 * @param batchName
	 * @param start
	 * @param end
	 * @param sameValues
	 * @param sameFields
	 * @return
	 * @throws Exception
	 */
	private String newDocument(String token, String typeName, String deskFileName, FileInputStream itemStream, Row row,
			Map<String, Long> fileList, Map<Integer, String> attrNames, String parentId, String relationName,
			String batchName, int start, int end, Map<String, Object> sameValues, String sameFields) throws Exception {

		int index = 1;
		String docId = null;
		try {
			index = ((Double) row.getCell(getColumnIndex(attrNames, "C_ORDER_INDEX", start, end)).getNumericCellValue())
					.intValue();
		} catch (Exception ex) {
			try {
				index = Integer.parseInt(
						row.getCell(getColumnIndex(attrNames, "C_ORDER_INDEX", start, end)).getStringCellValue());
			} catch (Exception ex1) {

			}
		}
		boolean isReuse = false;
		String desc = null;
		try {
			isReuse = row.getCell(getColumnIndex(attrNames, "DESIGN_REUSE", start, end)).getStringCellValue()
					.equals("复用");
		} catch (Exception ex) {

		}
		// 复用处理
		if (isReuse) {
			String coding = row.getCell(getColumnIndex(attrNames, "CODING", start, end)).getStringCellValue();
			String revision = row.getCell(getColumnIndex(attrNames, "REVISION", start, end)).getStringCellValue();
			String cond = " TYPE_NAME='图纸文件' and CODING='" + coding + "' and REVISION='" + revision + "'";// and
																											// STATUS='利用'
			List<Map<String, Object>> docList = documentService.getObjectsByConditon(token, "GeneralGrid", null,
					new Pager(), cond, null);
			if (docList.size() > 0) {
				docId = docList.get(0).get("ID").toString();
				desc = "复用";
			} else {
				throw new Exception("复用文件不存在，编码：" + coding + ",版本：" + revision);
			}
		} else {
			EcmDocument doc = new EcmDocument();
			doc.setTypeName(typeName);
			if (ImportService.importDocFolderId == null || "".equals(ImportService.importDocFolderId)) {
//				ImportService.importDocFolderId = folderService.getObjectByPath(token, "/移交文档").getId();
				ImportService.importDocFolderId = folderPathService.getFolderId(token, doc.getAttributes(), "3");
			}
			doc.setFolderId(ImportService.importDocFolderId);
			EcmContent content = null;
			if (itemStream != null) {
				content = new EcmContent();
//				content.setName(row.getCell(0).getStringCellValue());
				if (deskFileName != null && !"".equals(deskFileName)) {
					content.setName(deskFileName);
				}

				content.setContentSize(fileList.get(content.getName()));
				content.setFormatName(FileUtils.getExtention(content.getName()).toLowerCase());
				content.setInputStream(itemStream);

				doc.getAttributes().put("C_IMPORT_NAME", content.getName());
				doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
				doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
			}
			if (parentId != null) {
				setValues(doc.getAttributes(), attrNames, row, start, end, sameValues, null);
			} else {
				setValues(doc.getAttributes(), attrNames, row, start, end, sameValues, sameFields);
			}
			setDefaultValues(documentService.getSession(token), doc.getAttributes());
			doc.getAttributes().put("C_BATCH_CODE", batchName);
			docId = documentService.newObject(token, doc, content);
		}
		// 子对象添加关系
		if (parentId != null && docId != null) {
			newRelation(token, parentId, relationName, docId, index, desc);
		}
		return docId;
	}

	private UploadFile getUploadFile(String excelFileName, MultipartFile[] files, String uploadFolder,
			int rowIndex, StringBuilder sb) throws Exception {
		UploadFile ufile = new UploadFile();
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

	/**
	 * 获取文件
	 * 
	 * @Title:
	 * @author Haihong Rong
	 * @date: 2020-9-16 7:54:56
	 * @Description:
	 * @param excelFileName
	 * @param files
	 * @param uploadFolder
	 * @return
	 */
	private String getItemFilePath(String excelFileName, MultipartFile[] files, String uploadFolder) {

		excelFileName = excelFileName.substring(0,
				excelFileName.lastIndexOf(".") > -1 ? excelFileName.lastIndexOf(".") : excelFileName.length());
		String deskFileName = "";
		for (int findx = 0; findx < files.length; findx++) {
			MultipartFile file = files[findx];
			deskFileName = file.getOriginalFilename();
			if (excelFileName.equals(deskFileName.substring(0,
					deskFileName.lastIndexOf(".") > -1 ? deskFileName.lastIndexOf(".") : deskFileName.length()))) {
				return uploadFolder + deskFileName;
			}
		}
		return null;
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

	private void setValues(Map<String, Object> docAttrs, Map<Integer, String> attrNames, Row row, int start, int end,
			Map<String, Object> sameValues, String sameFields) throws Exception {
		String[] sameFlds = null;
		if (sameFields != null && sameFields.length() > 1) {
			sameFlds = sameFields.split(";");
		}
		for (int i = start; i <= end; i++) {
			String attrName = attrNames.get(i);
			if (!StringUtils.isEmpty(attrName)) {
				if (attrName.equalsIgnoreCase("reditionfile")) {
					continue;
				}
				String val = getCellValue(row.getCell(i));
				setValue(docAttrs, attrName, val);
				// 缓存父对象相同值
				if (sameFields != null && sameValues != null) {
					setSameValue(sameValues, sameFlds, attrName, val);
				}
			}
		}
		// 子对象添加相同值
		if (sameFields == null && sameValues != null) {
			for (String key : sameValues.keySet()) {
				String val = sameValues.get(key) != null ? sameValues.get(key).toString().trim() : null;
				setValue(docAttrs, key, val);
			}
		}
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

	private void setValue(Map<String, Object> docAttrs, String attrName, String val) throws Exception {
		if (!StringUtils.isEmpty(val)) {
			EcmAttribute en = CacheManagerOper.getEcmAttributes().get(attrName);
			// EcmFormItem en = getFormItem(frm.getEcmFormItems(),key.toString());
			if (en == null) {
				logger.error("" + attrName + " 不存在.");
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
				docAttrs.put(attrName, val.equals("是") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("true"));
				break;
			}
			case 4:
			case 5:
			case 6:
			case 7:
			case 8: // 数字
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
		 * StringBuilder sb = new StringBuilder(); int sucessCount =0; int failedCount
		 * =0; Map<String,Object> map = new HashMap<String, Object>();
		 * map.put("TYPE_NAME", "移交单"); String number = numberService.getNumber(token,
		 * map);
		 * 
		 * sb.append("开始导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).
		 * append("\r\n");
		 * 
		 * String uploadFolder =
		 * CacheManagerOper.getEcmParameters().get("UploadFolder").getValue();
		 * if(!uploadFolder.endsWith(File.separator)) { uploadFolder += File.separator;
		 * } uploadFolder +=
		 * getSession(token).getCurrentUser().getLoginName()+File.separator+number+File.
		 * separator; File f = new File(uploadFolder); f.mkdirs();
		 * 
		 * if(ImportService.importExcelFolderId==null) {
		 * ImportService.importExcelFolderId = folderService.getObjectByPath(token,
		 * "/表单/移交单").getId(); ImportService.importDocFolderId =
		 * folderService.getObjectByPath(token, "/移交文档").getId(); }
		 * 
		 * EcmDocument doc = new EcmDocument(); doc.getAttributes().put("NAME", number);
		 * doc.getAttributes().put("CODING", number); doc.setTypeName("导入批次");
		 * doc.setFolderId(importExcelFolderId); EcmContent content = new EcmContent();
		 * content.setName(excelSrcFile.getOriginalFilename());
		 * content.setContentSize(excelSrcFile.getSize());
		 * content.setFormatName(FileUtils.getExtention(excelSrcFile.getOriginalFilename
		 * ()).toLowerCase()); content.setInputStream(excelSrcFile.getInputStream());
		 * doc.getAttributes().put("FORMAT_NAME", content.getFormatName());
		 * doc.getAttributes().put("CONTENT_SIZE", content.getContentSize());
		 * 
		 * documentService.newObject(token, doc, content);
		 * 
		 * excelSrcFile.getInputStream().close();
		 * 
		 * Map<String,Long> fileList = new HashMap<String,Long>(); //先存储文件到本地
		 * for(MultipartFile file: files) { String name = file.getOriginalFilename();
		 * String itemPath = uploadFolder +name;
		 * writeToLocal(itemPath,file.getInputStream()); file.getInputStream().close();
		 * fileList.put(name,file.getSize()); }
		 * 
		 * String excelFile = content.getFilePath(); String storePath =
		 * CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();
		 * 
		 * 
		 * //List<Object[]> rows = excelUtil.read(storePath+excelFile, 0);
		 * 
		 * FileInputStream fis = new FileInputStream(storePath+excelFile);
		 * 
		 * Workbook workbook = WorkbookFactory.create(fis); if (fis != null) {
		 * fis.close(); } Sheet sheet = workbook.getSheetAt(0); try {
		 * 
		 * //第一行第一列为类型名称 String parentType =
		 * sheet.getRow(0).getCell(0).getStringCellValue(); //第一行第n列为子对象类型名称，没有不要填写值
		 * String childType = null; int childStartIndex =0; for(int
		 * i=1;i<=sheet.getRow(0).getLastCellNum();i++) {
		 * if(sheet.getRow(0).getCell(i)!=null &&
		 * !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
		 * childType = sheet.getRow(0).getCell(i).getStringCellValue(); childStartIndex
		 * = i; break; } } //第二行第一列为父子相同字段值定义，{父字段名称1}:{子字段名称1};{父字段名称2}:{子字段名称2} String
		 * sameFields = (sheet.getRow(1).getCell(0)!=null?sheet.getRow(1).getCell(0).
		 * getStringCellValue():""); Map<String,Object> sameValues = new
		 * HashMap<String,Object>(); //第三行字段名称 Map<Integer,String> attrNames = new
		 * HashMap<Integer,String>(); for(int
		 * i=0;i<=sheet.getRow(2).getLastCellNum();i++) {
		 * if(sheet.getRow(2).getCell(i)!=null &&
		 * !StringUtils.isEmpty(sheet.getRow(2).getCell(i).getStringCellValue())) {
		 * attrNames.put(i, sheet.getRow(2).getCell(i).getStringCellValue()); } } String
		 * newId = null;
		 * 
		 * //第四行为中文标签，第五行位值 for (int i = 4; i <= sheet.getLastRowNum(); i++) { try {
		 * content = null; //存在子对象
		 * 
		 * if(childStartIndex>1) { //2、3列至少一个不为空
		 * if((sheet.getRow(i).getCell(1)!=null&&!StringUtils.isEmpty(sheet.getRow(i).
		 * getCell(1).getStringCellValue()))
		 * ||(sheet.getRow(i).getCell(2)!=null&&!StringUtils.isEmpty(sheet.getRow(i).
		 * getCell(2).getStringCellValue()))) { sameValues = new
		 * HashMap<String,Object>(); newId = null; newId = newDocument( token,
		 * parentType,null, sheet.getRow(i), fileList, attrNames,null,number,
		 * 1,childStartIndex-1,sameValues,sameFields); if(!StringUtils.isEmpty(newId)) {
		 * newRelation(token, deliveryId, newId, i,null); } } String itemPath =
		 * uploadFolder +sheet.getRow(i).getCell(0).getStringCellValue();
		 * FileInputStream itemStream = null; if(!StringUtils.isEmpty(itemPath)) { File
		 * itemFile= new File(itemPath);
		 * 
		 * if(itemFile.exists()) { itemStream = new FileInputStream(itemFile); }else {
		 * sb.append("第").append(i+1).append("行文件不存在：").append(sheet.getRow(i).getCell(0
		 * ).getStringCellValue()).append("\r\n");; } } String tempId = null; try {
		 * tempId = newDocument( token, childType,itemStream, sheet.getRow(i), fileList,
		 * attrNames,newId,number,
		 * childStartIndex,sheet.getRow(i).getLastCellNum(),sameValues,null); } finally
		 * { //删除缓存文件 if(itemStream!=null) { itemStream.close(); f = new File(itemPath);
		 * f.delete(); } } }else { String itemPath = uploadFolder
		 * +sheet.getRow(i).getCell(0).getStringCellValue(); FileInputStream itemStream
		 * = null; if(!StringUtils.isEmpty(itemPath)) { File itemFile= new
		 * File(itemPath);
		 * 
		 * if(itemFile.exists()) { itemStream = new FileInputStream(itemFile); }else {
		 * sb.append("第").append(i+1).append("行文件不存在：").append(sheet.getRow(i).getCell(0
		 * ).getStringCellValue()).append("\r\n");; } } try { newId = newDocument(
		 * token, parentType,itemStream, sheet.getRow(i), fileList, attrNames,null,
		 * number, 1,sheet.getRow(i).getLastCellNum(),null,null);
		 * if(!StringUtils.isEmpty(newId)) { newRelation(token, deliveryId, newId,
		 * i,null); } } finally { //删除缓存文件 if(itemStream!=null) { itemStream.close(); f
		 * = new File(itemPath); f.delete(); } } } sucessCount ++; }catch(Exception ex)
		 * { ex.printStackTrace();
		 * sb.append("第").append(i+1).append("行导入错误：").append(ex.getMessage()).append(
		 * "\r\n");; failedCount ++; } } }finally { if(workbook != null) {
		 * workbook.close(); } }
		 * sb.append("完成导入:").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).
		 * append("\r\n"); sb.append("成功行数:").append(sucessCount).append("\r\n");
		 * sb.append("错误行数:").append(failedCount).append("\r\n"); return sb.toString();
		 */
		return null;
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
}
