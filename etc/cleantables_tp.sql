/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

SET FOREIGN_KEY_CHECKS=0;

USE thirdparty
TRUNCATE TABLE authorizations;
TRUNCATE TABLE BatchList;
TRUNCATE TABLE electric_power_quality_summaries;
TRUNCATE TABLE electric_power_usage_summaries;
TRUNCATE TABLE interval_readings;
TRUNCATE TABLE interval_blocks;
TRUNCATE TABLE meter_readings;
TRUNCATE TABLE meter_reading_related_links;
TRUNCATE TABLE line_item;
TRUNCATE TABLE reading_qualities;
TRUNCATE TABLE reading_types;
TRUNCATE TABLE resources;
TRUNCATE TABLE retail_customers;
TRUNCATE TABLE service_delivery_points;
TRUNCATE TABLE subscriptions;
TRUNCATE TABLE subscriptions_usage_points;
TRUNCATE TABLE time_configurations;
TRUNCATE TABLE usage_point_related_links;
TRUNCATE TABLE usage_points;
TRUNCATE TABLE application_information_scopes;
TRUNCATE TABLE application_information;

SET FOREIGN_KEY_CHECKS=1;
