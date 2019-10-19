package com.ecm.common.util;

import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class SecureUtils {
	/**
	 * DES encoder
	 */
	public static byte[] desEncode(String key, byte[] src) throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey sKey = keyFactory.generateSecret(keySpec);

		Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
		cipher.init(Cipher.ENCRYPT_MODE, sKey);
		byte[] dst = cipher.doFinal(src);

		return dst;
	}

	/**
	 * DES decoder
	 */
	public static byte[] desDecode(String key, byte[] src) throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		DESKeySpec keySpec = new DESKeySpec(key.getBytes("UTF8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey sKey = keyFactory.generateSecret(keySpec);

		Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
		cipher.init(Cipher.DECRYPT_MODE, sKey);
		byte[] dst = cipher.doFinal(src);

		return dst;
	}

	/**
	 * DES encoder
	 */
	public static String desEncode(String key, String src) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return new String(Base64.getEncoder().encode(desEncode(key, src.getBytes("UTF8"))));
	}

	/**
	 * DES decoder
	 */
	public static String desDecode(String key, String src) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		return new String(desDecode(key, Base64.getDecoder().decode(src.getBytes())), "UTF8");
	}

	/**
	 * Base64 encoder
	 */
	public static String b64Encode(byte[] src) {
		return new String(Base64.getEncoder().encode(src));
	}

	/**
	 * Base64 decoder
	 */
	public static byte[] b64Decode(String src) {
		return Base64.getDecoder().decode(src.getBytes());
	}

	/**
	 * MD5 encoder
	 */
	public static String md5Encode(byte[] src) throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] dst = md.digest(src);

		for (int i = 0; i < dst.length; i++) {
			sb.append(Integer.toHexString((dst[i] >> 4) & 0xf));
			sb.append(Integer.toHexString(dst[i] & 0xf));
		}

		return sb.toString();
	}

	/**
	 * MD5 encoder
	 */
	public static String md5Encode(File file) throws NoSuchAlgorithmException, IOException {
		StringBuilder sb = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("MD5");
		InputStream is = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int numRead;

		try {
			do {
				if ((numRead = is.read(buffer)) > 0) {
					md.update(buffer, 0, numRead);
				}
			} while (numRead != -1);

			byte[] dst = md.digest();

			for (int i = 0; i < dst.length; i++) {
				sb.append(Integer.toHexString((dst[i] >> 4) & 0xf));
				sb.append(Integer.toHexString(dst[i] & 0xf));
			}
		} finally {
			is.close();
		}

		return sb.toString();
	}

	/**
	 * Password generator.
	 */
	public static String generatePassword(int lenght) {
		return RandomStringUtils.randomAlphanumeric(lenght);
	}
	/**
	 * MD5加密字符串
	 * @param str
	 * @return
	 */
	public static String md5Encode(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1,md.digest()).toString(32);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * SHA加密字符串
	 * @param str
	 * @return
	 */
	public static String shaEncode(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(32);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
