
package com.cnpe.p6.wbsservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnpe.p6.wbsservice package. 
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

    private final static QName _CopyWBSFromTemplate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CopyWBSFromTemplate");
    private final static QName _UpdateWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "UpdateWBS");
    private final static QName _ReadAllWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadAllWBS");
    private final static QName _ReadActivityWBSPathResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadActivityWBSPathResponse");
    private final static QName _IntegrationFault_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/IntegrationFaultType/V1", "IntegrationFault");
    private final static QName _CreateWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CreateWBSResponse");
    private final static QName _GetFieldLengthWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "getFieldLengthWBS");
    private final static QName _ReadWBSPath_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadWBSPath");
    private final static QName _DeleteWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "DeleteWBS");
    private final static QName _CreateWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CreateWBS");
    private final static QName _GetFieldLengthWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "getFieldLengthWBSResponse");
    private final static QName _CopyWBSFromTemplateResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CopyWBSFromTemplateResponse");
    private final static QName _UpdateWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "UpdateWBSResponse");
    private final static QName _DeleteWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "DeleteWBSResponse");
    private final static QName _ReadWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadWBSResponse");
    private final static QName _ReadActivityWBSPath_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadActivityWBSPath");
    private final static QName _ReadWBSPathResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadWBSPathResponse");
    private final static QName _ReadWBS_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadWBS");
    private final static QName _ReadAllWBSResponse_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ReadAllWBSResponse");
    private final static QName _WBSSummaryAtCompletionTotalCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionTotalCostVariance");
    private final static QName _WBSSummaryAtCompletionMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionMaterialCost");
    private final static QName _WBSSummaryPlannedCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedCost");
    private final static QName _WBSSummaryActualMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualMaterialCost");
    private final static QName _WBSSummaryRemainingFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingFinishDate");
    private final static QName _WBSSummaryActualThisPeriodLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualThisPeriodLaborCost");
    private final static QName _WBSCurrentVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CurrentVariance");
    private final static QName _WBSSummaryEstimateAtCompletionByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEstimateAtCompletionByLaborUnits");
    private final static QName _WBSSummaryTotalFloat_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryTotalFloat");
    private final static QName _WBSSummaryActualNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualNonLaborUnits");
    private final static QName _WBSSummaryActualTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualTotalCost");
    private final static QName _WBSSummaryUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryUnitsPercentComplete");
    private final static QName _WBSDistributedCurrentBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "DistributedCurrentBudget");
    private final static QName _WBSAnticipatedFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "AnticipatedFinishDate");
    private final static QName _WBSSummaryEarnedValueByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEarnedValueByCost");
    private final static QName _WBSProposedBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ProposedBudget");
    private final static QName _WBSStatusReviewerObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "StatusReviewerObjectId");
    private final static QName _WBSSummaryRemainingStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingStartDate");
    private final static QName _WBSSummaryNonLaborUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryNonLaborUnitsPercentComplete");
    private final static QName _WBSSummaryPlannedFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedFinishDate");
    private final static QName _WBSSummaryAtCompletionDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionDuration");
    private final static QName _WBSSummaryPlannedLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedLaborCost");
    private final static QName _WBSUndistributedCurrentVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "UndistributedCurrentVariance");
    private final static QName _WBSSummaryExpenseCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryExpenseCostVariance");
    private final static QName _WBSSummaryProgressFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryProgressFinishDate");
    private final static QName _WBSSummaryMaterialCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryMaterialCostVariance");
    private final static QName _WBSSummaryAtCompletionNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionNonLaborCost");
    private final static QName _WBSSummaryInProgressActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryInProgressActivityCount");
    private final static QName _WBSSummaryCostVarianceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostVarianceIndexByCost");
    private final static QName _WBSSummaryPlannedExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedExpenseCost");
    private final static QName _WBSSummaryScheduleVarianceByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryScheduleVarianceByLaborUnits");
    private final static QName _WBSEarnedValueUserPercent_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "EarnedValueUserPercent");
    private final static QName _WBSSummaryActualValueByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualValueByLaborUnits");
    private final static QName _WBSSummaryActualThisPeriodCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualThisPeriodCost");
    private final static QName _WBSSummaryBaselineLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineLaborCost");
    private final static QName _WBSAnticipatedStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "AnticipatedStartDate");
    private final static QName _WBSSummaryDurationPercentOfPlanned_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryDurationPercentOfPlanned");
    private final static QName _WBSSummaryCompletedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCompletedActivityCount");
    private final static QName _WBSSummarySchedulePerformanceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummarySchedulePerformanceIndexByCost");
    private final static QName _WBSSummaryNonLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryNonLaborUnitsVariance");
    private final static QName _WBSSummaryDurationPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryDurationPercentComplete");
    private final static QName _WBSCurrentBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CurrentBudget");
    private final static QName _WBSIndependentETCLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "IndependentETCLaborUnits");
    private final static QName _WBSSummaryNotStartedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryNotStartedActivityCount");
    private final static QName _WBSSummaryActualNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualNonLaborCost");
    private final static QName _WBSSummaryActualThisPeriodNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualThisPeriodNonLaborCost");
    private final static QName _WBSSummarySchedulePercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummarySchedulePercentComplete");
    private final static QName _WBSSummaryRemainingTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingTotalCost");
    private final static QName _WBSSummaryActualThisPeriodMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualThisPeriodMaterialCost");
    private final static QName _WBSSummaryAtCompletionTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionTotalCost");
    private final static QName _WBSIndependentETCTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "IndependentETCTotalCost");
    private final static QName _WBSForecastStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ForecastStartDate");
    private final static QName _WBSSummaryBaselineCompletedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineCompletedActivityCount");
    private final static QName _WBSSummaryBaselineLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineLaborUnits");
    private final static QName _WBSSummaryAtCompletionLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionLaborCost");
    private final static QName _WBSSummaryActualDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualDuration");
    private final static QName _WBSSummaryRemainingDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingDuration");
    private final static QName _WBSStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "StartDate");
    private final static QName _WBSSummaryCostPerformanceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostPerformanceIndexByCost");
    private final static QName _WBSEarnedValueETCUserValue_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "EarnedValueETCUserValue");
    private final static QName _WBSSummaryCostVarianceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostVarianceIndexByLaborUnits");
    private final static QName _WBSSummaryBaselineTotalCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineTotalCost");
    private final static QName _WBSWBSCategoryObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "WBSCategoryObjectId");
    private final static QName _WBSSummaryActualFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualFinishDate");
    private final static QName _WBSSummaryBaselineInProgressActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineInProgressActivityCount");
    private final static QName _WBSSummaryAccountingVarianceByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAccountingVarianceByCost");
    private final static QName _WBSSummaryActualLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualLaborUnits");
    private final static QName _WBSSummaryBaselineNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineNonLaborUnits");
    private final static QName _WBSFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "FinishDate");
    private final static QName _WBSSummaryCostVarianceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostVarianceIndex");
    private final static QName _WBSSummaryLaborCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryLaborCostVariance");
    private final static QName _WBSSummaryActualThisPeriodLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualThisPeriodLaborUnits");
    private final static QName _WBSSummaryExpenseCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryExpenseCostPercentComplete");
    private final static QName _WBSTotalSpendingPlan_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "TotalSpendingPlan");
    private final static QName _WBSSummaryPlannedNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedNonLaborCost");
    private final static QName _WBSSummaryStartDateVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryStartDateVariance");
    private final static QName _WBSSummaryPlannedDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedDuration");
    private final static QName _WBSOriginalBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "OriginalBudget");
    private final static QName _WBSSummaryScheduleVarianceIndex_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryScheduleVarianceIndex");
    private final static QName _WBSForecastFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ForecastFinishDate");
    private final static QName _WBSSummaryAtCompletionNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionNonLaborUnits");
    private final static QName _WBSSummaryCostPerformanceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostPerformanceIndexByLaborUnits");
    private final static QName _WBSSummaryNonLaborCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryNonLaborCostVariance");
    private final static QName _WBSSummaryActualExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualExpenseCost");
    private final static QName _WBSSummaryBudgetAtCompletionByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBudgetAtCompletionByLaborUnits");
    private final static QName _WBSUnallocatedBudget_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "UnallocatedBudget");
    private final static QName _WBSSummaryCostVarianceByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostVarianceByLaborUnits");
    private final static QName _WBSSummaryPlannedStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedStartDate");
    private final static QName _WBSSummaryCostPercentOfPlanned_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostPercentOfPlanned");
    private final static QName _WBSSummaryRemainingLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingLaborCost");
    private final static QName _WBSSummaryVarianceAtCompletionByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryVarianceAtCompletionByLaborUnits");
    private final static QName _WBSSummaryActualThisPeriodNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualThisPeriodNonLaborUnits");
    private final static QName _WBSRolledUpStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "RolledUpStartDate");
    private final static QName _WBSSummaryBaselineExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineExpenseCost");
    private final static QName _WBSSummaryPlannedValueByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedValueByLaborUnits");
    private final static QName _WBSSummarySchedulePerformanceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummarySchedulePerformanceIndexByLaborUnits");
    private final static QName _WBSSummaryActualValueByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualValueByCost");
    private final static QName _WBSRolledUpFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "RolledUpFinishDate");
    private final static QName _WBSSummaryBudgetAtCompletionByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBudgetAtCompletionByCost");
    private final static QName _WBSSummaryEstimateToCompleteByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEstimateToCompleteByCost");
    private final static QName _WBSSummaryPlannedMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedMaterialCost");
    private final static QName _WBSSummaryAtCompletionExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionExpenseCost");
    private final static QName _WBSSummaryToCompletePerformanceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryToCompletePerformanceIndexByCost");
    private final static QName _WBSSummaryEstimateAtCompletionByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEstimateAtCompletionByCost");
    private final static QName _WBSSummaryEarnedValueByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEarnedValueByLaborUnits");
    private final static QName _WBSSummaryCostVarianceByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostVarianceByCost");
    private final static QName _WBSSummaryScheduleVarianceIndexByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryScheduleVarianceIndexByCost");
    private final static QName _WBSSummaryAtCompletionLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAtCompletionLaborUnits");
    private final static QName _WBSSummaryEstimateToCompleteByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEstimateToCompleteByLaborUnits");
    private final static QName _WBSSummaryRemainingMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingMaterialCost");
    private final static QName _WBSSummaryMaterialCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryMaterialCostPercentComplete");
    private final static QName _WBSSummaryBaselineStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineStartDate");
    private final static QName _WBSSummaryEstimateAtCompletionHighPercentByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEstimateAtCompletionHighPercentByLaborUnits");
    private final static QName _WBSSummaryCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryCostPercentComplete");
    private final static QName _WBSSummaryFinishDateVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryFinishDateVariance");
    private final static QName _WBSSummaryBaselineNotStartedActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineNotStartedActivityCount");
    private final static QName _WBSSummaryPlannedNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedNonLaborUnits");
    private final static QName _WBSSummaryBaselineMaterialCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineMaterialCost");
    private final static QName _WBSSummaryLaborUnitsVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryLaborUnitsVariance");
    private final static QName _WBSSummaryRemainingNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingNonLaborCost");
    private final static QName _WBSSummaryNonLaborCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryNonLaborCostPercentComplete");
    private final static QName _WBSTotalBenefitPlan_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "TotalBenefitPlan");
    private final static QName _WBSSummarySchedulePercentCompleteByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummarySchedulePercentCompleteByLaborUnits");
    private final static QName _WBSSummaryRemainingLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingLaborUnits");
    private final static QName _WBSParentObjectId_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "ParentObjectId");
    private final static QName _WBSSummaryActivityCount_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActivityCount");
    private final static QName _WBSSummaryBaselineDuration_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineDuration");
    private final static QName _WBSTotalBenefitPlanTally_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "TotalBenefitPlanTally");
    private final static QName _WBSSummaryActualLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualLaborCost");
    private final static QName _WBSWBSMilestonePercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "WBSMilestonePercentComplete");
    private final static QName _WBSSummaryPlannedValueByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedValueByCost");
    private final static QName _WBSSummaryScheduleVarianceIndexByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryScheduleVarianceIndexByLaborUnits");
    private final static QName _WBSSummaryPerformancePercentCompleteByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPerformancePercentCompleteByLaborUnits");
    private final static QName _WBSSummaryTotalCostVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryTotalCostVariance");
    private final static QName _WBSSummaryAccountingVarianceByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryAccountingVarianceByLaborUnits");
    private final static QName _WBSSummaryBaselineFinishDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineFinishDate");
    private final static QName _WBSCreateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "CreateDate");
    private final static QName _WBSSummaryRemainingNonLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingNonLaborUnits");
    private final static QName _WBSSummaryLaborUnitsPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryLaborUnitsPercentComplete");
    private final static QName _WBSSummaryRemainingExpenseCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryRemainingExpenseCost");
    private final static QName _WBSSummaryScheduleVarianceByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryScheduleVarianceByCost");
    private final static QName _WBSSummaryPlannedLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPlannedLaborUnits");
    private final static QName _WBSSummaryBaselineNonLaborCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryBaselineNonLaborCost");
    private final static QName _WBSSummaryActualStartDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryActualStartDate");
    private final static QName _WBSLastUpdateDate_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "LastUpdateDate");
    private final static QName _WBSTotalSpendingPlanTally_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "TotalSpendingPlanTally");
    private final static QName _WBSSummaryDurationVariance_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryDurationVariance");
    private final static QName _WBSSummaryPerformancePercentCompleteByCost_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryPerformancePercentCompleteByCost");
    private final static QName _WBSSummaryEstimateAtCompletionLowPercentByLaborUnits_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryEstimateAtCompletionLowPercentByLaborUnits");
    private final static QName _WBSSummaryLaborCostPercentComplete_QNAME = new QName("http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", "SummaryLaborCostPercentComplete");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnpe.p6.wbsservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteWBS }
     * 
     */
    public DeleteWBS createDeleteWBS() {
        return new DeleteWBS();
    }

    /**
     * Create an instance of {@link CopyWBSFromTemplate }
     * 
     */
    public CopyWBSFromTemplate createCopyWBSFromTemplate() {
        return new CopyWBSFromTemplate();
    }

    /**
     * Create an instance of {@link UpdateWBS }
     * 
     */
    public UpdateWBS createUpdateWBS() {
        return new UpdateWBS();
    }

    /**
     * Create an instance of {@link ReadAllWBS }
     * 
     */
    public ReadAllWBS createReadAllWBS() {
        return new ReadAllWBS();
    }

    /**
     * Create an instance of {@link ReadActivityWBSPathResponse }
     * 
     */
    public ReadActivityWBSPathResponse createReadActivityWBSPathResponse() {
        return new ReadActivityWBSPathResponse();
    }

    /**
     * Create an instance of {@link CreateWBSResponse }
     * 
     */
    public CreateWBSResponse createCreateWBSResponse() {
        return new CreateWBSResponse();
    }

    /**
     * Create an instance of {@link GetFieldLengthWBS }
     * 
     */
    public GetFieldLengthWBS createGetFieldLengthWBS() {
        return new GetFieldLengthWBS();
    }

    /**
     * Create an instance of {@link ReadWBSPath }
     * 
     */
    public ReadWBSPath createReadWBSPath() {
        return new ReadWBSPath();
    }

    /**
     * Create an instance of {@link CopyWBSFromTemplateResponse }
     * 
     */
    public CopyWBSFromTemplateResponse createCopyWBSFromTemplateResponse() {
        return new CopyWBSFromTemplateResponse();
    }

    /**
     * Create an instance of {@link UpdateWBSResponse }
     * 
     */
    public UpdateWBSResponse createUpdateWBSResponse() {
        return new UpdateWBSResponse();
    }

    /**
     * Create an instance of {@link CreateWBS }
     * 
     */
    public CreateWBS createCreateWBS() {
        return new CreateWBS();
    }

    /**
     * Create an instance of {@link GetFieldLengthWBSResponse }
     * 
     */
    public GetFieldLengthWBSResponse createGetFieldLengthWBSResponse() {
        return new GetFieldLengthWBSResponse();
    }

    /**
     * Create an instance of {@link ReadActivityWBSPath }
     * 
     */
    public ReadActivityWBSPath createReadActivityWBSPath() {
        return new ReadActivityWBSPath();
    }

    /**
     * Create an instance of {@link ReadWBSResponse }
     * 
     */
    public ReadWBSResponse createReadWBSResponse() {
        return new ReadWBSResponse();
    }

    /**
     * Create an instance of {@link DeleteWBSResponse }
     * 
     */
    public DeleteWBSResponse createDeleteWBSResponse() {
        return new DeleteWBSResponse();
    }

    /**
     * Create an instance of {@link ReadAllWBSResponse }
     * 
     */
    public ReadAllWBSResponse createReadAllWBSResponse() {
        return new ReadAllWBSResponse();
    }

    /**
     * Create an instance of {@link ReadWBS }
     * 
     */
    public ReadWBS createReadWBS() {
        return new ReadWBS();
    }

    /**
     * Create an instance of {@link ReadWBSPathResponse }
     * 
     */
    public ReadWBSPathResponse createReadWBSPathResponse() {
        return new ReadWBSPathResponse();
    }

    /**
     * Create an instance of {@link WBS }
     * 
     */
    public WBS createWBS() {
        return new WBS();
    }

    /**
     * Create an instance of {@link IntegrationFaultType }
     * 
     */
    public IntegrationFaultType createIntegrationFaultType() {
        return new IntegrationFaultType();
    }

    /**
     * Create an instance of {@link DeleteWBS.DeleteWithReplacement }
     * 
     */
    public DeleteWBS.DeleteWithReplacement createDeleteWBSDeleteWithReplacement() {
        return new DeleteWBS.DeleteWithReplacement();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyWBSFromTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CopyWBSFromTemplate")
    public JAXBElement<CopyWBSFromTemplate> createCopyWBSFromTemplate(CopyWBSFromTemplate value) {
        return new JAXBElement<CopyWBSFromTemplate>(_CopyWBSFromTemplate_QNAME, CopyWBSFromTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "UpdateWBS")
    public JAXBElement<UpdateWBS> createUpdateWBS(UpdateWBS value) {
        return new JAXBElement<UpdateWBS>(_UpdateWBS_QNAME, UpdateWBS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadAllWBS")
    public JAXBElement<ReadAllWBS> createReadAllWBS(ReadAllWBS value) {
        return new JAXBElement<ReadAllWBS>(_ReadAllWBS_QNAME, ReadAllWBS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadActivityWBSPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadActivityWBSPathResponse")
    public JAXBElement<ReadActivityWBSPathResponse> createReadActivityWBSPathResponse(ReadActivityWBSPathResponse value) {
        return new JAXBElement<ReadActivityWBSPathResponse>(_ReadActivityWBSPathResponse_QNAME, ReadActivityWBSPathResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CreateWBSResponse")
    public JAXBElement<CreateWBSResponse> createCreateWBSResponse(CreateWBSResponse value) {
        return new JAXBElement<CreateWBSResponse>(_CreateWBSResponse_QNAME, CreateWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "getFieldLengthWBS")
    public JAXBElement<GetFieldLengthWBS> createGetFieldLengthWBS(GetFieldLengthWBS value) {
        return new JAXBElement<GetFieldLengthWBS>(_GetFieldLengthWBS_QNAME, GetFieldLengthWBS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadWBSPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadWBSPath")
    public JAXBElement<ReadWBSPath> createReadWBSPath(ReadWBSPath value) {
        return new JAXBElement<ReadWBSPath>(_ReadWBSPath_QNAME, ReadWBSPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "DeleteWBS")
    public JAXBElement<DeleteWBS> createDeleteWBS(DeleteWBS value) {
        return new JAXBElement<DeleteWBS>(_DeleteWBS_QNAME, DeleteWBS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CreateWBS")
    public JAXBElement<CreateWBS> createCreateWBS(CreateWBS value) {
        return new JAXBElement<CreateWBS>(_CreateWBS_QNAME, CreateWBS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFieldLengthWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "getFieldLengthWBSResponse")
    public JAXBElement<GetFieldLengthWBSResponse> createGetFieldLengthWBSResponse(GetFieldLengthWBSResponse value) {
        return new JAXBElement<GetFieldLengthWBSResponse>(_GetFieldLengthWBSResponse_QNAME, GetFieldLengthWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyWBSFromTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CopyWBSFromTemplateResponse")
    public JAXBElement<CopyWBSFromTemplateResponse> createCopyWBSFromTemplateResponse(CopyWBSFromTemplateResponse value) {
        return new JAXBElement<CopyWBSFromTemplateResponse>(_CopyWBSFromTemplateResponse_QNAME, CopyWBSFromTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "UpdateWBSResponse")
    public JAXBElement<UpdateWBSResponse> createUpdateWBSResponse(UpdateWBSResponse value) {
        return new JAXBElement<UpdateWBSResponse>(_UpdateWBSResponse_QNAME, UpdateWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "DeleteWBSResponse")
    public JAXBElement<DeleteWBSResponse> createDeleteWBSResponse(DeleteWBSResponse value) {
        return new JAXBElement<DeleteWBSResponse>(_DeleteWBSResponse_QNAME, DeleteWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadWBSResponse")
    public JAXBElement<ReadWBSResponse> createReadWBSResponse(ReadWBSResponse value) {
        return new JAXBElement<ReadWBSResponse>(_ReadWBSResponse_QNAME, ReadWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadActivityWBSPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadActivityWBSPath")
    public JAXBElement<ReadActivityWBSPath> createReadActivityWBSPath(ReadActivityWBSPath value) {
        return new JAXBElement<ReadActivityWBSPath>(_ReadActivityWBSPath_QNAME, ReadActivityWBSPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadWBSPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadWBSPathResponse")
    public JAXBElement<ReadWBSPathResponse> createReadWBSPathResponse(ReadWBSPathResponse value) {
        return new JAXBElement<ReadWBSPathResponse>(_ReadWBSPathResponse_QNAME, ReadWBSPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadWBS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadWBS")
    public JAXBElement<ReadWBS> createReadWBS(ReadWBS value) {
        return new JAXBElement<ReadWBS>(_ReadWBS_QNAME, ReadWBS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReadAllWBSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ReadAllWBSResponse")
    public JAXBElement<ReadAllWBSResponse> createReadAllWBSResponse(ReadAllWBSResponse value) {
        return new JAXBElement<ReadAllWBSResponse>(_ReadAllWBSResponse_QNAME, ReadAllWBSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionTotalCostVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionTotalCostVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionTotalCostVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionMaterialCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionMaterialCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionMaterialCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualMaterialCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualMaterialCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualMaterialCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryRemainingFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryRemainingFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualThisPeriodLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualThisPeriodLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualThisPeriodLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CurrentVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSCurrentVariance(Double value) {
        return new JAXBElement<Double>(_WBSCurrentVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEstimateAtCompletionByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEstimateAtCompletionByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEstimateAtCompletionByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryTotalFloat", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryTotalFloat(Double value) {
        return new JAXBElement<Double>(_WBSSummaryTotalFloat_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualNonLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualNonLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualTotalCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualTotalCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualTotalCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryUnitsPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryUnitsPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "DistributedCurrentBudget", scope = WBS.class)
    public JAXBElement<Double> createWBSDistributedCurrentBudget(Double value) {
        return new JAXBElement<Double>(_WBSDistributedCurrentBudget_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "AnticipatedFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSAnticipatedFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSAnticipatedFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEarnedValueByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEarnedValueByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEarnedValueByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ProposedBudget", scope = WBS.class)
    public JAXBElement<Double> createWBSProposedBudget(Double value) {
        return new JAXBElement<Double>(_WBSProposedBudget_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "StatusReviewerObjectId", scope = WBS.class)
    public JAXBElement<Integer> createWBSStatusReviewerObjectId(Integer value) {
        return new JAXBElement<Integer>(_WBSStatusReviewerObjectId_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryRemainingStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryRemainingStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryNonLaborUnitsPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryNonLaborUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryNonLaborUnitsPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryPlannedFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryPlannedFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionDuration", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionDuration(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionDuration_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "UndistributedCurrentVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSUndistributedCurrentVariance(Double value) {
        return new JAXBElement<Double>(_WBSUndistributedCurrentVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryExpenseCostVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryExpenseCostVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryExpenseCostVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryProgressFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryProgressFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryProgressFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryMaterialCostVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryMaterialCostVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryMaterialCostVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionNonLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionNonLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionNonLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryInProgressActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryInProgressActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryInProgressActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostVarianceIndexByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostVarianceIndexByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostVarianceIndexByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedExpenseCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedExpenseCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedExpenseCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryScheduleVarianceByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryScheduleVarianceByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryScheduleVarianceByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "EarnedValueUserPercent", scope = WBS.class)
    public JAXBElement<Double> createWBSEarnedValueUserPercent(Double value) {
        return new JAXBElement<Double>(_WBSEarnedValueUserPercent_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualValueByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualValueByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualValueByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualThisPeriodCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualThisPeriodCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualThisPeriodCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "AnticipatedStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSAnticipatedStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSAnticipatedStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryDurationPercentOfPlanned", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryDurationPercentOfPlanned(Double value) {
        return new JAXBElement<Double>(_WBSSummaryDurationPercentOfPlanned_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCompletedActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryCompletedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryCompletedActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummarySchedulePerformanceIndexByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummarySchedulePerformanceIndexByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummarySchedulePerformanceIndexByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryNonLaborUnitsVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryNonLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryNonLaborUnitsVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryDurationPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryDurationPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryDurationPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CurrentBudget", scope = WBS.class)
    public JAXBElement<Double> createWBSCurrentBudget(Double value) {
        return new JAXBElement<Double>(_WBSCurrentBudget_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "IndependentETCLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSIndependentETCLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSIndependentETCLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryNotStartedActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryNotStartedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryNotStartedActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualNonLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualNonLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualNonLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualThisPeriodNonLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualThisPeriodNonLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualThisPeriodNonLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummarySchedulePercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummarySchedulePercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummarySchedulePercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingTotalCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingTotalCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingTotalCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualThisPeriodMaterialCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualThisPeriodMaterialCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualThisPeriodMaterialCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionTotalCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionTotalCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionTotalCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "IndependentETCTotalCost", scope = WBS.class)
    public JAXBElement<Double> createWBSIndependentETCTotalCost(Double value) {
        return new JAXBElement<Double>(_WBSIndependentETCTotalCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ForecastStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSForecastStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSForecastStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineCompletedActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryBaselineCompletedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryBaselineCompletedActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualDuration", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualDuration(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualDuration_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingDuration", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingDuration(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingDuration_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "StartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostPerformanceIndexByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostPerformanceIndexByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostPerformanceIndexByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "EarnedValueETCUserValue", scope = WBS.class)
    public JAXBElement<Double> createWBSEarnedValueETCUserValue(Double value) {
        return new JAXBElement<Double>(_WBSEarnedValueETCUserValue_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostVarianceIndexByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostVarianceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostVarianceIndexByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineTotalCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineTotalCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineTotalCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "WBSCategoryObjectId", scope = WBS.class)
    public JAXBElement<Integer> createWBSWBSCategoryObjectId(Integer value) {
        return new JAXBElement<Integer>(_WBSWBSCategoryObjectId_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryActualFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryActualFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineInProgressActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryBaselineInProgressActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryBaselineInProgressActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAccountingVarianceByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAccountingVarianceByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAccountingVarianceByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineNonLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineNonLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "FinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostVarianceIndex", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostVarianceIndex(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostVarianceIndex_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryLaborCostVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryLaborCostVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryLaborCostVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualThisPeriodLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualThisPeriodLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualThisPeriodLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryExpenseCostPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryExpenseCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryExpenseCostPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "TotalSpendingPlan", scope = WBS.class)
    public JAXBElement<Double> createWBSTotalSpendingPlan(Double value) {
        return new JAXBElement<Double>(_WBSTotalSpendingPlan_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedNonLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedNonLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedNonLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryStartDateVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryStartDateVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryStartDateVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedDuration", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedDuration(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedDuration_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "OriginalBudget", scope = WBS.class)
    public JAXBElement<Double> createWBSOriginalBudget(Double value) {
        return new JAXBElement<Double>(_WBSOriginalBudget_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryScheduleVarianceIndex", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryScheduleVarianceIndex(Double value) {
        return new JAXBElement<Double>(_WBSSummaryScheduleVarianceIndex_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ForecastFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSForecastFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSForecastFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionNonLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionNonLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostPerformanceIndexByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostPerformanceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostPerformanceIndexByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryNonLaborCostVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryNonLaborCostVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryNonLaborCostVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualExpenseCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualExpenseCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualExpenseCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBudgetAtCompletionByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBudgetAtCompletionByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBudgetAtCompletionByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "UnallocatedBudget", scope = WBS.class)
    public JAXBElement<Double> createWBSUnallocatedBudget(Double value) {
        return new JAXBElement<Double>(_WBSUnallocatedBudget_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostVarianceByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostVarianceByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostVarianceByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryPlannedStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryPlannedStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostPercentOfPlanned", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostPercentOfPlanned(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostPercentOfPlanned_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryVarianceAtCompletionByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryVarianceAtCompletionByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryVarianceAtCompletionByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualThisPeriodNonLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualThisPeriodNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualThisPeriodNonLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "RolledUpStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSRolledUpStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSRolledUpStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineExpenseCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineExpenseCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineExpenseCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedValueByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedValueByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedValueByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummarySchedulePerformanceIndexByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummarySchedulePerformanceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummarySchedulePerformanceIndexByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualValueByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualValueByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualValueByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "RolledUpFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSRolledUpFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSRolledUpFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBudgetAtCompletionByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBudgetAtCompletionByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBudgetAtCompletionByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEstimateToCompleteByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEstimateToCompleteByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEstimateToCompleteByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedMaterialCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedMaterialCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedMaterialCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionExpenseCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionExpenseCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionExpenseCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryToCompletePerformanceIndexByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryToCompletePerformanceIndexByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryToCompletePerformanceIndexByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEstimateAtCompletionByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEstimateAtCompletionByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEstimateAtCompletionByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEarnedValueByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEarnedValueByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEarnedValueByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostVarianceByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostVarianceByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostVarianceByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryScheduleVarianceIndexByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryScheduleVarianceIndexByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryScheduleVarianceIndexByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAtCompletionLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAtCompletionLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAtCompletionLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEstimateToCompleteByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEstimateToCompleteByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEstimateToCompleteByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingMaterialCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingMaterialCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingMaterialCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryMaterialCostPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryMaterialCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryMaterialCostPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryBaselineStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryBaselineStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEstimateAtCompletionHighPercentByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEstimateAtCompletionHighPercentByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEstimateAtCompletionHighPercentByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryCostPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryCostPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryFinishDateVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryFinishDateVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryFinishDateVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineNotStartedActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryBaselineNotStartedActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryBaselineNotStartedActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedNonLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedNonLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineMaterialCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineMaterialCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineMaterialCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryLaborUnitsVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryLaborUnitsVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryLaborUnitsVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingNonLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingNonLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingNonLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryNonLaborCostPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryNonLaborCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryNonLaborCostPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "TotalBenefitPlan", scope = WBS.class)
    public JAXBElement<Double> createWBSTotalBenefitPlan(Double value) {
        return new JAXBElement<Double>(_WBSTotalBenefitPlan_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummarySchedulePercentCompleteByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummarySchedulePercentCompleteByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummarySchedulePercentCompleteByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "ParentObjectId", scope = WBS.class)
    public JAXBElement<Integer> createWBSParentObjectId(Integer value) {
        return new JAXBElement<Integer>(_WBSParentObjectId_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActivityCount", scope = WBS.class)
    public JAXBElement<Integer> createWBSSummaryActivityCount(Integer value) {
        return new JAXBElement<Integer>(_WBSSummaryActivityCount_QNAME, Integer.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineDuration", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineDuration(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineDuration_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "TotalBenefitPlanTally", scope = WBS.class)
    public JAXBElement<Double> createWBSTotalBenefitPlanTally(Double value) {
        return new JAXBElement<Double>(_WBSTotalBenefitPlanTally_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryActualLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryActualLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "WBSMilestonePercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSWBSMilestonePercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSWBSMilestonePercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedValueByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedValueByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedValueByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryScheduleVarianceIndexByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryScheduleVarianceIndexByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryScheduleVarianceIndexByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPerformancePercentCompleteByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPerformancePercentCompleteByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPerformancePercentCompleteByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryTotalCostVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryTotalCostVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryTotalCostVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryAccountingVarianceByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryAccountingVarianceByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryAccountingVarianceByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineFinishDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryBaselineFinishDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryBaselineFinishDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "CreateDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSCreateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSCreateDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingNonLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingNonLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingNonLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryLaborUnitsPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryLaborUnitsPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryLaborUnitsPercentComplete_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryRemainingExpenseCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryRemainingExpenseCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryRemainingExpenseCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryScheduleVarianceByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryScheduleVarianceByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryScheduleVarianceByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPlannedLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPlannedLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPlannedLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryBaselineNonLaborCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryBaselineNonLaborCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryBaselineNonLaborCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryActualStartDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSSummaryActualStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSSummaryActualStartDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "LastUpdateDate", scope = WBS.class)
    public JAXBElement<XMLGregorianCalendar> createWBSLastUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_WBSLastUpdateDate_QNAME, XMLGregorianCalendar.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "TotalSpendingPlanTally", scope = WBS.class)
    public JAXBElement<Double> createWBSTotalSpendingPlanTally(Double value) {
        return new JAXBElement<Double>(_WBSTotalSpendingPlanTally_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryDurationVariance", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryDurationVariance(Double value) {
        return new JAXBElement<Double>(_WBSSummaryDurationVariance_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryPerformancePercentCompleteByCost", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryPerformancePercentCompleteByCost(Double value) {
        return new JAXBElement<Double>(_WBSSummaryPerformancePercentCompleteByCost_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryEstimateAtCompletionLowPercentByLaborUnits", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryEstimateAtCompletionLowPercentByLaborUnits(Double value) {
        return new JAXBElement<Double>(_WBSSummaryEstimateAtCompletionLowPercentByLaborUnits_QNAME, Double.class, WBS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", name = "SummaryLaborCostPercentComplete", scope = WBS.class)
    public JAXBElement<Double> createWBSSummaryLaborCostPercentComplete(Double value) {
        return new JAXBElement<Double>(_WBSSummaryLaborCostPercentComplete_QNAME, Double.class, WBS.class, value);
    }

}
