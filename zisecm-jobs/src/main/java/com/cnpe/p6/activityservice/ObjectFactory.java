
package com.cnpe.p6.activityservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnpe.p6.activityservice package. 
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

    private final static QName _GetFieldLengthActivityResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "getFieldLengthActivityResponse");
    private final static QName _CreateActivitiesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CreateActivitiesResponse");
    private final static QName _DeleteActivities_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DeleteActivities");
    private final static QName _ReadAllActivitiesByWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ReadAllActivitiesByWBS");
    private final static QName _IntegrationFault_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", "IntegrationFault");
    private final static QName _DissolveActivity_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DissolveActivity");
    private final static QName _ReadActivitiesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ReadActivitiesResponse");
    private final static QName _GetFieldLengthActivity_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "getFieldLengthActivity");
    private final static QName _DeleteActivitiesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DeleteActivitiesResponse");
    private final static QName _UpdateActivitiesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "UpdateActivitiesResponse");
    private final static QName _UpdateActivities_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "UpdateActivities");
    private final static QName _CopyActivityResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CopyActivityResponse");
    private final static QName _CreateActivities_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CreateActivities");
    private final static QName _ReadActivities_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ReadActivities");
    private final static QName _ReadAllActivitiesByWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ReadAllActivitiesByWBSResponse");
    private final static QName _DissolveActivityResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DissolveActivityResponse");
    private final static QName _CopyActivity_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CopyActivity");
    private final static QName _ActivityPlannedTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PlannedTotalCost");
    private final static QName _ActivityNonLaborCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "NonLaborCostVariance");
    private final static QName _ActivityBaselinePlannedDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedDuration");
    private final static QName _ActivityPlannedValueCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PlannedValueCost");
    private final static QName _ActivityBaseline1PlannedNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedNonLaborUnits");
    private final static QName _ActivityDurationPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DurationPercentComplete");
    private final static QName _ActivityLaborCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LaborCostPercentComplete");
    private final static QName _ActivityNonLaborCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "NonLaborCostPercentComplete");
    private final static QName _ActivityExpenseCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ExpenseCostPercentComplete");
    private final static QName _ActivityStartDate1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "StartDate1Variance");
    private final static QName _ActivityDataDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DataDate");
    private final static QName _ActivityStartDateVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "StartDateVariance");
    private final static QName _ActivityPreResponsePessimisticFinish_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PreResponsePessimisticFinish");
    private final static QName _ActivityBaseline1PlannedExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedExpenseCost");
    private final static QName _ActivityFloatPathOrder_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "FloatPathOrder");
    private final static QName _ActivityEarnedValueCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EarnedValueCost");
    private final static QName _ActivityWBSObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "WBSObjectId");
    private final static QName _ActivityIsLongestPath_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "IsLongestPath");
    private final static QName _ActivityActualThisPeriodNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualThisPeriodNonLaborUnits");
    private final static QName _ActivityPostResponsePessimisticStart_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PostResponsePessimisticStart");
    private final static QName _ActivityAtCompletionVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AtCompletionVariance");
    private final static QName _ActivityEstimateAtCompletionLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EstimateAtCompletionLaborUnits");
    private final static QName _ActivityActualThisPeriodLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualThisPeriodLaborUnits");
    private final static QName _ActivityBaseline1PlannedMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedMaterialCost");
    private final static QName _ActivityAtCompletionLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AtCompletionLaborUnitsVariance");
    private final static QName _ActivityRemainingDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingDuration");
    private final static QName _ActivityActualThisPeriodMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualThisPeriodMaterialCost");
    private final static QName _ActivityRemainingNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingNonLaborCost");
    private final static QName _ActivityReviewFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ReviewFinishDate");
    private final static QName _ActivityFinishDate1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "FinishDate1Variance");
    private final static QName _ActivityNonLaborUnits1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "NonLaborUnits1Variance");
    private final static QName _ActivityMaterialCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "MaterialCostPercentComplete");
    private final static QName _ActivityRemainingTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingTotalCost");
    private final static QName _ActivityRemainingEarlyFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingEarlyFinishDate");
    private final static QName _ActivityAtCompletionTotalUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AtCompletionTotalUnits");
    private final static QName _ActivityUnreadCommentCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "UnreadCommentCount");
    private final static QName _ActivityBaselineStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselineStartDate");
    private final static QName _ActivityUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "UnitsPercentComplete");
    private final static QName _ActivitySchedulePercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "SchedulePercentComplete");
    private final static QName _ActivityBaselinePlannedTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedTotalCost");
    private final static QName _ActivityFinishDateVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "FinishDateVariance");
    private final static QName _ActivityDurationPercentOfPlanned_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DurationPercentOfPlanned");
    private final static QName _ActivityEstimateAtCompletionCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EstimateAtCompletionCost");
    private final static QName _ActivityBaseline1Duration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1Duration");
    private final static QName _ActivityPreResponsePessimisticStart_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PreResponsePessimisticStart");
    private final static QName _ActivityTotalCost1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "TotalCost1Variance");
    private final static QName _ActivityLateStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LateStartDate");
    private final static QName _ActivityScheduleVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ScheduleVariance");
    private final static QName _ActivityActualTotalUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualTotalUnits");
    private final static QName _ActivityCBSId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CBSId");
    private final static QName _ActivityActualFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualFinishDate");
    private final static QName _ActivityNonLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "NonLaborUnitsVariance");
    private final static QName _ActivityCostPerformanceIndexLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostPerformanceIndexLaborUnits");
    private final static QName _ActivityLaborCost1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LaborCost1Variance");
    private final static QName _ActivityLaborUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LaborUnitsPercentComplete");
    private final static QName _ActivitySuspendDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "SuspendDate");
    private final static QName _ActivityScheduleVarianceLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ScheduleVarianceLaborUnits");
    private final static QName _ActivityPrimaryResourceObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PrimaryResourceObjectId");
    private final static QName _ActivityActualExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualExpenseCost");
    private final static QName _ActivityBaselinePlannedLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedLaborUnits");
    private final static QName _ActivityBaseline1PlannedLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedLaborUnits");
    private final static QName _ActivityRemainingTotalUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingTotalUnits");
    private final static QName _ActivityBaseline1StartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1StartDate");
    private final static QName _ActivityPlannedMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PlannedMaterialCost");
    private final static QName _ActivityNonLaborCost1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "NonLaborCost1Variance");
    private final static QName _ActivitySecondaryConstraintDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "SecondaryConstraintDate");
    private final static QName _ActivityAtCompletionMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AtCompletionMaterialCost");
    private final static QName _ActivityActualDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualDuration");
    private final static QName _ActivityLocationObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LocationObjectId");
    private final static QName _ActivityBaseline1FinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1FinishDate");
    private final static QName _ActivityLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LaborUnitsVariance");
    private final static QName _ActivityLaborUnits1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LaborUnits1Variance");
    private final static QName _ActivityExternalLateFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ExternalLateFinishDate");
    private final static QName _ActivityHasFutureBucketData_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "HasFutureBucketData");
    private final static QName _ActivityResumeDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ResumeDate");
    private final static QName _ActivityCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostPercentComplete");
    private final static QName _ActivityMostLikelyDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "MostLikelyDuration");
    private final static QName _ActivityBaselineFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselineFinishDate");
    private final static QName _ActivityEstimateToComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EstimateToComplete");
    private final static QName _ActivityPlannedValueLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PlannedValueLaborUnits");
    private final static QName _ActivityIsWorkPackage_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "IsWorkPackage");
    private final static QName _ActivityBaselinePlannedMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedMaterialCost");
    private final static QName _ActivityRemainingLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingLaborCost");
    private final static QName _ActivityCostPercentOfPlanned_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostPercentOfPlanned");
    private final static QName _ActivityLaborCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LaborCostVariance");
    private final static QName _ActivityMaterialCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "MaterialCostVariance");
    private final static QName _ActivityCostVarianceLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostVarianceLaborUnits");
    private final static QName _ActivityBaselinePlannedExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedExpenseCost");
    private final static QName _ActivityExternalEarlyStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ExternalEarlyStartDate");
    private final static QName _ActivityMinimumDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "MinimumDuration");
    private final static QName _ActivityDuration1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Duration1Variance");
    private final static QName _ActivityRemainingEarlyStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingEarlyStartDate");
    private final static QName _ActivityPrimaryConstraintDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PrimaryConstraintDate");
    private final static QName _ActivityNonLaborUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "NonLaborUnitsPercentComplete");
    private final static QName _ActivityEarnedValueLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EarnedValueLaborUnits");
    private final static QName _ActivityFloatPath_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "FloatPath");
    private final static QName _ActivitySchedulePerformanceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "SchedulePerformanceIndex");
    private final static QName _ActivityRemainingLateStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingLateStartDate");
    private final static QName _ActivityTotalCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "TotalCostVariance");
    private final static QName _ActivityScheduleVarianceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ScheduleVarianceIndex");
    private final static QName _ActivityActualTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualTotalCost");
    private final static QName _ActivityEarlyFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EarlyFinishDate");
    private final static QName _ActivityMaterialCost1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "MaterialCost1Variance");
    private final static QName _ActivityExpenseCost1Variance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ExpenseCost1Variance");
    private final static QName _ActivityBaseline1PlannedTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedTotalCost");
    private final static QName _ActivityCostPerformanceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostPerformanceIndex");
    private final static QName _ActivityBaselineDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselineDuration");
    private final static QName _ActivityAtCompletionExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AtCompletionExpenseCost");
    private final static QName _ActivityFreeFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "FreeFloat");
    private final static QName _ActivityEstimateToCompleteLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EstimateToCompleteLaborUnits");
    private final static QName _ActivityBaselinePlannedNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedNonLaborCost");
    private final static QName _ActivityPostResponsePessimisticFinish_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PostResponsePessimisticFinish");
    private final static QName _ActivityPerformancePercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PerformancePercentComplete");
    private final static QName _ActivityActualMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualMaterialCost");
    private final static QName _ActivityMaximumDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "MaximumDuration");
    private final static QName _ActivityRemainingExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingExpenseCost");
    private final static QName _ActivityBaseline1PlannedNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedNonLaborCost");
    private final static QName _ActivityCostVarianceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostVarianceIndex");
    private final static QName _ActivityExpectedFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ExpectedFinishDate");
    private final static QName _ActivityAccountingVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AccountingVariance");
    private final static QName _ActivityBaseline1PlannedLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedLaborCost");
    private final static QName _ActivityTotalFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "TotalFloat");
    private final static QName _ActivityCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostVariance");
    private final static QName _ActivityActualStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ActualStartDate");
    private final static QName _ActivityBaseline1PlannedDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "Baseline1PlannedDuration");
    private final static QName _ActivityExpenseCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ExpenseCostVariance");
    private final static QName _ActivityPlannedTotalUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PlannedTotalUnits");
    private final static QName _ActivityBudgetAtCompletion_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BudgetAtCompletion");
    private final static QName _ActivityEarlyStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "EarlyStartDate");
    private final static QName _ActivityCBSObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CBSObjectId");
    private final static QName _ActivitySchedulePerformanceIndexLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "SchedulePerformanceIndexLaborUnits");
    private final static QName _ActivityPlannedExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PlannedExpenseCost");
    private final static QName _ActivityCostVarianceIndexLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CostVarianceIndexLaborUnits");
    private final static QName _ActivityToCompletePerformanceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ToCompletePerformanceIndex");
    private final static QName _ActivityPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "PercentComplete");
    private final static QName _ActivityAtCompletionTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AtCompletionTotalCost");
    private final static QName _ActivityLateFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LateFinishDate");
    private final static QName _ActivityIsStarred_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "IsStarred");
    private final static QName _ActivityCreateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "CreateDate");
    private final static QName _ActivityIsCritical_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "IsCritical");
    private final static QName _ActivityRemainingLateFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingLateFinishDate");
    private final static QName _ActivityRemainingFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingFloat");
    private final static QName _ActivityScheduleVarianceIndexLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "ScheduleVarianceIndexLaborUnits");
    private final static QName _ActivityDurationVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "DurationVariance");
    private final static QName _ActivityLastUpdateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "LastUpdateDate");
    private final static QName _ActivityBaselinePlannedNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedNonLaborUnits");
    private final static QName _ActivityRemainingMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "RemainingMaterialCost");
    private final static QName _ActivityAccountingVarianceLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "AccountingVarianceLaborUnits");
    private final static QName _ActivityBaselinePlannedLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", "BaselinePlannedLaborCost");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnpe.p6.activityservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReadAllActivitiesByWBS }
     * 
     */
    public ReadAllActivitiesByWBS createReadAllActivitiesByWBS() {
        return new ReadAllActivitiesByWBS();
    }

    /**
     * Create an instance of {@link DeleteActivities }
     * 
     */
    public DeleteActivities createDeleteActivities() {
        return new DeleteActivities();
    }

    /**
     * Create an instance of {@link CreateActivitiesResponse }
     * 
     */
    public CreateActivitiesResponse createCreateActivitiesResponse() {
        return new CreateActivitiesResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthActivityResponse }
     * 
     */
    public GetFieldLengthActivityResponse createGetFieldLengthActivityResponse() {
        return new GetFieldLengthActivityResponse();
    }

    /**
     * Create an instance of {@link UpdateActivities }
     * 
     */
    public UpdateActivities createUpdateActivities() {
        return new UpdateActivities();
    }

    /**
     * Create an instance of {@link UpdateActivitiesResponse }
     * 
     */
    public UpdateActivitiesResponse createUpdateActivitiesResponse() {
        return new UpdateActivitiesResponse();
    }

    /**
     * Create an instance of {@link DeleteActivitiesResponse }
     * 
     */
    public DeleteActivitiesResponse createDeleteActivitiesResponse() {
        return new DeleteActivitiesResponse();
    }

    /**
     * Create an instance of {@link ReadActivitiesResponse }
     * 
     */
    public ReadActivitiesResponse createReadActivitiesResponse() {
        return new ReadActivitiesResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthActivity }
     * 
     */
    public GetFieldLengthActivity createGetFieldLengthActivity() {
        return new GetFieldLengthActivity();
    }

    /**
     * Create an instance of {@link DissolveActivity }
     * 
     */
    public DissolveActivity createDissolveActivity() {
        return new DissolveActivity();
    }

    /**
     * Create an instance of {@link ReadAllActivitiesByWBSResponse }
     * 
     */
    public ReadAllActivitiesByWBSResponse createReadAllActivitiesByWBSResponse() {
        return new ReadAllActivitiesByWBSResponse();
    }

    /**
     * Create an instance of {@link ReadActivities }
     * 
     */
    public ReadActivities createReadActivities() {
        return new ReadActivities();
    }

    /**
     * Create an instance of {@link CreateActivities }
     * 
     */
    public CreateActivities createCreateActivities() {
        return new CreateActivities();
    }

    /**
     * Create an instance of {@link CopyActivityResponse }
     * 
     */
    public CopyActivityResponse createCopyActivityResponse() {
        return new CopyActivityResponse();
    }

    /**
     * Create an instance of {@link CopyActivity }
     * 
     */
    public CopyActivity createCopyActivity() {
        return new CopyActivity();
    }

    /**
     * Create an instance of {@link DissolveActivityResponse }
     * 
     */
    public DissolveActivityResponse createDissolveActivityResponse() {
        return new DissolveActivityResponse();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link IntegrationFaultType }
     * 
     */
    public IntegrationFaultType createIntegrationFaultType() {
        return new IntegrationFaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "getFieldLengthActivityResponse")
    public JAXBElement<GetFieldLengthActivityResponse> createGetFieldLengthActivityResponse(GetFieldLengthActivityResponse value) {
        return new JAXBElement<GetFieldLengthActivityResponse>(_GetFieldLengthActivityResponse_QNAME, GetFieldLengthActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CreateActivitiesResponse")
    public JAXBElement<CreateActivitiesResponse> createCreateActivitiesResponse(CreateActivitiesResponse value) {
        return new JAXBElement<CreateActivitiesResponse>(_CreateActivitiesResponse_QNAME, CreateActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DeleteActivities")
    public JAXBElement<DeleteActivities> createDeleteActivities(DeleteActivities value) {
        return new JAXBElement<DeleteActivities>(_DeleteActivities_QNAME, DeleteActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllActivitiesByWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ReadAllActivitiesByWBS")
    public JAXBElement<ReadAllActivitiesByWBS> createReadAllActivitiesByWBS(ReadAllActivitiesByWBS value) {
        return new JAXBElement<ReadAllActivitiesByWBS>(_ReadAllActivitiesByWBS_QNAME, ReadAllActivitiesByWBS.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DissolveActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DissolveActivity")
    public JAXBElement<DissolveActivity> createDissolveActivity(DissolveActivity value) {
        return new JAXBElement<DissolveActivity>(_DissolveActivity_QNAME, DissolveActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ReadActivitiesResponse")
    public JAXBElement<ReadActivitiesResponse> createReadActivitiesResponse(ReadActivitiesResponse value) {
        return new JAXBElement<ReadActivitiesResponse>(_ReadActivitiesResponse_QNAME, ReadActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "getFieldLengthActivity")
    public JAXBElement<GetFieldLengthActivity> createGetFieldLengthActivity(GetFieldLengthActivity value) {
        return new JAXBElement<GetFieldLengthActivity>(_GetFieldLengthActivity_QNAME, GetFieldLengthActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DeleteActivitiesResponse")
    public JAXBElement<DeleteActivitiesResponse> createDeleteActivitiesResponse(DeleteActivitiesResponse value) {
        return new JAXBElement<DeleteActivitiesResponse>(_DeleteActivitiesResponse_QNAME, DeleteActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "UpdateActivitiesResponse")
    public JAXBElement<UpdateActivitiesResponse> createUpdateActivitiesResponse(UpdateActivitiesResponse value) {
        return new JAXBElement<UpdateActivitiesResponse>(_UpdateActivitiesResponse_QNAME, UpdateActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "UpdateActivities")
    public JAXBElement<UpdateActivities> createUpdateActivities(UpdateActivities value) {
        return new JAXBElement<UpdateActivities>(_UpdateActivities_QNAME, UpdateActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CopyActivityResponse")
    public JAXBElement<CopyActivityResponse> createCopyActivityResponse(CopyActivityResponse value) {
        return new JAXBElement<CopyActivityResponse>(_CopyActivityResponse_QNAME, CopyActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CreateActivities")
    public JAXBElement<CreateActivities> createCreateActivities(CreateActivities value) {
        return new JAXBElement<CreateActivities>(_CreateActivities_QNAME, CreateActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ReadActivities")
    public JAXBElement<ReadActivities> createReadActivities(ReadActivities value) {
        return new JAXBElement<ReadActivities>(_ReadActivities_QNAME, ReadActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllActivitiesByWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ReadAllActivitiesByWBSResponse")
    public JAXBElement<ReadAllActivitiesByWBSResponse> createReadAllActivitiesByWBSResponse(ReadAllActivitiesByWBSResponse value) {
        return new JAXBElement<ReadAllActivitiesByWBSResponse>(_ReadAllActivitiesByWBSResponse_QNAME, ReadAllActivitiesByWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DissolveActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DissolveActivityResponse")
    public JAXBElement<DissolveActivityResponse> createDissolveActivityResponse(DissolveActivityResponse value) {
        return new JAXBElement<DissolveActivityResponse>(_DissolveActivityResponse_QNAME, DissolveActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CopyActivity")
    public JAXBElement<CopyActivity> createCopyActivity(CopyActivity value) {
        return new JAXBElement<CopyActivity>(_CopyActivity_QNAME, CopyActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PlannedTotalCost", scope = Activity.class)
    public JAXBElement<Double> createActivityPlannedTotalCost(Double value) {
        return new JAXBElement<Double>(_ActivityPlannedTotalCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "NonLaborCostVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityNonLaborCostVariance(Double value) {
        return new JAXBElement<Double>(_ActivityNonLaborCostVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedDuration(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PlannedValueCost", scope = Activity.class)
    public JAXBElement<Double> createActivityPlannedValueCost(Double value) {
        return new JAXBElement<Double>(_ActivityPlannedValueCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedNonLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedNonLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DurationPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityDurationPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityDurationPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LaborCostPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityLaborCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityLaborCostPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "NonLaborCostPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityNonLaborCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityNonLaborCostPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ExpenseCostPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityExpenseCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityExpenseCostPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "StartDate1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityStartDate1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityStartDate1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DataDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityDataDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityDataDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "StartDateVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityStartDateVariance(Double value) {
        return new JAXBElement<Double>(_ActivityStartDateVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PreResponsePessimisticFinish", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityPreResponsePessimisticFinish(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityPreResponsePessimisticFinish_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedExpenseCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedExpenseCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedExpenseCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "FloatPathOrder", scope = Activity.class)
    public JAXBElement<Integer> createActivityFloatPathOrder(Integer value) {
        return new JAXBElement<Integer>(_ActivityFloatPathOrder_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EarnedValueCost", scope = Activity.class)
    public JAXBElement<Double> createActivityEarnedValueCost(Double value) {
        return new JAXBElement<Double>(_ActivityEarnedValueCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "WBSObjectId", scope = Activity.class)
    public JAXBElement<Integer> createActivityWBSObjectId(Integer value) {
        return new JAXBElement<Integer>(_ActivityWBSObjectId_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "IsLongestPath", scope = Activity.class)
    public JAXBElement<Boolean> createActivityIsLongestPath(Boolean value) {
        return new JAXBElement<Boolean>(_ActivityIsLongestPath_QNAME, Boolean.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualThisPeriodNonLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityActualThisPeriodNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityActualThisPeriodNonLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PostResponsePessimisticStart", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityPostResponsePessimisticStart(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityPostResponsePessimisticStart_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AtCompletionVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityAtCompletionVariance(Double value) {
        return new JAXBElement<Double>(_ActivityAtCompletionVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EstimateAtCompletionLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityEstimateAtCompletionLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityEstimateAtCompletionLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualThisPeriodLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityActualThisPeriodLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityActualThisPeriodLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AtCompletionLaborUnitsVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityAtCompletionLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_ActivityAtCompletionLaborUnitsVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingDuration(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualThisPeriodMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityActualThisPeriodMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityActualThisPeriodMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingNonLaborCost", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingNonLaborCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ReviewFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityReviewFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityReviewFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "FinishDate1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityFinishDate1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityFinishDate1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "NonLaborUnits1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityNonLaborUnits1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityNonLaborUnits1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "MaterialCostPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityMaterialCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityMaterialCostPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingTotalCost", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingTotalCost(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingTotalCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingEarlyFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityRemainingEarlyFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityRemainingEarlyFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AtCompletionTotalUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityAtCompletionTotalUnits(Double value) {
        return new JAXBElement<Double>(_ActivityAtCompletionTotalUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "UnreadCommentCount", scope = Activity.class)
    public JAXBElement<Integer> createActivityUnreadCommentCount(Integer value) {
        return new JAXBElement<Integer>(_ActivityUnreadCommentCount_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselineStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityBaselineStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityBaselineStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "UnitsPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityUnitsPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "SchedulePercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivitySchedulePercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivitySchedulePercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedTotalCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedTotalCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedTotalCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "FinishDateVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityFinishDateVariance(Double value) {
        return new JAXBElement<Double>(_ActivityFinishDateVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DurationPercentOfPlanned", scope = Activity.class)
    public JAXBElement<Double> createActivityDurationPercentOfPlanned(Double value) {
        return new JAXBElement<Double>(_ActivityDurationPercentOfPlanned_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EstimateAtCompletionCost", scope = Activity.class)
    public JAXBElement<Double> createActivityEstimateAtCompletionCost(Double value) {
        return new JAXBElement<Double>(_ActivityEstimateAtCompletionCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1Duration", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1Duration(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1Duration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PreResponsePessimisticStart", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityPreResponsePessimisticStart(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityPreResponsePessimisticStart_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "TotalCost1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityTotalCost1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityTotalCost1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LateStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityLateStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityLateStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ScheduleVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityScheduleVariance(Double value) {
        return new JAXBElement<Double>(_ActivityScheduleVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualTotalUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityActualTotalUnits(Double value) {
        return new JAXBElement<Double>(_ActivityActualTotalUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CBSId", scope = Activity.class)
    public JAXBElement<Integer> createActivityCBSId(Integer value) {
        return new JAXBElement<Integer>(_ActivityCBSId_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityActualFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityActualFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "NonLaborUnitsVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityNonLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_ActivityNonLaborUnitsVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostPerformanceIndexLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityCostPerformanceIndexLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityCostPerformanceIndexLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LaborCost1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityLaborCost1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityLaborCost1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LaborUnitsPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityLaborUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityLaborUnitsPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "SuspendDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivitySuspendDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivitySuspendDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ScheduleVarianceLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityScheduleVarianceLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityScheduleVarianceLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PrimaryResourceObjectId", scope = Activity.class)
    public JAXBElement<Integer> createActivityPrimaryResourceObjectId(Integer value) {
        return new JAXBElement<Integer>(_ActivityPrimaryResourceObjectId_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualExpenseCost", scope = Activity.class)
    public JAXBElement<Double> createActivityActualExpenseCost(Double value) {
        return new JAXBElement<Double>(_ActivityActualExpenseCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingTotalUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingTotalUnits(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingTotalUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1StartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityBaseline1StartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityBaseline1StartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PlannedMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityPlannedMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityPlannedMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "NonLaborCost1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityNonLaborCost1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityNonLaborCost1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "SecondaryConstraintDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivitySecondaryConstraintDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivitySecondaryConstraintDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AtCompletionMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityAtCompletionMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityAtCompletionMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityActualDuration(Double value) {
        return new JAXBElement<Double>(_ActivityActualDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LocationObjectId", scope = Activity.class)
    public JAXBElement<Integer> createActivityLocationObjectId(Integer value) {
        return new JAXBElement<Integer>(_ActivityLocationObjectId_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1FinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityBaseline1FinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityBaseline1FinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LaborUnitsVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_ActivityLaborUnitsVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LaborUnits1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityLaborUnits1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityLaborUnits1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ExternalLateFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityExternalLateFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityExternalLateFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "HasFutureBucketData", scope = Activity.class)
    public JAXBElement<Boolean> createActivityHasFutureBucketData(Boolean value) {
        return new JAXBElement<Boolean>(_ActivityHasFutureBucketData_QNAME, Boolean.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ResumeDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityResumeDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityResumeDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityCostPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "MostLikelyDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityMostLikelyDuration(Double value) {
        return new JAXBElement<Double>(_ActivityMostLikelyDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselineFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityBaselineFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityBaselineFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EstimateToComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityEstimateToComplete(Double value) {
        return new JAXBElement<Double>(_ActivityEstimateToComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PlannedValueLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityPlannedValueLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityPlannedValueLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "IsWorkPackage", scope = Activity.class)
    public JAXBElement<Boolean> createActivityIsWorkPackage(Boolean value) {
        return new JAXBElement<Boolean>(_ActivityIsWorkPackage_QNAME, Boolean.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingLaborCost", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingLaborCost(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingLaborCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostPercentOfPlanned", scope = Activity.class)
    public JAXBElement<Double> createActivityCostPercentOfPlanned(Double value) {
        return new JAXBElement<Double>(_ActivityCostPercentOfPlanned_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LaborCostVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityLaborCostVariance(Double value) {
        return new JAXBElement<Double>(_ActivityLaborCostVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "MaterialCostVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityMaterialCostVariance(Double value) {
        return new JAXBElement<Double>(_ActivityMaterialCostVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostVarianceLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityCostVarianceLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityCostVarianceLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedExpenseCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedExpenseCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedExpenseCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ExternalEarlyStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityExternalEarlyStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityExternalEarlyStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "MinimumDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityMinimumDuration(Double value) {
        return new JAXBElement<Double>(_ActivityMinimumDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Duration1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityDuration1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityDuration1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingEarlyStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityRemainingEarlyStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityRemainingEarlyStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PrimaryConstraintDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityPrimaryConstraintDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityPrimaryConstraintDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "NonLaborUnitsPercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityNonLaborUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityNonLaborUnitsPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EarnedValueLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityEarnedValueLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityEarnedValueLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "FloatPath", scope = Activity.class)
    public JAXBElement<Integer> createActivityFloatPath(Integer value) {
        return new JAXBElement<Integer>(_ActivityFloatPath_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "SchedulePerformanceIndex", scope = Activity.class)
    public JAXBElement<Double> createActivitySchedulePerformanceIndex(Double value) {
        return new JAXBElement<Double>(_ActivitySchedulePerformanceIndex_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingLateStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityRemainingLateStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityRemainingLateStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "TotalCostVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityTotalCostVariance(Double value) {
        return new JAXBElement<Double>(_ActivityTotalCostVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ScheduleVarianceIndex", scope = Activity.class)
    public JAXBElement<Double> createActivityScheduleVarianceIndex(Double value) {
        return new JAXBElement<Double>(_ActivityScheduleVarianceIndex_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualTotalCost", scope = Activity.class)
    public JAXBElement<Double> createActivityActualTotalCost(Double value) {
        return new JAXBElement<Double>(_ActivityActualTotalCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EarlyFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityEarlyFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityEarlyFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "MaterialCost1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityMaterialCost1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityMaterialCost1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ExpenseCost1Variance", scope = Activity.class)
    public JAXBElement<Double> createActivityExpenseCost1Variance(Double value) {
        return new JAXBElement<Double>(_ActivityExpenseCost1Variance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedTotalCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedTotalCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedTotalCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostPerformanceIndex", scope = Activity.class)
    public JAXBElement<Double> createActivityCostPerformanceIndex(Double value) {
        return new JAXBElement<Double>(_ActivityCostPerformanceIndex_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselineDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselineDuration(Double value) {
        return new JAXBElement<Double>(_ActivityBaselineDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AtCompletionExpenseCost", scope = Activity.class)
    public JAXBElement<Double> createActivityAtCompletionExpenseCost(Double value) {
        return new JAXBElement<Double>(_ActivityAtCompletionExpenseCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "FreeFloat", scope = Activity.class)
    public JAXBElement<Double> createActivityFreeFloat(Double value) {
        return new JAXBElement<Double>(_ActivityFreeFloat_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EstimateToCompleteLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityEstimateToCompleteLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityEstimateToCompleteLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedNonLaborCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedNonLaborCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PostResponsePessimisticFinish", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityPostResponsePessimisticFinish(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityPostResponsePessimisticFinish_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PerformancePercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityPerformancePercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityPerformancePercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityActualMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityActualMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "MaximumDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityMaximumDuration(Double value) {
        return new JAXBElement<Double>(_ActivityMaximumDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingExpenseCost", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingExpenseCost(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingExpenseCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedNonLaborCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedNonLaborCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostVarianceIndex", scope = Activity.class)
    public JAXBElement<Double> createActivityCostVarianceIndex(Double value) {
        return new JAXBElement<Double>(_ActivityCostVarianceIndex_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ExpectedFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityExpectedFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityExpectedFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AccountingVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityAccountingVariance(Double value) {
        return new JAXBElement<Double>(_ActivityAccountingVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedLaborCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedLaborCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedLaborCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "TotalFloat", scope = Activity.class)
    public JAXBElement<Double> createActivityTotalFloat(Double value) {
        return new JAXBElement<Double>(_ActivityTotalFloat_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityCostVariance(Double value) {
        return new JAXBElement<Double>(_ActivityCostVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ActualStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityActualStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityActualStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "Baseline1PlannedDuration", scope = Activity.class)
    public JAXBElement<Double> createActivityBaseline1PlannedDuration(Double value) {
        return new JAXBElement<Double>(_ActivityBaseline1PlannedDuration_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ExpenseCostVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityExpenseCostVariance(Double value) {
        return new JAXBElement<Double>(_ActivityExpenseCostVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PlannedTotalUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityPlannedTotalUnits(Double value) {
        return new JAXBElement<Double>(_ActivityPlannedTotalUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BudgetAtCompletion", scope = Activity.class)
    public JAXBElement<Double> createActivityBudgetAtCompletion(Double value) {
        return new JAXBElement<Double>(_ActivityBudgetAtCompletion_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "EarlyStartDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityEarlyStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityEarlyStartDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CBSObjectId", scope = Activity.class)
    public JAXBElement<Integer> createActivityCBSObjectId(Integer value) {
        return new JAXBElement<Integer>(_ActivityCBSObjectId_QNAME, Integer.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "SchedulePerformanceIndexLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivitySchedulePerformanceIndexLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivitySchedulePerformanceIndexLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PlannedExpenseCost", scope = Activity.class)
    public JAXBElement<Double> createActivityPlannedExpenseCost(Double value) {
        return new JAXBElement<Double>(_ActivityPlannedExpenseCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CostVarianceIndexLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityCostVarianceIndexLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityCostVarianceIndexLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ToCompletePerformanceIndex", scope = Activity.class)
    public JAXBElement<Double> createActivityToCompletePerformanceIndex(Double value) {
        return new JAXBElement<Double>(_ActivityToCompletePerformanceIndex_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "PercentComplete", scope = Activity.class)
    public JAXBElement<Double> createActivityPercentComplete(Double value) {
        return new JAXBElement<Double>(_ActivityPercentComplete_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AtCompletionTotalCost", scope = Activity.class)
    public JAXBElement<Double> createActivityAtCompletionTotalCost(Double value) {
        return new JAXBElement<Double>(_ActivityAtCompletionTotalCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LateFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityLateFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityLateFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "IsStarred", scope = Activity.class)
    public JAXBElement<Boolean> createActivityIsStarred(Boolean value) {
        return new JAXBElement<Boolean>(_ActivityIsStarred_QNAME, Boolean.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "CreateDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityCreateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityCreateDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "IsCritical", scope = Activity.class)
    public JAXBElement<Boolean> createActivityIsCritical(Boolean value) {
        return new JAXBElement<Boolean>(_ActivityIsCritical_QNAME, Boolean.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingLateFinishDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityRemainingLateFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityRemainingLateFinishDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingFloat", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingFloat(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingFloat_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "ScheduleVarianceIndexLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityScheduleVarianceIndexLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityScheduleVarianceIndexLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "DurationVariance", scope = Activity.class)
    public JAXBElement<Double> createActivityDurationVariance(Double value) {
        return new JAXBElement<Double>(_ActivityDurationVariance_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "LastUpdateDate", scope = Activity.class)
    public JAXBElement<XMLGregorianCalendar> createActivityLastUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivityLastUpdateDate_QNAME, XMLGregorianCalendar.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedNonLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedNonLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "RemainingMaterialCost", scope = Activity.class)
    public JAXBElement<Double> createActivityRemainingMaterialCost(Double value) {
        return new JAXBElement<Double>(_ActivityRemainingMaterialCost_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "AccountingVarianceLaborUnits", scope = Activity.class)
    public JAXBElement<Double> createActivityAccountingVarianceLaborUnits(Double value) {
        return new JAXBElement<Double>(_ActivityAccountingVarianceLaborUnits_QNAME, Double.class, Activity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", name = "BaselinePlannedLaborCost", scope = Activity.class)
    public JAXBElement<Double> createActivityBaselinePlannedLaborCost(Double value) {
        return new JAXBElement<Double>(_ActivityBaselinePlannedLaborCost_QNAME, Double.class, Activity.class, value);
    }

}
