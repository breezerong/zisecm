package com.ecm.core.db.oracle;

import com.ecm.core.db.IDBSequence;

public class OracleSequence implements IDBSequence{

	@Override
	public String getContentDataTicketSql() {
		// TODO Auto-generated method stub
		return "select ecm_data_ticket.nextval from dual";
	}

	
}
