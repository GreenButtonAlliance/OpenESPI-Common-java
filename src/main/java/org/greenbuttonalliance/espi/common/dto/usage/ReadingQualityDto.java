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

import org.greenbuttonalliance.espi.common.dto.atom.LinkDto;

import jakarta.xml.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * ReadingQuality DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents quality indicators for readings, providing information about
 * the accuracy, validation status, and reliability of meter readings.
 */
@XmlRootElement(name = "ReadingQuality", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadingQuality", namespace = "http://naesb.org/espi", propOrder = {
    "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "quality"
})
public record ReadingQualityDto(
    
    @XmlAttribute(name = "mRID")
    String uuid,
    
    @XmlElement(name = "published")
    OffsetDateTime published,
    
    @XmlElement(name = "updated")
    OffsetDateTime updated,
    
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    @XmlElementWrapper(name = "links", namespace = "http://www.w3.org/2005/Atom")
    List<LinkDto> relatedLinks,
    
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    LinkDto selfLink,
    
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    LinkDto upLink,
    
    @XmlElement(name = "description")
    String description,
    
    @XmlElement(name = "quality")
    String quality
) {
    
    /**
     * Default constructor for JAXB.
     */
    public ReadingQualityDto() {
        this(null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic reading quality data.
     */
    public ReadingQualityDto(String uuid, String quality) {
        this(uuid, null, null, null, null, null, null, quality);
    }
    
    /**
     * Constructor with description.
     */
    public ReadingQualityDto(String uuid, String description, String quality) {
        this(uuid, null, null, null, null, null, description, quality);
    }
}