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
import org.springframework.test.context.jdbc.Sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for MySQL Flyway migrations using TestContainers.
 * 
 * This test class validates:
 * - Flyway migration scripts execute successfully
 * - Database schema structure matches expectations
 * - All required tables are created with correct structure
 * - Indexes and constraints are properly applied
 * - Usage and customer schemas are properly separated
 */
@DisplayName("MySQL Flyway Migration Integration Tests")
class MySQLFlywayMigrationIntegrationTest extends TestContainersIntegrationTestBase {

    @Test
    @DisplayName("Should successfully execute all Flyway migrations")
    void shouldExecuteAllFlywayMigrations() throws SQLException {
        // Test verifies that setUp() method ran successfully without exceptions
        // and that database connections are working
        
        try (Connection usageConn = createSchemaDataSource("openespi_usage").getConnection();
             Connection customerConn = createSchemaDataSource("openespi_customer").getConnection()) {
            
            assertNotNull(usageConn, "Usage schema connection should be available");
            assertNotNull(customerConn, "Customer schema connection should be available");
            
            assertTrue(usageConn.isValid(5), "Usage schema connection should be valid");
            assertTrue(customerConn.isValid(5), "Customer schema connection should be valid");
        }
    }

    @Test
    @DisplayName("Should create all required tables in usage schema")
    void shouldCreateUsageSchemaTables() throws SQLException {
        List<String> expectedTables = List.of(
            // V1 - Base tables
            "application_information",
            "subscriptions", 
            "authorizations",
            "retail_customers",
            "usage_points",
            "service_delivery_points",
            
            // V2 - Meter and reading tables
            "meter_readings",
            "interval_blocks",
            "interval_readings",
            "reading_types",
            
            // V3 - Quality and summary tables
            "reading_qualities",
            "electric_power_quality_summaries",
            "usage_summaries",
            "summary_measurements"
        );

        List<String> actualTables = getTablesInSchema("openespi_usage");
        
        for (String expectedTable : expectedTables) {
            assertTrue(actualTables.contains(expectedTable), 
                "Usage schema should contain table: " + expectedTable);
        }
    }

    @Test
    @DisplayName("Should create all required tables in customer schema")
    void shouldCreateCustomerSchemaTables() throws SQLException {
        List<String> expectedTables = List.of(
            // V4 - Base customer tables
            "customers",
            "customer_agreements",
            "customer_accounts",
            "pricing_structures",
            "tariff_profiles",
            
            // V5 - Device and asset tables
            "end_devices",
            "assets",
            "asset_properties",
            "device_functions",
            
            // V6 - Organization and support tables
            "organisations",
            "service_locations",
            "postal_addresses",
            "electronic_addresses",
            "phone_numbers",
            "names",
            "statements"
        );

        List<String> actualTables = getTablesInSchema("openespi_customer");
        
        for (String expectedTable : expectedTables) {
            assertTrue(actualTables.contains(expectedTable), 
                "Customer schema should contain table: " + expectedTable);
        }
    }

    @Test
    @DisplayName("Should create proper table structure for application_information")
    void shouldCreateApplicationInformationTableStructure() throws SQLException {
        List<String> expectedColumns = List.of(
            "id", "uuid", "uuid_msb", "uuid_lsb", "description", 
            "created", "updated", "published",
            "up_link_rel", "up_link_href", "self_link_rel", "self_link_href",
            "client_name", "client_id", "client_secret", "client_id_issued_at",
            "client_secret_expires_at", "registration_client_uri", 
            "registration_access_token", "redirect_uris", "software_id",
            "software_version", "token_endpoint_auth_method", "response_types",
            "grant_types", "application_type", "contacts", "logo_uri",
            "policy_uri", "tos_uri", "jwks_uri", "scope"
        );

        List<String> actualColumns = getColumnsInTable("openespi_usage", "application_information");
        
        for (String expectedColumn : expectedColumns) {
            assertTrue(actualColumns.contains(expectedColumn), 
                "application_information table should contain column: " + expectedColumn);
        }
    }

    @Test
    @DisplayName("Should create proper table structure for retail_customers")
    void shouldCreateRetailCustomersTableStructure() throws SQLException {
        List<String> expectedColumns = List.of(
            "id", "uuid", "uuid_msb", "uuid_lsb", "description",
            "created", "updated", "published", 
            "up_link_rel", "up_link_href", "self_link_rel", "self_link_href",
            "enabled", "first_name", "last_name", "password", "role", "username"
        );

        List<String> actualColumns = getColumnsInTable("openespi_usage", "retail_customers");
        
        for (String expectedColumn : expectedColumns) {
            assertTrue(actualColumns.contains(expectedColumn), 
                "retail_customers table should contain column: " + expectedColumn);
        }
    }

    @Test
    @DisplayName("Should create proper table structure for customers with PII fields")
    void shouldCreateCustomersTableStructure() throws SQLException {
        List<String> expectedColumns = List.of(
            "id", "uuid", "uuid_msb", "uuid_lsb", "description",
            "created", "updated", "published",
            "up_link_rel", "up_link_href", "self_link_rel", "self_link_href",
            "kind", "special_need", "vip", "puc_number", "status", "priority",
            "locale", "customer_name", "retail_customer_href"
        );

        List<String> actualColumns = getColumnsInTable("openespi_customer", "customers");
        
        for (String expectedColumn : expectedColumns) {
            assertTrue(actualColumns.contains(expectedColumn), 
                "customers table should contain column: " + expectedColumn);
        }
    }

    @Test
    @DisplayName("Should create proper indexes for performance")
    void shouldCreateProperIndexes() throws SQLException {
        // Test critical indexes exist for performance
        assertTrue(indexExists("openespi_usage", "application_information", "idx_app_info_uuid"),
            "application_information should have UUID index");
        assertTrue(indexExists("openespi_usage", "application_information", "idx_app_info_client_id"),
            "application_information should have client_id index");
        
        assertTrue(indexExists("openespi_usage", "retail_customers", "idx_retail_customer_uuid"),
            "retail_customers should have UUID index");
        assertTrue(indexExists("openespi_usage", "retail_customers", "idx_retail_customer_username"),
            "retail_customers should have username index");
        
        assertTrue(indexExists("openespi_customer", "customers", "idx_customer_uuid"),
            "customers should have UUID index");
        assertTrue(indexExists("openespi_customer", "customers", "idx_customer_puc_number"),
            "customers should have PUC number index");
    }

    @Test
    @DisplayName("Should enforce unique constraints")
    void shouldEnforceUniqueConstraints() throws SQLException {
        // Test that unique constraints are properly applied
        assertTrue(hasUniqueConstraint("openespi_usage", "application_information", "uuid"),
            "application_information.uuid should have unique constraint");
        assertTrue(hasUniqueConstraint("openespi_usage", "application_information", "client_id"),
            "application_information.client_id should have unique constraint");
        
        assertTrue(hasUniqueConstraint("openespi_usage", "retail_customers", "uuid"),
            "retail_customers.uuid should have unique constraint");
        assertTrue(hasUniqueConstraint("openespi_usage", "retail_customers", "username"),
            "retail_customers.username should have unique constraint");
        
        assertTrue(hasUniqueConstraint("openespi_customer", "customers", "uuid"),
            "customers.uuid should have unique constraint");
    }

    @Test
    @DisplayName("Should use proper column types for ESPI compliance")
    void shouldUseProperColumnTypes() throws SQLException {
        // Test critical column types for ESPI compliance
        assertEquals("VARCHAR", getColumnType("openespi_usage", "application_information", "uuid"),
            "UUID column should be VARCHAR(36)");
        assertEquals("DATETIME", getColumnType("openespi_usage", "application_information", "created"),
            "Created timestamp should be DATETIME(6)");
        assertEquals("TEXT", getColumnType("openespi_usage", "application_information", "redirect_uris"),
            "Redirect URIs should be TEXT for multiple URLs");
        
        assertEquals("VARCHAR", getColumnType("openespi_customer", "customers", "uuid"),
            "Customer UUID should be VARCHAR(36)");
        assertEquals("TINYINT", getColumnType("openespi_customer", "customers", "vip"),
            "VIP flag should be BOOLEAN (TINYINT in MySQL)");
    }

    @Test
    @DisplayName("Should verify schema separation between usage and customer data")
    void shouldVerifySchemaSeparation() throws SQLException {
        // Verify that usage schema doesn't contain customer tables
        List<String> usageTables = getTablesInSchema("openespi_usage");
        assertFalse(usageTables.contains("customers"), 
            "Usage schema should not contain customers table");
        assertFalse(usageTables.contains("organisations"), 
            "Usage schema should not contain organisations table");
        
        // Verify that customer schema doesn't contain usage tables
        List<String> customerTables = getTablesInSchema("openespi_customer");
        assertFalse(customerTables.contains("meter_readings"), 
            "Customer schema should not contain meter_readings table");
        assertFalse(customerTables.contains("interval_readings"), 
            "Customer schema should not contain interval_readings table");
    }

    // Helper methods for database introspection

    private List<String> getTablesInSchema(String schema) throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(schema, null, null, new String[]{"TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }
        }
        return tables;
    }

    private List<String> getColumnsInTable(String schema, String table) throws SQLException {
        List<String> columns = new ArrayList<>();
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getColumns(schema, null, table, null)) {
                while (rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME"));
                }
            }
        }
        return columns;
    }

    private boolean indexExists(String schema, String table, String indexName) throws SQLException {
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getIndexInfo(schema, null, table, false, false)) {
                while (rs.next()) {
                    if (indexName.equals(rs.getString("INDEX_NAME"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasUniqueConstraint(String schema, String table, String column) throws SQLException {
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getIndexInfo(schema, null, table, true, false)) {
                while (rs.next()) {
                    if (column.equals(rs.getString("COLUMN_NAME"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String getColumnType(String schema, String table, String column) throws SQLException {
        try (Connection conn = createSchemaDataSource(schema).getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getColumns(schema, null, table, column)) {
                if (rs.next()) {
                    return rs.getString("TYPE_NAME");
                }
            }
        }
        return null;
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