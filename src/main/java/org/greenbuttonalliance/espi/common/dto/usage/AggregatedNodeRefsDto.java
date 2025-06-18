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
 * AggregatedNodeRefs DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a collection of aggregated node references for a UsagePoint.
 * Used to specify multiple aggregated pricing/load zones that may apply.
 * 
 * Follows NAESB ESPI specification for AggregatedNodeRefs complex type.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "AggregatedNodeRefs", namespace = "http://naesb.org/espi")
public record AggregatedNodeRefsDto(
    
    List<AggregatedNodeRefDto> aggregatedNodeRefs
) {
    
    /**
     * List of aggregated node references.
     * Each reference contains aggregated node ID and validity period.
     */
    @XmlElement(name = "AggregatedNodeRef")
    public List<AggregatedNodeRefDto> getAggregatedNodeRefs() {
        return aggregatedNodeRefs;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public AggregatedNodeRefsDto() {
        this(new ArrayList<>());
    }
    
    /**
     * Constructor with individual aggregated node references.
     * 
     * @param refs variable number of AggregatedNodeRef objects
     */
    public AggregatedNodeRefsDto(AggregatedNodeRefDto... refs) {
        this(Arrays.asList(refs));
    }
    
    /**
     * Compact constructor to ensure non-null list.
     */
    public AggregatedNodeRefsDto(List<AggregatedNodeRefDto> aggregatedNodeRefs) {
        this.aggregatedNodeRefs = aggregatedNodeRefs != null ? aggregatedNodeRefs : new ArrayList<>();
    }
    
    /**
     * Gets the number of aggregated node references.
     * 
     * @return count of aggregated node references
     */
    public int size() {
        return aggregatedNodeRefs.size();
    }
    
    /**
     * Checks if there are any aggregated node references.
     * 
     * @return true if no aggregated node references are present
     */
    public boolean isEmpty() {
        return aggregatedNodeRefs.isEmpty();
    }
    
    /**
     * Gets all currently valid aggregated node references.
     * 
     * @return list of valid aggregated node references
     */
    public List<AggregatedNodeRefDto> getValidRefs() {
        return aggregatedNodeRefs.stream()
            .filter(AggregatedNodeRefDto::isValid)
            .toList();
    }
    
    /**
     * Gets aggregated node references by reference ID.
     * 
     * @param ref reference ID to filter by
     * @return list of aggregated node references with matching ID
     */
    public List<AggregatedNodeRefDto> getRefsByRef(String ref) {
        return aggregatedNodeRefs.stream()
            .filter(aggNodeRef -> ref.equals(aggNodeRef.ref()))
            .toList();
    }
    
    /**
     * Checks if a specific aggregated node reference exists.
     * 
     * @param ref aggregated node reference to check
     * @return true if aggregated node reference exists
     */
    public boolean hasRef(String ref) {
        return aggregatedNodeRefs.stream()
            .anyMatch(aggNodeRef -> ref.equals(aggNodeRef.ref()));
    }
    
    /**
     * Gets a summary description of all aggregated node references.
     * 
     * @return formatted summary string
     */
    public String getSummary() {
        if (isEmpty()) {
            return "No aggregated node references";
        }
        
        long validCount = getValidRefs().size();
        return String.format("%d aggregated node reference(s): %d currently valid", 
                           size(), validCount);
    }
    
    /**
     * Creates AggregatedNodeRefs with common load zone references.
     * 
     * @return AggregatedNodeRefs with typical load zone references
     */
    public static AggregatedNodeRefsDto createLoadZoneRefs() {
        // Create pricing node references for the load zones
        PnodeRefDto northPnode = PnodeRefDto.createCurrent("HUB", "NORTH_HUB_PNODE");
        PnodeRefDto southPnode = PnodeRefDto.createCurrent("HUB", "SOUTH_HUB_PNODE");
        
        return new AggregatedNodeRefsDto(
            AggregatedNodeRefDto.createCurrent("LOAD_ZONE", "LOAD_ZONE_NORTH", northPnode),
            AggregatedNodeRefDto.createCurrent("LOAD_ZONE", "LOAD_ZONE_SOUTH", southPnode)
        );
    }
}