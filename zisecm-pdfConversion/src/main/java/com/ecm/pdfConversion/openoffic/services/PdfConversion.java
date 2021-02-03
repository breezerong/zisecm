package com.ecm.pdfConversion.openoffic.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.service.AuthService;
import com.ecm.core.service.ContentService;
import com.ecm.core.service.DocumentService;
import com.ecm.core.service.QueueItemService;
import com.ecm.icore.service.IEcmSession;
import com.ecm.pdfConversion.openoffic.TransferUtil;

public class PdfConversion {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TransferUtil wordTransferPdfUtil;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private QueueItemService queueItemService;
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private Environment env;
	@Autowired
	private AuthService authService;
	private static IEcmSession ecmSession = null;
	private IEcmSession getEcmSession() throws Exception {
		if (this.ecmSession == null) {
			String userName = env.getProperty("ecm.username");
			this.ecmSession = authService.login("jobs", userName, env.getProperty("ecm.password"));

		}

		return ecmSession;
	}
	@Scheduled(cron="${cron.pdfConversion}")
	public void conversion() {
		try {
			List<EcmQueueItem> list=  queueItemService.getObjects(getEcmSession().getToken(), " NAME='ecm_pdf_conversion' ");
			for(int i=0;list!=null&&i<list.size();i++) {
				EcmContent en=null;
				EcmQueueItem item= list.get(i);
				String docId= item.getObjectId();
				
				List<EcmContent> contents= contentService.getContents(getEcmSession().getToken(), docId,1);
				EcmContent content=null;
				if(contents!=null&&contents.size()>0) {
					content=contents.get(0);
				}else {
					log.error("指定的文件没有主文件，id="+docId);
					continue;
				}
				String fullPath = CacheManagerOper.getEcmStores().get(content.getStoreName()).getStorePath();
				String newPath= wordTransferPdfUtil.transferWordToPdf(fullPath+content.getFilePath());
				File file=new File(newPath);
				FileInputStream itemStream = new FileInputStream(file);
				en = new EcmContent();
				en.setName(content.getName());
				en.setContentSize(file.length());
				en.setFormatName(content.getFormatName());
				en.setInputStream(itemStream);
				documentService.addRendition(getEcmSession().getToken(), docId, en);
				
				queueItemService.deleteObject(getEcmSession().getToken(), item);
				
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}
