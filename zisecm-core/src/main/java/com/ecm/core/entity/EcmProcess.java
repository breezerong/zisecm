package com.ecm.core.entity;

import java.util.List;

public class EcmProcess extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer status;
    
    private List<EcmActivity> activities;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<EcmActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<EcmActivity> activities) {
		this.activities = activities;
	}
}