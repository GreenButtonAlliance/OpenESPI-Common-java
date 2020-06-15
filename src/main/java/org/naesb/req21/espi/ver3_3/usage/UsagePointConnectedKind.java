
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
 * <p>Java class for UsagePointConnectedKind.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UsagePointConnectedKind">
 *   &lt;restriction base="{http://naesb.org/espi}String32">
 *     &lt;enumeration value="connected"/>
 *     &lt;enumeration value="logicallyDisconnected"/>
 *     &lt;enumeration value="physicallyDisconnected"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UsagePointConnectedKind", namespace = "http://naesb.org/espi")
@XmlEnum
public enum UsagePointConnectedKind {


    /**
     * The usage point is connected to the network and able to receive or send the applicable commodity (electricity, gas, water, etc.).
     * 
     */
    @XmlEnumValue("connected")
    CONNECTED("connected"),

    /**
     * The usage point has been disconnected through operation of a disconnect function within the meter present at the usage point.  The usage point is unable to receive or send the applicable commodity (electricity, gas, water, etc.)  A logical disconnect can often be achieved without utilising a field crew.
     * 
     */
    @XmlEnumValue("logicallyDisconnected")
    LOGICALLY_DISCONNECTED("logicallyDisconnected"),

    /**
     * The usage point has been disconnected from the network at a point upstream of the meter. The usage point is unable to receive or send the applicable commodity (electricity, gas, water, etc.). A physical disconnect is often achieved by utilising a field crew.
     * 
     */
    @XmlEnumValue("physicallyDisconnected")
    PHYSICALLY_DISCONNECTED("physicallyDisconnected");
    private final String value;

    UsagePointConnectedKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsagePointConnectedKind fromValue(String v) {
        for (UsagePointConnectedKind c: UsagePointConnectedKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
