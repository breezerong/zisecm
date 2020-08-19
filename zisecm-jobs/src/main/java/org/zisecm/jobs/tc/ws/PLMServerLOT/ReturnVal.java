/**
 * ReturnVal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.zisecm.jobs.tc.ws.PLMServerLOT;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="ReturnVal",namespace="http://PLMServerLOT.ws.cnpe.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReturnVal",namespace="http://PLMServerLOT.ws.cnpe.com/")
public class ReturnVal  implements java.io.Serializable {
    private java.lang.String returnCode;

    private java.lang.String returnMessage;

    public ReturnVal() {
    }

    public ReturnVal(
           java.lang.String returnCode,
           java.lang.String returnMessage) {
           this.returnCode = returnCode;
           this.returnMessage = returnMessage;
    }


    /**
     * Gets the returnCode value for this ReturnVal.
     * 
     * @return returnCode
     */
    public java.lang.String getReturnCode() {
        return returnCode;
    }


    /**
     * Sets the returnCode value for this ReturnVal.
     * 
     * @param returnCode
     */
    public void setReturnCode(java.lang.String returnCode) {
        this.returnCode = returnCode;
    }


    /**
     * Gets the returnMessage value for this ReturnVal.
     * 
     * @return returnMessage
     */
    public java.lang.String getReturnMessage() {
        return returnMessage;
    }


    /**
     * Sets the returnMessage value for this ReturnVal.
     * 
     * @param returnMessage
     */
    public void setReturnMessage(java.lang.String returnMessage) {
        this.returnMessage = returnMessage;
    }

   

}
