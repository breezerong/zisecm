package com.cnpe.p6.udftypeservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2020-07-22T15:51:21.029+08:00
 * Generated source version: 3.0.3
 * 
 */
@WebService(targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", name = "UDFTypePortType")
@XmlSeeAlso({ObjectFactory.class})
public interface UDFTypePortType {

    @WebResult(name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
    @RequestWrapper(localName = "CreateUDFTypes", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.CreateUDFTypes")
    @WebMethod(operationName = "CreateUDFTypes", action = "CreateUDFTypes")
    @ResponseWrapper(localName = "CreateUDFTypesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.CreateUDFTypesResponse")
    public java.util.List<java.lang.Integer> createUDFTypes(
        @WebParam(name = "UDFType", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.util.List<com.cnpe.p6.udftypeservice.UDFType> udfType
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
    @RequestWrapper(localName = "UpdateUDFTypes", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.UpdateUDFTypes")
    @WebMethod(operationName = "UpdateUDFTypes", action = "UpdateUDFTypes")
    @ResponseWrapper(localName = "UpdateUDFTypesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.UpdateUDFTypesResponse")
    public boolean updateUDFTypes(
        @WebParam(name = "UDFType", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.util.List<com.cnpe.p6.udftypeservice.UDFType> udfType
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
    @RequestWrapper(localName = "getFieldLengthUDFType", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.GetFieldLengthUDFType")
    @WebMethod(action = "getFieldLengthUDFType")
    @ResponseWrapper(localName = "getFieldLengthUDFTypeResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.GetFieldLengthUDFTypeResponse")
    public int getFieldLengthUDFType(
        @WebParam(name = "Field", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.lang.String field
    ) throws IntegrationFault;

    @WebResult(name = "UDFFieldName", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
    @RequestWrapper(localName = "GetUDFFieldName", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.GetUDFFieldName")
    @WebMethod(operationName = "GetUDFFieldName", action = "GetUDFFieldName")
    @ResponseWrapper(localName = "GetUDFFieldNameResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.GetUDFFieldNameResponse")
    public java.lang.String getUDFFieldName(
        @WebParam(name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        int objectId
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
    @RequestWrapper(localName = "DeleteUDFTypes", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.DeleteUDFTypes")
    @WebMethod(operationName = "DeleteUDFTypes", action = "DeleteUDFTypes")
    @ResponseWrapper(localName = "DeleteUDFTypesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.DeleteUDFTypesResponse")
    public boolean deleteUDFTypes(
        @WebParam(name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.util.List<java.lang.Integer> objectId
    ) throws IntegrationFault;

    @WebResult(name = "UDFType", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
    @RequestWrapper(localName = "ReadUDFTypes", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.ReadUDFTypes")
    @WebMethod(operationName = "ReadUDFTypes", action = "ReadUDFTypes")
    @ResponseWrapper(localName = "ReadUDFTypesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1", className = "com.cnpe.p6.udftypeservice.ReadUDFTypesResponse")
    public java.util.List<com.cnpe.p6.udftypeservice.UDFType> readUDFTypes(
        @WebParam(name = "Field", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.util.List<com.cnpe.p6.udftypeservice.UDFTypeFieldType> field,
        @WebParam(name = "Filter", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.lang.String filter,
        @WebParam(name = "OrderBy", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/UDFType/V1")
        java.lang.String orderBy
    ) throws IntegrationFault;
}
