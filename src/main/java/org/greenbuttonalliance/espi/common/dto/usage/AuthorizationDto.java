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
 * Authorization DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents OAuth 2.0 authorization for third-party access to Green Button data.
 */
@XmlRootElement(name = "Authorization", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Authorization", namespace = "http://naesb.org/espi", propOrder = {
    "accessToken", "authorizationUri", "applicationInformationId", "retailCustomerId",
    "resourceURI", "scope", "status", "expiresIn", "grantType", "refreshToken",
    "tokenType", "thirdParty", "ppid", "authorizationCode"
})
public record AuthorizationDto(
    
    String uuid,
    String accessToken,
    String authorizationUri,
    String applicationInformationId,
    String retailCustomerId,
    String resourceURI,
    String scope,
    Short status,
    Long expiresIn,
    String grantType,
    String refreshToken,
    String tokenType,
    String thirdParty,
    String ppid,
    String authorizationCode
) {
    
    /**
     * Default constructor for JAXB.
     */
    public AuthorizationDto() {
        this(null, null, null, null, null, null, null, null, null,
             null, null, null, null, null, null);
    }
    
    /**
     * Constructor for basic authorization.
     */
    public AuthorizationDto(String accessToken, String scope, String retailCustomerId) {
        this(null, accessToken, null, null, retailCustomerId, null, scope, null, null,
             null, null, null, null, null, null);
    }
    
    // JAXB property accessors
    @XmlElement(name = "accessToken", namespace = "http://naesb.org/espi")
    public String getAccessToken() { return accessToken; }
    
    @XmlElement(name = "authorizationURI", namespace = "http://naesb.org/espi")
    public String getAuthorizationUri() { return authorizationUri; }
    
    @XmlElement(name = "applicationInformationId", namespace = "http://naesb.org/espi")
    public String getApplicationInformationId() { return applicationInformationId; }
    
    @XmlElement(name = "retailCustomerId", namespace = "http://naesb.org/espi")
    public String getRetailCustomerId() { return retailCustomerId; }
    
    @XmlElement(name = "resourceURI", namespace = "http://naesb.org/espi")
    public String getResourceURI() { return resourceURI; }
    
    @XmlElement(name = "scope", namespace = "http://naesb.org/espi")
    public String getScope() { return scope; }
    
    @XmlElement(name = "status", namespace = "http://naesb.org/espi")
    public Short getStatus() { return status; }
    
    @XmlElement(name = "expires_in", namespace = "http://naesb.org/espi")
    public Long getExpiresIn() { return expiresIn; }
    
    @XmlElement(name = "grant_type", namespace = "http://naesb.org/espi")
    public String getGrantType() { return grantType; }
    
    @XmlElement(name = "refresh_token", namespace = "http://naesb.org/espi")
    public String getRefreshToken() { return refreshToken; }
    
    @XmlElement(name = "token_type", namespace = "http://naesb.org/espi")
    public String getTokenType() { return tokenType; }
    
    @XmlElement(name = "third_party", namespace = "http://naesb.org/espi")
    public String getThirdParty() { return thirdParty; }
    
    @XmlElement(name = "ppid", namespace = "http://naesb.org/espi")
    public String getPpid() { return ppid; }
    
    @XmlElement(name = "code", namespace = "http://naesb.org/espi")
    public String getAuthorizationCode() { return authorizationCode; }
}