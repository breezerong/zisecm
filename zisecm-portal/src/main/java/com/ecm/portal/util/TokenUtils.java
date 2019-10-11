package com.ecm.portal.util;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

	public static boolean volidateToken(String token,HttpServletRequest req) {
		
		if(req.getSession()!=null&&req.getSession().getId().equals(token)&&req.getSession().getAttribute("ECMUserSession")!=null)
		{
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
