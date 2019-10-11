package com.ecm.core.entity;

import java.util.Date;

public class EcmSearchAudit  extends EcmObject{
   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

    private String userName;

    private String inputKey;

    private Date searchDate;

   
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getInputKey() {
        return inputKey;
    }

    public void setInputKey(String inputKey) {
        this.inputKey = inputKey == null ? null : inputKey.trim();
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
}