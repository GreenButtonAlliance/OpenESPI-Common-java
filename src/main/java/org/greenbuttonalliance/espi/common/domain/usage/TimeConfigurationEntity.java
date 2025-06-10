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

import jakarta.persistence.*;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for TimeConfiguration without JAXB concerns.
 * 
 * Contains attributes related to the configuration of time services for energy metering.
 * Manages timezone information, daylight saving time rules, and time offsets for
 * accurate time synchronization across energy data collection systems.
 * 
 * NOTE: This entity is shared between Usage and Customer schemas as both define
 * identical TimeConfiguration structures. The Customer schema TimeConfiguration
 * has the same fields and semantics as the Usage schema TimeConfiguration.
 */
@Entity
@Table(name = "time_configurations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"usagePoints"})
public class TimeConfigurationEntity extends IdentifiedObjectEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Daylight Saving Time end rule.
     * Binary data representing the rule for when DST ends.
     * Follows NAESB DstRuleType specification.
     */
    @Lob
    @Column(name = "dst_end_rule")
    private byte[] dstEndRule;

    /**
     * Daylight Saving Time offset in seconds.
     * Represents the time offset applied during DST period.
     */
    @Column(name = "dst_offset")
    private Long dstOffset;

    /**
     * Daylight Saving Time start rule.
     * Binary data representing the rule for when DST starts.
     * Follows NAESB DstRuleType specification.
     */
    @Lob
    @Column(name = "dst_start_rule")
    private byte[] dstStartRule;

    /**
     * Timezone offset in seconds from UTC.
     * Positive values are east of UTC, negative values are west of UTC.
     */
    @Column(name = "tz_offset")
    private Long tzOffset;

    /**
     * Usage points that use this time configuration.
     * One-to-many relationship since multiple usage points can share time configuration.
     */
    @OneToMany(mappedBy = "localTimeParameters", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UsagePointEntity> usagePoints = new ArrayList<>();

    /**
     * Constructor with timezone offset.
     * 
     * @param tzOffset the timezone offset in seconds from UTC
     */
    public TimeConfigurationEntity(Long tzOffset) {
        this.tzOffset = tzOffset;
    }

    /**
     * Constructor with full time configuration.
     * 
     * @param tzOffset the timezone offset in seconds from UTC
     * @param dstOffset the DST offset in seconds
     * @param dstStartRule the DST start rule
     * @param dstEndRule the DST end rule
     */
    public TimeConfigurationEntity(Long tzOffset, Long dstOffset, byte[] dstStartRule, byte[] dstEndRule) {
        this.tzOffset = tzOffset;
        this.dstOffset = dstOffset;
        this.dstStartRule = dstStartRule != null ? Arrays.copyOf(dstStartRule, dstStartRule.length) : null;
        this.dstEndRule = dstEndRule != null ? Arrays.copyOf(dstEndRule, dstEndRule.length) : null;
    }

    /**
     * Adds a usage point that uses this time configuration.
     * Sets up the bidirectional relationship.
     * 
     * @param usagePoint the usage point to add
     */
    public void addUsagePoint(UsagePointEntity usagePoint) {
        if (usagePoint != null) {
            this.usagePoints.add(usagePoint);
            usagePoint.setLocalTimeParameters(this);
        }
    }

    /**
     * Removes a usage point from this time configuration.
     * Clears the bidirectional relationship.
     * 
     * @param usagePoint the usage point to remove
     */
    public void removeUsagePoint(UsagePointEntity usagePoint) {
        if (usagePoint != null) {
            this.usagePoints.remove(usagePoint);
            usagePoint.setLocalTimeParameters(null);
        }
    }

    /**
     * Sets the DST start rule with proper array copying.
     * 
     * @param dstStartRule the DST start rule bytes
     */
    public void setDstStartRule(byte[] dstStartRule) {
        this.dstStartRule = dstStartRule != null ? Arrays.copyOf(dstStartRule, dstStartRule.length) : null;
    }

    /**
     * Gets the DST start rule with proper array copying.
     * 
     * @return copy of the DST start rule bytes
     */
    public byte[] getDstStartRule() {
        return dstStartRule != null ? Arrays.copyOf(dstStartRule, dstStartRule.length) : null;
    }

    /**
     * Sets the DST end rule with proper array copying.
     * 
     * @param dstEndRule the DST end rule bytes
     */
    public void setDstEndRule(byte[] dstEndRule) {
        this.dstEndRule = dstEndRule != null ? Arrays.copyOf(dstEndRule, dstEndRule.length) : null;
    }

    /**
     * Gets the DST end rule with proper array copying.
     * 
     * @return copy of the DST end rule bytes
     */
    public byte[] getDstEndRule() {
        return dstEndRule != null ? Arrays.copyOf(dstEndRule, dstEndRule.length) : null;
    }

    /**
     * Generates the self href for this time configuration.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return "/espi/1_1/resource/LocalTimeParameters/" + getHashedId();
    }

    /**
     * Generates the up href for this time configuration.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return "/espi/1_1/resource/LocalTimeParameters";
    }

    /**
     * Overrides the default self href generation to use time configuration specific logic.
     * 
     * @return self href for this time configuration
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use time configuration specific logic.
     * 
     * @return up href for this time configuration
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another TimeConfigurationEntity.
     * Updates all time configuration parameters with safe array copying.
     * 
     * @param other the other time configuration entity to merge from
     */
    public void merge(TimeConfigurationEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update time configuration parameters
            this.tzOffset = other.tzOffset;
            this.dstOffset = other.dstOffset;
            
            // Safe copying of byte arrays
            setDstStartRule(other.getDstStartRule());
            setDstEndRule(other.getDstEndRule());
            
            // Note: usagePoints collection is not merged to preserve existing relationships
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Clear usage points
        for (UsagePointEntity usagePoint : new ArrayList<>(usagePoints)) {
            removeUsagePoint(usagePoint);
        }
        usagePoints.clear();
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
     * Gets the timezone offset in hours.
     * 
     * @return timezone offset in hours, or null if not set
     */
    public Double getTzOffsetInHours() {
        return tzOffset != null ? tzOffset / 3600.0 : null;
    }

    /**
     * Sets the timezone offset from hours.
     * 
     * @param hours the timezone offset in hours
     */
    public void setTzOffsetInHours(Double hours) {
        this.tzOffset = hours != null ? Math.round(hours * 3600) : null;
    }

    /**
     * Gets the DST offset in hours.
     * 
     * @return DST offset in hours, or null if not set
     */
    public Double getDstOffsetInHours() {
        return dstOffset != null ? dstOffset / 3600.0 : null;
    }

    /**
     * Sets the DST offset from hours.
     * 
     * @param hours the DST offset in hours
     */
    public void setDstOffsetInHours(Double hours) {
        this.dstOffset = hours != null ? Math.round(hours * 3600) : null;
    }

    /**
     * Gets the effective timezone offset including DST.
     * 
     * @return total offset in seconds including DST
     */
    public Long getEffectiveOffset() {
        Long base = tzOffset != null ? tzOffset : 0L;
        Long dst = dstOffset != null ? dstOffset : 0L;
        return base + dst;
    }

    /**
     * Gets the effective timezone offset in hours including DST.
     * 
     * @return total offset in hours including DST
     */
    public Double getEffectiveOffsetInHours() {
        return getEffectiveOffset() / 3600.0;
    }

    /**
     * Creates a ZoneOffset from the timezone offset.
     * 
     * @return ZoneOffset object, or null if tzOffset is not set
     */
    public ZoneOffset getZoneOffset() {
        return tzOffset != null ? ZoneOffset.ofTotalSeconds(tzOffset.intValue()) : null;
    }

    /**
     * Creates a ZoneOffset including DST offset.
     * 
     * @return ZoneOffset object including DST, or null if offsets are not set
     */
    public ZoneOffset getEffectiveZoneOffset() {
        Long effective = getEffectiveOffset();
        return effective != null ? ZoneOffset.ofTotalSeconds(effective.intValue()) : null;
    }

    /**
     * Checks if this time configuration has DST rules defined.
     * 
     * @return true if DST rules are present, false otherwise
     */
    public boolean hasDstRules() {
        return dstStartRule != null && dstStartRule.length > 0 &&
               dstEndRule != null && dstEndRule.length > 0;
    }

    /**
     * Checks if DST is currently active (has non-zero offset).
     * 
     * @return true if DST offset is defined and non-zero, false otherwise
     */
    public boolean isDstActive() {
        return dstOffset != null && dstOffset != 0;
    }

    /**
     * Gets the number of usage points using this time configuration.
     * 
     * @return count of associated usage points
     */
    public int getUsagePointCount() {
        return usagePoints != null ? usagePoints.size() : 0;
    }

    /**
     * Creates a human-readable description of this time configuration.
     * 
     * @return formatted description string
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        
        if (tzOffset != null) {
            Double hours = getTzOffsetInHours();
            desc.append("UTC");
            if (hours >= 0) desc.append("+");
            desc.append(String.format("%.1f", hours));
        }
        
        if (isDstActive()) {
            Double dstHours = getDstOffsetInHours();
            desc.append(" (DST: ");
            if (dstHours >= 0) desc.append("+");
            desc.append(String.format("%.1f", dstHours));
            desc.append("h)");
        }
        
        if (hasDstRules()) {
            desc.append(" with DST rules");
        }
        
        return desc.length() > 0 ? desc.toString() : "No time configuration";
    }
}