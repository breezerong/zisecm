package org.zisecm.jobs.tc.utils;
import java.io.File;

import com.myjavaworld.ftp.ConnectionException;
import com.myjavaworld.ftp.FTPClient;
import com.myjavaworld.ftp.FTPException;
import com.myjavaworld.ftp.ListParser;
import com.myjavaworld.ftp.RemoteFile;

/**
 * 
 */

/**
 * @author xiaolei Creation Date 2014-12-30
 */
public class FtpDownload {

	private String host;
	private int port = 21;
	private String user;
	private String passwd;

	public FtpDownload(String host, int port, String user, String passwd) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.passwd = passwd;
	}

	public void downloadToLocal(String remoteFile, String localFile)
			throws Exception {
		long current = System.currentTimeMillis();
		String className = "com.myjavaworld.ftp.DefaultFTPClient";

		FTPClient client = (FTPClient) Class.forName(className).newInstance();

		ListParser parser = (ListParser) Class.forName(
				"com.myjavaworld.ftp.DefaultListParser").newInstance();

		client.setListParser(parser);

		client.setPassive(true);

		System.out.println("Connecting...");
		client.setTimeout(3000);
		client.connect(host, port);
		System.out.println("Connected. ");

		System.out.println("Logging in...");
		client.login(user, passwd);
		System.out.println("Logged in. ");

		RemoteFile source = parser.createRemoteFile(remoteFile, false);

		File destination = new File(localFile);
		System.out.println("Downloading: " + source);
		client.download(source, destination, 2, false);
		System.out.println("Done. ");

		System.out.println("Disconnecting...");
		client.disconnect();
		System.out.println("Disconnected. ");

		System.out.println("Download took "
				+ (System.currentTimeMillis() - current) + "ms.");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InstantiationException,
			ClassNotFoundException, IllegalAccessException, FTPException,
			ConnectionException {
		FtpDownload ftpDownload = new FtpDownload("10.30.7.40", 21, "fengbina",
				"password");
		try {
			ftpDownload.downloadToLocal("/cg2tc/284d41cb27554cc99bb36737835b997d", "D:/tmp/1.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
