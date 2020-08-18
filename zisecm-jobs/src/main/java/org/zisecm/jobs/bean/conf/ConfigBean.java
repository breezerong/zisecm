package org.zisecm.jobs.bean.conf;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="config")
public class ConfigBean {
	private List<ConfBean> confs;
	
	@XmlElementWrapper(name="confs")
	@XmlElement(name="conf")
	public List<ConfBean> getConfs() {
		return confs;
	}

	public void setConfs(List<ConfBean> confs) {
		this.confs = confs;
	}
	
}
