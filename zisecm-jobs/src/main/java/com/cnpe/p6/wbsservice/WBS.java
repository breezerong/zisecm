
package com.cnpe.p6.wbsservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>WBS complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="WBS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnticipatedFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="AnticipatedStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Code" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ContainsSummaryData" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CurrentBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CurrentVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="DistributedCurrentBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EarnedValueComputeType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Activity Percent Complete"/>
 *               &lt;enumeration value="0 / 100"/>
 *               &lt;enumeration value="50 / 50"/>
 *               &lt;enumeration value="Custom Percent Complete"/>
 *               &lt;enumeration value="WBS Milestones Percent Complete"/>
 *               &lt;enumeration value="Activity Percent Complete Using Resource Curves"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="EarnedValueETCComputeType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="ETC = Remaining Cost for Activity"/>
 *               &lt;enumeration value="PF = 1"/>
 *               &lt;enumeration value="PF = Custom Value"/>
 *               &lt;enumeration value="PF = 1 / CPI"/>
 *               &lt;enumeration value="PF = 1 / (CPI * SPI)"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="EarnedValueETCUserValue" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="EarnedValueUserPercent" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ForecastFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ForecastStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="GUID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\{[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\}|"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IndependentETCLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="IndependentETCTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="IntegratedType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="ERP"/>
 *               &lt;enumeration value="Gateway"/>
 *               &lt;enumeration value="PrimeScope"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IntegratedWBS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsBaseline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsWorkPackage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastUpdateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Name" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OBSName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OBSObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OriginalBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ParentObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ProjectId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ProposedBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RolledUpFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="RolledUpStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Status" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Planned"/>
 *               &lt;enumeration value="Active"/>
 *               &lt;enumeration value="Inactive"/>
 *               &lt;enumeration value="What-If"/>
 *               &lt;enumeration value="Requested"/>
 *               &lt;enumeration value="Template"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="StatusReviewerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusReviewerObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryAccountingVarianceByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAccountingVarianceByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryActualDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryActualLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryActualThisPeriodCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualThisPeriodLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualThisPeriodLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualThisPeriodMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualThisPeriodNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualThisPeriodNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualValueByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryActualValueByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryAtCompletionTotalCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineCompletedActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineInProgressActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineNotStartedActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryBaselineTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBudgetAtCompletionByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryBudgetAtCompletionByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCompletedActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostPercentOfPlanned" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostPerformanceIndexByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostPerformanceIndexByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostVarianceByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostVarianceByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostVarianceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostVarianceIndexByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryCostVarianceIndexByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryDurationPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryDurationPercentOfPlanned" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryDurationVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEarnedValueByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEarnedValueByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEstimateAtCompletionByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEstimateAtCompletionByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEstimateAtCompletionHighPercentByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEstimateAtCompletionLowPercentByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEstimateToCompleteByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryEstimateToCompleteByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryExpenseCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryExpenseCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryFinishDateVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryInProgressActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryLaborCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryLaborCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryLaborUnitsPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryLaborUnitsVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryMaterialCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryMaterialCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryNonLaborCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryNonLaborCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryNonLaborUnitsPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryNonLaborUnitsVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryNotStartedActivityCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SummaryPerformancePercentCompleteByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPerformancePercentCompleteByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedValueByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryPlannedValueByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryProgressFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SummaryRemainingTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummarySchedulePercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummarySchedulePercentCompleteByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummarySchedulePerformanceIndexByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummarySchedulePerformanceIndexByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryScheduleVarianceByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryScheduleVarianceByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryScheduleVarianceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryScheduleVarianceIndexByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryScheduleVarianceIndexByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryStartDateVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryToCompletePerformanceIndexByCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryTotalCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryTotalFloat" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryUnitsPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SummaryVarianceAtCompletionByLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalBenefitPlan" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalBenefitPlanTally" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalSpendingPlan" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalSpendingPlanTally" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UnallocatedBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UndistributedCurrentVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="WBSCategoryObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WBSMilestonePercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WBS", propOrder = {
    "anticipatedFinishDate",
    "anticipatedStartDate",
    "code",
    "containsSummaryData",
    "createDate",
    "createUser",
    "currentBudget",
    "currentVariance",
    "distributedCurrentBudget",
    "earnedValueComputeType",
    "earnedValueETCComputeType",
    "earnedValueETCUserValue",
    "earnedValueUserPercent",
    "finishDate",
    "forecastFinishDate",
    "forecastStartDate",
    "guid",
    "independentETCLaborUnits",
    "independentETCTotalCost",
    "integratedType",
    "integratedWBS",
    "isBaseline",
    "isTemplate",
    "isWorkPackage",
    "lastUpdateDate",
    "lastUpdateUser",
    "name",
    "obsName",
    "obsObjectId",
    "objectId",
    "originalBudget",
    "parentObjectId",
    "projectId",
    "projectObjectId",
    "proposedBudget",
    "rolledUpFinishDate",
    "rolledUpStartDate",
    "sequenceNumber",
    "startDate",
    "status",
    "statusReviewerName",
    "statusReviewerObjectId",
    "summaryAccountingVarianceByCost",
    "summaryAccountingVarianceByLaborUnits",
    "summaryActivityCount",
    "summaryActualDuration",
    "summaryActualExpenseCost",
    "summaryActualFinishDate",
    "summaryActualLaborCost",
    "summaryActualLaborUnits",
    "summaryActualMaterialCost",
    "summaryActualNonLaborCost",
    "summaryActualNonLaborUnits",
    "summaryActualStartDate",
    "summaryActualThisPeriodCost",
    "summaryActualThisPeriodLaborCost",
    "summaryActualThisPeriodLaborUnits",
    "summaryActualThisPeriodMaterialCost",
    "summaryActualThisPeriodNonLaborCost",
    "summaryActualThisPeriodNonLaborUnits",
    "summaryActualTotalCost",
    "summaryActualValueByCost",
    "summaryActualValueByLaborUnits",
    "summaryAtCompletionDuration",
    "summaryAtCompletionExpenseCost",
    "summaryAtCompletionLaborCost",
    "summaryAtCompletionLaborUnits",
    "summaryAtCompletionMaterialCost",
    "summaryAtCompletionNonLaborCost",
    "summaryAtCompletionNonLaborUnits",
    "summaryAtCompletionTotalCost",
    "summaryAtCompletionTotalCostVariance",
    "summaryBaselineCompletedActivityCount",
    "summaryBaselineDuration",
    "summaryBaselineExpenseCost",
    "summaryBaselineFinishDate",
    "summaryBaselineInProgressActivityCount",
    "summaryBaselineLaborCost",
    "summaryBaselineLaborUnits",
    "summaryBaselineMaterialCost",
    "summaryBaselineNonLaborCost",
    "summaryBaselineNonLaborUnits",
    "summaryBaselineNotStartedActivityCount",
    "summaryBaselineStartDate",
    "summaryBaselineTotalCost",
    "summaryBudgetAtCompletionByCost",
    "summaryBudgetAtCompletionByLaborUnits",
    "summaryCompletedActivityCount",
    "summaryCostPercentComplete",
    "summaryCostPercentOfPlanned",
    "summaryCostPerformanceIndexByCost",
    "summaryCostPerformanceIndexByLaborUnits",
    "summaryCostVarianceByCost",
    "summaryCostVarianceByLaborUnits",
    "summaryCostVarianceIndex",
    "summaryCostVarianceIndexByCost",
    "summaryCostVarianceIndexByLaborUnits",
    "summaryDurationPercentComplete",
    "summaryDurationPercentOfPlanned",
    "summaryDurationVariance",
    "summaryEarnedValueByCost",
    "summaryEarnedValueByLaborUnits",
    "summaryEstimateAtCompletionByCost",
    "summaryEstimateAtCompletionByLaborUnits",
    "summaryEstimateAtCompletionHighPercentByLaborUnits",
    "summaryEstimateAtCompletionLowPercentByLaborUnits",
    "summaryEstimateToCompleteByCost",
    "summaryEstimateToCompleteByLaborUnits",
    "summaryExpenseCostPercentComplete",
    "summaryExpenseCostVariance",
    "summaryFinishDateVariance",
    "summaryInProgressActivityCount",
    "summaryLaborCostPercentComplete",
    "summaryLaborCostVariance",
    "summaryLaborUnitsPercentComplete",
    "summaryLaborUnitsVariance",
    "summaryMaterialCostPercentComplete",
    "summaryMaterialCostVariance",
    "summaryNonLaborCostPercentComplete",
    "summaryNonLaborCostVariance",
    "summaryNonLaborUnitsPercentComplete",
    "summaryNonLaborUnitsVariance",
    "summaryNotStartedActivityCount",
    "summaryPerformancePercentCompleteByCost",
    "summaryPerformancePercentCompleteByLaborUnits",
    "summaryPlannedCost",
    "summaryPlannedDuration",
    "summaryPlannedExpenseCost",
    "summaryPlannedFinishDate",
    "summaryPlannedLaborCost",
    "summaryPlannedLaborUnits",
    "summaryPlannedMaterialCost",
    "summaryPlannedNonLaborCost",
    "summaryPlannedNonLaborUnits",
    "summaryPlannedStartDate",
    "summaryPlannedValueByCost",
    "summaryPlannedValueByLaborUnits",
    "summaryProgressFinishDate",
    "summaryRemainingDuration",
    "summaryRemainingExpenseCost",
    "summaryRemainingFinishDate",
    "summaryRemainingLaborCost",
    "summaryRemainingLaborUnits",
    "summaryRemainingMaterialCost",
    "summaryRemainingNonLaborCost",
    "summaryRemainingNonLaborUnits",
    "summaryRemainingStartDate",
    "summaryRemainingTotalCost",
    "summarySchedulePercentComplete",
    "summarySchedulePercentCompleteByLaborUnits",
    "summarySchedulePerformanceIndexByCost",
    "summarySchedulePerformanceIndexByLaborUnits",
    "summaryScheduleVarianceByCost",
    "summaryScheduleVarianceByLaborUnits",
    "summaryScheduleVarianceIndex",
    "summaryScheduleVarianceIndexByCost",
    "summaryScheduleVarianceIndexByLaborUnits",
    "summaryStartDateVariance",
    "summaryToCompletePerformanceIndexByCost",
    "summaryTotalCostVariance",
    "summaryTotalFloat",
    "summaryUnitsPercentComplete",
    "summaryVarianceAtCompletionByLaborUnits",
    "totalBenefitPlan",
    "totalBenefitPlanTally",
    "totalSpendingPlan",
    "totalSpendingPlanTally",
    "unallocatedBudget",
    "undistributedCurrentVariance",
    "wbsCategoryObjectId",
    "wbsMilestonePercentComplete"
})
public class WBS {

    @XmlElementRef(name = "AnticipatedFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> anticipatedFinishDate;
    @XmlElementRef(name = "AnticipatedStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> anticipatedStartDate;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "ContainsSummaryData")
    protected Boolean containsSummaryData;
    @XmlElementRef(name = "CreateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> createDate;
    @XmlElement(name = "CreateUser")
    protected String createUser;
    @XmlElementRef(name = "CurrentBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> currentBudget;
    @XmlElementRef(name = "CurrentVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> currentVariance;
    @XmlElementRef(name = "DistributedCurrentBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> distributedCurrentBudget;
    @XmlElement(name = "EarnedValueComputeType")
    protected String earnedValueComputeType;
    @XmlElement(name = "EarnedValueETCComputeType")
    protected String earnedValueETCComputeType;
    @XmlElementRef(name = "EarnedValueETCUserValue", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> earnedValueETCUserValue;
    @XmlElementRef(name = "EarnedValueUserPercent", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> earnedValueUserPercent;
    @XmlElementRef(name = "FinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> finishDate;
    @XmlElementRef(name = "ForecastFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> forecastFinishDate;
    @XmlElementRef(name = "ForecastStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> forecastStartDate;
    @XmlElement(name = "GUID")
    protected String guid;
    @XmlElementRef(name = "IndependentETCLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> independentETCLaborUnits;
    @XmlElementRef(name = "IndependentETCTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> independentETCTotalCost;
    @XmlElement(name = "IntegratedType")
    protected String integratedType;
    @XmlElement(name = "IntegratedWBS")
    protected Boolean integratedWBS;
    @XmlElement(name = "IsBaseline")
    protected Boolean isBaseline;
    @XmlElement(name = "IsTemplate")
    protected Boolean isTemplate;
    @XmlElement(name = "IsWorkPackage")
    protected Boolean isWorkPackage;
    @XmlElementRef(name = "LastUpdateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastUpdateDate;
    @XmlElement(name = "LastUpdateUser")
    protected String lastUpdateUser;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "OBSName")
    protected String obsName;
    @XmlElement(name = "OBSObjectId")
    protected Integer obsObjectId;
    @XmlElement(name = "ObjectId")
    protected Integer objectId;
    @XmlElementRef(name = "OriginalBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> originalBudget;
    @XmlElementRef(name = "ParentObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> parentObjectId;
    @XmlElement(name = "ProjectId")
    protected String projectId;
    @XmlElement(name = "ProjectObjectId")
    protected Integer projectObjectId;
    @XmlElementRef(name = "ProposedBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> proposedBudget;
    @XmlElementRef(name = "RolledUpFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> rolledUpFinishDate;
    @XmlElementRef(name = "RolledUpStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> rolledUpStartDate;
    @XmlElement(name = "SequenceNumber")
    protected Integer sequenceNumber;
    @XmlElementRef(name = "StartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> startDate;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "StatusReviewerName")
    protected String statusReviewerName;
    @XmlElementRef(name = "StatusReviewerObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> statusReviewerObjectId;
    @XmlElementRef(name = "SummaryAccountingVarianceByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAccountingVarianceByCost;
    @XmlElementRef(name = "SummaryAccountingVarianceByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAccountingVarianceByLaborUnits;
    @XmlElementRef(name = "SummaryActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryActivityCount;
    @XmlElementRef(name = "SummaryActualDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualDuration;
    @XmlElementRef(name = "SummaryActualExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualExpenseCost;
    @XmlElementRef(name = "SummaryActualFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryActualFinishDate;
    @XmlElementRef(name = "SummaryActualLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualLaborCost;
    @XmlElementRef(name = "SummaryActualLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualLaborUnits;
    @XmlElementRef(name = "SummaryActualMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualMaterialCost;
    @XmlElementRef(name = "SummaryActualNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualNonLaborCost;
    @XmlElementRef(name = "SummaryActualNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualNonLaborUnits;
    @XmlElementRef(name = "SummaryActualStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryActualStartDate;
    @XmlElementRef(name = "SummaryActualThisPeriodCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodCost;
    @XmlElementRef(name = "SummaryActualThisPeriodLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodLaborCost;
    @XmlElementRef(name = "SummaryActualThisPeriodLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodLaborUnits;
    @XmlElementRef(name = "SummaryActualThisPeriodMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodMaterialCost;
    @XmlElementRef(name = "SummaryActualThisPeriodNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodNonLaborCost;
    @XmlElementRef(name = "SummaryActualThisPeriodNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodNonLaborUnits;
    @XmlElementRef(name = "SummaryActualTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualTotalCost;
    @XmlElementRef(name = "SummaryActualValueByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualValueByCost;
    @XmlElementRef(name = "SummaryActualValueByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualValueByLaborUnits;
    @XmlElementRef(name = "SummaryAtCompletionDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionDuration;
    @XmlElementRef(name = "SummaryAtCompletionExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionExpenseCost;
    @XmlElementRef(name = "SummaryAtCompletionLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionLaborCost;
    @XmlElementRef(name = "SummaryAtCompletionLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionLaborUnits;
    @XmlElementRef(name = "SummaryAtCompletionMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionMaterialCost;
    @XmlElementRef(name = "SummaryAtCompletionNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionNonLaborCost;
    @XmlElementRef(name = "SummaryAtCompletionNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionNonLaborUnits;
    @XmlElementRef(name = "SummaryAtCompletionTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionTotalCost;
    @XmlElementRef(name = "SummaryAtCompletionTotalCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionTotalCostVariance;
    @XmlElementRef(name = "SummaryBaselineCompletedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryBaselineCompletedActivityCount;
    @XmlElementRef(name = "SummaryBaselineDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineDuration;
    @XmlElementRef(name = "SummaryBaselineExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineExpenseCost;
    @XmlElementRef(name = "SummaryBaselineFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryBaselineFinishDate;
    @XmlElementRef(name = "SummaryBaselineInProgressActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryBaselineInProgressActivityCount;
    @XmlElementRef(name = "SummaryBaselineLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineLaborCost;
    @XmlElementRef(name = "SummaryBaselineLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineLaborUnits;
    @XmlElementRef(name = "SummaryBaselineMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineMaterialCost;
    @XmlElementRef(name = "SummaryBaselineNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineNonLaborCost;
    @XmlElementRef(name = "SummaryBaselineNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineNonLaborUnits;
    @XmlElementRef(name = "SummaryBaselineNotStartedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryBaselineNotStartedActivityCount;
    @XmlElementRef(name = "SummaryBaselineStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryBaselineStartDate;
    @XmlElementRef(name = "SummaryBaselineTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineTotalCost;
    @XmlElementRef(name = "SummaryBudgetAtCompletionByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBudgetAtCompletionByCost;
    @XmlElementRef(name = "SummaryBudgetAtCompletionByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBudgetAtCompletionByLaborUnits;
    @XmlElementRef(name = "SummaryCompletedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryCompletedActivityCount;
    @XmlElementRef(name = "SummaryCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPercentComplete;
    @XmlElementRef(name = "SummaryCostPercentOfPlanned", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPercentOfPlanned;
    @XmlElementRef(name = "SummaryCostPerformanceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPerformanceIndexByCost;
    @XmlElementRef(name = "SummaryCostPerformanceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPerformanceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryCostVarianceByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceByCost;
    @XmlElementRef(name = "SummaryCostVarianceByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceByLaborUnits;
    @XmlElementRef(name = "SummaryCostVarianceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceIndex;
    @XmlElementRef(name = "SummaryCostVarianceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceIndexByCost;
    @XmlElementRef(name = "SummaryCostVarianceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryDurationPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryDurationPercentComplete;
    @XmlElementRef(name = "SummaryDurationPercentOfPlanned", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryDurationPercentOfPlanned;
    @XmlElementRef(name = "SummaryDurationVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryDurationVariance;
    @XmlElementRef(name = "SummaryEarnedValueByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEarnedValueByCost;
    @XmlElementRef(name = "SummaryEarnedValueByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEarnedValueByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateAtCompletionByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionByCost;
    @XmlElementRef(name = "SummaryEstimateAtCompletionByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateAtCompletionHighPercentByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionHighPercentByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateAtCompletionLowPercentByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionLowPercentByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateToCompleteByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateToCompleteByCost;
    @XmlElementRef(name = "SummaryEstimateToCompleteByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateToCompleteByLaborUnits;
    @XmlElementRef(name = "SummaryExpenseCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryExpenseCostPercentComplete;
    @XmlElementRef(name = "SummaryExpenseCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryExpenseCostVariance;
    @XmlElementRef(name = "SummaryFinishDateVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryFinishDateVariance;
    @XmlElementRef(name = "SummaryInProgressActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryInProgressActivityCount;
    @XmlElementRef(name = "SummaryLaborCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborCostPercentComplete;
    @XmlElementRef(name = "SummaryLaborCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborCostVariance;
    @XmlElementRef(name = "SummaryLaborUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborUnitsPercentComplete;
    @XmlElementRef(name = "SummaryLaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborUnitsVariance;
    @XmlElementRef(name = "SummaryMaterialCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryMaterialCostPercentComplete;
    @XmlElementRef(name = "SummaryMaterialCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryMaterialCostVariance;
    @XmlElementRef(name = "SummaryNonLaborCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborCostPercentComplete;
    @XmlElementRef(name = "SummaryNonLaborCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborCostVariance;
    @XmlElementRef(name = "SummaryNonLaborUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborUnitsPercentComplete;
    @XmlElementRef(name = "SummaryNonLaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborUnitsVariance;
    @XmlElementRef(name = "SummaryNotStartedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryNotStartedActivityCount;
    @XmlElementRef(name = "SummaryPerformancePercentCompleteByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPerformancePercentCompleteByCost;
    @XmlElementRef(name = "SummaryPerformancePercentCompleteByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPerformancePercentCompleteByLaborUnits;
    @XmlElementRef(name = "SummaryPlannedCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedCost;
    @XmlElementRef(name = "SummaryPlannedDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedDuration;
    @XmlElementRef(name = "SummaryPlannedExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedExpenseCost;
    @XmlElementRef(name = "SummaryPlannedFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryPlannedFinishDate;
    @XmlElementRef(name = "SummaryPlannedLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedLaborCost;
    @XmlElementRef(name = "SummaryPlannedLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedLaborUnits;
    @XmlElementRef(name = "SummaryPlannedMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedMaterialCost;
    @XmlElementRef(name = "SummaryPlannedNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedNonLaborCost;
    @XmlElementRef(name = "SummaryPlannedNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedNonLaborUnits;
    @XmlElementRef(name = "SummaryPlannedStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryPlannedStartDate;
    @XmlElementRef(name = "SummaryPlannedValueByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedValueByCost;
    @XmlElementRef(name = "SummaryPlannedValueByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedValueByLaborUnits;
    @XmlElementRef(name = "SummaryProgressFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryProgressFinishDate;
    @XmlElementRef(name = "SummaryRemainingDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingDuration;
    @XmlElementRef(name = "SummaryRemainingExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingExpenseCost;
    @XmlElementRef(name = "SummaryRemainingFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryRemainingFinishDate;
    @XmlElementRef(name = "SummaryRemainingLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingLaborCost;
    @XmlElementRef(name = "SummaryRemainingLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingLaborUnits;
    @XmlElementRef(name = "SummaryRemainingMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingMaterialCost;
    @XmlElementRef(name = "SummaryRemainingNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingNonLaborCost;
    @XmlElementRef(name = "SummaryRemainingNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingNonLaborUnits;
    @XmlElementRef(name = "SummaryRemainingStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryRemainingStartDate;
    @XmlElementRef(name = "SummaryRemainingTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingTotalCost;
    @XmlElementRef(name = "SummarySchedulePercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePercentComplete;
    @XmlElementRef(name = "SummarySchedulePercentCompleteByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePercentCompleteByLaborUnits;
    @XmlElementRef(name = "SummarySchedulePerformanceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePerformanceIndexByCost;
    @XmlElementRef(name = "SummarySchedulePerformanceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePerformanceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryScheduleVarianceByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceByCost;
    @XmlElementRef(name = "SummaryScheduleVarianceByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceByLaborUnits;
    @XmlElementRef(name = "SummaryScheduleVarianceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceIndex;
    @XmlElementRef(name = "SummaryScheduleVarianceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceIndexByCost;
    @XmlElementRef(name = "SummaryScheduleVarianceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryStartDateVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryStartDateVariance;
    @XmlElementRef(name = "SummaryToCompletePerformanceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryToCompletePerformanceIndexByCost;
    @XmlElementRef(name = "SummaryTotalCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryTotalCostVariance;
    @XmlElementRef(name = "SummaryTotalFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryTotalFloat;
    @XmlElementRef(name = "SummaryUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryUnitsPercentComplete;
    @XmlElementRef(name = "SummaryVarianceAtCompletionByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryVarianceAtCompletionByLaborUnits;
    @XmlElementRef(name = "TotalBenefitPlan", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalBenefitPlan;
    @XmlElementRef(name = "TotalBenefitPlanTally", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalBenefitPlanTally;
    @XmlElementRef(name = "TotalSpendingPlan", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalSpendingPlan;
    @XmlElementRef(name = "TotalSpendingPlanTally", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalSpendingPlanTally;
    @XmlElementRef(name = "UnallocatedBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> unallocatedBudget;
    @XmlElementRef(name = "UndistributedCurrentVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> undistributedCurrentVariance;
    @XmlElementRef(name = "WBSCategoryObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> wbsCategoryObjectId;
    @XmlElementRef(name = "WBSMilestonePercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/WBS/V2", type = JAXBElement.class)
    protected JAXBElement<Double> wbsMilestonePercentComplete;

    /**
     * 获取anticipatedFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getAnticipatedFinishDate() {
        return anticipatedFinishDate;
    }

    /**
     * 设置anticipatedFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setAnticipatedFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.anticipatedFinishDate = value;
    }

    /**
     * 获取anticipatedStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getAnticipatedStartDate() {
        return anticipatedStartDate;
    }

    /**
     * 设置anticipatedStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setAnticipatedStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.anticipatedStartDate = value;
    }

    /**
     * 获取code属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * 获取containsSummaryData属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isContainsSummaryData() {
        return containsSummaryData;
    }

    /**
     * 设置containsSummaryData属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContainsSummaryData(Boolean value) {
        this.containsSummaryData = value;
    }

    /**
     * 获取createDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCreateDate() {
        return createDate;
    }

    /**
     * 设置createDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCreateDate(JAXBElement<XMLGregorianCalendar> value) {
        this.createDate = value;
    }

    /**
     * 获取createUser属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置createUser属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateUser(String value) {
        this.createUser = value;
    }

    /**
     * 获取currentBudget属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCurrentBudget() {
        return currentBudget;
    }

    /**
     * 设置currentBudget属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCurrentBudget(JAXBElement<Double> value) {
        this.currentBudget = value;
    }

    /**
     * 获取currentVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCurrentVariance() {
        return currentVariance;
    }

    /**
     * 设置currentVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCurrentVariance(JAXBElement<Double> value) {
        this.currentVariance = value;
    }

    /**
     * 获取distributedCurrentBudget属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getDistributedCurrentBudget() {
        return distributedCurrentBudget;
    }

    /**
     * 设置distributedCurrentBudget属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setDistributedCurrentBudget(JAXBElement<Double> value) {
        this.distributedCurrentBudget = value;
    }

    /**
     * 获取earnedValueComputeType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarnedValueComputeType() {
        return earnedValueComputeType;
    }

    /**
     * 设置earnedValueComputeType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarnedValueComputeType(String value) {
        this.earnedValueComputeType = value;
    }

    /**
     * 获取earnedValueETCComputeType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarnedValueETCComputeType() {
        return earnedValueETCComputeType;
    }

    /**
     * 设置earnedValueETCComputeType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarnedValueETCComputeType(String value) {
        this.earnedValueETCComputeType = value;
    }

    /**
     * 获取earnedValueETCUserValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEarnedValueETCUserValue() {
        return earnedValueETCUserValue;
    }

    /**
     * 设置earnedValueETCUserValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEarnedValueETCUserValue(JAXBElement<Double> value) {
        this.earnedValueETCUserValue = value;
    }

    /**
     * 获取earnedValueUserPercent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEarnedValueUserPercent() {
        return earnedValueUserPercent;
    }

    /**
     * 设置earnedValueUserPercent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEarnedValueUserPercent(JAXBElement<Double> value) {
        this.earnedValueUserPercent = value;
    }

    /**
     * 获取finishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFinishDate() {
        return finishDate;
    }

    /**
     * 设置finishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.finishDate = value;
    }

    /**
     * 获取forecastFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getForecastFinishDate() {
        return forecastFinishDate;
    }

    /**
     * 设置forecastFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setForecastFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.forecastFinishDate = value;
    }

    /**
     * 获取forecastStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getForecastStartDate() {
        return forecastStartDate;
    }

    /**
     * 设置forecastStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setForecastStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.forecastStartDate = value;
    }

    /**
     * 获取guid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * 设置guid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * 获取independentETCLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getIndependentETCLaborUnits() {
        return independentETCLaborUnits;
    }

    /**
     * 设置independentETCLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setIndependentETCLaborUnits(JAXBElement<Double> value) {
        this.independentETCLaborUnits = value;
    }

    /**
     * 获取independentETCTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getIndependentETCTotalCost() {
        return independentETCTotalCost;
    }

    /**
     * 设置independentETCTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setIndependentETCTotalCost(JAXBElement<Double> value) {
        this.independentETCTotalCost = value;
    }

    /**
     * 获取integratedType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntegratedType() {
        return integratedType;
    }

    /**
     * 设置integratedType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntegratedType(String value) {
        this.integratedType = value;
    }

    /**
     * 获取integratedWBS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIntegratedWBS() {
        return integratedWBS;
    }

    /**
     * 设置integratedWBS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIntegratedWBS(Boolean value) {
        this.integratedWBS = value;
    }

    /**
     * 获取isBaseline属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBaseline() {
        return isBaseline;
    }

    /**
     * 设置isBaseline属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBaseline(Boolean value) {
        this.isBaseline = value;
    }

    /**
     * 获取isTemplate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTemplate() {
        return isTemplate;
    }

    /**
     * 设置isTemplate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTemplate(Boolean value) {
        this.isTemplate = value;
    }

    /**
     * 获取isWorkPackage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsWorkPackage() {
        return isWorkPackage;
    }

    /**
     * 设置isWorkPackage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsWorkPackage(Boolean value) {
        this.isWorkPackage = value;
    }

    /**
     * 获取lastUpdateDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * 设置lastUpdateDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastUpdateDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lastUpdateDate = value;
    }

    /**
     * 获取lastUpdateUser属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * 设置lastUpdateUser属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateUser(String value) {
        this.lastUpdateUser = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取obsName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOBSName() {
        return obsName;
    }

    /**
     * 设置obsName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOBSName(String value) {
        this.obsName = value;
    }

    /**
     * 获取obsObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOBSObjectId() {
        return obsObjectId;
    }

    /**
     * 设置obsObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOBSObjectId(Integer value) {
        this.obsObjectId = value;
    }

    /**
     * 获取objectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getObjectId() {
        return objectId;
    }

    /**
     * 设置objectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setObjectId(Integer value) {
        this.objectId = value;
    }

    /**
     * 获取originalBudget属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getOriginalBudget() {
        return originalBudget;
    }

    /**
     * 设置originalBudget属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setOriginalBudget(JAXBElement<Double> value) {
        this.originalBudget = value;
    }

    /**
     * 获取parentObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getParentObjectId() {
        return parentObjectId;
    }

    /**
     * 设置parentObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setParentObjectId(JAXBElement<Integer> value) {
        this.parentObjectId = value;
    }

    /**
     * 获取projectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * 设置projectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectId(String value) {
        this.projectId = value;
    }

    /**
     * 获取projectObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProjectObjectId() {
        return projectObjectId;
    }

    /**
     * 设置projectObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProjectObjectId(Integer value) {
        this.projectObjectId = value;
    }

    /**
     * 获取proposedBudget属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getProposedBudget() {
        return proposedBudget;
    }

    /**
     * 设置proposedBudget属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setProposedBudget(JAXBElement<Double> value) {
        this.proposedBudget = value;
    }

    /**
     * 获取rolledUpFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getRolledUpFinishDate() {
        return rolledUpFinishDate;
    }

    /**
     * 设置rolledUpFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setRolledUpFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.rolledUpFinishDate = value;
    }

    /**
     * 获取rolledUpStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getRolledUpStartDate() {
        return rolledUpStartDate;
    }

    /**
     * 设置rolledUpStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setRolledUpStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.rolledUpStartDate = value;
    }

    /**
     * 获取sequenceNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * 设置sequenceNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequenceNumber(Integer value) {
        this.sequenceNumber = value;
    }

    /**
     * 获取startDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getStartDate() {
        return startDate;
    }

    /**
     * 设置startDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.startDate = value;
    }

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * 获取statusReviewerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusReviewerName() {
        return statusReviewerName;
    }

    /**
     * 设置statusReviewerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusReviewerName(String value) {
        this.statusReviewerName = value;
    }

    /**
     * 获取statusReviewerObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getStatusReviewerObjectId() {
        return statusReviewerObjectId;
    }

    /**
     * 设置statusReviewerObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setStatusReviewerObjectId(JAXBElement<Integer> value) {
        this.statusReviewerObjectId = value;
    }

    /**
     * 获取summaryAccountingVarianceByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAccountingVarianceByCost() {
        return summaryAccountingVarianceByCost;
    }

    /**
     * 设置summaryAccountingVarianceByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAccountingVarianceByCost(JAXBElement<Double> value) {
        this.summaryAccountingVarianceByCost = value;
    }

    /**
     * 获取summaryAccountingVarianceByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAccountingVarianceByLaborUnits() {
        return summaryAccountingVarianceByLaborUnits;
    }

    /**
     * 设置summaryAccountingVarianceByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAccountingVarianceByLaborUnits(JAXBElement<Double> value) {
        this.summaryAccountingVarianceByLaborUnits = value;
    }

    /**
     * 获取summaryActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryActivityCount() {
        return summaryActivityCount;
    }

    /**
     * 设置summaryActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryActivityCount(JAXBElement<Integer> value) {
        this.summaryActivityCount = value;
    }

    /**
     * 获取summaryActualDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualDuration() {
        return summaryActualDuration;
    }

    /**
     * 设置summaryActualDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualDuration(JAXBElement<Double> value) {
        this.summaryActualDuration = value;
    }

    /**
     * 获取summaryActualExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualExpenseCost() {
        return summaryActualExpenseCost;
    }

    /**
     * 设置summaryActualExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualExpenseCost(JAXBElement<Double> value) {
        this.summaryActualExpenseCost = value;
    }

    /**
     * 获取summaryActualFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryActualFinishDate() {
        return summaryActualFinishDate;
    }

    /**
     * 设置summaryActualFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryActualFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryActualFinishDate = value;
    }

    /**
     * 获取summaryActualLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualLaborCost() {
        return summaryActualLaborCost;
    }

    /**
     * 设置summaryActualLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualLaborCost(JAXBElement<Double> value) {
        this.summaryActualLaborCost = value;
    }

    /**
     * 获取summaryActualLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualLaborUnits() {
        return summaryActualLaborUnits;
    }

    /**
     * 设置summaryActualLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualLaborUnits(JAXBElement<Double> value) {
        this.summaryActualLaborUnits = value;
    }

    /**
     * 获取summaryActualMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualMaterialCost() {
        return summaryActualMaterialCost;
    }

    /**
     * 设置summaryActualMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualMaterialCost(JAXBElement<Double> value) {
        this.summaryActualMaterialCost = value;
    }

    /**
     * 获取summaryActualNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualNonLaborCost() {
        return summaryActualNonLaborCost;
    }

    /**
     * 设置summaryActualNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualNonLaborCost(JAXBElement<Double> value) {
        this.summaryActualNonLaborCost = value;
    }

    /**
     * 获取summaryActualNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualNonLaborUnits() {
        return summaryActualNonLaborUnits;
    }

    /**
     * 设置summaryActualNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualNonLaborUnits(JAXBElement<Double> value) {
        this.summaryActualNonLaborUnits = value;
    }

    /**
     * 获取summaryActualStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryActualStartDate() {
        return summaryActualStartDate;
    }

    /**
     * 设置summaryActualStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryActualStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryActualStartDate = value;
    }

    /**
     * 获取summaryActualThisPeriodCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualThisPeriodCost() {
        return summaryActualThisPeriodCost;
    }

    /**
     * 设置summaryActualThisPeriodCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualThisPeriodCost(JAXBElement<Double> value) {
        this.summaryActualThisPeriodCost = value;
    }

    /**
     * 获取summaryActualThisPeriodLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualThisPeriodLaborCost() {
        return summaryActualThisPeriodLaborCost;
    }

    /**
     * 设置summaryActualThisPeriodLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualThisPeriodLaborCost(JAXBElement<Double> value) {
        this.summaryActualThisPeriodLaborCost = value;
    }

    /**
     * 获取summaryActualThisPeriodLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualThisPeriodLaborUnits() {
        return summaryActualThisPeriodLaborUnits;
    }

    /**
     * 设置summaryActualThisPeriodLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualThisPeriodLaborUnits(JAXBElement<Double> value) {
        this.summaryActualThisPeriodLaborUnits = value;
    }

    /**
     * 获取summaryActualThisPeriodMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualThisPeriodMaterialCost() {
        return summaryActualThisPeriodMaterialCost;
    }

    /**
     * 设置summaryActualThisPeriodMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualThisPeriodMaterialCost(JAXBElement<Double> value) {
        this.summaryActualThisPeriodMaterialCost = value;
    }

    /**
     * 获取summaryActualThisPeriodNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualThisPeriodNonLaborCost() {
        return summaryActualThisPeriodNonLaborCost;
    }

    /**
     * 设置summaryActualThisPeriodNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualThisPeriodNonLaborCost(JAXBElement<Double> value) {
        this.summaryActualThisPeriodNonLaborCost = value;
    }

    /**
     * 获取summaryActualThisPeriodNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualThisPeriodNonLaborUnits() {
        return summaryActualThisPeriodNonLaborUnits;
    }

    /**
     * 设置summaryActualThisPeriodNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualThisPeriodNonLaborUnits(JAXBElement<Double> value) {
        this.summaryActualThisPeriodNonLaborUnits = value;
    }

    /**
     * 获取summaryActualTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualTotalCost() {
        return summaryActualTotalCost;
    }

    /**
     * 设置summaryActualTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualTotalCost(JAXBElement<Double> value) {
        this.summaryActualTotalCost = value;
    }

    /**
     * 获取summaryActualValueByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualValueByCost() {
        return summaryActualValueByCost;
    }

    /**
     * 设置summaryActualValueByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualValueByCost(JAXBElement<Double> value) {
        this.summaryActualValueByCost = value;
    }

    /**
     * 获取summaryActualValueByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryActualValueByLaborUnits() {
        return summaryActualValueByLaborUnits;
    }

    /**
     * 设置summaryActualValueByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryActualValueByLaborUnits(JAXBElement<Double> value) {
        this.summaryActualValueByLaborUnits = value;
    }

    /**
     * 获取summaryAtCompletionDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionDuration() {
        return summaryAtCompletionDuration;
    }

    /**
     * 设置summaryAtCompletionDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionDuration(JAXBElement<Double> value) {
        this.summaryAtCompletionDuration = value;
    }

    /**
     * 获取summaryAtCompletionExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionExpenseCost() {
        return summaryAtCompletionExpenseCost;
    }

    /**
     * 设置summaryAtCompletionExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionExpenseCost(JAXBElement<Double> value) {
        this.summaryAtCompletionExpenseCost = value;
    }

    /**
     * 获取summaryAtCompletionLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionLaborCost() {
        return summaryAtCompletionLaborCost;
    }

    /**
     * 设置summaryAtCompletionLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionLaborCost(JAXBElement<Double> value) {
        this.summaryAtCompletionLaborCost = value;
    }

    /**
     * 获取summaryAtCompletionLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionLaborUnits() {
        return summaryAtCompletionLaborUnits;
    }

    /**
     * 设置summaryAtCompletionLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionLaborUnits(JAXBElement<Double> value) {
        this.summaryAtCompletionLaborUnits = value;
    }

    /**
     * 获取summaryAtCompletionMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionMaterialCost() {
        return summaryAtCompletionMaterialCost;
    }

    /**
     * 设置summaryAtCompletionMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionMaterialCost(JAXBElement<Double> value) {
        this.summaryAtCompletionMaterialCost = value;
    }

    /**
     * 获取summaryAtCompletionNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionNonLaborCost() {
        return summaryAtCompletionNonLaborCost;
    }

    /**
     * 设置summaryAtCompletionNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionNonLaborCost(JAXBElement<Double> value) {
        this.summaryAtCompletionNonLaborCost = value;
    }

    /**
     * 获取summaryAtCompletionNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionNonLaborUnits() {
        return summaryAtCompletionNonLaborUnits;
    }

    /**
     * 设置summaryAtCompletionNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionNonLaborUnits(JAXBElement<Double> value) {
        this.summaryAtCompletionNonLaborUnits = value;
    }

    /**
     * 获取summaryAtCompletionTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionTotalCost() {
        return summaryAtCompletionTotalCost;
    }

    /**
     * 设置summaryAtCompletionTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionTotalCost(JAXBElement<Double> value) {
        this.summaryAtCompletionTotalCost = value;
    }

    /**
     * 获取summaryAtCompletionTotalCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryAtCompletionTotalCostVariance() {
        return summaryAtCompletionTotalCostVariance;
    }

    /**
     * 设置summaryAtCompletionTotalCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryAtCompletionTotalCostVariance(JAXBElement<Double> value) {
        this.summaryAtCompletionTotalCostVariance = value;
    }

    /**
     * 获取summaryBaselineCompletedActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryBaselineCompletedActivityCount() {
        return summaryBaselineCompletedActivityCount;
    }

    /**
     * 设置summaryBaselineCompletedActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryBaselineCompletedActivityCount(JAXBElement<Integer> value) {
        this.summaryBaselineCompletedActivityCount = value;
    }

    /**
     * 获取summaryBaselineDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineDuration() {
        return summaryBaselineDuration;
    }

    /**
     * 设置summaryBaselineDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineDuration(JAXBElement<Double> value) {
        this.summaryBaselineDuration = value;
    }

    /**
     * 获取summaryBaselineExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineExpenseCost() {
        return summaryBaselineExpenseCost;
    }

    /**
     * 设置summaryBaselineExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineExpenseCost(JAXBElement<Double> value) {
        this.summaryBaselineExpenseCost = value;
    }

    /**
     * 获取summaryBaselineFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryBaselineFinishDate() {
        return summaryBaselineFinishDate;
    }

    /**
     * 设置summaryBaselineFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryBaselineFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryBaselineFinishDate = value;
    }

    /**
     * 获取summaryBaselineInProgressActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryBaselineInProgressActivityCount() {
        return summaryBaselineInProgressActivityCount;
    }

    /**
     * 设置summaryBaselineInProgressActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryBaselineInProgressActivityCount(JAXBElement<Integer> value) {
        this.summaryBaselineInProgressActivityCount = value;
    }

    /**
     * 获取summaryBaselineLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineLaborCost() {
        return summaryBaselineLaborCost;
    }

    /**
     * 设置summaryBaselineLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineLaborCost(JAXBElement<Double> value) {
        this.summaryBaselineLaborCost = value;
    }

    /**
     * 获取summaryBaselineLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineLaborUnits() {
        return summaryBaselineLaborUnits;
    }

    /**
     * 设置summaryBaselineLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineLaborUnits(JAXBElement<Double> value) {
        this.summaryBaselineLaborUnits = value;
    }

    /**
     * 获取summaryBaselineMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineMaterialCost() {
        return summaryBaselineMaterialCost;
    }

    /**
     * 设置summaryBaselineMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineMaterialCost(JAXBElement<Double> value) {
        this.summaryBaselineMaterialCost = value;
    }

    /**
     * 获取summaryBaselineNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineNonLaborCost() {
        return summaryBaselineNonLaborCost;
    }

    /**
     * 设置summaryBaselineNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineNonLaborCost(JAXBElement<Double> value) {
        this.summaryBaselineNonLaborCost = value;
    }

    /**
     * 获取summaryBaselineNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineNonLaborUnits() {
        return summaryBaselineNonLaborUnits;
    }

    /**
     * 设置summaryBaselineNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineNonLaborUnits(JAXBElement<Double> value) {
        this.summaryBaselineNonLaborUnits = value;
    }

    /**
     * 获取summaryBaselineNotStartedActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryBaselineNotStartedActivityCount() {
        return summaryBaselineNotStartedActivityCount;
    }

    /**
     * 设置summaryBaselineNotStartedActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryBaselineNotStartedActivityCount(JAXBElement<Integer> value) {
        this.summaryBaselineNotStartedActivityCount = value;
    }

    /**
     * 获取summaryBaselineStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryBaselineStartDate() {
        return summaryBaselineStartDate;
    }

    /**
     * 设置summaryBaselineStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryBaselineStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryBaselineStartDate = value;
    }

    /**
     * 获取summaryBaselineTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBaselineTotalCost() {
        return summaryBaselineTotalCost;
    }

    /**
     * 设置summaryBaselineTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBaselineTotalCost(JAXBElement<Double> value) {
        this.summaryBaselineTotalCost = value;
    }

    /**
     * 获取summaryBudgetAtCompletionByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBudgetAtCompletionByCost() {
        return summaryBudgetAtCompletionByCost;
    }

    /**
     * 设置summaryBudgetAtCompletionByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBudgetAtCompletionByCost(JAXBElement<Double> value) {
        this.summaryBudgetAtCompletionByCost = value;
    }

    /**
     * 获取summaryBudgetAtCompletionByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryBudgetAtCompletionByLaborUnits() {
        return summaryBudgetAtCompletionByLaborUnits;
    }

    /**
     * 设置summaryBudgetAtCompletionByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryBudgetAtCompletionByLaborUnits(JAXBElement<Double> value) {
        this.summaryBudgetAtCompletionByLaborUnits = value;
    }

    /**
     * 获取summaryCompletedActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryCompletedActivityCount() {
        return summaryCompletedActivityCount;
    }

    /**
     * 设置summaryCompletedActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryCompletedActivityCount(JAXBElement<Integer> value) {
        this.summaryCompletedActivityCount = value;
    }

    /**
     * 获取summaryCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostPercentComplete() {
        return summaryCostPercentComplete;
    }

    /**
     * 设置summaryCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostPercentComplete(JAXBElement<Double> value) {
        this.summaryCostPercentComplete = value;
    }

    /**
     * 获取summaryCostPercentOfPlanned属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostPercentOfPlanned() {
        return summaryCostPercentOfPlanned;
    }

    /**
     * 设置summaryCostPercentOfPlanned属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostPercentOfPlanned(JAXBElement<Double> value) {
        this.summaryCostPercentOfPlanned = value;
    }

    /**
     * 获取summaryCostPerformanceIndexByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostPerformanceIndexByCost() {
        return summaryCostPerformanceIndexByCost;
    }

    /**
     * 设置summaryCostPerformanceIndexByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostPerformanceIndexByCost(JAXBElement<Double> value) {
        this.summaryCostPerformanceIndexByCost = value;
    }

    /**
     * 获取summaryCostPerformanceIndexByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostPerformanceIndexByLaborUnits() {
        return summaryCostPerformanceIndexByLaborUnits;
    }

    /**
     * 设置summaryCostPerformanceIndexByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostPerformanceIndexByLaborUnits(JAXBElement<Double> value) {
        this.summaryCostPerformanceIndexByLaborUnits = value;
    }

    /**
     * 获取summaryCostVarianceByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostVarianceByCost() {
        return summaryCostVarianceByCost;
    }

    /**
     * 设置summaryCostVarianceByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostVarianceByCost(JAXBElement<Double> value) {
        this.summaryCostVarianceByCost = value;
    }

    /**
     * 获取summaryCostVarianceByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostVarianceByLaborUnits() {
        return summaryCostVarianceByLaborUnits;
    }

    /**
     * 设置summaryCostVarianceByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostVarianceByLaborUnits(JAXBElement<Double> value) {
        this.summaryCostVarianceByLaborUnits = value;
    }

    /**
     * 获取summaryCostVarianceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostVarianceIndex() {
        return summaryCostVarianceIndex;
    }

    /**
     * 设置summaryCostVarianceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostVarianceIndex(JAXBElement<Double> value) {
        this.summaryCostVarianceIndex = value;
    }

    /**
     * 获取summaryCostVarianceIndexByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostVarianceIndexByCost() {
        return summaryCostVarianceIndexByCost;
    }

    /**
     * 设置summaryCostVarianceIndexByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostVarianceIndexByCost(JAXBElement<Double> value) {
        this.summaryCostVarianceIndexByCost = value;
    }

    /**
     * 获取summaryCostVarianceIndexByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryCostVarianceIndexByLaborUnits() {
        return summaryCostVarianceIndexByLaborUnits;
    }

    /**
     * 设置summaryCostVarianceIndexByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryCostVarianceIndexByLaborUnits(JAXBElement<Double> value) {
        this.summaryCostVarianceIndexByLaborUnits = value;
    }

    /**
     * 获取summaryDurationPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryDurationPercentComplete() {
        return summaryDurationPercentComplete;
    }

    /**
     * 设置summaryDurationPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryDurationPercentComplete(JAXBElement<Double> value) {
        this.summaryDurationPercentComplete = value;
    }

    /**
     * 获取summaryDurationPercentOfPlanned属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryDurationPercentOfPlanned() {
        return summaryDurationPercentOfPlanned;
    }

    /**
     * 设置summaryDurationPercentOfPlanned属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryDurationPercentOfPlanned(JAXBElement<Double> value) {
        this.summaryDurationPercentOfPlanned = value;
    }

    /**
     * 获取summaryDurationVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryDurationVariance() {
        return summaryDurationVariance;
    }

    /**
     * 设置summaryDurationVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryDurationVariance(JAXBElement<Double> value) {
        this.summaryDurationVariance = value;
    }

    /**
     * 获取summaryEarnedValueByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEarnedValueByCost() {
        return summaryEarnedValueByCost;
    }

    /**
     * 设置summaryEarnedValueByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEarnedValueByCost(JAXBElement<Double> value) {
        this.summaryEarnedValueByCost = value;
    }

    /**
     * 获取summaryEarnedValueByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEarnedValueByLaborUnits() {
        return summaryEarnedValueByLaborUnits;
    }

    /**
     * 设置summaryEarnedValueByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEarnedValueByLaborUnits(JAXBElement<Double> value) {
        this.summaryEarnedValueByLaborUnits = value;
    }

    /**
     * 获取summaryEstimateAtCompletionByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEstimateAtCompletionByCost() {
        return summaryEstimateAtCompletionByCost;
    }

    /**
     * 设置summaryEstimateAtCompletionByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEstimateAtCompletionByCost(JAXBElement<Double> value) {
        this.summaryEstimateAtCompletionByCost = value;
    }

    /**
     * 获取summaryEstimateAtCompletionByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEstimateAtCompletionByLaborUnits() {
        return summaryEstimateAtCompletionByLaborUnits;
    }

    /**
     * 设置summaryEstimateAtCompletionByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEstimateAtCompletionByLaborUnits(JAXBElement<Double> value) {
        this.summaryEstimateAtCompletionByLaborUnits = value;
    }

    /**
     * 获取summaryEstimateAtCompletionHighPercentByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEstimateAtCompletionHighPercentByLaborUnits() {
        return summaryEstimateAtCompletionHighPercentByLaborUnits;
    }

    /**
     * 设置summaryEstimateAtCompletionHighPercentByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEstimateAtCompletionHighPercentByLaborUnits(JAXBElement<Double> value) {
        this.summaryEstimateAtCompletionHighPercentByLaborUnits = value;
    }

    /**
     * 获取summaryEstimateAtCompletionLowPercentByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEstimateAtCompletionLowPercentByLaborUnits() {
        return summaryEstimateAtCompletionLowPercentByLaborUnits;
    }

    /**
     * 设置summaryEstimateAtCompletionLowPercentByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEstimateAtCompletionLowPercentByLaborUnits(JAXBElement<Double> value) {
        this.summaryEstimateAtCompletionLowPercentByLaborUnits = value;
    }

    /**
     * 获取summaryEstimateToCompleteByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEstimateToCompleteByCost() {
        return summaryEstimateToCompleteByCost;
    }

    /**
     * 设置summaryEstimateToCompleteByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEstimateToCompleteByCost(JAXBElement<Double> value) {
        this.summaryEstimateToCompleteByCost = value;
    }

    /**
     * 获取summaryEstimateToCompleteByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryEstimateToCompleteByLaborUnits() {
        return summaryEstimateToCompleteByLaborUnits;
    }

    /**
     * 设置summaryEstimateToCompleteByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryEstimateToCompleteByLaborUnits(JAXBElement<Double> value) {
        this.summaryEstimateToCompleteByLaborUnits = value;
    }

    /**
     * 获取summaryExpenseCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryExpenseCostPercentComplete() {
        return summaryExpenseCostPercentComplete;
    }

    /**
     * 设置summaryExpenseCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryExpenseCostPercentComplete(JAXBElement<Double> value) {
        this.summaryExpenseCostPercentComplete = value;
    }

    /**
     * 获取summaryExpenseCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryExpenseCostVariance() {
        return summaryExpenseCostVariance;
    }

    /**
     * 设置summaryExpenseCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryExpenseCostVariance(JAXBElement<Double> value) {
        this.summaryExpenseCostVariance = value;
    }

    /**
     * 获取summaryFinishDateVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryFinishDateVariance() {
        return summaryFinishDateVariance;
    }

    /**
     * 设置summaryFinishDateVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryFinishDateVariance(JAXBElement<Double> value) {
        this.summaryFinishDateVariance = value;
    }

    /**
     * 获取summaryInProgressActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryInProgressActivityCount() {
        return summaryInProgressActivityCount;
    }

    /**
     * 设置summaryInProgressActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryInProgressActivityCount(JAXBElement<Integer> value) {
        this.summaryInProgressActivityCount = value;
    }

    /**
     * 获取summaryLaborCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryLaborCostPercentComplete() {
        return summaryLaborCostPercentComplete;
    }

    /**
     * 设置summaryLaborCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryLaborCostPercentComplete(JAXBElement<Double> value) {
        this.summaryLaborCostPercentComplete = value;
    }

    /**
     * 获取summaryLaborCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryLaborCostVariance() {
        return summaryLaborCostVariance;
    }

    /**
     * 设置summaryLaborCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryLaborCostVariance(JAXBElement<Double> value) {
        this.summaryLaborCostVariance = value;
    }

    /**
     * 获取summaryLaborUnitsPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryLaborUnitsPercentComplete() {
        return summaryLaborUnitsPercentComplete;
    }

    /**
     * 设置summaryLaborUnitsPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryLaborUnitsPercentComplete(JAXBElement<Double> value) {
        this.summaryLaborUnitsPercentComplete = value;
    }

    /**
     * 获取summaryLaborUnitsVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryLaborUnitsVariance() {
        return summaryLaborUnitsVariance;
    }

    /**
     * 设置summaryLaborUnitsVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryLaborUnitsVariance(JAXBElement<Double> value) {
        this.summaryLaborUnitsVariance = value;
    }

    /**
     * 获取summaryMaterialCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryMaterialCostPercentComplete() {
        return summaryMaterialCostPercentComplete;
    }

    /**
     * 设置summaryMaterialCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryMaterialCostPercentComplete(JAXBElement<Double> value) {
        this.summaryMaterialCostPercentComplete = value;
    }

    /**
     * 获取summaryMaterialCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryMaterialCostVariance() {
        return summaryMaterialCostVariance;
    }

    /**
     * 设置summaryMaterialCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryMaterialCostVariance(JAXBElement<Double> value) {
        this.summaryMaterialCostVariance = value;
    }

    /**
     * 获取summaryNonLaborCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryNonLaborCostPercentComplete() {
        return summaryNonLaborCostPercentComplete;
    }

    /**
     * 设置summaryNonLaborCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryNonLaborCostPercentComplete(JAXBElement<Double> value) {
        this.summaryNonLaborCostPercentComplete = value;
    }

    /**
     * 获取summaryNonLaborCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryNonLaborCostVariance() {
        return summaryNonLaborCostVariance;
    }

    /**
     * 设置summaryNonLaborCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryNonLaborCostVariance(JAXBElement<Double> value) {
        this.summaryNonLaborCostVariance = value;
    }

    /**
     * 获取summaryNonLaborUnitsPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryNonLaborUnitsPercentComplete() {
        return summaryNonLaborUnitsPercentComplete;
    }

    /**
     * 设置summaryNonLaborUnitsPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryNonLaborUnitsPercentComplete(JAXBElement<Double> value) {
        this.summaryNonLaborUnitsPercentComplete = value;
    }

    /**
     * 获取summaryNonLaborUnitsVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryNonLaborUnitsVariance() {
        return summaryNonLaborUnitsVariance;
    }

    /**
     * 设置summaryNonLaborUnitsVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryNonLaborUnitsVariance(JAXBElement<Double> value) {
        this.summaryNonLaborUnitsVariance = value;
    }

    /**
     * 获取summaryNotStartedActivityCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummaryNotStartedActivityCount() {
        return summaryNotStartedActivityCount;
    }

    /**
     * 设置summaryNotStartedActivityCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummaryNotStartedActivityCount(JAXBElement<Integer> value) {
        this.summaryNotStartedActivityCount = value;
    }

    /**
     * 获取summaryPerformancePercentCompleteByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPerformancePercentCompleteByCost() {
        return summaryPerformancePercentCompleteByCost;
    }

    /**
     * 设置summaryPerformancePercentCompleteByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPerformancePercentCompleteByCost(JAXBElement<Double> value) {
        this.summaryPerformancePercentCompleteByCost = value;
    }

    /**
     * 获取summaryPerformancePercentCompleteByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPerformancePercentCompleteByLaborUnits() {
        return summaryPerformancePercentCompleteByLaborUnits;
    }

    /**
     * 设置summaryPerformancePercentCompleteByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPerformancePercentCompleteByLaborUnits(JAXBElement<Double> value) {
        this.summaryPerformancePercentCompleteByLaborUnits = value;
    }

    /**
     * 获取summaryPlannedCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedCost() {
        return summaryPlannedCost;
    }

    /**
     * 设置summaryPlannedCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedCost(JAXBElement<Double> value) {
        this.summaryPlannedCost = value;
    }

    /**
     * 获取summaryPlannedDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedDuration() {
        return summaryPlannedDuration;
    }

    /**
     * 设置summaryPlannedDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedDuration(JAXBElement<Double> value) {
        this.summaryPlannedDuration = value;
    }

    /**
     * 获取summaryPlannedExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedExpenseCost() {
        return summaryPlannedExpenseCost;
    }

    /**
     * 设置summaryPlannedExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedExpenseCost(JAXBElement<Double> value) {
        this.summaryPlannedExpenseCost = value;
    }

    /**
     * 获取summaryPlannedFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryPlannedFinishDate() {
        return summaryPlannedFinishDate;
    }

    /**
     * 设置summaryPlannedFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryPlannedFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryPlannedFinishDate = value;
    }

    /**
     * 获取summaryPlannedLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedLaborCost() {
        return summaryPlannedLaborCost;
    }

    /**
     * 设置summaryPlannedLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedLaborCost(JAXBElement<Double> value) {
        this.summaryPlannedLaborCost = value;
    }

    /**
     * 获取summaryPlannedLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedLaborUnits() {
        return summaryPlannedLaborUnits;
    }

    /**
     * 设置summaryPlannedLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedLaborUnits(JAXBElement<Double> value) {
        this.summaryPlannedLaborUnits = value;
    }

    /**
     * 获取summaryPlannedMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedMaterialCost() {
        return summaryPlannedMaterialCost;
    }

    /**
     * 设置summaryPlannedMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedMaterialCost(JAXBElement<Double> value) {
        this.summaryPlannedMaterialCost = value;
    }

    /**
     * 获取summaryPlannedNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedNonLaborCost() {
        return summaryPlannedNonLaborCost;
    }

    /**
     * 设置summaryPlannedNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedNonLaborCost(JAXBElement<Double> value) {
        this.summaryPlannedNonLaborCost = value;
    }

    /**
     * 获取summaryPlannedNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedNonLaborUnits() {
        return summaryPlannedNonLaborUnits;
    }

    /**
     * 设置summaryPlannedNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedNonLaborUnits(JAXBElement<Double> value) {
        this.summaryPlannedNonLaborUnits = value;
    }

    /**
     * 获取summaryPlannedStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryPlannedStartDate() {
        return summaryPlannedStartDate;
    }

    /**
     * 设置summaryPlannedStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryPlannedStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryPlannedStartDate = value;
    }

    /**
     * 获取summaryPlannedValueByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedValueByCost() {
        return summaryPlannedValueByCost;
    }

    /**
     * 设置summaryPlannedValueByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedValueByCost(JAXBElement<Double> value) {
        this.summaryPlannedValueByCost = value;
    }

    /**
     * 获取summaryPlannedValueByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryPlannedValueByLaborUnits() {
        return summaryPlannedValueByLaborUnits;
    }

    /**
     * 设置summaryPlannedValueByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryPlannedValueByLaborUnits(JAXBElement<Double> value) {
        this.summaryPlannedValueByLaborUnits = value;
    }

    /**
     * 获取summaryProgressFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryProgressFinishDate() {
        return summaryProgressFinishDate;
    }

    /**
     * 设置summaryProgressFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryProgressFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryProgressFinishDate = value;
    }

    /**
     * 获取summaryRemainingDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingDuration() {
        return summaryRemainingDuration;
    }

    /**
     * 设置summaryRemainingDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingDuration(JAXBElement<Double> value) {
        this.summaryRemainingDuration = value;
    }

    /**
     * 获取summaryRemainingExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingExpenseCost() {
        return summaryRemainingExpenseCost;
    }

    /**
     * 设置summaryRemainingExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingExpenseCost(JAXBElement<Double> value) {
        this.summaryRemainingExpenseCost = value;
    }

    /**
     * 获取summaryRemainingFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryRemainingFinishDate() {
        return summaryRemainingFinishDate;
    }

    /**
     * 设置summaryRemainingFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryRemainingFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryRemainingFinishDate = value;
    }

    /**
     * 获取summaryRemainingLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingLaborCost() {
        return summaryRemainingLaborCost;
    }

    /**
     * 设置summaryRemainingLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingLaborCost(JAXBElement<Double> value) {
        this.summaryRemainingLaborCost = value;
    }

    /**
     * 获取summaryRemainingLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingLaborUnits() {
        return summaryRemainingLaborUnits;
    }

    /**
     * 设置summaryRemainingLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingLaborUnits(JAXBElement<Double> value) {
        this.summaryRemainingLaborUnits = value;
    }

    /**
     * 获取summaryRemainingMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingMaterialCost() {
        return summaryRemainingMaterialCost;
    }

    /**
     * 设置summaryRemainingMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingMaterialCost(JAXBElement<Double> value) {
        this.summaryRemainingMaterialCost = value;
    }

    /**
     * 获取summaryRemainingNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingNonLaborCost() {
        return summaryRemainingNonLaborCost;
    }

    /**
     * 设置summaryRemainingNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingNonLaborCost(JAXBElement<Double> value) {
        this.summaryRemainingNonLaborCost = value;
    }

    /**
     * 获取summaryRemainingNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingNonLaborUnits() {
        return summaryRemainingNonLaborUnits;
    }

    /**
     * 设置summaryRemainingNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingNonLaborUnits(JAXBElement<Double> value) {
        this.summaryRemainingNonLaborUnits = value;
    }

    /**
     * 获取summaryRemainingStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummaryRemainingStartDate() {
        return summaryRemainingStartDate;
    }

    /**
     * 设置summaryRemainingStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummaryRemainingStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summaryRemainingStartDate = value;
    }

    /**
     * 获取summaryRemainingTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryRemainingTotalCost() {
        return summaryRemainingTotalCost;
    }

    /**
     * 设置summaryRemainingTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryRemainingTotalCost(JAXBElement<Double> value) {
        this.summaryRemainingTotalCost = value;
    }

    /**
     * 获取summarySchedulePercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummarySchedulePercentComplete() {
        return summarySchedulePercentComplete;
    }

    /**
     * 设置summarySchedulePercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummarySchedulePercentComplete(JAXBElement<Double> value) {
        this.summarySchedulePercentComplete = value;
    }

    /**
     * 获取summarySchedulePercentCompleteByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummarySchedulePercentCompleteByLaborUnits() {
        return summarySchedulePercentCompleteByLaborUnits;
    }

    /**
     * 设置summarySchedulePercentCompleteByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummarySchedulePercentCompleteByLaborUnits(JAXBElement<Double> value) {
        this.summarySchedulePercentCompleteByLaborUnits = value;
    }

    /**
     * 获取summarySchedulePerformanceIndexByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummarySchedulePerformanceIndexByCost() {
        return summarySchedulePerformanceIndexByCost;
    }

    /**
     * 设置summarySchedulePerformanceIndexByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummarySchedulePerformanceIndexByCost(JAXBElement<Double> value) {
        this.summarySchedulePerformanceIndexByCost = value;
    }

    /**
     * 获取summarySchedulePerformanceIndexByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummarySchedulePerformanceIndexByLaborUnits() {
        return summarySchedulePerformanceIndexByLaborUnits;
    }

    /**
     * 设置summarySchedulePerformanceIndexByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummarySchedulePerformanceIndexByLaborUnits(JAXBElement<Double> value) {
        this.summarySchedulePerformanceIndexByLaborUnits = value;
    }

    /**
     * 获取summaryScheduleVarianceByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryScheduleVarianceByCost() {
        return summaryScheduleVarianceByCost;
    }

    /**
     * 设置summaryScheduleVarianceByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryScheduleVarianceByCost(JAXBElement<Double> value) {
        this.summaryScheduleVarianceByCost = value;
    }

    /**
     * 获取summaryScheduleVarianceByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryScheduleVarianceByLaborUnits() {
        return summaryScheduleVarianceByLaborUnits;
    }

    /**
     * 设置summaryScheduleVarianceByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryScheduleVarianceByLaborUnits(JAXBElement<Double> value) {
        this.summaryScheduleVarianceByLaborUnits = value;
    }

    /**
     * 获取summaryScheduleVarianceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryScheduleVarianceIndex() {
        return summaryScheduleVarianceIndex;
    }

    /**
     * 设置summaryScheduleVarianceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryScheduleVarianceIndex(JAXBElement<Double> value) {
        this.summaryScheduleVarianceIndex = value;
    }

    /**
     * 获取summaryScheduleVarianceIndexByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryScheduleVarianceIndexByCost() {
        return summaryScheduleVarianceIndexByCost;
    }

    /**
     * 设置summaryScheduleVarianceIndexByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryScheduleVarianceIndexByCost(JAXBElement<Double> value) {
        this.summaryScheduleVarianceIndexByCost = value;
    }

    /**
     * 获取summaryScheduleVarianceIndexByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryScheduleVarianceIndexByLaborUnits() {
        return summaryScheduleVarianceIndexByLaborUnits;
    }

    /**
     * 设置summaryScheduleVarianceIndexByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryScheduleVarianceIndexByLaborUnits(JAXBElement<Double> value) {
        this.summaryScheduleVarianceIndexByLaborUnits = value;
    }

    /**
     * 获取summaryStartDateVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryStartDateVariance() {
        return summaryStartDateVariance;
    }

    /**
     * 设置summaryStartDateVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryStartDateVariance(JAXBElement<Double> value) {
        this.summaryStartDateVariance = value;
    }

    /**
     * 获取summaryToCompletePerformanceIndexByCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryToCompletePerformanceIndexByCost() {
        return summaryToCompletePerformanceIndexByCost;
    }

    /**
     * 设置summaryToCompletePerformanceIndexByCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryToCompletePerformanceIndexByCost(JAXBElement<Double> value) {
        this.summaryToCompletePerformanceIndexByCost = value;
    }

    /**
     * 获取summaryTotalCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryTotalCostVariance() {
        return summaryTotalCostVariance;
    }

    /**
     * 设置summaryTotalCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryTotalCostVariance(JAXBElement<Double> value) {
        this.summaryTotalCostVariance = value;
    }

    /**
     * 获取summaryTotalFloat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryTotalFloat() {
        return summaryTotalFloat;
    }

    /**
     * 设置summaryTotalFloat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryTotalFloat(JAXBElement<Double> value) {
        this.summaryTotalFloat = value;
    }

    /**
     * 获取summaryUnitsPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryUnitsPercentComplete() {
        return summaryUnitsPercentComplete;
    }

    /**
     * 设置summaryUnitsPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryUnitsPercentComplete(JAXBElement<Double> value) {
        this.summaryUnitsPercentComplete = value;
    }

    /**
     * 获取summaryVarianceAtCompletionByLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSummaryVarianceAtCompletionByLaborUnits() {
        return summaryVarianceAtCompletionByLaborUnits;
    }

    /**
     * 设置summaryVarianceAtCompletionByLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSummaryVarianceAtCompletionByLaborUnits(JAXBElement<Double> value) {
        this.summaryVarianceAtCompletionByLaborUnits = value;
    }

    /**
     * 获取totalBenefitPlan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalBenefitPlan() {
        return totalBenefitPlan;
    }

    /**
     * 设置totalBenefitPlan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalBenefitPlan(JAXBElement<Double> value) {
        this.totalBenefitPlan = value;
    }

    /**
     * 获取totalBenefitPlanTally属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalBenefitPlanTally() {
        return totalBenefitPlanTally;
    }

    /**
     * 设置totalBenefitPlanTally属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalBenefitPlanTally(JAXBElement<Double> value) {
        this.totalBenefitPlanTally = value;
    }

    /**
     * 获取totalSpendingPlan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalSpendingPlan() {
        return totalSpendingPlan;
    }

    /**
     * 设置totalSpendingPlan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalSpendingPlan(JAXBElement<Double> value) {
        this.totalSpendingPlan = value;
    }

    /**
     * 获取totalSpendingPlanTally属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalSpendingPlanTally() {
        return totalSpendingPlanTally;
    }

    /**
     * 设置totalSpendingPlanTally属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalSpendingPlanTally(JAXBElement<Double> value) {
        this.totalSpendingPlanTally = value;
    }

    /**
     * 获取unallocatedBudget属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getUnallocatedBudget() {
        return unallocatedBudget;
    }

    /**
     * 设置unallocatedBudget属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setUnallocatedBudget(JAXBElement<Double> value) {
        this.unallocatedBudget = value;
    }

    /**
     * 获取undistributedCurrentVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getUndistributedCurrentVariance() {
        return undistributedCurrentVariance;
    }

    /**
     * 设置undistributedCurrentVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setUndistributedCurrentVariance(JAXBElement<Double> value) {
        this.undistributedCurrentVariance = value;
    }

    /**
     * 获取wbsCategoryObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getWBSCategoryObjectId() {
        return wbsCategoryObjectId;
    }

    /**
     * 设置wbsCategoryObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setWBSCategoryObjectId(JAXBElement<Integer> value) {
        this.wbsCategoryObjectId = value;
    }

    /**
     * 获取wbsMilestonePercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getWBSMilestonePercentComplete() {
        return wbsMilestonePercentComplete;
    }

    /**
     * 设置wbsMilestonePercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setWBSMilestonePercentComplete(JAXBElement<Double> value) {
        this.wbsMilestonePercentComplete = value;
    }

}
