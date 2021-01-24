
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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParticipationCategoryMPM.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParticipationCategoryMPM">
 *   &lt;restriction base="{http://naesb.org/espi}String4">
 *     &lt;enumeration value="Y"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="L"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParticipationCategoryMPM", namespace = "http://naesb.org/espi")
@XmlEnum
public enum ParticipationCategoryMPM {


    /**
     * Participates in both LMPM and SMPM
     * 
     */
    Y,

    /**
     * Not included in LMP price measures
     * 
     */
    N,

    /**
     * Participates in SMPM price measures
     * 
     */
    S,

    /**
     * Participates in LMPM price measures
     * 
     */
    L;

    public String value() {
        return name();
    }

    public static ParticipationCategoryMPM fromValue(String v) {
        return valueOf(v);
    }

}
