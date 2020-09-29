package com.ecm.cnpe.exchange.service.impl;

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
import com.ecm.cnpe.exchange.entity.StatusEntity;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeIED;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeInterface;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeRelevantDoc;
import com.ecm.cnpe.exchange.service.LogicOption4CnpeTransfer;
import com.ecm.cnpe.exchange.utils.OptionLogger;
import com.ecm.common.util.CollectionUtil;
import com.ecm.common.util.DateUtils;
import com.ecm.common.util.EcmStringUtils;
import com.ecm.common.util.ExcelUtil;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
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
import com.ecm.core.entity.ExcTransfer;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.core.exception.SqlDeniedException;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.EcmService;
import com.ecm.core.service.ExcSynDetailService;
import com.ecm.core.service.ExcTransferServiceImpl;
import com.ecm.core.service.FolderPathService;
import com.ecm.core.service.FolderService;
import com.ecm.core.service.NumberService;
import com.ecm.core.service.RelationService;
import com.ecm.icore.service.IEcmSession;
@Service
public class IEDImportService extends EcmService {
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
	
	@Autowired
	private LogicOption4CnpeInterface logicOptionInterfaceService;
	@Autowired
	private LogicOption4CnpeRelevantDoc logicOptionRelevantService;
	@Autowired
	private LogicOption4CnpeTransfer logicOptionTransferService;
	@Autowired
	private LogicOption4CnpeIED logicOptionCnpeIEDService;
	@Autowired
	private ExcSynDetailService detailService;
	
	@Autowired
	private ExcTransferServiceImpl excTransferService;
	
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
	public String importExcel(String token,String deliveryId,String relationName, MultipartFile  excelSrcFile,MultipartFile[] files) throws Exception {
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
		
		if(IEDImportService.importExcelFolderId==null) {
			IEDImportService.importExcelFolderId = folderService.getObjectByPath(token, "/表单/批量导入单").getId();
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
						isReuse = sheet.getRow(i).getCell(getColumnIndex(attrNames, "DESIGN_REUSE",1,sheet.getRow(i).getLastCellNum())).getStringCellValue().equals("复用");
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
							newId = newDocument( token, parentType,null, sheet.getRow(i),  
									fileList, attrNames,null,relationName,number, 1,
									childStartIndex-1,sameValues,sameFields);
							if(!StringUtils.isEmpty(newId)) {
								newRelation(token, deliveryId,relationName, newId, i,null);
							}
						}
						String itemPath = uploadFolder +getCellValue(sheet.getRow(i).getCell(0));
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
							tempId = newDocument( token, childType,itemStream,
									sheet.getRow(i),  fileList, attrNames,
									newId,relationName,number, childStartIndex,sheet.getRow(i).getLastCellNum(),
									sameValues,null);
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
						try {
							int fh = getColumnIndex(attrNames, "TYPE",1,sheet.getRow(i).getLastCellNum());
							if(fh!=-1) {			//IED批次操作自带字段，找到这个说明肯定是IED批次操作
							EcmDocument e1 = new EcmDocument();
							setValues(e1.getAttributes(), attrNames,sheet.getRow(i),1,sheet.getRow(i).getLastCellNum(),sameValues,null);
							Map attrs = e1.getAttributes();
							if(attrs.get("CODING")==null||sheet.getRow(i).getCell(getColumnIndex(attrNames, "TYPE",1,sheet.getRow(i).getLastCellNum())).getStringCellValue()==null) {			//第一波唯一性检查，CODING和TYPE必用
								sb.append("第"+i+"行IED外部编码或操作类型出现空值，已略过");
								continue;
							}
							
							//String types = sheet.getRow(i).getCell(getColumnIndex(attrNames, "TYPE",1,sheet.getRow(i).getLastCellNum())).getStringCellValue();
							if(attrs.get("CODING")!=null) {		
							String coding = attrs.get("CODING").toString();
							EcmDocument temp = new EcmDocument(); 							//找目标IED的ID，丢进方法里进行逻辑处理
							String cond ="TYPE_NAME='IED' and status ='审核中' and coding = '"+coding+"'";
							List<Map<String,Object>> result =documentService.getObjectMap(token, cond);
							if(result.size()==0) {
								sb.append("第"+i+"行IED的状态不为审核中,无法进行操作,已略过\r\n");
								continue;
							}
							temp.setAttributes(result.get(0));
							String id = temp.getId();										//找到ID,准备进行下一波操作
							if(sheet.getRow(i).getCell(getColumnIndex(attrNames, "TYPE",1,sheet.getRow(i).getLastCellNum())).getStringCellValue()!=null) {	//先判断操作类型是否为空
								String type= sheet.getRow(i).getCell(getColumnIndex(attrNames, "TYPE",1,sheet.getRow(i).getLastCellNum())).getStringCellValue();
								if(type.equals("驳回")&&attrs.get("C_REJECT_COMMENT")!=null) {
									String comment = e1.getAttributeValue("C_REJECT_COMMENT").toString();
									reject(token,id,comment);
									continue;
								}
								
								else if(type.equals("驳回")&&attrs.get("C_REJECT_COMMENT")==null) {
									sb.append("第"+i+"行IED缺少驳回理由,无法进行操作,已略过\r\n");
									continue;
								}
								if(type.equals("接收")) {									//走接收逻辑
									accept(token,id,sb);
									//sucessCount++;											
									continue;
								}
							}
							
							}
							}					//至此IED批次操作代码结束
							int k = getColumnIndex(attrNames, "C_COMMENT4",1,sheet.getRow(i).getLastCellNum());
							if(k!=-1) {	//批量反馈，找一个"IED导入"模板里没有的字段进行判断，如果存在，就证明肯定是IED批量反馈
								EcmDocument e1 = new EcmDocument();
					
								setValues(e1.getAttributes(), attrNames,sheet.getRow(i),1,sheet.getRow(i).getLastCellNum(),sameValues,null);
								Map attrs = e1.getAttributes();
								if(attrs.get("CODING")!=null&&attrs.get("C_COMMENT4")!=null&&attrs.get("C_ITEM4_DATE")!=null) {					//必填项空数据影响检查
								String Coding = attrs.get("CODING").toString();
								String comment = attrs.get("C_COMMENT4").toString();
								String date = attrs.get("C_ITEM4_DATE").toString();	
								Feedback(Coding,comment,date,token);
								continue;}
								if(attrs.get("CODING")==null||attrs.get("C_COMMENT4")==null||attrs.get("C_ITEM4_DATE")==null
										) {
									sb.append("第"+i+"行数据必填项出现空字段,请检查,该行操作已略过\r\n");
									continue;
								}
								
							}					//IED唯一性检查
							EcmDocument e1 = new EcmDocument();
							setValues(e1.getAttributes(), attrNames,sheet.getRow(i),1,sheet.getRow(i).getLastCellNum(),sameValues,null);
							Map attrs = e1.getAttributes();
							if(attrs.get("C_ITEM_STATUS1")==null||attrs.get("CODING")==null||attrs.get("C_PROJECT_NAME")==null) {
								
								sb.append("第"+i+"行数据必填项出现空字段,请检查,该行操作已略过\r\n");
								continue;
							}
							String status1 = sheet.getRow(i).getCell(getColumnIndex(attrNames, "C_ITEM_STATUS1",1,sheet.getRow(i).getLastCellNum())).getStringCellValue();
							String coding= sheet.getRow(i).getCell(getColumnIndex(attrNames, "CODING",1,sheet.getRow(i).getLastCellNum())).getStringCellValue();
							String project= sheet.getRow(i).getCell(getColumnIndex(attrNames, "C_PROJECT_NAME",1,sheet.getRow(i).getLastCellNum())).getStringCellValue();
				
							
							
							String cond = " TYPE_NAME='IED' and CODING='"+coding+"' and status='新建' and c_project_name='"+project+"'" ;
							List<Map<String,Object>> result =documentService.getObjectMap(token, cond);
							if(result.size()!=0) {
							sb.append("第"+i+"行的IED已存在,略过进行下一步操作").append("\r\n");
							continue;
							}
							if(status1.equals("修订")) {
							boolean isUpdate=update(token, parentType,itemStream, sheet.getRow(i),  
									fileList, attrNames,null,relationName, number, 1,sheet.getRow(i).getLastCellNum(),
									null,null,sb,i);			//方法用来升版，无论成功与否，都不应继续执行新建方法
							continue;							//报错信息和具体行数传入
							}
							
							
							newId = newDocument( token, parentType,itemStream, sheet.getRow(i),  
									fileList, attrNames,null,relationName, number, 1,sheet.getRow(i).getLastCellNum(),
									null,null);
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
								newRelation(token, deliveryId,relationName, newId, i,null);
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
	
	
	public void Feedback(String Coding,String Comment,String date,String token) throws NoPermissionException, AccessDeniedException, EcmException {					//IED批量反馈
		EcmDocument temp = new EcmDocument();
		Map<String,Object> mp = new HashMap();
		String cond = " TYPE_NAME='IED' and CODING='"+Coding+"' and status='已生效'" ;
		List<Map<String,Object>> result =documentService.getObjectMap(token, cond);
		mp = result.get(0);
		temp.setAttributes(mp);
		temp.addAttribute("C_ITEM4_DATE", date);
		temp.addAttribute("C_COMMENT4", Comment);
		documentService.updateObject(token, temp, null);
		
	}
	
	
	public boolean update(String token, String typeName,FileInputStream itemStream,
			Row row, Map<String,Long> fileList,
			Map<Integer,String> attrNames,String parentId, String relationName,
			String batchName,int start,int end,Map<String,Object> sameValues, String sameFields,StringBuilder sb,int i) throws Exception {
			Map <String,Object> attrsd = new HashMap();			//doc属性集
			Map <String,Object> attr = new HashMap();			
			EcmDocument doc = new EcmDocument();
			EcmDocument temp = new EcmDocument();
			if(parentId!=null) {
				setValues(doc.getAttributes(), attrNames,row,start,end,sameValues,null);
			}else {
				setValues(doc.getAttributes(), attrNames,row,start,end,sameValues,sameFields);
			}
			attrsd = doc.getAttributes();
			if(attrsd.get("C_IDENTIFY")==null||attrsd.get("C_EX1_DATE")==null||
					attrsd.get("C_EX2_DATE")==null||attrsd.get("C_OTHER_CODING")==null) {
				sb.append("第"+i+"行变更必填项出现空值,请检查,该行操作已略过\r\n");
				return false;
			}
			String idt = attrsd.get("C_IDENTIFY").toString(); 				//变更标识
			String comment1=attrsd.get("C_EX1_DATE").toString();
			String comment2=attrsd.get("C_EX2_DATE").toString();
			
			if(idt.equals("CAN")) {
				doc.addAttribute("C_COMMENT1", comment1);
				doc.addAttribute("C_COMMENT2", comment2);
			}
			
			String coding = row.getCell(getColumnIndex(attrNames, "CODING",start,end)).getStringCellValue();
			String Oldcoding= row.getCell(getColumnIndex(attrNames, "C_OTHER_CODING",start,end)).getStringCellValue();
			String cond = " TYPE_NAME='IED' and CODING='"+Oldcoding+"' and status='已生效'" ;	
			String nowCond =  " TYPE_NAME='IED' and CODING='"+coding+"'and status='新建'";
			List<Map<String,Object>> result =documentService.getObjectMap(token, cond);			//查找现版本IED
			List<Map<String,Object>> Nowresult =documentService.getObjectMap(token, nowCond);	//查找新建后的IED是否和待提交中的IED重复
			if(Nowresult.size()!=0) {
				sb.append("第"+i+"行IED升版后外部编码与系统现有新建IED冲突，已略过\r\n");
				return false;
			}
						
			if(result.size()==0) {
				sb.append("第"+i+"行IED无对应已生效IED，无法进行升版操作，已略过\r\n");
				return false;		//不存在已生效IED，无法进行升版操作
			}						//现在已经确认有已生效IED，现在进行数据比较
			temp.setAttributes(result.get(0));
			String id = temp.getId();
			String tempD1="";
			String tempD2="";
			if(temp.getAttributeValue("C_EX1_DATE")==null) {
				tempD1="";
			}
			if(temp.getAttributeValue("C_EX2_DATE")==null) {
				tempD2="";
			}
			else if(temp.getAttributeValue("C_EX1_DATE")==null&&temp.getAttributeValue("C_EX2_DATE")==null) {
	
			tempD1=temp.getAttributeValue("C_EX1_DATE").toString();
			tempD2=temp.getAttributeValue("C_EX2_DATE").toString();
			}
			if(!tempD1.equals(comment1)) {												//分别比较内/外部计划
				doc.addAttribute("C_COMMENT1", comment1);
			}
			if(!tempD2.equals(comment2)) {
				doc.addAttribute("C_COMMENT2", comment2);
			}										//数据比较完成，现在进行升版操作
			EcmDocument res = new EcmDocument();
			attr = doc.getAttributes();
			attr.put("C_IS_RELEASED", 0);
			res=documentService.checkIn(token, id,attr, null, false);
			documentService.updateStatus(token, res.getId(), "新建");
			documentService.updateStatus(token, temp.getId(), "变更中");
			OptionLogger.logger(token, detailService, res, "变更",
					res.getAttributeValue("C_COMPANY")!=null?res.getAttributeValue("C_COMPANY").toString():"");
			sb.append("第"+i+"行IED完成升版\r\n");
			
				return true;						//升版操作
			
	}
	
	
	private String newDocument(String token, String typeName,FileInputStream itemStream,
			Row row, Map<String,Long> fileList,
			Map<Integer,String> attrNames,String parentId, String relationName,
			String batchName,int start,int end,Map<String,Object> sameValues, String sameFields) throws Exception {
		
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
		boolean isReuse = false;
		String desc = null;
		try {
			isReuse = row.getCell(getColumnIndex(attrNames, "DESIGN_REUSE",start,end)).getStringCellValue().equals("复用");
		}catch(Exception ex) {
			
		}
		//复用处理
		if(isReuse)
		{
			String coding = row.getCell(getColumnIndex(attrNames, "CODING",start,end)).getStringCellValue();
			String revision = row.getCell(getColumnIndex(attrNames, "REVISION",start,end)).getStringCellValue();
			String cond = " TYPE_NAME='图纸文件' and CODING='"+coding+"' and REVISION='"+revision+"'";//and STATUS='利用'
			List<Map<String, Object>> docList = documentService.getObjectsByConditon(token,"GeneralGrid",null,new Pager(), cond,null);
			if(docList.size()>0) {
				docId = docList.get(0).get("ID").toString();
				desc = "复用";
			}else {
				throw new Exception("复用文件不存在，编码："+coding+",版本："+revision);
			}
		}else {
			EcmDocument doc = new EcmDocument();
			doc.setTypeName(typeName);
			
			int index2  = getColumnIndex(attrNames, "C_EX3_DATE",1,row.getLastCellNum());
			Cell cell = row.getCell(index2);
			cell.setCellType(CellType.STRING);
			String FU= cell.getStringCellValue();
			if(!FU.equals("")) {
				doc.addAttribute("C_ITEM_STATUS3","Y");
			}
			else {
				doc.addAttribute("C_ITEM_STATUS3","N");
			}
			
			if(IEDImportService.importDocFolderId==null||"".equals(IEDImportService.importDocFolderId)) {
//				ImportService.importDocFolderId = folderService.getObjectByPath(token, "/移交文档").getId();
				IEDImportService.importDocFolderId= folderPathService.getFolderId(token, doc.getAttributes(), "3");
			}
			doc.setFolderId(IEDImportService.importDocFolderId);
			EcmContent content = null;
			if(itemStream!=null) {
				content = new EcmContent();
				content.setName(row.getCell(0).getStringCellValue());
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
			doc.getAttributes().put("C_BATCH_CODE", batchName);
			docId = documentService.newObject(token, doc, content);
		}
		//子对象添加关系
		if(parentId!=null && docId != null) {
			newRelation(token, parentId,relationName, docId, index,desc);
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
	
	private void newRelation(String token,String parentId,
			String relationName,String childId,
			int orderIndex,String description) throws EcmException {
		if(!StringUtils.isEmpty(parentId)) {
			EcmRelation rel = new EcmRelation();
			rel.setParentId(parentId);
			rel.setChildId(childId);
			if(relationName==null||"".equals(relationName)) {
				rel.setName("irel_children");
			}else {
				rel.setName(relationName);
			}
			
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
	public void accept(String token,String id,StringBuilder sb) throws Exception {
		EcmDocument doc= documentService.getObjectById(token, id);
		String currentStatus= doc.getStatus();
		if(currentStatus==null||"".equals(currentStatus)) {
			currentStatus="新建";
		}
		if("新建".equals(currentStatus)) {
			doc.addAttribute("c_item_date", new Date());
		}
		
		String nextStatus= StatusEntity.getNextDcStatusValue(currentStatus, doc.getTypeName(), false);
		doc.setStatus(nextStatus);
		if("已生效".equals(nextStatus) || "已确认".equals(nextStatus)) {
			doc.addAttribute("C_RECEIVER",this.getSession(token).getCurrentUser().getUserName());
			doc.addAttribute("C_RECEIVE_DATE", new Date());
		}
		if("驳回".equals(currentStatus) || "已驳回".equals(currentStatus)) {
			doc.addAttribute("C_REJECTOR",this.getSession(token).getCurrentUser().getUserName());
			doc.addAttribute("C_REJECT_DATE", new Date());
		}
		if("IED".equals(doc.getTypeName())) {
			if("已生效".equals(nextStatus)) {
				//doc.addAttribute("C_ITEM_STATUS2", "Y");
				logicOptionCnpeIEDService.IEDOption(token, doc);
				OptionLogger.logger(token, detailService, doc, "接收", doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				
			}
			documentService.updateObject(token, doc, null);
			//
			/**
			 * if(判断状态){
			 * 	OptionLogger.logger(detailService, doc, "动作", doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
			 * }
			 */
			if("审核中".equals(nextStatus)) {
				OptionLogger.logger(token, detailService, doc, "提交", doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
			}
			//
		}else if("图文传真,会议纪要".contains(doc.getTypeName())){
			documentService.updateObject(token, doc, null);
			doc.addAttribute("C_IS_RELEASED", 1);
		}else {
			if("待确认".equals(nextStatus)) {
				//验证数据是否存在
				if("文件传递单".equals(doc.getTypeName())) {
					logicOptionTransferService.transferSubValidate(token, doc);
				}else if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
					logicOptionInterfaceService.interfaceValidateOption(token, doc);
				}else {
					logicOptionRelevantService.relevantValidateOption(token,doc);
				}
			}
			if("已确认".equals(nextStatus)) {
				if("文件传递单".equals(doc.getTypeName())) {
//					logicOptionTransferService.transferOption(token, doc,false);
					logicOptionTransferService.transferOption(token, detailService, doc, false);
				}else if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
					logicOptionInterfaceService.interfaceOption(token, doc);
				}else {
					logicOptionRelevantService.relevantOption(token,doc,false);
				}
				doc.addAttribute("C_IS_RELEASED", 1);
			}
			documentService.updateObject(token, doc, null);
			
		}
		
//		OptionLogger.logger(detailService, doc, "CNPE接收", 
//				doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		if("驳回".equals(currentStatus)) {
			OptionLogger.logger(token, detailService, doc, "驳回提交", 
					doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}else {
			OptionLogger.logger(token, detailService, doc, 
					doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
		}
	}
	public void reject(String token,String id,String comment) {
		try {
				EcmDocument doc= documentService.getObjectById(token, id);
				String currentStatus= doc.getStatus();
				if(currentStatus==null||"".equals(currentStatus)||"新建".equals(currentStatus)) {
					return;
				}
				
				String previousStatus= StatusEntity.getPreviousDcStatusValue(currentStatus, doc.getTypeName(), false);
				if(previousStatus == null) {
					previousStatus = "驳回";
				}
				doc.setStatus(previousStatus);
				doc.addAttribute("C_REJECT_COMMENT", comment);
				doc.addAttribute("C_REJECTOR", this.getSession(token).getCurrentUser().getUserName());
				doc.addAttribute("C_REJECT_DATE", new Date());
				documentService.updateObject(token, doc, null);
				
				
				// 如果是驳回申请，需要更新移交单属性
				
				String condition = "DOC_ID='"+id+"' AND ITEM_TYPE=2";
				List<ExcTransfer> tlist = excTransferService.selectByCondition(condition);
				// 最多一条移交记录
				if(tlist.size()>0) {
					
					ExcTransfer obj = tlist.get(0);
					if("待确认".equalsIgnoreCase(obj.getStatus1())){
						obj.setStatus1("已驳回");
						obj.setRejecter(this.getSession(token).getCurrentUser().getUserName());
						obj.setRejectDate(new Date());
						excTransferService.updateObject(obj);
					}
				}
				
				
				if("接口信息传递单".equals(doc.getTypeName())||"接口信息意见单".equals(doc.getTypeName())) {
					logicOptionInterfaceService.interfaceRejectOption(token, doc);
				}else if("文件传递单".equals(doc.getTypeName())){
					//待扩展需求
				}else {
					logicOptionRelevantService.relevantRejectOption(token,doc);
				}
				
				if("IED".equals(doc.getTypeName())) {
					OptionLogger.logger(token, detailService, doc, "驳回", 
							doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				}else {
					OptionLogger.logger(token, detailService, doc,
							doc.getAttributeValue("C_COMPANY")!=null?doc.getAttributeValue("C_COMPANY").toString():"");
				}
				
				
			
		}catch (Exception e) {
			// TODO: handle exception
	
		}	
	}
	
	
}
