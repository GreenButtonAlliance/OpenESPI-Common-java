
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
 * Street details, in the context of address.
 * 
 * <p>Java class for StreetDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StreetDetail">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="number" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="name" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="suffix" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="prefix" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="type" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="code" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="buildingName" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="suiteNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="addressGeneral" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="addressGeneral2" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="addressGeneral3" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="withinTownLimits" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StreetDetail", namespace = "http://naesb.org/espi/customer", propOrder = {
    "number",
    "name",
    "suffix",
    "prefix",
    "type",
    "code",
    "buildingName",
    "suiteNumber",
    "addressGeneral",
    "addressGeneral2",
    "addressGeneral3",
    "withinTownLimits"
})
public class StreetDetail
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String number;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String name;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String suffix;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String prefix;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String type;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String code;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String buildingName;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String suiteNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String addressGeneral;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String addressGeneral2;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String addressGeneral3;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean withinTownLimits;

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

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
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

    /**
     * Gets the value of the prefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the buildingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Sets the value of the buildingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildingName(String value) {
        this.buildingName = value;
    }

    /**
     * Gets the value of the suiteNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuiteNumber() {
        return suiteNumber;
    }

    /**
     * Sets the value of the suiteNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuiteNumber(String value) {
        this.suiteNumber = value;
    }

    /**
     * Gets the value of the addressGeneral property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressGeneral() {
        return addressGeneral;
    }

    /**
     * Sets the value of the addressGeneral property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressGeneral(String value) {
        this.addressGeneral = value;
    }

    /**
     * Gets the value of the addressGeneral2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressGeneral2() {
        return addressGeneral2;
    }

    /**
     * Sets the value of the addressGeneral2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressGeneral2(String value) {
        this.addressGeneral2 = value;
    }

    /**
     * Gets the value of the addressGeneral3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressGeneral3() {
        return addressGeneral3;
    }

    /**
     * Sets the value of the addressGeneral3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressGeneral3(String value) {
        this.addressGeneral3 = value;
    }

    /**
     * Gets the value of the withinTownLimits property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWithinTownLimits() {
        return withinTownLimits;
    }

    /**
     * Sets the value of the withinTownLimits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWithinTownLimits(Boolean value) {
        this.withinTownLimits = value;
    }

}
