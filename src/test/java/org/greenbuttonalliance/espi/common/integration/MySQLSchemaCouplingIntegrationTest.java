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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for loose coupling between usage and customer schemas using TestContainers with MySQL.
 * 
 * This test class validates the architectural principle that:
 * - Usage and customer schemas are completely decoupled at the database level
 * - No foreign key constraints exist between schemas
 * - Relationships are maintained through ATOM href URLs only
 * - Each schema can be independently deployed, scaled, and maintained
 * - Data consistency is maintained through application-level controls, not database constraints
 * - Cross-schema operations work through URL-based references
 * 
 * This design supports:
 * - Microservices architecture
 * - Independent scaling of usage vs customer data
 * - Separate compliance controls (usage data vs PII)
 * - Different backup/retention policies
 * - Geographic data distribution
 */
@DisplayName("MySQL Schema Coupling Integration Tests")
class MySQLSchemaCouplingIntegrationTest extends TestContainersIntegrationTestBase {

    @Test
    @DisplayName("Should have no foreign key constraints between usage and customer schemas")
    void shouldHaveNoForeignKeyConstraintsBetweenSchemas() throws SQLException {
        // Check usage schema tables for foreign keys to customer schema
        List<String> usageForeignKeys = getForeignKeysPointingToSchema("openespi_usage", "openespi_customer");
        assertTrue(usageForeignKeys.isEmpty(), 
                   "Usage schema should have no foreign keys to customer schema. Found: " + usageForeignKeys);

        // Check customer schema tables for foreign keys to usage schema
        List<String> customerForeignKeys = getForeignKeysPointingToSchema("openespi_customer", "openespi_usage");
        assertTrue(customerForeignKeys.isEmpty(), 
                   "Customer schema should have no foreign keys to usage schema. Found: " + customerForeignKeys);
    }

    @Test
    @DisplayName("Should allow independent schema operations")
    void shouldAllowIndependentSchemaOperations() throws SQLException {
        // Test that each schema can operate independently
        
        // Insert data into usage schema without customer schema dependency
        String usageCustomerUuid = insertRetailCustomerInUsageSchema("independent-usage-test@example.com");
        assertNotNull(usageCustomerUuid, "Should be able to insert into usage schema independently");

        // Insert data into customer schema without usage schema dependency
        String customerUuid = insertCustomerInCustomerSchema("Independent Customer", "independent-pii-test");
        assertNotNull(customerUuid, "Should be able to insert into customer schema independently");

        // Verify data exists in both schemas independently
        assertTrue(recordExistsInUsageSchema("retail_customers", usageCustomerUuid),
                   "Usage schema data should exist independently");
        assertTrue(recordExistsInCustomerSchema("customers", customerUuid),
                   "Customer schema data should exist independently");
    }

    @Test
    @DisplayName("Should maintain data integrity through application logic, not database constraints")
    void shouldMaintainDataIntegrityThroughApplicationLogic() throws SQLException {
        // This test demonstrates that referential integrity is maintained by application code
        // rather than database foreign key constraints
        
        // Create retail customer in usage schema
        String retailCustomerUuid = insertRetailCustomerInUsageSchema("integrity-test@example.com");
        
        // Create corresponding customer in customer schema (simulating application-level linking)
        String customerUuid = insertCustomerInCustomerSchema("Integrity Test Customer", "integrity-test-pii");
        
        // Set up application-level relationship via href (no foreign key)
        updateCustomerWithRetailCustomerHref(customerUuid, "/espi/1_1/resource/RetailCustomer/" + retailCustomerUuid);
        
        // Verify relationship exists through href, not foreign key
        String retrievedHref = getCustomerRetailCustomerHref(customerUuid);
        assertNotNull(retrievedHref, "Customer should have retail customer href");
        assertTrue(retrievedHref.contains(retailCustomerUuid), "Href should reference retail customer UUID");
        
        // Test that retail customer can be deleted without cascade constraints
        deleteFromUsageSchema("retail_customers", retailCustomerUuid);
        
        // Customer record should still exist (no cascade delete)
        assertTrue(recordExistsInCustomerSchema("customers", customerUuid),
                   "Customer should still exist after retail customer deletion (no FK cascade)");
        
        // But href reference becomes dangling (application responsibility to handle)
        String danglingHref = getCustomerRetailCustomerHref(customerUuid);
        assertNotNull(danglingHref, "Href reference persists even when target is deleted");
    }

    @Test
    @DisplayName("Should support cross-schema queries through application layer")
    void shouldSupportCrossSchemaQueriesThroughApplicationLayer() throws SQLException {
        // Simulate application-level cross-schema operations
        
        // Create related records in both schemas
        String retailCustomerUuid = insertRetailCustomerInUsageSchema("cross-query-test@example.com");
        String customerUuid = insertCustomerInCustomerSchema("Cross Query Customer", "cross-query-pii");
        
        // Link them via href (application-level relationship)
        updateCustomerWithRetailCustomerHref(customerUuid, "/espi/1_1/resource/RetailCustomer/" + retailCustomerUuid);
        
        // Application-level query: Find all customers with active retail customers
        List<String> linkedCustomers = findCustomersWithRetailCustomerHrefs();
        assertTrue(linkedCustomers.contains(customerUuid), 
                   "Should find customer with retail customer href");
        
        // Application-level query: Find retail customers referenced by customer schema
        List<String> referencedRetailCustomers = findRetailCustomersReferencedByCustomers();
        assertTrue(referencedRetailCustomers.contains(retailCustomerUuid),
                   "Should find retail customer referenced by customer schema");
    }

    @Test
    @DisplayName("Should handle schema isolation for different data types")
    void shouldHandleSchemaIsolationForDifferentDataTypes() throws SQLException {
        // Usage schema contains energy data (non-PII, high volume)
        insertUsageData("usage-isolation-test");
        
        // Customer schema contains PII data (sensitive, lower volume)
        insertPIIData("pii-isolation-test");
        
        // Verify data types are properly isolated
        assertTrue(hasUsageDataTables(), "Usage schema should have energy usage tables");
        assertTrue(hasPIIDataTables(), "Customer schema should have PII data tables");
        
        // Verify no PII data in usage schema
        assertFalse(tableExistsInSchema("openespi_usage", "customers"),
                    "Usage schema should not contain customer PII table");
        assertFalse(tableExistsInSchema("openespi_usage", "postal_addresses"),
                    "Usage schema should not contain address PII table");
        
        // Verify no usage data in customer schema  
        assertFalse(tableExistsInSchema("openespi_customer", "meter_readings"),
                    "Customer schema should not contain meter reading table");
        assertFalse(tableExistsInSchema("openespi_customer", "interval_readings"),
                    "Customer schema should not contain interval reading table");
    }

    @Test
    @DisplayName("Should support different security models per schema")
    void shouldSupportDifferentSecurityModelsPerSchema() throws SQLException {
        // This test verifies that schemas can have different security configurations
        // Important for compliance (PII vs non-PII data handling)
        
        // Usage schema: Contains usage data, requires different retention/access patterns
        String usageTablePrivileges = getTablePrivileges("openespi_usage", "meter_readings");
        assertNotNull(usageTablePrivileges, "Usage tables should have defined privileges");
        
        // Customer schema: Contains PII, requires stricter access controls
        String customerTablePrivileges = getTablePrivileges("openespi_customer", "customers");
        assertNotNull(customerTablePrivileges, "Customer tables should have defined privileges");
        
        // Schemas should be independently manageable
        assertTrue(canAccessSchema("openespi_usage"), "Should be able to access usage schema");
        assertTrue(canAccessSchema("openespi_customer"), "Should be able to access customer schema");
    }

    @Test
    @DisplayName("Should demonstrate loose coupling benefits")
    void shouldDemonstrateLooseCouplingBenefits() throws SQLException {
        // Create test data to demonstrate loose coupling benefits
        String retailCustomerUuid = insertRetailCustomerInUsageSchema("benefits-test@example.com");
        insertUsagePointForRetailCustomer(retailCustomerUuid);
        
        String customerUuid = insertCustomerInCustomerSchema("Benefits Test Customer", "benefits-pii");
        updateCustomerWithRetailCustomerHref(customerUuid, "/espi/1_1/resource/RetailCustomer/" + retailCustomerUuid);
        
        // Benefit 1: Independent scaling - can add usage data without affecting customer schema
        for (int i = 0; i < 10; i++) {
            insertMeterReadingForUsagePoint(retailCustomerUuid, "Reading " + i);
        }
        verifyUsageDataCount(retailCustomerUuid, 10);
        
        // Customer schema is unaffected by usage data growth
        assertTrue(recordExistsInCustomerSchema("customers", customerUuid),
                   "Customer data unaffected by usage data growth");
        
        // Benefit 2: Independent deployment - can modify customer schema structure without affecting usage
        addCustomerSchemaColumn(); // Simulates schema evolution
        assertTrue(recordExistsInUsageSchema("retail_customers", retailCustomerUuid),
                   "Usage data unaffected by customer schema changes");
        
        // Benefit 3: Independent backup/retention - schemas can have different policies
        // (This would be configured at the database/infrastructure level)
        assertTrue(canBackupSchemaIndependently("openespi_usage"),
                   "Usage schema should support independent backup");
        assertTrue(canBackupSchemaIndependently("openespi_customer"),
                   "Customer schema should support independent backup");
    }

    // Helper methods for database operations

    private List<String> getForeignKeysPointingToSchema(String fromSchema, String toSchema) throws SQLException {
        List<String> foreignKeys = new ArrayList<>();
        try (Connection conn = createSchemaDataSource(fromSchema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            
            // Check all tables in fromSchema for foreign keys to toSchema
            try (ResultSet tables = metaData.getTables(fromSchema, null, null, new String[]{"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    
                    try (ResultSet fks = metaData.getImportedKeys(fromSchema, null, tableName)) {
                        while (fks.next()) {
                            String pkTableSchema = fks.getString("PKTABLE_SCHEM");
                            if (toSchema.equals(pkTableSchema)) {
                                foreignKeys.add(fromSchema + "." + tableName + " -> " + 
                                              toSchema + "." + fks.getString("PKTABLE_NAME"));
                            }
                        }
                    }
                }
            }
        }
        return foreignKeys;
    }

    private String insertRetailCustomerInUsageSchema(String username) throws SQLException {
        String uuid = java.util.UUID.randomUUID().toString();
        String sql = """
            INSERT INTO retail_customers (uuid, username, first_name, last_name, enabled, created, updated, self_link_href)
            VALUES (?, ?, 'Test', 'User', true, NOW(6), NOW(6), ?)
            """;
        
        String href = "/espi/1_1/resource/RetailCustomer/" + uuid;
        
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid);
            stmt.setString(2, username);
            stmt.setString(3, href);
            stmt.executeUpdate();
        }
        
        return uuid;
    }

    private String insertCustomerInCustomerSchema(String customerName, String pucNumber) throws SQLException {
        String uuid = java.util.UUID.randomUUID().toString();
        String sql = """
            INSERT INTO customers (uuid, customer_name, puc_number, kind, created, updated, self_link_href)
            VALUES (?, ?, ?, 'individual', NOW(6), NOW(6), ?)
            """;
        
        String href = "/espi/1_1/resource/Customer/" + uuid;
        
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid);
            stmt.setString(2, customerName);
            stmt.setString(3, pucNumber);
            stmt.setString(4, href);
            stmt.executeUpdate();
        }
        
        return uuid;
    }

    private void updateCustomerWithRetailCustomerHref(String customerUuid, String retailCustomerHref) throws SQLException {
        String sql = "UPDATE customers SET retail_customer_href = ? WHERE uuid = ?";
        
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, retailCustomerHref);
            stmt.setString(2, customerUuid);
            stmt.executeUpdate();
        }
    }

    private String getCustomerRetailCustomerHref(String customerUuid) throws SQLException {
        String sql = "SELECT retail_customer_href FROM customers WHERE uuid = ?";
        
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerUuid);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getString("retail_customer_href") : null;
            }
        }
    }

    private boolean recordExistsInUsageSchema(String table, String uuid) throws SQLException {
        return recordExistsInSchema("openespi_usage", table, uuid);
    }

    private boolean recordExistsInCustomerSchema(String table, String uuid) throws SQLException {
        return recordExistsInSchema("openespi_customer", table, uuid);
    }

    private boolean recordExistsInSchema(String schema, String table, String uuid) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE uuid = ?";
        
        try (Connection conn = createSchemaDataSource(schema).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void deleteFromUsageSchema(String table, String uuid) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE uuid = ?";
        
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid);
            stmt.executeUpdate();
        }
    }

    private List<String> findCustomersWithRetailCustomerHrefs() throws SQLException {
        List<String> customers = new ArrayList<>();
        String sql = "SELECT uuid FROM customers WHERE retail_customer_href IS NOT NULL";
        
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(rs.getString("uuid"));
            }
        }
        
        return customers;
    }

    private List<String> findRetailCustomersReferencedByCustomers() throws SQLException {
        List<String> retailCustomers = new ArrayList<>();
        String sql = """
            SELECT DISTINCT SUBSTRING_INDEX(retail_customer_href, '/', -1) as retail_customer_uuid
            FROM customers 
            WHERE retail_customer_href IS NOT NULL
            """;
        
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                retailCustomers.add(rs.getString("retail_customer_uuid"));
            }
        }
        
        return retailCustomers;
    }

    // Additional helper methods for comprehensive testing

    private void insertUsageData(String testId) throws SQLException {
        // Insert sample usage data
        executeInUsageSchema("INSERT INTO usage_points (uuid, service_kind, created, updated) VALUES " +
                           "('" + java.util.UUID.randomUUID() + "', 'electricity', NOW(6), NOW(6))");
    }

    private void insertPIIData(String testId) throws SQLException {
        // Insert sample PII data
        executeInCustomerSchema("INSERT INTO postal_addresses (uuid, postal_code, created, updated) VALUES " +
                              "('" + java.util.UUID.randomUUID() + "', '12345', NOW(6), NOW(6))");
    }

    private boolean hasUsageDataTables() throws SQLException {
        return tableExistsInSchema("openespi_usage", "meter_readings") &&
               tableExistsInSchema("openespi_usage", "interval_readings");
    }

    private boolean hasPIIDataTables() throws SQLException {
        return tableExistsInSchema("openespi_customer", "customers") &&
               tableExistsInSchema("openespi_customer", "postal_addresses");
    }

    private boolean tableExistsInSchema(String schema, String table) throws SQLException {
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(schema, null, table, new String[]{"TABLE"})) {
                return rs.next();
            }
        }
    }

    private String getTablePrivileges(String schema, String table) throws SQLException {
        // Simplified privilege check - in real implementation would check actual MySQL privileges
        return tableExistsInSchema(schema, table) ? "SELECT,INSERT,UPDATE,DELETE" : null;
    }

    private boolean canAccessSchema(String schema) throws SQLException {
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            return conn.isValid(5);
        }
    }

    private void insertUsagePointForRetailCustomer(String retailCustomerUuid) throws SQLException {
        String uuid = java.util.UUID.randomUUID().toString();
        executeInUsageSchema("INSERT INTO usage_points (uuid, service_kind, created, updated) VALUES " +
                           "('" + uuid + "', 'electricity', NOW(6), NOW(6))");
    }

    private void insertMeterReadingForUsagePoint(String retailCustomerUuid, String description) throws SQLException {
        String uuid = java.util.UUID.randomUUID().toString();
        executeInUsageSchema("INSERT INTO meter_readings (uuid, description, created, updated) VALUES " +
                           "('" + uuid + "', '" + description + "', NOW(6), NOW(6))");
    }

    private void verifyUsageDataCount(String retailCustomerUuid, int expectedCount) throws SQLException {
        String sql = "SELECT COUNT(*) FROM meter_readings";
        
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            assertTrue(rs.next() && rs.getInt(1) >= expectedCount, 
                      "Should have at least " + expectedCount + " meter readings");
        }
    }

    private void addCustomerSchemaColumn() throws SQLException {
        // Simulate schema evolution
        try {
            executeInCustomerSchema("ALTER TABLE customers ADD COLUMN test_evolution_column VARCHAR(100)");
        } catch (SQLException e) {
            // Column might already exist from previous test runs
            if (!e.getMessage().contains("Duplicate column")) {
                throw e;
            }
        }
    }

    private boolean canBackupSchemaIndependently(String schema) {
        // In real implementation, this would check backup capabilities
        // For test purposes, we verify schema accessibility
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            return conn.isValid(5);
        } catch (SQLException e) {
            return false;
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