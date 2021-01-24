
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
 * [extension] Service Delivery Point is representation of revenue UsagePoint attributes
 * 
 * <p>Java class for ServiceDeliveryPoint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDeliveryPoint">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://naesb.org/espi}String256" minOccurs="0"/>
 *         &lt;element name="tariffProfile" type="{http://naesb.org/espi}String256" minOccurs="0"/>
 *         &lt;element name="customerAgreement" type="{http://naesb.org/espi}String256" minOccurs="0"/>
 *         &lt;element name="tariffRiderRefs" type="{http://naesb.org/espi}TariffRiderRefs" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDeliveryPoint", namespace = "http://naesb.org/espi", propOrder = {
    "name",
    "tariffProfile",
    "customerAgreement",
    "tariffRiderRefs"
})
public class ServiceDeliveryPoint
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi")
    protected String name;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String tariffProfile;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected String customerAgreement;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected TariffRiderRefs tariffRiderRefs;

    /**
     * Gets the value of the name property.
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
     * Sets the value of the name property.
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
     * Gets the value of the tariffProfile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTariffProfile() {
        return tariffProfile;
    }

    /**
     * Sets the value of the tariffProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTariffProfile(String value) {
        this.tariffProfile = value;
    }

    /**
     * Gets the value of the customerAgreement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerAgreement() {
        return customerAgreement;
    }

    /**
     * Sets the value of the customerAgreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerAgreement(String value) {
        this.customerAgreement = value;
    }

    /**
     * Gets the value of the tariffRiderRefs property.
     * 
     * @return
     *     possible object is
     *     {@link TariffRiderRefs }
     *     
     */
    public TariffRiderRefs getTariffRiderRefs() {
        return tariffRiderRefs;
    }

    /**
     * Sets the value of the tariffRiderRefs property.
     * 
     * @param value
     *     allowed object is
     *     {@link TariffRiderRefs }
     *     
     */
    public void setTariffRiderRefs(TariffRiderRefs value) {
        this.tariffRiderRefs = value;
    }

}
