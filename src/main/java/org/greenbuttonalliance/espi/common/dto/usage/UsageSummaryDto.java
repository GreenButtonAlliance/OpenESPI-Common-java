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
 * UsageSummary DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents aggregated usage data for a usage point.
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "UsageSummary", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsageSummary", namespace = "http://naesb.org/espi", propOrder = {
    "id", "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "billingPeriod", "billLastPeriod", "billToDate", 
    "costAdditionalLastPeriod", "currency", "qualityOfReading", "statusTimeStamp"
})
public record UsageSummaryDto(
    
    @XmlTransient
    Long id,
    
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
    
    @XmlElement(name = "billingPeriod")
    DateTimeIntervalDto billingPeriod,
    
    @XmlElement(name = "billLastPeriod")
    Long billLastPeriod,
    
    @XmlElement(name = "billToDate")
    Long billToDate,
    
    @XmlElement(name = "costAdditionalLastPeriod")
    Long costAdditionalLastPeriod,
    
    @XmlElement(name = "currency")
    String currency,
    
    @XmlElement(name = "qualityOfReading")
    String qualityOfReading,
    
    @XmlElement(name = "statusTimeStamp")
    OffsetDateTime statusTimeStamp
) {
    
    /**
     * Default constructor for JAXB.
     */
    public UsageSummaryDto() {
        this(null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic usage summary data.
     */
    public UsageSummaryDto(String uuid) {
        this(null, uuid, null, null, null, null, null, null,
             null, null, null, null, null, null, null);
    }
}