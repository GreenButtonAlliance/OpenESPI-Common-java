
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
 * <p>Java class for GrantType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GrantType">
 *   &lt;restriction base="{http://naesb.org/espi}String32">
 *     &lt;enumeration value="authorization_code"/>
 *     &lt;enumeration value="client_credentials"/>
 *     &lt;enumeration value="refresh_token"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GrantType", namespace = "http://naesb.org/espi")
@XmlEnum
public enum GrantType {


    /**
     * OAuth 2.0 Authorization Code Grant flow (RFC 6749 Section 4.1).
     * 
     */
    @XmlEnumValue("authorization_code")
    AUTHORIZATION_CODE("authorization_code"),

    /**
     * OAuth 2.0 Client Credentials Grant flow (RFC 6749 Section 4.4).
     * 
     */
    @XmlEnumValue("client_credentials")
    CLIENT_CREDENTIALS("client_credentials"),

    /**
     * OAuth 2.0 Refresh Token flow (RFC 6749 Section 6.).
     * 
     */
    @XmlEnumValue("refresh_token")
    REFRESH_TOKEN("refresh_token");
    private final String value;

    GrantType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GrantType fromValue(String v) {
        for (GrantType c: GrantType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
