
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * [extension] Demand Response Program enrollment or termination date information
 * 
 * <p>Java class for ProgramDate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProgramDate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="programDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="programDateDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramDate", namespace = "http://naesb.org/espi/customer", propOrder = {
    "programDate",
    "programDateDescription"
})
public class ProgramDate {

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long programDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String programDateDescription;

    /**
     * Gets the value of the programDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProgramDate() {
        return programDate;
    }

    /**
     * Sets the value of the programDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProgramDate(Long value) {
        this.programDate = value;
    }

    /**
     * Gets the value of the programDateDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramDateDescription() {
        return programDateDescription;
    }

    /**
     * Sets the value of the programDateDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramDateDescription(String value) {
        this.programDateDescription = value;
    }

}
