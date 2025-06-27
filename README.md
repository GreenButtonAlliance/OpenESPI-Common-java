[![Build Status](https://img.shields.io/badge/Build-Passing-success?style=flat&logo=github-actions)](https://github.com/GreenButtonAlliance/OpenESPI-Common-java/actions)
[![CI/CD Pipeline](https://github.com/GreenButtonAlliance/OpenESPI-Common-java/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/GreenButtonAlliance/OpenESPI-Common-java/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=GreenButtonAlliance_OpenESPI-Common-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=GreenButtonAlliance_OpenESPI-Common-java)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?style=flat&logo=spring)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=openjdk)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=flat&logo=apache-maven)](https://maven.apache.org/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-9+-purple?style=flat&logo=eclipse)](https://jakarta.ee/)
[![Hibernate](https://img.shields.io/badge/Hibernate-6.x-yellow?style=flat&logo=hibernate)](https://hibernate.org/)
[![MapStruct](https://img.shields.io/badge/MapStruct-1.6.0-orange?style=flat)](https://mapstruct.org/)
[![Lombok](https://img.shields.io/badge/Lombok-1.18.34-red?style=flat)](https://projectlombok.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-lightgrey?style=flat&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)
[![NAESB](https://img.shields.io/badge/NAESB-ESPI%20ver.%204.0-blue?style=flat)](https://www.naesb.org/)

# OpenESPI-Common

This is the Common module of the OpenESPI Green Button Data Custodian and Third Party implementation. It is a modern Spring Boot 3.5 application written in Java 21 and built on top of JPA for database access.

This Common run-time and test code is shared between stand-alone Data Custodian and Third Party applications. [OpenESPI-DataCustodian](https://github.com/greenbuttonalliance/OpenESPI-DataCustodian-java) and [OpenESPI-ThirdParty](https://github.com/greenbuttonalliance/OpenESPI-ThirdParty-java).

## Recent Modernization (2025)

The project has been modernized to leverage current enterprise Java standards:

- **Spring Boot 3.5.0** - Latest Spring Boot framework with auto-configuration
- **Java 21** - Modern LTS Java version with enhanced performance
- **Jakarta EE 9+** - Migrated from legacy javax packages 
- **Hibernate 6.x** - Modern ORM with improved performance
- **DTO Architecture** - Clean separation with JAXB-based DTOs for XML marshalling
- **MapStruct Mappers** - Type-safe entity-DTO conversion
- **UUID Primary Keys** - Modern entity architecture
- **Maven 3.9+** - Updated build system

An operational sandbox with these services operating may be found at:
<a href="https://sandbox.greenbuttonalliance.org:8443">sandbox.greenbuttonalliance.org:8443</a>

## Setup

First clone the project from github:

```bash
git clone https://github.com/greenbuttonalliance/OpenESPI-Common-java.git
cd OpenESPI-Common
```

Then install the OpenESPI-Common JAR in your local repository:
```bash
# Build and install the project with tests
mvn clean install

# or for a specific profile
mvn -P <profile name> clean install

# Skip tests if needed (for development builds)
mvn -Dmaven.test.skip=true clean install
```

## IDE Setup

### Eclipse Setup

Open Eclipse and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### Spring Tool Suite Setup

Open Spring Tool Suite and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### IntelliJ Setup

Open IntelliJ and open the project (File > Open...).

## Testing

The project includes comprehensive test classes to verify the Spring Boot 3.5 migration and core functionality:

### Test Classes

1. **`MigrationVerificationTest`** - Verifies core Spring Boot 3.5 migration features:
   - Jakarta EE 9+ Validation API functionality
   - Jakarta XML Binding (JAXB) for DTO marshalling
   - UUID primary key architecture
   - ESPI resource inheritance structure
   - Entity properties with Jakarta annotations
   - Customer domain entities independence
   - SummaryMeasurement DTO business logic

2. **`TestApplication`** - Spring Boot test application:
   - Minimal Spring Boot configuration for testing
   - Entity scanning for domain objects
   - JPA repository configuration
   - Test-specific profile activation

3. **`SpringBootTestConfiguration`** - Test configuration beans:
   - ESPI ID generator service for UUID5 generation
   - JAXB marshallers for domain objects and XML fragments
   - Bean validation factory
   - REST template for testing

### Running Tests

#### Run All Tests (Recommended)
```bash
# Execute complete test suite
mvn clean test

# Run tests with verbose output
mvn clean test -X

# Run specific test class
mvn test -Dtest=MigrationVerificationTest
```

#### Build with Tests
```bash
# Full build with test execution
mvn clean package

# Install to local repository with tests
mvn clean install

# Verify build integrity
mvn clean verify
```

#### Development Builds (Skip Tests)
```bash
# Fast compilation without tests (development only)
mvn clean compile -Dmaven.test.skip=true

# Fast package without tests (development only)
mvn clean package -Dmaven.test.skip=true

# Fast install without tests (development only)
mvn clean install -Dmaven.test.skip=true
```

#### Profile-Specific Testing
```bash
# Run with specific Maven profile
mvn clean test -P <profile-name>

# Example: development profile
mvn clean test -P development
```

### Test Coverage

The test suite verifies:
- ✅ Jakarta EE 9+ API compatibility
- ✅ Spring Boot 3.5 auto-configuration
- ✅ JAXB XML marshalling/unmarshalling
- ✅ UUID primary key generation and usage
- ✅ Entity relationship mapping
- ✅ Bean validation functionality
- ✅ DTO record structure and ESPI business logic
- ✅ Compilation integrity across all modules

### Test Reports

Test results are generated in:
- `target/surefire-reports/` - Test execution reports
- `target/site/jacoco/` - Code coverage reports (if jacoco plugin enabled)

For integration testing with external Green Button data, refer to the consumer applications that use this common module.
