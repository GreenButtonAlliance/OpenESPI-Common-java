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
 * Integration tests for ATOM href URL relationships using TestContainers with PostgreSQL.
 * 
 * This test class validates:
 * - ESPI-compliant ATOM href URL format and structure with PostgreSQL
 * - Self and up link relationships work correctly
 * - Cross-schema relationships via href URLs (not foreign keys)
 * - URL-based loose coupling between usage and customer schemas
 * - RESTful resource identification patterns
 * - Navigation between related resources via URLs
 * - PostgreSQL-specific URL storage and retrieval
 * - PostgreSQL text field handling for URL storage
 */
@DisplayName("PostgreSQL ATOM Href URL Integration Tests")
class PostgreSQLAtomHrefIntegrationTest extends PostgreSQLTestContainersIntegrationTestBase {

    @Test
    @DisplayName("Should generate proper ESPI href URLs for ApplicationInformation")
    void shouldGenerateProperEspiHrefUrlsForApplicationInformation() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("href-test-app-123")
                .withClientName("HREF Test Application")
                .build();

        // When
        ApplicationInformation saved = persistAndFlush(app);

        // Then
        assertNotNull(saved.getSelfLinkHref(), "Self link href should be generated");
        assertTrue(isValidEspiHref(saved.getSelfLinkHref()), 
                   "Self link href should follow ESPI pattern: " + saved.getSelfLinkHref());
        
        // Extract and validate resource ID from href
        String resourceId = extractResourceIdFromHref(saved.getSelfLinkHref());
        assertNotNull(resourceId, "Should be able to extract resource ID from href");
        assertEquals(saved.getUuid(), resourceId, "Resource ID should match entity UUID");
        
        // Verify href pattern: /espi/1_1/resource/ApplicationInformation/{uuid}
        assertTrue(saved.getSelfLinkHref().contains("/espi/1_1/resource/ApplicationInformation/"),
                   "Href should contain proper ESPI ApplicationInformation resource path");
        assertTrue(saved.getSelfLinkHref().endsWith(saved.getUuid()),
                   "Href should end with entity UUID");
    }

    @Test
    @DisplayName("Should generate proper ESPI href URLs for RetailCustomer")
    void shouldGenerateProperEspiHrefUrlsForRetailCustomer() {
        // Given
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("href-test-customer@example.com")
                .withFirstName("HREF")
                .withLastName("TestCustomer")
                .build();

        // When
        RetailCustomer saved = persistAndFlush(customer);

        // Then
        assertNotNull(saved.getSelfLinkHref(), "Self link href should be generated");
        assertTrue(isValidEspiHref(saved.getSelfLinkHref()), 
                   "Self link href should follow ESPI pattern: " + saved.getSelfLinkHref());
        
        // Verify href pattern: /espi/1_1/resource/RetailCustomer/{uuid}
        assertTrue(saved.getSelfLinkHref().contains("/espi/1_1/resource/RetailCustomer/"),
                   "Href should contain proper ESPI RetailCustomer resource path");
        assertTrue(saved.getSelfLinkHref().endsWith(saved.getUuid()),
                   "Href should end with entity UUID");
    }

    @Test
    @DisplayName("Should maintain href relationships in entity hierarchy")
    void shouldMaintainHrefRelationshipsInEntityHierarchy() {
        // Given - Create hierarchy: RetailCustomer -> UsagePoint -> MeterReading
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("hierarchy-customer@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withDescription("Hierarchy test usage point")
                .withRetailCustomer(savedCustomer)
                .build();
        UsagePoint savedUsagePoint = persistAndFlush(usagePoint);

        MeterReading meterReading = TestDataBuilder.newMeterReading()
                .withDescription("Hierarchy test meter reading")
                .withUsagePoint(savedUsagePoint)
                .build();

        // When
        MeterReading savedMeterReading = persistAndFlush(meterReading);
        flushAndClear();

        // Retrieve and verify
        MeterReading retrieved = entityManager.find(MeterReading.class, savedMeterReading.getId());

        // Then
        // Verify all entities have proper self hrefs
        assertTrue(isValidEspiHref(retrieved.getSelfLinkHref()),
                   "MeterReading should have valid self href");
        assertTrue(isValidEspiHref(retrieved.getUsagePoint().getSelfLinkHref()),
                   "UsagePoint should have valid self href");
        assertTrue(isValidEspiHref(retrieved.getUsagePoint().getRetailCustomer().getSelfLinkHref()),
                   "RetailCustomer should have valid self href");

        // Verify up link relationships
        assertNotNull(retrieved.getUpLinkHref(), "MeterReading should have up link");
        assertTrue(retrieved.getUpLinkHref().contains(retrieved.getUsagePoint().getUuid()),
                   "MeterReading up link should reference UsagePoint UUID");

        assertNotNull(retrieved.getUsagePoint().getUpLinkHref(), "UsagePoint should have up link");
        assertTrue(retrieved.getUsagePoint().getUpLinkHref().contains(retrieved.getUsagePoint().getRetailCustomer().getUuid()),
                   "UsagePoint up link should reference RetailCustomer UUID");
    }

    @Test
    @DisplayName("Should store and retrieve long href URLs in PostgreSQL")
    void shouldStoreAndRetrieveLongHrefUrlsInPostgreSQL() {
        // Given - Create href with long URL (testing PostgreSQL TEXT field handling)
        StringBuilder longHref = new StringBuilder();
        longHref.append("https://datacustodian.example.com:8443/DataCustodian/espi/1_1/resource/ApplicationInformation/");
        longHref.append("very-long-uuid-with-additional-path-parameters-and-query-strings");
        longHref.append("?access_token=").append("a".repeat(500)); // Very long access token
        longHref.append("&scope=FB=1_3_4_5_13_14_39;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13");

        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("long-href-test-123")
                .build();
        
        // Manually set long href for testing
        app.setSelfLinkHref(longHref.toString());

        // When
        ApplicationInformation saved = persistAndFlush(app);
        flushAndClear();
        
        ApplicationInformation retrieved = entityManager.find(ApplicationInformation.class, saved.getId());

        // Then
        assertNotNull(retrieved.getSelfLinkHref(), "Long href should be stored");
        assertEquals(longHref.toString(), retrieved.getSelfLinkHref(), "Long href should match exactly");
        assertTrue(retrieved.getSelfLinkHref().length() > 1000, "Href should be long (>1KB)");
    }

    @Test
    @DisplayName("Should handle cross-schema href references without foreign keys")
    void shouldHandleCrossSchemaHrefReferencesWithoutForeignKeys() throws SQLException {
        // Given - Create entities in different schemas that reference each other via href only
        
        // Create RetailCustomer in usage schema
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("cross-schema-test@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);
        
        // Create a cross-reference href that would point to customer schema
        String customerSchemaHref = "/espi/1_1/resource/Customer/" + savedCustomer.getUuid();
        
        // Store cross-reference in subscription
        Subscription subscription = TestDataBuilder.newSubscription()
                .withDescription("Cross-schema test subscription")
                .withRetailCustomer(savedCustomer)
                .build();
        subscription.setUpLinkHref(customerSchemaHref); // Cross-schema reference
        
        // When
        Subscription savedSubscription = persistAndFlush(subscription);
        flushAndClear();

        // Then
        Subscription retrieved = entityManager.find(Subscription.class, savedSubscription.getId());
        assertEquals(customerSchemaHref, retrieved.getUpLinkHref(),
                     "Cross-schema href should be stored correctly");
        
        // Verify no foreign key constraints exist between schemas by checking database metadata
        assertFalse(hasForeignKeyConstraintBetweenSchemas("openespi_usage", "openespi_customer"),
                    "No foreign key constraints should exist between schemas");
    }

    @Test
    @DisplayName("Should validate ESPI href format compliance")
    void shouldValidateEspiHrefFormatCompliance() {
        // Given - Test various href formats
        String validHref1 = "/espi/1_1/resource/ApplicationInformation/550e8400-e29b-41d4-a716-446655440000";
        String validHref2 = "https://example.com/DataCustodian/espi/1_1/resource/RetailCustomer/550e8400-e29b-41d4-a716-446655440001";
        String validHref3 = "/espi/1_1/resource/UsagePoint/550e8400-e29b-41d4-a716-446655440002/MeterReading";
        
        String invalidHref1 = "/invalid/path/resource/ApplicationInformation/550e8400-e29b-41d4-a716-446655440000";
        String invalidHref2 = "/espi/2_0/resource/ApplicationInformation/550e8400-e29b-41d4-a716-446655440000"; // Wrong version
        String invalidHref3 = "/espi/1_1/ApplicationInformation/550e8400-e29b-41d4-a716-446655440000"; // Missing "resource"

        // When/Then - Test valid hrefs
        assertTrue(isValidEspiHref(validHref1), "Valid ESPI href 1 should pass validation");
        assertTrue(isValidEspiHref(validHref2), "Valid ESPI href 2 should pass validation");
        assertTrue(isValidEspiHref(validHref3), "Valid ESPI href 3 should pass validation");

        // Test invalid hrefs
        assertFalse(isValidEspiHref(invalidHref1), "Invalid ESPI href 1 should fail validation");
        assertFalse(isValidEspiHref(invalidHref2), "Invalid ESPI href 2 should fail validation");
        assertFalse(isValidEspiHref(invalidHref3), "Invalid ESPI href 3 should fail validation");
        
        // Test edge cases
        assertFalse(isValidEspiHref(null), "Null href should fail validation");
        assertFalse(isValidEspiHref(""), "Empty href should fail validation");
        assertFalse(isValidEspiHref("   "), "Whitespace href should fail validation");
    }

    @Test
    @DisplayName("Should extract resource IDs from ESPI hrefs correctly")
    void shouldExtractResourceIdsFromEspiHrefsCorrectly() {
        // Given - Test various href formats
        String href1 = "/espi/1_1/resource/ApplicationInformation/550e8400-e29b-41d4-a716-446655440000";
        String href2 = "https://example.com/espi/1_1/resource/RetailCustomer/550e8400-e29b-41d4-a716-446655440001";
        String href3 = "/espi/1_1/resource/UsagePoint/550e8400-e29b-41d4-a716-446655440002/MeterReading/550e8400-e29b-41d4-a716-446655440003";
        
        // When/Then
        assertEquals("550e8400-e29b-41d4-a716-446655440000", extractResourceIdFromHref(href1),
                     "Should extract correct resource ID from href1");
        assertEquals("550e8400-e29b-41d4-a716-446655440001", extractResourceIdFromHref(href2),
                     "Should extract correct resource ID from href2");
        assertEquals("550e8400-e29b-41d4-a716-446655440002", extractResourceIdFromHref(href3),
                     "Should extract correct resource ID from href3");
        
        // Test invalid hrefs
        assertNull(extractResourceIdFromHref("/invalid/path"), "Should return null for invalid href");
        assertNull(extractResourceIdFromHref(null), "Should return null for null href");
    }

    @Test
    @DisplayName("Should verify href URL persistence in PostgreSQL database")
    void shouldVerifyHrefUrlPersistenceInPostgreSQLDatabase() throws SQLException {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("href-persistence-test-123")
                .build();

        // When
        ApplicationInformation saved = persistAndFlush(app);

        // Then - Verify href URLs are properly stored in PostgreSQL
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT self_link_href, up_link_href FROM openespi_usage.application_information WHERE id = ?")) {
            
            stmt.setLong(1, saved.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "Should find the application record");
                
                String dbSelfHref = rs.getString("self_link_href");
                String dbUpHref = rs.getString("up_link_href");
                
                assertNotNull(dbSelfHref, "Self href should be stored in database");
                assertEquals(saved.getSelfLinkHref(), dbSelfHref, "Database self href should match entity");
                
                // Verify href format at database level
                assertTrue(dbSelfHref.contains("/espi/1_1/resource/ApplicationInformation/"),
                           "Database href should contain proper ESPI pattern");
                assertTrue(dbSelfHref.endsWith(saved.getUuid()),
                           "Database href should end with entity UUID");
            }
        }
    }

    @Test
    @DisplayName("Should handle href URLs with special characters and encoding")
    void shouldHandleHrefUrlsWithSpecialCharactersAndEncoding() {
        // Given - Create href with special characters that need URL encoding
        String specialHref = "/espi/1_1/resource/ApplicationInformation/test-uuid-123" +
                           "?client_name=Test%20App%20with%20Spaces&redirect_uri=https%3A//example.com/callback%3Fstate%3Dabc123";
        
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("special-chars-test-123")
                .build();
        app.setSelfLinkHref(specialHref);

        // When
        ApplicationInformation saved = persistAndFlush(app);
        flushAndClear();
        
        ApplicationInformation retrieved = entityManager.find(ApplicationInformation.class, saved.getId());

        // Then
        assertEquals(specialHref, retrieved.getSelfLinkHref(),
                     "Special characters in href should be preserved");
        assertTrue(retrieved.getSelfLinkHref().contains("%20"), "URL encoding should be preserved");
        assertTrue(retrieved.getSelfLinkHref().contains("%3A"), "URL encoding should be preserved");
    }

    // Helper methods

    private boolean hasForeignKeyConstraintBetweenSchemas(String schema1, String schema2) throws SQLException {
        try (Connection conn = getPostgreSQLContainer().createConnection("")) {
            // Query PostgreSQL system tables to check for foreign key constraints between schemas
            String sql = """
                SELECT COUNT(*) as fk_count
                FROM information_schema.table_constraints tc
                JOIN information_schema.referential_constraints rc ON tc.constraint_name = rc.constraint_name
                JOIN information_schema.table_constraints tc2 ON rc.unique_constraint_name = tc2.constraint_name
                WHERE tc.table_schema = ? AND tc2.table_schema = ? AND tc.constraint_type = 'FOREIGN KEY'
                """;
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, schema1);
                stmt.setString(2, schema2);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() && rs.getInt("fk_count") > 0;
                }
            }
        }
    }

    private javax.sql.DataSource createSchemaDataSource(String schema) {
        org.springframework.boot.jdbc.DataSourceBuilder<?> builder = 
            org.springframework.boot.jdbc.DataSourceBuilder.create();
        
        return builder
                .url(getPostgreSQLContainer().getJdbcUrl() + "?currentSchema=" + schema)
                .username(getPostgreSQLContainer().getUsername())
                .password(getPostgreSQLContainer().getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}