
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
 * [extension] Reference to a Tariff Rider.
 * 
 * <p>Java class for TariffRiderRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TariffRiderRef">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="riderType" type="{http://naesb.org/espi}String256"/>
 *         &lt;element name="enrollmentStatus" type="{http://naesb.org/espi}EnrollmentStatus"/>
 *         &lt;element name="effectiveDate" type="{http://naesb.org/espi}TimeType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TariffRiderRef", namespace = "http://naesb.org/espi", propOrder = {
    "riderType",
    "enrollmentStatus",
    "effectiveDate"
})
public class TariffRiderRef
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected String riderType;
    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    @XmlSchemaType(name = "string")
    protected EnrollmentStatus enrollmentStatus;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected long effectiveDate;

    /**
     * Gets the value of the riderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiderType() {
        return riderType;
    }

    /**
     * Sets the value of the riderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiderType(String value) {
        this.riderType = value;
    }

    /**
     * Gets the value of the enrollmentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link EnrollmentStatus }
     *     
     */
    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    /**
     * Sets the value of the enrollmentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnrollmentStatus }
     *     
     */
    public void setEnrollmentStatus(EnrollmentStatus value) {
        this.enrollmentStatus = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     */
    public long getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     */
    public void setEffectiveDate(long value) {
        this.effectiveDate = value;
    }

}
