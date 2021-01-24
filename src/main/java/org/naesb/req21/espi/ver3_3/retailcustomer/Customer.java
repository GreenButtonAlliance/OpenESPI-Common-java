
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Organisation receiving services from service supplier.
 * 
 * <p>Java class for Customer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Customer">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}OrganisationRole">
 *       &lt;sequence>
 *         &lt;element name="kind" type="{http://naesb.org/espi/customer}CustomerKind" minOccurs="0"/>
 *         &lt;element name="specialNeed" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="vip" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="pucNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="status" type="{http://naesb.org/espi/customer}Status" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://naesb.org/espi/customer}Priority" minOccurs="0"/>
 *         &lt;element name="locale" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="customerName" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", namespace = "http://naesb.org/espi/customer", propOrder = {
    "kind",
    "specialNeed",
    "vip",
    "pucNumber",
    "status",
    "priority",
    "locale",
    "customerName"
})
public class Customer
    extends OrganisationRole
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String kind;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String specialNeed;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean vip;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String pucNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Status status;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Priority priority;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String locale;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String customerName;

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
     * Gets the value of the specialNeed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialNeed() {
        return specialNeed;
    }

    /**
     * Sets the value of the specialNeed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialNeed(String value) {
        this.specialNeed = value;
    }

    /**
     * Gets the value of the vip property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVip() {
        return vip;
    }

    /**
     * Sets the value of the vip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVip(Boolean value) {
        this.vip = value;
    }

    /**
     * Gets the value of the pucNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPucNumber() {
        return pucNumber;
    }

    /**
     * Sets the value of the pucNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPucNumber(String value) {
        this.pucNumber = value;
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
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

}
