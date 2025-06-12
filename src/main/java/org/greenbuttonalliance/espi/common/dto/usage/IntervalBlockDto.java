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
import java.time.OffsetDateTime;
import java.util.List;

/**
 * IntervalBlock DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a time sequence of readings of the same ReadingType.
 * Contains a date/time interval and a collection of interval readings.
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "IntervalBlock", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntervalBlock", namespace = "http://naesb.org/espi", propOrder = {
    "published", "updated", "relatedLinks", "selfLink", "upLink", "description", "interval", "intervalReadings"
})
public record IntervalBlockDto(
    
    @XmlTransient
    Long id,
    
    @XmlAttribute(name = "mRID")
    String uuid,
    
    @XmlElement(name = "published")
    OffsetDateTime published,
    
    @XmlElement(name = "updated")
    OffsetDateTime updated,
    
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "relatedLinks")
    List<String> relatedLinks,
    
    @XmlElement(name = "selfLink")
    String selfLink,
    
    @XmlElement(name = "upLink")
    String upLink,
    
    @XmlElement(name = "description")
    String description,
    
    @XmlElement(name = "interval")
    DateTimeIntervalDto interval,
    
    @XmlElement(name = "IntervalReading")
    @XmlElementWrapper(name = "IntervalReadings")
    List<IntervalReadingDto> intervalReadings
) {
    
    /**
     * Default constructor for JAXB.
     */
    public IntervalBlockDto() {
        this(null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic interval block data.
     */
    public IntervalBlockDto(String uuid, DateTimeIntervalDto interval) {
        this(null, uuid, null, null, null, null, null, null, interval, null);
    }
    
    /**
     * Constructor with interval and readings.
     */
    public IntervalBlockDto(String uuid, DateTimeIntervalDto interval, List<IntervalReadingDto> intervalReadings) {
        this(null, uuid, null, null, null, null, null, null, interval, intervalReadings);
    }
    
    /**
     * Generates the default self href for an interval block.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        return uuid != null ? "/espi/1_1/resource/IntervalBlock/" + uuid : null;
    }
    
    /**
     * Generates the default up href for an interval block.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        return "/espi/1_1/resource/IntervalBlock";
    }
    
    /**
     * Gets the total number of interval readings.
     * 
     * @return interval reading count
     */
    public int getIntervalReadingCount() {
        return intervalReadings != null ? intervalReadings.size() : 0;
    }
}