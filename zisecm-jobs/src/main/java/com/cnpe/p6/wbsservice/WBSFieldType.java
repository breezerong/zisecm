
package com.cnpe.p6.wbsservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>WBSFieldType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="WBSFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AnticipatedFinishDate"/>
 *     &lt;enumeration value="AnticipatedStartDate"/>
 *     &lt;enumeration value="Code"/>
 *     &lt;enumeration value="ContainsSummaryData"/>
 *     &lt;enumeration value="CreateDate"/>
 *     &lt;enumeration value="CreateUser"/>
 *     &lt;enumeration value="CurrentBudget"/>
 *     &lt;enumeration value="CurrentVariance"/>
 *     &lt;enumeration value="DistributedCurrentBudget"/>
 *     &lt;enumeration value="EarnedValueComputeType"/>
 *     &lt;enumeration value="EarnedValueETCComputeType"/>
 *     &lt;enumeration value="EarnedValueETCUserValue"/>
 *     &lt;enumeration value="EarnedValueUserPercent"/>
 *     &lt;enumeration value="FinishDate"/>
 *     &lt;enumeration value="ForecastFinishDate"/>
 *     &lt;enumeration value="ForecastStartDate"/>
 *     &lt;enumeration value="GUID"/>
 *     &lt;enumeration value="IndependentETCLaborUnits"/>
 *     &lt;enumeration value="IndependentETCTotalCost"/>
 *     &lt;enumeration value="IntegratedType"/>
 *     &lt;enumeration value="IntegratedWBS"/>
 *     &lt;enumeration value="IsBaseline"/>
 *     &lt;enumeration value="IsTemplate"/>
 *     &lt;enumeration value="IsWorkPackage"/>
 *     &lt;enumeration value="LastUpdateDate"/>
 *     &lt;enumeration value="LastUpdateUser"/>
 *     &lt;enumeration value="Name"/>
 *     &lt;enumeration value="OBSName"/>
 *     &lt;enumeration value="OBSObjectId"/>
 *     &lt;enumeration value="ObjectId"/>
 *     &lt;enumeration value="OriginalBudget"/>
 *     &lt;enumeration value="ParentObjectId"/>
 *     &lt;enumeration value="ProjectId"/>
 *     &lt;enumeration value="ProjectObjectId"/>
 *     &lt;enumeration value="ProposedBudget"/>
 *     &lt;enumeration value="RolledUpFinishDate"/>
 *     &lt;enumeration value="RolledUpStartDate"/>
 *     &lt;enumeration value="SequenceNumber"/>
 *     &lt;enumeration value="StartDate"/>
 *     &lt;enumeration value="Status"/>
 *     &lt;enumeration value="StatusReviewerName"/>
 *     &lt;enumeration value="StatusReviewerObjectId"/>
 *     &lt;enumeration value="SummaryAccountingVarianceByCost"/>
 *     &lt;enumeration value="SummaryAccountingVarianceByLaborUnits"/>
 *     &lt;enumeration value="SummaryActivityCount"/>
 *     &lt;enumeration value="SummaryActualDuration"/>
 *     &lt;enumeration value="SummaryActualExpenseCost"/>
 *     &lt;enumeration value="SummaryActualFinishDate"/>
 *     &lt;enumeration value="SummaryActualLaborCost"/>
 *     &lt;enumeration value="SummaryActualLaborUnits"/>
 *     &lt;enumeration value="SummaryActualMaterialCost"/>
 *     &lt;enumeration value="SummaryActualNonLaborCost"/>
 *     &lt;enumeration value="SummaryActualNonLaborUnits"/>
 *     &lt;enumeration value="SummaryActualStartDate"/>
 *     &lt;enumeration value="SummaryActualThisPeriodCost"/>
 *     &lt;enumeration value="SummaryActualThisPeriodLaborCost"/>
 *     &lt;enumeration value="SummaryActualThisPeriodLaborUnits"/>
 *     &lt;enumeration value="SummaryActualThisPeriodMaterialCost"/>
 *     &lt;enumeration value="SummaryActualThisPeriodNonLaborCost"/>
 *     &lt;enumeration value="SummaryActualThisPeriodNonLaborUnits"/>
 *     &lt;enumeration value="SummaryActualTotalCost"/>
 *     &lt;enumeration value="SummaryActualValueByCost"/>
 *     &lt;enumeration value="SummaryActualValueByLaborUnits"/>
 *     &lt;enumeration value="SummaryAtCompletionDuration"/>
 *     &lt;enumeration value="SummaryAtCompletionExpenseCost"/>
 *     &lt;enumeration value="SummaryAtCompletionLaborCost"/>
 *     &lt;enumeration value="SummaryAtCompletionLaborUnits"/>
 *     &lt;enumeration value="SummaryAtCompletionMaterialCost"/>
 *     &lt;enumeration value="SummaryAtCompletionNonLaborCost"/>
 *     &lt;enumeration value="SummaryAtCompletionNonLaborUnits"/>
 *     &lt;enumeration value="SummaryAtCompletionTotalCost"/>
 *     &lt;enumeration value="SummaryAtCompletionTotalCostVariance"/>
 *     &lt;enumeration value="SummaryBaselineCompletedActivityCount"/>
 *     &lt;enumeration value="SummaryBaselineDuration"/>
 *     &lt;enumeration value="SummaryBaselineExpenseCost"/>
 *     &lt;enumeration value="SummaryBaselineFinishDate"/>
 *     &lt;enumeration value="SummaryBaselineInProgressActivityCount"/>
 *     &lt;enumeration value="SummaryBaselineLaborCost"/>
 *     &lt;enumeration value="SummaryBaselineLaborUnits"/>
 *     &lt;enumeration value="SummaryBaselineMaterialCost"/>
 *     &lt;enumeration value="SummaryBaselineNonLaborCost"/>
 *     &lt;enumeration value="SummaryBaselineNonLaborUnits"/>
 *     &lt;enumeration value="SummaryBaselineNotStartedActivityCount"/>
 *     &lt;enumeration value="SummaryBaselineStartDate"/>
 *     &lt;enumeration value="SummaryBaselineTotalCost"/>
 *     &lt;enumeration value="SummaryBudgetAtCompletionByCost"/>
 *     &lt;enumeration value="SummaryBudgetAtCompletionByLaborUnits"/>
 *     &lt;enumeration value="SummaryCompletedActivityCount"/>
 *     &lt;enumeration value="SummaryCostPercentComplete"/>
 *     &lt;enumeration value="SummaryCostPercentOfPlanned"/>
 *     &lt;enumeration value="SummaryCostPerformanceIndexByCost"/>
 *     &lt;enumeration value="SummaryCostPerformanceIndexByLaborUnits"/>
 *     &lt;enumeration value="SummaryCostVarianceByCost"/>
 *     &lt;enumeration value="SummaryCostVarianceByLaborUnits"/>
 *     &lt;enumeration value="SummaryCostVarianceIndex"/>
 *     &lt;enumeration value="SummaryCostVarianceIndexByCost"/>
 *     &lt;enumeration value="SummaryCostVarianceIndexByLaborUnits"/>
 *     &lt;enumeration value="SummaryDurationPercentComplete"/>
 *     &lt;enumeration value="SummaryDurationPercentOfPlanned"/>
 *     &lt;enumeration value="SummaryDurationVariance"/>
 *     &lt;enumeration value="SummaryEarnedValueByCost"/>
 *     &lt;enumeration value="SummaryEarnedValueByLaborUnits"/>
 *     &lt;enumeration value="SummaryEstimateAtCompletionByCost"/>
 *     &lt;enumeration value="SummaryEstimateAtCompletionByLaborUnits"/>
 *     &lt;enumeration value="SummaryEstimateAtCompletionHighPercentByLaborUnits"/>
 *     &lt;enumeration value="SummaryEstimateAtCompletionLowPercentByLaborUnits"/>
 *     &lt;enumeration value="SummaryEstimateToCompleteByCost"/>
 *     &lt;enumeration value="SummaryEstimateToCompleteByLaborUnits"/>
 *     &lt;enumeration value="SummaryExpenseCostPercentComplete"/>
 *     &lt;enumeration value="SummaryExpenseCostVariance"/>
 *     &lt;enumeration value="SummaryFinishDateVariance"/>
 *     &lt;enumeration value="SummaryInProgressActivityCount"/>
 *     &lt;enumeration value="SummaryLaborCostPercentComplete"/>
 *     &lt;enumeration value="SummaryLaborCostVariance"/>
 *     &lt;enumeration value="SummaryLaborUnitsPercentComplete"/>
 *     &lt;enumeration value="SummaryLaborUnitsVariance"/>
 *     &lt;enumeration value="SummaryMaterialCostPercentComplete"/>
 *     &lt;enumeration value="SummaryMaterialCostVariance"/>
 *     &lt;enumeration value="SummaryNonLaborCostPercentComplete"/>
 *     &lt;enumeration value="SummaryNonLaborCostVariance"/>
 *     &lt;enumeration value="SummaryNonLaborUnitsPercentComplete"/>
 *     &lt;enumeration value="SummaryNonLaborUnitsVariance"/>
 *     &lt;enumeration value="SummaryNotStartedActivityCount"/>
 *     &lt;enumeration value="SummaryPerformancePercentCompleteByCost"/>
 *     &lt;enumeration value="SummaryPerformancePercentCompleteByLaborUnits"/>
 *     &lt;enumeration value="SummaryPlannedCost"/>
 *     &lt;enumeration value="SummaryPlannedDuration"/>
 *     &lt;enumeration value="SummaryPlannedExpenseCost"/>
 *     &lt;enumeration value="SummaryPlannedFinishDate"/>
 *     &lt;enumeration value="SummaryPlannedLaborCost"/>
 *     &lt;enumeration value="SummaryPlannedLaborUnits"/>
 *     &lt;enumeration value="SummaryPlannedMaterialCost"/>
 *     &lt;enumeration value="SummaryPlannedNonLaborCost"/>
 *     &lt;enumeration value="SummaryPlannedNonLaborUnits"/>
 *     &lt;enumeration value="SummaryPlannedStartDate"/>
 *     &lt;enumeration value="SummaryPlannedValueByCost"/>
 *     &lt;enumeration value="SummaryPlannedValueByLaborUnits"/>
 *     &lt;enumeration value="SummaryProgressFinishDate"/>
 *     &lt;enumeration value="SummaryRemainingDuration"/>
 *     &lt;enumeration value="SummaryRemainingExpenseCost"/>
 *     &lt;enumeration value="SummaryRemainingFinishDate"/>
 *     &lt;enumeration value="SummaryRemainingLaborCost"/>
 *     &lt;enumeration value="SummaryRemainingLaborUnits"/>
 *     &lt;enumeration value="SummaryRemainingMaterialCost"/>
 *     &lt;enumeration value="SummaryRemainingNonLaborCost"/>
 *     &lt;enumeration value="SummaryRemainingNonLaborUnits"/>
 *     &lt;enumeration value="SummaryRemainingStartDate"/>
 *     &lt;enumeration value="SummaryRemainingTotalCost"/>
 *     &lt;enumeration value="SummarySchedulePercentComplete"/>
 *     &lt;enumeration value="SummarySchedulePercentCompleteByLaborUnits"/>
 *     &lt;enumeration value="SummarySchedulePerformanceIndexByCost"/>
 *     &lt;enumeration value="SummarySchedulePerformanceIndexByLaborUnits"/>
 *     &lt;enumeration value="SummaryScheduleVarianceByCost"/>
 *     &lt;enumeration value="SummaryScheduleVarianceByLaborUnits"/>
 *     &lt;enumeration value="SummaryScheduleVarianceIndex"/>
 *     &lt;enumeration value="SummaryScheduleVarianceIndexByCost"/>
 *     &lt;enumeration value="SummaryScheduleVarianceIndexByLaborUnits"/>
 *     &lt;enumeration value="SummaryStartDateVariance"/>
 *     &lt;enumeration value="SummaryToCompletePerformanceIndexByCost"/>
 *     &lt;enumeration value="SummaryTotalCostVariance"/>
 *     &lt;enumeration value="SummaryTotalFloat"/>
 *     &lt;enumeration value="SummaryUnitsPercentComplete"/>
 *     &lt;enumeration value="SummaryVarianceAtCompletionByLaborUnits"/>
 *     &lt;enumeration value="TotalBenefitPlan"/>
 *     &lt;enumeration value="TotalBenefitPlanTally"/>
 *     &lt;enumeration value="TotalSpendingPlan"/>
 *     &lt;enumeration value="TotalSpendingPlanTally"/>
 *     &lt;enumeration value="UnallocatedBudget"/>
 *     &lt;enumeration value="UndistributedCurrentVariance"/>
 *     &lt;enumeration value="WBSCategoryObjectId"/>
 *     &lt;enumeration value="WBSMilestonePercentComplete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WBSFieldType")
@XmlEnum
public enum WBSFieldType {

    @XmlEnumValue("AnticipatedFinishDate")
    ANTICIPATED_FINISH_DATE("AnticipatedFinishDate"),
    @XmlEnumValue("AnticipatedStartDate")
    ANTICIPATED_START_DATE("AnticipatedStartDate"),
    @XmlEnumValue("Code")
    CODE("Code"),
    @XmlEnumValue("ContainsSummaryData")
    CONTAINS_SUMMARY_DATA("ContainsSummaryData"),
    @XmlEnumValue("CreateDate")
    CREATE_DATE("CreateDate"),
    @XmlEnumValue("CreateUser")
    CREATE_USER("CreateUser"),
    @XmlEnumValue("CurrentBudget")
    CURRENT_BUDGET("CurrentBudget"),
    @XmlEnumValue("CurrentVariance")
    CURRENT_VARIANCE("CurrentVariance"),
    @XmlEnumValue("DistributedCurrentBudget")
    DISTRIBUTED_CURRENT_BUDGET("DistributedCurrentBudget"),
    @XmlEnumValue("EarnedValueComputeType")
    EARNED_VALUE_COMPUTE_TYPE("EarnedValueComputeType"),
    @XmlEnumValue("EarnedValueETCComputeType")
    EARNED_VALUE_ETC_COMPUTE_TYPE("EarnedValueETCComputeType"),
    @XmlEnumValue("EarnedValueETCUserValue")
    EARNED_VALUE_ETC_USER_VALUE("EarnedValueETCUserValue"),
    @XmlEnumValue("EarnedValueUserPercent")
    EARNED_VALUE_USER_PERCENT("EarnedValueUserPercent"),
    @XmlEnumValue("FinishDate")
    FINISH_DATE("FinishDate"),
    @XmlEnumValue("ForecastFinishDate")
    FORECAST_FINISH_DATE("ForecastFinishDate"),
    @XmlEnumValue("ForecastStartDate")
    FORECAST_START_DATE("ForecastStartDate"),
    GUID("GUID"),
    @XmlEnumValue("IndependentETCLaborUnits")
    INDEPENDENT_ETC_LABOR_UNITS("IndependentETCLaborUnits"),
    @XmlEnumValue("IndependentETCTotalCost")
    INDEPENDENT_ETC_TOTAL_COST("IndependentETCTotalCost"),
    @XmlEnumValue("IntegratedType")
    INTEGRATED_TYPE("IntegratedType"),
    @XmlEnumValue("IntegratedWBS")
    INTEGRATED_WBS("IntegratedWBS"),
    @XmlEnumValue("IsBaseline")
    IS_BASELINE("IsBaseline"),
    @XmlEnumValue("IsTemplate")
    IS_TEMPLATE("IsTemplate"),
    @XmlEnumValue("IsWorkPackage")
    IS_WORK_PACKAGE("IsWorkPackage"),
    @XmlEnumValue("LastUpdateDate")
    LAST_UPDATE_DATE("LastUpdateDate"),
    @XmlEnumValue("LastUpdateUser")
    LAST_UPDATE_USER("LastUpdateUser"),
    @XmlEnumValue("Name")
    NAME("Name"),
    @XmlEnumValue("OBSName")
    OBS_NAME("OBSName"),
    @XmlEnumValue("OBSObjectId")
    OBS_OBJECT_ID("OBSObjectId"),
    @XmlEnumValue("ObjectId")
    OBJECT_ID("ObjectId"),
    @XmlEnumValue("OriginalBudget")
    ORIGINAL_BUDGET("OriginalBudget"),
    @XmlEnumValue("ParentObjectId")
    PARENT_OBJECT_ID("ParentObjectId"),
    @XmlEnumValue("ProjectId")
    PROJECT_ID("ProjectId"),
    @XmlEnumValue("ProjectObjectId")
    PROJECT_OBJECT_ID("ProjectObjectId"),
    @XmlEnumValue("ProposedBudget")
    PROPOSED_BUDGET("ProposedBudget"),
    @XmlEnumValue("RolledUpFinishDate")
    ROLLED_UP_FINISH_DATE("RolledUpFinishDate"),
    @XmlEnumValue("RolledUpStartDate")
    ROLLED_UP_START_DATE("RolledUpStartDate"),
    @XmlEnumValue("SequenceNumber")
    SEQUENCE_NUMBER("SequenceNumber"),
    @XmlEnumValue("StartDate")
    START_DATE("StartDate"),
    @XmlEnumValue("Status")
    STATUS("Status"),
    @XmlEnumValue("StatusReviewerName")
    STATUS_REVIEWER_NAME("StatusReviewerName"),
    @XmlEnumValue("StatusReviewerObjectId")
    STATUS_REVIEWER_OBJECT_ID("StatusReviewerObjectId"),
    @XmlEnumValue("SummaryAccountingVarianceByCost")
    SUMMARY_ACCOUNTING_VARIANCE_BY_COST("SummaryAccountingVarianceByCost"),
    @XmlEnumValue("SummaryAccountingVarianceByLaborUnits")
    SUMMARY_ACCOUNTING_VARIANCE_BY_LABOR_UNITS("SummaryAccountingVarianceByLaborUnits"),
    @XmlEnumValue("SummaryActivityCount")
    SUMMARY_ACTIVITY_COUNT("SummaryActivityCount"),
    @XmlEnumValue("SummaryActualDuration")
    SUMMARY_ACTUAL_DURATION("SummaryActualDuration"),
    @XmlEnumValue("SummaryActualExpenseCost")
    SUMMARY_ACTUAL_EXPENSE_COST("SummaryActualExpenseCost"),
    @XmlEnumValue("SummaryActualFinishDate")
    SUMMARY_ACTUAL_FINISH_DATE("SummaryActualFinishDate"),
    @XmlEnumValue("SummaryActualLaborCost")
    SUMMARY_ACTUAL_LABOR_COST("SummaryActualLaborCost"),
    @XmlEnumValue("SummaryActualLaborUnits")
    SUMMARY_ACTUAL_LABOR_UNITS("SummaryActualLaborUnits"),
    @XmlEnumValue("SummaryActualMaterialCost")
    SUMMARY_ACTUAL_MATERIAL_COST("SummaryActualMaterialCost"),
    @XmlEnumValue("SummaryActualNonLaborCost")
    SUMMARY_ACTUAL_NON_LABOR_COST("SummaryActualNonLaborCost"),
    @XmlEnumValue("SummaryActualNonLaborUnits")
    SUMMARY_ACTUAL_NON_LABOR_UNITS("SummaryActualNonLaborUnits"),
    @XmlEnumValue("SummaryActualStartDate")
    SUMMARY_ACTUAL_START_DATE("SummaryActualStartDate"),
    @XmlEnumValue("SummaryActualThisPeriodCost")
    SUMMARY_ACTUAL_THIS_PERIOD_COST("SummaryActualThisPeriodCost"),
    @XmlEnumValue("SummaryActualThisPeriodLaborCost")
    SUMMARY_ACTUAL_THIS_PERIOD_LABOR_COST("SummaryActualThisPeriodLaborCost"),
    @XmlEnumValue("SummaryActualThisPeriodLaborUnits")
    SUMMARY_ACTUAL_THIS_PERIOD_LABOR_UNITS("SummaryActualThisPeriodLaborUnits"),
    @XmlEnumValue("SummaryActualThisPeriodMaterialCost")
    SUMMARY_ACTUAL_THIS_PERIOD_MATERIAL_COST("SummaryActualThisPeriodMaterialCost"),
    @XmlEnumValue("SummaryActualThisPeriodNonLaborCost")
    SUMMARY_ACTUAL_THIS_PERIOD_NON_LABOR_COST("SummaryActualThisPeriodNonLaborCost"),
    @XmlEnumValue("SummaryActualThisPeriodNonLaborUnits")
    SUMMARY_ACTUAL_THIS_PERIOD_NON_LABOR_UNITS("SummaryActualThisPeriodNonLaborUnits"),
    @XmlEnumValue("SummaryActualTotalCost")
    SUMMARY_ACTUAL_TOTAL_COST("SummaryActualTotalCost"),
    @XmlEnumValue("SummaryActualValueByCost")
    SUMMARY_ACTUAL_VALUE_BY_COST("SummaryActualValueByCost"),
    @XmlEnumValue("SummaryActualValueByLaborUnits")
    SUMMARY_ACTUAL_VALUE_BY_LABOR_UNITS("SummaryActualValueByLaborUnits"),
    @XmlEnumValue("SummaryAtCompletionDuration")
    SUMMARY_AT_COMPLETION_DURATION("SummaryAtCompletionDuration"),
    @XmlEnumValue("SummaryAtCompletionExpenseCost")
    SUMMARY_AT_COMPLETION_EXPENSE_COST("SummaryAtCompletionExpenseCost"),
    @XmlEnumValue("SummaryAtCompletionLaborCost")
    SUMMARY_AT_COMPLETION_LABOR_COST("SummaryAtCompletionLaborCost"),
    @XmlEnumValue("SummaryAtCompletionLaborUnits")
    SUMMARY_AT_COMPLETION_LABOR_UNITS("SummaryAtCompletionLaborUnits"),
    @XmlEnumValue("SummaryAtCompletionMaterialCost")
    SUMMARY_AT_COMPLETION_MATERIAL_COST("SummaryAtCompletionMaterialCost"),
    @XmlEnumValue("SummaryAtCompletionNonLaborCost")
    SUMMARY_AT_COMPLETION_NON_LABOR_COST("SummaryAtCompletionNonLaborCost"),
    @XmlEnumValue("SummaryAtCompletionNonLaborUnits")
    SUMMARY_AT_COMPLETION_NON_LABOR_UNITS("SummaryAtCompletionNonLaborUnits"),
    @XmlEnumValue("SummaryAtCompletionTotalCost")
    SUMMARY_AT_COMPLETION_TOTAL_COST("SummaryAtCompletionTotalCost"),
    @XmlEnumValue("SummaryAtCompletionTotalCostVariance")
    SUMMARY_AT_COMPLETION_TOTAL_COST_VARIANCE("SummaryAtCompletionTotalCostVariance"),
    @XmlEnumValue("SummaryBaselineCompletedActivityCount")
    SUMMARY_BASELINE_COMPLETED_ACTIVITY_COUNT("SummaryBaselineCompletedActivityCount"),
    @XmlEnumValue("SummaryBaselineDuration")
    SUMMARY_BASELINE_DURATION("SummaryBaselineDuration"),
    @XmlEnumValue("SummaryBaselineExpenseCost")
    SUMMARY_BASELINE_EXPENSE_COST("SummaryBaselineExpenseCost"),
    @XmlEnumValue("SummaryBaselineFinishDate")
    SUMMARY_BASELINE_FINISH_DATE("SummaryBaselineFinishDate"),
    @XmlEnumValue("SummaryBaselineInProgressActivityCount")
    SUMMARY_BASELINE_IN_PROGRESS_ACTIVITY_COUNT("SummaryBaselineInProgressActivityCount"),
    @XmlEnumValue("SummaryBaselineLaborCost")
    SUMMARY_BASELINE_LABOR_COST("SummaryBaselineLaborCost"),
    @XmlEnumValue("SummaryBaselineLaborUnits")
    SUMMARY_BASELINE_LABOR_UNITS("SummaryBaselineLaborUnits"),
    @XmlEnumValue("SummaryBaselineMaterialCost")
    SUMMARY_BASELINE_MATERIAL_COST("SummaryBaselineMaterialCost"),
    @XmlEnumValue("SummaryBaselineNonLaborCost")
    SUMMARY_BASELINE_NON_LABOR_COST("SummaryBaselineNonLaborCost"),
    @XmlEnumValue("SummaryBaselineNonLaborUnits")
    SUMMARY_BASELINE_NON_LABOR_UNITS("SummaryBaselineNonLaborUnits"),
    @XmlEnumValue("SummaryBaselineNotStartedActivityCount")
    SUMMARY_BASELINE_NOT_STARTED_ACTIVITY_COUNT("SummaryBaselineNotStartedActivityCount"),
    @XmlEnumValue("SummaryBaselineStartDate")
    SUMMARY_BASELINE_START_DATE("SummaryBaselineStartDate"),
    @XmlEnumValue("SummaryBaselineTotalCost")
    SUMMARY_BASELINE_TOTAL_COST("SummaryBaselineTotalCost"),
    @XmlEnumValue("SummaryBudgetAtCompletionByCost")
    SUMMARY_BUDGET_AT_COMPLETION_BY_COST("SummaryBudgetAtCompletionByCost"),
    @XmlEnumValue("SummaryBudgetAtCompletionByLaborUnits")
    SUMMARY_BUDGET_AT_COMPLETION_BY_LABOR_UNITS("SummaryBudgetAtCompletionByLaborUnits"),
    @XmlEnumValue("SummaryCompletedActivityCount")
    SUMMARY_COMPLETED_ACTIVITY_COUNT("SummaryCompletedActivityCount"),
    @XmlEnumValue("SummaryCostPercentComplete")
    SUMMARY_COST_PERCENT_COMPLETE("SummaryCostPercentComplete"),
    @XmlEnumValue("SummaryCostPercentOfPlanned")
    SUMMARY_COST_PERCENT_OF_PLANNED("SummaryCostPercentOfPlanned"),
    @XmlEnumValue("SummaryCostPerformanceIndexByCost")
    SUMMARY_COST_PERFORMANCE_INDEX_BY_COST("SummaryCostPerformanceIndexByCost"),
    @XmlEnumValue("SummaryCostPerformanceIndexByLaborUnits")
    SUMMARY_COST_PERFORMANCE_INDEX_BY_LABOR_UNITS("SummaryCostPerformanceIndexByLaborUnits"),
    @XmlEnumValue("SummaryCostVarianceByCost")
    SUMMARY_COST_VARIANCE_BY_COST("SummaryCostVarianceByCost"),
    @XmlEnumValue("SummaryCostVarianceByLaborUnits")
    SUMMARY_COST_VARIANCE_BY_LABOR_UNITS("SummaryCostVarianceByLaborUnits"),
    @XmlEnumValue("SummaryCostVarianceIndex")
    SUMMARY_COST_VARIANCE_INDEX("SummaryCostVarianceIndex"),
    @XmlEnumValue("SummaryCostVarianceIndexByCost")
    SUMMARY_COST_VARIANCE_INDEX_BY_COST("SummaryCostVarianceIndexByCost"),
    @XmlEnumValue("SummaryCostVarianceIndexByLaborUnits")
    SUMMARY_COST_VARIANCE_INDEX_BY_LABOR_UNITS("SummaryCostVarianceIndexByLaborUnits"),
    @XmlEnumValue("SummaryDurationPercentComplete")
    SUMMARY_DURATION_PERCENT_COMPLETE("SummaryDurationPercentComplete"),
    @XmlEnumValue("SummaryDurationPercentOfPlanned")
    SUMMARY_DURATION_PERCENT_OF_PLANNED("SummaryDurationPercentOfPlanned"),
    @XmlEnumValue("SummaryDurationVariance")
    SUMMARY_DURATION_VARIANCE("SummaryDurationVariance"),
    @XmlEnumValue("SummaryEarnedValueByCost")
    SUMMARY_EARNED_VALUE_BY_COST("SummaryEarnedValueByCost"),
    @XmlEnumValue("SummaryEarnedValueByLaborUnits")
    SUMMARY_EARNED_VALUE_BY_LABOR_UNITS("SummaryEarnedValueByLaborUnits"),
    @XmlEnumValue("SummaryEstimateAtCompletionByCost")
    SUMMARY_ESTIMATE_AT_COMPLETION_BY_COST("SummaryEstimateAtCompletionByCost"),
    @XmlEnumValue("SummaryEstimateAtCompletionByLaborUnits")
    SUMMARY_ESTIMATE_AT_COMPLETION_BY_LABOR_UNITS("SummaryEstimateAtCompletionByLaborUnits"),
    @XmlEnumValue("SummaryEstimateAtCompletionHighPercentByLaborUnits")
    SUMMARY_ESTIMATE_AT_COMPLETION_HIGH_PERCENT_BY_LABOR_UNITS("SummaryEstimateAtCompletionHighPercentByLaborUnits"),
    @XmlEnumValue("SummaryEstimateAtCompletionLowPercentByLaborUnits")
    SUMMARY_ESTIMATE_AT_COMPLETION_LOW_PERCENT_BY_LABOR_UNITS("SummaryEstimateAtCompletionLowPercentByLaborUnits"),
    @XmlEnumValue("SummaryEstimateToCompleteByCost")
    SUMMARY_ESTIMATE_TO_COMPLETE_BY_COST("SummaryEstimateToCompleteByCost"),
    @XmlEnumValue("SummaryEstimateToCompleteByLaborUnits")
    SUMMARY_ESTIMATE_TO_COMPLETE_BY_LABOR_UNITS("SummaryEstimateToCompleteByLaborUnits"),
    @XmlEnumValue("SummaryExpenseCostPercentComplete")
    SUMMARY_EXPENSE_COST_PERCENT_COMPLETE("SummaryExpenseCostPercentComplete"),
    @XmlEnumValue("SummaryExpenseCostVariance")
    SUMMARY_EXPENSE_COST_VARIANCE("SummaryExpenseCostVariance"),
    @XmlEnumValue("SummaryFinishDateVariance")
    SUMMARY_FINISH_DATE_VARIANCE("SummaryFinishDateVariance"),
    @XmlEnumValue("SummaryInProgressActivityCount")
    SUMMARY_IN_PROGRESS_ACTIVITY_COUNT("SummaryInProgressActivityCount"),
    @XmlEnumValue("SummaryLaborCostPercentComplete")
    SUMMARY_LABOR_COST_PERCENT_COMPLETE("SummaryLaborCostPercentComplete"),
    @XmlEnumValue("SummaryLaborCostVariance")
    SUMMARY_LABOR_COST_VARIANCE("SummaryLaborCostVariance"),
    @XmlEnumValue("SummaryLaborUnitsPercentComplete")
    SUMMARY_LABOR_UNITS_PERCENT_COMPLETE("SummaryLaborUnitsPercentComplete"),
    @XmlEnumValue("SummaryLaborUnitsVariance")
    SUMMARY_LABOR_UNITS_VARIANCE("SummaryLaborUnitsVariance"),
    @XmlEnumValue("SummaryMaterialCostPercentComplete")
    SUMMARY_MATERIAL_COST_PERCENT_COMPLETE("SummaryMaterialCostPercentComplete"),
    @XmlEnumValue("SummaryMaterialCostVariance")
    SUMMARY_MATERIAL_COST_VARIANCE("SummaryMaterialCostVariance"),
    @XmlEnumValue("SummaryNonLaborCostPercentComplete")
    SUMMARY_NON_LABOR_COST_PERCENT_COMPLETE("SummaryNonLaborCostPercentComplete"),
    @XmlEnumValue("SummaryNonLaborCostVariance")
    SUMMARY_NON_LABOR_COST_VARIANCE("SummaryNonLaborCostVariance"),
    @XmlEnumValue("SummaryNonLaborUnitsPercentComplete")
    SUMMARY_NON_LABOR_UNITS_PERCENT_COMPLETE("SummaryNonLaborUnitsPercentComplete"),
    @XmlEnumValue("SummaryNonLaborUnitsVariance")
    SUMMARY_NON_LABOR_UNITS_VARIANCE("SummaryNonLaborUnitsVariance"),
    @XmlEnumValue("SummaryNotStartedActivityCount")
    SUMMARY_NOT_STARTED_ACTIVITY_COUNT("SummaryNotStartedActivityCount"),
    @XmlEnumValue("SummaryPerformancePercentCompleteByCost")
    SUMMARY_PERFORMANCE_PERCENT_COMPLETE_BY_COST("SummaryPerformancePercentCompleteByCost"),
    @XmlEnumValue("SummaryPerformancePercentCompleteByLaborUnits")
    SUMMARY_PERFORMANCE_PERCENT_COMPLETE_BY_LABOR_UNITS("SummaryPerformancePercentCompleteByLaborUnits"),
    @XmlEnumValue("SummaryPlannedCost")
    SUMMARY_PLANNED_COST("SummaryPlannedCost"),
    @XmlEnumValue("SummaryPlannedDuration")
    SUMMARY_PLANNED_DURATION("SummaryPlannedDuration"),
    @XmlEnumValue("SummaryPlannedExpenseCost")
    SUMMARY_PLANNED_EXPENSE_COST("SummaryPlannedExpenseCost"),
    @XmlEnumValue("SummaryPlannedFinishDate")
    SUMMARY_PLANNED_FINISH_DATE("SummaryPlannedFinishDate"),
    @XmlEnumValue("SummaryPlannedLaborCost")
    SUMMARY_PLANNED_LABOR_COST("SummaryPlannedLaborCost"),
    @XmlEnumValue("SummaryPlannedLaborUnits")
    SUMMARY_PLANNED_LABOR_UNITS("SummaryPlannedLaborUnits"),
    @XmlEnumValue("SummaryPlannedMaterialCost")
    SUMMARY_PLANNED_MATERIAL_COST("SummaryPlannedMaterialCost"),
    @XmlEnumValue("SummaryPlannedNonLaborCost")
    SUMMARY_PLANNED_NON_LABOR_COST("SummaryPlannedNonLaborCost"),
    @XmlEnumValue("SummaryPlannedNonLaborUnits")
    SUMMARY_PLANNED_NON_LABOR_UNITS("SummaryPlannedNonLaborUnits"),
    @XmlEnumValue("SummaryPlannedStartDate")
    SUMMARY_PLANNED_START_DATE("SummaryPlannedStartDate"),
    @XmlEnumValue("SummaryPlannedValueByCost")
    SUMMARY_PLANNED_VALUE_BY_COST("SummaryPlannedValueByCost"),
    @XmlEnumValue("SummaryPlannedValueByLaborUnits")
    SUMMARY_PLANNED_VALUE_BY_LABOR_UNITS("SummaryPlannedValueByLaborUnits"),
    @XmlEnumValue("SummaryProgressFinishDate")
    SUMMARY_PROGRESS_FINISH_DATE("SummaryProgressFinishDate"),
    @XmlEnumValue("SummaryRemainingDuration")
    SUMMARY_REMAINING_DURATION("SummaryRemainingDuration"),
    @XmlEnumValue("SummaryRemainingExpenseCost")
    SUMMARY_REMAINING_EXPENSE_COST("SummaryRemainingExpenseCost"),
    @XmlEnumValue("SummaryRemainingFinishDate")
    SUMMARY_REMAINING_FINISH_DATE("SummaryRemainingFinishDate"),
    @XmlEnumValue("SummaryRemainingLaborCost")
    SUMMARY_REMAINING_LABOR_COST("SummaryRemainingLaborCost"),
    @XmlEnumValue("SummaryRemainingLaborUnits")
    SUMMARY_REMAINING_LABOR_UNITS("SummaryRemainingLaborUnits"),
    @XmlEnumValue("SummaryRemainingMaterialCost")
    SUMMARY_REMAINING_MATERIAL_COST("SummaryRemainingMaterialCost"),
    @XmlEnumValue("SummaryRemainingNonLaborCost")
    SUMMARY_REMAINING_NON_LABOR_COST("SummaryRemainingNonLaborCost"),
    @XmlEnumValue("SummaryRemainingNonLaborUnits")
    SUMMARY_REMAINING_NON_LABOR_UNITS("SummaryRemainingNonLaborUnits"),
    @XmlEnumValue("SummaryRemainingStartDate")
    SUMMARY_REMAINING_START_DATE("SummaryRemainingStartDate"),
    @XmlEnumValue("SummaryRemainingTotalCost")
    SUMMARY_REMAINING_TOTAL_COST("SummaryRemainingTotalCost"),
    @XmlEnumValue("SummarySchedulePercentComplete")
    SUMMARY_SCHEDULE_PERCENT_COMPLETE("SummarySchedulePercentComplete"),
    @XmlEnumValue("SummarySchedulePercentCompleteByLaborUnits")
    SUMMARY_SCHEDULE_PERCENT_COMPLETE_BY_LABOR_UNITS("SummarySchedulePercentCompleteByLaborUnits"),
    @XmlEnumValue("SummarySchedulePerformanceIndexByCost")
    SUMMARY_SCHEDULE_PERFORMANCE_INDEX_BY_COST("SummarySchedulePerformanceIndexByCost"),
    @XmlEnumValue("SummarySchedulePerformanceIndexByLaborUnits")
    SUMMARY_SCHEDULE_PERFORMANCE_INDEX_BY_LABOR_UNITS("SummarySchedulePerformanceIndexByLaborUnits"),
    @XmlEnumValue("SummaryScheduleVarianceByCost")
    SUMMARY_SCHEDULE_VARIANCE_BY_COST("SummaryScheduleVarianceByCost"),
    @XmlEnumValue("SummaryScheduleVarianceByLaborUnits")
    SUMMARY_SCHEDULE_VARIANCE_BY_LABOR_UNITS("SummaryScheduleVarianceByLaborUnits"),
    @XmlEnumValue("SummaryScheduleVarianceIndex")
    SUMMARY_SCHEDULE_VARIANCE_INDEX("SummaryScheduleVarianceIndex"),
    @XmlEnumValue("SummaryScheduleVarianceIndexByCost")
    SUMMARY_SCHEDULE_VARIANCE_INDEX_BY_COST("SummaryScheduleVarianceIndexByCost"),
    @XmlEnumValue("SummaryScheduleVarianceIndexByLaborUnits")
    SUMMARY_SCHEDULE_VARIANCE_INDEX_BY_LABOR_UNITS("SummaryScheduleVarianceIndexByLaborUnits"),
    @XmlEnumValue("SummaryStartDateVariance")
    SUMMARY_START_DATE_VARIANCE("SummaryStartDateVariance"),
    @XmlEnumValue("SummaryToCompletePerformanceIndexByCost")
    SUMMARY_TO_COMPLETE_PERFORMANCE_INDEX_BY_COST("SummaryToCompletePerformanceIndexByCost"),
    @XmlEnumValue("SummaryTotalCostVariance")
    SUMMARY_TOTAL_COST_VARIANCE("SummaryTotalCostVariance"),
    @XmlEnumValue("SummaryTotalFloat")
    SUMMARY_TOTAL_FLOAT("SummaryTotalFloat"),
    @XmlEnumValue("SummaryUnitsPercentComplete")
    SUMMARY_UNITS_PERCENT_COMPLETE("SummaryUnitsPercentComplete"),
    @XmlEnumValue("SummaryVarianceAtCompletionByLaborUnits")
    SUMMARY_VARIANCE_AT_COMPLETION_BY_LABOR_UNITS("SummaryVarianceAtCompletionByLaborUnits"),
    @XmlEnumValue("TotalBenefitPlan")
    TOTAL_BENEFIT_PLAN("TotalBenefitPlan"),
    @XmlEnumValue("TotalBenefitPlanTally")
    TOTAL_BENEFIT_PLAN_TALLY("TotalBenefitPlanTally"),
    @XmlEnumValue("TotalSpendingPlan")
    TOTAL_SPENDING_PLAN("TotalSpendingPlan"),
    @XmlEnumValue("TotalSpendingPlanTally")
    TOTAL_SPENDING_PLAN_TALLY("TotalSpendingPlanTally"),
    @XmlEnumValue("UnallocatedBudget")
    UNALLOCATED_BUDGET("UnallocatedBudget"),
    @XmlEnumValue("UndistributedCurrentVariance")
    UNDISTRIBUTED_CURRENT_VARIANCE("UndistributedCurrentVariance"),
    @XmlEnumValue("WBSCategoryObjectId")
    WBS_CATEGORY_OBJECT_ID("WBSCategoryObjectId"),
    @XmlEnumValue("WBSMilestonePercentComplete")
    WBS_MILESTONE_PERCENT_COMPLETE("WBSMilestonePercentComplete");
    private final String value;

    WBSFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WBSFieldType fromValue(String v) {
        for (WBSFieldType c: WBSFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
