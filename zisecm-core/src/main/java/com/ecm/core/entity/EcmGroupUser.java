package com.ecm.core.entity;

public class EcmGroupUser extends EcmObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String groupId;

    private String userId;


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}