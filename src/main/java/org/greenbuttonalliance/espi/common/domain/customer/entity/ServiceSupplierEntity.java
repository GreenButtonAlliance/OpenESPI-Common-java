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
import org.greenbuttonalliance.espi.common.domain.customer.enums.SupplierKind;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
 * Pure JPA/Hibernate entity for ServiceSupplier without JAXB concerns.
 * 
 * Organisation that provides services to customers.
 */
@Entity
@Table(name = "service_suppliers", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class ServiceSupplierEntity extends IdentifiedObject {

    /**
     * Organisation having this role.
     */
    @Embedded
    private Organisation organisation;

    /**
     * Kind of supplier.
     */
    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private SupplierKind kind;

    /**
     * Unique transaction reference prefix number issued to an entity by the International Organization for 
     * Standardization for the purpose of tagging onto electronic financial transactions, as defined in 
     * ISO/IEC 7812-1 and ISO/IEC 7812-2.
     */
    @Column(name = "issuer_identification_number", length = 256)
    private String issuerIdentificationNumber;

    /**
     * [extension] Effective Date of Service Activation
     */
    @Column(name = "effective_date")
    private OffsetDateTime effectiveDate;
}