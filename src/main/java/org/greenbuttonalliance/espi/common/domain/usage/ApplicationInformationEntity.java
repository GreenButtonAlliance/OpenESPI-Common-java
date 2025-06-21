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
import org.greenbuttonalliance.espi.common.domain.legacy.GrantType;
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.ResponseType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import jakarta.validation.constraints.NotEmpty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Pure JPA/Hibernate entity for ApplicationInformation without JAXB concerns.
 * 
 * Contains information about a Third Party Application requesting access to the 
 * DataCustodian services. Information includes Organization Name, Website, Contact Info, 
 * Application Name, Description, Icon, Type, default Notification and Callback endpoints.
 */
@Entity
@Table(name = "application_information")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class ApplicationInformationEntity extends IdentifiedObject {

    private static final long serialVersionUID = 1L;

    /**
     * Kind/type classification for this application information.
     */
    @Column(name = "kind")
    private String kind;

    /**
     * Data custodian application status.
     */
    @Column(name = "data_custodian_application_status")
    private String dataCustodianApplicationStatus;

    /**
     * Data custodian default batch resource URI.
     */
    @Column(name = "data_custodian_default_batch_resource")
    private String dataCustodianDefaultBatchResource;

    /**
     * Data custodian default subscription resource URI.
     */
    @Column(name = "data_custodian_default_subscription_resource")
    private String dataCustodianDefaultSubscriptionResource;

    /**
     * Third party application description.
     */
    @Column(name = "third_party_application_description")
    private String thirdPartyApplicationDescription;

    /**
     * Third party application status.
     */
    @Column(name = "third_party_application_status")
    private String thirdPartyApplicationStatus;

    /**
     * Third party application type.
     */
    @Column(name = "third_party_application_type")
    private String thirdPartyApplicationType;

    /**
     * Third party application use.
     */
    @Column(name = "third_party_application_use")
    private String thirdPartyApplicationUse;

    /**
     * Third party phone number.
     */
    @Column(name = "third_party_phone")
    private String thirdPartyPhone;

    /**
     * Authorization server URI.
     */
    @Column(name = "authorization_server_uri")
    private String authorizationServerUri;

    /**
     * Third party notification URI.
     */
    @Column(name = "third_party_notify_uri")
    private String thirdPartyNotifyUri;

    /**
     * Authorization server authorization endpoint.
     */
    @Column(name = "authorization_server_authorization_endpoint")
    private String authorizationServerAuthorizationEndpoint;

    /**
     * Authorization server registration endpoint.
     */
    @Column(name = "authorization_server_registration_endpoint")
    private String authorizationServerRegistrationEndpoint;

    /**
     * Authorization server token endpoint.
     */
    @Column(name = "authorization_server_token_endpoint")
    private String authorizationServerTokenEndpoint;

    /**
     * Data custodian bulk request URI.
     */
    @Column(name = "data_custodian_bulk_request_uri")
    private String dataCustodianBulkRequestURI;

    /**
     * Data custodian third party selection screen URI.
     */
    @Column(name = "data_custodian_third_party_selection_screen_uri")
    private String dataCustodianThirdPartySelectionScreenURI;

    /**
     * Data custodian resource endpoint.
     */
    @Column(name = "data_custodian_resource_endpoint")
    private String dataCustodianResourceEndpoint;

    /**
     * Third party data custodian selection screen URI.
     */
    @Column(name = "third_party_data_custodian_selection_screen_uri")
    private String thirdPartyDataCustodianSelectionScreenURI;

    /**
     * Third party login screen URI.
     */
    @Column(name = "third_party_login_screen_uri")
    private String thirdPartyLoginScreenURI;

    /**
     * Third party scope selection screen URI.
     */
    @Column(name = "third_party_scope_selection_screen_uri")
    private String thirdPartyScopeSelectionScreenURI;

    /**
     * Third party user portal screen URI.
     */
    @Column(name = "third_party_user_portal_screen_uri")
    private String thirdPartyUserPortalScreenURI;

    /**
     * Client secret for OAuth2 authentication.
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * Logo URI for the application.
     */
    @Column(name = "logo_uri")
    private String logoUri;

    /**
     * Client name for the application.
     */
    @Column(name = "client_name")
    private String clientName;

    /**
     * Client URI for the application.
     */
    @Column(name = "client_uri")
    private String clientUri;

    /**
     * Redirect URI for OAuth2 flow.
     */
    @Column(name = "redirect_uri")
    private String redirectUri;

    /**
     * Client ID for OAuth2 authentication.
     * Required field with size constraints.
     */
    @NotEmpty
    @Size(min = 2, max = 64)
    @Column(name = "client_id", nullable = false)
    private String clientId;

    /**
     * Terms of service URI.
     */
    @Column(name = "tos_uri")
    private String tosUri;

    /**
     * Privacy policy URI.
     */
    @Column(name = "policy_uri")
    private String policyUri;

    /**
     * Software ID identifier.
     */
    @Column(name = "software_id")
    private String softwareId;

    /**
     * Software version identifier.
     */
    @Column(name = "software_version")
    private String softwareVersion;

    /**
     * Timestamp when client ID was issued.
     */
    @Column(name = "client_id_issued_at")
    private Long clientIdIssuedAt;

    /**
     * Timestamp when client secret expires.
     */
    @Column(name = "client_secret_expires_at")
    private Long clientSecretExpiresAt;

    /**
     * Contact information for the application.
     */
    @Column(name = "contacts")
    private String contacts;

    /**
     * Token endpoint authentication method.
     */
    @Column(name = "token_endpoint_auth_method")
    private String tokenEndpointAuthMethod;

    /**
     * Data custodian scope selection screen URI.
     */
    @Column(name = "data_custodian_scope_selection_screen_uri")
    private String dataCustodianScopeSelectionScreenURI;

    /**
     * Data custodian ID.
     * Required field with size constraints.
     */
    @Size(min = 2, max = 64)
    @Column(name = "data_custodian_id")
    private String dataCustodianId;

    /**
     * Third party application name.
     * Required field with size constraints and default value.
     */
    @NotEmpty
    @Size(min = 2, max = 64)
    @Column(name = "third_party_application_name", nullable = false)
    private String thirdPartyApplicationName = "Default Third Party Application Name";

    /**
     * OAuth2 scopes for this application.
     * Stored as element collection in separate table.
     */
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
        name = "application_information_scopes",
        joinColumns = @JoinColumn(name = "application_information_id")
    )
    @Column(name = "scope")
    private Set<String> scope = new HashSet<>();

    /**
     * OAuth2 grant types supported by this application.
     * Stored as element collection with enum mapping.
     */
    @ElementCollection(targetClass = GrantType.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "application_information_grant_types",
        joinColumns = @JoinColumn(name = "application_information_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "grant_type")
    private Set<GrantType> grantTypes = new HashSet<>();

    /**
     * OAuth2 response types supported by this application.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "response_types")
    private ResponseType responseTypes;

    /**
     * Registration client URI.
     */
    @Column(name = "registration_client_uri")
    private String registrationClientUri;

    /**
     * Registration access token.
     */
    @Column(name = "registration_access_token")
    private String registrationAccessToken;

    /**
     * Gets the scope array for backward compatibility.
     * 
     * @return array of scope strings
     */
    public String[] getScopeArray() {
        if (scope == null || scope.isEmpty()) {
            return new String[]{};
        }
        return scope.toArray(new String[0]);
    }

    /**
     * Merges data from another ApplicationInformationEntity.
     * 
     * @param other the other application information entity to merge from
     */
    public void merge(ApplicationInformationEntity other) {
        if (other != null) {
            super.merge(other);
            
            // OAuth2 and client information
            this.clientId = other.clientId;
            this.clientSecret = other.clientSecret;
            this.scope = new HashSet<>(other.scope);
            this.grantTypes = new HashSet<>(other.grantTypes);
            this.redirectUri = other.redirectUri;
            this.responseTypes = other.responseTypes;
            
            // Application status and configuration
            this.dataCustodianApplicationStatus = other.dataCustodianApplicationStatus;
            this.thirdPartyApplicationStatus = other.thirdPartyApplicationStatus;
            this.thirdPartyApplicationType = other.thirdPartyApplicationType;
            this.thirdPartyApplicationUse = other.thirdPartyApplicationUse;
            this.thirdPartyApplicationDescription = other.thirdPartyApplicationDescription;
            this.thirdPartyApplicationName = other.thirdPartyApplicationName;
            
            // URIs and endpoints
            this.authorizationServerUri = other.authorizationServerUri;
            this.thirdPartyNotifyUri = other.thirdPartyNotifyUri;
            this.authorizationServerAuthorizationEndpoint = other.authorizationServerAuthorizationEndpoint;
            this.authorizationServerRegistrationEndpoint = other.authorizationServerRegistrationEndpoint;
            this.authorizationServerTokenEndpoint = other.authorizationServerTokenEndpoint;
            this.dataCustodianBulkRequestURI = other.dataCustodianBulkRequestURI;
            this.dataCustodianResourceEndpoint = other.dataCustodianResourceEndpoint;
            this.dataCustodianScopeSelectionScreenURI = other.dataCustodianScopeSelectionScreenURI;
            
            // Additional application metadata
            this.logoUri = other.logoUri;
            this.clientName = other.clientName;
            this.clientUri = other.clientUri;
            this.tosUri = other.tosUri;
            this.policyUri = other.policyUri;
            this.softwareId = other.softwareId;
            this.softwareVersion = other.softwareVersion;
            this.clientIdIssuedAt = other.clientIdIssuedAt;
            this.clientSecretExpiresAt = other.clientSecretExpiresAt;
            this.contacts = other.contacts;
            this.tokenEndpointAuthMethod = other.tokenEndpointAuthMethod;
            this.registrationClientUri = other.registrationClientUri;
            this.registrationAccessToken = other.registrationAccessToken;
            
            // Data custodian information
            this.dataCustodianId = other.dataCustodianId;
            this.dataCustodianDefaultBatchResource = other.dataCustodianDefaultBatchResource;
            this.dataCustodianDefaultSubscriptionResource = other.dataCustodianDefaultSubscriptionResource;
            
            // Contact and additional information
            this.thirdPartyPhone = other.thirdPartyPhone;
            this.kind = other.kind;
        }
    }
}