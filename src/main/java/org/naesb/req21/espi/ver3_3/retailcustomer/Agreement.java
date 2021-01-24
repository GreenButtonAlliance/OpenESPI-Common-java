
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

package org.naesb.req21.espi.ver3_3.retailcustomer;

import javax.xml.bind.annotation.*;


/**
 * Formal agreement between two parties defining the terms and conditions for a set of services. The specifics of the services are, in turn, defined via one or more service agreements.
 * 
 * <p>Java class for Agreement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Agreement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Document">
 *       &lt;sequence>
 *         &lt;element name="signDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="validityInterval" type="{http://naesb.org/espi/customer}DateTimeInterval" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Agreement", namespace = "http://naesb.org/espi/customer", propOrder = {
    "signDate",
    "validityInterval"
})
@XmlSeeAlso({
    CustomerAgreement.class
})
public class Agreement
    extends Document
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long signDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected DateTimeInterval validityInterval;

    /**
     * Gets the value of the signDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSignDate() {
        return signDate;
    }

    /**
     * Sets the value of the signDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSignDate(Long value) {
        this.signDate = value;
    }

    /**
     * Gets the value of the validityInterval property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeInterval }
     *     
     */
    public DateTimeInterval getValidityInterval() {
        return validityInterval;
    }

    /**
     * Sets the value of the validityInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeInterval }
     *     
     */
    public void setValidityInterval(DateTimeInterval value) {
        this.validityInterval = value;
    }

}
