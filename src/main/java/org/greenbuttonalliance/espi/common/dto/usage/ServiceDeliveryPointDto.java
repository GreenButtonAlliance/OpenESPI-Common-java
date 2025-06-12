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
 * ServiceDeliveryPoint DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a physical location where energy services are delivered to a customer.
 * This is typically associated with a physical address and represents the endpoint
 * of the utility's distribution system where energy is consumed.
 * Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "ServiceDeliveryPoint", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDeliveryPoint", namespace = "http://naesb.org/espi", propOrder = {
    "published", "updated", "relatedLinks", "selfLink", "upLink", "description", "name", "tariffProfile", "customerAgreement"
})
public record ServiceDeliveryPointDto(
    
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
    
    @XmlElement(name = "name")
    String name,
    
    @XmlElement(name = "tariffProfile")
    String tariffProfile,
    
    @XmlElement(name = "customerAgreement")
    String customerAgreement
) {
    
    /**
     * Default constructor for JAXB.
     */
    public ServiceDeliveryPointDto() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic service delivery point data.
     */
    public ServiceDeliveryPointDto(String uuid, String name) {
        this(null, uuid, null, null, null, null, null, null, name, null, null);
    }
    
    /**
     * Constructor with full service delivery point information.
     */
    public ServiceDeliveryPointDto(String uuid, String name, String tariffProfile, String customerAgreement) {
        this(null, uuid, null, null, null, null, null, null, name, tariffProfile, customerAgreement);
    }
    
    /**
     * Generates the default self href for a service delivery point.
     * 
     * @return default self href
     */
    public String generateSelfHref() {
        return uuid != null ? "/espi/1_1/resource/ServiceDeliveryPoint/" + uuid : null;
    }
    
    /**
     * Generates the default up href for a service delivery point.
     * 
     * @return default up href
     */
    public String generateUpHref() {
        return "/espi/1_1/resource/ServiceDeliveryPoint";
    }
    
    /**
     * Gets a display name for this service delivery point.
     * Uses the name if available, otherwise creates a default display name.
     * 
     * @return display name string
     */
    public String getDisplayName() {
        if (name != null && !name.trim().isEmpty()) {
            return name.trim();
        }
        return "Service Delivery Point " + (uuid != null ? uuid : "Unknown");
    }
    
    /**
     * Checks if this service delivery point has a tariff profile assigned.
     * 
     * @return true if tariff profile is set, false otherwise
     */
    public boolean hasTariffProfile() {
        return tariffProfile != null && !tariffProfile.trim().isEmpty();
    }
    
    /**
     * Checks if this service delivery point has a customer agreement assigned.
     * 
     * @return true if customer agreement is set, false otherwise
     */
    public boolean hasCustomerAgreement() {
        return customerAgreement != null && !customerAgreement.trim().isEmpty();
    }
    
    /**
     * Validates the service delivery point configuration.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty();
    }
}