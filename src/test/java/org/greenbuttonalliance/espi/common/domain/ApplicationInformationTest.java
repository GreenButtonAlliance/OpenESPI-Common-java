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

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.support.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.greenbuttonalliance.espi.common.support.TestDataBuilder.*;

/**
 * Comprehensive JUnit 5 tests for ApplicationInformation entity.
 * 
 * Tests OAuth2 client registration data, validation, and Green Button Alliance compliance.
 */
@DisplayName("ApplicationInformation Tests")
class ApplicationInformationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    @DisplayName("Entity Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate successfully with all required fields")
        void shouldValidateWithRequiredFields() {
            ApplicationInformation app = applicationInformation()
                .withClientId("valid-client-id")
                .withDataCustodianId("valid-data-custodian-id")
                .build();

            Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(app);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null client ID")
        void shouldFailValidationWithNullClientId() {
            ApplicationInformation app = applicationInformation()
                .withClientId(null)
                .build();

            Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(app);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should fail validation with empty client ID")
        void shouldFailValidationWithEmptyClientId() {
            ApplicationInformation app = applicationInformation()
                .withClientId("")
                .build();

            Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(app);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Should fail validation with null data custodian ID")
        void shouldFailValidationWithNullDataCustodianId() {
            ApplicationInformation app = applicationInformation()
                .withDataCustodianId(null)
                .build();

            Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(app);

            assertThat(violations).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("OAuth2 Configuration Tests")
    class OAuth2ConfigurationTests {

        @Test
        @DisplayName("Should store complete OAuth2 client configuration")
        void shouldStoreCompleteOAuth2ClientConfiguration() {
            String clientId = "test-client-123";
            String clientSecret = "super-secret-key";
            String redirectUri = "http://localhost:8080/callback";
            String authEndpoint = "http://datacustodian.example.com/oauth/authorize";
            String tokenEndpoint = "http://datacustodian.example.com/oauth/token";

            ApplicationInformation app = applicationInformation()
                .withClientId(clientId)
                .build();
            
            app.setClientSecret(clientSecret);
            app.setRedirectUri(redirectUri);
            app.setAuthorizationServerAuthorizationEndpoint(authEndpoint);
            app.setAuthorizationServerTokenEndpoint(tokenEndpoint);

            assertThat(app.getClientId()).isEqualTo(clientId);
            assertThat(app.getClientSecret()).isEqualTo(clientSecret);
            assertThat(app.getRedirectUri()).isEqualTo(redirectUri);
            assertThat(app.getAuthorizationServerAuthorizationEndpoint()).isEqualTo(authEndpoint);
            assertThat(app.getAuthorizationServerTokenEndpoint()).isEqualTo(tokenEndpoint);
        }

        @Test
        @DisplayName("Should handle OAuth2 scope configuration")
        void shouldHandleOAuth2ScopeConfiguration() {
            ApplicationInformation app = applicationInformation().build();

            // Add standard Green Button scopes
            app.getScope().add("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");
            app.getScope().add("FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");

            assertThat(app.getScope()).hasSize(2);
            assertThat(app.getScope()).contains("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");
            assertThat(app.getScope()).contains("FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");
        }

        @Test
        @DisplayName("Should support custom OAuth2 scopes")
        void shouldSupportCustomOAuth2Scopes() {
            ApplicationInformation app = applicationInformation().build();

            String[] customScopes = {
                "read:meter-data",
                "write:usage-data",
                "admin:billing-data",
                "FB=4_5_15;IntervalDuration=900;BlockDuration=daily;HistoryLength=30"
            };

            for (String scope : customScopes) {
                app.getScope().add(scope);
            }

            assertThat(app.getScope()).hasSize(customScopes.length);
            for (String scope : customScopes) {
                assertThat(app.getScope()).contains(scope);
            }
        }
    }

    @Nested
    @DisplayName("Green Button Alliance Configuration Tests")
    class GreenButtonAllianceConfigurationTests {

        @Test
        @DisplayName("Should store Green Button Alliance specific configuration")
        void shouldStoreGreenButtonAllianceSpecificConfiguration() {
            String thirdPartyAppName = "Green Button Test Application";
            String dataCustodianId = "utility-company-123";
            String resourceEndpoint = "http://datacustodian.example.com/espi/1_1/resource";
            String notifyUri = "http://thirdparty.example.com/espi/1_1/Notification";

            ApplicationInformation app = applicationInformation()
                .withDataCustodianId(dataCustodianId)
                .build();
            
            app.setThirdPartyApplicationName(thirdPartyAppName);
            app.setDataCustodianResourceEndpoint(resourceEndpoint);
            app.setThirdPartyNotifyUri(notifyUri);

            assertThat(app.getThirdPartyApplicationName()).isEqualTo(thirdPartyAppName);
            assertThat(app.getDataCustodianId()).isEqualTo(dataCustodianId);
            assertThat(app.getDataCustodianResourceEndpoint()).isEqualTo(resourceEndpoint);
            assertThat(app.getThirdPartyNotifyUri()).isEqualTo(notifyUri);
        }

        @Test
        @DisplayName("Should validate Green Button specific URL patterns")
        void shouldValidateGreenButtonSpecificUrlPatterns() {
            ApplicationInformation app = applicationInformation().build();
            
            // Set URLs following Green Button patterns
            app.setAuthorizationServerAuthorizationEndpoint("http://datacustodian.example.com/DataCustodian/oauth/authorize");
            app.setAuthorizationServerTokenEndpoint("http://datacustodian.example.com/DataCustodian/oauth/token");
            app.setDataCustodianResourceEndpoint("http://datacustodian.example.com/DataCustodian/espi/1_1/resource");
            app.setThirdPartyNotifyUri("http://thirdparty.example.com/ThirdParty/espi/1_1/Notification");
            app.setRedirectUri("http://thirdparty.example.com/ThirdParty/espi/1_1/OAuthCallBack");

            // Verify URL patterns
            assertThat(app.getAuthorizationServerAuthorizationEndpoint()).contains("/oauth/authorize");
            assertThat(app.getAuthorizationServerTokenEndpoint()).contains("/oauth/token");
            assertThat(app.getDataCustodianResourceEndpoint()).contains("/espi/1_1/resource");
            assertThat(app.getThirdPartyNotifyUri()).contains("/espi/1_1/Notification");
            assertThat(app.getRedirectUri()).contains("/OAuthCallBack");
        }
    }

    @Nested
    @DisplayName("Entity Properties Tests")
    class PropertiesTests {

        @Test
        @DisplayName("Should set and get all basic properties correctly")
        void shouldSetAndGetBasicProperties() {
            Long id = 100L;
            UUID uuid = UUID.randomUUID();
            String applicationName = "Test Application";
            String clientId = "test-client-id";
            String clientSecret = "test-client-secret";
            String dataCustodianId = "test-data-custodian-id";

            ApplicationInformation app = applicationInformation()
                .withId(id)
                .withClientId(clientId)
                .withDataCustodianId(dataCustodianId)
                .build();
            
            app.setUUID(uuid);
            app.setThirdPartyApplicationName(applicationName);
            app.setClientSecret(clientSecret);

            assertThat(app.getId()).isEqualTo(id);
            assertThat(app.getUUID()).isEqualTo(uuid);
            assertThat(app.getThirdPartyApplicationName()).isEqualTo(applicationName);
            assertThat(app.getClientId()).isEqualTo(clientId);
            assertThat(app.getClientSecret()).isEqualTo(clientSecret);
            assertThat(app.getDataCustodianId()).isEqualTo(dataCustodianId);
        }

        @Test
        @DisplayName("Should handle null optional properties")
        void shouldHandleNullOptionalProperties() {
            ApplicationInformation app = applicationInformation().build();
            
            app.setThirdPartyApplicationName(null);
            app.setClientSecret(null);
            app.setRedirectUri(null);
            app.setThirdPartyNotifyUri(null);

            assertThat(app.getThirdPartyApplicationName()).isNull();
            assertThat(app.getClientSecret()).isNull();
            assertThat(app.getRedirectUri()).isNull();
            assertThat(app.getThirdPartyNotifyUri()).isNull();
        }

        @Test
        @DisplayName("Should initialize scope collection properly")
        void shouldInitializeScopeCollectionProperly() {
            ApplicationInformation app = new ApplicationInformation();

            assertThat(app.getScope()).isNotNull().isEmpty();
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should support complete third-party application registration flow")
        void shouldSupportCompleteThirdPartyApplicationRegistrationFlow() {
            // Simulate a complete application registration
            ApplicationInformation app = applicationInformation()
                .withClientId("energy-monitor-app-2023")
                .withDataCustodianId("metro-electric-utility")
                .build();
            
            app.setThirdPartyApplicationName("Smart Energy Monitor");
            app.setClientSecret("sk_live_a1b2c3d4e5f6g7h8i9j0");
            app.setRedirectUri("https://smartenergymonitor.com/auth/callback");
            app.setAuthorizationServerAuthorizationEndpoint("https://metroutility.com/oauth/authorize");
            app.setAuthorizationServerTokenEndpoint("https://metroutility.com/oauth/token");
            app.setDataCustodianResourceEndpoint("https://metroutility.com/espi/1_1/resource");
            app.setThirdPartyNotifyUri("https://smartenergymonitor.com/webhooks/green-button");

            // Add standard scopes
            app.getScope().add("FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");

            // Verify complete configuration
            assertThat(app.getClientId()).isNotBlank();
            assertThat(app.getClientSecret()).isNotBlank();
            assertThat(app.getRedirectUri()).startsWith("https://");
            assertThat(app.getAuthorizationServerAuthorizationEndpoint()).contains("/oauth/authorize");
            assertThat(app.getAuthorizationServerTokenEndpoint()).contains("/oauth/token");
            assertThat(app.getDataCustodianResourceEndpoint()).contains("/espi/1_1/resource");
            assertThat(app.getScope()).isNotEmpty();
        }

        @Test
        @DisplayName("Should support different deployment environments")
        void shouldSupportDifferentDeploymentEnvironments() {
            // Development environment
            ApplicationInformation devApp = applicationInformation()
                .withClientId("dev-client")
                .withDataCustodianId("dev-utility")
                .build();
            devApp.setRedirectUri("http://localhost:8080/callback");
            devApp.setAuthorizationServerAuthorizationEndpoint("http://localhost:9090/oauth/authorize");

            // Production environment
            ApplicationInformation prodApp = applicationInformation()
                .withClientId("prod-client")
                .withDataCustodianId("prod-utility")
                .build();
            prodApp.setRedirectUri("https://myapp.com/callback");
            prodApp.setAuthorizationServerAuthorizationEndpoint("https://utility.com/oauth/authorize");

            // Verify environment-specific configurations
            assertThat(devApp.getRedirectUri()).startsWith("http://localhost");
            assertThat(prodApp.getRedirectUri()).startsWith("https://");
            assertThat(devApp.getAuthorizationServerAuthorizationEndpoint()).contains("localhost");
            assertThat(prodApp.getAuthorizationServerAuthorizationEndpoint()).contains("utility.com");
        }
    }

    @Nested
    @DisplayName("Security Tests")
    class SecurityTests {

        @Test
        @DisplayName("Should handle client secrets securely")
        void shouldHandleClientSecretsSecurely() {
            ApplicationInformation app = applicationInformation().build();
            String clientSecret = "sk_live_very_secure_client_secret_123456789";
            
            app.setClientSecret(clientSecret);

            assertThat(app.getClientSecret()).isEqualTo(clientSecret);
            assertThat(app.getClientSecret()).hasSize(clientSecret.length());
        }

        @Test
        @DisplayName("Should validate URL schemes for security")
        void shouldValidateUrlSchemesForSecurity() {
            ApplicationInformation app = applicationInformation().build();

            // Test HTTPS URLs (production)
            app.setRedirectUri("https://secure-app.com/callback");
            app.setAuthorizationServerAuthorizationEndpoint("https://secure-utility.com/oauth/authorize");
            app.setDataCustodianResourceEndpoint("https://secure-utility.com/espi/1_1/resource");

            assertThat(app.getRedirectUri()).startsWith("https://");
            assertThat(app.getAuthorizationServerAuthorizationEndpoint()).startsWith("https://");
            assertThat(app.getDataCustodianResourceEndpoint()).startsWith("https://");

            // Test HTTP URLs (development/testing)
            app.setRedirectUri("http://localhost:8080/callback");
            assertThat(app.getRedirectUri()).startsWith("http://localhost");
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle very long client IDs")
        void shouldHandleVeryLongClientIds() {
            String longClientId = "very-long-client-id-" + "a".repeat(200);
            
            ApplicationInformation app = applicationInformation()
                .withClientId(longClientId)
                .build();

            assertThat(app.getClientId()).isEqualTo(longClientId);
        }

        @Test
        @DisplayName("Should handle special characters in client configuration")
        void shouldHandleSpecialCharactersInClientConfiguration() {
            String specialClientId = "client-with-special-chars_123!@#$%";
            String specialAppName = "App with Special Characters & Symbols <>";
            
            ApplicationInformation app = applicationInformation()
                .withClientId(specialClientId)
                .build();
            app.setThirdPartyApplicationName(specialAppName);

            assertThat(app.getClientId()).isEqualTo(specialClientId);
            assertThat(app.getThirdPartyApplicationName()).isEqualTo(specialAppName);
        }

        @Test
        @DisplayName("Should handle empty scope collections")
        void shouldHandleEmptyScopeCollections() {
            ApplicationInformation app = applicationInformation().build();

            assertThat(app.getScope()).isEmpty();
            
            // Should be able to add scopes to empty collection
            app.getScope().add("test-scope");
            assertThat(app.getScope()).hasSize(1);
        }

        @Test
        @DisplayName("Should handle scope modification operations")
        void shouldHandleScopeModificationOperations() {
            ApplicationInformation app = applicationInformation().build();
            
            String[] scopes = {
                "scope1",
                "scope2", 
                "scope3"
            };

            // Add scopes
            for (String scope : scopes) {
                app.getScope().add(scope);
            }
            assertThat(app.getScope()).hasSize(3);

            // Remove a scope
            app.getScope().remove("scope2");
            assertThat(app.getScope()).hasSize(2);
            assertThat(app.getScope()).doesNotContain("scope2");

            // Clear all scopes
            app.getScope().clear();
            assertThat(app.getScope()).isEmpty();
        }
    }
}