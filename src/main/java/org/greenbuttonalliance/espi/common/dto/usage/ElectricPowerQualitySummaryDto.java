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
import org.greenbuttonalliance.espi.common.dto.usage.DateTimeIntervalDto;

/**
 * ElectricPowerQualitySummary DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a comprehensive summary of power quality events for electric power delivery.
 * Contains information about voltage quality, frequency variations, interruptions,
 * flicker, harmonics, and other power quality metrics as defined by the NAESB ESPI 1.0 specification.
 * 
 * All power quality measurements follow IEC standards for power quality measurement and assessment.
 */
@XmlRootElement(name = "ElectricPowerQualitySummary", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElectricPowerQualitySummary", namespace = "http://naesb.org/espi", propOrder = {
    "description", "flickerPlt", "flickerPst", "harmonicVoltage", "longInterruptions", 
    "mainsVoltage", "measurementProtocol", "powerFrequency", "rapidVoltageChanges", 
    "shortInterruptions", "summaryInterval", "supplyVoltageDips", "supplyVoltageImbalance", 
    "supplyVoltageVariations", "tempOvervoltage", "usagePointId"
})
public record ElectricPowerQualitySummaryDto(
    
    @XmlTransient
    Long id,
    
    @XmlAttribute(name = "mRID")
    String uuid,
    
    @XmlElement(name = "description")
    String description,
    
    /**
     * Flicker PLT (Long-term) measurement.
     * Represents long-term flicker severity as per IEC 61000-4-15.
     * Measured over 2-hour intervals.
     * Unit: dimensionless (0.0 to 20.0 typical range)
     */
    @XmlElement(name = "flickerPlt")
    Long flickerPlt,
    
    /**
     * Flicker PST (Short-term) measurement.
     * Represents short-term flicker severity as per IEC 61000-4-15.
     * Measured over 10-minute intervals.
     * Unit: dimensionless (0.0 to 20.0 typical range)
     */
    @XmlElement(name = "flickerPst")
    Long flickerPst,
    
    /**
     * Total harmonic distortion for voltage.
     * Represents the harmonic content in the voltage waveform.
     * Unit: percentage of fundamental frequency (stored as basis points, e.g., 500 = 5.00%)
     */
    @XmlElement(name = "harmonicVoltage")
    Long harmonicVoltage,
    
    /**
     * Number of long interruptions during the summary period.
     * Interruptions lasting longer than 3 minutes as per IEEE 1159.
     * Unit: count
     */
    @XmlElement(name = "longInterruptions")
    Long longInterruptions,
    
    /**
     * RMS voltage of the mains supply.
     * Represents the effective voltage value.
     * Unit: millivolts (mV)
     */
    @XmlElement(name = "mainsVoltage")
    Long mainsVoltage,
    
    /**
     * Measurement protocol identifier.
     * Indicates the standard or method used for measurements.
     * Typical values:
     * - 1: IEC 61000-4-15 (Flicker)
     * - 2: IEC 61000-4-7 (Harmonics)
     * - 3: IEEE 1159 (Interruptions)
     * - 4: IEC 61000-4-30 (General power quality)
     */
    @XmlElement(name = "measurementProtocol")
    Short measurementProtocol,
    
    /**
     * Power frequency measurement.
     * Nominal frequency is typically 50Hz or 60Hz.
     * Unit: millihertz (mHz) - e.g., 60000 for 60.000 Hz
     */
    @XmlElement(name = "powerFrequency")
    Long powerFrequency,
    
    /**
     * Number of rapid voltage changes during the summary period.
     * Voltage changes exceeding specified thresholds per IEC 61000-4-15.
     * Unit: count
     */
    @XmlElement(name = "rapidVoltageChanges")
    Long rapidVoltageChanges,
    
    /**
     * Number of short interruptions during the summary period.
     * Interruptions lasting between 0.5 seconds and 3 minutes per IEEE 1159.
     * Unit: count
     */
    @XmlElement(name = "shortInterruptions")
    Long shortInterruptions,
    
    /**
     * Summary interval for this power quality summary.
     * Time period covered by these measurements.
     * Typically covers 24-hour periods for daily summaries.
     */
    @XmlElement(name = "summaryInterval")
    DateTimeIntervalDto summaryInterval,
    
    /**
     * Number of supply voltage dips during the summary period.
     * Temporary reductions in RMS voltage below 90% of nominal per IEC 61000-4-11.
     * Unit: count
     */
    @XmlElement(name = "supplyVoltageDips")
    Long supplyVoltageDips,
    
    /**
     * Supply voltage imbalance measurement.
     * Represents asymmetry in three-phase voltage systems per IEC 61000-4-27.
     * Unit: percentage of positive sequence component (stored as basis points)
     */
    @XmlElement(name = "supplyVoltageImbalance")
    Long supplyVoltageImbalance,
    
    /**
     * Supply voltage variations measurement.
     * Long-term voltage magnitude variations from nominal per IEC 61000-4-30.
     * Unit: percentage deviation from nominal (stored as basis points)
     */
    @XmlElement(name = "supplyVoltageVariations")
    Long supplyVoltageVariations,
    
    /**
     * Temporary overvoltage events count.
     * Voltage increases above 110% of nominal for limited duration per IEEE 1159.
     * Unit: count
     */
    @XmlElement(name = "tempOvervoltage")
    Long tempOvervoltage,
    
    /**
     * Reference to the usage point this power quality summary belongs to.
     * Represents the logical relationship to the measurement point.
     */
    @XmlTransient
    Long usagePointId
    
) {
    
    /**
     * Default constructor for JAXB.
     */
    public ElectricPowerQualitySummaryDto() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, 
             null, null, null, null, null);
    }
    
    /**
     * Constructor with basic identification.
     * 
     * @param id the database identifier
     * @param uuid the unique resource identifier
     * @param description human-readable description
     */
    public ElectricPowerQualitySummaryDto(Long id, String uuid, String description) {
        this(id, uuid, description, null, null, null, null, null, null, null, null, null, 
             null, null, null, null, null, null);
    }
    
    /**
     * Checks if this summary contains voltage quality measurements.
     * 
     * @return true if voltage-related measurements are present
     */
    public boolean hasVoltageQualityData() {
        return mainsVoltage != null ||
               supplyVoltageVariations != null ||
               supplyVoltageImbalance != null ||
               supplyVoltageDips != null ||
               tempOvervoltage != null ||
               harmonicVoltage != null;
    }
    
    /**
     * Checks if this summary contains interruption data.
     * 
     * @return true if interruption measurements are present
     */
    public boolean hasInterruptionData() {
        return longInterruptions != null || shortInterruptions != null;
    }
    
    /**
     * Checks if this summary contains flicker measurements.
     * 
     * @return true if flicker measurements are present
     */
    public boolean hasFlickerData() {
        return flickerPlt != null || flickerPst != null;
    }
    
    /**
     * Checks if this summary contains frequency data.
     * 
     * @return true if frequency measurements are present
     */
    public boolean hasFrequencyData() {
        return powerFrequency != null;
    }
    
    /**
     * Gets the total number of interruptions (short + long).
     * 
     * @return total interruptions count, or null if no data available
     */
    public Long getTotalInterruptions() {
        if (shortInterruptions == null && longInterruptions == null) {
            return null;
        }
        return (shortInterruptions != null ? shortInterruptions : 0) +
               (longInterruptions != null ? longInterruptions : 0);
    }
    
    /**
     * Gets the power frequency in Hz (converting from millihertz).
     * 
     * @return frequency in Hz, or null if not set
     */
    public Double getPowerFrequencyInHz() {
        return powerFrequency != null ? powerFrequency / 1000.0 : null;
    }
    
    /**
     * Gets the mains voltage in volts (converting from millivolts).
     * 
     * @return voltage in volts, or null if not set
     */
    public Double getMainsVoltageInVolts() {
        return mainsVoltage != null ? mainsVoltage / 1000.0 : null;
    }
    
    /**
     * Gets the voltage imbalance as a percentage.
     * 
     * @return voltage imbalance percentage, or null if not set
     */
    public Double getSupplyVoltageImbalancePercent() {
        return supplyVoltageImbalance != null ? supplyVoltageImbalance / 100.0 : null;
    }
    
    /**
     * Gets the harmonic voltage distortion as a percentage.
     * 
     * @return harmonic voltage percentage, or null if not set
     */
    public Double getHarmonicVoltagePercent() {
        return harmonicVoltage != null ? harmonicVoltage / 100.0 : null;
    }
    
    /**
     * Checks if the power frequency is within normal bounds.
     * Assumes 60Hz nominal frequency with ±0.5Hz tolerance.
     * 
     * @return true if frequency is within bounds, false otherwise
     */
    public boolean isPowerFrequencyNormal() {
        Double freqHz = getPowerFrequencyInHz();
        if (freqHz == null) return false;
        // Typical tolerance for 60Hz systems
        return freqHz >= 59.5 && freqHz <= 60.5;
    }
}