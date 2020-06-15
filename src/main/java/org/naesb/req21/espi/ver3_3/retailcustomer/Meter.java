
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
import java.util.ArrayList;
import java.util.List;


/**
 * Physical asset that performs the metering role of the usage point. Used for measuring consumption and detection of events.
 * 
 * <p>Java class for Meter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Meter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}EndDevice">
 *       &lt;sequence>
 *         &lt;element name="formNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="MeterMultipliers" type="{http://naesb.org/espi/customer}MeterMultiplier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="intervalLength" type="{http://naesb.org/espi/customer}UInt32" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Meter", namespace = "http://naesb.org/espi/customer", propOrder = {
    "formNumber",
    "meterMultipliers",
    "intervalLength"
})
public class Meter
    extends EndDevice
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String formNumber;
    @XmlElement(name = "MeterMultipliers", namespace = "http://naesb.org/espi/customer")
    protected List<MeterMultiplier> meterMultipliers;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    @XmlSchemaType(name = "unsignedInt")
    protected Long intervalLength;

    /**
     * Gets the value of the formNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormNumber() {
        return formNumber;
    }

    /**
     * Sets the value of the formNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormNumber(String value) {
        this.formNumber = value;
    }

    /**
     * Gets the value of the meterMultipliers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meterMultipliers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeterMultipliers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeterMultiplier }
     * 
     * 
     */
    public List<MeterMultiplier> getMeterMultipliers() {
        if (meterMultipliers == null) {
            meterMultipliers = new ArrayList<MeterMultiplier>();
        }
        return this.meterMultipliers;
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

}
