
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
 * [extension] Single customer energy efficiency program date mapping
 * 
 * <p>Java class for ProgramDateIdMapping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProgramDateIdMapping">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}Object">
 *       &lt;sequence>
 *         &lt;element name="programDateType" type="{http://naesb.org/espi/customer}ProgramDateKind"/>
 *         &lt;element name="code" type="{http://naesb.org/espi/customer}String64"/>
 *         &lt;element name="name" type="{http://naesb.org/espi/customer}String256"/>
 *         &lt;element name="note" type="{http://naesb.org/espi/customer}String256" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramDateIdMapping", namespace = "http://naesb.org/espi/customer", propOrder = {
    "programDateType",
    "code",
    "name",
    "note"
})
public class ProgramDateIdMapping
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true)
    protected String programDateType;
    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true)
    protected String code;
    @XmlElement(namespace = "http://naesb.org/espi/customer", required = true)
    protected String name;
    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected String note;

    /**
     * Gets the value of the programDateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramDateType() {
        return programDateType;
    }

    /**
     * Sets the value of the programDateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramDateType(String value) {
        this.programDateType = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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

}
