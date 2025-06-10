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
 * Statement DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a billing statement or document for a customer agreement.
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "Statement", namespace = "http://naesb.org/espi/customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Statement", namespace = "http://naesb.org/espi/customer", propOrder = {
    "id", "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "createdDateTime", "lastModifiedDateTime", "revisionNumber",
    "subject", "docStatus", "type", "customerAgreement", "statementRefs"
})
public record StatementDto(
    
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
    
    @XmlElement(name = "createdDateTime")
    OffsetDateTime createdDateTime,
    
    @XmlElement(name = "lastModifiedDateTime")
    OffsetDateTime lastModifiedDateTime,
    
    @XmlElement(name = "revisionNumber")
    String revisionNumber,
    
    @XmlElement(name = "subject")
    String subject,
    
    @XmlElement(name = "docStatus")
    String docStatus,
    
    @XmlElement(name = "type")
    String type,
    
    @XmlElement(name = "CustomerAgreement")
    CustomerAgreementDto customerAgreement,
    
    @XmlElement(name = "StatementRef")
    @XmlElementWrapper(name = "StatementRefs")
    List<StatementRefDto> statementRefs
) {
    
    /**
     * Default constructor for JAXB.
     */
    public StatementDto() {
        this(null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic statement data.
     */
    public StatementDto(String uuid, String subject) {
        this(null, uuid, null, null, null, null, null, null,
             null, null, null, subject, null, null, null, null);
    }
    
    /**
     * Gets the self href for this statement.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return selfLink != null ? selfLink.href() : null;
    }
    
    /**
     * Gets the up href for this statement.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return upLink != null ? upLink.href() : null;
    }
    
    /**
     * Generates the default self href for a statement.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        if (uuid != null && customerAgreement != null && customerAgreement.uuid() != null) {
            return "/espi/1_1/resource/CustomerAgreement/" + customerAgreement.uuid() + "/Statement/" + uuid;
        }
        return uuid != null ? "/espi/1_1/resource/Statement/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a statement.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        if (customerAgreement != null && customerAgreement.uuid() != null) {
            return "/espi/1_1/resource/CustomerAgreement/" + customerAgreement.uuid() + "/Statement";
        }
        return "/espi/1_1/resource/Statement";
    }
}