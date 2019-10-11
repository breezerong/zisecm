package com.ecm.core.entity;

import java.util.Date;

public class EcmQueueItem extends EcmObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private String name;

    private String eventName;

    private Date sendDate;

    private String sendUser;

    private Date dqueueDate;

    private String dqueueUser;

    private Boolean delectFlag = false;

    private String objectId;

    private Integer failureCount;

    private String message;

    private String taskName;

    private String workitemId;

    private String routerId;

    private Integer status =0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser == null ? null : sendUser.trim();
    }

    public Date getDqueueDate() {
        return dqueueDate;
    }

    public void setDqueueDate(Date dqueueDate) {
        this.dqueueDate = dqueueDate;
    }

    public String getDqueueUser() {
        return dqueueUser;
    }

    public void setDqueueUser(String dqueueUser) {
        this.dqueueUser = dqueueUser == null ? null : dqueueUser.trim();
    }

    public Boolean getDelectFlag() {
        return delectFlag;
    }

    public void setDelectFlag(Boolean delectFlag) {
        this.delectFlag = delectFlag;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}