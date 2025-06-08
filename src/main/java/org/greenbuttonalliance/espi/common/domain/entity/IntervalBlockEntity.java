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

package org.greenbuttonalliance.espi.common.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.DateTimeInterval;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for IntervalBlock without JAXB concerns.
 * 
 * Represents a time sequence of readings of the same ReadingType.
 * Contains a date/time interval and a collection of interval readings.
 */
@Entity
@Table(name = "interval_blocks", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@NamedQueries({
    @NamedQuery(
        name = "IntervalBlockEntity.findAllByMeterReadingId",
        query = "SELECT ib FROM IntervalBlockEntity ib WHERE ib.meterReading.id = :meterReadingId"
    ),
    @NamedQuery(
        name = "IntervalBlockEntity.findById",
        query = "SELECT ib FROM IntervalBlockEntity ib WHERE ib.id = :id"
    ),
    @NamedQuery(
        name = "IntervalBlockEntity.findByUuid",
        query = "SELECT ib FROM IntervalBlockEntity ib WHERE ib.uuid = :uuid"
    ),
    @NamedQuery(
        name = "IntervalBlockEntity.findAllIdsByUsagePointId",
        query = "SELECT ib.id FROM IntervalBlockEntity ib WHERE ib.meterReading.usagePoint.id = :usagePointId"
    ),
    @NamedQuery(
        name = "IntervalBlockEntity.findAllIds",
        query = "SELECT ib.id FROM IntervalBlockEntity ib"
    ),
    @NamedQuery(
        name = "IntervalBlockEntity.findAllIdsByXpath",
        query = "SELECT DISTINCT ib.id FROM UsagePointEntity up, MeterReadingEntity mr, IntervalBlockEntity ib WHERE up.retailCustomer.id = :o1Id AND mr.usagePoint.id = :o2Id AND ib.meterReading.id = :o3Id"
    ),
    @NamedQuery(
        name = "IntervalBlockEntity.findIdByXpath",
        query = "SELECT DISTINCT ib.id FROM UsagePointEntity up, MeterReadingEntity mr, IntervalBlockEntity ib WHERE up.retailCustomer.id = :o1Id AND mr.usagePoint.id = :o2Id AND ib.meterReading.id = :o3Id AND ib.id = :o4Id"
    )
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"intervalReadings", "meterReading"})
public class IntervalBlockEntity extends IdentifiedObjectEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Date and time interval for this block.
     * Embedded value object containing start time and duration.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "interval_start")),
        @AttributeOverride(name = "duration", column = @Column(name = "interval_duration"))
    })
    private DateTimeInterval interval;

    /**
     * Meter reading that this interval block belongs to.
     * Many interval blocks can belong to one meter reading.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_reading_id")
    private MeterReadingEntity meterReading;

    /**
     * Collection of interval readings within this block.
     * One-to-many relationship with cascade operations.
     * Uses eager loading for performance.
     */
    @OneToMany(mappedBy = "intervalBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<IntervalReadingEntity> intervalReadings = new ArrayList<>();

    /**
     * Constructor with interval parameters.
     * 
     * @param interval the date/time interval for this block
     */
    public IntervalBlockEntity(DateTimeInterval interval) {
        this.interval = interval;
    }

    /**
     * Adds an interval reading to this block.
     * Sets up the bidirectional relationship.
     * 
     * @param intervalReading the interval reading to add
     */
    public void addIntervalReading(IntervalReadingEntity intervalReading) {
        if (intervalReading != null) {
            this.intervalReadings.add(intervalReading);
            intervalReading.setIntervalBlock(this);
        }
    }

    /**
     * Removes an interval reading from this block.
     * Clears the bidirectional relationship.
     * 
     * @param intervalReading the interval reading to remove
     */
    public void removeIntervalReading(IntervalReadingEntity intervalReading) {
        if (intervalReading != null) {
            this.intervalReadings.remove(intervalReading);
            intervalReading.setIntervalBlock(null);
        }
    }

    /**
     * Sets the meter reading for this interval block.
     * This method ensures the bidirectional relationship is maintained.
     * 
     * @param meterReading the meter reading to set
     */
    public void setMeterReading(MeterReadingEntity meterReading) {
        // Remove from old meter reading if exists
        if (this.meterReading != null && this.meterReading != meterReading) {
            this.meterReading.removeIntervalBlock(this);
        }
        
        this.meterReading = meterReading;
        
        // Add to new meter reading if not null
        if (meterReading != null && !meterReading.getIntervalBlocks().contains(this)) {
            meterReading.addIntervalBlock(this);
        }
    }

    /**
     * Generates the self href for this interval block.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        if (meterReading != null) {
            return meterReading.getSelfHref() + "/IntervalBlock/" + getHashedId();
        }
        return "/espi/1_1/resource/IntervalBlock/" + getHashedId();
    }

    /**
     * Generates the up href for this interval block.
     * 
     * @return up href string pointing to the meter reading
     */
    public String getUpHref() {
        if (meterReading != null) {
            return meterReading.getSelfHref() + "/IntervalBlock";
        }
        return "/espi/1_1/resource/IntervalBlock";
    }

    /**
     * Overrides the default self href generation to use interval block specific logic.
     * 
     * @return self href for this interval block
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use interval block specific logic.
     * 
     * @return up href for this interval block
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another IntervalBlockEntity.
     * Updates interval and replaces interval readings with bidirectional setup.
     * 
     * @param other the other interval block entity to merge from
     */
    public void merge(IntervalBlockEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update interval
            this.interval = other.interval;
            
            // Replace interval readings and set up bidirectional relationships
            this.intervalReadings.clear();
            if (other.intervalReadings != null) {
                this.intervalReadings.addAll(other.intervalReadings);
                
                // Set up bidirectional relationships
                Iterator<IntervalReadingEntity> readings = this.intervalReadings.iterator();
                while (readings.hasNext()) {
                    IntervalReadingEntity reading = readings.next();
                    reading.setIntervalBlock(this);
                }
            }
            
            // Update meter reading if provided
            if (other.meterReading != null) {
                this.meterReading = other.meterReading;
            }
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Clear interval readings
        for (IntervalReadingEntity reading : new ArrayList<>(intervalReadings)) {
            removeIntervalReading(reading);
        }
        intervalReadings.clear();
        
        // Clear relationships
        setMeterReading(null);
    }

    /**
     * Sets up the parent relationship with a meter reading.
     * Used by the resource management system.
     * 
     * @param resource the parent meter reading resource
     */
    public void setUpResource(IdentifiedObjectEntity resource) {
        if (resource instanceof MeterReadingEntity meterReading) {
            meterReading.addIntervalBlock(this);
        }
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
}