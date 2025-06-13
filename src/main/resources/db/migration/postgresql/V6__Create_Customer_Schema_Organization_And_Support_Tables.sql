/*
 * OpenESPI Customer Schema - Organization and Support Tables Migration (PostgreSQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This migration creates tables for organizations, service suppliers, and supporting
 * entities within the customer schema following the NAESB ESPI 1.0 specification.
 *
 * PostgreSQL-specific implementation with proper data types and indexing.
 */

-- Set schema for customer data
SET search_path TO openespi_customer;

-- Organization Table
-- Organized structure of human resources, often with a purpose or mission
CREATE TABLE organizations (
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
    
    -- Organization specific fields
    electronic_address VARCHAR(255),
    phone1 VARCHAR(50),
    phone2 VARCHAR(50),
    postal_address TEXT,
    street_address TEXT
);

-- Create indexes for organizations
CREATE INDEX idx_organization_uuid ON organizations(uuid);
CREATE INDEX idx_organization_electronic_address ON organizations(electronic_address);
CREATE INDEX idx_organization_created ON organizations(created);
CREATE INDEX idx_organization_updated ON organizations(updated);

-- Related Links Table for Organizations
CREATE TABLE organization_related_links (
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_organization_related_links ON organization_related_links(organization_id);

-- Organization Role Table
-- Role that an organization plays with respect to other organizations
CREATE TABLE organization_roles (
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
    
    -- Organization role specific fields
    role_type VARCHAR(100),
    
    -- Relationships within customer schema
    organization_id BIGINT REFERENCES organizations(id) ON DELETE CASCADE
);

-- Create indexes for organization_roles
CREATE INDEX idx_organization_role_uuid ON organization_roles(uuid);
CREATE INDEX idx_organization_role_type ON organization_roles(role_type);
CREATE INDEX idx_organization_role_organization_id ON organization_roles(organization_id);
CREATE INDEX idx_organization_role_created ON organization_roles(created);
CREATE INDEX idx_organization_role_updated ON organization_roles(updated);

-- Related Links Table for Organization Roles
CREATE TABLE organization_role_related_links (
    organization_role_id BIGINT NOT NULL REFERENCES organization_roles(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_organization_role_related_links ON organization_role_related_links(organization_role_id);

-- Service Supplier Table
-- Organization that provides services to customers
CREATE TABLE service_suppliers (
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
    
    -- Service supplier specific fields
    issuer_identification_number VARCHAR(100),
    kind VARCHAR(50),
    
    -- Relationships within customer schema
    organization_role_id BIGINT REFERENCES organization_roles(id) ON DELETE CASCADE
);

-- Create indexes for service_suppliers
CREATE INDEX idx_service_supplier_uuid ON service_suppliers(uuid);
CREATE INDEX idx_service_supplier_issuer_id ON service_suppliers(issuer_identification_number);
CREATE INDEX idx_service_supplier_kind ON service_suppliers(kind);
CREATE INDEX idx_service_supplier_org_role_id ON service_suppliers(organization_role_id);
CREATE INDEX idx_service_supplier_created ON service_suppliers(created);
CREATE INDEX idx_service_supplier_updated ON service_suppliers(updated);

-- Related Links Table for Service Suppliers
CREATE TABLE service_supplier_related_links (
    service_supplier_id BIGINT NOT NULL REFERENCES service_suppliers(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_service_supplier_related_links ON service_supplier_related_links(service_supplier_id);

-- Agreement Table
-- Contractual agreement between parties
CREATE TABLE agreements (
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
    
    -- Agreement specific fields
    sign_date TIMESTAMP(6) WITH TIME ZONE,
    validity_interval_start BIGINT,
    validity_interval_duration BIGINT
);

-- Create indexes for agreements
CREATE INDEX idx_agreement_uuid ON agreements(uuid);
CREATE INDEX idx_agreement_sign_date ON agreements(sign_date);
CREATE INDEX idx_agreement_validity_start ON agreements(validity_interval_start);
CREATE INDEX idx_agreement_created ON agreements(created);
CREATE INDEX idx_agreement_updated ON agreements(updated);

-- Related Links Table for Agreements
CREATE TABLE agreement_related_links (
    agreement_id BIGINT NOT NULL REFERENCES agreements(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_agreement_related_links ON agreement_related_links(agreement_id);

-- Work Location Table
-- Information about a particular location for various forms of work
CREATE TABLE work_locations (
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
    
    -- Work location specific fields
    block_number VARCHAR(50),
    lot_number VARCHAR(50),
    subdivision VARCHAR(255),
    
    -- Relationships within customer schema
    location_id BIGINT REFERENCES locations(id) ON DELETE SET NULL
);

-- Create indexes for work_locations
CREATE INDEX idx_work_location_uuid ON work_locations(uuid);
CREATE INDEX idx_work_location_block_number ON work_locations(block_number);
CREATE INDEX idx_work_location_lot_number ON work_locations(lot_number);
CREATE INDEX idx_work_location_subdivision ON work_locations(subdivision);
CREATE INDEX idx_work_location_location_id ON work_locations(location_id);
CREATE INDEX idx_work_location_created ON work_locations(created);
CREATE INDEX idx_work_location_updated ON work_locations(updated);

-- Related Links Table for Work Locations
CREATE TABLE work_location_related_links (
    work_location_id BIGINT NOT NULL REFERENCES work_locations(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_work_location_related_links ON work_location_related_links(work_location_id);

-- Document Table
-- Parent class for different groupings of information collected and managed
CREATE TABLE documents (
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
    
    -- Document specific fields
    created_date_time TIMESTAMP(6) WITH TIME ZONE,
    last_modified_date_time TIMESTAMP(6) WITH TIME ZONE,
    revision_number VARCHAR(50),
    subject VARCHAR(255),
    title VARCHAR(255),
    doc_status VARCHAR(50),
    comment TEXT
);

-- Create indexes for documents
CREATE INDEX idx_document_uuid ON documents(uuid);
CREATE INDEX idx_document_title ON documents(title);
CREATE INDEX idx_document_subject ON documents(subject);
CREATE INDEX idx_document_doc_status ON documents(doc_status);
CREATE INDEX idx_document_created_date_time ON documents(created_date_time);
CREATE INDEX idx_document_created ON documents(created);
CREATE INDEX idx_document_updated ON documents(updated);

-- Related Links Table for Documents
CREATE TABLE document_related_links (
    document_id BIGINT NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_document_related_links ON document_related_links(document_id);

-- Program Date ID Mappings Table
-- Support table for program date mappings
CREATE TABLE program_date_id_mappings (
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
    
    -- Program date mapping specific fields
    program_date TIMESTAMP(6) WITH TIME ZONE,
    program_id VARCHAR(255)
);

-- Create indexes for program_date_id_mappings
CREATE INDEX idx_program_date_mapping_uuid ON program_date_id_mappings(uuid);
CREATE INDEX idx_program_date_mapping_program_date ON program_date_id_mappings(program_date);
CREATE INDEX idx_program_date_mapping_program_id ON program_date_id_mappings(program_id);
CREATE INDEX idx_program_date_mapping_created ON program_date_id_mappings(created);
CREATE INDEX idx_program_date_mapping_updated ON program_date_id_mappings(updated);

-- Related Links Table for Program Date ID Mappings
CREATE TABLE program_date_id_mapping_related_links (
    program_date_id_mapping_id BIGINT NOT NULL REFERENCES program_date_id_mappings(id) ON DELETE CASCADE,
    related_links VARCHAR(1024)
);

CREATE INDEX idx_program_date_mapping_related_links ON program_date_id_mapping_related_links(program_date_id_mapping_id);

-- Create triggers for automatic timestamp updates
CREATE TRIGGER update_organizations_updated BEFORE UPDATE ON organizations FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_organization_roles_updated BEFORE UPDATE ON organization_roles FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_service_suppliers_updated BEFORE UPDATE ON service_suppliers FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_agreements_updated BEFORE UPDATE ON agreements FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_work_locations_updated BEFORE UPDATE ON work_locations FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_documents_updated BEFORE UPDATE ON documents FOR EACH ROW EXECUTE FUNCTION update_updated_column();
CREATE TRIGGER update_program_date_id_mappings_updated BEFORE UPDATE ON program_date_id_mappings FOR EACH ROW EXECUTE FUNCTION update_updated_column();