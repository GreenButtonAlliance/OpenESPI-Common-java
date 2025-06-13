# OpenESPI Database Scripts

This directory contains database initialization, migration, and management scripts for the OpenESPI project.

## 🚀 Quick Start

### For Development (MySQL)
```bash
./env/dev/init-mysql-dev.sh
```

### For Development (PostgreSQL)
```bash
./env/dev/init-postgresql-dev.sh
```

## 📁 Directory Structure

- **`init/`** - Database and user creation scripts
- **`seed/`** - ESPI-compliant test data
- **`env/`** - Environment-specific initialization scripts
- **`cleanup/`** - Database cleanup and rollback scripts
- **`DATABASE_INITIALIZATION.md`** - Complete documentation

## 🔧 What's New

This modernized initialization system provides:

✅ **Multi-database support** (MySQL & PostgreSQL)  
✅ **Spring Boot 3.5 compatibility**  
✅ **Flyway migration integration**  
✅ **Schema separation** (usage, customer, tokenstore)  
✅ **ESPI-compliant seed data**  
✅ **Environment-specific configurations**  
✅ **Security-hardened database users**  
✅ **Automated initialization scripts**  

## 🗃️ Database Schemas

| Schema | Purpose |
|--------|---------|
| `openespi_usage` | Energy usage data (Usage Points, Meter Readings) |
| `openespi_customer` | Customer information and device data |
| `openespi_tokenstore` | OAuth 2.0 token storage |

## 👥 Database Users

| User | Purpose | Environment |
|------|---------|-------------|
| `openespi_dev` | Development | Dev/Test |
| `openespi_prod` | Production | Production |
| `openespi_readonly` | Analytics | All |
| `openespi_migrate` | Flyway migrations | All |

## 📊 Seed Data Includes

- **5 Third-party applications** with OAuth 2.0 configs
- **10 Diverse retail customers** (residential, commercial, solar, EV)
- **Usage points and readings** with realistic consumption patterns
- **Time configurations** for different time zones
- **ESPI-compliant data structures** following Green Button standards

## 🔄 Migration from Legacy Scripts

| Legacy Script | New Equivalent |
|---------------|----------------|
| `initializedatabases.sh` | `./env/dev/init-mysql-dev.sh` |
| `reset.sh` | `./cleanup/mysql/cleanup_data_only.sql` + seed scripts |
| `cleandatabases_*.sql` | `./cleanup/mysql/cleanup_databases.sql` |

## 📖 Full Documentation

See [DATABASE_INITIALIZATION.md](DATABASE_INITIALIZATION.md) for complete documentation including:

- Detailed setup instructions
- Spring Boot integration
- Security considerations
- Troubleshooting guide
- Environment configurations

## ⚠️ Important Notes

1. **Default passwords** are for development only - change for production!
2. **Run Flyway migrations** after database creation and before seed data
3. **Backup production data** before running cleanup scripts
4. **Review permissions** when setting up production environments

## 🆘 Need Help?

1. Check [DATABASE_INITIALIZATION.md](DATABASE_INITIALIZATION.md)
2. Review the troubleshooting section
3. Examine log files for errors
4. Consult Green Button Alliance community resources