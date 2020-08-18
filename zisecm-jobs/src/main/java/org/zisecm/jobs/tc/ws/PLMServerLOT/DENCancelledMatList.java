/**
 * DENCancelledMatList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DENCancelledMatList",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DENCancelledMatList",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class DENCancelledMatList  implements java.io.Serializable {
    private java.lang.String fileExtCode;

    private java.lang.String fileRev;

    private java.lang.String seqNo;

    private java.lang.String matNo;

    private java.lang.String matName;

    private java.lang.String matType;

    private java.lang.String quantity;

    private java.lang.String unitofValue;

    public DENCancelledMatList() {
    }

    public DENCancelledMatList(
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
     * Gets the fileExtCode value for this DENCancelledMatList.
     * 
     * @return fileExtCode
     */
    public java.lang.String getFileExtCode() {
        return fileExtCode;
    }


    /**
     * Sets the fileExtCode value for this DENCancelledMatList.
     * 
     * @param fileExtCode
     */
    public void setFileExtCode(java.lang.String fileExtCode) {
        this.fileExtCode = fileExtCode;
    }


    /**
     * Gets the fileRev value for this DENCancelledMatList.
     * 
     * @return fileRev
     */
    public java.lang.String getFileRev() {
        return fileRev;
    }


    /**
     * Sets the fileRev value for this DENCancelledMatList.
     * 
     * @param fileRev
     */
    public void setFileRev(java.lang.String fileRev) {
        this.fileRev = fileRev;
    }


    /**
     * Gets the seqNo value for this DENCancelledMatList.
     * 
     * @return seqNo
     */
    public java.lang.String getSeqNo() {
        return seqNo;
    }


    /**
     * Sets the seqNo value for this DENCancelledMatList.
     * 
     * @param seqNo
     */
    public void setSeqNo(java.lang.String seqNo) {
        this.seqNo = seqNo;
    }


    /**
     * Gets the matNo value for this DENCancelledMatList.
     * 
     * @return matNo
     */
    public java.lang.String getMatNo() {
        return matNo;
    }


    /**
     * Sets the matNo value for this DENCancelledMatList.
     * 
     * @param matNo
     */
    public void setMatNo(java.lang.String matNo) {
        this.matNo = matNo;
    }


    /**
     * Gets the matName value for this DENCancelledMatList.
     * 
     * @return matName
     */
    public java.lang.String getMatName() {
        return matName;
    }


    /**
     * Sets the matName value for this DENCancelledMatList.
     * 
     * @param matName
     */
    public void setMatName(java.lang.String matName) {
        this.matName = matName;
    }


    /**
     * Gets the matType value for this DENCancelledMatList.
     * 
     * @return matType
     */
    public java.lang.String getMatType() {
        return matType;
    }


    /**
     * Sets the matType value for this DENCancelledMatList.
     * 
     * @param matType
     */
    public void setMatType(java.lang.String matType) {
        this.matType = matType;
    }


    /**
     * Gets the quantity value for this DENCancelledMatList.
     * 
     * @return quantity
     */
    public java.lang.String getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this DENCancelledMatList.
     * 
     * @param quantity
     */
    public void setQuantity(java.lang.String quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the unitofValue value for this DENCancelledMatList.
     * 
     * @return unitofValue
     */
    public java.lang.String getUnitofValue() {
        return unitofValue;
    }


    /**
     * Sets the unitofValue value for this DENCancelledMatList.
     * 
     * @param unitofValue
     */
    public void setUnitofValue(java.lang.String unitofValue) {
        this.unitofValue = unitofValue;
    }

  

}
