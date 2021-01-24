
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

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * [extension] Contains attributes related to the configuration of the time service.
 * 
 * <p>Java class for TimeConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="dstEndRule" type="{http://naesb.org/espi/customer}DstRuleType"/>
 *         &lt;element name="dstOffset" type="{http://naesb.org/espi/customer}TimeType"/>
 *         &lt;element name="dstStartRule" type="{http://naesb.org/espi/customer}DstRuleType"/>
 *         &lt;element name="tzOffset" type="{http://naesb.org/espi/customer}TimeType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeConfiguration", namespace = "http://naesb.org/espi/customer", propOrder = {
    "dstEndRule",
    "dstOffset",
    "dstStartRule",
    "tzOffset"
})
public class TimeConfiguration
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] dstEndRule;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected long dstOffset;
    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] dstStartRule;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected long tzOffset;

    /**
     * Gets the value of the dstEndRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getDstEndRule() {
        return dstEndRule;
    }

    /**
     * Sets the value of the dstEndRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDstEndRule(byte[] value) {
        this.dstEndRule = value;
    }

    /**
     * Gets the value of the dstOffset property.
     * 
     */
    public long getDstOffset() {
        return dstOffset;
    }

    /**
     * Sets the value of the dstOffset property.
     * 
     */
    public void setDstOffset(long value) {
        this.dstOffset = value;
    }

    /**
     * Gets the value of the dstStartRule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getDstStartRule() {
        return dstStartRule;
    }

    /**
     * Sets the value of the dstStartRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDstStartRule(byte[] value) {
        this.dstStartRule = value;
    }

    /**
     * Gets the value of the tzOffset property.
     * 
     */
    public long getTzOffset() {
        return tzOffset;
    }

    /**
     * Sets the value of the tzOffset property.
     * 
     */
    public void setTzOffset(long value) {
        this.tzOffset = value;
    }

}
