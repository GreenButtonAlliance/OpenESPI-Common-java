
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
 * <p>Java class for AnodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AnodeType">
 *   &lt;restriction base="{http://naesb.org/espi}String8">
 *     &lt;enumeration value="SYS"/>
 *     &lt;enumeration value="RUC"/>
 *     &lt;enumeration value="LFZ"/>
 *     &lt;enumeration value="REG"/>
 *     &lt;enumeration value="AGR"/>
 *     &lt;enumeration value="POD"/>
 *     &lt;enumeration value="ALR"/>
 *     &lt;enumeration value="LTAC"/>
 *     &lt;enumeration value="ACA"/>
 *     &lt;enumeration value="ASR"/>
 *     &lt;enumeration value="ECA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AnodeType", namespace = "http://naesb.org/espi")
@XmlEnum
public enum AnodeType {


    /**
     * System Zone/Region; 
     * 
     */
    SYS,

    /**
     * RUC Zone
     * 
     */
    RUC,

    /**
     * Load Forecast Zone
     * 
     */
    LFZ,

    /**
     * Market Energy/Ancillary Service Region; 
     * 
     */
    REG,

    /**
     * Aggregate Generation Resource; 
     * 
     */
    AGR,

    /**
     * Point of Delivery; 
     * 
     */
    POD,

    /**
     * Aggregate Load Resource; 
     * 
     */
    ALR,

    /**
     * Load TransmissionAccessCharge (TAC) Group;
     * 
     */
    LTAC,

    /**
     * Adjacent Control Area
     * 
     */
    ACA,

    /**
     * Aggregated System Resource
     * 
     */
    ASR,

    /**
     * Embedded Control Area
     * 
     */
    ECA;

    public String value() {
        return name();
    }

    public static AnodeType fromValue(String v) {
        return valueOf(v);
    }

}
