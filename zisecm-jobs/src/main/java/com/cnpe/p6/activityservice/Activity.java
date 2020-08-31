
package com.cnpe.p6.activityservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Activity complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Activity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountingVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AccountingVarianceLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActivityOwnerUserId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActualDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ActualExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ActualLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ActualThisPeriodLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualThisPeriodLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualThisPeriodMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualThisPeriodNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualThisPeriodNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ActualTotalUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AtCompletionExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionLaborUnitsVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionTotalUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AtCompletionVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="AutoComputeActuals" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Baseline1Duration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1PlannedTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Baseline1StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="BaselineDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselineFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedDuration" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselinePlannedTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BaselineStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="BudgetAtCompletion" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CBSCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1024"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CBSId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CBSObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CalendarName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CalendarObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostPercentOfPlanned" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostPerformanceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostPerformanceIndexLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostVarianceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostVarianceIndexLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CostVarianceLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DataDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Duration1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="DurationPercentComplete" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DurationPercentOfPlanned" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="DurationType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Fixed Units/Time"/>
 *               &lt;enumeration value="Fixed Duration and Units/Time"/>
 *               &lt;enumeration value="Fixed Units"/>
 *               &lt;enumeration value="Fixed Duration and Units"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DurationVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EarlyFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="EarlyStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="EarnedValueCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EarnedValueLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EstimateAtCompletionCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EstimateAtCompletionLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EstimateToComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EstimateToCompleteLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EstimatedWeight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ExpectedFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ExpenseCost1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ExpenseCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ExpenseCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ExternalEarlyStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ExternalLateFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Feedback" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="FinishDate1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="FinishDateVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="FloatPath" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="FloatPathOrder" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="FreeFloat" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="GUID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\{[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\}|"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HasFutureBucketData" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Id" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IsBaseline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsCritical" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsLongestPath" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsNewFeedback" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsStarred" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="IsWorkPackage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LaborCost1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LaborCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LaborCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LaborUnits1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LaborUnitsPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LaborUnitsVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastUpdateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LateFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LateStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LevelingPriority" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Top"/>
 *               &lt;enumeration value="High"/>
 *               &lt;enumeration value="Normal"/>
 *               &lt;enumeration value="Low"/>
 *               &lt;enumeration value="Lowest"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LocationName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LocationObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MaterialCost1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MaterialCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MaterialCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MaximumDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MinimumDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MostLikelyDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Name" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="120"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NonLaborCost1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NonLaborCostPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NonLaborCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NonLaborUnits1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NonLaborUnitsPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NonLaborUnitsVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="NotesToResources" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OwnerIDArray" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OwnerNamesArray" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PercentCompleteType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Physical"/>
 *               &lt;enumeration value="Duration"/>
 *               &lt;enumeration value="Units"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PerformancePercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PhysicalPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PlannedExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PlannedLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PlannedTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedTotalUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedValueCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PlannedValueLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PostResponsePessimisticFinish" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PostResponsePessimisticStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PreResponsePessimisticFinish" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PreResponsePessimisticStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PrimaryConstraintDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PrimaryConstraintType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Start On"/>
 *               &lt;enumeration value="Start On or Before"/>
 *               &lt;enumeration value="Start On or After"/>
 *               &lt;enumeration value="Finish On"/>
 *               &lt;enumeration value="Finish On or Before"/>
 *               &lt;enumeration value="Finish On or After"/>
 *               &lt;enumeration value="As Late As Possible"/>
 *               &lt;enumeration value="Mandatory Start"/>
 *               &lt;enumeration value="Mandatory Finish"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PrimaryResourceId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PrimaryResourceName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PrimaryResourceObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ProjectFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProjectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProjectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ProjectProjectFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemainingDuration" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RemainingEarlyFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="RemainingEarlyStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="RemainingExpenseCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingFloat" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingLateFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="RemainingLateStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="RemainingMaterialCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingNonLaborCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingNonLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingTotalCost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RemainingTotalUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ResumeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ReviewFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ReviewRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ReviewStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="OK"/>
 *               &lt;enumeration value="For Review"/>
 *               &lt;enumeration value="Rejected"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SchedulePercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SchedulePerformanceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SchedulePerformanceIndexLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ScheduleVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ScheduleVarianceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ScheduleVarianceIndexLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ScheduleVarianceLaborUnits" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="SecondaryConstraintDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SecondaryConstraintType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Start On"/>
 *               &lt;enumeration value="Start On or Before"/>
 *               &lt;enumeration value="Start On or After"/>
 *               &lt;enumeration value="Finish On"/>
 *               &lt;enumeration value="Finish On or Before"/>
 *               &lt;enumeration value="Finish On or After"/>
 *               &lt;enumeration value="As Late As Possible"/>
 *               &lt;enumeration value="Mandatory Start"/>
 *               &lt;enumeration value="Mandatory Finish"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="StartDate1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="StartDateVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Status" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Not Started"/>
 *               &lt;enumeration value="In Progress"/>
 *               &lt;enumeration value="Completed"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="StatusCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Planned"/>
 *               &lt;enumeration value="Active"/>
 *               &lt;enumeration value="Inactive"/>
 *               &lt;enumeration value="What-If"/>
 *               &lt;enumeration value="Requested"/>
 *               &lt;enumeration value="Template"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SuspendDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ToCompletePerformanceIndex" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalCost1Variance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalCostVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalFloat" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Type" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Task Dependent"/>
 *               &lt;enumeration value="Resource Dependent"/>
 *               &lt;enumeration value="Level of Effort"/>
 *               &lt;enumeration value="Start Milestone"/>
 *               &lt;enumeration value="Finish Milestone"/>
 *               &lt;enumeration value="WBS Summary"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UnitsPercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UnreadCommentCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WBSCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="WBSName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="WBSObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WBSPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Activity", propOrder = {
    "accountingVariance",
    "accountingVarianceLaborUnits",
    "activityOwnerUserId",
    "actualDuration",
    "actualExpenseCost",
    "actualFinishDate",
    "actualLaborCost",
    "actualLaborUnits",
    "actualMaterialCost",
    "actualNonLaborCost",
    "actualNonLaborUnits",
    "actualStartDate",
    "actualThisPeriodLaborCost",
    "actualThisPeriodLaborUnits",
    "actualThisPeriodMaterialCost",
    "actualThisPeriodNonLaborCost",
    "actualThisPeriodNonLaborUnits",
    "actualTotalCost",
    "actualTotalUnits",
    "atCompletionDuration",
    "atCompletionExpenseCost",
    "atCompletionLaborCost",
    "atCompletionLaborUnits",
    "atCompletionLaborUnitsVariance",
    "atCompletionMaterialCost",
    "atCompletionNonLaborCost",
    "atCompletionNonLaborUnits",
    "atCompletionTotalCost",
    "atCompletionTotalUnits",
    "atCompletionVariance",
    "autoComputeActuals",
    "baseline1Duration",
    "baseline1FinishDate",
    "baseline1PlannedDuration",
    "baseline1PlannedExpenseCost",
    "baseline1PlannedLaborCost",
    "baseline1PlannedLaborUnits",
    "baseline1PlannedMaterialCost",
    "baseline1PlannedNonLaborCost",
    "baseline1PlannedNonLaborUnits",
    "baseline1PlannedTotalCost",
    "baseline1StartDate",
    "baselineDuration",
    "baselineFinishDate",
    "baselinePlannedDuration",
    "baselinePlannedExpenseCost",
    "baselinePlannedLaborCost",
    "baselinePlannedLaborUnits",
    "baselinePlannedMaterialCost",
    "baselinePlannedNonLaborCost",
    "baselinePlannedNonLaborUnits",
    "baselinePlannedTotalCost",
    "baselineStartDate",
    "budgetAtCompletion",
    "cbsCode",
    "cbsId",
    "cbsObjectId",
    "calendarName",
    "calendarObjectId",
    "costPercentComplete",
    "costPercentOfPlanned",
    "costPerformanceIndex",
    "costPerformanceIndexLaborUnits",
    "costVariance",
    "costVarianceIndex",
    "costVarianceIndexLaborUnits",
    "costVarianceLaborUnits",
    "createDate",
    "createUser",
    "dataDate",
    "duration1Variance",
    "durationPercentComplete",
    "durationPercentOfPlanned",
    "durationType",
    "durationVariance",
    "earlyFinishDate",
    "earlyStartDate",
    "earnedValueCost",
    "earnedValueLaborUnits",
    "estimateAtCompletionCost",
    "estimateAtCompletionLaborUnits",
    "estimateToComplete",
    "estimateToCompleteLaborUnits",
    "estimatedWeight",
    "expectedFinishDate",
    "expenseCost1Variance",
    "expenseCostPercentComplete",
    "expenseCostVariance",
    "externalEarlyStartDate",
    "externalLateFinishDate",
    "feedback",
    "finishDate",
    "finishDate1Variance",
    "finishDateVariance",
    "floatPath",
    "floatPathOrder",
    "freeFloat",
    "guid",
    "hasFutureBucketData",
    "id",
    "isBaseline",
    "isCritical",
    "isLongestPath",
    "isNewFeedback",
    "isStarred",
    "isTemplate",
    "isWorkPackage",
    "laborCost1Variance",
    "laborCostPercentComplete",
    "laborCostVariance",
    "laborUnits1Variance",
    "laborUnitsPercentComplete",
    "laborUnitsVariance",
    "lastUpdateDate",
    "lastUpdateUser",
    "lateFinishDate",
    "lateStartDate",
    "levelingPriority",
    "locationName",
    "locationObjectId",
    "materialCost1Variance",
    "materialCostPercentComplete",
    "materialCostVariance",
    "maximumDuration",
    "minimumDuration",
    "mostLikelyDuration",
    "name",
    "nonLaborCost1Variance",
    "nonLaborCostPercentComplete",
    "nonLaborCostVariance",
    "nonLaborUnits1Variance",
    "nonLaborUnitsPercentComplete",
    "nonLaborUnitsVariance",
    "notesToResources",
    "objectId",
    "ownerIDArray",
    "ownerNamesArray",
    "percentComplete",
    "percentCompleteType",
    "performancePercentComplete",
    "physicalPercentComplete",
    "plannedDuration",
    "plannedExpenseCost",
    "plannedFinishDate",
    "plannedLaborCost",
    "plannedLaborUnits",
    "plannedMaterialCost",
    "plannedNonLaborCost",
    "plannedNonLaborUnits",
    "plannedStartDate",
    "plannedTotalCost",
    "plannedTotalUnits",
    "plannedValueCost",
    "plannedValueLaborUnits",
    "postResponsePessimisticFinish",
    "postResponsePessimisticStart",
    "preResponsePessimisticFinish",
    "preResponsePessimisticStart",
    "primaryConstraintDate",
    "primaryConstraintType",
    "primaryResourceId",
    "primaryResourceName",
    "primaryResourceObjectId",
    "projectFlag",
    "projectId",
    "projectName",
    "projectObjectId",
    "projectProjectFlag",
    "remainingDuration",
    "remainingEarlyFinishDate",
    "remainingEarlyStartDate",
    "remainingExpenseCost",
    "remainingFloat",
    "remainingLaborCost",
    "remainingLaborUnits",
    "remainingLateFinishDate",
    "remainingLateStartDate",
    "remainingMaterialCost",
    "remainingNonLaborCost",
    "remainingNonLaborUnits",
    "remainingTotalCost",
    "remainingTotalUnits",
    "resumeDate",
    "reviewFinishDate",
    "reviewRequired",
    "reviewStatus",
    "schedulePercentComplete",
    "schedulePerformanceIndex",
    "schedulePerformanceIndexLaborUnits",
    "scheduleVariance",
    "scheduleVarianceIndex",
    "scheduleVarianceIndexLaborUnits",
    "scheduleVarianceLaborUnits",
    "secondaryConstraintDate",
    "secondaryConstraintType",
    "startDate",
    "startDate1Variance",
    "startDateVariance",
    "status",
    "statusCode",
    "suspendDate",
    "toCompletePerformanceIndex",
    "totalCost1Variance",
    "totalCostVariance",
    "totalFloat",
    "type",
    "unitsPercentComplete",
    "unreadCommentCount",
    "wbsCode",
    "wbsName",
    "wbsObjectId",
    "wbsPath"
})
public class Activity {

    @XmlElementRef(name = "AccountingVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> accountingVariance;
    @XmlElementRef(name = "AccountingVarianceLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> accountingVarianceLaborUnits;
    @XmlElement(name = "ActivityOwnerUserId")
    protected Integer activityOwnerUserId;
    @XmlElementRef(name = "ActualDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualDuration;
    @XmlElementRef(name = "ActualExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualExpenseCost;
    @XmlElementRef(name = "ActualFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> actualFinishDate;
    @XmlElement(name = "ActualLaborCost")
    protected Double actualLaborCost;
    @XmlElement(name = "ActualLaborUnits")
    protected Double actualLaborUnits;
    @XmlElementRef(name = "ActualMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualMaterialCost;
    @XmlElement(name = "ActualNonLaborCost")
    protected Double actualNonLaborCost;
    @XmlElement(name = "ActualNonLaborUnits")
    protected Double actualNonLaborUnits;
    @XmlElementRef(name = "ActualStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> actualStartDate;
    @XmlElement(name = "ActualThisPeriodLaborCost")
    protected Double actualThisPeriodLaborCost;
    @XmlElementRef(name = "ActualThisPeriodLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualThisPeriodLaborUnits;
    @XmlElementRef(name = "ActualThisPeriodMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualThisPeriodMaterialCost;
    @XmlElement(name = "ActualThisPeriodNonLaborCost")
    protected Double actualThisPeriodNonLaborCost;
    @XmlElementRef(name = "ActualThisPeriodNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualThisPeriodNonLaborUnits;
    @XmlElementRef(name = "ActualTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualTotalCost;
    @XmlElementRef(name = "ActualTotalUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> actualTotalUnits;
    @XmlElement(name = "AtCompletionDuration")
    protected Double atCompletionDuration;
    @XmlElementRef(name = "AtCompletionExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> atCompletionExpenseCost;
    @XmlElement(name = "AtCompletionLaborCost")
    protected Double atCompletionLaborCost;
    @XmlElement(name = "AtCompletionLaborUnits")
    protected Double atCompletionLaborUnits;
    @XmlElementRef(name = "AtCompletionLaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> atCompletionLaborUnitsVariance;
    @XmlElementRef(name = "AtCompletionMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> atCompletionMaterialCost;
    @XmlElement(name = "AtCompletionNonLaborCost")
    protected Double atCompletionNonLaborCost;
    @XmlElement(name = "AtCompletionNonLaborUnits")
    protected Double atCompletionNonLaborUnits;
    @XmlElementRef(name = "AtCompletionTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> atCompletionTotalCost;
    @XmlElementRef(name = "AtCompletionTotalUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> atCompletionTotalUnits;
    @XmlElementRef(name = "AtCompletionVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> atCompletionVariance;
    @XmlElement(name = "AutoComputeActuals")
    protected Boolean autoComputeActuals;
    @XmlElementRef(name = "Baseline1Duration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1Duration;
    @XmlElementRef(name = "Baseline1FinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> baseline1FinishDate;
    @XmlElementRef(name = "Baseline1PlannedDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedDuration;
    @XmlElementRef(name = "Baseline1PlannedExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedExpenseCost;
    @XmlElementRef(name = "Baseline1PlannedLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedLaborCost;
    @XmlElementRef(name = "Baseline1PlannedLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedLaborUnits;
    @XmlElementRef(name = "Baseline1PlannedMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedMaterialCost;
    @XmlElementRef(name = "Baseline1PlannedNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedNonLaborCost;
    @XmlElementRef(name = "Baseline1PlannedNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedNonLaborUnits;
    @XmlElementRef(name = "Baseline1PlannedTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baseline1PlannedTotalCost;
    @XmlElementRef(name = "Baseline1StartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> baseline1StartDate;
    @XmlElementRef(name = "BaselineDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselineDuration;
    @XmlElementRef(name = "BaselineFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> baselineFinishDate;
    @XmlElementRef(name = "BaselinePlannedDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedDuration;
    @XmlElementRef(name = "BaselinePlannedExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedExpenseCost;
    @XmlElementRef(name = "BaselinePlannedLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedLaborCost;
    @XmlElementRef(name = "BaselinePlannedLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedLaborUnits;
    @XmlElementRef(name = "BaselinePlannedMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedMaterialCost;
    @XmlElementRef(name = "BaselinePlannedNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedNonLaborCost;
    @XmlElementRef(name = "BaselinePlannedNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedNonLaborUnits;
    @XmlElementRef(name = "BaselinePlannedTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> baselinePlannedTotalCost;
    @XmlElementRef(name = "BaselineStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> baselineStartDate;
    @XmlElementRef(name = "BudgetAtCompletion", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> budgetAtCompletion;
    @XmlElement(name = "CBSCode")
    protected String cbsCode;
    @XmlElementRef(name = "CBSId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> cbsId;
    @XmlElementRef(name = "CBSObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> cbsObjectId;
    @XmlElement(name = "CalendarName")
    protected String calendarName;
    @XmlElement(name = "CalendarObjectId")
    protected Integer calendarObjectId;
    @XmlElementRef(name = "CostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costPercentComplete;
    @XmlElementRef(name = "CostPercentOfPlanned", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costPercentOfPlanned;
    @XmlElementRef(name = "CostPerformanceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costPerformanceIndex;
    @XmlElementRef(name = "CostPerformanceIndexLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costPerformanceIndexLaborUnits;
    @XmlElementRef(name = "CostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costVariance;
    @XmlElementRef(name = "CostVarianceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costVarianceIndex;
    @XmlElementRef(name = "CostVarianceIndexLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costVarianceIndexLaborUnits;
    @XmlElementRef(name = "CostVarianceLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> costVarianceLaborUnits;
    @XmlElementRef(name = "CreateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> createDate;
    @XmlElement(name = "CreateUser")
    protected String createUser;
    @XmlElementRef(name = "DataDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> dataDate;
    @XmlElementRef(name = "Duration1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> duration1Variance;
    @XmlElementRef(name = "DurationPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> durationPercentComplete;
    @XmlElementRef(name = "DurationPercentOfPlanned", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> durationPercentOfPlanned;
    @XmlElement(name = "DurationType")
    protected String durationType;
    @XmlElementRef(name = "DurationVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> durationVariance;
    @XmlElementRef(name = "EarlyFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> earlyFinishDate;
    @XmlElementRef(name = "EarlyStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> earlyStartDate;
    @XmlElementRef(name = "EarnedValueCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> earnedValueCost;
    @XmlElementRef(name = "EarnedValueLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> earnedValueLaborUnits;
    @XmlElementRef(name = "EstimateAtCompletionCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> estimateAtCompletionCost;
    @XmlElementRef(name = "EstimateAtCompletionLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> estimateAtCompletionLaborUnits;
    @XmlElementRef(name = "EstimateToComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> estimateToComplete;
    @XmlElementRef(name = "EstimateToCompleteLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> estimateToCompleteLaborUnits;
    @XmlElement(name = "EstimatedWeight")
    protected Double estimatedWeight;
    @XmlElementRef(name = "ExpectedFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> expectedFinishDate;
    @XmlElementRef(name = "ExpenseCost1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> expenseCost1Variance;
    @XmlElementRef(name = "ExpenseCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> expenseCostPercentComplete;
    @XmlElementRef(name = "ExpenseCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> expenseCostVariance;
    @XmlElementRef(name = "ExternalEarlyStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> externalEarlyStartDate;
    @XmlElementRef(name = "ExternalLateFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> externalLateFinishDate;
    @XmlElement(name = "Feedback")
    protected String feedback;
    @XmlElement(name = "FinishDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar finishDate;
    @XmlElementRef(name = "FinishDate1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> finishDate1Variance;
    @XmlElementRef(name = "FinishDateVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> finishDateVariance;
    @XmlElementRef(name = "FloatPath", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> floatPath;
    @XmlElementRef(name = "FloatPathOrder", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> floatPathOrder;
    @XmlElementRef(name = "FreeFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> freeFloat;
    @XmlElement(name = "GUID")
    protected String guid;
    @XmlElementRef(name = "HasFutureBucketData", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> hasFutureBucketData;
    @XmlElement(name = "Id")
    protected String id;
    @XmlElement(name = "IsBaseline")
    protected Boolean isBaseline;
    @XmlElementRef(name = "IsCritical", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isCritical;
    @XmlElementRef(name = "IsLongestPath", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isLongestPath;
    @XmlElement(name = "IsNewFeedback")
    protected Boolean isNewFeedback;
    @XmlElementRef(name = "IsStarred", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isStarred;
    @XmlElement(name = "IsTemplate")
    protected Boolean isTemplate;
    @XmlElementRef(name = "IsWorkPackage", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Boolean> isWorkPackage;
    @XmlElementRef(name = "LaborCost1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> laborCost1Variance;
    @XmlElementRef(name = "LaborCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> laborCostPercentComplete;
    @XmlElementRef(name = "LaborCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> laborCostVariance;
    @XmlElementRef(name = "LaborUnits1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> laborUnits1Variance;
    @XmlElementRef(name = "LaborUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> laborUnitsPercentComplete;
    @XmlElementRef(name = "LaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> laborUnitsVariance;
    @XmlElementRef(name = "LastUpdateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastUpdateDate;
    @XmlElement(name = "LastUpdateUser")
    protected String lastUpdateUser;
    @XmlElementRef(name = "LateFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lateFinishDate;
    @XmlElementRef(name = "LateStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lateStartDate;
    @XmlElement(name = "LevelingPriority")
    protected String levelingPriority;
    @XmlElement(name = "LocationName")
    protected String locationName;
    @XmlElementRef(name = "LocationObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> locationObjectId;
    @XmlElementRef(name = "MaterialCost1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> materialCost1Variance;
    @XmlElementRef(name = "MaterialCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> materialCostPercentComplete;
    @XmlElementRef(name = "MaterialCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> materialCostVariance;
    @XmlElementRef(name = "MaximumDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> maximumDuration;
    @XmlElementRef(name = "MinimumDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> minimumDuration;
    @XmlElementRef(name = "MostLikelyDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> mostLikelyDuration;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElementRef(name = "NonLaborCost1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> nonLaborCost1Variance;
    @XmlElementRef(name = "NonLaborCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> nonLaborCostPercentComplete;
    @XmlElementRef(name = "NonLaborCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> nonLaborCostVariance;
    @XmlElementRef(name = "NonLaborUnits1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> nonLaborUnits1Variance;
    @XmlElementRef(name = "NonLaborUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> nonLaborUnitsPercentComplete;
    @XmlElementRef(name = "NonLaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> nonLaborUnitsVariance;
    @XmlElement(name = "NotesToResources")
    protected String notesToResources;
    @XmlElement(name = "ObjectId")
    protected Integer objectId;
    @XmlElement(name = "OwnerIDArray")
    protected String ownerIDArray;
    @XmlElement(name = "OwnerNamesArray")
    protected String ownerNamesArray;
    @XmlElementRef(name = "PercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> percentComplete;
    @XmlElement(name = "PercentCompleteType")
    protected String percentCompleteType;
    @XmlElementRef(name = "PerformancePercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> performancePercentComplete;
    @XmlElement(name = "PhysicalPercentComplete")
    protected Double physicalPercentComplete;
    @XmlElement(name = "PlannedDuration")
    protected Double plannedDuration;
    @XmlElementRef(name = "PlannedExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> plannedExpenseCost;
    @XmlElement(name = "PlannedFinishDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedFinishDate;
    @XmlElement(name = "PlannedLaborCost")
    protected Double plannedLaborCost;
    @XmlElement(name = "PlannedLaborUnits")
    protected Double plannedLaborUnits;
    @XmlElementRef(name = "PlannedMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> plannedMaterialCost;
    @XmlElement(name = "PlannedNonLaborCost")
    protected Double plannedNonLaborCost;
    @XmlElement(name = "PlannedNonLaborUnits")
    protected Double plannedNonLaborUnits;
    @XmlElement(name = "PlannedStartDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedStartDate;
    @XmlElementRef(name = "PlannedTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> plannedTotalCost;
    @XmlElementRef(name = "PlannedTotalUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> plannedTotalUnits;
    @XmlElementRef(name = "PlannedValueCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> plannedValueCost;
    @XmlElementRef(name = "PlannedValueLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> plannedValueLaborUnits;
    @XmlElementRef(name = "PostResponsePessimisticFinish", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> postResponsePessimisticFinish;
    @XmlElementRef(name = "PostResponsePessimisticStart", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> postResponsePessimisticStart;
    @XmlElementRef(name = "PreResponsePessimisticFinish", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> preResponsePessimisticFinish;
    @XmlElementRef(name = "PreResponsePessimisticStart", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> preResponsePessimisticStart;
    @XmlElementRef(name = "PrimaryConstraintDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> primaryConstraintDate;
    @XmlElement(name = "PrimaryConstraintType")
    protected String primaryConstraintType;
    @XmlElement(name = "PrimaryResourceId")
    protected String primaryResourceId;
    @XmlElement(name = "PrimaryResourceName")
    protected String primaryResourceName;
    @XmlElementRef(name = "PrimaryResourceObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> primaryResourceObjectId;
    @XmlElement(name = "ProjectFlag")
    protected String projectFlag;
    @XmlElement(name = "ProjectId")
    protected String projectId;
    @XmlElement(name = "ProjectName")
    protected String projectName;
    @XmlElement(name = "ProjectObjectId")
    protected Integer projectObjectId;
    @XmlElement(name = "ProjectProjectFlag")
    protected String projectProjectFlag;
    @XmlElementRef(name = "RemainingDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingDuration;
    @XmlElementRef(name = "RemainingEarlyFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> remainingEarlyFinishDate;
    @XmlElementRef(name = "RemainingEarlyStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> remainingEarlyStartDate;
    @XmlElementRef(name = "RemainingExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingExpenseCost;
    @XmlElementRef(name = "RemainingFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingFloat;
    @XmlElementRef(name = "RemainingLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingLaborCost;
    @XmlElement(name = "RemainingLaborUnits")
    protected Double remainingLaborUnits;
    @XmlElementRef(name = "RemainingLateFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> remainingLateFinishDate;
    @XmlElementRef(name = "RemainingLateStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> remainingLateStartDate;
    @XmlElementRef(name = "RemainingMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingMaterialCost;
    @XmlElementRef(name = "RemainingNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingNonLaborCost;
    @XmlElement(name = "RemainingNonLaborUnits")
    protected Double remainingNonLaborUnits;
    @XmlElementRef(name = "RemainingTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingTotalCost;
    @XmlElementRef(name = "RemainingTotalUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> remainingTotalUnits;
    @XmlElementRef(name = "ResumeDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> resumeDate;
    @XmlElementRef(name = "ReviewFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> reviewFinishDate;
    @XmlElement(name = "ReviewRequired")
    protected Boolean reviewRequired;
    @XmlElement(name = "ReviewStatus")
    protected String reviewStatus;
    @XmlElementRef(name = "SchedulePercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> schedulePercentComplete;
    @XmlElementRef(name = "SchedulePerformanceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> schedulePerformanceIndex;
    @XmlElementRef(name = "SchedulePerformanceIndexLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> schedulePerformanceIndexLaborUnits;
    @XmlElementRef(name = "ScheduleVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> scheduleVariance;
    @XmlElementRef(name = "ScheduleVarianceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> scheduleVarianceIndex;
    @XmlElementRef(name = "ScheduleVarianceIndexLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> scheduleVarianceIndexLaborUnits;
    @XmlElementRef(name = "ScheduleVarianceLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> scheduleVarianceLaborUnits;
    @XmlElementRef(name = "SecondaryConstraintDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> secondaryConstraintDate;
    @XmlElement(name = "SecondaryConstraintType")
    protected String secondaryConstraintType;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElementRef(name = "StartDate1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> startDate1Variance;
    @XmlElementRef(name = "StartDateVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> startDateVariance;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "StatusCode")
    protected String statusCode;
    @XmlElementRef(name = "SuspendDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> suspendDate;
    @XmlElementRef(name = "ToCompletePerformanceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> toCompletePerformanceIndex;
    @XmlElementRef(name = "TotalCost1Variance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> totalCost1Variance;
    @XmlElementRef(name = "TotalCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> totalCostVariance;
    @XmlElementRef(name = "TotalFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> totalFloat;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElementRef(name = "UnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Double> unitsPercentComplete;
    @XmlElementRef(name = "UnreadCommentCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> unreadCommentCount;
    @XmlElement(name = "WBSCode")
    protected String wbsCode;
    @XmlElement(name = "WBSName")
    protected String wbsName;
    @XmlElementRef(name = "WBSObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Activity/V1", type = JAXBElement.class)
    protected JAXBElement<Integer> wbsObjectId;
    @XmlElement(name = "WBSPath")
    protected String wbsPath;

    /**
     * 获取accountingVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAccountingVariance() {
        return accountingVariance;
    }

    /**
     * 设置accountingVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAccountingVariance(JAXBElement<Double> value) {
        this.accountingVariance = value;
    }

    /**
     * 获取accountingVarianceLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAccountingVarianceLaborUnits() {
        return accountingVarianceLaborUnits;
    }

    /**
     * 设置accountingVarianceLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAccountingVarianceLaborUnits(JAXBElement<Double> value) {
        this.accountingVarianceLaborUnits = value;
    }

    /**
     * 获取activityOwnerUserId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getActivityOwnerUserId() {
        return activityOwnerUserId;
    }

    /**
     * 设置activityOwnerUserId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setActivityOwnerUserId(Integer value) {
        this.activityOwnerUserId = value;
    }

    /**
     * 获取actualDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualDuration() {
        return actualDuration;
    }

    /**
     * 设置actualDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualDuration(JAXBElement<Double> value) {
        this.actualDuration = value;
    }

    /**
     * 获取actualExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualExpenseCost() {
        return actualExpenseCost;
    }

    /**
     * 设置actualExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualExpenseCost(JAXBElement<Double> value) {
        this.actualExpenseCost = value;
    }

    /**
     * 获取actualFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getActualFinishDate() {
        return actualFinishDate;
    }

    /**
     * 设置actualFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setActualFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.actualFinishDate = value;
    }

    /**
     * 获取actualLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualLaborCost() {
        return actualLaborCost;
    }

    /**
     * 设置actualLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualLaborCost(Double value) {
        this.actualLaborCost = value;
    }

    /**
     * 获取actualLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualLaborUnits() {
        return actualLaborUnits;
    }

    /**
     * 设置actualLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualLaborUnits(Double value) {
        this.actualLaborUnits = value;
    }

    /**
     * 获取actualMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualMaterialCost() {
        return actualMaterialCost;
    }

    /**
     * 设置actualMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualMaterialCost(JAXBElement<Double> value) {
        this.actualMaterialCost = value;
    }

    /**
     * 获取actualNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualNonLaborCost() {
        return actualNonLaborCost;
    }

    /**
     * 设置actualNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualNonLaborCost(Double value) {
        this.actualNonLaborCost = value;
    }

    /**
     * 获取actualNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualNonLaborUnits() {
        return actualNonLaborUnits;
    }

    /**
     * 设置actualNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualNonLaborUnits(Double value) {
        this.actualNonLaborUnits = value;
    }

    /**
     * 获取actualStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getActualStartDate() {
        return actualStartDate;
    }

    /**
     * 设置actualStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setActualStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.actualStartDate = value;
    }

    /**
     * 获取actualThisPeriodLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualThisPeriodLaborCost() {
        return actualThisPeriodLaborCost;
    }

    /**
     * 设置actualThisPeriodLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualThisPeriodLaborCost(Double value) {
        this.actualThisPeriodLaborCost = value;
    }

    /**
     * 获取actualThisPeriodLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualThisPeriodLaborUnits() {
        return actualThisPeriodLaborUnits;
    }

    /**
     * 设置actualThisPeriodLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualThisPeriodLaborUnits(JAXBElement<Double> value) {
        this.actualThisPeriodLaborUnits = value;
    }

    /**
     * 获取actualThisPeriodMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualThisPeriodMaterialCost() {
        return actualThisPeriodMaterialCost;
    }

    /**
     * 设置actualThisPeriodMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualThisPeriodMaterialCost(JAXBElement<Double> value) {
        this.actualThisPeriodMaterialCost = value;
    }

    /**
     * 获取actualThisPeriodNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getActualThisPeriodNonLaborCost() {
        return actualThisPeriodNonLaborCost;
    }

    /**
     * 设置actualThisPeriodNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setActualThisPeriodNonLaborCost(Double value) {
        this.actualThisPeriodNonLaborCost = value;
    }

    /**
     * 获取actualThisPeriodNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualThisPeriodNonLaborUnits() {
        return actualThisPeriodNonLaborUnits;
    }

    /**
     * 设置actualThisPeriodNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualThisPeriodNonLaborUnits(JAXBElement<Double> value) {
        this.actualThisPeriodNonLaborUnits = value;
    }

    /**
     * 获取actualTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualTotalCost() {
        return actualTotalCost;
    }

    /**
     * 设置actualTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualTotalCost(JAXBElement<Double> value) {
        this.actualTotalCost = value;
    }

    /**
     * 获取actualTotalUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActualTotalUnits() {
        return actualTotalUnits;
    }

    /**
     * 设置actualTotalUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActualTotalUnits(JAXBElement<Double> value) {
        this.actualTotalUnits = value;
    }

    /**
     * 获取atCompletionDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAtCompletionDuration() {
        return atCompletionDuration;
    }

    /**
     * 设置atCompletionDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAtCompletionDuration(Double value) {
        this.atCompletionDuration = value;
    }

    /**
     * 获取atCompletionExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAtCompletionExpenseCost() {
        return atCompletionExpenseCost;
    }

    /**
     * 设置atCompletionExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAtCompletionExpenseCost(JAXBElement<Double> value) {
        this.atCompletionExpenseCost = value;
    }

    /**
     * 获取atCompletionLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAtCompletionLaborCost() {
        return atCompletionLaborCost;
    }

    /**
     * 设置atCompletionLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAtCompletionLaborCost(Double value) {
        this.atCompletionLaborCost = value;
    }

    /**
     * 获取atCompletionLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAtCompletionLaborUnits() {
        return atCompletionLaborUnits;
    }

    /**
     * 设置atCompletionLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAtCompletionLaborUnits(Double value) {
        this.atCompletionLaborUnits = value;
    }

    /**
     * 获取atCompletionLaborUnitsVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAtCompletionLaborUnitsVariance() {
        return atCompletionLaborUnitsVariance;
    }

    /**
     * 设置atCompletionLaborUnitsVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAtCompletionLaborUnitsVariance(JAXBElement<Double> value) {
        this.atCompletionLaborUnitsVariance = value;
    }

    /**
     * 获取atCompletionMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAtCompletionMaterialCost() {
        return atCompletionMaterialCost;
    }

    /**
     * 设置atCompletionMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAtCompletionMaterialCost(JAXBElement<Double> value) {
        this.atCompletionMaterialCost = value;
    }

    /**
     * 获取atCompletionNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAtCompletionNonLaborCost() {
        return atCompletionNonLaborCost;
    }

    /**
     * 设置atCompletionNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAtCompletionNonLaborCost(Double value) {
        this.atCompletionNonLaborCost = value;
    }

    /**
     * 获取atCompletionNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAtCompletionNonLaborUnits() {
        return atCompletionNonLaborUnits;
    }

    /**
     * 设置atCompletionNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAtCompletionNonLaborUnits(Double value) {
        this.atCompletionNonLaborUnits = value;
    }

    /**
     * 获取atCompletionTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAtCompletionTotalCost() {
        return atCompletionTotalCost;
    }

    /**
     * 设置atCompletionTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAtCompletionTotalCost(JAXBElement<Double> value) {
        this.atCompletionTotalCost = value;
    }

    /**
     * 获取atCompletionTotalUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAtCompletionTotalUnits() {
        return atCompletionTotalUnits;
    }

    /**
     * 设置atCompletionTotalUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAtCompletionTotalUnits(JAXBElement<Double> value) {
        this.atCompletionTotalUnits = value;
    }

    /**
     * 获取atCompletionVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAtCompletionVariance() {
        return atCompletionVariance;
    }

    /**
     * 设置atCompletionVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAtCompletionVariance(JAXBElement<Double> value) {
        this.atCompletionVariance = value;
    }

    /**
     * 获取autoComputeActuals属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAutoComputeActuals() {
        return autoComputeActuals;
    }

    /**
     * 设置autoComputeActuals属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoComputeActuals(Boolean value) {
        this.autoComputeActuals = value;
    }

    /**
     * 获取baseline1Duration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1Duration() {
        return baseline1Duration;
    }

    /**
     * 设置baseline1Duration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1Duration(JAXBElement<Double> value) {
        this.baseline1Duration = value;
    }

    /**
     * 获取baseline1FinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getBaseline1FinishDate() {
        return baseline1FinishDate;
    }

    /**
     * 设置baseline1FinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setBaseline1FinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.baseline1FinishDate = value;
    }

    /**
     * 获取baseline1PlannedDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedDuration() {
        return baseline1PlannedDuration;
    }

    /**
     * 设置baseline1PlannedDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedDuration(JAXBElement<Double> value) {
        this.baseline1PlannedDuration = value;
    }

    /**
     * 获取baseline1PlannedExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedExpenseCost() {
        return baseline1PlannedExpenseCost;
    }

    /**
     * 设置baseline1PlannedExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedExpenseCost(JAXBElement<Double> value) {
        this.baseline1PlannedExpenseCost = value;
    }

    /**
     * 获取baseline1PlannedLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedLaborCost() {
        return baseline1PlannedLaborCost;
    }

    /**
     * 设置baseline1PlannedLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedLaborCost(JAXBElement<Double> value) {
        this.baseline1PlannedLaborCost = value;
    }

    /**
     * 获取baseline1PlannedLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedLaborUnits() {
        return baseline1PlannedLaborUnits;
    }

    /**
     * 设置baseline1PlannedLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedLaborUnits(JAXBElement<Double> value) {
        this.baseline1PlannedLaborUnits = value;
    }

    /**
     * 获取baseline1PlannedMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedMaterialCost() {
        return baseline1PlannedMaterialCost;
    }

    /**
     * 设置baseline1PlannedMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedMaterialCost(JAXBElement<Double> value) {
        this.baseline1PlannedMaterialCost = value;
    }

    /**
     * 获取baseline1PlannedNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedNonLaborCost() {
        return baseline1PlannedNonLaborCost;
    }

    /**
     * 设置baseline1PlannedNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedNonLaborCost(JAXBElement<Double> value) {
        this.baseline1PlannedNonLaborCost = value;
    }

    /**
     * 获取baseline1PlannedNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedNonLaborUnits() {
        return baseline1PlannedNonLaborUnits;
    }

    /**
     * 设置baseline1PlannedNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedNonLaborUnits(JAXBElement<Double> value) {
        this.baseline1PlannedNonLaborUnits = value;
    }

    /**
     * 获取baseline1PlannedTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaseline1PlannedTotalCost() {
        return baseline1PlannedTotalCost;
    }

    /**
     * 设置baseline1PlannedTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaseline1PlannedTotalCost(JAXBElement<Double> value) {
        this.baseline1PlannedTotalCost = value;
    }

    /**
     * 获取baseline1StartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getBaseline1StartDate() {
        return baseline1StartDate;
    }

    /**
     * 设置baseline1StartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setBaseline1StartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.baseline1StartDate = value;
    }

    /**
     * 获取baselineDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselineDuration() {
        return baselineDuration;
    }

    /**
     * 设置baselineDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselineDuration(JAXBElement<Double> value) {
        this.baselineDuration = value;
    }

    /**
     * 获取baselineFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getBaselineFinishDate() {
        return baselineFinishDate;
    }

    /**
     * 设置baselineFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setBaselineFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.baselineFinishDate = value;
    }

    /**
     * 获取baselinePlannedDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedDuration() {
        return baselinePlannedDuration;
    }

    /**
     * 设置baselinePlannedDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedDuration(JAXBElement<Double> value) {
        this.baselinePlannedDuration = value;
    }

    /**
     * 获取baselinePlannedExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedExpenseCost() {
        return baselinePlannedExpenseCost;
    }

    /**
     * 设置baselinePlannedExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedExpenseCost(JAXBElement<Double> value) {
        this.baselinePlannedExpenseCost = value;
    }

    /**
     * 获取baselinePlannedLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedLaborCost() {
        return baselinePlannedLaborCost;
    }

    /**
     * 设置baselinePlannedLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedLaborCost(JAXBElement<Double> value) {
        this.baselinePlannedLaborCost = value;
    }

    /**
     * 获取baselinePlannedLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedLaborUnits() {
        return baselinePlannedLaborUnits;
    }

    /**
     * 设置baselinePlannedLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedLaborUnits(JAXBElement<Double> value) {
        this.baselinePlannedLaborUnits = value;
    }

    /**
     * 获取baselinePlannedMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedMaterialCost() {
        return baselinePlannedMaterialCost;
    }

    /**
     * 设置baselinePlannedMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedMaterialCost(JAXBElement<Double> value) {
        this.baselinePlannedMaterialCost = value;
    }

    /**
     * 获取baselinePlannedNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedNonLaborCost() {
        return baselinePlannedNonLaborCost;
    }

    /**
     * 设置baselinePlannedNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedNonLaborCost(JAXBElement<Double> value) {
        this.baselinePlannedNonLaborCost = value;
    }

    /**
     * 获取baselinePlannedNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedNonLaborUnits() {
        return baselinePlannedNonLaborUnits;
    }

    /**
     * 设置baselinePlannedNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedNonLaborUnits(JAXBElement<Double> value) {
        this.baselinePlannedNonLaborUnits = value;
    }

    /**
     * 获取baselinePlannedTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBaselinePlannedTotalCost() {
        return baselinePlannedTotalCost;
    }

    /**
     * 设置baselinePlannedTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBaselinePlannedTotalCost(JAXBElement<Double> value) {
        this.baselinePlannedTotalCost = value;
    }

    /**
     * 获取baselineStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getBaselineStartDate() {
        return baselineStartDate;
    }

    /**
     * 设置baselineStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setBaselineStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.baselineStartDate = value;
    }

    /**
     * 获取budgetAtCompletion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getBudgetAtCompletion() {
        return budgetAtCompletion;
    }

    /**
     * 设置budgetAtCompletion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setBudgetAtCompletion(JAXBElement<Double> value) {
        this.budgetAtCompletion = value;
    }

    /**
     * 获取cbsCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCBSCode() {
        return cbsCode;
    }

    /**
     * 设置cbsCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCBSCode(String value) {
        this.cbsCode = value;
    }

    /**
     * 获取cbsId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCBSId() {
        return cbsId;
    }

    /**
     * 设置cbsId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCBSId(JAXBElement<Integer> value) {
        this.cbsId = value;
    }

    /**
     * 获取cbsObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCBSObjectId() {
        return cbsObjectId;
    }

    /**
     * 设置cbsObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCBSObjectId(JAXBElement<Integer> value) {
        this.cbsObjectId = value;
    }

    /**
     * 获取calendarName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalendarName() {
        return calendarName;
    }

    /**
     * 设置calendarName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalendarName(String value) {
        this.calendarName = value;
    }

    /**
     * 获取calendarObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCalendarObjectId() {
        return calendarObjectId;
    }

    /**
     * 设置calendarObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCalendarObjectId(Integer value) {
        this.calendarObjectId = value;
    }

    /**
     * 获取costPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostPercentComplete() {
        return costPercentComplete;
    }

    /**
     * 设置costPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostPercentComplete(JAXBElement<Double> value) {
        this.costPercentComplete = value;
    }

    /**
     * 获取costPercentOfPlanned属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostPercentOfPlanned() {
        return costPercentOfPlanned;
    }

    /**
     * 设置costPercentOfPlanned属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostPercentOfPlanned(JAXBElement<Double> value) {
        this.costPercentOfPlanned = value;
    }

    /**
     * 获取costPerformanceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostPerformanceIndex() {
        return costPerformanceIndex;
    }

    /**
     * 设置costPerformanceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostPerformanceIndex(JAXBElement<Double> value) {
        this.costPerformanceIndex = value;
    }

    /**
     * 获取costPerformanceIndexLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostPerformanceIndexLaborUnits() {
        return costPerformanceIndexLaborUnits;
    }

    /**
     * 设置costPerformanceIndexLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostPerformanceIndexLaborUnits(JAXBElement<Double> value) {
        this.costPerformanceIndexLaborUnits = value;
    }

    /**
     * 获取costVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostVariance() {
        return costVariance;
    }

    /**
     * 设置costVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostVariance(JAXBElement<Double> value) {
        this.costVariance = value;
    }

    /**
     * 获取costVarianceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostVarianceIndex() {
        return costVarianceIndex;
    }

    /**
     * 设置costVarianceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostVarianceIndex(JAXBElement<Double> value) {
        this.costVarianceIndex = value;
    }

    /**
     * 获取costVarianceIndexLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostVarianceIndexLaborUnits() {
        return costVarianceIndexLaborUnits;
    }

    /**
     * 设置costVarianceIndexLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostVarianceIndexLaborUnits(JAXBElement<Double> value) {
        this.costVarianceIndexLaborUnits = value;
    }

    /**
     * 获取costVarianceLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCostVarianceLaborUnits() {
        return costVarianceLaborUnits;
    }

    /**
     * 设置costVarianceLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCostVarianceLaborUnits(JAXBElement<Double> value) {
        this.costVarianceLaborUnits = value;
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
     * 获取dataDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataDate() {
        return dataDate;
    }

    /**
     * 设置dataDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataDate(JAXBElement<XMLGregorianCalendar> value) {
        this.dataDate = value;
    }

    /**
     * 获取duration1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getDuration1Variance() {
        return duration1Variance;
    }

    /**
     * 设置duration1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setDuration1Variance(JAXBElement<Double> value) {
        this.duration1Variance = value;
    }

    /**
     * 获取durationPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getDurationPercentComplete() {
        return durationPercentComplete;
    }

    /**
     * 设置durationPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setDurationPercentComplete(JAXBElement<Double> value) {
        this.durationPercentComplete = value;
    }

    /**
     * 获取durationPercentOfPlanned属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getDurationPercentOfPlanned() {
        return durationPercentOfPlanned;
    }

    /**
     * 设置durationPercentOfPlanned属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setDurationPercentOfPlanned(JAXBElement<Double> value) {
        this.durationPercentOfPlanned = value;
    }

    /**
     * 获取durationType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDurationType() {
        return durationType;
    }

    /**
     * 设置durationType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDurationType(String value) {
        this.durationType = value;
    }

    /**
     * 获取durationVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getDurationVariance() {
        return durationVariance;
    }

    /**
     * 设置durationVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setDurationVariance(JAXBElement<Double> value) {
        this.durationVariance = value;
    }

    /**
     * 获取earlyFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getEarlyFinishDate() {
        return earlyFinishDate;
    }

    /**
     * 设置earlyFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setEarlyFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.earlyFinishDate = value;
    }

    /**
     * 获取earlyStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getEarlyStartDate() {
        return earlyStartDate;
    }

    /**
     * 设置earlyStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setEarlyStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.earlyStartDate = value;
    }

    /**
     * 获取earnedValueCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEarnedValueCost() {
        return earnedValueCost;
    }

    /**
     * 设置earnedValueCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEarnedValueCost(JAXBElement<Double> value) {
        this.earnedValueCost = value;
    }

    /**
     * 获取earnedValueLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEarnedValueLaborUnits() {
        return earnedValueLaborUnits;
    }

    /**
     * 设置earnedValueLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEarnedValueLaborUnits(JAXBElement<Double> value) {
        this.earnedValueLaborUnits = value;
    }

    /**
     * 获取estimateAtCompletionCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEstimateAtCompletionCost() {
        return estimateAtCompletionCost;
    }

    /**
     * 设置estimateAtCompletionCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEstimateAtCompletionCost(JAXBElement<Double> value) {
        this.estimateAtCompletionCost = value;
    }

    /**
     * 获取estimateAtCompletionLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEstimateAtCompletionLaborUnits() {
        return estimateAtCompletionLaborUnits;
    }

    /**
     * 设置estimateAtCompletionLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEstimateAtCompletionLaborUnits(JAXBElement<Double> value) {
        this.estimateAtCompletionLaborUnits = value;
    }

    /**
     * 获取estimateToComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEstimateToComplete() {
        return estimateToComplete;
    }

    /**
     * 设置estimateToComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEstimateToComplete(JAXBElement<Double> value) {
        this.estimateToComplete = value;
    }

    /**
     * 获取estimateToCompleteLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getEstimateToCompleteLaborUnits() {
        return estimateToCompleteLaborUnits;
    }

    /**
     * 设置estimateToCompleteLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setEstimateToCompleteLaborUnits(JAXBElement<Double> value) {
        this.estimateToCompleteLaborUnits = value;
    }

    /**
     * 获取estimatedWeight属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEstimatedWeight() {
        return estimatedWeight;
    }

    /**
     * 设置estimatedWeight属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEstimatedWeight(Double value) {
        this.estimatedWeight = value;
    }

    /**
     * 获取expectedFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getExpectedFinishDate() {
        return expectedFinishDate;
    }

    /**
     * 设置expectedFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setExpectedFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.expectedFinishDate = value;
    }

    /**
     * 获取expenseCost1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getExpenseCost1Variance() {
        return expenseCost1Variance;
    }

    /**
     * 设置expenseCost1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setExpenseCost1Variance(JAXBElement<Double> value) {
        this.expenseCost1Variance = value;
    }

    /**
     * 获取expenseCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getExpenseCostPercentComplete() {
        return expenseCostPercentComplete;
    }

    /**
     * 设置expenseCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setExpenseCostPercentComplete(JAXBElement<Double> value) {
        this.expenseCostPercentComplete = value;
    }

    /**
     * 获取expenseCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getExpenseCostVariance() {
        return expenseCostVariance;
    }

    /**
     * 设置expenseCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setExpenseCostVariance(JAXBElement<Double> value) {
        this.expenseCostVariance = value;
    }

    /**
     * 获取externalEarlyStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getExternalEarlyStartDate() {
        return externalEarlyStartDate;
    }

    /**
     * 设置externalEarlyStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setExternalEarlyStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.externalEarlyStartDate = value;
    }

    /**
     * 获取externalLateFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getExternalLateFinishDate() {
        return externalLateFinishDate;
    }

    /**
     * 设置externalLateFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setExternalLateFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.externalLateFinishDate = value;
    }

    /**
     * 获取feedback属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * 设置feedback属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeedback(String value) {
        this.feedback = value;
    }

    /**
     * 获取finishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFinishDate() {
        return finishDate;
    }

    /**
     * 设置finishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFinishDate(XMLGregorianCalendar value) {
        this.finishDate = value;
    }

    /**
     * 获取finishDate1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getFinishDate1Variance() {
        return finishDate1Variance;
    }

    /**
     * 设置finishDate1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setFinishDate1Variance(JAXBElement<Double> value) {
        this.finishDate1Variance = value;
    }

    /**
     * 获取finishDateVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getFinishDateVariance() {
        return finishDateVariance;
    }

    /**
     * 设置finishDateVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setFinishDateVariance(JAXBElement<Double> value) {
        this.finishDateVariance = value;
    }

    /**
     * 获取floatPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getFloatPath() {
        return floatPath;
    }

    /**
     * 设置floatPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setFloatPath(JAXBElement<Integer> value) {
        this.floatPath = value;
    }

    /**
     * 获取floatPathOrder属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getFloatPathOrder() {
        return floatPathOrder;
    }

    /**
     * 设置floatPathOrder属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setFloatPathOrder(JAXBElement<Integer> value) {
        this.floatPathOrder = value;
    }

    /**
     * 获取freeFloat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getFreeFloat() {
        return freeFloat;
    }

    /**
     * 设置freeFloat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setFreeFloat(JAXBElement<Double> value) {
        this.freeFloat = value;
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
     * 获取hasFutureBucketData属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getHasFutureBucketData() {
        return hasFutureBucketData;
    }

    /**
     * 设置hasFutureBucketData属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setHasFutureBucketData(JAXBElement<Boolean> value) {
        this.hasFutureBucketData = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * 获取isCritical属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsCritical() {
        return isCritical;
    }

    /**
     * 设置isCritical属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsCritical(JAXBElement<Boolean> value) {
        this.isCritical = value;
    }

    /**
     * 获取isLongestPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsLongestPath() {
        return isLongestPath;
    }

    /**
     * 设置isLongestPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsLongestPath(JAXBElement<Boolean> value) {
        this.isLongestPath = value;
    }

    /**
     * 获取isNewFeedback属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNewFeedback() {
        return isNewFeedback;
    }

    /**
     * 设置isNewFeedback属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNewFeedback(Boolean value) {
        this.isNewFeedback = value;
    }

    /**
     * 获取isStarred属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsStarred() {
        return isStarred;
    }

    /**
     * 设置isStarred属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsStarred(JAXBElement<Boolean> value) {
        this.isStarred = value;
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
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIsWorkPackage() {
        return isWorkPackage;
    }

    /**
     * 设置isWorkPackage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIsWorkPackage(JAXBElement<Boolean> value) {
        this.isWorkPackage = value;
    }

    /**
     * 获取laborCost1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLaborCost1Variance() {
        return laborCost1Variance;
    }

    /**
     * 设置laborCost1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLaborCost1Variance(JAXBElement<Double> value) {
        this.laborCost1Variance = value;
    }

    /**
     * 获取laborCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLaborCostPercentComplete() {
        return laborCostPercentComplete;
    }

    /**
     * 设置laborCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLaborCostPercentComplete(JAXBElement<Double> value) {
        this.laborCostPercentComplete = value;
    }

    /**
     * 获取laborCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLaborCostVariance() {
        return laborCostVariance;
    }

    /**
     * 设置laborCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLaborCostVariance(JAXBElement<Double> value) {
        this.laborCostVariance = value;
    }

    /**
     * 获取laborUnits1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLaborUnits1Variance() {
        return laborUnits1Variance;
    }

    /**
     * 设置laborUnits1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLaborUnits1Variance(JAXBElement<Double> value) {
        this.laborUnits1Variance = value;
    }

    /**
     * 获取laborUnitsPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLaborUnitsPercentComplete() {
        return laborUnitsPercentComplete;
    }

    /**
     * 设置laborUnitsPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLaborUnitsPercentComplete(JAXBElement<Double> value) {
        this.laborUnitsPercentComplete = value;
    }

    /**
     * 获取laborUnitsVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLaborUnitsVariance() {
        return laborUnitsVariance;
    }

    /**
     * 设置laborUnitsVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLaborUnitsVariance(JAXBElement<Double> value) {
        this.laborUnitsVariance = value;
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
     * 获取lateFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLateFinishDate() {
        return lateFinishDate;
    }

    /**
     * 设置lateFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLateFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lateFinishDate = value;
    }

    /**
     * 获取lateStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLateStartDate() {
        return lateStartDate;
    }

    /**
     * 设置lateStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLateStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lateStartDate = value;
    }

    /**
     * 获取levelingPriority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelingPriority() {
        return levelingPriority;
    }

    /**
     * 设置levelingPriority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelingPriority(String value) {
        this.levelingPriority = value;
    }

    /**
     * 获取locationName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * 设置locationName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationName(String value) {
        this.locationName = value;
    }

    /**
     * 获取locationObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getLocationObjectId() {
        return locationObjectId;
    }

    /**
     * 设置locationObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setLocationObjectId(JAXBElement<Integer> value) {
        this.locationObjectId = value;
    }

    /**
     * 获取materialCost1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMaterialCost1Variance() {
        return materialCost1Variance;
    }

    /**
     * 设置materialCost1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMaterialCost1Variance(JAXBElement<Double> value) {
        this.materialCost1Variance = value;
    }

    /**
     * 获取materialCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMaterialCostPercentComplete() {
        return materialCostPercentComplete;
    }

    /**
     * 设置materialCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMaterialCostPercentComplete(JAXBElement<Double> value) {
        this.materialCostPercentComplete = value;
    }

    /**
     * 获取materialCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMaterialCostVariance() {
        return materialCostVariance;
    }

    /**
     * 设置materialCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMaterialCostVariance(JAXBElement<Double> value) {
        this.materialCostVariance = value;
    }

    /**
     * 获取maximumDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMaximumDuration() {
        return maximumDuration;
    }

    /**
     * 设置maximumDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMaximumDuration(JAXBElement<Double> value) {
        this.maximumDuration = value;
    }

    /**
     * 获取minimumDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMinimumDuration() {
        return minimumDuration;
    }

    /**
     * 设置minimumDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMinimumDuration(JAXBElement<Double> value) {
        this.minimumDuration = value;
    }

    /**
     * 获取mostLikelyDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMostLikelyDuration() {
        return mostLikelyDuration;
    }

    /**
     * 设置mostLikelyDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMostLikelyDuration(JAXBElement<Double> value) {
        this.mostLikelyDuration = value;
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
     * 获取nonLaborCost1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNonLaborCost1Variance() {
        return nonLaborCost1Variance;
    }

    /**
     * 设置nonLaborCost1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNonLaborCost1Variance(JAXBElement<Double> value) {
        this.nonLaborCost1Variance = value;
    }

    /**
     * 获取nonLaborCostPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNonLaborCostPercentComplete() {
        return nonLaborCostPercentComplete;
    }

    /**
     * 设置nonLaborCostPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNonLaborCostPercentComplete(JAXBElement<Double> value) {
        this.nonLaborCostPercentComplete = value;
    }

    /**
     * 获取nonLaborCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNonLaborCostVariance() {
        return nonLaborCostVariance;
    }

    /**
     * 设置nonLaborCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNonLaborCostVariance(JAXBElement<Double> value) {
        this.nonLaborCostVariance = value;
    }

    /**
     * 获取nonLaborUnits1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNonLaborUnits1Variance() {
        return nonLaborUnits1Variance;
    }

    /**
     * 设置nonLaborUnits1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNonLaborUnits1Variance(JAXBElement<Double> value) {
        this.nonLaborUnits1Variance = value;
    }

    /**
     * 获取nonLaborUnitsPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNonLaborUnitsPercentComplete() {
        return nonLaborUnitsPercentComplete;
    }

    /**
     * 设置nonLaborUnitsPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNonLaborUnitsPercentComplete(JAXBElement<Double> value) {
        this.nonLaborUnitsPercentComplete = value;
    }

    /**
     * 获取nonLaborUnitsVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNonLaborUnitsVariance() {
        return nonLaborUnitsVariance;
    }

    /**
     * 设置nonLaborUnitsVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNonLaborUnitsVariance(JAXBElement<Double> value) {
        this.nonLaborUnitsVariance = value;
    }

    /**
     * 获取notesToResources属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotesToResources() {
        return notesToResources;
    }

    /**
     * 设置notesToResources属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotesToResources(String value) {
        this.notesToResources = value;
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
     * 获取ownerIDArray属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerIDArray() {
        return ownerIDArray;
    }

    /**
     * 设置ownerIDArray属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerIDArray(String value) {
        this.ownerIDArray = value;
    }

    /**
     * 获取ownerNamesArray属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerNamesArray() {
        return ownerNamesArray;
    }

    /**
     * 设置ownerNamesArray属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerNamesArray(String value) {
        this.ownerNamesArray = value;
    }

    /**
     * 获取percentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPercentComplete() {
        return percentComplete;
    }

    /**
     * 设置percentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPercentComplete(JAXBElement<Double> value) {
        this.percentComplete = value;
    }

    /**
     * 获取percentCompleteType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentCompleteType() {
        return percentCompleteType;
    }

    /**
     * 设置percentCompleteType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentCompleteType(String value) {
        this.percentCompleteType = value;
    }

    /**
     * 获取performancePercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPerformancePercentComplete() {
        return performancePercentComplete;
    }

    /**
     * 设置performancePercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPerformancePercentComplete(JAXBElement<Double> value) {
        this.performancePercentComplete = value;
    }

    /**
     * 获取physicalPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPhysicalPercentComplete() {
        return physicalPercentComplete;
    }

    /**
     * 设置physicalPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPhysicalPercentComplete(Double value) {
        this.physicalPercentComplete = value;
    }

    /**
     * 获取plannedDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPlannedDuration() {
        return plannedDuration;
    }

    /**
     * 设置plannedDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPlannedDuration(Double value) {
        this.plannedDuration = value;
    }

    /**
     * 获取plannedExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPlannedExpenseCost() {
        return plannedExpenseCost;
    }

    /**
     * 设置plannedExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPlannedExpenseCost(JAXBElement<Double> value) {
        this.plannedExpenseCost = value;
    }

    /**
     * 获取plannedFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedFinishDate() {
        return plannedFinishDate;
    }

    /**
     * 设置plannedFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedFinishDate(XMLGregorianCalendar value) {
        this.plannedFinishDate = value;
    }

    /**
     * 获取plannedLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPlannedLaborCost() {
        return plannedLaborCost;
    }

    /**
     * 设置plannedLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPlannedLaborCost(Double value) {
        this.plannedLaborCost = value;
    }

    /**
     * 获取plannedLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPlannedLaborUnits() {
        return plannedLaborUnits;
    }

    /**
     * 设置plannedLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPlannedLaborUnits(Double value) {
        this.plannedLaborUnits = value;
    }

    /**
     * 获取plannedMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPlannedMaterialCost() {
        return plannedMaterialCost;
    }

    /**
     * 设置plannedMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPlannedMaterialCost(JAXBElement<Double> value) {
        this.plannedMaterialCost = value;
    }

    /**
     * 获取plannedNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPlannedNonLaborCost() {
        return plannedNonLaborCost;
    }

    /**
     * 设置plannedNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPlannedNonLaborCost(Double value) {
        this.plannedNonLaborCost = value;
    }

    /**
     * 获取plannedNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPlannedNonLaborUnits() {
        return plannedNonLaborUnits;
    }

    /**
     * 设置plannedNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPlannedNonLaborUnits(Double value) {
        this.plannedNonLaborUnits = value;
    }

    /**
     * 获取plannedStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedStartDate() {
        return plannedStartDate;
    }

    /**
     * 设置plannedStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedStartDate(XMLGregorianCalendar value) {
        this.plannedStartDate = value;
    }

    /**
     * 获取plannedTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPlannedTotalCost() {
        return plannedTotalCost;
    }

    /**
     * 设置plannedTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPlannedTotalCost(JAXBElement<Double> value) {
        this.plannedTotalCost = value;
    }

    /**
     * 获取plannedTotalUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPlannedTotalUnits() {
        return plannedTotalUnits;
    }

    /**
     * 设置plannedTotalUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPlannedTotalUnits(JAXBElement<Double> value) {
        this.plannedTotalUnits = value;
    }

    /**
     * 获取plannedValueCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPlannedValueCost() {
        return plannedValueCost;
    }

    /**
     * 设置plannedValueCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPlannedValueCost(JAXBElement<Double> value) {
        this.plannedValueCost = value;
    }

    /**
     * 获取plannedValueLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getPlannedValueLaborUnits() {
        return plannedValueLaborUnits;
    }

    /**
     * 设置plannedValueLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setPlannedValueLaborUnits(JAXBElement<Double> value) {
        this.plannedValueLaborUnits = value;
    }

    /**
     * 获取postResponsePessimisticFinish属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getPostResponsePessimisticFinish() {
        return postResponsePessimisticFinish;
    }

    /**
     * 设置postResponsePessimisticFinish属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setPostResponsePessimisticFinish(JAXBElement<XMLGregorianCalendar> value) {
        this.postResponsePessimisticFinish = value;
    }

    /**
     * 获取postResponsePessimisticStart属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getPostResponsePessimisticStart() {
        return postResponsePessimisticStart;
    }

    /**
     * 设置postResponsePessimisticStart属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setPostResponsePessimisticStart(JAXBElement<XMLGregorianCalendar> value) {
        this.postResponsePessimisticStart = value;
    }

    /**
     * 获取preResponsePessimisticFinish属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getPreResponsePessimisticFinish() {
        return preResponsePessimisticFinish;
    }

    /**
     * 设置preResponsePessimisticFinish属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setPreResponsePessimisticFinish(JAXBElement<XMLGregorianCalendar> value) {
        this.preResponsePessimisticFinish = value;
    }

    /**
     * 获取preResponsePessimisticStart属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getPreResponsePessimisticStart() {
        return preResponsePessimisticStart;
    }

    /**
     * 设置preResponsePessimisticStart属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setPreResponsePessimisticStart(JAXBElement<XMLGregorianCalendar> value) {
        this.preResponsePessimisticStart = value;
    }

    /**
     * 获取primaryConstraintDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getPrimaryConstraintDate() {
        return primaryConstraintDate;
    }

    /**
     * 设置primaryConstraintDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setPrimaryConstraintDate(JAXBElement<XMLGregorianCalendar> value) {
        this.primaryConstraintDate = value;
    }

    /**
     * 获取primaryConstraintType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryConstraintType() {
        return primaryConstraintType;
    }

    /**
     * 设置primaryConstraintType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryConstraintType(String value) {
        this.primaryConstraintType = value;
    }

    /**
     * 获取primaryResourceId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryResourceId() {
        return primaryResourceId;
    }

    /**
     * 设置primaryResourceId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryResourceId(String value) {
        this.primaryResourceId = value;
    }

    /**
     * 获取primaryResourceName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryResourceName() {
        return primaryResourceName;
    }

    /**
     * 设置primaryResourceName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryResourceName(String value) {
        this.primaryResourceName = value;
    }

    /**
     * 获取primaryResourceObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getPrimaryResourceObjectId() {
        return primaryResourceObjectId;
    }

    /**
     * 设置primaryResourceObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setPrimaryResourceObjectId(JAXBElement<Integer> value) {
        this.primaryResourceObjectId = value;
    }

    /**
     * 获取projectFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectFlag() {
        return projectFlag;
    }

    /**
     * 设置projectFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectFlag(String value) {
        this.projectFlag = value;
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
     * 获取projectName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置projectName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectName(String value) {
        this.projectName = value;
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
     * 获取projectProjectFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectProjectFlag() {
        return projectProjectFlag;
    }

    /**
     * 设置projectProjectFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectProjectFlag(String value) {
        this.projectProjectFlag = value;
    }

    /**
     * 获取remainingDuration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingDuration() {
        return remainingDuration;
    }

    /**
     * 设置remainingDuration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingDuration(JAXBElement<Double> value) {
        this.remainingDuration = value;
    }

    /**
     * 获取remainingEarlyFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getRemainingEarlyFinishDate() {
        return remainingEarlyFinishDate;
    }

    /**
     * 设置remainingEarlyFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setRemainingEarlyFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.remainingEarlyFinishDate = value;
    }

    /**
     * 获取remainingEarlyStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getRemainingEarlyStartDate() {
        return remainingEarlyStartDate;
    }

    /**
     * 设置remainingEarlyStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setRemainingEarlyStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.remainingEarlyStartDate = value;
    }

    /**
     * 获取remainingExpenseCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingExpenseCost() {
        return remainingExpenseCost;
    }

    /**
     * 设置remainingExpenseCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingExpenseCost(JAXBElement<Double> value) {
        this.remainingExpenseCost = value;
    }

    /**
     * 获取remainingFloat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingFloat() {
        return remainingFloat;
    }

    /**
     * 设置remainingFloat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingFloat(JAXBElement<Double> value) {
        this.remainingFloat = value;
    }

    /**
     * 获取remainingLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingLaborCost() {
        return remainingLaborCost;
    }

    /**
     * 设置remainingLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingLaborCost(JAXBElement<Double> value) {
        this.remainingLaborCost = value;
    }

    /**
     * 获取remainingLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRemainingLaborUnits() {
        return remainingLaborUnits;
    }

    /**
     * 设置remainingLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRemainingLaborUnits(Double value) {
        this.remainingLaborUnits = value;
    }

    /**
     * 获取remainingLateFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getRemainingLateFinishDate() {
        return remainingLateFinishDate;
    }

    /**
     * 设置remainingLateFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setRemainingLateFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.remainingLateFinishDate = value;
    }

    /**
     * 获取remainingLateStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getRemainingLateStartDate() {
        return remainingLateStartDate;
    }

    /**
     * 设置remainingLateStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setRemainingLateStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.remainingLateStartDate = value;
    }

    /**
     * 获取remainingMaterialCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingMaterialCost() {
        return remainingMaterialCost;
    }

    /**
     * 设置remainingMaterialCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingMaterialCost(JAXBElement<Double> value) {
        this.remainingMaterialCost = value;
    }

    /**
     * 获取remainingNonLaborCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingNonLaborCost() {
        return remainingNonLaborCost;
    }

    /**
     * 设置remainingNonLaborCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingNonLaborCost(JAXBElement<Double> value) {
        this.remainingNonLaborCost = value;
    }

    /**
     * 获取remainingNonLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRemainingNonLaborUnits() {
        return remainingNonLaborUnits;
    }

    /**
     * 设置remainingNonLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRemainingNonLaborUnits(Double value) {
        this.remainingNonLaborUnits = value;
    }

    /**
     * 获取remainingTotalCost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingTotalCost() {
        return remainingTotalCost;
    }

    /**
     * 设置remainingTotalCost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingTotalCost(JAXBElement<Double> value) {
        this.remainingTotalCost = value;
    }

    /**
     * 获取remainingTotalUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRemainingTotalUnits() {
        return remainingTotalUnits;
    }

    /**
     * 设置remainingTotalUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRemainingTotalUnits(JAXBElement<Double> value) {
        this.remainingTotalUnits = value;
    }

    /**
     * 获取resumeDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getResumeDate() {
        return resumeDate;
    }

    /**
     * 设置resumeDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setResumeDate(JAXBElement<XMLGregorianCalendar> value) {
        this.resumeDate = value;
    }

    /**
     * 获取reviewFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getReviewFinishDate() {
        return reviewFinishDate;
    }

    /**
     * 设置reviewFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setReviewFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.reviewFinishDate = value;
    }

    /**
     * 获取reviewRequired属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReviewRequired() {
        return reviewRequired;
    }

    /**
     * 设置reviewRequired属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReviewRequired(Boolean value) {
        this.reviewRequired = value;
    }

    /**
     * 获取reviewStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReviewStatus() {
        return reviewStatus;
    }

    /**
     * 设置reviewStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReviewStatus(String value) {
        this.reviewStatus = value;
    }

    /**
     * 获取schedulePercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSchedulePercentComplete() {
        return schedulePercentComplete;
    }

    /**
     * 设置schedulePercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSchedulePercentComplete(JAXBElement<Double> value) {
        this.schedulePercentComplete = value;
    }

    /**
     * 获取schedulePerformanceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSchedulePerformanceIndex() {
        return schedulePerformanceIndex;
    }

    /**
     * 设置schedulePerformanceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSchedulePerformanceIndex(JAXBElement<Double> value) {
        this.schedulePerformanceIndex = value;
    }

    /**
     * 获取schedulePerformanceIndexLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSchedulePerformanceIndexLaborUnits() {
        return schedulePerformanceIndexLaborUnits;
    }

    /**
     * 设置schedulePerformanceIndexLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSchedulePerformanceIndexLaborUnits(JAXBElement<Double> value) {
        this.schedulePerformanceIndexLaborUnits = value;
    }

    /**
     * 获取scheduleVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getScheduleVariance() {
        return scheduleVariance;
    }

    /**
     * 设置scheduleVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setScheduleVariance(JAXBElement<Double> value) {
        this.scheduleVariance = value;
    }

    /**
     * 获取scheduleVarianceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getScheduleVarianceIndex() {
        return scheduleVarianceIndex;
    }

    /**
     * 设置scheduleVarianceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setScheduleVarianceIndex(JAXBElement<Double> value) {
        this.scheduleVarianceIndex = value;
    }

    /**
     * 获取scheduleVarianceIndexLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getScheduleVarianceIndexLaborUnits() {
        return scheduleVarianceIndexLaborUnits;
    }

    /**
     * 设置scheduleVarianceIndexLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setScheduleVarianceIndexLaborUnits(JAXBElement<Double> value) {
        this.scheduleVarianceIndexLaborUnits = value;
    }

    /**
     * 获取scheduleVarianceLaborUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getScheduleVarianceLaborUnits() {
        return scheduleVarianceLaborUnits;
    }

    /**
     * 设置scheduleVarianceLaborUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setScheduleVarianceLaborUnits(JAXBElement<Double> value) {
        this.scheduleVarianceLaborUnits = value;
    }

    /**
     * 获取secondaryConstraintDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSecondaryConstraintDate() {
        return secondaryConstraintDate;
    }

    /**
     * 设置secondaryConstraintDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSecondaryConstraintDate(JAXBElement<XMLGregorianCalendar> value) {
        this.secondaryConstraintDate = value;
    }

    /**
     * 获取secondaryConstraintType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondaryConstraintType() {
        return secondaryConstraintType;
    }

    /**
     * 设置secondaryConstraintType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondaryConstraintType(String value) {
        this.secondaryConstraintType = value;
    }

    /**
     * 获取startDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * 设置startDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * 获取startDate1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getStartDate1Variance() {
        return startDate1Variance;
    }

    /**
     * 设置startDate1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setStartDate1Variance(JAXBElement<Double> value) {
        this.startDate1Variance = value;
    }

    /**
     * 获取startDateVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getStartDateVariance() {
        return startDateVariance;
    }

    /**
     * 设置startDateVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setStartDateVariance(JAXBElement<Double> value) {
        this.startDateVariance = value;
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
     * 获取statusCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * 设置statusCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

    /**
     * 获取suspendDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSuspendDate() {
        return suspendDate;
    }

    /**
     * 设置suspendDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSuspendDate(JAXBElement<XMLGregorianCalendar> value) {
        this.suspendDate = value;
    }

    /**
     * 获取toCompletePerformanceIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getToCompletePerformanceIndex() {
        return toCompletePerformanceIndex;
    }

    /**
     * 设置toCompletePerformanceIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setToCompletePerformanceIndex(JAXBElement<Double> value) {
        this.toCompletePerformanceIndex = value;
    }

    /**
     * 获取totalCost1Variance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalCost1Variance() {
        return totalCost1Variance;
    }

    /**
     * 设置totalCost1Variance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalCost1Variance(JAXBElement<Double> value) {
        this.totalCost1Variance = value;
    }

    /**
     * 获取totalCostVariance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalCostVariance() {
        return totalCostVariance;
    }

    /**
     * 设置totalCostVariance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalCostVariance(JAXBElement<Double> value) {
        this.totalCostVariance = value;
    }

    /**
     * 获取totalFloat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalFloat() {
        return totalFloat;
    }

    /**
     * 设置totalFloat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalFloat(JAXBElement<Double> value) {
        this.totalFloat = value;
    }

    /**
     * 获取type属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * 获取unitsPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getUnitsPercentComplete() {
        return unitsPercentComplete;
    }

    /**
     * 设置unitsPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setUnitsPercentComplete(JAXBElement<Double> value) {
        this.unitsPercentComplete = value;
    }

    /**
     * 获取unreadCommentCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getUnreadCommentCount() {
        return unreadCommentCount;
    }

    /**
     * 设置unreadCommentCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setUnreadCommentCount(JAXBElement<Integer> value) {
        this.unreadCommentCount = value;
    }

    /**
     * 获取wbsCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWBSCode() {
        return wbsCode;
    }

    /**
     * 设置wbsCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWBSCode(String value) {
        this.wbsCode = value;
    }

    /**
     * 获取wbsName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWBSName() {
        return wbsName;
    }

    /**
     * 设置wbsName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWBSName(String value) {
        this.wbsName = value;
    }

    /**
     * 获取wbsObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getWBSObjectId() {
        return wbsObjectId;
    }

    /**
     * 设置wbsObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setWBSObjectId(JAXBElement<Integer> value) {
        this.wbsObjectId = value;
    }

    /**
     * 获取wbsPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWBSPath() {
        return wbsPath;
    }

    /**
     * 设置wbsPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWBSPath(String value) {
        this.wbsPath = value;
    }

}
