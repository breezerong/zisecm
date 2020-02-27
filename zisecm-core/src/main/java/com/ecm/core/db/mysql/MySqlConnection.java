package com.ecm.core.db.mysql;

import com.ecm.core.db.IDBConnection;
import com.ecm.core.db.IDBDocManager;
import com.ecm.core.db.IDBSequence;
import com.ecm.core.db.IDBUtils;

public class MySqlConnection implements IDBConnection{

	private IDBUtils dBUtils = new MySqlUtils();
	private IDBDocManager dBDocManager = new MySqlDocManager();
	private IDBSequence dBSequence = new MySqlSequence();
	
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
