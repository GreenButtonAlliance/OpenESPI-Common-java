
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
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tOUorCPPorConsumptionTier.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tOUorCPPorConsumptionTier">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="tou"/>
 *     &lt;enumeration value="cpp"/>
 *     &lt;enumeration value="consumptiontier"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tOUorCPPorConsumptionTier", namespace = "http://naesb.org/espi")
@XmlEnum
public enum TOUorCPPorConsumptionTier {


    /**
     * Time of Use
     * 
     */
    @XmlEnumValue("tou")
    TOU("tou"),

    /**
     * Consumption Peak Period
     * 
     */
    @XmlEnumValue("cpp")
    CPP("cpp"),

    /**
     * Consumption Tier
     * 
     */
    @XmlEnumValue("consumptiontier")
    CONSUMPTIONTIER("consumptiontier");
    private final String value;

    TOUorCPPorConsumptionTier(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TOUorCPPorConsumptionTier fromValue(String v) {
        for (TOUorCPPorConsumptionTier c: TOUorCPPorConsumptionTier.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
