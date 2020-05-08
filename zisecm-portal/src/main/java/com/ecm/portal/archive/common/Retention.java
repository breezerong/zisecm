package com.ecm.portal.archive.common;
/**
 * 保管期限
 * @author Administrator
 *
 */
public enum Retention {
	Ten("10年","10"),Thirty("30年","30"),Forever("永久","999");
	private String retentions;
	private String index;
	private Retention(String r,String index) {
		this.retentions=r;
		this.index=index;
	}
	
	public static String getRetention(String index) {
		for(Retention s:Retention.values()) {
			if(s.index.equals(index)) {
				return s.retentions;
			}
		}
		return "";
	}
}
