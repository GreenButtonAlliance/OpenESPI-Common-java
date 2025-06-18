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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PnodeRefs DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a collection of pricing node references for a UsagePoint.
 * Used to specify multiple pricing locations that may apply to energy costs.
 * 
 * Follows NAESB ESPI specification for PnodeRefs complex type.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "PnodeRefs", namespace = "http://naesb.org/espi")
public record PnodeRefsDto(
    
    List<PnodeRefDto> pnodeRefs
) {
    
    /**
     * List of pricing node references.
     * Each reference contains pricing node ID and validity period.
     */
    @XmlElement(name = "PnodeRef")
    public List<PnodeRefDto> getPnodeRefs() {
        return pnodeRefs;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public PnodeRefsDto() {
        this(new ArrayList<>());
    }
    
    /**
     * Constructor with individual pricing node references.
     * 
     * @param refs variable number of PnodeRef objects
     */
    public PnodeRefsDto(PnodeRefDto... refs) {
        this(Arrays.asList(refs));
    }
    
    /**
     * Compact constructor to ensure non-null list.
     */
    public PnodeRefsDto(List<PnodeRefDto> pnodeRefs) {
        this.pnodeRefs = pnodeRefs != null ? pnodeRefs : new ArrayList<>();
    }
    
    /**
     * Gets the number of pricing node references.
     * 
     * @return count of pricing node references
     */
    public int size() {
        return pnodeRefs.size();
    }
    
    /**
     * Checks if there are any pricing node references.
     * 
     * @return true if no pricing node references are present
     */
    public boolean isEmpty() {
        return pnodeRefs.isEmpty();
    }
    
    /**
     * Gets all currently valid pricing node references.
     * 
     * @return list of valid pricing node references
     */
    public List<PnodeRefDto> getValidRefs() {
        return pnodeRefs.stream()
            .filter(PnodeRefDto::isValid)
            .toList();
    }
    
    /**
     * Gets pricing node references by reference ID.
     * 
     * @param ref reference ID to filter by
     * @return list of pricing node references with matching ID
     */
    public List<PnodeRefDto> getRefsByRef(String ref) {
        return pnodeRefs.stream()
            .filter(pnodeRef -> ref.equals(pnodeRef.ref()))
            .toList();
    }
    
    /**
     * Checks if a specific pricing node reference exists.
     * 
     * @param ref pricing node reference to check
     * @return true if pricing node reference exists
     */
    public boolean hasRef(String ref) {
        return pnodeRefs.stream()
            .anyMatch(pnodeRef -> ref.equals(pnodeRef.ref()));
    }
    
    /**
     * Gets a summary description of all pricing node references.
     * 
     * @return formatted summary string
     */
    public String getSummary() {
        if (isEmpty()) {
            return "No pricing node references";
        }
        
        long validCount = getValidRefs().size();
        return String.format("%d pnode reference(s): %d currently valid", 
                           size(), validCount);
    }
    
    /**
     * Creates PnodeRefs with common market pricing nodes.
     * 
     * @return PnodeRefs with typical market pricing references
     */
    public static PnodeRefsDto createMarketRefs() {
        return new PnodeRefsDto(
            PnodeRefDto.createCurrent("HUB", "MARKET_HUB_EAST"),
            PnodeRefDto.createCurrent("HUB", "MARKET_HUB_WEST")
        );
    }
}