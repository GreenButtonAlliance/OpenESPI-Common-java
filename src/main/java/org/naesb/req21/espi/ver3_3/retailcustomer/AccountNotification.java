
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
 * [extension] Customer action notification (e.g., delinquency, move in, move out)
 * 
 * <p>Java class for AccountNotification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountNotification">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="methodKind" type="{http://naesb.org/espi/customer}NotificationMethodKind"/>
 *         &lt;element name="time" type="{http://naesb.org/espi/customer}TimeType"/>
 *         &lt;element name="note" type="{http://naesb.org/espi/customer}String256"/>
 *         &lt;element name="customerNotificationKind" type="{http://naesb.org/espi/customer}String256"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountNotification", namespace = "http://naesb.org/espi/customer", propOrder = {
    "methodKind",
    "time",
    "note",
    "customerNotificationKind"
})
public class AccountNotification
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true)
    protected String methodKind;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected long time;
    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true)
    protected String note;
    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true)
    protected String customerNotificationKind;

    /**
     * Gets the value of the methodKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodKind() {
        return methodKind;
    }

    /**
     * Sets the value of the methodKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodKind(String value) {
        this.methodKind = value;
    }

    /**
     * Gets the value of the time property.
     * 
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     */
    public void setTime(long value) {
        this.time = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the customerNotificationKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerNotificationKind() {
        return customerNotificationKind;
    }

    /**
     * Sets the value of the customerNotificationKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerNotificationKind(String value) {
        this.customerNotificationKind = value;
    }

}
