package com.ecm.core.db.postgresql;

import com.ecm.core.db.IDBConnection;
import com.ecm.core.db.IDBDocManager;
import com.ecm.core.db.IDBSequence;
import com.ecm.core.db.IDBUtils;

public class PostgreSqlConnection implements IDBConnection{

	private IDBUtils dBUtils = new PostgreSqlUtils();
	private IDBDocManager dBDocManager = new PostgreSqlDocManager();
	private IDBSequence dBSequence = new PostgreSqlSequence();
	
	@Override
	public IDBUtils getDBUtils() {
		// TODO Auto-generated method stub
		return dBUtils;
	}

	@Override
	public IDBDocManager getDBDocManager() {
		// TODO Auto-generated method stub
		return dBDocManager;
	}

	@Override
	public IDBSequence getDBSequece() {
		// TODO Auto-generated method stub
		return dBSequence;
	}

}
