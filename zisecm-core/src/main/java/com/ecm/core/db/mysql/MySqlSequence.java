package com.ecm.core.db.mysql;

import org.springframework.stereotype.Component;

import com.ecm.core.db.IDBSequence;
@Component
public class MySqlSequence implements IDBSequence{

	@Override
	public String getContentDataTicketSql() {
		// TODO Auto-generated method stub
		return "select next_val('data_ticket')";
	}

}
