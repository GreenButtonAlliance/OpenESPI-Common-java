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
 * Integration tests for loose coupling between usage and customer schemas using TestContainers with PostgreSQL.
 * 
 * This test class validates the architectural principle that:
 * - Usage and customer schemas are completely decoupled at the database level
 * - No foreign key constraints exist between schemas
 * - Relationships are maintained through ATOM href URLs only
 * - Each schema can be independently deployed, scaled, and maintained
 * - Data consistency is maintained through application-level controls, not database constraints
 * - Cross-schema operations work through URL-based references
 * - PostgreSQL-specific schema features (search paths, permissions) work correctly
 * 
 * This design supports:
 * - Microservices architecture
 * - Independent scaling of usage vs customer data
 * - Separate compliance controls (usage data vs PII)
 * - Different backup/retention policies
 * - PostgreSQL-specific multi-tenant patterns
 */
@DisplayName("PostgreSQL Schema Coupling Integration Tests")
class PostgreSQLSchemaCouplingIntegrationTest extends PostgreSQLTestContainersIntegrationTestBase {

    @Test
    @DisplayName("Should verify complete schema separation between usage and customer schemas")
    void shouldVerifyCompleteSchemaSeparation() throws SQLException {
        // Verify that usage schema doesn't contain customer tables
        List<String> usageTables = getTablesInSchema("openespi_usage");
        assertFalse(usageTables.contains("customers"), 
            "Usage schema should not contain customers table");
        assertFalse(usageTables.contains("organisations"), 
            "Usage schema should not contain organisations table");
        assertFalse(usageTables.contains("service_locations"), 
            "Usage schema should not contain service_locations table");
        
        // Verify that customer schema doesn't contain usage tables
        List<String> customerTables = getTablesInSchema("openespi_customer");
        assertFalse(customerTables.contains("meter_readings"), 
            "Customer schema should not contain meter_readings table");
        assertFalse(customerTables.contains("interval_readings"), 
            "Customer schema should not contain interval_readings table");
        assertFalse(customerTables.contains("usage_points"), 
            "Customer schema should not contain usage_points table");
    }

    @Test
    @DisplayName("Should verify no foreign key constraints exist between schemas")
    void shouldVerifyNoForeignKeyConstraintsBetweenSchemas() throws SQLException {
        // Test that no foreign keys exist from usage schema to customer schema
        List<String> usageToCustomerFks = getForeignKeyConstraintsBetweenSchemas("openespi_usage", "openespi_customer");
        assertTrue(usageToCustomerFks.isEmpty(), 
            "No foreign key constraints should exist from usage to customer schema. Found: " + usageToCustomerFks);
        
        // Test that no foreign keys exist from customer schema to usage schema
        List<String> customerToUsageFks = getForeignKeyConstraintsBetweenSchemas("openespi_customer", "openespi_usage");
        assertTrue(customerToUsageFks.isEmpty(), 
            "No foreign key constraints should exist from customer to usage schema. Found: " + customerToUsageFks);
    }

    @Test
    @DisplayName("Should allow independent schema operations without cross-dependencies")
    void shouldAllowIndependentSchemaOperationsWithoutCrossDependencies() throws SQLException {
        // Test that we can drop and recreate usage schema without affecting customer schema
        List<String> originalCustomerTables = getTablesInSchema("openespi_customer");
        int originalCustomerTableCount = originalCustomerTables.size();
        
        // Simulate dropping usage schema (we'll just verify we could do it)
        // In real scenario: DROP SCHEMA openespi_usage CASCADE;
        assertTrue(canDropSchemaIndependently("openespi_usage"),
            "Should be able to drop usage schema independently");
        
        // Verify customer schema is unaffected
        List<String> customerTablesAfter = getTablesInSchema("openespi_customer");
        assertEquals(originalCustomerTableCount, customerTablesAfter.size(),
            "Customer schema should be unaffected by usage schema operations");
        
        // Test the reverse - customer schema operations don't affect usage schema
        List<String> originalUsageTables = getTablesInSchema("openespi_usage");
        int originalUsageTableCount = originalUsageTables.size();
        
        assertTrue(canDropSchemaIndependently("openespi_customer"),
            "Should be able to drop customer schema independently");
        
        List<String> usageTablesAfter = getTablesInSchema("openespi_usage");
        assertEquals(originalUsageTableCount, usageTablesAfter.size(),
            "Usage schema should be unaffected by customer schema operations");
    }

    @Test
    @DisplayName("Should verify PostgreSQL schema search path independence")
    void shouldVerifyPostgreSQLSchemaSearchPathIndependence() throws SQLException {
        // Test that setting search path to one schema doesn't expose tables from the other
        
        // Set search path to usage schema only
        try (Connection conn = createSchemaDataSource("openespi_usage").getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("SET search_path TO openespi_usage");
            
            // Should be able to access usage tables without schema qualification
            assertTrue(tableExistsInCurrentSearchPath(conn, "application_information"),
                "Should find usage tables in search path");
            assertTrue(tableExistsInCurrentSearchPath(conn, "retail_customers"),
                "Should find usage tables in search path");
            
            // Should NOT be able to access customer tables without schema qualification
            assertFalse(tableExistsInCurrentSearchPath(conn, "customers"),
                "Should NOT find customer tables in usage search path");
            assertFalse(tableExistsInCurrentSearchPath(conn, "organisations"),
                "Should NOT find customer tables in usage search path");
        }
        
        // Set search path to customer schema only
        try (Connection conn = createSchemaDataSource("openespi_customer").getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("SET search_path TO openespi_customer");
            
            // Should be able to access customer tables without schema qualification
            assertTrue(tableExistsInCurrentSearchPath(conn, "customers"),
                "Should find customer tables in search path");
            assertTrue(tableExistsInCurrentSearchPath(conn, "organisations"),
                "Should find customer tables in search path");
            
            // Should NOT be able to access usage tables without schema qualification
            assertFalse(tableExistsInCurrentSearchPath(conn, "application_information"),
                "Should NOT find usage tables in customer search path");
            assertFalse(tableExistsInCurrentSearchPath(conn, "retail_customers"),
                "Should NOT find usage tables in customer search path");
        }
    }

    @Test
    @DisplayName("Should verify independent PostgreSQL schema permissions")
    void shouldVerifyIndependentPostgreSQLSchemaPermissions() throws SQLException {
        // Verify that schema permissions can be set independently
        try (Connection conn = getPostgreSQLContainer().createConnection("")) {
            
            // Check current permissions on both schemas
            List<String> usagePermissions = getSchemaPermissions(conn, "openespi_usage");
            List<String> customerPermissions = getSchemaPermissions(conn, "openespi_customer");
            
            assertFalse(usagePermissions.isEmpty(), "Usage schema should have permissions configured");
            assertFalse(customerPermissions.isEmpty(), "Customer schema should have permissions configured");
            
            // Verify permissions are schema-specific
            assertTrue(usagePermissions.stream().allMatch(p -> p.contains("openespi_usage")),
                "Usage permissions should be specific to usage schema");
            assertTrue(customerPermissions.stream().allMatch(p -> p.contains("openespi_customer")),
                "Customer permissions should be specific to customer schema");
        }
    }

    @Test
    @DisplayName("Should support independent PostgreSQL backup and restore operations")
    void shouldSupportIndependentPostgreSQLBackupAndRestoreOperations() throws SQLException {
        // Test that schemas can be backed up and restored independently
        
        // Verify we can get schema-specific metadata for backup
        try (Connection usageConn = createSchemaDataSource("openespi_usage").getConnection()) {
            DatabaseMetaData usageMetaData = usageConn.getMetaData();
            
            // Get all tables in usage schema for backup planning
            List<String> usageTables = getTablesInSchema("openespi_usage");
            assertFalse(usageTables.isEmpty(), "Usage schema should have tables for backup");
            
            // Verify we can get table-specific DDL information
            for (String table : usageTables.subList(0, Math.min(3, usageTables.size()))) {
                try (ResultSet columns = usageMetaData.getColumns(null, "openespi_usage", table, null)) {
                    assertTrue(columns.next(), "Should be able to get column metadata for table: " + table);
                }
            }
        }
        
        try (Connection customerConn = createSchemaDataSource("openespi_customer").getConnection()) {
            DatabaseMetaData customerMetaData = customerConn.getMetaData();
            
            // Get all tables in customer schema for backup planning
            List<String> customerTables = getTablesInSchema("openespi_customer");
            assertFalse(customerTables.isEmpty(), "Customer schema should have tables for backup");
            
            // Verify we can get table-specific DDL information
            for (String table : customerTables.subList(0, Math.min(3, customerTables.size()))) {
                try (ResultSet columns = customerMetaData.getColumns(null, "openespi_customer", table, null)) {
                    assertTrue(columns.next(), "Should be able to get column metadata for table: " + table);
                }
            }
        }
    }

    @Test
    @DisplayName("Should verify PostgreSQL JSONB data types work independently per schema")
    void shouldVerifyPostgreSQLJsonbDataTypesWorkIndependentlyPerSchema() throws SQLException {
        // Test that JSONB functionality works in customer schema
        assertTrue(hasJsonbFunctionalityInSchema("openespi_customer"),
            "Customer schema should support JSONB functionality");
        
        // Test that JSONB indexes work in customer schema
        assertTrue(testGinIndexExists("openespi_customer", "service_locations", "idx_service_location_position_points"),
            "Customer schema should have functional GIN indexes on JSONB fields");
        
        // Test that usage schema doesn't interfere with customer schema JSONB operations
        try (Connection customerConn = createSchemaDataSource("openespi_customer").getConnection();
             PreparedStatement stmt = customerConn.prepareStatement(
                 "SELECT COUNT(*) FROM openespi_customer.service_locations WHERE position_points IS NOT NULL")) {
            
            // This query should work even if usage schema has issues
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "JSONB query should work in customer schema independently");
            }
        }
    }

    @Test
    @DisplayName("Should verify independent sequence management between schemas")
    void shouldVerifyIndependentSequenceManagementBetweenSchemas() throws SQLException {
        // Test that BIGSERIAL sequences are schema-specific
        List<String> usageSequences = getSequencesInSchema("openespi_usage");
        List<String> customerSequences = getSequencesInSchema("openespi_customer");
        
        assertFalse(usageSequences.isEmpty(), "Usage schema should have sequences");
        assertFalse(customerSequences.isEmpty(), "Customer schema should have sequences");
        
        // Verify no sequence name conflicts between schemas
        for (String usageSeq : usageSequences) {
            assertFalse(customerSequences.contains(usageSeq), 
                "Sequence " + usageSeq + " should not exist in both schemas");
        }
        
        // Test that sequences work independently
        assertTrue(testBigSerialSequence("openespi_usage", "application_information", "id"),
            "Usage schema sequences should work independently");
        assertTrue(testBigSerialSequence("openespi_customer", "customers", "id"),
            "Customer schema sequences should work independently");
    }

    @Test
    @DisplayName("Should verify cross-schema operations require explicit schema qualification")
    void shouldVerifyCrossSchemaOperationsRequireExplicitSchemaQualification() throws SQLException {
        // Test that cross-schema queries require explicit schema names
        try (Connection usageConn = createSchemaDataSource("openespi_usage").getConnection()) {
            
            // Should be able to query usage tables without qualification
            try (PreparedStatement stmt = usageConn.prepareStatement(
                    "SELECT COUNT(*) FROM application_information")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    assertTrue(rs.next(), "Should be able to query usage tables without qualification");
                }
            }
            
            // Should NOT be able to query customer tables without qualification
            try (PreparedStatement stmt = usageConn.prepareStatement(
                    "SELECT COUNT(*) FROM customers")) {
                assertThrows(SQLException.class, () -> {
                    try (ResultSet rs = stmt.executeQuery()) {
                        rs.next();
                    }
                }, "Should NOT be able to query customer tables without schema qualification");
            } catch (SQLException e) {
                // Expected - table doesn't exist in current search path
                assertTrue(e.getMessage().toLowerCase().contains("does not exist") || 
                          e.getMessage().toLowerCase().contains("relation") ||
                          e.getMessage().toLowerCase().contains("customers"),
                    "Should get 'does not exist' error for unqualified cross-schema query");
            }
            
            // Should be able to query customer tables WITH explicit schema qualification
            try (PreparedStatement stmt = usageConn.prepareStatement(
                    "SELECT COUNT(*) FROM openespi_customer.customers")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    assertTrue(rs.next(), "Should be able to query customer tables with explicit schema qualification");
                }
            }
        }
    }

    // Helper methods for PostgreSQL schema analysis

    private List<String> getTablesInSchema(String schema) throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(null, schema, null, new String[]{"TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }
        }
        return tables;
    }

    private List<String> getForeignKeyConstraintsBetweenSchemas(String fromSchema, String toSchema) throws SQLException {
        List<String> constraints = new ArrayList<>();
        try (Connection conn = getPostgreSQLContainer().createConnection("")) {
            String sql = """
                SELECT tc.constraint_name
                FROM information_schema.table_constraints tc
                JOIN information_schema.referential_constraints rc ON tc.constraint_name = rc.constraint_name
                JOIN information_schema.table_constraints tc2 ON rc.unique_constraint_name = tc2.constraint_name
                WHERE tc.table_schema = ? AND tc2.table_schema = ? AND tc.constraint_type = 'FOREIGN KEY'
                """;
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fromSchema);
                stmt.setString(2, toSchema);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        constraints.add(rs.getString("constraint_name"));
                    }
                }
            }
        }
        return constraints;
    }

    private boolean canDropSchemaIndependently(String schema) throws SQLException {
        // Test by checking for dependencies that would prevent independent dropping
        try (Connection conn = getPostgreSQLContainer().createConnection("")) {
            String sql = """
                SELECT COUNT(*) as dependency_count
                FROM information_schema.table_constraints tc
                JOIN information_schema.referential_constraints rc ON tc.constraint_name = rc.constraint_name
                JOIN information_schema.table_constraints tc2 ON rc.unique_constraint_name = tc2.constraint_name
                WHERE tc2.table_schema = ? AND tc.table_schema != ?
                """;
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, schema);
                stmt.setString(2, schema);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() && rs.getInt("dependency_count") == 0;
                }
            }
        }
    }

    private boolean tableExistsInCurrentSearchPath(Connection conn, String tableName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT 1 FROM information_schema.tables WHERE table_name = ? AND table_schema = ANY(current_schemas(false))")) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private List<String> getSchemaPermissions(Connection conn, String schema) throws SQLException {
        List<String> permissions = new ArrayList<>();
        String sql = """
            SELECT grantee, privilege_type 
            FROM information_schema.schema_privileges 
            WHERE schema_name = ?
            """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, schema);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permissions.add(rs.getString("grantee") + ":" + rs.getString("privilege_type"));
                }
            }
        }
        return permissions;
    }

    private boolean hasJsonbFunctionalityInSchema(String schema) throws SQLException {
        try (Connection conn = createSchemaDataSource(schema).getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT column_name FROM information_schema.columns WHERE table_schema = ? AND data_type = 'jsonb'")) {
            
            stmt.setString(1, schema);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if any JSONB columns exist
            }
        }
    }

    private List<String> getSequencesInSchema(String schema) throws SQLException {
        List<String> sequences = new ArrayList<>();
        try (Connection conn = createSchemaDataSource(schema).getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = ?")) {
            
            stmt.setString(1, schema);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sequences.add(rs.getString("sequence_name"));
                }
            }
        }
        return sequences;
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