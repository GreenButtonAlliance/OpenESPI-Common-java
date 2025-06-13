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
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base class for TestContainers integration tests with MySQL.
 * 
 * This class provides:
 * - MySQL TestContainer setup with proper schema initialization
 * - Flyway migration execution for both usage and customer schemas
 * - Spring Boot test configuration with real database
 * - Common utilities for database operations and validation
 * 
 * Tests extending this class will have access to:
 * - Full MySQL database with proper schema structure
 * - Applied Flyway migrations (V1-V6)
 * - Entity Manager for JPA operations
 * - Bean Validator for entity validation
 * - Transaction management
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("testcontainers-mysql")
@Testcontainers
@Transactional
public abstract class TestContainersIntegrationTestBase {

    /**
     * MySQL TestContainer with version 8.0 for compatibility.
     * Configured with proper charset and timezone settings.
     */
    @Container
    static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("openespi_test")
            .withUsername("testuser")
            .withPassword("testpass")
            .withCommand("--character-set-server=utf8mb4", 
                       "--collation-server=utf8mb4_unicode_ci",
                       "--time-zone=+00:00");

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
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        
        // JPA configuration for TestContainers
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
        
        // Flyway configuration
        registry.add("spring.flyway.enabled", () -> "false"); // We'll manage Flyway manually
        registry.add("spring.flyway.locations", () -> "classpath:db/migration/mysql");
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
     * Creates both usage and customer schemas as separate databases.
     */
    private void setupDatabaseSchemas() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            // Create schemas if they don't exist
            statement.execute("CREATE DATABASE IF NOT EXISTS openespi_usage CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            statement.execute("CREATE DATABASE IF NOT EXISTS openespi_customer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            
            // Grant permissions
            statement.execute("GRANT ALL PRIVILEGES ON openespi_usage.* TO '" + MYSQL_CONTAINER.getUsername() + "'@'%'");
            statement.execute("GRANT ALL PRIVILEGES ON openespi_customer.* TO '" + MYSQL_CONTAINER.getUsername() + "'@'%'");
            statement.execute("FLUSH PRIVILEGES");
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
                .locations("classpath:db/migration/mysql")
                .table("flyway_schema_history_usage")
                .schemas("openespi_usage")
                .load();
        flywayUsage.migrate();

        // Migrate customer schema (V4-V6)  
        Flyway flywayCustomer = Flyway.configure()
                .dataSource(createSchemaDataSource("openespi_customer"))
                .locations("classpath:db/migration/mysql")
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
                .url(MYSQL_CONTAINER.getJdbcUrl().replace("/openespi_test", "/" + schema))
                .username(MYSQL_CONTAINER.getUsername())
                .password(MYSQL_CONTAINER.getPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
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
     * Get the MySQL container instance for direct database operations.
     */
    protected static MySQLContainer<?> getMySQLContainer() {
        return MYSQL_CONTAINER;
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
}