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
 * Integration test suite for MySQL TestContainers tests.
 * 
 * This suite runs all TestContainers integration tests for OpenESPI Common module
 * using a real MySQL database in Docker containers. The tests validate:
 * 
 * 1. Flyway Migration Validation:
 *    - All migration scripts execute successfully
 *    - Database schema structure matches expectations
 *    - Proper indexes and constraints are created
 * 
 * 2. Entity CRUD Operations:
 *    - Basic create, read, update, delete operations
 *    - Entity validation constraints
 *    - Transaction boundaries
 *    - ESPI-compliant UUID generation
 * 
 * 3. ATOM Href Relationships:
 *    - ESPI-compliant URL format validation
 *    - Self and up-link href generation
 *    - Cross-schema href references
 *    - RESTful resource navigation
 * 
 * 4. Schema Coupling Validation:
 *    - Loose coupling between usage and customer schemas
 *    - No foreign key constraints between schemas
 *    - Independent schema operations
 *    - Application-level relationship management
 * 
 * Test Execution:
 * - Uses MySQL 8.0 TestContainer
 * - Creates separate openespi_usage and openespi_customer schemas
 * - Applies all Flyway migrations (V1-V6)
 * - Runs tests in isolated transactions
 * - Cleans up resources automatically
 * 
 * To run this suite:
 * - mvn test -Dtest=MySQLTestContainersIntegrationTestSuite
 * - Requires Docker to be running
 * - Requires TestContainers dependencies in classpath
 * 
 * Configuration:
 * - Profile: testcontainers-mysql
 * - Database: MySQL 8.0 (latest stable)
 * - Schemas: openespi_usage, openespi_customer
 * - Migration location: classpath:db/migration/mysql
 */
/**
 * Documentation class for MySQL TestContainers Integration Test Suite.
 * 
 * To run all MySQL integration tests individually:
 * - MySQLFlywayMigrationIntegrationTest
 * - MySQLEntityCrudIntegrationTest  
 * - MySQLAtomHrefIntegrationTest
 * - MySQLSchemaCouplingIntegrationTest
 */
@DisplayName("OpenESPI Common MySQL Integration Tests Documentation")
public class MySQLTestContainersIntegrationTestSuite {

    /**
     * This test suite class serves as an entry point for running all MySQL
     * TestContainers integration tests together. The actual test methods are
     * implemented in the individual test classes:
     * 
     * - MySQLFlywayMigrationIntegrationTest: Database schema validation
     * - MySQLEntityCrudIntegrationTest: Entity persistence operations
     * - MySQLAtomHrefIntegrationTest: ESPI href URL validation
     * - MySQLSchemaCouplingIntegrationTest: Schema isolation validation
     * 
     * Each test class extends TestContainersIntegrationTestBase which provides:
     * - MySQL TestContainer setup and configuration
     * - Flyway migration execution
     * - Spring Boot test context with real database
     * - Common utilities for database operations
     * - Automatic cleanup and resource management
     * 
     * The suite ensures comprehensive validation of:
     * 1. Database structure and migration correctness
     * 2. JPA entity mapping and persistence behavior
     * 3. ESPI specification compliance
     * 4. Architectural design principles (loose coupling)
     * 5. Cross-schema relationship management
     * 6. Performance and constraint validation
     */

    // No implementation needed - JUnit 5 Platform Suite handles test discovery and execution
}