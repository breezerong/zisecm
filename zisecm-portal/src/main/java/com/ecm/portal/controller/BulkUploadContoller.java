package com.ecm.portal.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ecm.common.util.FileUtils;
import com.ecm.common.util.JSONUtils;
import com.ecm.core.ActionContext;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.Pager;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.util.ExcelUtil;

/**
 * 图纸操作
 * @author Haihong Rong
 *
 */
@Controller
public class BulkUploadContoller extends ControllerAbstract{

	private String gridviewName="DrawingGrid";
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ContentService contentService;

	
	/**
	 * 获取新建图纸列表
	 * @param argStr
	 * @return
	 */
	@RequestMapping(value = "/drawing/getDrawing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDrawing(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();
		try {
			Map<String, Object> args =null;
			if(argStr==null) {
				args =new HashMap<String,Object>();
			}else {
				args = JSONUtils.stringToMap(argStr);
			}
			int pageSize = Integer.parseInt(args.get("pageSize").toString());
			int pageIndex = Integer.parseInt(args.get("pageIndex").toString());
			Pager pager = new Pager();
			pager.setPageIndex(pageIndex);
			pager.setPageSize(pageSize);
			mp.put("data", getDocList(argStr, pager));
			mp.put("pager", pager);
			mp.put("code", ActionContext.SUCESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mp.put("code", ActionContext.FAILURE);
			mp.put("message", e.getMessage());
		}
		return mp;
	}
	
	
	/**
	 * 获取新建图纸数目
	 * @param argStr map字符串
	 * @return
	 */
	@RequestMapping(value = "/drawing/getDrawingCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDrawingCount(@RequestBody String argStr) {
		Map<String, Object> mp = new HashMap<String, Object>();

			try {
				mp.put("data", getDocCount(argStr));
				mp.put("code", ActionContext.SUCESS);
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			}

		return mp;
	}
	
	@RequestMapping(value="/drawing/uploadDrawing",method=RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> newDocument(String metaData, @RequestParam("files") MultipartFile[] files) throws Exception {
		Map<String, Object> mp = new HashMap<String, Object>();
		Map<String, Object> args =null;
		String id = null;
		
		if(files!=null&&files.length>0) {//add by liaojiyong
			int i=0;
			if(metaData==null) {
				args =new HashMap<String,Object>();
			}else {
				args = JSONUtils.stringObjectToMap(metaData);
			}
			for (MultipartFile uploadFile : files) {
				i++;
				String filename=uploadFile.getOriginalFilename();
				if (filename.startsWith("MyBatchImport")&&filename.endsWith(".xlsx")){
					 ExcelDataProcessor(uploadFile,files);
					break;
				}
			}
		}

		mp.put("data", id);
		mp.put("code", ActionContext.SUCESS);
		
		return mp;
		
	}
	/**
	 * 删除图纸文件
	 * @param metaData
	 * @return
	 */
	@RequestMapping(value="/drawing/delDrawing",method=RequestMethod.POST)
	@ResponseBody	
	public Map<String,Object> delDrawing(@RequestBody String metaData){
		Map<String, Object> mp = new HashMap<String, Object>();
		List<String> ids= JSONObject.parseArray(metaData, String.class);
		for(int i=0;i<ids.size();i++) {
//			Map<String,Object> id=JSON.parseObject(ids.get(i));
			String id=ids.get(i);
			try {
				documentService.deleteObject(getToken(),id);
			} catch (EcmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
			}
		}
		mp.put("code", ActionContext.SUCESS);
		return mp;
	}
	/**
	 * 更改为状态为已审批
	 * @param metaData
	 * @return
	 */
	@RequestMapping(value="/drawing/updateStatus",method=RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> updateStatus(@RequestBody String metaData){
		
		//已审批
		Map<String, Object> mp = new HashMap<String, Object>();
//		List<String> ids= JSONObject.parseArray(metaData, String.class);
		 Map<String,Object> obj =  JSONObject.parseObject(metaData); 
		 String status=(String)obj.get("STATUS");
		 String strList=(String) obj.get("list");
		 List<String> ids= JSONObject.parseArray(strList, String.class);
		 try {
			for(int i=0;i<ids.size();i++) {
	//			Map<String,Object> id=JSON.parseObject(ids.get(i));
				String id=ids.get(i);
				
					documentService.updateStatus(getToken(),id, status);
				
			}
			mp.put("code", ActionContext.SUCESS);
		 } catch (AccessDeniedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mp.put("code", ActionContext.TIME_OUT);
				mp.put("message", e.getMessage());
		 }
		
		return mp;
	
	}
	
	/**
	 * 根据状态获取文档数目
	 * @param argStr
	 * @return
	 * @throws AccessDeniedException 
	 */
	private long getDocCount(String argStr) throws AccessDeniedException  {
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		String condition = args.get("condition")==null?"":args.get("condition").toString();
		String status = args.get("status").toString();
		if(condition!=null&&condition.trim().length()>0) {
			condition += " and ";
		}
		condition += " CREATOR='"+getSession().getCurrentUser().getUserName()+"' and STATUS='"+status+"'";
		return documentService.getObjectCount(getToken(),gridviewName, "", condition);
	}
	/**
	 * 根据状态获取图纸文档清单
	 * @param argStr 请求Map
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String, Object>> getDocList(String argStr,Pager pager) throws Exception{
		Map<String, Object> args = JSONUtils.stringToMap(argStr);
		
		String condition = args.get("condition").toString();
		String status = args.get("status").toString();
		if(condition!=null&&condition.trim().length()>0) {
			condition += " and ";
		}
		condition += " CREATOR='"+getSession().getCurrentUser().getUserName()+"' and STATUS='"+status+"'";
		String orderBy = " CODING ASC";
		return  documentService.getObjects(getToken(),gridviewName,null,pager,condition,orderBy);
	}
	
	
	public void ExcelDataProcessor(MultipartFile  excelSrcFile,MultipartFile[] files) {
		String baseFilename=null;
		this.excelSrcFile=excelSrcFile;
		int rowIndex=0;
		int columnIndex=0;
		OutputStream out =null;
		EcmContent enExcel = new EcmContent();
		enExcel.createId();
		int filePathIndex=0;
		EcmContent en = null; 
		String bulkUploadSequentialNumber = "0";
		String typeName = "";
		int typeRowNumber=2;
		int recordRownumber=4;
		try {
 			baseFilename = FilenameUtils.getBaseName(this.excelSrcFile.getOriginalFilename());
			responseFileName = baseFilename + "_ValidationFile."
					+ FilenameUtils.getExtension(this.excelSrcFile.getOriginalFilename());
			this.wBook = WorkbookFactory.create(excelSrcFile.getInputStream());
			this.sheet = wBook.getSheetAt(0);
			CellStyle baseCellStyle = sheet.getRow(0)
					.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellStyle();
			cellStyleErrRow = wBook.createCellStyle();
			cellStyleErrRow.cloneStyleFrom(baseCellStyle);
			cellStyleErrRow.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			cellStyleErrRow.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyleErrCell = wBook.createCellStyle();
			cellStyleErrCell.cloneStyleFrom(baseCellStyle);
			cellStyleErrCell.setFillForegroundColor(IndexedColors.RED1.getIndex());
			cellStyleErrCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			 out = new FileOutputStream(responseFileName);
			ExcelUtil a = new ExcelUtil();
			excelInputStream=excelSrcFile.getInputStream();
			List<Object[]> excelData = a.read(excelSrcFile.getInputStream(),0);
			int excelDataSize = excelData.size();
			if(excelDataSize>5) {
				Object[] attributes=excelData.get(1);
				for (int i = 0; i < attributes.length; i++) {
					if("TYPE_NAME".equalsIgnoreCase(attributes[i].toString())){
						typeName=excelData.get(typeRowNumber)[i].toString();
					}else
					if("CODING".equalsIgnoreCase(attributes[i].toString())){//比较导入批次，临时使用CODING
						if(excelData.get(typeRowNumber)[i].toString().length()!=32) {
							bulkUploadSequentialNumber=enExcel.getId();
						}
					}
				}
				attributes=excelData.get(recordRownumber);
				for (int i = 0; i < attributes.length; i++) {
					if("TITLE".equalsIgnoreCase(attributes[i].toString())){//比较文件名称，零时使用Title
						filePathIndex=i;
						break;
					}
				}
				
				int fileNumber=files.length;
				
				
				for (int i = 5; i < excelDataSize; i++) {//导入数据
					rowIndex=i;
					Object[] excelRowData = excelData.get(i);
					HashMap<String,Object> args =new HashMap<String,Object>();
					for (int j = 0; j < excelRowData.length; j++) {
						args.put(excelData.get(recordRownumber)[j].toString(), excelRowData[j]);
					}
					boolean isFileExists=false;
					for (int j2 = 0; j2 < fileNumber; j2++) {
						if(files[j2].getOriginalFilename().equals(excelRowData[filePathIndex])) {
							en = new EcmContent();
							en.createId();
							en.setName(files[j2].getOriginalFilename());
							en.setContentSize(files[j2].getSize());
							en.setFormatName(FileUtils.getExtention(files[j2].getOriginalFilename()).toLowerCase());
							en.setInputStream(files[j2].getInputStream());
							args.put("FORMAT_NAME", en.getFormatName());
							args.put("CONTENT_SIZE", en.getContentSize());
							isFileExists=true;
							break;
						} 
					}
					if(!isFileExists) {
						this.handleErrorCell(this.getSession().getCurrentUser().getLoginName(),rowIndex,columnIndex,new HashSet(),"file no exists");
					}
					if(CacheManagerOper.getEcmParameters().get("DrawingFolder") != null) {
						args.put("FOLDER_ID", CacheManagerOper.getEcmParameters().get("DrawingFolder").getValue());
					}
					args.put("STATUS", "新建");
					args.put("TYPE_NAME",typeName);
					args.put("CODING",bulkUploadSequentialNumber);
					EcmDocument doc = new EcmDocument();
					   doc.setAttributes(args);
				   documentService.newObject(getToken(),doc,en);
				} 
			}
			
		} catch (Exception e) {
			isValidFileFormat = false;
			try {
				this.handleErrorCell(this.getSession().getCurrentUser().getLoginName(),rowIndex,columnIndex,new HashSet(),e.getMessage());
			} catch (AccessDeniedException e1) {
				e1.printStackTrace();
			}
 		}finally {
			try {
				if(out!=null && wBook!=null && excelInputStream !=null) {
					wBook.write(out);
					wBook.close();
					out.flush();
					out.close();
					File responseFile= new File(responseFileName);
					FileInputStream responseFileIS = new FileInputStream(responseFile);
 					enExcel.setName(responseFileName);
 					enExcel.setContentSize(responseFile.length());
 					enExcel.setFormatName(FileUtils.getExtention(responseFileName).toLowerCase());
 					enExcel.setInputStream(responseFileIS);
					HashMap<String,Object> args =new HashMap<String,Object>();
					args.put("NAME", responseFileName);
					args.put("NAME", responseFileName);
					args.put("TYPE_NAME", "图纸");
					args.put("STATUS", "新建");
					args.put("FORMAT_NAME", enExcel.getFormatName());
					if(bulkUploadSequentialNumber.length()==32) {
						args.put("CODING", bulkUploadSequentialNumber);
					}else {
						args.put("CODING", enExcel.getId());

					}
					EcmDocument doc = new EcmDocument();
					doc.setAttributes(args);
				    documentService.newObject(getToken(),doc,enExcel);
					excelInputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public void handleErrorCell(String userName, int rowIndex, int columnIndex, Set<Integer> errorRows,
			String errorMessage) {
		Row errorRow = this.sheet.getRow(rowIndex);
		Cell errorCell = errorRow.getCell(columnIndex, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		CreationHelper factory = this.wBook.getCreationHelper();
		Comment comment = errorCell.getCellComment();
		if (!errorRows.contains(rowIndex) && comment == null) {
			for (int i = 0; i < errorRow.getLastCellNum(); ++i) {
				Cell cCell = errorRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				if (cCell != null) {
					cCell.setCellStyle(this.cellStyleErrRow);
				}
			}

			errorRows.add(rowIndex);
		}

		String existingComment = null;
		if (comment == null) {
			ClientAnchor anchor = factory.createClientAnchor();
			anchor.setCol1(errorCell.getColumnIndex());
			anchor.setCol2(errorCell.getColumnIndex() + 1);
			anchor.setRow1(errorRow.getRowNum());
			anchor.setRow2(errorRow.getRowNum() + 3);
			Drawing drawing = this.sheet.createDrawingPatriarch();
			comment = drawing.createCellComment(anchor);
		} else {
			existingComment = comment.getString().getString();
		}

		if (!StringUtils.isNotBlank(existingComment) || !existingComment.contains(errorMessage)) {
			RichTextString str = factory.createRichTextString(
					StringUtils.isNotBlank(existingComment) ? existingComment + "\n" + errorMessage : errorMessage);
			comment.setString(str);
			comment.setAuthor(userName);
			errorCell.setCellStyle(this.cellStyleErrCell);
		}
	}

	public String getCellDataAsString(Row row, int cellIndex, String expectedDatatype) {
		String cellValue = null;
		if (row.getCell(cellIndex) == null) {
			return (String) cellValue;
		} else {
			CellType type = row.getCell(cellIndex).getCellType();
 			if (CellType.NUMERIC.equals(type)  && expectedDatatype.equalsIgnoreCase("DATE")) {
				return String.valueOf(row.getCell(cellIndex).getDateCellValue().getTime());
			} else if (CellType.NUMERIC.equals(type)) {
				return this.formatter.formatCellValue(row.getCell(cellIndex));
			} else if (CellType.STRING.equals(type)) {
				String val = this.formatter
						.formatCellValue(row.getCell(cellIndex, MissingCellPolicy.CREATE_NULL_AS_BLANK));
				return StringUtils.isNotBlank(val) ? val : null;
			} else {
				return (String) (CellType.BLANK.equals(type) ? cellValue : cellValue);
			}
		}
	}


	public int getLastRowNumberFromSheet() {
		int lastRow = 0;

		for (int r = 0; r <= this.sheet.getLastRowNum() ; r++) {
			if( this.sheet.getRow(r) != null
					&& this.sheet.getRow(r).getCell(0).getCellStyle() != null) {
				lastRow=r;
			} 
			;
		}

		return lastRow;
	}

	public int getHeaderRowIndex() {
		return this.headerRowIndex;
	}
	private DataFormatter formatter;
	private Workbook wBook;
	private Sheet sheet;
	private CellStyle cellStyleErrRow;
	private CellStyle cellStyleErrCell;
	int headerRowIndex;
	int metadataStartColumnIndex;
	int lastRowIndex;
	private MultipartFile excelSrcFile;
	private String responseFileName = null;
	private InputStream excelInputStream = null;
	private boolean isValidFileFormat=false;
 }
