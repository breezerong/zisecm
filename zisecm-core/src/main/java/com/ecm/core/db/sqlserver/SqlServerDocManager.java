package com.ecm.core.db.sqlserver;

import java.util.List;

import com.ecm.core.db.IDBDocManager;
import com.ecm.core.entity.EcmAttribute;

public class SqlServerDocManager implements IDBDocManager {

	@Override
	public String getDocAttributesSql() {
		// TODO Auto-generated method stub
		String sql = "SELECT" + 
				"    [Field]=C.name," + 
				"    [Type]=T.name," + 
				"    [Length]=C.max_length," + 
				"    [Null]= C.is_nullable," + 
				"    [Default]=CAST(ISNULL(D.definition,N'') as varchar(255))," + 
				"    [Comment]=CAST(ISNULL(PFD.[value],N'') as varchar(255))" + 
				"FROM sys.columns C" + 
				"    INNER JOIN sys.objects O" + 
				"        ON C.[object_id]=O.[object_id]" + 
				"            AND O.type='U'" + 
				"            AND O.is_ms_shipped=0" + 
				"    INNER JOIN sys.types T" + 
				"        ON C.user_type_id=T.user_type_id" + 
				"    LEFT JOIN sys.default_constraints D" + 
				"        ON C.[object_id]=D.parent_object_id" + 
				"            AND C.column_id=D.parent_column_id" + 
				"            AND C.default_object_id=D.[object_id]" + 
				"LEFT JOIN sys.extended_properties PFD" + 
				"        ON PFD.class=1" + 
				"            AND C.[object_id]=PFD.major_id" + 
				"            AND C.column_id=PFD.minor_id" + 
				"    LEFT JOIN sys.extended_properties PTB" + 
				"        ON PTB.class=1" + 
				"            AND PTB.minor_id=0" + 
				"            AND C.[object_id]=PTB.major_id" + 
				"WHERE O.name = 'ecm_document'" + 
				"ORDER BY O.name,C.column_id";
		return sql;
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
