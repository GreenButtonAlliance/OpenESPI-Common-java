/*
 * OpenESPI Usage Schema - Base Tables Migration
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates the foundational tables for the OpenESPI usage schema.
 * These tables implement the NAESB Energy Services Provider Interface (ESPI) 1.0 specification
 * for energy usage data exchange and Green Button data standards.
 */

-- Set schema for usage data
USE openespi_usage;

-- Application Information Table
-- Stores information about third-party applications that access usage data
CREATE TABLE application_information (
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
    request_uris TEXT,
    
    INDEX idx_application_client_id (client_id),
    INDEX idx_application_uuid (uuid),
    INDEX idx_application_created (created),
    INDEX idx_application_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Application Information
CREATE TABLE application_information_related_links (
    application_information_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (application_information_id) REFERENCES application_information(id) ON DELETE CASCADE,
    INDEX idx_app_info_related_links (application_information_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Retail Customer Table
-- Represents energy consumers with access to their usage data
CREATE TABLE retail_customers (
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
    
    -- Retail customer specific fields
    username VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    role VARCHAR(50) DEFAULT 'ROLE_USER',
    
    INDEX idx_retail_customer_uuid (uuid),
    INDEX idx_retail_customer_username (username),
    INDEX idx_retail_customer_created (created),
    INDEX idx_retail_customer_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Retail Customers
CREATE TABLE retail_customer_related_links (
    retail_customer_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (retail_customer_id) REFERENCES retail_customers(id) ON DELETE CASCADE,
    INDEX idx_retail_customer_related_links (retail_customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Authorization Table
-- OAuth 2.0 authorization grants for third-party access to usage data
CREATE TABLE authorizations (
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
    
    -- Authorization specific fields
    application_information_id BIGINT,
    retail_customer_id BIGINT,
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
    expires_at DATETIME(6),
    grant_type VARCHAR(50),
    response_type VARCHAR(50),
    third_party VARCHAR(255),
    
    FOREIGN KEY (application_information_id) REFERENCES application_information(id) ON DELETE CASCADE,
    FOREIGN KEY (retail_customer_id) REFERENCES retail_customers(id) ON DELETE CASCADE,
    
    INDEX idx_authorization_uuid (uuid),
    INDEX idx_authorization_access_token (access_token(255)),
    INDEX idx_authorization_refresh_token (refresh_token(255)),
    INDEX idx_authorization_app_id (application_information_id),
    INDEX idx_authorization_customer_id (retail_customer_id),
    INDEX idx_authorization_status (status),
    INDEX idx_authorization_expires_at (expires_at),
    INDEX idx_authorization_created (created),
    INDEX idx_authorization_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Authorizations
CREATE TABLE authorization_related_links (
    authorization_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (authorization_id) REFERENCES authorizations(id) ON DELETE CASCADE,
    INDEX idx_authorization_related_links (authorization_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Service Delivery Point Table
-- Physical locations where energy services are delivered
CREATE TABLE service_delivery_points (
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
    
    -- Service delivery point specific fields
    name VARCHAR(256),
    tariff_profile VARCHAR(256),
    customer_agreement VARCHAR(256),
    
    INDEX idx_sdp_uuid (uuid),
    INDEX idx_sdp_name (name),
    INDEX idx_sdp_tariff_profile (tariff_profile),
    INDEX idx_sdp_customer_agreement (customer_agreement),
    INDEX idx_sdp_created (created),
    INDEX idx_sdp_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Service Delivery Points
CREATE TABLE service_delivery_point_related_links (
    service_delivery_point_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (service_delivery_point_id) REFERENCES service_delivery_points(id) ON DELETE CASCADE,
    INDEX idx_sdp_related_links (service_delivery_point_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;