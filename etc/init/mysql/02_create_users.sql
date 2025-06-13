/*
 * OpenESPI Database Initialization - MySQL User Creation
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script creates database users with appropriate permissions for OpenESPI.
 * It creates separate users for different environments and use cases.
 */

-- Create application users
-- Development user (full access)
DROP USER IF EXISTS 'openespi_dev'@'localhost';
CREATE USER 'openespi_dev'@'localhost' IDENTIFIED BY 'openespi_dev_password';

-- Production user (restricted access)
DROP USER IF EXISTS 'openespi_prod'@'%';
CREATE USER 'openespi_prod'@'%' IDENTIFIED BY 'CHANGE_THIS_IN_PRODUCTION';

-- Read-only user for reporting/analytics
DROP USER IF EXISTS 'openespi_readonly'@'%';
CREATE USER 'openespi_readonly'@'%' IDENTIFIED BY 'readonly_password';

-- Migration user (for Flyway)
DROP USER IF EXISTS 'openespi_migrate'@'localhost';
CREATE USER 'openespi_migrate'@'localhost' IDENTIFIED BY 'migrate_password';

-- Grant permissions for development user
GRANT ALL PRIVILEGES ON openespi_usage.* TO 'openespi_dev'@'localhost';
GRANT ALL PRIVILEGES ON openespi_customer.* TO 'openespi_dev'@'localhost';
GRANT ALL PRIVILEGES ON openespi_tokenstore.* TO 'openespi_dev'@'localhost';

-- Grant permissions for production user
GRANT SELECT, INSERT, UPDATE, DELETE ON openespi_usage.* TO 'openespi_prod'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON openespi_customer.* TO 'openespi_prod'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON openespi_tokenstore.* TO 'openespi_prod'@'%';

-- Grant permissions for read-only user
GRANT SELECT ON openespi_usage.* TO 'openespi_readonly'@'%';
GRANT SELECT ON openespi_customer.* TO 'openespi_readonly'@'%';

-- Grant permissions for migration user
GRANT ALL PRIVILEGES ON openespi_usage.* TO 'openespi_migrate'@'localhost';
GRANT ALL PRIVILEGES ON openespi_customer.* TO 'openespi_migrate'@'localhost';
GRANT ALL PRIVILEGES ON openespi_tokenstore.* TO 'openespi_migrate'@'localhost';

-- Flush privileges to ensure changes take effect
FLUSH PRIVILEGES;

-- Show created users
SELECT User, Host FROM mysql.user WHERE User LIKE 'openespi_%';