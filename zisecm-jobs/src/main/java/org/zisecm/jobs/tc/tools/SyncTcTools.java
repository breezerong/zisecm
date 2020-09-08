package org.zisecm.jobs.tc.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.ws.PLMServerLOT.LoginInfo;

import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;

public class SyncTcTools {
	private static Session session;
	
	public static Session getSession(Environment env) throws Exception {
		if(session==null) {
			session=loginTC(env);
			
		}
		return session;
		
	}
	
	private static Session loginTC(Environment env) throws Exception
	  {
//	    LoginInfo loginInfo = new LoginInfo("http://10.30.2.132:9080/tc", "dcproxy", "dcproxy", "SoaAppX");
		String url=env.getProperty("ecm.tcUrl");
		String userName=env.getProperty("ecm.tcUsername");
		String password=env.getProperty("ecm.tcPassword");
		  LoginInfo loginInfo = new LoginInfo(url, userName, password, "SoaAppX");

		  Session sess = new Session(loginInfo.getServerHost());
	    System.out.println(loginInfo.getServerHost() + "---------");
	    try
	    {
	      sess.login(loginInfo.getUserID(), loginInfo.getPassword(), 
	        loginInfo.getDiscriminator());
	    } catch (Exception e) {
	      e.printStackTrace();

	      throw new Exception("登录失败，请检查用户名密码，或网络地址是否正确");
	    }

	    return sess;
	  }
	
	
	
}
