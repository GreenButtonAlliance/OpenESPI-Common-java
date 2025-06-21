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
import org.greenbuttonalliance.espi.common.domain.legacy.DateTimeInterval;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;

import jakarta.persistence.*;

/**
 * Pure JPA/Hibernate entity for ElectricPowerQualitySummary without JAXB concerns.
 * 
 * Represents a summary of power quality events for electric power delivery.
 * Contains information about voltage quality, frequency variations, interruptions,
 * and other power quality metrics typically required by customer facility 
 * energy management systems.
 */
@Entity
@Table(name = "electric_power_quality_summaries", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"usagePoint"})
public class ElectricPowerQualitySummaryEntity extends IdentifiedObject {

    private static final long serialVersionUID = 1L;

    /**
     * Flicker PLT (Long-term) measurement.
     * Represents long-term flicker severity as per IEC 61000-4-15.
     * Measured over 2-hour intervals.
     */
    @Column(name = "flicker_plt")
    private Long flickerPlt;

    /**
     * Flicker PST (Short-term) measurement.
     * Represents short-term flicker severity as per IEC 61000-4-15.
     * Measured over 10-minute intervals.
     */
    @Column(name = "flicker_pst")
    private Long flickerPst;

    /**
     * Total harmonic distortion for voltage.
     * Represents the harmonic content in the voltage waveform.
     * Measured as percentage of fundamental frequency.
     */
    @Column(name = "harmonic_voltage")
    private Long harmonicVoltage;

    /**
     * Number of long interruptions during the summary period.
     * Interruptions lasting longer than 3 minutes.
     */
    @Column(name = "long_interruptions")
    private Long longInterruptions;

    /**
     * RMS voltage of the mains supply.
     * Represents the effective voltage value.
     */
    @Column(name = "mains_voltage")
    private Long mainsVoltage;

    /**
     * Measurement protocol identifier.
     * Indicates the standard or method used for measurements.
     */
    @Column(name = "measurement_protocol")
    private Short measurementProtocol;

    /**
     * Power frequency measurement.
     * Nominal frequency is typically 50Hz or 60Hz.
     * Measured in millihertz (mHz).
     */
    @Column(name = "power_frequency")
    private Long powerFrequency;

    /**
     * Number of rapid voltage changes during the summary period.
     * Voltage changes exceeding specified thresholds.
     */
    @Column(name = "rapid_voltage_changes")
    private Long rapidVoltageChanges;

    /**
     * Number of short interruptions during the summary period.
     * Interruptions lasting between 0.5 seconds and 3 minutes.
     */
    @Column(name = "short_interruptions")
    private Long shortInterruptions;

    /**
     * Number of supply voltage dips during the summary period.
     * Temporary reductions in RMS voltage.
     */
    @Column(name = "supply_voltage_dips")
    private Long supplyVoltageDips;

    /**
     * Supply voltage imbalance measurement.
     * Represents asymmetry in three-phase voltage systems.
     * Measured as percentage of positive sequence component.
     */
    @Column(name = "supply_voltage_imbalance")
    private Long supplyVoltageImbalance;

    /**
     * Supply voltage variations measurement.
     * Long-term voltage magnitude variations from nominal.
     */
    @Column(name = "supply_voltage_variations")
    private Long supplyVoltageVariations;

    /**
     * Temporary overvoltage events count.
     * Voltage increases above specified thresholds for limited duration.
     */
    @Column(name = "temp_overvoltage")
    private Long tempOvervoltage;

    /**
     * Summary interval for this power quality summary.
     * Time period covered by these measurements.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "summary_interval_start")),
        @AttributeOverride(name = "duration", column = @Column(name = "summary_interval_duration"))
    })
    private DateTimeInterval summaryInterval;

    /**
     * Usage point that this power quality summary belongs to.
     * Many summaries can belong to one usage point.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_point_id")
    private UsagePointEntity usagePoint;

    /**
     * Constructor with summary interval.
     * 
     * @param summaryInterval the time interval for this summary
     */
    public ElectricPowerQualitySummaryEntity(DateTimeInterval summaryInterval) {
        this.summaryInterval = summaryInterval;
    }

    // Note: Simple setUsagePoint setter is generated by Lombok @Data
    // Complex bidirectional relationship management removed - handled by DataCustodian/ThirdParty applications

    /**
     * Generates the self href for this electric power quality summary.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        if (usagePoint != null) {
            return usagePoint.getSelfHref() + "/ElectricPowerQualitySummary/" + getHashedId();
        }
        return "/espi/1_1/resource/ElectricPowerQualitySummary/" + getHashedId();
    }

    /**
     * Generates the up href for this electric power quality summary.
     * 
     * @return up href string pointing to the usage point
     */
    public String getUpHref() {
        if (usagePoint != null) {
            return usagePoint.getSelfHref() + "/ElectricPowerQualitySummary";
        }
        return "/espi/1_1/resource/ElectricPowerQualitySummary";
    }

    /**
     * Overrides the default self href generation to use electric power quality summary specific logic.
     * 
     * @return self href for this electric power quality summary
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use electric power quality summary specific logic.
     * 
     * @return up href for this electric power quality summary
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another ElectricPowerQualitySummaryEntity.
     * Updates all power quality measurements and intervals.
     * 
     * @param other the other electric power quality summary entity to merge from
     */
    public void merge(ElectricPowerQualitySummaryEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update all power quality measurements
            this.flickerPlt = other.flickerPlt;
            this.flickerPst = other.flickerPst;
            this.harmonicVoltage = other.harmonicVoltage;
            this.longInterruptions = other.longInterruptions;
            this.mainsVoltage = other.mainsVoltage;
            this.measurementProtocol = other.measurementProtocol;
            this.powerFrequency = other.powerFrequency;
            this.rapidVoltageChanges = other.rapidVoltageChanges;
            this.shortInterruptions = other.shortInterruptions;
            this.supplyVoltageDips = other.supplyVoltageDips;
            this.supplyVoltageImbalance = other.supplyVoltageImbalance;
            this.supplyVoltageVariations = other.supplyVoltageVariations;
            this.tempOvervoltage = other.tempOvervoltage;
            
            // Update summary interval
            this.summaryInterval = other.summaryInterval;
            
            // Update usage point if provided
            if (other.usagePoint != null) {
                this.usagePoint = other.usagePoint;
            }
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Clear relationships with simple field assignment
        this.usagePoint = null;
    }

    /**
     * Sets up the parent relationship with a usage point.
     * Used by the resource management system.
     * Note: Bidirectional relationship setup handled by applications.
     * 
     * @param resource the parent usage point resource
     */
    public void setUpResource(IdentifiedObject resource) {
        if (resource instanceof UsagePointEntity parentUsagePoint) {
            this.usagePoint = parentUsagePoint;
        }
    }

    /**
     * Gets the query for finding parent resources.
     * Used by the resource management system.
     * 
     * @return the parent query string
     */
    public String getParentQuery() {
        return "UsagePointEntity.findByRelatedHref";
    }

    /**
     * Checks if this summary has voltage quality measurements.
     * 
     * @return true if voltage-related measurements are present, false otherwise
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
     * Checks if this summary has interruption data.
     * 
     * @return true if interruption measurements are present, false otherwise
     */
    public boolean hasInterruptionData() {
        return longInterruptions != null || shortInterruptions != null;
    }

    /**
     * Checks if this summary has flicker measurements.
     * 
     * @return true if flicker measurements are present, false otherwise
     */
    public boolean hasFlickerData() {
        return flickerPlt != null || flickerPst != null;
    }

    /**
     * Checks if this summary has frequency data.
     * 
     * @return true if frequency measurements are present, false otherwise
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

    /**
     * Gets the mains voltage in volts (assuming stored in millivolts).
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
     * Creates a summary description of power quality issues.
     * 
     * @return formatted description string
     */
    public String getPowerQualitySummary() {
        StringBuilder summary = new StringBuilder();
        
        if (hasInterruptionData()) {
            Long total = getTotalInterruptions();
            summary.append(total).append(" interruptions");
        }
        
        if (hasVoltageQualityData()) {
            if (summary.length() > 0) summary.append(", ");
            int voltageIssues = 0;
            if (supplyVoltageDips != null && supplyVoltageDips > 0) voltageIssues++;
            if (tempOvervoltage != null && tempOvervoltage > 0) voltageIssues++;
            if (rapidVoltageChanges != null && rapidVoltageChanges > 0) voltageIssues++;
            summary.append(voltageIssues).append(" voltage quality issues");
        }
        
        if (hasFlickerData()) {
            if (summary.length() > 0) summary.append(", ");
            summary.append("flicker measurements recorded");
        }
        
        if (hasFrequencyData()) {
            if (summary.length() > 0) summary.append(", ");
            summary.append("frequency: ").append(getPowerFrequencyInHz()).append(" Hz");
        }
        
        return summary.length() > 0 ? summary.toString() : "No power quality data";
    }
}