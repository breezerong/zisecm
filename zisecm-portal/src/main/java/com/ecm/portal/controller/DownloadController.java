package com.ecm.portal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.portal.entity.DownloadFile;

@Controller
public class DownloadController extends ControllerAbstract{
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private DocumentService documentService;
	
	public String downLoadOne(HttpServletResponse response,String id) {
		try {
			EcmContent en = contentService.getPrimaryContent(getToken(),id);
			//EcmDocument doc = documentService.getObjectById(getToken(), id);
			InputStream iStream = contentService.getContentStream(getToken(),en);
			// 清空response
	        response.reset();
	        // 设置response的Header
	        response.setCharacterEncoding("UTF-8");
	        response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(en.getName(), "UTF-8"));
	        response.addHeader("Content-Length", "" + en.getContentSize());
	        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	//        if(en.getFormatName().equalsIgnoreCase("pdf")){
	//        	response.setContentType("application/form-data");
	//        }else {
	        	response.setContentType("application/octet-stream");
	//        }
	        byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = iStream.read(buffer)) != -1) {
				toClient.write(buffer, 0, bytesRead);
			}
			iStream.close();
	        toClient.flush();
	        toClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "成功";
	}
	
	@RequestMapping(value = "/down", method = RequestMethod.POST)
	@ResponseBody
	public String down(HttpServletResponse response,@RequestBody String metaData) throws Exception {
		
		List<String> ids= JSONObject.parseArray(metaData, String.class);
		if(ids!=null&&ids.size()==1) {
			String id=ids.get(0);
			return downLoadOne(response,id);
		}
		
		List<DownloadFile> files = new ArrayList<DownloadFile>();
		String objType = null;
		for(int i=0;i<ids.size();i++) {
//			Map<String,Object> id=JSON.parseObject(ids.get(i));
			String id=ids.get(i);
			EcmContent en = contentService.getPrimaryContent(getToken(),id);
			EcmDocument doc = documentService.getObjectById(getToken(), id);
			
			String fileName = doc.getAttributes().get("SUB_TYPE")!=null && doc.getAttributes().get("SUB_TYPE").toString().length()>0?
					doc.getAttributes().get("SUB_TYPE").toString():doc.getCoding();
			if(objType == null) {
				objType = doc.getAttributes().get("OBJECT_TYPE")!=null?doc.getAttributes().get("OBJECT_TYPE").toString()
						:doc.getCoding().split("-")[0];
			}
			if(fileName.toLowerCase().indexOf("fm")>-1) {
				fileName = "00@封面."+doc.getFormatName();
			}else if(fileName.toLowerCase().indexOf("ml")>-1) {
				fileName = "00@目录."+doc.getFormatName();
			}else {
				fileName += "@" + doc.getName()+"."+doc.getFormatName();
			}
			String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			File f = new File(fullPath+en.getFilePath());
			DownloadFile df = new DownloadFile();
			df.setDownFile(f);
			df.setFileName(fileName);
			files.add(df);
		}
		
		downLoadFiles(objType, files, response);

		return "成功";
	}
	private static void isChartPathExist(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
		
	public static HttpServletResponse downLoadFiles(String objType, List<DownloadFile> files, HttpServletResponse response) throws Exception {
		String zipFilename = null;
		try {
			// List<File> 作为参数传进来，就是把多个文件的路径放到一个list里面
			// 创建一个临时压缩文件

			// 临时文件可以放在CDEF盘中，但不建议这么做，因为需要先设置磁盘的访问权限，最好是放在服务器上，方法最后有删除临时文件的步骤
			
			isChartPathExist("c:/download");
			if(objType==null||objType.length()==0) {
				Date date = new Date(); //获取当前的系统时间。
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ; //使用了默认的格式创建了一个日期格式化对象。
				String time = dateFormat.format(date); //可以把日期转换转指定格式的字符串
				zipFilename = "c:/download/"+time+".zip";
			}else {
				zipFilename = "c:/download/"+objType+".zip";
			}
			File file = new File(zipFilename);
			file.createNewFile();
			if (!file.exists()) {
				file.createNewFile();
			}
			response.reset();
			// response.getWriter()
			// 创建文件输出流
			FileOutputStream fous = new FileOutputStream(file);
			ZipOutputStream zipOut = new ZipOutputStream(fous);
			zipFile(files, zipOut);
			zipOut.close();
			fous.close();
			return downloadZip(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(zipFilename != null) {
				File f = new File(zipFilename);
				f.deleteOnExit();
			}
		}
		return response;
	}

	/**
	 * 把接受的全部文件打成压缩包
	 * 
	 * @param List<File>;
	 * @param org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(List<DownloadFile> files, ZipOutputStream outputStream) {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			DownloadFile file =  files.get(i);
			zipFile(file, outputStream);
		}
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 * 
	 * @param File
	 * @param org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(DownloadFile inputFile, ZipOutputStream ouputStream) {
		try {
			if (inputFile.getDownFile().exists()) {
				if (inputFile.getDownFile().isFile()) {
					FileInputStream IN = new FileInputStream(inputFile.getDownFile());
					BufferedInputStream bins = new BufferedInputStream(IN, 512);
					ZipEntry entry = new ZipEntry(inputFile.getFileName());
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
					// 关闭创建的流对象
					bins.close();
					IN.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
		if (file.exists() == false) {
			System.out.println("待压缩的文件目录：" + file + "不存在.");
		} else {
			try {
				// 以流的形式下载文件。
				InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();

				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");

				// 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(file.getName().getBytes("GB2312"), "ISO8859-1"));
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					File f = new File(file.getPath());
					f.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}


	@RequestMapping("/downlo")
	@ResponseBody
	public void downLoad(HttpServletResponse response){
	    String filename="劳动合同.doc";
	    String filePath = "C:\\Users\\Liang Qizhao\\Desktop\\" ;
	    File file = new File(filePath + "/" + filename);
	    if(file.exists()){ //判断文件父目录是否存在
	    	try {
			filename = java.net.URLEncoder.encode(filename, "UTF-8");
			filename = new String(filename.getBytes(), "iso-8859-1");
	        response.setContentType("application/force-download");
	        response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
	        
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = null; //文件输入流
	        BufferedInputStream bis = null;
	        
	        OutputStream os = null; //输出流
	       
	            os = response.getOutputStream();
	            fis = new FileInputStream(file); 
	            bis = new BufferedInputStream(fis);
	            int i = bis.read(buffer);
	            while(i != -1){
	                os.write(buffer);
	                i = bis.read(buffer);
	            }
	            bis.close();
	            fis.close();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("----------file download" + filename);
	    }
	}
}
