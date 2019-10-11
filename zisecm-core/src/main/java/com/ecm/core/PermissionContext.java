package com.ecm.core;
/**
 * 权限定
 * @author Haihong Rong
 *
 */
public class PermissionContext {
	/**
	 * 所有用户
	 */
	public static String EVERYONE = "everyone";
	
	/**
	 * 所有者
	 */
	public static String OWNER = "owner";
	/**
	 * 用户类型标识
	 */
	public static int USER_TARGET_TYPE=1;
	/**
	 * 组类型标识
	 */
	public static int GROUP_TARGET_TYPE=2;
	/**
	 * 系统权限
	 * @author Haihong Rong
	 *
	 */
	public class SystemPermission{
		/**
		 * 1.参数配置
		 */
		public static final int PARAMETER_CONFIGE=1;
		/**
		 * 2.用户角色
		 */
		public static final int USER_GROUP_CONFIGE=2;
		/**
		 * 3.流程配置
		 */
		public static final int WORKFLOW_CONFIGE=3;
		/**
		 * 4.界面配置
		 */
		public static final int UI_CONFIGE=4;
		/**
		 * 5.系统配置
		 */
		public static final int SYSTEM_CONFIGE=5;
		/**
		 * 9.超级用户
		 */
		public static final int SUPER_USER=9;
	}
	/**
	 * 客户端权限
	 * @author Haihong Rong
	 *
	 */
	public class ClientPermission{
		/**
		 * 1.查询
		 */
		public static final int SEARCH=1;
		/**
		 * 2.新建文档
		 */
		public static final int CREATE_DOCUMENT=2;
		/**
		 * 3.执行流程
		 */
		public static final int WORKFLOW=3;
		/**
		 * 4.文件管理员
		 */
		public static final int DOCUMENT_ADMIN=4;
		/**
		 * 5.系统管理员
		 */
		public static final int SYSTEM_ADMIN=5;
	}
	/**
	 * 文档权限
	 * @author Haihong Rong
	 *
	 */
	public class ObjectPermission{
		
		/**
		 * 1.无权限
		 */
		public static final int NONE=1;
		
		/**
		 * 2.查询
		 */
		public static final int BROWSER=2;
		/**
		 * 3.查看内容
		 */
		public static final int READ=3;
		
		/**
		 * 4.下载
		 */
		public static final int DOWNLOAD=4;
		/**
		 * 5.编辑属性
		 */
		public static final int WRITE_ATTRIBUTE=5;
		/**
		 * 6.升版
		 */
		public static final int NEW_REVISION=6;
		/**
		 * 7.编辑内容
		 */
		public static final int WRITE_CONTENT=7;
		
		/**
		 * 8.删除
		 */
		public static final int DELETE=8;
		
		/**
		 * 9.设置权限
		 */
		public static final int PEMISSION=9;
		
	}
	/**
	 * 扩展权限
	 * @author Haihong Rong
	 *
	 */
	public class ExtendPermission{
		
	}
}
