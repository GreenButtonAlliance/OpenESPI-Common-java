
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

package org.naesb.req21.espi.ver3_3.usage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * [extension] Reference to an AggregateNode (could be a load aggregation point which is a specialization of AggregateNode).
 * 
 * <p>Java class for AggregateNodeRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AggregateNodeRef">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="anodeType" type="{http://naesb.org/espi}AnodeType"/>
 *         &lt;element name="ref" type="{http://naesb.org/espi}String256"/>
 *         &lt;element name="startEffectiveDate" type="{http://naesb.org/espi}TimeType" minOccurs="0"/>
 *         &lt;element name="endEffectiveDate" type="{http://naesb.org/espi}TimeType" minOccurs="0"/>
 *         &lt;element name="pnodeRef" type="{http://naesb.org/espi}PnodeRef" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggregateNodeRef", namespace = "http://naesb.org/espi", propOrder = {
    "anodeType",
    "ref",
    "startEffectiveDate",
    "endEffectiveDate",
    "pnodeRef"
})
public class AggregateNodeRef
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "string")
    protected AnodeType anodeType;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String ref;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long startEffectiveDate;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected Long endEffectiveDate;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected List<PnodeRef> pnodeRef;

    /**
     * Gets the value of the anodeType property.
     * 
     * @return
     *     possible object is
     *     {@link AnodeType }
     *     
     */
    public AnodeType getAnodeType() {
        return anodeType;
    }

    /**
     * Sets the value of the anodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnodeType }
     *     
     */
    public void setAnodeType(AnodeType value) {
        this.anodeType = value;
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

    /**
     * Gets the value of the pnodeRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pnodeRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPnodeRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PnodeRef }
     * 
     * 
     */
    public List<PnodeRef> getPnodeRef() {
        if (pnodeRef == null) {
            pnodeRef = new ArrayList<PnodeRef>();
        }
        return this.pnodeRef;
    }

}
