/*
 * OpenESPI Usage Schema - Meter and Reading Tables Migration
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for meter readings, usage points, and related measurement data
 * following the NAESB ESPI 1.0 specification for energy usage data.
 */

-- Set schema for usage data
USE openespi_usage;

-- Usage Point Table
-- Logical points on a network where consumption or production is measured
CREATE TABLE usage_points (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    published DATETIME(6),
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Usage point specific fields
    role_flags BLOB,
    service_category_kind VARCHAR(50),
    status SMALLINT,
    
    -- Foreign key relationships
    retail_customer_id BIGINT,
    service_delivery_point_id BIGINT,
    
    FOREIGN KEY (retail_customer_id) REFERENCES retail_customers(id) ON DELETE CASCADE,
    FOREIGN KEY (service_delivery_point_id) REFERENCES service_delivery_points(id) ON DELETE SET NULL,
    
    INDEX idx_usage_point_uuid (uuid),
    INDEX idx_usage_point_customer_id (retail_customer_id),
    INDEX idx_usage_point_sdp_id (service_delivery_point_id),
    INDEX idx_usage_point_service_category (service_category_kind),
    INDEX idx_usage_point_status (status),
    INDEX idx_usage_point_created (created),
    INDEX idx_usage_point_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Usage Points
CREATE TABLE usage_point_related_links (
    usage_point_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
    INDEX idx_usage_point_related_links (usage_point_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Reading Type Table
-- Defines the characteristics of readings (e.g., energy, power, demand)
CREATE TABLE reading_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    published DATETIME(6),
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Reading type specific fields
    accumulation_behaviour VARCHAR(50),
    commodity VARCHAR(50),
    consumption_tier SMALLINT,
    currency VARCHAR(10),
    data_qualifier VARCHAR(50),
    default_quality VARCHAR(50),
    flow_direction VARCHAR(50),
    interval_length BIGINT,
    kind VARCHAR(50),
    phase VARCHAR(50),
    power_of_ten_multiplier SMALLINT,
    time_attribute VARCHAR(50),
    uom VARCHAR(50),
    cpp SMALLINT,
    interharmonic SMALLINT,
    measuring_period VARCHAR(50),
    tou SMALLINT,
    argument SMALLINT,
    
    INDEX idx_reading_type_uuid (uuid),
    INDEX idx_reading_type_kind (kind),
    INDEX idx_reading_type_commodity (commodity),
    INDEX idx_reading_type_uom (uom),
    INDEX idx_reading_type_created (created),
    INDEX idx_reading_type_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Reading Types
CREATE TABLE reading_type_related_links (
    reading_type_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (reading_type_id) REFERENCES reading_types(id) ON DELETE CASCADE,
    INDEX idx_reading_type_related_links (reading_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Meter Reading Table
-- Contains time series of readings for a specific usage point
CREATE TABLE meter_readings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    published DATETIME(6),
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Foreign key relationships
    usage_point_id BIGINT,
    reading_type_id BIGINT,
    
    FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
    FOREIGN KEY (reading_type_id) REFERENCES reading_types(id) ON DELETE SET NULL,
    
    INDEX idx_meter_reading_uuid (uuid),
    INDEX idx_meter_reading_usage_point_id (usage_point_id),
    INDEX idx_meter_reading_reading_type_id (reading_type_id),
    INDEX idx_meter_reading_created (created),
    INDEX idx_meter_reading_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Meter Readings
CREATE TABLE meter_reading_related_links (
    meter_reading_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (meter_reading_id) REFERENCES meter_readings(id) ON DELETE CASCADE,
    INDEX idx_meter_reading_related_links (meter_reading_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Interval Block Table
-- Time sequence of readings of the same reading type
CREATE TABLE interval_blocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    published DATETIME(6),
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Interval block specific fields
    interval_start BIGINT,
    interval_duration BIGINT,
    
    -- Foreign key relationships
    meter_reading_id BIGINT,
    
    FOREIGN KEY (meter_reading_id) REFERENCES meter_readings(id) ON DELETE CASCADE,
    
    INDEX idx_interval_block_uuid (uuid),
    INDEX idx_interval_block_meter_reading_id (meter_reading_id),
    INDEX idx_interval_block_interval_start (interval_start),
    INDEX idx_interval_block_created (created),
    INDEX idx_interval_block_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Interval Blocks
CREATE TABLE interval_block_related_links (
    interval_block_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (interval_block_id) REFERENCES interval_blocks(id) ON DELETE CASCADE,
    INDEX idx_interval_block_related_links (interval_block_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Interval Reading Table
-- Individual reading values within an interval block
CREATE TABLE interval_readings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    published DATETIME(6),
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Interval reading specific fields
    cost BIGINT,
    value BIGINT,
    time_period_start BIGINT,
    time_period_duration BIGINT,
    consumption_tier SMALLINT,
    tou SMALLINT,
    cpp SMALLINT,
    
    -- Foreign key relationships
    interval_block_id BIGINT,
    
    FOREIGN KEY (interval_block_id) REFERENCES interval_blocks(id) ON DELETE CASCADE,
    
    INDEX idx_interval_reading_uuid (uuid),
    INDEX idx_interval_reading_interval_block_id (interval_block_id),
    INDEX idx_interval_reading_time_period_start (time_period_start),
    INDEX idx_interval_reading_value (value),
    INDEX idx_interval_reading_created (created),
    INDEX idx_interval_reading_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Interval Readings
CREATE TABLE interval_reading_related_links (
    interval_reading_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (interval_reading_id) REFERENCES interval_readings(id) ON DELETE CASCADE,
    INDEX idx_interval_reading_related_links (interval_reading_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;