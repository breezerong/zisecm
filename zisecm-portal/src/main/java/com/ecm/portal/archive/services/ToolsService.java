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
	public String updateByExcel(String token, MultipartFile[] files, MultipartFile excelSrcFile) throws Exception {
		//BatchMountFile
		StringBuilder sb = new StringBuilder();
		int sucessCount = 0;
		int failedCount = 0;
		sb.append("开始更新").append(DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")).append("\r\n");
		Workbook workbook = WorkbookFactory.create(excelSrcFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);

		if (excelSrcFile.getInputStream() != null) {
			excelSrcFile.getInputStream().close();
		}
		try {

			// 第一行字段名称
			Map<Integer, String> attrNames = new HashMap<Integer, String>();

			for (int i = 0; i <= sheet.getRow(1).getLastCellNum(); i++) {
				if (sheet.getRow(0).getCell(i) != null
						&& !StringUtils.isEmpty(sheet.getRow(0).getCell(i).getStringCellValue())) {
					attrNames.put(i, sheet.getRow(0).getCell(i).getStringCellValue());
				}else if(sheet.getRow(1).getCell(i) != null && !StringUtils.isEmpty(sheet.getRow(1).getCell(i).getStringCellValue())){
					attrNames.put(i, sheet.getRow(1).getCell(i).getStringCellValue());
					//childStartIndex = i;
				}
			}

			// 第二行为中文标签，第三行位值
			for (int i = 2; i <= sheet.getLastRowNum(); i++) {
				try {
					String id = sheet.getRow(i).getCell(1).getStringCellValue();
					if (StringUtils.isEmpty(id)) {
						continue;
					}
					EcmDocument doc = documentService.getObjectById(token, id);
					if (doc != null) {
						Map<String, Object>  values = this.setValues(token,doc, attrNames, sheet.getRow(i), 2,
								sheet.getRow(i).getLastCellNum());
						documentService.updateObject(token, values);
						if(sheet.getRow(i).getCell(0).getStringCellValue() != null) {
							for(MultipartFile file: files) {
								if(file.getOriginalFilename().equals(sheet.getRow(i).getCell(0).getStringCellValue())) {
									EcmContent en = new EcmContent();
									en.setName(file.getOriginalFilename());
									en.setContentSize(file.getSize());
									en.setInputStream(file.getInputStream());
									en.setFormatName(FileUtils.getExtention(file.getOriginalFilename()));
									en.setContentType(1);
									documentService.mountFile(token, id, en);
									continue;
								}
							}
						}
						sucessCount++;
					} else {
						sb.append("第").append(i + 1).append("行更新错误：").append(id).append("不存在\r\n");
						
						failedCount++;
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
	
}
