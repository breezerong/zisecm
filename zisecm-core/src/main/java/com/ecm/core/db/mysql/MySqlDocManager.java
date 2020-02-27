package com.ecm.core.db.mysql;
import com.ecm.core.db.IDBDocManager;
import com.ecm.core.entity.EcmAttribute;

public class MySqlDocManager implements IDBDocManager {

	@Override
	public String getDocAttributesSql() {
		// TODO Auto-generated method stub
		return "show full columns from ecm_document";
	}

	@Override
	public String newDocAttributeSql(EcmAttribute en) {
		//ALTER TABLE ecm_document ADD C_DOC_STATUS varchar(32) NULL COMMENT '文档状态';
		//ALTER TABLE ecm_document ADD IS_CURRENT TINYINT(1) DEFAULT 1 NULL COMMENT '是否当前版本';
		//ALTER TABLE ecm_document ADD IS_HIDDEN TINYINT(1) DEFAULT 0 null COMMENT '是否隐藏';
		//ALTER ecm_document ADD column1 INT NULL;
		//ALTER ecm_document ADD column2 DATETIME NULL;
		//ALTER ecm_document ADD column3 DOUBLE NULL;
		String sql ="ALTER TABLE ecm_document ADD ";
		sql+=en.getName().toUpperCase()+" ";
		//1：String，2：DateTime，3：boolean,4：Int，5:long，6：Double，7:decimal,8:float
		switch(en.getFieldType()) {
		case 2:
			sql += "DATETIME ";
			break;
		case 3:
			sql += "TINYINT(1) ";
			break;
		case 4:
			sql += "INT ";
			break;
		case 5:
			sql += "BIGINT ";
			break;
		case 6:
			sql += "DOUBLE ";
			break;
		case 7:
			sql += "DECIMAL ";
			break;
		case 8:
			sql += "FLOAT ";
			break;
		
		default:
			sql += "varchar("+en.getLength()+") ";
			break;
		}
		if(en.getIsNull().equals("NO")) {
			sql += " NOT NULL ";
		}else {
			sql += " NULL ";
		}
		if(en.getDefaultValue()!=null&& en.getDefaultValue().length()>0) {
			if(en.getFieldType()==1) {
				sql += " DEFAULT '"+en.getDefaultValue()+"' ";
			}
			else {
				sql += " DEFAULT "+en.getDefaultValue()+" ";
			}
		}
		if(en.getDescription()!=null&&en.getDescription().length()>0) {
			sql += " COMMENT '"+en.getDescription()+"' ";
		}
		return sql;
	}

	@Override
	public String updateDocAttributeSql(EcmAttribute en) {
		String sql ="ALTER TABLE ecm_document MODIFY ";
		sql+=en.getName().toUpperCase()+" ";
		
		switch(en.getFieldType()) {
		case 2:
			sql += "DATETIME ";
			break;
		case 3:
			sql += "TINYINT(1) ";
			break;
		case 4:
			sql += "INT ";
			break;
		case 5:
			sql += "BIGINT ";
			break;
		case 6:
			sql += "DOUBLE ";
			break;
		case 7:
			sql += "DECIMAL ";
			break;
		case 8:
			sql += "FLOAT ";
			break;
		
		default:
			sql += "varchar("+en.getLength()+") ";
			break;
		}
		if(en.getIsNull().equals("NO")) {
			sql += "NOT NULL ";
		}else {
			sql += "NULL ";
		}
		if(en.getDefaultValue()!=null&& en.getDefaultValue().length()>0) {
			sql += "DEFAULT "+en.getDefaultValue()+" ";
		}
		if(en.getDescription()!=null&&en.getDescription().length()>0) {
			sql += "COMMENT '"+en.getDescription()+"' ";
		}
		return sql;
	}

	@Override
	public String deleteDocAttributeSql(EcmAttribute en) {
		// TODO Auto-generated method stub
		String sql ="ALTER TABLE ecm_document DROP COLUMN " +en.getName();
		return sql;
	}

	

}
