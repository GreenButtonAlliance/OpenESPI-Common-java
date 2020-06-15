
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

package org.naesb.req21.espi.ver3_3.usage;

import javax.xml.bind.annotation.*;


/**
 * [extension] Reference to a Pnode.
 * 
 * <p>Java class for PnodeRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PnodeRef">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="apnodeType" type="{http://naesb.org/espi}ApnodeType"/>
 *         &lt;element name="ref" type="{http://naesb.org/espi}String256"/>
 *         &lt;element name="startEffectiveDate" type="{http://naesb.org/espi}TimeType" minOccurs="0"/>
 *         &lt;element name="endEffectiveDate" type="{http://naesb.org/espi}TimeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PnodeRef", namespace = "http://naesb.org/espi", propOrder = {
    "apnodeType",
    "ref",
    "startEffectiveDate",
    "endEffectiveDate"
})
public class PnodeRef
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "string")
    protected ApnodeType apnodeType;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String ref;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long startEffectiveDate;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long endEffectiveDate;

    /**
     * Gets the value of the apnodeType property.
     * 
     * @return
     *     possible object is
     *     {@link ApnodeType }
     *     
     */
    public ApnodeType getApnodeType() {
        return apnodeType;
    }

    /**
     * Sets the value of the apnodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApnodeType }
     *     
     */
    public void setApnodeType(ApnodeType value) {
        this.apnodeType = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Gets the value of the startEffectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartEffectiveDate() {
        return startEffectiveDate;
    }

    /**
     * Sets the value of the startEffectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartEffectiveDate(Long value) {
        this.startEffectiveDate = value;
    }

    /**
     * Gets the value of the endEffectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndEffectiveDate() {
        return endEffectiveDate;
    }

    /**
     * Sets the value of the endEffectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndEffectiveDate(Long value) {
        this.endEffectiveDate = value;
    }

}
