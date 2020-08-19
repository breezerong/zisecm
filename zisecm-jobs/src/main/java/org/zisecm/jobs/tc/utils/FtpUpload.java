package org.zisecm.jobs.tc.utils;
import java.io.File;

import com.myjavaworld.ftp.FTPClient;
import com.myjavaworld.ftp.ListParser;
import com.myjavaworld.ftp.RemoteFile;

/**
 * 
 */

/**
 * @author xiaolei Creation Date 2014-12-30
 */
public class FtpUpload {

    private String host;   
    private int port = 21;   
    private String user;   
    private String passwd;   
       
    public FtpUpload(String host, String user, String passwd){   
        this.host = host;   
        this.user = user;   
        this.passwd = passwd;   
    }   
       
    public FtpUpload(String host, int port, String user, String passwd){   
        this.host = host;   
        this.port = port;   
        this.user = user;   
        this.passwd = passwd;   
    }   
    
    
    public void upload(String remoteDir, String localFullFileName) throws Exception{   
		   String className = "com.myjavaworld.ftp.DefaultFTPClient";

		    FTPClient client = (FTPClient)Class.forName(className).newInstance();

		    ListParser parser = (ListParser)Class.forName("com.myjavaworld.ftp.DefaultListParser").newInstance();

		    client.setListParser(parser);

		    client.setTimeout(2000);//连接超时2s
		    System.out.println("Connecting...");

		    client.connect(host,port);
		    System.out.println("Connected. ");

		    System.out.println("Logging in...");
		    client.login(user, passwd);
		    System.out.println("Logged in. ");

		    //make dirs   
	        String path = "";   
	        String[] paths = remoteDir.split( "/"); 
	        for(int i = 0; i < paths.length; i++){   
	            path += "/" + paths[i];   
	            
	            try {
					RemoteFile tmpRemoteDir = parser.createRemoteFile(path,true);
					client.createDirectory(tmpRemoteDir);
				} catch (Exception e) {}
	        }   
		    
	        String remoteFullFileName = remoteDir;
	        if(!remoteDir.endsWith("/"))
	        	remoteFullFileName+="/";
	        remoteFullFileName +=localFullFileName.substring(localFullFileName.lastIndexOf(File.separator)+1);
	        
		    File source = new File(localFullFileName);
		    RemoteFile destination = parser.createRemoteFile(remoteFullFileName, false);

		    System.out.println("Uploading Dir:"+remoteDir);
		    System.out.println("Uploading: " + source);
		    client.upload(source, destination, 2, false, 0L);
		    System.out.println("Done. ");

		    System.out.println("Disconnecting...");
		    client.disconnect();
		    System.out.println("Disconnected. ");
    }   
    
	public static void main(String[] args){
		   
		FtpUpload ftpUpload = new FtpUpload("localhost", 21, "ftpuser", "ftpuser");
		try {
			ftpUpload.upload("/1/2/3", "c:\\1234.doc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
