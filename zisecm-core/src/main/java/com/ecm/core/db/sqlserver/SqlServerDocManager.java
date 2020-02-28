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
		String sql ="ALTER TABLE ecm_document ADD ";
		sql+=en.getName().toUpperCase()+" ";
		//1：String，2：DateTime，3：boolean,4：Int，5:long，6：Double，7:decimal,8:float
		switch(en.getFieldType()) {
		case 2:
			sql += "datetime ";
			break;
		case 3:
			sql += "tinyint ";
			break;
		case 4:
			sql += "int ";
			break;
		case 5:
			sql += "bigint ";
			break;
		case 6:
			sql += "double ";
			break;
		case 7:
			sql += "decimal ";
			break;
		case 8:
			sql += "float ";
			break;
		
		default:
			sql += "nvarchar("+en.getLength()+") ";
			break;
		}
		if(en.getIsNull().equals("NO")) {
			sql += " NOT NULL ";
		}else {
			sql += " NULL ";
		}
		if(en.getDefaultValue()!=null&& en.getDefaultValue().length()>0) {
			sql += "\n\rGO\n\r";
			sql += "alter table ecm_document add CONSTRAINT DF_ecm_document_"+en.getName();
			if(en.getFieldType()==1) {
				sql += " DEFAULT N'"+en.getDefaultValue()+"' ";
			}
			else {
				sql += " DEFAULT "+en.getDefaultValue()+" ";
			}
			sql += " FOR "+en.getName();
		}
		if(en.getDescription()!=null&&en.getDescription().length()>0) {
			sql += "\n\rGO\n\r";
			sql += "EXEC sys.sp_addextendedproperty @name=N'MS_"+en.getName()+"', @value=N'"+en.getDescription()
			+"' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ecm_document', "
			+"@level2type=N'COLUMN',@level2name=N'"+en.getName()+"'";
		}
		return sql;
	}

	@Override
	public String updateDocAttributeSql(EcmAttribute en) {
		// SQL Server不能直接修改字段长度，修改描述需要先查询再修改，比较麻烦，暂时不在线修改
		return null;
	}

	@Override
	public String deleteDocAttributeSql(EcmAttribute en) {
		String sql ="ALTER TABLE ecm_document DROP COLUMN " +en.getName();
		return sql;
	}

	

}
