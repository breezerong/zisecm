package com.ecm.common.util;


public class PasswordUtils {
	private static String key = "Passw0rd@20191001";
	public static String encodePassword(String pwd) throws Exception {
		return SecureUtils.desEncode(key, pwd);
	}
	
	public static String decodePassword(String pwd) throws Exception {
		return SecureUtils.desDecode(key, pwd);
	}
	
	public static void main(String[] args) throws Exception {
		if(args != null && args.length>1) {
			if(args[0].equalsIgnoreCase("-d")) {
				System.out.println(decodePassword(args[1]));
			}else if(args[0].equalsIgnoreCase("-e")) {
				System.out.println(encodePassword(args[1]));
			}
		}
	}
}
