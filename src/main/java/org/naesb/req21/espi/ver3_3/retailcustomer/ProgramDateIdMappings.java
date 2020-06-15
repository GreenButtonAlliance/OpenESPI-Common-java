
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
 * [extension] Collection of all customer Energy Efficiency programs
 * 
 * <p>Java class for ProgramDateIdMappings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProgramDateIdMappings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi/customer}IdentifiedObject">
 *       &lt;sequence>
 *         &lt;element name="programDateIdMapping" type="{http://naesb.org/espi/customer}ProgramDateIdMapping" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramDateIdMappings", namespace = "http://naesb.org/espi/customer", propOrder = {
    "programDateIdMapping"
})
public class ProgramDateIdMappings
    extends IdentifiedObject
{

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected ProgramDateIdMapping programDateIdMapping;

    /**
     * Gets the value of the programDateIdMapping property.
     * 
     * @return
     *     possible object is
     *     {@link ProgramDateIdMapping }
     *     
     */
    public ProgramDateIdMapping getProgramDateIdMapping() {
        return programDateIdMapping;
    }

    /**
     * Sets the value of the programDateIdMapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProgramDateIdMapping }
     *     
     */
    public void setProgramDateIdMapping(ProgramDateIdMapping value) {
        this.programDateIdMapping = value;
    }

}
