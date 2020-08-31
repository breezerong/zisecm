
package com.cnpe.p6.activityservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ActivityFieldType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ActivityFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AccountingVariance"/>
 *     &lt;enumeration value="AccountingVarianceLaborUnits"/>
 *     &lt;enumeration value="ActivityOwnerUserId"/>
 *     &lt;enumeration value="ActualDuration"/>
 *     &lt;enumeration value="ActualExpenseCost"/>
 *     &lt;enumeration value="ActualFinishDate"/>
 *     &lt;enumeration value="ActualLaborCost"/>
 *     &lt;enumeration value="ActualLaborUnits"/>
 *     &lt;enumeration value="ActualMaterialCost"/>
 *     &lt;enumeration value="ActualNonLaborCost"/>
 *     &lt;enumeration value="ActualNonLaborUnits"/>
 *     &lt;enumeration value="ActualStartDate"/>
 *     &lt;enumeration value="ActualThisPeriodLaborCost"/>
 *     &lt;enumeration value="ActualThisPeriodLaborUnits"/>
 *     &lt;enumeration value="ActualThisPeriodMaterialCost"/>
 *     &lt;enumeration value="ActualThisPeriodNonLaborCost"/>
 *     &lt;enumeration value="ActualThisPeriodNonLaborUnits"/>
 *     &lt;enumeration value="ActualTotalCost"/>
 *     &lt;enumeration value="ActualTotalUnits"/>
 *     &lt;enumeration value="AtCompletionDuration"/>
 *     &lt;enumeration value="AtCompletionExpenseCost"/>
 *     &lt;enumeration value="AtCompletionLaborCost"/>
 *     &lt;enumeration value="AtCompletionLaborUnits"/>
 *     &lt;enumeration value="AtCompletionLaborUnitsVariance"/>
 *     &lt;enumeration value="AtCompletionMaterialCost"/>
 *     &lt;enumeration value="AtCompletionNonLaborCost"/>
 *     &lt;enumeration value="AtCompletionNonLaborUnits"/>
 *     &lt;enumeration value="AtCompletionTotalCost"/>
 *     &lt;enumeration value="AtCompletionTotalUnits"/>
 *     &lt;enumeration value="AtCompletionVariance"/>
 *     &lt;enumeration value="AutoComputeActuals"/>
 *     &lt;enumeration value="Baseline1Duration"/>
 *     &lt;enumeration value="Baseline1FinishDate"/>
 *     &lt;enumeration value="Baseline1PlannedDuration"/>
 *     &lt;enumeration value="Baseline1PlannedExpenseCost"/>
 *     &lt;enumeration value="Baseline1PlannedLaborCost"/>
 *     &lt;enumeration value="Baseline1PlannedLaborUnits"/>
 *     &lt;enumeration value="Baseline1PlannedMaterialCost"/>
 *     &lt;enumeration value="Baseline1PlannedNonLaborCost"/>
 *     &lt;enumeration value="Baseline1PlannedNonLaborUnits"/>
 *     &lt;enumeration value="Baseline1PlannedTotalCost"/>
 *     &lt;enumeration value="Baseline1StartDate"/>
 *     &lt;enumeration value="BaselineDuration"/>
 *     &lt;enumeration value="BaselineFinishDate"/>
 *     &lt;enumeration value="BaselinePlannedDuration"/>
 *     &lt;enumeration value="BaselinePlannedExpenseCost"/>
 *     &lt;enumeration value="BaselinePlannedLaborCost"/>
 *     &lt;enumeration value="BaselinePlannedLaborUnits"/>
 *     &lt;enumeration value="BaselinePlannedMaterialCost"/>
 *     &lt;enumeration value="BaselinePlannedNonLaborCost"/>
 *     &lt;enumeration value="BaselinePlannedNonLaborUnits"/>
 *     &lt;enumeration value="BaselinePlannedTotalCost"/>
 *     &lt;enumeration value="BaselineStartDate"/>
 *     &lt;enumeration value="BudgetAtCompletion"/>
 *     &lt;enumeration value="CBSCode"/>
 *     &lt;enumeration value="CBSId"/>
 *     &lt;enumeration value="CBSObjectId"/>
 *     &lt;enumeration value="CalendarName"/>
 *     &lt;enumeration value="CalendarObjectId"/>
 *     &lt;enumeration value="CostPercentComplete"/>
 *     &lt;enumeration value="CostPercentOfPlanned"/>
 *     &lt;enumeration value="CostPerformanceIndex"/>
 *     &lt;enumeration value="CostPerformanceIndexLaborUnits"/>
 *     &lt;enumeration value="CostVariance"/>
 *     &lt;enumeration value="CostVarianceIndex"/>
 *     &lt;enumeration value="CostVarianceIndexLaborUnits"/>
 *     &lt;enumeration value="CostVarianceLaborUnits"/>
 *     &lt;enumeration value="CreateDate"/>
 *     &lt;enumeration value="CreateUser"/>
 *     &lt;enumeration value="DataDate"/>
 *     &lt;enumeration value="Duration1Variance"/>
 *     &lt;enumeration value="DurationPercentComplete"/>
 *     &lt;enumeration value="DurationPercentOfPlanned"/>
 *     &lt;enumeration value="DurationType"/>
 *     &lt;enumeration value="DurationVariance"/>
 *     &lt;enumeration value="EarlyFinishDate"/>
 *     &lt;enumeration value="EarlyStartDate"/>
 *     &lt;enumeration value="EarnedValueCost"/>
 *     &lt;enumeration value="EarnedValueLaborUnits"/>
 *     &lt;enumeration value="EstimateAtCompletionCost"/>
 *     &lt;enumeration value="EstimateAtCompletionLaborUnits"/>
 *     &lt;enumeration value="EstimateToComplete"/>
 *     &lt;enumeration value="EstimateToCompleteLaborUnits"/>
 *     &lt;enumeration value="EstimatedWeight"/>
 *     &lt;enumeration value="ExpectedFinishDate"/>
 *     &lt;enumeration value="ExpenseCost1Variance"/>
 *     &lt;enumeration value="ExpenseCostPercentComplete"/>
 *     &lt;enumeration value="ExpenseCostVariance"/>
 *     &lt;enumeration value="ExternalEarlyStartDate"/>
 *     &lt;enumeration value="ExternalLateFinishDate"/>
 *     &lt;enumeration value="Feedback"/>
 *     &lt;enumeration value="FinishDate"/>
 *     &lt;enumeration value="FinishDate1Variance"/>
 *     &lt;enumeration value="FinishDateVariance"/>
 *     &lt;enumeration value="FloatPath"/>
 *     &lt;enumeration value="FloatPathOrder"/>
 *     &lt;enumeration value="FreeFloat"/>
 *     &lt;enumeration value="GUID"/>
 *     &lt;enumeration value="HasFutureBucketData"/>
 *     &lt;enumeration value="Id"/>
 *     &lt;enumeration value="IsBaseline"/>
 *     &lt;enumeration value="IsCritical"/>
 *     &lt;enumeration value="IsLongestPath"/>
 *     &lt;enumeration value="IsNewFeedback"/>
 *     &lt;enumeration value="IsStarred"/>
 *     &lt;enumeration value="IsTemplate"/>
 *     &lt;enumeration value="IsWorkPackage"/>
 *     &lt;enumeration value="LaborCost1Variance"/>
 *     &lt;enumeration value="LaborCostPercentComplete"/>
 *     &lt;enumeration value="LaborCostVariance"/>
 *     &lt;enumeration value="LaborUnits1Variance"/>
 *     &lt;enumeration value="LaborUnitsPercentComplete"/>
 *     &lt;enumeration value="LaborUnitsVariance"/>
 *     &lt;enumeration value="LastUpdateDate"/>
 *     &lt;enumeration value="LastUpdateUser"/>
 *     &lt;enumeration value="LateFinishDate"/>
 *     &lt;enumeration value="LateStartDate"/>
 *     &lt;enumeration value="LevelingPriority"/>
 *     &lt;enumeration value="LocationName"/>
 *     &lt;enumeration value="LocationObjectId"/>
 *     &lt;enumeration value="MaterialCost1Variance"/>
 *     &lt;enumeration value="MaterialCostPercentComplete"/>
 *     &lt;enumeration value="MaterialCostVariance"/>
 *     &lt;enumeration value="MaximumDuration"/>
 *     &lt;enumeration value="MinimumDuration"/>
 *     &lt;enumeration value="MostLikelyDuration"/>
 *     &lt;enumeration value="Name"/>
 *     &lt;enumeration value="NonLaborCost1Variance"/>
 *     &lt;enumeration value="NonLaborCostPercentComplete"/>
 *     &lt;enumeration value="NonLaborCostVariance"/>
 *     &lt;enumeration value="NonLaborUnits1Variance"/>
 *     &lt;enumeration value="NonLaborUnitsPercentComplete"/>
 *     &lt;enumeration value="NonLaborUnitsVariance"/>
 *     &lt;enumeration value="NotesToResources"/>
 *     &lt;enumeration value="ObjectId"/>
 *     &lt;enumeration value="OwnerIDArray"/>
 *     &lt;enumeration value="OwnerNamesArray"/>
 *     &lt;enumeration value="PercentComplete"/>
 *     &lt;enumeration value="PercentCompleteType"/>
 *     &lt;enumeration value="PerformancePercentComplete"/>
 *     &lt;enumeration value="PhysicalPercentComplete"/>
 *     &lt;enumeration value="PlannedDuration"/>
 *     &lt;enumeration value="PlannedExpenseCost"/>
 *     &lt;enumeration value="PlannedFinishDate"/>
 *     &lt;enumeration value="PlannedLaborCost"/>
 *     &lt;enumeration value="PlannedLaborUnits"/>
 *     &lt;enumeration value="PlannedMaterialCost"/>
 *     &lt;enumeration value="PlannedNonLaborCost"/>
 *     &lt;enumeration value="PlannedNonLaborUnits"/>
 *     &lt;enumeration value="PlannedStartDate"/>
 *     &lt;enumeration value="PlannedTotalCost"/>
 *     &lt;enumeration value="PlannedTotalUnits"/>
 *     &lt;enumeration value="PlannedValueCost"/>
 *     &lt;enumeration value="PlannedValueLaborUnits"/>
 *     &lt;enumeration value="PostResponsePessimisticFinish"/>
 *     &lt;enumeration value="PostResponsePessimisticStart"/>
 *     &lt;enumeration value="PreResponsePessimisticFinish"/>
 *     &lt;enumeration value="PreResponsePessimisticStart"/>
 *     &lt;enumeration value="PrimaryConstraintDate"/>
 *     &lt;enumeration value="PrimaryConstraintType"/>
 *     &lt;enumeration value="PrimaryResourceId"/>
 *     &lt;enumeration value="PrimaryResourceName"/>
 *     &lt;enumeration value="PrimaryResourceObjectId"/>
 *     &lt;enumeration value="ProjectFlag"/>
 *     &lt;enumeration value="ProjectId"/>
 *     &lt;enumeration value="ProjectName"/>
 *     &lt;enumeration value="ProjectObjectId"/>
 *     &lt;enumeration value="ProjectProjectFlag"/>
 *     &lt;enumeration value="RemainingDuration"/>
 *     &lt;enumeration value="RemainingEarlyFinishDate"/>
 *     &lt;enumeration value="RemainingEarlyStartDate"/>
 *     &lt;enumeration value="RemainingExpenseCost"/>
 *     &lt;enumeration value="RemainingFloat"/>
 *     &lt;enumeration value="RemainingLaborCost"/>
 *     &lt;enumeration value="RemainingLaborUnits"/>
 *     &lt;enumeration value="RemainingLateFinishDate"/>
 *     &lt;enumeration value="RemainingLateStartDate"/>
 *     &lt;enumeration value="RemainingMaterialCost"/>
 *     &lt;enumeration value="RemainingNonLaborCost"/>
 *     &lt;enumeration value="RemainingNonLaborUnits"/>
 *     &lt;enumeration value="RemainingTotalCost"/>
 *     &lt;enumeration value="RemainingTotalUnits"/>
 *     &lt;enumeration value="ResumeDate"/>
 *     &lt;enumeration value="ReviewFinishDate"/>
 *     &lt;enumeration value="ReviewRequired"/>
 *     &lt;enumeration value="ReviewStatus"/>
 *     &lt;enumeration value="SchedulePercentComplete"/>
 *     &lt;enumeration value="SchedulePerformanceIndex"/>
 *     &lt;enumeration value="SchedulePerformanceIndexLaborUnits"/>
 *     &lt;enumeration value="ScheduleVariance"/>
 *     &lt;enumeration value="ScheduleVarianceIndex"/>
 *     &lt;enumeration value="ScheduleVarianceIndexLaborUnits"/>
 *     &lt;enumeration value="ScheduleVarianceLaborUnits"/>
 *     &lt;enumeration value="SecondaryConstraintDate"/>
 *     &lt;enumeration value="SecondaryConstraintType"/>
 *     &lt;enumeration value="StartDate"/>
 *     &lt;enumeration value="StartDate1Variance"/>
 *     &lt;enumeration value="StartDateVariance"/>
 *     &lt;enumeration value="Status"/>
 *     &lt;enumeration value="StatusCode"/>
 *     &lt;enumeration value="SuspendDate"/>
 *     &lt;enumeration value="ToCompletePerformanceIndex"/>
 *     &lt;enumeration value="TotalCost1Variance"/>
 *     &lt;enumeration value="TotalCostVariance"/>
 *     &lt;enumeration value="TotalFloat"/>
 *     &lt;enumeration value="Type"/>
 *     &lt;enumeration value="UnitsPercentComplete"/>
 *     &lt;enumeration value="UnreadCommentCount"/>
 *     &lt;enumeration value="WBSCode"/>
 *     &lt;enumeration value="WBSName"/>
 *     &lt;enumeration value="WBSObjectId"/>
 *     &lt;enumeration value="WBSPath"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActivityFieldType")
@XmlEnum
public enum ActivityFieldType {

    @XmlEnumValue("AccountingVariance")
    ACCOUNTING_VARIANCE("AccountingVariance"),
    @XmlEnumValue("AccountingVarianceLaborUnits")
    ACCOUNTING_VARIANCE_LABOR_UNITS("AccountingVarianceLaborUnits"),
    @XmlEnumValue("ActivityOwnerUserId")
    ACTIVITY_OWNER_USER_ID("ActivityOwnerUserId"),
    @XmlEnumValue("ActualDuration")
    ACTUAL_DURATION("ActualDuration"),
    @XmlEnumValue("ActualExpenseCost")
    ACTUAL_EXPENSE_COST("ActualExpenseCost"),
    @XmlEnumValue("ActualFinishDate")
    ACTUAL_FINISH_DATE("ActualFinishDate"),
    @XmlEnumValue("ActualLaborCost")
    ACTUAL_LABOR_COST("ActualLaborCost"),
    @XmlEnumValue("ActualLaborUnits")
    ACTUAL_LABOR_UNITS("ActualLaborUnits"),
    @XmlEnumValue("ActualMaterialCost")
    ACTUAL_MATERIAL_COST("ActualMaterialCost"),
    @XmlEnumValue("ActualNonLaborCost")
    ACTUAL_NON_LABOR_COST("ActualNonLaborCost"),
    @XmlEnumValue("ActualNonLaborUnits")
    ACTUAL_NON_LABOR_UNITS("ActualNonLaborUnits"),
    @XmlEnumValue("ActualStartDate")
    ACTUAL_START_DATE("ActualStartDate"),
    @XmlEnumValue("ActualThisPeriodLaborCost")
    ACTUAL_THIS_PERIOD_LABOR_COST("ActualThisPeriodLaborCost"),
    @XmlEnumValue("ActualThisPeriodLaborUnits")
    ACTUAL_THIS_PERIOD_LABOR_UNITS("ActualThisPeriodLaborUnits"),
    @XmlEnumValue("ActualThisPeriodMaterialCost")
    ACTUAL_THIS_PERIOD_MATERIAL_COST("ActualThisPeriodMaterialCost"),
    @XmlEnumValue("ActualThisPeriodNonLaborCost")
    ACTUAL_THIS_PERIOD_NON_LABOR_COST("ActualThisPeriodNonLaborCost"),
    @XmlEnumValue("ActualThisPeriodNonLaborUnits")
    ACTUAL_THIS_PERIOD_NON_LABOR_UNITS("ActualThisPeriodNonLaborUnits"),
    @XmlEnumValue("ActualTotalCost")
    ACTUAL_TOTAL_COST("ActualTotalCost"),
    @XmlEnumValue("ActualTotalUnits")
    ACTUAL_TOTAL_UNITS("ActualTotalUnits"),
    @XmlEnumValue("AtCompletionDuration")
    AT_COMPLETION_DURATION("AtCompletionDuration"),
    @XmlEnumValue("AtCompletionExpenseCost")
    AT_COMPLETION_EXPENSE_COST("AtCompletionExpenseCost"),
    @XmlEnumValue("AtCompletionLaborCost")
    AT_COMPLETION_LABOR_COST("AtCompletionLaborCost"),
    @XmlEnumValue("AtCompletionLaborUnits")
    AT_COMPLETION_LABOR_UNITS("AtCompletionLaborUnits"),
    @XmlEnumValue("AtCompletionLaborUnitsVariance")
    AT_COMPLETION_LABOR_UNITS_VARIANCE("AtCompletionLaborUnitsVariance"),
    @XmlEnumValue("AtCompletionMaterialCost")
    AT_COMPLETION_MATERIAL_COST("AtCompletionMaterialCost"),
    @XmlEnumValue("AtCompletionNonLaborCost")
    AT_COMPLETION_NON_LABOR_COST("AtCompletionNonLaborCost"),
    @XmlEnumValue("AtCompletionNonLaborUnits")
    AT_COMPLETION_NON_LABOR_UNITS("AtCompletionNonLaborUnits"),
    @XmlEnumValue("AtCompletionTotalCost")
    AT_COMPLETION_TOTAL_COST("AtCompletionTotalCost"),
    @XmlEnumValue("AtCompletionTotalUnits")
    AT_COMPLETION_TOTAL_UNITS("AtCompletionTotalUnits"),
    @XmlEnumValue("AtCompletionVariance")
    AT_COMPLETION_VARIANCE("AtCompletionVariance"),
    @XmlEnumValue("AutoComputeActuals")
    AUTO_COMPUTE_ACTUALS("AutoComputeActuals"),
    @XmlEnumValue("Baseline1Duration")
    BASELINE_1_DURATION("Baseline1Duration"),
    @XmlEnumValue("Baseline1FinishDate")
    BASELINE_1_FINISH_DATE("Baseline1FinishDate"),
    @XmlEnumValue("Baseline1PlannedDuration")
    BASELINE_1_PLANNED_DURATION("Baseline1PlannedDuration"),
    @XmlEnumValue("Baseline1PlannedExpenseCost")
    BASELINE_1_PLANNED_EXPENSE_COST("Baseline1PlannedExpenseCost"),
    @XmlEnumValue("Baseline1PlannedLaborCost")
    BASELINE_1_PLANNED_LABOR_COST("Baseline1PlannedLaborCost"),
    @XmlEnumValue("Baseline1PlannedLaborUnits")
    BASELINE_1_PLANNED_LABOR_UNITS("Baseline1PlannedLaborUnits"),
    @XmlEnumValue("Baseline1PlannedMaterialCost")
    BASELINE_1_PLANNED_MATERIAL_COST("Baseline1PlannedMaterialCost"),
    @XmlEnumValue("Baseline1PlannedNonLaborCost")
    BASELINE_1_PLANNED_NON_LABOR_COST("Baseline1PlannedNonLaborCost"),
    @XmlEnumValue("Baseline1PlannedNonLaborUnits")
    BASELINE_1_PLANNED_NON_LABOR_UNITS("Baseline1PlannedNonLaborUnits"),
    @XmlEnumValue("Baseline1PlannedTotalCost")
    BASELINE_1_PLANNED_TOTAL_COST("Baseline1PlannedTotalCost"),
    @XmlEnumValue("Baseline1StartDate")
    BASELINE_1_START_DATE("Baseline1StartDate"),
    @XmlEnumValue("BaselineDuration")
    BASELINE_DURATION("BaselineDuration"),
    @XmlEnumValue("BaselineFinishDate")
    BASELINE_FINISH_DATE("BaselineFinishDate"),
    @XmlEnumValue("BaselinePlannedDuration")
    BASELINE_PLANNED_DURATION("BaselinePlannedDuration"),
    @XmlEnumValue("BaselinePlannedExpenseCost")
    BASELINE_PLANNED_EXPENSE_COST("BaselinePlannedExpenseCost"),
    @XmlEnumValue("BaselinePlannedLaborCost")
    BASELINE_PLANNED_LABOR_COST("BaselinePlannedLaborCost"),
    @XmlEnumValue("BaselinePlannedLaborUnits")
    BASELINE_PLANNED_LABOR_UNITS("BaselinePlannedLaborUnits"),
    @XmlEnumValue("BaselinePlannedMaterialCost")
    BASELINE_PLANNED_MATERIAL_COST("BaselinePlannedMaterialCost"),
    @XmlEnumValue("BaselinePlannedNonLaborCost")
    BASELINE_PLANNED_NON_LABOR_COST("BaselinePlannedNonLaborCost"),
    @XmlEnumValue("BaselinePlannedNonLaborUnits")
    BASELINE_PLANNED_NON_LABOR_UNITS("BaselinePlannedNonLaborUnits"),
    @XmlEnumValue("BaselinePlannedTotalCost")
    BASELINE_PLANNED_TOTAL_COST("BaselinePlannedTotalCost"),
    @XmlEnumValue("BaselineStartDate")
    BASELINE_START_DATE("BaselineStartDate"),
    @XmlEnumValue("BudgetAtCompletion")
    BUDGET_AT_COMPLETION("BudgetAtCompletion"),
    @XmlEnumValue("CBSCode")
    CBS_CODE("CBSCode"),
    @XmlEnumValue("CBSId")
    CBS_ID("CBSId"),
    @XmlEnumValue("CBSObjectId")
    CBS_OBJECT_ID("CBSObjectId"),
    @XmlEnumValue("CalendarName")
    CALENDAR_NAME("CalendarName"),
    @XmlEnumValue("CalendarObjectId")
    CALENDAR_OBJECT_ID("CalendarObjectId"),
    @XmlEnumValue("CostPercentComplete")
    COST_PERCENT_COMPLETE("CostPercentComplete"),
    @XmlEnumValue("CostPercentOfPlanned")
    COST_PERCENT_OF_PLANNED("CostPercentOfPlanned"),
    @XmlEnumValue("CostPerformanceIndex")
    COST_PERFORMANCE_INDEX("CostPerformanceIndex"),
    @XmlEnumValue("CostPerformanceIndexLaborUnits")
    COST_PERFORMANCE_INDEX_LABOR_UNITS("CostPerformanceIndexLaborUnits"),
    @XmlEnumValue("CostVariance")
    COST_VARIANCE("CostVariance"),
    @XmlEnumValue("CostVarianceIndex")
    COST_VARIANCE_INDEX("CostVarianceIndex"),
    @XmlEnumValue("CostVarianceIndexLaborUnits")
    COST_VARIANCE_INDEX_LABOR_UNITS("CostVarianceIndexLaborUnits"),
    @XmlEnumValue("CostVarianceLaborUnits")
    COST_VARIANCE_LABOR_UNITS("CostVarianceLaborUnits"),
    @XmlEnumValue("CreateDate")
    CREATE_DATE("CreateDate"),
    @XmlEnumValue("CreateUser")
    CREATE_USER("CreateUser"),
    @XmlEnumValue("DataDate")
    DATA_DATE("DataDate"),
    @XmlEnumValue("Duration1Variance")
    DURATION_1_VARIANCE("Duration1Variance"),
    @XmlEnumValue("DurationPercentComplete")
    DURATION_PERCENT_COMPLETE("DurationPercentComplete"),
    @XmlEnumValue("DurationPercentOfPlanned")
    DURATION_PERCENT_OF_PLANNED("DurationPercentOfPlanned"),
    @XmlEnumValue("DurationType")
    DURATION_TYPE("DurationType"),
    @XmlEnumValue("DurationVariance")
    DURATION_VARIANCE("DurationVariance"),
    @XmlEnumValue("EarlyFinishDate")
    EARLY_FINISH_DATE("EarlyFinishDate"),
    @XmlEnumValue("EarlyStartDate")
    EARLY_START_DATE("EarlyStartDate"),
    @XmlEnumValue("EarnedValueCost")
    EARNED_VALUE_COST("EarnedValueCost"),
    @XmlEnumValue("EarnedValueLaborUnits")
    EARNED_VALUE_LABOR_UNITS("EarnedValueLaborUnits"),
    @XmlEnumValue("EstimateAtCompletionCost")
    ESTIMATE_AT_COMPLETION_COST("EstimateAtCompletionCost"),
    @XmlEnumValue("EstimateAtCompletionLaborUnits")
    ESTIMATE_AT_COMPLETION_LABOR_UNITS("EstimateAtCompletionLaborUnits"),
    @XmlEnumValue("EstimateToComplete")
    ESTIMATE_TO_COMPLETE("EstimateToComplete"),
    @XmlEnumValue("EstimateToCompleteLaborUnits")
    ESTIMATE_TO_COMPLETE_LABOR_UNITS("EstimateToCompleteLaborUnits"),
    @XmlEnumValue("EstimatedWeight")
    ESTIMATED_WEIGHT("EstimatedWeight"),
    @XmlEnumValue("ExpectedFinishDate")
    EXPECTED_FINISH_DATE("ExpectedFinishDate"),
    @XmlEnumValue("ExpenseCost1Variance")
    EXPENSE_COST_1_VARIANCE("ExpenseCost1Variance"),
    @XmlEnumValue("ExpenseCostPercentComplete")
    EXPENSE_COST_PERCENT_COMPLETE("ExpenseCostPercentComplete"),
    @XmlEnumValue("ExpenseCostVariance")
    EXPENSE_COST_VARIANCE("ExpenseCostVariance"),
    @XmlEnumValue("ExternalEarlyStartDate")
    EXTERNAL_EARLY_START_DATE("ExternalEarlyStartDate"),
    @XmlEnumValue("ExternalLateFinishDate")
    EXTERNAL_LATE_FINISH_DATE("ExternalLateFinishDate"),
    @XmlEnumValue("Feedback")
    FEEDBACK("Feedback"),
    @XmlEnumValue("FinishDate")
    FINISH_DATE("FinishDate"),
    @XmlEnumValue("FinishDate1Variance")
    FINISH_DATE_1_VARIANCE("FinishDate1Variance"),
    @XmlEnumValue("FinishDateVariance")
    FINISH_DATE_VARIANCE("FinishDateVariance"),
    @XmlEnumValue("FloatPath")
    FLOAT_PATH("FloatPath"),
    @XmlEnumValue("FloatPathOrder")
    FLOAT_PATH_ORDER("FloatPathOrder"),
    @XmlEnumValue("FreeFloat")
    FREE_FLOAT("FreeFloat"),
    GUID("GUID"),
    @XmlEnumValue("HasFutureBucketData")
    HAS_FUTURE_BUCKET_DATA("HasFutureBucketData"),
    @XmlEnumValue("Id")
    ID("Id"),
    @XmlEnumValue("IsBaseline")
    IS_BASELINE("IsBaseline"),
    @XmlEnumValue("IsCritical")
    IS_CRITICAL("IsCritical"),
    @XmlEnumValue("IsLongestPath")
    IS_LONGEST_PATH("IsLongestPath"),
    @XmlEnumValue("IsNewFeedback")
    IS_NEW_FEEDBACK("IsNewFeedback"),
    @XmlEnumValue("IsStarred")
    IS_STARRED("IsStarred"),
    @XmlEnumValue("IsTemplate")
    IS_TEMPLATE("IsTemplate"),
    @XmlEnumValue("IsWorkPackage")
    IS_WORK_PACKAGE("IsWorkPackage"),
    @XmlEnumValue("LaborCost1Variance")
    LABOR_COST_1_VARIANCE("LaborCost1Variance"),
    @XmlEnumValue("LaborCostPercentComplete")
    LABOR_COST_PERCENT_COMPLETE("LaborCostPercentComplete"),
    @XmlEnumValue("LaborCostVariance")
    LABOR_COST_VARIANCE("LaborCostVariance"),
    @XmlEnumValue("LaborUnits1Variance")
    LABOR_UNITS_1_VARIANCE("LaborUnits1Variance"),
    @XmlEnumValue("LaborUnitsPercentComplete")
    LABOR_UNITS_PERCENT_COMPLETE("LaborUnitsPercentComplete"),
    @XmlEnumValue("LaborUnitsVariance")
    LABOR_UNITS_VARIANCE("LaborUnitsVariance"),
    @XmlEnumValue("LastUpdateDate")
    LAST_UPDATE_DATE("LastUpdateDate"),
    @XmlEnumValue("LastUpdateUser")
    LAST_UPDATE_USER("LastUpdateUser"),
    @XmlEnumValue("LateFinishDate")
    LATE_FINISH_DATE("LateFinishDate"),
    @XmlEnumValue("LateStartDate")
    LATE_START_DATE("LateStartDate"),
    @XmlEnumValue("LevelingPriority")
    LEVELING_PRIORITY("LevelingPriority"),
    @XmlEnumValue("LocationName")
    LOCATION_NAME("LocationName"),
    @XmlEnumValue("LocationObjectId")
    LOCATION_OBJECT_ID("LocationObjectId"),
    @XmlEnumValue("MaterialCost1Variance")
    MATERIAL_COST_1_VARIANCE("MaterialCost1Variance"),
    @XmlEnumValue("MaterialCostPercentComplete")
    MATERIAL_COST_PERCENT_COMPLETE("MaterialCostPercentComplete"),
    @XmlEnumValue("MaterialCostVariance")
    MATERIAL_COST_VARIANCE("MaterialCostVariance"),
    @XmlEnumValue("MaximumDuration")
    MAXIMUM_DURATION("MaximumDuration"),
    @XmlEnumValue("MinimumDuration")
    MINIMUM_DURATION("MinimumDuration"),
    @XmlEnumValue("MostLikelyDuration")
    MOST_LIKELY_DURATION("MostLikelyDuration"),
    @XmlEnumValue("Name")
    NAME("Name"),
    @XmlEnumValue("NonLaborCost1Variance")
    NON_LABOR_COST_1_VARIANCE("NonLaborCost1Variance"),
    @XmlEnumValue("NonLaborCostPercentComplete")
    NON_LABOR_COST_PERCENT_COMPLETE("NonLaborCostPercentComplete"),
    @XmlEnumValue("NonLaborCostVariance")
    NON_LABOR_COST_VARIANCE("NonLaborCostVariance"),
    @XmlEnumValue("NonLaborUnits1Variance")
    NON_LABOR_UNITS_1_VARIANCE("NonLaborUnits1Variance"),
    @XmlEnumValue("NonLaborUnitsPercentComplete")
    NON_LABOR_UNITS_PERCENT_COMPLETE("NonLaborUnitsPercentComplete"),
    @XmlEnumValue("NonLaborUnitsVariance")
    NON_LABOR_UNITS_VARIANCE("NonLaborUnitsVariance"),
    @XmlEnumValue("NotesToResources")
    NOTES_TO_RESOURCES("NotesToResources"),
    @XmlEnumValue("ObjectId")
    OBJECT_ID("ObjectId"),
    @XmlEnumValue("OwnerIDArray")
    OWNER_ID_ARRAY("OwnerIDArray"),
    @XmlEnumValue("OwnerNamesArray")
    OWNER_NAMES_ARRAY("OwnerNamesArray"),
    @XmlEnumValue("PercentComplete")
    PERCENT_COMPLETE("PercentComplete"),
    @XmlEnumValue("PercentCompleteType")
    PERCENT_COMPLETE_TYPE("PercentCompleteType"),
    @XmlEnumValue("PerformancePercentComplete")
    PERFORMANCE_PERCENT_COMPLETE("PerformancePercentComplete"),
    @XmlEnumValue("PhysicalPercentComplete")
    PHYSICAL_PERCENT_COMPLETE("PhysicalPercentComplete"),
    @XmlEnumValue("PlannedDuration")
    PLANNED_DURATION("PlannedDuration"),
    @XmlEnumValue("PlannedExpenseCost")
    PLANNED_EXPENSE_COST("PlannedExpenseCost"),
    @XmlEnumValue("PlannedFinishDate")
    PLANNED_FINISH_DATE("PlannedFinishDate"),
    @XmlEnumValue("PlannedLaborCost")
    PLANNED_LABOR_COST("PlannedLaborCost"),
    @XmlEnumValue("PlannedLaborUnits")
    PLANNED_LABOR_UNITS("PlannedLaborUnits"),
    @XmlEnumValue("PlannedMaterialCost")
    PLANNED_MATERIAL_COST("PlannedMaterialCost"),
    @XmlEnumValue("PlannedNonLaborCost")
    PLANNED_NON_LABOR_COST("PlannedNonLaborCost"),
    @XmlEnumValue("PlannedNonLaborUnits")
    PLANNED_NON_LABOR_UNITS("PlannedNonLaborUnits"),
    @XmlEnumValue("PlannedStartDate")
    PLANNED_START_DATE("PlannedStartDate"),
    @XmlEnumValue("PlannedTotalCost")
    PLANNED_TOTAL_COST("PlannedTotalCost"),
    @XmlEnumValue("PlannedTotalUnits")
    PLANNED_TOTAL_UNITS("PlannedTotalUnits"),
    @XmlEnumValue("PlannedValueCost")
    PLANNED_VALUE_COST("PlannedValueCost"),
    @XmlEnumValue("PlannedValueLaborUnits")
    PLANNED_VALUE_LABOR_UNITS("PlannedValueLaborUnits"),
    @XmlEnumValue("PostResponsePessimisticFinish")
    POST_RESPONSE_PESSIMISTIC_FINISH("PostResponsePessimisticFinish"),
    @XmlEnumValue("PostResponsePessimisticStart")
    POST_RESPONSE_PESSIMISTIC_START("PostResponsePessimisticStart"),
    @XmlEnumValue("PreResponsePessimisticFinish")
    PRE_RESPONSE_PESSIMISTIC_FINISH("PreResponsePessimisticFinish"),
    @XmlEnumValue("PreResponsePessimisticStart")
    PRE_RESPONSE_PESSIMISTIC_START("PreResponsePessimisticStart"),
    @XmlEnumValue("PrimaryConstraintDate")
    PRIMARY_CONSTRAINT_DATE("PrimaryConstraintDate"),
    @XmlEnumValue("PrimaryConstraintType")
    PRIMARY_CONSTRAINT_TYPE("PrimaryConstraintType"),
    @XmlEnumValue("PrimaryResourceId")
    PRIMARY_RESOURCE_ID("PrimaryResourceId"),
    @XmlEnumValue("PrimaryResourceName")
    PRIMARY_RESOURCE_NAME("PrimaryResourceName"),
    @XmlEnumValue("PrimaryResourceObjectId")
    PRIMARY_RESOURCE_OBJECT_ID("PrimaryResourceObjectId"),
    @XmlEnumValue("ProjectFlag")
    PROJECT_FLAG("ProjectFlag"),
    @XmlEnumValue("ProjectId")
    PROJECT_ID("ProjectId"),
    @XmlEnumValue("ProjectName")
    PROJECT_NAME("ProjectName"),
    @XmlEnumValue("ProjectObjectId")
    PROJECT_OBJECT_ID("ProjectObjectId"),
    @XmlEnumValue("ProjectProjectFlag")
    PROJECT_PROJECT_FLAG("ProjectProjectFlag"),
    @XmlEnumValue("RemainingDuration")
    REMAINING_DURATION("RemainingDuration"),
    @XmlEnumValue("RemainingEarlyFinishDate")
    REMAINING_EARLY_FINISH_DATE("RemainingEarlyFinishDate"),
    @XmlEnumValue("RemainingEarlyStartDate")
    REMAINING_EARLY_START_DATE("RemainingEarlyStartDate"),
    @XmlEnumValue("RemainingExpenseCost")
    REMAINING_EXPENSE_COST("RemainingExpenseCost"),
    @XmlEnumValue("RemainingFloat")
    REMAINING_FLOAT("RemainingFloat"),
    @XmlEnumValue("RemainingLaborCost")
    REMAINING_LABOR_COST("RemainingLaborCost"),
    @XmlEnumValue("RemainingLaborUnits")
    REMAINING_LABOR_UNITS("RemainingLaborUnits"),
    @XmlEnumValue("RemainingLateFinishDate")
    REMAINING_LATE_FINISH_DATE("RemainingLateFinishDate"),
    @XmlEnumValue("RemainingLateStartDate")
    REMAINING_LATE_START_DATE("RemainingLateStartDate"),
    @XmlEnumValue("RemainingMaterialCost")
    REMAINING_MATERIAL_COST("RemainingMaterialCost"),
    @XmlEnumValue("RemainingNonLaborCost")
    REMAINING_NON_LABOR_COST("RemainingNonLaborCost"),
    @XmlEnumValue("RemainingNonLaborUnits")
    REMAINING_NON_LABOR_UNITS("RemainingNonLaborUnits"),
    @XmlEnumValue("RemainingTotalCost")
    REMAINING_TOTAL_COST("RemainingTotalCost"),
    @XmlEnumValue("RemainingTotalUnits")
    REMAINING_TOTAL_UNITS("RemainingTotalUnits"),
    @XmlEnumValue("ResumeDate")
    RESUME_DATE("ResumeDate"),
    @XmlEnumValue("ReviewFinishDate")
    REVIEW_FINISH_DATE("ReviewFinishDate"),
    @XmlEnumValue("ReviewRequired")
    REVIEW_REQUIRED("ReviewRequired"),
    @XmlEnumValue("ReviewStatus")
    REVIEW_STATUS("ReviewStatus"),
    @XmlEnumValue("SchedulePercentComplete")
    SCHEDULE_PERCENT_COMPLETE("SchedulePercentComplete"),
    @XmlEnumValue("SchedulePerformanceIndex")
    SCHEDULE_PERFORMANCE_INDEX("SchedulePerformanceIndex"),
    @XmlEnumValue("SchedulePerformanceIndexLaborUnits")
    SCHEDULE_PERFORMANCE_INDEX_LABOR_UNITS("SchedulePerformanceIndexLaborUnits"),
    @XmlEnumValue("ScheduleVariance")
    SCHEDULE_VARIANCE("ScheduleVariance"),
    @XmlEnumValue("ScheduleVarianceIndex")
    SCHEDULE_VARIANCE_INDEX("ScheduleVarianceIndex"),
    @XmlEnumValue("ScheduleVarianceIndexLaborUnits")
    SCHEDULE_VARIANCE_INDEX_LABOR_UNITS("ScheduleVarianceIndexLaborUnits"),
    @XmlEnumValue("ScheduleVarianceLaborUnits")
    SCHEDULE_VARIANCE_LABOR_UNITS("ScheduleVarianceLaborUnits"),
    @XmlEnumValue("SecondaryConstraintDate")
    SECONDARY_CONSTRAINT_DATE("SecondaryConstraintDate"),
    @XmlEnumValue("SecondaryConstraintType")
    SECONDARY_CONSTRAINT_TYPE("SecondaryConstraintType"),
    @XmlEnumValue("StartDate")
    START_DATE("StartDate"),
    @XmlEnumValue("StartDate1Variance")
    START_DATE_1_VARIANCE("StartDate1Variance"),
    @XmlEnumValue("StartDateVariance")
    START_DATE_VARIANCE("StartDateVariance"),
    @XmlEnumValue("Status")
    STATUS("Status"),
    @XmlEnumValue("StatusCode")
    STATUS_CODE("StatusCode"),
    @XmlEnumValue("SuspendDate")
    SUSPEND_DATE("SuspendDate"),
    @XmlEnumValue("ToCompletePerformanceIndex")
    TO_COMPLETE_PERFORMANCE_INDEX("ToCompletePerformanceIndex"),
    @XmlEnumValue("TotalCost1Variance")
    TOTAL_COST_1_VARIANCE("TotalCost1Variance"),
    @XmlEnumValue("TotalCostVariance")
    TOTAL_COST_VARIANCE("TotalCostVariance"),
    @XmlEnumValue("TotalFloat")
    TOTAL_FLOAT("TotalFloat"),
    @XmlEnumValue("Type")
    TYPE("Type"),
    @XmlEnumValue("UnitsPercentComplete")
    UNITS_PERCENT_COMPLETE("UnitsPercentComplete"),
    @XmlEnumValue("UnreadCommentCount")
    UNREAD_COMMENT_COUNT("UnreadCommentCount"),
    @XmlEnumValue("WBSCode")
    WBS_CODE("WBSCode"),
    @XmlEnumValue("WBSName")
    WBS_NAME("WBSName"),
    @XmlEnumValue("WBSObjectId")
    WBS_OBJECT_ID("WBSObjectId"),
    @XmlEnumValue("WBSPath")
    WBS_PATH("WBSPath");
    private final String value;

    ActivityFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActivityFieldType fromValue(String v) {
        for (ActivityFieldType c: ActivityFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
