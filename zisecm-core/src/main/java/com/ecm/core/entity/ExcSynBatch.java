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
public class ExcSynBatch extends EcmObject {

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
     * 创建时间
     */
    private Date creationDate;

    /**
     * 状态
     */
    private String stauts;

    /**
     * 执行时间
     */
    private Date executeDate;

    /**
     * 新建数目
     */
    private Integer newCount;

    /**
     * 更新数目
     */
    private Integer updateCount;

    /**
     * 失败数目
     */
    private Integer failCount;

    
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
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }
    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }
    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }
    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    @Override
    public String toString() {
        return "ExcSynBatch{" +
            "id=" + getId() +
            ", appName=" + appName +
            ", actionName=" + actionName +
            ", batchNum=" + batchNum +
            ", creationDate=" + creationDate +
            ", stauts=" + stauts +
            ", executeDate=" + executeDate +
            ", newCount=" + newCount +
            ", updateCount=" + updateCount +
            ", failCount=" + failCount +
        "}";
    }
}
