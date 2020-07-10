package com.ecm.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-24
 */
public class ExcTransfer extends EcmObject {

    private static final long serialVersionUID = 1L;


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
    private Date creationDate;

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
    private Date rejectDate;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 发送日期
     */
    private Date sendDate;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 接收日期
     */
    private Date receiveDate;

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

    /**
     * 1：CNPE发送，2：分包商发送
     * @return
     */
    public Integer getItemType() {
        return itemType;
    }
    /**
     * 1：CNPE发送，2：分包商发送
     * @param itemType
     */
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
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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
    public Date getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
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
            "id=" + getId() +
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
