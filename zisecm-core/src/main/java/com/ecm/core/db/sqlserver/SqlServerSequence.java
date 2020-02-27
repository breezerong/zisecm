package com.ecm.core.db.sqlserver;

import com.ecm.core.db.IDBSequence;

public class SqlServerSequence implements IDBSequence{

	@Override
	public String getContentDataTicketSql() {
		// TODO Auto-generated method stub
		return "exec next_val 'data_ticket'";
	}

	

}
