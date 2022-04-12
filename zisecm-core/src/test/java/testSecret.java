import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class testSecret {
	private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";
    private static final int numOfEncAndDec = 0x99; //加密解密秘钥
    private static int dataOfFile = 0; //文件字节内容
	
	@Test
    public void testSecretAc() throws Exception {
		String fileName = "D:\\opt\\excdata\\00\\00\\00\\07\\29_1.docx";

		String content = "Successful operation!";
		appendMethod_one(fileName, content);
				
//		File file1 = new File("C:\\Users\\WHY\\Desktop\\北京住房公积金管理中心业务凭证回单.pdf");
//		File file2 = new File("C:\\Users\\WHY\\Desktop\\加密.pdf");
//		File file3 = new File("C:\\Users\\WHY\\Desktop\\解密.pdf");		
//		EncFile(file1, file2);
//		DecFile(file2, file3);
		//secretAction(file);
	}
	
	private void secretAction(File file) throws Exception {
		InputStream encryptedInputStream = null;
		FileOutputStream out = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] nonEncryptedByteArray = IOUtils.toByteArray(fis);
		    
		    IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		    SecretKeySpec secretkey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		    
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); //Cipher instance using AES encryption algorithm
		    cipher.init(Cipher.ENCRYPT_MODE, secretkey, iv);
		    byte[] encryptedByteArray = cipher.doFinal(nonEncryptedByteArray);
		    
		    encryptedInputStream = new ByteArrayInputStream(new Base64().encode(encryptedByteArray));
		    
		    byte[] nonEncryptedByteArrayOut = IOUtils.toByteArray(encryptedInputStream);
	        
	        Cipher cipherOut = Cipher.getInstance("AES/CBC/PKCS5PADDING"); //Cipher instance using AES encryption algorithm
	        cipherOut.init(Cipher.DECRYPT_MODE, secretkey, iv);
	        byte[] encrypted1 = new Base64().decode(nonEncryptedByteArrayOut);
	        byte[] encryptedByteArrayOut = cipher.doFinal(encrypted1);
	        
	        String filepath ="C:\\Users\\WHY\\Desktop\\知识管理系统用户操作手册.pdf"; 
	        File fileOut  = new File(filepath); 
	        if(file.exists()){ 
	           file.delete(); 
	        }
	        out = new FileOutputStream(fileOut); 
	        out.write(encryptedByteArrayOut,0,encryptedByteArrayOut.length); 
	        out.flush(); 
	        out.close();
		    
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	private static void EncFile(File srcFile, File encFile) throws Exception {
		if (!srcFile.exists()) {
			System.out.println("source file not exixt");
			return;
		}

		if (!encFile.exists()) {
			System.out.println("encrypt file created");
			encFile.createNewFile();
		}
		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(encFile);

		while ((dataOfFile = fis.read()) > -1) {
			fos.write(dataOfFile ^ numOfEncAndDec);
		}

		fis.close();
		fos.flush();
		fos.close();
	}
	
	private static void DecFile(File encFile, File decFile) throws Exception {
		if (!encFile.exists()) {
			System.out.println("encrypt file not exixt");
			return;
		}

		if (!decFile.exists()) {
			System.out.println("decrypt file created");
			decFile.createNewFile();
		}

		InputStream fis = new FileInputStream(encFile);
		OutputStream fos = new FileOutputStream(decFile);

		while ((dataOfFile = fis.read()) > -1) {
			fos.write(dataOfFile ^ numOfEncAndDec);
		}

		fis.close();
		fos.flush();
		fos.close();
	}
	
	public static void appendMethod_one(String fileName, String content) {
		try {
			InputStream in = new FileInputStream(fileName);
			byte[] nonEncryptedByteArray = IOUtils.toByteArray(in);
			for (int i = 0; i < nonEncryptedByteArray.length; i++) {
				nonEncryptedByteArray[i]= nonEncryptedByteArray[i] ^= (1 << 1);
			} 
			
			OutputStream out = new FileOutputStream(fileName);
			out.write(nonEncryptedByteArray,0,nonEncryptedByteArray.length); 
	        out.flush(); 
	        out.close();
			// 按读写方式创建一个随机访问文件流
//			RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
//			long fileLength = raf.length();
//			raf.seek(fileLength);
//			raf.writeBytes(content);

			// 关闭流
			//raf.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	
    /**
     * Invert the specified bit
     *
     * @param originByte Raw byte value
     * @param bitIndex   bit index (From 0~7)
     * @return Final byte value
     */
    public static byte setSpecifiedBitToReverse(byte originByte, int bitIndex) {
        return originByte ^= (1 << bitIndex);
    }

    /**
     * Get the value of the specified bit
     *
     * @param originByte Raw byte value
     * @param bitIndex   bit index (From 0~7)
     * @return Final byte value
     */
    public static byte getSpecifiedBitValue(byte originByte, int bitIndex) {
        return (byte) ((originByte) >> (bitIndex) & 1);
    }
}
