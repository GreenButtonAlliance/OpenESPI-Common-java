/*
 * OpenESPI Seed Data - Retail Customers (MySQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script populates sample retail customers (energy consumers) with
 * realistic test data for Green Button energy usage scenarios.
 */

USE openespi_usage;

-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE retail_customer_related_links;
TRUNCATE TABLE retail_customers;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert sample retail customers with diverse profiles
INSERT INTO retail_customers (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    username, first_name, last_name, password, enabled, role
) VALUES 
-- Administrative user (Data Custodian Admin)
(
    'F06E8B03-0299-467E-972A-A883ECDCC575',
    'Data Custodian Administrator with system management privileges',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/1',
    'admin',
    'Grace',
    'Hopper',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lp5KOOWdRMaWf.Z8C', -- 'admin'
    TRUE,
    'ROLE_CUSTODIAN'
),
-- Residential customer - High usage family home
(
    '106E8B03-0299-467E-972A-A883ECDCC575',
    'Residential customer with high energy usage - large family home',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/2',
    'alan.turing',
    'Alan',
    'Turing',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Residential customer - Medium usage apartment
(
    '206E8B03-0299-467E-972A-A883ECDCC575',
    'Residential customer with medium energy usage - urban apartment',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/3',
    'donald.knuth',
    'Donald',
    'Knuth',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Residential customer - Low usage efficient home
(
    '306E8B03-0299-467E-972A-A883ECDCC575',
    'Residential customer with low energy usage - energy efficient home',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/4',
    'paul.dirac',
    'Paul',
    'Dirac',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Small business customer
(
    '406E8B03-0299-467E-972A-A883ECDCC575',
    'Small business customer - local retail shop with variable usage',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/5',
    'alonzo.church',
    'Alonzo',
    'Church',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Customer with solar panels (net metering)
(
    '506E8B03-0299-467E-972A-A883ECDCC575',
    'Residential customer with solar panels and net metering',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/6',
    'charles.babbage',
    'Charles',
    'Babbage',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Electric vehicle owner
(
    '606E8B03-0299-467E-972A-A883ECDCC575',
    'Residential customer with electric vehicle - high usage during charging',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/7',
    'john.vonneumann',
    'John',
    'von Neumann',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Time-of-use customer
(
    '706E8B03-0299-467E-972A-A883ECDCC575',
    'Residential customer on time-of-use billing with smart appliances',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/8',
    'marian.rejewski',
    'Marian',
    'Rejewski',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Multi-unit building customer
(
    '806E8B03-0299-467E-972A-A883ECDCC575',
    'Commercial customer - multi-unit residential building manager',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/9',
    'vint.cerf',
    'Vint',
    'Cerf',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
),
-- Test customer for development
(
    '906E8B03-0299-467E-972A-A883ECDCC575',
    'Test customer for development and testing purposes',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/RetailCustomer',
    'self', '/espi/1_1/resource/RetailCustomer/10',
    'test.user',
    'Test',
    'User',
    '$2a$10$dXJ3SW6G7P2PZ92BSHjwvemFO6RCQ4H9zOtg8RQf.A1jKbUXJb2ga', -- 'password'
    TRUE,
    'ROLE_USER'
);

-- Insert related links for retail customers
INSERT INTO retail_customer_related_links (retail_customer_id, related_links)
SELECT 
    rc.id,
    CONCAT('/espi/1_1/resource/RetailCustomer/', rc.id, '/UsagePoint') as related_links
FROM retail_customers rc;