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
 * TariffRiderRefs DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a collection of tariff rider references containing rate options
 * applied to the base tariff profile. Used within ServiceDeliveryPoint to 
 * describe customer-specific billing arrangements and rate riders.
 * 
 * Follows NAESB ESPI specification for TariffRiderRefs complex type.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TariffRiderRefs", namespace = "http://naesb.org/espi")
public record TariffRiderRefsDto(
    
    List<TariffRiderRefDto> tariffRiderRefs
) {
    
    /**
     * List of tariff rider references.
     * Each rider contains riderType, enrollmentStatus, and effectiveDate.
     * Supports multiple rate options per service delivery point.
     */
    @XmlElement(name = "TariffRiderRef")
    public List<TariffRiderRefDto> getTariffRiderRefs() {
        return tariffRiderRefs;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public TariffRiderRefsDto() {
        this(new ArrayList<>());
    }
    
    /**
     * Constructor with individual tariff riders.
     * 
     * @param riders variable number of TariffRiderRef objects
     */
    public TariffRiderRefsDto(TariffRiderRefDto... riders) {
        this(Arrays.asList(riders));
    }
    
    /**
     * Compact constructor to ensure non-null list.
     */
    public TariffRiderRefsDto(List<TariffRiderRefDto> tariffRiderRefs) {
        this.tariffRiderRefs = tariffRiderRefs != null ? tariffRiderRefs : new ArrayList<>();
    }
    
    /**
     * Gets the number of tariff riders.
     * 
     * @return count of tariff riders
     */
    public int size() {
        return tariffRiderRefs.size();
    }
    
    /**
     * Checks if there are any tariff riders.
     * 
     * @return true if no tariff riders are present
     */
    public boolean isEmpty() {
        return tariffRiderRefs.isEmpty();
    }
    
    /**
     * Gets all active (enrolled) tariff riders.
     * 
     * @return list of active tariff riders
     */
    public List<TariffRiderRefDto> getActiveRiders() {
        return tariffRiderRefs.stream()
            .filter(TariffRiderRefDto::isActive)
            .toList();
    }
    
    /**
     * Gets tariff riders by enrollment status.
     * 
     * @param status enrollment status to filter by
     * @return list of tariff riders with matching status
     */
    public List<TariffRiderRefDto> getRidersByStatus(String status) {
        return tariffRiderRefs.stream()
            .filter(rider -> status.equals(rider.enrollmentStatus()))
            .toList();
    }
    
    /**
     * Gets tariff riders by rider type.
     * 
     * @param riderType rider type to filter by
     * @return list of tariff riders with matching type
     */
    public List<TariffRiderRefDto> getRidersByType(String riderType) {
        return tariffRiderRefs.stream()
            .filter(rider -> riderType.equals(rider.riderType()))
            .toList();
    }
    
    /**
     * Checks if a specific rider type is enrolled.
     * 
     * @param riderType rider type to check
     * @return true if rider type is enrolled
     */
    public boolean hasEnrolledRider(String riderType) {
        return tariffRiderRefs.stream()
            .anyMatch(rider -> riderType.equals(rider.riderType()) && 
                              "ENROLLED".equals(rider.enrollmentStatus()));
    }
    
    /**
     * Gets a summary description of all riders.
     * 
     * @return formatted summary string
     */
    public String getSummary() {
        if (isEmpty()) {
            return "No tariff riders";
        }
        
        long enrolledCount = getRidersByStatus("ENROLLED").size();
        long pendingCount = getRidersByStatus("PENDING").size();
        
        return String.format("%d rider(s): %d enrolled, %d pending", 
                           size(), enrolledCount, pendingCount);
    }
    
    /**
     * Creates a TariffRiderRefs with common residential riders.
     * 
     * @return TariffRiderRefs with typical residential rate options
     */
    public static TariffRiderRefsDto createResidentialRiders() {
        return new TariffRiderRefsDto(
            TariffRiderRefDto.createTimeOfUse("ENROLLED", System.currentTimeMillis() / 1000),
            TariffRiderRefDto.createNetMetering("PENDING", System.currentTimeMillis() / 1000 + 86400)
        );
    }
}