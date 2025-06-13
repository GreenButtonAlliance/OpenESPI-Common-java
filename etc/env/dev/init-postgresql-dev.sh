#!/usr/bin/env bash
#
# OpenESPI Database Initialization - Development Environment (PostgreSQL)
# 
# Copyright (c) 2018-2025 Green Button Alliance, Inc.
# Licensed under the Apache License, Version 2.0
#
# This script initializes the PostgreSQL databases for development environment.
# It creates databases, users, and populates seed data for testing.

set -e  # Exit on any error
set -u  # Exit on undefined variables

# Configuration
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/../../../.." && pwd)"
INIT_DIR="${PROJECT_ROOT}/etc/init/postgresql"
SEED_DIR="${PROJECT_ROOT}/etc/seed/postgresql"

# PostgreSQL connection parameters
POSTGRES_HOST="${POSTGRES_HOST:-localhost}"
POSTGRES_PORT="${POSTGRES_PORT:-5432}"
POSTGRES_USER="${POSTGRES_USER:-postgres}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD:-postgres}"
POSTGRES_DB="${POSTGRES_DB:-postgres}"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

check_postgresql_connection() {
    log_info "Checking PostgreSQL connection..."
    export PGPASSWORD="${POSTGRES_PASSWORD}"
    if ! psql -h "${POSTGRES_HOST}" -p "${POSTGRES_PORT}" -U "${POSTGRES_USER}" -d "${POSTGRES_DB}" -c "SELECT 1;" > /dev/null 2>&1; then
        log_error "Cannot connect to PostgreSQL. Please check your connection parameters."
        log_error "Host: ${POSTGRES_HOST}, Port: ${POSTGRES_PORT}, User: ${POSTGRES_USER}, Database: ${POSTGRES_DB}"
        exit 1
    fi
    log_success "PostgreSQL connection successful"
}

execute_sql_file() {
    local file_path="$1"
    local description="$2"
    
    log_info "Executing: ${description}"
    export PGPASSWORD="${POSTGRES_PASSWORD}"
    if psql -h "${POSTGRES_HOST}" -p "${POSTGRES_PORT}" -U "${POSTGRES_USER}" -d "${POSTGRES_DB}" -f "${file_path}"; then
        log_success "Completed: ${description}"
    else
        log_error "Failed: ${description}"
        exit 1
    fi
}

run_flyway_migration() {
    log_info "Running Flyway migrations..."
    
    # Check if Maven is available
    if ! command -v mvn &> /dev/null; then
        log_error "Maven is required for Flyway migrations but not found in PATH"
        exit 1
    fi
    
    # Run Flyway migration from project root
    cd "${PROJECT_ROOT}"
    if mvn flyway:migrate -Dspring.profiles.active=dev-postgresql -Dflyway.locations=classpath:db/migration/postgresql; then
        log_success "Flyway migrations completed successfully"
    else
        log_error "Flyway migrations failed"
        exit 1
    fi
}

# Main execution
main() {
    log_info "Starting OpenESPI PostgreSQL Development Environment Initialization"
    log_info "Project Root: ${PROJECT_ROOT}"
    
    # Check if required directories exist
    if [[ ! -d "${INIT_DIR}" ]]; then
        log_error "Initialization directory not found: ${INIT_DIR}"
        exit 1
    fi
    
    if [[ ! -d "${SEED_DIR}" ]]; then
        log_error "Seed data directory not found: ${SEED_DIR}"
        exit 1
    fi
    
    # Check PostgreSQL connection
    check_postgresql_connection
    
    # Step 1: Create databases
    execute_sql_file "${INIT_DIR}/01_create_databases.sql" "Creating databases"
    
    # Step 2: Create users
    execute_sql_file "${INIT_DIR}/02_create_users.sql" "Creating database users"
    
    # Step 3: Run Flyway migrations to create schema
    run_flyway_migration
    
    # Step 4: Populate seed data
    log_info "Populating seed data..."
    execute_sql_file "${SEED_DIR}/01_seed_application_information.sql" "Seeding application information"
    
    # Step 5: Verify installation
    log_info "Verifying installation..."
    export PGPASSWORD="${POSTGRES_PASSWORD}"
    local customer_count=$(psql -h "${POSTGRES_HOST}" -p "${POSTGRES_PORT}" -U "${POSTGRES_USER}" -d "openespi_usage" -t -c "SELECT COUNT(*) FROM usage.retail_customers;" 2>/dev/null | xargs || echo "0")
    local app_count=$(psql -h "${POSTGRES_HOST}" -p "${POSTGRES_PORT}" -U "${POSTGRES_USER}" -d "openespi_usage" -t -c "SELECT COUNT(*) FROM usage.application_information;" 2>/dev/null | xargs || echo "0")
    
    log_success "Database initialization completed successfully!"
    log_info "Summary:"
    log_info "  - Retail Customers: ${customer_count}"
    log_info "  - Applications: ${app_count}"
    log_info "  - Development user: openespi_dev (password: openespi_dev_password)"
    log_info "  - Database URLs:"
    log_info "    - Usage: jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/openespi_usage"
    log_info "    - Customer: jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/openespi_customer"
    log_info "    - TokenStore: jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/openespi_tokenstore"
    
    log_warning "SECURITY NOTE: Change default passwords before using in production!"
}

# Show usage information
usage() {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -h, --help           Show this help message"
    echo "  -H, --host HOST      PostgreSQL host (default: localhost)"
    echo "  -P, --port PORT      PostgreSQL port (default: 5432)"
    echo "  -u, --user USER      PostgreSQL user (default: postgres)"
    echo "  -p, --password PASS  PostgreSQL password (default: postgres)"
    echo "  -d, --database DB    PostgreSQL database (default: postgres)"
    echo ""
    echo "Environment Variables:"
    echo "  POSTGRES_HOST        PostgreSQL host"
    echo "  POSTGRES_PORT        PostgreSQL port"
    echo "  POSTGRES_USER        PostgreSQL user"
    echo "  POSTGRES_PASSWORD    PostgreSQL password"
    echo "  POSTGRES_DB          PostgreSQL database"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            usage
            exit 0
            ;;
        -H|--host)
            POSTGRES_HOST="$2"
            shift 2
            ;;
        -P|--port)
            POSTGRES_PORT="$2"
            shift 2
            ;;
        -u|--user)
            POSTGRES_USER="$2"
            shift 2
            ;;
        -p|--password)
            POSTGRES_PASSWORD="$2"
            shift 2
            ;;
        -d|--database)
            POSTGRES_DB="$2"
            shift 2
            ;;
        *)
            log_error "Unknown option: $1"
            usage
            exit 1
            ;;
    esac
done

# Run main function
main "$@"