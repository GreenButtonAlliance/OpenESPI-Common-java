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

package org.greenbuttonalliance.espi.common.dto.usage;

import jakarta.xml.bind.annotation.*;

/**
 * ApplicationInformation DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents OAuth 2.0 application information for third-party access
 * to Green Button data.
 */
@XmlRootElement(name = "ApplicationInformation", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ApplicationInformation", namespace = "http://naesb.org/espi", propOrder = {
    "dataCustodianBulkRequestURI", "dataCustodianResourceEndpoint", "dataCustodianApplicationStatus",
    "thirdPartyApplicationDescription", "thirdPartyApplicationStatus", "thirdPartyApplicationType",
    "thirdPartyApplicationUse", "thirdPartyPhone", "authorizationServerAuthorizationEndpoint",
    "authorizationServerRegistrationEndpoint", "authorizationServerTokenEndpoint",
    "dataCustodianScopeSelectionScreenURI", "thirdPartyLoginScreenURI", "thirdPartyNotifyURI",
    "authorizationServerUri", "thirdPartyApplicationName", "clientName", "clientId", "clientSecret",
    "clientIdIssuedAt", "clientSecretExpiresAt", "contacts", "clientUri", "logoUri", "policyUri",
    "redirectUri", "softwareId", "softwareVersion", "tokenEndpointAuthMethod", "responseType",
    "registrationAccessToken", "registrationClientUri", "grantTypes", "scopes"
})
public record ApplicationInformationDto(
    
    String uuid,
    String dataCustodianBulkRequestURI,
    String dataCustodianResourceEndpoint,
    Short dataCustodianApplicationStatus,
    String thirdPartyApplicationDescription,
    Short thirdPartyApplicationStatus,
    Short thirdPartyApplicationType,
    Short thirdPartyApplicationUse,
    String thirdPartyPhone,
    String authorizationServerAuthorizationEndpoint,
    String authorizationServerRegistrationEndpoint,
    String authorizationServerTokenEndpoint,
    String dataCustodianScopeSelectionScreenURI,
    String thirdPartyLoginScreenURI,
    String thirdPartyNotifyURI,
    String authorizationServerUri,
    String thirdPartyApplicationName,
    String clientName,
    String clientId,
    String clientSecret,
    Long clientIdIssuedAt,
    Long clientSecretExpiresAt,
    String contacts,
    String clientUri,
    String logoUri,
    String policyUri,
    String redirectUri,
    String softwareId,
    String softwareVersion,
    String tokenEndpointAuthMethod,
    String responseType,
    String registrationAccessToken,
    String registrationClientUri,
    String grantTypes,
    String scopes
) {
    
    /**
     * Default constructor for JAXB.
     */
    public ApplicationInformationDto() {
        this(null, null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, null);
    }
    
    /**
     * Constructor for basic application information.
     */
    public ApplicationInformationDto(String clientId, String clientSecret, String thirdPartyApplicationName) {
        this(null, null, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, thirdPartyApplicationName, null,
             clientId, clientSecret, null, null, null, null, null, null, null,
             null, null, null, null, null, null, null, null);
    }
    
    // JAXB property accessors
    @XmlElement(name = "dataCustodianBulkRequestURI", namespace = "http://naesb.org/espi")
    public String getDataCustodianBulkRequestURI() { return dataCustodianBulkRequestURI; }
    
    @XmlElement(name = "dataCustodianResourceEndpoint", namespace = "http://naesb.org/espi")
    public String getDataCustodianResourceEndpoint() { return dataCustodianResourceEndpoint; }
    
    @XmlElement(name = "dataCustodianApplicationStatus", namespace = "http://naesb.org/espi")
    public Short getDataCustodianApplicationStatus() { return dataCustodianApplicationStatus; }
    
    @XmlElement(name = "thirdPartyApplicationDescription", namespace = "http://naesb.org/espi")
    public String getThirdPartyApplicationDescription() { return thirdPartyApplicationDescription; }
    
    @XmlElement(name = "thirdPartyApplicationStatus", namespace = "http://naesb.org/espi")
    public Short getThirdPartyApplicationStatus() { return thirdPartyApplicationStatus; }
    
    @XmlElement(name = "thirdPartyApplicationType", namespace = "http://naesb.org/espi")
    public Short getThirdPartyApplicationType() { return thirdPartyApplicationType; }
    
    @XmlElement(name = "thirdPartyApplicationUse", namespace = "http://naesb.org/espi")
    public Short getThirdPartyApplicationUse() { return thirdPartyApplicationUse; }
    
    @XmlElement(name = "thirdPartyPhone", namespace = "http://naesb.org/espi")
    public String getThirdPartyPhone() { return thirdPartyPhone; }
    
    @XmlElement(name = "authorizationServerAuthorizationEndpoint", namespace = "http://naesb.org/espi")
    public String getAuthorizationServerAuthorizationEndpoint() { return authorizationServerAuthorizationEndpoint; }
    
    @XmlElement(name = "authorizationServerRegistrationEndpoint", namespace = "http://naesb.org/espi")
    public String getAuthorizationServerRegistrationEndpoint() { return authorizationServerRegistrationEndpoint; }
    
    @XmlElement(name = "authorizationServerTokenEndpoint", namespace = "http://naesb.org/espi")
    public String getAuthorizationServerTokenEndpoint() { return authorizationServerTokenEndpoint; }
    
    @XmlElement(name = "dataCustodianScopeSelectionScreenURI", namespace = "http://naesb.org/espi")
    public String getDataCustodianScopeSelectionScreenURI() { return dataCustodianScopeSelectionScreenURI; }
    
    @XmlElement(name = "thirdPartyLoginScreenURI", namespace = "http://naesb.org/espi")
    public String getThirdPartyLoginScreenURI() { return thirdPartyLoginScreenURI; }
    
    @XmlElement(name = "thirdPartyNotifyURI", namespace = "http://naesb.org/espi")
    public String getThirdPartyNotifyURI() { return thirdPartyNotifyURI; }
    
    @XmlElement(name = "authorizationServerUri", namespace = "http://naesb.org/espi")
    public String getAuthorizationServerUri() { return authorizationServerUri; }
    
    @XmlElement(name = "thirdPartyApplicationName", namespace = "http://naesb.org/espi")
    public String getThirdPartyApplicationName() { return thirdPartyApplicationName; }
    
    @XmlElement(name = "client_name", namespace = "http://naesb.org/espi")
    public String getClientName() { return clientName; }
    
    @XmlElement(name = "client_id", namespace = "http://naesb.org/espi")
    public String getClientId() { return clientId; }
    
    @XmlElement(name = "client_secret", namespace = "http://naesb.org/espi")
    public String getClientSecret() { return clientSecret; }
    
    @XmlElement(name = "client_id_issued_at", namespace = "http://naesb.org/espi")
    public Long getClientIdIssuedAt() { return clientIdIssuedAt; }
    
    @XmlElement(name = "client_secret_expires_at", namespace = "http://naesb.org/espi")
    public Long getClientSecretExpiresAt() { return clientSecretExpiresAt; }
    
    @XmlElement(name = "contacts", namespace = "http://naesb.org/espi")
    public String getContacts() { return contacts; }
    
    @XmlElement(name = "client_uri", namespace = "http://naesb.org/espi")
    public String getClientUri() { return clientUri; }
    
    @XmlElement(name = "logo_uri", namespace = "http://naesb.org/espi")
    public String getLogoUri() { return logoUri; }
    
    @XmlElement(name = "policy_uri", namespace = "http://naesb.org/espi")
    public String getPolicyUri() { return policyUri; }
    
    @XmlElement(name = "redirect_uri", namespace = "http://naesb.org/espi")
    public String getRedirectUri() { return redirectUri; }
    
    @XmlElement(name = "software_id", namespace = "http://naesb.org/espi")
    public String getSoftwareId() { return softwareId; }
    
    @XmlElement(name = "software_version", namespace = "http://naesb.org/espi")
    public String getSoftwareVersion() { return softwareVersion; }
    
    @XmlElement(name = "token_endpoint_auth_method", namespace = "http://naesb.org/espi")
    public String getTokenEndpointAuthMethod() { return tokenEndpointAuthMethod; }
    
    @XmlElement(name = "response_type", namespace = "http://naesb.org/espi")
    public String getResponseType() { return responseType; }
    
    @XmlElement(name = "registration_access_token", namespace = "http://naesb.org/espi")
    public String getRegistrationAccessToken() { return registrationAccessToken; }
    
    @XmlElement(name = "registration_client_uri", namespace = "http://naesb.org/espi")
    public String getRegistrationClientUri() { return registrationClientUri; }
    
    @XmlElement(name = "grant_types", namespace = "http://naesb.org/espi")
    public String getGrantTypes() { return grantTypes; }
    
    @XmlElement(name = "scope", namespace = "http://naesb.org/espi")
    public String getScopes() { return scopes; }
}