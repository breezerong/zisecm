package com.ecm.core.entity;

public class EcmStorageRow {
    private String id;

    private String coding;

    private String parentCoding;

    private Double totalLength;

    private Double remainLength;

    private Integer archiveCount;

    private Integer itemCount;

    private String description;

    private String stauts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding == null ? null : coding.trim();
    }

    public String getParentCoding() {
        return parentCoding;
    }

    public void setParentCoding(String parentCoding) {
        this.parentCoding = parentCoding == null ? null : parentCoding.trim();
    }

    public Double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Double totalLength) {
        this.totalLength = totalLength;
    }

    public Double getRemainLength() {
        return remainLength;
    }

    public void setRemainLength(Double remainLength) {
        this.remainLength = remainLength;
    }

    public Integer getArchiveCount() {
        return archiveCount;
    }

    public void setArchiveCount(Integer archiveCount) {
        this.archiveCount = archiveCount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts == null ? null : stauts.trim();
    }
}