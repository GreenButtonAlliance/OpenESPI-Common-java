/*
 * OpenESPI Customer Schema - Base Tables Migration (PostgreSQL)
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
 *
 * PostgreSQL-specific implementation with proper data types and indexing.
 */

-- Set schema for customer data
SET search_path TO openespi_customer;

-- Customer Table
-- Organisation receiving services from service supplier (contains PII)
CREATE TABLE customers (
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
    retail_customer_href VARCHAR(1024)
);

-- Create indexes for customers
CREATE INDEX idx_customer_uuid ON customers(uuid);
CREATE INDEX idx_customer_kind ON customers(kind);
CREATE INDEX idx_customer_puc_number ON customers(puc_number);
CREATE INDEX idx_customer_status ON customers(status);
CREATE INDEX idx_customer_retail_customer_href ON customers(retail_customer_href);
CREATE INDEX idx_customer_created ON customers(created);
CREATE INDEX idx_customer_updated ON customers(updated);

-- Related Links Table for Customers
CREATE TABLE customer_related_links (
    customer_id BIGINT NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_customer_related_links ON customer_related_links(customer_id);

-- Customer Account Table
-- Financial account information for customer billing
CREATE TABLE customer_accounts (
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
    
    -- Customer account specific fields
    account_number VARCHAR(100),
    account_kind VARCHAR(50),
    budget_bill VARCHAR(255),
    billing_cycle VARCHAR(50),
    last_bill_amount DECIMAL(15,2),
    
    -- Relationship to customer (within same schema)
    customer_id BIGINT REFERENCES customers(id) ON DELETE CASCADE
);

-- Create indexes for customer_accounts
CREATE INDEX idx_customer_account_uuid ON customer_accounts(uuid);
CREATE INDEX idx_customer_account_number ON customer_accounts(account_number);
CREATE INDEX idx_customer_account_kind ON customer_accounts(account_kind);
CREATE INDEX idx_customer_account_customer_id ON customer_accounts(customer_id);
CREATE INDEX idx_customer_account_created ON customer_accounts(created);
CREATE INDEX idx_customer_account_updated ON customer_accounts(updated);

-- Related Links Table for Customer Accounts
CREATE TABLE customer_account_related_links (
    customer_account_id BIGINT NOT NULL REFERENCES customer_accounts(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_customer_account_related_links ON customer_account_related_links(customer_account_id);

-- Service Location Table
-- Physical location where services are provided (contains PII)
CREATE TABLE service_locations (
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
    
    -- Service location specific fields
    direction VARCHAR(255),
    geoinfo_reference VARCHAR(255),
    position_points JSONB, -- JSON array of coordinates (PostgreSQL native JSON)
    status VARCHAR(50),
    access_method VARCHAR(100),
    site_access_problem VARCHAR(1024),
    needs_inspection BOOLEAN DEFAULT FALSE,
    
    -- Address fields (PII)
    address_general VARCHAR(1024)
);

-- Create indexes for service_locations
CREATE INDEX idx_service_location_uuid ON service_locations(uuid);
CREATE INDEX idx_service_location_status ON service_locations(status);
CREATE INDEX idx_service_location_needs_inspection ON service_locations(needs_inspection);
CREATE INDEX idx_service_location_created ON service_locations(created);
CREATE INDEX idx_service_location_updated ON service_locations(updated);
-- PostgreSQL JSONB index for efficient JSON queries on coordinates
CREATE INDEX idx_service_location_position_points ON service_locations USING GIN (position_points);

-- Related Links Table for Service Locations
CREATE TABLE service_location_related_links (
    service_location_id BIGINT NOT NULL REFERENCES service_locations(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_service_location_related_links ON service_location_related_links(service_location_id);

-- Usage Point References Table
-- CRITICAL: This table stores ATOM href URLs to usage points in the usage schema
-- NO foreign keys to usage schema - loose coupling via href URLs
CREATE TABLE service_location_usage_points (
    id BIGSERIAL PRIMARY KEY,
    service_location_id BIGINT NOT NULL REFERENCES service_locations(id) ON DELETE CASCADE,
    
    -- ATOM href reference to usage point in usage schema (NO foreign key)
    usage_point_href VARCHAR(1024) NOT NULL,
    
    -- Optional cached data for performance (periodically synchronized)
    usage_point_uuid VARCHAR(36),
    usage_point_description VARCHAR(255),
    service_category VARCHAR(50),
    last_synchronized TIMESTAMP(6) WITH TIME ZONE,
    
    -- Ensure unique combination of service location and usage point href
    UNIQUE(service_location_id, usage_point_href)
);

-- Create indexes for service_location_usage_points
CREATE INDEX idx_sl_usage_points_service_location_id ON service_location_usage_points(service_location_id);
CREATE INDEX idx_sl_usage_points_href ON service_location_usage_points(usage_point_href);
CREATE INDEX idx_sl_usage_points_uuid ON service_location_usage_points(usage_point_uuid);
CREATE INDEX idx_sl_usage_points_last_sync ON service_location_usage_points(last_synchronized);

-- Customer Agreement Table
-- Legal agreement between customer and service provider
CREATE TABLE customer_agreements (
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
    
    -- Customer agreement specific fields
    load_mgmt_cap VARCHAR(255),
    sign_date TIMESTAMP(6) WITH TIME ZONE,
    validity_interval_start BIGINT,
    validity_interval_duration BIGINT,
    budget_bill VARCHAR(255),
    
    -- Relationships within customer schema
    customer_id BIGINT REFERENCES customers(id) ON DELETE CASCADE,
    service_location_id BIGINT REFERENCES service_locations(id) ON DELETE SET NULL
);

-- Create indexes for customer_agreements
CREATE INDEX idx_customer_agreement_uuid ON customer_agreements(uuid);
CREATE INDEX idx_customer_agreement_customer_id ON customer_agreements(customer_id);
CREATE INDEX idx_customer_agreement_service_location_id ON customer_agreements(service_location_id);
CREATE INDEX idx_customer_agreement_sign_date ON customer_agreements(sign_date);
CREATE INDEX idx_customer_agreement_created ON customer_agreements(created);
CREATE INDEX idx_customer_agreement_updated ON customer_agreements(updated);

-- Related Links Table for Customer Agreements
CREATE TABLE customer_agreement_related_links (
    customer_agreement_id BIGINT NOT NULL REFERENCES customer_agreements(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_customer_agreement_related_links ON customer_agreement_related_links(customer_agreement_id);

-- Create trigger function for updating timestamps (reuse from usage schema if exists)
CREATE OR REPLACE FUNCTION update_updated_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_customers_updated BEFORE UPDATE ON customers FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_customer_accounts_updated BEFORE UPDATE ON customer_accounts FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_service_locations_updated BEFORE UPDATE ON service_locations FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_customer_agreements_updated BEFORE UPDATE ON customer_agreements FOR EACH ROW EXECUTE FUNCTION update_updated_column();