/**
 * DENAddedMatList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DENAddedMatList",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DENAddedMatList",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class DENAddedMatList  implements java.io.Serializable {
    private java.lang.String fileExtCode;

    private java.lang.String fileRev;

    private java.lang.String seqNo;

    private java.lang.String matNo;

    private java.lang.String matName;

    private java.lang.String matType;

    private java.lang.String quantity;

    private java.lang.String unitofValue;

    public DENAddedMatList() {
    }

    public DENAddedMatList(
           java.lang.String fileExtCode,
           java.lang.String fileRev,
           java.lang.String seqNo,
           java.lang.String matNo,
           java.lang.String matName,
           java.lang.String matType,
           java.lang.String quantity,
           java.lang.String unitofValue) {
           this.fileExtCode = fileExtCode;
           this.fileRev = fileRev;
           this.seqNo = seqNo;
           this.matNo = matNo;
           this.matName = matName;
           this.matType = matType;
           this.quantity = quantity;
           this.unitofValue = unitofValue;
    }


    /**
     * Gets the fileExtCode value for this DENAddedMatList.
     * 
     * @return fileExtCode
     */
    public java.lang.String getFileExtCode() {
        return fileExtCode;
    }


    /**
     * Sets the fileExtCode value for this DENAddedMatList.
     * 
     * @param fileExtCode
     */
    public void setFileExtCode(java.lang.String fileExtCode) {
        this.fileExtCode = fileExtCode;
    }


    /**
     * Gets the fileRev value for this DENAddedMatList.
     * 
     * @return fileRev
     */
    public java.lang.String getFileRev() {
        return fileRev;
    }


    /**
     * Sets the fileRev value for this DENAddedMatList.
     * 
     * @param fileRev
     */
    public void setFileRev(java.lang.String fileRev) {
        this.fileRev = fileRev;
    }


    /**
     * Gets the seqNo value for this DENAddedMatList.
     * 
     * @return seqNo
     */
    public java.lang.String getSeqNo() {
        return seqNo;
    }


    /**
     * Sets the seqNo value for this DENAddedMatList.
     * 
     * @param seqNo
     */
    public void setSeqNo(java.lang.String seqNo) {
        this.seqNo = seqNo;
    }


    /**
     * Gets the matNo value for this DENAddedMatList.
     * 
     * @return matNo
     */
    public java.lang.String getMatNo() {
        return matNo;
    }


    /**
     * Sets the matNo value for this DENAddedMatList.
     * 
     * @param matNo
     */
    public void setMatNo(java.lang.String matNo) {
        this.matNo = matNo;
    }


    /**
     * Gets the matName value for this DENAddedMatList.
     * 
     * @return matName
     */
    public java.lang.String getMatName() {
        return matName;
    }


    /**
     * Sets the matName value for this DENAddedMatList.
     * 
     * @param matName
     */
    public void setMatName(java.lang.String matName) {
        this.matName = matName;
    }


    /**
     * Gets the matType value for this DENAddedMatList.
     * 
     * @return matType
     */
    public java.lang.String getMatType() {
        return matType;
    }


    /**
     * Sets the matType value for this DENAddedMatList.
     * 
     * @param matType
     */
    public void setMatType(java.lang.String matType) {
        this.matType = matType;
    }


    /**
     * Gets the quantity value for this DENAddedMatList.
     * 
     * @return quantity
     */
    public java.lang.String getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this DENAddedMatList.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.String quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the unitofValue value for this DENAddedMatList.
     * 
     * @return unitofValue
     */
    public java.lang.String getUnitofValue() {
        return unitofValue;
    }


    /**
     * Sets the unitofValue value for this DENAddedMatList.
     * 
     * @param unitofValue
     */
    public void setUnitofValue(java.lang.String unitofValue) {
        this.unitofValue = unitofValue;
    }

  

}
