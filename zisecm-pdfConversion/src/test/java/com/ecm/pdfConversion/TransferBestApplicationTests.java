//package com.ecm.pdfConversion;
//
//import java.util.concurrent.CountDownLatch;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.ecm.pdfConversion.openoffic.FileInfoHelps;
//
//import lombok.extern.slf4j.Slf4j;
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class TransferBestApplicationTests {
//
//	
//	@Autowired
//	FileInfoHelps fileInfoHelps;
//
//     @Test
//     public void transferLocalFile() {
//         try {
//            /*******************word 转pdf******************/
//             long time = System.currentTimeMillis();
//             final CountDownLatch end =new CountDownLatch(10);
//             System.out.println("start :======" + time);
//             
//             fileInfoHelps.generatePreviewFile(end,"ppp.pptx");
//             /*******************pdf转图片******************/
//             /**
//             long time2 = System.currentTimeMillis();
//             List<String> pdfImages2 = wordTransferPdfUtil.transferPdfToImage("courtChongqing/test_new/333.pdf");
//             for (String pdfImage : pdfImages2) {
//                 System.out.println(pdfImage);
//             }
//            **/
//           //等待检查
//     		try {
//     			end.await();
//     		} catch (InterruptedException e) {
//     			// TODO Auto-generated catch block
//     			e.printStackTrace();
//     		} 
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//    }
//}
