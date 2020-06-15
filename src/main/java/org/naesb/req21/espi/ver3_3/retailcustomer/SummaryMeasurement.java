
/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.naesb.req21.espi.ver3_3.retailcustomer;

import javax.xml.bind.annotation.*;


/**
 * An aggregated summary measurement reading
 * 
 * <p>Java class for SummaryMeasurement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SummaryMeasurement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="powerOfTenMultiplier" type="{http://naesb.org/espi/customer}UnitMultiplierKind" minOccurs="0"/>
 *         &lt;element name="timeStamp" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="uom" type="{http://naesb.org/espi/customer}UnitSymbolKind" minOccurs="0"/>
 *         &lt;element name="value" type="{http://naesb.org/espi/customer}Int48" minOccurs="0"/>
 *         &lt;element name="readingTypeRef" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummaryMeasurement", namespace = "http://naesb.org/espi/customer", propOrder = {
    "powerOfTenMultiplier",
    "timeStamp",
    "uom",
    "value",
    "readingTypeRef"
})
public class SummaryMeasurement
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String powerOfTenMultiplier;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long timeStamp;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String uom;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long value;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    @XmlSchemaType(name = "anyURI")
    protected String readingTypeRef;

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
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTimeStamp(Long value) {
        this.timeStamp = value;
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
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * Gets the value of the readingTypeRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReadingTypeRef() {
        return readingTypeRef;
    }

    /**
     * Sets the value of the readingTypeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReadingTypeRef(String value) {
        this.readingTypeRef = value;
    }

}
