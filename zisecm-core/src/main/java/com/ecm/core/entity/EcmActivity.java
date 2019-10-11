package com.ecm.core.entity;

import java.util.List;

public class EcmActivity extends EcmSysObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer itemType;

    private String performer;

    private String methodName;

    private Integer performType;

    private String processId;
    
    private Integer nextCount;
    /**
     * 角色处理方式，0：所有人都完成，1：任意一个人完成，2：指定数量人完成
     */
    private Integer roleType;

    private Integer roleCount;
    /*序号
     *
     */
    private Integer orderIndex;
    
    private List<EcmTransaction> transactions;

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer == null ? null : performer.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public Integer getPerformType() {
        return performType;
    }

    public void setPerformType(Integer performType) {
        this.performType = performType;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getRoleCount() {
        return roleCount;
    }

    public void setRoleCount(Integer roleCount) {
        this.roleCount = roleCount;
    }

	public List<EcmTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<EcmTransaction> transactions) {
		this.transactions = transactions;
	}

	public Integer getNextCount() {
		return nextCount;
	}

	public void setNextCount(Integer nextCount) {
		this.nextCount = nextCount;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
}