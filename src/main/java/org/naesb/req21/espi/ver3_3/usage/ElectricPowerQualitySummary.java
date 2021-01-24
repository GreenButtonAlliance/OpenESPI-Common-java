
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
 * A summary of power quality events. This information represents a summary of power quality information typically required by customer facility energy management systems. It is not intended to satisfy the detailed requirements of power quality monitoring. All values are as defined by measurementProtocol during the period. The standards typically also give ranges of allowed values; the information attributes are the raw measurements, not the "yes/no" determination by the various standards. See referenced standards for definition, measurement protocol and period.
 * 
 * <p>Java class for ElectricPowerQualitySummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElectricPowerQualitySummary">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="flickerPlt" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="flickerPst" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="harmonicVoltage" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="longInterruptions" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="mainsVoltage" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="measurementProtocol" type="{http://naesb.org/espi}UInt8" minOccurs="0"/>
 *         &lt;element name="powerFrequency" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="rapidVoltageChanges" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="shortInterruptions" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="summaryInterval" type="{http://naesb.org/espi}DateTimeInterval"/>
 *         &lt;element name="supplyVoltageDips" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="supplyVoltageImbalance" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="supplyVoltageVariations" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="tempOvervoltage" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElectricPowerQualitySummary", namespace = "http://naesb.org/espi", propOrder = {
    "flickerPlt",
    "flickerPst",
    "harmonicVoltage",
    "longInterruptions",
    "mainsVoltage",
    "measurementProtocol",
    "powerFrequency",
    "rapidVoltageChanges",
    "shortInterruptions",
    "summaryInterval",
    "supplyVoltageDips",
    "supplyVoltageImbalance",
    "supplyVoltageVariations",
    "tempOvervoltage"
})
public class ElectricPowerQualitySummary
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long flickerPlt;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long flickerPst;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long harmonicVoltage;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long longInterruptions;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long mainsVoltage;
    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "unsignedByte")
    protected Short measurementProtocol;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long powerFrequency;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long rapidVoltageChanges;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long shortInterruptions;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected DateTimeInterval summaryInterval;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long supplyVoltageDips;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long supplyVoltageImbalance;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long supplyVoltageVariations;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long tempOvervoltage;

    /**
     * Gets the value of the flickerPlt property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFlickerPlt() {
        return flickerPlt;
    }

    /**
     * Sets the value of the flickerPlt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFlickerPlt(Long value) {
        this.flickerPlt = value;
    }

    /**
     * Gets the value of the flickerPst property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFlickerPst() {
        return flickerPst;
    }

    /**
     * Sets the value of the flickerPst property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFlickerPst(Long value) {
        this.flickerPst = value;
    }

    /**
     * Gets the value of the harmonicVoltage property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHarmonicVoltage() {
        return harmonicVoltage;
    }

    /**
     * Sets the value of the harmonicVoltage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHarmonicVoltage(Long value) {
        this.harmonicVoltage = value;
    }

    /**
     * Gets the value of the longInterruptions property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLongInterruptions() {
        return longInterruptions;
    }

    /**
     * Sets the value of the longInterruptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLongInterruptions(Long value) {
        this.longInterruptions = value;
    }

    /**
     * Gets the value of the mainsVoltage property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMainsVoltage() {
        return mainsVoltage;
    }

    /**
     * Sets the value of the mainsVoltage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMainsVoltage(Long value) {
        this.mainsVoltage = value;
    }

    /**
     * Gets the value of the measurementProtocol property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getMeasurementProtocol() {
        return measurementProtocol;
    }

    /**
     * Sets the value of the measurementProtocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setMeasurementProtocol(Short value) {
        this.measurementProtocol = value;
    }

    /**
     * Gets the value of the powerFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPowerFrequency() {
        return powerFrequency;
    }

    /**
     * Sets the value of the powerFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPowerFrequency(Long value) {
        this.powerFrequency = value;
    }

    /**
     * Gets the value of the rapidVoltageChanges property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRapidVoltageChanges() {
        return rapidVoltageChanges;
    }

    /**
     * Sets the value of the rapidVoltageChanges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRapidVoltageChanges(Long value) {
        this.rapidVoltageChanges = value;
    }

    /**
     * Gets the value of the shortInterruptions property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getShortInterruptions() {
        return shortInterruptions;
    }

    /**
     * Sets the value of the shortInterruptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setShortInterruptions(Long value) {
        this.shortInterruptions = value;
    }

    /**
     * Gets the value of the summaryInterval property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeInterval }
     *     
     */
    public DateTimeInterval getSummaryInterval() {
        return summaryInterval;
    }

    /**
     * Sets the value of the summaryInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeInterval }
     *     
     */
    public void setSummaryInterval(DateTimeInterval value) {
        this.summaryInterval = value;
    }

    /**
     * Gets the value of the supplyVoltageDips property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSupplyVoltageDips() {
        return supplyVoltageDips;
    }

    /**
     * Sets the value of the supplyVoltageDips property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSupplyVoltageDips(Long value) {
        this.supplyVoltageDips = value;
    }

    /**
     * Gets the value of the supplyVoltageImbalance property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSupplyVoltageImbalance() {
        return supplyVoltageImbalance;
    }

    /**
     * Sets the value of the supplyVoltageImbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSupplyVoltageImbalance(Long value) {
        this.supplyVoltageImbalance = value;
    }

    /**
     * Gets the value of the supplyVoltageVariations property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSupplyVoltageVariations() {
        return supplyVoltageVariations;
    }

    /**
     * Sets the value of the supplyVoltageVariations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSupplyVoltageVariations(Long value) {
        this.supplyVoltageVariations = value;
    }

    /**
     * Gets the value of the tempOvervoltage property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTempOvervoltage() {
        return tempOvervoltage;
    }

    /**
     * Sets the value of the tempOvervoltage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTempOvervoltage(Long value) {
        this.tempOvervoltage = value;
    }

}
