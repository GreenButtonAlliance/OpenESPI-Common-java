/*
 * OpenESPI UUID Primary Key Migration (MySQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration converts all resource tables from BIGINT AUTO_INCREMENT IDs to UUID primary keys
 * for NAESB ESPI compliance. UUID5 identifiers provide deterministic, globally unique
 * resource identification based on href rel="self" values.
 *
 * CRITICAL: This is a breaking change that affects all resource entities.
 * Backup your database before running this migration.
 */

-- Use openespi_usage database
USE openespi_usage;

-- Disable foreign key checks temporarily
SET foreign_key_checks = 0;

-- =========================================================================
-- STEP 1: Add new UUID columns to all resource tables
-- =========================================================================

-- Application Information
ALTER TABLE application_information 
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY application_information_new_id_unique (new_id);

-- Retail Customers  
ALTER TABLE retail_customers
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY retail_customers_new_id_unique (new_id);

-- Authorizations
ALTER TABLE authorizations
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY authorizations_new_id_unique (new_id);

-- Usage Points
ALTER TABLE usage_points
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY usage_points_new_id_unique (new_id);

-- Meter Readings
ALTER TABLE meter_readings
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY meter_readings_new_id_unique (new_id);

-- Interval Blocks
ALTER TABLE interval_blocks
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY interval_blocks_new_id_unique (new_id);

-- Reading Types
ALTER TABLE reading_types
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY reading_types_new_id_unique (new_id);

-- Usage Summaries
ALTER TABLE usage_summaries
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY usage_summaries_new_id_unique (new_id);

-- Electric Power Quality Summaries
ALTER TABLE electric_power_quality_summaries
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY electric_power_quality_summaries_new_id_unique (new_id);

-- Time Configurations
ALTER TABLE time_configurations
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY time_configurations_new_id_unique (new_id);

-- Subscriptions
ALTER TABLE subscriptions
ADD COLUMN new_id CHAR(36) DEFAULT (UUID()),
ADD UNIQUE KEY subscriptions_new_id_unique (new_id);

-- =========================================================================
-- STEP 2: Update foreign key columns to UUID type
-- =========================================================================

-- Add new UUID foreign key columns
ALTER TABLE authorizations
ADD COLUMN new_application_information_id CHAR(36),
ADD COLUMN new_retail_customer_id CHAR(36);

ALTER TABLE usage_points
ADD COLUMN new_retail_customer_id CHAR(36);

ALTER TABLE meter_readings  
ADD COLUMN new_usage_point_id CHAR(36),
ADD COLUMN new_reading_type_id CHAR(36);

ALTER TABLE interval_blocks
ADD COLUMN new_meter_reading_id CHAR(36);

ALTER TABLE interval_readings
ADD COLUMN new_interval_block_id CHAR(36);

ALTER TABLE reading_qualities
ADD COLUMN new_interval_reading_id CHAR(36);

ALTER TABLE usage_summaries
ADD COLUMN new_usage_point_id CHAR(36);

ALTER TABLE electric_power_quality_summaries
ADD COLUMN new_usage_point_id CHAR(36);

ALTER TABLE subscriptions
ADD COLUMN new_application_information_id CHAR(36),
ADD COLUMN new_usage_point_id CHAR(36);

ALTER TABLE pnode_refs
ADD COLUMN new_usage_point_id CHAR(36);

ALTER TABLE aggregated_node_refs
ADD COLUMN new_usage_point_id CHAR(36),
ADD COLUMN new_pnode_ref_id CHAR(36);

-- =========================================================================
-- STEP 3: Populate new UUID foreign key columns with mapped values
-- =========================================================================

-- Map authorization foreign keys
UPDATE authorizations a 
JOIN application_information ai ON a.application_information_id = ai.id 
SET a.new_application_information_id = ai.new_id;

UPDATE authorizations a 
JOIN retail_customers rc ON a.retail_customer_id = rc.id 
SET a.new_retail_customer_id = rc.new_id;

-- Map usage point foreign keys
UPDATE usage_points up 
JOIN retail_customers rc ON up.retail_customer_id = rc.id 
SET up.new_retail_customer_id = rc.new_id;

-- Map meter reading foreign keys
UPDATE meter_readings mr 
JOIN usage_points up ON mr.usage_point_id = up.id 
SET mr.new_usage_point_id = up.new_id;

UPDATE meter_readings mr 
JOIN reading_types rt ON mr.reading_type_id = rt.id 
SET mr.new_reading_type_id = rt.new_id;

-- Map interval block foreign keys
UPDATE interval_blocks ib 
JOIN meter_readings mr ON ib.meter_reading_id = mr.id 
SET ib.new_meter_reading_id = mr.new_id;

-- Map interval reading foreign keys
UPDATE interval_readings ir 
JOIN interval_blocks ib ON ir.interval_block_id = ib.id 
SET ir.new_interval_block_id = ib.new_id;

-- Map reading quality foreign keys
UPDATE reading_qualities rq 
JOIN interval_readings ir ON rq.interval_reading_id = ir.id 
SET rq.new_interval_reading_id = ir.new_id;

-- Map usage summary foreign keys
UPDATE usage_summaries us 
JOIN usage_points up ON us.usage_point_id = up.id 
SET us.new_usage_point_id = up.new_id;

-- Map electric power quality summary foreign keys
UPDATE electric_power_quality_summaries epqs 
JOIN usage_points up ON epqs.usage_point_id = up.id 
SET epqs.new_usage_point_id = up.new_id;

-- Map subscription foreign keys
UPDATE subscriptions s 
JOIN application_information ai ON s.application_information_id = ai.id 
SET s.new_application_information_id = ai.new_id;

UPDATE subscriptions s 
JOIN usage_points up ON s.usage_point_id = up.id 
SET s.new_usage_point_id = up.new_id;

-- Map pnode_refs foreign keys
UPDATE pnode_refs pr 
JOIN usage_points up ON pr.usage_point_id = up.id 
SET pr.new_usage_point_id = up.new_id;

-- Map aggregated_node_refs foreign keys
UPDATE aggregated_node_refs anr 
JOIN usage_points up ON anr.usage_point_id = up.id 
SET anr.new_usage_point_id = up.new_id;

UPDATE aggregated_node_refs anr 
JOIN pnode_refs pr ON anr.pnode_ref_id = pr.id 
SET anr.new_pnode_ref_id = pr.new_id;

-- =========================================================================
-- STEP 4: Drop old foreign key constraints and columns
-- =========================================================================

-- Drop foreign key constraints
ALTER TABLE authorizations 
DROP FOREIGN KEY IF EXISTS fk_auth_app_info,
DROP FOREIGN KEY IF EXISTS fk_auth_retail_customer;

ALTER TABLE usage_points
DROP FOREIGN KEY IF EXISTS fk_usage_point_retail_customer;

ALTER TABLE meter_readings
DROP FOREIGN KEY IF EXISTS fk_meter_reading_usage_point,
DROP FOREIGN KEY IF EXISTS fk_meter_reading_reading_type;

ALTER TABLE interval_blocks
DROP FOREIGN KEY IF EXISTS fk_interval_block_meter_reading;

ALTER TABLE interval_readings
DROP FOREIGN KEY IF EXISTS fk_interval_reading_interval_block;

ALTER TABLE reading_qualities
DROP FOREIGN KEY IF EXISTS fk_reading_quality_interval_reading;

ALTER TABLE usage_summaries
DROP FOREIGN KEY IF EXISTS fk_usage_summary_usage_point;

ALTER TABLE electric_power_quality_summaries
DROP FOREIGN KEY IF EXISTS fk_epqs_usage_point;

ALTER TABLE subscriptions
DROP FOREIGN KEY IF EXISTS fk_subscription_app_info,
DROP FOREIGN KEY IF EXISTS fk_subscription_usage_point;

ALTER TABLE pnode_refs
DROP FOREIGN KEY IF EXISTS fk_pnode_ref_usage_point;

ALTER TABLE aggregated_node_refs
DROP FOREIGN KEY IF EXISTS fk_aggregated_node_ref_usage_point,
DROP FOREIGN KEY IF EXISTS fk_aggregated_node_ref_pnode_ref;

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
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE application_information 
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

-- Similar for all other tables...
ALTER TABLE retail_customers
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE retail_customers
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE authorizations
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE authorizations
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE usage_points
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE usage_points  
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE meter_readings
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE meter_readings
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE interval_blocks
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE interval_blocks
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE reading_types
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE reading_types
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE usage_summaries
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE usage_summaries
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE electric_power_quality_summaries
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE electric_power_quality_summaries
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE time_configurations
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE time_configurations
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

ALTER TABLE subscriptions
DROP PRIMARY KEY,
DROP COLUMN id,
ADD PRIMARY KEY (new_id);

ALTER TABLE subscriptions
CHANGE COLUMN new_id id CHAR(36) NOT NULL;

-- =========================================================================
-- STEP 6: Rename foreign key columns and add constraints
-- =========================================================================

-- Rename foreign key columns back to original names
ALTER TABLE authorizations
CHANGE COLUMN new_application_information_id application_information_id CHAR(36),
CHANGE COLUMN new_retail_customer_id retail_customer_id CHAR(36);

ALTER TABLE usage_points
CHANGE COLUMN new_retail_customer_id retail_customer_id CHAR(36);

ALTER TABLE meter_readings
CHANGE COLUMN new_usage_point_id usage_point_id CHAR(36),
CHANGE COLUMN new_reading_type_id reading_type_id CHAR(36);

ALTER TABLE interval_blocks
CHANGE COLUMN new_meter_reading_id meter_reading_id CHAR(36);

ALTER TABLE interval_readings
CHANGE COLUMN new_interval_block_id interval_block_id CHAR(36);

ALTER TABLE reading_qualities
CHANGE COLUMN new_interval_reading_id interval_reading_id CHAR(36);

ALTER TABLE usage_summaries
CHANGE COLUMN new_usage_point_id usage_point_id CHAR(36);

ALTER TABLE electric_power_quality_summaries
CHANGE COLUMN new_usage_point_id usage_point_id CHAR(36);

ALTER TABLE subscriptions
CHANGE COLUMN new_application_information_id application_information_id CHAR(36),
CHANGE COLUMN new_usage_point_id usage_point_id CHAR(36);

ALTER TABLE pnode_refs
CHANGE COLUMN new_usage_point_id usage_point_id CHAR(36);

ALTER TABLE aggregated_node_refs
CHANGE COLUMN new_usage_point_id usage_point_id CHAR(36),
CHANGE COLUMN new_pnode_ref_id pnode_ref_id CHAR(36);

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
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE retail_customers
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb, 
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE authorizations
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE usage_points
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE meter_readings
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE interval_blocks
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE reading_types
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE usage_summaries
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE electric_power_quality_summaries
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE time_configurations
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

ALTER TABLE subscriptions
DROP COLUMN IF EXISTS uuid,
DROP COLUMN IF EXISTS uuid_msb,
DROP COLUMN IF EXISTS uuid_lsb;

-- Re-enable foreign key checks
SET foreign_key_checks = 1;

-- Update related links table structures for UUID references
ALTER TABLE identified_object_related_links
DROP FOREIGN KEY IF EXISTS identified_object_related_links_identified_object_id_fkey,
MODIFY COLUMN identified_object_id CHAR(36),
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