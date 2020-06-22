package com.ecm.core.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chen Shuo
 * @since 2020-06-22
 */
public class EcmUiRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String relationName;

    private String gridName;

    private Integer readonly;

    private String typeName;

    private String formName;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }
    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }
    public Integer getReadonly() {
        return readonly;
    }

    public void setReadonly(Integer readonly) {
        this.readonly = readonly;
    }
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EcmUiRelation{" +
            "id=" + id +
            ", relationName=" + relationName +
            ", gridName=" + gridName +
            ", readonly=" + readonly +
            ", typeName=" + typeName +
            ", formName=" + formName +
            ", description=" + description +
        "}";
    }
}
