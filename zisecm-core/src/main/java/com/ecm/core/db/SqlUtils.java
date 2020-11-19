package com.ecm.core.db;

import java.util.List;

import com.ecm.core.entity.LoginUser;

public class SqlUtils {
	public static String replaceSql(String sql,LoginUser userObj) {
		if(sql!=null&&sql.contains("@currentuser")) {
			sql=sql.replaceAll("@currentuser", userObj.getUserName());
    	}
		if(sql!=null&&sql.contains("@dept")) {
			sql=sql.replaceAll("@dept", userObj.getDepartment());
    	}
		if(sql!=null&&sql.contains("@company")) {
			sql=sql.replaceAll("@company", userObj.getCompany());
	    }
		if(sql!=null&&sql.contains("@project")) {
			List<String> projectList= userObj.getMyProjects();
			
			if(projectList==null||projectList.size()==0) {
				sql=sql.replaceAll("'@project'", "''");
				sql=sql.replaceAll("@project", "''");
			}else {
				String whereProject=" (";
				for(int i=0;i<projectList.size();i++) {
					String project=projectList.get(i);
					if(i==0) {
						whereProject+="C_PROJECT_NAME ='"+project+"'";
					}else {
						whereProject+=" or C_PROJECT_NAME ='"+project+"'";
					}
					
				}
				whereProject+=")";
				if(sql.contains("C_PROJECT_NAME in(@project)")) {
					sql=sql.replaceAll("C_PROJECT_NAME in(@project)", whereProject);
				} 
				if(sql.contains("C_PROJECT_NAME in (@project)")) {
					sql=sql.replaceAll("C_PROJECT_NAME in (@project)", whereProject);
				} 
				if(sql.contains("C_PROJECT_NAME in (@project) ")) {
					sql=sql.replaceAll("C_PROJECT_NAME in (@project) ", whereProject);
				} 
				if(sql.contains("C_PROJECT_NAME='@project'")) {
					sql=sql.replaceAll("C_PROJECT_NAME='@project'", whereProject);
				}
				if(sql.contains("C_PROJECT_NAME ='@project'")) {
					sql=sql.replaceAll("C_PROJECT_NAME ='@project'", whereProject);
				}
				if(sql.contains("C_PROJECT_NAME = '@project'")) {
					sql=sql.replaceAll("C_PROJECT_NAME = '@project'", whereProject);
				}
				if(sql.contains("C_PROJECT_NAME = '@project' ")) {
					sql=sql.replaceAll("C_PROJECT_NAME = '@project' ", whereProject);
				}
			}
			
		}
		if(sql!=null&&sql.contains("@data")) {
			sql=sql.replaceAll("@data", "GETDATE()");
		}
		if(sql!=null&&sql.contains("'@data'")) {
			sql=sql.replaceAll("'@data'", "GETDATE()");
		}
		return sql;
	}
}
