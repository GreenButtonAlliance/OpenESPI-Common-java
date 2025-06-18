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

/**
 * ServiceDeliveryPoint DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a physical location where energy services are delivered to a customer.
 * This is typically associated with a physical address and represents the endpoint
 * of the utility's distribution system where energy is consumed.
 * 
 * ServiceDeliveryPoint is an embedded element within UsagePoint and contains only
 * ESPI business data - no Atom metadata (links, timestamps) as it's not a standalone resource.
 */
@XmlRootElement(name = "ServiceDeliveryPoint", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ServiceDeliveryPoint", namespace = "http://naesb.org/espi", propOrder = {
    "description", "name", "tariffProfile", "customerAgreement", "tariffRiderRefs"
})
public record ServiceDeliveryPointDto(
    
    Long id,
    String uuid,
    String description,
    String name,
    String tariffProfile,
    String customerAgreement,
    TariffRiderRefsDto tariffRiderRefs
) {
    
    @XmlTransient
    public Long getId() {
        return id;
    }
    
    @XmlAttribute(name = "mRID")
    public String getUuid() {
        return uuid;
    }
    
    /**
     * Human-readable description of the service delivery point.
     * Typically describes the location or purpose of the delivery point.
     */
    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }
    
    /**
     * The name is any free human readable and possibly non unique text 
     * naming the service delivery point object.
     */
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }
    
    /**
     * A schedule of charges; structure associated with Tariff that allows 
     * the definition of complex tariff structures such as step and time of use.
     */
    @XmlElement(name = "tariffProfile")
    public String getTariffProfile() {
        return tariffProfile;
    }
    
    /**
     * Agreement between the customer and the ServiceSupplier to pay for 
     * service at a specific service location.
     */
    @XmlElement(name = "customerAgreement")
    public String getCustomerAgreement() {
        return customerAgreement;
    }
    
    /**
     * List of rate options applied to the base tariff profile.
     * Contains enrollment status and effective dates for each rider.
     */
    @XmlElement(name = "tariffRiderRefs")
    public TariffRiderRefsDto getTariffRiderRefs() {
        return tariffRiderRefs;
    }
    
    /**
     * Default constructor for JAXB.
     */
    public ServiceDeliveryPointDto() {
        this(null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic service delivery point data.
     */
    public ServiceDeliveryPointDto(String uuid, String name) {
        this(null, uuid, null, name, null, null, null);
    }
    
    /**
     * Constructor with full service delivery point information.
     */
    public ServiceDeliveryPointDto(String uuid, String name, String tariffProfile, 
                                 String customerAgreement, TariffRiderRefsDto tariffRiderRefs) {
        this(null, uuid, null, name, tariffProfile, customerAgreement, tariffRiderRefs);
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
     * Checks if this service delivery point has tariff riders configured.
     * 
     * @return true if tariff riders are present, false otherwise
     */
    public boolean hasTariffRiders() {
        return tariffRiderRefs != null && !tariffRiderRefs.isEmpty();
    }
    
    /**
     * Gets the number of active (enrolled) tariff riders.
     * 
     * @return count of active tariff riders
     */
    public int getActiveTariffRiderCount() {
        return hasTariffRiders() ? tariffRiderRefs.getActiveRiders().size() : 0;
    }
    
    /**
     * Gets a summary of tariff rider configuration.
     * 
     * @return formatted summary string
     */
    public String getTariffRiderSummary() {
        if (!hasTariffRiders()) {
            return "No tariff riders configured";
        }
        return tariffRiderRefs.getSummary();
    }
    
    /**
     * Validates the service delivery point configuration.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty();
    }
    
    /**
     * Creates a residential service delivery point with common settings.
     * 
     * @param uuid unique identifier
     * @param name display name
     * @param customerAgreement customer agreement reference
     * @return ServiceDeliveryPoint with residential configuration
     */
    public static ServiceDeliveryPointDto createResidential(String uuid, String name, String customerAgreement) {
        return new ServiceDeliveryPointDto(
            null,
            uuid,
            "Residential electric service delivery point",
            name,
            "RESIDENTIAL_TOU",
            customerAgreement,
            TariffRiderRefsDto.createResidentialRiders()
        );
    }
}