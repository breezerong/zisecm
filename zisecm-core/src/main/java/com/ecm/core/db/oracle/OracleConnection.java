package com.ecm.core.db.oracle;

import com.ecm.core.db.IDBConnection;
import com.ecm.core.db.IDBDocManager;
import com.ecm.core.db.IDBSequence;
import com.ecm.core.db.IDBUtils;

public class OracleConnection implements IDBConnection{

	private IDBUtils dBUtils = new OracleUtils();
	private IDBDocManager dBDocManager = new OracleDocManager();
	private IDBSequence dBSequence = new OracleSequence();
	
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
