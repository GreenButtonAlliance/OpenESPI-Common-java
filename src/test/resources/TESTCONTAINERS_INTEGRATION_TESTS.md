# TestContainers Integration Tests

This document describes the TestContainers integration tests implemented for OpenESPI Common module to validate MySQL database operations, Flyway migrations, and schema design.

## Overview

The TestContainers integration tests provide comprehensive validation of:
- Flyway migration scripts execution
- Database schema structure and constraints
- Entity persistence and CRUD operations
- ESPI-compliant ATOM href URL relationships
- Loose coupling between usage and customer schemas

## Test Classes

### 1. TestContainersIntegrationTestBase
**Location**: `src/test/java/org/greenbuttonalliance/espi/common/integration/TestContainersIntegrationTestBase.java`

Base class providing:
- MySQL 8.0 TestContainer setup
- Flyway migration execution for both schemas
- Spring Boot test configuration with real database
- Common utilities for database operations
- ESPI href URL validation helpers

**Key Features**:
- Automatic schema creation (openespi_usage, openespi_customer)
- Flyway migrations applied on startup
- Transaction management for test isolation
- Helper methods for cross-schema operations

### 2. MySQLFlywayMigrationIntegrationTest
**Location**: `src/test/java/org/greenbuttonalliance/espi/common/integration/MySQLFlywayMigrationIntegrationTest.java`

**Purpose**: Validates that Flyway migration scripts execute successfully and create the expected database structure.

**Test Coverage**:
- ✅ All Flyway migrations execute without errors
- ✅ Usage schema tables created (V1-V3 migrations)
- ✅ Customer schema tables created (V4-V6 migrations)
- ✅ Proper table structure with correct columns
- ✅ Indexes and constraints properly applied
- ✅ Unique constraints enforced
- ✅ Column types comply with ESPI specification
- ✅ Schema separation between usage and customer data

**Key Validations**:
- application_information table structure
- retail_customers table structure  
- customers table with PII fields
- Performance indexes creation
- Unique constraint enforcement
- ESPI-compliant column types

### 3. MySQLEntityCrudIntegrationTest
**Location**: `src/test/java/org/greenbuttonalliance/espi/common/integration/MySQLEntityCrudIntegrationTest.java`

**Purpose**: Validates basic CRUD operations on key entities using real MySQL database.

**Test Coverage**:
- ✅ Create and retrieve ApplicationInformation entities
- ✅ Create and retrieve RetailCustomer entities  
- ✅ Create and retrieve UsagePoint entities
- ✅ Complete meter reading hierarchy creation
- ✅ Entity validation constraints enforcement
- ✅ Entity updates with timestamp handling
- ✅ Entity deletion operations
- ✅ Unique constraint violations
- ✅ ESPI-compliant UUID generation
- ✅ Timestamp storage and retrieval
- ✅ Large text field handling

**Key Features Tested**:
- JPA entity mapping correctness
- ESPI UUID5 generation (deterministic but unique)
- UTC timestamp handling with Z termination
- Transaction boundary management
- Bean validation integration
- Large text field support (TEXT columns)

### 4. MySQLAtomHrefIntegrationTest  
**Location**: `src/test/java/org/greenbuttonalliance/espi/common/integration/MySQLAtomHrefIntegrationTest.java`

**Purpose**: Validates ESPI-compliant ATOM href URL generation and relationship management.

**Test Coverage**:
- ✅ ESPI ATOM href URL format compliance
- ✅ Parent-child href relationships
- ✅ Cross-schema href relationships
- ✅ Subscription href relationships
- ✅ href URL format validation
- ✅ Resource ID extraction from URLs
- ✅ Navigation between related resources

**ESPI Compliance Validated**:
- URL pattern: `/espi/1_1/resource/{ResourceType}/{id}`
- Self-link href generation
- Up-link href relationships
- Cross-schema loose coupling via URLs
- RESTful resource navigation

### 5. MySQLSchemaCouplingIntegrationTest
**Location**: `src/test/java/org/greenbuttonalliance/espi/common/integration/MySQLSchemaCouplingIntegrationTest.java`

**Purpose**: Validates the architectural principle of loose coupling between usage and customer schemas.

**Test Coverage**:
- ✅ No foreign key constraints between schemas
- ✅ Independent schema operations
- ✅ Application-level data integrity
- ✅ Cross-schema queries through URLs
- ✅ Schema isolation for different data types
- ✅ Different security models per schema
- ✅ Loose coupling benefits demonstration

**Architectural Principles Validated**:
- Database-level schema independence
- URL-based relationship management
- Independent scaling capabilities
- Separate deployment models
- PII vs non-PII data isolation
- Microservices architecture support

## Configuration

### Test Profile
**Profile**: `testcontainers-mysql`
**Configuration**: `src/test/resources/application-testcontainers-mysql.properties`

**Key Settings**:
- MySQL 8.0 TestContainer
- Flyway migrations disabled (managed manually)
- Debug logging for SQL statements
- TestContainers reuse enabled for performance

### Database Setup
**Usage Schema**: `openespi_usage`
- Energy usage data (non-PII)
- High-volume time-series data
- Usage points, meter readings, interval data

**Customer Schema**: `openespi_customer`  
- Customer information (PII)
- Lower volume reference data
- Customer details, addresses, organizations

### Flyway Migrations
**Location**: `src/main/resources/db/migration/mysql/`
- V1-V3: Usage schema tables
- V4-V6: Customer schema tables
- Separate migration tracking per schema

## Running the Tests

### Prerequisites
- Docker running on the system
- Maven 3.6+ 
- Java 17+
- TestContainers dependencies in classpath

### Execution Commands

```bash
# Run all TestContainers integration tests
mvn test -Dtest="**/integration/**IntegrationTest"

# Run specific test class
mvn test -Dtest=MySQLFlywayMigrationIntegrationTest

# Run with specific profile
mvn test -Dspring.profiles.active=testcontainers-mysql

# Debug with verbose logging
mvn test -Dtest=MySQLEntityCrudIntegrationTest -Dlogging.level.org.testcontainers=DEBUG
```

### Performance Considerations
- Tests use TestContainer reuse for faster execution
- Each test runs in isolated transaction
- Database schemas are recreated for each test class
- Flyway migrations run once per test class setup

## Benefits

### 1. Real Database Testing
- Tests run against actual MySQL 8.0 database
- Validates SQL compatibility and performance
- Catches database-specific issues early

### 2. Migration Validation
- Ensures Flyway scripts execute successfully
- Validates database structure matches application expectations
- Tests schema evolution scenarios

### 3. Architecture Validation
- Proves loose coupling design works correctly
- Validates ESPI specification compliance
- Tests microservices architecture patterns

### 4. CI/CD Integration
- Tests can run in automated pipelines
- No external database dependencies
- Consistent test environment across teams

## Troubleshooting

### Common Issues

**Docker Not Running**:
```
org.testcontainers.containers.ContainerLaunchException: Could not find a valid Docker environment
```
Solution: Ensure Docker Desktop is running

**Port Conflicts**:
```
Caused by: java.net.BindException: Address already in use
```
Solution: TestContainers automatically handles port assignment

**Memory Issues**:
```
OutOfMemoryError during container startup
```
Solution: Increase Docker memory allocation or reduce test parallelism

### Debug Configuration
Add to test properties for debugging:
```properties
logging.level.org.testcontainers=DEBUG
logging.level.org.flywaydb=DEBUG
logging.level.org.hibernate.SQL=DEBUG
spring.jpa.show-sql=true
```

## Future Enhancements

### 1. PostgreSQL Support
- Duplicate tests for PostgreSQL database
- Multi-database compatibility validation
- Database-specific optimization testing

### 2. Performance Testing
- Load testing with large datasets
- Query performance validation
- Index effectiveness testing

### 3. Data Migration Testing
- Test data migration scenarios
- Schema evolution testing
- Backward compatibility validation

### 4. Security Testing
- PII data handling validation
- Access control testing
- Audit trail verification

## Integration with Development Workflow

### Local Development
- Developers can run integration tests locally
- Fast feedback on database changes
- Schema validation during development

### CI/CD Pipeline
- Automated execution in build pipeline
- Migration validation on pull requests
- Database compatibility testing

### Deployment Validation
- Pre-deployment database testing
- Schema migration validation
- Data integrity verification

This comprehensive TestContainers integration test suite ensures the OpenESPI Common module's database layer is robust, compliant with ESPI specifications, and follows architectural best practices for microservices design.