/*
 * OpenESPI Customer Schema - Organization and Support Tables Migration
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for organizations, service suppliers, and supporting
 * entities within the customer schema following the NAESB ESPI 1.0 specification.
 */

-- Set schema for customer data
USE openespi_customer;

-- Organization Table
-- Organized structure of human resources, often with a purpose or mission
CREATE TABLE organizations (
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
    
    -- Organization specific fields
    electronic_address VARCHAR(255),
    phone1 VARCHAR(50),
    phone2 VARCHAR(50),
    postal_address TEXT,
    street_address TEXT,
    
    INDEX idx_organization_uuid (uuid),
    INDEX idx_organization_electronic_address (electronic_address),
    INDEX idx_organization_created (created),
    INDEX idx_organization_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Organizations
CREATE TABLE organization_related_links (
    organization_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE,
    INDEX idx_organization_related_links (organization_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Organization Role Table
-- Role that an organization plays with respect to other organizations
CREATE TABLE organization_roles (
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
    
    -- Organization role specific fields
    role_type VARCHAR(100),
    
    -- Relationships within customer schema
    organization_id BIGINT,
    
    FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE,
    
    INDEX idx_organization_role_uuid (uuid),
    INDEX idx_organization_role_type (role_type),
    INDEX idx_organization_role_organization_id (organization_id),
    INDEX idx_organization_role_created (created),
    INDEX idx_organization_role_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Organization Roles
CREATE TABLE organization_role_related_links (
    organization_role_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (organization_role_id) REFERENCES organization_roles(id) ON DELETE CASCADE,
    INDEX idx_organization_role_related_links (organization_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Service Supplier Table
-- Organization that provides services to customers
CREATE TABLE service_suppliers (
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
    
    -- Service supplier specific fields
    issuer_identification_number VARCHAR(100),
    kind VARCHAR(50),
    
    -- Relationships within customer schema
    organization_role_id BIGINT,
    
    FOREIGN KEY (organization_role_id) REFERENCES organization_roles(id) ON DELETE CASCADE,
    
    INDEX idx_service_supplier_uuid (uuid),
    INDEX idx_service_supplier_issuer_id (issuer_identification_number),
    INDEX idx_service_supplier_kind (kind),
    INDEX idx_service_supplier_org_role_id (organization_role_id),
    INDEX idx_service_supplier_created (created),
    INDEX idx_service_supplier_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Service Suppliers
CREATE TABLE service_supplier_related_links (
    service_supplier_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (service_supplier_id) REFERENCES service_suppliers(id) ON DELETE CASCADE,
    INDEX idx_service_supplier_related_links (service_supplier_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Agreement Table
-- Contractual agreement between parties
CREATE TABLE agreements (
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
    
    -- Agreement specific fields
    sign_date DATETIME(6),
    validity_interval_start BIGINT,
    validity_interval_duration BIGINT,
    
    INDEX idx_agreement_uuid (uuid),
    INDEX idx_agreement_sign_date (sign_date),
    INDEX idx_agreement_validity_start (validity_interval_start),
    INDEX idx_agreement_created (created),
    INDEX idx_agreement_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Agreements
CREATE TABLE agreement_related_links (
    agreement_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (agreement_id) REFERENCES agreements(id) ON DELETE CASCADE,
    INDEX idx_agreement_related_links (agreement_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Work Location Table
-- Information about a particular location for various forms of work
CREATE TABLE work_locations (
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
    
    -- Work location specific fields
    block_number VARCHAR(50),
    lot_number VARCHAR(50),
    subdivision VARCHAR(255),
    
    -- Relationships within customer schema
    location_id BIGINT,
    
    FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL,
    
    INDEX idx_work_location_uuid (uuid),
    INDEX idx_work_location_block_number (block_number),
    INDEX idx_work_location_lot_number (lot_number),
    INDEX idx_work_location_subdivision (subdivision),
    INDEX idx_work_location_location_id (location_id),
    INDEX idx_work_location_created (created),
    INDEX idx_work_location_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Work Locations
CREATE TABLE work_location_related_links (
    work_location_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (work_location_id) REFERENCES work_locations(id) ON DELETE CASCADE,
    INDEX idx_work_location_related_links (work_location_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Document Table
-- Parent class for different groupings of information collected and managed
CREATE TABLE documents (
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
    
    -- Document specific fields
    created_date_time DATETIME(6),
    last_modified_date_time DATETIME(6),
    revision_number VARCHAR(50),
    subject VARCHAR(255),
    title VARCHAR(255),
    doc_status VARCHAR(50),
    comment TEXT,
    
    INDEX idx_document_uuid (uuid),
    INDEX idx_document_title (title),
    INDEX idx_document_subject (subject),
    INDEX idx_document_doc_status (doc_status),
    INDEX idx_document_created_date_time (created_date_time),
    INDEX idx_document_created (created),
    INDEX idx_document_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Documents
CREATE TABLE document_related_links (
    document_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (document_id) REFERENCES documents(id) ON DELETE CASCADE,
    INDEX idx_document_related_links (document_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Program Date ID Mappings Table
-- Support table for program date mappings
CREATE TABLE program_date_id_mappings (
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
    
    -- Program date mapping specific fields
    program_date DATETIME(6),
    program_id VARCHAR(255),
    
    INDEX idx_program_date_mapping_uuid (uuid),
    INDEX idx_program_date_mapping_program_date (program_date),
    INDEX idx_program_date_mapping_program_id (program_id),
    INDEX idx_program_date_mapping_created (created),
    INDEX idx_program_date_mapping_updated (updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Related Links Table for Program Date ID Mappings
CREATE TABLE program_date_id_mapping_related_links (
    program_date_id_mapping_id BIGINT NOT NULL,
    related_links VARCHAR(1024),
    FOREIGN KEY (program_date_id_mapping_id) REFERENCES program_date_id_mappings(id) ON DELETE CASCADE,
    INDEX idx_program_date_mapping_related_links (program_date_id_mapping_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;