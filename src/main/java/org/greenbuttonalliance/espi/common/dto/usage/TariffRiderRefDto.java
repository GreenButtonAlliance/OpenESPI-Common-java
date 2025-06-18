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
 * TariffRiderRef DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a single tariff rider reference containing rate options applied 
 * to the base tariff profile, enrollment status, and effective date.
 * 
 * Part of the NAESB ESPI ServiceDeliveryPoint structure for customer billing arrangements.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TariffRiderRef", namespace = "http://naesb.org/espi", propOrder = {
    "riderType", "enrollmentStatus", "effectiveDate"
})
public record TariffRiderRefDto(
    
    String riderType,
    String enrollmentStatus,
    Long effectiveDate
) {
    
    /**
     * Rate options applied to the base tariff profile.
     * Examples: "TIME_OF_USE_PEAK", "NET_METERING", "DEMAND_RESPONSE", "GREEN_TARIFF"
     */
    @XmlElement(name = "riderType")
    public String getRiderType() {
        return riderType;
    }
    
    /**
     * Retail Customer's Tariff Rider enrollment status.
     * Examples: "ENROLLED", "PENDING", "CANCELLED", "SUSPENDED"
     */
    @XmlElement(name = "enrollmentStatus")
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }
    
    /**
     * Effective date of Retail Customer's Tariff Rider enrollment status.
     * Stored as epoch seconds (TimeType in ESPI).
     */
    @XmlElement(name = "effectiveDate")
    public Long getEffectiveDate() {
        return effectiveDate;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public TariffRiderRefDto() {
        this(null, null, null);
    }
    
    /**
     * Constructor with rider type and enrollment status.
     * Effective date defaults to current time.
     */
    public TariffRiderRefDto(String riderType, String enrollmentStatus) {
        this(riderType, enrollmentStatus, System.currentTimeMillis() / 1000);
    }
    
    /**
     * Checks if this tariff rider is currently active.
     * 
     * @return true if enrolled and effective date is in the past
     */
    public boolean isActive() {
        return "ENROLLED".equals(enrollmentStatus) && 
               effectiveDate != null && 
               effectiveDate <= (System.currentTimeMillis() / 1000);
    }
    
    /**
     * Gets the effective date as a human-readable string.
     * 
     * @return formatted date or "Not set" if null
     */
    public String getEffectiveDateFormatted() {
        if (effectiveDate == null) {
            return "Not set";
        }
        return java.time.Instant.ofEpochSecond(effectiveDate).toString();
    }
    
    /**
     * Creates a TariffRiderRef for a time-of-use enrollment.
     * 
     * @param status enrollment status
     * @param effectiveDate effective date in epoch seconds
     * @return TariffRiderRef for TOU enrollment
     */
    public static TariffRiderRefDto createTimeOfUse(String status, Long effectiveDate) {
        return new TariffRiderRefDto("TIME_OF_USE", status, effectiveDate);
    }
    
    /**
     * Creates a TariffRiderRef for net metering enrollment.
     * 
     * @param status enrollment status
     * @param effectiveDate effective date in epoch seconds
     * @return TariffRiderRef for net metering enrollment
     */
    public static TariffRiderRefDto createNetMetering(String status, Long effectiveDate) {
        return new TariffRiderRefDto("NET_METERING", status, effectiveDate);
    }
}