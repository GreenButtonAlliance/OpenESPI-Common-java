
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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApnodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApnodeType">
 *   &lt;restriction base="{http://naesb.org/espi}String8">
 *     &lt;enumeration value="AG"/>
 *     &lt;enumeration value="CPZ"/>
 *     &lt;enumeration value="DPZ"/>
 *     &lt;enumeration value="LAP"/>
 *     &lt;enumeration value="TH"/>
 *     &lt;enumeration value="SYS"/>
 *     &lt;enumeration value="CA"/>
 *     &lt;enumeration value="DCA"/>
 *     &lt;enumeration value="GA"/>
 *     &lt;enumeration value="GH"/>
 *     &lt;enumeration value="EHV"/>
 *     &lt;enumeration value="ZN"/>
 *     &lt;enumeration value="INT"/>
 *     &lt;enumeration value="BUS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApnodeType", namespace = "http://naesb.org/espi")
@XmlEnum
public enum ApnodeType {


    /**
     * Aggregated Generation
     * 
     */
    AG,

    /**
     * Custom Price Zone
     * 
     */
    CPZ,

    /**
     * Default Price Zone
     * 
     */
    DPZ,

    /**
     * Load Aggregation Point
     * 
     */
    LAP,

    /**
     * Trading  Hub
     * 
     */
    TH,

    /**
     * System Zone
     * 
     */
    SYS,

    /**
     * Control Area
     * 
     */
    CA,

    /**
     * Designated Congestion Area
     * 
     */
    DCA,

    /**
     * generic aggregation
     * 
     */
    GA,

    /**
     * generic hub
     * 
     */
    GH,

    /**
     *  500 kV - Extra High Voltage aggregate price nodes
     * 
     */
    EHV,

    /**
     * Zone
     * 
     */
    ZN,

    /**
     * Interface
     * 
     */
    INT,

    /**
     * Bus
     * 
     */
    BUS;

    public String value() {
        return name();
    }

    public static ApnodeType fromValue(String v) {
        return valueOf(v);
    }

}
