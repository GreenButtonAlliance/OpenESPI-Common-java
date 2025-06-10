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

/**
 * DateTimeInterval DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a time interval with start and duration.
 * Used in various Green Button resources for time-based data.
 */
@XmlRootElement(name = "DateTimeInterval", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeInterval", namespace = "http://naesb.org/espi", propOrder = {
    "start", "duration"
})
public record DateTimeIntervalDto(
    
    @XmlElement(name = "start")
    Long start,
    
    @XmlElement(name = "duration")
    Long duration
) {
    
    /**
     * Default constructor for JAXB.
     */
    public DateTimeIntervalDto() {
        this(null, null);
    }
    
    /**
     * Constructor with OffsetDateTime conversion.
     */
    public DateTimeIntervalDto(OffsetDateTime startDateTime, Long duration) {
        this(startDateTime != null ? startDateTime.toEpochSecond() : null, duration);
    }
    
    /**
     * Gets the start time as OffsetDateTime.
     * 
     * @return start time as OffsetDateTime or null
     */
    public OffsetDateTime getStartDateTime() {
        return start != null ? OffsetDateTime.ofEpochSecond(start, 0, java.time.ZoneOffset.UTC) : null;
    }
    
    /**
     * Gets the end time as epoch seconds.
     * 
     * @return end time or null if start or duration is null
     */
    public Long getEnd() {
        return start != null && duration != null ? start + duration : null;
    }
    
    /**
     * Gets the end time as OffsetDateTime.
     * 
     * @return end time as OffsetDateTime or null
     */
    public OffsetDateTime getEndDateTime() {
        Long end = getEnd();
        return end != null ? OffsetDateTime.ofEpochSecond(end, 0, java.time.ZoneOffset.UTC) : null;
    }
}