/*
 * OpenESPI Usage Schema - Quality and Summary Tables Migration (PostgreSQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for reading quality, usage summaries, and power quality data
 * following the NAESB ESPI 1.0 specification.
 *
 * PostgreSQL-specific implementation with proper data types and indexing.
 */

-- Set schema for usage data
SET search_path TO openespi_usage;

-- Reading Quality Table
-- Indicates the quality and validation status of readings
CREATE TABLE reading_qualities (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP(6) WITH TIME ZONE,
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Reading quality specific fields
    quality VARCHAR(50),
    
    -- Foreign key relationships
    interval_reading_id BIGINT REFERENCES interval_readings(id) ON DELETE CASCADE
);

-- Create indexes for reading_qualities
CREATE INDEX idx_reading_quality_uuid ON reading_qualities(uuid);
CREATE INDEX idx_reading_quality_interval_reading_id ON reading_qualities(interval_reading_id);
CREATE INDEX idx_reading_quality_quality ON reading_qualities(quality);
CREATE INDEX idx_reading_quality_created ON reading_qualities(created);
CREATE INDEX idx_reading_quality_updated ON reading_qualities(updated);

-- Related Links Table for Reading Qualities
CREATE TABLE reading_quality_related_links (
    reading_quality_id BIGINT NOT NULL REFERENCES reading_qualities(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_reading_quality_related_links ON reading_quality_related_links(reading_quality_id);

-- Usage Summary Table
-- Summary information for usage over a period of time
CREATE TABLE usage_summaries (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP(6) WITH TIME ZONE,
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
    usage_point_id BIGINT REFERENCES usage_points(id) ON DELETE CASCADE
);

-- Create indexes for usage_summaries
CREATE INDEX idx_usage_summary_uuid ON usage_summaries(uuid);
CREATE INDEX idx_usage_summary_usage_point_id ON usage_summaries(usage_point_id);
CREATE INDEX idx_usage_summary_billing_period_start ON usage_summaries(billing_period_start);
CREATE INDEX idx_usage_summary_created ON usage_summaries(created);
CREATE INDEX idx_usage_summary_updated ON usage_summaries(updated);

-- Related Links Table for Usage Summaries
CREATE TABLE usage_summary_related_links (
    usage_summary_id BIGINT NOT NULL REFERENCES usage_summaries(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_usage_summary_related_links ON usage_summary_related_links(usage_summary_id);

-- Electric Power Quality Summary Table
-- Summary of electric power quality measurements
CREATE TABLE electric_power_quality_summaries (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP(6) WITH TIME ZONE,
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
    usage_point_id BIGINT REFERENCES usage_points(id) ON DELETE CASCADE
);

-- Create indexes for electric_power_quality_summaries
CREATE INDEX idx_epqs_uuid ON electric_power_quality_summaries(uuid);
CREATE INDEX idx_epqs_usage_point_id ON electric_power_quality_summaries(usage_point_id);
CREATE INDEX idx_epqs_summary_interval_start ON electric_power_quality_summaries(summary_interval_start);
CREATE INDEX idx_epqs_created ON electric_power_quality_summaries(created);
CREATE INDEX idx_epqs_updated ON electric_power_quality_summaries(updated);

-- Related Links Table for Electric Power Quality Summaries
CREATE TABLE electric_power_quality_summary_related_links (
    electric_power_quality_summary_id BIGINT NOT NULL REFERENCES electric_power_quality_summaries(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_epqs_related_links ON electric_power_quality_summary_related_links(electric_power_quality_summary_id);

-- Subscription Table
-- Manages subscriptions for data notifications and updates
CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP(6) WITH TIME ZONE,
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Subscription specific fields
    has_related_href VARCHAR(1024),
    notification_uri VARCHAR(1024),
    last_update TIMESTAMP(6) WITH TIME ZONE,
    
    -- Foreign key relationships
    application_information_id BIGINT REFERENCES application_information(id) ON DELETE CASCADE,
    retail_customer_id BIGINT REFERENCES retail_customers(id) ON DELETE CASCADE,
    usage_point_id BIGINT REFERENCES usage_points(id) ON DELETE CASCADE
);

-- Create indexes for subscriptions
CREATE INDEX idx_subscription_uuid ON subscriptions(uuid);
CREATE INDEX idx_subscription_app_id ON subscriptions(application_information_id);
CREATE INDEX idx_subscription_customer_id ON subscriptions(retail_customer_id);
CREATE INDEX idx_subscription_usage_point_id ON subscriptions(usage_point_id);
CREATE INDEX idx_subscription_has_related_href ON subscriptions(has_related_href);
CREATE INDEX idx_subscription_created ON subscriptions(created);
CREATE INDEX idx_subscription_updated ON subscriptions(updated);
CREATE INDEX idx_subscription_last_update ON subscriptions(last_update);

-- Related Links Table for Subscriptions
CREATE TABLE subscription_related_links (
    subscription_id BIGINT NOT NULL REFERENCES subscriptions(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_subscription_related_links ON subscription_related_links(subscription_id);

-- Time Configuration Table
-- Configuration for time zones and daylight saving time
CREATE TABLE time_configurations (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP(6) WITH TIME ZONE,
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Time configuration specific fields
    dst_end_rule BYTEA,
    dst_offset BIGINT,
    dst_start_rule BYTEA,
    tz_offset BIGINT
);

-- Create indexes for time_configurations
CREATE INDEX idx_time_config_uuid ON time_configurations(uuid);
CREATE INDEX idx_time_config_created ON time_configurations(created);
CREATE INDEX idx_time_config_updated ON time_configurations(updated);

-- Related Links Table for Time Configurations
CREATE TABLE time_configuration_related_links (
    time_configuration_id BIGINT NOT NULL REFERENCES time_configurations(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_time_config_related_links ON time_configuration_related_links(time_configuration_id);

-- Batch List Table
-- Manages batch operations for data processing
CREATE TABLE batch_lists (
    id BIGSERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    uuid_msb BIGINT,
    uuid_lsb BIGINT,
    description VARCHAR(255),
    created TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published TIMESTAMP(6) WITH TIME ZONE,
    up_link_rel VARCHAR(255),
    up_link_href VARCHAR(1024),
    self_link_rel VARCHAR(255),
    self_link_href VARCHAR(1024),
    
    -- Batch list specific fields (resources stored as JSON)
    resources JSONB
);

-- Create indexes for batch_lists
CREATE INDEX idx_batch_list_uuid ON batch_lists(uuid);
CREATE INDEX idx_batch_list_created ON batch_lists(created);
CREATE INDEX idx_batch_list_updated ON batch_lists(updated);
-- PostgreSQL JSONB index for efficient JSON queries
CREATE INDEX idx_batch_list_resources ON batch_lists USING GIN (resources);

-- Related Links Table for Batch Lists
CREATE TABLE batch_list_related_links (
    batch_list_id BIGINT NOT NULL REFERENCES batch_lists(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_batch_list_related_links ON batch_list_related_links(batch_list_id);

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_reading_qualities_updated BEFORE UPDATE ON reading_qualities FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_usage_summaries_updated BEFORE UPDATE ON usage_summaries FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_electric_power_quality_summaries_updated BEFORE UPDATE ON electric_power_quality_summaries FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_subscriptions_updated BEFORE UPDATE ON subscriptions FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_time_configurations_updated BEFORE UPDATE ON time_configurations FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_batch_lists_updated BEFORE UPDATE ON batch_lists FOR EACH ROW EXECUTE FUNCTION update_updated_column();