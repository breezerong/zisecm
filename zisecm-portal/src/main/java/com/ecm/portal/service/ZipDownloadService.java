package com.ecm.portal.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ecm.common.util.DateUtils;

@Service
public class ZipDownloadService {

	    /**
	     * 创建zip文件
	     *
	     * @param files 文件
	     * @param path  暂存目录
	     */
	    public  void createZipFiles(List<File> files,List<String> fileNames, HttpServletResponse response) {
	        try {
	            //List<File> 作为参数传进来，就是把多个文件的路径放到一个list里面
	            //创建一个临时压缩文件
	            //临时文件可以放在CDEF盘中，但不建议这么做，因为需要先设置磁盘的访问权限，最好是放在服务器上，方法最后有删除临时文件的步骤
	        	
	            response.reset();
	            response.setHeader("Content-disposition",
	    				"attachment; filename=" + new String(("打包文件"+DateUtils.currentDate("yyyy-MM-dd HH:mm:ss")+".zip").getBytes(), "ISO-8859-1")); // 设定输出文件头
	            //创建文件输出流
		        OutputStream out = null;
	            out = response.getOutputStream();
	            ZipOutputStream zipOutput = new ZipOutputStream(out);
	            zipFile(files,fileNames, zipOutput);
	            zipOutput.close();
	            out.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	 /**
	     * 根据输入的文件与输出流对文件进行打包
	     */
	    private  void zipFile(File inputFile,String fileName, ZipOutputStream outputStream) {
	        try {
	            if (inputFile.exists()) {
	                if (inputFile.isFile()) {
	                    FileInputStream IN = new FileInputStream(inputFile);
	                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
	                    ZipEntry entry = new ZipEntry(fileName);
	                    outputStream.putNextEntry(entry);

	                    // 向压缩文件中输出数据
	                    int nNumber;
	                    byte[] buffer = new byte[512];
	                    while ((nNumber = bins.read(buffer)) != -1) {
	                        outputStream.write(buffer, 0, nNumber);
	                    }

	                    // 关闭创建的流对象
	                    bins.close();
	                    IN.close();
	                } else {
	                    try {
	                        File[] files = inputFile.listFiles();
	                        if (files != null) {
	                            for (File file : files) {
	                                zipFile(file, fileName,outputStream);
	                            }
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	    /**
	     * 把接受的全部文件打成压缩包
	     */
	    private  void zipFile(List<File> files,List<String> fileNames, ZipOutputStream outputStream) {
	    	for (int i = 0; i < files.size(); i++) {
	            File file = (File) files.get(i);
	            String fileName =  fileNames.get(i);
	            zipFile(file,fileName, outputStream);

			}

	    }
	    
	    /**
	     * 处理文件内容乱码
	     *
	     * @param file
	     * @param originFileName
	     * @param request
	     * @param response
	     */
	    public  void downloadFile(File file, String originFileName, HttpServletRequest request, HttpServletResponse response) {
	        if (file.exists()) {
	            try {
	                // 以流的形式下载文件。
	                InputStream input = new BufferedInputStream(new FileInputStream(file.getPath()));
	                byte[] buffer = new byte[input.available()];
	                input.read(buffer);
	                input.close();
	                // 清空response
	                response.reset();
	                generate(originFileName, request, response);

	                OutputStream output = new BufferedOutputStream(response.getOutputStream());
	                output.write(buffer);
	                output.flush();
	                output.close();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	 /**
	     * 处理文件名乱码和浏览器兼容问题
	     *
	     * @param fileName
	     * @param request
	     * @param response
	     * @throws UnsupportedEncodingException
	     */
	    private  void generate(String fileName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/octet-stream");
	        response.setHeader("success", "true");

	        String userAgent = request.getHeader("User-Agent");
	        String formFileName;
	        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
	            // 针对IE或者以IE为内核的浏览器：
	            formFileName = URLEncoder.encode(fileName, "UTF-8");
	        } else {
	            // 非IE浏览器的处理：
	            formFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        }

	        //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
	        response.setHeader("Content-Disposition", "attachment;filename=" + formFileName);
	    }
	}
 