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
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for CustomerAgreement without JAXB concerns.
 * 
 * Agreement between the customer and the service supplier to pay for service at a specific service location. 
 * It records certain billing information about the type of service provided at the service location and is 
 * used during charge creation to determine the type of service.
 */
@Entity
@Table(name = "customer_agreements", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"futureStatus"})
public class CustomerAgreementEntity extends Agreement {

    /**
     * Load management code.
     */
    @Column(name = "load_mgmt", length = 256)
    private String loadMgmt;

    /**
     * If true, the customer is a pre-pay customer for the specified service.
     */
    @Column(name = "is_pre_pay")
    private Boolean isPrePay;

    /**
     * Final date and time the service will be billed to the previous customer.
     */
    @Column(name = "shut_off_date_time")
    private OffsetDateTime shutOffDateTime;

    /**
     * Demand Response program characteristics covered by Customer Agreement
     * TODO: Create DemandResponseProgramEntity and enable this relationship
     */
    // @OneToMany(mappedBy = "customerAgreement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<DemandResponseProgramEntity> demandResponsePrograms;

    /**
     * All pricing structures applicable to this customer agreement.
     * TODO: Create PricingStructureEntity and enable this relationship
     */
    // @OneToMany(mappedBy = "customerAgreement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<PricingStructureEntity> pricingStructures;

    /**
     * Currency for all monetary amounts for this agreement.
     */
    @Column(name = "currency", length = 3)
    private String currency; // ISO 4217 currency code

    /**
     * [extension] Known future changes to CustomerAgreement's Status of Service.
     */
    @ElementCollection
    @CollectionTable(name = "customer_agreement_future_status", joinColumns = @JoinColumn(name = "customer_agreement_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "status_value")),
        @AttributeOverride(name = "dateTime", column = @Column(name = "status_date_time")),
        @AttributeOverride(name = "reason", column = @Column(name = "status_reason"))
    })
    private List<CustomerEntity.Status> futureStatus;

    /**
     * [extension] Customer agreement identifier
     */
    @Column(name = "agreement_id", length = 256)
    private String agreementId;
}