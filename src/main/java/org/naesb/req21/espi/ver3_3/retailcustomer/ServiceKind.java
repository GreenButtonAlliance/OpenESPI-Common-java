
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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceKind.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ServiceKind">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="electricity"/>
 *     &lt;enumeration value="gas"/>
 *     &lt;enumeration value="water"/>
 *     &lt;enumeration value="time"/>
 *     &lt;enumeration value="heat"/>
 *     &lt;enumeration value="refuse"/>
 *     &lt;enumeration value="sewerage"/>
 *     &lt;enumeration value="rates"/>
 *     &lt;enumeration value="tvLicence"/>
 *     &lt;enumeration value="internet"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ServiceKind", namespace = "http://naesb.org/espi/customer")
@XmlEnum
public enum ServiceKind {


    /**
     * Electricity service.
     * 
     */
    @XmlEnumValue("electricity")
    ELECTRICITY("electricity"),

    /**
     * Gas service.
     * 
     */
    @XmlEnumValue("gas")
    GAS("gas"),

    /**
     * Water service.
     * 
     */
    @XmlEnumValue("water")
    WATER("water"),

    /**
     * Time service.
     * 
     */
    @XmlEnumValue("time")
    TIME("time"),

    /**
     * Heat service.
     * 
     */
    @XmlEnumValue("heat")
    HEAT("heat"),

    /**
     * Refuse (waster) service.
     * 
     */
    @XmlEnumValue("refuse")
    REFUSE("refuse"),

    /**
     * Sewerage service.
     * 
     */
    @XmlEnumValue("sewerage")
    SEWERAGE("sewerage"),

    /**
     * Rates (e.g. tax, charge, toll, duty, tariff, etc.) service.
     * 
     */
    @XmlEnumValue("rates")
    RATES("rates"),

    /**
     * TV license service.
     * 
     */
    @XmlEnumValue("tvLicence")
    TV_LICENCE("tvLicence"),

    /**
     * Internet service.
     * 
     */
    @XmlEnumValue("internet")
    INTERNET("internet"),

    /**
     * Other kind of service.
     * 
     */
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    ServiceKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ServiceKind fromValue(String v) {
        for (ServiceKind c: ServiceKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
