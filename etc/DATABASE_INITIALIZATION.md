# OpenESPI Database Initialization System

This document describes the comprehensive database initialization system for OpenESPI that works with Flyway migrations and Spring Boot 3.5 profiles.

## Overview

The OpenESPI database initialization system provides:

- **Multi-database support**: MySQL and PostgreSQL
- **Environment-specific configurations**: Development, Test, Production
- **Schema separation**: `openespi_usage`, `openespi_customer`, `openespi_tokenstore`
- **ESPI-compliant seed data**: Realistic Green Button test data
- **Flyway integration**: Compatible with existing migration scripts
- **Security**: Role-based database users with appropriate permissions

## Directory Structure

```
etc/
├── init/                           # Database and user creation scripts
│   ├── mysql/
│   │   ├── 01_create_databases.sql
│   │   └── 02_create_users.sql
│   └── postgresql/
│       ├── 01_create_databases.sql
│       └── 02_create_users.sql
├── seed/                           # ESPI-compliant test data
│   ├── mysql/
│   │   ├── 01_seed_application_information.sql
│   │   ├── 02_seed_retail_customers.sql
│   │   └── 03_seed_usage_points_and_readings.sql
│   └── postgresql/
│       └── 01_seed_application_information.sql
├── env/                            # Environment-specific scripts
│   └── dev/
│       ├── init-mysql-dev.sh
│       └── init-postgresql-dev.sh
├── cleanup/                        # Cleanup and rollback scripts
│   ├── mysql/
│   │   ├── cleanup_databases.sql
│   │   └── cleanup_data_only.sql
│   └── postgresql/
│       └── cleanup_databases.sql
└── DATABASE_INITIALIZATION.md     # This documentation
```

## Database Schema Design

### Schema Separation

The system uses three separate schemas:

1. **openespi_usage**: Energy usage data (Usage Points, Meter Readings, Interval Readings)
2. **openespi_customer**: Customer information and device data
3. **openespi_tokenstore**: OAuth 2.0 token storage

### Database Users

| User | Purpose | Permissions |
|------|---------|-------------|
| `openespi_dev` | Development | Full access to all schemas |
| `openespi_prod` | Production | SELECT, INSERT, UPDATE, DELETE |
| `openespi_readonly` | Analytics/Reporting | SELECT only |
| `openespi_migrate` | Flyway migrations | Full schema management |

## Quick Start

### Development Environment

#### MySQL
```bash
# Initialize MySQL development environment
./etc/env/dev/init-mysql-dev.sh

# With custom parameters
./etc/env/dev/init-mysql-dev.sh --host localhost --port 3306 --user root --password mypassword
```

#### PostgreSQL
```bash
# Initialize PostgreSQL development environment
./etc/env/dev/init-postgresql-dev.sh

# With custom parameters
./etc/env/dev/init-postgresql-dev.sh --host localhost --port 5432 --user postgres --password mypassword
```

## Manual Setup Process

### 1. Database Creation

#### MySQL
```bash
mysql -u root -p < etc/init/mysql/01_create_databases.sql
mysql -u root -p < etc/init/mysql/02_create_users.sql
```

#### PostgreSQL
```bash
psql -U postgres -f etc/init/postgresql/01_create_databases.sql
psql -U postgres -f etc/init/postgresql/02_create_users.sql
```

### 2. Schema Creation (Flyway)

```bash
# MySQL
mvn flyway:migrate -Dspring.profiles.active=dev-mysql -Dflyway.locations=classpath:db/migration/mysql

# PostgreSQL
mvn flyway:migrate -Dspring.profiles.active=dev-postgresql -Dflyway.locations=classpath:db/migration/postgresql
```

### 3. Seed Data Population

#### MySQL
```bash
mysql -u root -p < etc/seed/mysql/01_seed_application_information.sql
mysql -u root -p < etc/seed/mysql/02_seed_retail_customers.sql
mysql -u root -p < etc/seed/mysql/03_seed_usage_points_and_readings.sql
```

#### PostgreSQL
```bash
psql -U postgres -f etc/seed/postgresql/01_seed_application_information.sql
```

## Spring Boot 3.5 Integration

### Database Profiles

The initialization system supports the following Spring Boot profiles:

- `dev-mysql`: Development with MySQL
- `dev-postgresql`: Development with PostgreSQL
- `test-mysql`: Testing with MySQL
- `test-postgresql`: Testing with PostgreSQL
- `prod-mysql`: Production with MySQL
- `prod-postgresql`: Production with PostgreSQL

### Application Properties

#### MySQL Development (application-dev-mysql.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/openespi_usage
spring.datasource.username=openespi_dev
spring.datasource.password=openespi_dev_password
spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration/mysql
```

#### PostgreSQL Development (application-dev-postgresql.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/openespi_usage
spring.datasource.username=openespi_dev
spring.datasource.password=openespi_dev_password
spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration/postgresql
```

## ESPI-Compliant Test Data

The seed data includes:

### Application Information
- 5 sample third-party applications with OAuth 2.0 configurations
- Different application types: web, native, mobile
- Realistic redirect URIs and client credentials

### Retail Customers
- 10 diverse customer profiles:
  - Administrative user (Data Custodian)
  - High usage family home
  - Medium usage apartment
  - Low usage efficient home
  - Small business
  - Solar customer with net metering
  - Electric vehicle owner
  - Time-of-use customer
  - Multi-unit building manager
  - Test customer for development

### Usage Points and Readings
- Time configurations for different time zones
- Service delivery points with tariff information
- Reading types for energy and power measurements
- Usage points linked to customers
- Meter readings with realistic consumption patterns

## Environment-Specific Configurations

### Development
- Full access permissions
- Comprehensive seed data
- Debug logging enabled
- Local database connections

### Test
- Isolated test databases
- Minimal seed data
- Test-specific configurations
- In-memory or containerized databases

### Production
- Restricted user permissions
- No seed data (production data only)
- Security-hardened configurations
- External database connections

## Security Considerations

### Password Management
- Default passwords are provided for development only
- **MUST** be changed for production environments
- Use environment variables or secrets management

### User Permissions
- Principle of least privilege
- Separate users for different purposes
- Read-only access for analytics

### Network Security
- Database users restricted by host when possible
- SSL/TLS connections recommended for production
- Firewall rules for database access

## Cleanup and Rollback

### Complete Cleanup
```bash
# MySQL
mysql -u root -p < etc/cleanup/mysql/cleanup_databases.sql

# PostgreSQL
psql -U postgres -f etc/cleanup/postgresql/cleanup_databases.sql
```

### Data-Only Cleanup (Preserve Structure)
```bash
# MySQL
mysql -u root -p < etc/cleanup/mysql/cleanup_data_only.sql
```

## Troubleshooting

### Common Issues

#### Connection Failures
- Verify database server is running
- Check connection parameters (host, port, username, password)
- Ensure database user has connection privileges

#### Permission Errors
- Verify user has appropriate database permissions
- Check if user exists and has correct host restrictions
- Ensure schema/database exists before granting permissions

#### Flyway Migration Failures
- Check if baseline migration is needed
- Verify migration scripts are valid SQL
- Ensure database schema is clean before first migration

#### Seed Data Errors
- Run schema creation (Flyway) before seed data
- Check for foreign key constraint violations
- Verify character encoding (UTF-8) support

### Debugging

#### Enable SQL Logging
```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.flywaydb=DEBUG
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

#### Verify Database State
```sql
-- MySQL
SHOW DATABASES LIKE 'openespi_%';
SELECT User, Host FROM mysql.user WHERE User LIKE 'openespi_%';
SELECT COUNT(*) FROM openespi_usage.retail_customers;

-- PostgreSQL
\l openespi_*
\du openespi_*
SELECT COUNT(*) FROM usage.retail_customers;
```

## Integration with Legacy Scripts

The new initialization system is designed to replace the legacy scripts in `etc/` while maintaining compatibility:

### Legacy Script Mapping
- `initializedatabases.sh` → `./etc/env/dev/init-mysql-dev.sh`
- `reset.sh` → `./etc/cleanup/mysql/cleanup_data_only.sql` + seed scripts
- `cleandatabases_*.sql` → `./etc/cleanup/mysql/cleanup_databases.sql`

### Migration Path
1. Test new scripts in development environment
2. Verify data compatibility with existing applications
3. Update deployment scripts to use new initialization system
4. Remove legacy scripts after successful migration

## Future Enhancements

- **Docker Compose**: Complete containerized development environment
- **Kubernetes**: Helm charts for cloud deployment
- **Monitoring**: Database health checks and metrics
- **Backup**: Automated backup and restore procedures
- **Performance**: Indexing optimization and query analysis

## Support

For issues and questions:
- Review this documentation
- Check the troubleshooting section
- Examine log files for error details
- Consult the Green Button Alliance community resources