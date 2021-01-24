
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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TokenEndPointMethod.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TokenEndPointMethod">
 *   &lt;restriction base="{http://naesb.org/espi}String32">
 *     &lt;enumeration value="client_secret_basic"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TokenEndPointMethod", namespace = "http://naesb.org/espi")
@XmlEnum
public enum TokenEndPointMethod {


    /**
     * [extension] Indicates the client uses HTTP Basic authentication (RFC 6749 Section 2.3.1).
     * 
     */
    @XmlEnumValue("client_secret_basic")
    CLIENT_SECRET_BASIC("client_secret_basic");
    private final String value;

    TokenEndPointMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TokenEndPointMethod fromValue(String v) {
        for (TokenEndPointMethod c: TokenEndPointMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
