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
import org.greenbuttonalliance.espi.common.dto.RationalNumberDto;
import org.greenbuttonalliance.espi.common.dto.ReadingInterharmonicDto;

/**
 * ReadingType DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents comprehensive characteristics associated with all readings included in a MeterReading.
 * Contains metadata about the type of measurements including commodity, data qualifier,
 * unit of measure, flow direction, accumulation behavior, pricing structures, and other 
 * reading attributes as defined by the NAESB ESPI 1.0 specification.
 * 
 * This DTO supports the complete range of ESPI reading type attributes for electricity,
 * gas, water, and other commodity measurements with full pricing and time-of-use support.
 */
@XmlRootElement(name = "ReadingType", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadingType", namespace = "http://naesb.org/espi", propOrder = {
    "description", "accumulationBehaviour", "commodity", "consumptionTier", "currency", 
    "dataQualifier", "defaultQuality", "flowDirection", "intervalLength", "kind", 
    "phase", "powerOfTenMultiplier", "timeAttribute", "uom", "cpp", "measuringPeriod", 
    "tou", "argument", "interharmonic"
})
public record ReadingTypeDto(
    
    @XmlTransient
    Long id,
    
    @XmlAttribute(name = "mRID")
    String uuid,
    
    @XmlElement(name = "description")
    String description,
    
    /**
     * Accumulation behavior describing how readings accumulate over time.
     * 
     * Common ESPI values:
     * - DELTAOUTPUT: Change since previous reading
     * - SUMMATION: Cumulative total
     * - NONE: Instantaneous value
     * - INDICATING: Current state indication
     * - BULK_QUANTITY: Total amount in storage
     */
    @XmlElement(name = "accumulationBehaviour")
    String accumulationBehaviour,
    
    /**
     * Commodity being measured.
     * 
     * ESPI-defined values:
     * - ELECTRICITY: Electric energy/power
     * - GAS: Natural gas
     * - WATER: Water consumption
     * - TIME: Time-based measurements
     * - HEAT: Thermal energy
     * - COOLING: Cooling energy
     * - REFUSE: Waste management
     */
    @XmlElement(name = "commodity")
    String commodity,
    
    /**
     * Consumption tier for tiered pricing structures.
     * Used in time-of-use and inclining block rate systems.
     * 
     * Typical values: 1, 2, 3, etc. representing pricing tiers
     */
    @XmlElement(name = "consumptionTier")
    String consumptionTier,
    
    /**
     * Currency code for monetary readings.
     * ISO 4217 currency codes (USD, EUR, CAD, etc.).
     * Only present for cost/price reading types.
     */
    @XmlElement(name = "currency")
    String currency,
    
    /**
     * Data qualifier describing the nature of the reading value.
     * 
     * ESPI values:
     * - AVERAGE: Average value over interval
     * - MAXIMUM: Maximum value during interval
     * - MINIMUM: Minimum value during interval
     * - NORMAL: Standard measurement
     * - ESTIMATED: Estimated/calculated value
     * - PROJECTED: Future projected value
     */
    @XmlElement(name = "dataQualifier")
    String dataQualifier,
    
    /**
     * Default quality indicator for readings of this type.
     * Represents the expected baseline quality level.
     * 
     * Values align with ReadingQuality definitions:
     * - VALID: Good quality data
     * - ESTIMATED: Estimated values
     * - QUESTIONABLE: Data integrity concerns
     * - DERIVED: Calculated from other readings
     */
    @XmlElement(name = "defaultQuality")
    String defaultQuality,
    
    /**
     * Direction of energy flow for electrical measurements.
     * 
     * ESPI values:
     * - FORWARD: Energy delivered (normal consumption)
     * - REVERSE: Energy received (generation/backfeed)
     * - LAGGING: Lagging reactive power
     * - LEADING: Leading reactive power
     * - NET: Net energy (forward minus reverse)
     * - TOTAL: Total energy (forward plus reverse)
     */
    @XmlElement(name = "flowDirection")
    String flowDirection,
    
    /**
     * Length of the measurement interval in seconds.
     * Defines the time period for each reading value.
     * 
     * Common ESPI values:
     * - 300: 5-minute intervals
     * - 900: 15-minute intervals (most common)
     * - 1800: 30-minute intervals
     * - 3600: 1-hour intervals
     * - 86400: Daily intervals
     * - 2592000: Monthly intervals (approximate)
     */
    @XmlElement(name = "intervalLength")
    Long intervalLength,
    
    /**
     * Kind of measurement being performed.
     * 
     * ESPI electrical values:
     * - ENERGY: Total energy (kWh)
     * - POWER: Instantaneous power (kW)
     * - DEMAND: Maximum demand
     * - VOLTAGE: RMS voltage
     * - CURRENT: RMS current
     * - FREQUENCY: Power frequency
     * - POWER_FACTOR: Power factor
     * - APPARENT_POWER: Apparent power (kVA)
     * - REACTIVE_POWER: Reactive power (kVAR)
     * - COST: Monetary cost
     * - CARBON: Carbon emissions
     */
    @XmlElement(name = "kind")
    String kind,
    
    /**
     * Phase information for electrical measurements.
     * 
     * ESPI phase values:
     * - AN, BN, CN: Line-to-neutral single phases
     * - AB, BC, CA: Line-to-line phases
     * - ABC: Three-phase total
     * - A, B, C: Individual phases (line-to-ground)
     * - S1, S2: Split-phase (North American residential)
     * - NET: Net measurement across all phases
     */
    @XmlElement(name = "phase")
    String phase,
    
    /**
     * Power of ten multiplier for the unit of measure.
     * Applies scaling to the base unit.
     * 
     * ESPI multiplier values:
     * - KILO: 10^3 (k)
     * - MEGA: 10^6 (M) 
     * - GIGA: 10^9 (G)
     * - MILLI: 10^-3 (m)
     * - MICRO: 10^-6 (μ)
     * - NONE: 10^0 (no scaling)
     */
    @XmlElement(name = "powerOfTenMultiplier")
    String powerOfTenMultiplier,
    
    /**
     * Time attribute describing the time period of interest.
     * 
     * ESPI values:
     * - NONE: No specific time context
     * - BILLING_PERIOD: Within billing cycle
     * - DAILY: Daily aggregation
     * - MONTHLY: Monthly aggregation
     * - SEASONAL: Seasonal aggregation
     * - ANNUAL: Annual aggregation
     * - PRESENT: Current/real-time value
     * - PREVIOUS: Previous period value
     */
    @XmlElement(name = "timeAttribute")
    String timeAttribute,
    
    /**
     * Unit of measure for the readings.
     * 
     * ESPI electrical units:
     * - WH: Watt-hours (energy)
     * - W: Watts (power)
     * - VAH: Volt-ampere hours (apparent energy)
     * - VA: Volt-amperes (apparent power)
     * - VARH: Reactive energy hours
     * - VAR: Reactive power
     * - V: Volts (voltage)
     * - A: Amperes (current)
     * - HZ: Hertz (frequency)
     * - PF: Power factor (dimensionless)
     * - THERM: Thermal units (gas)
     * - M3: Cubic meters (gas/water volume)
     * - FT3: Cubic feet (gas volume)
     * - GAL: Gallons (water volume)
     */
    @XmlElement(name = "uom")
    String uom,
    
    /**
     * Critical peak pricing indicator.
     * Used for demand response programs and peak pricing events.
     * 
     * Values typically:
     * - 0: Normal pricing
     * - 1: Critical peak pricing active
     * - 2: Peak pricing warning
     */
    @XmlElement(name = "cpp")
    String cpp,
    
    /**
     * Measuring period for the readings.
     * Describes the measurement timing characteristics and sampling.
     * 
     * Values describe how measurements are taken:
     * - CONTINUOUS: Continuous monitoring
     * - INTERVAL: Fixed interval sampling
     * - INSTANTANEOUS: Point-in-time measurements
     * - CUMULATIVE: Cumulative totals
     */
    @XmlElement(name = "measuringPeriod")
    String measuringPeriod,
    
    /**
     * Time-of-use indicator.
     * Used for time-based pricing structures and rate schedules.
     * 
     * Typical utility TOU periods:
     * - 1: Peak hours (highest rates)
     * - 2: Partial-peak hours (medium rates) 
     * - 3: Off-peak hours (lowest rates)
     * - 0: No TOU pricing
     */
    @XmlElement(name = "tou")
    String tou,
    
    /**
     * Rational number argument for complex calculations.
     * Used for mathematical transformations and scaling of readings.
     * 
     * Common uses:
     * - CT/PT ratio transformations
     * - Unit conversions
     * - Scaling factors for display
     */
    @XmlElement(name = "argument")
    RationalNumberDto argument,
    
    /**
     * Interharmonic information for power quality measurements.
     * Contains details about harmonic distortion and frequency analysis.
     * 
     * Used for:
     * - Total harmonic distortion (THD) measurements
     * - Individual harmonic analysis
     * - Power quality monitoring
     * - Frequency domain analysis
     */
    @XmlElement(name = "interharmonic")
    ReadingInterharmonicDto interharmonic
    
) {
    
    /**
     * Default constructor for JAXB.
     */
    public ReadingTypeDto() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, 
             null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Constructor with basic identification.
     * 
     * @param id the database identifier
     * @param uuid the unique resource identifier
     * @param description human-readable description
     */
    public ReadingTypeDto(Long id, String uuid, String description) {
        this(id, uuid, description, null, null, null, null, null, null, null, null, 
             null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Constructor with essential measurement characteristics.
     * 
     * @param id the database identifier
     * @param uuid the unique resource identifier
     * @param description human-readable description
     * @param commodity the commodity being measured
     * @param kind the kind of measurement
     * @param uom the unit of measure
     */
    public ReadingTypeDto(Long id, String uuid, String description, String commodity, String kind, String uom) {
        this(id, uuid, description, null, commodity, null, null, null, null, null, 
             null, kind, null, null, null, uom, null, null, null, null, null);
    }
    
    /**
     * Checks if this reading type represents energy measurements.
     * 
     * @return true if kind is energy-related
     */
    public boolean isEnergyMeasurement() {
        return "ENERGY".equalsIgnoreCase(kind) || 
               "ACTIVE_ENERGY".equalsIgnoreCase(kind) ||
               "REACTIVE_ENERGY".equalsIgnoreCase(kind) ||
               "APPARENT_ENERGY".equalsIgnoreCase(kind);
    }
    
    /**
     * Checks if this reading type represents power measurements.
     * 
     * @return true if kind is power-related
     */
    public boolean isPowerMeasurement() {
        return "POWER".equalsIgnoreCase(kind) ||
               "ACTIVE_POWER".equalsIgnoreCase(kind) ||
               "REACTIVE_POWER".equalsIgnoreCase(kind) ||
               "APPARENT_POWER".equalsIgnoreCase(kind);
    }
    
    /**
     * Checks if this reading type represents demand measurements.
     * 
     * @return true if kind is demand-related
     */
    public boolean isDemandMeasurement() {
        return "DEMAND".equalsIgnoreCase(kind) ||
               "MAXIMUM_DEMAND".equalsIgnoreCase(kind) ||
               "MINIMUM_DEMAND".equalsIgnoreCase(kind);
    }
    
    /**
     * Checks if this reading type represents monetary values.
     * 
     * @return true if currency is specified
     */
    public boolean isMonetaryMeasurement() {
        return currency != null && !currency.trim().isEmpty();
    }
    
    /**
     * Checks if this reading type has time-of-use pricing.
     * 
     * @return true if tou is specified
     */
    public boolean hasTimeOfUsePricing() {
        return tou != null && !tou.trim().isEmpty() && !"0".equals(tou);
    }
    
    /**
     * Checks if this reading type has tiered pricing.
     * 
     * @return true if consumption tier is specified
     */
    public boolean hasTieredPricing() {
        return consumptionTier != null && !consumptionTier.trim().isEmpty();
    }
    
    /**
     * Checks if this reading type has critical peak pricing.
     * 
     * @return true if cpp is active
     */
    public boolean hasCriticalPeakPricing() {
        return cpp != null && !cpp.trim().isEmpty() && !"0".equals(cpp);
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
     * Checks if this is a standard 15-minute interval reading type.
     * 
     * @return true if interval length is 900 seconds (15 minutes)
     */
    public boolean isStandard15MinuteInterval() {
        return Long.valueOf(900L).equals(intervalLength);
    }
    
    /**
     * Checks if this is a daily interval reading type.
     * 
     * @return true if interval length is 86400 seconds (24 hours)
     */
    public boolean isDailyInterval() {
        return Long.valueOf(86400L).equals(intervalLength);
    }
    
    /**
     * Checks if this reading type represents electricity commodity.
     * 
     * @return true if commodity is electricity
     */
    public boolean isElectricity() {
        return "ELECTRICITY".equalsIgnoreCase(commodity);
    }
    
    /**
     * Checks if this reading type represents gas commodity.
     * 
     * @return true if commodity is gas
     */
    public boolean isGas() {
        return "GAS".equalsIgnoreCase(commodity);
    }
    
    /**
     * Checks if this reading type represents water commodity.
     * 
     * @return true if commodity is water
     */
    public boolean isWater() {
        return "WATER".equalsIgnoreCase(commodity);
    }
    
    /**
     * Gets a human-readable description of the interval length.
     * 
     * @return formatted interval description
     */
    public String getIntervalDescription() {
        if (intervalLength == null) return "Unspecified interval";
        
        long minutes = intervalLength / 60;
        long hours = intervalLength / 3600;
        long days = intervalLength / 86400;
        
        if (days > 0) {
            return days + " day" + (days > 1 ? "s" : "");
        } else if (hours > 0) {
            return hours + " hour" + (hours > 1 ? "s" : "");
        } else if (minutes > 0) {
            return minutes + " minute" + (minutes > 1 ? "s" : "");
        } else {
            return intervalLength + " second" + (intervalLength > 1 ? "s" : "");
        }
    }
    
    /**
     * Creates a human-readable summary of this reading type.
     * 
     * @return formatted description string
     */
    public String getReadingTypeSummary() {
        StringBuilder summary = new StringBuilder();
        
        if (commodity != null) {
            summary.append(commodity.charAt(0)).append(commodity.substring(1).toLowerCase());
        }
        
        if (kind != null) {
            if (summary.length() > 0) summary.append(" ");
            summary.append(kind.toLowerCase().replace("_", " "));
        }
        
        if (uom != null) {
            summary.append(" (").append(uom).append(")");
        }
        
        if (intervalLength != null) {
            summary.append(" - ").append(getIntervalDescription());
        }
        
        if (hasTimeOfUsePricing()) {
            summary.append(" [TOU: ").append(tou).append("]");
        }
        
        if (hasTieredPricing()) {
            summary.append(" [Tier: ").append(consumptionTier).append("]");
        }
        
        return summary.toString();
    }
}