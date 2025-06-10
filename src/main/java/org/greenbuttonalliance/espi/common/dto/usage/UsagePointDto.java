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

import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.dto.atom.LinkDto;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * UsagePoint DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a logical point on a network at which consumption or production
 * is either physically measured (e.g., metered) or estimated (e.g., unmetered street lights).
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "UsagePoint", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsagePoint", namespace = "http://naesb.org/espi", propOrder = {
    "id", "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "roleFlags", "serviceCategory", "status", "serviceDeliveryPoint",
    "meterReadings", "usageSummaries", "electricPowerQualitySummaries"
})
public record UsagePointDto(
    
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
    
    @XmlElement(name = "roleFlags", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    byte[] roleFlags,
    
    @XmlElement(name = "ServiceCategory")
    ServiceCategory serviceCategory,
    
    @XmlElement(name = "status")
    Short status,
    
    @XmlElement(name = "ServiceDeliveryPoint")
    ServiceDeliveryPointDto serviceDeliveryPoint,
    
    @XmlElement(name = "MeterReading")
    @XmlElementWrapper(name = "MeterReadings")
    List<MeterReadingDto> meterReadings,
    
    @XmlElement(name = "UsageSummary")
    @XmlElementWrapper(name = "UsageSummaries")
    List<UsageSummaryDto> usageSummaries,
    
    @XmlElement(name = "ElectricPowerQualitySummary")
    @XmlElementWrapper(name = "ElectricPowerQualitySummaries")
    List<ElectricPowerQualitySummaryDto> electricPowerQualitySummaries
) {
    
    /**
     * Default constructor for JAXB.
     */
    public UsagePointDto() {
        this(null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic usage point data.
     */
    public UsagePointDto(String uuid, ServiceCategory serviceCategory) {
        this(null, uuid, null, null, null, null, null, null,
             null, serviceCategory, null, null, null, null, null);
    }
    
    /**
     * Gets the self href for this usage point.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return selfLink != null ? selfLink.href() : null;
    }
    
    /**
     * Gets the up href for this usage point.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return upLink != null ? upLink.href() : null;
    }
    
    /**
     * Generates the default self href for a usage point.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        return uuid != null ? "/espi/1_1/resource/UsagePoint/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a usage point.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        return "/espi/1_1/resource/UsagePoint";
    }
    
    /**
     * Gets the total number of meter readings.
     * 
     * @return meter reading count
     */
    public int getMeterReadingCount() {
        return meterReadings != null ? meterReadings.size() : 0;
    }
    
    /**
     * Gets the total number of usage summaries.
     * 
     * @return usage summary count
     */
    public int getUsageSummaryCount() {
        return usageSummaries != null ? usageSummaries.size() : 0;
    }
}