package com.ecm.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
public class ExcTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    private Integer itemType;

    /**
     * 文件ID
     */
    private String docId;

    /**
     * 发送方
     */
    private String fromName;

    /**
     * 接收方
     */
    private String toName;

    /**
     * 创建时间
     */
    private LocalDateTime creationDate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 拒绝人
     */
    private String rejecter;

    /**
     * 拒绝日期
     */
    private LocalDateTime rejectDate;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 发送日期
     */
    private LocalDateTime sendDate;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 接收日期
     */
    private LocalDateTime receiveDate;

    /**
     * 状态
     */
    private String stauts;

    /**
     * 错误信息
     */
    private String comment;

    /**
     * 同步状态
     */
    private String synStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getRejecter() {
        return rejecter;
    }

    public void setRejecter(String rejecter) {
        this.rejecter = rejecter;
    }
    public LocalDateTime getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(LocalDateTime rejectDate) {
        this.rejectDate = rejectDate;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateTime receiveDate) {
        this.receiveDate = receiveDate;
    }
    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(String synStatus) {
        this.synStatus = synStatus;
    }

    @Override
    public String toString() {
        return "ExcTransfer{" +
            "id=" + id +
            ", itemType=" + itemType +
            ", docId=" + docId +
            ", fromName=" + fromName +
            ", toName=" + toName +
            ", creationDate=" + creationDate +
            ", creator=" + creator +
            ", rejecter=" + rejecter +
            ", rejectDate=" + rejectDate +
            ", sender=" + sender +
            ", sendDate=" + sendDate +
            ", receiver=" + receiver +
            ", receiveDate=" + receiveDate +
            ", stauts=" + stauts +
            ", comment=" + comment +
            ", synStatus=" + synStatus +
        "}";
    }
}
