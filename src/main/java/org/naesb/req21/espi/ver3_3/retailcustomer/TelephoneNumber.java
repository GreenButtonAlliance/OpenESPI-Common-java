
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
 * Telephone number.
 * 
 * <p>Java class for TelephoneNumber complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TelephoneNumber">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="countryCode" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="areaCode" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="cityCode" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="localNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="ext" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="dialOut" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="internationalPrefix" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="ituPhone" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelephoneNumber", namespace = "http://naesb.org/espi/customer", propOrder = {
    "countryCode",
    "areaCode",
    "cityCode",
    "localNumber",
    "ext",
    "dialOut",
    "internationalPrefix",
    "ituPhone"
})
public class TelephoneNumber
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String countryCode;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String areaCode;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String cityCode;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String localNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String ext;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String dialOut;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String internationalPrefix;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String ituPhone;

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    /**
     * Gets the value of the cityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Sets the value of the cityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityCode(String value) {
        this.cityCode = value;
    }

    /**
     * Gets the value of the localNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalNumber() {
        return localNumber;
    }

    /**
     * Sets the value of the localNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalNumber(String value) {
        this.localNumber = value;
    }

    /**
     * Gets the value of the ext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExt() {
        return ext;
    }

    /**
     * Sets the value of the ext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExt(String value) {
        this.ext = value;
    }

    /**
     * Gets the value of the dialOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDialOut() {
        return dialOut;
    }

    /**
     * Sets the value of the dialOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDialOut(String value) {
        this.dialOut = value;
    }

    /**
     * Gets the value of the internationalPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternationalPrefix() {
        return internationalPrefix;
    }

    /**
     * Sets the value of the internationalPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternationalPrefix(String value) {
        this.internationalPrefix = value;
    }

    /**
     * Gets the value of the ituPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItuPhone() {
        return ituPhone;
    }

    /**
     * Sets the value of the ituPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItuPhone(String value) {
        this.ituPhone = value;
    }

}
