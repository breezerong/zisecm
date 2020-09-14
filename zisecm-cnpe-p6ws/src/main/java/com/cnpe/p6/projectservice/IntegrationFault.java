
package com.cnpe.p6.projectservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.0.3
 * 2020-07-08T18:51:03.408+08:00
 * Generated source version: 3.0.3
 */

@WebFault(name = "IntegrationFault", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1")
public class IntegrationFault extends Exception {
    
    private com.cnpe.p6.projectservice.IntegrationFaultType integrationFault;

    public IntegrationFault() {
        super();
    }
    
    public IntegrationFault(String message) {
        super(message);
    }
    
    public IntegrationFault(String message, Throwable cause) {
        super(message, cause);
    }

    public IntegrationFault(String message, com.cnpe.p6.projectservice.IntegrationFaultType integrationFault) {
        super(message);
        this.integrationFault = integrationFault;
    }

    public IntegrationFault(String message, com.cnpe.p6.projectservice.IntegrationFaultType integrationFault, Throwable cause) {
        super(message, cause);
        this.integrationFault = integrationFault;
    }

    public com.cnpe.p6.projectservice.IntegrationFaultType getFaultInfo() {
        return this.integrationFault;
    }
}
