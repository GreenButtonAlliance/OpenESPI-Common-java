
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
 * <p>Java class for TokenType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TokenType">
 *   &lt;restriction base="{http://naesb.org/espi}String32">
 *     &lt;enumeration value="Bearer"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TokenType", namespace = "http://naesb.org/espi")
@XmlEnum
public enum TokenType {


    /**
     * A security token with the property that any party in possession of the token (a "bearer") can use the token in any way that any other party in possession of it can.  Using a bearer token does not require a bearer to prove possession of cryptographic key material (proof-of-possession) (RFC6750 Section 1.2).
     * 
     */
    @XmlEnumValue("Bearer")
    BEARER("Bearer");
    private final String value;

    TokenType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TokenType fromValue(String v) {
        for (TokenType c: TokenType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
