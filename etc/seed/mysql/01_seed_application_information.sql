/*
 * OpenESPI Seed Data - Application Information (MySQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script populates sample application information for third-party applications
 * that can access Green Button energy usage data through OAuth 2.0.
 */

USE openespi_usage;

-- Clear existing data
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE application_information_related_links;
TRUNCATE TABLE application_information;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert sample third-party applications
INSERT INTO application_information (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    client_name, client_id, client_secret, client_id_issued_at,
    redirect_uris, software_id, software_version,
    token_endpoint_auth_method, response_types, grant_types,
    application_type, contacts, logo_uri, policy_uri, tos_uri
) VALUES 
-- Energy Management Application
(
    '550e8400-e29b-41d4-a716-446655440000',
    'Energy Management System for residential customers',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ApplicationInformation',
    'self', '/espi/1_1/resource/ApplicationInformation/1',
    'Green Button Energy Manager',
    'energy_mgr_client_001',
    'energy_mgr_secret_12345678901234567890',
    UNIX_TIMESTAMP() * 1000,
    'https://energy-manager.example.com/callback,https://energy-manager.example.com/oauth/callback',
    'com.greenbuttonalliance.energymanager',
    '2.1.0',
    'client_secret_basic',
    'code',
    'authorization_code,refresh_token',
    'web',
    'support@energy-manager.example.com,admin@energy-manager.example.com',
    'https://energy-manager.example.com/logo.png',
    'https://energy-manager.example.com/privacy',
    'https://energy-manager.example.com/terms'
),
-- Home Automation System
(
    '550e8400-e29b-41d4-a716-446655440001',
    'Smart home automation system with energy optimization',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ApplicationInformation',
    'self', '/espi/1_1/resource/ApplicationInformation/2',
    'SmartHome Energy Optimizer',
    'smarthome_client_002',
    'smarthome_secret_98765432109876543210',
    UNIX_TIMESTAMP() * 1000,
    'https://smarthome.example.com/auth/callback',
    'com.smarthome.energyoptimizer',
    '3.0.1',
    'client_secret_post',
    'code',
    'authorization_code,refresh_token',
    'web',
    'support@smarthome.example.com',
    'https://smarthome.example.com/assets/logo.svg',
    'https://smarthome.example.com/privacy-policy',
    'https://smarthome.example.com/terms-of-service'
),
-- Mobile Energy App
(
    '550e8400-e29b-41d4-a716-446655440002',
    'Mobile application for energy usage tracking and analysis',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ApplicationInformation',
    'self', '/espi/1_1/resource/ApplicationInformation/3',
    'EnergyTracker Mobile',
    'energytracker_mobile_003',
    'mobile_secret_11223344556677889900',
    UNIX_TIMESTAMP() * 1000,
    'com.energytracker.mobile://oauth/callback',
    'com.energytracker.mobile',
    '1.5.2',
    'client_secret_basic',
    'code',
    'authorization_code,refresh_token',
    'native',
    'mobile-support@energytracker.com',
    NULL,
    'https://energytracker.com/mobile/privacy',
    'https://energytracker.com/mobile/terms'
),
-- Analytics Platform
(
    '550e8400-e29b-41d4-a716-446655440003',
    'Advanced energy analytics and reporting platform',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ApplicationInformation',
    'self', '/espi/1_1/resource/ApplicationInformation/4',
    'Energy Analytics Pro',
    'analytics_pro_client_004',
    'analytics_secret_00998877665544332211',
    UNIX_TIMESTAMP() * 1000,
    'https://analytics.energypro.com/oauth/green-button/callback',
    'com.energypro.analytics',
    '4.2.0',
    'client_secret_jwt',
    'code',
    'authorization_code,refresh_token,client_credentials',
    'web',
    'support@energypro.com,api-support@energypro.com',
    'https://analytics.energypro.com/static/logo-256.png',
    'https://energypro.com/privacy',
    'https://energypro.com/terms'
),
-- Test Application (for development)
(
    '550e8400-e29b-41d4-a716-446655440004',
    'Test application for Green Button development and testing',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ApplicationInformation',
    'self', '/espi/1_1/resource/ApplicationInformation/5',
    'Green Button Test Client',
    'test_client_localhost',
    'test_secret_development_only_unsafe',
    UNIX_TIMESTAMP() * 1000,
    'http://localhost:8080/callback,http://localhost:9000/oauth/callback,http://127.0.0.1:8080/callback',
    'com.greenbuttonalliance.testclient',
    '1.0.0-SNAPSHOT',
    'client_secret_basic',
    'code',
    'authorization_code,refresh_token',
    'web',
    'dev@greenbuttonalliance.org',
    NULL,
    'http://localhost:8080/privacy',
    'http://localhost:8080/terms'
);

-- Insert related links for applications
INSERT INTO application_information_related_links (application_information_id, related_links)
SELECT 
    ai.id,
    CASE 
        WHEN ai.client_id = 'energy_mgr_client_001' THEN 'https://energy-manager.example.com/api/v1/subscriptions'
        WHEN ai.client_id = 'smarthome_client_002' THEN 'https://smarthome.example.com/api/energy/subscriptions'
        WHEN ai.client_id = 'energytracker_mobile_003' THEN 'https://api.energytracker.com/v2/subscriptions'
        WHEN ai.client_id = 'analytics_pro_client_004' THEN 'https://api.energypro.com/analytics/subscriptions'
        WHEN ai.client_id = 'test_client_localhost' THEN 'http://localhost:8080/api/subscriptions'
    END as related_links
FROM application_information ai
WHERE ai.client_id IN (
    'energy_mgr_client_001', 
    'smarthome_client_002', 
    'energytracker_mobile_003', 
    'analytics_pro_client_004', 
    'test_client_localhost'
);