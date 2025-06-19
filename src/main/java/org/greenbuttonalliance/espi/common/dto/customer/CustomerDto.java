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

import jakarta.xml.bind.annotation.*;
import java.time.OffsetDateTime;

/**
 * Customer DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents a customer entity containing Personal Identifiable Information (PII)
 * separate from usage data. Supports Atom protocol XML wrapping.
 */
@XmlRootElement(name = "Customer", namespace = "http://naesb.org/espi/customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Customer", namespace = "http://naesb.org/espi/customer", propOrder = {
    "organisationRole", "kind", "specialNeed", "vip", "pucNumber", "status", "priority", "locale", "customerName"
})
public record CustomerDto(
    
    @XmlTransient
    String uuid,
    
    @XmlElement(name = "OrganisationRole")
    OrganisationRoleDto organisationRole,
    
    @XmlElement(name = "kind")
    CustomerKind kind,
    
    @XmlElement(name = "specialNeed")
    String specialNeed,
    
    @XmlElement(name = "vip")
    Boolean vip,
    
    @XmlElement(name = "pucNumber")
    String pucNumber,
    
    @XmlElement(name = "status")
    StatusDto status,
    
    @XmlElement(name = "priority")
    PriorityDto priority,
    
    @XmlElement(name = "locale")
    String locale,
    
    @XmlElement(name = "customerName")
    String customerName
) {
    
    /**
     * Default constructor for JAXB.
     */
    public CustomerDto() {
        this(null, null, null, null, null, null, null, null, null, null);
    }
    
    /**
     * Minimal constructor for basic customer data.
     */
    public CustomerDto(String uuid, CustomerKind kind) {
        this(uuid, null, kind, null, null, null, null, null, null, null);
    }
    
    /**
     * Embeddable DTO for Status.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record StatusDto(
        @XmlElement(name = "value")
        String value,
        
        @XmlElement(name = "dateTime")
        OffsetDateTime dateTime,
        
        @XmlElement(name = "reason")
        String reason
    ) {
        public StatusDto() {
            this(null, null, null);
        }
    }
    
    /**
     * Embeddable DTO for Priority.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record PriorityDto(
        @XmlElement(name = "value")
        Integer value,
        
        @XmlElement(name = "rank")
        Integer rank,
        
        @XmlElement(name = "type")
        String type
    ) {
        public PriorityDto() {
            this(null, null, null);
        }
    }
    
    /**
     * Embeddable DTO for OrganisationRole.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record OrganisationRoleDto(
        @XmlElement(name = "organisation")
        OrganisationDto organisation
    ) {
        public OrganisationRoleDto() {
            this(null);
        }
    }
    
    /**
     * Embeddable DTO for Organisation.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record OrganisationDto(
        @XmlElement(name = "organisationName")
        String organisationName,
        
        @XmlElement(name = "streetAddress")
        StreetAddressDto streetAddress,
        
        @XmlElement(name = "postalAddress")
        StreetAddressDto postalAddress,
        
        @XmlElement(name = "phone1")
        PhoneNumberDto phone1,
        
        @XmlElement(name = "phone2")
        PhoneNumberDto phone2,
        
        @XmlElement(name = "electronicAddress")
        ElectronicAddressDto electronicAddress
    ) {
        public OrganisationDto() {
            this(null, null, null, null, null, null);
        }
    }
    
    /**
     * Embeddable DTO for StreetAddress.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record StreetAddressDto(
        @XmlElement(name = "streetDetail")
        String streetDetail,
        
        @XmlElement(name = "townDetail")
        String townDetail,
        
        @XmlElement(name = "stateOrProvince")
        String stateOrProvince,
        
        @XmlElement(name = "postalCode")
        String postalCode,
        
        @XmlElement(name = "country")
        String country
    ) {
        public StreetAddressDto() {
            this(null, null, null, null, null);
        }
    }
    
    /**
     * Embeddable DTO for PhoneNumber.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record PhoneNumberDto(
        @XmlElement(name = "areaCode")
        String areaCode,
        
        @XmlElement(name = "cityCode")
        String cityCode,
        
        @XmlElement(name = "localNumber")
        String localNumber,
        
        @XmlElement(name = "extension")
        String extension
    ) {
        public PhoneNumberDto() {
            this(null, null, null, null);
        }
    }
    
    /**
     * Embeddable DTO for ElectronicAddress.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static record ElectronicAddressDto(
        @XmlElement(name = "email1")
        String email1,
        
        @XmlElement(name = "email2")
        String email2,
        
        @XmlElement(name = "web")
        String web,
        
        @XmlElement(name = "radio")
        String radio
    ) {
        public ElectronicAddressDto() {
            this(null, null, null, null);
        }
    }
}