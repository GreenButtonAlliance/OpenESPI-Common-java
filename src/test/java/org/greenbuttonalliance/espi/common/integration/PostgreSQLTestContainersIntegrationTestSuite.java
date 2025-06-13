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

/**
 * Integration test suite for PostgreSQL TestContainers tests.
 * 
 * This suite runs all TestContainers integration tests for OpenESPI Common module
 * using a real PostgreSQL database in Docker containers. The tests validate:
 * 
 * 1. PostgreSQL Flyway Migration Validation:
 *    - All migration scripts execute successfully with PostgreSQL-specific features
 *    - Database schema structure matches expectations with PostgreSQL data types
 *    - PostgreSQL BIGSERIAL sequences are working correctly
 *    - TIMESTAMP WITH TIME ZONE fields are properly configured
 *    - JSONB fields and GIN indexes are functional
 *    - PostgreSQL-specific indexes and constraints are properly applied
 *    - Usage and customer schemas are properly separated
 * 
 * 2. PostgreSQL Entity CRUD Operations:
 *    - Basic create, read, update, delete operations with PostgreSQL
 *    - Entity validation constraints are enforced
 *    - ESPI-compliant UUID generation and storage
 *    - PostgreSQL-specific data types (BIGSERIAL, TIMESTAMP WITH TIME ZONE) work correctly
 *    - PostgreSQL timezone handling for timestamps
 *    - Transaction boundaries work as expected with PostgreSQL
 *    - PostgreSQL boolean values are handled correctly
 * 
 * 3. PostgreSQL ATOM Href Relationships:
 *    - ESPI-compliant URL format validation with PostgreSQL TEXT fields
 *    - Self and up-link href generation and storage
 *    - Cross-schema href references work without foreign key constraints
 *    - RESTful resource navigation with PostgreSQL
 *    - Long URL storage and retrieval in PostgreSQL TEXT fields
 *    - Special characters and URL encoding in href fields
 * 
 * 4. PostgreSQL Schema Coupling Validation:
 *    - Loose coupling between usage and customer schemas
 *    - No foreign key constraints exist between schemas
 *    - Independent schema operations work correctly
 *    - PostgreSQL schema search path independence
 *    - PostgreSQL-specific schema permissions work independently
 *    - JSONB functionality works independently per schema
 *    - PostgreSQL sequence management between schemas
 *    - Cross-schema operations require explicit schema qualification
 * 
 * PostgreSQL-Specific Features Tested:
 * - BIGSERIAL sequences for primary key generation
 * - TIMESTAMP WITH TIME ZONE for proper timezone handling
 * - JSONB data type for JSON storage with native querying
 * - GIN indexes on JSONB fields for efficient JSON queries
 * - PostgreSQL schema-based multi-tenancy patterns
 * - PostgreSQL TEXT fields for unlimited text storage
 * - PostgreSQL boolean data type handling
 * - PostgreSQL search path and schema isolation
 * 
 * Test Execution:
 * - Uses PostgreSQL 14 TestContainer
 * - Creates separate openespi_usage and openespi_customer schemas
 * - Applies all Flyway migrations (V1-V6) with PostgreSQL-specific features
 * - Runs tests in isolated transactions
 * - Validates PostgreSQL-specific data types and features
 * - Cleans up resources automatically
 * 
 * To run this suite:
 * - mvn test -Dtest=PostgreSQL*IntegrationTest
 * - Requires Docker to be running
 * - Requires TestContainers dependencies in classpath
 * - Requires PostgreSQL JDBC driver
 * 
 * Configuration:
 * - Profile: testcontainers-postgresql
 * - Database: PostgreSQL 14 (supports all modern PostgreSQL features)
 * - Schemas: openespi_usage, openespi_customer
 * - Migration location: classpath:db/migration/postgresql
 * - Timezone: UTC
 * - Character encoding: UTF-8
 * - Connection pool: HikariCP with PostgreSQL-specific settings
 */
/**
 * Documentation class for PostgreSQL TestContainers Integration Test Suite.
 * 
 * To run all PostgreSQL integration tests individually:
 * - PostgreSQLFlywayMigrationIntegrationTest
 * - PostgreSQLEntityCrudIntegrationTest
 * - PostgreSQLAtomHrefIntegrationTest
 * - PostgreSQLSchemaCouplingIntegrationTest
 */
@DisplayName("OpenESPI Common PostgreSQL Integration Tests Documentation")
public class PostgreSQLTestContainersIntegrationTestSuite {

    /**
     * This test suite class serves as an entry point for running all PostgreSQL
     * TestContainers integration tests together. The actual test methods are
     * implemented in the individual test classes:
     * 
     * - PostgreSQLFlywayMigrationIntegrationTest: Database schema validation with PostgreSQL features
     * - PostgreSQLEntityCrudIntegrationTest: Entity persistence operations with PostgreSQL data types
     * - PostgreSQLAtomHrefIntegrationTest: ESPI href URL validation with PostgreSQL TEXT fields
     * - PostgreSQLSchemaCouplingIntegrationTest: Schema isolation validation with PostgreSQL features
     * 
     * Each test class extends PostgreSQLTestContainersIntegrationTestBase which provides:
     * - PostgreSQL TestContainer setup and configuration
     * - Flyway migration execution with PostgreSQL-specific features
     * - Spring Boot test context with real PostgreSQL database
     * - Common utilities for PostgreSQL database operations
     * - PostgreSQL-specific data type testing utilities
     * - Automatic cleanup and resource management
     * 
     * The suite ensures comprehensive validation of:
     * 1. PostgreSQL database structure and migration correctness
     * 2. JPA entity mapping with PostgreSQL-specific data types
     * 3. ESPI specification compliance with PostgreSQL
     * 4. Architectural design principles (loose coupling) with PostgreSQL schemas
     * 5. Cross-schema relationship management via URLs
     * 6. PostgreSQL performance features (JSONB, GIN indexes)
     * 7. PostgreSQL timezone and character encoding handling
     * 8. PostgreSQL multi-tenancy patterns
     * 
     * Key PostgreSQL Features Validated:
     * 
     * BIGSERIAL Sequences:
     * - Auto-incrementing 64-bit integer primary keys
     * - Sequence independence between schemas
     * - Proper sequence progression and reset handling
     * 
     * TIMESTAMP WITH TIME ZONE:
     * - UTC timezone storage and retrieval
     * - Proper timezone conversion and handling
     * - ESPI-compliant timestamp format compliance
     * 
     * JSONB Data Type:
     * - Native JSON storage with indexing
     * - GIN index creation and querying
     * - JSONB operator support (->>, @>, etc.)
     * - Cross-schema JSONB functionality
     * 
     * PostgreSQL Schema Features:
     * - Schema-based isolation and multi-tenancy
     * - Search path configuration and independence
     * - Schema-specific permissions and access control
     * - Cross-schema querying with explicit qualification
     * 
     * Performance Features:
     * - GIN indexes on JSONB fields for fast JSON queries
     * - B-tree indexes on UUID and business key fields
     * - Proper constraint and foreign key handling
     * - Query optimization for large datasets
     * 
     * Comparison with MySQL Implementation:
     * 
     * Key Differences:
     * - PostgreSQL uses schemas within a database vs MySQL's multiple databases
     * - BIGSERIAL vs MySQL's BIGINT AUTO_INCREMENT
     * - TIMESTAMP WITH TIME ZONE vs MySQL's DATETIME(6)
     * - JSONB with GIN indexes vs MySQL's JSON with generated columns
     * - PostgreSQL BOOLEAN vs MySQL's TINYINT(1)
     * - PostgreSQL TEXT (unlimited) vs MySQL's TEXT (65KB limit)
     * 
     * Advantages of PostgreSQL:
     * - Native JSON support with JSONB indexing
     * - Superior timezone handling with TIMESTAMP WITH TIME ZONE
     * - Schema-based multi-tenancy without multiple databases
     * - More sophisticated indexing options (GIN, GIST, etc.)
     * - Unlimited TEXT field storage
     * - More standards-compliant SQL implementation
     * 
     * Running the Tests:
     * 
     * Individual Test Classes:
     * mvn test -Dtest=PostgreSQLFlywayMigrationIntegrationTest
     * mvn test -Dtest=PostgreSQLEntityCrudIntegrationTest
     * mvn test -Dtest=PostgreSQLAtomHrefIntegrationTest
     * mvn test -Dtest=PostgreSQLSchemaCouplingIntegrationTest
     * 
     * All PostgreSQL Tests:
     * mvn test -Dtest=PostgreSQL*IntegrationTest
     * 
     * All Integration Tests (MySQL + PostgreSQL):
     * mvn test -Dtest=*IntegrationTest
     * 
     * Profile-Specific Execution:
     * mvn test -Dtest=PostgreSQL*IntegrationTest -Dspring.profiles.active=testcontainers-postgresql
     * 
     * Prerequisites:
     * - Docker Engine running (for TestContainers)
     * - PostgreSQL JDBC driver in classpath
     * - TestContainers dependency configured
     * - Sufficient memory for PostgreSQL container (recommend 512MB+)
     * - Network access for Docker image downloads
     * 
     * Troubleshooting:
     * 
     * Common Issues:
     * 1. Docker not running: Ensure Docker Desktop/Engine is started
     * 2. Memory issues: Increase Docker memory allocation
     * 3. Network timeouts: Check firewall and proxy settings
     * 4. Port conflicts: TestContainers handles port allocation automatically
     * 5. PostgreSQL driver missing: Add org.postgresql:postgresql dependency
     * 
     * Environment Variables:
     * - TESTCONTAINERS_REUSE_ENABLE=true (reuse containers between test runs)
     * - TESTCONTAINERS_RYUK_DISABLED=true (disable container cleanup in CI)
     * - DOCKER_HOST (if using remote Docker)
     * 
     * CI/CD Considerations:
     * - Ensure Docker-in-Docker or Docker socket mounting in CI
     * - Consider using PostgreSQL service containers in GitHub Actions
     * - Set appropriate timeouts for container startup
     * - Monitor resource usage in CI environments
     */

    // No implementation needed - JUnit 5 Platform Suite handles test discovery and execution
}