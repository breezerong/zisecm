
package com.cnpe.p6.udftypeservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnpe.p6.udftypeservice package. 
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

    private final static QName _ReadUDFTypesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "ReadUDFTypesResponse");
    private final static QName _CreateUDFTypesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "CreateUDFTypesResponse");
    private final static QName _IntegrationFault_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", "IntegrationFault");
    private final static QName _GetFieldLengthUDFTypeResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "getFieldLengthUDFTypeResponse");
    private final static QName _CreateUDFTypes_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "CreateUDFTypes");
    private final static QName _GetFieldLengthUDFType_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "getFieldLengthUDFType");
    private final static QName _DeleteUDFTypesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "DeleteUDFTypesResponse");
    private final static QName _UpdateUDFTypesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "UpdateUDFTypesResponse");
    private final static QName _UpdateUDFTypes_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "UpdateUDFTypes");
    private final static QName _ReadUDFTypes_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "ReadUDFTypes");
    private final static QName _DeleteUDFTypes_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "DeleteUDFTypes");
    private final static QName _GetUDFFieldName_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "GetUDFFieldName");
    private final static QName _GetUDFFieldNameResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "GetUDFFieldNameResponse");
    private final static QName _UDFTypeLastUpdateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "LastUpdateDate");
    private final static QName _UDFTypeIsCalculated_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "IsCalculated");
    private final static QName _UDFTypeCreateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "CreateDate");
    private final static QName _UDFTypeIsConditional_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", "IsConditional");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnpe.p6.udftypeservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadUDFTypes }
     * 
     */
    public ReadUDFTypes createReadUDFTypes() {
        return new ReadUDFTypes();
    }

    /**
     * Create an instance of {@link ReadUDFTypesResponse }
     * 
     */
    public ReadUDFTypesResponse createReadUDFTypesResponse() {
        return new ReadUDFTypesResponse();
    }

    /**
     * Create an instance of {@link CreateUDFTypesResponse }
     * 
     */
    public CreateUDFTypesResponse createCreateUDFTypesResponse() {
        return new CreateUDFTypesResponse();
    }

    /**
     * Create an instance of {@link UpdateUDFTypes }
     * 
     */
    public UpdateUDFTypes createUpdateUDFTypes() {
        return new UpdateUDFTypes();
    }

    /**
     * Create an instance of {@link GetUDFFieldName }
     * 
     */
    public GetUDFFieldName createGetUDFFieldName() {
        return new GetUDFFieldName();
    }

    /**
     * Create an instance of {@link DeleteUDFTypesResponse }
     * 
     */
    public DeleteUDFTypesResponse createDeleteUDFTypesResponse() {
        return new DeleteUDFTypesResponse();
    }

    /**
     * Create an instance of {@link GetUDFFieldNameResponse }
     * 
     */
    public GetUDFFieldNameResponse createGetUDFFieldNameResponse() {
        return new GetUDFFieldNameResponse();
    }

    /**
     * Create an instance of {@link UpdateUDFTypesResponse }
     * 
     */
    public UpdateUDFTypesResponse createUpdateUDFTypesResponse() {
        return new UpdateUDFTypesResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthUDFTypeResponse }
     * 
     */
    public GetFieldLengthUDFTypeResponse createGetFieldLengthUDFTypeResponse() {
        return new GetFieldLengthUDFTypeResponse();
    }

    /**
     * Create an instance of {@link CreateUDFTypes }
     * 
     */
    public CreateUDFTypes createCreateUDFTypes() {
        return new CreateUDFTypes();
    }

    /**
     * Create an instance of {@link DeleteUDFTypes }
     * 
     */
    public DeleteUDFTypes createDeleteUDFTypes() {
        return new DeleteUDFTypes();
    }

    /**
     * Create an instance of {@link GetFieldLengthUDFType }
     * 
     */
    public GetFieldLengthUDFType createGetFieldLengthUDFType() {
        return new GetFieldLengthUDFType();
    }

    /**
     * Create an instance of {@link UDFType }
     * 
     */
    public UDFType createUDFType() {
        return new UDFType();
    }

    /**
     * Create an instance of {@link IntegrationFaultType }
     * 
     */
    public IntegrationFaultType createIntegrationFaultType() {
        return new IntegrationFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadUDFTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "ReadUDFTypesResponse")
    public JAXBElement<ReadUDFTypesResponse> createReadUDFTypesResponse(ReadUDFTypesResponse value) {
        return new JAXBElement<ReadUDFTypesResponse>(_ReadUDFTypesResponse_QNAME, ReadUDFTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUDFTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "CreateUDFTypesResponse")
    public JAXBElement<CreateUDFTypesResponse> createCreateUDFTypesResponse(CreateUDFTypesResponse value) {
        return new JAXBElement<CreateUDFTypesResponse>(_CreateUDFTypesResponse_QNAME, CreateUDFTypesResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthUDFTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "getFieldLengthUDFTypeResponse")
    public JAXBElement<GetFieldLengthUDFTypeResponse> createGetFieldLengthUDFTypeResponse(GetFieldLengthUDFTypeResponse value) {
        return new JAXBElement<GetFieldLengthUDFTypeResponse>(_GetFieldLengthUDFTypeResponse_QNAME, GetFieldLengthUDFTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUDFTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "CreateUDFTypes")
    public JAXBElement<CreateUDFTypes> createCreateUDFTypes(CreateUDFTypes value) {
        return new JAXBElement<CreateUDFTypes>(_CreateUDFTypes_QNAME, CreateUDFTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthUDFType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "getFieldLengthUDFType")
    public JAXBElement<GetFieldLengthUDFType> createGetFieldLengthUDFType(GetFieldLengthUDFType value) {
        return new JAXBElement<GetFieldLengthUDFType>(_GetFieldLengthUDFType_QNAME, GetFieldLengthUDFType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUDFTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "DeleteUDFTypesResponse")
    public JAXBElement<DeleteUDFTypesResponse> createDeleteUDFTypesResponse(DeleteUDFTypesResponse value) {
        return new JAXBElement<DeleteUDFTypesResponse>(_DeleteUDFTypesResponse_QNAME, DeleteUDFTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUDFTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "UpdateUDFTypesResponse")
    public JAXBElement<UpdateUDFTypesResponse> createUpdateUDFTypesResponse(UpdateUDFTypesResponse value) {
        return new JAXBElement<UpdateUDFTypesResponse>(_UpdateUDFTypesResponse_QNAME, UpdateUDFTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUDFTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "UpdateUDFTypes")
    public JAXBElement<UpdateUDFTypes> createUpdateUDFTypes(UpdateUDFTypes value) {
        return new JAXBElement<UpdateUDFTypes>(_UpdateUDFTypes_QNAME, UpdateUDFTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadUDFTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "ReadUDFTypes")
    public JAXBElement<ReadUDFTypes> createReadUDFTypes(ReadUDFTypes value) {
        return new JAXBElement<ReadUDFTypes>(_ReadUDFTypes_QNAME, ReadUDFTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUDFTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "DeleteUDFTypes")
    public JAXBElement<DeleteUDFTypes> createDeleteUDFTypes(DeleteUDFTypes value) {
        return new JAXBElement<DeleteUDFTypes>(_DeleteUDFTypes_QNAME, DeleteUDFTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUDFFieldName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "GetUDFFieldName")
    public JAXBElement<GetUDFFieldName> createGetUDFFieldName(GetUDFFieldName value) {
        return new JAXBElement<GetUDFFieldName>(_GetUDFFieldName_QNAME, GetUDFFieldName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUDFFieldNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "GetUDFFieldNameResponse")
    public JAXBElement<GetUDFFieldNameResponse> createGetUDFFieldNameResponse(GetUDFFieldNameResponse value) {
        return new JAXBElement<GetUDFFieldNameResponse>(_GetUDFFieldNameResponse_QNAME, GetUDFFieldNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "LastUpdateDate", scope = UDFType.class)
    public JAXBElement<XMLGregorianCalendar> createUDFTypeLastUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFTypeLastUpdateDate_QNAME, XMLGregorianCalendar.class, UDFType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "IsCalculated", scope = UDFType.class)
    public JAXBElement<Boolean> createUDFTypeIsCalculated(Boolean value) {
        return new JAXBElement<Boolean>(_UDFTypeIsCalculated_QNAME, Boolean.class, UDFType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "CreateDate", scope = UDFType.class)
    public JAXBElement<XMLGregorianCalendar> createUDFTypeCreateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFTypeCreateDate_QNAME, XMLGregorianCalendar.class, UDFType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "IsConditional", scope = UDFType.class)
    public JAXBElement<Boolean> createUDFTypeIsConditional(Boolean value) {
        return new JAXBElement<Boolean>(_UDFTypeIsConditional_QNAME, Boolean.class, UDFType.class, value);
    }

}
