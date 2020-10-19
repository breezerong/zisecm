package org.zisecm.jobs.bean.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ConfBean {
	private List<SubTable> uprelationShips;
	private List<SubTable> downrelationShips;
	private List<AttrBean> attributes;
	private Map<String,Object> data=new HashMap<String, Object>();
	
	public void put(String key,String value) {
		if(data==null) {
			data=new HashMap<String, Object>();
		}
		data.put(key, value);
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	@XmlElementWrapper(name="attributes")
	@XmlElement(name="attribute")
	public List<AttrBean> getAttributes() {
		return attributes;
	}
	
	@XmlElementWrapper(name="uprelationShips")
	@XmlElement(name="subtable")
	public List<SubTable> getUprelationShips() {
		return uprelationShips;
	}

	public void setUprelationShips(List<SubTable> uprelationShips) {
		this.uprelationShips = uprelationShips;
	}
	
	@XmlElementWrapper(name="downrelationShips")
	@XmlElement(name="subtable")
	public List<SubTable> getDownrelationShips() {
		return downrelationShips;
	}
	
	public void setDownrelationShips(List<SubTable> downrelationShips) {
		this.downrelationShips = downrelationShips;
	}
	
	private String ccColumn;
	private String sendToColumn;
	
	
	@XmlAttribute
	public String getCcColumn() {
		return ccColumn;
	}

	public void setCcColumn(String ccColumn) {
		this.ccColumn = ccColumn;
	}
	@XmlAttribute
	public String getSendToColumn() {
		return sendToColumn;
	}

	public void setSendToColumn(String sendToColumn) {
		this.sendToColumn = sendToColumn;
	}

	@XmlAttribute
	public String getSourceTypeName() {
		return sourceTypeName;
	}


	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}
	@XmlAttribute
	public String getTcTypeName() {
		return tcTypeName;
	}

	public void setTcTypeName(String tcTypeName) {
		this.tcTypeName = tcTypeName;
	}
	@XmlAttribute
	public String getTcTableName() {
		return tcTableName;
	}

	public void setTcTableName(String tcTableName) {
		this.tcTableName = tcTableName;
	}

	public void setAttributes(List<AttrBean> attributes) {
		this.attributes = attributes;
	}
	
	@XmlAttribute
	public String getTargetSystem() {
		return targetSystem;
	}
	public void setTargetSystem(String targetSystem) {
		this.targetSystem = targetSystem;
	}

	private String id;
	
	private String sourceTypeName;
	
	private String tcTypeName;
	
	private String tcTableName;
	
	private String targetSystem;
	
	private String childType;
	
	private String syncQuerySql;
	
	private QueryConstructor queryConstructor;
	
	private String workflowName;
	
	@XmlAttribute
	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public QueryConstructor getQueryConstructor() {
		return queryConstructor;
	}

	public void setQueryConstructor(QueryConstructor queryConstructor) {
		this.queryConstructor = queryConstructor;
	}

	@XmlAttribute
	public String getSyncQuerySql() {
		return syncQuerySql;
	}

	public void setSyncQuerySql(String syncQuerySql) {
		this.syncQuerySql = syncQuerySql;
	}

	
	@XmlAttribute
	public String getChildType() {
		return childType;
	}
	

	public void setChildType(String childType) {
		this.childType = childType;
	}
	
	private SearchConf validateSearchConf;
	@XmlElement
	public SearchConf getValidateSearchConf() {
		return validateSearchConf;
	}

	public void setValidateSearchConf(SearchConf validateSearchConf) {
		this.validateSearchConf = validateSearchConf;
	}
	
}
