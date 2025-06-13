/*
 * OpenESPI Usage Schema - Meter and Reading Tables Migration (PostgreSQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for meter readings, usage points, and related measurement data
 * following the NAESB ESPI 1.0 specification for energy usage data.
 *
 * PostgreSQL-specific implementation with proper data types and indexing.
 */

-- Set schema for usage data
SET search_path TO openespi_usage;

-- Usage Point Table
-- Logical points on a network where consumption or production is measured
CREATE TABLE usage_points (
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
    
    -- Usage point specific fields
    role_flags BYTEA,
    service_category_kind VARCHAR(50),
    status SMALLINT,
    
    -- Foreign key relationships
    retail_customer_id BIGINT REFERENCES retail_customers(id) ON DELETE CASCADE,
    service_delivery_point_id BIGINT REFERENCES service_delivery_points(id) ON DELETE SET NULL
);

-- Create indexes for usage_points
CREATE INDEX idx_usage_point_uuid ON usage_points(uuid);
CREATE INDEX idx_usage_point_customer_id ON usage_points(retail_customer_id);
CREATE INDEX idx_usage_point_sdp_id ON usage_points(service_delivery_point_id);
CREATE INDEX idx_usage_point_service_category ON usage_points(service_category_kind);
CREATE INDEX idx_usage_point_status ON usage_points(status);
CREATE INDEX idx_usage_point_created ON usage_points(created);
CREATE INDEX idx_usage_point_updated ON usage_points(updated);

-- Related Links Table for Usage Points
CREATE TABLE usage_point_related_links (
    usage_point_id BIGINT NOT NULL REFERENCES usage_points(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_usage_point_related_links ON usage_point_related_links(usage_point_id);

-- Reading Type Table
-- Defines the characteristics of readings (e.g., energy, power, demand)
CREATE TABLE reading_types (
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
    argument SMALLINT
);

-- Create indexes for reading_types
CREATE INDEX idx_reading_type_uuid ON reading_types(uuid);
CREATE INDEX idx_reading_type_kind ON reading_types(kind);
CREATE INDEX idx_reading_type_commodity ON reading_types(commodity);
CREATE INDEX idx_reading_type_uom ON reading_types(uom);
CREATE INDEX idx_reading_type_created ON reading_types(created);
CREATE INDEX idx_reading_type_updated ON reading_types(updated);

-- Related Links Table for Reading Types
CREATE TABLE reading_type_related_links (
    reading_type_id BIGINT NOT NULL REFERENCES reading_types(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_reading_type_related_links ON reading_type_related_links(reading_type_id);

-- Meter Reading Table
-- Contains time series of readings for a specific usage point
CREATE TABLE meter_readings (
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
    
    -- Foreign key relationships
    usage_point_id BIGINT REFERENCES usage_points(id) ON DELETE CASCADE,
    reading_type_id BIGINT REFERENCES reading_types(id) ON DELETE SET NULL
);

-- Create indexes for meter_readings
CREATE INDEX idx_meter_reading_uuid ON meter_readings(uuid);
CREATE INDEX idx_meter_reading_usage_point_id ON meter_readings(usage_point_id);
CREATE INDEX idx_meter_reading_reading_type_id ON meter_readings(reading_type_id);
CREATE INDEX idx_meter_reading_created ON meter_readings(created);
CREATE INDEX idx_meter_reading_updated ON meter_readings(updated);

-- Related Links Table for Meter Readings
CREATE TABLE meter_reading_related_links (
    meter_reading_id BIGINT NOT NULL REFERENCES meter_readings(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_meter_reading_related_links ON meter_reading_related_links(meter_reading_id);

-- Interval Block Table
-- Time sequence of readings of the same reading type
CREATE TABLE interval_blocks (
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
    
    -- Interval block specific fields
    interval_start BIGINT,
    interval_duration BIGINT,
    
    -- Foreign key relationships
    meter_reading_id BIGINT REFERENCES meter_readings(id) ON DELETE CASCADE
);

-- Create indexes for interval_blocks
CREATE INDEX idx_interval_block_uuid ON interval_blocks(uuid);
CREATE INDEX idx_interval_block_meter_reading_id ON interval_blocks(meter_reading_id);
CREATE INDEX idx_interval_block_interval_start ON interval_blocks(interval_start);
CREATE INDEX idx_interval_block_created ON interval_blocks(created);
CREATE INDEX idx_interval_block_updated ON interval_blocks(updated);

-- Related Links Table for Interval Blocks
CREATE TABLE interval_block_related_links (
    interval_block_id BIGINT NOT NULL REFERENCES interval_blocks(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_interval_block_related_links ON interval_block_related_links(interval_block_id);

-- Interval Reading Table
-- Individual reading values within an interval block
CREATE TABLE interval_readings (
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
    
    -- Interval reading specific fields
    cost BIGINT,
    value BIGINT,
    time_period_start BIGINT,
    time_period_duration BIGINT,
    consumption_tier SMALLINT,
    tou SMALLINT,
    cpp SMALLINT,
    
    -- Foreign key relationships
    interval_block_id BIGINT REFERENCES interval_blocks(id) ON DELETE CASCADE
);

-- Create indexes for interval_readings
CREATE INDEX idx_interval_reading_uuid ON interval_readings(uuid);
CREATE INDEX idx_interval_reading_interval_block_id ON interval_readings(interval_block_id);
CREATE INDEX idx_interval_reading_time_period_start ON interval_readings(time_period_start);
CREATE INDEX idx_interval_reading_value ON interval_readings(value);
CREATE INDEX idx_interval_reading_created ON interval_readings(created);
CREATE INDEX idx_interval_reading_updated ON interval_readings(updated);

-- Related Links Table for Interval Readings
CREATE TABLE interval_reading_related_links (
    interval_reading_id BIGINT NOT NULL REFERENCES interval_readings(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_interval_reading_related_links ON interval_reading_related_links(interval_reading_id);

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_usage_points_updated BEFORE UPDATE ON usage_points FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_reading_types_updated BEFORE UPDATE ON reading_types FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_meter_readings_updated BEFORE UPDATE ON meter_readings FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_interval_blocks_updated BEFORE UPDATE ON interval_blocks FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_interval_readings_updated BEFORE UPDATE ON interval_readings FOR EACH ROW EXECUTE FUNCTION update_updated_column();