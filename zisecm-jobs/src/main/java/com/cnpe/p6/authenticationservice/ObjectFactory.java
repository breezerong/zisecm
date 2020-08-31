
package com.cnpe.p6.authenticationservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnpe.p6.authenticationservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Logout_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1", "Logout");
    private final static QName _ReadSessionProperties_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1", "ReadSessionProperties");
    private final static QName _IntegrationFault_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", "IntegrationFault");
    private final static QName _ReadDatabaseInstances_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1", "ReadDatabaseInstances");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnpe.p6.authenticationservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadDatabaseInstancesResponse }
     * 
     */
    public ReadDatabaseInstancesResponse createReadDatabaseInstancesResponse() {
        return new ReadDatabaseInstancesResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link UpdateSessionProperties }
     * 
     */
    public UpdateSessionProperties createUpdateSessionProperties() {
        return new UpdateSessionProperties();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link ReadDatabaseInstancesResponse.DatabaseInstance }
     * 
     */
    public ReadDatabaseInstancesResponse.DatabaseInstance createReadDatabaseInstancesResponseDatabaseInstance() {
        return new ReadDatabaseInstancesResponse.DatabaseInstance();
    }

    /**
     * Create an instance of {@link ReadSessionPropertiesResponse }
     * 
     */
    public ReadSessionPropertiesResponse createReadSessionPropertiesResponse() {
        return new ReadSessionPropertiesResponse();
    }

    /**
     * Create an instance of {@link UpdateSessionPropertiesResponse }
     * 
     */
    public UpdateSessionPropertiesResponse createUpdateSessionPropertiesResponse() {
        return new UpdateSessionPropertiesResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link IntegrationFaultType }
     * 
     */
    public IntegrationFaultType createIntegrationFaultType() {
        return new IntegrationFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1", name = "Logout")
    public JAXBElement<Object> createLogout(Object value) {
        return new JAXBElement<Object>(_Logout_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1", name = "ReadSessionProperties")
    public JAXBElement<Object> createReadSessionProperties(Object value) {
        return new JAXBElement<Object>(_ReadSessionProperties_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntegrationFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", name = "IntegrationFault")
    public JAXBElement<IntegrationFaultType> createIntegrationFault(IntegrationFaultType value) {
        return new JAXBElement<IntegrationFaultType>(_IntegrationFault_QNAME, IntegrationFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Authentication/V1", name = "ReadDatabaseInstances")
    public JAXBElement<Object> createReadDatabaseInstances(Object value) {
        return new JAXBElement<Object>(_ReadDatabaseInstances_QNAME, Object.class, null, value);
    }

}
