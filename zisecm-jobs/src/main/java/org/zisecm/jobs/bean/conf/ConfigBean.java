package org.zisecm.jobs.bean.conf;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class ConfigBean {
	private List<ConfBean> mainconfs;
	private List<ConfBean> subconfs;
	
	@XmlElementWrapper(name="mainconfs")
	@XmlElement(name="conf")
	public List<ConfBean> getMainconfs() {
		return mainconfs;
	}

	public void setMainconfs(List<ConfBean> mainconfs) {
		this.mainconfs = mainconfs;
	}
	
	@XmlElementWrapper(name="subconfs")
	@XmlElement(name="conf")
	public List<ConfBean> getSubconfs() {
		return subconfs;
	}

	public void setSubconfs(List<ConfBean> subconfs) {
		this.subconfs = subconfs;
	}
	
	
	
	
	
	
	
}
