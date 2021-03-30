package com.ecm.pdfConversion.openoffic;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jodconverter.core.DocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.ecm.pdfConversion.JacobOffice2PdfUtil;
import com.ecm.pdfConversion.openoffic.config.PdfTransferUtil;
import com.ecm.pdfConversion.openoffic.config.ResourceComponent;

import lombok.extern.slf4j.Slf4j;

/**
 * word 转pdf
 *
 * @author lr
 */
@Slf4j
@Repository
@Component(value="wordTransferPdfUtil")
public class TransferUtil {
    /**
     * 这里没有 主要是配置不启用的话 无法注入
     */
//	@Autowired
//    private static DocumentConverter documentConverter;

    public final static String WORD_SUFFIX_DOC = "doc";
    public final static String WORD_SUFFIX_DOCX = "docx";
    public final static String WORD_SUFFIX_PPT = "ppt";
    public final static String WORD_SUFFIX_PPTX = "pptx";
    public final static String WORD_SUFFIX_XLS = "xls";
    public final static String WORD_SUFFIX_XLSX = "xlsx";
    public final static String PDF_SUFFIX = "pdf";
    private static Logger log = LoggerFactory.getLogger(TransferUtil.class);


    /**
     * word ->pdf
     *
     * @param webPath 浏览器可访问路径（数据库存的）如 /test/wd.word
     * @return 相同文件夹下的转换后的pdf 路径 如/test/wd_20190517151515333.pdf
     * @throws Exception
     */
    public String transferWordToPdf(String webPath) throws Exception {
        initBeanUtil();
        //转换成本地实际磁盘路径
//        String originLocalFilePath = ResourceComponent.getLocation(webPath);
//        File inputFile = new File(originLocalFilePath);
        File inputFile = new File(webPath);
        if (!inputFile.exists() || !inputFile.isFile() ||
                (!StringUtils.contains(inputFile.getName(), WORD_SUFFIX_DOC)
                        && !StringUtils.contains(inputFile.getName(), WORD_SUFFIX_DOCX)
                        && !StringUtils.contains(inputFile.getName(), WORD_SUFFIX_PPT)
                        && !StringUtils.contains(inputFile.getName(), WORD_SUFFIX_PPTX)
                        && !StringUtils.contains(inputFile.getName(), WORD_SUFFIX_XLS)
                        && !StringUtils.contains(inputFile.getName(), WORD_SUFFIX_XLSX))) {
            throw new Exception(webPath+" -> pdf转换错误  当前文件不是word或 文件不存在: " + webPath);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timeNow = formatter.format(LocalDateTime.now());
        String newPdfWebPath = StringUtils.substringBeforeLast(webPath, ".") + "_" + timeNow + ".pdf";
        try {
//            File outputFile = new File(ResourceComponent.getLocation(newPdfWebPath));
//        	 File outputFile = new File(newPdfWebPath);
//            documentConverter.convert(inputFile).to(outputFile).execute();
        	 new JacobOffice2PdfUtil().office2pdf(webPath, newPdfWebPath);
        } catch (Exception e) {
            log.error("word->pdf 转换错误------------> Exception: ", e);
            throw e;
        }
        return newPdfWebPath;
    }

    /**
     * pdf转图片 返回本地存储路径图片集合
     *
     * @param webPath
     * @return
     * @throws Exception
     */
    public static List<String> transferPdfToImage(String webPath) throws Exception {
        initBeanUtil();
        String originLocalFilePath = ResourceComponent.getLocation(webPath);
        File inputFile = new File(originLocalFilePath);
        if (!inputFile.exists() ||
                !inputFile.isFile() ||
                webPath.lastIndexOf(".pdf") < 0) {
            throw new Exception("pdf-> img 源文件不是pdf文件 或者文件不存在！" + webPath);
        }
        String localPdfPath = ResourceComponent.getLocation(webPath);
        String newImgWebPathPreSuffix = StringUtils.substringBeforeLast(webPath, ".");
        String localImgPath = ResourceComponent.getLocation(newImgWebPathPreSuffix);
        PdfTransferUtil transferUtil = new PdfTransferUtil();
        List<byte[]> ins = transferUtil.pdf2Image(localPdfPath, "png", 1.5f);
        List<String> localFilePath = new ArrayList<>(ins.size());
        for (int i = 0; i < ins.size(); i++) {
            byte[] data = ins.get(i);
            String pathReal = localImgPath + "_pdf_" + i + ".png";
            FileUtils.writeByteArrayToFile(new File(pathReal), data);
            localFilePath.add(pathReal);
        }
        return localFilePath;
    }

    /**
     * pdf转图片 返回web可访问图片集合
     *
     * @param webPath
     * @return
     * @throws Exception
     */
    public static List<String> transferPdfToWebImage(String webPath) throws Exception {
        List<String> localPathImage = transferPdfToImage(webPath);
        List<String> webPathImage = new ArrayList<>(localPathImage.size());
        for (String s : localPathImage) {
            webPathImage.add(ResourceComponent.getWebPath(s));
        }
        return webPathImage;
    }

    /**
     * 初始化bean
     */
    private static void initBeanUtil() {
//        if (documentConverter == null) {
//            documentConverter = SpringUtil.getBean(DocumentConverter.class);
//        }
    }

    /**
     * 获取文件后缀名 不包含点
     */
    public static String getFileSuffix(String fileWholeName) throws Exception {
        if (StringUtils.isEmpty(fileWholeName)) {
            throw new Exception("文件名称为空!");
        }
        int lastIndexOf = fileWholeName.lastIndexOf(".");
        return fileWholeName.substring(lastIndexOf + 1);
    }

    /**
     * 上传文件保存到本地，返回可访问web路径
     *
     * @param suffix
     * @param file
     * @return
     * @throws Exception
     */
    public static String saveLocation(String suffix, MultipartFile file) throws Exception {
        String fileName = System.currentTimeMillis() + "." + suffix;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        String time = formatter.format(LocalDateTime.now());
        String fileSavePath = ResourceComponent.fileUploadPath + time + "/";
        String fileLocalSavePath = fileSavePath + fileName;
        FileUtils.writeByteArrayToFile(new File(fileLocalSavePath), file.getBytes());
       
        String viewPath = StringUtils.substringAfter(fileLocalSavePath, ResourceComponent.fileUploadPath);
        System.out.println("保存的文件路径--> fileLocalSavePath:{}"+viewPath);
        
        return viewPath;
    }

}