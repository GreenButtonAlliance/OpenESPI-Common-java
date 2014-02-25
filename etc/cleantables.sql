SET FOREIGN_KEY_CHECKS=0;

USE datacustodian
DELETE FROM authorizations;
DELETE FROM BatchList;
DELETE FROM electric_power_quality_summaries;
DELETE FROM electric_power_usage_summaries;
DELETE FROM interval_readings;
DELETE FROM interval_blocks;
DELETE FROM meter_readings;
DELETE FROM meter_reading_related_links;
DELETE FROM line_item;
DELETE FROM reading_qualities;
DELETE FROM reading_types;
DELETE FROM resources;
DELETE FROM retail_customers;
DELETE FROM service_categories;
DELETE FROM service_delivery_points;
DELETE FROM subscriptions;
DELETE FROM subscriptions_usage_points;
DELETE FROM time_configurations;
DELETE FROM usage_point_related_links;
DELETE FROM usage_points;
DELETE FROM application_information_scopes;
DELETE FROM application_information;


ALTER TABLE application_information AUTO_INCREMENT = 1;
ALTER TABLE authorizations AUTO_INCREMENT = 1;
ALTER TABLE BatchList  AUTO_INCREMENT = 1;
ALTER TABLE electric_power_quality_summaries  AUTO_INCREMENT = 1;
ALTER TABLE electric_power_usage_summaries AUTO_INCREMENT = 1;
ALTER TABLE interval_blocks AUTO_INCREMENT = 1;
ALTER TABLE interval_readings AUTO_INCREMENT = 1;
ALTER TABLE line_item AUTO_INCREMENT = 1;
ALTER TABLE meter_readings AUTO_INCREMENT = 1;
ALTER TABLE reading_qualities AUTO_INCREMENT = 1;
ALTER TABLE reading_types AUTO_INCREMENT = 1;
ALTER TABLE retail_customers AUTO_INCREMENT = 1;
ALTER TABLE retail_customers AUTO_INCREMENT = 1;
ALTER TABLE service_delivery_points AUTO_INCREMENT = 1;
ALTER TABLE subscriptions AUTO_INCREMENT = 1;
ALTER TABLE time_configurations AUTO_INCREMENT = 1;
ALTER TABLE usage_points AUTO_INCREMENT = 1;


USE thirdparty
DELETE FROM authorizations;
DELETE FROM BatchList;
DELETE FROM electric_power_quality_summaries;
DELETE FROM electric_power_usage_summaries;
DELETE FROM interval_readings;
DELETE FROM interval_blocks;
DELETE FROM meter_readings;
DELETE FROM meter_reading_related_links;
DELETE FROM line_item;
DELETE FROM reading_qualities;
DELETE FROM reading_types;
DELETE FROM resources;
DELETE FROM retail_customers;
DELETE FROM service_categories;
DELETE FROM service_delivery_points;
DELETE FROM subscriptions;
DELETE FROM subscriptions_usage_points;
DELETE FROM time_configurations;
DELETE FROM usage_point_related_links;
DELETE FROM usage_points;
DELETE FROM application_information_scopes;
DELETE FROM application_information;


ALTER TABLE application_information AUTO_INCREMENT = 1;
ALTER TABLE authorizations AUTO_INCREMENT = 1;
ALTER TABLE BatchList  AUTO_INCREMENT = 1;
ALTER TABLE electric_power_quality_summaries  AUTO_INCREMENT = 1;
ALTER TABLE electric_power_usage_summaries AUTO_INCREMENT = 1;
ALTER TABLE interval_blocks AUTO_INCREMENT = 1;
ALTER TABLE interval_readings AUTO_INCREMENT = 1;
ALTER TABLE line_item AUTO_INCREMENT = 1;
ALTER TABLE meter_readings AUTO_INCREMENT = 1;
ALTER TABLE reading_qualities AUTO_INCREMENT = 1;
ALTER TABLE reading_types AUTO_INCREMENT = 1;
ALTER TABLE retail_customers AUTO_INCREMENT = 1;
ALTER TABLE retail_customers AUTO_INCREMENT = 1;
ALTER TABLE service_delivery_points AUTO_INCREMENT = 1;
ALTER TABLE subscriptions AUTO_INCREMENT = 1;
ALTER TABLE time_configurations AUTO_INCREMENT = 1;
ALTER TABLE usage_points AUTO_INCREMENT = 1;
