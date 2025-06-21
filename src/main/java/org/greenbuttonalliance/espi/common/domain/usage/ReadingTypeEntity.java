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

package org.greenbuttonalliance.espi.common.domain.usage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.legacy.RationalNumber;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.ReadingInterharmonic;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for ReadingType without JAXB concerns.
 * 
 * Represents characteristics associated with all readings included in a MeterReading.
 * Contains metadata about the type of measurements including commodity, data qualifier,
 * unit of measure, flow direction, accumulation behavior, and other reading attributes.
 */
@Entity
@Table(name = "reading_types", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"meterReadings"})
public class ReadingTypeEntity extends IdentifiedObject {

    private static final long serialVersionUID = 1L;

    /**
     * Accumulation behavior describing how readings accumulate over time.
     * Values: DELTAOUTPUT, SUMMATION, etc.
     */
    @Column(name = "accumulation_behaviour", length = 50)
    private String accumulationBehaviour;

    /**
     * Commodity being measured (e.g., electricity, gas, water).
     * Values: ELECTRICITY, GAS, WATER, etc.
     */
    @Column(name = "commodity", length = 50)
    private String commodity;

    /**
     * Consumption tier for tiered pricing structures.
     * Used in time-of-use and tiered billing systems.
     */
    @Column(name = "consumption_tier", length = 50)
    private String consumptionTier;

    /**
     * Currency code for monetary readings.
     * ISO 4217 currency codes (USD, EUR, etc.).
     */
    @Column(name = "currency", length = 3)
    private String currency;

    /**
     * Data qualifier describing the nature of the reading.
     * Values: AVERAGE, MAXIMUM, MINIMUM, etc.
     */
    @Column(name = "data_qualifier", length = 50)
    private String dataQualifier;

    /**
     * Default quality indicator for readings of this type.
     * Represents the expected quality level.
     */
    @Column(name = "default_quality", length = 50)
    private String defaultQuality;

    /**
     * Direction of energy flow.
     * Values: FORWARD, REVERSE, LAGGING, LEADING, etc.
     */
    @Column(name = "flow_direction", length = 50)
    private String flowDirection;

    /**
     * Length of the measurement interval in seconds.
     * Common values: 900 (15 min), 3600 (1 hour), 86400 (1 day).
     */
    @Column(name = "interval_length")
    private Long intervalLength;

    /**
     * Kind of measurement being performed.
     * Values: ENERGY, POWER, VOLTAGE, CURRENT, etc.
     */
    @Column(name = "kind", length = 50)
    private String kind;

    /**
     * Phase information for electrical measurements.
     * Values: AN, BN, CN, AB, BC, CA, ABC, etc.
     */
    @Column(name = "phase", length = 50)
    private String phase;

    /**
     * Power of ten multiplier for the unit of measure.
     * Values: KILO (10^3), MEGA (10^6), MILLI (10^-3), etc.
     */
    @Column(name = "power_of_ten_multiplier", length = 50)
    private String powerOfTenMultiplier;

    /**
     * Time attribute describing the time period of interest.
     * Values: NONE, BILLING_PERIOD, DAILY, etc.
     */
    @Column(name = "time_attribute", length = 50)
    private String timeAttribute;

    /**
     * Unit of measure for the readings.
     * Values: WH (watt-hours), W (watts), V (volts), A (amperes), etc.
     */
    @Column(name = "uom", length = 50)
    private String uom;

    /**
     * Critical peak pricing indicator.
     * Used for demand response programs.
     */
    @Column(name = "cpp", length = 50)
    private String cpp;

    /**
     * Measuring period for the readings.
     * Describes the measurement timing characteristics.
     */
    @Column(name = "measuring_period", length = 50)
    private String measuringPeriod;

    /**
     * Time-of-use indicator.
     * Used for time-based pricing structures.
     */
    @Column(name = "tou", length = 50)
    private String tou;

    /**
     * Rational number argument for complex calculations.
     * Used for mathematical transformations of readings.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "numerator", column = @Column(name = "argument_numerator")),
        @AttributeOverride(name = "denominator", column = @Column(name = "argument_denominator"))
    })
    private RationalNumber argument;

    /**
     * Interharmonic information for power quality measurements.
     * Contains details about harmonic distortion.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "numerator", column = @Column(name = "interharmonic_numerator")),
        @AttributeOverride(name = "denominator", column = @Column(name = "interharmonic_denominator"))
    })
    private ReadingInterharmonic interharmonic;

    /**
     * Meter readings that use this reading type.
     * One-to-many relationship with eager loading for performance.
     */
    @OneToMany(mappedBy = "readingType", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<MeterReadingEntity> meterReadings = new ArrayList<>();

    /**
     * Constructor with essential reading type characteristics.
     * 
     * @param commodity the commodity being measured
     * @param kind the kind of measurement
     * @param uom the unit of measure
     */
    public ReadingTypeEntity(String commodity, String kind, String uom) {
        this.commodity = commodity;
        this.kind = kind;
        this.uom = uom;
    }

    // Note: Meter reading collection accessors are generated by Lombok @Data
    // Bidirectional relationship management methods removed - handled by DataCustodian/ThirdParty applications

    /**
     * Generates the self href for this reading type.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return "/espi/1_1/resource/ReadingType/" + getHashedId();
    }

    /**
     * Generates the up href for this reading type.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return "/espi/1_1/resource/ReadingType";
    }

    /**
     * Overrides the default self href generation to use reading type specific logic.
     * 
     * @return self href for this reading type
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use reading type specific logic.
     * 
     * @return up href for this reading type
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another ReadingTypeEntity.
     * Updates all characteristics and embedded values.
     * 
     * @param other the other reading type entity to merge from
     */
    public void merge(ReadingTypeEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update all reading type characteristics
            this.accumulationBehaviour = other.accumulationBehaviour;
            this.commodity = other.commodity;
            this.consumptionTier = other.consumptionTier;
            this.currency = other.currency;
            this.dataQualifier = other.dataQualifier;
            this.defaultQuality = other.defaultQuality;
            this.flowDirection = other.flowDirection;
            this.intervalLength = other.intervalLength;
            this.kind = other.kind;
            this.phase = other.phase;
            this.powerOfTenMultiplier = other.powerOfTenMultiplier;
            this.timeAttribute = other.timeAttribute;
            this.uom = other.uom;
            this.cpp = other.cpp;
            this.measuringPeriod = other.measuringPeriod;
            this.tou = other.tou;
            
            // Update embedded values
            this.argument = other.argument;
            this.interharmonic = other.interharmonic;
            
            // Note: meterReadings collection is not merged to preserve existing relationships
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     * Simplified - applications handle relationship cleanup.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Simple collection clearing - applications handle bidirectional cleanup
        meterReadings.clear();
    }

    /**
     * Gets the query for finding parent resources.
     * Used by the resource management system.
     * 
     * @return the parent query string
     */
    public String getParentQuery() {
        return "MeterReadingEntity.findByRelatedHref";
    }

    /**
     * Checks if this reading type represents energy measurements.
     * 
     * @return true if kind is energy-related, false otherwise
     */
    public boolean isEnergyMeasurement() {
        return "ENERGY".equalsIgnoreCase(kind) || 
               "ACTIVE_ENERGY".equalsIgnoreCase(kind) ||
               "REACTIVE_ENERGY".equalsIgnoreCase(kind);
    }

    /**
     * Checks if this reading type represents power measurements.
     * 
     * @return true if kind is power-related, false otherwise
     */
    public boolean isPowerMeasurement() {
        return "POWER".equalsIgnoreCase(kind) ||
               "ACTIVE_POWER".equalsIgnoreCase(kind) ||
               "REACTIVE_POWER".equalsIgnoreCase(kind);
    }

    /**
     * Checks if this reading type represents demand measurements.
     * 
     * @return true if kind is demand-related, false otherwise
     */
    public boolean isDemandMeasurement() {
        return "DEMAND".equalsIgnoreCase(kind) ||
               "MAXIMUM_DEMAND".equalsIgnoreCase(kind);
    }

    /**
     * Checks if this reading type represents monetary values.
     * 
     * @return true if currency is specified, false otherwise
     */
    public boolean isMonetaryMeasurement() {
        return currency != null && !currency.trim().isEmpty();
    }

    /**
     * Checks if this reading type has time-of-use pricing.
     * 
     * @return true if tou is specified, false otherwise
     */
    public boolean hasTimeOfUsePricing() {
        return tou != null && !tou.trim().isEmpty();
    }

    /**
     * Checks if this reading type has tiered pricing.
     * 
     * @return true if consumption tier is specified, false otherwise
     */
    public boolean hasTieredPricing() {
        return consumptionTier != null && !consumptionTier.trim().isEmpty();
    }

    /**
     * Gets the interval length in minutes.
     * 
     * @return interval length in minutes, or null if not specified
     */
    public Long getIntervalLengthInMinutes() {
        return intervalLength != null ? intervalLength / 60 : null;
    }

    /**
     * Gets the interval length in hours.
     * 
     * @return interval length in hours, or null if not specified
     */
    public Long getIntervalLengthInHours() {
        return intervalLength != null ? intervalLength / 3600 : null;
    }

    /**
     * Creates a human-readable description of this reading type.
     * 
     * @return formatted description string
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        
        if (commodity != null) {
            desc.append(commodity.charAt(0)).append(commodity.substring(1).toLowerCase());
        }
        
        if (kind != null) {
            if (desc.length() > 0) desc.append(" ");
            desc.append(kind.toLowerCase().replace("_", " "));
        }
        
        if (uom != null) {
            desc.append(" (").append(uom).append(")");
        }
        
        if (intervalLength != null) {
            desc.append(" - ").append(getIntervalLengthInMinutes()).append(" min intervals");
        }
        
        return desc.toString();
    }
}