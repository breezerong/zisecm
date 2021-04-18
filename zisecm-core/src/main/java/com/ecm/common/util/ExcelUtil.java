package com.ecm.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 public class ExcelUtil {

	// excel文件路径
	private String path = "";
	
	// 写入excel时，是否自动扩展列宽度来符合内容。
	private boolean autoColumnWidth = false;

	public boolean isAutoColumnWidth() {
		return autoColumnWidth;
	}


	/**
	 * 无参构造函数 默认
	 */
	public ExcelUtil() {
		
	}

 
	public List<Object[]> read(String path, int sheetOrder) throws FileNotFoundException, IOException, InvalidFormatException {
		return read(null,path,sheetOrder);
	}	
	
	public List<Object[]> read(InputStream fis, int sheetOrder) throws FileNotFoundException, IOException, InvalidFormatException {
		return read(fis,null,sheetOrder);
	}	
 	@SuppressWarnings("incomplete-switch")
	public List<Object[]> read(InputStream fis,String path,int sheetOrder) throws FileNotFoundException, IOException, InvalidFormatException {
		if(fis== null) {
			 fis = new FileInputStream(path);
		} 
		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(sheetOrder);
		// 用来记录excel值
		List<Object[]> valueList = new LinkedList<Object[]>();
		// 循环遍历每一行、每一列。
		for (Row row : sheet) {
			// 每一行
			Object[] rowObject = null;
			for (Cell cell : row) {
				// cell.getCellType是获得cell里面保存的值的type
				switch (cell.getCellType()) {
				case BOOLEAN:
					// 得到Boolean对象的方法
					rowObject = CollectionUtil.addObjectToArray(rowObject, cell.getBooleanCellValue());
					break;
				case NUMERIC:
					// 先看是否是日期格式
					if (DateUtil.isCellDateFormatted(cell)) {
						// 读取日期格式
						rowObject = CollectionUtil.addObjectToArray(rowObject, cell.getDateCellValue());
					} else {
						DecimalFormat df = new DecimalFormat();
						// 单元格的值,替换掉,
						String value = df.format(cell.getNumericCellValue()).replace(",", "");
						// 读取数字
						rowObject = CollectionUtil.addObjectToArray(rowObject, value);
					}
					break;
				case FORMULA:
					// 读取公式
					rowObject = CollectionUtil.addObjectToArray(rowObject, cell.getCellFormula());
					break;
				case STRING:
					// 读取String
					rowObject = CollectionUtil.addObjectToArray(rowObject, cell.getRichStringCellValue().toString());
					break;
				}
			}
			// 将这行添加到list。
			valueList.add(rowObject);
		}
		return valueList;
	}

 	public String read(int sheetOrder, int colum, int row) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(sheetOrder);
		Row rows = sheet.getRow(row - 1);
		Cell cell = rows.getCell(colum - 1);
		String content = cell.getStringCellValue();
		return content;
	}

 	public void write(int sheetOrder, int colum, int row, String content) throws Exception {
		FileInputStream fis = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(sheetOrder);
		Row rows = sheet.createRow(row - 1);
		Cell cell = rows.createCell(colum - 1);
		cell.setCellValue(content);
		FileOutputStream fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.close();

	}

 	public int getSheetLastRowNum(int sheetOrder) throws IOException, InvalidFormatException {
		FileInputStream fis = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(fis);
		if (fis != null) {
			fis.close();
		}
		Sheet sheet = workbook.getSheetAt(sheetOrder);
		return sheet.getLastRowNum();
	}

 	public void makeExcel(String sheetName, String[] fieldName, List<Object[]> data) throws IOException {
		// 在内存中生成工作薄
		XSSFWorkbook workbook = makeWorkBook(sheetName, fieldName, data,false);
		// 截取文件夹路径
		String filePath = path.substring(0, path.lastIndexOf("\\"));
		// 如果路径不存在，创建路径
		File file = new File(filePath);
		// System.out.println(path+"-----------"+file.exists());
		if (!file.exists())
			file.mkdirs();
		FileOutputStream fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.close();
	}

 	public void makeExcel(String pathExt,String sheetName, String[] fieldName, List<Object[]> data) throws IOException {
		// 在内存中生成工作薄
		XSSFWorkbook workbook = makeWorkBook(sheetName, fieldName, data,false);
		// 截取文件夹路径
		String filePath = pathExt.substring(0, pathExt.lastIndexOf("\\"));
		// 如果路径不存在，创建路径
		File file = new File(filePath);
		// System.out.println(path+"-----------"+file.exists());
		if (!file.exists())
			file.mkdirs();
		FileOutputStream fileOut = new FileOutputStream(pathExt);
		workbook.write(fileOut);
		fileOut.close();
	}
 	public void makeStreamExcel(String excelName, String sheetName, String[] fieldName, List<Object[]> data,
			HttpServletResponse response,boolean hideFirstRow) throws IOException {
		OutputStream os = null;
		response.reset(); // 清空输出流
		os = response.getOutputStream(); // 取得输出流
		response.setHeader("Content-disposition",
				"attachment; filename=" + URLEncoder.encode(excelName,"UTF-8")); // 设定输出文件头
		response.setContentType("application/msexcel"); // 定义输出类型
		// 在内存中生成工作薄
		XSSFWorkbook workbook = makeWorkBook(sheetName, fieldName, data , hideFirstRow);
		os.flush();
		workbook.write(os);
//		os.flush();
		workbook.close();
	}
 	
 	public void makeStreamExcel(String excelName, String templatePath,Map<String,Object> data,
			HttpServletResponse response) throws IOException {
		OutputStream os = null;
		response.reset(); // 清空输出流
		os = response.getOutputStream(); // 取得输出流
		response.setHeader("Content-disposition",
				"attachment; filename=" + URLEncoder.encode(excelName,"UTF-8")); // 设定输出文件头
		response.setContentType("application/msexcel"); // 定义输出类型
		// 在内存中生成工作薄
		Workbook workbook = makeWorkBook(data,templatePath);
		os.flush();
		workbook.write(os);
//		os.flush();
		workbook.close();
	}
 	
 	private XSSFWorkbook makeWorkBook(String sheetName, String[] fieldName, List<Object[]> data,
 			short firstRowNum,boolean hideFirstRow) {
		// 用来记录最大列宽,自动调整列宽。
		Integer collength[] = new Integer[fieldName.length];
		// 产生工作薄对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 产生工作表对象
		XSSFSheet sheet = workbook.createSheet();
		// 为了工作表能支持中文,设置字符集为UTF_16
		workbook.setSheetName(0, sheetName);
		// 产生一行
		XSSFRow row = sheet.createRow(firstRowNum);
		
		
		row.setZeroHeight(hideFirstRow);
		// 产生单元格
		XSSFCell cell;
		// 写入各个字段的名称
		for (int i = 0; i < fieldName.length; i++) {
			// 创建第一行各个字段名称的单元格
			cell = row.createCell((short) i);
			// 设置单元格内容为字符串型
			cell.setCellType(CellType.STRING);
			// 为了能在单元格中输入中文,设置字符集为UTF_16
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			// 给单元格内容赋值
			cell.setCellValue(new XSSFRichTextString(fieldName[i]));
			// 初始化列宽
			collength[i] = fieldName[i].getBytes().length;
		}
		// 临时单元格内容
		String tempCellContent = "";
		// 写入各条记录,每条记录对应excel表中的一行
		for (int i = 0; i < data.size(); i++) {
			Object[] tmp = data.get(i);
			// 生成一行
			row = sheet.createRow(i + 1);
			for (int j = 0; j < tmp.length; j++) {
				
				if(tmp[j] == null){
					cell = row.createCell((short) j);
					cell.setCellType(CellType.STRING);
					tempCellContent = "";
					cell.setCellValue(new XSSFRichTextString(tempCellContent));
				}else {
					if (tmp[j] instanceof Date) {
						cell = row.createCell((short) j);
						XSSFDataFormat format = workbook.createDataFormat();
						XSSFCellStyle style = workbook.createCellStyle();
						style.setDataFormat(format.getFormat("yyyy-MM-dd"));
						
						Date dateValue = (Date) tmp[j];
						cell.setCellValue(dateValue);
						cell.setCellStyle(style);
					}else {
						cell = row.createCell((short) j);
						cell.setCellType(CellType.STRING);
						tempCellContent = tmp[j].toString();
						cell.setCellValue(new XSSFRichTextString(tempCellContent));
					}
				}
				
				

				// 如果自动调整列宽度。
				if (autoColumnWidth) {
					if (j >= collength.length) { // 标题列数小于数据列数时。
						collength = CollectionUtil.addObjectToArray(collength, tempCellContent.getBytes().length);
					} else {
						// 如果这个内容的宽度大于之前最大的，就按照这个设置宽度。
						if (collength[j] < tempCellContent.getBytes().length) {
							collength[j] = tempCellContent.getBytes().length;
						}
					}
				}
			}
		}

		// 自动调整列宽度。
		if (autoColumnWidth) {
			// 调整列为这列文字对应的最大宽度。
			for (int i = 0; i < fieldName.length; i++) {
				sheet.setColumnWidth(i, collength[i] * 2 * 256);
			}
		}
		return workbook;
	}
 	
 	private Workbook makeWorkBook(Map<String,Object> data,
 			String templatePath) throws IOException {
 		Workbook wb = null;
		// TODO Auto-generated method stub
		 //excel文件路径
        String ext = templatePath.substring(templatePath.lastIndexOf("."));
       String excelPathNew = templatePath.substring(0,templatePath.lastIndexOf("."))+"_new"+ext;
        try {
        	FileChannel in = new FileInputStream(templatePath).getChannel(),
                    outNio = new FileOutputStream(excelPathNew).getChannel();
            in.transferTo(0, in.size(), outNio);
            //String encoding = "GBK";
            File excel = new File(excelPathNew);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在
            	
            	
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return null;
                }
                
                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: "+firstRowIndex);
                System.out.println("lastRowIndex: "+lastRowIndex);
                Map<String,Integer> cellIndexMp=new HashMap<String, Integer>();
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                System.out.println(cell.toString());
                                String cellStr=cell.getStringCellValue();
                                if(cellStr!=null&&cellStr.contains("#{")) {
                                	String bfStr=cellStr.substring(0,cellStr.indexOf("#{"));
                                	String cellStrNew=cellStr.substring(cellStr.indexOf("#{")+2,cellStr.indexOf("}"));
                                	String cellStrComm=cellStr.substring(cellStr.indexOf("}")+1);
                                	if(cellStrNew.startsWith("Date(")) {

                        				String tempStr = cellStrNew.replace("Date(", "").replace(")", "");
                        				String[] temps = tempStr.split(",");
                        				String attrName = temps[0];
                        				String format = temps[1];
                        				Date dt = new Date();
                        				if(!attrName.equalsIgnoreCase("now")) {
                        					try {
                        						dt = (Date)data.get(attrName);
                        					}catch (Exception e) {
                        						// TODO: handle exception
                        						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        						dt=formatter.parse((String) data.get(attrName));
                        					}
                        				}
                        				if(dt==null) {
                        					throw new Exception(attrName+"为空！");
                        				}
                        				SimpleDateFormat sdf = new SimpleDateFormat(format);
                        				cell.setCellValue(bfStr+sdf.format(dt)+cellStrComm);
                        			
                                	}else {
                                		cell.setCellValue(data.get(cellStrNew)+cellStrComm);
                                	}
                                }else if(cellStr!=null&&cellStr.contains("${")) {
                                	String cellStrNew=cellStr.substring(cellStr.indexOf("${")+2,cellStr.indexOf("}"));
                                	cellIndexMp.put(cellStrNew, cIndex);
                                }
                            }
                        }
                    }
                }
                List<Map<String,Object>> rowData=(List<Map<String, Object>>) data.get("data");
                
//                if(rowData!=null&&rowData.size()>0) {
//                	sheet.shiftRows(sheet.getLastRowNum()-1, sheet.getLastRowNum()+1, 1,true,false);
//                }
              
                for(int x=0;rowData!=null&&x<rowData.size();x++) {
                	Map<String,Object> dt= rowData.get(x);
                	sheet.shiftRows(sheet.getLastRowNum()-1, sheet.getLastRowNum()+1, 1,true,false);
                	Row r=  sheet.createRow(sheet.getLastRowNum()-2);
                	Set<String> keys= cellIndexMp.keySet();
                	Iterator<String> it= keys.iterator();
                	while(it.hasNext()) {
                		String key= it.next();
                		Integer v= cellIndexMp.get(key);
                		Cell c= r.createCell(v);
                		c.setCellValue(dt.get(key)!=null?dt.get(key).toString():"");
                		
                	}
                }
//              Row r=  sheet.createRow(lastRowIndex-1);
//              Cell c= r.createCell(0);
//               c.setCellValue("xxxxx");
               
//               File f=new File(excelPathNewModel);
//               FileOutputStream out =new FileOutputStream(f);
//               
//       		wb.write(out);
//       		out.flush();
////       		os.flush();
//       		wb.close();
               
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return wb;
    
 	}
 	
 	private XSSFWorkbook makeWorkBook(String sheetName, String[] fieldName, List<Object[]> data,boolean hideFirstRow) {
 		return makeWorkBook(sheetName,fieldName,data,(short)0,hideFirstRow);
 	}

 	public void setAutoColumnWidth(boolean autoColumnWidth) {
		this.autoColumnWidth = autoColumnWidth;
	}
 	

}
