package com.ecm.core.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Test {
public static void main(String[] args) {
	
//	try {
//		long a=FileUtils.checksumCRC32(new  File("C:\\temp\\public\\20200718054503.zip"));
//		long b=FileUtils.checksumCRC32(new  File("C:\\temp\\public\\1.zip"));
//		long c=FileUtils.checksumCRC32(new  File("C:\\temp\\public\\2.zip"));
//		String md5=generateZipFileMD5("C:\\\\temp\\\\public\\\\1.zip");
//		String md5FileName="RE-"+"1"+"txt";
//		FileUtils.write(new File("C:\\\\temp\\\\public\\\\a.txt"), md5, "UTF-8", true);
//		System.out.println(a+"-"+b+"-"+c);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	
	try {
		long a=FileUtils.checksumCRC32(new  File("C:\\temp\\public\\20200718054503.zip"));
		long b=FileUtils.checksumCRC32(new  File("C:\\temp\\public\\1.zip"));
		long c=FileUtils.checksumCRC32(new  File("C:\\temp\\public\\2.zip"));
		String md5=generateZipFileMD5("C:\\\\temp\\\\public\\\\1.zip");
		String md5FileName="RE-"+"1"+"txt";
            FileWriter fw = new FileWriter("C:\\\\temp\\\\public\\\\cc.txt",true);   
            PrintWriter pw=new PrintWriter(fw);   
            pw.println("append content\n");  //字符串末尾不需要换行符
            pw.close () ;   
            fw.close () ;   
//          		FileUtils.writeLines(new File("C:\\\\temp\\\\public\\\\a.txt"), lines,"\n",true);
		System.out.println(a+"-"+b+"-"+c);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

private static String generateZipFileMD5(String filePath) throws IOException {
	String md5 = "";
	try (FileInputStream fis = new FileInputStream(filePath);) {
		md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
	}
	return md5;

}

}
