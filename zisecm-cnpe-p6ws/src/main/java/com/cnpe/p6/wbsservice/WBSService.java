package com.cnpe.p6.wbsservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2020-07-08T18:51:12.311+08:00
 * Generated source version: 3.0.3
 * 
 */
@WebServiceClient(name = "WBSService", 
                  wsdlLocation = "http://10.30.200.163:8206/p6ws/services/WBSService?wsdl",
                  targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2") 
public class WBSService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "WBSService");
    public final static QName WBSPort = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "WBSPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.30.200.163:8206/p6ws/services/WBSService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(WBSService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://10.30.200.163:8206/p6ws/services/WBSService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public WBSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WBSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WBSService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns WBSPortType
     */
    @WebEndpoint(name = "WBSPort")
    public WBSPortType getWBSPort() {
        return super.getPort(WBSPort, WBSPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WBSPortType
     */
    @WebEndpoint(name = "WBSPort")
    public WBSPortType getWBSPort(WebServiceFeature... features) {
        return super.getPort(WBSPort, WBSPortType.class, features);
    }

}
