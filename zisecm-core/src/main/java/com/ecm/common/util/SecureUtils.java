package com.ecm.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.DigestUtils;

public class SecureUtils {
	
	private static final String UTF_8 = "UTF-8";
	private static final Base64.Decoder decoder64 = Base64.getDecoder();
    private static final Base64.Encoder encoder64 = Base64.getEncoder();
    
    public static final String AES_ALGORITHM = "AES/CFB/PKCS5Padding";
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String RSA_ALGORITHM_NOPADDING = "RSA";
    
    
    public static final String PUBLIC_Key= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs7gHJ+qrkn5UXKzup6izXsmOB+bLllkQaUBMCwfd/KXYdZUM+aOqpRCNM9rTBIPEYW27zdrjZceDQxkSbZOIYjZyYEojgdxPUoEGGHXvfqMsCxQeJqzaDjZWHcRckzD7FUof3a211EFNyOiZqfLTLwrjiQJky+a4lcprrj/mi4pI6SxY8MTlhHVYmcizmt5REFALB5IsM0yJbniPJgz6mjXQJJfvpE1W6JWGhzp///fXvbJTuw4zFIbMVrpp/npxXuJeBLTTxULbKDr6I6oBr1VR/Ez22PcjIiUm5zPe69905lu6BmOISOhfewdxQHulsyTkRAnGotEPeH+J1SCQiwIDAQAB";
    public static final String PRIVATE_KEY="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzuAcn6quSflRcrO6nqLNeyY4H5suWWRBpQEwLB938pdh1lQz5o6qlEI0z2tMEg8RhbbvN2uNlx4NDGRJtk4hiNnJgSiOB3E9SgQYYde9+oywLFB4mrNoONlYdxFyTMPsVSh/drbXUQU3I6Jmp8tMvCuOJAmTL5riVymuuP+aLikjpLFjwxOWEdViZyLOa3lEQUAsHkiwzTIlueI8mDPqaNdAkl++kTVbolYaHOn//99e9slO7DjMUhsxWumn+enFe4l4EtNPFQtsoOvojqgGvVVH8TPbY9yMiJSbnM97r33TmW7oGY4hI6F97B3FAe6WzJORECcai0Q94f4nVIJCLAgMBAAECggEBAIzzoGkmHDpg6jllSCgmn8ePvN1lnfblE4H486sTGH+2cvaFWA/S1Vtwh85LqYS486SWM91X91E0ETbmyxU1yJl+qmXqv75x06SZnPZCSPvBbiGiPaOHnd3Xr+LYxuKKX+JqqGNsgldegGs6QoqP8CHNIHescPGamtqgT6Pyaha1pM4eHFvA0X+hYn5N9QPUa6NzbGCXl6ZJDgOdAMGO+DV1P2p9dF1HlMEJ0/auhbDWbYyHsYuoPk+nIbGWFPNNqgNEohuJPEdpTqJUzOzPsqRPw6bKkpn7nD3wqBDmcd+tAq2KytGprt1j/jm+qxEwA2ZPILjGCCM7vYvKsWjl1YECgYEA5+ZqqSPgqnffLiucHDLswEBjBMMhHE4ETsTFqge6D9B2FCtmlhXRHq3V0zWWsz8rzgRLZLMBTS96v3Ui375hQ6Q9cb7aDQl7LeZ6o7pi0SzkypzqpC0PWPh7sdzW/M7769NoGSDwTrg1WR9gPzvBnCde+xYzqQ3gwrFnoAJVEBkCgYEAxmVbWqTIRgtB4mSwYUnanjNXm+cbPHPDFnYBckEcALhjtZmAkO03KMRMchdVzXk3TpmzIC7FKxrZhV3ygxJN3cG+Rur+KLUjVBtdNV9LQZ9k+V/e/S7DYXvgVTJ0I3eKT+ysgShyJX0AgQzRDD8pvO8DRqeG+ukwrzxGRCwrakMCgYASQioTtydxRI7l67kW30uUt30UWui4ecwNB6EoigM0S42AeQpSttUIPVegkY+T/V1Nkzj8bLulgB+CA48DXtH5qWzND+HLNPvl2Ue+rGYrYgWBjmlLE+EpeCcFEgY7UXC0WKjRka5aBFbu9XF2iRXtXYw06MKYhOc1bRJjnstjwQKBgQDE0+vVOuRCsYp0zAY9DyNojnFdh+P6jrK+xuZabwQUJtstYJQvtFhkBpP9IO+MQbVAMraGuD/+qs2hRfqCVReRaVSYUS1EP6lEGT3Y5Kjp+vQXgCjfsJRAU8DVZY/ZXBCJiLoADZtpW+GzJn8DkmqbOdlbMHt21QDq6Xagzd1otwKBgQCJrh8H60jrRlpLpEaISDWla3xe0smRYopVLU7uq8guAGMPQkywb9miH6Ok8CkJdSzQScWbuPtlkjx7a3dAnr4wgNruvFGXDrowQnoefL+fb6nrqWHHGWz4A5QjiFU8MWEbRyLa4PON5aa8c4qPBcwjuOqOJFL5IIc6RV2Z7S5afA==";
	
    /**
     * RSA 解密
     * @Title:
     * @author Haihong Rong
     * @date:   2020-11-14 17:43:44 
     * @Description:       
     * @param privateKeyStr
     * @param content
     * @return
     */
	public static String decryptRSA(String privateKeyStr, String content) {
		try {
			 KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NOPADDING);
		     PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decoder64.decode(privateKeyStr));
		     PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		     Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NOPADDING);
		     cipher.init(Cipher.DECRYPT_MODE, privateKey);
		     return new String(cipher.doFinal(decoder64.decode(content)), UTF_8);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
    }
	
	/**
	 * RSA加密
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-14 17:43:59 
	 * @Description:       
	 * @param publicKeyStr
	 * @param plainText
	 * @return
	 */
	public static String encryptRSA(String publicKeyStr, String plainText) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, getRSAPublidKeyBybase64(publicKeyStr));
			return encoder64.encodeToString(cipher.doFinal(plainText.getBytes(UTF_8)));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
    }
	/**
	 * 
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-14 17:44:27 
	 * @Description:       
	 * @param base64s
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static RSAPublicKey getRSAPublidKeyBybase64(String base64s) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoder64.decode(base64s));
        RSAPublicKey publicKey = null;
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
        return publicKey;
	}
	/**
	 * 
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-14 17:44:32 
	 * @Description:       
	 * @param base64s 秘钥字符串
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
     public static RSAPrivateKey getRSAPrivateKeyBybase64(String base64s) throws InvalidKeySpecException, NoSuchAlgorithmException {
         PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoder64.decode(base64s));
         RSAPrivateKey privateKey = null;
         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        return privateKey;
	}
     
	/**
	 *  DES 加密
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-14 17:44:49 
	 * @Description:       
	 * @param key 秘钥
	 * @param src 加密数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
     private static byte[] desEncode(String key, byte[] src) throws InvalidKeyException, UnsupportedEncodingException,
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
	 * DES 解密
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-14 17:46:03 
	 * @Description:       
	 * @param key
	 * @param src
	 * @return
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
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
	 * 生成RSA 秘钥对
	 * @Title:
	 * @author Haihong Rong
	 * @date:   2020-11-14 16:15:40 
	 * @Description:       
	 * @throws NoSuchAlgorithmException
	 */
	public static void createKeyPairs() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        System.out.println("公钥: ");
        System.out.println("-----BEGIN PUBLIC KEY-----");
        System.out.println(encoder64.encodeToString(publicKey.getEncoded()));
        System.out.println("-----END PUBLIC KEY-----");
        System.out.println("私钥: ");
        System.out.println("-----BEGIN RSA PRIVATE KEY-----");
        System.out.println(encoder64.encodeToString(privateKey.getEncoded()));
        System.out.println("-----END RSA PRIVATE KEY-----");
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
			return DigestUtils.md5DigestAsHex(str.getBytes("UTF-8"));
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(str.getBytes());
//			return new BigInteger(1,md.digest()).toString(32);
		} catch (Exception e) {
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
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		createKeyPairs();
	}
}
