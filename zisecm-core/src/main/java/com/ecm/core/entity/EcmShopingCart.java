package com.ecm.core.entity;

import java.util.Date;

public class EcmShopingCart {
    private String id;

    private String docuemnt_id;
    private Date add_date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDocuemnt_id() {
		return docuemnt_id;
	}
	public void setDocuemnt_id(String docuemnt_id) {
		this.docuemnt_id = docuemnt_id;
	}
	public Date getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

 
}