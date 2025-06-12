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
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Pure JPA/Hibernate entity for ReadingQuality without JAXB concerns.
 * 
 * Represents the quality of a specific reading value or interval reading value.
 * Note that more than one Quality may be applicable to a given Reading. 
 * Typically not used unless problems or unusual conditions occur (i.e., quality 
 * for each Reading is assumed to be 'Good' (valid) unless stated otherwise in 
 * associated ReadingQuality).
 */
@Entity
@Table(name = "reading_qualities", indexes = {
    @Index(name = "idx_reading_quality_interval_reading", columnList = "interval_reading_id"),
    @Index(name = "idx_reading_quality_quality", columnList = "quality")
})
@Data
@NoArgsConstructor
@ToString(exclude = {"intervalReading"})
public class ReadingQualityEntity {

    private static final long serialVersionUID = 1L;

    // Quality constants based on common industry standards
    public static final String QUALITY_GOOD = "GOOD";
    public static final String QUALITY_QUESTIONABLE = "QUESTIONABLE";
    public static final String QUALITY_ESTIMATED = "ESTIMATED";
    public static final String QUALITY_INVALID = "INVALID";
    public static final String QUALITY_MISSING = "MISSING";
    public static final String QUALITY_FAILED = "FAILED";
    public static final String QUALITY_OVERFLOW = "OVERFLOW";
    public static final String QUALITY_PARTIAL = "PARTIAL";
    public static final String QUALITY_DERIVED = "DERIVED";
    public static final String QUALITY_SUBSTITUTED = "SUBSTITUTED";

    /**
     * Primary key for the reading quality.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Quality indicator for the reading.
     * Describes the reliability or condition of the associated reading value.
     */
    @Column(name = "quality", nullable = false, length = 50)
    @NotNull(message = "Quality cannot be null")
    @Size(max = 50, message = "Quality cannot exceed 50 characters")
    private String quality;

    /**
     * Interval reading associated with this quality indicator.
     * Many reading qualities can be associated with one interval reading.
     */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "interval_reading_id")
    private IntervalReadingEntity intervalReading;

    /**
     * Constructor with quality value.
     * 
     * @param quality the quality indicator
     */
    public ReadingQualityEntity(String quality) {
        this.quality = quality;
    }

    /**
     * Constructor with quality and interval reading.
     * 
     * @param quality the quality indicator
     * @param intervalReading the associated interval reading
     */
    public ReadingQualityEntity(String quality, IntervalReadingEntity intervalReading) {
        this.quality = quality;
        this.intervalReading = intervalReading;
    }

    /**
     * Gets the interval reading for this quality indicator.
     * Lombok @Data should generate this, but added manually for compilation.
     * 
     * @return the associated interval reading
     */
    public IntervalReadingEntity getIntervalReading() {
        return this.intervalReading;
    }

    /**
     * Sets the interval reading for this quality indicator.
     * This method ensures the bidirectional relationship is maintained.
     * 
     * @param intervalReading the interval reading to set
     */
    public void setIntervalReading(IntervalReadingEntity intervalReading) {
        // Remove from old interval reading if exists
        if (this.intervalReading != null && this.intervalReading != intervalReading) {
            this.intervalReading.removeReadingQuality(this);
        }
        
        this.intervalReading = intervalReading;
        
        // Add to new interval reading if not null
        if (intervalReading != null && !intervalReading.getReadingQualities().contains(this)) {
            intervalReading.addReadingQuality(this);
        }
    }

    /**
     * Checks if this quality indicates a good reading.
     * 
     * @return true if quality indicates good reading, false otherwise
     */
    public boolean isGoodQuality() {
        return QUALITY_GOOD.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a questionable reading.
     * 
     * @return true if quality indicates questionable reading, false otherwise
     */
    public boolean isQuestionableQuality() {
        return QUALITY_QUESTIONABLE.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates an estimated reading.
     * 
     * @return true if quality indicates estimated reading, false otherwise
     */
    public boolean isEstimatedQuality() {
        return QUALITY_ESTIMATED.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates an invalid reading.
     * 
     * @return true if quality indicates invalid reading, false otherwise
     */
    public boolean isInvalidQuality() {
        return QUALITY_INVALID.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a missing reading.
     * 
     * @return true if quality indicates missing reading, false otherwise
     */
    public boolean isMissingQuality() {
        return QUALITY_MISSING.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a failed reading.
     * 
     * @return true if quality indicates failed reading, false otherwise
     */
    public boolean isFailedQuality() {
        return QUALITY_FAILED.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates an overflow reading.
     * 
     * @return true if quality indicates overflow reading, false otherwise
     */
    public boolean isOverflowQuality() {
        return QUALITY_OVERFLOW.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a partial reading.
     * 
     * @return true if quality indicates partial reading, false otherwise
     */
    public boolean isPartialQuality() {
        return QUALITY_PARTIAL.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a derived reading.
     * 
     * @return true if quality indicates derived reading, false otherwise
     */
    public boolean isDerivedQuality() {
        return QUALITY_DERIVED.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a substituted reading.
     * 
     * @return true if quality indicates substituted reading, false otherwise
     */
    public boolean isSubstitutedQuality() {
        return QUALITY_SUBSTITUTED.equalsIgnoreCase(quality);
    }

    /**
     * Checks if this quality indicates a problematic reading.
     * A reading is considered problematic if it's not good quality.
     * 
     * @return true if quality indicates problematic reading, false otherwise
     */
    public boolean isProblematicQuality() {
        return !isGoodQuality();
    }

    /**
     * Checks if this quality indicates the reading should be excluded from calculations.
     * Typically invalid, missing, or failed readings should be excluded.
     * 
     * @return true if reading should be excluded, false otherwise
     */
    public boolean shouldExcludeFromCalculations() {
        return isInvalidQuality() || isMissingQuality() || isFailedQuality();
    }

    /**
     * Checks if this quality indicates the reading can be used with caution.
     * Typically questionable, estimated, or partial readings can be used with caution.
     * 
     * @return true if reading can be used with caution, false otherwise
     */
    public boolean canUseWithCaution() {
        return isQuestionableQuality() || isEstimatedQuality() || isPartialQuality() || 
               isDerivedQuality() || isSubstitutedQuality();
    }

    /**
     * Gets the quality severity level.
     * Returns a numeric value indicating the severity of the quality issue.
     * 
     * @return severity level (0 = good, higher values = more severe)
     */
    public int getQualitySeverityLevel() {
        if (isGoodQuality()) return 0;
        if (isDerivedQuality() || isSubstitutedQuality()) return 1;
        if (isEstimatedQuality()) return 2;
        if (isPartialQuality()) return 3;
        if (isQuestionableQuality()) return 4;
        if (isOverflowQuality()) return 5;
        if (isFailedQuality()) return 6;
        if (isMissingQuality()) return 7;
        if (isInvalidQuality()) return 8;
        return 9; // Unknown quality
    }

    /**
     * Gets a human-readable description of the quality.
     * 
     * @return quality description string
     */
    public String getQualityDescription() {
        if (quality == null) return "Unknown";
        
        switch (quality.toUpperCase()) {
            case QUALITY_GOOD:
                return "Good - Reading is valid and reliable";
            case QUALITY_QUESTIONABLE:
                return "Questionable - Reading may have issues";
            case QUALITY_ESTIMATED:
                return "Estimated - Reading is an estimate";
            case QUALITY_INVALID:
                return "Invalid - Reading is not valid";
            case QUALITY_MISSING:
                return "Missing - No reading available";
            case QUALITY_FAILED:
                return "Failed - Reading collection failed";
            case QUALITY_OVERFLOW:
                return "Overflow - Reading exceeded measurement range";
            case QUALITY_PARTIAL:
                return "Partial - Reading is incomplete";
            case QUALITY_DERIVED:
                return "Derived - Reading calculated from other values";
            case QUALITY_SUBSTITUTED:
                return "Substituted - Reading replaced with substitute value";
            default:
                return "Custom quality: " + quality;
        }
    }

    /**
     * Validates the quality value.
     * 
     * @return true if quality is valid, false otherwise
     */
    public boolean isValidQuality() {
        if (quality == null || quality.trim().isEmpty()) {
            return false;
        }
        
        // Check if it's one of the standard qualities
        String upperQuality = quality.toUpperCase();
        return upperQuality.equals(QUALITY_GOOD) ||
               upperQuality.equals(QUALITY_QUESTIONABLE) ||
               upperQuality.equals(QUALITY_ESTIMATED) ||
               upperQuality.equals(QUALITY_INVALID) ||
               upperQuality.equals(QUALITY_MISSING) ||
               upperQuality.equals(QUALITY_FAILED) ||
               upperQuality.equals(QUALITY_OVERFLOW) ||
               upperQuality.equals(QUALITY_PARTIAL) ||
               upperQuality.equals(QUALITY_DERIVED) ||
               upperQuality.equals(QUALITY_SUBSTITUTED) ||
               // Allow custom qualities if they're reasonable length
               (quality.length() <= 50 && quality.matches("[A-Z_][A-Z0-9_]*"));
    }

    /**
     * Gets a normalized quality value.
     * Converts quality to uppercase and trims whitespace.
     * 
     * @return normalized quality string
     */
    public String getNormalizedQuality() {
        return quality != null ? quality.trim().toUpperCase() : null;
    }

    /**
     * Compares this reading quality with another for business equality.
     * Two reading qualities are considered equal if they have the same quality value.
     * 
     * @param other the other reading quality to compare
     * @return true if business equal, false otherwise
     */
    public boolean isBusinessEqual(ReadingQualityEntity other) {
        if (other == null) {
            return false;
        }
        return java.util.Objects.equals(getNormalizedQuality(), other.getNormalizedQuality());
    }

    /**
     * Gets a summary string for this reading quality.
     * 
     * @return summary string with key information
     */
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Quality: ").append(quality != null ? quality : "null");
        summary.append(" (Severity: ").append(getQualitySeverityLevel()).append(")");
        
        if (intervalReading != null) {
            summary.append(" for Interval Reading ID: ").append(intervalReading.getId());
        }
        
        return summary.toString();
    }

    /**
     * Pre-persist callback to normalize quality value.
     */
    @PrePersist
    @PreUpdate
    protected void normalizeQuality() {
        if (quality != null) {
            quality = quality.trim().toUpperCase();
        }
    }
}