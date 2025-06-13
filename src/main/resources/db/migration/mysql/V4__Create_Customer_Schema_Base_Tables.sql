/*
 * OpenESPI Customer Schema - Base Tables Migration
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates the foundational tables for the OpenESPI customer schema.
 * These tables implement customer-specific information including Personally Identifiable Information (PII)
 * following the NAESB ESPI 1.0 specification.
 *
 * IMPORTANT: Customer and Usage schemas are decoupled by design.
 * Relationships are maintained through ATOM self link href URLs, NOT foreign keys.
 */

-- Set schema for customer data
USE openespi_customer;

-- Customer Table
-- Organisation receiving services from service supplier (contains PII)
CREATE TABLE customers (
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
    
    -- Customer specific fields
    kind VARCHAR(50),
    special_need VARCHAR(255),
    vip BOOLEAN DEFAULT FALSE,
    puc_number VARCHAR(100),
    status VARCHAR(50),
    priority VARCHAR(50),
    locale VARCHAR(10),
    customer_name VARCHAR(255),
    
    -- ATOM href reference to usage schema retail customer (NO foreign key)
    retail_customer_href VARCHAR(1024),
    
    INDEX idx_customer_uuid (uuid),
    INDEX idx_customer_kind (kind),
    INDEX idx_customer_puc_number (puc_number),
    INDEX idx_customer_status (status),
    INDEX idx_customer_retail_customer_href (retail_customer_href(255)),
    INDEX idx_customer_created (created),
    INDEX idx_customer_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Customers
CREATE TABLE customer_related_links (
    customer_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    INDEX idx_customer_related_links (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Customer Account Table
-- Financial account information for customer billing
CREATE TABLE customer_accounts (
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
    
    -- Customer account specific fields
    account_number VARCHAR(100),
    account_kind VARCHAR(50),
    budget_bill VARCHAR(255),
    billing_cycle VARCHAR(50),
    last_bill_amount DECIMAL(15,2),
    
    -- ATOM href reference to customer (within same schema)
    customer_id BIGINT,
    
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    
    INDEX idx_customer_account_uuid (uuid),
    INDEX idx_customer_account_number (account_number),
    INDEX idx_customer_account_kind (account_kind),
    INDEX idx_customer_account_customer_id (customer_id),
    INDEX idx_customer_account_created (created),
    INDEX idx_customer_account_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Customer Accounts
CREATE TABLE customer_account_related_links (
    customer_account_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (customer_account_id) REFERENCES customer_accounts(id) ON DELETE CASCADE,
    INDEX idx_customer_account_related_links (customer_account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Service Location Table
-- Physical location where services are provided (contains PII)
CREATE TABLE service_locations (
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
    
    -- Service location specific fields
    direction VARCHAR(255),
    geoinfo_reference VARCHAR(255),
    position_points TEXT, -- JSON array of coordinates
    status VARCHAR(50),
    access_method VARCHAR(100),
    site_access_problem VARCHAR(1024),
    needs_inspection BOOLEAN DEFAULT FALSE,
    
    -- Address fields (PII)
    address_general VARCHAR(1024),
    
    INDEX idx_service_location_uuid (uuid),
    INDEX idx_service_location_status (status),
    INDEX idx_service_location_needs_inspection (needs_inspection),
    INDEX idx_service_location_created (created),
    INDEX idx_service_location_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Service Locations
CREATE TABLE service_location_related_links (
    service_location_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (service_location_id) REFERENCES service_locations(id) ON DELETE CASCADE,
    INDEX idx_service_location_related_links (service_location_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usage Point References Table
-- CRITICAL: This table stores ATOM href URLs to usage points in the usage schema
-- NO foreign keys to usage schema - loose coupling via href URLs
CREATE TABLE service_location_usage_points (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_location_id BIGINT NOT NULL,
    
    -- ATOM href reference to usage point in usage schema (NO foreign key)
    usage_point_href VARCHAR(1024) NOT NULL,
    
    -- Optional cached data for performance (periodically synchronized)
    usage_point_uuid VARCHAR(36),
    usage_point_description VARCHAR(255),
    service_category VARCHAR(50),
    last_synchronized DATETIME(6),
    
    FOREIGN KEY (service_location_id) REFERENCES service_locations(id) ON DELETE CASCADE,
    
    INDEX idx_sl_usage_points_service_location_id (service_location_id),
    INDEX idx_sl_usage_points_href (usage_point_href(255)),
    INDEX idx_sl_usage_points_uuid (usage_point_uuid),
    INDEX idx_sl_usage_points_last_sync (last_synchronized),
    
    -- Ensure unique combination of service location and usage point href
    UNIQUE KEY uk_service_location_usage_point_href (service_location_id, usage_point_href(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Customer Agreement Table
-- Legal agreement between customer and service provider
CREATE TABLE customer_agreements (
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
    
    -- Customer agreement specific fields
    load_mgmt_cap VARCHAR(255),
    sign_date DATETIME(6),
    validity_interval_start BIGINT,
    validity_interval_duration BIGINT,
    budget_bill VARCHAR(255),
    
    -- Relationships within customer schema
    customer_id BIGINT,
    service_location_id BIGINT,
    
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (service_location_id) REFERENCES service_locations(id) ON DELETE SET NULL,
    
    INDEX idx_customer_agreement_uuid (uuid),
    INDEX idx_customer_agreement_customer_id (customer_id),
    INDEX idx_customer_agreement_service_location_id (service_location_id),
    INDEX idx_customer_agreement_sign_date (sign_date),
    INDEX idx_customer_agreement_created (created),
    INDEX idx_customer_agreement_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Customer Agreements
CREATE TABLE customer_agreement_related_links (
    customer_agreement_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (customer_agreement_id) REFERENCES customer_agreements(id) ON DELETE CASCADE,
    INDEX idx_customer_agreement_related_links (customer_agreement_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;