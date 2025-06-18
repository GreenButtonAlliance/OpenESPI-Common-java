/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.dto.usage;

import jakarta.xml.bind.annotation.*;

/**
 * PnodeRef DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a reference to a pricing node in the electrical grid.
 * Used within UsagePoint to specify pricing locations for energy costs.
 * 
 * Part of the NAESB ESPI UsagePoint structure for pricing node references.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "PnodeRef", namespace = "http://naesb.org/espi", propOrder = {
    "apnodeType", "ref", "startEffectiveDate", "endEffectiveDate"
})
public record PnodeRefDto(
    
    String apnodeType,
    String ref,
    Long startEffectiveDate,
    Long endEffectiveDate
) {
    
    /**
     * Type of the aggregated pricing node.
     * Indicates the category or classification of the pricing node.
     */
    @XmlElement(name = "apnodeType")
    public String getApnodeType() {
        return apnodeType;
    }
    
    /**
     * Reference to the pricing node identifier.
     */
    @XmlElement(name = "ref")
    public String getRef() {
        return ref;
    }
    
    /**
     * Start effective date for the pricing node reference validity.
     * Stored as epoch seconds (TimeType in ESPI).
     */
    @XmlElement(name = "startEffectiveDate")
    public Long getStartEffectiveDate() {
        return startEffectiveDate;
    }
    
    /**
     * End effective date for the pricing node reference validity.
     * Stored as epoch seconds (TimeType in ESPI).
     */
    @XmlElement(name = "endEffectiveDate")
    public Long getEndEffectiveDate() {
        return endEffectiveDate;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public PnodeRefDto() {
        this(null, null, null, null);
    }
    
    /**
     * Constructor with pricing node reference and type.
     */
    public PnodeRefDto(String apnodeType, String ref) {
        this(apnodeType, ref, null, null);
    }
    
    /**
     * Checks if this pricing node reference is currently valid.
     * 
     * @return true if valid for current time
     */
    public boolean isValid() {
        long currentTime = System.currentTimeMillis() / 1000;
        return (startEffectiveDate == null || startEffectiveDate <= currentTime) && 
               (endEffectiveDate == null || endEffectiveDate >= currentTime);
    }
    
    /**
     * Creates a PnodeRef with current validity period.
     * 
     * @param apnodeType the type of pricing node
     * @param ref pricing node reference
     * @return PnodeRef valid from now
     */
    public static PnodeRefDto createCurrent(String apnodeType, String ref) {
        long currentTime = System.currentTimeMillis() / 1000;
        return new PnodeRefDto(apnodeType, ref, currentTime, null);
    }
    
    /**
     * Creates a PnodeRef with specified validity period.
     * 
     * @param apnodeType the type of pricing node
     * @param ref pricing node reference
     * @param startEffectiveDate start of validity period (epoch seconds)
     * @param endEffectiveDate end of validity period (epoch seconds, null for indefinite)
     * @return PnodeRef with specified validity
     */
    public static PnodeRefDto create(String apnodeType, String ref, Long startEffectiveDate, Long endEffectiveDate) {
        return new PnodeRefDto(apnodeType, ref, startEffectiveDate, endEffectiveDate);
    }
}