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
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Pure JPA/Hibernate entity for Subscription without JAXB concerns.
 * 
 * Defines the parameters of a subscription between Third Party and Data
 * Custodian. Represents a formal agreement allowing third-party applications
 * to access specific usage points and energy data for a retail customer.
 */
@Entity
@Table(name = "subscriptions", indexes = {
    @Index(name = "idx_subscription_retail_customer", columnList = "retail_customer_id"),
    @Index(name = "idx_subscription_application", columnList = "application_information_id"),
    @Index(name = "idx_subscription_authorization", columnList = "authorization_id"),
    @Index(name = "idx_subscription_last_update", columnList = "last_update")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"retailCustomer", "authorization", "applicationInformation", "usagePoints"})
public class SubscriptionEntity extends IdentifiedObject {

    private static final long serialVersionUID = 1L;

    /**
     * Optional hashed identifier for external references.
     * Used for privacy and security in external communications.
     */
    @Column(name = "hashed_id", length = 64)
    private String hashedId;

    /**
     * Last update timestamp for this subscription.
     * Tracks when the subscription configuration was last modified.
     */
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastUpdate;

    /**
     * Retail customer who owns this subscription.
     * The customer whose data is being accessed through this subscription.
     */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "retail_customer_id")
    @NotNull
    private RetailCustomerEntity retailCustomer;

    /**
     * Authorization associated with this subscription.
     * One-to-one relationship representing the OAuth2 authorization.
     */
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "authorization_id")
    private AuthorizationEntity authorization;

    /**
     * Application information for the subscribed application.
     * Identifies which third-party application has access.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_information_id")
    @NotNull
    private ApplicationInformationEntity applicationInformation;

    /**
     * Usage points included in this subscription.
     * Many-to-many relationship allowing selective data access.
     */
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
        name = "subscription_usage_points",
        joinColumns = @JoinColumn(name = "subscription_id"),
        inverseJoinColumns = @JoinColumn(name = "usage_point_id")
    )
    private List<UsagePointEntity> usagePoints = new ArrayList<>();

    /**
     * Constructor with basic subscription information.
     * 
     * @param retailCustomer the customer who owns the subscription
     * @param applicationInformation the application accessing the data
     */
    public SubscriptionEntity(RetailCustomerEntity retailCustomer, ApplicationInformationEntity applicationInformation) {
        this.retailCustomer = retailCustomer;
        this.applicationInformation = applicationInformation;
        this.lastUpdate = Calendar.getInstance();
    }

    /**
     * Sets the retail customer for this subscription.
     * This method ensures the bidirectional relationship is maintained.
     * 
     * @param retailCustomer the retail customer to set
     */
    public void setRetailCustomer(RetailCustomerEntity retailCustomer) {
        this.retailCustomer = retailCustomer;
        
        // Note: RetailCustomer doesn't have a subscriptions collection in the entity we reviewed,
        // so we don't need to maintain a bidirectional relationship there
    }

    // Note: Simple setter for authorization is generated by Lombok @Data
    // Complex bidirectional relationship management removed - handled by DataCustodian/ThirdParty applications

    // Note: Usage point collection accessors are generated by Lombok @Data
    // Bidirectional relationship management methods removed - handled by DataCustodian/ThirdParty applications

    /**
     * Updates the last update timestamp to current time.
     */
    public void updateLastUpdate() {
        this.lastUpdate = Calendar.getInstance();
    }

    /**
     * Gets the last update time as LocalDateTime.
     * 
     * @return last update as LocalDateTime, or null if not set
     */
    public LocalDateTime getLastUpdateAsLocalDateTime() {
        if (lastUpdate == null) {
            return null;
        }
        return LocalDateTime.ofInstant(lastUpdate.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Sets the last update time from LocalDateTime.
     * 
     * @param dateTime the LocalDateTime to set
     */
    public void setLastUpdateFromLocalDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
            this.lastUpdate = Calendar.getInstance();
            this.lastUpdate.setTimeInMillis(instant.toEpochMilli());
        } else {
            this.lastUpdate = null;
        }
    }

    /**
     * Gets the last update time as Instant.
     * 
     * @return last update as Instant, or null if not set
     */
    public Instant getLastUpdateAsInstant() {
        return lastUpdate != null ? lastUpdate.toInstant() : null;
    }

    /**
     * Generates the self href for this subscription.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return "/espi/1_1/resource/Subscription/" + getHashedId();
    }

    /**
     * Generates the up href for this subscription.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return "/espi/1_1/resource/Subscription";
    }

    /**
     * Overrides the default self href generation to use subscription specific logic.
     * 
     * @return self href for this subscription
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use subscription specific logic.
     * 
     * @return up href for this subscription
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another SubscriptionEntity.
     * Updates subscription parameters while preserving critical relationships.
     * 
     * @param other the other subscription entity to merge from
     */
    public void merge(SubscriptionEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update basic fields
            this.hashedId = other.hashedId;
            this.lastUpdate = other.lastUpdate;
            
            // Update relationships if provided
            if (other.applicationInformation != null) {
                this.applicationInformation = other.applicationInformation;
            }
            if (other.authorization != null) {
                this.authorization = other.authorization;
            }
            if (other.retailCustomer != null) {
                this.retailCustomer = other.retailCustomer;
            }
            if (other.usagePoints != null) {
                this.usagePoints = new ArrayList<>(other.usagePoints);
            }
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     * Simplified - applications handle relationship cleanup.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Simple collection clearing - applications handle bidirectional cleanup
        usagePoints.clear();
        
        // Clear authorization with simple field assignment
        this.authorization = null;
        
        // Note: We don't clear retailCustomer or applicationInformation as they might be referenced elsewhere
    }

    /**
     * Checks if this subscription is active.
     * A subscription is active if it has an active authorization.
     * 
     * @return true if subscription is active, false otherwise
     */
    public boolean isActive() {
        return authorization != null && authorization.isActive();
    }

    /**
     * Checks if this subscription has expired.
     * A subscription is expired if its authorization has expired.
     * 
     * @return true if subscription is expired, false otherwise
     */
    public boolean isExpired() {
        return authorization != null && authorization.isExpired();
    }

    /**
     * Checks if this subscription is revoked.
     * A subscription is revoked if its authorization is revoked.
     * 
     * @return true if subscription is revoked, false otherwise
     */
    public boolean isRevoked() {
        return authorization != null && authorization.isRevoked();
    }

    /**
     * Gets the number of usage points in this subscription.
     * 
     * @return count of usage points
     */
    public int getUsagePointCount() {
        return usagePoints != null ? usagePoints.size() : 0;
    }

    /**
     * Checks if this subscription includes the specified usage point.
     * 
     * @param usagePoint the usage point to check
     * @return true if included, false otherwise
     */
    public boolean includesUsagePoint(UsagePointEntity usagePoint) {
        return usagePoints != null && usagePoints.contains(usagePoint);
    }

    /**
     * Checks if this subscription includes a usage point with the specified ID.
     * 
     * @param usagePointId the usage point ID to check
     * @return true if included, false otherwise
     */
    // Note: includesUsagePointId() method removed - applications can implement custom lookup logic

    /**
     * Gets the subscription ID from a resource URI.
     * Extracts the ID from URI patterns like "/espi/1_1/resource/Subscription/{id}".
     * 
     * @param resourceURI the resource URI
     * @return subscription ID, or null if not found
     */
    public static String getSubscriptionIdFromUri(String resourceURI) {
        if (resourceURI == null || resourceURI.trim().isEmpty()) {
            return null;
        }
        String[] pieces = resourceURI.split("/");
        return pieces.length > 0 ? pieces[pieces.length - 1] : null;
    }

    /**
     * Checks if this subscription belongs to the specified customer.
     * 
     * @param customerId the customer ID to check
     * @return true if belongs to customer, false otherwise
     */
    public boolean belongsToCustomer(Long customerId) {
        return retailCustomer != null && customerId != null && 
               customerId.equals(retailCustomer.getId());
    }

    /**
     * Checks if this subscription is for the specified application.
     * 
     * @param applicationId the application ID to check
     * @return true if for the application, false otherwise
     */
    // Note: isForApplication() method removed - applications can implement custom lookup logic

    /**
     * Pre-persist callback to set default values.
     */
    @PrePersist
    protected void onCreate() {
        if (lastUpdate == null) {
            lastUpdate = Calendar.getInstance();
        }
    }

    /**
     * Pre-update callback to update the last update timestamp.
     */
    @PreUpdate
    protected void onUpdate() {
        updateLastUpdate();
    }
}