package org.zisecm.jobs.bean.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class RelationShip {
	private String name;
	private String refTcType;//对应tc的类型
//	private List<SearchConf> SearchConfs;
	private String childrenDataSql;
	
	private String referenceName;//<!-- p2c代表设计分包系统中主表映射值子表，相反c2p是子表映射至主表 -->
	
	private String refformId;//formName
	
	private String fileListColumn;
	
	private SearchConf searchConf;
	
	private String formName;
	
	private String id;
	
	private String refSubId;//指向子表id根据子表id获取子表配置
	
	@XmlAttribute
	public String getRefSubId() {
		return refSubId;
	}

	public void setRefSubId(String refSubId) {
		this.refSubId = refSubId;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	private Map<String,Object> data=new HashMap<String, Object>();
	public void pub(String key,Object value) {
		if(this.data==null) {
			this.data=new HashMap<String, Object>();
		}
		this.data.put(key, value);
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
//	<relationShip fileListColumn="" refformName="" name="关系名称" refTcType="对应tc的类型" referenceName="p2c"><!-- p2c代表设计分包系统中主表映射值子表，相反c2p是子表映射至主表 -->
	
	@XmlAttribute
	public String getChildrenDataSql() {
		return childrenDataSql;
	}
	@XmlAttribute
	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	@XmlAttribute
	public String getRefformId() {
		return refformId;
	}

	public void setRefformId(String refformId) {
		this.refformId = refformId;
	}
	@XmlAttribute
	public String getFileListColumn() {
		return fileListColumn;
	}

	public void setFileListColumn(String fileListColumn) {
		this.fileListColumn = fileListColumn;
	}

	public void setChildrenDataSql(String childrenDataSql) {
		this.childrenDataSql = childrenDataSql;
	}

	@XmlAttribute
	public String getRefTcType() {
		return refTcType;
	}

	public void setRefTcType(String refTcType) {
		this.refTcType = refTcType;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@XmlElement(name="searchConf")
	public SearchConf getSearchConf() {
		return searchConf;
	}

	public void setSearchConf(SearchConf searchConf) {
		this.searchConf = searchConf;
	}
	
	
}
