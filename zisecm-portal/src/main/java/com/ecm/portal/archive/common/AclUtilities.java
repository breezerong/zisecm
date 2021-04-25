package com.ecm.portal.archive.common;

import com.ecm.core.cache.manager.CacheManagerOper;
import com.google.common.base.Strings;

public class AclUtilities {
	public String getReleaseAclName(String SecurityLevel) {
		return getReleaseAclName(SecurityLevel, null);
	}
	public String getReleaseAclName(String SecurityLevel, String department) {
		if (Strings.isNullOrEmpty(department)) {
			department = "01";
		} else {
			department = "01";
		}
		String aclName = "";
		if (CacheManagerOper.getEcmParameters().get("isNpic") != null) {
			if (SecurityLevel != null) {
				switch (SecurityLevel) {
				case "非密":
				case "内部":
					aclName = "acl_" + department + "_release_public";
					break;
				case "秘密":
				case "普通商密":
				case "机密":
				case "核心商密":
					aclName = "acl_" + department + "_release_security";
					break;
				default:
					aclName = "acl_" + department + "_release_public";
					break;
				}
			} else {
				aclName = "acl_" + department + "_release_public";
			}
		} else {
			if (SecurityLevel != null) {
				switch (SecurityLevel) {
				case "内部公开":
					aclName = "acl_release_public";
					break;
				case "受限":
					aclName = "acl_release_control";
					break;
				case "秘密":
				case "普通商密":
				case "机密":
				case "核心商密":
					aclName = "acl_release_scuret";
					break;
				default:
					aclName = "acl_release_public";
					break;
				}

			} else {
				aclName = "acl_release_public";
			}
		}
		return aclName;
	}
}
