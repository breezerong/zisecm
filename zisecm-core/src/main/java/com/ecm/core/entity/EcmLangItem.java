package com.ecm.core.entity;
/**
 * 语言值
 * @author Haihong Rong
 * Date:2020年3月26日 下午2:26:36
 */
public class EcmLangItem extends EcmObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	/**
	 * 语言标识
	 */
    private String messageKey;

    /**
     * 语言类型，zh-cn,en等
     */
    private String langKey;
	/**
	 * 语言值
	 */
    private String messageLabel;

    
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey == null ? null : messageKey.trim();
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey == null ? null : langKey.trim();
    }

    public String getMessageLabel() {
        return messageLabel;
    }

    public void setMessageLabel(String messageLabel) {
        this.messageLabel = messageLabel == null ? null : messageLabel.trim();
    }
}