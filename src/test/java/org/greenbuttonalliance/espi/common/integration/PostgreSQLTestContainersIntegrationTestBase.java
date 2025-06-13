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
import javax.sql.DataSource;
import jakarta.validation.Validator;
import org.flywaydb.core.Flyway;
import org.greenbuttonalliance.espi.common.TestApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base class for TestContainers integration tests with PostgreSQL.
 * 
 * This class provides:
 * - PostgreSQL TestContainer setup with proper schema initialization
 * - Flyway migration execution for both usage and customer schemas
 * - Spring Boot test configuration with real PostgreSQL database
 * - Common utilities for database operations and validation
 * - PostgreSQL-specific features testing (JSONB, BIGSERIAL, TIMEZONE)
 * 
 * Tests extending this class will have access to:
 * - Full PostgreSQL database with proper schema structure
 * - Applied Flyway migrations (V1-V6) with PostgreSQL-specific features
 * - Entity Manager for JPA operations
 * - Bean Validator for entity validation
 * - Transaction management
 * - PostgreSQL-specific data types and indexing
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("testcontainers-postgresql")
@Testcontainers
@Transactional
public abstract class PostgreSQLTestContainersIntegrationTestBase {

    /**
     * PostgreSQL TestContainer with version 14 for PostgreSQL-specific features.
     * Configured with proper timezone and locale settings.
     */
    @Container
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("openespi_test")
            .withUsername("testuser")
            .withPassword("testpass")
            .withCommand("postgres", 
                       "-c", "timezone=UTC",
                       "-c", "log_statement=all",
                       "-c", "log_duration=on",
                       "-c", "max_connections=200");

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected Validator validator;

    @Autowired
    protected DataSource dataSource;

    /**
     * Configure Spring Boot properties dynamically from TestContainer.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        
        // JPA configuration for TestContainers
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
        
        // PostgreSQL-specific JPA properties
        registry.add("spring.jpa.properties.hibernate.jdbc.time_zone", () -> "UTC");
        registry.add("spring.jpa.properties.hibernate.type.preferred_instant_jdbc_type", () -> "TIMESTAMP_WITH_TIMEZONE");
        
        // Flyway configuration
        registry.add("spring.flyway.enabled", () -> "false"); // We'll manage Flyway manually
        registry.add("spring.flyway.locations", () -> "classpath:db/migration/postgresql");
    }

    /**
     * Setup method executed before each test to ensure clean database state.
     */
    @BeforeEach
    void setUp() throws SQLException {
        setupDatabaseSchemas();
        runFlywayMigrations();
    }

    /**
     * Cleanup method executed after each test.
     */
    @AfterEach
    void tearDown() {
        // TestContainers automatically cleans up, but we can add custom cleanup if needed
    }

    /**
     * Create the required database schemas for OpenESPI.
     * Creates both usage and customer schemas as separate schemas within the same database.
     */
    private void setupDatabaseSchemas() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            // Create schemas if they don't exist
            statement.execute("CREATE SCHEMA IF NOT EXISTS openespi_usage");
            statement.execute("CREATE SCHEMA IF NOT EXISTS openespi_customer");
            
            // Grant permissions
            statement.execute("GRANT ALL PRIVILEGES ON SCHEMA openespi_usage TO " + POSTGRESQL_CONTAINER.getUsername());
            statement.execute("GRANT ALL PRIVILEGES ON SCHEMA openespi_customer TO " + POSTGRESQL_CONTAINER.getUsername());
            statement.execute("GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA openespi_usage TO " + POSTGRESQL_CONTAINER.getUsername());
            statement.execute("GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA openespi_customer TO " + POSTGRESQL_CONTAINER.getUsername());
            statement.execute("GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA openespi_usage TO " + POSTGRESQL_CONTAINER.getUsername());
            statement.execute("GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA openespi_customer TO " + POSTGRESQL_CONTAINER.getUsername());
        }
    }

    /**
     * Run Flyway migrations for both usage and customer schemas.
     * This ensures the database structure matches the application expectations.
     */
    private void runFlywayMigrations() {
        // Migrate usage schema (V1-V3)
        Flyway flywayUsage = Flyway.configure()
                .dataSource(createSchemaDataSource("openespi_usage"))
                .locations("classpath:db/migration/postgresql")
                .table("flyway_schema_history_usage")
                .schemas("openespi_usage")
                .load();
        flywayUsage.migrate();

        // Migrate customer schema (V4-V6)  
        Flyway flywayCustomer = Flyway.configure()
                .dataSource(createSchemaDataSource("openespi_customer"))
                .locations("classpath:db/migration/postgresql")
                .table("flyway_schema_history_customer")
                .schemas("openespi_customer")
                .load();
        flywayCustomer.migrate();
    }

    /**
     * Create a DataSource for a specific schema.
     */
    private DataSource createSchemaDataSource(String schema) {
        org.springframework.boot.jdbc.DataSourceBuilder<?> builder = 
            org.springframework.boot.jdbc.DataSourceBuilder.create();
        
        return builder
                .url(POSTGRESQL_CONTAINER.getJdbcUrl() + "?currentSchema=" + schema)
                .username(POSTGRESQL_CONTAINER.getUsername())
                .password(POSTGRESQL_CONTAINER.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Execute SQL statement in the usage schema.
     */
    protected void executeInUsageSchema(String sql) throws SQLException {
        try (Connection connection = createSchemaDataSource("openespi_usage").getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * Execute SQL statement in the customer schema.
     */
    protected void executeInCustomerSchema(String sql) throws SQLException {
        try (Connection connection = createSchemaDataSource("openespi_customer").getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * Execute JSONB query in PostgreSQL.
     */
    protected void executeJsonbQuery(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * Flush and clear the entity manager.
     */
    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Persist an entity and flush.
     */
    protected <T> T persistAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    /**
     * Merge an entity and flush.
     */
    protected <T> T mergeAndFlush(T entity) {
        T merged = entityManager.merge(entity);
        entityManager.flush();
        return merged;
    }

    /**
     * Get the PostgreSQL container instance for direct database operations.
     */
    protected static PostgreSQLContainer<?> getPostgreSQLContainer() {
        return POSTGRESQL_CONTAINER;
    }

    /**
     * Helper method to validate ESPI ATOM href URL format.
     * ESPI requires specific URL patterns for resource links.
     */
    protected boolean isValidEspiHref(String href) {
        if (href == null || href.isEmpty()) {
            return false;
        }
        
        // ESPI href pattern: /espi/1_1/resource/{ResourceType}/{id}
        return href.matches(".*\\/espi\\/1_1\\/resource\\/\\w+\\/[\\w-]+.*");
    }

    /**
     * Helper method to extract resource ID from ESPI href URL.
     */
    protected String extractResourceIdFromHref(String href) {
        if (!isValidEspiHref(href)) {
            return null;
        }
        
        String[] parts = href.split("/");
        // Find the part after "resource/{ResourceType}/"
        for (int i = 0; i < parts.length - 1; i++) {
            if ("resource".equals(parts[i]) && i + 2 < parts.length) {
                return parts[i + 2];
            }
        }
        
        return null;
    }

    /**
     * Helper method to test PostgreSQL-specific data types.
     */
    protected boolean testBigSerialSequence(String schema, String table, String column) throws SQLException {
        try (Connection connection = createSchemaDataSource(schema).getConnection();
             Statement statement = connection.createStatement()) {
            
            String sequenceName = schema + "." + table + "_" + column + "_seq";
            String sql = "SELECT last_value FROM " + sequenceName;
            var resultSet = statement.executeQuery(sql);
            return resultSet.next() && resultSet.getLong(1) >= 0;
        }
    }

    /**
     * Helper method to test PostgreSQL JSONB functionality.
     */
    protected boolean testJsonbQuery(String schema, String table, String jsonbColumn, String jsonPath) throws SQLException {
        try (Connection connection = createSchemaDataSource(schema).getConnection();
             Statement statement = connection.createStatement()) {
            
            String sql = "SELECT " + jsonbColumn + " -> '" + jsonPath + "' FROM " + schema + "." + table + " LIMIT 1";
            var resultSet = statement.executeQuery(sql);
            return true; // If query executes without error, JSONB is working
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Helper method to test PostgreSQL GIN indexes on JSONB fields.
     */
    protected boolean testGinIndexExists(String schema, String table, String indexName) throws SQLException {
        try (Connection connection = createSchemaDataSource(schema).getConnection();
             Statement statement = connection.createStatement()) {
            
            String sql = "SELECT indexname FROM pg_indexes WHERE schemaname = '" + schema + 
                        "' AND tablename = '" + table + "' AND indexname = '" + indexName + "'";
            var resultSet = statement.executeQuery(sql);
            return resultSet.next();
        }
    }

    /**
     * Helper method to test PostgreSQL timezone handling.
     */
    protected boolean testTimezoneHandling() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            String sql = "SELECT EXTRACT(TIMEZONE FROM NOW()) AS current_timezone";
            var resultSet = statement.executeQuery(sql);
            return resultSet.next() && resultSet.getDouble(1) == 0.0; // UTC = 0
        }
    }
}