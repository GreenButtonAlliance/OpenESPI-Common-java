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

package org.greenbuttonalliance.espi.common.dto;

import jakarta.xml.bind.annotation.*;

/**
 * SummaryMeasurement DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents an aggregated summary measurement reading used in various
 * ESPI resources like UsagePoint, UsageSummary, etc.
 * 
 * Contains value, unit of measure, power of ten multiplier, timestamp, and readingTypeRef.
 * 
 * IMPORTANT: readingTypeRef business rules per NAESB ESPI standard:
 * - If UsagePoint atom 'related' readingType href URL is present: readingTypeRef is redundant and should be null/omitted
 * - If no 'related' readingType href URL exists: readingTypeRef should default to the atom 'self' link's href URL
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SummaryMeasurement", namespace = "http://naesb.org/espi", propOrder = {
    "powerOfTenMultiplier", "timeStamp", "uom", "value", "readingTypeRef"
})
public record SummaryMeasurementDto(
    
    String powerOfTenMultiplier,
    Long timeStamp,
    String uom,
    Long value,
    String readingTypeRef
) {
    
    /**
     * Gets the power of ten multiplier for the measurement.
     * Used to scale the value (e.g., "3" for kilo, "6" for mega).
     */
    @XmlElement(name = "powerOfTenMultiplier")
    public String getPowerOfTenMultiplier() {
        return powerOfTenMultiplier;
    }
    
    /**
     * Gets the timestamp when this measurement was taken.
     * Stored as epoch seconds (TimeType in ESPI).
     */
    @XmlElement(name = "timeStamp")
    public Long getTimeStamp() {
        return timeStamp;
    }
    
    /**
     * Gets the unit of measure for this measurement.
     * Examples: "W" (watts), "V" (volts), "A" (amperes).
     */
    @XmlElement(name = "uom")
    public String getUom() {
        return uom;
    }
    
    /**
     * Gets the measurement value.
     * Combined with powerOfTenMultiplier to get the actual value.
     */
    @XmlElement(name = "value")
    public Long getValue() {
        return value;
    }
    
    /**
     * Gets the reading type reference (URI).
     * Extension reference to a full ReadingType resource.
     * 
     * BUSINESS RULE: Per NAESB ESPI standard:
     * - Returns null if UsagePoint atom 'related' readingType href URL is present (redundant)
     * - Returns atom 'self' link href URL if no 'related' readingType href exists
     */
    @XmlElement(name = "readingTypeRef")
    public String getReadingTypeRef() {
        return readingTypeRef;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public SummaryMeasurementDto() {
        this(null, null, null, null, null);
    }
    
    /**
     * Constructor with value and unit of measure.
     */
    public SummaryMeasurementDto(Long value, String uom) {
        this(null, null, uom, value, null);
    }
    
    /**
     * Constructor with value, unit, and multiplier.
     */
    public SummaryMeasurementDto(Long value, String uom, String powerOfTenMultiplier) {
        this(powerOfTenMultiplier, null, uom, value, null);
    }
    
    /**
     * Constructor with value, unit, multiplier, and reading type reference.
     */
    public SummaryMeasurementDto(Long value, String uom, String powerOfTenMultiplier, String readingTypeRef) {
        this(powerOfTenMultiplier, null, uom, value, readingTypeRef);
    }
    
    /**
     * Gets the actual scaled value by applying the power of ten multiplier.
     * 
     * @return scaled value as double
     */
    public double getScaledValue() {
        if (value == null) {
            return 0.0;
        }
        if (powerOfTenMultiplier == null) {
            return value.doubleValue();
        }
        try {
            int multiplier = Integer.parseInt(powerOfTenMultiplier);
            return value.doubleValue() * Math.pow(10, multiplier);
        } catch (NumberFormatException e) {
            return value.doubleValue();
        }
    }
    
    /**
     * Checks if this measurement has a valid value.
     * 
     * @return true if value is not null
     */
    public boolean hasValue() {
        return value != null;
    }
    
    /**
     * Creates a SummaryMeasurement for power measurements in watts.
     */
    public static SummaryMeasurementDto createPowerMeasurement(Long watts) {
        return new SummaryMeasurementDto(watts, "W");
    }
    
    /**
     * Creates a SummaryMeasurement for voltage measurements in volts.
     */
    public static SummaryMeasurementDto createVoltageMeasurement(Long volts) {
        return new SummaryMeasurementDto(volts, "V");
    }
    
    /**
     * Creates a SummaryMeasurement for current measurements in amperes.
     */
    public static SummaryMeasurementDto createCurrentMeasurement(Long amperes) {
        return new SummaryMeasurementDto(amperes, "A");
    }
    
    /**
     * Creates a SummaryMeasurement with proper readingTypeRef business logic.
     * 
     * @param value the measurement value
     * @param uom the unit of measure
     * @param powerOfTenMultiplier the multiplier (can be null)
     * @param hasRelatedReadingTypeHref true if UsagePoint atom has 'related' readingType href
     * @param selfHref the atom 'self' link href URL (used as fallback)
     * @return SummaryMeasurementDto with correct readingTypeRef logic
     */
    public static SummaryMeasurementDto createWithReadingTypeLogic(
            Long value, String uom, String powerOfTenMultiplier,
            boolean hasRelatedReadingTypeHref, String selfHref) {
        
        // Apply ESPI business rule for readingTypeRef
        String readingTypeRef = null;
        if (!hasRelatedReadingTypeHref && selfHref != null) {
            // No 'related' readingType href exists, use 'self' link href
            readingTypeRef = selfHref;
        }
        // If hasRelatedReadingTypeHref is true, readingTypeRef remains null (redundant)
        
        return new SummaryMeasurementDto(powerOfTenMultiplier, null, uom, value, readingTypeRef);
    }
}