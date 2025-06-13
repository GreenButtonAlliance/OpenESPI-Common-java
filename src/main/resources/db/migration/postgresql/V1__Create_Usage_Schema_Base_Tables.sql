/*
 * OpenESPI Usage Schema - Base Tables Migration (PostgreSQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates the foundational tables for the OpenESPI usage schema.
 * These tables implement the NAESB Energy Services Provider Interface (ESPI) 1.0 specification
 * for energy usage data exchange and Green Button data standards.
 *
 * PostgreSQL-specific implementation with proper data types and indexing.
 */

-- Set schema for usage data
SET search_path TO openespi_usage;

-- Application Information Table
-- Stores information about third-party applications that access usage data
CREATE TABLE application_information (
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
    
    -- Application specific fields
    client_name VARCHAR(255),
    client_id VARCHAR(255) NOT NULL UNIQUE,
    client_secret VARCHAR(255),
    client_id_issued_at BIGINT,
    client_secret_expires_at BIGINT,
    registration_client_uri VARCHAR(1024),
    registration_access_token VARCHAR(1024),
    redirect_uris TEXT,
    software_id VARCHAR(255),
    software_version VARCHAR(255),
    token_endpoint_auth_method VARCHAR(50),
    response_types VARCHAR(255),
    grant_types VARCHAR(255),
    application_type VARCHAR(50),
    contacts TEXT,
    logo_uri VARCHAR(1024),
    policy_uri VARCHAR(1024),
    tos_uri VARCHAR(1024),
    jwks_uri VARCHAR(1024),
    sector_identifier_uri VARCHAR(1024),
    subject_type VARCHAR(50),
    id_token_signed_response_alg VARCHAR(50),
    id_token_encrypted_response_alg VARCHAR(50),
    id_token_encrypted_response_enc VARCHAR(50),
    userinfo_signed_response_alg VARCHAR(50),
    userinfo_encrypted_response_alg VARCHAR(50),
    userinfo_encrypted_response_enc VARCHAR(50),
    request_object_signing_alg VARCHAR(50),
    request_object_encryption_alg VARCHAR(50),
    request_object_encryption_enc VARCHAR(50),
    default_max_age BIGINT,
    require_auth_time BOOLEAN DEFAULT FALSE,
    default_acr_values VARCHAR(255),
    initiate_login_uri VARCHAR(1024),
    request_uris TEXT
);

-- Create indexes for application_information
CREATE INDEX idx_application_client_id ON application_information(client_id);
CREATE INDEX idx_application_uuid ON application_information(uuid);
CREATE INDEX idx_application_created ON application_information(created);
CREATE INDEX idx_application_updated ON application_information(updated);

-- Related Links Table for Application Information
CREATE TABLE application_information_related_links (
    application_information_id BIGINT NOT NULL REFERENCES application_information(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_app_info_related_links ON application_information_related_links(application_information_id);

-- Retail Customer Table
-- Represents energy consumers with access to their usage data
CREATE TABLE retail_customers (
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
    
    -- Retail customer specific fields
    username VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    role VARCHAR(50) DEFAULT 'ROLE_USER'
);

-- Create indexes for retail_customers
CREATE INDEX idx_retail_customer_uuid ON retail_customers(uuid);
CREATE INDEX idx_retail_customer_username ON retail_customers(username);
CREATE INDEX idx_retail_customer_created ON retail_customers(created);
CREATE INDEX idx_retail_customer_updated ON retail_customers(updated);

-- Related Links Table for Retail Customers
CREATE TABLE retail_customer_related_links (
    retail_customer_id BIGINT NOT NULL REFERENCES retail_customers(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_retail_customer_related_links ON retail_customer_related_links(retail_customer_id);

-- Authorization Table
-- OAuth 2.0 authorization grants for third-party access to usage data
CREATE TABLE authorizations (
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
    
    -- Authorization specific fields
    application_information_id BIGINT REFERENCES application_information(id) ON DELETE CASCADE,
    retail_customer_id BIGINT REFERENCES retail_customers(id) ON DELETE CASCADE,
    access_token VARCHAR(1024),
    refresh_token VARCHAR(1024),
    authorization_uri VARCHAR(1024),
    ap_title VARCHAR(255),
    ap_description TEXT,
    ap_duration BIGINT,
    scope VARCHAR(1024),
    token_type VARCHAR(50) DEFAULT 'Bearer',
    expires_in BIGINT,
    state VARCHAR(255),
    error VARCHAR(255),
    error_description TEXT,
    error_uri VARCHAR(1024),
    resource_uri VARCHAR(1024),
    customer_resource_uri VARCHAR(1024),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    expires_at TIMESTAMP(6) WITH TIME ZONE,
    grant_type VARCHAR(50),
    response_type VARCHAR(50),
    third_party VARCHAR(255)
);

-- Create indexes for authorizations
CREATE INDEX idx_authorization_uuid ON authorizations(uuid);
CREATE INDEX idx_authorization_access_token ON authorizations(access_token);
CREATE INDEX idx_authorization_refresh_token ON authorizations(refresh_token);
CREATE INDEX idx_authorization_app_id ON authorizations(application_information_id);
CREATE INDEX idx_authorization_customer_id ON authorizations(retail_customer_id);
CREATE INDEX idx_authorization_status ON authorizations(status);
CREATE INDEX idx_authorization_expires_at ON authorizations(expires_at);
CREATE INDEX idx_authorization_created ON authorizations(created);
CREATE INDEX idx_authorization_updated ON authorizations(updated);

-- Related Links Table for Authorizations
CREATE TABLE authorization_related_links (
    authorization_id BIGINT NOT NULL REFERENCES authorizations(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_authorization_related_links ON authorization_related_links(authorization_id);

-- Service Delivery Point Table
-- Physical locations where energy services are delivered
CREATE TABLE service_delivery_points (
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
    
    -- Service delivery point specific fields
    name VARCHAR(256),
    tariff_profile VARCHAR(256),
    customer_agreement VARCHAR(256)
);

-- Create indexes for service_delivery_points
CREATE INDEX idx_sdp_uuid ON service_delivery_points(uuid);
CREATE INDEX idx_sdp_name ON service_delivery_points(name);
CREATE INDEX idx_sdp_tariff_profile ON service_delivery_points(tariff_profile);
CREATE INDEX idx_sdp_customer_agreement ON service_delivery_points(customer_agreement);
CREATE INDEX idx_sdp_created ON service_delivery_points(created);
CREATE INDEX idx_sdp_updated ON service_delivery_points(updated);

-- Related Links Table for Service Delivery Points
CREATE TABLE service_delivery_point_related_links (
    service_delivery_point_id BIGINT NOT NULL REFERENCES service_delivery_points(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_sdp_related_links ON service_delivery_point_related_links(service_delivery_point_id);

-- Create trigger function for updating timestamps
CREATE OR REPLACE FUNCTION update_updated_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_application_information_updated BEFORE UPDATE ON application_information FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_retail_customers_updated BEFORE UPDATE ON retail_customers FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_authorizations_updated BEFORE UPDATE ON authorizations FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_service_delivery_points_updated BEFORE UPDATE ON service_delivery_points FOR EACH ROW EXECUTE FUNCTION update_updated_column();