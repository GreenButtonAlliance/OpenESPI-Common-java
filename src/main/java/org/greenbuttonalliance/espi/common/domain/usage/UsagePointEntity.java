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

package org.greenbuttonalliance.espi.common.domain.usage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Pure JPA/Hibernate entity for UsagePoint without JAXB concerns.
 * 
 * Represents a logical point on a network at which consumption or production 
 * is either physically measured (e.g., metered) or estimated (e.g., unmetered street lights).
 */
@Entity
@Table(name = "usage_points", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class UsagePointEntity extends IdentifiedObjectEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Role flags for the usage point (hex binary representation).
     */
    @Column(name = "role_flags")
    private byte[] roleFlags;

    /**
     * Service category for this usage point.
     * Required field indicating the type of service.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_category", nullable = false)
    private ServiceCategory serviceCategory;

    /**
     * Status of the usage point.
     */
    @Column(name = "status")
    private Short status;

    /**
     * URI for this usage point.
     * Used for external references and linking.
     */
    @Column(name = "uri")
    private String uri;

    /**
     * Service delivery point associated with this usage point.
     * One-to-one relationship with cascade operations.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_delivery_point_id")
    private ServiceDeliveryPointEntity serviceDeliveryPoint;

    /**
     * Retail customer that owns this usage point.
     * Many usage points can belong to one retail customer.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retail_customer_id")
    private RetailCustomerEntity retailCustomer;

    /**
     * Time configuration parameters for this usage point.
     * Many usage points can share the same time configuration.
     */
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "local_time_parameters_id")
    private TimeConfigurationEntity localTimeParameters;

    /**
     * Meter readings associated with this usage point.
     * One-to-many relationship with cascade and orphan removal.
     */
    @OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MeterReadingEntity> meterReadings = new ArrayList<>();

    // ElectricPowerUsageSummary relationships removed - deprecated resource

    /**
     * Usage summaries for this usage point.
     * One-to-many relationship with cascade and orphan removal.
     */
    @OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UsageSummaryEntity> usageSummaries = new ArrayList<>();

    /**
     * Electric power quality summaries for this usage point.
     * One-to-many relationship with cascade and orphan removal.
     */
    @OneToMany(mappedBy = "usagePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ElectricPowerQualitySummaryEntity> electricPowerQualitySummaries = new ArrayList<>();

    /**
     * Subscriptions that include this usage point.
     * Many-to-many relationship mapped by the subscriptions side.
     */
    @ManyToMany(mappedBy = "usagePoints")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<SubscriptionEntity> subscriptions = new HashSet<>();

    /**
     * Single subscription reference for this usage point.
     * Optional one-to-one relationship.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;

    /**
     * Generates the self href for this usage point.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return getUpHref() + "/" + getHashedId();
    }

    /**
     * Generates the up href for this usage point.
     * 
     * @return up href string pointing to the retail customer
     */
    public String getUpHref() {
        if (retailCustomer != null) {
            return "RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint";
        }
        return "/espi/1_1/resource/UsagePoint";
    }

    // Note: Meter reading collection accessors are generated by Lombok @Data
    // Bidirectional relationship management methods removed - handled by DataCustodian/ThirdParty applications

    // ElectricPowerUsageSummary management methods removed - deprecated resource

    // Note: Usage summary collection accessors are generated by Lombok @Data
    // Bidirectional relationship management methods removed - handled by DataCustodian/ThirdParty applications

    // Note: Electric power quality summary collection accessors are generated by Lombok @Data
    // Bidirectional relationship management methods removed - handled by DataCustodian/ThirdParty applications

    // Note: Subscription collection accessors are generated by Lombok @Data
    // Bidirectional relationship management methods removed - handled by DataCustodian/ThirdParty applications

    /**
     * Overrides the default self href generation to use usage point specific logic.
     * 
     * @return self href for this usage point
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use usage point specific logic.
     * 
     * @return up href for this usage point
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another UsagePointEntity.
     * 
     * @param other the other usage point entity to merge from
     */
    public void merge(UsagePointEntity other) {
        if (other != null) {
            super.merge(other);
            this.serviceCategory = other.serviceCategory;
            this.status = other.status;
            this.roleFlags = other.roleFlags;
            this.uri = other.uri;
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     */
    public void unlink() {
        clearRelatedLinks();
        electricPowerQualitySummaries.clear();
        // electricPowerUsageSummaries removed - deprecated resource
        usageSummaries.clear();
        meterReadings.clear();
        retailCustomer = null;
        subscriptions.clear();
        subscription = null;
    }

    // Note: equals() and hashCode() methods are generated by Lombok @Data annotation
}