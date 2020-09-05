package com.cnpe.p6.activityservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2020-07-08T18:51:22.044+08:00
 * Generated source version: 3.0.3
 * 
 */
@WebService(targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActivityPortType")
@XmlSeeAlso({ObjectFactory.class})
public interface ActivityPortType {

    @RequestWrapper(localName = "CopyActivity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.CopyActivity")
    @WebMethod(operationName = "CopyActivity", action = "CopyActivity")
    @ResponseWrapper(localName = "CopyActivityResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.CopyActivityResponse")
    public void copyActivity(
        @WebParam(mode = WebParam.Mode.INOUT, name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        javax.xml.ws.Holder<java.lang.Integer> objectId,
        @WebParam(name = "TargetProjectObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Integer targetProjectObjectId,
        @WebParam(name = "TargetWBSObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Integer targetWBSObjectId,
        @WebParam(name = "TargetActivityId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.String targetActivityId,
        @WebParam(name = "CopyResourceAndRoleAssignments", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyResourceAndRoleAssignments,
        @WebParam(name = "CopyRelationships", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyRelationships,
        @WebParam(name = "CopyActivityCodes", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyActivityCodes,
        @WebParam(name = "CopyActivityNotes", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyActivityNotes,
        @WebParam(name = "CopyActivityExpenses", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyActivityExpenses,
        @WebParam(name = "CopyActivitySteps", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyActivitySteps,
        @WebParam(name = "CopyProjectDocuments", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyProjectDocuments,
        @WebParam(name = "CopyPastPeriodActuals", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.Boolean copyPastPeriodActuals
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "UpdateActivities", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.UpdateActivities")
    @WebMethod(operationName = "UpdateActivities", action = "UpdateActivities")
    @ResponseWrapper(localName = "UpdateActivitiesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.UpdateActivitiesResponse")
    public boolean updateActivities(
        @WebParam(name = "Activity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.util.List<com.cnpe.p6.activityservice.Activity> activity
    ) throws IntegrationFault;

    @WebResult(name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "CreateActivities", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.CreateActivities")
    @WebMethod(operationName = "CreateActivities", action = "CreateActivities")
    @ResponseWrapper(localName = "CreateActivitiesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.CreateActivitiesResponse")
    public java.util.List<java.lang.Integer> createActivities(
        @WebParam(name = "Activity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.util.List<com.cnpe.p6.activityservice.Activity> activity
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "getFieldLengthActivity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.GetFieldLengthActivity")
    @WebMethod(action = "getFieldLengthActivity")
    @ResponseWrapper(localName = "getFieldLengthActivityResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.GetFieldLengthActivityResponse")
    public int getFieldLengthActivity(
        @WebParam(name = "Field", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.String field
    ) throws IntegrationFault;

    @WebResult(name = "Activity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "ReadActivities", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.ReadActivities")
    @WebMethod(operationName = "ReadActivities", action = "ReadActivities")
    @ResponseWrapper(localName = "ReadActivitiesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.ReadActivitiesResponse")
    public java.util.List<com.cnpe.p6.activityservice.Activity> readActivities(
        @WebParam(name = "Field", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.util.List<com.cnpe.p6.activityservice.ActivityFieldType> field,
        @WebParam(name = "Filter", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.String filter,
        @WebParam(name = "OrderBy", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.String orderBy
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "DeleteActivities", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.DeleteActivities")
    @WebMethod(operationName = "DeleteActivities", action = "DeleteActivities")
    @ResponseWrapper(localName = "DeleteActivitiesResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.DeleteActivitiesResponse")
    public boolean deleteActivities(
        @WebParam(name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.util.List<java.lang.Integer> objectId
    ) throws IntegrationFault;

    @WebResult(name = "Activity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "ReadAllActivitiesByWBS", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.ReadAllActivitiesByWBS")
    @WebMethod(operationName = "ReadAllActivitiesByWBS", action = "ReadAllActivitiesByWBS")
    @ResponseWrapper(localName = "ReadAllActivitiesByWBSResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.ReadAllActivitiesByWBSResponse")
    public java.util.List<com.cnpe.p6.activityservice.Activity> readAllActivitiesByWBS(
        @WebParam(name = "WBSObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        int wbsObjectId,
        @WebParam(name = "Field", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.util.List<com.cnpe.p6.activityservice.ActivityFieldType> field,
        @WebParam(name = "Filter", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.String filter,
        @WebParam(name = "OrderBy", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        java.lang.String orderBy
    ) throws IntegrationFault;

    @WebResult(name = "Return", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
    @RequestWrapper(localName = "DissolveActivity", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.DissolveActivity")
    @WebMethod(operationName = "DissolveActivity", action = "DissolveActivity")
    @ResponseWrapper(localName = "DissolveActivityResponse", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", className = "com.cnpe.p6.activityservice.DissolveActivityResponse")
    public boolean dissolveActivity(
        @WebParam(name = "ObjectId", targetNamespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1")
        int objectId
    ) throws IntegrationFault;
}
