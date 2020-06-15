
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
import java.util.ArrayList;
import java.util.List;


/**
 * Agreement between the customer and the service supplier to pay for service at a specific service location. It records certain billing information about the type of service provided at the service location and is used during charge creation to determine the type of service.
 * 
 * <p>Java class for CustomerAgreement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerAgreement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Agreement">
 *       &lt;sequence>
 *         &lt;element name="loadMgmt" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="isPrePay" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="shutOffDateTime" type="{http://naesb.org/espi/customer}TimeType" minOccurs="0"/>
 *         &lt;element name="DemandResponseProgram" type="{http://naesb.org/espi/customer}DemandResponseProgram" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PricingStructures" type="{http://naesb.org/espi/customer}PricingStructure" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://naesb.org/espi/customer}Currency" minOccurs="0"/>
 *         &lt;element name="futureStatus" type="{http://naesb.org/espi/customer}Status" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="agreementId" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAgreement", namespace = "http://naesb.org/espi/customer", propOrder = {
    "loadMgmt",
    "isPrePay",
    "shutOffDateTime",
    "demandResponseProgram",
    "pricingStructures",
    "currency",
    "futureStatus",
    "agreementId"
})
public class CustomerAgreement
    extends Agreement
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String loadMgmt;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean isPrePay;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long shutOffDateTime;
    @XmlElement(name = "DemandResponseProgram", namespace = "http://naesb.org/espi/customer")
    protected List<DemandResponseProgram> demandResponseProgram;
    @XmlElement(name = "PricingStructures", namespace = "http://naesb.org/espi/customer")
    protected List<PricingStructure> pricingStructures;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String currency;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected List<Status> futureStatus;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String agreementId;

    /**
     * Gets the value of the loadMgmt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadMgmt() {
        return loadMgmt;
    }

    /**
     * Sets the value of the loadMgmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadMgmt(String value) {
        this.loadMgmt = value;
    }

    /**
     * Gets the value of the isPrePay property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPrePay() {
        return isPrePay;
    }

    /**
     * Sets the value of the isPrePay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPrePay(Boolean value) {
        this.isPrePay = value;
    }

    /**
     * Gets the value of the shutOffDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getShutOffDateTime() {
        return shutOffDateTime;
    }

    /**
     * Sets the value of the shutOffDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setShutOffDateTime(Long value) {
        this.shutOffDateTime = value;
    }

    /**
     * Gets the value of the demandResponseProgram property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the demandResponseProgram property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDemandResponseProgram().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DemandResponseProgram }
     * 
     * 
     */
    public List<DemandResponseProgram> getDemandResponseProgram() {
        if (demandResponseProgram == null) {
            demandResponseProgram = new ArrayList<DemandResponseProgram>();
        }
        return this.demandResponseProgram;
    }

    /**
     * Gets the value of the pricingStructures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pricingStructures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPricingStructures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PricingStructure }
     * 
     * 
     */
    public List<PricingStructure> getPricingStructures() {
        if (pricingStructures == null) {
            pricingStructures = new ArrayList<PricingStructure>();
        }
        return this.pricingStructures;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the futureStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the futureStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFutureStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Status }
     * 
     * 
     */
    public List<Status> getFutureStatus() {
        if (futureStatus == null) {
            futureStatus = new ArrayList<Status>();
        }
        return this.futureStatus;
    }

    /**
     * Gets the value of the agreementId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementId() {
        return agreementId;
    }

    /**
     * Sets the value of the agreementId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementId(String value) {
        this.agreementId = value;
    }

}
