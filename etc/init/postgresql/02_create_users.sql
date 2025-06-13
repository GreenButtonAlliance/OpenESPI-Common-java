/*
 * OpenESPI Database Initialization - PostgreSQL User Creation
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script creates database users with appropriate permissions for OpenESPI.
 * It creates separate users for different environments and use cases.
 */

-- Create application users
-- Development user (full access)
DROP USER IF EXISTS openespi_dev;
CREATE USER openespi_dev WITH PASSWORD 'openespi_dev_password';

-- Production user (restricted access)
DROP USER IF EXISTS openespi_prod;
CREATE USER openespi_prod WITH PASSWORD 'CHANGE_THIS_IN_PRODUCTION';

-- Read-only user for reporting/analytics
DROP USER IF EXISTS openespi_readonly;
CREATE USER openespi_readonly WITH PASSWORD 'readonly_password';

-- Migration user (for Flyway)
DROP USER IF EXISTS openespi_migrate;
CREATE USER openespi_migrate WITH PASSWORD 'migrate_password';

-- Grant database-level permissions
-- Development user
GRANT CONNECT ON DATABASE openespi_usage TO openespi_dev;
GRANT CONNECT ON DATABASE openespi_customer TO openespi_dev;
GRANT CONNECT ON DATABASE openespi_tokenstore TO openespi_dev;

-- Production user
GRANT CONNECT ON DATABASE openespi_usage TO openespi_prod;
GRANT CONNECT ON DATABASE openespi_customer TO openespi_prod;
GRANT CONNECT ON DATABASE openespi_tokenstore TO openespi_prod;

-- Read-only user
GRANT CONNECT ON DATABASE openespi_usage TO openespi_readonly;
GRANT CONNECT ON DATABASE openespi_customer TO openespi_readonly;

-- Migration user
GRANT CONNECT ON DATABASE openespi_usage TO openespi_migrate;
GRANT CONNECT ON DATABASE openespi_customer TO openespi_migrate;
GRANT CONNECT ON DATABASE openespi_tokenstore TO openespi_migrate;

-- Grant schema-level permissions for usage database
\c openespi_usage;

-- Development user permissions
GRANT ALL PRIVILEGES ON SCHEMA usage TO openespi_dev;
GRANT ALL PRIVILEGES ON SCHEMA audit TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA usage TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA usage TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA usage TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT ALL ON TABLES TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT ALL ON SEQUENCES TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT ALL ON FUNCTIONS TO openespi_dev;

-- Production user permissions
GRANT USAGE ON SCHEMA usage TO openespi_prod;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA usage TO openespi_prod;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA usage TO openespi_prod;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO openespi_prod;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT USAGE, SELECT ON SEQUENCES TO openespi_prod;

-- Read-only user permissions
GRANT USAGE ON SCHEMA usage TO openespi_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA usage TO openespi_readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT SELECT ON TABLES TO openespi_readonly;

-- Migration user permissions
GRANT ALL PRIVILEGES ON SCHEMA usage TO openespi_migrate;
GRANT ALL PRIVILEGES ON SCHEMA audit TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA usage TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA usage TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA usage TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT ALL ON TABLES TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT ALL ON SEQUENCES TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA usage GRANT ALL ON FUNCTIONS TO openespi_migrate;

-- Grant schema-level permissions for customer database
\c openespi_customer;

-- Development user permissions
GRANT ALL PRIVILEGES ON SCHEMA customer TO openespi_dev;
GRANT ALL PRIVILEGES ON SCHEMA audit TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA customer TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA customer TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA customer TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT ALL ON TABLES TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT ALL ON SEQUENCES TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT ALL ON FUNCTIONS TO openespi_dev;

-- Production user permissions
GRANT USAGE ON SCHEMA customer TO openespi_prod;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA customer TO openespi_prod;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA customer TO openespi_prod;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO openespi_prod;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT USAGE, SELECT ON SEQUENCES TO openespi_prod;

-- Read-only user permissions
GRANT USAGE ON SCHEMA customer TO openespi_readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA customer TO openespi_readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT SELECT ON TABLES TO openespi_readonly;

-- Migration user permissions
GRANT ALL PRIVILEGES ON SCHEMA customer TO openespi_migrate;
GRANT ALL PRIVILEGES ON SCHEMA audit TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA customer TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA customer TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA customer TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT ALL ON TABLES TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT ALL ON SEQUENCES TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA customer GRANT ALL ON FUNCTIONS TO openespi_migrate;

-- Grant schema-level permissions for tokenstore database
\c openespi_tokenstore;

-- Development user permissions
GRANT ALL PRIVILEGES ON SCHEMA tokenstore TO openespi_dev;
GRANT ALL PRIVILEGES ON SCHEMA audit TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA tokenstore TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA tokenstore TO openespi_dev;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA tokenstore TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT ALL ON TABLES TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT ALL ON SEQUENCES TO openespi_dev;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT ALL ON FUNCTIONS TO openespi_dev;

-- Production user permissions
GRANT USAGE ON SCHEMA tokenstore TO openespi_prod;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA tokenstore TO openespi_prod;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA tokenstore TO openespi_prod;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO openespi_prod;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT USAGE, SELECT ON SEQUENCES TO openespi_prod;

-- Migration user permissions
GRANT ALL PRIVILEGES ON SCHEMA tokenstore TO openespi_migrate;
GRANT ALL PRIVILEGES ON SCHEMA audit TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA tokenstore TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA tokenstore TO openespi_migrate;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA tokenstore TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT ALL ON TABLES TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT ALL ON SEQUENCES TO openespi_migrate;
ALTER DEFAULT PRIVILEGES IN SCHEMA tokenstore GRANT ALL ON FUNCTIONS TO openespi_migrate;

-- Show created users
\du openespi_*