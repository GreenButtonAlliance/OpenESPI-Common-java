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

package org.greenbuttonalliance.espi.common.domain.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.usage.IdentifiedObjectEntity;

import jakarta.persistence.*;

/**
 * Pure JPA/Hibernate entity for Organisation without JAXB concerns.
 * 
 * Organisation that might have roles as utility, customer, supplier, manufacturer, etc.
 */
@Entity
@Table(name = "organisations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class OrganisationEntity extends IdentifiedObjectEntity {

    /**
     * Organisation name (replaces deprecated 'name' field)
     */
    @Column(name = "organisation_name", length = 256)
    private String organisationName;

    /**
     * Street address for this organisation.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "streetDetail", column = @Column(name = "street_detail")),
        @AttributeOverride(name = "townDetail", column = @Column(name = "street_town")),
        @AttributeOverride(name = "stateOrProvince", column = @Column(name = "street_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "street_postal")),
        @AttributeOverride(name = "country", column = @Column(name = "street_country"))
    })
    private StreetAddress streetAddress;

    /**
     * Postal address for this organisation.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "streetDetail", column = @Column(name = "postal_detail")),
        @AttributeOverride(name = "townDetail", column = @Column(name = "postal_town")),
        @AttributeOverride(name = "stateOrProvince", column = @Column(name = "postal_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "postal_postal")),
        @AttributeOverride(name = "country", column = @Column(name = "postal_country"))
    })
    private StreetAddress postalAddress;

    /**
     * Primary phone number for this organisation.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "areaCode", column = @Column(name = "phone1_area_code")),
        @AttributeOverride(name = "cityCode", column = @Column(name = "phone1_city_code")),
        @AttributeOverride(name = "localNumber", column = @Column(name = "phone1_local_number")),
        @AttributeOverride(name = "extension", column = @Column(name = "phone1_extension"))
    })
    private PhoneNumber phone1;

    /**
     * Secondary phone number for this organisation.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "areaCode", column = @Column(name = "phone2_area_code")),
        @AttributeOverride(name = "cityCode", column = @Column(name = "phone2_city_code")),
        @AttributeOverride(name = "localNumber", column = @Column(name = "phone2_local_number")),
        @AttributeOverride(name = "extension", column = @Column(name = "phone2_extension"))
    })
    private PhoneNumber phone2;

    /**
     * Electronic address for this organisation.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "email1", column = @Column(name = "electronic_email1")),
        @AttributeOverride(name = "email2", column = @Column(name = "electronic_email2")),
        @AttributeOverride(name = "web", column = @Column(name = "electronic_web")),
        @AttributeOverride(name = "radio", column = @Column(name = "electronic_radio"))
    })
    private ElectronicAddress electronicAddress;

    /**
     * Embeddable class for StreetAddress
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class StreetAddress {
        @Column(name = "street_detail", length = 256)
        private String streetDetail;
        
        @Column(name = "town_detail", length = 256) 
        private String townDetail;
        
        @Column(name = "state_or_province", length = 256)
        private String stateOrProvince;
        
        @Column(name = "postal_code", length = 256)
        private String postalCode;
        
        @Column(name = "country", length = 256)
        private String country;
    }

    /**
     * Embeddable class for PhoneNumber
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class PhoneNumber {
        @Column(name = "area_code", length = 256)
        private String areaCode;
        
        @Column(name = "city_code", length = 256)
        private String cityCode;
        
        @Column(name = "local_number", length = 256)
        private String localNumber;
        
        @Column(name = "extension", length = 256)
        private String extension;
    }

    /**
     * Embeddable class for ElectronicAddress
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class ElectronicAddress {
        @Column(name = "email1", length = 256)
        private String email1;
        
        @Column(name = "email2", length = 256)
        private String email2;
        
        @Column(name = "web", length = 256)
        private String web;
        
        @Column(name = "radio", length = 256)
        private String radio;
    }
}