package org.energyos.espi.common.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class AccessToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("resourceURI")
    private String resourceURI;
    @JsonProperty("authorizationURI")
    private String authorizationURI;
    @JsonProperty("error")
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    @JsonProperty("error_uri")
    private String errorURI;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }  

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getAuthorizationURI() {
        return authorizationURI;
    }

    public void setAuthorizationURI(String authorizationURI) {
        this.authorizationURI = authorizationURI;
    }
    
    public String getError() {
    	return error;
    }
    
    public void setError(String error) {
    	this.error = error;
    }
    
    public String getErrorDescription() {
    	return errorDescription;
    }
    
    public void setErrorDescription(String errorDescription) {
    	this.errorDescription = errorDescription;
    }
    
    public String getErrorURI() {
    	return errorURI;
    }
    
    public void setErrorURI(String errorURI) {
    	this.errorURI = errorURI;
    }    
}
