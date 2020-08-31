
package com.cnpe.p6.udfvalueservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnpe.p6.udfvalueservice package. 
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

    private final static QName _DeleteUDFValues_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "DeleteUDFValues");
    private final static QName _DeleteUDFValuesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "DeleteUDFValuesResponse");
    private final static QName _CreateUDFValues_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "CreateUDFValues");
    private final static QName _GetFieldLengthUDFValueResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "getFieldLengthUDFValueResponse");
    private final static QName _IntegrationFault_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", "IntegrationFault");
    private final static QName _ReadUDFValuesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "ReadUDFValuesResponse");
    private final static QName _ReadCalculatedUDFValuesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "ReadCalculatedUDFValuesResponse");
    private final static QName _UpdateUDFValues_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "UpdateUDFValues");
    private final static QName _ReadUDFValues_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "ReadUDFValues");
    private final static QName _GetFieldLengthUDFValue_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "getFieldLengthUDFValue");
    private final static QName _UpdateUDFValuesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "UpdateUDFValuesResponse");
    private final static QName _CreateUDFValuesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "CreateUDFValuesResponse");
    private final static QName _ReadCalculatedUDFValues_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "ReadCalculatedUDFValues");
    private final static QName _UDFValueConditionalIndicator_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "ConditionalIndicator");
    private final static QName _UDFValueStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "StartDate");
    private final static QName _UDFValueLastUpdateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "LastUpdateDate");
    private final static QName _UDFValueIsBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "IsBaseline");
    private final static QName _UDFValueIsUDFTypeCalculated_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "IsUDFTypeCalculated");
    private final static QName _UDFValueDouble_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "Double");
    private final static QName _UDFValueCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "Cost");
    private final static QName _UDFValueCreateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "CreateDate");
    private final static QName _UDFValueInteger_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "Integer");
    private final static QName _UDFValueIsUDFTypeConditional_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "IsUDFTypeConditional");
    private final static QName _UDFValueFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "FinishDate");
    private final static QName _UDFValueProjectObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "ProjectObjectId");
    private final static QName _UDFValueUDFCodeObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "UDFCodeObjectId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnpe.p6.udfvalueservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadCalculatedUDFValuesResponse }
     * 
     */
    public ReadCalculatedUDFValuesResponse createReadCalculatedUDFValuesResponse() {
        return new ReadCalculatedUDFValuesResponse();
    }

    /**
     * Create an instance of {@link DeleteUDFValues }
     * 
     */
    public DeleteUDFValues createDeleteUDFValues() {
        return new DeleteUDFValues();
    }

    /**
     * Create an instance of {@link CreateUDFValuesResponse }
     * 
     */
    public CreateUDFValuesResponse createCreateUDFValuesResponse() {
        return new CreateUDFValuesResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthUDFValue }
     * 
     */
    public GetFieldLengthUDFValue createGetFieldLengthUDFValue() {
        return new GetFieldLengthUDFValue();
    }

    /**
     * Create an instance of {@link UpdateUDFValuesResponse }
     * 
     */
    public UpdateUDFValuesResponse createUpdateUDFValuesResponse() {
        return new UpdateUDFValuesResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthUDFValueResponse }
     * 
     */
    public GetFieldLengthUDFValueResponse createGetFieldLengthUDFValueResponse() {
        return new GetFieldLengthUDFValueResponse();
    }

    /**
     * Create an instance of {@link CreateUDFValues }
     * 
     */
    public CreateUDFValues createCreateUDFValues() {
        return new CreateUDFValues();
    }

    /**
     * Create an instance of {@link DeleteUDFValuesResponse }
     * 
     */
    public DeleteUDFValuesResponse createDeleteUDFValuesResponse() {
        return new DeleteUDFValuesResponse();
    }

    /**
     * Create an instance of {@link UpdateUDFValues }
     * 
     */
    public UpdateUDFValues createUpdateUDFValues() {
        return new UpdateUDFValues();
    }

    /**
     * Create an instance of {@link ReadUDFValues }
     * 
     */
    public ReadUDFValues createReadUDFValues() {
        return new ReadUDFValues();
    }

    /**
     * Create an instance of {@link ReadUDFValuesResponse }
     * 
     */
    public ReadUDFValuesResponse createReadUDFValuesResponse() {
        return new ReadUDFValuesResponse();
    }

    /**
     * Create an instance of {@link ReadCalculatedUDFValues }
     * 
     */
    public ReadCalculatedUDFValues createReadCalculatedUDFValues() {
        return new ReadCalculatedUDFValues();
    }

    /**
     * Create an instance of {@link UDFValue }
     * 
     */
    public UDFValue createUDFValue() {
        return new UDFValue();
    }

    /**
     * Create an instance of {@link IntegrationFaultType }
     * 
     */
    public IntegrationFaultType createIntegrationFaultType() {
        return new IntegrationFaultType();
    }

    /**
     * Create an instance of {@link ReadCalculatedUDFValuesResponse.CalculatedUDFValue }
     * 
     */
    public ReadCalculatedUDFValuesResponse.CalculatedUDFValue createReadCalculatedUDFValuesResponseCalculatedUDFValue() {
        return new ReadCalculatedUDFValuesResponse.CalculatedUDFValue();
    }

    /**
     * Create an instance of {@link DeleteUDFValues.ObjectId }
     * 
     */
    public DeleteUDFValues.ObjectId createDeleteUDFValuesObjectId() {
        return new DeleteUDFValues.ObjectId();
    }

    /**
     * Create an instance of {@link CreateUDFValuesResponse.ObjectId }
     * 
     */
    public CreateUDFValuesResponse.ObjectId createCreateUDFValuesResponseObjectId() {
        return new CreateUDFValuesResponse.ObjectId();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUDFValues }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "DeleteUDFValues")
    public JAXBElement<DeleteUDFValues> createDeleteUDFValues(DeleteUDFValues value) {
        return new JAXBElement<DeleteUDFValues>(_DeleteUDFValues_QNAME, DeleteUDFValues.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUDFValuesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "DeleteUDFValuesResponse")
    public JAXBElement<DeleteUDFValuesResponse> createDeleteUDFValuesResponse(DeleteUDFValuesResponse value) {
        return new JAXBElement<DeleteUDFValuesResponse>(_DeleteUDFValuesResponse_QNAME, DeleteUDFValuesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUDFValues }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "CreateUDFValues")
    public JAXBElement<CreateUDFValues> createCreateUDFValues(CreateUDFValues value) {
        return new JAXBElement<CreateUDFValues>(_CreateUDFValues_QNAME, CreateUDFValues.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthUDFValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "getFieldLengthUDFValueResponse")
    public JAXBElement<GetFieldLengthUDFValueResponse> createGetFieldLengthUDFValueResponse(GetFieldLengthUDFValueResponse value) {
        return new JAXBElement<GetFieldLengthUDFValueResponse>(_GetFieldLengthUDFValueResponse_QNAME, GetFieldLengthUDFValueResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadUDFValuesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ReadUDFValuesResponse")
    public JAXBElement<ReadUDFValuesResponse> createReadUDFValuesResponse(ReadUDFValuesResponse value) {
        return new JAXBElement<ReadUDFValuesResponse>(_ReadUDFValuesResponse_QNAME, ReadUDFValuesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadCalculatedUDFValuesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ReadCalculatedUDFValuesResponse")
    public JAXBElement<ReadCalculatedUDFValuesResponse> createReadCalculatedUDFValuesResponse(ReadCalculatedUDFValuesResponse value) {
        return new JAXBElement<ReadCalculatedUDFValuesResponse>(_ReadCalculatedUDFValuesResponse_QNAME, ReadCalculatedUDFValuesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUDFValues }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "UpdateUDFValues")
    public JAXBElement<UpdateUDFValues> createUpdateUDFValues(UpdateUDFValues value) {
        return new JAXBElement<UpdateUDFValues>(_UpdateUDFValues_QNAME, UpdateUDFValues.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadUDFValues }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ReadUDFValues")
    public JAXBElement<ReadUDFValues> createReadUDFValues(ReadUDFValues value) {
        return new JAXBElement<ReadUDFValues>(_ReadUDFValues_QNAME, ReadUDFValues.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthUDFValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "getFieldLengthUDFValue")
    public JAXBElement<GetFieldLengthUDFValue> createGetFieldLengthUDFValue(GetFieldLengthUDFValue value) {
        return new JAXBElement<GetFieldLengthUDFValue>(_GetFieldLengthUDFValue_QNAME, GetFieldLengthUDFValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateUDFValuesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "UpdateUDFValuesResponse")
    public JAXBElement<UpdateUDFValuesResponse> createUpdateUDFValuesResponse(UpdateUDFValuesResponse value) {
        return new JAXBElement<UpdateUDFValuesResponse>(_UpdateUDFValuesResponse_QNAME, UpdateUDFValuesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUDFValuesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "CreateUDFValuesResponse")
    public JAXBElement<CreateUDFValuesResponse> createCreateUDFValuesResponse(CreateUDFValuesResponse value) {
        return new JAXBElement<CreateUDFValuesResponse>(_CreateUDFValuesResponse_QNAME, CreateUDFValuesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadCalculatedUDFValues }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ReadCalculatedUDFValues")
    public JAXBElement<ReadCalculatedUDFValues> createReadCalculatedUDFValues(ReadCalculatedUDFValues value) {
        return new JAXBElement<ReadCalculatedUDFValues>(_ReadCalculatedUDFValues_QNAME, ReadCalculatedUDFValues.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ConditionalIndicator", scope = UDFValue.class)
    public JAXBElement<Integer> createUDFValueConditionalIndicator(Integer value) {
        return new JAXBElement<Integer>(_UDFValueConditionalIndicator_QNAME, Integer.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "StartDate", scope = UDFValue.class)
    public JAXBElement<XMLGregorianCalendar> createUDFValueStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFValueStartDate_QNAME, XMLGregorianCalendar.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "LastUpdateDate", scope = UDFValue.class)
    public JAXBElement<XMLGregorianCalendar> createUDFValueLastUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFValueLastUpdateDate_QNAME, XMLGregorianCalendar.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "IsBaseline", scope = UDFValue.class)
    public JAXBElement<Boolean> createUDFValueIsBaseline(Boolean value) {
        return new JAXBElement<Boolean>(_UDFValueIsBaseline_QNAME, Boolean.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "IsUDFTypeCalculated", scope = UDFValue.class)
    public JAXBElement<Boolean> createUDFValueIsUDFTypeCalculated(Boolean value) {
        return new JAXBElement<Boolean>(_UDFValueIsUDFTypeCalculated_QNAME, Boolean.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "Double", scope = UDFValue.class)
    public JAXBElement<Double> createUDFValueDouble(Double value) {
        return new JAXBElement<Double>(_UDFValueDouble_QNAME, Double.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "Cost", scope = UDFValue.class)
    public JAXBElement<Double> createUDFValueCost(Double value) {
        return new JAXBElement<Double>(_UDFValueCost_QNAME, Double.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "CreateDate", scope = UDFValue.class)
    public JAXBElement<XMLGregorianCalendar> createUDFValueCreateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFValueCreateDate_QNAME, XMLGregorianCalendar.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "Integer", scope = UDFValue.class)
    public JAXBElement<Integer> createUDFValueInteger(Integer value) {
        return new JAXBElement<Integer>(_UDFValueInteger_QNAME, Integer.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "IsUDFTypeConditional", scope = UDFValue.class)
    public JAXBElement<Boolean> createUDFValueIsUDFTypeConditional(Boolean value) {
        return new JAXBElement<Boolean>(_UDFValueIsUDFTypeConditional_QNAME, Boolean.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "FinishDate", scope = UDFValue.class)
    public JAXBElement<XMLGregorianCalendar> createUDFValueFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFValueFinishDate_QNAME, XMLGregorianCalendar.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ProjectObjectId", scope = UDFValue.class)
    public JAXBElement<Integer> createUDFValueProjectObjectId(Integer value) {
        return new JAXBElement<Integer>(_UDFValueProjectObjectId_QNAME, Integer.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "UDFCodeObjectId", scope = UDFValue.class)
    public JAXBElement<Integer> createUDFValueUDFCodeObjectId(Integer value) {
        return new JAXBElement<Integer>(_UDFValueUDFCodeObjectId_QNAME, Integer.class, UDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "ConditionalIndicator", scope = ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class)
    public JAXBElement<Integer> createReadCalculatedUDFValuesResponseCalculatedUDFValueConditionalIndicator(Integer value) {
        return new JAXBElement<Integer>(_UDFValueConditionalIndicator_QNAME, Integer.class, ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "StartDate", scope = ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class)
    public JAXBElement<XMLGregorianCalendar> createReadCalculatedUDFValuesResponseCalculatedUDFValueStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFValueStartDate_QNAME, XMLGregorianCalendar.class, ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "Double", scope = ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class)
    public JAXBElement<Double> createReadCalculatedUDFValuesResponseCalculatedUDFValueDouble(Double value) {
        return new JAXBElement<Double>(_UDFValueDouble_QNAME, Double.class, ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "Cost", scope = ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class)
    public JAXBElement<Double> createReadCalculatedUDFValuesResponseCalculatedUDFValueCost(Double value) {
        return new JAXBElement<Double>(_UDFValueCost_QNAME, Double.class, ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "Integer", scope = ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class)
    public JAXBElement<Integer> createReadCalculatedUDFValuesResponseCalculatedUDFValueInteger(Integer value) {
        return new JAXBElement<Integer>(_UDFValueInteger_QNAME, Integer.class, ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", name = "FinishDate", scope = ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class)
    public JAXBElement<XMLGregorianCalendar> createReadCalculatedUDFValuesResponseCalculatedUDFValueFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UDFValueFinishDate_QNAME, XMLGregorianCalendar.class, ReadCalculatedUDFValuesResponse.CalculatedUDFValue.class, value);
    }

}
