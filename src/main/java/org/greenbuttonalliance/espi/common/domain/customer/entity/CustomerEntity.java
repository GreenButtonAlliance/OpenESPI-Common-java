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
import org.greenbuttonalliance.espi.common.domain.customer.enums.CustomerKind;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.usage.TimeConfigurationEntity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for Customer without JAXB concerns.
 * 
 * Organisation receiving services from service supplier.
 * Contains customer-specific information including kind, special needs,
 * VIP status, PUC number, status, priority, locale, and customer name.
 * 
 * This entity represents Personally Identifiable Information (PII) and
 * cannot be part of normal ESPI usage information. These resources are
 * available via the same messaging patterns as other ESPI data but are
 * defined in a separate namespace.
 */
@Entity
@Table(name = "customers", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class CustomerEntity extends IdentifiedObject {

    /**
     * Organisation having this role.
     */
    @Embedded
    private Organisation organisation;

    /**
     * Kind of customer (enum value).
     */
    @Column(name = "kind")
    @Enumerated(EnumType.STRING)
    private CustomerKind kind;

    /**
     * True if customer organisation has special service needs such as life support, hospitals, etc.
     */
    @Column(name = "special_need", length = 256)
    private String specialNeed;

    /**
     * (use 'priority' instead) True if this is an important customer. 
     * Importance is for matters different than those in 'specialNeed' attribute.
     */
    @Column(name = "vip")
    private Boolean vip;

    /**
     * (if applicable) Public utilities commission (PUC) identification number.
     */
    @Column(name = "puc_number", length = 256)
    private String pucNumber;

    /**
     * Status of this customer.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "status_value")),
        @AttributeOverride(name = "dateTime", column = @Column(name = "status_date_time")),
        @AttributeOverride(name = "reason", column = @Column(name = "status_reason"))
    })
    private Status status;

    /**
     * Priority of the customer.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "priority_value")),
        @AttributeOverride(name = "rank", column = @Column(name = "priority_rank")),
        @AttributeOverride(name = "type", column = @Column(name = "priority_type"))
    })
    private Priority priority;

    /**
     * Locale designating language to use in communications with this customer.
     */
    @Column(name = "locale", length = 256)
    private String locale;

    /**
     * [extension] Customer name
     */
    @Column(name = "customer_name", length = 256)
    private String customerName;
    
    /**
     * Customer accounts owned by this customer.
     * One customer can have multiple customer accounts.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerAccountEntity> customerAccounts;
    
    /**
     * Time configuration for this customer.
     * Each customer has one time configuration.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_configuration_id")
    private TimeConfigurationEntity timeConfiguration;
    
    /**
     * Billing statements for this customer.
     * One customer can have multiple statements.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StatementEntity> statements;

    /**
     * Embeddable class for Status
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class Status {
        @Column(name = "value", length = 256)
        private String value;
        
        @Column(name = "date_time")
        private OffsetDateTime dateTime;
        
        @Column(name = "reason", length = 256)
        private String reason;
    }

    /**
     * Embeddable class for Priority
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class Priority {
        @Column(name = "value")
        private Integer value;
        
        @Column(name = "rank")
        private Integer rank;
        
        @Column(name = "type", length = 256)
        private String type;
    }
}