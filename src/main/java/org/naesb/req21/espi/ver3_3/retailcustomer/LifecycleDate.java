
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
 * Dates for lifecycle events of an asset.
 * 
 * <p>Java class for LifecycleDate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LifecycleDate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="manufacturedDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="purchaseDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="receivedDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="installationDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="removalDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="retiredDate" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LifecycleDate", namespace = "http://naesb.org/espi/customer", propOrder = {
    "manufacturedDate",
    "purchaseDate",
    "receivedDate",
    "installationDate",
    "removalDate",
    "retiredDate"
})
public class LifecycleDate
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long manufacturedDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long purchaseDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long receivedDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long installationDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long removalDate;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long retiredDate;

    /**
     * Gets the value of the manufacturedDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getManufacturedDate() {
        return manufacturedDate;
    }

    /**
     * Sets the value of the manufacturedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setManufacturedDate(Long value) {
        this.manufacturedDate = value;
    }

    /**
     * Gets the value of the purchaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the value of the purchaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPurchaseDate(Long value) {
        this.purchaseDate = value;
    }

    /**
     * Gets the value of the receivedDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getReceivedDate() {
        return receivedDate;
    }

    /**
     * Sets the value of the receivedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setReceivedDate(Long value) {
        this.receivedDate = value;
    }

    /**
     * Gets the value of the installationDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInstallationDate() {
        return installationDate;
    }

    /**
     * Sets the value of the installationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInstallationDate(Long value) {
        this.installationDate = value;
    }

    /**
     * Gets the value of the removalDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRemovalDate() {
        return removalDate;
    }

    /**
     * Sets the value of the removalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRemovalDate(Long value) {
        this.removalDate = value;
    }

    /**
     * Gets the value of the retiredDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRetiredDate() {
        return retiredDate;
    }

    /**
     * Sets the value of the retiredDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRetiredDate(Long value) {
        this.retiredDate = value;
    }

}
