package com.ecm.core.db.oracle;

import com.ecm.core.db.IDBDocManager;
import com.ecm.core.entity.EcmAttribute;

public class OracleDocManager implements IDBDocManager {

	@Override
	public String getDocAttributesSql() {
		// TODO Auto-generated method stub
		return "select a.COLUMN_NAME as \"Field\",a.DATA_TYPE as \"Type\",a.DATA_LENGTH as \"Length\",a.NULLABLE as \"Null\",a.DATA_DEFAULT as \"Default\",b.COMMENTS as \"Comment\" from user_tab_columns a, user_col_comments b where a.table_name='ECM_DOCUMENT' and A.TABLE_NAME=B.TABLE_NAME and A.COLUMN_NAME=B.COLUMN_NAME";
	}

	@Override
	public String newDocAttributeSql(EcmAttribute en) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateDocAttributeSql(EcmAttribute en) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDocAttributeSql(EcmAttribute en) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
