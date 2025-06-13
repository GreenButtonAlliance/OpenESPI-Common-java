/*
 * OpenESPI Database Initialization - MySQL Database Creation
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script creates the necessary databases and users for OpenESPI.
 * It sets up separate schemas for usage data and customer data.
 * Run this script before executing Flyway migrations.
 */

-- Drop existing databases if they exist (use with caution)
DROP DATABASE IF EXISTS openespi_usage;
DROP DATABASE IF EXISTS openespi_customer;
DROP DATABASE IF EXISTS openespi_tokenstore;

-- Legacy database cleanup (for backward compatibility)
DROP DATABASE IF EXISTS datacustodian;
DROP DATABASE IF EXISTS thirdparty;
DROP DATABASE IF EXISTS tokenstore;

-- Create new databases with proper character set
CREATE DATABASE openespi_usage 
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE DATABASE openespi_customer 
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE DATABASE openespi_tokenstore 
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;

-- Show created databases
SHOW DATABASES LIKE 'openespi_%';

-- Set global settings for better performance and security
SET GLOBAL sql_mode = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
SET GLOBAL innodb_buffer_pool_size = 268435456; -- 256MB
SET GLOBAL innodb_log_file_size = 67108864; -- 64MB