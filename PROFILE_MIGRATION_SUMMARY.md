# Spring Boot 3.5 Profile Migration Summary

## Overview
This document outlines the migration from legacy profile configuration to modern Spring Boot 3.5 profile management for OpenESPI-Common.

## ✅ New Spring Boot 3.5 Profiles Created

### 1. **dev-mysql** (Default Active)
- **File**: `application-dev-mysql.yml`
- **Purpose**: MySQL development environment
- **Database**: MySQL 8.0+ with openespi_dev database
- **Features**: Debug logging, Flyway migrations, H2 console disabled
- **Usage**: `mvn spring-boot:run -P dev-mysql`

### 2. **dev-postgresql** 
- **File**: `application-dev-postgresql.yml`
- **Purpose**: PostgreSQL development environment
- **Database**: PostgreSQL 13+ with openespi_dev database
- **Features**: Debug logging, Flyway migrations, PostgreSQL-specific configuration
- **Usage**: `mvn spring-boot:run -P dev-postgresql`

### 3. **prod**
- **File**: `application-prod.yml`
- **Purpose**: Production environment
- **Database**: Environment-specific (supports MySQL/PostgreSQL)
- **Features**: Minimal logging, SSL required, environment variables
- **Usage**: `mvn spring-boot:run -P prod`

### 4. **local**
- **File**: `application-local.yml`
- **Purpose**: Local development with H2 in-memory database
- **Database**: H2 in-memory with sample data
- **Features**: H2 console enabled, debug logging, create-drop schema
- **Usage**: `mvn spring-boot:run -P local`

### 5. **docker**
- **File**: `application-docker.yml`
- **Purpose**: Docker containerized environments
- **Database**: MySQL via Docker Compose service
- **Features**: Container-optimized settings, Prometheus metrics
- **Usage**: `mvn spring-boot:run -P docker`

### 6. **aws-sandbox**
- **File**: `application-aws-sandbox.yml`
- **Purpose**: Green Button Alliance AWS Sandbox
- **Database**: AWS RDS MySQL
- **Features**: CloudWatch integration, GBA-specific settings
- **Usage**: `mvn spring-boot:run -P aws-sandbox`

### 7. **test** (Existing)
- **File**: `application-test.yml` / `application-test.properties`
- **Purpose**: JUnit 5 testing with H2 in-memory
- **Database**: H2 in-memory for tests
- **Features**: Test-specific configuration, ESPI test data

## 🔄 Legacy Profile Compatibility

### Maintained for Backward Compatibility:
- **`devmysql`** → Maps to `dev-mysql`
- **`awsgbasandbox`** → Maps to `aws-sandbox`
- **`dev`** → Maps to `local`

### Profile Migration Mapping:
```bash
# Legacy → Modern
mvn -P devmysql        → mvn -P dev-mysql
mvn -P awsgbasandbox   → mvn -P aws-sandbox
mvn -P dev             → mvn -P local
```

## 📋 Configuration Features

### Database Configuration
- **Connection Pooling**: HikariCP with optimized settings
- **Flyway Migrations**: Automatic database schema management
- **Multi-Schema Support**: Separate schemas for usage and customer data
- **Environment Variables**: Externalized configuration for production

### Logging Configuration
- **Development**: DEBUG level with SQL logging
- **Production**: INFO level with minimal output
- **Pattern**: Consistent timestamp and thread information
- **File Logging**: Environment-specific log files

### Management & Monitoring
- **Basic Configuration**: Foundation for actuator setup (endpoints disabled by default)
- **Application Responsibility**: DataCustodian and ThirdParty should configure specific endpoints
- **Info Endpoint**: Basic environment information available
- **Monitoring Integration**: Applications should add Prometheus, CloudWatch, etc.

### ESPI-Specific Configuration
- **UUID Namespaces**: ESPI-compliant namespace definitions
- **Schema Configuration**: Usage and customer schema separation
- **Debug Features**: Development-specific ESPI debugging

## 🚀 Usage Examples

### Development with MySQL:
```bash
mvn spring-boot:run -P dev-mysql
# Uses: application-dev-mysql.yml
# Database: MySQL localhost:3306/openespi_dev
```

### Development with PostgreSQL:
```bash
mvn spring-boot:run -P dev-postgresql
# Uses: application-dev-postgresql.yml
# Database: PostgreSQL localhost:5432/openespi_dev
```

### Local Development (H2):
```bash
mvn spring-boot:run -P local
# Uses: application-local.yml
# Database: H2 in-memory
# H2 Console: http://localhost:8080/h2-console
```

### Production Deployment:
```bash
mvn spring-boot:run -P prod \
  -DDB_URL=jdbc:mysql://prod-db:3306/openespi \
  -DDB_USERNAME=prod_user \
  -DDB_PASSWORD=secure_password
```

### Docker Environment:
```bash
mvn spring-boot:run -P docker
# Uses: application-docker.yml
# Database: MySQL container via Docker Compose
```

## 🔧 Environment Variables

### Production Environment Variables:
```bash
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/openespi_prod
DB_USERNAME=openespi_user
DB_PASSWORD=secure_password
DB_DRIVER=com.mysql.cj.jdbc.Driver

# SSL Configuration
SSL_ENABLED=true
SSL_KEYSTORE_PATH=/path/to/keystore.p12
SSL_KEYSTORE_PASSWORD=keystore_password

# OAuth2 Configuration
OAUTH2_ISSUER_URI=https://auth.example.com
```

## 📁 File Structure
```
src/main/resources/
├── application.properties              # Default configuration
├── application-dev-mysql.yml          # MySQL development
├── application-dev-postgresql.yml     # PostgreSQL development
├── application-prod.yml               # Production
├── application-local.yml              # Local development (H2)
├── application-docker.yml             # Docker environment
└── application-aws-sandbox.yml        # GBA AWS Sandbox

src/test/resources/
├── application-test.yml               # JUnit 5 tests
└── application-test.properties        # Test configuration
```

## ✅ Benefits of New Profile System

1. **Environment Parity**: Consistent configuration across all environments
2. **Externalized Configuration**: Production secrets via environment variables
3. **Database Flexibility**: Support for MySQL, PostgreSQL, and H2
4. **Monitoring Ready**: Built-in actuator and metrics configuration
5. **Security First**: Production profiles secure by default
6. **Development Friendly**: Rich debugging and console access for development
7. **Container Ready**: Docker and Kubernetes deployment support
8. **ESPI Compliant**: Standard-specific configuration management

## 🔍 Validation

### Profile Validation Commands:
```bash
# List all available profiles
mvn help:all-profiles

# Test profile activation
mvn help:effective-pom -P dev-postgresql

# Validate configuration
mvn spring-boot:run -P local --dry-run
```

## 🏗️ Architectural Design Decisions

### Actuator and Monitoring Placement
**Decision**: Actuator endpoints and monitoring configurations are **NOT** included in OpenESPI-Common profiles.

**Rationale**:
- OpenESPI-Common is a **dependency library JAR**, not a standalone application
- DataCustodian and ThirdParty are the **actual web applications** that need monitoring
- Applications should control their own endpoint exposure and security
- Monitoring requirements vary between DataCustodian (utility-facing) and ThirdParty (customer-facing)

**Implementation**:
- OpenESPI-Common provides **basic management configuration** (disabled by default)
- Applications should add their own actuator configuration:
  ```yaml
  management:
    endpoints:
      web:
        exposure:
          include: health,info,metrics
    endpoint:
      health:
        show-details: when_authorized
  ```

### Profile Responsibility Separation
- **OpenESPI-Common**: Database, logging, ESPI-specific configuration
- **DataCustodian**: Web endpoints, actuator, utility-specific monitoring
- **ThirdParty**: Web endpoints, actuator, customer-specific monitoring

This migration provides a modern, scalable foundation for Spring Boot 3.5 deployment across all OpenESPI environments while maintaining backward compatibility with existing deployment scripts.