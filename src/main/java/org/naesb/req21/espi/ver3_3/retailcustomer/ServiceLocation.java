
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
 * A real estate location, commonly referred to as premises.
 * 
 * <p>Java class for ServiceLocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceLocation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}WorkLocation">
 *       &lt;sequence>
 *         &lt;element name="accessMethod" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="siteAccessProblem" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *         &lt;element name="needsInspection" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UsagePoints" type="{http://naesb.org/espi/customer}UsagePoints" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outageBlock" type="{http://naesb.org/espi/customer}String32" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLocation", namespace = "http://naesb.org/espi/customer", propOrder = {
    "accessMethod",
    "siteAccessProblem",
    "needsInspection",
    "usagePoints",
    "outageBlock"
})
public class ServiceLocation
    extends WorkLocation
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String accessMethod;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String siteAccessProblem;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected Boolean needsInspection;
    @XmlElement(name = "UsagePoints", namespace = "http://naesb.org/espi/customer")
    protected List<UsagePoints> usagePoints;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String outageBlock;

    /**
     * Gets the value of the accessMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessMethod() {
        return accessMethod;
    }

    /**
     * Sets the value of the accessMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessMethod(String value) {
        this.accessMethod = value;
    }

    /**
     * Gets the value of the siteAccessProblem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteAccessProblem() {
        return siteAccessProblem;
    }

    /**
     * Sets the value of the siteAccessProblem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteAccessProblem(String value) {
        this.siteAccessProblem = value;
    }

    /**
     * Gets the value of the needsInspection property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeedsInspection() {
        return needsInspection;
    }

    /**
     * Sets the value of the needsInspection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeedsInspection(Boolean value) {
        this.needsInspection = value;
    }

    /**
     * Gets the value of the usagePoints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usagePoints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsagePoints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UsagePoints }
     * 
     * 
     */
    public List<UsagePoints> getUsagePoints() {
        if (usagePoints == null) {
            usagePoints = new ArrayList<UsagePoints>();
        }
        return this.usagePoints;
    }

    /**
     * Gets the value of the outageBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutageBlock() {
        return outageBlock;
    }

    /**
     * Sets the value of the outageBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutageBlock(String value) {
        this.outageBlock = value;
    }

}
