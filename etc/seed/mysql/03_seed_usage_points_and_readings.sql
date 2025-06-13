/*
 * OpenESPI Seed Data - Usage Points and Reading Data (MySQL)
 * 
 * Copyright (c) 2018-2025 Green Button Alliance, Inc.
 * Licensed under the Apache License, Version 2.0
 *
 * This script populates sample usage points, meter readings, and interval readings
 * with realistic energy consumption patterns following ESPI specifications.
 */

USE openespi_usage;

-- Clear existing data (respecting foreign key constraints)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE reading_qualities;
TRUNCATE TABLE interval_readings;
TRUNCATE TABLE interval_blocks;
TRUNCATE TABLE meter_reading_related_links;
TRUNCATE TABLE meter_readings;
TRUNCATE TABLE reading_types;
TRUNCATE TABLE usage_point_related_links;
TRUNCATE TABLE usage_points;
TRUNCATE TABLE service_delivery_point_related_links;
TRUNCATE TABLE service_delivery_points;
TRUNCATE TABLE time_configurations;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Time Configurations (Local Time Parameters)
INSERT INTO time_configurations (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    dst_end_rule, dst_offset, dst_start_rule, tz_offset
) VALUES 
-- Pacific Standard Time
(
    '550e8400-e29b-41d4-a716-446655440100',
    'Pacific Standard Time (PST) with Daylight Saving Time',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/LocalTimeParameters',
    'self', '/espi/1_1/resource/LocalTimeParameters/1',
    0x80000002, -- First Sunday in November at 2:00 AM
    3600, -- 1 hour DST offset
    0x80000009, -- Second Sunday in March at 2:00 AM
    -28800 -- -8 hours from UTC
),
-- Eastern Standard Time
(
    '550e8400-e29b-41d4-a716-446655440101',
    'Eastern Standard Time (EST) with Daylight Saving Time',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/LocalTimeParameters',
    'self', '/espi/1_1/resource/LocalTimeParameters/2',
    0x80000002, -- First Sunday in November at 2:00 AM
    3600, -- 1 hour DST offset
    0x80000009, -- Second Sunday in March at 2:00 AM
    -18000 -- -5 hours from UTC
),
-- Central Standard Time
(
    '550e8400-e29b-41d4-a716-446655440102',
    'Central Standard Time (CST) with Daylight Saving Time',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/LocalTimeParameters',
    'self', '/espi/1_1/resource/LocalTimeParameters/3',
    0x80000002, -- First Sunday in November at 2:00 AM
    3600, -- 1 hour DST offset
    0x80000009, -- Second Sunday in March at 2:00 AM
    -21600 -- -6 hours from UTC
);

-- Insert Service Delivery Points
INSERT INTO service_delivery_points (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    name, tariff_profile, customer_agreement
) VALUES 
-- Residential delivery points
(
    '550e8400-e29b-41d4-a716-446655440200',
    'Residential service delivery point for single family home',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ServiceDeliveryPoint',
    'self', '/espi/1_1/resource/ServiceDeliveryPoint/1',
    'Main House Electric Service',
    'Residential TOU Schedule A',
    'AGR-2023-001'
),
(
    '550e8400-e29b-41d4-a716-446655440201',
    'Residential service delivery point for apartment unit',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ServiceDeliveryPoint',
    'self', '/espi/1_1/resource/ServiceDeliveryPoint/2',
    'Apartment Unit 205 Electric Service',
    'Residential Standard Rate',
    'AGR-2023-002'
),
-- Commercial delivery points
(
    '550e8400-e29b-41d4-a716-446655440202',
    'Small commercial service delivery point',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ServiceDeliveryPoint',
    'self', '/espi/1_1/resource/ServiceDeliveryPoint/3',
    'Small Business Electric Service',
    'Commercial Small General Service',
    'AGR-2023-003'
);

-- Insert Reading Types (different energy measurement types)
INSERT INTO reading_types (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    accumulation_behaviour, argument_numerator, argument_denominator,
    commodity, consumption_tier, cpp, currency, data_qualifier,
    default_quality, flow_direction, interharmonic_numerator, interharmonic_denominator,
    interval_length, kind, measuring_period, phase, power_of_ten_multiplier,
    time_attribute, tou, uom
) VALUES 
-- Energy Delivered (kWh)
(
    '550e8400-e29b-41d4-a716-446655440300',
    'Energy delivered in kWh with 15-minute intervals',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ReadingType',
    'self', '/espi/1_1/resource/ReadingType/1',
    'deltaData', 1, 1, 'electricity', NULL, NULL, 'USD', 'average',
    'valid', 'forward', NULL, NULL, 900, 'energy', 'fifteenMinute',
    'notApplicable', 0, 'notApplicable', NULL, 'Wh'
),
-- Power Delivered (kW)
(
    '550e8400-e29b-41d4-a716-446655440301',
    'Power delivered in kW with 15-minute intervals',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ReadingType',
    'self', '/espi/1_1/resource/ReadingType/2',
    'instantaneous', 1, 1, 'electricity', NULL, NULL, 'USD', 'average',
    'valid', 'forward', NULL, NULL, 900, 'power', 'fifteenMinute',
    'notApplicable', 3, 'notApplicable', NULL, 'W'
),
-- Energy Received (for solar/net metering)
(
    '550e8400-e29b-41d4-a716-446655440302',
    'Energy received from customer generation (solar)',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/ReadingType',
    'self', '/espi/1_1/resource/ReadingType/3',
    'deltaData', 1, 1, 'electricity', NULL, NULL, 'USD', 'average',
    'valid', 'reverse', NULL, NULL, 900, 'energy', 'fifteenMinute',
    'notApplicable', 0, 'notApplicable', NULL, 'Wh'
);

-- Insert Usage Points for each retail customer
INSERT INTO usage_points (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    role_flags, status, uri, local_time_parameters_id,
    retail_customer_id, service_category_kind, service_delivery_point_id
) VALUES 
-- Alan Turing - High usage family home
(
    '550e8400-e29b-41d4-a716-446655440400',
    'Electric usage point for family residence with high consumption',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/UsagePoint',
    'self', '/espi/1_1/resource/UsagePoint/1',
    0x01, 1, '/espi/1_1/resource/UsagePoint/1', 1,
    2, 0, 1 -- Electric service category (0)
),
-- Donald Knuth - Medium usage apartment
(
    '550e8400-e29b-41d4-a716-446655440401',
    'Electric usage point for urban apartment with medium consumption',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/UsagePoint',
    'self', '/espi/1_1/resource/UsagePoint/2',
    0x01, 1, '/espi/1_1/resource/UsagePoint/2', 2,
    3, 0, 2
),
-- Paul Dirac - Low usage efficient home
(
    '550e8400-e29b-41d4-a716-446655440402',
    'Electric usage point for energy efficient home with low consumption',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/UsagePoint',
    'self', '/espi/1_1/resource/UsagePoint/3',
    0x01, 1, '/espi/1_1/resource/UsagePoint/3', 1,
    4, 0, 1
),
-- Charles Babbage - Solar customer (delivery and receipt)
(
    '550e8400-e29b-41d4-a716-446655440403',
    'Electric usage point with solar generation and net metering',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/UsagePoint',
    'self', '/espi/1_1/resource/UsagePoint/4',
    0x01, 1, '/espi/1_1/resource/UsagePoint/4', 1,
    6, 0, 1
),
-- John von Neumann - EV owner
(
    '550e8400-e29b-41d4-a716-446655440404',
    'Electric usage point for residence with electric vehicle charging',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/UsagePoint',
    'self', '/espi/1_1/resource/UsagePoint/5',
    0x01, 1, '/espi/1_1/resource/UsagePoint/5', 1,
    7, 0, 1
);

-- Insert Meter Readings for each usage point
INSERT INTO meter_readings (
    uuid, description, created, updated, published,
    up_link_rel, up_link_href, self_link_rel, self_link_href,
    reading_type_id, usage_point_id
) VALUES 
-- Alan Turing - Energy readings
(
    '550e8400-e29b-41d4-a716-446655440500',
    'Energy consumption meter reading for high usage family home',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/MeterReading',
    'self', '/espi/1_1/resource/MeterReading/1',
    1, 1
),
-- Donald Knuth - Energy readings
(
    '550e8400-e29b-41d4-a716-446655440501',
    'Energy consumption meter reading for medium usage apartment',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/MeterReading',
    'self', '/espi/1_1/resource/MeterReading/2',
    1, 2
),
-- Paul Dirac - Energy readings
(
    '550e8400-e29b-41d4-a716-446655440502',
    'Energy consumption meter reading for low usage efficient home',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/MeterReading',
    'self', '/espi/1_1/resource/MeterReading/3',
    1, 3
),
-- Charles Babbage - Energy delivered readings
(
    '550e8400-e29b-41d4-a716-446655440503',
    'Energy delivered meter reading for solar customer',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/MeterReading',
    'self', '/espi/1_1/resource/MeterReading/4',
    1, 4
),
-- Charles Babbage - Energy received readings (solar generation)
(
    '550e8400-e29b-41d4-a716-446655440504',
    'Energy received meter reading for solar generation',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/MeterReading',
    'self', '/espi/1_1/resource/MeterReading/5',
    3, 4
),
-- John von Neumann - EV charging readings
(
    '550e8400-e29b-41d4-a716-446655440505',
    'Energy consumption meter reading for EV charging home',
    UTC_TIMESTAMP(6), UTC_TIMESTAMP(6), UTC_TIMESTAMP(6),
    'up', '/espi/1_1/resource/MeterReading',
    'self', '/espi/1_1/resource/MeterReading/6',
    1, 5
);

-- Insert related links for usage points
INSERT INTO usage_point_related_links (usage_point_id, related_links)
SELECT 
    up.id,
    CONCAT('/espi/1_1/resource/UsagePoint/', up.id, '/MeterReading') as related_links
FROM usage_points up;

-- Insert related links for meter readings
INSERT INTO meter_reading_related_links (meter_reading_id, related_links)
SELECT 
    mr.id,
    CONCAT('/espi/1_1/resource/MeterReading/', mr.id, '/IntervalBlock') as related_links
FROM meter_readings mr;