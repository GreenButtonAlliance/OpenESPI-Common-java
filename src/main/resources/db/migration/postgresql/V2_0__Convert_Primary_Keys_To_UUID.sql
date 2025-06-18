/*
 * OpenESPI UUID Primary Key Migration (PostgreSQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration converts all resource tables from BIGSERIAL Long IDs to UUID primary keys
 * for NAESB ESPI compliance. UUID5 identifiers provide deterministic, globally unique
 * resource identification based on href rel="self" values.
 *
 * CRITICAL: This is a breaking change that affects all resource entities.
 * Backup your database before running this migration.
 */

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Set schema for usage data
SET search_path TO openespi_usage;

-- Disable foreign key checks temporarily
SET session_replication_role = replica;

-- =========================================================================
-- STEP 1: Add new UUID columns to all resource tables
-- =========================================================================

-- Application Information
ALTER TABLE application_information 
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT application_information_new_id_unique UNIQUE (new_id);

-- Retail Customers  
ALTER TABLE retail_customers
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT retail_customers_new_id_unique UNIQUE (new_id);

-- Authorizations
ALTER TABLE authorizations
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT authorizations_new_id_unique UNIQUE (new_id);

-- Usage Points
ALTER TABLE usage_points
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT usage_points_new_id_unique UNIQUE (new_id);

-- Meter Readings
ALTER TABLE meter_readings
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT meter_readings_new_id_unique UNIQUE (new_id);

-- Interval Blocks
ALTER TABLE interval_blocks
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT interval_blocks_new_id_unique UNIQUE (new_id);

-- Reading Types
ALTER TABLE reading_types
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT reading_types_new_id_unique UNIQUE (new_id);

-- Usage Summaries
ALTER TABLE usage_summaries
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT usage_summaries_new_id_unique UNIQUE (new_id);

-- Electric Power Quality Summaries
ALTER TABLE electric_power_quality_summaries
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT electric_power_quality_summaries_new_id_unique UNIQUE (new_id);

-- Time Configurations
ALTER TABLE time_configurations
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT time_configurations_new_id_unique UNIQUE (new_id);

-- Subscriptions
ALTER TABLE subscriptions
ADD COLUMN new_id UUID DEFAULT uuid_generate_v4(),
ADD CONSTRAINT subscriptions_new_id_unique UNIQUE (new_id);

-- =========================================================================
-- STEP 2: Update foreign key columns to UUID type
-- =========================================================================

-- Add new UUID foreign key columns
ALTER TABLE authorizations
ADD COLUMN new_application_information_id UUID,
ADD COLUMN new_retail_customer_id UUID;

ALTER TABLE usage_points
ADD COLUMN new_retail_customer_id UUID;

ALTER TABLE meter_readings  
ADD COLUMN new_usage_point_id UUID,
ADD COLUMN new_reading_type_id UUID;

ALTER TABLE interval_blocks
ADD COLUMN new_meter_reading_id UUID;

ALTER TABLE interval_readings
ADD COLUMN new_interval_block_id UUID;

ALTER TABLE reading_qualities
ADD COLUMN new_interval_reading_id UUID;

ALTER TABLE usage_summaries
ADD COLUMN new_usage_point_id UUID;

ALTER TABLE electric_power_quality_summaries
ADD COLUMN new_usage_point_id UUID;

ALTER TABLE subscriptions
ADD COLUMN new_application_information_id UUID,
ADD COLUMN new_usage_point_id UUID;

ALTER TABLE pnode_refs
ADD COLUMN new_usage_point_id UUID;

ALTER TABLE aggregated_node_refs
ADD COLUMN new_usage_point_id UUID,
ADD COLUMN new_pnode_ref_id UUID;

-- =========================================================================
-- STEP 3: Populate new UUID foreign key columns with mapped values
-- =========================================================================

-- Map authorization foreign keys
UPDATE authorizations SET new_application_information_id = ai.new_id 
FROM application_information ai WHERE authorizations.application_information_id = ai.id;

UPDATE authorizations SET new_retail_customer_id = rc.new_id 
FROM retail_customers rc WHERE authorizations.retail_customer_id = rc.id;

-- Map usage point foreign keys
UPDATE usage_points SET new_retail_customer_id = rc.new_id 
FROM retail_customers rc WHERE usage_points.retail_customer_id = rc.id;

-- Map meter reading foreign keys
UPDATE meter_readings SET new_usage_point_id = up.new_id 
FROM usage_points up WHERE meter_readings.usage_point_id = up.id;

UPDATE meter_readings SET new_reading_type_id = rt.new_id 
FROM reading_types rt WHERE meter_readings.reading_type_id = rt.id;

-- Map interval block foreign keys
UPDATE interval_blocks SET new_meter_reading_id = mr.new_id 
FROM meter_readings mr WHERE interval_blocks.meter_reading_id = mr.id;

-- Map interval reading foreign keys
UPDATE interval_readings SET new_interval_block_id = ib.new_id 
FROM interval_blocks ib WHERE interval_readings.interval_block_id = ib.id;

-- Map reading quality foreign keys
UPDATE reading_qualities SET new_interval_reading_id = ir.new_id 
FROM interval_readings ir WHERE reading_qualities.interval_reading_id = ir.id;

-- Map usage summary foreign keys
UPDATE usage_summaries SET new_usage_point_id = up.new_id 
FROM usage_points up WHERE usage_summaries.usage_point_id = up.id;

-- Map electric power quality summary foreign keys
UPDATE electric_power_quality_summaries SET new_usage_point_id = up.new_id 
FROM usage_points up WHERE electric_power_quality_summaries.usage_point_id = up.id;

-- Map subscription foreign keys
UPDATE subscriptions SET new_application_information_id = ai.new_id 
FROM application_information ai WHERE subscriptions.application_information_id = ai.id;

UPDATE subscriptions SET new_usage_point_id = up.new_id 
FROM usage_points up WHERE subscriptions.usage_point_id = up.id;

-- Map pnode_refs foreign keys
UPDATE pnode_refs SET new_usage_point_id = up.new_id 
FROM usage_points up WHERE pnode_refs.usage_point_id = up.id;

-- Map aggregated_node_refs foreign keys
UPDATE aggregated_node_refs SET new_usage_point_id = up.new_id 
FROM usage_points up WHERE aggregated_node_refs.usage_point_id = up.id;

UPDATE aggregated_node_refs SET new_pnode_ref_id = pr.new_id 
FROM pnode_refs pr WHERE aggregated_node_refs.pnode_ref_id = pr.id;

-- =========================================================================
-- STEP 4: Drop old foreign key constraints and columns
-- =========================================================================

-- Drop foreign key constraints (PostgreSQL auto-drops them with columns)
-- Drop old foreign key columns
ALTER TABLE authorizations 
DROP COLUMN application_information_id,
DROP COLUMN retail_customer_id;

ALTER TABLE usage_points
DROP COLUMN retail_customer_id;

ALTER TABLE meter_readings  
DROP COLUMN usage_point_id,
DROP COLUMN reading_type_id;

ALTER TABLE interval_blocks
DROP COLUMN meter_reading_id;

ALTER TABLE interval_readings
DROP COLUMN interval_block_id;

ALTER TABLE reading_qualities
DROP COLUMN interval_reading_id;

ALTER TABLE usage_summaries
DROP COLUMN usage_point_id;

ALTER TABLE electric_power_quality_summaries
DROP COLUMN usage_point_id;

ALTER TABLE subscriptions
DROP COLUMN application_information_id,
DROP COLUMN usage_point_id;

ALTER TABLE pnode_refs
DROP COLUMN usage_point_id;

ALTER TABLE aggregated_node_refs
DROP COLUMN usage_point_id,
DROP COLUMN pnode_ref_id;

-- =========================================================================
-- STEP 5: Replace primary keys with UUID columns
-- =========================================================================

-- Drop old primary key constraints and columns, rename new ones
ALTER TABLE application_information 
DROP CONSTRAINT application_information_pkey,
DROP COLUMN id,
ADD CONSTRAINT application_information_pkey PRIMARY KEY (new_id);

ALTER TABLE application_information 
RENAME COLUMN new_id TO id;

-- Similar for all other tables...
ALTER TABLE retail_customers
DROP CONSTRAINT retail_customers_pkey,  
DROP COLUMN id,
ADD CONSTRAINT retail_customers_pkey PRIMARY KEY (new_id);

ALTER TABLE retail_customers
RENAME COLUMN new_id TO id;

ALTER TABLE authorizations
DROP CONSTRAINT authorizations_pkey,
DROP COLUMN id, 
ADD CONSTRAINT authorizations_pkey PRIMARY KEY (new_id);

ALTER TABLE authorizations
RENAME COLUMN new_id TO id;

ALTER TABLE usage_points
DROP CONSTRAINT usage_points_pkey,
DROP COLUMN id,
ADD CONSTRAINT usage_points_pkey PRIMARY KEY (new_id);

ALTER TABLE usage_points  
RENAME COLUMN new_id TO id;

ALTER TABLE meter_readings
DROP CONSTRAINT meter_readings_pkey,
DROP COLUMN id,
ADD CONSTRAINT meter_readings_pkey PRIMARY KEY (new_id);

ALTER TABLE meter_readings
RENAME COLUMN new_id TO id;

ALTER TABLE interval_blocks
DROP CONSTRAINT interval_blocks_pkey,
DROP COLUMN id,
ADD CONSTRAINT interval_blocks_pkey PRIMARY KEY (new_id);

ALTER TABLE interval_blocks
RENAME COLUMN new_id TO id;

ALTER TABLE reading_types
DROP CONSTRAINT reading_types_pkey,
DROP COLUMN id,
ADD CONSTRAINT reading_types_pkey PRIMARY KEY (new_id);

ALTER TABLE reading_types
RENAME COLUMN new_id TO id;

ALTER TABLE usage_summaries
DROP CONSTRAINT usage_summaries_pkey,
DROP COLUMN id,
ADD CONSTRAINT usage_summaries_pkey PRIMARY KEY (new_id);

ALTER TABLE usage_summaries
RENAME COLUMN new_id TO id;

ALTER TABLE electric_power_quality_summaries
DROP CONSTRAINT electric_power_quality_summaries_pkey,
DROP COLUMN id,
ADD CONSTRAINT electric_power_quality_summaries_pkey PRIMARY KEY (new_id);

ALTER TABLE electric_power_quality_summaries
RENAME COLUMN new_id TO id;

ALTER TABLE time_configurations
DROP CONSTRAINT time_configurations_pkey,
DROP COLUMN id,
ADD CONSTRAINT time_configurations_pkey PRIMARY KEY (new_id);

ALTER TABLE time_configurations
RENAME COLUMN new_id TO id;

ALTER TABLE subscriptions
DROP CONSTRAINT subscriptions_pkey,
DROP COLUMN id,
ADD CONSTRAINT subscriptions_pkey PRIMARY KEY (new_id);

ALTER TABLE subscriptions
RENAME COLUMN new_id TO id;

-- =========================================================================
-- STEP 6: Rename foreign key columns and add constraints
-- =========================================================================

-- Rename foreign key columns back to original names
ALTER TABLE authorizations
RENAME COLUMN new_application_information_id TO application_information_id,
RENAME COLUMN new_retail_customer_id TO retail_customer_id;

ALTER TABLE usage_points
RENAME COLUMN new_retail_customer_id TO retail_customer_id;

ALTER TABLE meter_readings
RENAME COLUMN new_usage_point_id TO usage_point_id,
RENAME COLUMN new_reading_type_id TO reading_type_id;

ALTER TABLE interval_blocks
RENAME COLUMN new_meter_reading_id TO meter_reading_id;

ALTER TABLE interval_readings
RENAME COLUMN new_interval_block_id TO interval_block_id;

ALTER TABLE reading_qualities
RENAME COLUMN new_interval_reading_id TO interval_reading_id;

ALTER TABLE usage_summaries
RENAME COLUMN new_usage_point_id TO usage_point_id;

ALTER TABLE electric_power_quality_summaries
RENAME COLUMN new_usage_point_id TO usage_point_id;

ALTER TABLE subscriptions
RENAME COLUMN new_application_information_id TO application_information_id,
RENAME COLUMN new_usage_point_id TO usage_point_id;

ALTER TABLE pnode_refs
RENAME COLUMN new_usage_point_id TO usage_point_id;

ALTER TABLE aggregated_node_refs
RENAME COLUMN new_usage_point_id TO usage_point_id,
RENAME COLUMN new_pnode_ref_id TO pnode_ref_id;

-- =========================================================================
-- STEP 7: Add back foreign key constraints
-- =========================================================================

-- Add foreign key constraints
ALTER TABLE authorizations
ADD CONSTRAINT fk_auth_app_info FOREIGN KEY (application_information_id) REFERENCES application_information(id) ON DELETE CASCADE,
ADD CONSTRAINT fk_auth_retail_customer FOREIGN KEY (retail_customer_id) REFERENCES retail_customers(id) ON DELETE CASCADE;

ALTER TABLE usage_points
ADD CONSTRAINT fk_usage_point_retail_customer FOREIGN KEY (retail_customer_id) REFERENCES retail_customers(id) ON DELETE CASCADE;

ALTER TABLE meter_readings
ADD CONSTRAINT fk_meter_reading_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
ADD CONSTRAINT fk_meter_reading_reading_type FOREIGN KEY (reading_type_id) REFERENCES reading_types(id) ON DELETE SET NULL;

ALTER TABLE interval_blocks
ADD CONSTRAINT fk_interval_block_meter_reading FOREIGN KEY (meter_reading_id) REFERENCES meter_readings(id) ON DELETE CASCADE;

ALTER TABLE interval_readings
ADD CONSTRAINT fk_interval_reading_interval_block FOREIGN KEY (interval_block_id) REFERENCES interval_blocks(id) ON DELETE CASCADE;

ALTER TABLE reading_qualities
ADD CONSTRAINT fk_reading_quality_interval_reading FOREIGN KEY (interval_reading_id) REFERENCES interval_readings(id) ON DELETE CASCADE;

ALTER TABLE usage_summaries
ADD CONSTRAINT fk_usage_summary_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE;

ALTER TABLE electric_power_quality_summaries
ADD CONSTRAINT fk_epqs_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE;

ALTER TABLE subscriptions
ADD CONSTRAINT fk_subscription_app_info FOREIGN KEY (application_information_id) REFERENCES application_information(id) ON DELETE CASCADE,
ADD CONSTRAINT fk_subscription_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE;

ALTER TABLE pnode_refs
ADD CONSTRAINT fk_pnode_ref_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE;

ALTER TABLE aggregated_node_refs
ADD CONSTRAINT fk_aggregated_node_ref_usage_point FOREIGN KEY (usage_point_id) REFERENCES usage_points(id) ON DELETE CASCADE,
ADD CONSTRAINT fk_aggregated_node_ref_pnode_ref FOREIGN KEY (pnode_ref_id) REFERENCES pnode_refs(id) ON DELETE SET NULL;

-- =========================================================================
-- STEP 8: Remove old uuid columns and update indexes
-- =========================================================================

-- Remove old uuid columns since id is now UUID
ALTER TABLE application_information 
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE retail_customers
DROP COLUMN uuid,
DROP COLUMN uuid_msb, 
DROP COLUMN uuid_lsb;

ALTER TABLE authorizations
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE usage_points
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE meter_readings
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE interval_blocks
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE reading_types
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE usage_summaries
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE electric_power_quality_summaries
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE time_configurations
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

ALTER TABLE subscriptions
DROP COLUMN uuid,
DROP COLUMN uuid_msb,
DROP COLUMN uuid_lsb;

-- Re-enable foreign key checks
SET session_replication_role = DEFAULT;

-- Update related links table structures for UUID references
ALTER TABLE identified_object_related_links
DROP CONSTRAINT identified_object_related_links_identified_object_id_fkey,
ALTER COLUMN identified_object_id TYPE UUID USING identified_object_id::text::uuid,
ADD CONSTRAINT identified_object_related_links_identified_object_id_fkey 
    FOREIGN KEY (identified_object_id) REFERENCES application_information(id) ON DELETE CASCADE;

-- Create indexes for performance
CREATE INDEX idx_auth_app_info_id ON authorizations(application_information_id);
CREATE INDEX idx_auth_retail_customer_id ON authorizations(retail_customer_id);
CREATE INDEX idx_usage_point_retail_customer_id ON usage_points(retail_customer_id);
CREATE INDEX idx_meter_reading_usage_point_id ON meter_readings(usage_point_id);
CREATE INDEX idx_meter_reading_reading_type_id ON meter_readings(reading_type_id);
CREATE INDEX idx_interval_block_meter_reading_id ON interval_blocks(meter_reading_id);
CREATE INDEX idx_interval_reading_interval_block_id ON interval_readings(interval_block_id);
CREATE INDEX idx_usage_summary_usage_point_id ON usage_summaries(usage_point_id);
CREATE INDEX idx_epqs_usage_point_id ON electric_power_quality_summaries(usage_point_id);
CREATE INDEX idx_subscription_app_info_id ON subscriptions(application_information_id);
CREATE INDEX idx_subscription_usage_point_id ON subscriptions(usage_point_id);
CREATE INDEX idx_pnode_ref_usage_point_id ON pnode_refs(usage_point_id);
CREATE INDEX idx_aggregated_node_ref_usage_point_id ON aggregated_node_refs(usage_point_id);
CREATE INDEX idx_aggregated_node_ref_pnode_ref_id ON aggregated_node_refs(pnode_ref_id);

COMMIT;