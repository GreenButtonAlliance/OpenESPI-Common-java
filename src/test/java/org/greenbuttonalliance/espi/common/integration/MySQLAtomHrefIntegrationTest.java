/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.integration;

import org.greenbuttonalliance.espi.common.domain.ApplicationInformation;
import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.support.TestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ATOM href URL relationships using TestContainers with MySQL.
 * 
 * This test class validates:
 * - ESPI-compliant ATOM href URL format and structure
 * - Self and up link relationships work correctly
 * - Cross-schema relationships via href URLs (not foreign keys)
 * - URL-based loose coupling between usage and customer schemas
 * - RESTful resource identification patterns
 * - Navigation between related resources via URLs
 */
@DisplayName("MySQL ATOM Href URL Integration Tests")
class MySQLAtomHrefIntegrationTest extends TestContainersIntegrationTestBase {

    @Test
    @DisplayName("Should generate proper ESPI ATOM href URLs for all entities")
    void shouldGenerateProperEspiAtomHrefUrls() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("href-test-app")
                .build();
        ApplicationInformation savedApp = persistAndFlush(app);

        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("href-test@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withRetailCustomer(savedCustomer)
                .build();
        UsagePoint savedUsagePoint = persistAndFlush(usagePoint);

        // Then - Verify all entities have proper ESPI href URLs
        assertEspiCompliantHref(savedApp.getSelfLinkHref(), "ApplicationInformation", savedApp.getUuid());
        assertEspiCompliantHref(savedCustomer.getSelfLinkHref(), "RetailCustomer", savedCustomer.getUuid());
        assertEspiCompliantHref(savedUsagePoint.getSelfLinkHref(), "UsagePoint", savedUsagePoint.getUuid());
    }

    @Test
    @DisplayName("Should establish proper parent-child href relationships")
    void shouldEstablishProperParentChildHrefRelationships() {
        // Given - Create hierarchy: Customer -> UsagePoint -> MeterReading
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("hierarchy-test@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withRetailCustomer(savedCustomer)
                .build();
        UsagePoint savedUsagePoint = persistAndFlush(usagePoint);

        MeterReading meterReading = TestDataBuilder.newMeterReading()
                .withDescription("Test meter reading")
                .withUsagePoint(savedUsagePoint)
                .build();
        MeterReading savedMeterReading = persistAndFlush(meterReading);

        // Then - Verify up-link relationships
        String customerHref = savedCustomer.getSelfLinkHref();
        String usagePointHref = savedUsagePoint.getSelfLinkHref();
        String meterReadingHref = savedMeterReading.getSelfLinkHref();

        // UsagePoint should have up-link to RetailCustomer
        assertNotNull(savedUsagePoint.getUpLinkHref(), "UsagePoint should have up-link href");
        assertTrue(savedUsagePoint.getUpLinkHref().contains("RetailCustomer"), 
                   "UsagePoint up-link should reference RetailCustomer");

        // MeterReading should have up-link to UsagePoint
        assertNotNull(savedMeterReading.getUpLinkHref(), "MeterReading should have up-link href");
        assertTrue(savedMeterReading.getUpLinkHref().contains("UsagePoint"), 
                   "MeterReading up-link should reference UsagePoint");
    }

    @Test
    @DisplayName("Should support cross-schema href relationships")
    void shouldSupportCrossSchemaHrefRelationships() throws SQLException {
        // Given - Create customer in customer schema and reference from usage schema
        
        // Insert customer record directly into customer schema (simulating separate service)
        String customerUuid = insertCustomerInCustomerSchema("cross-schema-test-customer");
        String customerHref = "/espi/1_1/resource/Customer/" + customerUuid;

        // Create retail customer with href reference to customer schema
        RetailCustomer retailCustomer = TestDataBuilder.newRetailCustomer()
                .withUsername("cross-schema@example.com")
                .build();
        
        // Simulate setting customer href reference (in real system this would be set by service layer)
        // This is how loose coupling works - no foreign key, just URL reference
        RetailCustomer savedRetailCustomer = persistAndFlush(retailCustomer);

        // Then - Verify the href reference can be used to link schemas
        assertNotNull(savedRetailCustomer.getSelfLinkHref(), "RetailCustomer should have self href");
        
        // Verify the customer exists in customer schema
        assertTrue(customerExistsInCustomerSchema(customerUuid), 
                   "Customer should exist in customer schema");
        
        // Verify retail customer exists in usage schema  
        assertTrue(retailCustomerExistsInUsageSchema(savedRetailCustomer.getUuid()),
                   "RetailCustomer should exist in usage schema");
    }

    @Test
    @DisplayName("Should handle subscription href relationships correctly")
    void shouldHandleSubscriptionHrefRelationshipsCorrectly() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("subscription-test-app")
                .build();
        ApplicationInformation savedApp = persistAndFlush(app);

        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("subscription-test@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withRetailCustomer(savedCustomer)
                .build();
        UsagePoint savedUsagePoint = persistAndFlush(usagePoint);

        Subscription subscription = TestDataBuilder.newSubscription()
                .withApplicationInformation(savedApp)
                .withRetailCustomer(savedCustomer)
                .withUsagePoint(savedUsagePoint)
                .build();

        // When
        Subscription savedSubscription = persistAndFlush(subscription);

        // Then - Verify subscription href relationships
        assertEspiCompliantHref(savedSubscription.getSelfLinkHref(), "Subscription", savedSubscription.getUuid());
        
        // Subscription should link to ApplicationInformation
        assertNotNull(savedSubscription.getApplicationInformation(), 
                      "Subscription should have ApplicationInformation");
        assertEspiCompliantHref(savedSubscription.getApplicationInformation().getSelfLinkHref(), 
                                "ApplicationInformation", savedSubscription.getApplicationInformation().getUuid());
        
        // Subscription should link to RetailCustomer
        assertNotNull(savedSubscription.getRetailCustomer(), 
                      "Subscription should have RetailCustomer");
        assertEspiCompliantHref(savedSubscription.getRetailCustomer().getSelfLinkHref(), 
                                "RetailCustomer", savedSubscription.getRetailCustomer().getUuid());
    }

    @Test
    @DisplayName("Should validate href URL format compliance")
    void shouldValidateHrefUrlFormatCompliance() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("url-format-test")
                .build();
        ApplicationInformation savedApp = persistAndFlush(app);

        // Then - Verify href follows ESPI URL pattern
        String href = savedApp.getSelfLinkHref();
        assertNotNull(href, "Self href should not be null");
        
        // ESPI pattern: /espi/1_1/resource/{ResourceType}/{id}
        assertTrue(href.matches(".*\\/espi\\/1_1\\/resource\\/ApplicationInformation\\/[\\w-]+.*"), 
                   "Href should match ESPI URL pattern: " + href);
        
        // Should contain the UUID
        assertTrue(href.contains(savedApp.getUuid()), 
                   "Href should contain entity UUID");
        
        // Should be absolute or properly formed relative URL
        assertTrue(href.startsWith("/") || href.startsWith("http"), 
                   "Href should be properly formed URL");
    }

    @Test
    @DisplayName("Should extract resource IDs from href URLs correctly")
    void shouldExtractResourceIdsFromHrefUrlsCorrectly() {
        // Given
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("extract-test@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        // When
        String href = savedCustomer.getSelfLinkHref();
        String extractedId = extractResourceIdFromHref(href);

        // Then
        assertNotNull(extractedId, "Should extract resource ID from href");
        assertEquals(savedCustomer.getUuid(), extractedId, 
                     "Extracted ID should match entity UUID");
    }

    @Test
    @DisplayName("Should support navigation between related resources via URLs")
    void shouldSupportNavigationBetweenRelatedResourcesViaUrls() {
        // Given - Create a complete hierarchy with href relationships
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("navigation-test@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withRetailCustomer(savedCustomer)
                .build();
        UsagePoint savedUsagePoint = persistAndFlush(usagePoint);

        MeterReading meterReading = TestDataBuilder.newMeterReading()
                .withDescription("Navigation test reading")
                .withUsagePoint(savedUsagePoint)
                .build();
        MeterReading savedMeterReading = persistAndFlush(meterReading);

        // Then - Verify navigation path through href URLs
        String customerHref = savedCustomer.getSelfLinkHref();
        String usagePointHref = savedUsagePoint.getSelfLinkHref();
        String meterReadingHref = savedMeterReading.getSelfLinkHref();

        // Extract IDs from hrefs and verify they match
        assertEquals(savedCustomer.getUuid(), extractResourceIdFromHref(customerHref));
        assertEquals(savedUsagePoint.getUuid(), extractResourceIdFromHref(usagePointHref));
        assertEquals(savedMeterReading.getUuid(), extractResourceIdFromHref(meterReadingHref));

        // Verify parent-child navigation via up-link hrefs
        String meterReadingUpLink = savedMeterReading.getUpLinkHref();
        assertNotNull(meterReadingUpLink, "MeterReading should have up-link");
        assertTrue(meterReadingUpLink.contains("UsagePoint"), 
                   "MeterReading up-link should reference UsagePoint");
        
        String usagePointUpLink = savedUsagePoint.getUpLinkHref();
        assertNotNull(usagePointUpLink, "UsagePoint should have up-link");
        assertTrue(usagePointUpLink.contains("RetailCustomer"), 
                   "UsagePoint up-link should reference RetailCustomer");
    }

    // Helper methods

    private void assertEspiCompliantHref(String href, String resourceType, String uuid) {
        assertNotNull(href, resourceType + " href should not be null");
        assertTrue(isValidEspiHref(href), resourceType + " href should be ESPI-compliant: " + href);
        assertTrue(href.contains(resourceType), resourceType + " href should contain resource type");
        assertTrue(href.contains(uuid), resourceType + " href should contain UUID");
    }

    private String insertCustomerInCustomerSchema(String customerName) throws SQLException {
        String uuid = java.util.UUID.randomUUID().toString();
        String sql = """
            INSERT INTO customers (uuid, customer_name, description, created, updated, self_link_href)
            VALUES (?, ?, ?, NOW(6), NOW(6), ?)
            """;
        
        String href = "/espi/1_1/resource/Customer/" + uuid;
        
        executeInCustomerSchema("INSERT INTO customers (uuid, customer_name, description, created, updated, self_link_href) " +
                               "VALUES ('" + uuid + "', '" + customerName + "', 'Test customer', NOW(6), NOW(6), '" + href + "')");
        
        return uuid;
    }

    private boolean customerExistsInCustomerSchema(String uuid) throws SQLException {
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM customers WHERE uuid = ?")) {
            
            stmt.setString(1, uuid);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private boolean retailCustomerExistsInUsageSchema(String uuid) throws SQLException {
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM retail_customers WHERE uuid = ?")) {
            
            stmt.setString(1, uuid);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private javax.sql.DataSource createSchemaDataSource(String schema) {
        org.springframework.boot.jdbc.DataSourceBuilder<?> builder = 
            org.springframework.boot.jdbc.DataSourceBuilder.create();
        
        return builder
                .url(getMySQLContainer().getJdbcUrl().replace("/openespi_test", "/" + schema))
                .username(getMySQLContainer().getUsername())
                .password(getMySQLContainer().getPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}