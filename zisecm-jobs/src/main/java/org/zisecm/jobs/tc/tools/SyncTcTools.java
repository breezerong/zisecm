package org.zisecm.jobs.tc.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.zisecm.jobs.tc.clientx.Session;
import org.zisecm.jobs.tc.ws.PLMServerLOT.LoginInfo;

import com.ecm.core.service.AuthService;
import com.ecm.icore.service.IEcmSession;

public class SyncTcTools {
	private static Session session;
	
	public static Session getSession() throws Exception {
		if(session==null) {
			session=loginTC();
			
		}
		return session;
		
	}
	
	private static Session loginTC() throws Exception
	  {
//	    LoginInfo loginInfo = new LoginInfo("http://10.30.2.132:9080/tc", "dcproxy", "dcproxy", "SoaAppX");

		  LoginInfo loginInfo = new LoginInfo("http://192.168.2.130:7001/tc", "infodba", "infodba", "SoaAppX");

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
