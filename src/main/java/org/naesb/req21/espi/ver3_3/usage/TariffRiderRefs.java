
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * [extension] References to associated Tariff Riders.
 * 
 * <p>Java class for TariffRiderRefs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TariffRiderRefs">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="tariffRiderRef" type="{http://naesb.org/espi}TariffRiderRef" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TariffRiderRefs", namespace = "http://naesb.org/espi", propOrder = {
    "tariffRiderRef"
})
public class TariffRiderRefs
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi", required = true)
    protected List<TariffRiderRef> tariffRiderRef;

    /**
     * Gets the value of the tariffRiderRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tariffRiderRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTariffRiderRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TariffRiderRef }
     * 
     * 
     */
    public List<TariffRiderRef> getTariffRiderRef() {
        if (tariffRiderRef == null) {
            tariffRiderRef = new ArrayList<TariffRiderRef>();
        }
        return this.tariffRiderRef;
    }

}
