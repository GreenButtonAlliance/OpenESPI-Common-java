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

import org.greenbuttonalliance.espi.common.domain.customer.enums.CustomerKind;
import org.greenbuttonalliance.espi.common.dto.atom.LinkDto;

import jakarta.xml.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Customer DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a customer entity containing Personal Identifiable Information (PII)
 * separate from usage data. Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "Customer", namespace = "http://naesb.org/espi/customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", namespace = "http://naesb.org/espi/customer", propOrder = {
    "id", "uuid", "published", "updated", "selfLink", "upLink", "relatedLinks",
    "description", "kind", "specialNeed", "status", "pucNumber", "customerAccounts"
})
public record CustomerDto(
    
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
    
    @XmlElement(name = "kind")
    CustomerKind kind,
    
    @XmlElement(name = "specialNeed")
    String specialNeed,
    
    @XmlElement(name = "status")
    String status,
    
    @XmlElement(name = "pucNumber")
    String pucNumber,
    
    @XmlElement(name = "CustomerAccount")
    @XmlElementWrapper(name = "CustomerAccounts")
    List<CustomerAccountDto> customerAccounts
) {
    
    /**
     * Default constructor for JAXB.
     */
    public CustomerDto() {
        this(null, null, null, null, null, null, null, null, 
             null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic customer data.
     */
    public CustomerDto(String uuid, CustomerKind kind) {
        this(null, uuid, null, null, null, null, null, null,
             kind, null, null, null, null);
    }
    
    /**
     * Gets the self href for this customer.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return selfLink != null ? selfLink.href() : null;
    }
    
    /**
     * Gets the up href for this customer.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return upLink != null ? upLink.href() : null;
    }
    
    /**
     * Generates the default self href for a customer.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        return uuid != null ? "/espi/1_1/resource/Customer/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a customer.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        return "/espi/1_1/resource/Customer";
    }
}