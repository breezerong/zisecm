package com.ecm.pdfConversion.openoffic.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * openOffice 配置
 *
 
 */
@ConfigurationProperties("jodconverter")
@Data
public class JodConverterProperties {
    private boolean enabled;
    private String officeHome;
    private String portNumbers = "8100";
    private String workingDir;
    private String templateProfileDir;
    private boolean killExistingProcess = true;
    private long processTimeout = 120000L;
    private long processRetryInterval = 250L;
    private long taskExecutionTimeout = 120000L;
    private int maxTasksPerProcess = 200;
    private long taskQueueTimeout = 30000L;

    public String getOfficeHome() {
        //根据不同系统分别设置
        //office-home: C:\Program Files (x86)\OpenOffice 4  #windows下默认 不用修改
        // #office-home: /opt/openoffice4      #linux 默认 不用修改
        if (ResourceComponent.isWindowsSystem()) {
            this.officeHome = "C:\\Program Files (x86)\\OpenOffice 4";
        } else {
            this.officeHome = "/opt/openoffice4";
        }
        return this.officeHome;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPortNumbers() {
		return portNumbers;
	}

	public void setPortNumbers(String portNumbers) {
		this.portNumbers = portNumbers;
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

	public String getTemplateProfileDir() {
		return templateProfileDir;
	}

	public void setTemplateProfileDir(String templateProfileDir) {
		this.templateProfileDir = templateProfileDir;
	}

	public boolean isKillExistingProcess() {
		return killExistingProcess;
	}

	public void setKillExistingProcess(boolean killExistingProcess) {
		this.killExistingProcess = killExistingProcess;
	}

	public long getProcessTimeout() {
		return processTimeout;
	}

	public void setProcessTimeout(long processTimeout) {
		this.processTimeout = processTimeout;
	}

	public long getProcessRetryInterval() {
		return processRetryInterval;
	}

	public void setProcessRetryInterval(long processRetryInterval) {
		this.processRetryInterval = processRetryInterval;
	}

	public long getTaskExecutionTimeout() {
		return taskExecutionTimeout;
	}

	public void setTaskExecutionTimeout(long taskExecutionTimeout) {
		this.taskExecutionTimeout = taskExecutionTimeout;
	}

	public int getMaxTasksPerProcess() {
		return maxTasksPerProcess;
	}

	public void setMaxTasksPerProcess(int maxTasksPerProcess) {
		this.maxTasksPerProcess = maxTasksPerProcess;
	}

	public long getTaskQueueTimeout() {
		return taskQueueTimeout;
	}

	public void setTaskQueueTimeout(long taskQueueTimeout) {
		this.taskQueueTimeout = taskQueueTimeout;
	}

	public void setOfficeHome(String officeHome) {
		this.officeHome = officeHome;
	}

}