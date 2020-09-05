
package com.cnpe.p6.projectservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Project complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Project">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActivityDefaultActivityType" minOccurs="0">
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
 *         &lt;element name="ActivityDefaultCalendarName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActivityDefaultCalendarObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActivityDefaultCostAccountObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActivityDefaultDurationType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Fixed Units/Time"/>
 *               &lt;enumeration value="Fixed Duration and Units/Time"/>
 *               &lt;enumeration value="Fixed Units"/>
 *               &lt;enumeration value="Fixed Duration and Units"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ActivityDefaultPercentCompleteType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Physical"/>
 *               &lt;enumeration value="Duration"/>
 *               &lt;enumeration value="Units"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ActivityDefaultPricePerUnit" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ActivityDefaultReviewRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ActivityIdBasedOnSelectedActivity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ActivityIdIncrement" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActivityIdPrefix" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ActivityIdSuffix" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActivityPercentCompleteBasedOnActivitySteps" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AddActualToRemaining" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AddedBy" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AllowNegativeActualUnitsFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AllowStatusReview" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AnnualDiscountRate" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *               &lt;minInclusive value="0.0"/>
 *               &lt;maxInclusive value="100.0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AnticipatedFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="AnticipatedStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="AssignmentDefaultDrivingFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AssignmentDefaultRateType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Price / Unit"/>
 *               &lt;enumeration value="Price / Unit 2"/>
 *               &lt;enumeration value="Price / Unit 3"/>
 *               &lt;enumeration value="Price / Unit 4"/>
 *               &lt;enumeration value="Price / Unit 5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CalculateFloatBasedOnFinishDate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CheckOutDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CheckOutStatus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CheckOutUserObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ComputeTotalFloatType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Start Float = Late Start - Early Start"/>
 *               &lt;enumeration value="Finish Float = Late Finish - Early Finish"/>
 *               &lt;enumeration value="Smallest of Start Float and Finish Float"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ContainsSummaryData" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ContractManagementGroupName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ContractManagementProjectName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="24"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CostQuantityRecalculateFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CriticalActivityFloatLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CriticalActivityFloatThreshold" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CriticalActivityPathType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Critical Float"/>
 *               &lt;enumeration value="Longest Path"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CriticalFloatThreshold" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CurrentBaselineProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CurrentBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="CurrentVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="DataDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DateAdded" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DefaultPriceTimeUnits" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Hour"/>
 *               &lt;enumeration value="Day"/>
 *               &lt;enumeration value="Week"/>
 *               &lt;enumeration value="Month"/>
 *               &lt;enumeration value="Year"/>
 *               &lt;enumeration value="Days Hours"/>
 *               &lt;enumeration value="Hours Minutes"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DiscountApplicationPeriod" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Month"/>
 *               &lt;enumeration value="Quarter"/>
 *               &lt;enumeration value="Year"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
 *         &lt;element name="EnablePrimeSycFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EnablePublication" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EnableSummarization" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EtlInterval" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Immediate"/>
 *               &lt;enumeration value="Scheduled"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="FiscalYearStartMonth" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ForecastFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ForecastStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="GUID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="\{[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\}|"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HasFutureBucketData" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="HistoryInterval" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Month"/>
 *               &lt;enumeration value="Week"/>
 *               &lt;enumeration value="Quarter"/>
 *               &lt;enumeration value="Year"/>
 *               &lt;enumeration value="Financial Period"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HistoryLevel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="None"/>
 *               &lt;enumeration value="Project"/>
 *               &lt;enumeration value="WBS"/>
 *               &lt;enumeration value="Activity_Daily"/>
 *               &lt;enumeration value="Activity_Weekly"/>
 *               &lt;enumeration value="Activity_Monthly"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Id" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IgnoreOtherProjectRelationships" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
 *         &lt;element name="IsTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LastApplyActualsDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastFinancialPeriodObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LastLevelDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastPublishedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastScheduleDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastSummarizedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastUpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LastUpdateUser" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LevelAllResources" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LevelDateFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LevelFloatThresholdCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LevelOuterAssign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LevelOuterAssignPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LevelOverAllocationPercent" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="LevelPriorityList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LevelResourceList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LevelWithinFloat" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LevelingPriority" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LimitMultipleFloatPaths" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LinkActualToActualThisPeriod" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LinkPercentCompleteWithActual" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LinkPlannedAndAtCompletionFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LocationName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="LocationObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="MakeOpenEndedActivitiesCritical" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="MaximumMultipleFloatPaths" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MultipleFloatPathsEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="MultipleFloatPathsEndingActivityObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MultipleFloatPathsUseTotalFloat" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="MustFinishByDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Name" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NetPresentValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
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
 *         &lt;element name="OutOfSequenceScheduleType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Retained Logic"/>
 *               &lt;enumeration value="Progress Override"/>
 *               &lt;enumeration value="Actual Dates"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OverallProjectScore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="OwnerResourceObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ParentEPSObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PaybackPeriod" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PlannedStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PrimaryResourcesCanMarkActivitiesAsCompleted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProjectForecastStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ProjectScheduleType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="Duration"/>
 *               &lt;enumeration value="Resource"/>
 *               &lt;enumeration value="Cost"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PropertyType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="ApplyActuals"/>
 *               &lt;enumeration value="Scheduling"/>
 *               &lt;enumeration value="ScheduleCheck"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ProposedBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PublicationPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="PublishLevel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="WBS Level"/>
 *               &lt;enumeration value="Assignment Level"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RelationshipLagCalendar" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Predecessor Activity Calendar"/>
 *               &lt;enumeration value="Successor Activity Calendar"/>
 *               &lt;enumeration value="24 Hour Calendar"/>
 *               &lt;enumeration value="Project Default Calendar"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ResetPlannedToRemainingFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ResourceCanBeAssignedToSameActivityMoreThanOnce" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ResourcesCanAssignThemselvesToActivities" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ResourcesCanEditAssignmentPercentComplete" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ResourcesCanMarkAssignmentAsCompleted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ResourcesCanViewInactiveActivities" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ReturnOnInvestment" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RiskExposure" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="RiskLevel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Very High"/>
 *               &lt;enumeration value="High"/>
 *               &lt;enumeration value="Medium"/>
 *               &lt;enumeration value="Low"/>
 *               &lt;enumeration value="Very Low"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RiskMatrixName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="40"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RiskMatrixObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="RiskScore" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ScheduleWBSHierarchyType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Complete"/>
 *               &lt;enumeration value="Partial"/>
 *               &lt;enumeration value="Levels"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ScheduledFinishDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SourceProjectObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="StartToStartLagCalculationType" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
 *         &lt;element name="StrategicPriority" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="10000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SummarizeResourcesRolesByWBS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SummarizeToWBSLevel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="SummarizedDataDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
 *         &lt;element name="SummaryLevel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=""/>
 *               &lt;enumeration value="WBS Level"/>
 *               &lt;enumeration value="Assignment Level"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
 *         &lt;element name="SyncWbsHierarchyFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberActivityFields" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="512"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TeamMemberAddNewActualUnits" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberAssignmentOption" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TeamMemberCanStatusOtherResources" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberCanUpdateNotebooks" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberDisplayPlannedUnits" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberIncludePrimaryResources" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberResourceAssignmentFields" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="512"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TeamMemberStepsAddDeletable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TeamMemberViewableFields" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="512"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TotalBenefitPlan" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalBenefitPlanTally" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalFunding" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalSpendingPlan" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TotalSpendingPlanTally" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UnallocatedBudget" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UndistributedCurrentVariance" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UnifierCBSTasksOnlyFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnifierDataMappingName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UnifierDeleteActivitiesFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnifierEnabledFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UnifierProjectName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UnifierProjectNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UnifierScheduleSheetName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UseExpectedFinishDates" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UseProjectBaselineForEarnedValue" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WBSCodeSeparator" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="WBSHierarchyLevels" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WBSMilestonePercentComplete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="WBSObjectId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WebSiteRootDirectory" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="120"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="WebSiteURL" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="external" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Project", propOrder = {
    "activityDefaultActivityType",
    "activityDefaultCalendarName",
    "activityDefaultCalendarObjectId",
    "activityDefaultCostAccountObjectId",
    "activityDefaultDurationType",
    "activityDefaultPercentCompleteType",
    "activityDefaultPricePerUnit",
    "activityDefaultReviewRequired",
    "activityIdBasedOnSelectedActivity",
    "activityIdIncrement",
    "activityIdPrefix",
    "activityIdSuffix",
    "activityPercentCompleteBasedOnActivitySteps",
    "addActualToRemaining",
    "addedBy",
    "allowNegativeActualUnitsFlag",
    "allowStatusReview",
    "annualDiscountRate",
    "anticipatedFinishDate",
    "anticipatedStartDate",
    "assignmentDefaultDrivingFlag",
    "assignmentDefaultRateType",
    "calculateFloatBasedOnFinishDate",
    "checkOutDate",
    "checkOutStatus",
    "checkOutUserObjectId",
    "computeTotalFloatType",
    "containsSummaryData",
    "contractManagementGroupName",
    "contractManagementProjectName",
    "costQuantityRecalculateFlag",
    "createDate",
    "createUser",
    "criticalActivityFloatLimit",
    "criticalActivityFloatThreshold",
    "criticalActivityPathType",
    "criticalFloatThreshold",
    "currentBaselineProjectObjectId",
    "currentBudget",
    "currentVariance",
    "dataDate",
    "dateAdded",
    "defaultPriceTimeUnits",
    "description",
    "discountApplicationPeriod",
    "distributedCurrentBudget",
    "earnedValueComputeType",
    "earnedValueETCComputeType",
    "earnedValueETCUserValue",
    "earnedValueUserPercent",
    "enablePrimeSycFlag",
    "enablePublication",
    "enableSummarization",
    "etlInterval",
    "finishDate",
    "fiscalYearStartMonth",
    "forecastFinishDate",
    "forecastStartDate",
    "guid",
    "hasFutureBucketData",
    "historyInterval",
    "historyLevel",
    "id",
    "ignoreOtherProjectRelationships",
    "independentETCLaborUnits",
    "independentETCTotalCost",
    "integratedType",
    "isTemplate",
    "lastApplyActualsDate",
    "lastFinancialPeriodObjectId",
    "lastLevelDate",
    "lastPublishedOn",
    "lastScheduleDate",
    "lastSummarizedDate",
    "lastUpdateDate",
    "lastUpdateUser",
    "latitude",
    "levelAllResources",
    "levelDateFlag",
    "levelFloatThresholdCount",
    "levelOuterAssign",
    "levelOuterAssignPriority",
    "levelOverAllocationPercent",
    "levelPriorityList",
    "levelResourceList",
    "levelWithinFloat",
    "levelingPriority",
    "limitMultipleFloatPaths",
    "linkActualToActualThisPeriod",
    "linkPercentCompleteWithActual",
    "linkPlannedAndAtCompletionFlag",
    "locationName",
    "locationObjectId",
    "longitude",
    "makeOpenEndedActivitiesCritical",
    "maximumMultipleFloatPaths",
    "multipleFloatPathsEnabled",
    "multipleFloatPathsEndingActivityObjectId",
    "multipleFloatPathsUseTotalFloat",
    "mustFinishByDate",
    "name",
    "netPresentValue",
    "obsName",
    "obsObjectId",
    "objectId",
    "originalBudget",
    "outOfSequenceScheduleType",
    "overallProjectScore",
    "ownerResourceObjectId",
    "parentEPSObjectId",
    "paybackPeriod",
    "plannedStartDate",
    "primaryResourcesCanMarkActivitiesAsCompleted",
    "projectForecastStartDate",
    "projectScheduleType",
    "propertyType",
    "proposedBudget",
    "publicationPriority",
    "publishLevel",
    "relationshipLagCalendar",
    "resetPlannedToRemainingFlag",
    "resourceCanBeAssignedToSameActivityMoreThanOnce",
    "resourcesCanAssignThemselvesToActivities",
    "resourcesCanEditAssignmentPercentComplete",
    "resourcesCanMarkAssignmentAsCompleted",
    "resourcesCanViewInactiveActivities",
    "returnOnInvestment",
    "riskExposure",
    "riskLevel",
    "riskMatrixName",
    "riskMatrixObjectId",
    "riskScore",
    "scheduleWBSHierarchyType",
    "scheduledFinishDate",
    "sourceProjectObjectId",
    "startDate",
    "startToStartLagCalculationType",
    "status",
    "statusReviewerName",
    "statusReviewerObjectId",
    "strategicPriority",
    "summarizeResourcesRolesByWBS",
    "summarizeToWBSLevel",
    "summarizedDataDate",
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
    "summaryLevel",
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
    "syncWbsHierarchyFlag",
    "teamMemberActivityFields",
    "teamMemberAddNewActualUnits",
    "teamMemberAssignmentOption",
    "teamMemberCanStatusOtherResources",
    "teamMemberCanUpdateNotebooks",
    "teamMemberDisplayPlannedUnits",
    "teamMemberIncludePrimaryResources",
    "teamMemberResourceAssignmentFields",
    "teamMemberStepsAddDeletable",
    "teamMemberViewableFields",
    "totalBenefitPlan",
    "totalBenefitPlanTally",
    "totalFunding",
    "totalSpendingPlan",
    "totalSpendingPlanTally",
    "unallocatedBudget",
    "undistributedCurrentVariance",
    "unifierCBSTasksOnlyFlag",
    "unifierDataMappingName",
    "unifierDeleteActivitiesFlag",
    "unifierEnabledFlag",
    "unifierProjectName",
    "unifierProjectNumber",
    "unifierScheduleSheetName",
    "useExpectedFinishDates",
    "useProjectBaselineForEarnedValue",
    "wbsCodeSeparator",
    "wbsHierarchyLevels",
    "wbsMilestonePercentComplete",
    "wbsObjectId",
    "webSiteRootDirectory",
    "webSiteURL"
})
public class Project {

    @XmlElement(name = "ActivityDefaultActivityType")
    protected String activityDefaultActivityType;
    @XmlElement(name = "ActivityDefaultCalendarName")
    protected String activityDefaultCalendarName;
    @XmlElementRef(name = "ActivityDefaultCalendarObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> activityDefaultCalendarObjectId;
    @XmlElementRef(name = "ActivityDefaultCostAccountObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> activityDefaultCostAccountObjectId;
    @XmlElement(name = "ActivityDefaultDurationType")
    protected String activityDefaultDurationType;
    @XmlElement(name = "ActivityDefaultPercentCompleteType")
    protected String activityDefaultPercentCompleteType;
    @XmlElementRef(name = "ActivityDefaultPricePerUnit", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> activityDefaultPricePerUnit;
    @XmlElement(name = "ActivityDefaultReviewRequired")
    protected Boolean activityDefaultReviewRequired;
    @XmlElement(name = "ActivityIdBasedOnSelectedActivity")
    protected Boolean activityIdBasedOnSelectedActivity;
    @XmlElementRef(name = "ActivityIdIncrement", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> activityIdIncrement;
    @XmlElement(name = "ActivityIdPrefix")
    protected String activityIdPrefix;
    @XmlElementRef(name = "ActivityIdSuffix", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> activityIdSuffix;
    @XmlElement(name = "ActivityPercentCompleteBasedOnActivitySteps")
    protected Boolean activityPercentCompleteBasedOnActivitySteps;
    @XmlElement(name = "AddActualToRemaining")
    protected Boolean addActualToRemaining;
    @XmlElement(name = "AddedBy")
    protected String addedBy;
    @XmlElement(name = "AllowNegativeActualUnitsFlag")
    protected Boolean allowNegativeActualUnitsFlag;
    @XmlElement(name = "AllowStatusReview")
    protected Boolean allowStatusReview;
    @XmlElementRef(name = "AnnualDiscountRate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> annualDiscountRate;
    @XmlElementRef(name = "AnticipatedFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> anticipatedFinishDate;
    @XmlElementRef(name = "AnticipatedStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> anticipatedStartDate;
    @XmlElement(name = "AssignmentDefaultDrivingFlag")
    protected Boolean assignmentDefaultDrivingFlag;
    @XmlElement(name = "AssignmentDefaultRateType")
    protected String assignmentDefaultRateType;
    @XmlElementRef(name = "CalculateFloatBasedOnFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> calculateFloatBasedOnFinishDate;
    @XmlElementRef(name = "CheckOutDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> checkOutDate;
    @XmlElement(name = "CheckOutStatus")
    protected Boolean checkOutStatus;
    @XmlElementRef(name = "CheckOutUserObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> checkOutUserObjectId;
    @XmlElement(name = "ComputeTotalFloatType")
    protected String computeTotalFloatType;
    @XmlElement(name = "ContainsSummaryData")
    protected Boolean containsSummaryData;
    @XmlElement(name = "ContractManagementGroupName")
    protected String contractManagementGroupName;
    @XmlElement(name = "ContractManagementProjectName")
    protected String contractManagementProjectName;
    @XmlElement(name = "CostQuantityRecalculateFlag")
    protected Boolean costQuantityRecalculateFlag;
    @XmlElementRef(name = "CreateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> createDate;
    @XmlElement(name = "CreateUser")
    protected String createUser;
    @XmlElementRef(name = "CriticalActivityFloatLimit", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> criticalActivityFloatLimit;
    @XmlElementRef(name = "CriticalActivityFloatThreshold", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> criticalActivityFloatThreshold;
    @XmlElement(name = "CriticalActivityPathType")
    protected String criticalActivityPathType;
    @XmlElementRef(name = "CriticalFloatThreshold", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> criticalFloatThreshold;
    @XmlElementRef(name = "CurrentBaselineProjectObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> currentBaselineProjectObjectId;
    @XmlElementRef(name = "CurrentBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> currentBudget;
    @XmlElementRef(name = "CurrentVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> currentVariance;
    @XmlElementRef(name = "DataDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> dataDate;
    @XmlElement(name = "DateAdded")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateAdded;
    @XmlElement(name = "DefaultPriceTimeUnits")
    protected String defaultPriceTimeUnits;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "DiscountApplicationPeriod")
    protected String discountApplicationPeriod;
    @XmlElementRef(name = "DistributedCurrentBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> distributedCurrentBudget;
    @XmlElement(name = "EarnedValueComputeType")
    protected String earnedValueComputeType;
    @XmlElement(name = "EarnedValueETCComputeType")
    protected String earnedValueETCComputeType;
    @XmlElementRef(name = "EarnedValueETCUserValue", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> earnedValueETCUserValue;
    @XmlElementRef(name = "EarnedValueUserPercent", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> earnedValueUserPercent;
    @XmlElement(name = "EnablePrimeSycFlag")
    protected Boolean enablePrimeSycFlag;
    @XmlElement(name = "EnablePublication")
    protected Boolean enablePublication;
    @XmlElement(name = "EnableSummarization")
    protected Boolean enableSummarization;
    @XmlElement(name = "EtlInterval")
    protected String etlInterval;
    @XmlElementRef(name = "FinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> finishDate;
    @XmlElement(name = "FiscalYearStartMonth")
    protected Integer fiscalYearStartMonth;
    @XmlElementRef(name = "ForecastFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> forecastFinishDate;
    @XmlElementRef(name = "ForecastStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> forecastStartDate;
    @XmlElement(name = "GUID")
    protected String guid;
    @XmlElementRef(name = "HasFutureBucketData", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> hasFutureBucketData;
    @XmlElement(name = "HistoryInterval")
    protected String historyInterval;
    @XmlElement(name = "HistoryLevel")
    protected String historyLevel;
    @XmlElement(name = "Id")
    protected String id;
    @XmlElementRef(name = "IgnoreOtherProjectRelationships", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> ignoreOtherProjectRelationships;
    @XmlElementRef(name = "IndependentETCLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> independentETCLaborUnits;
    @XmlElementRef(name = "IndependentETCTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> independentETCTotalCost;
    @XmlElement(name = "IntegratedType")
    protected String integratedType;
    @XmlElement(name = "IsTemplate")
    protected Boolean isTemplate;
    @XmlElementRef(name = "LastApplyActualsDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastApplyActualsDate;
    @XmlElementRef(name = "LastFinancialPeriodObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> lastFinancialPeriodObjectId;
    @XmlElementRef(name = "LastLevelDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastLevelDate;
    @XmlElementRef(name = "LastPublishedOn", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastPublishedOn;
    @XmlElementRef(name = "LastScheduleDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastScheduleDate;
    @XmlElementRef(name = "LastSummarizedDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastSummarizedDate;
    @XmlElementRef(name = "LastUpdateDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> lastUpdateDate;
    @XmlElement(name = "LastUpdateUser")
    protected String lastUpdateUser;
    @XmlElementRef(name = "Latitude", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> latitude;
    @XmlElementRef(name = "LevelAllResources", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> levelAllResources;
    @XmlElementRef(name = "LevelDateFlag", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> levelDateFlag;
    @XmlElementRef(name = "LevelFloatThresholdCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> levelFloatThresholdCount;
    @XmlElementRef(name = "LevelOuterAssign", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> levelOuterAssign;
    @XmlElementRef(name = "LevelOuterAssignPriority", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> levelOuterAssignPriority;
    @XmlElementRef(name = "LevelOverAllocationPercent", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> levelOverAllocationPercent;
    @XmlElement(name = "LevelPriorityList")
    protected String levelPriorityList;
    @XmlElement(name = "LevelResourceList")
    protected String levelResourceList;
    @XmlElementRef(name = "LevelWithinFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> levelWithinFloat;
    @XmlElement(name = "LevelingPriority")
    protected Integer levelingPriority;
    @XmlElementRef(name = "LimitMultipleFloatPaths", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> limitMultipleFloatPaths;
    @XmlElement(name = "LinkActualToActualThisPeriod")
    protected Boolean linkActualToActualThisPeriod;
    @XmlElement(name = "LinkPercentCompleteWithActual")
    protected Boolean linkPercentCompleteWithActual;
    @XmlElement(name = "LinkPlannedAndAtCompletionFlag")
    protected Boolean linkPlannedAndAtCompletionFlag;
    @XmlElement(name = "LocationName")
    protected String locationName;
    @XmlElementRef(name = "LocationObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> locationObjectId;
    @XmlElementRef(name = "Longitude", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> longitude;
    @XmlElementRef(name = "MakeOpenEndedActivitiesCritical", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> makeOpenEndedActivitiesCritical;
    @XmlElementRef(name = "MaximumMultipleFloatPaths", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> maximumMultipleFloatPaths;
    @XmlElementRef(name = "MultipleFloatPathsEnabled", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> multipleFloatPathsEnabled;
    @XmlElementRef(name = "MultipleFloatPathsEndingActivityObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> multipleFloatPathsEndingActivityObjectId;
    @XmlElementRef(name = "MultipleFloatPathsUseTotalFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> multipleFloatPathsUseTotalFloat;
    @XmlElementRef(name = "MustFinishByDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> mustFinishByDate;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElementRef(name = "NetPresentValue", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> netPresentValue;
    @XmlElement(name = "OBSName")
    protected String obsName;
    @XmlElement(name = "OBSObjectId")
    protected Integer obsObjectId;
    @XmlElement(name = "ObjectId")
    protected Integer objectId;
    @XmlElementRef(name = "OriginalBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> originalBudget;
    @XmlElement(name = "OutOfSequenceScheduleType")
    protected String outOfSequenceScheduleType;
    @XmlElementRef(name = "OverallProjectScore", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> overallProjectScore;
    @XmlElementRef(name = "OwnerResourceObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> ownerResourceObjectId;
    @XmlElement(name = "ParentEPSObjectId")
    protected Integer parentEPSObjectId;
    @XmlElementRef(name = "PaybackPeriod", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> paybackPeriod;
    @XmlElement(name = "PlannedStartDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedStartDate;
    @XmlElement(name = "PrimaryResourcesCanMarkActivitiesAsCompleted")
    protected Boolean primaryResourcesCanMarkActivitiesAsCompleted;
    @XmlElementRef(name = "ProjectForecastStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> projectForecastStartDate;
    @XmlElement(name = "ProjectScheduleType")
    protected String projectScheduleType;
    @XmlElement(name = "PropertyType")
    protected String propertyType;
    @XmlElementRef(name = "ProposedBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> proposedBudget;
    @XmlElement(name = "PublicationPriority")
    protected Integer publicationPriority;
    @XmlElement(name = "PublishLevel")
    protected String publishLevel;
    @XmlElement(name = "RelationshipLagCalendar")
    protected String relationshipLagCalendar;
    @XmlElement(name = "ResetPlannedToRemainingFlag")
    protected Boolean resetPlannedToRemainingFlag;
    @XmlElement(name = "ResourceCanBeAssignedToSameActivityMoreThanOnce")
    protected Boolean resourceCanBeAssignedToSameActivityMoreThanOnce;
    @XmlElement(name = "ResourcesCanAssignThemselvesToActivities")
    protected Boolean resourcesCanAssignThemselvesToActivities;
    @XmlElement(name = "ResourcesCanEditAssignmentPercentComplete")
    protected Boolean resourcesCanEditAssignmentPercentComplete;
    @XmlElement(name = "ResourcesCanMarkAssignmentAsCompleted")
    protected Boolean resourcesCanMarkAssignmentAsCompleted;
    @XmlElement(name = "ResourcesCanViewInactiveActivities")
    protected Boolean resourcesCanViewInactiveActivities;
    @XmlElementRef(name = "ReturnOnInvestment", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> returnOnInvestment;
    @XmlElementRef(name = "RiskExposure", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> riskExposure;
    @XmlElement(name = "RiskLevel")
    protected String riskLevel;
    @XmlElement(name = "RiskMatrixName")
    protected String riskMatrixName;
    @XmlElementRef(name = "RiskMatrixObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> riskMatrixObjectId;
    @XmlElementRef(name = "RiskScore", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> riskScore;
    @XmlElement(name = "ScheduleWBSHierarchyType")
    protected String scheduleWBSHierarchyType;
    @XmlElementRef(name = "ScheduledFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> scheduledFinishDate;
    @XmlElementRef(name = "SourceProjectObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> sourceProjectObjectId;
    @XmlElementRef(name = "StartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> startDate;
    @XmlElementRef(name = "StartToStartLagCalculationType", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> startToStartLagCalculationType;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "StatusReviewerName")
    protected String statusReviewerName;
    @XmlElementRef(name = "StatusReviewerObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> statusReviewerObjectId;
    @XmlElementRef(name = "StrategicPriority", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> strategicPriority;
    @XmlElement(name = "SummarizeResourcesRolesByWBS")
    protected Boolean summarizeResourcesRolesByWBS;
    @XmlElementRef(name = "SummarizeToWBSLevel", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summarizeToWBSLevel;
    @XmlElementRef(name = "SummarizedDataDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summarizedDataDate;
    @XmlElementRef(name = "SummaryAccountingVarianceByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAccountingVarianceByCost;
    @XmlElementRef(name = "SummaryAccountingVarianceByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAccountingVarianceByLaborUnits;
    @XmlElementRef(name = "SummaryActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryActivityCount;
    @XmlElementRef(name = "SummaryActualDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualDuration;
    @XmlElementRef(name = "SummaryActualExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualExpenseCost;
    @XmlElementRef(name = "SummaryActualFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryActualFinishDate;
    @XmlElementRef(name = "SummaryActualLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualLaborCost;
    @XmlElementRef(name = "SummaryActualLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualLaborUnits;
    @XmlElementRef(name = "SummaryActualMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualMaterialCost;
    @XmlElementRef(name = "SummaryActualNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualNonLaborCost;
    @XmlElementRef(name = "SummaryActualNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualNonLaborUnits;
    @XmlElementRef(name = "SummaryActualStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryActualStartDate;
    @XmlElementRef(name = "SummaryActualThisPeriodCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodCost;
    @XmlElementRef(name = "SummaryActualThisPeriodLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodLaborCost;
    @XmlElementRef(name = "SummaryActualThisPeriodLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodLaborUnits;
    @XmlElementRef(name = "SummaryActualThisPeriodMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodMaterialCost;
    @XmlElementRef(name = "SummaryActualThisPeriodNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodNonLaborCost;
    @XmlElementRef(name = "SummaryActualThisPeriodNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualThisPeriodNonLaborUnits;
    @XmlElementRef(name = "SummaryActualTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualTotalCost;
    @XmlElementRef(name = "SummaryActualValueByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualValueByCost;
    @XmlElementRef(name = "SummaryActualValueByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryActualValueByLaborUnits;
    @XmlElementRef(name = "SummaryAtCompletionDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionDuration;
    @XmlElementRef(name = "SummaryAtCompletionExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionExpenseCost;
    @XmlElementRef(name = "SummaryAtCompletionLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionLaborCost;
    @XmlElementRef(name = "SummaryAtCompletionLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionLaborUnits;
    @XmlElementRef(name = "SummaryAtCompletionMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionMaterialCost;
    @XmlElementRef(name = "SummaryAtCompletionNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionNonLaborCost;
    @XmlElementRef(name = "SummaryAtCompletionNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionNonLaborUnits;
    @XmlElementRef(name = "SummaryAtCompletionTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionTotalCost;
    @XmlElementRef(name = "SummaryAtCompletionTotalCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryAtCompletionTotalCostVariance;
    @XmlElementRef(name = "SummaryBaselineCompletedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryBaselineCompletedActivityCount;
    @XmlElementRef(name = "SummaryBaselineDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineDuration;
    @XmlElementRef(name = "SummaryBaselineExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineExpenseCost;
    @XmlElementRef(name = "SummaryBaselineFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryBaselineFinishDate;
    @XmlElementRef(name = "SummaryBaselineInProgressActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryBaselineInProgressActivityCount;
    @XmlElementRef(name = "SummaryBaselineLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineLaborCost;
    @XmlElementRef(name = "SummaryBaselineLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineLaborUnits;
    @XmlElementRef(name = "SummaryBaselineMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineMaterialCost;
    @XmlElementRef(name = "SummaryBaselineNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineNonLaborCost;
    @XmlElementRef(name = "SummaryBaselineNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineNonLaborUnits;
    @XmlElementRef(name = "SummaryBaselineNotStartedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryBaselineNotStartedActivityCount;
    @XmlElementRef(name = "SummaryBaselineStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryBaselineStartDate;
    @XmlElementRef(name = "SummaryBaselineTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBaselineTotalCost;
    @XmlElementRef(name = "SummaryBudgetAtCompletionByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBudgetAtCompletionByCost;
    @XmlElementRef(name = "SummaryBudgetAtCompletionByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryBudgetAtCompletionByLaborUnits;
    @XmlElementRef(name = "SummaryCompletedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryCompletedActivityCount;
    @XmlElementRef(name = "SummaryCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPercentComplete;
    @XmlElementRef(name = "SummaryCostPercentOfPlanned", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPercentOfPlanned;
    @XmlElementRef(name = "SummaryCostPerformanceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPerformanceIndexByCost;
    @XmlElementRef(name = "SummaryCostPerformanceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostPerformanceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryCostVarianceByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceByCost;
    @XmlElementRef(name = "SummaryCostVarianceByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceByLaborUnits;
    @XmlElementRef(name = "SummaryCostVarianceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceIndex;
    @XmlElementRef(name = "SummaryCostVarianceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceIndexByCost;
    @XmlElementRef(name = "SummaryCostVarianceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryCostVarianceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryDurationPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryDurationPercentComplete;
    @XmlElementRef(name = "SummaryDurationPercentOfPlanned", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryDurationPercentOfPlanned;
    @XmlElementRef(name = "SummaryDurationVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryDurationVariance;
    @XmlElementRef(name = "SummaryEarnedValueByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEarnedValueByCost;
    @XmlElementRef(name = "SummaryEarnedValueByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEarnedValueByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateAtCompletionByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionByCost;
    @XmlElementRef(name = "SummaryEstimateAtCompletionByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateAtCompletionHighPercentByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionHighPercentByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateAtCompletionLowPercentByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateAtCompletionLowPercentByLaborUnits;
    @XmlElementRef(name = "SummaryEstimateToCompleteByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateToCompleteByCost;
    @XmlElementRef(name = "SummaryEstimateToCompleteByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryEstimateToCompleteByLaborUnits;
    @XmlElementRef(name = "SummaryExpenseCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryExpenseCostPercentComplete;
    @XmlElementRef(name = "SummaryExpenseCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryExpenseCostVariance;
    @XmlElementRef(name = "SummaryFinishDateVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryFinishDateVariance;
    @XmlElementRef(name = "SummaryInProgressActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryInProgressActivityCount;
    @XmlElementRef(name = "SummaryLaborCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborCostPercentComplete;
    @XmlElementRef(name = "SummaryLaborCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborCostVariance;
    @XmlElementRef(name = "SummaryLaborUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborUnitsPercentComplete;
    @XmlElementRef(name = "SummaryLaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryLaborUnitsVariance;
    @XmlElement(name = "SummaryLevel")
    protected String summaryLevel;
    @XmlElementRef(name = "SummaryMaterialCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryMaterialCostPercentComplete;
    @XmlElementRef(name = "SummaryMaterialCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryMaterialCostVariance;
    @XmlElementRef(name = "SummaryNonLaborCostPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborCostPercentComplete;
    @XmlElementRef(name = "SummaryNonLaborCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborCostVariance;
    @XmlElementRef(name = "SummaryNonLaborUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborUnitsPercentComplete;
    @XmlElementRef(name = "SummaryNonLaborUnitsVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryNonLaborUnitsVariance;
    @XmlElementRef(name = "SummaryNotStartedActivityCount", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> summaryNotStartedActivityCount;
    @XmlElementRef(name = "SummaryPerformancePercentCompleteByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPerformancePercentCompleteByCost;
    @XmlElementRef(name = "SummaryPerformancePercentCompleteByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPerformancePercentCompleteByLaborUnits;
    @XmlElementRef(name = "SummaryPlannedCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedCost;
    @XmlElementRef(name = "SummaryPlannedDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedDuration;
    @XmlElementRef(name = "SummaryPlannedExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedExpenseCost;
    @XmlElementRef(name = "SummaryPlannedFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryPlannedFinishDate;
    @XmlElementRef(name = "SummaryPlannedLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedLaborCost;
    @XmlElementRef(name = "SummaryPlannedLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedLaborUnits;
    @XmlElementRef(name = "SummaryPlannedMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedMaterialCost;
    @XmlElementRef(name = "SummaryPlannedNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedNonLaborCost;
    @XmlElementRef(name = "SummaryPlannedNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedNonLaborUnits;
    @XmlElementRef(name = "SummaryPlannedStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryPlannedStartDate;
    @XmlElementRef(name = "SummaryPlannedValueByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedValueByCost;
    @XmlElementRef(name = "SummaryPlannedValueByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryPlannedValueByLaborUnits;
    @XmlElementRef(name = "SummaryProgressFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryProgressFinishDate;
    @XmlElementRef(name = "SummaryRemainingDuration", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingDuration;
    @XmlElementRef(name = "SummaryRemainingExpenseCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingExpenseCost;
    @XmlElementRef(name = "SummaryRemainingFinishDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryRemainingFinishDate;
    @XmlElementRef(name = "SummaryRemainingLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingLaborCost;
    @XmlElementRef(name = "SummaryRemainingLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingLaborUnits;
    @XmlElementRef(name = "SummaryRemainingMaterialCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingMaterialCost;
    @XmlElementRef(name = "SummaryRemainingNonLaborCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingNonLaborCost;
    @XmlElementRef(name = "SummaryRemainingNonLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingNonLaborUnits;
    @XmlElementRef(name = "SummaryRemainingStartDate", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> summaryRemainingStartDate;
    @XmlElementRef(name = "SummaryRemainingTotalCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryRemainingTotalCost;
    @XmlElementRef(name = "SummarySchedulePercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePercentComplete;
    @XmlElementRef(name = "SummarySchedulePercentCompleteByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePercentCompleteByLaborUnits;
    @XmlElementRef(name = "SummarySchedulePerformanceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePerformanceIndexByCost;
    @XmlElementRef(name = "SummarySchedulePerformanceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summarySchedulePerformanceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryScheduleVarianceByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceByCost;
    @XmlElementRef(name = "SummaryScheduleVarianceByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceByLaborUnits;
    @XmlElementRef(name = "SummaryScheduleVarianceIndex", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceIndex;
    @XmlElementRef(name = "SummaryScheduleVarianceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceIndexByCost;
    @XmlElementRef(name = "SummaryScheduleVarianceIndexByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryScheduleVarianceIndexByLaborUnits;
    @XmlElementRef(name = "SummaryStartDateVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryStartDateVariance;
    @XmlElementRef(name = "SummaryToCompletePerformanceIndexByCost", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryToCompletePerformanceIndexByCost;
    @XmlElementRef(name = "SummaryTotalCostVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryTotalCostVariance;
    @XmlElementRef(name = "SummaryTotalFloat", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryTotalFloat;
    @XmlElementRef(name = "SummaryUnitsPercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryUnitsPercentComplete;
    @XmlElementRef(name = "SummaryVarianceAtCompletionByLaborUnits", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> summaryVarianceAtCompletionByLaborUnits;
    @XmlElementRef(name = "SyncWbsHierarchyFlag", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> syncWbsHierarchyFlag;
    @XmlElement(name = "TeamMemberActivityFields")
    protected String teamMemberActivityFields;
    @XmlElement(name = "TeamMemberAddNewActualUnits")
    protected Boolean teamMemberAddNewActualUnits;
    @XmlElement(name = "TeamMemberAssignmentOption")
    protected String teamMemberAssignmentOption;
    @XmlElement(name = "TeamMemberCanStatusOtherResources")
    protected Boolean teamMemberCanStatusOtherResources;
    @XmlElement(name = "TeamMemberCanUpdateNotebooks")
    protected Boolean teamMemberCanUpdateNotebooks;
    @XmlElement(name = "TeamMemberDisplayPlannedUnits")
    protected Boolean teamMemberDisplayPlannedUnits;
    @XmlElement(name = "TeamMemberIncludePrimaryResources")
    protected Boolean teamMemberIncludePrimaryResources;
    @XmlElement(name = "TeamMemberResourceAssignmentFields")
    protected String teamMemberResourceAssignmentFields;
    @XmlElement(name = "TeamMemberStepsAddDeletable")
    protected Boolean teamMemberStepsAddDeletable;
    @XmlElement(name = "TeamMemberViewableFields")
    protected String teamMemberViewableFields;
    @XmlElementRef(name = "TotalBenefitPlan", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalBenefitPlan;
    @XmlElementRef(name = "TotalBenefitPlanTally", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalBenefitPlanTally;
    @XmlElementRef(name = "TotalFunding", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalFunding;
    @XmlElementRef(name = "TotalSpendingPlan", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalSpendingPlan;
    @XmlElementRef(name = "TotalSpendingPlanTally", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> totalSpendingPlanTally;
    @XmlElementRef(name = "UnallocatedBudget", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> unallocatedBudget;
    @XmlElementRef(name = "UndistributedCurrentVariance", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> undistributedCurrentVariance;
    @XmlElementRef(name = "UnifierCBSTasksOnlyFlag", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> unifierCBSTasksOnlyFlag;
    @XmlElement(name = "UnifierDataMappingName")
    protected String unifierDataMappingName;
    @XmlElementRef(name = "UnifierDeleteActivitiesFlag", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> unifierDeleteActivitiesFlag;
    @XmlElementRef(name = "UnifierEnabledFlag", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> unifierEnabledFlag;
    @XmlElement(name = "UnifierProjectName")
    protected String unifierProjectName;
    @XmlElement(name = "UnifierProjectNumber")
    protected String unifierProjectNumber;
    @XmlElement(name = "UnifierScheduleSheetName")
    protected String unifierScheduleSheetName;
    @XmlElementRef(name = "UseExpectedFinishDates", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Boolean> useExpectedFinishDates;
    @XmlElement(name = "UseProjectBaselineForEarnedValue")
    protected Boolean useProjectBaselineForEarnedValue;
    @XmlElement(name = "WBSCodeSeparator")
    protected String wbsCodeSeparator;
    @XmlElementRef(name = "WBSHierarchyLevels", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> wbsHierarchyLevels;
    @XmlElementRef(name = "WBSMilestonePercentComplete", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Double> wbsMilestonePercentComplete;
    @XmlElementRef(name = "WBSObjectId", namespace = "http://xmlns.oracle.com/Primavera/P6/WS/Project/V2", type = JAXBElement.class)
    protected JAXBElement<Integer> wbsObjectId;
    @XmlElement(name = "WebSiteRootDirectory")
    protected String webSiteRootDirectory;
    @XmlElement(name = "WebSiteURL")
    protected String webSiteURL;
    @XmlAttribute(name = "external")
    protected Boolean external;

    /**
     * 获取activityDefaultActivityType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityDefaultActivityType() {
        return activityDefaultActivityType;
    }

    /**
     * 设置activityDefaultActivityType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityDefaultActivityType(String value) {
        this.activityDefaultActivityType = value;
    }

    /**
     * 获取activityDefaultCalendarName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityDefaultCalendarName() {
        return activityDefaultCalendarName;
    }

    /**
     * 设置activityDefaultCalendarName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityDefaultCalendarName(String value) {
        this.activityDefaultCalendarName = value;
    }

    /**
     * 获取activityDefaultCalendarObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getActivityDefaultCalendarObjectId() {
        return activityDefaultCalendarObjectId;
    }

    /**
     * 设置activityDefaultCalendarObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setActivityDefaultCalendarObjectId(JAXBElement<Integer> value) {
        this.activityDefaultCalendarObjectId = value;
    }

    /**
     * 获取activityDefaultCostAccountObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getActivityDefaultCostAccountObjectId() {
        return activityDefaultCostAccountObjectId;
    }

    /**
     * 设置activityDefaultCostAccountObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setActivityDefaultCostAccountObjectId(JAXBElement<Integer> value) {
        this.activityDefaultCostAccountObjectId = value;
    }

    /**
     * 获取activityDefaultDurationType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityDefaultDurationType() {
        return activityDefaultDurationType;
    }

    /**
     * 设置activityDefaultDurationType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityDefaultDurationType(String value) {
        this.activityDefaultDurationType = value;
    }

    /**
     * 获取activityDefaultPercentCompleteType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityDefaultPercentCompleteType() {
        return activityDefaultPercentCompleteType;
    }

    /**
     * 设置activityDefaultPercentCompleteType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityDefaultPercentCompleteType(String value) {
        this.activityDefaultPercentCompleteType = value;
    }

    /**
     * 获取activityDefaultPricePerUnit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getActivityDefaultPricePerUnit() {
        return activityDefaultPricePerUnit;
    }

    /**
     * 设置activityDefaultPricePerUnit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setActivityDefaultPricePerUnit(JAXBElement<Double> value) {
        this.activityDefaultPricePerUnit = value;
    }

    /**
     * 获取activityDefaultReviewRequired属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActivityDefaultReviewRequired() {
        return activityDefaultReviewRequired;
    }

    /**
     * 设置activityDefaultReviewRequired属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActivityDefaultReviewRequired(Boolean value) {
        this.activityDefaultReviewRequired = value;
    }

    /**
     * 获取activityIdBasedOnSelectedActivity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActivityIdBasedOnSelectedActivity() {
        return activityIdBasedOnSelectedActivity;
    }

    /**
     * 设置activityIdBasedOnSelectedActivity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActivityIdBasedOnSelectedActivity(Boolean value) {
        this.activityIdBasedOnSelectedActivity = value;
    }

    /**
     * 获取activityIdIncrement属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getActivityIdIncrement() {
        return activityIdIncrement;
    }

    /**
     * 设置activityIdIncrement属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setActivityIdIncrement(JAXBElement<Integer> value) {
        this.activityIdIncrement = value;
    }

    /**
     * 获取activityIdPrefix属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityIdPrefix() {
        return activityIdPrefix;
    }

    /**
     * 设置activityIdPrefix属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityIdPrefix(String value) {
        this.activityIdPrefix = value;
    }

    /**
     * 获取activityIdSuffix属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getActivityIdSuffix() {
        return activityIdSuffix;
    }

    /**
     * 设置activityIdSuffix属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setActivityIdSuffix(JAXBElement<Integer> value) {
        this.activityIdSuffix = value;
    }

    /**
     * 获取activityPercentCompleteBasedOnActivitySteps属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActivityPercentCompleteBasedOnActivitySteps() {
        return activityPercentCompleteBasedOnActivitySteps;
    }

    /**
     * 设置activityPercentCompleteBasedOnActivitySteps属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActivityPercentCompleteBasedOnActivitySteps(Boolean value) {
        this.activityPercentCompleteBasedOnActivitySteps = value;
    }

    /**
     * 获取addActualToRemaining属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddActualToRemaining() {
        return addActualToRemaining;
    }

    /**
     * 设置addActualToRemaining属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddActualToRemaining(Boolean value) {
        this.addActualToRemaining = value;
    }

    /**
     * 获取addedBy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddedBy() {
        return addedBy;
    }

    /**
     * 设置addedBy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddedBy(String value) {
        this.addedBy = value;
    }

    /**
     * 获取allowNegativeActualUnitsFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowNegativeActualUnitsFlag() {
        return allowNegativeActualUnitsFlag;
    }

    /**
     * 设置allowNegativeActualUnitsFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowNegativeActualUnitsFlag(Boolean value) {
        this.allowNegativeActualUnitsFlag = value;
    }

    /**
     * 获取allowStatusReview属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowStatusReview() {
        return allowStatusReview;
    }

    /**
     * 设置allowStatusReview属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowStatusReview(Boolean value) {
        this.allowStatusReview = value;
    }

    /**
     * 获取annualDiscountRate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getAnnualDiscountRate() {
        return annualDiscountRate;
    }

    /**
     * 设置annualDiscountRate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setAnnualDiscountRate(JAXBElement<Double> value) {
        this.annualDiscountRate = value;
    }

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
     * 获取assignmentDefaultDrivingFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAssignmentDefaultDrivingFlag() {
        return assignmentDefaultDrivingFlag;
    }

    /**
     * 设置assignmentDefaultDrivingFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAssignmentDefaultDrivingFlag(Boolean value) {
        this.assignmentDefaultDrivingFlag = value;
    }

    /**
     * 获取assignmentDefaultRateType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignmentDefaultRateType() {
        return assignmentDefaultRateType;
    }

    /**
     * 设置assignmentDefaultRateType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignmentDefaultRateType(String value) {
        this.assignmentDefaultRateType = value;
    }

    /**
     * 获取calculateFloatBasedOnFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getCalculateFloatBasedOnFinishDate() {
        return calculateFloatBasedOnFinishDate;
    }

    /**
     * 设置calculateFloatBasedOnFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setCalculateFloatBasedOnFinishDate(JAXBElement<Boolean> value) {
        this.calculateFloatBasedOnFinishDate = value;
    }

    /**
     * 获取checkOutDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * 设置checkOutDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setCheckOutDate(JAXBElement<XMLGregorianCalendar> value) {
        this.checkOutDate = value;
    }

    /**
     * 获取checkOutStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCheckOutStatus() {
        return checkOutStatus;
    }

    /**
     * 设置checkOutStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCheckOutStatus(Boolean value) {
        this.checkOutStatus = value;
    }

    /**
     * 获取checkOutUserObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCheckOutUserObjectId() {
        return checkOutUserObjectId;
    }

    /**
     * 设置checkOutUserObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCheckOutUserObjectId(JAXBElement<Integer> value) {
        this.checkOutUserObjectId = value;
    }

    /**
     * 获取computeTotalFloatType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComputeTotalFloatType() {
        return computeTotalFloatType;
    }

    /**
     * 设置computeTotalFloatType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComputeTotalFloatType(String value) {
        this.computeTotalFloatType = value;
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
     * 获取contractManagementGroupName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractManagementGroupName() {
        return contractManagementGroupName;
    }

    /**
     * 设置contractManagementGroupName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractManagementGroupName(String value) {
        this.contractManagementGroupName = value;
    }

    /**
     * 获取contractManagementProjectName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractManagementProjectName() {
        return contractManagementProjectName;
    }

    /**
     * 设置contractManagementProjectName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractManagementProjectName(String value) {
        this.contractManagementProjectName = value;
    }

    /**
     * 获取costQuantityRecalculateFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCostQuantityRecalculateFlag() {
        return costQuantityRecalculateFlag;
    }

    /**
     * 设置costQuantityRecalculateFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCostQuantityRecalculateFlag(Boolean value) {
        this.costQuantityRecalculateFlag = value;
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
     * 获取criticalActivityFloatLimit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCriticalActivityFloatLimit() {
        return criticalActivityFloatLimit;
    }

    /**
     * 设置criticalActivityFloatLimit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCriticalActivityFloatLimit(JAXBElement<Double> value) {
        this.criticalActivityFloatLimit = value;
    }

    /**
     * 获取criticalActivityFloatThreshold属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCriticalActivityFloatThreshold() {
        return criticalActivityFloatThreshold;
    }

    /**
     * 设置criticalActivityFloatThreshold属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCriticalActivityFloatThreshold(JAXBElement<Double> value) {
        this.criticalActivityFloatThreshold = value;
    }

    /**
     * 获取criticalActivityPathType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCriticalActivityPathType() {
        return criticalActivityPathType;
    }

    /**
     * 设置criticalActivityPathType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCriticalActivityPathType(String value) {
        this.criticalActivityPathType = value;
    }

    /**
     * 获取criticalFloatThreshold属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getCriticalFloatThreshold() {
        return criticalFloatThreshold;
    }

    /**
     * 设置criticalFloatThreshold属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setCriticalFloatThreshold(JAXBElement<Double> value) {
        this.criticalFloatThreshold = value;
    }

    /**
     * 获取currentBaselineProjectObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCurrentBaselineProjectObjectId() {
        return currentBaselineProjectObjectId;
    }

    /**
     * 设置currentBaselineProjectObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCurrentBaselineProjectObjectId(JAXBElement<Integer> value) {
        this.currentBaselineProjectObjectId = value;
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
     * 获取dateAdded属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateAdded() {
        return dateAdded;
    }

    /**
     * 设置dateAdded属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateAdded(XMLGregorianCalendar value) {
        this.dateAdded = value;
    }

    /**
     * 获取defaultPriceTimeUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultPriceTimeUnits() {
        return defaultPriceTimeUnits;
    }

    /**
     * 设置defaultPriceTimeUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultPriceTimeUnits(String value) {
        this.defaultPriceTimeUnits = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取discountApplicationPeriod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountApplicationPeriod() {
        return discountApplicationPeriod;
    }

    /**
     * 设置discountApplicationPeriod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountApplicationPeriod(String value) {
        this.discountApplicationPeriod = value;
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
     * 获取enablePrimeSycFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnablePrimeSycFlag() {
        return enablePrimeSycFlag;
    }

    /**
     * 设置enablePrimeSycFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnablePrimeSycFlag(Boolean value) {
        this.enablePrimeSycFlag = value;
    }

    /**
     * 获取enablePublication属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnablePublication() {
        return enablePublication;
    }

    /**
     * 设置enablePublication属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnablePublication(Boolean value) {
        this.enablePublication = value;
    }

    /**
     * 获取enableSummarization属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableSummarization() {
        return enableSummarization;
    }

    /**
     * 设置enableSummarization属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableSummarization(Boolean value) {
        this.enableSummarization = value;
    }

    /**
     * 获取etlInterval属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEtlInterval() {
        return etlInterval;
    }

    /**
     * 设置etlInterval属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEtlInterval(String value) {
        this.etlInterval = value;
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
     * 获取fiscalYearStartMonth属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFiscalYearStartMonth() {
        return fiscalYearStartMonth;
    }

    /**
     * 设置fiscalYearStartMonth属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFiscalYearStartMonth(Integer value) {
        this.fiscalYearStartMonth = value;
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
     * 获取historyInterval属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryInterval() {
        return historyInterval;
    }

    /**
     * 设置historyInterval属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryInterval(String value) {
        this.historyInterval = value;
    }

    /**
     * 获取historyLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryLevel() {
        return historyLevel;
    }

    /**
     * 设置historyLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryLevel(String value) {
        this.historyLevel = value;
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
     * 获取ignoreOtherProjectRelationships属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getIgnoreOtherProjectRelationships() {
        return ignoreOtherProjectRelationships;
    }

    /**
     * 设置ignoreOtherProjectRelationships属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setIgnoreOtherProjectRelationships(JAXBElement<Boolean> value) {
        this.ignoreOtherProjectRelationships = value;
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
     * 获取lastApplyActualsDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastApplyActualsDate() {
        return lastApplyActualsDate;
    }

    /**
     * 设置lastApplyActualsDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastApplyActualsDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lastApplyActualsDate = value;
    }

    /**
     * 获取lastFinancialPeriodObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getLastFinancialPeriodObjectId() {
        return lastFinancialPeriodObjectId;
    }

    /**
     * 设置lastFinancialPeriodObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setLastFinancialPeriodObjectId(JAXBElement<Integer> value) {
        this.lastFinancialPeriodObjectId = value;
    }

    /**
     * 获取lastLevelDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastLevelDate() {
        return lastLevelDate;
    }

    /**
     * 设置lastLevelDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastLevelDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lastLevelDate = value;
    }

    /**
     * 获取lastPublishedOn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastPublishedOn() {
        return lastPublishedOn;
    }

    /**
     * 设置lastPublishedOn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastPublishedOn(JAXBElement<XMLGregorianCalendar> value) {
        this.lastPublishedOn = value;
    }

    /**
     * 获取lastScheduleDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastScheduleDate() {
        return lastScheduleDate;
    }

    /**
     * 设置lastScheduleDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastScheduleDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lastScheduleDate = value;
    }

    /**
     * 获取lastSummarizedDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLastSummarizedDate() {
        return lastSummarizedDate;
    }

    /**
     * 设置lastSummarizedDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLastSummarizedDate(JAXBElement<XMLGregorianCalendar> value) {
        this.lastSummarizedDate = value;
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
     * 获取latitude属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLatitude() {
        return latitude;
    }

    /**
     * 设置latitude属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLatitude(JAXBElement<Double> value) {
        this.latitude = value;
    }

    /**
     * 获取levelAllResources属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getLevelAllResources() {
        return levelAllResources;
    }

    /**
     * 设置levelAllResources属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setLevelAllResources(JAXBElement<Boolean> value) {
        this.levelAllResources = value;
    }

    /**
     * 获取levelDateFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getLevelDateFlag() {
        return levelDateFlag;
    }

    /**
     * 设置levelDateFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setLevelDateFlag(JAXBElement<Boolean> value) {
        this.levelDateFlag = value;
    }

    /**
     * 获取levelFloatThresholdCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getLevelFloatThresholdCount() {
        return levelFloatThresholdCount;
    }

    /**
     * 设置levelFloatThresholdCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setLevelFloatThresholdCount(JAXBElement<Integer> value) {
        this.levelFloatThresholdCount = value;
    }

    /**
     * 获取levelOuterAssign属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getLevelOuterAssign() {
        return levelOuterAssign;
    }

    /**
     * 设置levelOuterAssign属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setLevelOuterAssign(JAXBElement<Boolean> value) {
        this.levelOuterAssign = value;
    }

    /**
     * 获取levelOuterAssignPriority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getLevelOuterAssignPriority() {
        return levelOuterAssignPriority;
    }

    /**
     * 设置levelOuterAssignPriority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setLevelOuterAssignPriority(JAXBElement<Integer> value) {
        this.levelOuterAssignPriority = value;
    }

    /**
     * 获取levelOverAllocationPercent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLevelOverAllocationPercent() {
        return levelOverAllocationPercent;
    }

    /**
     * 设置levelOverAllocationPercent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLevelOverAllocationPercent(JAXBElement<Double> value) {
        this.levelOverAllocationPercent = value;
    }

    /**
     * 获取levelPriorityList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelPriorityList() {
        return levelPriorityList;
    }

    /**
     * 设置levelPriorityList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelPriorityList(String value) {
        this.levelPriorityList = value;
    }

    /**
     * 获取levelResourceList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelResourceList() {
        return levelResourceList;
    }

    /**
     * 设置levelResourceList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelResourceList(String value) {
        this.levelResourceList = value;
    }

    /**
     * 获取levelWithinFloat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getLevelWithinFloat() {
        return levelWithinFloat;
    }

    /**
     * 设置levelWithinFloat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setLevelWithinFloat(JAXBElement<Boolean> value) {
        this.levelWithinFloat = value;
    }

    /**
     * 获取levelingPriority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLevelingPriority() {
        return levelingPriority;
    }

    /**
     * 设置levelingPriority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLevelingPriority(Integer value) {
        this.levelingPriority = value;
    }

    /**
     * 获取limitMultipleFloatPaths属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getLimitMultipleFloatPaths() {
        return limitMultipleFloatPaths;
    }

    /**
     * 设置limitMultipleFloatPaths属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setLimitMultipleFloatPaths(JAXBElement<Boolean> value) {
        this.limitMultipleFloatPaths = value;
    }

    /**
     * 获取linkActualToActualThisPeriod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLinkActualToActualThisPeriod() {
        return linkActualToActualThisPeriod;
    }

    /**
     * 设置linkActualToActualThisPeriod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLinkActualToActualThisPeriod(Boolean value) {
        this.linkActualToActualThisPeriod = value;
    }

    /**
     * 获取linkPercentCompleteWithActual属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLinkPercentCompleteWithActual() {
        return linkPercentCompleteWithActual;
    }

    /**
     * 设置linkPercentCompleteWithActual属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLinkPercentCompleteWithActual(Boolean value) {
        this.linkPercentCompleteWithActual = value;
    }

    /**
     * 获取linkPlannedAndAtCompletionFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLinkPlannedAndAtCompletionFlag() {
        return linkPlannedAndAtCompletionFlag;
    }

    /**
     * 设置linkPlannedAndAtCompletionFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLinkPlannedAndAtCompletionFlag(Boolean value) {
        this.linkPlannedAndAtCompletionFlag = value;
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
     * 获取longitude属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getLongitude() {
        return longitude;
    }

    /**
     * 设置longitude属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setLongitude(JAXBElement<Double> value) {
        this.longitude = value;
    }

    /**
     * 获取makeOpenEndedActivitiesCritical属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getMakeOpenEndedActivitiesCritical() {
        return makeOpenEndedActivitiesCritical;
    }

    /**
     * 设置makeOpenEndedActivitiesCritical属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setMakeOpenEndedActivitiesCritical(JAXBElement<Boolean> value) {
        this.makeOpenEndedActivitiesCritical = value;
    }

    /**
     * 获取maximumMultipleFloatPaths属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getMaximumMultipleFloatPaths() {
        return maximumMultipleFloatPaths;
    }

    /**
     * 设置maximumMultipleFloatPaths属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setMaximumMultipleFloatPaths(JAXBElement<Integer> value) {
        this.maximumMultipleFloatPaths = value;
    }

    /**
     * 获取multipleFloatPathsEnabled属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getMultipleFloatPathsEnabled() {
        return multipleFloatPathsEnabled;
    }

    /**
     * 设置multipleFloatPathsEnabled属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setMultipleFloatPathsEnabled(JAXBElement<Boolean> value) {
        this.multipleFloatPathsEnabled = value;
    }

    /**
     * 获取multipleFloatPathsEndingActivityObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getMultipleFloatPathsEndingActivityObjectId() {
        return multipleFloatPathsEndingActivityObjectId;
    }

    /**
     * 设置multipleFloatPathsEndingActivityObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setMultipleFloatPathsEndingActivityObjectId(JAXBElement<Integer> value) {
        this.multipleFloatPathsEndingActivityObjectId = value;
    }

    /**
     * 获取multipleFloatPathsUseTotalFloat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getMultipleFloatPathsUseTotalFloat() {
        return multipleFloatPathsUseTotalFloat;
    }

    /**
     * 设置multipleFloatPathsUseTotalFloat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setMultipleFloatPathsUseTotalFloat(JAXBElement<Boolean> value) {
        this.multipleFloatPathsUseTotalFloat = value;
    }

    /**
     * 获取mustFinishByDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getMustFinishByDate() {
        return mustFinishByDate;
    }

    /**
     * 设置mustFinishByDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setMustFinishByDate(JAXBElement<XMLGregorianCalendar> value) {
        this.mustFinishByDate = value;
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
     * 获取netPresentValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getNetPresentValue() {
        return netPresentValue;
    }

    /**
     * 设置netPresentValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setNetPresentValue(JAXBElement<Double> value) {
        this.netPresentValue = value;
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
     * 获取outOfSequenceScheduleType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutOfSequenceScheduleType() {
        return outOfSequenceScheduleType;
    }

    /**
     * 设置outOfSequenceScheduleType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutOfSequenceScheduleType(String value) {
        this.outOfSequenceScheduleType = value;
    }

    /**
     * 获取overallProjectScore属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getOverallProjectScore() {
        return overallProjectScore;
    }

    /**
     * 设置overallProjectScore属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setOverallProjectScore(JAXBElement<Integer> value) {
        this.overallProjectScore = value;
    }

    /**
     * 获取ownerResourceObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getOwnerResourceObjectId() {
        return ownerResourceObjectId;
    }

    /**
     * 设置ownerResourceObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setOwnerResourceObjectId(JAXBElement<Integer> value) {
        this.ownerResourceObjectId = value;
    }

    /**
     * 获取parentEPSObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getParentEPSObjectId() {
        return parentEPSObjectId;
    }

    /**
     * 设置parentEPSObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setParentEPSObjectId(Integer value) {
        this.parentEPSObjectId = value;
    }

    /**
     * 获取paybackPeriod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getPaybackPeriod() {
        return paybackPeriod;
    }

    /**
     * 设置paybackPeriod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setPaybackPeriod(JAXBElement<Integer> value) {
        this.paybackPeriod = value;
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
     * 获取primaryResourcesCanMarkActivitiesAsCompleted属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrimaryResourcesCanMarkActivitiesAsCompleted() {
        return primaryResourcesCanMarkActivitiesAsCompleted;
    }

    /**
     * 设置primaryResourcesCanMarkActivitiesAsCompleted属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrimaryResourcesCanMarkActivitiesAsCompleted(Boolean value) {
        this.primaryResourcesCanMarkActivitiesAsCompleted = value;
    }

    /**
     * 获取projectForecastStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getProjectForecastStartDate() {
        return projectForecastStartDate;
    }

    /**
     * 设置projectForecastStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setProjectForecastStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.projectForecastStartDate = value;
    }

    /**
     * 获取projectScheduleType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectScheduleType() {
        return projectScheduleType;
    }

    /**
     * 设置projectScheduleType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectScheduleType(String value) {
        this.projectScheduleType = value;
    }

    /**
     * 获取propertyType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * 设置propertyType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyType(String value) {
        this.propertyType = value;
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
     * 获取publicationPriority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPublicationPriority() {
        return publicationPriority;
    }

    /**
     * 设置publicationPriority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPublicationPriority(Integer value) {
        this.publicationPriority = value;
    }

    /**
     * 获取publishLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishLevel() {
        return publishLevel;
    }

    /**
     * 设置publishLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishLevel(String value) {
        this.publishLevel = value;
    }

    /**
     * 获取relationshipLagCalendar属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipLagCalendar() {
        return relationshipLagCalendar;
    }

    /**
     * 设置relationshipLagCalendar属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipLagCalendar(String value) {
        this.relationshipLagCalendar = value;
    }

    /**
     * 获取resetPlannedToRemainingFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResetPlannedToRemainingFlag() {
        return resetPlannedToRemainingFlag;
    }

    /**
     * 设置resetPlannedToRemainingFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResetPlannedToRemainingFlag(Boolean value) {
        this.resetPlannedToRemainingFlag = value;
    }

    /**
     * 获取resourceCanBeAssignedToSameActivityMoreThanOnce属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResourceCanBeAssignedToSameActivityMoreThanOnce() {
        return resourceCanBeAssignedToSameActivityMoreThanOnce;
    }

    /**
     * 设置resourceCanBeAssignedToSameActivityMoreThanOnce属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResourceCanBeAssignedToSameActivityMoreThanOnce(Boolean value) {
        this.resourceCanBeAssignedToSameActivityMoreThanOnce = value;
    }

    /**
     * 获取resourcesCanAssignThemselvesToActivities属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResourcesCanAssignThemselvesToActivities() {
        return resourcesCanAssignThemselvesToActivities;
    }

    /**
     * 设置resourcesCanAssignThemselvesToActivities属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResourcesCanAssignThemselvesToActivities(Boolean value) {
        this.resourcesCanAssignThemselvesToActivities = value;
    }

    /**
     * 获取resourcesCanEditAssignmentPercentComplete属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResourcesCanEditAssignmentPercentComplete() {
        return resourcesCanEditAssignmentPercentComplete;
    }

    /**
     * 设置resourcesCanEditAssignmentPercentComplete属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResourcesCanEditAssignmentPercentComplete(Boolean value) {
        this.resourcesCanEditAssignmentPercentComplete = value;
    }

    /**
     * 获取resourcesCanMarkAssignmentAsCompleted属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResourcesCanMarkAssignmentAsCompleted() {
        return resourcesCanMarkAssignmentAsCompleted;
    }

    /**
     * 设置resourcesCanMarkAssignmentAsCompleted属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResourcesCanMarkAssignmentAsCompleted(Boolean value) {
        this.resourcesCanMarkAssignmentAsCompleted = value;
    }

    /**
     * 获取resourcesCanViewInactiveActivities属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResourcesCanViewInactiveActivities() {
        return resourcesCanViewInactiveActivities;
    }

    /**
     * 设置resourcesCanViewInactiveActivities属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResourcesCanViewInactiveActivities(Boolean value) {
        this.resourcesCanViewInactiveActivities = value;
    }

    /**
     * 获取returnOnInvestment属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getReturnOnInvestment() {
        return returnOnInvestment;
    }

    /**
     * 设置returnOnInvestment属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setReturnOnInvestment(JAXBElement<Double> value) {
        this.returnOnInvestment = value;
    }

    /**
     * 获取riskExposure属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getRiskExposure() {
        return riskExposure;
    }

    /**
     * 设置riskExposure属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setRiskExposure(JAXBElement<Double> value) {
        this.riskExposure = value;
    }

    /**
     * 获取riskLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * 设置riskLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskLevel(String value) {
        this.riskLevel = value;
    }

    /**
     * 获取riskMatrixName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskMatrixName() {
        return riskMatrixName;
    }

    /**
     * 设置riskMatrixName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskMatrixName(String value) {
        this.riskMatrixName = value;
    }

    /**
     * 获取riskMatrixObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getRiskMatrixObjectId() {
        return riskMatrixObjectId;
    }

    /**
     * 设置riskMatrixObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setRiskMatrixObjectId(JAXBElement<Integer> value) {
        this.riskMatrixObjectId = value;
    }

    /**
     * 获取riskScore属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getRiskScore() {
        return riskScore;
    }

    /**
     * 设置riskScore属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setRiskScore(JAXBElement<Integer> value) {
        this.riskScore = value;
    }

    /**
     * 获取scheduleWBSHierarchyType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleWBSHierarchyType() {
        return scheduleWBSHierarchyType;
    }

    /**
     * 设置scheduleWBSHierarchyType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleWBSHierarchyType(String value) {
        this.scheduleWBSHierarchyType = value;
    }

    /**
     * 获取scheduledFinishDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getScheduledFinishDate() {
        return scheduledFinishDate;
    }

    /**
     * 设置scheduledFinishDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setScheduledFinishDate(JAXBElement<XMLGregorianCalendar> value) {
        this.scheduledFinishDate = value;
    }

    /**
     * 获取sourceProjectObjectId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSourceProjectObjectId() {
        return sourceProjectObjectId;
    }

    /**
     * 设置sourceProjectObjectId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSourceProjectObjectId(JAXBElement<Integer> value) {
        this.sourceProjectObjectId = value;
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
     * 获取startToStartLagCalculationType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getStartToStartLagCalculationType() {
        return startToStartLagCalculationType;
    }

    /**
     * 设置startToStartLagCalculationType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setStartToStartLagCalculationType(JAXBElement<Boolean> value) {
        this.startToStartLagCalculationType = value;
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
     * 获取strategicPriority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getStrategicPriority() {
        return strategicPriority;
    }

    /**
     * 设置strategicPriority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setStrategicPriority(JAXBElement<Integer> value) {
        this.strategicPriority = value;
    }

    /**
     * 获取summarizeResourcesRolesByWBS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSummarizeResourcesRolesByWBS() {
        return summarizeResourcesRolesByWBS;
    }

    /**
     * 设置summarizeResourcesRolesByWBS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSummarizeResourcesRolesByWBS(Boolean value) {
        this.summarizeResourcesRolesByWBS = value;
    }

    /**
     * 获取summarizeToWBSLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSummarizeToWBSLevel() {
        return summarizeToWBSLevel;
    }

    /**
     * 设置summarizeToWBSLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSummarizeToWBSLevel(JAXBElement<Integer> value) {
        this.summarizeToWBSLevel = value;
    }

    /**
     * 获取summarizedDataDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getSummarizedDataDate() {
        return summarizedDataDate;
    }

    /**
     * 设置summarizedDataDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setSummarizedDataDate(JAXBElement<XMLGregorianCalendar> value) {
        this.summarizedDataDate = value;
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
     * 获取summaryLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryLevel() {
        return summaryLevel;
    }

    /**
     * 设置summaryLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryLevel(String value) {
        this.summaryLevel = value;
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
     * 获取syncWbsHierarchyFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getSyncWbsHierarchyFlag() {
        return syncWbsHierarchyFlag;
    }

    /**
     * 设置syncWbsHierarchyFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setSyncWbsHierarchyFlag(JAXBElement<Boolean> value) {
        this.syncWbsHierarchyFlag = value;
    }

    /**
     * 获取teamMemberActivityFields属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeamMemberActivityFields() {
        return teamMemberActivityFields;
    }

    /**
     * 设置teamMemberActivityFields属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeamMemberActivityFields(String value) {
        this.teamMemberActivityFields = value;
    }

    /**
     * 获取teamMemberAddNewActualUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamMemberAddNewActualUnits() {
        return teamMemberAddNewActualUnits;
    }

    /**
     * 设置teamMemberAddNewActualUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamMemberAddNewActualUnits(Boolean value) {
        this.teamMemberAddNewActualUnits = value;
    }

    /**
     * 获取teamMemberAssignmentOption属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeamMemberAssignmentOption() {
        return teamMemberAssignmentOption;
    }

    /**
     * 设置teamMemberAssignmentOption属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeamMemberAssignmentOption(String value) {
        this.teamMemberAssignmentOption = value;
    }

    /**
     * 获取teamMemberCanStatusOtherResources属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamMemberCanStatusOtherResources() {
        return teamMemberCanStatusOtherResources;
    }

    /**
     * 设置teamMemberCanStatusOtherResources属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamMemberCanStatusOtherResources(Boolean value) {
        this.teamMemberCanStatusOtherResources = value;
    }

    /**
     * 获取teamMemberCanUpdateNotebooks属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamMemberCanUpdateNotebooks() {
        return teamMemberCanUpdateNotebooks;
    }

    /**
     * 设置teamMemberCanUpdateNotebooks属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamMemberCanUpdateNotebooks(Boolean value) {
        this.teamMemberCanUpdateNotebooks = value;
    }

    /**
     * 获取teamMemberDisplayPlannedUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamMemberDisplayPlannedUnits() {
        return teamMemberDisplayPlannedUnits;
    }

    /**
     * 设置teamMemberDisplayPlannedUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamMemberDisplayPlannedUnits(Boolean value) {
        this.teamMemberDisplayPlannedUnits = value;
    }

    /**
     * 获取teamMemberIncludePrimaryResources属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamMemberIncludePrimaryResources() {
        return teamMemberIncludePrimaryResources;
    }

    /**
     * 设置teamMemberIncludePrimaryResources属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamMemberIncludePrimaryResources(Boolean value) {
        this.teamMemberIncludePrimaryResources = value;
    }

    /**
     * 获取teamMemberResourceAssignmentFields属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeamMemberResourceAssignmentFields() {
        return teamMemberResourceAssignmentFields;
    }

    /**
     * 设置teamMemberResourceAssignmentFields属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeamMemberResourceAssignmentFields(String value) {
        this.teamMemberResourceAssignmentFields = value;
    }

    /**
     * 获取teamMemberStepsAddDeletable属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTeamMemberStepsAddDeletable() {
        return teamMemberStepsAddDeletable;
    }

    /**
     * 设置teamMemberStepsAddDeletable属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTeamMemberStepsAddDeletable(Boolean value) {
        this.teamMemberStepsAddDeletable = value;
    }

    /**
     * 获取teamMemberViewableFields属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeamMemberViewableFields() {
        return teamMemberViewableFields;
    }

    /**
     * 设置teamMemberViewableFields属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeamMemberViewableFields(String value) {
        this.teamMemberViewableFields = value;
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
     * 获取totalFunding属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getTotalFunding() {
        return totalFunding;
    }

    /**
     * 设置totalFunding属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setTotalFunding(JAXBElement<Double> value) {
        this.totalFunding = value;
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
     * 获取unifierCBSTasksOnlyFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getUnifierCBSTasksOnlyFlag() {
        return unifierCBSTasksOnlyFlag;
    }

    /**
     * 设置unifierCBSTasksOnlyFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setUnifierCBSTasksOnlyFlag(JAXBElement<Boolean> value) {
        this.unifierCBSTasksOnlyFlag = value;
    }

    /**
     * 获取unifierDataMappingName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierDataMappingName() {
        return unifierDataMappingName;
    }

    /**
     * 设置unifierDataMappingName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierDataMappingName(String value) {
        this.unifierDataMappingName = value;
    }

    /**
     * 获取unifierDeleteActivitiesFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getUnifierDeleteActivitiesFlag() {
        return unifierDeleteActivitiesFlag;
    }

    /**
     * 设置unifierDeleteActivitiesFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setUnifierDeleteActivitiesFlag(JAXBElement<Boolean> value) {
        this.unifierDeleteActivitiesFlag = value;
    }

    /**
     * 获取unifierEnabledFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getUnifierEnabledFlag() {
        return unifierEnabledFlag;
    }

    /**
     * 设置unifierEnabledFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setUnifierEnabledFlag(JAXBElement<Boolean> value) {
        this.unifierEnabledFlag = value;
    }

    /**
     * 获取unifierProjectName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierProjectName() {
        return unifierProjectName;
    }

    /**
     * 设置unifierProjectName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierProjectName(String value) {
        this.unifierProjectName = value;
    }

    /**
     * 获取unifierProjectNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierProjectNumber() {
        return unifierProjectNumber;
    }

    /**
     * 设置unifierProjectNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierProjectNumber(String value) {
        this.unifierProjectNumber = value;
    }

    /**
     * 获取unifierScheduleSheetName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifierScheduleSheetName() {
        return unifierScheduleSheetName;
    }

    /**
     * 设置unifierScheduleSheetName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifierScheduleSheetName(String value) {
        this.unifierScheduleSheetName = value;
    }

    /**
     * 获取useExpectedFinishDates属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getUseExpectedFinishDates() {
        return useExpectedFinishDates;
    }

    /**
     * 设置useExpectedFinishDates属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setUseExpectedFinishDates(JAXBElement<Boolean> value) {
        this.useExpectedFinishDates = value;
    }

    /**
     * 获取useProjectBaselineForEarnedValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseProjectBaselineForEarnedValue() {
        return useProjectBaselineForEarnedValue;
    }

    /**
     * 设置useProjectBaselineForEarnedValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseProjectBaselineForEarnedValue(Boolean value) {
        this.useProjectBaselineForEarnedValue = value;
    }

    /**
     * 获取wbsCodeSeparator属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWBSCodeSeparator() {
        return wbsCodeSeparator;
    }

    /**
     * 设置wbsCodeSeparator属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWBSCodeSeparator(String value) {
        this.wbsCodeSeparator = value;
    }

    /**
     * 获取wbsHierarchyLevels属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getWBSHierarchyLevels() {
        return wbsHierarchyLevels;
    }

    /**
     * 设置wbsHierarchyLevels属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setWBSHierarchyLevels(JAXBElement<Integer> value) {
        this.wbsHierarchyLevels = value;
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
     * 获取webSiteRootDirectory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebSiteRootDirectory() {
        return webSiteRootDirectory;
    }

    /**
     * 设置webSiteRootDirectory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebSiteRootDirectory(String value) {
        this.webSiteRootDirectory = value;
    }

    /**
     * 获取webSiteURL属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebSiteURL() {
        return webSiteURL;
    }

    /**
     * 设置webSiteURL属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebSiteURL(String value) {
        this.webSiteURL = value;
    }

    /**
     * 获取external属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExternal() {
        return external;
    }

    /**
     * 设置external属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExternal(Boolean value) {
        this.external = value;
    }

}
