
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
import java.util.ArrayList;
import java.util.List;


/**
 * Assignment of a group of products and services purchased by the customer through a customer agreement, used as a mechanism for customer billing and payment. It contains common information from the various types of customer agreements to create billings (invoices) for a customer and receive payment.
 * 
 * <p>Java class for CustomerAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerAccount">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Document">
 *       &lt;sequence>
 *         &lt;element name="billingCycle" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="budgetBill" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="lastBillAmount" type="{http://naesb.org/espi/customer}Int48" minOccurs="0"/>
 *         &lt;element name="notifications" type="{http://naesb.org/espi/customer}AccountNotification" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactInfo" type="{http://naesb.org/espi/customer}Organisation" minOccurs="0"/>
 *         &lt;element name="accountId" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAccount", namespace = "http://naesb.org/espi/customer", propOrder = {
    "billingCycle",
    "budgetBill",
    "lastBillAmount",
    "notifications",
    "contactInfo",
    "accountId"
})
public class CustomerAccount
    extends Document
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String billingCycle;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String budgetBill;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Long lastBillAmount;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected List<AccountNotification> notifications;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Organisation contactInfo;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String accountId;

    /**
     * Gets the value of the billingCycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingCycle() {
        return billingCycle;
    }

    /**
     * Sets the value of the billingCycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingCycle(String value) {
        this.billingCycle = value;
    }

    /**
     * Gets the value of the budgetBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBudgetBill() {
        return budgetBill;
    }

    /**
     * Sets the value of the budgetBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBudgetBill(String value) {
        this.budgetBill = value;
    }

    /**
     * Gets the value of the lastBillAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLastBillAmount() {
        return lastBillAmount;
    }

    /**
     * Sets the value of the lastBillAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLastBillAmount(Long value) {
        this.lastBillAmount = value;
    }

    /**
     * Gets the value of the notifications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notifications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotifications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountNotification }
     * 
     * 
     */
    public List<AccountNotification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<AccountNotification>();
        }
        return this.notifications;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Organisation }
     *     
     */
    public Organisation getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organisation }
     *     
     */
    public void setContactInfo(Organisation value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the accountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the value of the accountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountId(String value) {
        this.accountId = value;
    }

}
