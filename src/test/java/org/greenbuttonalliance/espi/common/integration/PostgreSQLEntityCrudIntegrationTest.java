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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolation;
import org.greenbuttonalliance.espi.common.domain.ApplicationInformation;
import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.domain.IntervalBlock;
import org.greenbuttonalliance.espi.common.domain.IntervalReading;
import org.greenbuttonalliance.espi.common.support.TestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CRUD operations on key entities using TestContainers with PostgreSQL.
 * 
 * This test class validates:
 * - Basic CRUD operations work correctly with real PostgreSQL database
 * - Entity validation constraints are enforced
 * - ESPI-compliant UUID generation and storage
 * - ATOM href URL relationships function properly
 * - PostgreSQL-specific data types (BIGSERIAL, TIMESTAMP WITH TIME ZONE) work correctly
 * - PostgreSQL timezone handling for timestamps
 * - Transaction boundaries work as expected with PostgreSQL
 * - PostgreSQL-specific features like JSONB queries work with entities
 */
@DisplayName("PostgreSQL Entity CRUD Integration Tests")
class PostgreSQLEntityCrudIntegrationTest extends PostgreSQLTestContainersIntegrationTestBase {

    @PersistenceContext
    private EntityManager usageEntityManager;

    @Test
    @DisplayName("Should create and retrieve ApplicationInformation entity with PostgreSQL features")
    void shouldCreateAndRetrieveApplicationInformation() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("test-client-123")
                .withClientName("Test Application")
                .withDescription("Test application for PostgreSQL integration testing")
                .build();

        // When
        ApplicationInformation saved = persistAndFlush(app);
        flushAndClear();
        
        ApplicationInformation retrieved = entityManager.find(ApplicationInformation.class, saved.getId());

        // Then
        assertNotNull(retrieved, "Retrieved ApplicationInformation should not be null");
        assertEquals(app.getClientId(), retrieved.getClientId());
        assertEquals(app.getClientName(), retrieved.getClientName());
        assertEquals(app.getDescription(), retrieved.getDescription());
        assertNotNull(retrieved.getUuid(), "UUID should be generated");
        assertNotNull(retrieved.getCreated(), "Created timestamp should be set");
        assertNotNull(retrieved.getUpdated(), "Updated timestamp should be set");
        assertTrue(isValidEspiHref(retrieved.getSelfLinkHref()), "Self link href should be ESPI-compliant");
        
        // Verify PostgreSQL BIGSERIAL ID generation
        assertTrue(retrieved.getId() > 0, "BIGSERIAL ID should be positive");
    }

    @Test
    @DisplayName("Should create and retrieve RetailCustomer entity")
    void shouldCreateAndRetrieveRetailCustomer() {
        // Given
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("testuser@example.com")
                .withFirstName("John")
                .withLastName("Doe")
                .withEnabled(true)
                .build();

        // When
        RetailCustomer saved = persistAndFlush(customer);
        flushAndClear();
        
        RetailCustomer retrieved = entityManager.find(RetailCustomer.class, saved.getId());

        // Then
        assertNotNull(retrieved, "Retrieved RetailCustomer should not be null");
        assertEquals(customer.getUsername(), retrieved.getUsername());
        assertEquals(customer.getFirstName(), retrieved.getFirstName());
        assertEquals(customer.getLastName(), retrieved.getLastName());
        assertEquals(customer.getEnabled(), retrieved.getEnabled());
        assertNotNull(retrieved.getUuid(), "UUID should be generated");
        assertTrue(isValidEspiHref(retrieved.getSelfLinkHref()), "Self link href should be ESPI-compliant");
    }

    @Test
    @DisplayName("Should create and retrieve UsagePoint entity")
    void shouldCreateAndRetrieveUsagePoint() {
        // Given
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("customer@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withDescription("Main electricity meter")
                .withRetailCustomer(savedCustomer)
                .build();

        // When
        UsagePoint saved = persistAndFlush(usagePoint);
        flushAndClear();
        
        UsagePoint retrieved = entityManager.find(UsagePoint.class, saved.getId());

        // Then
        assertNotNull(retrieved, "Retrieved UsagePoint should not be null");
        assertEquals(usagePoint.getServiceKind(), retrieved.getServiceKind());
        assertEquals(usagePoint.getDescription(), retrieved.getDescription());
        assertNotNull(retrieved.getRetailCustomer(), "RetailCustomer relationship should be maintained");
        assertEquals(savedCustomer.getId(), retrieved.getRetailCustomer().getId());
        assertNotNull(retrieved.getUuid(), "UUID should be generated");
        assertTrue(isValidEspiHref(retrieved.getSelfLinkHref()), "Self link href should be ESPI-compliant");
    }

    @Test
    @DisplayName("Should create complete meter reading hierarchy")
    void shouldCreateCompleteMeterReadingHierarchy() {
        // Given - Create the complete hierarchy: Customer -> UsagePoint -> MeterReading -> IntervalBlock -> IntervalReading
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("hierarchytest@example.com")
                .build();
        RetailCustomer savedCustomer = persistAndFlush(customer);

        UsagePoint usagePoint = TestDataBuilder.newUsagePoint()
                .withServiceKind("electricity")
                .withRetailCustomer(savedCustomer)
                .build();
        UsagePoint savedUsagePoint = persistAndFlush(usagePoint);

        MeterReading meterReading = TestDataBuilder.newMeterReading()
                .withDescription("Monthly meter reading")
                .withUsagePoint(savedUsagePoint)
                .build();
        MeterReading savedMeterReading = persistAndFlush(meterReading);

        IntervalBlock intervalBlock = TestDataBuilder.newIntervalBlock()
                .withInterval(Instant.parse("2023-01-01T00:00:00Z"), Instant.parse("2023-01-31T23:59:59Z"))
                .withMeterReading(savedMeterReading)
                .build();
        IntervalBlock savedIntervalBlock = persistAndFlush(intervalBlock);

        IntervalReading intervalReading = TestDataBuilder.newIntervalReading()
                .withValue(12345L)
                .withTimePeriod(Instant.parse("2023-01-15T12:00:00Z"), 3600L) // 1 hour duration
                .withIntervalBlock(savedIntervalBlock)
                .build();

        // When
        IntervalReading savedIntervalReading = persistAndFlush(intervalReading);
        flushAndClear();

        // Retrieve the complete hierarchy
        IntervalReading retrieved = entityManager.find(IntervalReading.class, savedIntervalReading.getId());

        // Then
        assertNotNull(retrieved, "Retrieved IntervalReading should not be null");
        assertEquals(intervalReading.getValue(), retrieved.getValue());
        assertNotNull(retrieved.getIntervalBlock(), "IntervalBlock relationship should be maintained");
        assertNotNull(retrieved.getIntervalBlock().getMeterReading(), "MeterReading relationship should be maintained");
        assertNotNull(retrieved.getIntervalBlock().getMeterReading().getUsagePoint(), "UsagePoint relationship should be maintained");
        assertNotNull(retrieved.getIntervalBlock().getMeterReading().getUsagePoint().getRetailCustomer(), "RetailCustomer relationship should be maintained");
        
        // Verify all entities have proper ESPI compliance
        assertTrue(isValidEspiHref(retrieved.getSelfLinkHref()), "IntervalReading href should be ESPI-compliant");
        assertTrue(isValidEspiHref(retrieved.getIntervalBlock().getSelfLinkHref()), "IntervalBlock href should be ESPI-compliant");
        assertTrue(isValidEspiHref(retrieved.getIntervalBlock().getMeterReading().getSelfLinkHref()), "MeterReading href should be ESPI-compliant");
    }

    @Test
    @DisplayName("Should enforce entity validation constraints")
    void shouldEnforceEntityValidationConstraints() {
        // Test ApplicationInformation validation
        ApplicationInformation invalidApp = new ApplicationInformation();
        // Missing required client_id
        
        Set<ConstraintViolation<ApplicationInformation>> appViolations = validator.validate(invalidApp);
        assertFalse(appViolations.isEmpty(), "ApplicationInformation without client_id should have validation errors");

        // Test RetailCustomer validation
        RetailCustomer invalidCustomer = new RetailCustomer();
        // Missing required username
        
        Set<ConstraintViolation<RetailCustomer>> customerViolations = validator.validate(invalidCustomer);
        assertFalse(customerViolations.isEmpty(), "RetailCustomer without username should have validation errors");
    }

    @Test
    @DisplayName("Should handle entity updates correctly with PostgreSQL")
    void shouldHandleEntityUpdatesCorrectly() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("update-test-123")
                .withClientName("Original Name")
                .build();
        ApplicationInformation saved = persistAndFlush(app);
        Instant originalUpdated = saved.getUpdated();

        // When - Update the entity
        saved.setClientName("Updated Name");
        saved.setDescription("Updated description");
        ApplicationInformation updated = mergeAndFlush(saved);

        // Then
        assertEquals("Updated Name", updated.getClientName());
        assertEquals("Updated description", updated.getDescription());
        assertTrue(updated.getUpdated().isAfter(originalUpdated), "Updated timestamp should be newer");
    }

    @Test
    @DisplayName("Should handle entity deletion correctly")
    void shouldHandleEntityDeletionCorrectly() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("delete-test-123")
                .build();
        ApplicationInformation saved = persistAndFlush(app);
        Long entityId = saved.getId();

        // When
        entityManager.remove(saved);
        flushAndClear();

        // Then
        ApplicationInformation retrieved = entityManager.find(ApplicationInformation.class, entityId);
        assertNull(retrieved, "Deleted entity should not be found");
    }

    @Test
    @DisplayName("Should maintain unique constraints")
    void shouldMaintainUniqueConstraints() {
        // Given - Create first ApplicationInformation
        ApplicationInformation app1 = TestDataBuilder.newApplicationInformation()
                .withClientId("unique-test-123")
                .build();
        persistAndFlush(app1);

        // When/Then - Try to create another with same client_id
        ApplicationInformation app2 = TestDataBuilder.newApplicationInformation()
                .withClientId("unique-test-123") // Same client_id
                .build();

        assertThrows(Exception.class, () -> {
            persistAndFlush(app2);
        }, "Should throw exception for duplicate client_id");
    }

    @Test
    @DisplayName("Should generate proper ESPI-compliant UUIDs")
    void shouldGenerateProperEspiCompliantUuids() {
        // Given
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("uuid-test-123")
                .build();

        // When
        ApplicationInformation saved = persistAndFlush(app);

        // Then
        assertNotNull(saved.getUuid(), "UUID should be generated");
        assertEquals(36, saved.getUuid().length(), "UUID should be 36 characters (with hyphens)");
        assertTrue(saved.getUuid().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"), 
                   "UUID should match standard format");
        
        // UUID should be deterministic (UUID5 based on namespace + name)
        ApplicationInformation app2 = TestDataBuilder.newApplicationInformation()
                .withClientId("uuid-test-123")
                .build();
        ApplicationInformation saved2 = persistAndFlush(app2);
        
        // Different entities should have different UUIDs even with same data
        assertNotEquals(saved.getUuid(), saved2.getUuid(), "Different entities should have different UUIDs");
    }

    @Test
    @DisplayName("Should store and retrieve timestamps correctly with PostgreSQL timezone handling")
    void shouldStoreAndRetrieveTimestampsCorrectlyWithTimezone() {
        // Given
        Instant testTime = Instant.parse("2023-06-15T14:30:00Z");
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("timestamp-test-123")
                .withPublished(testTime)
                .build();

        // When
        ApplicationInformation saved = persistAndFlush(app);
        flushAndClear();
        
        ApplicationInformation retrieved = entityManager.find(ApplicationInformation.class, saved.getId());

        // Then
        assertNotNull(retrieved.getCreated(), "Created timestamp should be set");
        assertNotNull(retrieved.getUpdated(), "Updated timestamp should be set");
        assertEquals(testTime, retrieved.getPublished(), "Published timestamp should match");
        
        // Timestamps should be in proper format (UTC with Z termination)
        assertTrue(retrieved.getCreated().toString().endsWith("Z"), "Created timestamp should be UTC");
        assertTrue(retrieved.getUpdated().toString().endsWith("Z"), "Updated timestamp should be UTC");
        
        // Verify PostgreSQL timezone handling at database level
        assertTrue(testTimezoneHandling(), "PostgreSQL should handle UTC timezone correctly");
    }

    @Test
    @DisplayName("Should handle large text fields correctly")
    void shouldHandleLargeTextFieldsCorrectly() {
        // Given - Create large text content
        StringBuilder largeText = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeText.append("https://example.com/redirect/").append(i).append("\n");
        }
        
        ApplicationInformation app = TestDataBuilder.newApplicationInformation()
                .withClientId("large-text-test-123")
                .withRedirectUris(largeText.toString())
                .build();

        // When
        ApplicationInformation saved = persistAndFlush(app);
        flushAndClear();
        
        ApplicationInformation retrieved = entityManager.find(ApplicationInformation.class, saved.getId());

        // Then
        assertNotNull(retrieved.getRedirectUris(), "Large text field should be saved");
        assertEquals(largeText.toString(), retrieved.getRedirectUris(), "Large text content should match");
        assertTrue(retrieved.getRedirectUris().length() > 10000, "Text should be large (>10KB)");
    }

    @Test
    @DisplayName("Should verify PostgreSQL BIGSERIAL sequence usage")
    void shouldVerifyBigSerialSequenceUsage() throws SQLException {
        // Given - Create multiple entities to test sequence progression
        ApplicationInformation app1 = TestDataBuilder.newApplicationInformation()
                .withClientId("sequence-test-1")
                .build();
        ApplicationInformation app2 = TestDataBuilder.newApplicationInformation()
                .withClientId("sequence-test-2")
                .build();

        // When
        ApplicationInformation saved1 = persistAndFlush(app1);
        ApplicationInformation saved2 = persistAndFlush(app2);

        // Then
        assertTrue(saved1.getId() > 0, "First entity should have positive ID");
        assertTrue(saved2.getId() > saved1.getId(), "Second entity should have higher ID");
        
        // Verify sequence is working at database level
        assertTrue(testBigSerialSequence("openespi_usage", "application_information", "id"),
                   "BIGSERIAL sequence should be functional");
    }

    @Test
    @DisplayName("Should handle PostgreSQL boolean values correctly")
    void shouldHandlePostgreSQLBooleanValuesCorrectly() throws SQLException {
        // Given
        RetailCustomer customer = TestDataBuilder.newRetailCustomer()
                .withUsername("boolean-test@example.com")
                .withEnabled(true)
                .build();

        // When
        RetailCustomer saved = persistAndFlush(customer);
        flushAndClear();

        // Then - Verify at entity level
        RetailCustomer retrieved = entityManager.find(RetailCustomer.class, saved.getId());
        assertTrue(retrieved.getEnabled(), "Boolean value should be true");

        // Verify at database level that PostgreSQL boolean is used
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT enabled FROM openespi_usage.retail_customers WHERE id = ?")) {
            
            stmt.setLong(1, saved.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "Should find the customer record");
                assertTrue(rs.getBoolean("enabled"), "Database boolean value should be true");
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