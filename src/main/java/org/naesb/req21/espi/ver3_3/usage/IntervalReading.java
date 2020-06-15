
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

package org.naesb.req21.espi.ver3_3.usage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Specific value measured by a meter or other asset. Each Reading is associated with a specific ReadingType.
 * 
 * <p>Java class for IntervalReading complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntervalReading">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="cost" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="ReadingQuality" type="{http://naesb.org/espi}ReadingQuality" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="timePeriod" type="{http://naesb.org/espi}DateTimeInterval" minOccurs="0"/>
 *         &lt;element name="value" type="{http://naesb.org/espi}Int48" minOccurs="0"/>
 *         &lt;element name="consumptionTier" type="{http://naesb.org/espi}Int16" minOccurs="0"/>
 *         &lt;element name="tou" type="{http://naesb.org/espi}Int16" minOccurs="0"/>
 *         &lt;element name="cpp" type="{http://naesb.org/espi}Int16" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntervalReading", namespace = "http://naesb.org/espi", propOrder = {
    "cost",
    "readingQuality",
    "timePeriod",
    "value",
    "consumptionTier",
    "tou",
    "cpp"
})
public class IntervalReading
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long cost;
    @XmlElement(name = "ReadingQuality", namespace = "http://naesb.org/espi")
    protected List<ReadingQuality> readingQuality;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected DateTimeInterval timePeriod;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long value;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Short consumptionTier;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Short tou;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Short cpp;

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCost(Long value) {
        this.cost = value;
    }

    /**
     * Gets the value of the readingQuality property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the readingQuality property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReadingQuality().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReadingQuality }
     * 
     * 
     */
    public List<ReadingQuality> getReadingQuality() {
        if (readingQuality == null) {
            readingQuality = new ArrayList<ReadingQuality>();
        }
        return this.readingQuality;
    }

    /**
     * Gets the value of the timePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeInterval }
     *     
     */
    public DateTimeInterval getTimePeriod() {
        return timePeriod;
    }

    /**
     * Sets the value of the timePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeInterval }
     *     
     */
    public void setTimePeriod(DateTimeInterval value) {
        this.timePeriod = value;
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

}
