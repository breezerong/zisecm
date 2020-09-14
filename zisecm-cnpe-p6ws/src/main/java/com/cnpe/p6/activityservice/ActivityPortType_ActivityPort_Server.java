
package com.cnpe.p6.activityservice;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2020-07-08T18:51:22.052+08:00
 * Generated source version: 3.0.3
 * 
 */
 
public class ActivityPortType_ActivityPort_Server{

    protected ActivityPortType_ActivityPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new ActivityPortImpl();
        String address = "http://10.30.200.163:8206/p6ws/services/ActivityService";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new ActivityPortType_ActivityPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
