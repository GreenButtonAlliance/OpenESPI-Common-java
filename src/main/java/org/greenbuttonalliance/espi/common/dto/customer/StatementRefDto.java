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
 * StatementRef DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a reference to a statement document.
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "StatementRef", namespace = "http://naesb.org/espi/customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatementRef", namespace = "http://naesb.org/espi/customer", propOrder = {
    "id", "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "referenceId", "referenceType", "referenceDate", 
    "referenceUrl", "statement"
})
public record StatementRefDto(
    
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
    
    @XmlElement(name = "referenceId")
    String referenceId,
    
    @XmlElement(name = "referenceType")
    String referenceType,
    
    @XmlElement(name = "referenceDate")
    OffsetDateTime referenceDate,
    
    @XmlElement(name = "referenceUrl")
    String referenceUrl,
    
    @XmlElement(name = "Statement")
    StatementDto statement
) {
    
    /**
     * Default constructor for JAXB.
     */
    public StatementRefDto() {
        this(null, null, null, null, null, null, null, null,
             null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic reference data.
     */
    public StatementRefDto(String uuid, String referenceId) {
        this(null, uuid, null, null, null, null, null, null,
             referenceId, null, null, null, null);
    }
    
    /**
     * Gets the self href for this statement reference.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return selfLink != null ? selfLink.href() : null;
    }
    
    /**
     * Gets the up href for this statement reference.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return upLink != null ? upLink.href() : null;
    }
    
    /**
     * Generates the default self href for a statement reference.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        if (uuid != null && statement != null && statement.uuid() != null) {
            return "/espi/1_1/resource/Statement/" + statement.uuid() + "/StatementRef/" + uuid;
        }
        return uuid != null ? "/espi/1_1/resource/StatementRef/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a statement reference.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        if (statement != null && statement.uuid() != null) {
            return "/espi/1_1/resource/Statement/" + statement.uuid() + "/StatementRef";
        }
        return "/espi/1_1/resource/StatementRef";
    }
}