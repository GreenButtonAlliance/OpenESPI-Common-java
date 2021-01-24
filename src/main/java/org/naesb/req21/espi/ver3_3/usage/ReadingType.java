
/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.naesb.req21.espi.ver3_3.usage;

import javax.xml.bind.annotation.*;


/**
 * Characteristics associated with all Readings included in a MeterReading.
 * 
 * <p>Java class for ReadingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadingType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="accumulationBehaviour" type="{http://naesb.org/espi}AccumulationKind" minOccurs="0"/>
 *         &lt;element name="commodity" type="{http://naesb.org/espi}CommodityKind" minOccurs="0"/>
 *         &lt;element name="consumptionTier" type="{http://naesb.org/espi}Int16" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://naesb.org/espi}Currency" minOccurs="0"/>
 *         &lt;element name="dataQualifier" type="{http://naesb.org/espi}DataQualifierKind" minOccurs="0"/>
 *         &lt;element name="defaultQuality" type="{http://naesb.org/espi}QualityOfReading" minOccurs="0"/>
 *         &lt;element name="flowDirection" type="{http://naesb.org/espi}FlowDirectionKind" minOccurs="0"/>
 *         &lt;element name="intervalLength" type="{http://naesb.org/espi}UInt32" minOccurs="0"/>
 *         &lt;element name="kind" type="{http://naesb.org/espi}MeasurementKind" minOccurs="0"/>
 *         &lt;element name="phase" type="{http://naesb.org/espi}PhaseCodeKind" minOccurs="0"/>
 *         &lt;element name="powerOfTenMultiplier" type="{http://naesb.org/espi}UnitMultiplierKind" minOccurs="0"/>
 *         &lt;element name="timeAttribute" type="{http://naesb.org/espi}TimePeriodOfInterest" minOccurs="0"/>
 *         &lt;element name="tou" type="{http://naesb.org/espi}Int16" minOccurs="0"/>
 *         &lt;element name="uom" type="{http://naesb.org/espi}UnitSymbolKind" minOccurs="0"/>
 *         &lt;element name="cpp" type="{http://naesb.org/espi}Int16" minOccurs="0"/>
 *         &lt;element name="interharmonic" type="{http://naesb.org/espi}ReadingInterharmonic" minOccurs="0"/>
 *         &lt;element name="measuringPeriod" type="{http://naesb.org/espi}TimeAttributeKind" minOccurs="0"/>
 *         &lt;element name="argument" type="{http://naesb.org/espi}RationalNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadingType", namespace = "http://naesb.org/espi", propOrder = {
    "accumulationBehaviour",
    "commodity",
    "consumptionTier",
    "currency",
    "dataQualifier",
    "defaultQuality",
    "flowDirection",
    "intervalLength",
    "kind",
    "phase",
    "powerOfTenMultiplier",
    "timeAttribute",
    "tou",
    "uom",
    "cpp",
    "interharmonic",
    "measuringPeriod",
    "argument"
})
public class ReadingType
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi")
    protected String accumulationBehaviour;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String commodity;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Short consumptionTier;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String currency;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String dataQualifier;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String defaultQuality;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String flowDirection;
    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "unsignedInt")
    protected Long intervalLength;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String kind;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String phase;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String powerOfTenMultiplier;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String timeAttribute;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Short tou;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String uom;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Short cpp;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected ReadingInterharmonic interharmonic;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String measuringPeriod;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected RationalNumber argument;

    /**
     * Gets the value of the accumulationBehaviour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccumulationBehaviour() {
        return accumulationBehaviour;
    }

    /**
     * Sets the value of the accumulationBehaviour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccumulationBehaviour(String value) {
        this.accumulationBehaviour = value;
    }

    /**
     * Gets the value of the commodity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodity() {
        return commodity;
    }

    /**
     * Sets the value of the commodity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodity(String value) {
        this.commodity = value;
    }

    /**
     * Gets the value of the consumptionTier property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getConsumptionTier() {
        return consumptionTier;
    }

    /**
     * Sets the value of the consumptionTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setConsumptionTier(Short value) {
        this.consumptionTier = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the dataQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataQualifier() {
        return dataQualifier;
    }

    /**
     * Sets the value of the dataQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataQualifier(String value) {
        this.dataQualifier = value;
    }

    /**
     * Gets the value of the defaultQuality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultQuality() {
        return defaultQuality;
    }

    /**
     * Sets the value of the defaultQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultQuality(String value) {
        this.defaultQuality = value;
    }

    /**
     * Gets the value of the flowDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowDirection() {
        return flowDirection;
    }

    /**
     * Sets the value of the flowDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowDirection(String value) {
        this.flowDirection = value;
    }

    /**
     * Gets the value of the intervalLength property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIntervalLength() {
        return intervalLength;
    }

    /**
     * Sets the value of the intervalLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIntervalLength(Long value) {
        this.intervalLength = value;
    }

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKind() {
        return kind;
    }

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKind(String value) {
        this.kind = value;
    }

    /**
     * Gets the value of the phase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhase() {
        return phase;
    }

    /**
     * Sets the value of the phase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhase(String value) {
        this.phase = value;
    }

    /**
     * Gets the value of the powerOfTenMultiplier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPowerOfTenMultiplier() {
        return powerOfTenMultiplier;
    }

    /**
     * Sets the value of the powerOfTenMultiplier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPowerOfTenMultiplier(String value) {
        this.powerOfTenMultiplier = value;
    }

    /**
     * Gets the value of the timeAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeAttribute() {
        return timeAttribute;
    }

    /**
     * Sets the value of the timeAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeAttribute(String value) {
        this.timeAttribute = value;
    }

    /**
     * Gets the value of the tou property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTou() {
        return tou;
    }

    /**
     * Sets the value of the tou property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTou(Short value) {
        this.tou = value;
    }

    /**
     * Gets the value of the uom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUom() {
        return uom;
    }

    /**
     * Sets the value of the uom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUom(String value) {
        this.uom = value;
    }

    /**
     * Gets the value of the cpp property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCpp() {
        return cpp;
    }

    /**
     * Sets the value of the cpp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCpp(Short value) {
        this.cpp = value;
    }

    /**
     * Gets the value of the interharmonic property.
     * 
     * @return
     *     possible object is
     *     {@link ReadingInterharmonic }
     *     
     */
    public ReadingInterharmonic getInterharmonic() {
        return interharmonic;
    }

    /**
     * Sets the value of the interharmonic property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReadingInterharmonic }
     *     
     */
    public void setInterharmonic(ReadingInterharmonic value) {
        this.interharmonic = value;
    }

    /**
     * Gets the value of the measuringPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasuringPeriod() {
        return measuringPeriod;
    }

    /**
     * Sets the value of the measuringPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasuringPeriod(String value) {
        this.measuringPeriod = value;
    }

    /**
     * Gets the value of the argument property.
     * 
     * @return
     *     possible object is
     *     {@link RationalNumber }
     *     
     */
    public RationalNumber getArgument() {
        return argument;
    }

    /**
     * Sets the value of the argument property.
     * 
     * @param value
     *     allowed object is
     *     {@link RationalNumber }
     *     
     */
    public void setArgument(RationalNumber value) {
        this.argument = value;
    }

}
