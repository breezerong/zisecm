package com.ecm.pdfConversion.openoffic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
 
/**
 * 一个通用的工具类
 * 
 * @author tanghui
 * 
 */
public class BaseCommonUtil {
 
	protected static final Logger logger = Logger.getLogger(BaseCommonUtil.class);
 
	// 配置图片及视频相关参数常量
	public static final String STARTTIME = "08.010"; // 默认视频开始时间
	public static final String ENDTIME = "08.011"; // 默认视频结束时间
	public static final double IMGPROP = 0.05; // 默认图片缩放比例 1/20
	public static final int IMGMIXSIZE = 125; // 默认图片最小尺寸
	public static final double VIDEO_IMG_SIZE = 0.3; // 默认视频水印图片缩放尺寸
	public static final int VIDEO_IMG_W = 100; // 默认视频水印图片缩放尺寸-宽
	public static final int VIDEO_IMG_H = 100; // 默认视频水印图片缩放尺寸-高
	public static final String IMGWM = "watermark/imgWatermark/"; // 图片水印默认路径
	public static final String AVSPATH = "watermark/avs/"; // AVS文件默认路径
	public static final String TXTIMGPATH = "watermark/txtImg/"; // 文字生成的图片临时路径
	
	
	
	/**
	 * 将路径中包含的空格转换成 "%20"
	 * 
	 * @param path
	 * @return
	 */
	public static String getChRealPath(String path) {
		if (path != null && !path.equals("")) {
			path = path.replaceAll("%20", " ");
		}
		return path;
	}
	
	
	/**
	 * 传入对象
	 * 
	 * @param obj
	 *            前提obj 存在set get方法
	 * @param objDec
	 * @return 返回对象存在的属性值
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static String objectToStringByAttrName(Object obj, String attrName)
			throws Exception {
		Class<? extends Object> objClass = obj.getClass();
		String objStr = "";
		Object value = null;
		if (obj != null && StringUtils.isNotEmpty(attrName)
				&& !attrName.equals("serialVersionUID")) {
			// 获得对象方法集合
			Field[] fields = objClass.getDeclaredFields();
			// 根据字段名找到对应的get方法，null表示无参数
			Method metd = objClass.getMethod("get" + change(attrName), null);
			if (metd != null)
				value = metd.invoke(obj, null); // 调用该字段的get方法
		}
		if (value != null) {
			objStr = value + "";
		}
		return objStr;
	}
	/**
	 * 将src的第一个字母转换为大写，src为空时返回null
	 * 
	 * @param src
	 *            源字符串
	 * @return 字符串
	 */
	public static String change(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}
	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		} else {
			return true;
		}
	}
	// 创建文件上传路径
	public static void mkdir(String path) {
		File fd = null;
		try {
			if (StringUtils.isNotEmpty(path)) {
				path = path.substring(0,
						replaceFliePathStr(path).lastIndexOf("/"));
				fd = new File(path);
				if (!fd.exists()) {
					fd.mkdirs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fd = null;
		}
	}
	/**
	 * 计算转换的总时间
	 * 
	 * @param ms
	 * @return
	 */
	public static String sumTime(long ms) {
		int ss = 1000;
		long mi = ss * 60;
		long hh = mi * 60;
		long dd = hh * 24;
		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;
		String strDay = day < 10 ? "0" + day + "天" : "" + day + "天";
		String strHour = hour < 10 ? "0" + hour + "小时" : "" + hour + "小时";
		String strMinute = minute < 10 ? "0" + minute + "分" : "" + minute + "分";
		String strSecond = second < 10 ? "0" + second + "秒" : "" + second + "秒";
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond + "毫秒" : ""
				+ strMilliSecond + " 毫秒";
		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " "
				+ strMilliSecond;
	}
	/**
	 * 替换 \ 为 /
	 * 
	 * @param filePath
	 * @return
	 */
	public static String replaceFliePathStr(String filePath) {
		if (StringUtils.isNotEmpty(filePath))
			return filePath.replaceAll("\\\\", "\\/");
		else
			return filePath;
	}
	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exitFile(String filePath) {
		if (StringUtils.isNotEmpty(filePath)) {
			filePath = replaceFliePathStr(filePath);
			File file = new File(filePath);
			if (!file.exists()) {
				logger.error("文件 【" + filePath + "】不存在");
				return false;
			}
		}
		return true;
	}
	public static String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&");
		str = str.replaceAll("<", "<");
		str = str.replaceAll(">", ">");
		str = str.replaceAll("\"", "\"");
		return str;
	}
	/**
	 * @param 获取水印的位子
	 *            0:左上角 1:右上角 2:左下角 3:右下角 4:居中 5:平铺
	 * @return
	 */
	public static int getWmDegree(int wmPosition) {
		int position = 3; // 默认为右下角
		if (wmPosition == 01) {
			position = 0;
		} else if (wmPosition == 02) {
			position = 1;
		} else if (wmPosition == 03) {
			position = 2;
		} else if (wmPosition == 04) {
			position = 3;
		} else if (wmPosition == 05) {
			position = 4;
		} else if (wmPosition == 06) {
			position = 5;
		}
		return position;
	}
	/**
	 * @param fontSizeType
	 *            通过水印方位来设置水印的角度 01：完全透明 02：半透明 03 : 模糊
	 * @return
	 */
	public static String getAlpha(int wmAlpha) {
		String alpha = "0.5"; // 默认为半透明
		if (wmAlpha == 01) {
			alpha = "1";
		} else if (wmAlpha == 02) {
			alpha = "0.5";
		} else if (wmAlpha == 03) {
			alpha = "0.2";
		}
		return alpha;
	}
	/**
	 * 获取当前时间的long
	 * 
	 * @return
	 */
	public static Long getDateNum() {
		return System.nanoTime();
	}
	/**
	 * 判断字符串数组中是否包含某字符串元素
	 * 
	 * @param substring
	 *            某字符串
	 * @param source
	 *            源字符串数组
	 * @return 包含则返回true，否则返回false
	 */
	public static boolean isIn(String substring, String[] source) {
		if (StringUtils.isNotEmpty(substring)) {
			if (source == null || source.length == 0) {
				return false;
			}
			for (int i = 0; i < source.length; i++) {
				String aSource = source[i];
				if (aSource.toUpperCase().equals(substring.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 字符转换编码实现
	 * 
	 * @param oldstring
	 * @param oldEncoding
	 * @param newEncoding
	 * @return
	 */
	public static String encodingString(String oldstring, String oldEncoding,
			String newEncoding) {
		OutputStreamWriter outputStreamWriter = null;
		InputStreamReader inputStreamRead = null;
		String temp = null;
		// System.out.println(oldstring);
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					oldstring.getBytes(oldEncoding));
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			char cbuf[] = new char[1024];
			int retVal = 0;
			inputStreamRead = new InputStreamReader(byteArrayInputStream,
					oldEncoding);
			outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream,
					newEncoding);
			while ((retVal = inputStreamRead.read(cbuf)) != -1) {
				outputStreamWriter.write(cbuf, 0, retVal);
			}
			try {
				temp = new String(byteArrayOutputStream.toByteArray(),
						newEncoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStreamRead.close();
				outputStreamWriter.close();
			} catch (Exception e) {
			}
		}
		return temp;
	}
	/**
	 * 通过文件路径获取文件的扩展名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileTypeByPath(String path) {
		if (StringUtils.isNotEmpty(path)) {
			path = replaceFliePathStr(path);
			if (path.indexOf(".") != -1) {
				return path.substring(path.lastIndexOf(".") + 1, path.length())
						.toLowerCase();
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	/**
	 * 创建bat文件
	 * 
	 * @param command
	 *            命令
	 * @param filePath
	 *            输出路径
	 * @return
	 */
	public static boolean createBat(String command, String filePath) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(filePath);
			fw.write(command);
			return exitFile(filePath);
		} catch (IOException e) {
			logger.error(" creatBat 创建" + filePath + "BAT 文件失败！");
			return false;
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(" creatBat 创建" + filePath + "BAT 文件失败！");
					return false;
				}
			}
		}
	}
	/**
	 * 执行BAT文件
	 * 
	 * @param batPath
	 *            bat路径
	 * @return
	 */
	public static boolean execute(String batPath) {
		Process process;
		String line = null;
		try {
			process = Runtime.getRuntime().exec(batPath);
			InputStream fis = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			if (process.waitFor() != 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(" execute 执行" + batPath + "BAT 文件失败！");
			return false;
		}
	}
	/**
	 * * 删除空目录
	 * 
	 * @param dir
	 *            将要删除的目录路径
	 * 
	 */
	public static void doDeleteEmptyDir(String dir) {
		boolean success = (new File(dir)).delete();
		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}
	}
	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
	/**
	 * 获取某个文件夹下的所有文件
	 * 
	 * @param dirPath
	 * @return
	 */
	public static File[] listFiles(String dirPath) {
		File[] fileList = null;
		File f = new File(dirPath);
		if (f.exists()) {
			if (f.isFile()) {
				fileList = new File[1];
				fileList[0] = f;
			} else {
				fileList = f.listFiles(); /* 此处获取文件夹下的所有文件 */
				// for(int i=0;i<fileList.length;i++){
				// System.out.println("全路径:" +
				// fileList[i].getAbsolutePath());//打印全路径，可以更改为你自己需要的方法
				// System.out.println("文件名称:" +
				// fileList[i].getName());//打印全路径，可以更改为你自己需要的方法
				// }
			}
		} else {
			logger.error("文件不存在！");
		}
		return fileList;
	}
	/**
	 * 查询某个文件夹下的所有文件包括子目录
	 * 
	 * @param dirPath
	 * @return
	 */
	public static List<File> listFilesByDir(String dirPath) {
		List<File> listFile = null;
		File f = new File(dirPath);
		if (f.exists()) {
			if (f.isFile()) {
				if (listFile == null)
					listFile = new ArrayList<File>();
				listFile.add(f);
			} else {
				File[] list = f.listFiles();
				if (list != null && list.length != 0) {
					for (int i = 0; i < list.length; i++) {
						listFile = listFiles(listFile,
								list[i].getAbsolutePath());
					}
				}
			}
		} else {
			logger.error("文件不存在！");
		}
		for (int i = 0; i < listFile.size(); i++) {
			System.out.println("文件名称：" + listFile.get(i).getName() + "全路径："
					+ listFile.get(i).getAbsolutePath());
		}
		System.out.println("文件总数：" + listFile.size());
		return listFile;
	}
	public static List<File> listFiles(List<File> listFile, String dirPath) {
		File f = new File(dirPath);
		if (f.exists()) {
			if (f.isFile()) {
				if (listFile == null)
					listFile = new ArrayList<File>();
				listFile.add(f);
			} else {
				File[] list = f.listFiles();
				if (list != null && list.length != 0) {
					for (int i = 0; i < list.length; i++) {
						listFile = listFiles(listFile,
								list[i].getAbsolutePath());
					}
				}
			}
		} else {
			logger.error("文件不存在！");
		}
		return listFile;
	}
	
	 //字符串处理
	 public static String strArr(String str){
	    	StringBuffer strsb = null;
	    	if(StringUtils.isNotBlank(str)){
	    		if(str.indexOf(",") != -1){
	    			String[] strs = str.split(",");
	    			for (String str0 : strs) {
	    				if(strsb == null){
	    					strsb = new StringBuffer();
	    	            }
	    				strsb.append("'" + str0 + "',"); 
					}
	    		}
	    	}
	    	
	    	 if(strsb != null){
	    		 str = strsb.substring(0, strsb.toString().length()-1);
		      }
	    	 
	    	return str;
	  }
	 
//	public static void runbat(String batName) {       
//	  			try {            
//	  				Process ps = Runtime.getRuntime().exec(batName);            
//	  				InputStream in = ps.getInputStream();            
//	  				int c;            
//	  				while ((c = in.read()) != -1) {                
//	  					System.out.print(c);           
//	  					}            
//	  				in.close();            
//	  				ps.waitFor();        
//	  				} catch (Exception e) {            
//	  					e.printStackTrace();
//	  				}
//	  			}
	public static void main(String args[]) {
		// listFilesByDir("F:/360云盘");
		// createBat("\"D:/conver/ffmpeg-win\" -y –i \"D:/conver/5.mp4\" -vf \"movie=D:/conver/watermarklogo.png[th]; [in][th] overlay=10:10 [out]\" \"D:/conver/6.mp4\"","D:\\text.bat");
//		runbat("D://conver//coverVideo.bat WebRoot//watermark//txtImg D://conver//ffmpeg-win");
//		Process process = null;
//		try {
//			process = Runtime.getRuntime().exec(
//					"cmd.exe /c start " + "D://conver//coverVideo.bat");
//			VideoConverHelps.doWaitFor(process);
//			process.destroy();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
 
		// execute("D:\\text.bat");
		// String tarImgPath = "D:/1111/12.kps";
		// mkdir(tarImgPath.substring(0, tarImgPath.lastIndexOf("/")));
		// System.out.println("1:" +
		// Thread.currentThread().getContextClassLoader().getResource(""));
		// System.out.println("2:" +
		// test.class.getClassLoader().getResource(""));
		// System.out.println("3:" + ClassLoader.getSystemResource(""));
		// System.out.println("4:" +
		// test.class.getResource("/xmlutil/navMap.xml").getPath());//test.class文件所在路径
		// System.out.println("5:" + test.class.getResource("/").getPath()); //
		// Class包所在路径，得到的是URL对象，用url.getPath()获取绝对路径String
		// System.out.println("6:" + new File("/").getAbsolutePath());
		// System.out.println("7:" + System.getProperty("user.dir"));
		// System.out.println("8:" +
		// System.getProperty("file.encoding"));//获取文件编码
	}
 
}
