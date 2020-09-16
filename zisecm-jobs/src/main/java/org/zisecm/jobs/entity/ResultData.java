package org.zisecm.jobs.entity;

import java.util.List;

import com.ecm.core.entity.EcmDocument;

public class ResultData {
	private EcmDocument document;
	private List<FileInfo> list;
	public EcmDocument getDocument() {
		return document;
	}
	public void setDocument(EcmDocument document) {
		this.document = document;
	}
	public List<FileInfo> getList() {
		return list;
	}
	public void setList(List<FileInfo> list) {
		this.list = list;
	}
	
	
	
}
