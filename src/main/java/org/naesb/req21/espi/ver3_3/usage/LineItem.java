
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * [extension] Line item of detail for additional cost
 * 
 * <p>Java class for LineItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItem">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="rounding" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="dateTime" type="{http://naesb.org/espi}TimeType" minOccurs="0"/>
 *         &lt;element name="note" type="{http://naesb.org/espi}String256"/>
 *         &lt;element name="measurement" type="{http://naesb.org/espi}SummaryMeasurement" minOccurs="0"/>
 *         &lt;element name="itemKind" type="{http://naesb.org/espi}ItemKind"/>
 *         &lt;element name="unitCost" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="itemPeriod" type="{http://naesb.org/espi}DateTimeInterval" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItem", namespace = "http://naesb.org/espi", propOrder = {
    "amount",
    "rounding",
    "dateTime",
    "note",
    "measurement",
    "itemKind",
    "unitCost",
    "itemPeriod"
})
public class LineItem
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long amount;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long rounding;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long dateTime;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String note;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected SummaryMeasurement measurement;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String itemKind;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long unitCost;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected DateTimeInterval itemPeriod;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAmount(Long value) {
        this.amount = value;
    }

    /**
     * Gets the value of the rounding property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRounding() {
        return rounding;
    }

    /**
     * Sets the value of the rounding property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRounding(Long value) {
        this.rounding = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDateTime(Long value) {
        this.dateTime = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the measurement property.
     * 
     * @return
     *     possible object is
     *     {@link SummaryMeasurement }
     *     
     */
    public SummaryMeasurement getMeasurement() {
        return measurement;
    }

    /**
     * Sets the value of the measurement property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaryMeasurement }
     *     
     */
    public void setMeasurement(SummaryMeasurement value) {
        this.measurement = value;
    }

    /**
     * Gets the value of the itemKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemKind() {
        return itemKind;
    }

    /**
     * Sets the value of the itemKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemKind(String value) {
        this.itemKind = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUnitCost(Long value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the itemPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeInterval }
     *     
     */
    public DateTimeInterval getItemPeriod() {
        return itemPeriod;
    }

    /**
     * Sets the value of the itemPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeInterval }
     *     
     */
    public void setItemPeriod(DateTimeInterval value) {
        this.itemPeriod = value;
    }

}
