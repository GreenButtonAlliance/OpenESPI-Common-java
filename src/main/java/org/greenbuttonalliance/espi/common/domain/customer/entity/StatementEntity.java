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
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for Statement without JAXB concerns.
 * 
 * [extension] Billing statement for provided services.
 */
@Entity
@Table(name = "statements", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"statementRefs"})
public class StatementEntity extends IdentifiedObject {

    /**
     * [extension] Date and time at which a billing statement was issued.
     */
    @Column(name = "issue_date_time")
    private OffsetDateTime issueDateTime;

    /**
     * [extension] Contains document reference metadata needed to access a document representation of a billing statement.
     */
    @OneToMany(mappedBy = "statement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StatementRefEntity> statementRefs;
    
    /**
     * Customer that owns this statement.
     * Many statements can belong to one customer.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    // Note: Lombok @Data should generate setter methods automatically
}