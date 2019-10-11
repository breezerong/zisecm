package com.ecm.core.entity;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.ecm.common.util.FileUtils;

public class EcmContent extends EcmSysObject{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String filePath;

    private String parentId;
    private Integer contentType;

    private String formatName;

    private Long contentSize;

    private String storeName;

    private Integer orderIndex;
    
    private Long dataTicket;
    
    private InputStream inputStream;

    @Override
    public void setName(String name) {
    	super.setName(name);
    	if(StringUtils.isEmpty(formatName)) {
    		this.setFormatName(FileUtils.getExtention(name));
    	}
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

   
    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName == null ? null : formatName.trim();
    }

    public Long getContentSize() {
        return contentSize;
    }

    public void setContentSize(Long contentSize) {
        this.contentSize = contentSize;
    }

    
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

	public Long getDataTicket() {
		return dataTicket;
	}

	public void setDataTicket(Long dataTicket) {
		this.dataTicket = dataTicket;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}