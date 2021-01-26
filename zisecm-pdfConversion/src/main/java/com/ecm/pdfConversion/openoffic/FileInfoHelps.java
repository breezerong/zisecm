package com.ecm.pdfConversion.openoffic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.tomcat.jni.FileInfo;
import org.apache.tools.ant.types.resources.FileResource;
import org.jboss.logging.Logger;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component(value="fileInfoHelps")
@Transactional
public class FileInfoHelps {
	Logger logger = Logger.getLogger(FileInfoHelps.class);
	
	 @Autowired 
	 ThreadManager threadManager;
	 
	 @Autowired
     private TransferUtil wordTransferPdfUtil;
	
	
	//预览文件保存在文件的当前目录下的preview的文件下
	String savePath = null;
	/**
	*预览文件生成代码
	*/
	public void generatePreviewFile(CountDownLatch end,String webPath) throws IOException{
		
		//预览文件线程操作
		threadManager.execute(end,new Runnable() {
			@Override
			public void run() {
				try {
					savePath= wordTransferPdfUtil.transferWordToPdf(webPath);
					//创建保存路径
					if(!new File(savePath).exists()){
						new File(savePath).mkdirs();
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}finally {
					//通知线程执行完成
					end.countDown();
				}
			}
		});
		
	}
	
	
}
