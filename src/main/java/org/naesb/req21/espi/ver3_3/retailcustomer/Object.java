
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
import java.util.ArrayList;
import java.util.List;


/**
 * Superclass of all object classes to allow extensions. Inheritance from Object provides an inherent extension mechanism permitting custom data to be included in a separate namespace.
 * 
 * <p>Java class for Object complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Object">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Object", namespace = "http://naesb.org/espi/customer", propOrder = {
    "extension"
})
@XmlSeeAlso({
    ProgramDateIdMapping.class,
    TelephoneNumber.class,
    AccountNotification.class,
    TownDetail.class,
    MeterMultiplier.class,
    PositionPoint.class,
    AcceptanceTest.class,
    LifecycleDate.class,
    UsagePoints.class,
    Status.class,
    Priority.class,
    DateTimeInterval.class,
    ElectronicAddress.class,
    BatchItemInfo.class,
    StreetAddress.class,
    StreetDetail.class,
    SummaryMeasurement.class,
    IdentifiedObject.class
})
public class Object {

    @XmlElement(namespace = "http://naesb.org/espi/customer")
    protected List<java.lang.Object> extension;

    /**
     * Gets the value of the extension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.Object }
     * 
     * 
     */
    public List<java.lang.Object> getExtension() {
        if (extension == null) {
            extension = new ArrayList<java.lang.Object>();
        }
        return this.extension;
    }

}
