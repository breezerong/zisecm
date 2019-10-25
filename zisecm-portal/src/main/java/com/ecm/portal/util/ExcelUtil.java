package com.ecm.portal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

 public class ExcelUtil {

	// excel文件路径
	private String path = "";
	
	// 写入excel时，是否自动扩展列宽度来符合内容。
	private boolean autoColumnWidth = false;

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
		HSSFWorkbook workbook = makeWorkBook(sheetName, fieldName, data);
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

 	public void makeStreamExcel(String excelName, String sheetName, String[] fieldName, List<Object[]> data,
			HttpServletResponse response) throws IOException {
		OutputStream os = null;
		response.reset(); // 清空输出流
		os = response.getOutputStream(); // 取得输出流
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(excelName.getBytes(), "ISO-8859-1")); // 设定输出文件头
		response.setContentType("application/msexcel"); // 定义输出类型
		// 在内存中生成工作薄
		HSSFWorkbook workbook = makeWorkBook(sheetName, fieldName, data);
		os.flush();
		workbook.write(os);
	}

 	private HSSFWorkbook makeWorkBook(String sheetName, String[] fieldName, List<Object[]> data) {
		// 用来记录最大列宽,自动调整列宽。
		Integer collength[] = new Integer[fieldName.length];

		// 产生工作薄对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 产生工作表对象
		HSSFSheet sheet = workbook.createSheet();
		// 为了工作表能支持中文,设置字符集为UTF_16
		workbook.setSheetName(0, sheetName);
		// 产生一行
		HSSFRow row = sheet.createRow(0);
		// 产生单元格
		HSSFCell cell;
		// 写入各个字段的名称
		for (int i = 0; i < fieldName.length; i++) {
			// 创建第一行各个字段名称的单元格
			cell = row.createCell((short) i);
			// 设置单元格内容为字符串型
			cell.setCellType(CellType.STRING);
			// 为了能在单元格中输入中文,设置字符集为UTF_16
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			// 给单元格内容赋值
			cell.setCellValue(new HSSFRichTextString(fieldName[i]));
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
				cell = row.createCell((short) j);
				// 设置单元格字符类型为String
				cell.setCellType(CellType.STRING);
				tempCellContent = (tmp[j] == null) ? "" : tmp[j].toString();
				cell.setCellValue(new HSSFRichTextString(tempCellContent));

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

 	public void setAutoColumnWidth(boolean autoColumnWidth) {
		this.autoColumnWidth = autoColumnWidth;
	}

}
