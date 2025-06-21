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
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;

import jakarta.persistence.*;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for ServiceLocation without JAXB concerns.
 * 
 * A real estate location, commonly referred to as premises.
 * 
 * This is an actual ESPI resource entity that extends IdentifiedObject directly.
 */
@Entity
@Table(name = "service_locations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"usagePoints"})
public class ServiceLocationEntity extends IdentifiedObject {

    // Location fields (previously inherited from Location superclass)
    
    /**
     * Classification by utility's corporate standards and practices, relative to the location itself 
     * (e.g., geographical, functional accounting, etc., not a given property that happens to exist at that location).
     */
    @Column(name = "type", length = 256)
    private String type;

    /**
     * Main address of the location.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "poBox", column = @Column(name = "main_po_box")),
        @AttributeOverride(name = "street", column = @Column(name = "main_street")),
        @AttributeOverride(name = "streetSuffix", column = @Column(name = "main_street_suffix")),
        @AttributeOverride(name = "suite", column = @Column(name = "main_suite"))
    })
    private Organisation.StreetAddress mainAddress;

    /**
     * Secondary address of the location. For example, PO Box address may have different ZIP code than that in the 'mainAddress'.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "poBox", column = @Column(name = "secondary_po_box")),
        @AttributeOverride(name = "street", column = @Column(name = "secondary_street")),
        @AttributeOverride(name = "streetSuffix", column = @Column(name = "secondary_street_suffix")),
        @AttributeOverride(name = "suite", column = @Column(name = "secondary_suite"))
    })
    private Organisation.StreetAddress secondaryAddress;

    /**
     * Phone number.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "areaCode", column = @Column(name = "phone1_area_code")),
        @AttributeOverride(name = "cityCode", column = @Column(name = "phone1_city_code")),
        @AttributeOverride(name = "localNumber", column = @Column(name = "phone1_local_number")),
        @AttributeOverride(name = "extension", column = @Column(name = "phone1_extension"))
    })
    private Organisation.PhoneNumber phone1;

    /**
     * Additional phone number.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "areaCode", column = @Column(name = "phone2_area_code")),
        @AttributeOverride(name = "cityCode", column = @Column(name = "phone2_city_code")),
        @AttributeOverride(name = "localNumber", column = @Column(name = "phone2_local_number")),
        @AttributeOverride(name = "extension", column = @Column(name = "phone2_extension"))
    })
    private Organisation.PhoneNumber phone2;

    /**
     * Electronic address.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "email1", column = @Column(name = "electronic_email1")),
        @AttributeOverride(name = "email2", column = @Column(name = "electronic_email2")),
        @AttributeOverride(name = "web", column = @Column(name = "electronic_web")),
        @AttributeOverride(name = "radio", column = @Column(name = "electronic_radio"))
    })
    private Organisation.ElectronicAddress electronicAddress;

    /**
     * (if applicable) Reference to geographical information source, often external to the utility.
     */
    @Column(name = "geo_info_reference", length = 256)
    private String geoInfoReference;

    /**
     * (if applicable) Direction that allows field crews to quickly find a given asset.
     */
    @Column(name = "direction", length = 256)
    private String direction;

    /**
     * Status of this location.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "status_value")),
        @AttributeOverride(name = "dateTime", column = @Column(name = "status_date_time")),
        @AttributeOverride(name = "reason", column = @Column(name = "status_reason"))
    })
    private CustomerEntity.Status status;

    // WorkLocation fields (WorkLocation is simply a Location specialized for work activities - no additional fields)

    // ServiceLocation specific fields

    /**
     * Method for the service person to access this service location. For example, a description of where to obtain 
     * a key if the facility is unmanned and secured.
     */
    @Column(name = "access_method", length = 256)
    private String accessMethod;

    /**
     * Problems previously encountered when visiting or performing work on this location. Examples include: 
     * bad dog, violent customer, verbally abusive occupant, obstructions, safety hazards, etc.
     */
    @Column(name = "site_access_problem", length = 256)
    private String siteAccessProblem;

    /**
     * True if inspection is needed of facilities at this service location. This could be requested by a customer, 
     * due to suspected tampering, environmental concerns (e.g., a fire in the vicinity), or to correct incompatible data.
     */
    @Column(name = "needs_inspection")
    private Boolean needsInspection;

    /**
     * All usage points delivering service (of the same type) to this service location.
     * TODO: Create UsagePointsEntity and enable this relationship
     */
    // @OneToMany(mappedBy = "serviceLocation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<UsagePointsEntity> usagePoints;

    /**
     * [extension] Outage Block Identifier
     */
    @Column(name = "outage_block", length = 32)
    private String outageBlock;

    // Explicit setter methods (Lombok should generate these, but adding for compatibility)
    
    /**
     * Sets whether this service location needs inspection.
     * @param needsInspection true if inspection is needed
     */
    public void setNeedsInspection(Boolean needsInspection) {
        this.needsInspection = needsInspection;
    }

    /**
     * Sets the site access problem description.
     * @param siteAccessProblem the access problem description
     */
    public void setSiteAccessProblem(String siteAccessProblem) {
        this.siteAccessProblem = siteAccessProblem;
    }
}