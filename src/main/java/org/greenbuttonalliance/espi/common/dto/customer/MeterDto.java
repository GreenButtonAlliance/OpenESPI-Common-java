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

package org.greenbuttonalliance.espi.common.dto.customer;

import org.greenbuttonalliance.espi.common.dto.atom.LinkDto;

import jakarta.xml.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Meter DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a meter device extending EndDevice with meter-specific functionality.
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "Meter", namespace = "http://naesb.org/espi/customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Meter", namespace = "http://naesb.org/espi/customer", propOrder = {
    "id", "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "amrSystem", "installCode", "isPan", "installDate", 
    "removedDate", "serialNumber", "formNumber", "kh", "meterMultiplier",
    "serviceLocation"
})
public record MeterDto(
    
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
    
    // EndDevice fields
    @XmlElement(name = "amrSystem")
    String amrSystem,
    
    @XmlElement(name = "installCode")
    String installCode,
    
    @XmlElement(name = "isPan")
    Boolean isPan,
    
    @XmlElement(name = "installDate")
    OffsetDateTime installDate,
    
    @XmlElement(name = "removedDate")
    OffsetDateTime removedDate,
    
    @XmlElement(name = "serialNumber")
    String serialNumber,
    
    // Meter-specific fields
    @XmlElement(name = "formNumber")
    String formNumber,
    
    @XmlElement(name = "kh")
    Double kh,
    
    @XmlElement(name = "meterMultiplier")
    Double meterMultiplier,
    
    @XmlElement(name = "ServiceLocation")
    ServiceLocationDto serviceLocation
) {
    
    /**
     * Default constructor for JAXB.
     */
    public MeterDto() {
        this(null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic meter data.
     */
    public MeterDto(String uuid, String serialNumber, String formNumber) {
        this(null, uuid, null, null, null, null, null, null,
             null, null, null, null, null, serialNumber, formNumber, null, null, null);
    }
    
    /**
     * Gets the self href for this meter.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return selfLink != null ? selfLink.href() : null;
    }
    
    /**
     * Gets the up href for this meter.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return upLink != null ? upLink.href() : null;
    }
    
    /**
     * Generates the default self href for a meter.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        if (uuid != null && serviceLocation != null && serviceLocation.uuid() != null) {
            return "/espi/1_1/resource/ServiceLocation/" + serviceLocation.uuid() + "/Meter/" + uuid;
        }
        return uuid != null ? "/espi/1_1/resource/Meter/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a meter.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        if (serviceLocation != null && serviceLocation.uuid() != null) {
            return "/espi/1_1/resource/ServiceLocation/" + serviceLocation.uuid() + "/Meter";
        }
        return "/espi/1_1/resource/Meter";
    }
}