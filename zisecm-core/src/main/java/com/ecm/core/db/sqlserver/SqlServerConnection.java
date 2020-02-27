package com.ecm.core.db.sqlserver;

import com.ecm.core.db.IDBConnection;
import com.ecm.core.db.IDBDocManager;
import com.ecm.core.db.IDBSequence;
import com.ecm.core.db.IDBUtils;

public class SqlServerConnection implements IDBConnection{

	private IDBUtils dBUtils = new SqlServerlUtils();
	private IDBDocManager dBDocManager = new SqlServerDocManager();
	private IDBSequence dBSequence = new SqlServerSequence();
	
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
