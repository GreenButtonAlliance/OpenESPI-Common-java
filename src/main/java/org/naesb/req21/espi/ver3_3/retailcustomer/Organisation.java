
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
 * Organisation that might have roles as utility, contractor, supplier, manufacturer, customer, etc.
 * 
 * <p>Java class for Organisation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Organisation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="streetAddress" type="{http://naesb.org/espi/customer}StreetAddress" minOccurs="0"/>
 *         &lt;element name="postalAddress" type="{http://naesb.org/espi/customer}StreetAddress" minOccurs="0"/>
 *         &lt;element name="phone1" type="{http://naesb.org/espi/customer}TelephoneNumber" minOccurs="0"/>
 *         &lt;element name="phone2" type="{http://naesb.org/espi/customer}TelephoneNumber" minOccurs="0"/>
 *         &lt;element name="electronicAddress" type="{http://naesb.org/espi/customer}ElectronicAddress" minOccurs="0"/>
 *         &lt;element name="organisationName" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Organisation", namespace = "http://naesb.org/espi/customer", propOrder = {
    "streetAddress",
    "postalAddress",
    "phone1",
    "phone2",
    "electronicAddress",
    "organisationName"
})
public class Organisation
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected StreetAddress streetAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected StreetAddress postalAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected TelephoneNumber phone1;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected TelephoneNumber phone2;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected ElectronicAddress electronicAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String organisationName;

    /**
     * Gets the value of the streetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link StreetAddress }
     *     
     */
    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the value of the streetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreetAddress }
     *     
     */
    public void setStreetAddress(StreetAddress value) {
        this.streetAddress = value;
    }

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link StreetAddress }
     *     
     */
    public StreetAddress getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreetAddress }
     *     
     */
    public void setPostalAddress(StreetAddress value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the phone1 property.
     * 
     * @return
     *     possible object is
     *     {@link TelephoneNumber }
     *     
     */
    public TelephoneNumber getPhone1() {
        return phone1;
    }

    /**
     * Sets the value of the phone1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephoneNumber }
     *     
     */
    public void setPhone1(TelephoneNumber value) {
        this.phone1 = value;
    }

    /**
     * Gets the value of the phone2 property.
     * 
     * @return
     *     possible object is
     *     {@link TelephoneNumber }
     *     
     */
    public TelephoneNumber getPhone2() {
        return phone2;
    }

    /**
     * Sets the value of the phone2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link TelephoneNumber }
     *     
     */
    public void setPhone2(TelephoneNumber value) {
        this.phone2 = value;
    }

    /**
     * Gets the value of the electronicAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ElectronicAddress }
     *     
     */
    public ElectronicAddress getElectronicAddress() {
        return electronicAddress;
    }

    /**
     * Sets the value of the electronicAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElectronicAddress }
     *     
     */
    public void setElectronicAddress(ElectronicAddress value) {
        this.electronicAddress = value;
    }

    /**
     * Gets the value of the organisationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganisationName() {
        return organisationName;
    }

    /**
     * Sets the value of the organisationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganisationName(String value) {
        this.organisationName = value;
    }

}
