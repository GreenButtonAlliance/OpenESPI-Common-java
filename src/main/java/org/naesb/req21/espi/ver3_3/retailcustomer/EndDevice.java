
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


/**
 * Asset container that performs one or more end device functions. One type of end device is a meter which can perform metering, load management, connect/disconnect, accounting functions, etc. Some end devices, such as ones monitoring and controlling air conditioners, refrigerators, pool pumps may be connected to a meter. All end devices may have communication capability defined by the associated communication function(s). An end device may be owned by a consumer, a service provider, utility or otherwise.
 * There may be a related end device function that identifies a sensor or control point within a metering application or communications systems (e.g., water, gas, electricity).
 * Some devices may use an optical port that conforms to the ANSI C12.18 standard for communications.
 * 
 * <p>Java class for EndDevice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EndDevice">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}AssetContainer">
 *       &lt;sequence>
 *         &lt;element name="isVirtual" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isPan" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="installCode" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="amrSystem" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EndDevice", namespace = "http://naesb.org/espi/customer", propOrder = {
    "isVirtual",
    "isPan",
    "installCode",
    "amrSystem"
})
@XmlSeeAlso({
    Meter.class
})
public class EndDevice
    extends AssetContainer
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean isVirtual;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean isPan;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String installCode;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String amrSystem;

    /**
     * Gets the value of the isVirtual property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVirtual() {
        return isVirtual;
    }

    /**
     * Sets the value of the isVirtual property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVirtual(Boolean value) {
        this.isVirtual = value;
    }

    /**
     * Gets the value of the isPan property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPan() {
        return isPan;
    }

    /**
     * Sets the value of the isPan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPan(Boolean value) {
        this.isPan = value;
    }

    /**
     * Gets the value of the installCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallCode() {
        return installCode;
    }

    /**
     * Sets the value of the installCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallCode(String value) {
        this.installCode = value;
    }

    /**
     * Gets the value of the amrSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmrSystem() {
        return amrSystem;
    }

    /**
     * Sets the value of the amrSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmrSystem(String value) {
        this.amrSystem = value;
    }

}
