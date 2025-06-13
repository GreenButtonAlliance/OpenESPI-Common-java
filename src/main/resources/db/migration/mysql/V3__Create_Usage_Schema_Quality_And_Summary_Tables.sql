/*
 * OpenESPI Usage Schema - Quality and Summary Tables Migration
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for reading quality, usage summaries, and power quality data
 * following the NAESB ESPI 1.0 specification.
 */

-- Set schema for usage data
USE openespi_usage;

-- Reading Quality Table
-- Indicates the quality and validation status of readings
CREATE TABLE reading_qualities (
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
    
    -- Reading quality specific fields
    quality VARCHAR(50),
    
    -- Foreign key relationships
    interval_reading_id BIGINT,
    
    FOREIGN KEY (interval_reading_id) REFERENCES interval_readings(id) ON DELETE CASCADE,
    
    INDEX idx_reading_quality_uuid (uuid),
    INDEX idx_reading_quality_interval_reading_id (interval_reading_id),
    INDEX idx_reading_quality_quality (quality),
    INDEX idx_reading_quality_created (created),
    INDEX idx_reading_quality_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Reading Qualities
CREATE TABLE reading_quality_related_links (
    reading_quality_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (reading_quality_id) REFERENCES reading_qualities(id) ON DELETE CASCADE,
    INDEX idx_reading_quality_related_links (reading_quality_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usage Summary Table
-- Summary information for usage over a period of time
CREATE TABLE usage_summaries (
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
    
    -- Usage summary specific fields
    billing_period_start BIGINT,
    billing_period_duration BIGINT,
    bill_last_period BIGINT,
    bill_to_date BIGINT,
    cost_additional_last_period BIGINT,
    currency VARCHAR(10),
    quality_of_reading VARCHAR(50),
    status_time_stamp BIGINT,
    
    -- Foreign key relationships
    usage_point_id BIGINT,
    
    FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
    
    INDEX idx_usage_summary_uuid (uuid),
    INDEX idx_usage_summary_usage_point_id (usage_point_id),
    INDEX idx_usage_summary_billing_period_start (billing_period_start),
    INDEX idx_usage_summary_created (created),
    INDEX idx_usage_summary_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Usage Summaries
CREATE TABLE usage_summary_related_links (
    usage_summary_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (usage_summary_id) REFERENCES usage_summaries(id) ON DELETE CASCADE,
    INDEX idx_usage_summary_related_links (usage_summary_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Electric Power Quality Summary Table
-- Summary of electric power quality measurements
CREATE TABLE electric_power_quality_summaries (
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
    
    -- Electric power quality specific fields
    flicker_plt BIGINT,
    flicker_pst BIGINT,
    harmonic_voltage BIGINT,
    long_interruptions BIGINT,
    mains_voltage BIGINT,
    measurement_protocol SMALLINT,
    power_frequency BIGINT,
    rapid_voltage_changes BIGINT,
    short_interruptions BIGINT,
    summary_interval_start BIGINT,
    summary_interval_duration BIGINT,
    supply_voltage_dips BIGINT,
    supply_voltage_imbalance BIGINT,
    supply_voltage_variations BIGINT,
    temp_overvoltage BIGINT,
    
    -- Foreign key relationships
    usage_point_id BIGINT,
    
    FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
    
    INDEX idx_epqs_uuid (uuid),
    INDEX idx_epqs_usage_point_id (usage_point_id),
    INDEX idx_epqs_summary_interval_start (summary_interval_start),
    INDEX idx_epqs_created (created),
    INDEX idx_epqs_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Electric Power Quality Summaries
CREATE TABLE electric_power_quality_summary_related_links (
    electric_power_quality_summary_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (electric_power_quality_summary_id) REFERENCES electric_power_quality_summaries(id) ON DELETE CASCADE,
    INDEX idx_epqs_related_links (electric_power_quality_summary_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Subscription Table
-- Manages subscriptions for data notifications and updates
CREATE TABLE subscriptions (
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
    
    -- Subscription specific fields
    has_related_href VARCHAR(1024),
    notification_uri VARCHAR(1024),
    last_update DATETIME(6),
    
    -- Foreign key relationships
    application_information_id BIGINT,
    retail_customer_id BIGINT,
    usage_point_id BIGINT,
    
    FOREIGN KEY (application_information_id) REFERENCES application_information(id) ON DELETE CASCADE,
    FOREIGN KEY (retail_customer_id) REFERENCES retail_customers(id) ON DELETE CASCADE,
    FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
    
    INDEX idx_subscription_uuid (uuid),
    INDEX idx_subscription_app_id (application_information_id),
    INDEX idx_subscription_customer_id (retail_customer_id),
    INDEX idx_subscription_usage_point_id (usage_point_id),
    INDEX idx_subscription_has_related_href (has_related_href(255)),
    INDEX idx_subscription_created (created),
    INDEX idx_subscription_updated (updated),
    INDEX idx_subscription_last_update (last_update)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Subscriptions
CREATE TABLE subscription_related_links (
    subscription_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id) ON DELETE CASCADE,
    INDEX idx_subscription_related_links (subscription_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Time Configuration Table
-- Configuration for time zones and daylight saving time
CREATE TABLE time_configurations (
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
    
    -- Time configuration specific fields
    dst_end_rule BLOB,
    dst_offset BIGINT,
    dst_start_rule BLOB,
    tz_offset BIGINT,
    
    INDEX idx_time_config_uuid (uuid),
    INDEX idx_time_config_created (created),
    INDEX idx_time_config_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Time Configurations
CREATE TABLE time_configuration_related_links (
    time_configuration_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (time_configuration_id) REFERENCES time_configurations(id) ON DELETE CASCADE,
    INDEX idx_time_config_related_links (time_configuration_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Batch List Table
-- Manages batch operations for data processing
CREATE TABLE batch_lists (
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
    
    -- Batch list specific fields (resources stored as JSON or serialized data)
    resources LONGTEXT,
    
    INDEX idx_batch_list_uuid (uuid),
    INDEX idx_batch_list_created (created),
    INDEX idx_batch_list_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Batch Lists
CREATE TABLE batch_list_related_links (
    batch_list_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (batch_list_id) REFERENCES batch_lists(id) ON DELETE CASCADE,
    INDEX idx_batch_list_related_links (batch_list_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;