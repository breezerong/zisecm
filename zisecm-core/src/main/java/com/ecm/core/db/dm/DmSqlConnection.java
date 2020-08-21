package com.ecm.core.db.dm;

import com.ecm.core.db.IDBConnection;
import com.ecm.core.db.IDBDocManager;
import com.ecm.core.db.IDBSequence;
import com.ecm.core.db.IDBUtils;

public class DmSqlConnection implements IDBConnection{

	private IDBUtils dBUtils = new DmUtils();
	private IDBDocManager dBDocManager = new DmDocManager();
	private IDBSequence dBSequence = new DmSequence();
	
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
