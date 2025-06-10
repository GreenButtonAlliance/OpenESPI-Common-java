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

import jakarta.persistence.*;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for ServiceLocation without JAXB concerns.
 * 
 * A real estate location, commonly referred to as premises.
 */
@Entity
@Table(name = "service_locations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"usagePoints"})
public class ServiceLocationEntity extends WorkLocationEntity {

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
}