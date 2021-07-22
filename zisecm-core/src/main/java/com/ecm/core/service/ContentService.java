package com.ecm.core.service;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ecm.core.PermissionContext.ObjectPermission;
import com.ecm.core.cache.manager.CacheManagerOper;
import com.ecm.core.dao.EcmContentMapper;
import com.ecm.core.db.DBFactory;
import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmDocument;
import com.ecm.core.entity.EcmQueueItem;
import com.ecm.core.exception.AccessDeniedException;
import com.ecm.core.exception.EcmException;
import com.ecm.core.exception.NoPermissionException;
import com.ecm.icore.service.IContentService;
import com.google.common.base.Strings;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
/**
 * 内容服务
 * @author Haihong Rong
 * @date 2019年7月21日 上午8:30:45
 */
@Service
@Scope("prototype")
public class ContentService extends EcmObjectService<EcmContent> implements IContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
			
	@Autowired
	private EcmContentMapper contentMapper;

	@Autowired
	private QueueItemService queueItemService;

	
	@Override
	public List<EcmContent> getObjects(String token, String docId, int contentType) {
		if(contentType<1)
		{
			return contentMapper.getAllContents(docId);
		}
		return contentMapper.getContents(docId, contentType);
	}
 
	
	@Override
	public List<EcmContent> getAllContents(String token, String docId) {
		
		
		return contentMapper.getAllContents(docId);
	}
	
	@Override
	public List<EcmContent> getContents(String token, String docId ,int contentType) throws AccessDeniedException, NoPermissionException {
		return contentMapper.getContents(docId, contentType);
	}
	
	@Override
	public List<EcmContent> getObjects(String token, String condition) {
		return contentMapper.selectByCondition(condition);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public String newObject(String token, Object obj) throws EcmException {
		// TODO Auto-generated method stub
				try {
					EcmContent en = (EcmContent)obj;
					//新增内容前，将旧版IS_CURRENT设置成0
					if(StringUtils.isNotEmpty(en.getOldId())) {
						updateOldContentCurrent(en.getOldId());
					}
					
					
					if(StringUtils.isEmpty(en.getId())) {
						en.createId();
					}
					en.setDataTicket(contentMapper.selectDataTicket(DBFactory.getDBConn().getDBSequece().getContentDataTicketSql()));
					String fullPath = getFullPath(en);
					logger.info("Full path:{}",fullPath);
					File file = new File(fullPath);   
					long len = 0;
					BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(file));
					try
					{
						byte[] buffer = new byte[8 * 1024];
						int bytesRead;
						while ((bytesRead = en.getInputStream().read(buffer)) != -1) {
						   fis.write(buffer, 0, bytesRead);
						   len += bytesRead;
						}
					}
					finally
					{
						fis.close();
					}
					en.setContentSize(len);
					en.setCreationDate(new Date());
					en.setCreator(getSession(token).getCurrentUser().getUserName());
					en.setModifiedDate(en.getCreationDate());
					en.setModifier(en.getCreator());
					//en.setIsCurrent(1);
					contentMapper.insert(en);
					createPdfOcrEvent(token, en);
					en.getInputStream().close();
					logger.info("Insert content:{}",en.getId());
					return en.getId();
				}catch(Exception ex) {
					ex.printStackTrace();
					new EcmException(ex.getMessage());
				}
				return null;
	}

	
	/**
	 *    修改现存的内容数据 ， IS_CURRENT=1的 重置为0
	 * 
	 * @param token
	 * @param obj
	 * @return
	 * @throws EcmException
	 */
	private boolean updateOldContentCurrent(String oldContentId) throws EcmException {
		try {
			
			EcmContent  oldObj =  contentMapper.selectByPrimaryKey(oldContentId);
			if(oldObj!=null) {
				oldObj.setIsCurrent(0);
				contentMapper.updateByPrimaryKey(oldObj);
			}
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			new EcmException(ex.getMessage());
		}
		return false;
	}
	
	
	@Override
	public boolean updateObject(String token, Object obj) throws EcmException {
		try {
			EcmContent en = (EcmContent)obj;
			boolean ocrUpdate = "OCR".equalsIgnoreCase(en.getDescription());
			if(ocrUpdate) {
				en.setDescription("");
			}
			// TODO Auto-generated method stub
			if(en.getInputStream()!=null && en.getFilePath()!=null && en.getFilePath().length()>0)
			{
				String filePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
				filePath += File.separator + en.getFilePath();
				File f = new File(filePath);
				f.delete();
			}
			String fullPath = getFullPath(en);
			File file = new File(fullPath);   
			if(file.exists()) {
				file.delete();
			}
			long len = 0;
			BufferedOutputStream fis = new BufferedOutputStream(new FileOutputStream(file));
			try
			{
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				while ((bytesRead = en.getInputStream().read(buffer)) != -1) {
				   fis.write(buffer, 0, bytesRead);
				   len += bytesRead;
				}
			}
			finally
			{
				fis.close();
			}
			en.setContentSize(len);
			en.setModifiedDate(new Date());
			en.setModifier(getSession(token).getCurrentUser().getUserName());
			contentMapper.updateByPrimaryKey(en);
			en.getInputStream().close();
			if(!ocrUpdate) {
				createPdfOcrEvent(token, en);
			}
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			new EcmException(ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteObject(String token, Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof EcmContent ) {
			return contentMapper.deleteByPrimaryKey(((EcmContent)obj).getId())>0;
		}else {
			return contentMapper.deleteByPrimaryKey((String)obj)>0;
		}
	}

	@Override
	public InputStream getContentStream(String token, EcmContent en) throws Exception {
		// TODO Auto-generated method stub
		InputStream fis = null;
		logger.debug(JSON.toJSONString(en));
		logger.debug(JSON.toJSONString(CacheManagerOper.getEcmStores()));
		String fullPath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		File f = new File(fullPath+en.getFilePath());
		en.setContentSize(f.length());
		fis = new BufferedInputStream(new FileInputStream(fullPath+en.getFilePath()));
		return fis;
	}

	@Override
	public EcmContent getObjectById(String token, String objId) {
		// TODO Auto-generated method stub
		return contentMapper.selectByPrimaryKey(objId);
	}
	/**
	 */
	@Override
	public EcmContent getObject(String token, String objId,int contentType,String formatName) {
		String sql ="";
		sql = " PARENT_ID='"+DBFactory.getDBConn().getDBUtils().getString(objId)
				+"' and FORMAT_NAME='"+DBFactory.getDBConn().getDBUtils().getString(formatName)+"'";
		if(contentType>0) {
			sql = " PARENT_ID='"+DBFactory.getDBConn().getDBUtils().getString(objId)+"' and CONTENT_TYPE="+contentType
					+" and FORMAT_NAME='"+DBFactory.getDBConn().getDBUtils().getString(formatName)+"'";
		}
	
		logger.info(sql);
		
		List<EcmContent> list = contentMapper.selectByCondition(sql);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	
	@Override
	public void copyContent(String token, String fromDocId,String toDocId) throws Exception {
		List<EcmContent> list = getObjects(token, toDocId, 0);
		if(list.size()>0) {
			throw new EcmException("Document already has content:"+toDocId); 
		}
		list = getObjects(token, fromDocId, 0);
		if(list != null) {
			for(EcmContent conent:list) {
				conent.setInputStream(getContentStream( token, conent));
				conent.createId();
				conent.setParentId(toDocId);
				this.newObject(token, conent);
				conent.getInputStream().close();
				
			}
		}
	}
	/**
	 * 复制制定类型文件内容
	 * @param token
	 * @param fromDocId 被复制文件ID
	 * @param toDocId 目标文件ID
	 * @param contentType 类型,1:主文件，2：格式副本，3：附件，小于1任意类型
	 * @return
	 */
	public void copyContent(String token, String fromDocId,String toDocId,int contentType ) throws Exception {
		
		List<EcmContent> list = getObjects(token, toDocId, contentType);
		if(list.size()>0) {
			throw new EcmException("Document already has content:"+toDocId); 
		}
		list = getObjects(token, fromDocId, contentType);
		for(EcmContent conent:list) {
			conent.setInputStream(getContentStream( token, conent));
			conent.createId();
			conent.setParentId(toDocId);
			this.newObject(token, conent);
			conent.getInputStream().close();
		}
	}
	
	private String getFullPath(EcmContent en) throws Exception
	{
		String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
		String fullPath= storePath;
//		String os = System.getProperty("os.name").toLowerCase(); 
//		String splitStr = os.startsWith("win")?"\\":"/";
		if(fullPath.endsWith(File.separator))
		{
			fullPath = fullPath.substring(0,fullPath.length()-1);
		}
		Date dt = new Date();
		fullPath += getPath(en.getDataTicket());
		//Document ID文件路径+类型+时间+格式，格式副本在同一个目录。
		String  fileName = fullPath +"_"+en.getContentType()+"."+en.getFormatName();
		en.setFilePath(fileName.replace(storePath, ""));
		Path path = Paths.get(fileName);
		if (!Files.isWritable(path)) {
			fullPath = fullPath.substring(0,fullPath.length()-3);
			Files.createDirectories(Paths.get(fullPath));
		}
		return fileName;
	}
	/**
	 * 创建4级目录+
	 * @param id
	 * @param splitStr
	 * @return
	 */
	private String getPath(Long id) {
		//最多1078203909375
		String strHex = String.format("%010x", id);
		String path ="";
		for(int i=0;i<5;i++) {
			path +=File.separator+strHex.substring(i*2, i*2+2);
		}
		return path;
	}

	@Override
	public EcmContent getPrimaryContent(String token, String objId) {
		// TODO Auto-generated method stub
		logger.debug(objId);
		List<EcmContent> list = contentMapper.getContents(objId, 1);//历史数据需要初始化 IS_CURRENT=1

		return list.size()>0?list.get(0):null;
	}
	
	
	
	@Override
	public String getPrimaryFilePath(String token, String objId) {
		// TODO Auto-generated method stub
		List<EcmContent> list = contentMapper.getContents(objId, 1);
		if(list.size()>0) {
			EcmContent en = list.get(0);
			String storePath = CacheManagerOper.getEcmStores().get(en.getStoreName()).getStorePath();
			return storePath+en.getFilePath();
		}
		return null;
	}
	@Override
	public boolean updatePrimaryContentSize(String token, String docId,long fileSize) {
		List<EcmContent> list = contentMapper.getContents(docId, 1);
		if(list.size()>0) {
			EcmContent en = list.get(0);
			en.setContentSize(fileSize);
			return contentMapper.updateByPrimaryKey(en)>0;
		}
		return false;
	}
	
	/**
	 * 是否启用PDF OCR
	 * @return
	 */
	private boolean pdfOcrEnabled() {
		boolean ret = false;
		try {
			ret = Boolean.parseBoolean(CacheManagerOper.getEcmParameters().get("PdfOcrEnabled").getValue());
		}
		catch(Exception ex) {
			
		}
		return ret;
	}
	/**
	 * PDF OCR 最大文件大小
	 * @return
	 */
	private int pdfOcrMaxSize() {
		//默认50MB
		int maxSize = 50485760;
		try {
			maxSize = Integer.parseInt(CacheManagerOper.getEcmParameters().get("MaxIndexSize").getValue());
		}
		catch(Exception ex) {
			
		}
		return maxSize;
	}

	private void createPdfOcrEvent(String token, EcmContent en) throws EcmException, AccessDeniedException {
		if(pdfOcrEnabled()&& en!= null && en.getFormatName().equals("pdf") && en.getContentSize()<=pdfOcrMaxSize()) {
			queue(token, en.getId(), "ecm_pdf_ocr", "ecm_pdf_ocr", en.getParentId(),null);
		}
	}
	
	public void queue(String token, String id, String name, String eventName, String parentId,String message)
			throws EcmException, AccessDeniedException {
		EcmQueueItem queueItem = new EcmQueueItem();
		queueItem.createId();
		queueItem.setObjectId(id);
		queueItem.setName(name);
		queueItem.setEventName(eventName);
		queueItem.setRouterId(parentId);
		queueItem.setMessage(message);
		queueItem.setDelectFlag(false);
		queueItem.setStatus(0);
		queueItemService.newObject(token, queueItem);
	}

	@Override
	public boolean deleteObjectById(String token, String id)
			throws EcmException, AccessDeniedException, NoPermissionException {
		// TODO Auto-generated method stub
		return contentMapper.deleteByPrimaryKey(id)>0;
	}
	public ByteArrayOutputStream setWatermark(
			InputStream inputFile, String token) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String waterMarkName=format1.format(cal.getTime()) +"  "+ getSession(token).getCurrentUser().getUserName();
			PdfReader reader = new PdfReader(inputFile);
			ByteArrayOutputStream ot=new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, ot);
//			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:/Users/hp/Desktop/测试文档1111.pdf"));
			//这里的字体设置比较关键，这个设置是支持中文的写法
			BaseFont base = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
			int total = reader.getNumberOfPages() + 1;
			PdfContentByte under;
			Rectangle pageRect = null;
			JLabel label = new JLabel();
			label.setText(waterMarkName);
			FontMetrics metrics;
			metrics = label.getFontMetrics(label.getFont());
			int textH = metrics.getHeight();  //字符串的高,   只和字体有关
			int textW = metrics.stringWidth(label.getText());  //字符串的宽
			// set Transparency
			PdfGState gs = new PdfGState();
			// 设置透明度
			gs.setFillOpacity(0.5f);
			for (int i = 1; i < total; i++) {
				pageRect = stamper.getReader().
				getPageSizeWithRotation(i);
				// 获得PDF最顶层
				under = stamper.getOverContent(i);
				under.saveState();
				under.setGState(gs);
				under.restoreState();
				under.beginText();
				under.setFontAndSize(base, 15);
				under.setColorFill(BaseColor.LIGHT_GRAY);
				// 水印文字成45度角倾斜
		          for (int height =  textH; height < pageRect.getHeight()*2; height = height + textH * 10) {
	                  for (int width =  textW; width < pageRect.getWidth()*1.5 + textW; width = width + textW * 1) {
	                      // rotation:倾斜角度
	                	  under.setGState(gs);
	      				under.showTextAligned(Element.ALIGN_MIDDLE, waterMarkName, width - textW, height - textH, 15);
	                  }
	              }
//				under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, x,y, 55);
				// 添加水印文字
				under.endText();
				under.setLineWidth(0.1f);
				under.stroke();
			}
			stamper.close();
			return ot;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ByteArrayOutputStream addWatermark(String sourceImgPath, String tarImgPath,String fileExt,String token){
        Font font = new Font("宋体", Font.BOLD, 24);//水印字体，大小
        Color markContentColor = Color.white;//水印颜色
        ByteArrayOutputStream out = null;
        Integer degree = -45;//设置水印文字的旋转角度
        float alpha = 1.0f;//设置水印透明度 默认为1.0  值越小颜色越浅
        OutputStream outImgStream = null;
        try {
        	Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
        	String waterMarkContent =format1.format(cal.getTime()) +"  "+ getSession(token).getCurrentUser().getUserName();
            File srcImgFile = new File(sourceImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//得到画笔
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //设置水印颜色
            g.setFont(font);              //设置字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            if (null != degree) {
                g.rotate(Math.toRadians(degree),(double)bufImg.getWidth(),(double)bufImg.getHeight());//设置水印旋转
            }
            JLabel label = new JLabel(waterMarkContent);
            FontMetrics metrics = label.getFontMetrics(font);
            int width = metrics.stringWidth(label.getText());//文字水印的宽
            int rowsNumber = srcImgHeight/width+srcImgHeight%width;// 图片的高  除以  文字水印的宽  打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth/width+srcImgWidth%width;//图片的宽 除以 文字水印的宽  每行打印的列数(以文字水印的宽为间隔)
            //防止图片太小而文字水印太长，所以至少打印一次
            if(rowsNumber < 1){
                rowsNumber = 1;
            }
            if(columnsNumber < 1){
                columnsNumber = 1;
            }
            for(int j=0;j<rowsNumber;j++){
                for(int i=0;i<columnsNumber;i++){
                    g.drawString(waterMarkContent, i*width + j*width, -i*width + j*width);//画出水印,并设置水印位置
                }
            }
            g.dispose();// 释放资源
            // 输出图片  
            outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, fileExt, outImgStream);
            File file = new File(tarImgPath);
            FileInputStream in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
            out.write(b, 0, b.length);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally{
            try {
                if(outImgStream != null){
                    outImgStream.flush();
                    outImgStream.close();
                    File file = new File(tarImgPath);
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
//        File file = new File(tarImgPath);
//        file.delete();
        return out;
    }
}
