/*
 * OpenESPI Data Cleanup - MySQL (Structure Preserved)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script cleans up only the data from OpenESPI tables,
 * preserving the database structure for re-seeding.
 */

USE openespi_usage;

-- Disable foreign key checks for truncation
SET FOREIGN_KEY_CHECKS = 0;

-- Clear all data from usage schema tables
TRUNCATE TABLE reading_qualities;
TRUNCATE TABLE interval_readings;
TRUNCATE TABLE interval_blocks;
TRUNCATE TABLE meter_reading_related_links;
TRUNCATE TABLE meter_readings;
TRUNCATE TABLE reading_types;
TRUNCATE TABLE usage_point_related_links;
TRUNCATE TABLE usage_points;
TRUNCATE TABLE service_delivery_point_related_links;
TRUNCATE TABLE service_delivery_points;
TRUNCATE TABLE time_configurations;
TRUNCATE TABLE subscription_usage_points;
TRUNCATE TABLE subscriptions;
TRUNCATE TABLE authorization_related_links;
TRUNCATE TABLE authorizations;
TRUNCATE TABLE retail_customer_related_links;
TRUNCATE TABLE retail_customers;
TRUNCATE TABLE application_information_related_links;
TRUNCATE TABLE application_information;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Reset auto-increment counters
ALTER TABLE application_information AUTO_INCREMENT = 1;
ALTER TABLE retail_customers AUTO_INCREMENT = 1;
ALTER TABLE authorizations AUTO_INCREMENT = 1;
ALTER TABLE service_delivery_points AUTO_INCREMENT = 1;
ALTER TABLE time_configurations AUTO_INCREMENT = 1;
ALTER TABLE usage_points AUTO_INCREMENT = 1;
ALTER TABLE reading_types AUTO_INCREMENT = 1;
ALTER TABLE meter_readings AUTO_INCREMENT = 1;
ALTER TABLE interval_blocks AUTO_INCREMENT = 1;
ALTER TABLE interval_readings AUTO_INCREMENT = 1;
ALTER TABLE reading_qualities AUTO_INCREMENT = 1;
ALTER TABLE subscriptions AUTO_INCREMENT = 1;

-- Switch to customer schema (if exists)
USE openespi_customer;

-- Clear customer schema tables (structure depends on migration files)
-- Add table truncation commands here when customer schema is implemented

-- Switch to tokenstore schema
USE openespi_tokenstore;

-- Clear tokenstore schema tables (OAuth2 token storage)
-- Add table truncation commands here when tokenstore schema is implemented

SELECT 'Data cleanup completed successfully' AS status;