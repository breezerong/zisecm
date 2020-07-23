package com.ecm.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
public class ExcSynDetail extends EcmObject {

    private static final long serialVersionUID = 1L;

   
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 事件名称
     */
    private String actionName;

    /**
     * 批次号
     */
    private String batchNum;

    /**
     * 同步数据ID
     */
    private String fromId;

    /**
     * 目标对象ID
     */
    private String toId;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 导出时间
     */
    private Date exportDate;

    /**
     * 状态
     */
    private String stauts;

    /**
     * 导入时间
     */
    private Date importDate;
    /**
     * 目标公司
     */
    private String toCompany;
    
    /**
     * 错误信息
     */
    private String errorMessage;

   
    public String getToCompany() {
		return toCompany;
	}

	public void setToCompany(String toCompany) {
		this.toCompany = toCompany;
	}

	public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }
    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }
    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ExcSynDetail{" +
            "id=" + getId() +
            ", appName=" + appName +
            ", actionName=" + actionName +
            ", batchNum=" + batchNum +
            ", fromId=" + fromId +
            ", toId=" + toId +
            ", creationDate=" + creationDate +
            ", exportDate=" + exportDate +
            ", stauts=" + stauts +
            ", importDate=" + importDate +
            ", errorMessage=" + errorMessage +
        "}";
    }
}
