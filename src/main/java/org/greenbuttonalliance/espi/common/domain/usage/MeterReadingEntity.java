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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for MeterReading without JAXB concerns.
 * 
 * Represents a set of values obtained from the meter. Contains interval blocks
 * with specific reading data and associated reading type information.
 */
@Entity
@Table(name = "meter_readings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"intervalBlocks", "usagePoint", "readingType"})
public class MeterReadingEntity extends IdentifiedObjectEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Usage point that this meter reading belongs to.
     * Many meter readings can belong to one usage point.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_point_id")
    private UsagePointEntity usagePoint;

    /**
     * Reading type that defines what this meter reading measures.
     * Many meter readings can share the same reading type.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reading_type_id")
    private ReadingTypeEntity readingType;

    /**
     * Interval blocks containing the actual meter reading data.
     * One-to-many relationship with cascade and orphan removal.
     * Uses lazy loading for performance.
     */
    @OneToMany(mappedBy = "meterReading", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<IntervalBlockEntity> intervalBlocks = new ArrayList<>();

    /**
     * Adds an interval block to this meter reading.
     * Sets up the bidirectional relationship.
     * 
     * @param intervalBlock the interval block to add
     */
    public void addIntervalBlock(IntervalBlockEntity intervalBlock) {
        if (intervalBlock != null) {
            intervalBlock.setMeterReading(this);
            intervalBlocks.add(intervalBlock);
        }
    }

    /**
     * Removes an interval block from this meter reading.
     * Clears the bidirectional relationship.
     * 
     * @param intervalBlock the interval block to remove
     */
    public void removeIntervalBlock(IntervalBlockEntity intervalBlock) {
        if (intervalBlock != null) {
            intervalBlock.setMeterReading(null);
            intervalBlocks.remove(intervalBlock);
        }
    }

    /**
     * Removes the reading type from this meter reading.
     * Used for unlinking relationships.
     * 
     * @param readingType the reading type to remove (parameter for consistency)
     */
    public void removeReadingType(ReadingTypeEntity readingType) {
        this.readingType = null;
    }

    /**
     * Sets the usage point for this meter reading.
     * This method ensures the bidirectional relationship is maintained.
     * 
     * @param usagePoint the usage point to set
     */
    public void setUsagePoint(UsagePointEntity usagePoint) {
        // Remove from old usage point if exists
        if (this.usagePoint != null && this.usagePoint != usagePoint) {
            this.usagePoint.removeMeterReading(this);
        }
        
        this.usagePoint = usagePoint;
        
        // Add to new usage point if not null
        if (usagePoint != null && !usagePoint.getMeterReadings().contains(this)) {
            usagePoint.addMeterReading(this);
        }
    }

    /**
     * Generates the self href for this meter reading.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        if (usagePoint != null) {
            return usagePoint.getSelfHref() + "/MeterReading/" + getHashedId();
        }
        return "/espi/1_1/resource/MeterReading/" + getHashedId();
    }

    /**
     * Generates the up href for this meter reading.
     * 
     * @return up href string pointing to the usage point
     */
    public String getUpHref() {
        if (usagePoint != null) {
            return usagePoint.getSelfHref() + "/MeterReading";
        }
        return "/espi/1_1/resource/MeterReading";
    }

    /**
     * Overrides the default self href generation to use meter reading specific logic.
     * 
     * @return self href for this meter reading
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use meter reading specific logic.
     * 
     * @return up href for this meter reading
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another MeterReadingEntity.
     * Handles interval blocks with UUID validation and replacement logic.
     * 
     * @param other the other meter reading entity to merge from
     */
    public void merge(MeterReadingEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Merge interval blocks with replacement logic
            for (IntervalBlockEntity otherBlock : other.getIntervalBlocks()) {
                // TODO: Validate that the UUIDs are equal!!
                if (this.intervalBlocks.contains(otherBlock)) {
                    this.intervalBlocks.remove(otherBlock);
                }
                this.intervalBlocks.add(otherBlock);
                otherBlock.setMeterReading(this);
            }
            
            // Update reading type if provided
            if (other.readingType != null) {
                this.readingType = other.readingType;
            }
            
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
        
        // Clear interval blocks
        for (IntervalBlockEntity block : new ArrayList<>(intervalBlocks)) {
            removeIntervalBlock(block);
        }
        intervalBlocks.clear();
        
        // Clear relationships
        setReadingType(null);
        setUsagePoint(null);
    }

    /**
     * Sets up the parent relationship with a usage point.
     * Used by the resource management system.
     * 
     * @param resource the parent usage point resource
     */
    public void setUpResource(IdentifiedObjectEntity resource) {
        if (resource instanceof UsagePointEntity usagePoint) {
            usagePoint.addMeterReading(this);
        }
    }
}