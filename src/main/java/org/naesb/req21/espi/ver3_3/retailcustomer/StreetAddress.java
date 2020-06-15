
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
 * General purpose street and postal address information.
 * 
 * <p>Java class for StreetAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StreetAddress">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="streetDetail" type="{http://naesb.org/espi/customer}StreetDetail" minOccurs="0"/>
 *         &lt;element name="townDetail" type="{http://naesb.org/espi/customer}TownDetail" minOccurs="0"/>
 *         &lt;element name="status" type="{http://naesb.org/espi/customer}Status" minOccurs="0"/>
 *         &lt;element name="postalCode" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="poBox" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StreetAddress", namespace = "http://naesb.org/espi/customer", propOrder = {
    "streetDetail",
    "townDetail",
    "status",
    "postalCode",
    "poBox"
})
public class StreetAddress
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected StreetDetail streetDetail;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected TownDetail townDetail;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Status status;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String postalCode;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String poBox;

    /**
     * Gets the value of the streetDetail property.
     * 
     * @return
     *     possible object is
     *     {@link StreetDetail }
     *     
     */
    public StreetDetail getStreetDetail() {
        return streetDetail;
    }

    /**
     * Sets the value of the streetDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreetDetail }
     *     
     */
    public void setStreetDetail(StreetDetail value) {
        this.streetDetail = value;
    }

    /**
     * Gets the value of the townDetail property.
     * 
     * @return
     *     possible object is
     *     {@link TownDetail }
     *     
     */
    public TownDetail getTownDetail() {
        return townDetail;
    }

    /**
     * Sets the value of the townDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TownDetail }
     *     
     */
    public void setTownDetail(TownDetail value) {
        this.townDetail = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the poBox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoBox() {
        return poBox;
    }

    /**
     * Sets the value of the poBox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoBox(String value) {
        this.poBox = value;
    }

}
