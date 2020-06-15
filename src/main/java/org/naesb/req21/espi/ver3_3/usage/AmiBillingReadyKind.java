
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
 * <p>Java class for AmiBillingReadyKind.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AmiBillingReadyKind">
 *   &lt;restriction base="{http://naesb.org/espi}String32">
 *     &lt;enumeration value="amiCapable"/>
 *     &lt;enumeration value="amiDisabled"/>
 *     &lt;enumeration value="billingApproved"/>
 *     &lt;enumeration value="enabled"/>
 *     &lt;enumeration value="nonAmi"/>
 *     &lt;enumeration value="nonMetered"/>
 *     &lt;enumeration value="operable"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AmiBillingReadyKind", namespace = "http://naesb.org/espi")
@XmlEnum
public enum AmiBillingReadyKind {


    /**
     * Usage point is equipped with an AMI capable meter that is not yet currently equipped with a communications module.
     * 
     */
    @XmlEnumValue("amiCapable")
    AMI_CAPABLE("amiCapable"),

    /**
     * Usage point is equipped with an AMI capable meter; however, the AMI functionality has been disabled or is not being used.
     * 
     */
    @XmlEnumValue("amiDisabled")
    AMI_DISABLED("amiDisabled"),

    /**
     * Usage point is equipped with an operating AMI capable meter and accuracy has been certified for billing purposes.
     * 
     */
    @XmlEnumValue("billingApproved")
    BILLING_APPROVED("billingApproved"),

    /**
     * Usage point is equipped with an AMI capable meter having communications capability.
     * 
     */
    @XmlEnumValue("enabled")
    ENABLED("enabled"),

    /**
     * Usage point is equipped with a non AMI capable meter.
     * 
     */
    @XmlEnumValue("nonAmi")
    NON_AMI("nonAmi"),

    /**
     * Usage point is not currently equipped with a meter.
     * 
     */
    @XmlEnumValue("nonMetered")
    NON_METERED("nonMetered"),

    /**
     * Usage point is equipped with an AMI capable meter that is functioning and communicating with the AMI network.
     * 
     */
    @XmlEnumValue("operable")
    OPERABLE("operable");
    private final String value;

    AmiBillingReadyKind(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AmiBillingReadyKind fromValue(String v) {
        for (AmiBillingReadyKind c: AmiBillingReadyKind.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
