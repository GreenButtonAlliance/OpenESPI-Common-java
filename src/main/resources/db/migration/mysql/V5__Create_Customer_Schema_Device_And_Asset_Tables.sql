/*
 * OpenESPI Customer Schema - Device and Asset Tables Migration
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for end devices, meters, and asset management
 * within the customer schema following the NAESB ESPI 1.0 specification.
 */

-- Set schema for customer data
USE openespi_customer;

-- End Device Table
-- Asset container that performs one or more end device functions
CREATE TABLE end_devices (
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
    
    -- End device specific fields
    amr_system VARCHAR(255),
    install_code VARCHAR(255),
    is_pan BOOLEAN DEFAULT FALSE,
    is_virtual BOOLEAN DEFAULT FALSE,
    time_zone_offset BIGINT,
    
    -- Installation and lifecycle
    install_date DATETIME(6),
    removed_date DATETIME(6),
    
    -- Device identification
    serial_number VARCHAR(255),
    
    -- Relationships within customer schema
    service_location_id BIGINT,
    
    FOREIGN KEY (service_location_id) REFERENCES service_locations(id) ON DELETE SET NULL,
    
    INDEX idx_end_device_uuid (uuid),
    INDEX idx_end_device_serial_number (serial_number),
    INDEX idx_end_device_amr_system (amr_system),
    INDEX idx_end_device_service_location_id (service_location_id),
    INDEX idx_end_device_install_date (install_date),
    INDEX idx_end_device_removed_date (removed_date),
    INDEX idx_end_device_created (created),
    INDEX idx_end_device_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for End Devices
CREATE TABLE end_device_related_links (
    end_device_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (end_device_id) REFERENCES end_devices(id) ON DELETE CASCADE,
    INDEX idx_end_device_related_links (end_device_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Meter Table
-- Physical device that measures and records usage
CREATE TABLE meters (
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
    
    -- Meter specific fields
    form_number VARCHAR(255),
    meter_multiplier DECIMAL(15,6),
    
    -- Relationships within customer schema
    end_device_id BIGINT,
    
    FOREIGN KEY (end_device_id) REFERENCES end_devices(id) ON DELETE CASCADE,
    
    INDEX idx_meter_uuid (uuid),
    INDEX idx_meter_form_number (form_number),
    INDEX idx_meter_end_device_id (end_device_id),
    INDEX idx_meter_created (created),
    INDEX idx_meter_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Meters
CREATE TABLE meter_related_links (
    meter_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (meter_id) REFERENCES meters(id) ON DELETE CASCADE,
    INDEX idx_meter_related_links (meter_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Asset Container Table
-- Container for assets and other asset containers
CREATE TABLE asset_containers (
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
    
    -- Asset container specific fields (inherited by end devices)
    -- Additional fields can be added as needed
    
    INDEX idx_asset_container_uuid (uuid),
    INDEX idx_asset_container_created (created),
    INDEX idx_asset_container_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Asset Containers
CREATE TABLE asset_container_related_links (
    asset_container_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (asset_container_id) REFERENCES asset_containers(id) ON DELETE CASCADE,
    INDEX idx_asset_container_related_links (asset_container_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Asset Table
-- Tangible resource with potential value to an organization
CREATE TABLE assets (
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
    
    -- Asset specific fields
    critical BOOLEAN DEFAULT FALSE,
    lot_number VARCHAR(255),
    purchase_price DECIMAL(15,2),
    serial_number VARCHAR(255),
    type VARCHAR(255),
    
    -- Lifecycle dates
    received_date DATETIME(6),
    installation_date DATETIME(6),
    removal_date DATETIME(6),
    retired_date DATETIME(6),
    
    -- Relationships within customer schema
    asset_container_id BIGINT,
    
    FOREIGN KEY (asset_container_id) REFERENCES asset_containers(id) ON DELETE SET NULL,
    
    INDEX idx_asset_uuid (uuid),
    INDEX idx_asset_serial_number (serial_number),
    INDEX idx_asset_lot_number (lot_number),
    INDEX idx_asset_type (type),
    INDEX idx_asset_critical (critical),
    INDEX idx_asset_container_id (asset_container_id),
    INDEX idx_asset_received_date (received_date),
    INDEX idx_asset_installation_date (installation_date),
    INDEX idx_asset_created (created),
    INDEX idx_asset_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Assets
CREATE TABLE asset_related_links (
    asset_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
    INDEX idx_asset_related_links (asset_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Location Table
-- Geographic location information
CREATE TABLE locations (
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
    
    -- Location specific fields
    direction VARCHAR(255),
    geoinfo_reference VARCHAR(255),
    
    -- Coordinates (stored as individual fields for indexing)
    main_address_line VARCHAR(255),
    second_address_line VARCHAR(255),
    town_detail VARCHAR(255),
    state_or_province VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    
    INDEX idx_location_uuid (uuid),
    INDEX idx_location_postal_code (postal_code),
    INDEX idx_location_state_or_province (state_or_province),
    INDEX idx_location_country (country),
    INDEX idx_location_created (created),
    INDEX idx_location_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Locations
CREATE TABLE location_related_links (
    location_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE,
    INDEX idx_location_related_links (location_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;