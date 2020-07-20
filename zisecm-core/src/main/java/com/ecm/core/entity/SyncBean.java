package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncBean{
    private List<Map<String, Object>> documents;;
    private List<EcmRelation> relations;
	public List<EcmContent> getContents() {
		return contents;
	}
	public void setContents(List<EcmContent> contents) {
		this.contents = contents;
	}
	private List<EcmContent> contents;
	public List<Map<String, Object>> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Map<String, Object>> documents) {
		this.documents = documents;
	}
	public List<EcmRelation> getRelations() {
		return relations;
	}
	public void setRelations(List<EcmRelation> relations) {
		this.relations = relations;
	}

}
