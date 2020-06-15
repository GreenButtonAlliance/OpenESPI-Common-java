
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
 * Tangible resource of the utility, including power system equipment, various end devices, cabinets, buildings, etc. For electrical network equipment, the role of the asset is defined through PowerSystemResource and its subclasses, defined mainly in the Wires model (refer to IEC61970-301 and model package IEC61970::Wires). Asset description places emphasis on the physical characteristics of the equipment fulfilling that role.
 * 
 * <p>Java class for Asset complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Asset">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="utcNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="serialNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="lotNumber" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="purchasePrice" type="{http://naesb.org/espi/customer}Int48" minOccurs="0"/>
 *         &lt;element name="critical" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="electronicAddress" type="{http://naesb.org/espi/customer}ElectronicAddress" minOccurs="0"/>
 *         &lt;element name="lifecycle" type="{http://naesb.org/espi/customer}LifecycleDate" minOccurs="0"/>
 *         &lt;element name="acceptanceTest" type="{http://naesb.org/espi/customer}AcceptanceTest" minOccurs="0"/>
 *         &lt;element name="initialCondition" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="initialLossOfLife" type="{http://naesb.org/espi/customer}PerCent" minOccurs="0"/>
 *         &lt;element name="status" type="{http://naesb.org/espi/customer}Status" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Asset", namespace = "http://naesb.org/espi/customer", propOrder = {
    "type",
    "utcNumber",
    "serialNumber",
    "lotNumber",
    "purchasePrice",
    "critical",
    "electronicAddress",
    "lifecycle",
    "acceptanceTest",
    "initialCondition",
    "initialLossOfLife",
    "status"
})
@XmlSeeAlso({
    AssetContainer.class
})
public class Asset
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String type;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String utcNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String serialNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String lotNumber;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long purchasePrice;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean critical;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected ElectronicAddress electronicAddress;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected LifecycleDate lifecycle;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected AcceptanceTest acceptanceTest;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String initialCondition;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    @XmlSchemaType(name = "integer")
    protected Integer initialLossOfLife;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Status status;

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
     * Gets the value of the utcNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtcNumber() {
        return utcNumber;
    }

    /**
     * Sets the value of the utcNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtcNumber(String value) {
        this.utcNumber = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the lotNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * Sets the value of the lotNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotNumber(String value) {
        this.lotNumber = value;
    }

    /**
     * Gets the value of the purchasePrice property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets the value of the purchasePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPurchasePrice(Long value) {
        this.purchasePrice = value;
    }

    /**
     * Gets the value of the critical property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCritical() {
        return critical;
    }

    /**
     * Sets the value of the critical property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCritical(Boolean value) {
        this.critical = value;
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
     * Gets the value of the lifecycle property.
     * 
     * @return
     *     possible object is
     *     {@link LifecycleDate }
     *     
     */
    public LifecycleDate getLifecycle() {
        return lifecycle;
    }

    /**
     * Sets the value of the lifecycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link LifecycleDate }
     *     
     */
    public void setLifecycle(LifecycleDate value) {
        this.lifecycle = value;
    }

    /**
     * Gets the value of the acceptanceTest property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptanceTest }
     *     
     */
    public AcceptanceTest getAcceptanceTest() {
        return acceptanceTest;
    }

    /**
     * Sets the value of the acceptanceTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptanceTest }
     *     
     */
    public void setAcceptanceTest(AcceptanceTest value) {
        this.acceptanceTest = value;
    }

    /**
     * Gets the value of the initialCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitialCondition() {
        return initialCondition;
    }

    /**
     * Sets the value of the initialCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitialCondition(String value) {
        this.initialCondition = value;
    }

    /**
     * Gets the value of the initialLossOfLife property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInitialLossOfLife() {
        return initialLossOfLife;
    }

    /**
     * Sets the value of the initialLossOfLife property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInitialLossOfLife(Integer value) {
        this.initialLossOfLife = value;
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

}
