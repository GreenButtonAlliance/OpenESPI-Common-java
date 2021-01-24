
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
 * Interval of date and time. End is not included because it can be derived from the start and the duration.
 * 
 * <p>Java class for DateTimeInterval complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DateTimeInterval">
 *   &lt;complexContent>
 *     &lt;extension base="{http://naesb.org/espi}Object">
 *       &lt;sequence>
 *         &lt;element name="duration" type="{http://naesb.org/espi}UInt32"/>
 *         &lt;element name="start" type="{http://naesb.org/espi}TimeType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeInterval", namespace = "http://naesb.org/espi", propOrder = {
    "duration",
    "start"
})
public class DateTimeInterval
    extends Object
{

    @XmlElement(namespace = "http://naesb.org/espi")
    @XmlSchemaType(name = "unsignedInt")
    protected long duration;
    @XmlElement(namespace = "http://naesb.org/espi")
    protected long start;

    /**
     * Gets the value of the duration property.
     * 
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     */
    public void setDuration(long value) {
        this.duration = value;
    }

    /**
     * Gets the value of the start property.
     * 
     */
    public long getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     */
    public void setStart(long value) {
        this.start = value;
    }

}
