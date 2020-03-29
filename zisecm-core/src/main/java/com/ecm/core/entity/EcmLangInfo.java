package com.ecm.core.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 语言信息
 * @author Haihong Rong
 * Date:2020年3月26日 下午2:25:55
 */
public class EcmLangInfo extends EcmObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * 语言标签Key
     */
    private String messageKey;
    /**
     * 语言标签类型，DocType，System
     */
    private String messageType;
/**
 * 语言标签描述
 */
    private String description;
    
    private List<EcmLangItem> langItems = new ArrayList<EcmLangItem> ();

   
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey == null ? null : messageKey.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public List<EcmLangItem> getLangItems() {
		return langItems;
	}

	public void setLangItems(List<EcmLangItem> langItems) {
		this.langItems = langItems;
	}
	
	public String getLabel(String langKey) {
		for(EcmLangItem item: langItems) {
			if(item.getLangKey().equals(langKey)) {
				return item.getMessageLabel();
			}
		}
		return "";
	}
}