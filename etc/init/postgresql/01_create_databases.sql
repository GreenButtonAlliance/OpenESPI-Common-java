/*
 * OpenESPI Database Initialization - PostgreSQL Database Creation
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script creates the necessary databases and schemas for OpenESPI.
 * It sets up separate databases for usage data and customer data.
 * Run this script as a PostgreSQL superuser before executing Flyway migrations.
 */

-- Drop existing databases if they exist (use with caution)
DROP DATABASE IF EXISTS openespi_usage;
DROP DATABASE IF EXISTS openespi_customer;
DROP DATABASE IF EXISTS openespi_tokenstore;

-- Create new databases with proper encoding
CREATE DATABASE openespi_usage
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    TEMPLATE = template0;

CREATE DATABASE openespi_customer
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    TEMPLATE = template0;

CREATE DATABASE openespi_tokenstore
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    TEMPLATE = template0;

-- Connect to each database and create schemas
\c openespi_usage;

-- Create schemas in usage database
CREATE SCHEMA IF NOT EXISTS usage AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS audit AUTHORIZATION postgres;

-- Enable required extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

\c openespi_customer;

-- Create schemas in customer database
CREATE SCHEMA IF NOT EXISTS customer AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS audit AUTHORIZATION postgres;

-- Enable required extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

\c openespi_tokenstore;

-- Create schemas in tokenstore database
CREATE SCHEMA IF NOT EXISTS tokenstore AUTHORIZATION postgres;
CREATE SCHEMA IF NOT EXISTS audit AUTHORIZATION postgres;

-- Enable required extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Set timezone to UTC
SET timezone = 'UTC';

-- Show created databases
\l openespi_*