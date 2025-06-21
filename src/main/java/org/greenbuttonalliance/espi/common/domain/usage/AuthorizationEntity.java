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
import org.greenbuttonalliance.espi.common.domain.legacy.DateTimeInterval;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.GrantType;
import org.greenbuttonalliance.espi.common.domain.legacy.OAuthError;
import org.greenbuttonalliance.espi.common.domain.legacy.ResponseType;
import org.greenbuttonalliance.espi.common.domain.legacy.TokenType;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Pure JPA/Hibernate entity for Authorization without JAXB concerns.
 * 
 * Represents an OAuth2 authorization granted by a retail customer for third-party
 * access to their energy data. Contains access tokens, scopes, expiration information,
 * and maintains the relationship between customers and authorized applications.
 */
@Entity
@Table(name = "authorizations", indexes = {
    @Index(name = "idx_authorization_access_token", columnList = "access_token"),
    @Index(name = "idx_authorization_refresh_token", columnList = "refresh_token"),
    @Index(name = "idx_authorization_state", columnList = "state"),
    @Index(name = "idx_authorization_resource_uri", columnList = "resource_uri")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"accessToken", "refreshToken", "retailCustomer", "subscription", "applicationInformation"})
public class AuthorizationEntity extends IdentifiedObject {

    private static final long serialVersionUID = 1L;

    // Authorization status constants
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_REVOKED = "REVOKED";
    public static final String STATUS_EXPIRED = "EXPIRED";
    public static final String STATUS_PENDING = "PENDING";

    /**
     * Period during which this authorization is valid.
     * Defines the time range for authorized access.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "authorized_period_start")),
        @AttributeOverride(name = "duration", column = @Column(name = "authorized_period_duration"))
    })
    private DateTimeInterval authorizedPeriod;

    /**
     * Period during which data was published.
     * Defines the time range of available data.
     */
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "start", column = @Column(name = "published_period_start")),
        @AttributeOverride(name = "duration", column = @Column(name = "published_period_duration"))
    })
    private DateTimeInterval publishedPeriod;

    /**
     * OAuth2 access token for API access.
     * Used for authentication in API requests.
     */
    @Column(name = "access_token", length = 512)
    private String accessToken;

    /**
     * OAuth2 refresh token for token renewal.
     * Used to obtain new access tokens when they expire.
     */
    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    /**
     * Authorization status.
     * Indicates the current state of the authorization.
     */
    @Column(name = "status", length = 50)
    private String status = STATUS_PENDING;

    /**
     * Expiration timestamp for the authorization.
     * Unix timestamp indicating when the authorization expires.
     */
    @Column(name = "expires_in")
    private Long expiresIn;

    /**
     * OAuth2 grant type used for this authorization.
     * Indicates the flow used to obtain the authorization.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "grant_type")
    private GrantType grantType;

    /**
     * OAuth2 scope defining the permissions granted.
     * Space-separated list of permissions.
     */
    @Column(name = "scope", length = 256)
    private String scope;

    /**
     * OAuth2 state parameter for CSRF protection.
     * Used to maintain state between authorization request and callback.
     */
    @Column(name = "state", length = 256)
    private String state;

    /**
     * OAuth2 response type.
     * Indicates the type of response expected from authorization.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "response_type")
    private ResponseType responseType;

    /**
     * OAuth2 token type.
     * Typically "Bearer" for bearer tokens.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType;

    /**
     * OAuth2 authorization code.
     * Temporary code used in authorization code flow.
     */
    @Column(name = "code", length = 512)
    private String code;

    /**
     * OAuth2 error information.
     * Contains error details if authorization failed.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "error")
    private OAuthError error;

    /**
     * OAuth2 error description.
     * Human-readable error description.
     */
    @Column(name = "error_description", length = 256)
    private String errorDescription;

    /**
     * OAuth2 error URI.
     * URI with more information about the error.
     */
    @Column(name = "error_uri", length = 512)
    private String errorUri;

    /**
     * URI of the authorized resource.
     * Points to the specific resource that was authorized.
     */
    @Column(name = "resource_uri", length = 512)
    private String resourceURI;

    /**
     * Authorization endpoint URI.
     * URI used for the authorization request.
     */
    @Column(name = "authorization_uri", length = 512)
    private String authorizationURI;

    /**
     * Third party identifier.
     * Identifies the third-party application or organization.
     */
    @Column(name = "third_party", length = 256)
    private String thirdParty;

    /**
     * Retail customer who granted this authorization.
     * Many authorizations can belong to one customer.
     */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "retail_customer_id")
    private RetailCustomerEntity retailCustomer;

    /**
     * Subscription associated with this authorization.
     * One-to-one relationship with optional subscription.
     */
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private SubscriptionEntity subscription;

    /**
     * Application information for the authorized application.
     * Many authorizations can belong to one application.
     */
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_information_id")
    private ApplicationInformationEntity applicationInformation;

    /**
     * Constructor with basic authorization information.
     * 
     * @param retailCustomer the customer granting authorization
     * @param applicationInformation the application being authorized
     * @param scope the scope of permissions granted
     */
    public AuthorizationEntity(RetailCustomerEntity retailCustomer, ApplicationInformationEntity applicationInformation, String scope) {
        this.retailCustomer = retailCustomer;
        this.applicationInformation = applicationInformation;
        this.scope = scope;
        this.status = STATUS_PENDING;
    }

    // Note: Simple setters for retailCustomer and subscription are generated by Lombok @Data
    // Complex bidirectional relationship management is handled by DataCustodian/ThirdParty applications

    /**
     * Generates the self href for this authorization.
     * 
     * @return self href string
     */
    public String getSelfHref() {
        return "/espi/1_1/resource/Authorization/" + getHashedId();
    }

    /**
     * Generates the up href for this authorization.
     * 
     * @return up href string
     */
    public String getUpHref() {
        return "/espi/1_1/resource/Authorization";
    }

    /**
     * Overrides the default self href generation to use authorization specific logic.
     * 
     * @return self href for this authorization
     */
    @Override
    protected String generateDefaultSelfHref() {
        return getSelfHref();
    }

    /**
     * Overrides the default up href generation to use authorization specific logic.
     * 
     * @return up href for this authorization
     */
    @Override
    protected String generateDefaultUpHref() {
        return getUpHref();
    }

    /**
     * Merges data from another AuthorizationEntity.
     * Updates authorization parameters while preserving relationships.
     * 
     * @param other the other authorization entity to merge from
     */
    public void merge(AuthorizationEntity other) {
        if (other != null) {
            super.merge(other);
            
            // Update authorization parameters
            this.authorizedPeriod = other.authorizedPeriod;
            this.publishedPeriod = other.publishedPeriod;
            this.status = other.status;
            this.expiresIn = other.expiresIn;
            this.grantType = other.grantType;
            this.scope = other.scope;
            this.responseType = other.responseType;
            this.tokenType = other.tokenType;
            this.error = other.error;
            this.errorDescription = other.errorDescription;
            this.errorUri = other.errorUri;
            this.resourceURI = other.resourceURI;
            this.authorizationURI = other.authorizationURI;
            this.thirdParty = other.thirdParty;
            
            // Note: Sensitive fields like tokens are not merged
            // Note: Relationships are not merged to preserve existing associations
        }
    }

    /**
     * Clears all relationships when unlinking the entity.
     * Simplified - applications handle relationship cleanup.
     */
    public void unlink() {
        clearRelatedLinks();
        
        // Simple field clearing - applications handle bidirectional cleanup
        this.retailCustomer = null;
        this.subscription = null;
        
        // Note: applicationInformation is not cleared as it might be referenced elsewhere
    }

    /**
     * Checks if this authorization is currently active.
     * 
     * @return true if status is ACTIVE and not expired, false otherwise
     */
    public boolean isActive() {
        return STATUS_ACTIVE.equals(status) && !isExpired();
    }

    /**
     * Checks if this authorization has expired.
     * 
     * @return true if expired, false otherwise
     */
    public boolean isExpired() {
        if (expiresIn == null) {
            return false; // No expiration set
        }
        return System.currentTimeMillis() > expiresIn;
    }

    /**
     * Checks if this authorization is revoked.
     * 
     * @return true if status is REVOKED, false otherwise
     */
    public boolean isRevoked() {
        return STATUS_REVOKED.equals(status);
    }

    /**
     * Checks if this authorization is pending.
     * 
     * @return true if status is PENDING, false otherwise
     */
    public boolean isPending() {
        return STATUS_PENDING.equals(status);
    }

    /**
     * Activates this authorization.
     * Sets status to ACTIVE and clears any error information.
     */
    public void activate() {
        this.status = STATUS_ACTIVE;
        this.error = null;
        this.errorDescription = null;
        this.errorUri = null;
    }

    /**
     * Revokes this authorization.
     * Sets status to REVOKED and clears tokens.
     */
    public void revoke() {
        this.status = STATUS_REVOKED;
        this.accessToken = null;
        this.refreshToken = null;
    }

    /**
     * Marks this authorization as expired.
     * Sets status to EXPIRED.
     */
    public void expire() {
        this.status = STATUS_EXPIRED;
    }

    /**
     * Sets error information for this authorization.
     * 
     * @param error the OAuth error type
     * @param description human-readable error description
     * @param uri URI with more error information
     */
    public void setError(OAuthError error, String description, String uri) {
        this.error = error;
        this.errorDescription = description;
        this.errorUri = uri;
        this.status = STATUS_REVOKED; // Error typically means revoked
    }

    /**
     * Sets the expiration time from now plus the given seconds.
     * 
     * @param expiresInSeconds seconds until expiration
     */
    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresIn = System.currentTimeMillis() + (expiresInSeconds * 1000);
    }

    /**
     * Gets the expiration time as an Instant.
     * 
     * @return expiration instant, or null if not set
     */
    public Instant getExpirationInstant() {
        return expiresIn != null ? Instant.ofEpochMilli(expiresIn) : null;
    }

    /**
     * Gets the remaining time until expiration in seconds.
     * 
     * @return seconds until expiration, or null if no expiration set
     */
    public Long getSecondsUntilExpiration() {
        if (expiresIn == null) {
            return null;
        }
        long remaining = (expiresIn - System.currentTimeMillis()) / 1000;
        return Math.max(0, remaining);
    }

    /**
     * Checks if this authorization has the specified scope.
     * 
     * @param requiredScope the scope to check for
     * @return true if the scope is included, false otherwise
     */
    public boolean hasScope(String requiredScope) {
        if (scope == null || requiredScope == null) {
            return false;
        }
        String[] scopes = scope.split("\\s+");
        for (String s : scopes) {
            if (requiredScope.equals(s.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the scopes as an array.
     * 
     * @return array of scope strings, or empty array if no scope set
     */
    public String[] getScopesArray() {
        if (scope == null || scope.trim().isEmpty()) {
            return new String[0];
        }
        return scope.trim().split("\\s+");
    }

    /**
     * Pre-persist callback to set default values.
     */
    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = STATUS_PENDING;
        }
    }
}