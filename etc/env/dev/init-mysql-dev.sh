#!/usr/bin/env bash
#
# OpenESPI Database Initialization - Development Environment (MySQL)
# 
# Copyright (c) 2018-2025 Green Button Alliance, Inc.
# Licensed under the Apache License, Version 2.0
#
# This script initializes the MySQL databases for development environment.
# It creates databases, users, and populates seed data for testing.

set -e  # Exit on any error
set -u  # Exit on undefined variables

# Configuration
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/../../../.." && pwd)"
INIT_DIR="${PROJECT_ROOT}/etc/init/mysql"
SEED_DIR="${PROJECT_ROOT}/etc/seed/mysql"

# MySQL connection parameters
MYSQL_HOST="${MYSQL_HOST:-localhost}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_ROOT_USER="${MYSQL_ROOT_USER:-root}"
MYSQL_ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD:-password}"

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

check_mysql_connection() {
    log_info "Checking MySQL connection..."
    if ! mysql --host="${MYSQL_HOST}" --port="${MYSQL_PORT}" --user="${MYSQL_ROOT_USER}" --password="${MYSQL_ROOT_PASSWORD}" -e "SELECT 1;" > /dev/null 2>&1; then
        log_error "Cannot connect to MySQL. Please check your connection parameters."
        log_error "Host: ${MYSQL_HOST}, Port: ${MYSQL_PORT}, User: ${MYSQL_ROOT_USER}"
        exit 1
    fi
    log_success "MySQL connection successful"
}

execute_sql_file() {
    local file_path="$1"
    local description="$2"
    
    log_info "Executing: ${description}"
    if mysql --host="${MYSQL_HOST}" --port="${MYSQL_PORT}" --user="${MYSQL_ROOT_USER}" --password="${MYSQL_ROOT_PASSWORD}" < "${file_path}"; then
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
    if mvn flyway:migrate -Dspring.profiles.active=dev-mysql -Dflyway.locations=classpath:db/migration/mysql; then
        log_success "Flyway migrations completed successfully"
    else
        log_error "Flyway migrations failed"
        exit 1
    fi
}

# Main execution
main() {
    log_info "Starting OpenESPI MySQL Development Environment Initialization"
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
    
    # Check MySQL connection
    check_mysql_connection
    
    # Step 1: Create databases
    execute_sql_file "${INIT_DIR}/01_create_databases.sql" "Creating databases"
    
    # Step 2: Create users
    execute_sql_file "${INIT_DIR}/02_create_users.sql" "Creating database users"
    
    # Step 3: Run Flyway migrations to create schema
    run_flyway_migration
    
    # Step 4: Populate seed data
    log_info "Populating seed data..."
    execute_sql_file "${SEED_DIR}/01_seed_application_information.sql" "Seeding application information"
    execute_sql_file "${SEED_DIR}/02_seed_retail_customers.sql" "Seeding retail customers"
    execute_sql_file "${SEED_DIR}/03_seed_usage_points_and_readings.sql" "Seeding usage points and readings"
    
    # Step 5: Verify installation
    log_info "Verifying installation..."
    local customer_count=$(mysql --host="${MYSQL_HOST}" --port="${MYSQL_PORT}" --user="${MYSQL_ROOT_USER}" --password="${MYSQL_ROOT_PASSWORD}" -N -e "SELECT COUNT(*) FROM openespi_usage.retail_customers;" 2>/dev/null || echo "0")
    local app_count=$(mysql --host="${MYSQL_HOST}" --port="${MYSQL_PORT}" --user="${MYSQL_ROOT_USER}" --password="${MYSQL_ROOT_PASSWORD}" -N -e "SELECT COUNT(*) FROM openespi_usage.application_information;" 2>/dev/null || echo "0")
    
    log_success "Database initialization completed successfully!"
    log_info "Summary:"
    log_info "  - Retail Customers: ${customer_count}"
    log_info "  - Applications: ${app_count}"
    log_info "  - Development user: openespi_dev (password: openespi_dev_password)"
    log_info "  - Database URLs:"
    log_info "    - Usage: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/openespi_usage"
    log_info "    - Customer: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/openespi_customer"
    log_info "    - TokenStore: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/openespi_tokenstore"
    
    log_warning "SECURITY NOTE: Change default passwords before using in production!"
}

# Show usage information
usage() {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -h, --help           Show this help message"
    echo "  -H, --host HOST      MySQL host (default: localhost)"
    echo "  -P, --port PORT      MySQL port (default: 3306)"
    echo "  -u, --user USER      MySQL root user (default: root)"
    echo "  -p, --password PASS  MySQL root password (default: password)"
    echo ""
    echo "Environment Variables:"
    echo "  MYSQL_HOST           MySQL host"
    echo "  MYSQL_PORT           MySQL port"
    echo "  MYSQL_ROOT_USER      MySQL root user"
    echo "  MYSQL_ROOT_PASSWORD  MySQL root password"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            usage
            exit 0
            ;;
        -H|--host)
            MYSQL_HOST="$2"
            shift 2
            ;;
        -P|--port)
            MYSQL_PORT="$2"
            shift 2
            ;;
        -u|--user)
            MYSQL_ROOT_USER="$2"
            shift 2
            ;;
        -p|--password)
            MYSQL_ROOT_PASSWORD="$2"
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