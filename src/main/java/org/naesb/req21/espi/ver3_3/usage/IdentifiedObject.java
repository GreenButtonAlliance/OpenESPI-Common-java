
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


/**
 * This is a root class to provide common naming attributes for all classes needing naming attributes
 * 
 * <p>Java class for IdentifiedObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentifiedObject">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="batchItemInfo" type="{http://naesb.org/espi}BatchItemInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentifiedObject", namespace = "http://naesb.org/espi", propOrder = {
    "batchItemInfo"
})
@XmlSeeAlso({
    IntervalBlock.class,
    ElectricPowerQualitySummary.class,
    UsagePoint.class,
    TimeConfiguration.class,
    UsageSummary.class,
    Authorization.class,
    ReadingType.class,
    ProgramIdMappings.class,
    MeterReading.class,
    ElectricPowerUsageSummary.class,
    ApplicationInformation.class
})
public class IdentifiedObject
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi")
    protected BatchItemInfo batchItemInfo;

    /**
     * Gets the value of the batchItemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BatchItemInfo }
     *     
     */
    public BatchItemInfo getBatchItemInfo() {
        return batchItemInfo;
    }

    /**
     * Sets the value of the batchItemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchItemInfo }
     *     
     */
    public void setBatchItemInfo(BatchItemInfo value) {
        this.batchItemInfo = value;
    }

}
