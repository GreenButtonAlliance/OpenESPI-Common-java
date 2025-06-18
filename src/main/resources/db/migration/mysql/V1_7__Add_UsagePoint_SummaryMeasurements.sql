-- 
-- Migration to add SummaryMeasurement fields to UsagePoint
-- estimatedLoad, nominalServiceVoltage, ratedCurrent, ratedPower as embedded SummaryMeasurements
--

-- Add SummaryMeasurement columns for estimatedLoad
ALTER TABLE usage_points 
ADD COLUMN estimated_load_multiplier VARCHAR(50) AFTER sdp_customer_agreement,
ADD COLUMN estimated_load_timestamp BIGINT AFTER estimated_load_multiplier,
ADD COLUMN estimated_load_uom VARCHAR(50) AFTER estimated_load_timestamp,
ADD COLUMN estimated_load_value BIGINT AFTER estimated_load_uom;

-- Add SummaryMeasurement columns for nominalServiceVoltage
ALTER TABLE usage_points 
ADD COLUMN nominal_voltage_multiplier VARCHAR(50) AFTER estimated_load_value,
ADD COLUMN nominal_voltage_timestamp BIGINT AFTER nominal_voltage_multiplier,
ADD COLUMN nominal_voltage_uom VARCHAR(50) AFTER nominal_voltage_timestamp,
ADD COLUMN nominal_voltage_value BIGINT AFTER nominal_voltage_uom;

-- Add SummaryMeasurement columns for ratedCurrent
ALTER TABLE usage_points 
ADD COLUMN rated_current_multiplier VARCHAR(50) AFTER nominal_voltage_value,
ADD COLUMN rated_current_timestamp BIGINT AFTER rated_current_multiplier,
ADD COLUMN rated_current_uom VARCHAR(50) AFTER rated_current_timestamp,
ADD COLUMN rated_current_value BIGINT AFTER rated_current_uom;

-- Add SummaryMeasurement columns for ratedPower
ALTER TABLE usage_points 
ADD COLUMN rated_power_multiplier VARCHAR(50) AFTER rated_current_value,
ADD COLUMN rated_power_timestamp BIGINT AFTER rated_power_multiplier,
ADD COLUMN rated_power_uom VARCHAR(50) AFTER rated_power_timestamp,
ADD COLUMN rated_power_value BIGINT AFTER rated_power_uom;

-- Create indexes for performance on commonly queried measurement values
CREATE INDEX idx_usage_points_estimated_load_value ON usage_points(estimated_load_value);
CREATE INDEX idx_usage_points_nominal_voltage_value ON usage_points(nominal_voltage_value);
CREATE INDEX idx_usage_points_rated_current_value ON usage_points(rated_current_value);
CREATE INDEX idx_usage_points_rated_power_value ON usage_points(rated_power_value);