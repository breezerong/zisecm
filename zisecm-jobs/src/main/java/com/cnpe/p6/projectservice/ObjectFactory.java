
package com.cnpe.p6.projectservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnpe.p6.projectservice package. 
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

    private final static QName _UpdateProjectsResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UpdateProjectsResponse");
    private final static QName _UpdateProjectPreferencesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UpdateProjectPreferencesResponse");
    private final static QName _CopyBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyBaselineResponse");
    private final static QName _LoadAllResourcesResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadAllResourcesResponse");
    private final static QName _CopyProjectAsBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyProjectAsBaseline");
    private final static QName _UpdateProjectPreferences_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UpdateProjectPreferences");
    private final static QName _CopyBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyBaseline");
    private final static QName _LoadAllResources_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadAllResources");
    private final static QName _CalculateProjectScore_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CalculateProjectScore");
    private final static QName _ConvertProjectToBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ConvertProjectToBaselineResponse");
    private final static QName _IntegrationFault_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", "IntegrationFault");
    private final static QName _PublishProject_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "PublishProject");
    private final static QName _IsProjectLocked_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "IsProjectLocked");
    private final static QName _ReadProjectsResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ReadProjectsResponse");
    private final static QName _LoadActivitiesNewerThanBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadActivitiesNewerThanBaselineResponse");
    private final static QName _GetFieldLengthProjectResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "getFieldLengthProjectResponse");
    private final static QName _CopyProjectResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyProjectResponse");
    private final static QName _CreateProjectFromTemplateResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateProjectFromTemplateResponse");
    private final static QName _CopyWBSFromTemplate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyWBSFromTemplate");
    private final static QName _CopyProjectAsReflectionResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyProjectAsReflectionResponse");
    private final static QName _LoadActivityUDFValuesNewerThanBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadActivityUDFValuesNewerThanBaselineResponse");
    private final static QName _ReadProjects_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ReadProjects");
    private final static QName _CopyWBSFromTemplateResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyWBSFromTemplateResponse");
    private final static QName _CopyProject_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyProject");
    private final static QName _LoadActivitiesNewerThanBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadActivitiesNewerThanBaseline");
    private final static QName _PublishProjectResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "PublishProjectResponse");
    private final static QName _AssignProjectAsBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "AssignProjectAsBaseline");
    private final static QName _AssignProjectAsBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "AssignProjectAsBaselineResponse");
    private final static QName _LoadActivityUDFValuesNewerThanBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadActivityUDFValuesNewerThanBaseline");
    private final static QName _DeleteProjectsResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "DeleteProjectsResponse");
    private final static QName _CopyProjectAsReflection_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyProjectAsReflection");
    private final static QName _CopyProjectAsBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CopyProjectAsBaselineResponse");
    private final static QName _CalculateProjectScoreResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CalculateProjectScoreResponse");
    private final static QName _CreateProjectsResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateProjectsResponse");
    private final static QName _CreateCopyAsTemplate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateCopyAsTemplate");
    private final static QName _CreateProjects_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateProjects");
    private final static QName _IsProjectLockedResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "IsProjectLockedResponse");
    private final static QName _ConvertProjectToBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ConvertProjectToBaseline");
    private final static QName _CreateCopyAsTemplateResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateCopyAsTemplateResponse");
    private final static QName _LoadActivityCodesNewerThanBaseline_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadActivityCodesNewerThanBaseline");
    private final static QName _CreateProjectFromTemplate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateProjectFromTemplate");
    private final static QName _DeleteProjects_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "DeleteProjects");
    private final static QName _LoadActivityCodesNewerThanBaselineResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LoadActivityCodesNewerThanBaselineResponse");
    private final static QName _UpdateProjects_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UpdateProjects");
    private final static QName _GetFieldLengthProject_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "getFieldLengthProject");
    private final static QName _ProjectSummaryCostVarianceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostVarianceIndexByLaborUnits");
    private final static QName _ProjectEarnedValueETCUserValue_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "EarnedValueETCUserValue");
    private final static QName _ProjectSummaryActualFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualFinishDate");
    private final static QName _ProjectSummaryActualNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualNonLaborCost");
    private final static QName _ProjectSummaryNotStartedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryNotStartedActivityCount");
    private final static QName _ProjectIndependentETCLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "IndependentETCLaborUnits");
    private final static QName _ProjectMultipleFloatPathsEndingActivityObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "MultipleFloatPathsEndingActivityObjectId");
    private final static QName _ProjectSummaryActualThisPeriodMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualThisPeriodMaterialCost");
    private final static QName _ProjectSummaryAtCompletionTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionTotalCost");
    private final static QName _ProjectSummarySchedulePercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummarySchedulePercentComplete");
    private final static QName _ProjectSummaryActualThisPeriodNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualThisPeriodNonLaborCost");
    private final static QName _ProjectIndependentETCTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "IndependentETCTotalCost");
    private final static QName _ProjectSummaryBaselineLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineLaborUnits");
    private final static QName _ProjectUnifierDeleteActivitiesFlag_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UnifierDeleteActivitiesFlag");
    private final static QName _ProjectSummarySchedulePerformanceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummarySchedulePerformanceIndexByCost");
    private final static QName _ProjectSummaryNonLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryNonLaborUnitsVariance");
    private final static QName _ProjectWBSHierarchyLevels_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "WBSHierarchyLevels");
    private final static QName _ProjectLevelWithinFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelWithinFloat");
    private final static QName _ProjectReturnOnInvestment_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ReturnOnInvestment");
    private final static QName _ProjectSummaryActualValueByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualValueByLaborUnits");
    private final static QName _ProjectAnticipatedStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "AnticipatedStartDate");
    private final static QName _ProjectSummaryBaselineLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineLaborCost");
    private final static QName _ProjectRiskScore_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "RiskScore");
    private final static QName _ProjectSummaryPlannedLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedLaborCost");
    private final static QName _ProjectLastFinancialPeriodObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastFinancialPeriodObjectId");
    private final static QName _ProjectUndistributedCurrentVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UndistributedCurrentVariance");
    private final static QName _ProjectSummaryMaterialCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryMaterialCostVariance");
    private final static QName _ProjectLatitude_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "Latitude");
    private final static QName _ProjectSummaryProgressFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryProgressFinishDate");
    private final static QName _ProjectSummaryInProgressActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryInProgressActivityCount");
    private final static QName _ProjectStatusReviewerObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "StatusReviewerObjectId");
    private final static QName _ProjectDataDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "DataDate");
    private final static QName _ProjectPaybackPeriod_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "PaybackPeriod");
    private final static QName _ProjectUnifierEnabledFlag_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UnifierEnabledFlag");
    private final static QName _ProjectSummaryUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryUnitsPercentComplete");
    private final static QName _ProjectSummaryActualTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualTotalCost");
    private final static QName _ProjectAnticipatedFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "AnticipatedFinishDate");
    private final static QName _ProjectSummaryEarnedValueByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEarnedValueByCost");
    private final static QName _ProjectSummaryAtCompletionMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionMaterialCost");
    private final static QName _ProjectSummaryAtCompletionTotalCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionTotalCostVariance");
    private final static QName _ProjectLevelOuterAssign_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelOuterAssign");
    private final static QName _ProjectSummarizedDataDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummarizedDataDate");
    private final static QName _ProjectSummaryRemainingFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingFinishDate");
    private final static QName _ProjectSummaryEstimateAtCompletionByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEstimateAtCompletionByLaborUnits");
    private final static QName _ProjectSummaryActualThisPeriodLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualThisPeriodLaborCost");
    private final static QName _ProjectCurrentVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CurrentVariance");
    private final static QName _ProjectSummaryScheduleVarianceByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryScheduleVarianceByCost");
    private final static QName _ProjectLastApplyActualsDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastApplyActualsDate");
    private final static QName _ProjectSummaryRemainingExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingExpenseCost");
    private final static QName _ProjectLastUpdateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastUpdateDate");
    private final static QName _ProjectSummaryActualStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualStartDate");
    private final static QName _ProjectStrategicPriority_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "StrategicPriority");
    private final static QName _ProjectSummaryLaborCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryLaborCostPercentComplete");
    private final static QName _ProjectSummaryEstimateAtCompletionLowPercentByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEstimateAtCompletionLowPercentByLaborUnits");
    private final static QName _ProjectSyncWbsHierarchyFlag_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SyncWbsHierarchyFlag");
    private final static QName _ProjectSummaryDurationVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryDurationVariance");
    private final static QName _ProjectSummaryActualLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualLaborCost");
    private final static QName _ProjectWBSMilestonePercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "WBSMilestonePercentComplete");
    private final static QName _ProjectSummaryAccountingVarianceByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAccountingVarianceByLaborUnits");
    private final static QName _ProjectSummaryBaselineFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineFinishDate");
    private final static QName _ProjectSummaryTotalCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryTotalCostVariance");
    private final static QName _ProjectOwnerResourceObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "OwnerResourceObjectId");
    private final static QName _ProjectSummaryLaborUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryLaborUnitsPercentComplete");
    private final static QName _ProjectSummaryRemainingNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingNonLaborUnits");
    private final static QName _ProjectLastScheduleDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastScheduleDate");
    private final static QName _ProjectSummaryCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostPercentComplete");
    private final static QName _ProjectSummaryBaselineNotStartedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineNotStartedActivityCount");
    private final static QName _ProjectSummaryFinishDateVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryFinishDateVariance");
    private final static QName _ProjectLongitude_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "Longitude");
    private final static QName _ProjectSummaryLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryLaborUnitsVariance");
    private final static QName _ProjectSummaryPlannedNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedNonLaborUnits");
    private final static QName _ProjectSummaryActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActivityCount");
    private final static QName _ProjectTotalBenefitPlan_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "TotalBenefitPlan");
    private final static QName _ProjectSummaryRemainingLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingLaborUnits");
    private final static QName _ProjectSummarySchedulePercentCompleteByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummarySchedulePercentCompleteByLaborUnits");
    private final static QName _ProjectSummaryNonLaborCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryNonLaborCostPercentComplete");
    private final static QName _ProjectSummaryRemainingNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingNonLaborCost");
    private final static QName _ProjectSummaryCostVarianceByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostVarianceByCost");
    private final static QName _ProjectSummaryBaselineStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineStartDate");
    private final static QName _ProjectProjectForecastStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ProjectForecastStartDate");
    private final static QName _ProjectSummarySchedulePerformanceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummarySchedulePerformanceIndexByLaborUnits");
    private final static QName _ProjectSummaryEstimateToCompleteByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEstimateToCompleteByCost");
    private final static QName _ProjectSummaryActualExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualExpenseCost");
    private final static QName _ProjectSummaryCostVarianceByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostVarianceByLaborUnits");
    private final static QName _ProjectMustFinishByDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "MustFinishByDate");
    private final static QName _ProjectSummaryRemainingLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingLaborCost");
    private final static QName _ProjectSummaryBaselineExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineExpenseCost");
    private final static QName _ProjectSummaryVarianceAtCompletionByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryVarianceAtCompletionByLaborUnits");
    private final static QName _ProjectSummaryPlannedNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedNonLaborCost");
    private final static QName _ProjectRiskExposure_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "RiskExposure");
    private final static QName _ProjectActivityDefaultPricePerUnit_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ActivityDefaultPricePerUnit");
    private final static QName _ProjectSummaryPlannedDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedDuration");
    private final static QName _ProjectMakeOpenEndedActivitiesCritical_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "MakeOpenEndedActivitiesCritical");
    private final static QName _ProjectForecastFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ForecastFinishDate");
    private final static QName _ProjectSummaryScheduleVarianceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryScheduleVarianceIndex");
    private final static QName _ProjectSummaryCostPerformanceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostPerformanceIndexByLaborUnits");
    private final static QName _ProjectCriticalActivityFloatThreshold_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CriticalActivityFloatThreshold");
    private final static QName _ProjectNetPresentValue_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "NetPresentValue");
    private final static QName _ProjectSummaryActualLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualLaborUnits");
    private final static QName _ProjectSummaryBaselineNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineNonLaborUnits");
    private final static QName _ProjectSummaryAccountingVarianceByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAccountingVarianceByCost");
    private final static QName _ProjectFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "FinishDate");
    private final static QName _ProjectLevelOuterAssignPriority_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelOuterAssignPriority");
    private final static QName _ProjectUnifierCBSTasksOnlyFlag_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UnifierCBSTasksOnlyFlag");
    private final static QName _ProjectLimitMultipleFloatPaths_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LimitMultipleFloatPaths");
    private final static QName _ProjectHasFutureBucketData_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "HasFutureBucketData");
    private final static QName _ProjectSummaryActualDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualDuration");
    private final static QName _ProjectSummaryAtCompletionLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionLaborCost");
    private final static QName _ProjectLocationObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LocationObjectId");
    private final static QName _ProjectStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "StartDate");
    private final static QName _ProjectMultipleFloatPathsUseTotalFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "MultipleFloatPathsUseTotalFloat");
    private final static QName _ProjectSummaryRemainingDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingDuration");
    private final static QName _ProjectActivityDefaultCalendarObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ActivityDefaultCalendarObjectId");
    private final static QName _ProjectSummaryCostPerformanceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostPerformanceIndexByCost");
    private final static QName _ProjectSummaryBaselineTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineTotalCost");
    private final static QName _ProjectLevelAllResources_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelAllResources");
    private final static QName _ProjectCurrentBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CurrentBudget");
    private final static QName _ProjectSummaryRemainingTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingTotalCost");
    private final static QName _ProjectForecastStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ForecastStartDate");
    private final static QName _ProjectSummaryBaselineCompletedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineCompletedActivityCount");
    private final static QName _ProjectRiskMatrixObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "RiskMatrixObjectId");
    private final static QName _ProjectSummaryDurationPercentOfPlanned_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryDurationPercentOfPlanned");
    private final static QName _ProjectSummaryCompletedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCompletedActivityCount");
    private final static QName _ProjectSummaryDurationPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryDurationPercentComplete");
    private final static QName _ProjectSummaryScheduleVarianceByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryScheduleVarianceByLaborUnits");
    private final static QName _ProjectSummaryPlannedExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedExpenseCost");
    private final static QName _ProjectActivityIdIncrement_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ActivityIdIncrement");
    private final static QName _ProjectSummaryCostVarianceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostVarianceIndexByCost");
    private final static QName _ProjectIgnoreOtherProjectRelationships_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "IgnoreOtherProjectRelationships");
    private final static QName _ProjectSummarizeToWBSLevel_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummarizeToWBSLevel");
    private final static QName _ProjectEarnedValueUserPercent_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "EarnedValueUserPercent");
    private final static QName _ProjectSummaryActualThisPeriodCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualThisPeriodCost");
    private final static QName _ProjectCheckOutDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CheckOutDate");
    private final static QName _ProjectSummaryAtCompletionDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionDuration");
    private final static QName _ProjectSummaryPlannedFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedFinishDate");
    private final static QName _ProjectSummaryExpenseCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryExpenseCostVariance");
    private final static QName _ProjectSummaryAtCompletionNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionNonLaborCost");
    private final static QName _ProjectProposedBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ProposedBudget");
    private final static QName _ProjectUseExpectedFinishDates_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UseExpectedFinishDates");
    private final static QName _ProjectSummaryRemainingStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingStartDate");
    private final static QName _ProjectMultipleFloatPathsEnabled_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "MultipleFloatPathsEnabled");
    private final static QName _ProjectCriticalFloatThreshold_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CriticalFloatThreshold");
    private final static QName _ProjectSummaryNonLaborUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryNonLaborUnitsPercentComplete");
    private final static QName _ProjectActivityDefaultCostAccountObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ActivityDefaultCostAccountObjectId");
    private final static QName _ProjectLastSummarizedDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastSummarizedDate");
    private final static QName _ProjectSummaryTotalFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryTotalFloat");
    private final static QName _ProjectDistributedCurrentBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "DistributedCurrentBudget");
    private final static QName _ProjectWBSObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "WBSObjectId");
    private final static QName _ProjectSummaryActualNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualNonLaborUnits");
    private final static QName _ProjectLevelFloatThresholdCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelFloatThresholdCount");
    private final static QName _ProjectSummaryPlannedCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedCost");
    private final static QName _ProjectCriticalActivityFloatLimit_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CriticalActivityFloatLimit");
    private final static QName _ProjectSummaryActualMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualMaterialCost");
    private final static QName _ProjectLevelDateFlag_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelDateFlag");
    private final static QName _ProjectSummaryBaselineNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineNonLaborCost");
    private final static QName _ProjectSummaryPlannedLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedLaborUnits");
    private final static QName _ProjectTotalSpendingPlanTally_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "TotalSpendingPlanTally");
    private final static QName _ProjectCheckOutUserObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CheckOutUserObjectId");
    private final static QName _ProjectSummaryPerformancePercentCompleteByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPerformancePercentCompleteByCost");
    private final static QName _ProjectTotalBenefitPlanTally_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "TotalBenefitPlanTally");
    private final static QName _ProjectSummaryBaselineDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineDuration");
    private final static QName _ProjectSummaryPerformancePercentCompleteByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPerformancePercentCompleteByLaborUnits");
    private final static QName _ProjectSummaryScheduleVarianceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryScheduleVarianceIndexByLaborUnits");
    private final static QName _ProjectSummaryPlannedValueByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedValueByCost");
    private final static QName _ProjectStartToStartLagCalculationType_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "StartToStartLagCalculationType");
    private final static QName _ProjectCreateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CreateDate");
    private final static QName _ProjectScheduledFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ScheduledFinishDate");
    private final static QName _ProjectSummaryBaselineMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineMaterialCost");
    private final static QName _ProjectOverallProjectScore_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "OverallProjectScore");
    private final static QName _ProjectSummaryEarnedValueByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEarnedValueByLaborUnits");
    private final static QName _ProjectSummaryRemainingMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryRemainingMaterialCost");
    private final static QName _ProjectLastPublishedOn_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastPublishedOn");
    private final static QName _ProjectSummaryAtCompletionLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionLaborUnits");
    private final static QName _ProjectSummaryEstimateToCompleteByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEstimateToCompleteByLaborUnits");
    private final static QName _ProjectTotalFunding_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "TotalFunding");
    private final static QName _ProjectSummaryScheduleVarianceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryScheduleVarianceIndexByCost");
    private final static QName _ProjectSummaryMaterialCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryMaterialCostPercentComplete");
    private final static QName _ProjectSummaryEstimateAtCompletionHighPercentByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEstimateAtCompletionHighPercentByLaborUnits");
    private final static QName _ProjectLastLevelDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LastLevelDate");
    private final static QName _ProjectSummaryPlannedValueByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedValueByLaborUnits");
    private final static QName _ProjectSummaryBudgetAtCompletionByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBudgetAtCompletionByCost");
    private final static QName _ProjectSummaryActualValueByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualValueByCost");
    private final static QName _ProjectAnnualDiscountRate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "AnnualDiscountRate");
    private final static QName _ProjectSummaryToCompletePerformanceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryToCompletePerformanceIndexByCost");
    private final static QName _ProjectSummaryAtCompletionExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionExpenseCost");
    private final static QName _ProjectSummaryPlannedMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedMaterialCost");
    private final static QName _ProjectLevelOverAllocationPercent_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "LevelOverAllocationPercent");
    private final static QName _ProjectSummaryEstimateAtCompletionByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryEstimateAtCompletionByCost");
    private final static QName _ProjectUnallocatedBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "UnallocatedBudget");
    private final static QName _ProjectSummaryBudgetAtCompletionByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBudgetAtCompletionByLaborUnits");
    private final static QName _ProjectSummaryPlannedStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryPlannedStartDate");
    private final static QName _ProjectMaximumMultipleFloatPaths_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "MaximumMultipleFloatPaths");
    private final static QName _ProjectSummaryCostPercentOfPlanned_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostPercentOfPlanned");
    private final static QName _ProjectSummaryActualThisPeriodNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualThisPeriodNonLaborUnits");
    private final static QName _ProjectTotalSpendingPlan_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "TotalSpendingPlan");
    private final static QName _ProjectSummaryExpenseCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryExpenseCostPercentComplete");
    private final static QName _ProjectOriginalBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "OriginalBudget");
    private final static QName _ProjectSummaryStartDateVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryStartDateVariance");
    private final static QName _ProjectSummaryAtCompletionNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryAtCompletionNonLaborUnits");
    private final static QName _ProjectSummaryNonLaborCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryNonLaborCostVariance");
    private final static QName _ProjectSourceProjectObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SourceProjectObjectId");
    private final static QName _ProjectCalculateFloatBasedOnFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CalculateFloatBasedOnFinishDate");
    private final static QName _ProjectSummaryBaselineInProgressActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryBaselineInProgressActivityCount");
    private final static QName _ProjectCurrentBaselineProjectObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "CurrentBaselineProjectObjectId");
    private final static QName _ProjectSummaryActualThisPeriodLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryActualThisPeriodLaborUnits");
    private final static QName _ProjectSummaryLaborCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryLaborCostVariance");
    private final static QName _ProjectSummaryCostVarianceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "SummaryCostVarianceIndex");
    private final static QName _ProjectActivityIdSuffix_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", "ActivityIdSuffix");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnpe.p6.projectservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IntegrationFaultType }
     * 
     */
    public IntegrationFaultType createIntegrationFaultType() {
        return new IntegrationFaultType();
    }

    /**
     * Create an instance of {@link CopyWBSFromTemplate }
     * 
     */
    public CopyWBSFromTemplate createCopyWBSFromTemplate() {
        return new CopyWBSFromTemplate();
    }

    /**
     * Create an instance of {@link CreateProjectFromTemplateResponse }
     * 
     */
    public CreateProjectFromTemplateResponse createCreateProjectFromTemplateResponse() {
        return new CreateProjectFromTemplateResponse();
    }

    /**
     * Create an instance of {@link LoadActivityUDFValuesNewerThanBaselineResponse }
     * 
     */
    public LoadActivityUDFValuesNewerThanBaselineResponse createLoadActivityUDFValuesNewerThanBaselineResponse() {
        return new LoadActivityUDFValuesNewerThanBaselineResponse();
    }

    /**
     * Create an instance of {@link CopyProjectAsReflectionResponse }
     * 
     */
    public CopyProjectAsReflectionResponse createCopyProjectAsReflectionResponse() {
        return new CopyProjectAsReflectionResponse();
    }

    /**
     * Create an instance of {@link ReadProjects }
     * 
     */
    public ReadProjects createReadProjects() {
        return new ReadProjects();
    }

    /**
     * Create an instance of {@link LoadAllResourcesResponse }
     * 
     */
    public LoadAllResourcesResponse createLoadAllResourcesResponse() {
        return new LoadAllResourcesResponse();
    }

    /**
     * Create an instance of {@link CopyBaselineResponse }
     * 
     */
    public CopyBaselineResponse createCopyBaselineResponse() {
        return new CopyBaselineResponse();
    }

    /**
     * Create an instance of {@link CopyProjectAsBaseline }
     * 
     */
    public CopyProjectAsBaseline createCopyProjectAsBaseline() {
        return new CopyProjectAsBaseline();
    }

    /**
     * Create an instance of {@link UpdateProjectsResponse }
     * 
     */
    public UpdateProjectsResponse createUpdateProjectsResponse() {
        return new UpdateProjectsResponse();
    }

    /**
     * Create an instance of {@link UpdateProjectPreferencesResponse }
     * 
     */
    public UpdateProjectPreferencesResponse createUpdateProjectPreferencesResponse() {
        return new UpdateProjectPreferencesResponse();
    }

    /**
     * Create an instance of {@link CopyBaseline }
     * 
     */
    public CopyBaseline createCopyBaseline() {
        return new CopyBaseline();
    }

    /**
     * Create an instance of {@link LoadAllResources }
     * 
     */
    public LoadAllResources createLoadAllResources() {
        return new LoadAllResources();
    }

    /**
     * Create an instance of {@link CalculateProjectScore }
     * 
     */
    public CalculateProjectScore createCalculateProjectScore() {
        return new CalculateProjectScore();
    }

    /**
     * Create an instance of {@link ConvertProjectToBaselineResponse }
     * 
     */
    public ConvertProjectToBaselineResponse createConvertProjectToBaselineResponse() {
        return new ConvertProjectToBaselineResponse();
    }

    /**
     * Create an instance of {@link UpdateProjectPreferences }
     * 
     */
    public UpdateProjectPreferences createUpdateProjectPreferences() {
        return new UpdateProjectPreferences();
    }

    /**
     * Create an instance of {@link IsProjectLocked }
     * 
     */
    public IsProjectLocked createIsProjectLocked() {
        return new IsProjectLocked();
    }

    /**
     * Create an instance of {@link PublishProject }
     * 
     */
    public PublishProject createPublishProject() {
        return new PublishProject();
    }

    /**
     * Create an instance of {@link LoadActivitiesNewerThanBaselineResponse }
     * 
     */
    public LoadActivitiesNewerThanBaselineResponse createLoadActivitiesNewerThanBaselineResponse() {
        return new LoadActivitiesNewerThanBaselineResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthProjectResponse }
     * 
     */
    public GetFieldLengthProjectResponse createGetFieldLengthProjectResponse() {
        return new GetFieldLengthProjectResponse();
    }

    /**
     * Create an instance of {@link CopyProjectResponse }
     * 
     */
    public CopyProjectResponse createCopyProjectResponse() {
        return new CopyProjectResponse();
    }

    /**
     * Create an instance of {@link ReadProjectsResponse }
     * 
     */
    public ReadProjectsResponse createReadProjectsResponse() {
        return new ReadProjectsResponse();
    }

    /**
     * Create an instance of {@link ConvertProjectToBaseline }
     * 
     */
    public ConvertProjectToBaseline createConvertProjectToBaseline() {
        return new ConvertProjectToBaseline();
    }

    /**
     * Create an instance of {@link IsProjectLockedResponse }
     * 
     */
    public IsProjectLockedResponse createIsProjectLockedResponse() {
        return new IsProjectLockedResponse();
    }

    /**
     * Create an instance of {@link LoadActivityCodesNewerThanBaseline }
     * 
     */
    public LoadActivityCodesNewerThanBaseline createLoadActivityCodesNewerThanBaseline() {
        return new LoadActivityCodesNewerThanBaseline();
    }

    /**
     * Create an instance of {@link CreateCopyAsTemplateResponse }
     * 
     */
    public CreateCopyAsTemplateResponse createCreateCopyAsTemplateResponse() {
        return new CreateCopyAsTemplateResponse();
    }

    /**
     * Create an instance of {@link UpdateProjects }
     * 
     */
    public UpdateProjects createUpdateProjects() {
        return new UpdateProjects();
    }

    /**
     * Create an instance of {@link GetFieldLengthProject }
     * 
     */
    public GetFieldLengthProject createGetFieldLengthProject() {
        return new GetFieldLengthProject();
    }

    /**
     * Create an instance of {@link DeleteProjects }
     * 
     */
    public DeleteProjects createDeleteProjects() {
        return new DeleteProjects();
    }

    /**
     * Create an instance of {@link CreateProjectFromTemplate }
     * 
     */
    public CreateProjectFromTemplate createCreateProjectFromTemplate() {
        return new CreateProjectFromTemplate();
    }

    /**
     * Create an instance of {@link LoadActivityCodesNewerThanBaselineResponse }
     * 
     */
    public LoadActivityCodesNewerThanBaselineResponse createLoadActivityCodesNewerThanBaselineResponse() {
        return new LoadActivityCodesNewerThanBaselineResponse();
    }

    /**
     * Create an instance of {@link CopyWBSFromTemplateResponse }
     * 
     */
    public CopyWBSFromTemplateResponse createCopyWBSFromTemplateResponse() {
        return new CopyWBSFromTemplateResponse();
    }

    /**
     * Create an instance of {@link LoadActivityUDFValuesNewerThanBaseline }
     * 
     */
    public LoadActivityUDFValuesNewerThanBaseline createLoadActivityUDFValuesNewerThanBaseline() {
        return new LoadActivityUDFValuesNewerThanBaseline();
    }

    /**
     * Create an instance of {@link AssignProjectAsBaselineResponse }
     * 
     */
    public AssignProjectAsBaselineResponse createAssignProjectAsBaselineResponse() {
        return new AssignProjectAsBaselineResponse();
    }

    /**
     * Create an instance of {@link AssignProjectAsBaseline }
     * 
     */
    public AssignProjectAsBaseline createAssignProjectAsBaseline() {
        return new AssignProjectAsBaseline();
    }

    /**
     * Create an instance of {@link PublishProjectResponse }
     * 
     */
    public PublishProjectResponse createPublishProjectResponse() {
        return new PublishProjectResponse();
    }

    /**
     * Create an instance of {@link LoadActivitiesNewerThanBaseline }
     * 
     */
    public LoadActivitiesNewerThanBaseline createLoadActivitiesNewerThanBaseline() {
        return new LoadActivitiesNewerThanBaseline();
    }

    /**
     * Create an instance of {@link CopyProject }
     * 
     */
    public CopyProject createCopyProject() {
        return new CopyProject();
    }

    /**
     * Create an instance of {@link CopyProjectAsBaselineResponse }
     * 
     */
    public CopyProjectAsBaselineResponse createCopyProjectAsBaselineResponse() {
        return new CopyProjectAsBaselineResponse();
    }

    /**
     * Create an instance of {@link CopyProjectAsReflection }
     * 
     */
    public CopyProjectAsReflection createCopyProjectAsReflection() {
        return new CopyProjectAsReflection();
    }

    /**
     * Create an instance of {@link DeleteProjectsResponse }
     * 
     */
    public DeleteProjectsResponse createDeleteProjectsResponse() {
        return new DeleteProjectsResponse();
    }

    /**
     * Create an instance of {@link CreateCopyAsTemplate }
     * 
     */
    public CreateCopyAsTemplate createCreateCopyAsTemplate() {
        return new CreateCopyAsTemplate();
    }

    /**
     * Create an instance of {@link CreateProjects }
     * 
     */
    public CreateProjects createCreateProjects() {
        return new CreateProjects();
    }

    /**
     * Create an instance of {@link CalculateProjectScoreResponse }
     * 
     */
    public CalculateProjectScoreResponse createCalculateProjectScoreResponse() {
        return new CalculateProjectScoreResponse();
    }

    /**
     * Create an instance of {@link CreateProjectsResponse }
     * 
     */
    public CreateProjectsResponse createCreateProjectsResponse() {
        return new CreateProjectsResponse();
    }

    /**
     * Create an instance of {@link Project }
     * 
     */
    public Project createProject() {
        return new Project();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UpdateProjectsResponse")
    public JAXBElement<UpdateProjectsResponse> createUpdateProjectsResponse(UpdateProjectsResponse value) {
        return new JAXBElement<UpdateProjectsResponse>(_UpdateProjectsResponse_QNAME, UpdateProjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjectPreferencesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UpdateProjectPreferencesResponse")
    public JAXBElement<UpdateProjectPreferencesResponse> createUpdateProjectPreferencesResponse(UpdateProjectPreferencesResponse value) {
        return new JAXBElement<UpdateProjectPreferencesResponse>(_UpdateProjectPreferencesResponse_QNAME, UpdateProjectPreferencesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyBaselineResponse")
    public JAXBElement<CopyBaselineResponse> createCopyBaselineResponse(CopyBaselineResponse value) {
        return new JAXBElement<CopyBaselineResponse>(_CopyBaselineResponse_QNAME, CopyBaselineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadAllResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadAllResourcesResponse")
    public JAXBElement<LoadAllResourcesResponse> createLoadAllResourcesResponse(LoadAllResourcesResponse value) {
        return new JAXBElement<LoadAllResourcesResponse>(_LoadAllResourcesResponse_QNAME, LoadAllResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyProjectAsBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyProjectAsBaseline")
    public JAXBElement<CopyProjectAsBaseline> createCopyProjectAsBaseline(CopyProjectAsBaseline value) {
        return new JAXBElement<CopyProjectAsBaseline>(_CopyProjectAsBaseline_QNAME, CopyProjectAsBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjectPreferences }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UpdateProjectPreferences")
    public JAXBElement<UpdateProjectPreferences> createUpdateProjectPreferences(UpdateProjectPreferences value) {
        return new JAXBElement<UpdateProjectPreferences>(_UpdateProjectPreferences_QNAME, UpdateProjectPreferences.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyBaseline")
    public JAXBElement<CopyBaseline> createCopyBaseline(CopyBaseline value) {
        return new JAXBElement<CopyBaseline>(_CopyBaseline_QNAME, CopyBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadAllResources }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadAllResources")
    public JAXBElement<LoadAllResources> createLoadAllResources(LoadAllResources value) {
        return new JAXBElement<LoadAllResources>(_LoadAllResources_QNAME, LoadAllResources.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateProjectScore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CalculateProjectScore")
    public JAXBElement<CalculateProjectScore> createCalculateProjectScore(CalculateProjectScore value) {
        return new JAXBElement<CalculateProjectScore>(_CalculateProjectScore_QNAME, CalculateProjectScore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConvertProjectToBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ConvertProjectToBaselineResponse")
    public JAXBElement<ConvertProjectToBaselineResponse> createConvertProjectToBaselineResponse(ConvertProjectToBaselineResponse value) {
        return new JAXBElement<ConvertProjectToBaselineResponse>(_ConvertProjectToBaselineResponse_QNAME, ConvertProjectToBaselineResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link PublishProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "PublishProject")
    public JAXBElement<PublishProject> createPublishProject(PublishProject value) {
        return new JAXBElement<PublishProject>(_PublishProject_QNAME, PublishProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsProjectLocked }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "IsProjectLocked")
    public JAXBElement<IsProjectLocked> createIsProjectLocked(IsProjectLocked value) {
        return new JAXBElement<IsProjectLocked>(_IsProjectLocked_QNAME, IsProjectLocked.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadProjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ReadProjectsResponse")
    public JAXBElement<ReadProjectsResponse> createReadProjectsResponse(ReadProjectsResponse value) {
        return new JAXBElement<ReadProjectsResponse>(_ReadProjectsResponse_QNAME, ReadProjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadActivitiesNewerThanBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadActivitiesNewerThanBaselineResponse")
    public JAXBElement<LoadActivitiesNewerThanBaselineResponse> createLoadActivitiesNewerThanBaselineResponse(LoadActivitiesNewerThanBaselineResponse value) {
        return new JAXBElement<LoadActivitiesNewerThanBaselineResponse>(_LoadActivitiesNewerThanBaselineResponse_QNAME, LoadActivitiesNewerThanBaselineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "getFieldLengthProjectResponse")
    public JAXBElement<GetFieldLengthProjectResponse> createGetFieldLengthProjectResponse(GetFieldLengthProjectResponse value) {
        return new JAXBElement<GetFieldLengthProjectResponse>(_GetFieldLengthProjectResponse_QNAME, GetFieldLengthProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyProjectResponse")
    public JAXBElement<CopyProjectResponse> createCopyProjectResponse(CopyProjectResponse value) {
        return new JAXBElement<CopyProjectResponse>(_CopyProjectResponse_QNAME, CopyProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectFromTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateProjectFromTemplateResponse")
    public JAXBElement<CreateProjectFromTemplateResponse> createCreateProjectFromTemplateResponse(CreateProjectFromTemplateResponse value) {
        return new JAXBElement<CreateProjectFromTemplateResponse>(_CreateProjectFromTemplateResponse_QNAME, CreateProjectFromTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyWBSFromTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyWBSFromTemplate")
    public JAXBElement<CopyWBSFromTemplate> createCopyWBSFromTemplate(CopyWBSFromTemplate value) {
        return new JAXBElement<CopyWBSFromTemplate>(_CopyWBSFromTemplate_QNAME, CopyWBSFromTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyProjectAsReflectionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyProjectAsReflectionResponse")
    public JAXBElement<CopyProjectAsReflectionResponse> createCopyProjectAsReflectionResponse(CopyProjectAsReflectionResponse value) {
        return new JAXBElement<CopyProjectAsReflectionResponse>(_CopyProjectAsReflectionResponse_QNAME, CopyProjectAsReflectionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadActivityUDFValuesNewerThanBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadActivityUDFValuesNewerThanBaselineResponse")
    public JAXBElement<LoadActivityUDFValuesNewerThanBaselineResponse> createLoadActivityUDFValuesNewerThanBaselineResponse(LoadActivityUDFValuesNewerThanBaselineResponse value) {
        return new JAXBElement<LoadActivityUDFValuesNewerThanBaselineResponse>(_LoadActivityUDFValuesNewerThanBaselineResponse_QNAME, LoadActivityUDFValuesNewerThanBaselineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadProjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ReadProjects")
    public JAXBElement<ReadProjects> createReadProjects(ReadProjects value) {
        return new JAXBElement<ReadProjects>(_ReadProjects_QNAME, ReadProjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyWBSFromTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyWBSFromTemplateResponse")
    public JAXBElement<CopyWBSFromTemplateResponse> createCopyWBSFromTemplateResponse(CopyWBSFromTemplateResponse value) {
        return new JAXBElement<CopyWBSFromTemplateResponse>(_CopyWBSFromTemplateResponse_QNAME, CopyWBSFromTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyProject")
    public JAXBElement<CopyProject> createCopyProject(CopyProject value) {
        return new JAXBElement<CopyProject>(_CopyProject_QNAME, CopyProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadActivitiesNewerThanBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadActivitiesNewerThanBaseline")
    public JAXBElement<LoadActivitiesNewerThanBaseline> createLoadActivitiesNewerThanBaseline(LoadActivitiesNewerThanBaseline value) {
        return new JAXBElement<LoadActivitiesNewerThanBaseline>(_LoadActivitiesNewerThanBaseline_QNAME, LoadActivitiesNewerThanBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PublishProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "PublishProjectResponse")
    public JAXBElement<PublishProjectResponse> createPublishProjectResponse(PublishProjectResponse value) {
        return new JAXBElement<PublishProjectResponse>(_PublishProjectResponse_QNAME, PublishProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssignProjectAsBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "AssignProjectAsBaseline")
    public JAXBElement<AssignProjectAsBaseline> createAssignProjectAsBaseline(AssignProjectAsBaseline value) {
        return new JAXBElement<AssignProjectAsBaseline>(_AssignProjectAsBaseline_QNAME, AssignProjectAsBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssignProjectAsBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "AssignProjectAsBaselineResponse")
    public JAXBElement<AssignProjectAsBaselineResponse> createAssignProjectAsBaselineResponse(AssignProjectAsBaselineResponse value) {
        return new JAXBElement<AssignProjectAsBaselineResponse>(_AssignProjectAsBaselineResponse_QNAME, AssignProjectAsBaselineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadActivityUDFValuesNewerThanBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadActivityUDFValuesNewerThanBaseline")
    public JAXBElement<LoadActivityUDFValuesNewerThanBaseline> createLoadActivityUDFValuesNewerThanBaseline(LoadActivityUDFValuesNewerThanBaseline value) {
        return new JAXBElement<LoadActivityUDFValuesNewerThanBaseline>(_LoadActivityUDFValuesNewerThanBaseline_QNAME, LoadActivityUDFValuesNewerThanBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "DeleteProjectsResponse")
    public JAXBElement<DeleteProjectsResponse> createDeleteProjectsResponse(DeleteProjectsResponse value) {
        return new JAXBElement<DeleteProjectsResponse>(_DeleteProjectsResponse_QNAME, DeleteProjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyProjectAsReflection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyProjectAsReflection")
    public JAXBElement<CopyProjectAsReflection> createCopyProjectAsReflection(CopyProjectAsReflection value) {
        return new JAXBElement<CopyProjectAsReflection>(_CopyProjectAsReflection_QNAME, CopyProjectAsReflection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyProjectAsBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CopyProjectAsBaselineResponse")
    public JAXBElement<CopyProjectAsBaselineResponse> createCopyProjectAsBaselineResponse(CopyProjectAsBaselineResponse value) {
        return new JAXBElement<CopyProjectAsBaselineResponse>(_CopyProjectAsBaselineResponse_QNAME, CopyProjectAsBaselineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateProjectScoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CalculateProjectScoreResponse")
    public JAXBElement<CalculateProjectScoreResponse> createCalculateProjectScoreResponse(CalculateProjectScoreResponse value) {
        return new JAXBElement<CalculateProjectScoreResponse>(_CalculateProjectScoreResponse_QNAME, CalculateProjectScoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateProjectsResponse")
    public JAXBElement<CreateProjectsResponse> createCreateProjectsResponse(CreateProjectsResponse value) {
        return new JAXBElement<CreateProjectsResponse>(_CreateProjectsResponse_QNAME, CreateProjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCopyAsTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateCopyAsTemplate")
    public JAXBElement<CreateCopyAsTemplate> createCreateCopyAsTemplate(CreateCopyAsTemplate value) {
        return new JAXBElement<CreateCopyAsTemplate>(_CreateCopyAsTemplate_QNAME, CreateCopyAsTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateProjects")
    public JAXBElement<CreateProjects> createCreateProjects(CreateProjects value) {
        return new JAXBElement<CreateProjects>(_CreateProjects_QNAME, CreateProjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsProjectLockedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "IsProjectLockedResponse")
    public JAXBElement<IsProjectLockedResponse> createIsProjectLockedResponse(IsProjectLockedResponse value) {
        return new JAXBElement<IsProjectLockedResponse>(_IsProjectLockedResponse_QNAME, IsProjectLockedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConvertProjectToBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ConvertProjectToBaseline")
    public JAXBElement<ConvertProjectToBaseline> createConvertProjectToBaseline(ConvertProjectToBaseline value) {
        return new JAXBElement<ConvertProjectToBaseline>(_ConvertProjectToBaseline_QNAME, ConvertProjectToBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCopyAsTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateCopyAsTemplateResponse")
    public JAXBElement<CreateCopyAsTemplateResponse> createCreateCopyAsTemplateResponse(CreateCopyAsTemplateResponse value) {
        return new JAXBElement<CreateCopyAsTemplateResponse>(_CreateCopyAsTemplateResponse_QNAME, CreateCopyAsTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadActivityCodesNewerThanBaseline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadActivityCodesNewerThanBaseline")
    public JAXBElement<LoadActivityCodesNewerThanBaseline> createLoadActivityCodesNewerThanBaseline(LoadActivityCodesNewerThanBaseline value) {
        return new JAXBElement<LoadActivityCodesNewerThanBaseline>(_LoadActivityCodesNewerThanBaseline_QNAME, LoadActivityCodesNewerThanBaseline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectFromTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateProjectFromTemplate")
    public JAXBElement<CreateProjectFromTemplate> createCreateProjectFromTemplate(CreateProjectFromTemplate value) {
        return new JAXBElement<CreateProjectFromTemplate>(_CreateProjectFromTemplate_QNAME, CreateProjectFromTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "DeleteProjects")
    public JAXBElement<DeleteProjects> createDeleteProjects(DeleteProjects value) {
        return new JAXBElement<DeleteProjects>(_DeleteProjects_QNAME, DeleteProjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadActivityCodesNewerThanBaselineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LoadActivityCodesNewerThanBaselineResponse")
    public JAXBElement<LoadActivityCodesNewerThanBaselineResponse> createLoadActivityCodesNewerThanBaselineResponse(LoadActivityCodesNewerThanBaselineResponse value) {
        return new JAXBElement<LoadActivityCodesNewerThanBaselineResponse>(_LoadActivityCodesNewerThanBaselineResponse_QNAME, LoadActivityCodesNewerThanBaselineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UpdateProjects")
    public JAXBElement<UpdateProjects> createUpdateProjects(UpdateProjects value) {
        return new JAXBElement<UpdateProjects>(_UpdateProjects_QNAME, UpdateProjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "getFieldLengthProject")
    public JAXBElement<GetFieldLengthProject> createGetFieldLengthProject(GetFieldLengthProject value) {
        return new JAXBElement<GetFieldLengthProject>(_GetFieldLengthProject_QNAME, GetFieldLengthProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostVarianceIndexByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostVarianceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostVarianceIndexByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "EarnedValueETCUserValue", scope = Project.class)
    public JAXBElement<Double> createProjectEarnedValueETCUserValue(Double value) {
        return new JAXBElement<Double>(_ProjectEarnedValueETCUserValue_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryActualFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryActualFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualNonLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualNonLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryNotStartedActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryNotStartedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryNotStartedActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "IndependentETCLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectIndependentETCLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectIndependentETCLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "MultipleFloatPathsEndingActivityObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectMultipleFloatPathsEndingActivityObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectMultipleFloatPathsEndingActivityObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualThisPeriodMaterialCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualThisPeriodMaterialCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualThisPeriodMaterialCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionTotalCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionTotalCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionTotalCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummarySchedulePercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummarySchedulePercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummarySchedulePercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualThisPeriodNonLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualThisPeriodNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualThisPeriodNonLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "IndependentETCTotalCost", scope = Project.class)
    public JAXBElement<Double> createProjectIndependentETCTotalCost(Double value) {
        return new JAXBElement<Double>(_ProjectIndependentETCTotalCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UnifierDeleteActivitiesFlag", scope = Project.class)
    public JAXBElement<Boolean> createProjectUnifierDeleteActivitiesFlag(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectUnifierDeleteActivitiesFlag_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummarySchedulePerformanceIndexByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummarySchedulePerformanceIndexByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummarySchedulePerformanceIndexByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryNonLaborUnitsVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryNonLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryNonLaborUnitsVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "WBSHierarchyLevels", scope = Project.class)
    public JAXBElement<Integer> createProjectWBSHierarchyLevels(Integer value) {
        return new JAXBElement<Integer>(_ProjectWBSHierarchyLevels_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelWithinFloat", scope = Project.class)
    public JAXBElement<Boolean> createProjectLevelWithinFloat(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectLevelWithinFloat_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ReturnOnInvestment", scope = Project.class)
    public JAXBElement<Double> createProjectReturnOnInvestment(Double value) {
        return new JAXBElement<Double>(_ProjectReturnOnInvestment_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualValueByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualValueByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualValueByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "AnticipatedStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectAnticipatedStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectAnticipatedStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "RiskScore", scope = Project.class)
    public JAXBElement<Integer> createProjectRiskScore(Integer value) {
        return new JAXBElement<Integer>(_ProjectRiskScore_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastFinancialPeriodObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectLastFinancialPeriodObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectLastFinancialPeriodObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UndistributedCurrentVariance", scope = Project.class)
    public JAXBElement<Double> createProjectUndistributedCurrentVariance(Double value) {
        return new JAXBElement<Double>(_ProjectUndistributedCurrentVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryMaterialCostVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryMaterialCostVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryMaterialCostVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "Latitude", scope = Project.class)
    public JAXBElement<Double> createProjectLatitude(Double value) {
        return new JAXBElement<Double>(_ProjectLatitude_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryProgressFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryProgressFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryProgressFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryInProgressActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryInProgressActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryInProgressActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "StatusReviewerObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectStatusReviewerObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectStatusReviewerObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "DataDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectDataDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectDataDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "PaybackPeriod", scope = Project.class)
    public JAXBElement<Integer> createProjectPaybackPeriod(Integer value) {
        return new JAXBElement<Integer>(_ProjectPaybackPeriod_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UnifierEnabledFlag", scope = Project.class)
    public JAXBElement<Boolean> createProjectUnifierEnabledFlag(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectUnifierEnabledFlag_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryUnitsPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryUnitsPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualTotalCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualTotalCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualTotalCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "AnticipatedFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectAnticipatedFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectAnticipatedFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEarnedValueByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEarnedValueByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEarnedValueByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionMaterialCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionMaterialCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionMaterialCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionTotalCostVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionTotalCostVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionTotalCostVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelOuterAssign", scope = Project.class)
    public JAXBElement<Boolean> createProjectLevelOuterAssign(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectLevelOuterAssign_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummarizedDataDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummarizedDataDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummarizedDataDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryRemainingFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryRemainingFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEstimateAtCompletionByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEstimateAtCompletionByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEstimateAtCompletionByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualThisPeriodLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualThisPeriodLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualThisPeriodLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CurrentVariance", scope = Project.class)
    public JAXBElement<Double> createProjectCurrentVariance(Double value) {
        return new JAXBElement<Double>(_ProjectCurrentVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryScheduleVarianceByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryScheduleVarianceByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryScheduleVarianceByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastApplyActualsDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectLastApplyActualsDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectLastApplyActualsDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingExpenseCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingExpenseCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingExpenseCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastUpdateDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectLastUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectLastUpdateDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryActualStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryActualStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "StrategicPriority", scope = Project.class)
    public JAXBElement<Integer> createProjectStrategicPriority(Integer value) {
        return new JAXBElement<Integer>(_ProjectStrategicPriority_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryLaborCostPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryLaborCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryLaborCostPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEstimateAtCompletionLowPercentByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEstimateAtCompletionLowPercentByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEstimateAtCompletionLowPercentByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SyncWbsHierarchyFlag", scope = Project.class)
    public JAXBElement<Boolean> createProjectSyncWbsHierarchyFlag(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectSyncWbsHierarchyFlag_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryDurationVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryDurationVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryDurationVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "WBSMilestonePercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectWBSMilestonePercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectWBSMilestonePercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAccountingVarianceByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAccountingVarianceByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAccountingVarianceByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryBaselineFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryBaselineFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryTotalCostVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryTotalCostVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryTotalCostVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "OwnerResourceObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectOwnerResourceObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectOwnerResourceObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryLaborUnitsPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryLaborUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryLaborUnitsPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingNonLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingNonLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastScheduleDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectLastScheduleDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectLastScheduleDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineNotStartedActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryBaselineNotStartedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryBaselineNotStartedActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryFinishDateVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryFinishDateVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryFinishDateVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "Longitude", scope = Project.class)
    public JAXBElement<Double> createProjectLongitude(Double value) {
        return new JAXBElement<Double>(_ProjectLongitude_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryLaborUnitsVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryLaborUnitsVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedNonLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedNonLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "TotalBenefitPlan", scope = Project.class)
    public JAXBElement<Double> createProjectTotalBenefitPlan(Double value) {
        return new JAXBElement<Double>(_ProjectTotalBenefitPlan_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummarySchedulePercentCompleteByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummarySchedulePercentCompleteByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummarySchedulePercentCompleteByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryNonLaborCostPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryNonLaborCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryNonLaborCostPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingNonLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingNonLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostVarianceByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostVarianceByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostVarianceByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryBaselineStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryBaselineStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ProjectForecastStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectProjectForecastStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectProjectForecastStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummarySchedulePerformanceIndexByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummarySchedulePerformanceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummarySchedulePerformanceIndexByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEstimateToCompleteByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEstimateToCompleteByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEstimateToCompleteByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualExpenseCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualExpenseCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualExpenseCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostVarianceByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostVarianceByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostVarianceByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "MustFinishByDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectMustFinishByDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectMustFinishByDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineExpenseCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineExpenseCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineExpenseCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryVarianceAtCompletionByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryVarianceAtCompletionByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryVarianceAtCompletionByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedNonLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedNonLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "RiskExposure", scope = Project.class)
    public JAXBElement<Double> createProjectRiskExposure(Double value) {
        return new JAXBElement<Double>(_ProjectRiskExposure_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ActivityDefaultPricePerUnit", scope = Project.class)
    public JAXBElement<Double> createProjectActivityDefaultPricePerUnit(Double value) {
        return new JAXBElement<Double>(_ProjectActivityDefaultPricePerUnit_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedDuration", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedDuration(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedDuration_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "MakeOpenEndedActivitiesCritical", scope = Project.class)
    public JAXBElement<Boolean> createProjectMakeOpenEndedActivitiesCritical(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectMakeOpenEndedActivitiesCritical_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ForecastFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectForecastFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectForecastFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryScheduleVarianceIndex", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryScheduleVarianceIndex(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryScheduleVarianceIndex_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostPerformanceIndexByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostPerformanceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostPerformanceIndexByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CriticalActivityFloatThreshold", scope = Project.class)
    public JAXBElement<Double> createProjectCriticalActivityFloatThreshold(Double value) {
        return new JAXBElement<Double>(_ProjectCriticalActivityFloatThreshold_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "NetPresentValue", scope = Project.class)
    public JAXBElement<Double> createProjectNetPresentValue(Double value) {
        return new JAXBElement<Double>(_ProjectNetPresentValue_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineNonLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineNonLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAccountingVarianceByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAccountingVarianceByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAccountingVarianceByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "FinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelOuterAssignPriority", scope = Project.class)
    public JAXBElement<Integer> createProjectLevelOuterAssignPriority(Integer value) {
        return new JAXBElement<Integer>(_ProjectLevelOuterAssignPriority_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UnifierCBSTasksOnlyFlag", scope = Project.class)
    public JAXBElement<Boolean> createProjectUnifierCBSTasksOnlyFlag(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectUnifierCBSTasksOnlyFlag_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LimitMultipleFloatPaths", scope = Project.class)
    public JAXBElement<Boolean> createProjectLimitMultipleFloatPaths(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectLimitMultipleFloatPaths_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "HasFutureBucketData", scope = Project.class)
    public JAXBElement<Boolean> createProjectHasFutureBucketData(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectHasFutureBucketData_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualDuration", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualDuration(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualDuration_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LocationObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectLocationObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectLocationObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "StartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "MultipleFloatPathsUseTotalFloat", scope = Project.class)
    public JAXBElement<Boolean> createProjectMultipleFloatPathsUseTotalFloat(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectMultipleFloatPathsUseTotalFloat_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingDuration", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingDuration(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingDuration_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ActivityDefaultCalendarObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectActivityDefaultCalendarObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectActivityDefaultCalendarObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostPerformanceIndexByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostPerformanceIndexByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostPerformanceIndexByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineTotalCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineTotalCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineTotalCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelAllResources", scope = Project.class)
    public JAXBElement<Boolean> createProjectLevelAllResources(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectLevelAllResources_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CurrentBudget", scope = Project.class)
    public JAXBElement<Double> createProjectCurrentBudget(Double value) {
        return new JAXBElement<Double>(_ProjectCurrentBudget_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingTotalCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingTotalCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingTotalCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ForecastStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectForecastStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectForecastStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineCompletedActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryBaselineCompletedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryBaselineCompletedActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "RiskMatrixObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectRiskMatrixObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectRiskMatrixObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryDurationPercentOfPlanned", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryDurationPercentOfPlanned(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryDurationPercentOfPlanned_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCompletedActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryCompletedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryCompletedActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryDurationPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryDurationPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryDurationPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryScheduleVarianceByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryScheduleVarianceByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryScheduleVarianceByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedExpenseCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedExpenseCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedExpenseCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ActivityIdIncrement", scope = Project.class)
    public JAXBElement<Integer> createProjectActivityIdIncrement(Integer value) {
        return new JAXBElement<Integer>(_ProjectActivityIdIncrement_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostVarianceIndexByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostVarianceIndexByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostVarianceIndexByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "IgnoreOtherProjectRelationships", scope = Project.class)
    public JAXBElement<Boolean> createProjectIgnoreOtherProjectRelationships(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectIgnoreOtherProjectRelationships_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummarizeToWBSLevel", scope = Project.class)
    public JAXBElement<Integer> createProjectSummarizeToWBSLevel(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummarizeToWBSLevel_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "EarnedValueUserPercent", scope = Project.class)
    public JAXBElement<Double> createProjectEarnedValueUserPercent(Double value) {
        return new JAXBElement<Double>(_ProjectEarnedValueUserPercent_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualThisPeriodCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualThisPeriodCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualThisPeriodCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CheckOutDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectCheckOutDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectCheckOutDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionDuration", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionDuration(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionDuration_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryPlannedFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryPlannedFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryExpenseCostVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryExpenseCostVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryExpenseCostVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionNonLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionNonLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ProposedBudget", scope = Project.class)
    public JAXBElement<Double> createProjectProposedBudget(Double value) {
        return new JAXBElement<Double>(_ProjectProposedBudget_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UseExpectedFinishDates", scope = Project.class)
    public JAXBElement<Boolean> createProjectUseExpectedFinishDates(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectUseExpectedFinishDates_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryRemainingStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryRemainingStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "MultipleFloatPathsEnabled", scope = Project.class)
    public JAXBElement<Boolean> createProjectMultipleFloatPathsEnabled(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectMultipleFloatPathsEnabled_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CriticalFloatThreshold", scope = Project.class)
    public JAXBElement<Double> createProjectCriticalFloatThreshold(Double value) {
        return new JAXBElement<Double>(_ProjectCriticalFloatThreshold_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryNonLaborUnitsPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryNonLaborUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryNonLaborUnitsPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ActivityDefaultCostAccountObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectActivityDefaultCostAccountObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectActivityDefaultCostAccountObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastSummarizedDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectLastSummarizedDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectLastSummarizedDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryTotalFloat", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryTotalFloat(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryTotalFloat_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "DistributedCurrentBudget", scope = Project.class)
    public JAXBElement<Double> createProjectDistributedCurrentBudget(Double value) {
        return new JAXBElement<Double>(_ProjectDistributedCurrentBudget_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "WBSObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectWBSObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectWBSObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualNonLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualNonLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelFloatThresholdCount", scope = Project.class)
    public JAXBElement<Integer> createProjectLevelFloatThresholdCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectLevelFloatThresholdCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CriticalActivityFloatLimit", scope = Project.class)
    public JAXBElement<Double> createProjectCriticalActivityFloatLimit(Double value) {
        return new JAXBElement<Double>(_ProjectCriticalActivityFloatLimit_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualMaterialCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualMaterialCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualMaterialCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelDateFlag", scope = Project.class)
    public JAXBElement<Boolean> createProjectLevelDateFlag(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectLevelDateFlag_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineNonLaborCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineNonLaborCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineNonLaborCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "TotalSpendingPlanTally", scope = Project.class)
    public JAXBElement<Double> createProjectTotalSpendingPlanTally(Double value) {
        return new JAXBElement<Double>(_ProjectTotalSpendingPlanTally_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CheckOutUserObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectCheckOutUserObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectCheckOutUserObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPerformancePercentCompleteByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPerformancePercentCompleteByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPerformancePercentCompleteByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "TotalBenefitPlanTally", scope = Project.class)
    public JAXBElement<Double> createProjectTotalBenefitPlanTally(Double value) {
        return new JAXBElement<Double>(_ProjectTotalBenefitPlanTally_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineDuration", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineDuration(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineDuration_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPerformancePercentCompleteByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPerformancePercentCompleteByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPerformancePercentCompleteByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryScheduleVarianceIndexByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryScheduleVarianceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryScheduleVarianceIndexByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedValueByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedValueByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedValueByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "StartToStartLagCalculationType", scope = Project.class)
    public JAXBElement<Boolean> createProjectStartToStartLagCalculationType(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectStartToStartLagCalculationType_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CreateDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectCreateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectCreateDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ScheduledFinishDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectScheduledFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectScheduledFinishDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineMaterialCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBaselineMaterialCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBaselineMaterialCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "OverallProjectScore", scope = Project.class)
    public JAXBElement<Integer> createProjectOverallProjectScore(Integer value) {
        return new JAXBElement<Integer>(_ProjectOverallProjectScore_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEarnedValueByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEarnedValueByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEarnedValueByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryRemainingMaterialCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryRemainingMaterialCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryRemainingMaterialCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastPublishedOn", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectLastPublishedOn(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectLastPublishedOn_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEstimateToCompleteByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEstimateToCompleteByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEstimateToCompleteByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "TotalFunding", scope = Project.class)
    public JAXBElement<Double> createProjectTotalFunding(Double value) {
        return new JAXBElement<Double>(_ProjectTotalFunding_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryScheduleVarianceIndexByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryScheduleVarianceIndexByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryScheduleVarianceIndexByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryMaterialCostPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryMaterialCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryMaterialCostPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEstimateAtCompletionHighPercentByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEstimateAtCompletionHighPercentByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEstimateAtCompletionHighPercentByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LastLevelDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectLastLevelDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectLastLevelDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedValueByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedValueByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedValueByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBudgetAtCompletionByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBudgetAtCompletionByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBudgetAtCompletionByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualValueByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualValueByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualValueByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "AnnualDiscountRate", scope = Project.class)
    public JAXBElement<Double> createProjectAnnualDiscountRate(Double value) {
        return new JAXBElement<Double>(_ProjectAnnualDiscountRate_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryToCompletePerformanceIndexByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryToCompletePerformanceIndexByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryToCompletePerformanceIndexByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionExpenseCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionExpenseCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionExpenseCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedMaterialCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryPlannedMaterialCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryPlannedMaterialCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "LevelOverAllocationPercent", scope = Project.class)
    public JAXBElement<Double> createProjectLevelOverAllocationPercent(Double value) {
        return new JAXBElement<Double>(_ProjectLevelOverAllocationPercent_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryEstimateAtCompletionByCost", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryEstimateAtCompletionByCost(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryEstimateAtCompletionByCost_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "UnallocatedBudget", scope = Project.class)
    public JAXBElement<Double> createProjectUnallocatedBudget(Double value) {
        return new JAXBElement<Double>(_ProjectUnallocatedBudget_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBudgetAtCompletionByLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryBudgetAtCompletionByLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryBudgetAtCompletionByLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryPlannedStartDate", scope = Project.class)
    public JAXBElement<XMLGregorianCalendar> createProjectSummaryPlannedStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ProjectSummaryPlannedStartDate_QNAME, XMLGregorianCalendar.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "MaximumMultipleFloatPaths", scope = Project.class)
    public JAXBElement<Integer> createProjectMaximumMultipleFloatPaths(Integer value) {
        return new JAXBElement<Integer>(_ProjectMaximumMultipleFloatPaths_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostPercentOfPlanned", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostPercentOfPlanned(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostPercentOfPlanned_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualThisPeriodNonLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualThisPeriodNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualThisPeriodNonLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "TotalSpendingPlan", scope = Project.class)
    public JAXBElement<Double> createProjectTotalSpendingPlan(Double value) {
        return new JAXBElement<Double>(_ProjectTotalSpendingPlan_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryExpenseCostPercentComplete", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryExpenseCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryExpenseCostPercentComplete_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "OriginalBudget", scope = Project.class)
    public JAXBElement<Double> createProjectOriginalBudget(Double value) {
        return new JAXBElement<Double>(_ProjectOriginalBudget_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryStartDateVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryStartDateVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryStartDateVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryAtCompletionNonLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryAtCompletionNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryAtCompletionNonLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryNonLaborCostVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryNonLaborCostVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryNonLaborCostVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SourceProjectObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectSourceProjectObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectSourceProjectObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CalculateFloatBasedOnFinishDate", scope = Project.class)
    public JAXBElement<Boolean> createProjectCalculateFloatBasedOnFinishDate(Boolean value) {
        return new JAXBElement<Boolean>(_ProjectCalculateFloatBasedOnFinishDate_QNAME, Boolean.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryBaselineInProgressActivityCount", scope = Project.class)
    public JAXBElement<Integer> createProjectSummaryBaselineInProgressActivityCount(Integer value) {
        return new JAXBElement<Integer>(_ProjectSummaryBaselineInProgressActivityCount_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "CurrentBaselineProjectObjectId", scope = Project.class)
    public JAXBElement<Integer> createProjectCurrentBaselineProjectObjectId(Integer value) {
        return new JAXBElement<Integer>(_ProjectCurrentBaselineProjectObjectId_QNAME, Integer.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryActualThisPeriodLaborUnits", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryActualThisPeriodLaborUnits(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryActualThisPeriodLaborUnits_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryLaborCostVariance", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryLaborCostVariance(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryLaborCostVariance_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "SummaryCostVarianceIndex", scope = Project.class)
    public JAXBElement<Double> createProjectSummaryCostVarianceIndex(Double value) {
        return new JAXBElement<Double>(_ProjectSummaryCostVarianceIndex_QNAME, Double.class, Project.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", name = "ActivityIdSuffix", scope = Project.class)
    public JAXBElement<Integer> createProjectActivityIdSuffix(Integer value) {
        return new JAXBElement<Integer>(_ProjectActivityIdSuffix_QNAME, Integer.class, Project.class, value);
    }

}
