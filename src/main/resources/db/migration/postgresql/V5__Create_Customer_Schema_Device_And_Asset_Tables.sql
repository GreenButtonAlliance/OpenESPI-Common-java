/*
 * OpenESPI Customer Schema - Device and Asset Tables Migration (PostgreSQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for end devices, meters, and asset management
 * within the customer schema following the NAESB ESPI 1.0 specification.
 *
 * PostgreSQL-specific implementation with proper data types and indexing.
 */

-- Set schema for customer data
SET search_path TO openespi_customer;

-- End Device Table
-- Asset container that performs one or more end device functions
CREATE TABLE end_devices (
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
    
    -- End device specific fields
    amr_system VARCHAR(255),
    install_code VARCHAR(255),
    is_pan BOOLEAN DEFAULT FALSE,
    is_virtual BOOLEAN DEFAULT FALSE,
    time_zone_offset BIGINT,
    
    -- Installation and lifecycle
    install_date TIMESTAMP(6) WITH TIME ZONE,
    removed_date TIMESTAMP(6) WITH TIME ZONE,
    
    -- Device identification
    serial_number VARCHAR(255),
    
    -- Relationships within customer schema
    service_location_id BIGINT REFERENCES service_locations(id) ON DELETE SET NULL
);

-- Create indexes for end_devices
CREATE INDEX idx_end_device_uuid ON end_devices(uuid);
CREATE INDEX idx_end_device_serial_number ON end_devices(serial_number);
CREATE INDEX idx_end_device_amr_system ON end_devices(amr_system);
CREATE INDEX idx_end_device_service_location_id ON end_devices(service_location_id);
CREATE INDEX idx_end_device_install_date ON end_devices(install_date);
CREATE INDEX idx_end_device_removed_date ON end_devices(removed_date);
CREATE INDEX idx_end_device_created ON end_devices(created);
CREATE INDEX idx_end_device_updated ON end_devices(updated);

-- Related Links Table for End Devices
CREATE TABLE end_device_related_links (
    end_device_id BIGINT NOT NULL REFERENCES end_devices(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_end_device_related_links ON end_device_related_links(end_device_id);

-- Meter Table
-- Physical device that measures and records usage
CREATE TABLE meters (
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
    
    -- Meter specific fields
    form_number VARCHAR(255),
    meter_multiplier DECIMAL(15,6),
    
    -- Relationships within customer schema
    end_device_id BIGINT REFERENCES end_devices(id) ON DELETE CASCADE
);

-- Create indexes for meters
CREATE INDEX idx_meter_uuid ON meters(uuid);
CREATE INDEX idx_meter_form_number ON meters(form_number);
CREATE INDEX idx_meter_end_device_id ON meters(end_device_id);
CREATE INDEX idx_meter_created ON meters(created);
CREATE INDEX idx_meter_updated ON meters(updated);

-- Related Links Table for Meters
CREATE TABLE meter_related_links (
    meter_id BIGINT NOT NULL REFERENCES meters(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_meter_related_links ON meter_related_links(meter_id);

-- Asset Container Table
-- Container for assets and other asset containers
CREATE TABLE asset_containers (
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
    
    -- Asset container specific fields (inherited by end devices)
    -- Additional fields can be added as needed
);

-- Create indexes for asset_containers
CREATE INDEX idx_asset_container_uuid ON asset_containers(uuid);
CREATE INDEX idx_asset_container_created ON asset_containers(created);
CREATE INDEX idx_asset_container_updated ON asset_containers(updated);

-- Related Links Table for Asset Containers
CREATE TABLE asset_container_related_links (
    asset_container_id BIGINT NOT NULL REFERENCES asset_containers(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_asset_container_related_links ON asset_container_related_links(asset_container_id);

-- Asset Table
-- Tangible resource with potential value to an organization
CREATE TABLE assets (
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
    
    -- Asset specific fields
    critical BOOLEAN DEFAULT FALSE,
    lot_number VARCHAR(255),
    purchase_price DECIMAL(15,2),
    serial_number VARCHAR(255),
    type VARCHAR(255),
    
    -- Lifecycle dates
    received_date TIMESTAMP(6) WITH TIME ZONE,
    installation_date TIMESTAMP(6) WITH TIME ZONE,
    removal_date TIMESTAMP(6) WITH TIME ZONE,
    retired_date TIMESTAMP(6) WITH TIME ZONE,
    
    -- Relationships within customer schema
    asset_container_id BIGINT REFERENCES asset_containers(id) ON DELETE SET NULL
);

-- Create indexes for assets
CREATE INDEX idx_asset_uuid ON assets(uuid);
CREATE INDEX idx_asset_serial_number ON assets(serial_number);
CREATE INDEX idx_asset_lot_number ON assets(lot_number);
CREATE INDEX idx_asset_type ON assets(type);
CREATE INDEX idx_asset_critical ON assets(critical);
CREATE INDEX idx_asset_container_id ON assets(asset_container_id);
CREATE INDEX idx_asset_received_date ON assets(received_date);
CREATE INDEX idx_asset_installation_date ON assets(installation_date);
CREATE INDEX idx_asset_created ON assets(created);
CREATE INDEX idx_asset_updated ON assets(updated);

-- Related Links Table for Assets
CREATE TABLE asset_related_links (
    asset_id BIGINT NOT NULL REFERENCES assets(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_asset_related_links ON asset_related_links(asset_id);

-- Location Table
-- Geographic location information
CREATE TABLE locations (
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
    
    -- Location specific fields
    direction VARCHAR(255),
    geoinfo_reference VARCHAR(255),
    
    -- Coordinates (stored as individual fields for indexing)
    main_address_line VARCHAR(255),
    second_address_line VARCHAR(255),
    town_detail VARCHAR(255),
    state_or_province VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100)
);

-- Create indexes for locations
CREATE INDEX idx_location_uuid ON locations(uuid);
CREATE INDEX idx_location_postal_code ON locations(postal_code);
CREATE INDEX idx_location_state_or_province ON locations(state_or_province);
CREATE INDEX idx_location_country ON locations(country);
CREATE INDEX idx_location_created ON locations(created);
CREATE INDEX idx_location_updated ON locations(updated);

-- Related Links Table for Locations
CREATE TABLE location_related_links (
    location_id BIGINT NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_location_related_links ON location_related_links(location_id);

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_end_devices_updated BEFORE UPDATE ON end_devices FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_meters_updated BEFORE UPDATE ON meters FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_asset_containers_updated BEFORE UPDATE ON asset_containers FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_assets_updated BEFORE UPDATE ON assets FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_locations_updated BEFORE UPDATE ON locations FOR EACH ROW EXECUTE FUNCTION update_updated_column();