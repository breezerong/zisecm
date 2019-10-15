package com.ecm.portal.entity;

import java.io.File;

public class DownloadFile {
	
	private File downFile;
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getDownFile() {
		return downFile;
	}
	public void setDownFile(File downFile) {
		this.downFile = downFile;
	}

}
