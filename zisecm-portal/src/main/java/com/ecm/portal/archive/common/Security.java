package com.ecm.portal.archive.common;
/**
 * 密级
 * @author Administrator
 *
 */
public enum Security {
	Core("核心商密","1"),General("普通商密","1"),Limited("受限","3"),Expose("内部公开","4");
	private String zhSecurity;
	private String index;
	private Security(String security,String index) {
		this.zhSecurity=security;
		this.index=index;
	}
	/**
	 * 根据密级index获取密级中文
	 * @param index
	 * @return
	 */
	public static String getSecurity(String index) {
		for(Security s:Security.values()) {
			if(s.index.equals(index)) {
				return s.zhSecurity;
			}
		}
		return "";
	}
}
