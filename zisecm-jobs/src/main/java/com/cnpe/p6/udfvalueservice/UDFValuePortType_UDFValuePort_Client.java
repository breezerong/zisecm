
package com.cnpe.p6.udfvalueservice;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2020-07-08T18:50:44.998+08:00
 * Generated source version: 3.0.3
 * 
 */
public final class UDFValuePortType_UDFValuePort_Client {

    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/UDFValue/V1", "UDFValueService");

    private UDFValuePortType_UDFValuePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = UDFValueService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        UDFValueService ss = new UDFValueService(wsdlURL, SERVICE_NAME);
        UDFValuePortType port = ss.getUDFValuePort();  
        
        {
        System.out.println("Invoking updateUDFValues...");
        java.util.List<com.cnpe.p6.udfvalueservice.UDFValue> _updateUDFValues_udfValue = null;
        try {
            boolean _updateUDFValues__return = port.updateUDFValues(_updateUDFValues_udfValue);
            System.out.println("updateUDFValues.result=" + _updateUDFValues__return);

        } catch (IntegrationFault e) { 
            System.out.println("Expected exception: IntegrationFault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking readUDFValues...");
        java.util.List<com.cnpe.p6.udfvalueservice.UDFValueFieldType> _readUDFValues_field = null;
        java.lang.String _readUDFValues_filter = "";
        java.lang.String _readUDFValues_orderBy = "";
        try {
            java.util.List<com.cnpe.p6.udfvalueservice.UDFValue> _readUDFValues__return = port.readUDFValues(_readUDFValues_field, _readUDFValues_filter, _readUDFValues_orderBy);
            System.out.println("readUDFValues.result=" + _readUDFValues__return);

        } catch (IntegrationFault e) { 
            System.out.println("Expected exception: IntegrationFault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking readCalculatedUDFValues...");
        java.util.List<com.cnpe.p6.udfvalueservice.CalculatedUDFValueFieldType> _readCalculatedUDFValues_field = null;
        java.util.List<java.lang.Integer> _readCalculatedUDFValues_udfTypeObjectId = null;
        java.util.List<java.lang.Integer> _readCalculatedUDFValues_projectObjectId = null;
        try {
            java.util.List<com.cnpe.p6.udfvalueservice.ReadCalculatedUDFValuesResponse.CalculatedUDFValue> _readCalculatedUDFValues__return = port.readCalculatedUDFValues(_readCalculatedUDFValues_field, _readCalculatedUDFValues_udfTypeObjectId, _readCalculatedUDFValues_projectObjectId);
            System.out.println("readCalculatedUDFValues.result=" + _readCalculatedUDFValues__return);

        } catch (IntegrationFault e) { 
            System.out.println("Expected exception: IntegrationFault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteUDFValues...");
        java.util.List<com.cnpe.p6.udfvalueservice.DeleteUDFValues.ObjectId> _deleteUDFValues_objectId = null;
        try {
            boolean _deleteUDFValues__return = port.deleteUDFValues(_deleteUDFValues_objectId);
            System.out.println("deleteUDFValues.result=" + _deleteUDFValues__return);

        } catch (IntegrationFault e) { 
            System.out.println("Expected exception: IntegrationFault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getFieldLengthUDFValue...");
        java.lang.String _getFieldLengthUDFValue_field = "";
        try {
            int _getFieldLengthUDFValue__return = port.getFieldLengthUDFValue(_getFieldLengthUDFValue_field);
            System.out.println("getFieldLengthUDFValue.result=" + _getFieldLengthUDFValue__return);

        } catch (IntegrationFault e) { 
            System.out.println("Expected exception: IntegrationFault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking createUDFValues...");
        java.util.List<com.cnpe.p6.udfvalueservice.UDFValue> _createUDFValues_udfValue = null;
        try {
            java.util.List<com.cnpe.p6.udfvalueservice.CreateUDFValuesResponse.ObjectId> _createUDFValues__return = port.createUDFValues(_createUDFValues_udfValue);
            System.out.println("createUDFValues.result=" + _createUDFValues__return);

        } catch (IntegrationFault e) { 
            System.out.println("Expected exception: IntegrationFault has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
