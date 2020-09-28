package org.zisecm.jobs.entity;

import java.util.List;
import java.util.Map;

import com.ecm.core.entity.EcmContent;
import com.ecm.core.entity.EcmRelation;
import com.ecm.core.entity.EcmUser;
import com.ecm.core.entity.ExcSynBatch;

public class SyncBean{
	private String beanType;
    public String getBeanType() {
		return beanType;
	}
	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}
	private List<Map<String, Object>> documents;;
    private List<Map<String, Object>> transfers;;
	private List<EcmRelation> relations;
	private List<EcmContent> contents;
	private EcmUser ecmUser;
	
	private List<ExcSynBatch> synBatchList;

	public List<ExcSynBatch> getSynBatchList() {
		return synBatchList;
	}
	public void setSynBatchList(List<ExcSynBatch> synBatchList) {
		this.synBatchList = synBatchList;
	}
	public EcmUser getEcmUser() {
		return ecmUser;
	}
	public void setEcmUser(EcmUser ecmUser) {
		this.ecmUser = ecmUser;
	}
	public List<Map<String, Object>> getTransfers() {
		return transfers;
	}
	public void setTransfers(List<Map<String, Object>> transfers) {
		this.transfers = transfers;
	}
	public List<EcmContent> getContents() {
		return contents;
	}
	public void setContents(List<EcmContent> contents) {
		this.contents = contents;
	}
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